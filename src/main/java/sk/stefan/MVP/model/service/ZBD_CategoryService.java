package sk.stefan.MVP.model.service;

import java.util.List;
import java.util.Map;
import sk.stefan.MVP.model.entity.dao.todo.TBD_Category;
import sk.stefan.MVP.model.entity.dao.todo.TBD_User;

/**
 * Rozhrazeni sluzby pro praci s kategroii
 *
 * @author Marek Svarc
 */
public interface ZBD_CategoryService {

    /**
     * Vrati seznam vsech kategorii daneho uzivatele.
     * @param user
     * @return 
     */
    public List<TBD_Category> findAllCategoriesByUser(TBD_User user);

    /**
     * Prida kategorii do databaze
     * @param category
     */
    public void addCategory(TBD_Category category);

    /**
     * Zmeni vlstnosti kategorie v databazi
     * @param category
     */
    public void modifyCategory(TBD_Category category);

    /**
     * Odstrani kategorii z databaze
     * @param category
     */
    public void deleteCategory(TBD_Category category);

    /**
     * ziska Idcka vsech kategorii prislouchajicich danemu uzivateli
     * @param user
     * @return 
     */
    Map<String, Long> findAllCategoriesIdByUser(TBD_User user);
}
