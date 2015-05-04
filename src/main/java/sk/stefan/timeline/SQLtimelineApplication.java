package sk.stefan.timeline;



import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import com.vaadin.addon.timeline.Timeline;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class SQLtimelineApplication {
    
    private static final int DEFAULT_DATA_POINTS = 1000;
    private SQLContainer tableContainer;
    private SQLContainer timelineContainer;
    private Database db = new Database();

    Calendar start = Calendar.getInstance();
    Calendar end = Calendar.getInstance();
    private Timeline timeline;
    private CheckBox browserVisible;
    private TextField dataPoints;
    private VerticalLayout mainLayout;
    {
        start.set(2009, 5, 1, 0, 0, 0);
        end.set(2009, 9, 1, 0, 0, 0);
    }

    
    public void init() {
        db.initConnectionPool();
        db.initDatabase(DEFAULT_DATA_POINTS, start.getTimeInMillis(),
                end.getTimeInMillis());

        createUI();

    }

    private void createUI() {
        
        mainLayout = new VerticalLayout();
        Window mainWindow = new Window("Sqltimeline Application", mainLayout);

//        browserVisible = new CheckBox("Browser visible");
//        browserVisible.addListener(new ClickListener() {
//            private static final long serialVersionUID = 1L;
//
//            public void buttonClick(ClickEvent event) {
//                timeline.setBrowserVisible(browserVisible.booleanValue());
//            }
//        });
        
        browserVisible.setImmediate(true);

        dataPoints = new TextField("Data points");
        
//        dataPoints.addValidator(new AbstractStringValidator(
//                "Must be an integer between 0 and 10000") {
//            @Override
//            public boolean isValidString(String value) {
//                try {
//                    int i = Integer.parseInt(value);
//                    if (i <= 0 || i > 10000) {
//                        return false;
//                    }
//
//                    return true;
//                } catch (NumberFormatException e) {
//                    return false;
//                }
//            }
//        });
        dataPoints.setValue(String.valueOf(DEFAULT_DATA_POINTS));

        Button reinitDB = new Button("Create data", new Button.ClickListener() {

            public void buttonClick(ClickEvent event) {
                createDataClicked();

            }
        });

        Table t = new Table();
        timeline = createTimeline();
        setVerticalRange(timeline);

        try {
            tableContainer = new SQLContainer(new TableQuery("cpuload",
                    db.getConnectionPool()));
            t.setContainerDataSource(tableContainer);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        HorizontalLayout settings = new HorizontalLayout();
        settings.addComponent(dataPoints);
        settings.addComponent(reinitDB);
        settings.setComponentAlignment(reinitDB, Alignment.BOTTOM_RIGHT);

        mainLayout.addComponent(settings);
        mainLayout.addComponent(browserVisible);

        mainLayout.addComponent(t);
        mainLayout.addComponent(timeline);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

//        setMainWindow(mainWindow);
    }

    protected void createDataClicked() {
        try {
            dataPoints.validate();
        } catch (InvalidValueException e) {
            return;
        }
        int nrDataPoints = Integer.parseInt((String) dataPoints.getValue());
        db.initDatabase(DEFAULT_DATA_POINTS, start.getTimeInMillis(),
                end.getTimeInMillis());
        tableContainer.refresh();

        // Workaround for Timeline 1.0.0 issue, should really be only
        // timelineContainer.refresh();
        Timeline newTimeline = createTimeline();
        mainLayout.replaceComponent(timeline, newTimeline);
        timeline = newTimeline;
        long intervalInMs = (end.getTimeInMillis() - start.getTimeInMillis())
                / nrDataPoints;

        Date e = end.getTime();
        long endTime = e.getTime();
        // Show the 1000 last data points by default
        long startTime = endTime - 1000 * intervalInMs;
        timeline.setVisibleDateRange(new Date(startTime), new Date(endTime));
        setVerticalRange(timeline);

    }

    protected void setVerticalRange(Timeline timeline2) {
        try {
            timeline.setVerticalAxisRange(0.0f,
                    (float) Math.ceil(db.getMaxValue()));
        } catch (SQLException e1) {
            timeline.setVerticalAxisRange(0.0f, 10.0f);
            e1.printStackTrace();
        }
    }

    private Timeline createTimeline() {
        
        try {
            Timeline timeline = new Timeline();
            timeline.setBrowserVisible(browserVisible.booleanValue());
            timeline.setWidth("100%");
            timelineContainer = new SQLContainer(new TableQuery("cpuload",
                    db.getConnectionPool()));
//            timelineContainer.setDebugMode(true);
            timeline.addGraphDataSource(timelineContainer, "DATETIME", "VALUE");

            return timeline;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}