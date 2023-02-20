package numble.bankingserver.domain.transferhistory.repository;

import numble.bankingserver.domain.transferhistory.entity.TransferHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferHistoryRepository extends JpaRepository<TransferHistory, Long> {
}
