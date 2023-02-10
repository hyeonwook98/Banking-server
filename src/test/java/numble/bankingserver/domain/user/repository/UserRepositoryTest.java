package numble.bankingserver.domain.user.repository;

import numble.bankingserver.domain.user.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(false)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testUser() throws Exception {
        User user = new User();
        user.setUsername("member");

        userRepository.save(user);
        Optional<User> findUser = userRepository.findById(1L);

        Assertions.assertThat(findUser.get().getUsername().equals("member"));
    }
}