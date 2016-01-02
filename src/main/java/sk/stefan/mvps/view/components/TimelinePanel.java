package sk.stefan.mvps.view.components;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.declarative.Design;
import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.base.elements.Trendline;
import org.dussan.vaadin.dcharts.base.elements.XYaxis;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.events.ChartData;
import org.dussan.vaadin.dcharts.events.click.ChartDataClickEvent;
import org.dussan.vaadin.dcharts.events.click.ChartDataClickHandler;
import org.dussan.vaadin.dcharts.metadata.XYaxes;
import org.dussan.vaadin.dcharts.metadata.locations.TooltipLocations;
import org.dussan.vaadin.dcharts.metadata.renderers.AxisRenderers;
import org.dussan.vaadin.dcharts.metadata.renderers.LabelRenderers;
import org.dussan.vaadin.dcharts.options.Axes;
import org.dussan.vaadin.dcharts.options.Cursor;
import org.dussan.vaadin.dcharts.options.Highlighter;
import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.options.SeriesDefaults;
import org.dussan.vaadin.dcharts.renderers.tick.AxisTickRenderer;
import org.dussan.vaadin.dcharts.renderers.tick.CanvasAxisTickRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.model.service.LinkService;
import sk.stefan.mvps.view.components.hlasovani.V6s_VotesView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by elopin on 09.11.2015.
 */
@SpringComponent
@Scope("prototype")
@DesignRoot
public class TimelinePanel extends Panel {

    @Autowired
    private LinkService linkService;

    private DCharts chart;
    private Button butZobrazHlasovani;

    private TabEntity relatedEntity;
    private List<Vote> container;
    private XYaxis xYaxis;
    private Map<String, Vote> voteMap = new HashMap<>();

    private SimpleDateFormat dateFormatter;

    public TimelinePanel() {
        Design.read(this);

        butZobrazHlasovani.addClickListener(event -> Page.getCurrent()
                .open(linkService.getUriFragmentForTabWithParentEntity(V6s_VotesView.class, relatedEntity.getEntityName(), relatedEntity.getId()), null));

        container = new ArrayList<>();
        dateFormatter = new SimpleDateFormat("dd-M-yyyy HH:mm");

        chart.setEnableChartDataClickEvent(true);

        chart.addHandler((ChartDataClickHandler) event -> {
            ChartData data = event.getChartData();
            Object[] originData = (Object[]) data.getOriginData();
            Vote vote = voteMap.get(originData[0]);
            if (vote != null) {
                Page.getCurrent().open(linkService.getUriFragmentForEntity(vote), null);
            }
        });

        xYaxis = new XYaxis()
                .setRenderer(AxisRenderers.DATE)
        .setTickOptions(new CanvasAxisTickRenderer().setAngle(-90).setFormatString("%m/%d/%y-%H:%M "));
        Axes axes = new Axes()
                .addAxis(xYaxis)
                .addAxis(
                        new XYaxis(XYaxes.Y).setTickOptions(new AxisTickRenderer()
                                .setFormatString("%d")).setShowTicks(false).setNumberTicks(4));

        Highlighter highlighter = new Highlighter()
                .setShow(true)
                .setSizeAdjust(10)
                .setShowTooltip(true)
                .setShowMarker(true);

        Options options = new Options()
                .addOption(axes)
                .addOption(highlighter);

        chart.setWidthUndefined();
        chart.setOptions(options);

    }

    public void setRelatedEntity(TabEntity relatedEntity) {
        this.relatedEntity = relatedEntity;
        butZobrazHlasovani.setVisible(relatedEntity != null);
    }

    public void setVotes(List<Vote> votes) {
        container.clear();
        voteMap.clear();
        container.addAll(votes);

        Collections.sort(votes, (v1, v2) -> v1.getVote_date().compareTo(v2.getVote_date()));
        DataSeries dataSeries = new DataSeries().newSeries();
        votes.forEach(vote -> {
            String date = dateFormatter.format(vote.getVote_date());
            dataSeries.add(date, vote.getResult_vote().ordinal() + 1);
            voteMap.put(date, vote);
        });
        xYaxis.setNumberTicks(container.size());
        chart.setDataSeries(dataSeries).show();
    }
}
