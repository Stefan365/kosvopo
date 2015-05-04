package sk.stefan.MVP.view.components;

import java.util.Date;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Random;

import java.util.Calendar;
import com.vaadin.addon.timeline.Timeline;
import com.vaadin.addon.timeline.Timeline.EventButtonClickEvent;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.*;

@SuppressWarnings("serial")
public final class MyTimeLine extends VerticalLayout {

    private final Timeline timeline;

    public MyTimeLine(String title) {
//    __app = application;
//
//    panel.setWidth("660px");
//    panel.setHeight("375px");
//    panel.setCaption("Synthèse du Mois");

        timeline = new Timeline(title);
        timeline.setWidth("100%");

        // create the data sources
        Container.Indexed graphDataSource = createGraphDataSource();
        Container.Indexed markerDataSource = createmarkerDataSource();
        final Container.Indexed eventDataSource = createEventDateSource();

        // add our data sources
        timeline.addGraphDataSource(graphDataSource,
                Timeline.PropertyId.TIMESTAMP, Timeline.PropertyId.VALUE);
        
        timeline.setMarkerDataSource(markerDataSource,
                Timeline.PropertyId.TIMESTAMP, Timeline.PropertyId.CAPTION,
                Timeline.PropertyId.VALUE);
        timeline.setEventDataSource(eventDataSource,
                Timeline.PropertyId.TIMESTAMP, Timeline.PropertyId.CAPTION);

        timeline.setGraphLegend(graphDataSource, "dimension x");
        timeline.setGraphOutlineColor(graphDataSource, Color.BLUE);
        timeline.setBrowserFillColor(graphDataSource, new Color(255, 0, 0, 128));
        timeline.setGraphOutlineThickness(2.0);

        timeline.setBrowserOutlineColor(graphDataSource, Color.BLACK);
        timeline.setBrowserFillColor(graphDataSource, new Color(0, 0, 0, 128));

        // adding a listener
        timeline.addListener(new Timeline.EventClickListener() {

            @Override
            public void eventClick(EventButtonClickEvent event) {
                Item item = eventDataSource.getItem(event.getItemIds().iterator().next());
                Date sunday = (Date) item.getItemProperty(Timeline.PropertyId.TIMESTAMP).getValue();

                SimpleDateFormat formatter = new SimpleDateFormat(
                        "EEE, MMM d, ''yy");

//        __app.getMainWindow()
//            .showNotification(formatter.format(sunday));
            }

        });

        setMargin(true);
        setSpacing(true);

        addComponent(timeline);
    }

    /*
     * create a graph container with a month of random data
     */
    public Container.Indexed createGraphDataSource() {
    
        Container.Indexed container = new IndexedContainer();

        container.addContainerProperty(Timeline.PropertyId.TIMESTAMP,
                Date.class, null);
        container.addContainerProperty(Timeline.PropertyId.VALUE, Float.class,
                0f);

        // add random data to container
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        java.util.Date today = new java.util.Date();
        Random generator = new Random();

        while (cal.getTime().before(today)) {
            // create a point in time
            Item item = container.addItem(cal.getTime());

            // set the time stamp property
            item.getItemProperty(Timeline.PropertyId.TIMESTAMP).setValue(
                    cal.getTime());

            // set the value property
            item.getItemProperty(Timeline.PropertyId.VALUE).setValue(
                    generator.nextFloat());

            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return container;
    }

    /*
     * Creates a marker container with a marker for each seven days
     */
    public Container.Indexed createmarkerDataSource() {
        Container.Indexed container = new IndexedContainer();

        container.addContainerProperty(Timeline.PropertyId.TIMESTAMP,
                Date.class, null);
        container.addContainerProperty(Timeline.PropertyId.CAPTION,
                String.class, "our marker symbol");
        container.addContainerProperty(Timeline.PropertyId.VALUE, String.class,
                "Our description");

        // add a marker for every 7 days
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        java.util.Date today = new java.util.Date();
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMM d, ''yy");

        while (cal.getTime().before(today)) {
            // create a point in time
            Item item = container.addItem(cal.getTime());

            // set the time stamp property
            item.getItemProperty(Timeline.PropertyId.TIMESTAMP).setValue(
                    cal.getTime());

            // set the caption property
            item.getItemProperty(Timeline.PropertyId.VALUE).setValue("M");

            // set the value property
            item.getItemProperty(Timeline.PropertyId.VALUE).setValue(
                    formatter.format(cal.getTime()));

            cal.add(Calendar.DAY_OF_MONTH, 7);
        }
        return container;
    }

    /*
     * Creates an event container with a marker for each Sunday
     */
    public Container.Indexed createEventDateSource() {
        
        Container.Indexed container = new IndexedContainer();

        container.addContainerProperty(Timeline.PropertyId.TIMESTAMP,
                Date.class, null);
        container.addContainerProperty(Timeline.PropertyId.CAPTION,
                String.class, "our marker symbol");

        // add a marker for every 7 days
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        java.util.Date today = new java.util.Date();

        while (cal.getTime().before(today)) {
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                // create a point in time
                Item item = container.addItem(cal.getTime());

                // set the time stamp property
                item.getItemProperty(Timeline.PropertyId.TIMESTAMP).setValue(
                        cal.getTime());

                // set the caption property
                item.getItemProperty(Timeline.PropertyId.CAPTION).setValue(
                        "Sunday");
            }
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return container;
    }

}
