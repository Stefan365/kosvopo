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

    NonEditableFields(String name) {
        this.name = name;
    }

    public String[] getNonEditableParams() {
        switch (this) {
            case KRAJ:
                return new String[]{};
            case LOCATION:
                //return new String[]{"visible"};
                return new String[]{};
                
            case OKRES:
                //return new String[]{"visible"};
                return new String[]{};
                
            case PERSON_CLASS:
                return new String[]{};
                //return new String[]{"visible"};
            case PUBLIC_BODY:
                return new String[]{};
                //return new String[]{"visible"};
            case PUBLIC_PERSON:
                return new String[]{};
                //return new String[]{"visible"};
            case PUBLIC_ROLE:
                return new String[]{};
                //return new String[]{"visible"};
            case SUBJECT:
                return new String[]{};
                //return new String[]{"visible"};
            case TENURE:
                return new String[]{};
                //return new String[]{"visible"};
            case THEME:
                return new String[]{};
                //return new String[]{"visible"};
            case VOTE:
                return new String[]{};
                //return new String[]{"visible", "link"};
            case VOTE_CLASS:
                return new String[]{};
                //return new String[]{"visible"};
            case VOTE_OF_ROLE:
                return new String[]{};
                //return new String[]{"visible"};
            case A_USER:
                //return new String[]{"password"};
                return new String[]{};
            case A_ROLE:
                return new String[]{};
            case A_USER_ROLE:
                return new String[]{};
                //return new String[]{"since", "till"};
            default:
                return null;
        }
    }

}
