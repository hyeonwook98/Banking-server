package numble.bankingserver.domain.account.service;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.account.entity.Account;
import numble.bankingserver.domain.account.repository.AccountRepository;
import numble.bankingserver.domain.accountfactory.dto.request.AccountOpenRequest;
import numble.bankingserver.domain.accountfactory.service.AccountFactoryService;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.global.dto.response.SuccessResponse;
import numble.bankingserver.global.jwt.JwtTokenCheckService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class AccountOpenService {

    private final AccountRepository accountRepository;
    private final AccountFactoryService accountFactoryService;

    @Transactional
    public ResponseEntity<SuccessResponse> openAccount(User hostUser, AccountOpenRequest request) {

        Long accountNumber = accountFactoryService.setAccountNumber(request);

        Account account = Account.builder()
                .user(hostUser)
                .accountNumber(accountNumber)
                .accountType(request.getAccountType())
                .build();

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
