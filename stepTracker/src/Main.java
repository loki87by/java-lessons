import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StepTracker stepTracker = new StepTracker();
        printMenu();
        int userInput = scanner.nextInt();
        int tmpInput;
        int dayInput;
        int stepsInput;

        while (userInput != 0) {
            if ((userInput == 1) || (userInput == 2)) {
                System.out.println("Выбери месяц");
                tmpInput = scanner.nextInt();
                while ((tmpInput < 1) || (tmpInput > 12)) {
                    System.out.println("Попробуй еще раз");
                    tmpInput = scanner.nextInt();
                }
                if (userInput == 2) {
                    stepTracker.monthData.get(--tmpInput).getStatistic();
                } else {
                    System.out.println("Введи дату(от 1 до 30)");
                    dayInput = scanner.nextInt();
                    while ((dayInput < 1) || (dayInput > 30)) {
                        System.out.println("Попробуй еще раз");
                        dayInput = scanner.nextInt();
                    }
                    System.out.println("Вбей сколько прошел");
                    stepsInput = scanner.nextInt();
                    while (stepsInput < 0) {
                        System.out.println("Я понимаю, что ты лентяй, но не на столько, давай положительное число");
                        stepsInput = scanner.nextInt();
                    }
                    stepTracker.monthData.get(--tmpInput).setSteps(--dayInput, stepsInput);
                }
            } else if (userInput == 3) {
                System.out.println("Введи количество");
                tmpInput = scanner.nextInt();
                stepTracker.ChangeTarget(tmpInput);
            } else {
                System.out.println("Херню ввёл, попробуем еще раз");
            }

            printMenu();
            userInput = scanner.nextInt();
        }
        System.out.println("Программа завершена");
    }

    private static void printMenu() {
        System.out.println("Чё хош? (Введи цифру)");
        System.out.println("1 - Вбить сколько протопал");
        System.out.println("2 - Позырить статистику");
        System.out.println("3 - Изменить целевое количество");
        System.out.println("0 - Выйти");
    }
}