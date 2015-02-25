package sk.stefan.MVP.view;

import sk.stefan.MVP.view.components.NavigationComponent;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Link;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

public class Kos3View extends VerticalLayout implements View {

    private VerticalLayout layout;
    private MenuBar menubar;
    private Link linka;
    private Label selection;

    public Kos3View() {
        this.initLayout();
        this.addComponent(NavigationComponent.getNavComp());
    }

    private void initLayout() {

        layout = new VerticalLayout();
        layout.setMargin(true);

        this.addComponent(layout);

        // A.MENUBAR
        menubar = new MenuBar();
        menubar.setWidth(55.0f, Sizeable.Unit.PERCENTAGE);
        menubar.setHeight(150.0f, Sizeable.Unit.PERCENTAGE);

        // A feedback component
        selection = new Label("-");
        layout.addComponent(selection);

        // Define a common menu command for all the menu items.
        MenuBar.Command mycommand = new MenuBar.Command() {
            @Override
            public void menuSelected(MenuItem selectedItem) {
                selection.setValue("" + selectedItem.getText());
            }
        };

        // Hlavna zalozka A.
        MenuBar.MenuItem hlasovania = menubar.addItem("HLASOVANIA", null, null);
        // Podzalozky:
        MenuBar.MenuItem hlasovaniaAbc = hlasovania.addItem("Podla abecedy", null, mycommand);
        MenuBar.MenuItem hlasovaniaCas = hlasovania.addItem("Podla casu", null, mycommand);

        // Hlavna zalozka B.
        MenuBar.MenuItem hodnotenia = menubar.addItem("HODNOTENIA", null, null);
        // Podzalozky:
        MenuBar.MenuItem hodnoteniaCas = hodnotenia.addItem("Podla casu", null, mycommand);

        // Hlavna zalozka C.
        MenuBar.MenuItem verOrg = menubar.addItem("VEREJNE ORGANY", null, null);
        // Podzalozky:
        MenuBar.MenuItem verOrgCas = verOrg.addItem("Podla casu", null, mycommand);

        // Hlavna zalozka D.
        MenuBar.MenuItem strana = menubar.addItem("STRANY", null, null);
        // Podzalozky:
        MenuBar.MenuItem stranaCas = strana.addItem("Podla casu", null, mycommand);

        layout.addComponent(menubar);

        //LABELS:
        // A feedback component
        Label meno = new Label("MENO:");
        Label priezvisko = new Label("PRIEZVISKO:");
        Label datumNar = new Label("DATUM_NARODENIA:");
        Label verejnyOrg = new Label("VEREJNY_ORGAN:");

        layout.addComponents(meno, priezvisko, datumNar, verejnyOrg);

        linka = new Link("Log in", new ExternalResource("http://vaadin.com/"));
        linka.setTargetName("_blank");

        layout.addComponent(linka);

    }

    @Override
    public void enter(ViewChangeEvent event) {
        this.addComponent(NavigationComponent.getNavComp());
    }

}
