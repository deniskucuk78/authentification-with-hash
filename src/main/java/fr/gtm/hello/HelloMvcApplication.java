package fr.gtm.hello;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloMvcApplication {

	public static void main(String[] args)  throws NoSuchAlgorithmException {
		SpringApplication.run(HelloMvcApplication.class, args);
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		String message = "Hello World";
		byte[] hash = md.digest(message.getBytes());
		System.out.println(hash);
	}

}
