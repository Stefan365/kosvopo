package sk.stefan.MVP.view.converters;

import com.vaadin.data.util.converter.StringToBooleanConverter;


//final Property<Integer> property = new ObjectProperty<Integer>(13);
//
//final TextField textField = new TextField("TextField", property);
//textField.setConverter(new StringToIntegerConverter());
//
//...

public class MyBooleanConverter extends StringToBooleanConverter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8934270295554192791L;

	public static Boolean convertToModel(final String value)
			throws ConversionException {
		try {
			return Boolean.parseBoolean(value);
		} catch (final NumberFormatException e) {
			throw new ConversionException("Must be in binary format");
		}
	}

	public static String convertToPresentation(final Boolean ent)
			throws ConversionException {
		return ent.toString();
	}
}