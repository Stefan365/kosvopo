package sk.stefan.mvps.view.components;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import sk.stefan.enums.UserType;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.entity.Document;
import sk.stefan.mvps.model.service.DocumentService;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.utils.Localizator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Panel s dokumenty.
 * @author elopin on 09.11.2015.
 */
@SpringComponent
@Scope("prototype")
@DesignRoot
public class DokumentyPanel<E extends TabEntity> extends Panel implements Upload.Receiver, Upload.SucceededListener {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private SecurityService securityService;

    //Design
    private Label emptyLabel;
    private VerticalLayout layout;
    private Upload upload;

    //data
    private E entity;
    private Map<Integer, DokumentComponent> dokumentMap = new HashMap<>();
    private String filename;
    private File file;

    private boolean canRemove;

    private static final long serialVersionUID = 1L;

    public DokumentyPanel() {
        Design.read(this);
        Localizator.localizeDesign(this);

        upload.addSucceededListener(this);
        upload.setReceiver(this);
    }

    public void setEntity(E entity) {
        this.entity = entity;

        //oprávnění
        canRemove = securityService.currentUserHasRole(UserType.ADMIN) || securityService.currentUserHasRole(UserType.VOLUNTEER);
        upload.setVisible(canRemove);

        layout.removeAllComponents();
        List<Document> documents = documentService.findAllEntityDocuments(documentService.getClassTableName(entity.getClass()), entity.getId());
        documents.forEach(document -> {
            DokumentComponent dokumentComponent = context.getBean(DokumentComponent.class);
            dokumentComponent.setRemovable(canRemove);
            dokumentComponent.setDocument(document);
            dokumentComponent.setRemoveListener(this::removeDokument);
            layout.addComponent(dokumentComponent);
            dokumentMap.put(document.getId(), dokumentComponent);
        });
        emptyLabel.setVisible(documents.isEmpty());
        layout.setVisible(!documents.isEmpty());
    }

    private void removeDokument(Document document) {
        DokumentComponent dokumentComponent = dokumentMap.remove(document.getId());
        if (dokumentComponent != null) {
            layout.removeComponent(dokumentComponent);
        }
        setEntity(entity);
    }

    @Override
    public OutputStream receiveUpload(String filename, String mimetype) {
        this.filename = filename;
        FileOutputStream fos;

        try {
            file = new File(filename);
            fos = new FileOutputStream(file);

        } catch (final java.io.FileNotFoundException e) {
            new Notification("Could not open file<br/>",
                    e.getMessage(), Notification.Type.ERROR_MESSAGE)
                    .show(Page.getCurrent());
            return null;
        }
        return fos;
    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent succeededEvent) {

        InputStream inStream = null;

        String tn = documentService.getClassTableName(entity.getClass());

        try {
            Document doc = new Document();
            doc.setFile_name(filename);
            doc.setTable_name(tn);
            doc.setTable_row_id(entity.getId());
            doc.setUpload_date(new Date());
            doc.setVisible(Boolean.TRUE);

            inStream = new FileInputStream(file);
            byte[] bFile = new byte[inStream.available()];
            inStream.read(bFile);
            doc.setDocument(bFile);

            doc = documentService.save(doc);
            Notification.show("File saved to Database!");
            setEntity(entity);


        } catch (IOException | IllegalArgumentException | SecurityException e) {
                throw new RuntimeException("Chyba při uploadu", e);
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
            } catch (IOException ex) {
                throw new RuntimeException("Chyba při uzavírán vstupu dokumentu!", ex);
            }
        }
    }
}
