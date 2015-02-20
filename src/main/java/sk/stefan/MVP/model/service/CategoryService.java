package sk.stefan.MVP.model.service;

import java.util.List;
import java.util.Map;
import sk.stefan.MVP.model.entity.dao.todo.Category;
import sk.stefan.MVP.model.entity.dao.todo.User_log;

/**
 * Rozhrazeni sluzby pro praci s kategroii
 *
 * @author Marek Svarc
 */
public interface CategoryService {

    /**
     * Vrati seznam vsech kategorii daneho uzivatele.
     * @param user
     * @return 
     */
    public List<Category> findAllCategoriesByUser(User_log user);

    /**
     * Prida kategorii do databaze
     * @param category
     */
    public void addCategory(Category category);

    /**
     * Zmeni vlstnosti kategorie v databazi
     * @param category
     */
    public void modifyCategory(Category category);

    /**
     * Odstrani kategorii z databaze
     * @param category
     */
    public void deleteCategory(Category category);

    /**
     * ziska Idcka vsech kategorii prislouchajicich danemu uzivateli
     * @param user
     * @return 
     */
    Map<String, Long> findAllCategoriesIdByUser(User_log user);
}
