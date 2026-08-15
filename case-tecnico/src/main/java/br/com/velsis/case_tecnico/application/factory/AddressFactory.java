package br.com.velsis.case_tecnico.application.factory;

import br.com.velsis.case_tecnico.application.builder.AddressBuilder;
import br.com.velsis.case_tecnico.application.dto.request.PatchAddressRequestDTO;
import br.com.velsis.case_tecnico.application.dto.request.PostAddressRequestDTO;
import br.com.velsis.case_tecnico.domain.entity.AddressEntity;

/**
 * Classe de fábrica (Factory) responsável por centralizar a lógica de criação e atualização
 * de instâncias da entidade de domínio AddressEntity.
 * Utiliza o padrão Builder para a construção de novos endereços a partir de DTOs de criação (POST)
 * e implementa regras de atualização parcial (Patch) que aplicam modificações apenas nos campos
 * não nulos fornecidos na requisição, preservando o estado atual dos demais dados.
 */
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

    public static AddressEntity update(AddressEntity target, PatchAddressRequestDTO request) {
        if (request.street() != null) target.setStreet(request.street());
        if (request.number() != null) target.setNumber(request.number());
        if (request.city() != null) target.setCity(request.city());
        if (request.uf() != null) target.setUf(request.uf());
        if (request.zipcode() != null) target.setZipcode(request.zipcode());
        return target;
    }
}
