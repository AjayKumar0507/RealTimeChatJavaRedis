package com.kash.RealTimeChat;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RealTimeChatApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().load();
        System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
		SpringApplication.run(RealTimeChatApplication.class, args);
	}

}
