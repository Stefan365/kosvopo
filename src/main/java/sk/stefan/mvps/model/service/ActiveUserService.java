package sk.stefan.mvps.model.service;

import sk.stefan.mvps.model.entity.A_User;

/**
 * Lahka servisa, ktora sa stara LEN o identifikaciu aktualneho usera.
 * Pouziva sa vsade, pretoze ukladame udalosti.
 *
 * Created by sveres on 2/13/16.
 */
public interface ActiveUserService {

	public A_User getActualUser();
}
