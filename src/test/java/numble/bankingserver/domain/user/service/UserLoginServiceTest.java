package numble.bankingserver.domain.user.service;

import numble.bankingserver.domain.user.dto.request.UserLoginRequest;
import numble.bankingserver.domain.user.dto.response.UserLoginResponse;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.domain.user.repository.UserRepository;
import numble.bankingserver.global.enums.Gender;
import numble.bankingserver.global.exception.BankingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletResponse;

@SpringBootTest
class UserLoginServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserLoginService userLoginService;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Test
    @Transactional
    @DisplayName("로그인 성공")
    public void userLoginSuccess() {

        ServletWebRequest servletContainer = (ServletWebRequest) RequestContextHolder.getRequestAttributes();
        HttpServletResponse httpServletResponse = servletContainer.getResponse();

        User user = User.builder()
                .id("asdf")
                .password(passwordEncoder.encode("asdf"))
                .name("홍길동")
                .phoneNumber("010-0000-0000")
                .email("ghdrlfehd@naver.com")
                .address("경상남도 통영시")
                .gender(Gender.MAN)
                .birthYear("1998-11-17")
                .build();

        userRepository.save(user);

        UserLoginRequest request = UserLoginRequest.builder()
                .id("asdf")
                .password("asdf")
                .build();

        UserLoginResponse response = userLoginService.loginUser(request);

        Assertions.assertTrue(!response.getAccessToken().isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("로그인 실패")
    public void userLoginFail() {

        ServletWebRequest servletContainer = (ServletWebRequest) RequestContextHolder.getRequestAttributes();
        HttpServletResponse httpServletResponse = servletContainer.getResponse();

        User user = User.builder()
                .id("asdf")
                .password(passwordEncoder.encode("asdf"))
                .name("홍길동")
                .phoneNumber("010-0000-0000")
                .email("ghdrlfehd@naver.com")
                .address("경상남도 통영시")
                .gender(Gender.MAN)
                .birthYear("1998-11-17")
                .build();

        userRepository.save(user);

        UserLoginRequest request = UserLoginRequest.builder()
                .id("asdf")
                .password("11")
                .build();

        Assertions.assertThrows(BankingException.class, () -> {
            userLoginService.loginUser(request);
        });

    }
}