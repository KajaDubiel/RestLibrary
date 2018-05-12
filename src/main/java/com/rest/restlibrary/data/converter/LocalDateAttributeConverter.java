package com.rest.restlibrary.data.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date>{
    @Override
    public Date convertToDatabaseColumn(LocalDate localDate) {
        if(localDate == null){
            return null;
        } else {
            return Date.valueOf(localDate);
        }
    }

    @Override
    public LocalDate convertToEntityAttribute(Date sqlDate) {
        if(sqlDate == null){
            return null;
        } else {
            return sqlDate.toLocalDate();
        }
    }
}
