package sk.stefan.MVP.view;

import com.vaadin.addon.timeline.Timeline;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.sql.SQLException;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import sk.stefan.DBconnection.DoDBconn;
import sk.stefan.MVP.model.service.UserService;
import sk.stefan.MVP.model.serviceImpl.UserServiceImpl;
import sk.stefan.MVP.view.components.MyTimeLine;

public class V2_EnterView extends VerticalLayout implements View {

    private static final Logger log = Logger.getLogger(V2_EnterView.class);

    private static final long serialVersionUID = -2001141270398193257L;

//    private final Label vstupniLab;
    private final UserService userService;

//    private final VerticalLayout temporaryLy;
//    private final NavigationComponent navComp;
    private final Navigator nav;

    private final MyTimeLine myTimeline;

    private Timeline timeline;

    public V2_EnterView() {

        this.setMargin(true);
        this.setSpacing(true);

        this.nav = UI.getCurrent().getNavigator();

        myTimeline = new MyTimeLine("AHOJ KAMO");
        
        this.initTimeline();
        this.addComponents(timeline);

//        navComp =  ((KosvopoUI)UI.getCurrent()).getNavComp();
//        log.info("IS NAVCOMP NULL?" + (navComp== null));
//        this.addComponent(navComp);
//        temporaryLy = new VerticalLayout();
//        this.addComponent(temporaryLy);
        this.userService = new UserServiceImpl();

//        this.vstupniLab = new Label("KAMIL");
//        this.vstupniLab.setCaption("Marcel Z Maleho Mesta JE Tu!!! Je, je, je jeee....");
//        this.vstupniLab.setValue("HODNOTA");
//        temporaryLy.addComponent(vstupniLab);
    }

    private void initTimeline() {
        
        try {
            timeline = new Timeline();
            
            timeline.setBrowserVisible(Boolean.TRUE);
            timeline.setWidth("100%");
            SQLContainer timelineContainer = DoDBconn.createSqlContainera("t_vote");
            timeline.addGraphDataSource(timelineContainer, "vote_date", "for_vote");
        } catch (SQLException ex) {
            log.error(ex.getMessage(),ex);
        }


    }

    @Override
    public void enter(ViewChangeEvent event) {

    }

}
