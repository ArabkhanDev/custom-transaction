package az.company.customtransaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "az.company")
public class CustomTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomTransactionApplication.class, args);
	}
}

