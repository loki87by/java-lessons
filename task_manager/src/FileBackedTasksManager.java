import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class FileBackedTasksManager extends InMemoryTaskManager {

    static Path pathToFile = Paths.get("../../managerSaves.csv");
HashMap<Integer, Object> tasks = InMemoryTaskManager.tasks;
    Path file;
    private void save() throws IOException {
        Files.deleteIfExists(pathToFile);
        if (!Files.exists(pathToFile)) {
            file = Files.createFile(pathToFile);
        }
        try (Writer fileWriter = new FileWriter("managerSaves.csv", StandardCharsets.UTF_8)) {
            tasks = InMemoryTaskManager.tasks;
            for (Object task: tasks.values()) {
                if (task instanceof Subtask) {
                    int id = ((Subtask) task).getId();
                    String className = String.valueOf(task.getClass()).substring(6);
                    String name = ((Subtask) task).getName();
                    String status = String.valueOf(((Subtask) task).getStatus());
                    String content = getString(task);
                    String taskData = String.format("%d, %s, %s, %s, %s\n", id, className, name, status, content);
                    fileWriter.write(taskData);
                    if (task instanceof Task && !((Task) task).content.isEmpty()) {
                        for (Subtask st: ((Task) task).content.values()) {
                            int stid = st.getId();
                            String stclassName = String.valueOf(st.getClass()).substring(6);
                            String stname = st.getName();
                            String ststatus = String.valueOf(st.getStatus());
                            String stcontent = getString(st);
                            String stData = String.format("%d, %s, %s, %s, %s\n", stid, stclassName, stname, ststatus, stcontent);
                            fileWriter.write(stData);
                        }
                    } else if (task instanceof Epic && !((Epic) task).content.isEmpty()) {
                        for (Subtask st: ((Epic) task).content.values()) {
                            int tid = st.getId();
                            String tclassName = String.valueOf(st.getClass()).substring(6);
                            String tname = st.getName();
                            String tstatus = String.valueOf(st.getStatus());
                            String tcontent = getString(st);
                            String ttaskData = String.format("%d, %s, %s, %s, %s\n", tid, tclassName, tname, tstatus, tcontent);
                            fileWriter.write(ttaskData);
                            if (st instanceof Task && !((Task) st).content.isEmpty()) {
                                for (Subtask s: ((Task) st).content.values()) {
                                    int sid = s.getId();
                                    String sclassName = String.valueOf(s.getClass()).substring(6);
                                    String sname = s.getName();
                                    String sstatus = String.valueOf(s.getStatus());
                                    String scontent = getString(s);
                                    String sData = String.format("%d, %s, %s, %s, %s\n", sid, sclassName, sname, sstatus, scontent);
                                    fileWriter.write(sData);
                                }
                            }
                        }
                    }
                }
            }
            fileWriter.write("\n");
            StringBuilder sb = new StringBuilder();
            ArrayList<Subtask> story = historyManager.getHistory();
            for (Subtask st : story) {
                sb.append(st.getId()).append(",");
            }
            System.out.println("story: "+sb);
            fileWriter.write(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String getString(Object task) {
        String content = " ";
        if (task instanceof Task) {
            StringBuilder taskContents = new StringBuilder();
            for (Subtask t: ((Task) task).content.values()) {
                taskContents.append(t.getId());
                taskContents.append(";");
            }
            content = taskContents.toString();
        }
        if (task instanceof Epic) {
            StringBuilder taskContents = new StringBuilder();
            for (Subtask t: ((Epic) task).content.values()) {
                taskContents.append(t.getId());
                taskContents.append(";");
            }
            content = taskContents.toString();
        }
        return content;
    }

    @Override
    public void setEpic(String name) throws IOException {
        super.setEpic(name);
        tasks = InMemoryTaskManager.tasks;
        save();
    };
    @Override
    public void setTask(String name, int id) throws IOException {
        super.setTask(name, id);
        tasks = InMemoryTaskManager.tasks;
        save();
    };
    @Override
    public void setSubtask(String data, int id) throws IOException {
        super.setSubtask(data, id);
        tasks = InMemoryTaskManager.tasks;
        save();
    };
    @Override
    public void changeStatus(int id, String status) throws IOException {
        super.changeStatus(id, status);
        save();
    };
    @Override
    public void rename(int id, String new_name) throws IOException {
        super.rename(id, new_name);
        save();
    };
    @Override
    public void deleteAllTasks() throws IOException {
        super.deleteAllTasks();
        save();
    };
    @Override
    public void removeTask(int id) throws IOException {
        super.removeTask(id);
        save();
    };
}
