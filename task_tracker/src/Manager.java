import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    static HashMap<Integer, Object> tasks;

    public void init(HashMap<Integer, Object> tasks) {
        Manager.tasks = tasks;
    }

    public void getAllTasks() {
        HashMap<Integer, Object> newTasks = new HashMap<>();
        HashMap<Integer, Object> doingTasks = new HashMap<>();
        HashMap<Integer, Object> finishedTasks = new HashMap<>();

        for (Object task : tasks.values()) {
            //System.out.println("task: "+task);
            if(task instanceof Task t) {
                //System.out.println("true");
                String status = t.getStatus().toLowerCase();
                //System.out.println("status: "+status+" id: "+t.getId());
                if (status.equals("new")) {
                    newTasks.put(t.getId(), task);
                } else if (status.equals("done")) {
                    finishedTasks.put(t.getId(), task);
                } else {
                    doingTasks.put(t.getId(), task);
                }
            } else {
                System.out.println("false");
            }
        }
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("To do");
        if (!newTasks.isEmpty()) {
            for(Object nti : newTasks.values()){
                System.out.println(nti);
            }
        }  else {
            System.out.println("none");
        }
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("In progress");
        if (!doingTasks.isEmpty()) {
            for(Object nti : doingTasks.values()){
                System.out.println(nti);
            }
        }  else {
            System.out.println("none");
        }
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("Done");
        if (!finishedTasks.isEmpty()) {
            for(Object nti : finishedTasks.values()){
                System.out.println(nti);
            }
        }  else {
            System.out.println("none");
        }
        System.out.println("----------------------------------------------------------------------------");
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

/*    public void getById(int id) {
        Object task = tasks.get(id);
        if (task != null) {
            System.out.println(task);
        } else {
            System.out.println("С id = " + id + " записей не найдено");
        }
    }*/

    private int getNextIndex() {
        int count = 0;
        if (!tasks.isEmpty()) {
            int index = 0;
            for (Object task : tasks.values()) {
                if (task instanceof Task t) {
                    int currentId = t.getId();
                    index = Math.max(index, currentId);
                    if (t instanceof Epic) {
                        for (Object sub : ((Epic) t).getContent().values()) {
                            if (sub instanceof Subtask s) {
                                int ind = s.getId();
                                index = Math.max(index, ind);
                            }
                        }
                    }
                }
            }
            count = index;
        }
        return count;
    }

    private Subtask createSubtask(String[] data) {
        int count = getNextIndex();
        String name;
        String status = "new";
        count++;
        name = data[0];
        HashMap<Integer, String> stringContent = new HashMap<>();
        for (int i = 1; i < data.length; i++) {
            stringContent.put(count, data[i]);
            count++;
        }
        return new Subtask(name, stringContent, count, status);
    }

    public void setTask(String[] data) {
        Subtask st = createSubtask(data);
        int index = st.getId();
        tasks.put(index, st);
    }
    private String getStatus(String status) {
        String lower = status.toLowerCase();
        if (lower.equals("new") || lower.equals("done")) {
            return status;
        } else {
            return "in_progress";
        }
    }

    public void updateTask(int id, String[] data) {
        Object task = tasks.get(id);
        Subtask st = createSubtask(data);
        if (task instanceof Subtask existingTask) {
            int count = getNextIndex();
            HashMap<Integer, String> stringContent = new HashMap<>();
            for (int i = 2; i < data.length; i++) {
                stringContent.put(count, data[i]);
                count++;
            }
            existingTask.setName(data[0]);
            existingTask.setStatus(getStatus(data[1]));
            existingTask.setContent(stringContent);
        } else if (task instanceof Epic existingTask) {
            int count = getNextIndex();
            HashMap<Integer, Object> dataContent = new HashMap<>();
            for (int i = 2; i < data.length; i++) {
                dataContent.put(count, data[i]);
                count++;
            }
            existingTask.setName(data[0]);
            existingTask.setStatus(getStatus(data[1]));
            existingTask.setContent(dataContent);
            tasks.put(id, st);
            checkEpicState(id);
        }
    }

    public void removeTask(int id) {
        tasks.remove(id);
    }

    public void updateEpic(int taskId, int stringId, String[] data) {
        Subtask st = createSubtask(data);
        Object task = tasks.get(taskId);
        String name;
        String status;
        int index;
        HashMap<Integer, String> stringContent;
        HashMap<Integer, Object> dataContent;
        HashMap<Integer, Object> objectContent = new HashMap<>();
        if (task instanceof Task t) {
            name = t.getName();
            status = t.getStatus();
            index = t.getId();
            if (t instanceof Subtask s) {
                stringContent = s.getContent();
                for (int key : stringContent.keySet()) {
                    if (key != stringId) {
                        objectContent.put(key, stringContent.get(key));
                    } else {
                        objectContent.put(key, st);
                    }
                }
            } else if (t instanceof Epic e) {
                dataContent = e.getContent();
                for (int key : dataContent.keySet()) {
                    if (key != stringId) {
                        objectContent.put(key, dataContent.get(key));
                    } else {
                        objectContent.put(key, st);
                    }
                }
            }
            Epic epic = new Epic(name, objectContent, index, status);
            tasks.put(taskId, epic);
            checkEpicState(taskId);
        }
    }

    private Object getById (int id) {
        Object task = null;
        for(Object cur : tasks.values()) {
            if(cur instanceof Subtask t) {
                if (t.getId()==id) {
                    task = cur;
                }
            } else if(cur instanceof Epic e) {
                if (e.getId()==id) {
                    task = cur;
                } else {
                    for(Object current : e.getContent().values()) {
                        if(current instanceof Subtask t) {
                            if (t.getId()==id) {
                                task = current;
                            }
                        }
                    }
                }
            }
        }
        return task;
    }

    public void checkEpicState(int id) {
        Object task = tasks.get(id);
        boolean isNew=false;
        boolean isDone=false;
        String status="in_progress";
        if (task instanceof Epic e) {
            HashMap<Integer, Object> content = e.getContent();
            if(content.isEmpty()) {
                isNew=true;
            } else {
                ArrayList<String> statuses = new ArrayList<>();
                for(int key: content.keySet()) {
                    Object currentTask = content.get(key);
                    if (currentTask instanceof Task t){
                        statuses.add(t.getStatus());
                    }
                }
                if (statuses.stream().allMatch(s -> s.equals("new"))) {
                    isNew=true;
                } else if (statuses.stream().allMatch(s -> s.equals("done"))) {
                    isDone=true;
                }
            }
            if(isNew) {
                status="new";
            } else if(isDone) {
                status="done";
            }
            e.setStatus(status);
            tasks.put(id, e);
        }
    }

}
