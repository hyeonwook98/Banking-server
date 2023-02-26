package numble.bankingserver.domain.account.service;

import numble.bankingserver.domain.accountfactory.dto.request.AccountOpenRequest;
import numble.bankingserver.domain.accountfactory.entity.AccountFactory;
import numble.bankingserver.domain.accountfactory.repository.AccountFactoryRepository;
import numble.bankingserver.domain.accountfactory.service.AccountFactoryService;
import numble.bankingserver.global.enums.AccountType;
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
class AccountOpenRaceConditionTest {

    @Autowired
    AccountFactoryService accountFactoryService;

    @Autowired
    AccountFactoryRepository accountFactoryRepository;

    @Test
    @DisplayName("계좌번호 생성시 동시성 문제 해결")
    void openAccount() throws InterruptedException {

        Long[] number = new Long[1];
        Optional<AccountFactory> accountFactory = accountFactoryRepository.findById(1L);
        Long beforeNumber = accountFactory.get().getNumber();

        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        AccountOpenRequest accountOpenRequest = new AccountOpenRequest(AccountType.SAVINGS_ACCOUNT);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    number[0] = accountFactoryService.setAccountNumber(accountOpenRequest);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Optional<AccountFactory> accountFactory1 = accountFactoryRepository.findById(1L);
        Long afterNumber = accountFactory1.get().getNumber();
        Assertions.assertEquals(100L, afterNumber - beforeNumber);

    }
}