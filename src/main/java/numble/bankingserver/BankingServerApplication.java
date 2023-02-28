package numble.bankingserver;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class BankingServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(BankingServerApplication.class, args);
	}
}
