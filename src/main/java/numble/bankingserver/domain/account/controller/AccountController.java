package numble.bankingserver.domain.account.controller;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.account.dto.request.AccountCloseRequest;
import numble.bankingserver.domain.account.dto.request.AccountDepositRequest;
import numble.bankingserver.domain.account.dto.request.AccountVerifyRequest;
import numble.bankingserver.domain.account.dto.response.AccountSearchResponse;
import numble.bankingserver.domain.account.dto.response.AccountVerifyResponse;
import numble.bankingserver.domain.account.service.AccountCloseService;
import numble.bankingserver.domain.account.service.AccountDepositService;
import numble.bankingserver.domain.account.service.AccountOpenService;
import numble.bankingserver.domain.account.service.AccountSearchService;
import numble.bankingserver.domain.account.service.AccountTransferService;
import numble.bankingserver.domain.account.service.AccountVerifyService;
import numble.bankingserver.domain.accountfactory.dto.request.AccountOpenRequest;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.global.dto.response.SuccessResponse;
import numble.bankingserver.global.jwt.JwtTokenCheckService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/account")
@RestController
public class AccountController {

    private final JwtTokenCheckService jwtTokenCheckService;
    private final AccountOpenService accountOpenService;
    private final AccountCloseService accountCloseService;
    private final AccountDepositService accountDepositService;
    private final AccountSearchService accountSearchService;
    private final AccountVerifyService accountVerifyService;
    private final AccountTransferService accountTransferService;

    @PostMapping("/open")
    public ResponseEntity<SuccessResponse> openAccount(HttpServletRequest httpServletRequest,
                                                       @RequestBody @Valid AccountOpenRequest request) {
        User hostUser = jwtTokenCheckService.checkToken(httpServletRequest);
        return accountOpenService.openAccount(hostUser, request);
    }

    @PostMapping("/close")
    public ResponseEntity<SuccessResponse> closeAccount(HttpServletRequest httpServletRequest,
                                                        @RequestBody @Valid AccountCloseRequest request) {
        User hostUser = jwtTokenCheckService.checkToken(httpServletRequest);
        return accountCloseService.closeAccount(hostUser, request);
    }

    @PostMapping("/deposit")
    public ResponseEntity<SuccessResponse> depositMoney(HttpServletRequest httpServletRequest,
                                                        @RequestBody @Valid AccountDepositRequest request) {
        User hostUser = jwtTokenCheckService.checkToken(httpServletRequest);
        return accountDepositService.depositMoney(hostUser, request);
    }

    @GetMapping("/search")
    public AccountSearchResponse searchAccount(HttpServletRequest httpServletRequest) {
        User hostUser = jwtTokenCheckService.checkToken(httpServletRequest);
        return accountSearchService.searchAccount(hostUser);
    }

    @PostMapping("/verify")
    public AccountVerifyResponse verifyAccount(HttpServletRequest httpServletRequest,
                                               @RequestBody @Valid AccountVerifyRequest request) {
        User hostUser = jwtTokenCheckService.checkToken(httpServletRequest);
        return accountVerifyService.verifyAccount(hostUser, request);
    }

    @PostMapping("/transfer")
    public ResponseEntity<SuccessResponse> transferAccount(HttpServletRequest httpServletRequest,
                                               @RequestBody @Valid AccountVerifyRequest request) throws InterruptedException {
        jwtTokenCheckService.checkToken(httpServletRequest);
        return accountTransferService.transferAccount(request);
    }
}
