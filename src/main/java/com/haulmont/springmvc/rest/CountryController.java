package com.haulmont.springmvc.rest;

import com.haulmont.springmvc.model.Country;
import com.haulmont.springmvc.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CountryController {

    private final CountryRepository repository;

    @Autowired
    public CountryController(CountryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/test")
    public String test() {
        return "test connection";
    }

    @GetMapping("/countries")
    public List<CountryDto> getAll() {
        return repository.findAll().stream()
                .map(CountryDto::toDto)
                .collect(Collectors.toList());
    }

    @RequestMapping(
            value = "/country/{id}",
            method = RequestMethod.GET
    )
    public CountryDto get(
            @PathVariable("id") long id
    ) {
        Country country = repository.findById(id).orElseThrow(NotFoundException::new);
        return CountryDto.toDto(country);
    }

    @RequestMapping(
            value = "/country",
            method = RequestMethod.GET
    )
    public CountryDto get(
            @RequestParam("name") String name
    ) {
        Country country = repository.findByName(name).orElseThrow(NotFoundException::new);
        return CountryDto.toDto(country);
    }

    @RequestMapping(
            value = "/country/",
            method = RequestMethod.POST
    )
    public @ResponseBody
    CountryDto create(
            @RequestBody CountryDto dto
    ) {
        Country country = CountryDto.toDomainObject(dto);
        Country countryWithId = repository.save(country);
        return CountryDto.toDto(countryWithId);
    }

    @DeleteMapping("/country/{id}")
    public void delete(@PathVariable("id") long id) {
        repository.deleteById(id);
    }

    @PutMapping("/country/{id}/holder")
    public void changeName(
            @PathVariable("id") long id,
            @RequestParam("name") String name
    ) {
        Country country = repository.findById(id).orElseThrow(NotFoundException::new);
        country.setName(name);
        repository.save(country);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotEnoughFunds(NotFoundException ex) {
        return ResponseEntity.badRequest().body("Country not found");
    }
}
