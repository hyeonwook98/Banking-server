package numble.bankingserver.domain.user.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NonNull
    private Long accountNumber;

    private Long balance;

    @Builder
    public Account(User user, @NonNull Long accountNumber, Long balance) {
        this.user = user;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    //연관관계 메서드
    public void setUser(User user) {
        this.user = user;
        user.getAccounts().add(this);
    }
}
