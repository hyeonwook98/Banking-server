package numble.bankingserver.domain.accountnumber.repository;

import numble.bankingserver.domain.accountnumber.domain.AccountFactory;
import numble.bankingserver.domain.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;

public interface AccountFactoryRepository extends JpaRepository<AccountFactory, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    AccountFactory findByAccountType(AccountType accountType);
}
