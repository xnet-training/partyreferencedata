package com.crossnetcorp.banking.partyreferencedata.infrastructure.mappers;

import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;

import com.crossnetcorp.banking.partyreferencedata.domain.model.PartyReferenceData;
import com.crossnetcorp.banking.partyreferencedata.infrastructure.model.mysql.PartyTable;

/**
 *
 * @author ianache
 */
@Mapper(componentModel = "spring")
public interface InfrastructurePartyReferenceDataMapper {

    PartyTable toTable(PartyReferenceData domain);

    PartyReferenceData toDomain(PartyTable table);

    default List<PartyTable> toTable(List<PartyReferenceData> items) {
        return items.stream().map(v -> toTable(v))
                .collect(Collectors.toList());
    }

    default List<PartyReferenceData> toDomain(List<PartyTable> items) {
        return items.stream().map(v -> toDomain(v))
                .collect(Collectors.toList());
    }
    
}
