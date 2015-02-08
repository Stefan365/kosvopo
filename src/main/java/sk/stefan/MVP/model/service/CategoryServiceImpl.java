package sk.stefan.MVP.model.service;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import sk.stefan.DBconnection.todo.DBAdapter;
import sk.stefan.MVP.model.entity.dao.todo.Category;
import sk.stefan.MVP.model.entity.dao.todo.User_log;

/**
 * Implementace rozhrazeni sluzby pro praci s kategroii
 *
 * @author Marek Svarc
 */
public class CategoryServiceImpl implements CategoryService {

    private Logger logger = Logger.getLogger(CategoryServiceImpl.class);

    private DBAdapter dbAdapter;

    public CategoryServiceImpl() {
        dbAdapter = new DBAdapter();
    }

    @Override
    public List<Category> findAllCategoriesByUser(User_log user) {
        return dbAdapter.findAllCategoriesByUser(user);
    }

    @Override
    public void addCategory(Category category) {
        dbAdapter.addCategory(category);
    }

    @Override
    public void modifyCategory(Category category) {
        dbAdapter.modifyCategory(category);
    }

    @Override
    public void deleteCategory(Category category) {
        dbAdapter.deleteCategory(category);
    }

    //stefan: potrebuji to kvuli praci s comboboxem:
    @Override
    public Map<String, Long> findAllCategoriesIdByUser(User_log user) {
        List<Category> listCat = this.findAllCategoriesByUser(user);
        Map<String, Long> map = new HashMap<>();
        for (Category c : listCat) {
            map.put(c.getRepresentativeName(), c.getId());
        }
        return map;
    }

}
