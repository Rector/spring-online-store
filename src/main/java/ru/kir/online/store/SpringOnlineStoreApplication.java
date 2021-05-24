package ru.kir.online.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:app.properties")
@EnableAspectJAutoProxy
public class SpringOnlineStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringOnlineStoreApplication.class, args);
	}

}
