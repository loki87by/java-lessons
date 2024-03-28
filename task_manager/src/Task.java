import java.util.HashMap;
import java.util.Objects;

public class Task extends Subtask {
    String name;
    int id;
    Status status;
    HashMap<Integer, Subtask> content;

    //setters
    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    //getters
    @Override
    public int getId() {
        return id;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public HashMap<Integer, Subtask> getContent() {
        return content;
    }

    //restructure
    @Override
    public String toString() {
        return "\t\u001B[38;5;33m" +
                name + " (Task-" +
                id + ")" + "\u001B[0m" + ", status='" +
                (status==Status.NEW ? "\u001b[31m" :
                        status==Status.DONE ?
                                "\u001b[32m" : "\u001b[33m") +
                status + "\u001B[0m" + '\'' + ", content=\n\t\t" +
                content + "\n\t";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Task task = (Task) o;
        return id == task.id &&
                Objects.equals(name, task.name)
                && Objects.equals(status, task.status)
                && Objects.equals(content, task.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, id, status, content);
    }

    public Task(String name, int id, Status status) {
        super(name, id, status);
        this.name = name;
        this.id = id;
        this.status = status;
        this.content = new HashMap<>();
    }
}