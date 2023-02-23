package numble.bankingserver.domain.account.service;

import numble.bankingserver.domain.account.dto.request.AccountDepositRequest;
import numble.bankingserver.domain.account.dto.request.AccountVerifyRequest;
import numble.bankingserver.domain.account.dto.response.AccountVerifyResponse;
import numble.bankingserver.domain.account.entity.Account;
import numble.bankingserver.domain.account.repository.AccountRepository;
import numble.bankingserver.domain.accountfactory.dto.request.AccountOpenRequest;
import numble.bankingserver.domain.friendlist.dto.request.FriendAddRequest;
import numble.bankingserver.domain.friendlist.service.FriendAddService;
import numble.bankingserver.domain.user.dto.request.UserJoinRequest;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.domain.user.repository.UserRepository;
import numble.bankingserver.domain.user.service.UserJoinService;
import numble.bankingserver.global.enums.AccountType;
import numble.bankingserver.global.enums.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class AccountVerifyServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountOpenService accountOpenService;
    @Autowired
    AccountDepositService accountDepositService;
    @Autowired
    UserJoinService userJoinService;
    @Autowired
    AccountVerifyService accountVerifyService;
    @Autowired
    FriendAddService friendAddService;

    @Test
    @Transactional
    @DisplayName("계좌검증 성공")
    void verifyAccount() {

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
        Optional<User> hostUser = userRepository.findById("asdf");

        AccountOpenRequest accountOpenRequest = new AccountOpenRequest(AccountType.SAVINGS_ACCOUNT);
        accountOpenService.openAccount(hostUser.get(), accountOpenRequest);

        List<Account> hostAccountList = accountRepository.findByUser(hostUser.get());
        Account hostAccount = hostAccountList.get(0);

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
        Optional<User> friendUser = userRepository.findById("aaaa");

        AccountOpenRequest accountOpenRequest1 = new AccountOpenRequest(AccountType.SAVINGS_ACCOUNT);
        accountOpenService.openAccount(friendUser.get(), accountOpenRequest1);

        List<Account> friendAccountList = accountRepository.findByUser(friendUser.get());
        Account friendAccount = friendAccountList.get(0);

        FriendAddRequest friendAddRequest = new FriendAddRequest(friendUser.get().getPhoneNumber());
        friendAddService.addFriend(hostUser.get(), friendAddRequest);

        AccountVerifyRequest accountVerifyRequest = new AccountVerifyRequest(hostAccount.getAccountNumber(),
                friendAccount.getAccountNumber(), 1000L);

        AccountVerifyResponse accountVerifyResponse = accountVerifyService.
                verifyAccount(hostUser.get(), accountVerifyRequest);

        Assertions.assertEquals(accountVerifyResponse.getSentAmount(), 1000L);
    }
}