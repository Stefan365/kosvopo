/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.enums;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author stefan
 */
public enum PublicUsefulness {

    PU1("1. škodlivé"), PU2("2. mierne škodlivé"), PU3("3. neutrálne"), PU4("4. mierne prospešné"), PU5("5. prospešné");

    public final String name;

    PublicUsefulness(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    /**
     * vrací seznam jmen daných enumů
     *
     * @return
     */
    public static List<String> getNames() {
        List<String> list = new ArrayList<>();

        for (PublicUsefulness pu : PublicUsefulness.values()) {
            list.add(pu.getName());
        }
        return list;
    }

    /**
     * vrací seznam short hodnot daných enumů
     *
     * @return
     */
    public static List<Integer> getOrdinals() {
        List<Integer> list = new ArrayList<>();
        for (PublicUsefulness pu : PublicUsefulness.values()) {
            list.add(pu.ordinal());
        }
        return list;
    }

}
