package sk.stefan.interfaces;

import java.util.List;

public interface MyRepo<T> {

	// 1.
	public List<T> findAll();

	// 2.
	public T findOne(Integer id);

	// 3.
	public List<T> findByParam(String paramName, String paramValue);

	// 4.
        /**
         * Zahrna update i vytvorenie novej entity.
         */
	public T save(T ent);

	// 5.
	public boolean delete(T ent);
	
	// 6.
	public boolean copy(T entFrom, T entTo);


}
