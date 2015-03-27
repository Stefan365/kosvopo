package sk.stefan.zaklad;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import sk.stefan.MVP.model.entity.dao.VoteOfRole;

public class Foo<T> {

    static class Bara extends VoteOfRole {
    }

    static class Bar<T> {
    }

    static class SubBar extends Bar<Integer> {
    }

    public static void main(String argv[]) {

        ParameterizedType pt = (ParameterizedType) SubBar.class.getGenericSuperclass();
		//System.out.println(pt);
        //System.out.println(Bara.class.getGenericSuperclass());
        String str = Bara.class.getCanonicalName();
//        System.out.println(str);
        try {
            Class<?> cls = Class.forName(str);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

		//System.out.println(Bara.class.getGenericSuperclass());
        //System.out.println(Bara.class.getGenericSuperclass());
        Type[] t = pt.getActualTypeArguments();
        for (int i = 0; i < t.length; i++) {
            //System.out.println(t[i]);
        }
    }
}
