package sk.stefan.MVP.view.components;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.BaseTheme;

public class NavigationComponent extends HorizontalLayout {

    /**
     *
     */
    private static final long serialVersionUID = 8811699550804144740L;

    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17;
    HorizontalLayout hl;

    Navigator navigator;

    public NavigationComponent(Navigator nav) {

        this.navigator = nav;

        b1 = new Button("login",
                new Button.ClickListener() {
                    private static final long serialVersionUID = 7834517499543650204L;

                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("login");
                    }
                });

        b2 = new Button("vstupny", (ClickEvent event) -> {
            navigator.navigateTo("vstupny");
        });

        b3 = new Button("druhy", new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                navigator.navigateTo("druhy");
            }
        });

        b4 = new Button("homo",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("homo");
                    }
                });

        b5 = new Button("abook",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("addressbook");
                    }
                });

        b6 = new Button("fila",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("filamanager");
                    }
                });

        b7 = new Button("kos1",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("kos1");
                    }
                });

        b8 = new Button("kos2",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("kos2");
                    }
                });

        b9 = new Button("kos3",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("kos3");
                    }
                });

        b10 = new Button("kos4",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("kos4");
                    }
                });

        b11 = new Button("kos5",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("kos5");
                    }
                });

        b12 = new Button("kos6",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("kos6");
                    }
                });

        b13 = new Button("download",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("download");
                    }
                });

        b14 = new Button("kos8",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("kos8");
                    }
                });

        b15 = new Button("page1",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("page1");
                    }
                });

        b16 = new Button("page2",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("page2");
                    }
                });

        b17 = new Button("welcome",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("welcome");
                    }
                });

        b1.setStyleName(BaseTheme.BUTTON_LINK);
        b2.setStyleName(BaseTheme.BUTTON_LINK);
        b3.setStyleName(BaseTheme.BUTTON_LINK);
        b4.setStyleName(BaseTheme.BUTTON_LINK);
        b5.setStyleName(BaseTheme.BUTTON_LINK);
        b6.setStyleName(BaseTheme.BUTTON_LINK);
        b7.setStyleName(BaseTheme.BUTTON_LINK);
        b8.setStyleName(BaseTheme.BUTTON_LINK);
        b9.setStyleName(BaseTheme.BUTTON_LINK);
        b10.setStyleName(BaseTheme.BUTTON_LINK);
        b11.setStyleName(BaseTheme.BUTTON_LINK);
        b12.setStyleName(BaseTheme.BUTTON_LINK);
        b13.setStyleName(BaseTheme.BUTTON_LINK);
        b14.setStyleName(BaseTheme.BUTTON_LINK);
        b15.setStyleName(BaseTheme.BUTTON_LINK);
        b16.setStyleName(BaseTheme.BUTTON_LINK);
        b17.setStyleName(BaseTheme.BUTTON_LINK);

		//hl = new HorizontalLayout();
        this.setSpacing(true);
        this.setMargin(true);

        this.addComponent(b1);
        this.addComponent(b2);
        this.addComponent(b3);
        this.addComponent(b4);
        this.addComponent(b5);
        this.addComponent(b6);
        this.addComponent(b7);
        this.addComponent(b8);
        this.addComponent(b9);
        this.addComponent(b10);
        this.addComponent(b11);
        this.addComponent(b12);
        this.addComponent(b13);
        this.addComponent(b14);
        this.addComponent(b15);
        this.addComponent(b16);
        this.addComponent(b17);

    }
}
