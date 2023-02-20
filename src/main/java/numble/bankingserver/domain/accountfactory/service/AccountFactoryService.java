package numble.bankingserver.domain.accountfactory.service;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.accountfactory.entity.AccountFactory;
import numble.bankingserver.domain.accountfactory.dto.request.AccountOpenRequest;
import numble.bankingserver.domain.accountfactory.repository.AccountFactoryRepository;
import numble.bankingserver.global.enums.AccountType;
import numble.bankingserver.global.error.ErrorCode;
import numble.bankingserver.global.exception.BankingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AccountFactoryService {

    private final AccountFactoryRepository accountFactoryRepository;

    @Transactional
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
