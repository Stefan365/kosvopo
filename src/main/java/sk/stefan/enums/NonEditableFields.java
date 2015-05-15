/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.enums;

/**
 *
 * @author stefan
 */
public enum NonEditableFields {

    REGION("t_region"),
    LOCATION("t_location"),
    DISTRICT("t_district"),
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

    NonEditableFields(String name) {
        this.name = name;
    }

    public String[] getNonEditableParams() {
        switch (this) {
            case REGION:
                
                return new String[]{"visible"};
////              
//                return new String[]{};
            
            case LOCATION:
                return new String[]{"visible"};
                
            case DISTRICT:
                return new String[]{"visible"};
//                
//                return new String[]{};
                
            case PERSON_CLASS:
//                return new String[]{};
                return new String[]{"visible"};
            case PUBLIC_BODY:
//                return new String[]{};
                return new String[]{"visible"};
            case PUBLIC_PERSON:
//                return new String[]{};
                return new String[]{"visible"};
            case PUBLIC_ROLE:
//                return new String[]{};
                return new String[]{"visible"};
            case SUBJECT:
//                return new String[]{};
                return new String[]{"visible"};
            case TENURE:
//                return new String[]{};
                return new String[]{"visible"};
            case THEME:
//                return new String[]{};
                return new String[]{"visible"};
            case VOTE:
//                return new String[]{};
                return new String[]{"visible"};
            case VOTE_CLASS:
//                return new String[]{};
                return new String[]{"visible"};
            case VOTE_OF_ROLE:
//                return new String[]{};
                return new String[]{"visible"};
                
            case A_USER:
                return new String[]{"visible"};
//                return new String[]{};
            case A_ROLE:
                return new String[]{"visible"};
            case A_USER_ROLE:
//                return new String[]{};
                return new String[]{"actual","since", "till","visible"};
            default:
                
                return null;
        }
    }

}
