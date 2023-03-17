package ru.job4j.di;

/**
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        Context context = new Context();
        context.reg(Store.class);
        context.reg(ConsoleInput.class);
        context.reg(StartUI.class);

        StartUI ui = context.get(StartUI.class);
        ConsoleInput consoleInput = context.get(ConsoleInput.class);

        ui.add("Petr Arsentev");
        ui.add("Ivan ivanov");
        ui.print();
        consoleInput.askStr("What`s up?");
    }
}
