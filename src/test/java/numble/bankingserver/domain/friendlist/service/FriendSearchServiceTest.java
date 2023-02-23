package numble.bankingserver.domain.friendlist.service;

import numble.bankingserver.domain.friendlist.dto.request.FriendAddRequest;
import numble.bankingserver.domain.friendlist.dto.response.SearchFriendListResponse;
import numble.bankingserver.domain.friendlist.repository.FriendListRepository;
import numble.bankingserver.domain.user.dto.request.UserJoinRequest;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.domain.user.repository.UserRepository;
import numble.bankingserver.domain.user.service.UserJoinService;
import numble.bankingserver.global.enums.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
class FriendSearchServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    FriendListRepository friendListRepository;
    @Autowired
    FriendAddService friendAddService;
    @Autowired
    UserJoinService userJoinService;
    @Autowired
    FriendSearchService friendSearchService;

    @Test
    @Transactional
    @DisplayName("친구목록 조회 성공")
    void searchfriends() {

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

        FriendAddRequest friendAddRequest = new FriendAddRequest("010-1111-1111");
        friendAddService.addFriend(findUser.get(), friendAddRequest);

        SearchFriendListResponse response = friendSearchService.searchFriend(findUser.get());
        Assertions.assertTrue(response.getCount() == 1);

    }

    @Test
    @Transactional
    @DisplayName("친구목록 없음")
    void searchfriendsError() {

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

        SearchFriendListResponse response = friendSearchService.searchFriend(findUser.get());
        Assertions.assertTrue(response.getCount() == 0);

    }
}