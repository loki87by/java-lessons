import java.util.Objects;

public class Subtask {
    String name;
    int id;
    Status status;

    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    //getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    //restructure
    @Override
    public String toString() {
        return "\u001B[38;5;28m" +
                name + " (Subtask-" +
                id + ")\u001B[0m" + ", status='" +
                (status==Status.NEW ? "\u001b[31m" :
                        status==Status.DONE ?
                                "\u001b[32m" : "\u001b[33m") +
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

    public Subtask(String name, int id, Status status) {
        this.name = name;
        this.id = id;
        this.status = status;
    }
}
