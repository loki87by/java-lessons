import java.util.HashMap;

public class Main {
    static HashMap<Integer, Object> content = new HashMap<>();
    static HashMap<Integer, String> content2 = new HashMap<>();
    public static void main(String[] args) {
        content2.put(1, "content");
        content2.put(5, "ok");
        Subtask subtask = new Subtask("ne_name", content2, 456, "ne_status");
        System.out.println("subtask: "+subtask);
        content.put(12, "message");
        content.put(2, subtask);
        Epic epic = new Epic("name", content, 123, "status");
        System.out.println("epic: "+epic);
    }
}
