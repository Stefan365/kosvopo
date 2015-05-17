package sk.stefan.converters;
import com.vaadin.data.util.converter.Converter;
import java.util.Locale;
import sk.stefan.MVP.model.service.SecurityService;
import sk.stefan.MVP.model.serviceImpl.SecurityServiceImpl;
/**
 * Konvertor mezi raw password - as it is type in form to byte[] / as it is stored in entitites.
 * 
 * @author Stefan
 */
public class PasswordConverter implements Converter<String, byte[]> {

    private static final long serialVersionUID = 14324L;
    
    private SecurityService securityService;
    
    public PasswordConverter(){
        
        this.securityService = new SecurityServiceImpl();
    
    }
            
            
            
    @Override
    public byte[] convertToModel(String value,
            Class<? extends byte[]> targetType, Locale locale)
            throws Converter.ConversionException {
        
        byte[] pwd = securityService.encryptPassword(value);
        return pwd;
        
    }

    @Override
    public String convertToPresentation(byte[] value,
            Class<? extends String> targetType, Locale locale)
            throws Converter.ConversionException {
//        return value;
        return "Karolko";
    }

    @Override
    public Class<byte[]> getModelType() {
        return byte[].class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }

    
}