package numble.bankingserver.domain.transferhistory.controller;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.transferhistory.dto.TransferHistorySearchDto;
import numble.bankingserver.domain.transferhistory.dto.request.TransferHistorySearchRequest;
import numble.bankingserver.domain.transferhistory.service.AllHistorySearchService;
import numble.bankingserver.domain.transferhistory.service.DepositHistorySearchService;
import numble.bankingserver.domain.transferhistory.service.WithdrawHistorySearchService;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.global.jwt.JwtTokenCheckService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/transferhistory")
@RestController
public class TransferHistoryController {

    private final AllHistorySearchService transferHistoryAllSearchService;
    private final DepositHistorySearchService depositHistorySearchService;
    private final WithdrawHistorySearchService withdrawHistorySearchService;
    private final JwtTokenCheckService jwtTokenCheckService;

    @PostMapping("/search/all")
    public List<TransferHistorySearchDto> searchAll(HttpServletRequest httpServletRequest,
                                                      @RequestBody @Valid TransferHistorySearchRequest request) {
        User hostUser = jwtTokenCheckService.checkToken(httpServletRequest);
        return transferHistoryAllSearchService.searchAllHistory(hostUser, request);
    }

    @PostMapping("/search/deposit")
    public List<TransferHistorySearchDto> searchDeposit(HttpServletRequest httpServletRequest,
                                                    @RequestBody @Valid TransferHistorySearchRequest request) {
        User hostUser = jwtTokenCheckService.checkToken(httpServletRequest);
        return depositHistorySearchService.searchDepositHistory(hostUser, request);
    }

    @PostMapping("/search/withdraw")
    public List<TransferHistorySearchDto> searchWithdraw(HttpServletRequest httpServletRequest,
                                                    @RequestBody @Valid TransferHistorySearchRequest request) {
        User hostUser = jwtTokenCheckService.checkToken(httpServletRequest);
        return withdrawHistorySearchService.searchWithdrawHistory(hostUser, request);
    }
}
