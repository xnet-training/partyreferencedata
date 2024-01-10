package com.crossnetcorp.banking.partyreferencedata.domain.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ianache
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartyReferenceData implements Serializable {
    private String id;
}

