package br.com.velsis.case_tecnico.builder;

import br.com.velsis.case_tecnico.entity.AddressEntity;

public class AddressBuilder {

    private String street;
    private Integer number;
    private String city;
    private String uf;
    private String zipcode;

    public AddressBuilder street(String street) {
        this.street = street;
        return this;
    }

    public AddressBuilder number(Integer number) {
        this.number = number;
        return this;
    }

    public AddressBuilder city(String city) {
        this.city = city;
        return this;
    }

    public AddressBuilder uf(String uf) {
        this.uf = uf;
        return this;
    }

    public AddressBuilder zipcode(String zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public AddressEntity build() {
        AddressEntity address = new AddressEntity();

        address.setStreet(street);
        address.setNumber(number);
        address.setCity(city);
        address.setUf(uf);
        address.setZipcode(zipcode);

        return address;
    }
}