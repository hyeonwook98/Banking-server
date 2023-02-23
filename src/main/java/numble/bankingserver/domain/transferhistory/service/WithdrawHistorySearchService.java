package numble.bankingserver.domain.transferhistory.service;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.account.repository.AccountRepository;
import numble.bankingserver.domain.transferhistory.dto.TransferHistorySearchDto;
import numble.bankingserver.domain.transferhistory.dto.request.TransferHistorySearchRequest;
import numble.bankingserver.domain.transferhistory.entity.TransferHistory;
import numble.bankingserver.domain.transferhistory.repository.TransferHistoryRepository;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.global.enums.TransferType;
import numble.bankingserver.global.error.ErrorCode;
import numble.bankingserver.global.exception.BankingException;
import numble.bankingserver.global.jwt.JwtTokenCheckService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WithdrawHistorySearchService {

    private final AccountRepository accountRepository;
    private final TransferHistoryRepository transferHistoryRepository;
    private final JwtTokenCheckService jwtTokenCheckService;

    @Transactional(readOnly = true)
    public List<TransferHistorySearchDto> searchWithdrawHistory(User hostUser, TransferHistorySearchRequest request) {

        accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new BankingException(ErrorCode.ACCOUNT_NOT_FOUND));

        List<TransferHistory> transferHistories = transferHistoryRepository.
                findByHostAccountNumberAndTransferTypeOrderByCreatedAtDesc(request.getAccountNumber(), TransferType.WITHDRAW);

        List<TransferHistorySearchDto> transferHistoryList = transferHistories.stream()
                .map(t -> new TransferHistorySearchDto(t.getFriendAccountNumber(), t.getFriendName(),
                        t.getTransferType(), t.getAmount(), t.getBalance()))
                .collect(Collectors.toList());

        return transferHistoryList;
    }
}
