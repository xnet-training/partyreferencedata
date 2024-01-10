package com.crossnetcorp.banking.partyreferencedata.presentation.internal;

import com.crossnetcorp.banking.partyreferencedata.application.ApplicationPartyReferenceDataService;
import com.crossnetcorp.banking.partyreferencedata.application.ApplicationException;
import com.crossnetcorp.banking.partyreferencedata.application.model.PartyReferenceDataDTO;
import com.crossnetcorp.banking.partyreferencedata.presentation.mappers.*;
import com.crossnetcorp.banking.partyreferencedata.presentation.PartyApiDelegate;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


/**
 *
 * @author ianache
 */
@Slf4j
@Component
public class PartyReferenceDataAPIDelegateImpl implements PartyApiDelegate {

    @Autowired
    private ApplicationPartyReferenceDataService service;

    @Autowired
    private PresentationPartyReferenceDataMapper mapper;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return PartyApiDelegate.super.getRequest();
    }

    /*
    @Override
    public ResponseEntity<SolicitudPrestamoPigniraticioResponse> obtenerSolicitudPrestamoPignoraticio(String loanId,
            String id) {
        log.info("Consulta de Solicitud ");
        SolicitudPrestamoPigniraticioResponse response = new SolicitudPrestamoPigniraticioResponse();
        response.setId("SOL91");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResourceId> registrarPrestamoPignoraticio(
            PrestamoConsumoPignoraticioRequest prestamoConsumoPignoraticioRequest) {
        try {
            partyreferencedataDTO dto = 
                    applicationpartyreferencedataService.registrarpartyreferencedata(
                            prestamoConsumoPignoraticioRequest.getClienteId(),
                            prestamoConsumoPignoraticioRequest.getProductoId(),
                            prestamoConsumoPignoraticioRequest.getSolicitudId(),
                            prestamoConsumoPignoraticioRequest.getMonto().doubleValue(),
                            prestamoConsumoPignoraticioRequest.getCuotas().intValue());
            ResourceId result = new ResourceId();
            result.setId(dto.getId());
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (ApplicationException ex) {
            log.error("Error Interno: {}", ex.getMessage());
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }    }

    @Override
    public ResponseEntity<ResourceId> registrarSolicitudPrestamoPignoraticio(
            SolicitudPrestamoPignoraticioRequest solicitudPrestamoPignoraticioRequest) {
        // TODO Auto-generated method stub
        return partyreferencedatafacilitysApiDelegate.super.registrarSolicitudPrestamoPignoraticio(
                solicitudPrestamoPignoraticioRequest);
    }
*/
}
