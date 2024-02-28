import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager{
    private static InMemoryHistoryManager instance;
    private final HashMap<Integer, Subtask> history = new HashMap<>();

    private InMemoryHistoryManager() {}

    public static synchronized InMemoryHistoryManager getInstance() {
        if (instance == null) {
            instance = new InMemoryHistoryManager();
        }
        return instance;
    }
    @Override
    public HashMap<Integer, Subtask> getHistory() {
        return history;
    }
    @Override
    public void add(Subtask task) {
        history.put(history.size(), task);
    }
}
