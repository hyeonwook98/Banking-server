package numble.bankingserver.domain.account.service;

import numble.bankingserver.domain.account.entity.Account;
import numble.bankingserver.domain.account.repository.AccountRepository;
import numble.bankingserver.domain.accountfactory.dto.request.AccountOpenRequest;
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
class AccountOpenServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountOpenService accountOpenService;
    @Autowired
    UserJoinService userJoinService;

    @Test
    @Transactional
    @DisplayName("계좌개설 성공")
    void openAccount() {

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

        List<Account> accountList = accountRepository.findByUser(hostUser.get());

        Assertions.assertTrue(accountList.size() == 1);

    }
}