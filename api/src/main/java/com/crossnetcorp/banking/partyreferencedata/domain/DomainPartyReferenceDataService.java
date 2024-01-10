package com.crossnetcorp.banking.partyreferencedata.domain;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.crossnetcorp.banking.partyreferencedata.domain.model.PartyReferenceData;

/**
 *
 * @author ianache
 */
@Repository
public interface DomainPartyReferenceDataService {

    List<PartyReferenceData> listPartyReferenceData();

    PartyReferenceData registerPartyReferenceData(PartyReferenceData domainObject);

}
