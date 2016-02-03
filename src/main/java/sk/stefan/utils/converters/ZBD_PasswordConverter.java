package sk.stefan.utils.converters;
import com.vaadin.data.util.converter.Converter;
import java.util.Locale;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.model.serviceImpl.SecurityServiceImpl;
/**
 * Konvertor mezi raw password - as it is type in form to byte[] / as it is stored in entitites.
 * potom som to vyriesil inak.
 * 
 * @author Stefan
 */
public class ZBD_PasswordConverter implements Converter<String, byte[]> {

    private static final long serialVersionUID = 14324L;
    
    private SecurityService securityService;
    
    public ZBD_PasswordConverter(){
        
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