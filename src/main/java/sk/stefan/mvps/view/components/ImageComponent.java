package sk.stefan.mvps.view.components;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Image;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import java.io.ByteArrayInputStream;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sk.stefan.utils.ImageTools;
import sk.stefan.utils.Localizator;

/**
 * Komponenta pro nahrání a zobrazení obrázku.
 * @author elopin on 28.11.2015.
 */
@Component
@Scope("prototype")
@DesignRoot
public class ImageComponent extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    //Design
    private Image image;
    private Upload upload;

    //data
    private byte[] imageByte;
    private ByteArrayOutputStream baos;

    public ImageComponent() {
        Design.read(this);
        Localizator.localizeDesign(this);

        upload.setReceiver((filename, mimetype) -> baos = new ByteArrayOutputStream());
        upload.addSucceededListener(event -> {
            imageByte = baos.toByteArray();
            fillImage();

        });
    }

    public void setImage(byte[] imageByte) {
        this.imageByte = imageByte;
        fillImage();
    }

    public byte[] getImage() {
        return imageByte;
    }

    private void fillImage() {
        image.setSource(null);
        if (imageByte != null) {
            StreamSource source = (StreamSource) () -> new ByteArrayInputStream(imageByte);
            String imageFileName = "image" + Page.getCurrent().getWebBrowser().getCurrentDate().getTime();
            StreamResource resource = new StreamResource(source, imageFileName);
            resource.setCacheTime(0);
            image.setSource(resource);
        } else {
            ImageTools.fillDefaultImage(image);
        }
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        upload.setVisible(!readOnly);
    }

}
