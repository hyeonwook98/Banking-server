package numble.bankingserver.domain.account.service;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.account.dto.AccountSearchDto;
import numble.bankingserver.domain.account.dto.response.AccountSearchResponse;
import numble.bankingserver.domain.account.entity.Account;
import numble.bankingserver.domain.account.repository.AccountRepository;
import numble.bankingserver.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountSearchService {

    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public AccountSearchResponse searchAccount(User hostUser) {

        List<Account> accounts = accountRepository.findByUserOrderByCreatedAt(hostUser);
        List<AccountSearchDto> accountList = accounts.stream()
                .map(account -> new AccountSearchDto(
                        account.getAccountNumber(), account.getBalance(), account.getAccountType()))
                .collect(Collectors.toList());

        return new AccountSearchResponse(Long.valueOf(accounts.size()) , accountList);
    }
}
