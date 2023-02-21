package numble.bankingserver;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.accountfactory.entity.AccountFactory;
import numble.bankingserver.domain.accountfactory.repository.AccountFactoryRepository;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.domain.user.repository.UserRepository;
import numble.bankingserver.global.enums.AccountType;
import numble.bankingserver.global.enums.Gender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
@SpringBootApplication
public class BankingServerApplication {

	private final UserRepository userRepository;
	private final AccountFactoryRepository accountFactoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(BankingServerApplication.class, args);
	}

	@Bean
	public void setting() {
		accountFactoryRepository.save(AccountFactory.builder()
				.accountType(AccountType.SAVINGS_ACCOUNT).build());

		userRepository.save(User.builder()
				.id("gusdnr")
				.password("1234")
				.name("김현욱")
				.phoneNumber("010-2988-9330")
				.email("gusdnr@naver.com")
				.address("경상남도 통영시 도천동")
				.gender(Gender.MAN)
				.birthYear("1988-11-17")
				.build());
		userRepository.save(User.builder()
				.id("wpdhks")
				.password("1234")
				.name("김제완")
				.phoneNumber("010-3274-9330")
				.email("wpdhks@naver.com")
				.address("경상남도 통영시 도천동")
				.gender(Gender.MAN)
				.birthYear("1988-11-17")
				.build());
		userRepository.save(User.builder()
				.id("guscjf")
				.password("1234")
				.name("김현철")
				.phoneNumber("010-9713-9310")
				.email("guscjf@naver.com")
				.address("경상남도 통영시 도천동")
				.gender(Gender.MAN)
				.birthYear("1988-11-17")
				.build());
	}
}
