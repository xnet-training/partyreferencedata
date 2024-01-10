package com.crossnetcorp.banking.partyreferencedata.infrastructure.internal.mysql;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.crossnetcorp.banking.partyreferencedata.domain.DomainPartyReferenceDataRepository;
import com.crossnetcorp.banking.partyreferencedata.domain.model.PartyReferenceData;
import com.crossnetcorp.banking.partyreferencedata.infrastructure.mappers.InfrastructurePartyReferenceDataMapper;
import com.crossnetcorp.banking.partyreferencedata.infrastructure.model.mysql.PartyTable;

/**
 *
 * @author ccolome
 */
@Slf4j
@Service
@Qualifier("MYSQL")
public class DomainPartyReferenceDataRepositoryMSQLImpl implements DomainPartyReferenceDataRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private InfrastructurePartyReferenceDataMapper mapper;

    @Override
    public List<PartyReferenceData> listPartyReferenceData() {
        List<PartyReferenceData> items = new ArrayList<>();
        try {

            List<PartyTable> itemsTable = entityManager.createQuery(
                    "FROM partyreferencedataTable tb",
                    PartyTable.class
            ).getResultList();

            items = this.mapper.toDomain(itemsTable);
            log.debug("SE OBTUVO LISTA DE ROOTENTITIES");
        } catch (Exception e) {
            log.error("OCURRIO UN ERROR AL LISTAR ROOTENTITIES: {}", e.getMessage());
        }
        return items;
    }

    @Override
    public PartyReferenceData upsertPartyReferenceData(PartyReferenceData data) {

        PartyReferenceData result = new PartyReferenceData();

        try {

            PartyTable row = mapper.toTable( data );

            entityManager.persist(row);
            entityManager.flush();

            result = mapper.toDomain(row);
            log.debug("SE REGISTRO CORRECTAMENTE");
        } catch (Exception e) {
            log.error("OCURRIO UN ERROR AL REGISTRAR: {}", e.getMessage());
        }
        return result;

    }

}
