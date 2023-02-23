package numble.bankingserver.domain.friendlist.service;

import numble.bankingserver.domain.friendlist.dto.request.FriendAddRequest;
import numble.bankingserver.domain.friendlist.entity.FriendList;
import numble.bankingserver.domain.friendlist.repository.FriendListRepository;
import numble.bankingserver.domain.user.dto.request.UserJoinRequest;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.domain.user.repository.UserRepository;
import numble.bankingserver.domain.user.service.UserJoinService;
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
class FriendAddServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    FriendListRepository friendListRepository;
    @Autowired
    FriendAddService friendAddService;
    @Autowired
    UserJoinService userJoinService;

    @Test
    @Transactional
    @DisplayName("친구추가 성공")
    void addFriend() {

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
        Optional<User> findUser = userRepository.findById("asdf");

        UserJoinRequest request1 = UserJoinRequest.builder()
                .id("aaaa")
                .password("aaaa")
                .name("임꺽정")
                .phoneNumber("010-1111-1111")
                .email("dlaRjrwjd@naver.com")
                .address("경상남도 통영시")
                .gender(Gender.MAN)
                .birthYear("1998-11-17")
                .build();

        userJoinService.joinUser(request1);
        Optional<User> findUser1 = userRepository.findById("aaaa");

        FriendAddRequest friendAddRequest = new FriendAddRequest("010-1111-1111");
        friendAddService.addFriend(findUser.get(), friendAddRequest);

        Optional<FriendList> friendList = friendListRepository.
                findByHostUserAndFriendUser(findUser.get(), findUser1.get());

        Assertions.assertTrue(!friendList.isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("이미 친구상태여서 에러발생")
    void addFriendError() {
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
        Optional<User> findUser = userRepository.findById("asdf");

        UserJoinRequest request1 = UserJoinRequest.builder()
                .id("aaaa")
                .password("aaaa")
                .name("임꺽정")
                .phoneNumber("010-1111-1111")
                .email("dlaRjrwjd@naver.com")
                .address("경상남도 통영시")
                .gender(Gender.MAN)
                .birthYear("1998-11-17")
                .build();

        userJoinService.joinUser(request1);
        Optional<User> findUser1 = userRepository.findById("aaaa");

        FriendAddRequest friendAddRequest = new FriendAddRequest("010-1111-1111");
        friendAddService.addFriend(findUser.get(), friendAddRequest);

        Assertions.assertThrows(BankingException.class, () -> {
            friendAddService.addFriend(findUser.get(), friendAddRequest);
        });
    }
}