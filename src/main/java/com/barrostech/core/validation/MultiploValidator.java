package com.barrostech.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class MultiploValidator implements ConstraintValidator <Multiplo, Number> {

    private int numeroValidado;

    @Override
    public void initialize(Multiplo constraintAnnotation) {
        numeroValidado = constraintAnnotation.numero();
    }

    @Override
    public boolean isValid(Number number, ConstraintValidatorContext constraintValidatorContext) {
        boolean valido = true;

        if(number != null){
            var valorDecimal = BigDecimal.valueOf(number.doubleValue());
            var multiploDecimal = BigDecimal.valueOf(numeroValidado);
            var resto = valorDecimal.remainder(multiploDecimal);

            valido = BigDecimal.ZERO.compareTo(resto) == 0;
        }

        return valido;
    }
}
