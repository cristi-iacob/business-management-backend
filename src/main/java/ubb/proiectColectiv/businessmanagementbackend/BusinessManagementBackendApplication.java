package ubb.proiectColectiv.businessmanagementbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ubb.proiectColectiv.businessmanagementbackend.controller.Controller;

import java.io.IOException;

@SpringBootApplication
public class BusinessManagementBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusinessManagementBackendApplication.class, args);

		Controller ctrl = new Controller();

		try {
			ctrl.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
