package numble.bankingserver.domain.accountfactory.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.bankingserver.global.entity.BaseTimeEntity;
import numble.bankingserver.global.enums.AccountType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_accountfactory")
public class AccountFactory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long number = 0L;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Builder
    public AccountFactory(AccountType accountType) {
        this.accountType = accountType;
    }

    public void addNumber() {
        this.number += 1;
    }
}
