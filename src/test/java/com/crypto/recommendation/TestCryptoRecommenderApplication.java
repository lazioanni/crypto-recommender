package com.crypto.recommendation;

import org.springframework.boot.SpringApplication;

public class TestCryptoRecommenderApplication {

	public static void main(String[] args) {
		SpringApplication.from(CryptoRecommenderApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
