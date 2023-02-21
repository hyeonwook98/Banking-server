package numble.bankingserver.domain.transferhistory.service;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.account.entity.Account;
import numble.bankingserver.domain.account.repository.AccountRepository;
import numble.bankingserver.domain.transferhistory.dto.TransferHistorySearchDto;
import numble.bankingserver.domain.transferhistory.dto.request.TransferHistorySearchRequest;
import numble.bankingserver.domain.transferhistory.entity.TransferHistory;
import numble.bankingserver.domain.transferhistory.repository.TransferHistoryRepository;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.domain.user.repository.UserRepository;
import numble.bankingserver.global.error.ErrorCode;
import numble.bankingserver.global.exception.BankingException;
import numble.bankingserver.global.jwt.JwtTokenProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AllHistorySearchService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransferHistoryRepository transferHistoryRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public List<TransferHistorySearchDto> searchAllHistory(HttpServletRequest httpServletRequest,
                                                           TransferHistorySearchRequest request) {

        String bearerToken = jwtTokenProvider.resolveToken(httpServletRequest);
        String token = jwtTokenProvider.parseToken(bearerToken);

        if (token == null) {
            throw new BankingException(ErrorCode.INVALID_JWT);
        }

        String findId = jwtTokenProvider.getTokenSubject(token);
        Optional<User> hostUser = userRepository.findById(findId);

        accountRepository.findByUserAndAccountNumber(hostUser.get(), request.getAccountNumber())
                .orElseThrow(() -> new BankingException(ErrorCode.WRONG_ACCESS));

        List<TransferHistory> transferHistories = transferHistoryRepository.
                findByHostAccountNumberOrderByCreatedAtDesc(request.getAccountNumber());

        List<TransferHistorySearchDto> transferHistoryList = transferHistories.stream()
                .map(t -> new TransferHistorySearchDto(t.getFriendAccountNumber(), t.getFriendName(),
                        t.getTransferType(), t.getAmount(), t.getBalance()))
                .collect(Collectors.toList());

        return transferHistoryList;
    }
}
