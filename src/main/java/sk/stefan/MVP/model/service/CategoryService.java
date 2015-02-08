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

    /** Vrati seznam vsech kategorii daneho uzivatele. */
    public List<Category> findAllCategoriesByUser(User_log user);
    
    /** Prida kategorii do databaze */
    public void addCategory(Category category);

    /** Zmeni vlstnosti kategorie v databazi */
    public void modifyCategory(Category category);

    /** Odstrani kategorii z databaze */
    public void deleteCategory(Category category);

    /** ziska Idcka vsech kategorii prislouchajicich danemu uzivateli*/
	Map<String, Long> findAllCategoriesIdByUser(User_log user);
}
