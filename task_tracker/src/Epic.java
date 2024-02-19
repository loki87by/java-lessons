import java.util.HashMap;
import java.util.Objects;

public class Epic extends Task {
    HashMap<Integer, Object> content;
    String name;
    int id;
    String status;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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
    public String toString() {
        return "Epic = {" +
                " name='" + name + '\'' +
                ", id=" + id +'\n' +
                //", status='" + status + '\'' +
                "content=" + content +'\n' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return id == epic.id && Objects.equals(content, epic.content) && Objects.equals(name, epic.name) && Objects.equals(status, epic.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), content, name, id, status);
    }

    public HashMap<Integer, Object> getContent() {
        return content;
    }

    public void setContent(HashMap<Integer, Object> content) {
        this.content = content;
    }


    public Epic(String name, HashMap<Integer, Object> content, int id, String status) {
        super(name, id, status);
        this.name = name;
        this.content = content;
        this.id = id;
        this.status = status;
    }
}
