package sk.stefan.mvps.view.components;

import com.vaadin.addon.timeline.Timeline;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.UI;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.dbConnection.DoDBconn;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.model.serviceImpl.VoteServiceImpl;
import sk.stefan.utils.ToolsFiltering;

/**
 * Timeline na zobrazenie hlasovani.
 * este treba dokoncit ten debilny graf, co tam vobec nieje treba
 * a popisky pri prejdeni mysou.
 */
@SuppressWarnings("serial")
public final class MyTimeline extends Timeline {

    private static final Logger log = Logger.getLogger(MyTimeline.class);

    private static final long serialVersionUID = -2001141270398193257L;

    private final VoteService voteService;

    private SQLContainer timelineContainer;

    private final Filter basicFilter = new Compare.Equal("visible", Boolean.TRUE);

    private final Filter filter;

    public MyTimeline(List<Integer> ids) {

        voteService = new VoteServiceImpl();

        filter = ToolsFiltering.createFilter(ids);
        
        this.initTimeline();
        this.initTimelineListener();

    }

    public MyTimeline() {
        voteService = new VoteServiceImpl();

        //tj. all possible.
        filter = null;
        
        this.initTimeline();
        this.initTimelineListener();
    }

    private void initTimeline() {

        try {
            timelineContainer = DoDBconn.createSqlContainera("t_vote");
            timelineContainer.addContainerFilter(basicFilter);

            
            if (filter != null) {
                timelineContainer.addContainerFilter(filter);
            }
            this.setBrowserVisible(Boolean.TRUE);
            this.setWidth("80%");
//            this.addGraphDataSource(timelineContainer, "vote_date", 3);
//            this.setEventDataSource(timelineContainer, "vote_date", 3);
            this.addGraphDataSource(timelineContainer, "vote_date", "result_vote");
            this.setEventDataSource(timelineContainer, "vote_date", "result_vote");

        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }

    }

    private void initTimelineListener() {

        this.addListener(new Timeline.EventClickListener() {

            @Override
            public void eventClick(Timeline.EventButtonClickEvent event) {
//                Notification.show("AHOJ");
                Item item = timelineContainer.getItem(event.getItemIds().iterator().next());
                Integer voteId = (Integer) item.getItemProperty("id").getValue();

                Vote vot = voteService.findOne(voteId);
                UI.getCurrent().getSession().setAttribute(Vote.class, vot);
                UI.getCurrent().getNavigator().navigateTo("V6_VoteView");

            }

        });
    }

}
