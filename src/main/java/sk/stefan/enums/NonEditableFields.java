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

    T_REGION("t_region"),
    T_LOCATION("t_location"),
    T_DISTRICT("t_district"),
    T_PERSON_CLASSIFICATION("t_person_classification"),
    T_PUBLIC_BODY("t_public_body"),
    T_PUBLIC_PERSON("t_public_person"),
    T_PUBLIC_ROLE("t_public_role"),
    T_SUBJECT("t_subject"),
    T_TENURE("t_tenure"),
    T_THEME("t_theme"),
    T_VOTE("t_vote"),
    T_VOTE_CLASSIFICATION("t_vote_classification"),
    T_VOTE_OF_ROLE("t_vote_of_role"),
    A_ROLE("a_role"),
    A_USER("a_user"),
    A_USER_ROLE("a_user_role");

    private final String name;

    NonEditableFields(String name) {
        this.name = name;
    }

    public String[] getNonEditableParams() {
        switch (this) {
            case T_REGION:
                
                return new String[]{"visible"};
////              
//                return new String[]{};
            
            case T_LOCATION:
                return new String[]{"visible"};
                
            case T_DISTRICT:
//                return new String[]{"visible"};
//                
//                return new String[]{};
                
            case T_PERSON_CLASSIFICATION:
//                return new String[]{};
                return new String[]{"visible"};
            case T_PUBLIC_BODY:
//                return new String[]{};
                return new String[]{"visible"};
            case T_PUBLIC_PERSON:
//                return new String[]{};
                return new String[]{"visible"};
            case T_PUBLIC_ROLE:
//                return new String[]{};
                return new String[]{"visible"};
            case T_SUBJECT:
//                return new String[]{};
                return new String[]{"visible"};
            case T_TENURE:
//                return new String[]{};
                return new String[]{"visible"};
            case T_THEME:
//                return new String[]{};
                return new String[]{"visible"};
            case T_VOTE:
//                return new String[]{};
                return new String[]{"visible"};
            case T_VOTE_CLASSIFICATION:
//                return new String[]{};
                return new String[]{"visible"};
            case T_VOTE_OF_ROLE:
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
