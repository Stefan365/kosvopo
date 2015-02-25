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

    KRAJ("T_kraj"),
    LOCATION("T_location"),
    OKRES("T_okres"),
    PERSON_CLASS("T_person_classification"),
    PUBLIC_BODY("T_public_body"),
    PUBLIC_PERSON("T_public_person"),
    PUBLIC_ROLE("T_public_role"),
    A_ROLE("a_role"),
    SUBJECT("T_subject"),
    TENURE("T_tenure"),
    THEME("T_theme"),
    A_USER("a_user"),
    A_USER_ROLE("a_user_role"),
    VOTE("T_vote"),
    VOTE_CLASS("T_vote_classification"),
    VOTE_OF_ROLE("T_vote_of_role");

    private String name;

    NonEditableFields(String name) {
        this.name = name;
    }

    public String[] getNonEditableParams() {
        switch (this) {
            case KRAJ:
                return new String[]{};
            case LOCATION:
                return new String[]{"visible"};
            case OKRES:
                return new String[]{"visible"};
            case PERSON_CLASS:
                return new String[]{"visible"};
            case PUBLIC_BODY:
                return new String[]{"visible"};
            case PUBLIC_PERSON:
                return new String[]{"visible"};
            case PUBLIC_ROLE:
                return new String[]{"visible"};
            case SUBJECT:
                return new String[]{"visible"};
            case TENURE:
                return new String[]{"visible"};
            case THEME:
                return new String[]{"visible"};
            case VOTE:
                return new String[]{"visible", "link"};
            case VOTE_CLASS:
                return new String[]{"visible"};
            case VOTE_OF_ROLE:
                return new String[]{"visible"};
            case A_USER:
                return new String[]{"password"};
            case A_ROLE:
                return new String[]{};
            case A_USER_ROLE:
                return new String[]{"since", "till"};
            default:
                return null;
        }
    }

}
