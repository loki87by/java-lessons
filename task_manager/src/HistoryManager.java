import java.util.ArrayList;

public interface HistoryManager {
    void add(Subtask task);
    /*void remove(int id);*/
    ArrayList<Subtask> getHistory();
}
