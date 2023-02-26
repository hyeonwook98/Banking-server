package numble.bankingserver.global.config;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.accountfactory.entity.AccountFactory;
import numble.bankingserver.domain.accountfactory.repository.AccountFactoryRepository;
import numble.bankingserver.global.enums.AccountType;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class BasicSettingConfig {

    private final AccountFactoryRepository accountFactoryRepository;

    @PostConstruct
    public void setting() {
        accountFactoryRepository.save(AccountFactory.builder()
                .accountType(AccountType.SAVINGS_ACCOUNT).build());
    }
}
