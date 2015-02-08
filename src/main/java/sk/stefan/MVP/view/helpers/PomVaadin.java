package sk.stefan.MVP.view.helpers;

import sk.stefan.interfaces.PresentationName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import sk.stefan.MVP.model.entity.dao.PublicPerson2;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

public class PomVaadin {

	public static void initCombo(AbstractSelect sel,
			List<? extends PresentationName> list) {
		sel.removeAllItems();

		if (list != null) {
			for (Object item : list) {
				sel.addItem(item);
				sel.setItemCaption(item,
						((PresentationName) item).getPresentationName());
			}
		}
		sel.setValue(null);

	}

	public static void initComboStr(AbstractSelect sel, List<String> list) {
		sel.removeAllItems();

		if (list != null) {
			for (Object item : list) {
				sel.addItem(item);
				sel.setItemCaption(item, item.toString());
			}
		}
		sel.setValue(null);

	}



}
