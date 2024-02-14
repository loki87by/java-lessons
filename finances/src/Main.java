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
        YearlyReport yearlyReport = null;

        String path = "finances/src/assets/";

        while (userInput != 0) {
            if (userInput == 1 || userInput == 2) {
                System.out.println("Введите год");
                yearInput = scanner.nextInt();
                while (yearInput < 1987 || yearInput >= currentYear) {
                    System.out.println("Введите год с 1987 по 2023");
                    yearInput = scanner.nextInt();
                }
                if (userInput == 1) {
                    System.out.println("Введите месяц с которого предоставлять отчёт");
                    monthInput = scanner.nextInt();
                    while (monthInput <= 0 || monthInput > 10) {
                        System.out.println("Введите месяц с 1 по 10");
                        monthInput = scanner.nextInt();
                    }
                    MonthReportRead monthReportRead = new MonthReportRead(path, yearInput, monthInput);
                    monthlyReport = new MonthlyReport();
                    monthlyReport.setData(monthReportRead.monthData);
                    yearInput = 0;
                    monthInput = 0;
                } else {
                    YearReportRead yearReportRead = new YearReportRead(path, yearInput);
                    if (!yearReportRead.yearData.isEmpty()) {
                        yearlyReport = new YearlyReport();
                        yearlyReport.setData(yearReportRead.yearData, yearInput);
                    }
                    yearInput = 0;
                    monthInput = 0;
                }
            } else if (userInput == 3) {
                if (monthlyReport == null) {
                    System.out.println("АЛЛЁ, ХЪЮСТОН! Сначала нужно считать все месячные отчёты!");
                } else if (yearlyReport == null) {
                    System.out.println("АЛЛЁ, ХЪЮСТОН! Сначала нужно считать годовой отчёт!");
                } else {
                    int cnt = 0;
                    for (int month : yearlyReport.yearData.keySet()) {
                        if (monthlyReport.monthData.containsKey(month)) {
                            compareReports(yearlyReport.yearData.get(month).debet, monthlyReport.totalNotExpencesSums.get(cnt), yearlyReport.yearData.get(month).credit, monthlyReport.totalExpencesSums.get(cnt), month, yearlyReport.currentYear);
                        }
                        cnt++;
                    }
                }
            } else if (userInput == 4) {
                monthlyReport.getData();
            } else if (userInput == 5) {
                yearlyReport.getData();
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

    public static class YearlyReport {
        HashMap<Integer, YearReportRead.YearData> yearData = new HashMap<>();

        void setData(HashMap<Integer, YearReportRead.YearData> yd, int year) {
            yearData = yd;
            currentYear = year;
        }

        int currentYear;
        int avgDebit = 0;
        int avgCredit = 0;

        void getData() {
            System.out.print("В отчете за " + currentYear + " год в ");
            for (int i = 1; i <= yearData.size(); i++) {
                System.out.println(i + "-м месяце прибыль составила " + yearData.get(i).difference + ".");
                avgDebit += yearData.get(i).debet;
                avgCredit += yearData.get(i).credit;
                if (i != yearData.size()) {
                    System.out.print("В ");
                    avgDebit /= 3;
                    avgCredit /= 3;
                }
            }
            System.out.println("Средний расход составил: " + avgCredit + ", а средний доход: " + avgDebit);
        }
    }

    public static class MonthlyReport {
        HashMap<Integer, MonthReportRead.MonthData> monthData = new HashMap<>();
        ArrayList<String> isExpencesNames = new ArrayList<>();
        ArrayList<String> isntExpencesNames = new ArrayList<>();
        ArrayList<Integer> isntExpencesSums = new ArrayList<>();
        ArrayList<Integer> isExpencesSums = new ArrayList<>();
        ArrayList<Integer> totalExpencesSums = new ArrayList<>();
        ArrayList<Integer> totalNotExpencesSums = new ArrayList<>();

        void setData(HashMap<Integer, MonthReportRead.MonthData> md) {
            monthData = md;
            topSale(md);
            topExpence(md);
        }

        void getData() {
            if (!isExpencesNames.isEmpty() && !isntExpencesNames.isEmpty() && !isntExpencesSums.isEmpty() && !isExpencesSums.isEmpty()) {
                for (int i = 1; i <= monthData.size(); i++) {
                    MonthReportRead.MonthData data = monthData.get(i);
                    if (data != null) {
                        System.out.print("В " + data.monthName + " больше всего доходов по товару '" + isntExpencesNames.get(i - 1) + "' на сумму: " + isntExpencesSums.get(i - 1));
                        System.out.println(", а расходов по товару '" + isExpencesNames.get(i - 1) + "' на сумму: " + isExpencesSums.get(i - 1));
                    }
                }
            }
        }

        void topSale(HashMap<Integer, MonthReportRead.MonthData> monthData) {
            for (int i = 1; i <= monthData.size(); i++) {
                ArrayList<Integer> summs = new ArrayList<>();
                MonthReportRead.MonthData data = monthData.get(i);
                if (data != null) {
                    for (int j = 0; j < data.getSize(); j++) {
                        if (!data.expences.get(j)) {
                            int sum = data.quantitys.get(j) * data.coasts.get(j);
                            summs.add(sum);
                        } else {
                            summs.add(0);
                        }
                    }
                    int max = Collections.max(summs);
                    int totalSum = summs.stream().mapToInt(Integer::intValue).sum();
                    int indexOfMax = summs.indexOf(max);
                    String nameOfMax = monthData.get(i).items.get(indexOfMax);
                    isntExpencesNames.add(nameOfMax);
                    isntExpencesSums.add(max);
                    totalNotExpencesSums.add(totalSum);
                } else {
                    isntExpencesNames.add(null);
                    isntExpencesSums.add(null);
                    totalNotExpencesSums.add(null);
                }
            }
        }

        void topExpence(HashMap<Integer, MonthReportRead.MonthData> monthData) {
            for (int i = 1; i <= monthData.size(); i++) {
                ArrayList<Integer> summs = new ArrayList<>();
                MonthReportRead.MonthData data = monthData.get(i);
                if (data != null) {
                    for (int j = 0; j < data.getSize(); j++) {
                        if (data.expences.get(j)) {
                            int sum = data.quantitys.get(j) * data.coasts.get(j);
                            summs.add(sum);
                        } else {
                            summs.add(0);
                        }
                    }
                    int max = Collections.max(summs);
                    int totalSum = summs.stream().mapToInt(Integer::intValue).sum();
                    int indexOfMax = summs.indexOf(max);
                    String nameOfMax = monthData.get(i).items.get(indexOfMax);
                    isExpencesNames.add(nameOfMax);
                    isExpencesSums.add(max);
                    totalExpencesSums.add(totalSum);
                } else {
                    isExpencesNames.add(null);
                    isExpencesSums.add(null);
                    totalExpencesSums.add(null);
                }
            }
        }
    }

    public static void compareReports(int doyr, int domr, int coyr, int comr, int num, int year) {
        if (doyr == domr && coyr == comr) {
            System.out.println("За " + num + "-й месяц " + year + "-го года несоответствий не найдено.");
        } else {
            System.out.println("За " + num + "-й месяц " + year + "-го года обнаружены следующие несоответствия:");
            if (doyr != domr) {
                System.out.println("В годовом отчете указан доход " + doyr + ", а в месячном " + domr + ".");
            }
            if (coyr != comr) {
                System.out.println("В годовом отчете указан расход " + coyr + ", а в месячном " + comr + ".");
            }
        }
    }
}

