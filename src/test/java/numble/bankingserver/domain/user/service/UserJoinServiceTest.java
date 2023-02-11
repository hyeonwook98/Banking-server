package numble.bankingserver.domain.user.service;

import numble.bankingserver.domain.user.dto.request.UserJoinRequest;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.domain.user.repository.UserRepository;
import numble.bankingserver.global.enums.Gender;
import numble.bankingserver.global.exception.BankingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
class UserJoinServiceTest {

    @Autowired
    UserJoinService userJoinService;
    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    @DisplayName("회원가입성공")
    public void userJoin() {

        UserJoinRequest request = UserJoinRequest.builder()
                .id("asdf")
                .password("asdf")
                .name("김현욱")
                .phoneNumber("010-2988-9330")
                .email("hyeonwook98@naver.com")
                .address("경상남도 통영시")
                .gender(Gender.MAN)
                .birthYear("1998-11-17")
                .build();

        userJoinService.joinUser(request);
        Optional<User> findUser = userRepository.findById("asdf");

        Assertions.assertEquals(request.getId(), findUser.get().getId());
    }

    @Test
    @Transactional
    @DisplayName("아이디중복으로 인한 회원가입 예외")
    public void joinErrorById() {

        UserJoinRequest request = UserJoinRequest.builder()
                .id("asdf")
                .password("asdf")
                .name("김현욱")
                .phoneNumber("010-2988-9330")
                .email("hyeonwook98@naver.com")
                .address("경상남도 통영시")
                .gender(Gender.MAN)
                .birthYear("1998-11-17")
                .build();

        userJoinService.joinUser(request);

        UserJoinRequest request1 = UserJoinRequest.builder()
                .id("asdf")
                .password("asdf")
                .name("김현욱")
                .phoneNumber("010-2988-9331")
                .email("hyeonwook88@naver.com")
                .address("경상남도 통영시")
                .gender(Gender.MAN)
                .birthYear("1998-11-17")
                .build();

        Assertions.assertThrows(BankingException.class, () -> {
            userJoinService.joinUser(request1);
        });
    }

    @Test
    @Transactional
    @DisplayName("이메일중복으로 인한 회원가입 예외")
    public void joinErrorByEmail() {

        UserJoinRequest request = UserJoinRequest.builder()
                .id("asdf")
                .password("asdf")
                .name("김현욱")
                .phoneNumber("010-2988-9330")
                .email("hyeonwook98@naver.com")
                .address("경상남도 통영시")
                .gender(Gender.MAN)
                .birthYear("1998-11-17")
                .build();

        userJoinService.joinUser(request);

        UserJoinRequest request1 = UserJoinRequest.builder()
                .id("asdf1")
                .password("asdf")
                .name("김현욱")
                .phoneNumber("010-2988-9331")
                .email("hyeonwook98@naver.com")
                .address("경상남도 통영시")
                .gender(Gender.MAN)
                .birthYear("1998-11-17")
                .build();

        Assertions.assertThrows(BankingException.class, () -> {
            userJoinService.joinUser(request1);
        });
    }

    @Test
    @Transactional
    @DisplayName("휴대폰번호중복으로 인한 회원가입 예외")
    public void joinErrorByPhoneNumber() {

        UserJoinRequest request = UserJoinRequest.builder()
                .id("asdf")
                .password("asdf")
                .name("김현욱")
                .phoneNumber("010-2988-9330")
                .email("hyeonwook98@naver.com")
                .address("경상남도 통영시")
                .gender(Gender.MAN)
                .birthYear("1998-11-17")
                .build();

        userJoinService.joinUser(request);

        UserJoinRequest request1 = UserJoinRequest.builder()
                .id("asd1")
                .password("asdf")
                .name("김현욱")
                .phoneNumber("010-2988-9330")
                .email("hyeonwook9@naver.com")
                .address("경상남도 통영시")
                .gender(Gender.MAN)
                .birthYear("1998-11-17")
                .build();

        Assertions.assertThrows(BankingException.class, () -> {
            userJoinService.joinUser(request1);
        });
    }
}