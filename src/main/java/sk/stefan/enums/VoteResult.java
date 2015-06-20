package sk.stefan.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Enum vysledky hlasovania
 *
 * @author stefan
 */
public enum VoteResult {

    APPROVED("schválené"), DISSALLOWED("zamietnuté");

    public final String name;

    VoteResult(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    /**
     * vrací seznam jmen daných enumů
     * @return 
     */
    public static List<String> getNames() {
        List<String> list = new ArrayList<>();

        for (VoteResult r : VoteResult.values()) {
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
        for (VoteResult r : VoteResult.values()) {
            list.add(r.ordinal());
        }
        return list;
    }

}
