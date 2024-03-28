public class Managers {
    public static TaskManager getDefault() {
        return new FileBackedTasksManager();
    }

    public static HistoryManager getDefaultHistory() {
        return InMemoryHistoryManager.getInstance();
    }
}
