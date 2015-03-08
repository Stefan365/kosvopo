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
public enum VoteAction {
    
    FOR("Za"), AGAINST("Proti"), REFAIN("Zadržal sa"), ABSENT("Neprítomný");

    public final String name;

    VoteAction(String name) {
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

        for (VoteAction va : VoteAction.values()) {
            list.add(va.getName());
        }
        return list;
    }

    /**
     * vrací seznam short hodnot daných enumů
     * @return 
     */
    public static List<Integer> getOrdinals() {
        List<Integer> list = new ArrayList<>();
        for (VoteAction va : VoteAction.values()) {
            list.add(va.ordinal());
        }
        return list;
    }

    
}
