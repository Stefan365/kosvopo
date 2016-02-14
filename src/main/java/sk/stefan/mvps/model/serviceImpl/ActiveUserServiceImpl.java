package sk.stefan.mvps.model.serviceImpl;

import org.springframework.stereotype.Service;

import com.vaadin.ui.UI;

import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.service.ActiveUserService;

/**
 * Created by sveres on 2/13/16.
 */
@Service
public class ActiveUserServiceImpl implements ActiveUserService {

	@Override
	public A_User getActualUser() {
		return UI.getCurrent().getSession().getAttribute(A_User.class);
	}
}
