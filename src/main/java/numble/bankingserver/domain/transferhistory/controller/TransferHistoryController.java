package numble.bankingserver.domain.transferhistory.controller;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.transferhistory.dto.TransferHistorySearchDto;
import numble.bankingserver.domain.transferhistory.dto.request.TransferHistorySearchRequest;
import numble.bankingserver.domain.transferhistory.service.HistorySearchService;
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

    private final JwtTokenCheckService jwtTokenCheckService;
    private final HistorySearchService historySearchService;

    @PostMapping("/search")
    public List<TransferHistorySearchDto> searchTransferHistory(HttpServletRequest httpServletRequest,
                                                    @RequestBody @Valid TransferHistorySearchRequest request) {
        User hostUser = jwtTokenCheckService.checkToken(httpServletRequest);
        return historySearchService.searchAllHistory(hostUser, request);
    }
}
