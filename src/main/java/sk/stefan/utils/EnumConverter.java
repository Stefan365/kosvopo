package sk.stefan.utils;

import com.vaadin.data.util.converter.Converter;
import sk.stefan.interfaces.PresentableEnum;

import java.util.Locale;

/**
 * Created by elopin on 13.11.2015.
 */
public class EnumConverter implements Converter<String, PresentableEnum> {
    @Override
    public PresentableEnum convertToModel(String value, Class<? extends PresentableEnum> targetType, Locale locale) throws ConversionException {
        return null;
    }

    @Override
    public String convertToPresentation(PresentableEnum value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        return value.getName();
    }

    @Override
    public Class<PresentableEnum> getModelType() {
        return PresentableEnum.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
