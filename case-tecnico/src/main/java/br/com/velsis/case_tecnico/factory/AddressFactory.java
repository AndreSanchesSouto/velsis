package br.com.velsis.case_tecnico.factory;

import br.com.velsis.case_tecnico.builder.AddressBuilder;
import br.com.velsis.case_tecnico.dto.request.PostAddressRequestDTO;
import br.com.velsis.case_tecnico.entity.AddressEntity;
import org.springframework.stereotype.Component;

@Component
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
