package numble.bankingserver.domain.account.repository;

import numble.bankingserver.domain.account.entity.Account;
import numble.bankingserver.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(Long accountNumber);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Account a where a.accountNumber = :accountNumber")
    Optional<Account> findByAccountNumberWithPessimisticLock(@Param("accountNumber") Long accountNumber);

    List<Account> findByUserOrderByCreatedAt(User user);

    Optional<Account> findByUserAndAccountNumber(User user, Long accountNumber);

    List<Account> findByUser(User user);
}
