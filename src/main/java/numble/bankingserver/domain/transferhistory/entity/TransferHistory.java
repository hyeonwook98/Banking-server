package numble.bankingserver.domain.transferhistory.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import numble.bankingserver.domain.account.entity.Account;
import numble.bankingserver.global.entity.BaseTimeEntity;
import numble.bankingserver.global.enums.TransferType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_transferhistory")
public class TransferHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_account_id")
    private Account hostAccount;

    @NonNull
    private Long friendAccountNumber;

    @NonNull
    private String friendName;

    @NonNull
    @Enumerated(EnumType.STRING)
    private TransferType transferType;

    @NonNull
    private Long amount;

    @NonNull
    private Long balance;

    @Builder
    public TransferHistory(Account hostAccount, Long friendAccountNumber, String friendName,
                           TransferType transferType, Long amount, Long balance) {
        this.hostAccount = hostAccount;
        this.friendAccountNumber = friendAccountNumber;
        this.friendName = friendName;
        this.transferType = transferType;
        this.amount = amount;
        this.balance = balance;
    }
}
