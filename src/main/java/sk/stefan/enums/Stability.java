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
public enum Stability {
    S1("1. nespoľahlivý"), S2("2. podľa počasia"), S3("3. priemer"), S4("4. vcelku stabilný"), S5("5. stabilný");

    public final String name;

    Stability(String name) {
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

        for (Stability s : Stability.values()) {
            list.add(s.getName());
        }
        return list;
    }

    /**
     * vrací seznam short hodnot daných enumů
     * @return 
     */
    public static List<Integer> getOrdinals() {
        List<Integer> list = new ArrayList<>();
        for (Stability s : Stability.values()) {
            list.add(s.ordinal());
        }
        return list;
    }


    
}
