package sk.stefan.MVP.view;

import sk.stefan.MVP.view.components.KomB2;
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
import java.io.File;

public class FilaManagerView extends VerticalLayout implements View {

    /**
     *
     */
    private static final long serialVersionUID = 4285768319781449707L;


    private FilesystemContainer docs;
    private Table docList;
    private KomB2 docView;
    private HorizontalSplitPanel split;

    public FilaManagerView() {

        this.initLayout();
        this.addComponent(NavigationComponent.getNavComp());
    }

    private void initLayout() {

        docs = new FilesystemContainer(new File("C:/Users/User/Documents/KNIHA O DRAKOVI"));
        docList = new Table("Documnets", docs);
        docView = new KomB2();

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
        this.addComponent(NavigationComponent.getNavComp());
    }

}