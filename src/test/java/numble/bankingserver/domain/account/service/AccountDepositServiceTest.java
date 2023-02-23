package numble.bankingserver.domain.account.service;

import numble.bankingserver.domain.account.dto.request.AccountDepositRequest;
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

@Transactional
@SpringBootTest
class AccountDepositServiceTest {

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

    @Test
    @Transactional
    @DisplayName("계좌에 입금하기")
    void depositMoney() {

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
        Account account = accountList.get(0);

        AccountDepositRequest accountDepositRequest = new AccountDepositRequest(account.getAccountNumber(), 100L);
        accountDepositService.depositMoney(accountDepositRequest);

        List<Account> list = accountRepository.findByUser(hostUser.get());

        Assertions.assertEquals(100L, list.get(0).getBalance());

    }
}