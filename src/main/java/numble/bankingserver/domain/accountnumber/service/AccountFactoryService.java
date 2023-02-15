package numble.bankingserver.domain.accountnumber.service;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.accountnumber.domain.AccountFactory;
import numble.bankingserver.domain.accountnumber.dto.request.AccountOpenRequest;
import numble.bankingserver.domain.accountnumber.repository.AccountFactoryRepository;
import numble.bankingserver.domain.enums.AccountType;
import numble.bankingserver.global.error.ErrorCode;
import numble.bankingserver.global.exception.BankingException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountFactoryService {

    private final AccountFactoryRepository accountFactoryRepository;

    public Long setAccountNumber(AccountOpenRequest request) {

        if (request.getAccountType() != AccountType.SAVINGS_ACCOUNT) {
            throw new BankingException(ErrorCode.WRONG_ACCOUNT_TYPE);
        }

        AccountFactory findAccountNumberInfo = accountFactoryRepository.findByAccountType(request.getAccountType());
        Long findNumber = findAccountNumberInfo.getNumber();
        if (findNumber != 1000000000) {
            findAccountNumberInfo.addNumber();
            return 3333000000000L + findNumber;
        } else {
            throw new BankingException(ErrorCode.ACCOUNT_NUMBER_OVER);
        }
    }
}
