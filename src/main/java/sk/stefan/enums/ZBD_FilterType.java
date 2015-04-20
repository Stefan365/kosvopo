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
public enum ZBD_FilterType {
    LOCATION("miesto"), PUB_BODY("verejný orgán"), PUB_PERSON("verejná osoba");

    public final String name;

    ZBD_FilterType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    /**
     * vrací seznam jmen daných enumů.
     * @return 
     */
    public static List<String> getNames() {
        List<String> list = new ArrayList<>();

        for (ZBD_FilterType ft : ZBD_FilterType.values()) {
            list.add(ft.getName());
        }
        return list;
    }

    /**
     * vrací seznam short hodnot daných enumů
     * @return 
     */
    public static List<Integer> getOrdinals() {
        List<Integer> list = new ArrayList<>();
        for (ZBD_FilterType ft : ZBD_FilterType.values()) {
            list.add(ft.ordinal());
        }
        return list;
    }
    
}
