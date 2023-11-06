package ma.nemo.assignment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication

public class NiceStoreApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(NiceStoreApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
	}
}
