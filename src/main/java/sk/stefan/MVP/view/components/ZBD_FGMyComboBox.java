package sk.stefan.MVP.view.components;

import sk.stefan.interfaces.PresentationName;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

import sk.stefan.MVP.view.converters.ZBD_MyBooleanConverter;
import sk.stefan.utils.ToolsDao;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.converter.StringToBooleanConverter;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;

public class ZBD_FGMyComboBox<T, E> extends ComboBox {

    private Item item; // aktualne vybrany objekt zo SQLcontaineru
    private Property prop;

    private String fn; // field name
    private TextField tf; // pomocny field, nebude viditelny. kvoli konvertoru,
    // ktory sa obtiazne implementuje na ComboBox.
    private FieldGroup fg;
    private Class<?> clsT;
    private Class<?> clsE;
    private Type typE;
    private String propertyETypeName;
    private List<E> listEnt;

    /**
     *
     * Konstruktor
     */
    public ZBD_FGMyComboBox(Class<?> clsT, Class<?> clsE, FieldGroup fg, String fn, List<E> listEnt) {
        super(fn);
        this.fn = fn;

        this.clsT = clsT;
        this.clsE = clsE;
        this.tf = new TextField();

        this.listEnt = listEnt;
        this.fg = fg;
        this.involveFg();
        try {
            typE = (Class<?>) clsE;
            propertyETypeName = ((Class<?>) typE).getCanonicalName();
            this.initConverter();
            this.initListener();
            this.initCombo();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }

    //3.
    @SuppressWarnings("unchecked")
    public void setItemDataSource(Item item) {
        //this.item = hlasovaniaPoslTB.getItem(hlasId));;
        this.item = item;
        fg.setItemDataSource(item);

    }

    /**
     * Zapletie BFG do celkovej struktury:  
	 *
     */
    private void involveFg() {
        this.fg.bind(tf, fn);
        this.setValue(tf.getValue());
    }

    // 1.
    /**
     * Vytvori pole pre sql.Date a zviaze ho s BFG.
     *
     * @throws IllegalAccessException
     * @throws InstantiationException
     *
     */
    // public ComboBox bindComboBoxField(String fn, BeanFieldGroup<T> bfg, List<E> listEnt)
    public void initCombo() {

        if (listEnt != null) {
            for (E et : listEnt) {
                this.addItem(et);
                if (clsE.toString().contains("java.lang.String")
                        || clsE.toString().contains("java.lang.Boolean")) {
                    this.setItemCaption(et, et.toString());
                } else {
                    this.setItemCaption(et,
                            ((PresentationName) et).getPresentationName());
                }
            }
        }
        this.initComboVal();
        this.setImmediate(true);
        this.setNewItemsAllowed(false);
        this.setTextInputAllowed(true);
        this.setNullSelectionAllowed(false);
        this.setVisible(true);

    }

    //2.
    /**
     * Inicializacia konvertoru pre pomocny field ()
	 *
     */
    public void initConverter() {

        switch (propertyETypeName) {
            case "java.lang.Boolean":
                //tf.setConverter(new MyBooleanConverter());
                tf.setConverter(new StringToBooleanConverter());
                break;
            default:
			// prirad vhodny konvertor podla typu entity:
            // pre kazdy typ entity tu bude jeden konvertor.
        }

    }

    //4.
    /**
     * Inicializuje value change listener COmboBoxu
     *
     *
     */
    public void initListener() {
        this.addValueChangeListener(new ValueChangeListener() {

            @Override
            public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
                // TODO Auto-generated method stub
                @SuppressWarnings("unchecked")
                E val = (E) event.getProperty().getValue();

                switch (propertyETypeName) {
                    case "java.lang.Boolean":
                        //String s = (tf.getConverter()).convertToPresentation((Boolean) val, String.class, new Locale("EN"));
                        String s = ZBD_MyBooleanConverter.convertToPresentation((Boolean) val);
                        tf.setValue(s);
                        break;
                    default:
					// prirad vhodny konvertor podla typu entity:
                    // pre kazdy typ entity tu bude jeden konvertor.
                }
            }

        });

    }

    //5.
    /**
     * Sluzi na nastavenie hodnoty v comboBoxe pri zobrazeni formulara.
     *
     *
     */
    private void initComboVal() {
        String methodName = ToolsDao.getG_SetterName(fn, "get");

        if (fg.getItemDataSource() != null) {
            item = fg.getItemDataSource();//.getItemProperty(id); //property
            prop = item.getItemProperty(fn);
        } else {
            return;
        }
//		item = hlasovaniaPoslTB.getValue();
//
//		if (item != null) {
//			fg.setItemDataSource(hlasovaniaPoslTB
//					.getItem(item));
//			hlasovaniFL.setVisible(true);
//
//			fg.setEnabled(false);
//			editBT1.setEnabled(true);
//			saveBT1.setEnabled(false);
//		}

        if (item != null) {
            System.out.println("KAROLKO VITAZI!1");
//			try {
//				Object item = event.getProperty().getValue();
////				Item item = table.getItem(item);
//
//				Method method = clsT.getDeclaredMethod(methodName);
//				@SuppressWarnings("unchecked")
//				E newValue = (E) method.invoke(ent);
//				System.out.println("KAROLKO VITAZI!2" + newValue.toString());
//				this.setValue(newValue);
//
//			} catch (IllegalAccessException | IllegalArgumentException
//					| InvocationTargetException | NoSuchMethodException
//					| SecurityException e) {
//				e.printStackTrace();
//			}
        }
    }

}
