package com.crossnetcorp.banking.partyreferencedata.application.mappers;

import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;

import com.crossnetcorp.banking.partyreferencedata.application.model.PartyReferenceDataDTO;
import com.crossnetcorp.banking.partyreferencedata.domain.model.PartyReferenceData;

/**
 *
 * @author ianache
 */
@Mapper(componentModel = "spring")
public interface ApplicationPartyReferenceDataMapper {

    PartyReferenceDataDTO fromDomain(PartyReferenceData domainObject);
    
    default PartyReferenceData fromDTO(PartyReferenceDataDTO dto) {;
        return PartyReferenceData.builder()
                .id(dto.getId())
            .build();        
    }

    default List<PartyReferenceDataDTO> fromDomain(List<PartyReferenceData> items) {
        return items.stream().map(nv -> fromDomain(nv))
                .collect(Collectors.toList());
    }
}
