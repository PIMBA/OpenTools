package OpenTaskManager.TaskManager;

import java.util.*;

/**
 * Created by WangYH on 2017/1/14.
 */
public class PoolManager {
    List<TaskPool> taskPools= new LinkedList<>();
    private static PoolManager instance;
    public static PoolManager getInstance() {
        if(instance == null) instance = new PoolManager();
        return instance;
    }
    public TaskPool getTaskPool(String name){
        for (TaskPool t : taskPools) {
            if(t.getName().equals(name)) return t;
        }
        return null;
    }
    public TaskPool newTaskPool(String name) {
        TaskPool ret = getTaskPool(name);
        if(ret != null) return null;
        ret = new TaskPool();
        ret.setName(name);
        taskPools.add(ret);
        return ret;
    }
    public String [] getAllName(){
        if(taskPools.size() < 1) return null;
        String [] names = new String [this.taskPools.size()];
        int counter =0;
        for(TaskPool t :taskPools) names[counter++] = t.getName();
        return names;
    }
}
