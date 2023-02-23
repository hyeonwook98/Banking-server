package numble.bankingserver.domain.user.service;

import numble.bankingserver.domain.account.service.AccountOpenService;
import numble.bankingserver.domain.accountfactory.dto.request.AccountOpenRequest;
import numble.bankingserver.domain.user.dto.request.UserJoinRequest;
import numble.bankingserver.domain.user.dto.request.UserLoginRequest;
import numble.bankingserver.domain.user.dto.response.UserLoginResponse;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.domain.user.repository.UserRepository;
import numble.bankingserver.global.enums.AccountType;
import numble.bankingserver.global.enums.Gender;
import numble.bankingserver.global.exception.BankingException;
import numble.bankingserver.global.jwt.JwtProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@SpringBootTest
class UserDeleteServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserJoinService userJoinService;
    @Autowired
    UserLoginService userLoginService;
    @Autowired
    UserDeleteService userDeleteService;
    @Autowired
    AccountOpenService accountOpenService;

    @Test
    @Transactional
    @DisplayName("유저탈퇴성공")
    void deleteUser() {

        UserJoinRequest request = UserJoinRequest.builder()
                .id("asdf")
                .password("asdf")
                .name("홍길동")
                .phoneNumber("010-0000-0000")
                .email("ghdrlfehd@naver.com")
                .address("경상남도 통영시")
                .gender(Gender.MAN)
                .birthYear("1998-11-17")
                .build();

        userJoinService.joinUser(request);
        Optional<User> hostUser = userRepository.findByPhoneNumber("010-0000-0000");

        userDeleteService.deleteUser(hostUser.get());

        Optional<User> findUser = userRepository.findByPhoneNumber("010-0000-0000");
        Assertions.assertTrue(findUser.isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("계좌가 남아있어 유저삭제 에러발생")
    void deleteUserError() {
        UserJoinRequest request = UserJoinRequest.builder()
                .id("asdf")
                .password("asdf")
                .name("홍길동")
                .phoneNumber("010-0000-0000")
                .email("ghdrlfehd@naver.com")
                .address("경상남도 통영시")
                .gender(Gender.MAN)
                .birthYear("1998-11-17")
                .build();

        userJoinService.joinUser(request);
        Optional<User> hostUser = userRepository.findByPhoneNumber("010-0000-0000");
        AccountOpenRequest accountOpenRequest = new AccountOpenRequest(AccountType.SAVINGS_ACCOUNT);
        accountOpenService.openAccount(hostUser.get(), accountOpenRequest);

        Assertions.assertThrows(BankingException.class, () -> {
            userDeleteService.deleteUser(hostUser.get());
        });
    }
}