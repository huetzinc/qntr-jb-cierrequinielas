package com.desmxsoft.qntrjbcierrequiniela;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {	"com.desmxsoft.qntrjbcierrequiniela.controller"})
public class CierreQuinielasApplication {

	public static void main(String[] args) {
		SpringApplication.run(CierreQuinielasApplication.class, args);
	}

}
