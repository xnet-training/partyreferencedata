package com.crossnetcorp.banking.partyreferencedata.application.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartyReferenceDataDTO implements Serializable {
    private String id;

}
