/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.listeners.todo;

/**
 * Pri zmene filtru sa moze stat, ze zasiahne i prave vybranu ulohu (tj. jej
 * zobrazenie) V tomto pripade sa vynuluje TaskLabel.
 *
 * @author Stefan
 */
public interface RefreshTaskLabelListener {

    public void refreshTaskLabel();
}
