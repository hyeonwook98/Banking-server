package numble.bankingserver.domain.accountfactory.service;

import numble.bankingserver.domain.accountfactory.dto.request.AccountOpenRequest;
import numble.bankingserver.global.enums.AccountType;
import numble.bankingserver.global.exception.BankingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class AccountFactoryServiceTest {

    @Autowired
    AccountFactoryService accountFactoryService;

    @Test
    @Transactional
    @DisplayName("계좌번호 생성 성공")
    void setAccountNumber() {

        AccountOpenRequest accountOpenRequest = new AccountOpenRequest(AccountType.SAVINGS_ACCOUNT);

        Long accountNumber = accountFactoryService.setAccountNumber(accountOpenRequest);
        Long accountNumber2 = accountFactoryService.setAccountNumber(accountOpenRequest);

        Assertions.assertEquals(3333000000000L, accountNumber);
        Assertions.assertEquals(3333000000001L, accountNumber2);
    }

    @Test
    @Transactional
    @DisplayName("계좌번호 생성 에러")
    void setAccountNumberError() {

        Long[] accountNumber = new Long[1];
        Long[] accountNumber2 = new Long[1];

        AccountOpenRequest accountOpenRequest = new AccountOpenRequest(null);

        Assertions.assertThrows(BankingException.class, () -> {
            accountNumber[0] = accountFactoryService.setAccountNumber(accountOpenRequest);
            accountNumber2[0] = accountFactoryService.setAccountNumber(accountOpenRequest);
        });

    }
}