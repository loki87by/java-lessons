import java.util.HashMap;
import java.util.Objects;

public class Task extends Subtask {
    String name;
    int id;
    String status;
    HashMap<Integer, Subtask> content;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    public HashMap<Integer, Subtask> getContent() {
        return content;
    }

    public void setContent(HashMap<Integer, Subtask> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "\t\u001B[38;5;33m" +
                name + " (Task-" +
                id + ")" + "\u001B[0m" + ", status='" +
                (status.equals("new") ? "\u001b[31m" : status.equals("done") ? "\u001b[32m" : "\u001b[33m") +
                status + "\u001B[0m" + '\'' + ", content=\n\t\t" +
                content + "\n\t";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(status, task.status) && Objects.equals(content, task.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, id, status, content);
    }


    public Task(String name, int id, String status) {
        super(name, id, status);
        this.name = name;
        this.id = id;
        this.status = status;
        this.content = new HashMap<>();
    }
}

