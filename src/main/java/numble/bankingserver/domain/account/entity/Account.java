package numble.bankingserver.domain.account.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import numble.bankingserver.global.enums.AccountType;
import numble.bankingserver.domain.user.entity.User;

import javax.persistence.Column;
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
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NonNull
    private Long accountNumber;

    private Long balance = 0L;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Builder
    public Account(User user, Long accountNumber, AccountType accountType) {
        this.user = user;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
    }
}
