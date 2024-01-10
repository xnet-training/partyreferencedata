package com.crossnetcorp.banking.partyreferencedata.domain.internal;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.crossnetcorp.banking.partyreferencedata.domain.DomainPartyReferenceDataRepository;
import com.crossnetcorp.banking.partyreferencedata.domain.DomainPartyReferenceDataService;
import com.crossnetcorp.banking.partyreferencedata.domain.model.PartyReferenceData;

/**
 *
 * @author ianache
 */
@Slf4j
@Service
public class DomainPartyReferenceDataServiceImpl implements DomainPartyReferenceDataService {

    @Autowired
    @Qualifier("MYSQL")
    private DomainPartyReferenceDataRepository repository;
    
    @Override
    public List<PartyReferenceData> listPartyReferenceData() {
        log.debug("INICIO PROCESAMIENTO DE LISTAR partyreferencedata");
        List<PartyReferenceData> partyreferencedatas = repository.listPartyReferenceData();
        log.debug("FIN PROCESAMIENTO DE LISTAR partyreferencedata");
        return partyreferencedatas;
    }

    @Override
    public PartyReferenceData registerPartyReferenceData(PartyReferenceData domainObject) {
        log.debug("INICIO PROCESAMIENTO DE REGISTRAR");
        log.debug(domainObject.toString());
        domainObject = repository.upsertPartyReferenceData(domainObject);
        log.debug("FIN PROCESAMIENTO DE REGISTRAR");
        return domainObject;
    }

}
