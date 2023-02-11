package numble.bankingserver.domain.account.controller;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.account.dto.request.CloseAccountRequest;
import numble.bankingserver.domain.account.service.AccountCloseService;
import numble.bankingserver.domain.account.service.AccountOpenService;
import numble.bankingserver.global.dto.response.SuccessResponse;
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

    private final AccountOpenService accountOpenService;
    private final AccountCloseService accountCloseService;

    @GetMapping("/open")
    public ResponseEntity<SuccessResponse> openAccount(HttpServletRequest request) {
        return accountOpenService.openAccount(request);
    }

    @PostMapping("/close")
    public ResponseEntity<SuccessResponse> closeAccount(@RequestBody @Valid CloseAccountRequest closeAccountRequest,
                                                        HttpServletRequest request) {
        return accountCloseService.closeAccount(closeAccountRequest, request);
    }
}
