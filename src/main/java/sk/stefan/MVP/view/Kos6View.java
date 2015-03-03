package sk.stefan.MVP.view;

import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.utils.ImageUploader;
import sk.stefan.utils.ImageUploaderA;
import sk.stefan.utils.MyImageSource;
import sk.stefan.utils.Take5;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Link;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload.ProgressListener;

import java.io.File;
import java.io.Serializable;
import sk.stefan.MVP.model.entity.dao.User;

public class Kos6View extends VerticalLayout implements View {

    private VerticalLayout layout;
    private MenuBar menubar;
    private Link linka;
    private Label selection;
    private Label nazov;
    private Image sample;
    private StreamResource.StreamSource imagesource;
    private StreamResource resource;
    private MenuBar.Command mycommand;
    private ImageUploader receiver;
    private Panel panel;
    private Upload uploada, upload;
    private Label counter;

    public Kos6View(Navigator nav) {
        this.initLayout();
        this.addComponent(NavigationComponent.getNavComp());
    }

    private void initLayout() {

        layout = new VerticalLayout();
        layout.setMargin(true);

        this.addComponent(layout);

        // Prvy obrazok, nefunguje:
        sample = new Image();

        // funguje:
        sample.setSource(new ThemeResource("img/psychobox.png"));

        // nefunguje:
        // sample.setSource(new ThemeResource(
        // "C:/Users/User/Pictures/obr/psychobox.png"));
        layout.addComponent(sample);

        // kruhovity obrazok:
        imagesource = new MyImageSource();
        resource = new StreamResource(imagesource, "myimage.png");
        layout.addComponent(new Image("OBRAZOK", resource));

        // A.MENUBAR
        menubar = new MenuBar();
        menubar.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);

        // A feedback component
        selection = new Label("-");
        layout.addComponent(selection);

        // Define a common menu command for all the menu items.
        mycommand = new MenuBar.Command() {

            @Override
            public void menuSelected(MenuItem selectedItem) {
                selection.setValue("Ordered a " + selectedItem.getText()
                        + " from menu.");
            }
        };

        MenuBar.MenuItem politici = menubar
                .addItem("POLITICI", null, mycommand);

        // Put some items in the menu hierarchically
        MenuBar.MenuItem beverages = menubar.addItem("Beverages", null, null);

        MenuBar.MenuItem hot_beverages = beverages.addItem("Hot", null, null);
        hot_beverages.addItem("Tea", null, mycommand);
        hot_beverages.addItem("Coffee", null, mycommand);
        MenuBar.MenuItem cold_beverages = beverages.addItem("Cold", null, null);
        cold_beverages.addItem("Milk", null, mycommand);

        // Another top-level item
        MenuBar.MenuItem snacks = menubar.addItem("Snacks", null, null);
        snacks.addItem("Weisswurst", null, mycommand);
        snacks.addItem("Salami", null, mycommand);

        // Yet another top-level item
        MenuBar.MenuItem services = menubar.addItem("Services", null, null);
        services.addItem("Car Service", null, mycommand);

        layout.addComponent(menubar);

        // E. Picture
        // Find the application directory
        String basepath = VaadinService.getCurrent().getBaseDirectory()
                .getAbsolutePath();

        // Image as a file resource
        FileResource resourceA = new FileResource(new File(basepath
                + "/WEB-INF/images/image.png"));

        // Show the image in the application
        Image imageA = new Image("Image from file", resourceA);

        // Let the user view the file in browser or download it
        Link link = new Link("Link to the image file", resourceA);

        Button enabled = new Button("Enabled");
        enabled.setEnabled(true);
        Button disabled = new Button("Disabled");
        disabled.setEnabled(false);

        enabled.setDescription("<h2><img src=\"../VAADIN/themes/sampler/icons/comment_yellow.gif\"/>"
                + "A richtext tooltip</h2>"
                + "<ul>"
                + " <li>Use rich formatting with XHTML</li>"
                + " <li>Include images from themes</li>"
                + " <li>etc.</li>"
                + "</ul>");
        layout.addComponent(enabled);
        layout.addComponent(disabled);

        // Text field with maximum length
        final TextField tf = new TextField("My Eventful Field");
        tf.setValue("Initial content");
        tf.setMaxLength(20);

        // Counter for input length
        counter = new Label();
        counter.setValue(tf.toString().length() + " of " + tf.getMaxLength());
        // Display the current length interactively in the counter
        tf.addValueChangeListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                int len = event.toString().length();
                counter.setValue(len + " of " + tf.getValue());
            }
        });

        // This is actually the default
        tf.setTextChangeEventMode(TextChangeEventMode.LAZY);

        layout.addComponent(tf);
        // layout.addComponent(value);
        layout.addComponent(counter);

        Button button = new Button("Click Me!");

        button.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                Notification.show("Thank You!");
            }
        });

        layout.addComponent(button);

        // 5.14 CheckBox
        // A check box with default state (not checked, false).
        final CheckBox checkbox1 = new CheckBox("My CheckBox");
        layout.addComponent(checkbox1);

        // Another check box with explicitly set checked state.
        final CheckBox checkbox2 = new CheckBox("Checked CheckBox");
        checkbox2.setValue(true);
        layout.addComponent(checkbox2);

        // Make some application logic. We use anonymous listener
        // classes here. The above references were defined as final
        // to allow accessing them from inside anonymous classes.
        checkbox1.addValueChangeListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                // Copy the value to the other checkbox.
                checkbox2.setValue(checkbox1.getValue());
            }
        });

        checkbox2.addValueChangeListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                // Copy the value to the other checkbox.
                checkbox1.setValue(checkbox2.getValue());
            }
        });

        // 5.15 SELECTION
        // Create a selection component
        ComboBox select2 = new ComboBox("Inner Planets");
        // A class that implements toString()
        class PlanetId extends Object implements Serializable {

            String planetName;

            PlanetId(String name) {
                planetName = name;
            }

            public String toString() {
                return "The Planet " + planetName;
            }
        }

        // Use such objects as item identifiers
        String planets[] = {"Mercury", "Venus", "Earth", "Mars"};

        for (int i = 0; i < planets.length; i++) {
            select2.addItem(new PlanetId(planets[i]));
        }

        layout.addComponent(select2);

        // volanie funkcie:
        this.propertyModeExample(layout);

		// B. FILE UPLOADER:
        // B.1:
		/*
         * receiver = new ImageUploader();
         * 
         * // Create the upload with a caption and set receiver later upload =
         * new Upload("Upload Image Here", receiver);
         * upload.setButtonCaption("Start Upload");
         * upload.addSucceededListener(receiver);
         * 
         * upload.addProgressListener(new ProgressListener() {
         * 
         * @Override public void updateProgress(long readBytes, long
         * contentLength) { if (readBytes > 30000000) {
         * Notification.show("Too big file", Notification.Type.ERROR_MESSAGE);
         * upload.interruptUpload(); } } });
         * 
         * // Put the components in a panel panel = new
         * Panel("Cool Image Storage"); //layout.addComponents(upload, image2);
         * layout.addComponents(upload); layout.addComponents(panel);
         * panel.setContent(layout); /
         */
        // B.2
        // Implement both receiver that saves upload in a file and
        // listener for successful upload
        ImageUploaderA uploadera = new ImageUploaderA();

        // Create the upload with a caption and set receiver later
        uploada = new Upload("Upload Image Here", uploadera);
        uploada.setButtonCaption("Start Upload");
        uploada.addProgressListener(new ProgressListener() {
            @Override
            public void updateProgress(long readBytes, long contentLength) {

            }
        });

        uploada.addSucceededListener(uploadera);

        // Put the components in a panel
        // Panel panel = new Panel("Cool Image Storage");
        // Layout panelContent = new VerticalLayout();
        layout.addComponents(uploada, uploadera.getImage());

        // panel.setContent(layout);
        // layout.addComponents(upload, image2);
        // layout.addComponents(panel);
        // C. TLACITKA:
        Button b3 = new Button("Tarot");

        final String s = "";
        final Label tarot = new Label("TAROT");
        b3.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                Take5.nakrmzoznam();
                tarot.setValue(Take5.zobraz());
                layout.addComponent(new Label(s));
            }
        });

        layout.addComponent(b3);
        layout.addComponent(tarot);

    }

    public void propertyModeExample(VerticalLayout layout) {

        // Have a bean container to put the beans in
        BeanItemContainer<User> container = new BeanItemContainer<User>(
                User.class);

        // Put some example data in it
        container.addItem(new User("Mercury"));
        container.addItem(new User("Venus"));
        container.addItem(new User("Earth"));
        container.addItem(new User("Mars"));
        // Create a selection component bound to the container
        ComboBox select = new ComboBox("Los Planets", container);
		// Set the caption mode to read the caption directly
        // from the 'name' property of the bean

        // select.setItemCaptionMode(ComboBox.ITEM_CAPTION_MODE_PROPERTY);
        select.setItemCaptionPropertyId("name");
        layout.addComponent(select);

    }

    @Override
    public void enter(ViewChangeEvent event) {
        this.addComponent(NavigationComponent.getNavComp());
    }

}
