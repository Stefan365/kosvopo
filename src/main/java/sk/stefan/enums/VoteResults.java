package sk.stefan.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Enum představující periody opakování úkolu
 *
 * @author stefan
 */
public enum VoteResults {

    APPROVED("Schválené"), DISSALLOWED("zamietnuté");

    public final String name;

    VoteResults(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    /**
     * vrací seznam jmen daných enumů
     * @return 
     */
    public static List<String> getResultNames() {
        List<String> list = new ArrayList<>();

        for (VoteResults r : VoteResults.values()) {
            list.add(r.getName());
        }
        return list;
    }

    /**
     * vrací seznam short hodnot daných enumů
     * @return 
     */
    public static List<Integer> getOrdinals() {
        List<Integer> list = new ArrayList<>();
        for (VoteResults r : VoteResults.values()) {
            list.add(r.ordinal());
        }
        return list;
    }

}
