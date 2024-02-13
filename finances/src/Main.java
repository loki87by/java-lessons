import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int currentYear = LocalDate.now().getYear();
        printMenu();
        int userInput = scanner.nextInt();
        int yearInput;
        int monthInput;
        MonthlyReport monthlyReport = null;

        String path = "finances/src/assets/";

        while (userInput != 0) {
            if (userInput == 1) {
                System.out.println("Введите год");
                yearInput = scanner.nextInt();
                while (yearInput < 1987 || yearInput >= currentYear) {
                    System.out.println("Введите год с 1987 по 2023");
                    yearInput = scanner.nextInt();
                }
                System.out.println("Введите месяц с которого предоставлять отчёт");
                monthInput = scanner.nextInt();
                while (monthInput <= 0 || monthInput > 10) {
                    System.out.println("Введите месяц с 1 по 10");
                    monthInput = scanner.nextInt();
                }
                MonthReportRead monthReportRead = new MonthReportRead(path, yearInput, monthInput);
                monthlyReport = new MonthlyReport();
                monthlyReport.setData(monthReportRead.monthData);
                monthlyReport.getData();
            } else if (userInput == 2) {
                System.out.println("Считать годовой отчёт");
            } else if (userInput == 3) {
                System.out.println("Сверить отчёты");
            } else if (userInput == 4) {
                System.out.println("Вывести информацию о всех месячных отчётах");
            } else if (userInput == 5) {
                System.out.println("Вывести информацию о годовом отчёте");
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
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("0 - Выйти");
    }

    public static class MonthlyReport {
        HashMap<Integer, MonthReportRead.MonthData> monthData = new HashMap<>();
        ArrayList<String> isExpencesNames = new ArrayList<>();
        ArrayList<String> isntExpencesNames = new ArrayList<>();
        ArrayList<Integer> isntExpencesSums = new ArrayList<>();
        ArrayList<Integer> isExpencesSums = new ArrayList<>();

        void setData(HashMap<Integer, MonthReportRead.MonthData> md) {
            monthData = md;
            topSale(md);
            topExpence(md);
        }

        void getData() {
            if (!isExpencesNames.isEmpty() && !isntExpencesNames.isEmpty() && !isntExpencesSums.isEmpty() && !isExpencesSums.isEmpty()) {
                for (int i = 1; i <= monthData.size(); i++) {
                    MonthReportRead.MonthData data = monthData.get(i);
                    System.out.print("В " + data.monthName + " больше всего доходов по товару '" + isntExpencesNames.get(i-1) + "' на сумму: " + isntExpencesSums.get(i-1));
                    System.out.println(", а расходов по товару '" + isExpencesNames.get(i-1) + "' на сумму: " + isExpencesSums.get(i-1));
                }
            }
        }

        void topSale(HashMap<Integer, MonthReportRead.MonthData> monthData) {
            for (int i = 1; i <= monthData.size(); i++) {
                ArrayList<Integer> summs = new ArrayList<>();
                MonthReportRead.MonthData data = monthData.get(i);
                for (int j = 0; j < data.getSize(); j++) {
                    if (!data.expences.get(j)) {
                        int sum = data.quantitys.get(j) * data.coasts.get(j);
                        summs.add(sum);
                    } else {
                        summs.add(0);
                    }
                }
                int max = Collections.max(summs);
                int indexOfMax = summs.indexOf(max);
                String nameOfMax = monthData.get(i).items.get(indexOfMax);
                isntExpencesNames.add(nameOfMax);
                isntExpencesSums.add(max);
                //System.out.println("Больше всего доходов по товару '" + nameOfMax + "' на сумму: " + max);
            }
        }

        void topExpence(HashMap<Integer, MonthReportRead.MonthData> monthData) {
            for (int i = 1; i <= monthData.size(); i++) {
                ArrayList<Integer> summs = new ArrayList<>();
                MonthReportRead.MonthData data = monthData.get(i);
                for (int j = 0; j < data.getSize(); j++) {
                    if (data.expences.get(j)) {
                        int sum = data.quantitys.get(j) * data.coasts.get(j);
                        summs.add(sum);
                    } else {
                        summs.add(0);
                    }
                }
                int max = Collections.max(summs);
                int indexOfMax = summs.indexOf(max);
                String nameOfMax = monthData.get(i).items.get(indexOfMax);
                isExpencesNames.add(nameOfMax);
                isExpencesSums.add(max);
                //System.out.println("Больше всего расходов по товару '" + nameOfMax + "' на сумму: " + max);
            }
        }
    }
}
