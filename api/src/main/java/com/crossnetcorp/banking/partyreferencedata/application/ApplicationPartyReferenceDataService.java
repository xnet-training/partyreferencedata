package com.crossnetcorp.banking.partyreferencedata.application;


import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Application;

import com.crossnetcorp.banking.partyreferencedata.application.model.PartyReferenceDataDTO;
import org.springframework.stereotype.Service;

/**
 *
 * @author ianache
 */
@Service
public interface ApplicationPartyReferenceDataService {
        
        List<PartyReferenceDataDTO> listPartyReferenceData() throws ApplicationException;

        PartyReferenceDataDTO getPartyReferenceData(String id) throws ApplicationException;

        PartyReferenceDataDTO registerPartyReferenceData(PartyReferenceDataDTO dto)  throws ApplicationException;
}
