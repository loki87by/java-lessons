import java.util.HashMap;

public class Main {
    static HashMap<Integer, Object> tasks = new HashMap<>();
    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.init(tasks);

        //manager.getAllTasks();
        //manager.getById(456);
        String[] myArray = {"new name", "A vacation in a foreign land", "1", "2"};
        String[] newArray = {"old name", "Uncle Sam does the best he can", "3", "3", "8"};
        //String[] lastArray = {"now name", "You're in the army now", "2", "1"};
        manager.setTask(myArray);
        manager.setTask(newArray);
        //manager.getAllTasks();
        manager.removeTask(7);
        //manager.updateTask(4, lastArray);
        //manager.getAllTasks();
        //lastArray[1] = "oh-oo-oh you're in the army...";
        //manager.updateTask(4, lastArray);
        //System.out.println("upd");
        //manager.getAllTasks();
        //lastArray[1] = "...NOW!";
        //manager.updateTask(4,  lastArray);
        manager.updateEpic(3, 11, myArray);
        manager.updateEpic(9, 10, newArray);
        manager.getAllTasks();
    }

    private void printMainMenu() {
        System.out.println("Чё те надо?");
        System.out.println("1 - показать трекер");
        System.out.println("2 - корректировать");
        System.out.println("3 - удалить");
        System.out.println("4 - изменить статус");
        System.out.println("0 - выйти");
    }
    private void printStatusMenu() {
        System.out.println("Чё те надо?");
        System.out.println("1 - показать трекер");
        System.out.println("2 - корректировать");
        System.out.println("3 - удалить");
    }

}
