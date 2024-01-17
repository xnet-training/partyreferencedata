package com.crossnetcorp.banking.partyreferencedata.presentation;

import com.crossnetcorp.banking.partyreferencedata.application.ApplicationException;

import java.util.Date;

public class ValidationUtils {
    static public void menorEdad(Date date) throws ApplicationException {
        Date fechaActual = new Date();
        long diferenciaEnMilisegundos = fechaActual.getTime() - date.getTime();
        long diferenciaEnAnios = (long)(diferenciaEnMilisegundos / (1000 * 60 * 60 * 24 * 365.25));
        if (diferenciaEnAnios < 18) {
            throw new ApplicationException(1000, "Menor de edad");
        };
    }
}
