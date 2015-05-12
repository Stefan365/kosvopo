package sk.stefan.MVP.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;
import org.apache.log4j.Logger;
import sk.stefan.MVP.view.components.MyTimeline;

/**
 * 
 */
public class V2_EnterView extends VerticalLayout implements View {

    private static final Logger log = Logger.getLogger(V2_EnterView.class);

    private static final long serialVersionUID = -2001141270398193257L;
    
    private final MyTimeline timeline;


    /**
     * 
     */
    public V2_EnterView() {

        this.setMargin(true);
        this.setSpacing(true);
        
        this.initlayout();

        this.timeline = new MyTimeline();
//        this.addComponent(timeline);
        
    }

    private void initlayout() {

        Resource res = new ThemeResource("images/demokracia_ciel.jpg");
        Image image = new Image("budovanie demokracie", res);
        this.addComponent(image);
    }

    @Override
    public void enter(ViewChangeEvent event) {

    }

}
