package Moon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import Moon.MoonApplication;
import Moon.adapters.inputs.LoginInput;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@SpringBootApplication
public class MoonApplication implements CommandLineRunner {
	
	@Autowired
	private LoginInput loginInput;

	public static void main(String[] args) {
		SpringApplication.run(MoonApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		loginInput.menu();
	}

}
