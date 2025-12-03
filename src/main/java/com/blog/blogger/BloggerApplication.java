package com.blog.blogger;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BloggerApplication {

	public static void main(String[] args) {
		// SpringApplication.run(BloggerApplication.class, args);

		ConfigurableApplicationContext context = SpringApplication.run(BloggerApplication.class, args);

		PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
		String rawPassword = "Atif@123";
		String encodedPassword = passwordEncoder.encode(rawPassword);

		System.out.println("Encoded Password for DB: " + encodedPassword);



	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
