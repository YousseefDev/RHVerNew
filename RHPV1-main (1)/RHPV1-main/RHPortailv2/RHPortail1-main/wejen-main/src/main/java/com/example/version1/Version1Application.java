package com.example.version1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@OpenAPIDefinition(
//		security = @SecurityRequirement(name = "bearerAuth")
//)
//@SecurityScheme(
//		name = "bearerAuth",
//		description = "Some desc",
//		scheme = "bearer",
//		type = SecuritySchemeType.HTTP,
//		in = SecuritySchemeIn.HEADER
//)
@SpringBootApplication
public class Version1Application {

    public static void main(String[] args) {
        SpringApplication.run(Version1Application.class, args);
    }

//	@Bean
//	public CommandLineRunner commandLineRunner(
//	) {
//		return  args -> {
//			System.out.println("Container started");
//		};
//	}
}