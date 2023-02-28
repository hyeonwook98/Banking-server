package numble.bankingserver.domain.transferhistory.repository;

import numble.bankingserver.domain.transferhistory.entity.TransferHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface TransferHistoryRepository extends JpaRepository<TransferHistory, Long> {
    @Transactional
    void deleteAllByHostAccountNumber(Long accountNumber);
}
