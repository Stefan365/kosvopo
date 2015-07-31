/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.panels;

import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import sk.stefan.mvps.view.components.MyTimeline;

/**
 *
 * @author stefan
 */
public class MyTimelinePanel extends Panel {

    
    private static final long serialVersionUID = 1L;

    private MyTimeline timeline;

    public MyTimelinePanel(MyTimeline timeline) {
        
        this.setSizeFull();
        this.setStyleName("timelinePanel");
        this.timeline = timeline;
        VerticalLayout vl = new VerticalLayout();
        vl.addComponent(timeline);
//        vl.setSizeFull();
        this.setContent(vl);
    }

    public MyTimeline getTimeline() {
        return timeline;
    }

}
