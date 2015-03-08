package sk.stefan.MVP.view;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.entity.dao.PublicPerson2;
import sk.stefan.MVP.model.repo.dao.UniRepo;

import com.vaadin.addon.jpacontainer.EntityContainer;
import com.vaadin.addon.jpacontainer.EntityProvider;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Link;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

import java.sql.SQLException;
import java.util.List;
import sk.stefan.MVP.model.entity.dao.A_User;
import sk.stefan.MVP.view.components.AddVoteOfRoleComponent;

/**
 * Rozhraní pro výběr poslanců (veřejných osob) k podrobnějšímu sledování.
 *
 * @author veres
 */
public class K4_PoslanciView extends VerticalLayout implements View {

    public EntityProvider<A_User> vitemprov;
    public EntityContainer<A_User> vitemcontainer;
    public EntityManagerFactory emf;
    public EntityManager em;

    private UniRepo<PublicPerson2> pupRepo;
    private UniRepo<PublicPerson> poslRepo;

    private Navigator navigator;
    private VerticalLayout layout;
    private MenuBar menubar;
    private Link linka;
    private Label selection;
    private Label nazov;
    private ComboBox poslanciCB;
    private PublicPerson2 poslanec;
    private TextField tf;
    private BeanFieldGroup<PublicPerson2> bfg;
    private Button chooseBT, addPpVoteBT;

   private AddVoteOfRoleComponent avorc;

    /**
     *
     * Konstruktor
     *
     */
    public K4_PoslanciView(Navigator nav) {

        this.navigator = nav;
        pupRepo = new UniRepo<>(PublicPerson2.class);
        poslRepo = new UniRepo<>(PublicPerson.class);

        this.initLayout();
        //this.initLayMenuBar();
        this.initLayPoslanci();
        this.initPoslanci(poslanciCB);

        this.addComponent(NavigationComponent.getNavComp());
		//avorc = new AddVoteOfRoleComponent(poslRepo.findOne(3));

		//avorc.setVisible(false);
        //this.addComponent(avorc);
        this.setMargin(true);
        this.setSpacing(true);

    }

    public void initPoslanci(ComboBox com) {
        List<PublicPerson2> poslanci = pupRepo.findAll();
        com.removeAllItems();
        if (poslanci != null) {
            for (PublicPerson2 pp : poslanci) {
                com.addItem(pp);
                com.setItemCaption(pp, pp.getPresentationName());
            }
            //Notification.show("POSLANCI INITIALIZED!");
        }
    }

    private void initLayout() {

        layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);

        this.addComponent(layout);

    }

    //1. init comboBox
    public void initLayPoslanci() {

        bfg = new BeanFieldGroup<PublicPerson2>(PublicPerson2.class);
        tf = new TextField("vybrany poslanec");
        bfg.bind(tf, "first_name");

        poslanciCB = new ComboBox("Vyber poslanca: ");

        poslanciCB.addValueChangeListener(new ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {

                poslanec = (PublicPerson2) event.getProperty().getValue();
		        //if ( UI.getCurrent().getSession().getAttribute(Schuze.class)) != null)
                //UI.getCurrent().getSession().setAttribute("poslanec", poslanec);
                UI.getCurrent().getSession().setAttribute(PublicPerson2.class, poslanec);

                if (poslanec != null) {
                    // Notification.show(okres.toString());
					/**/
                    bfg.setItemDataSource(poslanec);
                    //bfg.bind(tf, "first_name");
                    chooseBT.setEnabled(true);

                    Notification.show("ZMENA POSLANCA: "
                            + poslanec.getPresentationName());
                }
            }
        });

        poslanciCB.setImmediate(true);
        poslanciCB.setNewItemsAllowed(false);
        poslanciCB.setTextInputAllowed(true);
        poslanciCB.setNullSelectionAllowed(false);

        // choose button:
        chooseBT = new Button("vyber");
        chooseBT.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                if (poslanec != null) {

                    PublicPerson2 posl = (PublicPerson2) poslanciCB.getValue();
                    Object itemId = poslanciCB.getValue();

                    tf.setEnabled(false);

                    navigator.navigateTo("kos4");

                }
            }
        });

        this.addPpVoteBT = new Button("pridaj hlasovanie poslanca");
        addPpVoteBT.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                if (poslanec != null) {
					//Notification.show("POSLANEC: " + poslanec.getPresentationName());
                    //avorc.setPoslanec(poslanec);
                    avorc.setPoslanec(poslRepo.findOne(3));
                    avorc.setVisible(true);
                }
            }
        });

        //layout.addComponents(menubar, nazov, linka, poslanciCB, tf, choose);
        layout.addComponents(poslanciCB, tf, chooseBT, this.addPpVoteBT);

    }

    //2. init comboBox
    public void initLayMenuBar() {

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
        MenuBar.MenuItem hlasovaniaAbc = hlasovania.addItem("Podla abecedy",
                null, mycommand);
        MenuBar.MenuItem hlasovaniaCas = hlasovania.addItem("Podla casu", null,
                mycommand);

        // Hlavna zalozka B.
        MenuBar.MenuItem poslanci = menubar
                .addItem("POSLANCI", null, mycommand);

		// LABELS:azov
        // A feedback component
        nazov = new Label("NAZOV:");

        linka = new Link("KOSLLANCI",
                new ExternalResource("http://vaadin.com/"));
        linka.setTargetName("_blank");

        /*
         * emf = Persistence.createEntityManagerFactory("vendorapp"); em =
         * emf.createEntityManager(); vitemcontainer=new
         * JPAContainer<Planet>(Planet.class);
         * 
         * vitemprov = new
         * BatchableLocalEntityProvider<Planet>(Planet.class,em);
         * vitemcontainer.setEntityProvider(vitemprov); vitemcontainer.commit();
         */
        layout.addComponents(menubar, nazov, linka);

    }

    @Override
    public void enter(ViewChangeEvent event) {
        this.addComponent(NavigationComponent.getNavComp());
    }

}
