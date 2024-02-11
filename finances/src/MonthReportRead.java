import java.io.IOException;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

public class MonthReportRead {
    private String path;
    private int year;
    private int month;
    public HashMap<Integer, MonthData> monthData = new HashMap<>();

    public MonthReportRead(String path, int year, int month) {
        for (int i = 0; i <= 2; i++) {
            String curMonth = month == 10 ? "" + (month + i) : "0" + (month + i);
            String curPath = new File(path + "/m." + year + curMonth + ".csv").getAbsolutePath();;
            String fileData = readFileContentOrNull(curPath);
            if (fileData != null) {
                setData(fileData);
            }
        }
    }

    private static String readFileContentOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Не читаeмый или отсутствующий файл "+path);
            return null;
        }
    }

    private void setData(String file) {
        String[] lines = file.split("\\n");
        for (int i = 1; i < lines.length; i++) {
            String[] data = lines[i].split(",");
            String name = data[0];
            boolean is_expence = data[1].equals("true");
            int quantity = Integer.parseInt(data[2]);
            int sum = Integer.parseInt(data[3]);
            MonthData month = new MonthData();
            month.setData(name, is_expence, quantity, sum);
            monthData.put(i, month);
        }
    }

    static class MonthData {
        String item;
        boolean expence;
        int quantity;
        int coast;

        public void setData(String it, boolean exp, int quan, int cst) {
            item = it;
            expence = exp;
            quantity = quan;
            coast = cst;
        }
    }
}