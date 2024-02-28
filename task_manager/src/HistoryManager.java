import java.util.HashMap;

public interface HistoryManager {
    void add(Subtask task);
    HashMap<Integer, Subtask> getHistory();
}
