package numble.bankingserver.domain.transferhistory.repository;

import numble.bankingserver.domain.transferhistory.dto.request.TransferHistorySearchRequest;
import numble.bankingserver.domain.transferhistory.entity.TransferHistory;

import java.util.List;

public interface TransferHistoryCustomRepository {

    List<TransferHistory> findTransferHistories(TransferHistorySearchRequest request);

}
