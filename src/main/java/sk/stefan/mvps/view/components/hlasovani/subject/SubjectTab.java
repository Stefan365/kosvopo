package sk.stefan.mvps.view.components.hlasovani.subject;

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
public class SubjectTab extends VerticalLayout implements TabComponent {

    @Autowired
    private VoteService voteService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private SecurityService securityService;

    private SubjectPanel subjectPanel;

    //data:
    private Subject subject;

    public SubjectTab() {
        Design.read(this);
    }

    @Override
    public void setSaveListener(SaveListener<TabEntity> saveListener) {
//        obohateny listener:
        subjectPanel.setSaveListener(l -> saveListener.save(subject));
//        povodny listener:
//        subjectPanel.setSaveListener(saveListener);
    }

    @Override
    public void setRemoveListener(RemoveListener<TabEntity> removeListener) {
        subjectPanel.setRemoveListener(l -> removeListener.remove(subject));
    }

    @Override
    public void setEntity(TabEntity tabEntity) {
        this.subject = (Subject) tabEntity;
        subjectPanel.setSubject(subject);
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
