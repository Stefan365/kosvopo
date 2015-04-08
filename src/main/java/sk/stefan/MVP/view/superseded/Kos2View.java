package sk.stefan.MVP.view.superseded;

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

public class Kos2View extends VerticalLayout implements View {

    /**
     *
     */
    private static final long serialVersionUID = -8488411174808029573L;
    private VerticalLayout layout;
    private MenuBar menubar;
    private Label selection;
    private Link linka;

    public Kos2View() {
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
        MenuBar.MenuItem vykonneOR = menubar.addItem("VYKONNE ORGANY", null, null);
        // Podzalozky:
        MenuBar.MenuItem vykonneORreg = vykonneOR.addItem("Podla regionu", null, mycommand);
        MenuBar.MenuItem vykonneORabc = vykonneOR.addItem("Podla abecedy", null, mycommand);

        // Hlavna zalozka B.
        MenuBar.MenuItem ludia = menubar.addItem("VEREJNE OSOBY", null, null);
        // Podzalozky:
        MenuBar.MenuItem ludiaReg = ludia.addItem("Podla regionu", null, mycommand);
        MenuBar.MenuItem ludiaAbc = ludia.addItem("Podla abecedy", null, mycommand);

        // Hlavna zalozka C.
        MenuBar.MenuItem hlasovania = menubar.addItem("HLASOVANIA", null, null);
        // Podzalozky:
        MenuBar.MenuItem hlasovaniaTem = hlasovania.addItem("Podla temy", null, mycommand);
        MenuBar.MenuItem hlasovaniaReg = hlasovania.addItem("Podla regionu", null, mycommand);
        MenuBar.MenuItem hlasovaniaAbc = hlasovania.addItem("Podla abecedy", null, mycommand);
        MenuBar.MenuItem hlasovaniaLud = hlasovania.addItem("Podla osob ktore hlasovali", null, mycommand);

        layout.addComponent(menubar);

        linka = new Link("Log in", new ExternalResource("http://vaadin.com/"));
        linka.setTargetName("_blank");

        layout.addComponent(linka);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        this.addComponent(NavigationComponent.getNavComp());
    }

}
