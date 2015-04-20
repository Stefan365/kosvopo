package sk.stefan.MVP.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;
import java.util.Date;
import sk.stefan.MVP.view.components.NavigationComponent;

public class ZBD_ExternalLinkView extends VerticalLayout implements View {
    
    private static final long serialVersionUID = 1L;

    //private Navigator navigator;
    public ZBD_ExternalLinkView(Navigator nav) {

        //navigator = nav;
        this.setMargin(true);
        // A.MENUBAR
        MenuBar menubar = new MenuBar();
        menubar.setWidth(25.0f, Sizeable.Unit.PERCENTAGE);
        menubar.setHeight(150.0f, Sizeable.Unit.PERCENTAGE);

        // menubar.setWidth("450px");
        // menubar.setHeight("50px");
        // A feedback component
        final Label selection = new Label("-");
        this.addComponent(selection);

        // Define a common menu command for all the menu items.
        MenuBar.Command mycommand = new MenuBar.Command() {
            @Override
            public void menuSelected(MenuItem selectedItem) {
                selection.setValue("" + selectedItem.getText());
            }
        };

        // Hlavna zalozka A.
        MenuBar.MenuItem pridaj = menubar.addItem("PRIDAJ", null, null);

        // Podzalozky:
        MenuBar.MenuItem pridajNVC = pridaj.addItem(
                "Noveho Verejneho Cinitela", null, mycommand);
        MenuBar.MenuItem pridajVO = pridaj.addItem("Verejny Organ", null,
                mycommand);
        MenuBar.MenuItem pridajL = pridaj.addItem("Lokaciu", null, mycommand);
        MenuBar.MenuItem pridajVH = pridaj.addItem("Vysledok Hlasovania", null,
                mycommand);
        MenuBar.MenuItem pridajPR = pridaj.addItem("Public Role", null,
                mycommand);
        MenuBar.MenuItem pridajKVC = pridaj.addItem(
                "Klasifikacia Verejneho Cinitela", null, mycommand);
        MenuBar.MenuItem pridajDOC = pridaj
                .addItem("Dokument", null, mycommand);

        // Hlavna zalozka B.
        MenuBar.MenuItem uprav = menubar.addItem("UPRAV", null, null);
        MenuBar.MenuItem upravVC = uprav.addItem("Verejneho Cinitela", null,
                mycommand);
        MenuBar.MenuItem upravVO = uprav.addItem("Verejny Organ", null,
                mycommand);
        MenuBar.MenuItem upravL = uprav.addItem("Lokaciu", null, mycommand);
        MenuBar.MenuItem upravK = uprav
                .addItem("Klasifikaciu", null, mycommand);
        MenuBar.MenuItem upravVH = uprav.addItem("Vysledok Hlasovania", null,
                mycommand);
        MenuBar.MenuItem upravPR = uprav
                .addItem("Public Role", null, mycommand);

        /*
         * MenuBar.MenuItem snacks = menubar.addItem("Snacks", null, null);
         * snacks.addItem("Weisswurst", null, mycommand); MenuBar.MenuItem
         * salami = snacks.addItem("Salami", null, null);
         * salami.addItem("VELMO DOBRA", null, mycommand);
         */
        // Hlavna zalozka C.
        MenuBar.MenuItem odstran = menubar.addItem("ODSTRAN", null, null);
        odstran.addItem("Verejneho Cinitela", null, mycommand);
        odstran.addItem("Verejny Organ", null, mycommand);
        odstran.addItem("Lokaciu", null, mycommand);
        odstran.addItem("Dokument", null, mycommand);

        this.addComponent(menubar);

        Link linka = new Link("CLICK ME OR PICTURE", new ExternalResource(
                "http://vaadin.com/"));
        linka.setIcon(new ThemeResource("img/psychobox.png"));
        // zabezpeci otvorenie v novej zalozke:
        linka.setTargetName("_blank");
        // zabezpeci otvorenie v novom okne:
        // linka.setTargetBorder(Link.TARGET_BORDER_NONE);
        // linka.setTargetHeight(300);
        // linka.setTargetWidth(400);

        this.addComponent(linka);

        DateField date = new DateField("MY DATE");
        date.setValue(new Date());
        date.setDateFormat("yyyy-MM-dd");
        date.setLenient(true);

        this.addComponent(date);

        this.addComponent(NavigationComponent.createNavigationComponent());

    }

    @Override
    public void enter(ViewChangeEvent event) {

    }

}
