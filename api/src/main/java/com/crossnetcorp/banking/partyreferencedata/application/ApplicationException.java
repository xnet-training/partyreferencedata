package com.crossnetcorp.banking.partyreferencedata.application;


import lombok.Data;

/**
 *
 * @author ianache
 */
@Data
public class ApplicationException extends Exception {
    private Integer code;
    public ApplicationException(Integer code, String string) {
        super(string);
        this.code = code;
    }
    
}
