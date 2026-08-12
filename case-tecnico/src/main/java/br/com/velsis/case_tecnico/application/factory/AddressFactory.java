package br.com.velsis.case_tecnico.application.factory;

import br.com.velsis.case_tecnico.application.builder.AddressBuilder;
import br.com.velsis.case_tecnico.application.dto.request.PostAddressRequestDTO;
import br.com.velsis.case_tecnico.domain.entity.AddressEntity;

public class AddressFactory {

    public static AddressEntity create(PostAddressRequestDTO request) {
        return new AddressBuilder()
                .street(request.street())
                .number(request.number())
                .city(request.city())
                .uf(request.uf())
                .zipcode(request.zipcode())
                .build();
    }
}
