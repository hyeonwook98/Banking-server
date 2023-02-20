package numble.bankingserver.domain.account.service;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.account.dto.request.AccountVerifyRequest;
import numble.bankingserver.domain.account.entity.Account;
import numble.bankingserver.domain.account.repository.AccountRepository;
import numble.bankingserver.domain.transferhistory.entity.TransferHistory;
import numble.bankingserver.domain.transferhistory.repository.TransferHistoryRepository;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.domain.user.repository.UserRepository;
import numble.bankingserver.global.dto.response.SuccessResponse;
import numble.bankingserver.global.enums.TransferType;
import numble.bankingserver.global.error.ErrorCode;
import numble.bankingserver.global.exception.BankingException;
import numble.bankingserver.global.jwt.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class AccountTransferService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransferHistoryRepository transferHistoryRepository;

    @Transactional
    public ResponseEntity<SuccessResponse> transferAccount(HttpServletRequest httpServletRequest,
                                                           AccountVerifyRequest request) {

        String bearerToken = jwtTokenProvider.resolveToken(httpServletRequest);
        String token = jwtTokenProvider.parseToken(bearerToken);

        if (token == null) {
            throw new BankingException(ErrorCode.INVALID_JWT);
        }

        String findId = jwtTokenProvider.getTokenSubject(token);
        User hostUser = userRepository.findById(findId)
                .orElseThrow(() -> new BankingException(ErrorCode.USER_NOT_FOUND));

        Account hostAccount = accountRepository.findByAccountNumberWithPessimisticLock(
                request.getHostAccountNumber()).get();
        hostAccount.withdraw(request.getSentAmount());
        Account friendAccount = accountRepository.findByAccountNumberWithPessimisticLock(
                request.getFriendAccountNumber()).get();
        friendAccount.deposit(request.getSentAmount());

        transferHistoryRepository.save(TransferHistory.builder()
                .hostAccount(hostAccount)
                .friendAccountNumber(request.getFriendAccountNumber())
                .friendName(friendAccount.getUser().getName())
                .transferType(TransferType.WITHDRAW)
                .amount(request.getSentAmount())
                .balance(hostAccount.getBalance())
                .build());

        transferHistoryRepository.save(TransferHistory.builder()
                .hostAccount(friendAccount)
                .friendAccountNumber(request.getHostAccountNumber())
                .friendName(hostAccount.getUser().getName())
                .transferType(TransferType.DEPOSIT)
                .amount(request.getSentAmount())
                .balance(friendAccount.getBalance())
                .build());

        return new ResponseEntity<>(
                SuccessResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Transfer Success")
                        .build(),
                HttpStatus.valueOf(HttpStatus.OK.value())
        );
    }
}
