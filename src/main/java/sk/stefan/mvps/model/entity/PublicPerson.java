package sk.stefan.mvps.model.entity;


import java.sql.Date;
import lombok.Data;
import sk.stefan.interfaces.PresentationName;
import sk.stefan.interfaces.TabEntity;

/**
 * Trieda reprezentuje verejne cinnu fyzicku osobu (napr. Vaclav Havel, etc...).
 */
@Data
public class PublicPerson implements TabEntity {

    public static final String TN = "t_public_person";
    
    public static final String PRES_NAME = "Verejná osoba";

    private Integer id;

    private String first_name;

    private String last_name;

    private Date date_of_birth;

    private byte[] image;

    private Boolean visible;


    @Override
    public String getEntityName() {
        return "person";
    }

    @Override
    public String getRelatedTabName() {
        return "verejnaOsoba";
    }

    public static String getTN() {
        return TN;
    }

    @Override
    public String getPresentationName() {
        return (this.getFirst_name() + " " + this.getLast_name());
    }

}
