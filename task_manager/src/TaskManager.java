import java.util.HashMap;

public interface TaskManager {

        void getAllTasks();
         void init(HashMap<Integer, Object> tasks);
         void setEpic(String name);
         void setTask(String name, int id);
         void setSubtask(String data, int id);
        void changeStatus(int id, String status);
        void rename(int id, String new_name) ;
        void deleteAllTasks();
        void removeTask(int id);
        void history();
    }
