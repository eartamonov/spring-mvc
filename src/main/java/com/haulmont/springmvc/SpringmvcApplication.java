package com.haulmont.springmvc;

import com.haulmont.springmvc.model.Country;
import com.haulmont.springmvc.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;

@EnableJpaRepositories
@SpringBootApplication
public class SpringmvcApplication {
	@Autowired
	public CountryRepository countryRepository;


	public static void main(String[] args) {
		SpringApplication.run(SpringmvcApplication.class, args);
	}

	@PostConstruct
	public void init() {
		countryRepository.save(new Country("Russian", "RU"));
		countryRepository.save(new Country("Germany", "GER"));
	}
}
