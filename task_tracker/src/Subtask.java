import java.util.HashMap;
import java.util.Objects;

public class Subtask extends Task {
    @Override
    public String toString() {
        return "Subtask = {" +
                " name='" + name + '\'' +
                ", id=" + id + '\n' +
                "content=" + content + '\n' +
                //", status='" + status + '\'' +
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


    public HashMap<Integer, String> getContent() {
        return content;
    }

    public void setContent(HashMap<Integer, String> content) {
        this.content = content;
    }


    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Subtask(String name, HashMap<Integer, String> content, int id, String status) {
        super(name, id, status);
        this.name = name;
        this.content = content;
        this.id = id;
        this.status = status;
    }
}
