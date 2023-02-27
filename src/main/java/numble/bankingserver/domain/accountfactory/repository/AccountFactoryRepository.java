package numble.bankingserver.domain.accountfactory.repository;

import numble.bankingserver.domain.accountfactory.entity.AccountFactory;
import numble.bankingserver.global.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

public interface AccountFactoryRepository extends JpaRepository<AccountFactory, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="3000")})
    AccountFactory findByAccountType(AccountType accountType);

}
