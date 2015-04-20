//package sk.stefan.MVP.view.converters;
//
//import java.util.Locale;
//
//import com.vaadin.data.util.converter.Converter;
//
////public class BooleanConverter implements Converter<String, Boolean> {
//public class BooleanConverter<T> implements Converter<Object, T> {
//	
//	@Override
//	public Class<T> getModelType() {
//		return Boolean.class;
//	}
//
//	@Override
//	public Class<Object> getPresentationType() {
//		return Object.class;
//	}
//
//	@Override
//	public Boolean convertToModel(String value, Class<? extends Boolean> targetType, Locale locale)
//			throws com.vaadin.data.util.converter.Converter.ConversionException {
//		
//		return Boolean.parseBoolean(value);
//	}
//
//	@Override
//	public String convertToPresentation(Boolean value,
//			Class<? extends String> targetType, Locale locale)
//			throws com.vaadin.data.util.converter.Converter.ConversionException {
//		return value.toString();
//	}
//}