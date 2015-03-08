/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.filters;

import com.vaadin.data.Container;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

/**
 * Do aplikuje na zadany sql container dany filter.
 *
 * @author stefan
 */
public class SqlContainerFilter {

    private static SQLContainer sqlContainer;
    private final Container.Filter visibleFilter = new Compare.Equal("visible", Boolean.TRUE);
    private Container.Filter pubBodyFilter;

    public SqlContainerFilter(SQLContainer sqlC) {
        sqlContainer = sqlC;
    }

    public static void addFilter(String fn, String value) {
        getSqlContainer().addContainerFilter(new Like(fn, value));
    }

//    public void addBasicFilter() {
//        sqlContainer.removeContainerFilter(filterBasic);
//        filterBasic = new And(new Like("id_lur", "" + user.getId()),
//                new Compare.Equal("deleted", Boolean.FALSE));
//        sqlContainer.addContainerFilter(filterBasic);
//        sqlContainer.refresh();
//    }
    // 5. stefan
    /**
     * Inicializuje Visible filter na SQLcontaier, vysledkom je, ze zobrazi iba
     * ukoly uzivatela, ktory je prihlaseny. Pripadne ich historia.
     *
     */
    public void addVisibleFilter() {

        sqlContainer.addContainerFilter(visibleFilter);
        sqlContainer.refresh();
    }

    // 6. stefan
    /**
     * Odstrani filter historie, tj. ukazu sa aj ukoncene ukoly.
     *
     */
    public void removeVisibleFilter() {
        sqlContainer.removeContainerFilter(visibleFilter);
        sqlContainer.refresh();
    }

    /**
     * @return the sqlContainer
     */
    public static SQLContainer getSqlContainer() {
        return sqlContainer;
    }

    /**
     * @param aSqlContainer the sqlContainer to set
     */
    public static void setSqlContainer(SQLContainer aSqlContainer) {
        sqlContainer = aSqlContainer;
    }

}
