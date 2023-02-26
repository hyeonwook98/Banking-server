package numble.bankingserver.domain.account.service;

import numble.bankingserver.domain.account.dto.request.AccountDepositRequest;
import numble.bankingserver.domain.account.dto.request.AccountVerifyRequest;
import numble.bankingserver.domain.account.entity.Account;
import numble.bankingserver.domain.account.repository.AccountRepository;
import numble.bankingserver.domain.accountfactory.dto.request.AccountOpenRequest;
import numble.bankingserver.domain.transferhistory.repository.TransferHistoryRepository;
import numble.bankingserver.domain.user.dto.request.UserJoinRequest;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.domain.user.repository.UserRepository;
import numble.bankingserver.domain.user.service.UserJoinService;
import numble.bankingserver.global.enums.AccountType;
import numble.bankingserver.global.enums.Gender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class AccountTransferRaceConditionTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransferHistoryRepository transferHistoryRepository;
    @Autowired
    AccountOpenService accountOpenService;
    @Autowired
    AccountDepositService accountDepositService;
    @Autowired
    UserJoinService userJoinService;
    @Autowired
    AccountTransferService accountTransferService;

    @BeforeEach
    void settingUser() {

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

        AccountDepositRequest accountDepositRequest = new AccountDepositRequest(hostAccount.getAccountNumber(), 10000L);
        accountDepositService.depositMoney(accountDepositRequest);

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

        AccountDepositRequest accountDepositRequest1 = new AccountDepositRequest(friendAccount.getAccountNumber(), 10000L);
        accountDepositService.depositMoney(accountDepositRequest1);

        UserJoinRequest request2 = UserJoinRequest.builder()
                .id("ee")
                .password("ee")
                .name("이순신")
                .phoneNumber("010-2222-2222")
                .email("dltnstls@naver.com")
                .address("경상남도 통영시")
                .gender(Gender.MAN)
                .birthYear("1998-11-17")
                .build();

        userJoinService.joinUser(request2);
        Optional<User> thirdUser = userRepository.findById("ee");

        AccountOpenRequest accountOpenRequest2 = new AccountOpenRequest(AccountType.SAVINGS_ACCOUNT);
        accountOpenService.openAccount(thirdUser.get(), accountOpenRequest2);

    }

    @AfterEach
    void delete() {
        Optional<User> aaaa = userRepository.findById("aaaa");
        Optional<User> asdf = userRepository.findById("asdf");
        Optional<User> ee = userRepository.findById("ee");

        List<Account> friendAccountList1 = accountRepository.findByUser(aaaa.get());
        Account account1 = friendAccountList1.get(0);
        List<Account> friendAccountList2 = accountRepository.findByUser(asdf.get());
        Account account2 = friendAccountList2.get(0);
        List<Account> friendAccountList3 = accountRepository.findByUser(ee.get());
        Account account3 = friendAccountList3.get(0);

        accountRepository.delete(account1);
        accountRepository.delete(account2);
        accountRepository.delete(account3);

        userRepository.delete(aaaa.get());
        userRepository.delete(asdf.get());
        userRepository.delete(ee.get());

        transferHistoryRepository.deleteAllByHostAccountNumber(account1.getAccountNumber());
        transferHistoryRepository.deleteAllByHostAccountNumber(account2.getAccountNumber());
        transferHistoryRepository.deleteAllByHostAccountNumber(account3.getAccountNumber());

    }

    @Test
    @DisplayName("계좌이체 시 동시성 문제 해결")
    void transferAccount() throws InterruptedException {

        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        Optional<User> hostUser = userRepository.findById("asdf");
        List<Account> hostAccountList = accountRepository.findByUser(hostUser.get());
        Account hostAccount = hostAccountList.get(0);


        Optional<User> friendUser = userRepository.findById("aaaa");
        List<Account> friendAccountList = accountRepository.findByUser(friendUser.get());
        Account friendAccount = friendAccountList.get(0);

        Optional<User> thirdUser = userRepository.findById("ee");
        List<Account> thirdAccountList = accountRepository.findByUser(thirdUser.get());
        Account thirdAccount = thirdAccountList.get(0);

        AccountVerifyRequest accountVerifyRequest1 = new AccountVerifyRequest(hostAccount.getAccountNumber(),
                thirdAccount.getAccountNumber(), 100L);
        AccountVerifyRequest accountVerifyRequest2 = new AccountVerifyRequest(friendAccount.getAccountNumber(),
                thirdAccount.getAccountNumber(), 100L);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    accountTransferService.transferAccount(accountVerifyRequest1);
                    accountTransferService.transferAccount(accountVerifyRequest2);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        List<Account> hostAccountLists = accountRepository.findByUser(hostUser.get());
        List<Account> friendAccountLists = accountRepository.findByUser(friendUser.get());
        List<Account> thirdAccountLists = accountRepository.findByUser(thirdUser.get());

        Assertions.assertTrue(hostAccountLists.get(0).getBalance() == 0);
        Assertions.assertTrue(friendAccountLists.get(0).getBalance() == 0);
        Assertions.assertTrue(thirdAccountLists.get(0).getBalance() == 20000);

    }
}