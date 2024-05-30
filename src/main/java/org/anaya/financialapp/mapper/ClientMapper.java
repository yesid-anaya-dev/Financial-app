package org.anaya.financialapp.mapper;

import org.anaya.financialapp.domain.model.Client;
import org.anaya.financialapp.dto.ClientRequest;
import org.anaya.financialapp.dto.ClientResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    Client toClient(ClientRequest clientRequest);

    ClientResponse toClientResponse(Client client);

    default LocalDate mapStringToLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    default String mapLocalDateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
