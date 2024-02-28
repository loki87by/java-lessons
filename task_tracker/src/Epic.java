import java.util.HashMap;
import java.util.Objects;

public class Epic extends Subtask {
    HashMap<Integer, Task> content;
    String name;
    int id;
    Status status;

    public HashMap<Integer, Task> getContent() {

        return content;
    }

    //setters
    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    //getters
    @Override
    public Status getStatus() {
        return status;
    }


    //restructure
    @Override
    public String toString() {
        return "\u001B[38;5;44m" +
                name + " (Epic-" +
                id + ")" + "\u001B[0m" + ", content=\n\t" +
                content + "\n\t";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return id == epic.id &&
                Objects.equals(content, epic.content) &&
                Objects.equals(name, epic.name) &&
                Objects.equals(status, epic.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), content, name, id, status);
    }

    public Epic(String name, int id, Status status) {
        super(name, id, status);
        this.name = name;
        this.id = id;
        this.status = status;
        this.content = new HashMap<>();
    }
}
