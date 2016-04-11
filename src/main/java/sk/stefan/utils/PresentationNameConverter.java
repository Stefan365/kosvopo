package sk.stefan.utils;

import com.vaadin.data.util.converter.Converter;
import sk.stefan.interfaces.PresentationName;
import sk.stefan.mvps.model.repo.UniRepo;

import java.util.Locale;

/**
 * Konvertor pro použití v Gridu, kdy je v buňce tabulky potřeba zobrazit stringovou reprezentaci entity.
 * @author  by elopin on 13.11.2015.
 */
public class PresentationNameConverter<T extends PresentationName> implements Converter<String, Integer> {

    private UniRepo<T> uniRepo;

    public PresentationNameConverter(Class clazz) {
        uniRepo = new UniRepo<>(clazz);
    }

    @Override
    public Integer convertToModel(String value, Class<? extends Integer> targetType, Locale locale) throws ConversionException {
        return null;
    }

    @Override
    public String convertToPresentation(Integer value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        return uniRepo.findOne(value).getPresentationName();
    }

    @Override
    public Class<Integer> getModelType() {
        return Integer.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
