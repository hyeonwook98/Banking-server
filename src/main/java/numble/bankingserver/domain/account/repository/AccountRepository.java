package numble.bankingserver.domain.account.repository;

import numble.bankingserver.domain.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    void deleteByAccountNumber(Long accountNumber);
}
