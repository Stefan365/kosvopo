package sk.stefan.mvps.view.components.hlasovani;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.declarative.Design;
import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.metadata.renderers.SeriesRenderers;
import org.dussan.vaadin.dcharts.options.Legend;
import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.options.SeriesDefaults;
import org.dussan.vaadin.dcharts.renderers.series.PieRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.enums.VoteAction;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.model.entity.VoteOfRole;
import sk.stefan.mvps.model.service.VoteService;

import java.util.List;

/**
 * Graf pro zobrazení hlasování.
 *
 * @author elopin on 22.11.2015.
 */
@SpringComponent
@Scope("prototype")
@DesignRoot
public class GrafHlasovaniPanel extends Panel {

    @Autowired
    private VoteService voteService;

    //Design
    private DCharts chart;

    //data
    private Vote vote;

    public GrafHlasovaniPanel() {
        Design.read(this);
    }

    public void setVote(Vote v) {
        this.vote = v;

        List<VoteOfRole> votes = voteService.findVoteOfRolesByVoteId(vote.getId());
        long pro = votes.parallelStream().filter(voteOfRole -> voteOfRole.getDecision() == VoteAction.FOR).count();
        long proti = votes.parallelStream().filter(voteOfRole -> voteOfRole.getDecision() == VoteAction.AGAINST).count();
        long zdrzel = votes.parallelStream().filter(voteOfRole -> voteOfRole.getDecision() == VoteAction.REFAIN).count();
        long nepritomny = votes.parallelStream().filter(voteOfRole -> voteOfRole.getDecision() == VoteAction.ABSENT).count();

        DataSeries dataSeries = new DataSeries();
                dataSeries.newSeries().add(VoteAction.FOR.getName(), pro);
                dataSeries.newSeries().add(VoteAction.AGAINST.getName(), proti);
                dataSeries.newSeries().add(VoteAction.REFAIN.getName(), zdrzel);
                dataSeries.newSeries().add(VoteAction.ABSENT.getName(), nepritomny);

        SeriesDefaults seriesDefaults = new SeriesDefaults()
                .setRenderer(SeriesRenderers.PIE)
                .setRendererOptions(
                        new PieRenderer()
                                .setShowDataLabels(true));

        Legend legend = new Legend()
                .setShow(true);

        Options options = new Options()
                .setSeriesDefaults(seriesDefaults)
                .setLegend(legend);
        chart.setDataSeries(dataSeries)
                .setOptions(options)
                .show();
    }
}
