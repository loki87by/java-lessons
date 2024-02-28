import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static HashMap<Integer, Object> tasks = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);
    static double userInput;
    static double secondInput;
    static String strParamInput;
    static int idInput;
    static TaskManager inMemoryTaskManager = Managers.getDefault();
    static HistoryManager historyManager = Managers.getDefaultHistory();

    public static void main(String[] args) {
        inMemoryTaskManager.init(tasks);
        setBasicTasks();
        printMainMenu();
        userInput = scanner.nextDouble();
        while (userInput != 0) {
            if (userInput >= 1 && userInput < 2) {
                inMemoryTaskManager.getAllTasks();
                printMainMenu();
                userInput = scanner.nextDouble();
            } else if (userInput >= 2 && userInput < 3) {
                if (isInteger(userInput)) {
                    printAddMenu();
                } else {
                    addMenu();
                }
            } else if (userInput >= 3 && userInput < 4) {
                if (isInteger(userInput)) {
                    printCorrectMenu();
                } else {
                    correctMenu();
                }
            } else if (userInput >= 4 && userInput < 5) {
                if (isInteger(userInput)) {
                    printDeleteMenu();
                } else {
                    deleteMenu();
                }
            } else if (userInput == 5) {
                inMemoryTaskManager.history();
                printMainMenu();
                userInput = scanner.nextDouble();
            } else if (userInput == 6) {
                showNewHistory();
                printMainMenu();
                userInput = scanner.nextDouble();
            }
        }
        System.out.println("Программа завершена");
    }

    //default operations:
    private static void setBasicTasks() {
        inMemoryTaskManager.setTask("генеральная уборка", 0);
        inMemoryTaskManager.setSubtask("пнуть робот-пылесос", 1);
        inMemoryTaskManager.setSubtask("включить стиралку", 1);
        inMemoryTaskManager.setEpic("выспаться");
        inMemoryTaskManager.setTask("лечь в кровать", 4);
        inMemoryTaskManager.setSubtask("отложить телефон", 5);
        inMemoryTaskManager.setSubtask("отдохнуть после проделанной работы", 1);
        inMemoryTaskManager.setSubtask("я сказал отложить телефон!", 5);
        inMemoryTaskManager.setTask("закрыть глаза", 4);
        inMemoryTaskManager.setSubtask("оба!", 9);
    }
    private static void showNewHistory() {
        HashMap<Integer, Subtask> history = historyManager.getHistory();
        for (int key : history.keySet()) {
            System.out.println((history.size()-key)+". "+history.get(key));
        }
    }

    //checkers functions:
    private static boolean isInteger(double ui) {
        return Math.ceil(ui) == ui;
    }

    //userInput handlers:
    // *save variables from inputs
    private static void getNameAndIdFromInput(String additional) {
        System.out.println("Введите название");
        strParamInput = scanner.next();
        System.out.println("введите id родительской задачи" + (!additional.isEmpty() ? additional : ""));
        idInput = scanner.nextInt();
    }

    private static void getIdAndStatusFromInput(String additional) {
        System.out.println("введите id");
        idInput = scanner.nextInt();
        System.out.println("Введите " + additional);
        strParamInput = scanner.next();
    }

    private static void getNameFromInput() {
        System.out.println("Введите название эпика");
        strParamInput = scanner.next();
    }

    private static void getIdFromInput() {
        System.out.println("введите id");
        idInput = scanner.nextInt();
    }

    // *print menu from inputs
    private static void printMainMenu() {
        System.out.println("Чё те надо?");
        System.out.println("1 - показать трекер");
        System.out.println("2 - добавить");
        System.out.println("3 - корректировать");
        System.out.println("4 - удалить");
        System.out.println("5 - история");
        System.out.println("6 - история new");
        System.out.println("0 - выйти");
    }

    private static void printAddMenu() {
        System.out.println("Что плюсуем?");
        System.out.println("1 - Подзадачу");
        System.out.println("2 - Задачу");
        System.out.println("3 - Эпик");
        secondInput = scanner.nextDouble();
        addMenu();
    }

    private static void printCorrectMenu() {
        System.out.println("Что правим?");
        System.out.println("1 - Статус");
        System.out.println("2 - Заголовок");
        secondInput = scanner.nextDouble();
        correctMenu();
    }

    private static void printDeleteMenu() {
        System.out.println("Что удаляем?");
        System.out.println("1 - Запись");
        System.out.println("2 - Всё");
        secondInput = scanner.nextDouble();
        deleteMenu();
    }

    //submenu:
    private static void addMenu() {
        if (userInput == 2.1 || (Math.ceil(userInput) == 2 && secondInput == 1)) {
            getNameAndIdFromInput("");
            inMemoryTaskManager.setSubtask(strParamInput, idInput);
        } else if (userInput == 2.2 || (Math.ceil(userInput) == 2 && secondInput == 2)) {
            getNameAndIdFromInput(" или 0 для новой задачи");
            inMemoryTaskManager.setTask(strParamInput, idInput);
        } else if (userInput == 2.3 || (Math.ceil(userInput) == 2 && secondInput == 3)) {
            getNameFromInput();
            inMemoryTaskManager.setEpic(strParamInput);
        } else {
            System.out.println("что-то введено не корректно");
        }
        printMainMenu();
        userInput = scanner.nextDouble();
    }

    private static void correctMenu() {
        if (userInput == 3.1 || (Math.ceil(userInput) == 3 && secondInput == 1)) {
            getIdAndStatusFromInput("новый статус");
            inMemoryTaskManager.changeStatus(idInput, strParamInput);
        } else if (userInput == 3.2 || (Math.ceil(userInput) == 3 && secondInput == 2)) {
            getIdAndStatusFromInput("новое имя");
            inMemoryTaskManager.rename(idInput, strParamInput);
        } else {
            System.out.println("что-то введено не корректно");
        }
        printMainMenu();
        userInput = scanner.nextDouble();
    }

    private static void deleteMenu() {
        if (userInput == 4.1 || (Math.ceil(userInput) == 4 && secondInput == 1)) {
            getIdFromInput();
            inMemoryTaskManager.removeTask(idInput);
        } else if (userInput == 4.2 || (Math.ceil(userInput) == 4 && secondInput == 2)) {
            inMemoryTaskManager.deleteAllTasks();
        } else {
            System.out.println("что-то введено не корректно");
        }
        printMainMenu();
        userInput = scanner.nextDouble();
    }
}
