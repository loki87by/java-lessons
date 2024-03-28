import java.io.IOException;
import java.util.HashMap;

public interface TaskManager {

        void getAllTasks();
         void init(HashMap<Integer, Object> tasks);
         void setEpic(String name) throws IOException;
         void setTask(String name, int id) throws IOException;
         void setSubtask(String data, int id) throws IOException;
        void changeStatus(int id, String status) throws IOException;
        void rename(int id, String new_name) throws IOException;
        void deleteAllTasks() throws IOException;
        void removeTask(int id) throws IOException;
    }
