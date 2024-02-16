import java.util.HashMap;
import java.util.Objects;

public class Subtask extends Task {
    @Override
    public String toString() {
        return "Subtask{" +
                "content=" + content +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subtask subtask = (Subtask) o;
        return id == subtask.id && Objects.equals(content, subtask.content) && Objects.equals(name, subtask.name) && Objects.equals(status, subtask.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, name, id, status);
    }

    HashMap<Integer, String> content;
    String name;
    int id;
    String status;


    public Subtask(String name, HashMap<Integer, String> content, int id, String status) {
        super(name, id, status);
        this.name = name;
        this.content = content;
        this.id = id;
        this.status = status;
    }
}
