package com.barrostech.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator <ValorZeroIncluiDescricao, Object>  {

    private String valorField;
    private String descricaoField;
    private String descricaoObrigatoria;

    @Override
    public void initialize(ValorZeroIncluiDescricao constraintAnnotation) {
        valorField = constraintAnnotation.valorField();
        descricaoField = constraintAnnotation.descricaoField();
        descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
    }

    @Override
    public boolean isValid(Object objetoValidado, ConstraintValidatorContext constraintValidatorContext) {
        boolean valido = true;

        try {
            BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(objetoValidado.getClass(), valorField)
                    .getReadMethod().invoke(objetoValidado);

            String descricao = (String) BeanUtils.getPropertyDescriptor(objetoValidado.getClass(), descricaoField)
                    .getReadMethod().invoke(objetoValidado);

            if(valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null){
                valido = descricao.toLowerCase().contains(descricaoObrigatoria.toLowerCase());
            }
            return valido;
        }  catch (Exception e) {
            throw new ValidationException(e);
        }


    }
}
