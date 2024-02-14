import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class YearReportRead {
    public HashMap<Integer, YearData> yearData = new HashMap<>();
    static String[] monthNames = {"январе", "фaврале", "марте", "апреле", "мае", "июне", "июле", "августе", "сентябре", "октябре", "ноябре", "декабре"};

    public YearReportRead(String path, int year) {
        String curPath = new File(path + "/y." + year + ".csv").getAbsolutePath();
        String fileData = readFileContentOrNull(curPath);
        setData(fileData);
    }

    private static String readFileContentOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Не читаeмый или отсутствующий файл " + path);
            return null;
        }
    }

    private void setData(String file) {
        if (file != null) {
            String[] lines = file.split("\\n");
            YearData month = null;
            int monthIndex = 0;
            for (int i = 1; i < lines.length; i += 2) {
                month = new YearData();
                String[] data1 = lines[i].split(",");
                String[] data2 = lines[i + 1].split(",");
                int monthNumber = Integer.parseInt(data1[0].replace("0", ""));
                monthIndex = monthNumber;
                month.setName(monthNames[monthNumber - 1]);
                boolean is_expence1 = data1[2].equals("true");
                boolean is_expence2 = data2[2].equals("true");
                int amount1 = Integer.parseInt(data1[1]);
                int amount2 = Integer.parseInt(data2[1]);
                month.setData(is_expence1, is_expence2, amount1, amount2);
                yearData.put(monthIndex, month);
            }
        }
    }

    static class YearData {
        ArrayList<Boolean> expences = new ArrayList<>();
        ArrayList<Integer> amounts = new ArrayList<>();
        String monthName;
        int difference;
        int debet;
        int credit;

        public void setData(boolean exp1, boolean exp2, int sum1, int sum2) {
            expences.add(exp1);
            expences.add(exp2);
            amounts.add(sum1);
            amounts.add(sum2);
            int notExpenceIndex = expences.indexOf(false);
            int expenceIndex = expences.indexOf(true);
            debet = amounts.get(notExpenceIndex);
            credit = amounts.get(expenceIndex);
            difference = credit - debet;
        }

        public void setName(String name) {
            monthName = name;
        }

    }
}