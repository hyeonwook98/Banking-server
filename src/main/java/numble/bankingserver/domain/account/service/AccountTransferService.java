package numble.bankingserver.domain.account.service;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.account.dto.request.AccountVerifyRequest;
import numble.bankingserver.domain.account.entity.Account;
import numble.bankingserver.domain.account.repository.AccountRepository;
import numble.bankingserver.domain.transferhistory.entity.TransferHistory;
import numble.bankingserver.domain.transferhistory.repository.TransferHistoryRepository;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.global.dto.response.SuccessResponse;
import numble.bankingserver.global.enums.TransferType;
import numble.bankingserver.global.jwt.JwtTokenCheckService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class AccountTransferService {

    private final AccountRepository accountRepository;
    private final TransferHistoryRepository transferHistoryRepository;

    @Transactional
    public ResponseEntity<SuccessResponse> transferAccount(AccountVerifyRequest request) {

        Account hostAccount = accountRepository.findByAccountNumberWithPessimisticLock(
                request.getHostAccountNumber()).get();
        hostAccount.withdraw(request.getSentAmount());
        Account friendAccount = accountRepository.findByAccountNumberWithPessimisticLock(
                request.getFriendAccountNumber()).get();
        friendAccount.deposit(request.getSentAmount());

        transferHistoryRepository.save(TransferHistory.builder()
                .hostAccountNumber(hostAccount.getAccountNumber())
                .friendAccountNumber(request.getFriendAccountNumber())
                .friendName(friendAccount.getUser().getName())
                .transferType(TransferType.WITHDRAW)
                .amount(request.getSentAmount())
                .balance(hostAccount.getBalance())
                .build());

        transferHistoryRepository.save(TransferHistory.builder()
                .hostAccountNumber(friendAccount.getAccountNumber())
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
