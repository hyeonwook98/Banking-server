package numble.bankingserver.domain.transferhistory.repository;

import numble.bankingserver.domain.account.entity.Account;
import numble.bankingserver.domain.transferhistory.entity.TransferHistory;
import numble.bankingserver.global.enums.TransferType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferHistoryRepository extends JpaRepository<TransferHistory, Long> {
    List<TransferHistory> findByHostAccountNumberOrderByCreatedAtDesc(Long accountNumber);
    List<TransferHistory> findByHostAccountNumberAndTransferTypeOrderByCreatedAtDesc(Long accountNumber, TransferType type);
}
