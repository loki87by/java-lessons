import java.util.HashMap;

public class StepTracker {
    static HashMap<Integer, MonthData> monthData = new HashMap<Integer, MonthData>();
    static int stepsTarget = 10000;

    public static void StepTracker() {
        for (int i = 0; i < 12; i++) {
            MonthData month = new MonthData();
            month.init();
            monthData.put(i, month);
            System.out.println(i);
        }
    }

    public static void ChangeTarget(Integer arg) {
        stepsTarget = arg;
    }

    public static class MonthData {
        static HashMap<Integer, Day> days = new HashMap<>();

        private void init() {
            for (int i = 0; i < 30; i++) {
                Day day = new Day();
                days.put(i, day);
            }
        }

        public void GetStatistic() {
            int sum = 0;
            int max = 0;
            int series = 0;
            Integer bestSeries = 0;
            Converter converter = new Converter();
            for(Integer day: days.keySet()) {
                System.out.print((day+1)+" день: "+(days.get(day).GetSteps()));
                sum+=days.get(day).GetSteps();
                max = Math.max(max, days.get(day).GetSteps());
                converter.SetData(days.get(day).GetSteps());
                if(days.get(day).GetSteps() < stepsTarget) {
                    bestSeries = Math.max(bestSeries, series);
                    series = 0;
                } else {
                    series++;
                }
                if (day < days.size()-1) {
                    System.out.print(", ");
                } else {
                    System.out.println(' ');
                }
            }
            System.out.println("Общее количество шагов за месяц: "+ sum);
            System.out.println("Максимальное пройденное количество шагов в месяце: "+ max);
            System.out.println("Среднее количество шагов: "+ (sum / days.size()));
            System.out.println("Пройденная дистанция (в км): "+ converter.GetDistance());
            System.out.println("Количество сожжённых килокалорий: "+ converter.GetKcals());
            System.out.println("Лучшая серия: "+ bestSeries + " дней.");
        }

        public static void SetSteps(Integer day, Integer steps) {
            days.get(day).SetSteps(steps);
        }
    }

    static class Converter {
        static double distance = 0.0;
        static double kcals = 0.0;
        public static void SetData(int steps) {
            distance += steps / 1000 * .75;
            kcals = +steps * 50 / 1000;
        }
        public static double GetDistance() {
            return distance;
        }
        public static double GetKcals() {
            return kcals;
        }
    }

    static class Day {
        static int steps = 0;
        public static void SetSteps(Integer current) {
            steps = current;
        }
        public static int GetSteps() {
            return steps;
        }
    }
}