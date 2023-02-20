package numble.bankingserver.domain.account.service;

import numble.bankingserver.domain.account.entity.Account;
import numble.bankingserver.domain.account.repository.AccountRepository;
import numble.bankingserver.domain.accountfactory.dto.request.AccountOpenRequest;
import numble.bankingserver.domain.accountfactory.entity.AccountFactory;
import numble.bankingserver.domain.accountfactory.repository.AccountFactoryRepository;
import numble.bankingserver.domain.accountfactory.service.AccountFactoryService;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.domain.user.repository.UserRepository;
import numble.bankingserver.global.enums.AccountType;
import numble.bankingserver.global.enums.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class AccountOpenServiceTest {

    @Autowired
    AccountFactoryService accountFactoryService;

    @Autowired
    AccountFactoryRepository accountFactoryRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("계좌번호 생성 시 동시성 문제 해결")
    void openAccount() throws InterruptedException {

        Optional<AccountFactory> accountFactory = accountFactoryRepository.findById(1L);
        Long findNumber = accountFactory.get().getNumber();

        int threadCount = 100;
        //멀티스레드 이용 ExecutorService : 비동기를 단순하게 처리할 수 있도록 해주는 java api
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        //다른 스레드에서 수행이 완료될 때 까지 대기할 수 있도록 도와주는 API - 요청이 끝날때 까지 기다림
        CountDownLatch latch = new CountDownLatch(threadCount);

        AccountOpenRequest accountOpenRequest = new AccountOpenRequest(AccountType.SAVINGS_ACCOUNT);

        User user = User.builder()
                .id("asdf")
                .password("asdf")
                .name("김현욱")
                .phoneNumber("010-2988-9330")
                .email("hyeonwook98@naver.com")
                .address("경상남도 통영시")
                .gender(Gender.MAN)
                .birthYear("1998-11-17")
                .build();

        userRepository.saveAndFlush(user);
        Optional<User> findUser = userRepository.findByPhoneNumber("010-2988-9330");

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    Long number = accountFactoryService.setAccountNumber(accountOpenRequest);
                    Account account = Account.builder()
                            .user(findUser.get())
                            .accountNumber(number)
                            .accountType(AccountType.SAVINGS_ACCOUNT)
                            .build();

                    accountRepository.save(account);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Optional<AccountFactory> accountFactory1 = accountFactoryRepository.findById(1L);
        Long findNumber1 = accountFactory1.get().getNumber();
        Assertions.assertEquals(100L, findNumber1 - findNumber);
    }
}