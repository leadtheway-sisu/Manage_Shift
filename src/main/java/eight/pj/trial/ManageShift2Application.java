package eight.pj.trial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "eight.pj.trial.repository")
public class ManageShift2Application {

	public static void main(String[] args) {
		SpringApplication.run(ManageShift2Application.class, args);
	}

}
