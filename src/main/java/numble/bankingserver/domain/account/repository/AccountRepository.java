package numble.bankingserver.domain.account.repository;

import numble.bankingserver.domain.account.entity.Account;
import numble.bankingserver.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    void deleteByAccountNumber(Long accountNumber);

    List<Account> findByUserOrderByCreatedAt(User user);
}
