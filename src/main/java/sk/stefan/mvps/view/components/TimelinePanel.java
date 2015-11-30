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
import org.dussan.vaadin.dcharts.metadata.XYaxes;
import org.dussan.vaadin.dcharts.metadata.renderers.AxisRenderers;
import org.dussan.vaadin.dcharts.options.Axes;
import org.dussan.vaadin.dcharts.options.Cursor;
import org.dussan.vaadin.dcharts.options.Highlighter;
import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.options.SeriesDefaults;
import org.dussan.vaadin.dcharts.renderers.tick.AxisTickRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.model.service.LinkService;
import sk.stefan.mvps.view.components.hlasovani.V6s_VotesView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    private SimpleDateFormat dateFormatter;

    public TimelinePanel() {
        Design.read(this);

        butZobrazHlasovani.addClickListener(event -> Page.getCurrent()
                .open(linkService.getUriFragmentForTabWithParentEntity(V6s_VotesView.class, relatedEntity.getEntityName(), relatedEntity.getId()), null));

        container = new ArrayList<>();
        dateFormatter = new SimpleDateFormat("dd-mm-yyyy");

        SeriesDefaults seriesDefaults = new SeriesDefaults()
                .setTrendline(new Trendline().setShow(true));

        Axes axes = new Axes()
                .addAxis(
                        new XYaxis()
                                .setRenderer(AxisRenderers.DATE)
                                .setTickOptions(
                                        new AxisTickRenderer()
                                                .setFormatString("%#m/%#d/%y"))
                                .setNumberTicks(container.size()))
                .addAxis(
                        new XYaxis(XYaxes.Y).setTickOptions(new AxisTickRenderer()
                                .setFormatString("%d")).setShowTicks(false).setNumberTicks(4));

        Highlighter highlighter = new Highlighter()
                .setShow(true)
                .setSizeAdjust(10)
                .setShowTooltip(false)
                .setShowMarker(true);


        Cursor cursor = new Cursor()
                .setShow(true);

        Options options = new Options()
                .addOption(seriesDefaults)
                .addOption(axes)
                .addOption(highlighter)
                .addOption(cursor);

        chart.setWidthUndefined();
        chart.setOptions(options);

    }

    public void setRelatedEntity(TabEntity relatedEntity) {
        this.relatedEntity = relatedEntity;
        butZobrazHlasovani.setVisible(relatedEntity != null);
    }

    public void setVotes(List<Vote> votes) {

        Collections.sort(votes, (v1, v2) -> v1.getVote_date().compareTo(v2.getVote_date()));
        DataSeries dataSeries = new DataSeries().newSeries();
        votes.forEach(vote -> {
            dataSeries.add(dateFormatter.format(vote.getVote_date()), vote.getResult_vote().ordinal() + 1);
        });


        chart.setDataSeries(dataSeries).show();
    }
}
