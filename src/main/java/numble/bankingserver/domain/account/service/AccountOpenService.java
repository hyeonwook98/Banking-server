package numble.bankingserver.domain.account.service;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.account.entity.Account;
import numble.bankingserver.domain.account.repository.AccountRepository;
import numble.bankingserver.domain.accountnumber.dto.request.AccountOpenRequest;
import numble.bankingserver.domain.accountnumber.service.AccountFactoryService;
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
    private final AccountFactoryService accountFactoryService;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public ResponseEntity<SuccessResponse> openAccount(HttpServletRequest httpServletRequest,
                                                       AccountOpenRequest request) {

        String bearerToken = jwtTokenProvider.resolveToken(httpServletRequest);
        String token = jwtTokenProvider.parseToken(bearerToken);

        if (token == null) {
            throw new BankingException(ErrorCode.INVALID_JWT);
        }

        String findId = jwtTokenProvider.getTokenSubject(token);
        Optional<User> findUser = userRepository.findById(findId);
        Long accountNumber = accountFactoryService.setAccountNumber(request);

        Account account = Account.builder()
                .user(findUser.get())
                .accountNumber(accountNumber)
                .accountType(request.getAccountType())
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
