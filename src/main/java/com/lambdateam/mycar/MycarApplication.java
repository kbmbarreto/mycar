package com.lambdateam.mycar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EntityScan(basePackages = {"com.lambdateam.mycar.model"})
@ComponentScan(basePackages = {"com.lambdateam.mycar.*"})
@EnableJpaRepositories(basePackages = {"com.lambdateam.mycar.repository"})
@EnableTransactionManagement
@RestController
public class MycarApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(MycarApplication.class, args);
        System.out.println(new BCryptPasswordEncoder().encode("Teste@123"));

	}

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC -3:00"));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*").allowedOrigins("*");
    }
}