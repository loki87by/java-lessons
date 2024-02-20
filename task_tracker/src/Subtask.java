import java.util.HashMap;
import java.util.Objects;

public class Subtask {
    String name;
    int id;
    String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "\u001B[38;5;28m" +
                name + " (Subtask-" +
                id + ")\u001B[0m" + ", status='" +
                (status.equals("new") ? "\u001b[31m" : status.equals("done") ? "\u001b[32m" : "\u001b[33m") +
                status + "\u001B[0m" + '\'' + "\n\t\t";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subtask subtask = (Subtask) o;
        return id == subtask.id && Objects.equals(name, subtask.name) && Objects.equals(status, subtask.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, status);
    }

    public Subtask(String name, int id, String status) {
        this.name = name;
        this.id = id;
        this.status = status;
    }
}
