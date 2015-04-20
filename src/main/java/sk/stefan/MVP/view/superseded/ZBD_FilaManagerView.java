package sk.stefan.MVP.view.superseded;

import sk.stefan.MVP.view.components.ZBD_KomB2;
import sk.stefan.MVP.view.components.NavigationComponent;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.FilesystemContainer;
import com.vaadin.data.util.TextFileProperty;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import java.io.File;
import sk.stefan.MVP.model.entity.dao.A_User;
import sk.stefan.MVP.model.service.SecurityService;
import sk.stefan.MVP.model.serviceImpl.SecurityServiceImpl;

public class ZBD_FilaManagerView extends VerticalLayout implements View {

    /**
     *
     */
    private static final long serialVersionUID = 4285768319781449707L;

    private A_User user;
    private SecurityService securityService;
    
    private FilesystemContainer docs;
    private Table docList;
    private ZBD_KomB2 docView;
    private HorizontalSplitPanel split;

    public ZBD_FilaManagerView() {

        securityService = new SecurityServiceImpl();

        this.initLayout();
        this.addComponent(NavigationComponent.createNavigationComponent());
    }

    private void initLayout() {

        docs = new FilesystemContainer(new File("C:/Users/User/Documents/KNIHA O DRAKOVI"));
        docList = new Table("Documnets", docs);
        docView = new ZBD_KomB2();

		// setContent(docList);
        // final VerticalLayout layout = new VerticalLayout();
        split = new HorizontalSplitPanel();
        this.addComponent(split);

        split.addComponent(docList);
        split.addComponent(docView);

		//this.addComponent(docList);
        //this.addComponent(docView);
        docList.addValueChangeListener(new ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
                docView.setPropertyDataSource(new TextFileProperty((File) event
                        .getProperty().getValue()));
                docView.setVisible(true);
            }

        });

        docList.setImmediate(true);
        docList.setSelectable(true);
        docList.setSizeFull();

    }

    @Override
    public void enter(ViewChangeEvent event) {
        
        user = securityService.getCurrentUser();
        if (user != null) {
            //do nothing
        } else {
            UI.getCurrent().getNavigator().navigateTo("V2_EnterView");
        }
    }

}
