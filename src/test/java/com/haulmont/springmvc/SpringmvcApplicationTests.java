package com.haulmont.springmvc;

import com.haulmont.springmvc.rest.CountryDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringmvcApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void greetingShouldReturnDefaultMessage() throws Exception {
		String out = this.restTemplate.getForObject("http://localhost:" + port + "/test", String.class);
		System.out.println(out);
		assertThat(out).contains("test connection");

		CountryDto countryDto = this.restTemplate.getForObject("http://localhost:" + port + "/country/1", CountryDto.class);
		System.out.println(CountryDto.toDomainObject(countryDto));

		CountryDto countryDtoRussian = this.restTemplate.getForObject("http://localhost:" + port + "/country?name=Russian", CountryDto.class);
		System.out.println(CountryDto.toDomainObject(countryDtoRussian));
		assertThat(CountryDto.toDomainObject(countryDtoRussian))
				.isEqualTo(CountryDto.toDomainObject(new CountryDto(1L, "Russian", "RU")));

		this.restTemplate.delete("http://localhost:" + port + "/country/" + countryDtoRussian.getId());
		out = this.restTemplate.getForObject("http://localhost:" + port + "/country?name=Russian", String.class);
		assertThat(out).contains("Country not found");
		System.out.println(out);
	}
}
