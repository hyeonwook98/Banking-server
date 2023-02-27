package numble.bankingserver.domain.transferhistory.service;

import numble.bankingserver.domain.account.dto.request.AccountDepositRequest;
import numble.bankingserver.domain.account.dto.request.AccountVerifyRequest;
import numble.bankingserver.domain.account.entity.Account;
import numble.bankingserver.domain.account.repository.AccountRepository;
import numble.bankingserver.domain.account.service.AccountDepositService;
import numble.bankingserver.domain.account.service.AccountOpenService;
import numble.bankingserver.domain.account.service.AccountTransferService;
import numble.bankingserver.domain.accountfactory.dto.request.AccountOpenRequest;
import numble.bankingserver.domain.transferhistory.dto.TransferHistorySearchDto;
import numble.bankingserver.domain.transferhistory.dto.request.TransferHistorySearchRequest;
import numble.bankingserver.domain.user.dto.request.UserJoinRequest;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.domain.user.repository.UserRepository;
import numble.bankingserver.domain.user.service.UserJoinService;
import numble.bankingserver.global.enums.AccountType;
import numble.bankingserver.global.enums.Gender;
import numble.bankingserver.global.enums.TransferType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class HistorySearchServiceTest {

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
    AccountTransferService accountTransferService;
    @Autowired
    TransferHistorySearchService transferHistorySearchService;

    @Test
    @Transactional
    @DisplayName("이체내역전체조회")
    void searchAllHistory() throws InterruptedException {

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

        AccountDepositRequest accountDepositRequest = new AccountDepositRequest(
                hostAccount.getAccountNumber(), 10000L);
        accountDepositService.depositMoney(hostUser.get(), accountDepositRequest);

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

        AccountVerifyRequest accountVerifyRequest = new AccountVerifyRequest(hostAccount.getAccountNumber(),
                friendAccount.getAccountNumber(), 1000L);
        AccountVerifyRequest accountVerifyRequest2 = new AccountVerifyRequest(friendAccount.getAccountNumber(),
                hostAccount.getAccountNumber(), 1000L);
        accountTransferService.transferAccount(accountVerifyRequest);
        accountTransferService.transferAccount(accountVerifyRequest);
        accountTransferService.transferAccount(accountVerifyRequest);
        accountTransferService.transferAccount(accountVerifyRequest2);

        TransferHistorySearchRequest searchRequest = new TransferHistorySearchRequest(
                hostAccount.getAccountNumber(), null);
        List<TransferHistorySearchDto> list = transferHistorySearchService.searchAllHistory(hostUser.get(), searchRequest);

        Assertions.assertEquals(list.size(), 4);
    }

    @Test
    @Transactional
    @DisplayName("입금내역만조회")
    void searchDepositHistory() throws InterruptedException {

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

        AccountDepositRequest accountDepositRequest = new AccountDepositRequest(
                hostAccount.getAccountNumber(), 10000L);
        accountDepositService.depositMoney(hostUser.get(), accountDepositRequest);

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

        AccountVerifyRequest accountVerifyRequest = new AccountVerifyRequest(hostAccount.getAccountNumber(),
                friendAccount.getAccountNumber(), 1000L);
        AccountVerifyRequest accountVerifyRequest2 = new AccountVerifyRequest(friendAccount.getAccountNumber(),
                hostAccount.getAccountNumber(), 1000L);

        AccountDepositRequest accountDepositRequest2 = new AccountDepositRequest(
                friendAccount.getAccountNumber(), 10000L);
        accountDepositService.depositMoney(friendUser.get(), accountDepositRequest2);

        accountTransferService.transferAccount(accountVerifyRequest);
        accountTransferService.transferAccount(accountVerifyRequest);
        accountTransferService.transferAccount(accountVerifyRequest);
        accountTransferService.transferAccount(accountVerifyRequest2);

        TransferHistorySearchRequest searchRequest = new TransferHistorySearchRequest(
                hostAccount.getAccountNumber(), TransferType.DEPOSIT);
        List<TransferHistorySearchDto> list = transferHistorySearchService.searchAllHistory(hostUser.get(), searchRequest);

        Assertions.assertEquals(list.size(), 1);
    }

    @Test
    @Transactional
    @DisplayName("출금내역만조회")
    void searchWithdrawtHistory() throws InterruptedException {

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

        AccountDepositRequest accountDepositRequest = new AccountDepositRequest(
                hostAccount.getAccountNumber(), 10000L);
        accountDepositService.depositMoney(hostUser.get(), accountDepositRequest);

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

        AccountVerifyRequest accountVerifyRequest = new AccountVerifyRequest(hostAccount.getAccountNumber(),
                friendAccount.getAccountNumber(), 1000L);
        AccountVerifyRequest accountVerifyRequest2 = new AccountVerifyRequest(friendAccount.getAccountNumber(),
                hostAccount.getAccountNumber(), 1000L);

        AccountDepositRequest accountDepositRequest2 = new AccountDepositRequest(
                friendAccount.getAccountNumber(), 10000L);
        accountDepositService.depositMoney(friendUser.get(), accountDepositRequest2);

        accountTransferService.transferAccount(accountVerifyRequest);
        accountTransferService.transferAccount(accountVerifyRequest);
        accountTransferService.transferAccount(accountVerifyRequest);
        accountTransferService.transferAccount(accountVerifyRequest2);

        TransferHistorySearchRequest searchRequest = new TransferHistorySearchRequest(
                hostAccount.getAccountNumber(), TransferType.WITHDRAW);
        List<TransferHistorySearchDto> list = transferHistorySearchService.searchAllHistory(hostUser.get(), searchRequest);

        Assertions.assertEquals(list.size(), 3);
    }
}