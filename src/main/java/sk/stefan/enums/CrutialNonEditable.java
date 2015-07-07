/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.enums;

/**
 * Toto budú policka, ktore nebude mozne menit po vytvoreni entity.
 *
 * Pri editacii entity by mali byt menitelne len niektore polozky, a nie vsetky
 * ako pri jej vytvarani. (napr. hodnotenie hlasovania. pri vytvarani musi byt
 * na vyber aj samotne hlasovanie, pri editacii by uz volitene byt nemalo. Mali
 * by byt viditelne - ale needitovatelne.)
 *
 * @author stefan
 */
public enum CrutialNonEditable {

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

    CrutialNonEditable(String name) {
        this.name = name;
    }

    public String[] getCrutialParams() {
        switch (this) {
            case T_REGION:
                return new String[]{};

            case T_DISTRICT:
                return new String[]{"region_id"};
                
            case T_LOCATION:
                return new String[]{"district_id"};

            case T_PERSON_CLASSIFICATION:
                return new String[]{"classification_date", "public_person_id", "actual"};

            case T_PUBLIC_BODY:
                return new String[]{"location_id"};

            case T_PUBLIC_PERSON:
                return new String[]{};

            case T_PUBLIC_ROLE:
                return new String[]{"public_body_id", "tenure_id", "public_person_id", "name"};

            case T_SUBJECT:
                return new String[]{"public_role_id", "theme_id"};

            case T_TENURE:
                return new String[]{"since", "till"};

            case T_THEME:
                return new String[]{};

            case T_VOTE:
//                return new String[]{"for_vote","against_vote", "refrain_vote", "absent", "visible"};
                return new String[]{"subject_id", "result_vote", "vote_date"};

            case T_VOTE_CLASSIFICATION:
                return new String[]{"vote_id", "public_usefulness"};

            case T_VOTE_OF_ROLE:
                return new String[]{"public_role_id", "vote_id", "decision"};

            case A_USER:
                return new String[]{"login"};

            case A_ROLE:
                return new String[]{"role","role_name"};

            case A_USER_ROLE:
                return new String[]{"role_id","user_id","actual", "since", "till"};

            default:
                return null;
        }
    }

}
