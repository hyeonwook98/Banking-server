package numble.bankingserver.domain.transferhistory.repository;

import numble.bankingserver.domain.account.entity.Account;
import numble.bankingserver.domain.transferhistory.entity.TransferHistory;
import numble.bankingserver.global.enums.TransferType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TransferHistoryRepository extends JpaRepository<TransferHistory, Long> {
    @Transactional
    void deleteAllByHostAccountNumber(Long accountNumber);
}
