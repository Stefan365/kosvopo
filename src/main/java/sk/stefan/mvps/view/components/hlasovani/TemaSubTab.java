package sk.stefan.mvps.view.components.hlasovani;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.annotations.ViewTab;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.listeners.RemoveListener;
import sk.stefan.listeners.SaveListener;
import sk.stefan.mvps.model.entity.Subject;
import sk.stefan.mvps.model.service.LinkService;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.view.tabs.TabComponent;

/**
 * Záložka pro zobrazení tématu-predmetu hlasování.
 *
 * @author Lukas on 20.02.2016.
 */
@ViewTab("subject")
@SpringComponent
@Scope("prototype")
@DesignRoot
public class TemaSubTab extends VerticalLayout implements TabComponent {

    @Autowired
    private VoteService voteService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private SecurityService securityService;

    private TemaSubPanel temasubPanel;

    //data:
    private Subject subject;

    public TemaSubTab() {
        Design.read(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setSaveListener(SaveListener<TabEntity> saveListener) {
        temasubPanel.setSaveListener(l -> saveListener.accept(subject));
//        temasubPanel.setSaveListener(saveListener);
    }

    @Override
    public void setRemoveListener(RemoveListener<TabEntity> removeListener) {
        temasubPanel.setRemoveListener(l -> removeListener.accept(subject));
    }

    @Override
    public void setEntity(TabEntity tabEntity) {
        this.subject = (Subject) tabEntity;
        temasubPanel.setThemeSub(subject);
    }

    @Override
    public TabEntity getEntity() {
        return subject;
    }

    @Override
    public void show() {
        subject = voteService.findSubjectById(subject.getId());
        setEntity(subject);
    }

    @Override
    public String getTabCaption() {
        return subject.getPresentationName();
    }

    @Override
    public String getTabId() {
        return "subject" + subject.getId();
    }
}
