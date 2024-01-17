package com.crossnetcorp.banking.partyreferencedata.application.internal;

import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.hibernate.id.GUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crossnetcorp.banking.partyreferencedata.application.ApplicationPartyReferenceDataService;
import com.crossnetcorp.banking.partyreferencedata.application.ApplicationException;
import com.crossnetcorp.banking.partyreferencedata.application.mappers.ApplicationPartyReferenceDataMapper;
import com.crossnetcorp.banking.partyreferencedata.application.model.PartyReferenceDataDTO;
import com.crossnetcorp.banking.partyreferencedata.domain.DomainPartyReferenceDataService;
import com.crossnetcorp.banking.partyreferencedata.domain.model.PartyReferenceData;


/**
 *
 * @author ianache
 */
@Slf4j
@Service
@Transactional
public class ApplicationPartyReferenceDataServiceImpl implements ApplicationPartyReferenceDataService {

    @Autowired
    private DomainPartyReferenceDataService service;

    @Autowired
    private ApplicationPartyReferenceDataMapper mapper;

    @Override
    public PartyReferenceDataDTO registerPartyReferenceData(PartyReferenceDataDTO dto) throws ApplicationException {
        try {
            PartyReferenceData result = service.registerPartyReferenceData(
                PartyReferenceData.builder()
                            .id(null)
                            .build()
            );
            return mapper.fromDomain(result);
        } catch(Exception e) {
            log.error("OCURRIO UN ERROR EN REQUEST REGISTRO: {}", e.getMessage());
            throw new ApplicationException(1001, e.getMessage());
        }
    }

    @Override
    public List<PartyReferenceDataDTO> listPartyReferenceData() throws ApplicationException {
        log.debug("INICIO REQUEST LISTAR");
        return mapper.fromDomain(service.listPartyReferenceData());
    }

    @Override
    public PartyReferenceDataDTO getPartyReferenceData(String id) throws ApplicationException {
        return null;
    }


}
