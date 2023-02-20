package numble.bankingserver.domain.accountfactory.repository;

import numble.bankingserver.domain.accountfactory.entity.AccountFactory;
import numble.bankingserver.global.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;

public interface AccountFactoryRepository extends JpaRepository<AccountFactory, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    AccountFactory findByAccountType(AccountType accountType);
}
