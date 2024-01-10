package com.crossnetcorp.banking.partyreferencedata.domain;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.crossnetcorp.banking.partyreferencedata.domain.model.PartyReferenceData;

/**
 *
 * @author ianache
 */
@Repository
public interface DomainPartyReferenceDataRepository {

    List<PartyReferenceData> listPartyReferenceData();

    PartyReferenceData upsertPartyReferenceData(PartyReferenceData domainObject);
}
