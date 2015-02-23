package sk.stefan.MVP.view;

import java.util.Date;

import sk.stefan.DBconnection.DoDBconn;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.entity.dao.PublicPerson2;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.VoteOfRole;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.model.service.VoteOfRoleService;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.MVP.view.components.YesNoWindow_old;
import sk.stefan.MVP.view.helpers.PomDao;
import sk.stefan.MVP.view.helpers.PomFormLayout;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Link;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalSplitPanel;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import sk.stefan.listeners.GeneralComponentListener;

/**
 * Rozhraní pro zobrazeni udaju o konkretni verejne osobe (poslancovi)
 *
 * @author veres
 */
public class K5_Verejna_osobaView extends HorizontalSplitPanel implements View,
        GeneralComponentListener {

    /**
     *
     */
    private static final long serialVersionUID = 8961194513750742537L;

    private PublicPerson2 poslanec;
    private VoteOfRole hlasovanie;

    private VerticalSplitPanel layoutVertSp;
    private VerticalLayout layout, leftLayout;

    private PomFormLayout<PublicPerson2> fl;

    private MenuBar menubar;
    private Link linka;
    private Label selection;
    private Label nazov;

    // verejna osoba:
    private Label fn, ln, by, party, ver_organ;

    // odkaz na verejny organ:
    private Button ver_organBT;
    private ComboBox hlasovanieCB;

    // formLayout pre editaciu udajov poslanca, len pre administratora.
    private FormLayout poslanecFL;
    private HorizontalLayout poslanecHL = new HorizontalLayout();
    private HorizontalLayout hlasovaniHL = new HorizontalLayout();

    private Button removePoslanecBT;
    private Button saveBT, saveBT1;
    private Button editBT, editBT1;
    // private FieldGroup editorFields = new FieldGroup();

    private BeanFieldGroup<PublicPerson2> bfg;
    private BeanFieldGroup<PublicPerson2> bfg2; //na skousku:

    // private BeanFieldGroup<VoteOfRole> bfg1;
    private FieldGroup fg;

    private Set<String> fieldNames, fieldNames1;

    private Table hlasovaniaPoslTB = new Table();
    private Object hlasId; // aktualne vybrany objekt zo SQLcontaineru

    private FormLayout hlasovaniFL;
    private Button removeVoteOfRoleBT;

    private UniRepo<PublicPerson2> publicPersonRepo;
    private UniRepo<PublicRole> publicRoleRepo;
    private UniRepo<VoteOfRole> voteOfRoleRepo;
    private VoteOfRoleService voteOfRoleService;// = new VoteOfRoleService();

    SQLContainer sqlContainer;

	// Class<T> persistentClass = (Class<T>) ((ParameterizedType)
    // getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    /**
     * Konstruktor.
     *
     *
     */
    public K5_Verejna_osobaView(Navigator nav) {

        publicPersonRepo = new UniRepo<PublicPerson2>(PublicPerson2.class);
        publicRoleRepo = new UniRepo<PublicRole>(PublicRole.class);
        voteOfRoleRepo = new UniRepo<VoteOfRole>(VoteOfRole.class);
        voteOfRoleService = new VoteOfRoleService();

        try {
            sqlContainer = DoDBconn.getContainer("t_vote_of_role");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.initLayout();
        // this.initLayMenuBar();
        poslanecFL.addComponent(NavigationComponent.getNavComp());
        try {
            this.initPoslanecFL();
            this.initVoteOfRoleFL();
            this.initHlasovaniaPoslTB();
        } catch (NoSuchFieldException | SecurityException e) {
            Notification.show(
                    "1.Nastala pravdepodobne chyba v komunikacii s DB!",
                    Type.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }

    // 0.
    private void initHlasovaniaPoslTB() throws NoSuchFieldException,
            SecurityException {

        Set<String> nazvy = PomDao.getClassProperties(VoteOfRole.class, true);

        hlasovaniaPoslTB.setContainerDataSource(sqlContainer);
        Object[] nazvyO = nazvy.toArray(new String[nazvy.size()]);
        hlasovaniaPoslTB.setVisibleColumns(nazvyO);
        hlasovaniaPoslTB.setSelectable(true);
        hlasovaniaPoslTB.setImmediate(true);

        hlasovaniaPoslTB
                .addValueChangeListener(new Property.ValueChangeListener() {
                    @Override
                    public void valueChange(ValueChangeEvent event) {
                        hlasId = hlasovaniaPoslTB.getValue();

                        if (hlasId != null) {
                            fg.setItemDataSource(hlasovaniaPoslTB
                                    .getItem(hlasId));
                            hlasovaniFL.setVisible(true);

                            fg.setEnabled(false);
                            editBT1.setEnabled(true);
                            saveBT1.setEnabled(false);
                        }
                    }
                });

        // hlasovaniaPoslTB
        this.initFilter();

    }

    private void initFilter() {

        // dodanie filtru, aby sa nezobrazovala cela tabulka:
        if (poslanec != null) {
            // Notification.show("SOM TU!");
            List<PublicRole> lpr;
            lpr = publicRoleRepo.findByParam("public_person_id",
                    "" + poslanec.getId());
            List<Integer> pr_ids = new ArrayList<>();
            for (PublicRole pr : lpr) {
                pr_ids.add(pr.getId());
            }
            tableAddFilter(pr_ids);
        }
    }

    // 1. Inicializuje editor udajov o poslancovi:
    public void initPoslanecFL() throws NoSuchFieldException, SecurityException {

        removePoslanecBT = new Button("Odstranit");

        poslanecFL.addComponent(removePoslanecBT);
        removePoslanecBT.setEnabled(true);

        removePoslanecBT.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            }
        });

        bfg = new BeanFieldGroup<PublicPerson2>(PublicPerson2.class);
        bfg.setBuffered(false);

        /* User interface can be created dynamically to reflect underlying data. */
        if (poslanec != null) {
            fieldNames = PomDao.getClassProperties(PublicPerson2.class, false);
        } else {
            fieldNames = PomDao.getClassProperties(PublicPerson2.class, false);
        }

        for (String fieldName : fieldNames) {
            if ("date_of_birth".equals(fieldName)) {
                PopupDateField dateF = new PopupDateField() {

                    @Override
                    protected java.util.Date handleUnparsableDateString(
                            String dateString) {// throws
                        // Converter.ConversionException{
                        // Try custom parsing
                        String fields[] = dateString.split("-");
                        if (fields.length >= 3) {
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat(
                                        "yyyy-MM-dd hh:mm:ss");
                                return (Date) sdf.parse(dateString);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        return poslanec.getDate_of_birth();
                    }
                };
                dateF.setImmediate(true);
                dateF.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));
                dateF.setLocale(Locale.UK);
                dateF.setResolution(Resolution.MINUTE);
                dateF.setDateFormat("yyyy-MM-dd HH:mm:ss");
                bfg.bind(dateF, "date_of_birth");
                poslanecFL.addComponent(dateF);
            } else if ("date_of_birth".equals(fieldName)) {

            } else {
                TextField field = new TextField(fieldName);
                bfg.bind(field, fieldName);
                poslanecFL.addComponent(field);
            }
			// field.setWidth("100%");
            // field.setTextChangeEventMode(TextChangeEventMode.LAZY);
            // editorFields.bind(field, fieldName);

        }

        poslanecFL.addComponent(poslanecHL);
		// poslanecFL.addComponent(new NavigationComponent(navigator));

        // Pridanie tlacidiel:
        editBT = new Button("Edit");
        saveBT = new Button("Save");
        editBT.setEnabled(true);
        saveBT.setEnabled(false);

        editBT.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                bfg.setEnabled(true);
                editBT.setEnabled(false);
                saveBT.setEnabled(true);

            }
        });

        saveBT.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                // ulozenie zmien do DB:
                try {
                    bfg.commit();
                    publicPersonRepo.save(poslanec);
                } catch (CommitException e1) {
                    Notification.show("Nepodarilo sa container commit!",
                            Type.ERROR_MESSAGE);
                    e1.printStackTrace();
                }

                // }
                bfg.setEnabled(false);
                editBT.setEnabled(true);
                saveBT.setEnabled(false);
            }
        });
        poslanecHL.addComponent(editBT);
        poslanecHL.addComponent(saveBT);

    }

    // 2. metoda hlavni inicializace prostredi:
    private void initLayout() {

        leftLayout = new VerticalLayout();
        poslanecFL = new FormLayout();
        poslanecFL.setMargin(true);
        poslanecFL.setSpacing(true);

        leftLayout.addComponent(poslanecFL);

        bfg2 = new BeanFieldGroup<PublicPerson2>(PublicPerson2.class);
        bfg2.setBuffered(false);
        if (poslanec != null) {
            bfg2.setItemDataSource(poslanec);
        }
        fl = new PomFormLayout<PublicPerson2>(PublicPerson2.class, bfg2);
        leftLayout.addComponent(fl);

        this.addComponent(leftLayout);

        layoutVertSp = new VerticalSplitPanel();
        // layout.setMargin(true);

        this.addComponent(layoutVertSp);
        hlasovaniFL = new FormLayout();
        hlasovaniFL.setVisible(false);

        layoutVertSp.addComponent(hlasovaniaPoslTB);

        // layout.setExpandRatio(hlasovaniaAllTB, 1);
        hlasovaniaPoslTB.setSizeFull();
        layoutVertSp.addComponent(hlasovaniFL);

    }

    // 4. Inicializuje editor udajov o poslancovi:
    public void initVoteOfRoleFL() throws NoSuchFieldException,
            SecurityException {

        removeVoteOfRoleBT = new Button("Odstranit A");

        hlasovaniFL.addComponent(removeVoteOfRoleBT);
        removeVoteOfRoleBT.setEnabled(true);

        removeVoteOfRoleBT.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                final YesNoWindow_old window = new YesNoWindow_old("Upozornenie",
                        "Chcete Hlasovanie Vymazať?",
                        (GeneralComponentListener) event.getButton()
                        .getParent().getParent().getParent());
                UI.getCurrent().addWindow(window);
            }
        });

        fg = new FieldGroup();// new
        // BeanFieldGroup<VoteOfRole>(VoteOfRole.class);
        fg.setBuffered(false);

        /* User interface can be created dynamically to reflect underlying data. */
        if (hlasovanie != null) {
            fieldNames1 = PomDao.getClassProperties(hlasovanie.getClass(), true);
        } else {
            fieldNames1 = PomDao.getClassProperties(VoteOfRole.class, true);
        }

        for (String fN : fieldNames1) {
            TextField field = new TextField(fN);
            switch (fN) {
                case "visible":
                case "vote_id":
                case "public_role_id":
                    fg.bind(field, fN);
                    field.setReadOnly(true);
                    break;
                default:
                    fg.bind(field, fN);
                    break;
            }
            // fg.bind(field, fN);
            hlasovaniFL.addComponent(field);
        }

        hlasovaniFL.addComponent(hlasovaniHL);
		// poslanecFL.addComponent(new NavigationComponent(navigator));

        // Pridanie tlacidiel:
        editBT1 = new Button("Edit A");
        saveBT1 = new Button("Save A");
        editBT1.setEnabled(true);
        saveBT1.setEnabled(false);

        editBT1.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                fg.setEnabled(true);
                editBT1.setEnabled(false);
                saveBT1.setEnabled(true);
            }
        });

        saveBT1.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    sqlContainer.commit();
                } catch (SQLException e) {
                    try {
                        sqlContainer.rollback();
                    } catch (UnsupportedOperationException e1) {
                        e1.printStackTrace();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }
                fg.setEnabled(false);
                editBT1.setEnabled(true);
                saveBT1.setEnabled(false);
            }
        });
        hlasovaniHL.addComponent(editBT1);
        hlasovaniHL.addComponent(saveBT1);
    }

    // 5. init comboBox
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

        linka = new Link("POSLANCI", new ExternalResource("http://vaadin.com/"));
        linka.setTargetName("_blank");

        layout.addComponents(menubar, nazov, linka);

    }

    // 6.
    private void tableAddFilter(List<Integer> pr_ids) {
        sqlContainer.removeAllContainerFilters();

        if (pr_ids != null && pr_ids.size() != 0) {
            Like lik;
            List<Filter> liks = new ArrayList<>();
            for (Integer i : pr_ids) {
                lik = new Like("public_role_id", "" + i);
                liks.add(lik);
            }

            Filter[] liksf = new Filter[liks.size()];
            liksf = liks.toArray(liksf);

			// Notification.show("DELKA: " + liksf.length);
            sqlContainer.addContainerFilter(new Or(liksf));
        } else {
            sqlContainer
                    .addContainerFilter(new Like("public_role_id", "" + -1));
            Notification
                    .show("Pozor, daná verejná osoba nemá žiadnu verejnú roľu!");
        }

		// sqlContainer.addContainerFilter(new Like("public_role_id", "3"));
        // sqlContainer.addContainerFilter(new Like("decision", "ZA"));
        sqlContainer.refresh();

    }

    private void doDelete() {
        sqlContainer.removeItem(hlasId);

        try {
            sqlContainer.commit();
            Notification.show("Hlasovanie úspešne zmazané!");
        } catch (UnsupportedOperationException | SQLException e) {
            try {
                sqlContainer.rollback();
            } catch (UnsupportedOperationException | SQLException e1) {
                e1.printStackTrace();
            }
            Notification.show("Mazanie sa nepodarilo!", Type.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @Override
    public void doYesAction() {
        // Notification.show("SI V REMOVE BT!");
        doDelete();
        hlasovaniFL.setVisible(false);
    }

    @Override
    public void doNoAction() {
        // do nothing
    }

    @Override
    public void enter(ViewChangeEvent event) {
        
        if (UI.getCurrent().getSession().getAttribute(PublicPerson2.class) != null) {
            this.poslanec = UI.getCurrent().getSession()
                    .getAttribute(PublicPerson2.class);
            this.initFilter();
            bfg.setItemDataSource(poslanec);
            bfg.setEnabled(false);
            fl.setEntity(poslanec);
            bfg2.setEnabled(false);

        }
        
        leftLayout.addComponent(NavigationComponent.getNavComp());
        
    }

}
