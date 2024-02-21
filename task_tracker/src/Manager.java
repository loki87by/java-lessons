import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    static HashMap<Integer, Object> tasks;

    public void init(HashMap<Integer, Object> tasks) {
        Manager.tasks = tasks;
    }

    private int getNextIndex() {
        int count = 0;
        if (!tasks.isEmpty()) {
            int index = 0;
            for (Object task : tasks.values()) {
                if (task instanceof Subtask s) {
                    int currentId = s.getId();
                    index = Math.max(index, currentId);
                    if (s instanceof Task t) {
                        if (t.content != null) {
                            for (Subtask sub : t.content.values()) {
                                int ind = sub.getId();
                                index = Math.max(index, ind);
                            }
                        }
                    } else if (s instanceof Epic e) {
                        if (e.content != null) {
                            for (Task t : e.content.values()) {
                                int ind = t.getId();
                                index = Math.max(index, ind);
                                if (t instanceof Task tt) {
                                    if (tt.content != null) {
                                        for (Subtask sub : tt.content.values()) {
                                            int indx = sub.getId();
                                            index = Math.max(index, indx);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            count = index;
        }
        count++;
        return count;
    }

    private Subtask createSubtask(String name) {
        int count = getNextIndex();
        String status = "new";
        return new Subtask(name, count, status);
    }

    public void setSubtask(String data, int id) {
        Subtask st = createSubtask(data);
        for (int index : tasks.keySet()) {
            if (index == id) {
                if (tasks.get(index) instanceof Task t) {
                    t.content.put(st.id, st);
                } else if (tasks.get(index) instanceof Epic e) {
                    for (int indx : e.content.keySet()) {
                        if (indx == id) {
                            if (e.getContent().get(indx) instanceof Task t) {
                                t.content.put(st.id, st);
                            }
                        }
                    }
                }
            }
        }
    }

    private Task createTask(String name) {
        int count = getNextIndex();
        String status = "new";
        return new Task(name, count, status);
    }

    public void setTask(String name, int id) {
        Task task = createTask(name);
        if (id > 0) {
            for (int index : tasks.keySet()) {
                if (index == id && tasks.get(index) instanceof Epic e) {
                    e.content.put(task.id, task);
                }
            }
        } else {
            tasks.put(task.id, task);
        }
    }

    private Epic createEpic(String name) {
        int count = getNextIndex();
        String status = "new";
        return new Epic(name, count, status);
    }

    public void setEpic(String name) {
        Epic epic = createEpic(name);
        tasks.put(epic.id, epic);
    }

    public void getAllTasks() {
        HashMap<Integer, Object> newTasks = new HashMap<>();
        HashMap<Integer, Object> doingTasks = new HashMap<>();
        HashMap<Integer, Object> finishedTasks = new HashMap<>();

        for (Object task : tasks.values()) {
            if (task instanceof Subtask t) {
                String status = t.getStatus().toLowerCase();
                if (status.equals("new")) {
                    newTasks.put(t.id, task);
                } else if (status.equals("done")) {
                    finishedTasks.put(t.id, task);
                } else {
                    doingTasks.put(t.id, task);
                }
            } else {
                System.out.println("false");
            }
        }
        System.out.println("\u001b[36m----------------------------------------------------------------------\u001B[0m");
        System.out.println("\u001b[31m\u001B[1m" + "To do" + "\u001B[0m");
        if (!newTasks.isEmpty()) {
            for (Object nti : newTasks.values()) {
                System.out.println(nti);
            }
        } else {
            System.out.println("none");
        }
        System.out.println("\u001b[36m----------------------------------------------------------------------\u001B[0m");
        System.out.println("\u001b[33m\u001B[1m" + "In progress" + "\u001B[0m");
        if (!doingTasks.isEmpty()) {
            for (Object nti : doingTasks.values()) {
                System.out.println(nti);
            }
        } else {
            System.out.println("none");
        }
        System.out.println("\u001b[36m----------------------------------------------------------------------\u001B[0m");
        System.out.println("\u001b[32m\u001B[1m" + "Done" + "\u001B[0m");
        if (!finishedTasks.isEmpty()) {
            for (Object nti : finishedTasks.values()) {
                System.out.println(nti);
            }
        } else {
            System.out.println("none");
        }
        System.out.println("\u001b[36m----------------------------------------------------------------------\u001B[0m");
    }

    private Object getById(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        } else {
            for (int taskId : tasks.keySet()) {
                if (tasks.get(taskId) instanceof Task t) {
                    if (t.content.containsKey(id)) {
                        return t.content.get(id);
                    }
                } else if (tasks.get(taskId) instanceof Epic e) {
                    if (e.content.containsKey(id)) {
                        return e.content.get(id);
                    } else {
                        if (tasks.get(taskId) instanceof Task t) {
                            if (t.content.containsKey(id)) {
                                return t.content.get(id);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private String getStatus(String status) {
        String lower = status.toLowerCase();
        if (lower.equals("new") || lower.equals("done")) {
            return lower;
        } else {
            return "in_progress";
        }
    }

    private void checkEpicState() {
        for (int id : tasks.keySet()) {
            Object task = tasks.get(id);
            if (task instanceof Epic e) {
                boolean isNew = false;
                boolean isDone = false;
                String status = "in_progress";
                if (e.getContent().isEmpty()) {
                    isNew = true;
                } else {
                    ArrayList<String> statuses = new ArrayList<>();
                    for (int key : e.getContent().keySet()) {
                        if (e.getContent().get(key) instanceof Task t) {
                            statuses.add(t.getStatus());
                        }
                    }
                    if (statuses.stream().allMatch(s -> s.equals("new"))) {
                        isNew = true;
                    } else if (statuses.stream().allMatch(s -> s.equals("done"))) {
                        isDone = true;
                    }
                }
                if (isNew) {
                    status = "new";
                } else if (isDone) {
                    status = "done";
                }
                e.setStatus(status);
            }
        }
    }

    public void changeStatus(int id, String status) {
        Object task = getById(id);
        if (task instanceof Subtask s) {
            if (s instanceof Task t) {
                t.setStatus(getStatus(status));
            } else if (s instanceof Epic) {
                System.out.println("\u001b[38;5;196mERROR:\u001b[0m Операция отклонена, статус эпика зависит от статуса вложений");
            } else {
                s.setStatus(getStatus(status));
            }
            checkEpicState();
        }
    }

    public void rename(int id, String new_name) {
        Object task = getById(id);
        if (task instanceof Subtask s) {
            s.setName(new_name);
        }
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void removeTask(int id) {
        if (!tasks.containsKey(id)) {
            for (int key : tasks.keySet()) {
                if (tasks.get(key) instanceof Task t) {
                    if (t.getContent().containsKey(id)) {
                        t.content.remove(id);
                    }
                } else if (tasks.get(key) instanceof Epic e) {
                    if (e.getContent().containsKey(id)) {
                        e.content.remove(id);
                    } else {
                        for (int index : e.getContent().keySet()) {
                            if (e.getContent().get(index) instanceof Task t) {
                                if (t.getContent().containsKey(id)) {
                                    t.content.remove(id);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            tasks.remove(id);
        }
    }
}
