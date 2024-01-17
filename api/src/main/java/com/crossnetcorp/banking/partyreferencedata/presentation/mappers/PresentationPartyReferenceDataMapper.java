package com.crossnetcorp.banking.partyreferencedata.presentation.mappers;

import com.crossnetcorp.banking.partyreferencedata.application.model.PartyReferenceDataDTO;

import java.util.List;
import java.util.stream.Collectors;

import com.crossnetcorp.banking.partyreferencedata.presentation.views.NewPartyReferenceDataRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 *
 * @author ianache
 */
@Mapper(componentModel = "spring")
public interface PresentationPartyReferenceDataMapper {
    /*@Mappings({
            @Mapping(target = "", source = "")
    })*/
    PartyReferenceDataDTO toDTO(NewPartyReferenceDataRequest request);

    // PartyReferenceDataDTO toDTO(PrestamoConsumoPignoraticioRequest obj);

/*  @Mappings({
        @Mapping(target = "id", source = "id")
    })
    ResourceId toNewpartyreferencedataView(partyreferencedataDTO partyreferencedata);*/

    //ListpartyreferencedataView toListpartyreferencedataView(partyreferencedataDTO partyreferencedatas);

    //default List<ListpartyreferencedataView> toListpartyreferencedatasView(List<partyreferencedataDTO> partyreferencedata) {
    //    return partyreferencedata.stream().map(nv -> toListpartyreferencedataView(nv))
    //            .collect(Collectors.toList());
   // }
}
