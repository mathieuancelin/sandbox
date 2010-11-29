package com.sample.osgi.cditests;

import java.util.Iterator;

/**
 *
 * @author mathieuancelin
 */
public class Services<T> implements Iterable<T> {

//    private final Class serviceClass;

//    private List<T> services = new ArrayList<T>();

    public void getClassType() {
//        Type type = getClass();
//        System.out.println(type);
//        ParameterizedType pType = ((ParameterizedType) type);
//        Type argument = pType.getActualTypeArguments()[0];
//        System.out.println(argument);
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

//    public class TypeHelper {
//        public void getModelClass(Class<?> clazz) {
//            ParameterizedType type = (ParameterizedType) clazz;
//
//            System.out.println(((ParameterizedType) clazz).getActualTypeArguments()[0]);
//        }
//    }
}
