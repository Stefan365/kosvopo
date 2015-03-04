/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.enums;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author stefan
 */
public enum DepictColNames {

    KRAJ("t_kraj"),
    LOCATION("t_location"),
    OKRES("t_okres"),
    PERSON_CLASS("t_person_classification"),
    PUBLIC_BODY("t_public_body"),
    PUBLIC_PERSON("t_public_person"),
    PUBLIC_ROLE("t_public_role"),
    SUBJECT("t_subject"),
    TENURE("t_tenure"),
    THEME("t_theme"),
    VOTE("t_vote"),
    VOTE_CLASS("t_vote_classification"),
    VOTE_OF_ROLE("t_vote_of_role"),
    A_ROLE("a_role"),
    A_USER("a_user"),
    A_USER_ROLE("a_user_role");

    private final String name;

    DepictColNames(String nm) {
        this.name = nm;
    }

    public static Properties getNonEditableParams(String tabName) throws FileNotFoundException, IOException {
        String fileN;
        fileN = tabName + ".properties";
        
        Properties prop = new Properties();
        
        InputStream input = new FileInputStream(fileN);
        prop.load(input);
        return prop;

//        switch (this) {
//            case KRAJ:
//                input = new FileInputStream("t_kraj.properties");
//                prop.load(input);
//                return prop;
//            case LOCATION:
//                input = new FileInputStream("t_kraj.properties");
//                prop.load(input);
//                return prop;
//            case OKRES:
//                input = new FileInputStream("t_kraj.properties");
//                prop.load(input);
//                return prop;
//            case PERSON_CLASS:
//                input = new FileInputStream("t_kraj.properties");
//                prop.load(input);
//                return prop;
//            case PUBLIC_BODY:
//                input = new FileInputStream("t_kraj.properties");
//                prop.load(input);
//                return prop;
//            case PUBLIC_PERSON:
//                input = new FileInputStream("t_kraj.properties");
//                prop.load(input);
//                return prop;
//            case PUBLIC_ROLE:
//                input = new FileInputStream("t_kraj.properties");
//                prop.load(input);
//                return prop;
//            case SUBJECT:
//                input = new FileInputStream("t_kraj.properties");
//                prop.load(input);
//                return prop;
//            case TENURE:
//                input = new FileInputStream("t_kraj.properties");
//                prop.load(input);
//                return prop;
//            case THEME:
//                input = new FileInputStream("t_kraj.properties");
//                prop.load(input);
//                return prop;
//            case VOTE:
//                input = new FileInputStream("t_kraj.properties");
//                prop.load(input);
//                return prop;
//            case VOTE_CLASS:
//                input = new FileInputStream("t_kraj.properties");
//                prop.load(input);
//                return prop;
//            case VOTE_OF_ROLE:
//                input = new FileInputStream("t_kraj.properties");
//                prop.load(input);
//                return prop;
//            case A_USER:
//                input = new FileInputStream("t_kraj.properties");
//                prop.load(input);
//                return prop;
//            case A_ROLE:
//                input = new FileInputStream("t_kraj.properties");
//                prop.load(input);
//                return prop;
//            case A_USER_ROLE:
//                input = new FileInputStream("t_kraj.properties");
//                prop.load(input);
//                return prop;
//            default:
//                return null;
//        }
    }
}
