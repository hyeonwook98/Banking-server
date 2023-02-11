package numble.bankingserver.domain.account.service;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.account.entity.Account;
import numble.bankingserver.domain.account.repository.AccountRepository;
import numble.bankingserver.domain.accountCount.domain.AccountCount;
import numble.bankingserver.domain.accountCount.service.SetAccountNumberService;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.domain.user.repository.UserRepository;
import numble.bankingserver.global.dto.response.SuccessResponse;
import numble.bankingserver.global.error.ErrorCode;
import numble.bankingserver.global.exception.BankingException;
import numble.bankingserver.global.jwt.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountOpenService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final SetAccountNumberService setAccountNumberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AccountCount accountCount;

    @Transactional
    public ResponseEntity<SuccessResponse> openAccount(HttpServletRequest request) {

        String bearerToken = jwtTokenProvider.resolveToken(request);
        String token = jwtTokenProvider.parseToken(bearerToken);

        if (token == null) {
            throw new BankingException(ErrorCode.INVALID_JWT);
        }

        String findId = jwtTokenProvider.getTokenSubject(token);
        Optional<User> findUser = userRepository.findById(findId);
        Long acountNumber = setAccountNumberService.setAccountNumber();
        accountCount.addCount();

        Account account = Account.builder()
                .user(findUser.get())
                .accountNumber(acountNumber)
                .build();

        account.setUser(findUser.get());

        accountRepository.save(account);

        return new ResponseEntity<>(
                SuccessResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Account Open Success")
                        .build(),
                HttpStatus.valueOf(HttpStatus.CREATED.value())
        );
    }
}
