package ru.job4j.di;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class SpringDI {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("ru.job4j.di");
        context.refresh();

        Store store = context.getBean(Store.class);
        store.add("Petr Arsentev");
        Store another = context.getBean(Store.class);
        another.getAll().forEach(System.out::println);

        StartUI ui = context.getBean(StartUI.class);
        StartUI ui2 = context.getBean(StartUI.class);
        ui.add("Petr Arsentev from ui");
        ui2.add("Ivan ivanov from ui2");
        ui.print();
        ui2.print();
        ConsoleInput consoleInput = context.getBean(ConsoleInput.class);
        consoleInput.askStr("What`s up?");
    }
}
