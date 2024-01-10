package com.crossnetcorp.banking.partyreferencedata.infrastructure.model.mysql;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ianache
 */
@Entity
@Table(name = "tb_party")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartyTable implements Serializable {

    @Id
    // @GeneratedValue(strategy =  GenerationType.UUID)
    @Column(name = "id")
    private String id;

/*    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "estado")
    private Integer estado;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha", updatable = false)
    private Date fecha;*/
}
