import java.util.ArrayList;

public interface HistoryManager {
    void add(Subtask task);
    ArrayList<Subtask> getHistory();
}
