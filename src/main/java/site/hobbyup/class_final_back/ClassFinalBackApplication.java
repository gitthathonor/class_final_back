package site.hobbyup.class_final_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ClassFinalBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClassFinalBackApplication.class, args);
	}

}
