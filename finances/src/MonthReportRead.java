import java.io.IOException;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class MonthReportRead {
    //private String path;
    //private int year;
    //private int month;
    static String[] monthNames = {"январе", "фaврале", "марте", "апреле", "мае", "июне", "июле", "августе", "сентябре", "октябре", "ноябре", "декабре"};
    public HashMap<Integer, MonthData> monthData = new HashMap<>();

    public MonthReportRead(String path, int year, int month) {
        for (int i = 0; i <= 2; i++) {
            String curMonth = month == 10 ? "" + (month + i) : "0" + (month + i);
            String curPath = new File(path + "/m." + year + curMonth + ".csv").getAbsolutePath();
            String fileData = readFileContentOrNull(curPath);
            if (fileData != null) {
                setData(fileData, (month + i));
            }
        }
    }

    private static String readFileContentOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Не читаeмый или отсутствующий файл " + path);
            return null;
        }
    }

    private void setData(String file, int monthIndex) {
        String[] lines = file.split("\\n");
        MonthData month = new MonthData();
        for (int i = 1; i < lines.length; i++) {
            String[] data = lines[i].split(",");
            String name = data[0];
            boolean is_expence = data[1].equals("true");
            int quantity = Integer.parseInt(data[2]);
            int sum = Integer.parseInt(data[3]);
            month.setData(name, is_expence, quantity, sum);
        }
        month.setName(monthIndex);
        monthData.put(monthIndex, month);
    }

    static class MonthData {
        ArrayList<String> items = new ArrayList<>();
        ArrayList<Boolean> expences = new ArrayList<>();
        ArrayList<Integer> quantitys = new ArrayList<>();
        ArrayList<Integer> coasts = new ArrayList<>();
        String monthName;

        public void setData(String it, boolean exp, int quan, int cst) {
            items.add(it);
            expences.add(exp);
            quantitys.add(quan);
            coasts.add(cst);
        }

        public int getSize() {
            return items.size();
        }

        public void setName(int index) {
            //System.out.println(index);
            monthName = monthNames[index-1];
        }
    }
}