import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static HashMap<Integer, Object> tasks = new HashMap<>();
    static Scanner scanner = new Scanner();

    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.init(tasks);
        manager.setTask("bla", 0);
        manager.setSubtask("bla bla", 1);
        manager.setSubtask("bla bla bla", 1);
        manager.setEpic("xyzEpic");
        manager.setTask("xyz", 4);
        manager.setSubtask("xyz xyz", 5);
        manager.setSubtask("bla bla bla bla", 1);
        manager.rename(1, "DONE");
        //manager.getAllTasks();
        manager.removeTask(5);
        manager.getAllTasks();
    }

    private void printMainMenu() {
        System.out.println("Чё те надо?");
        System.out.println("1 - показать трекер"); //manager.getAllTasks();
        System.out.println("2 - добавить"); //printAddMenu();
        System.out.println("3 - корректировать"); //printCorrectMenu()
        System.out.println("4 - удалить"); //printDeleteMenu()
        System.out.println("0 - выйти");
    }

    private void printAddMenu() {
        System.out.println("Что плюсуем?");
        System.out.println("1 - Подзадачу"); //manager.setSubtask(name, id);
        System.out.println("2 - Задачу"); //manager.setTask(name, id||0);
        System.out.println("3 - Эпик"); //manager.setEpic(name);
    }

    private void printCorrectMenu() {
        System.out.println("Что правим?");
        System.out.println("1 - Статус"); //manager.changeStatus(id, new_status);
        System.out.println("2 - Заголовок"); //manager.rename(id, new_name);
    }

    private void printDeleteMenu() {
        System.out.println("Что удаляем?");
        System.out.println("1 - Запись"); //manager.removeTask(id);
        System.out.println("2 - Всё"); //manager.deleteAllTasks();
    }

}
