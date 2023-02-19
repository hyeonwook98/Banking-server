package numble.bankingserver.domain.account.repository;

import numble.bankingserver.domain.account.entity.Account;
import numble.bankingserver.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {


    Optional<Account> findByAccountNumber(Long accountNumber);
    void deleteByAccountNumber(Long accountNumber);

    List<Account> findByUserOrderByCreatedAt(User user);
}
