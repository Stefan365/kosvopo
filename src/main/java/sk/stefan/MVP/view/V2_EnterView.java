package sk.stefan.MVP.view;

import com.vaadin.addon.timeline.Timeline;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.AbstractClientConnector;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import sk.stefan.DBconnection.DoDBconn;
import sk.stefan.MVP.model.entity.A_User;
import sk.stefan.MVP.model.entity.Vote;
import sk.stefan.MVP.model.service.VoteService;
import sk.stefan.MVP.model.serviceImpl.VoteServiceImpl;

public class V2_EnterView extends VerticalLayout implements View {

    private static final Logger log = Logger.getLogger(V2_EnterView.class);

    private static final long serialVersionUID = -2001141270398193257L;

    private final VoteService voteService;

    private final Timeline timeline;

    private SQLContainer timelineContainer;

    public V2_EnterView() {

        this.setMargin(true);
        this.setSpacing(true);

        voteService = new VoteServiceImpl();
        timeline = new Timeline();

        this.initTimeline();
        initTimelineListener();
        this.addComponents(timeline);

    }

    /**
     */
    private void initTimeline() {

        
        try {
            timelineContainer = DoDBconn.createSqlContainera("t_vote");
            timeline.setBrowserVisible(Boolean.TRUE);
            timeline.setWidth("100%");
            timeline.addGraphDataSource(timelineContainer, "vote_date", "for_vote");
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }

    }

    private void initTimelineListener() {

        timeline.addListener(new Timeline.EventClickListener() {

            @Override
            public void eventClick(Timeline.EventButtonClickEvent event) {
                Notification.show("AHOJ");
                Item item = timelineContainer.getItem(event.getItemIds().iterator().next());
                Integer voteId = (Integer) item.getItemProperty("id").getValue();

                Vote vot = voteService.findOne(voteId);
                UI.getCurrent().getSession().setAttribute(Vote.class, vot);
                UI.getCurrent().getNavigator().navigateTo("V6_VoteView");

            }

        });
    }

    @Override
    public void enter(ViewChangeEvent event) {

    }

}
