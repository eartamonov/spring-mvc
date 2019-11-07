package com.haulmont.springmvc.rest;

import com.haulmont.springmvc.model.Country;

public class CountryDto {

    private Long id;
    private String name;
    private String codeName;

    public CountryDto(Long id, String name, String codeName) {
        this.id = id;
        this.name = name;
        this.codeName = codeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public static Country toDomainObject(CountryDto dto) {
        return new Country(dto.getName(), dto.getCodeName());
    }

    public static CountryDto toDto(Country country) {
        return new CountryDto(country.getId(), country.getName(), country.getCodeName());
    }

}
