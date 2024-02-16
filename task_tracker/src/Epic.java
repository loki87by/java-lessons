import java.util.HashMap;
import java.util.Objects;

public class Epic extends Task {
    HashMap<Integer, Object> content;
    String name;
    int id;
    String status;

    @Override
    public String toString() {
        return "Epic{" +
                "content=" + content +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                ", isEpic=" + isEpic +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return id == epic.id && isEpic == epic.isEpic && Objects.equals(content, epic.content) && Objects.equals(name, epic.name) && Objects.equals(status, epic.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), content, name, id, status, isEpic);
    }

    boolean isEpic = true;

    public Epic(String name, HashMap<Integer, Object> content, int id, String status) {
        super(name, id, status);
        this.name = name;
        this.content = content;
        this.id = id;
        this.status = status;
    }
}
