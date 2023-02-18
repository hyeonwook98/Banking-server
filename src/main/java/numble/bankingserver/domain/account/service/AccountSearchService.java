package numble.bankingserver.domain.account.service;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.account.dto.SearchAccountDto;
import numble.bankingserver.domain.account.dto.response.SearchAccountResponse;
import numble.bankingserver.domain.account.entity.Account;
import numble.bankingserver.domain.account.repository.AccountRepository;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.domain.user.repository.UserRepository;
import numble.bankingserver.global.error.ErrorCode;
import numble.bankingserver.global.exception.BankingException;
import numble.bankingserver.global.jwt.JwtTokenProvider;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountSearchService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public SearchAccountResponse searchAccount(HttpServletRequest httpServletRequest) {

        String bearerToken = jwtTokenProvider.resolveToken(httpServletRequest);
        String token = jwtTokenProvider.parseToken(bearerToken);

        if (token == null) {
            throw new BankingException(ErrorCode.INVALID_JWT);
        }

        String findId = jwtTokenProvider.getTokenSubject(token);
        User findUser = userRepository.findById(findId)
                .orElseThrow(() -> new BankingException(ErrorCode.USER_NOT_FOUND));

        List<Account> accounts = accountRepository.findByUserOrderByCreatedAt(findUser);
        List<SearchAccountDto> accountList = accounts.stream()
                .map(account -> new SearchAccountDto(
                        account.getAccountNumber(), account.getBalance(), account.getAccountType()))
                .collect(Collectors.toList());

        return new SearchAccountResponse(Long.valueOf(accounts.size()) , accountList);
    }
}
