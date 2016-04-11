package sk.stefan.mvps.view.components;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.mvps.model.entity.Document;
import sk.stefan.mvps.model.service.DocumentService;
import sk.stefan.utils.Localizator;

import java.util.function.Consumer;

/**
 * Řádek s dokumentem v panelu dokumentů. Umožňuje stáhnout dokument kliknutím na link, nebo ho deaktivovat.
 * @author Lukas on 30.12.2015.
 */
@SpringComponent
@Scope("prototype")
@DesignRoot
public class DokumentComponent extends HorizontalLayout {

    @Autowired
    private DocumentService documentService;

    //Design
    private Link link;
    private Button remove;

    //data
    private Document document;
    private Consumer<Document> removeListener;

    private boolean removable;

    public DokumentComponent() {
        Design.read(this);
        remove.addClickListener(event -> onRemove());
    }

    public void setRemoveListener(Consumer<Document> removeListener) {
        this.removeListener = removeListener;
    }

    private void onRemove() {
        documentService.deactivate(document);
        if (removeListener != null) {
            removeListener.accept(document);
        }
    }

    public void setDocument(Document document) {
        this.document = document;
        link.setCaption(document.getFile_name());
        link.setResource(documentService.getDocumentResource(document));
        remove.setVisible(removable);
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
    }
}
