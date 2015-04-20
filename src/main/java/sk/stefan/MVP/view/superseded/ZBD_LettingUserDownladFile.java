package sk.stefan.MVP.view.superseded;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import sk.stefan.MVP.view.components.NavigationComponent;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ZBD_LettingUserDownladFile extends VerticalLayout implements View {

    private Button downloadButton;
    private StreamResource myResource;
    private FileDownloader fileDownloader;
    private Window win;
    private HorizontalLayout lay2;
    private final NavigationComponent navComp;
    
    public ZBD_LettingUserDownladFile(Navigator nav) {

        this.initLayout();
        navComp = NavigationComponent.createNavigationComponent();
        this.addComponent(navComp);
    }

    private void initLayout() {

        downloadButton = new Button("Download image");

        myResource = createResource();
        fileDownloader = new FileDownloader(myResource);

        fileDownloader.extend(downloadButton);
        //String path = "C:/Users/User/Pictures/obr/psychobox.png";
        String path = "C:/psychobox.png";

        publishReport(path);

        this.addComponent(downloadButton);
    }

    private StreamResource createResource() {
        return new StreamResource(new StreamSource() {
            @Override
            public InputStream getStream() {
                String text = "My image";

                BufferedImage bi = new BufferedImage(100, 30, BufferedImage.TYPE_3BYTE_BGR);
                bi.getGraphics().drawChars(text.toCharArray(), 0, text.length(), 10, 20);

                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ImageIO.write(bi, "png", bos);
                    return new ByteArrayInputStream(bos.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }

            }
        }, "psychobox.png");
    }

    @Override
    public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

    }

    private void publishReport(final String path) {

        StreamSource s = new StreamSource() {

            public java.io.InputStream getStream() {
                try {
                    File f = new File(path);
                    FileInputStream fis = new FileInputStream(f);
                    return fis;
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return null;
            }
        };

        //StreamResource r = new StreamResource(s, "appointmentScheduleDate.pdf", this.getUI());
        StreamResource r = new StreamResource(s, "C:/psychobox.png");
        r.setCacheTime(-1);
        Embedded e = new Embedded();
        e.setSizeFull();
        //e.setType(Embedded.TYPE_BROWSER);

        r.setMIMEType("application/pdf");

        e.setSource(r);
        e.setHeight("650px");

        /*
         win = new Window("Report");
         win.center();
         win.setModal(true);
         win.setHeight("700px");
         win.setWidth("900px");
         */
        lay2 = new HorizontalLayout();
        lay2.addComponent(e);
        this.addComponent(lay2);
	    //win.addComponent(e);

        //this.getUI().addWindow(win);
	    /**/
    }

}
