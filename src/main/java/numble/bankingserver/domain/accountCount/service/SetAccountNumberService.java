package numble.bankingserver.domain.accountCount.service;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.accountCount.domain.AccountCount;
import numble.bankingserver.global.error.ErrorCode;
import numble.bankingserver.global.exception.BankingException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SetAccountNumberService {

    public Long setAccountNumber() {

        if (AccountCount.count != 1000000000) {
            return 3333000000000L + AccountCount.count;
        } else {
            throw new BankingException(ErrorCode.ACCOUNT_NUMBER_OVER);
        }
    }
}
