package numble.bankingserver.domain.transferhistory.service;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.account.repository.AccountRepository;
import numble.bankingserver.domain.transferhistory.dto.TransferHistorySearchDto;
import numble.bankingserver.domain.transferhistory.dto.request.TransferHistorySearchRequest;
import numble.bankingserver.domain.transferhistory.entity.TransferHistory;
import numble.bankingserver.domain.transferhistory.repository.TransferHistoryCustomRepositoryImpl;
import numble.bankingserver.domain.transferhistory.repository.TransferHistoryRepository;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.global.error.ErrorCode;
import numble.bankingserver.global.exception.BankingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HistorySearchService {

    private final AccountRepository accountRepository;
    private final TransferHistoryCustomRepositoryImpl transferHistoryCustomRepository;

    @Transactional(readOnly = true)
    public List<TransferHistorySearchDto> searchAllHistory(User hostUser,
                                                           TransferHistorySearchRequest request) {

        accountRepository.findByUserAndAccountNumber(hostUser, request.getAccountNumber())
                .orElseThrow(() -> new BankingException(ErrorCode.WRONG_ACCESS));

        List<TransferHistory> transferHistories = transferHistoryCustomRepository.
                findTransferHistories(request);

        List<TransferHistorySearchDto> transferHistoryList = transferHistories.stream()
                .map(t -> new TransferHistorySearchDto(t.getFriendAccountNumber(), t.getFriendName(),
                        t.getTransferType(), t.getAmount(), t.getBalance(), t.getCreatedAt()))
                .collect(Collectors.toList());

        return transferHistoryList;
    }
}
