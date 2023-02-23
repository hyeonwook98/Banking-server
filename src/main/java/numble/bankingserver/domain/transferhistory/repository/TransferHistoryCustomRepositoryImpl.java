package numble.bankingserver.domain.transferhistory.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.transferhistory.dto.request.TransferHistorySearchRequest;
import numble.bankingserver.domain.transferhistory.entity.TransferHistory;
import numble.bankingserver.global.enums.TransferType;
import org.springframework.stereotype.Repository;

import java.util.List;

import static numble.bankingserver.domain.transferhistory.entity.QTransferHistory.*;

@RequiredArgsConstructor
@Repository
public class TransferHistoryCustomRepositoryImpl implements TransferHistoryCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<TransferHistory> findTransferHistories(TransferHistorySearchRequest request) {

        return jpaQueryFactory
                .selectFrom(transferHistory)
                .where(accountNumberEq(request.getAccountNumber()),
                        transferTypeEq(request.getTransferType()))
                .orderBy(transferHistory.createdAt.desc())
                .fetch();
    }

    private BooleanExpression accountNumberEq(Long accountNumber) {
        if (accountNumber != 0L) {
            return transferHistory.hostAccountNumber.eq(accountNumber);
        }
        return null;
    }
    private BooleanExpression transferTypeEq(TransferType transferType) {
        if (transferType == TransferType.DEPOSIT || transferType == TransferType.WITHDRAW) {
            return transferHistory.transferType.eq(transferType);
        }
        return null;
    }

}
