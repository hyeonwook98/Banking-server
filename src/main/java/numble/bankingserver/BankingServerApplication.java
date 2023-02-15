package numble.bankingserver;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.accountnumber.entity.AccountFactory;
import numble.bankingserver.domain.accountnumber.repository.AccountFactoryRepository;
import numble.bankingserver.global.enums.AccountType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
@SpringBootApplication
public class BankingServerApplication {

	private final AccountFactoryRepository accountFactoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(BankingServerApplication.class, args);
	}

	@Bean
	public void setting() {
		accountFactoryRepository.save(AccountFactory.builder()
				.accountType(AccountType.SAVINGS_ACCOUNT).build());
	}

}
