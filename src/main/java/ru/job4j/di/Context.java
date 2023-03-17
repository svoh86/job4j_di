package ru.job4j.di;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * В этом классе будем регистрировать классы,
 * а он будет отдавать нам проинициализированные объекты.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class Context {
    /**
     * Карта с объектами. В ней мы будем хранить проинициализированные объекты.
     */
    private Map<String, Object> els = new HashMap<>();

    /**
     * Метод регистрации классов.
     * cl.getDeclaredConstructors() - Сначала нужно получить все конструкторы класса.
     * Если их больше 1, то мы не знаем как загружать этот класс и кидаем исключение.
     * <p>
     * Когда мы нашли конструктор, мы собираем аргументы этого конструктора - con.getParameterTypes(),
     * ищем уже зарегистрированные объекты, чтобы внедрить их в конструктор.
     * <p>
     * Последний этап - это создание объекта и добавление его в карту.
     * els.put(cl.getCanonicalName(), con.newInstance(args.toArray()));
     *
     * @param cl Class
     */
    public void reg(Class cl) {
        Constructor[] constructors = cl.getDeclaredConstructors();
        if (constructors.length > 1) {
            throw new IllegalStateException("Class has multiple constructors : " + cl.getCanonicalName());
        }
        Constructor con = constructors[0];
        List<Object> args = new ArrayList<>();
        for (Class arg : con.getParameterTypes()) {
            if (!els.containsKey(arg.getCanonicalName())) {
                throw new IllegalStateException("Object doesn't found in context : " + arg.getCanonicalName());
            }
            args.add(els.get(arg.getCanonicalName()));
        }
        try {
            els.put(cl.getCanonicalName(), con.newInstance(args.toArray()));
        } catch (Exception e) {
            throw new IllegalStateException("Couldn't create an instance of : " + cl.getCanonicalName(), e);
        }
    }

    /**
     * Метод get возвращает проинициализированный объект.
     * inst.getCanonicalName() - Через рефлексию можно получить имя класса.
     *
     * @param inst Class
     * @param <T>  общий параметр
     * @return проинициализированный объект
     */
    public <T> T get(Class<T> inst) {
        return (T) els.get(inst.getCanonicalName());
    }

}
