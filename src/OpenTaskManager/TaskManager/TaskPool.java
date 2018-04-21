package OpenTaskManager.TaskManager;


import OpenTaskManager.Log.LogFactory;
import OpenTaskManager.OpenInterfaces.ITask;
import OpenTaskManager.OpenInterfaces.ITaskBuilder;

import java.util.ArrayList;
import java.util.List;


/**
 * TaskPool类
 * 这个类用来管理任务
 * Created by WangYH on 2016/8/1.
 */

public class TaskPool implements Runnable{
    private List<Task> taskRefs = new ArrayList<>();

    private transient boolean ifFull = false;

    private transient int size = 1;
    private transient boolean wiilStop = false;
    private transient boolean canStop = true;
    private transient boolean stopSq = false;

    private String name = null;
    private ITaskBuilder builder = null;

    private Thread threadQ = null;

    public synchronized boolean setName(String name){
        if(builder != null) {
            LogFactory.logError("TaskPool has name : "+name);
            return false;
        }
        builder = TaskBuilderFactory.getBuilder(name);
        if(builder != null) this.name = name;
        LogFactory.logInfo("TaskPool named : "+name);
        return true;
    }

    public void stop(){
        this.wiilStop = true;
    }

    public synchronized void waitForRoom(){
        ifFull = true;
        LogFactory.logInfo("TaskPool "+name+"is full,waiting");
        try {
            this.wait();
        } catch (InterruptedException ignored) {
        }
    }
    private synchronized void continueListen(){
        ifFull = false;
        this.notify();
        LogFactory.logInfo("TaskPool "+name+"is not full,listening");
    }

    private synchronized void pushIntoTaskPool(Task ref){
        this.taskRefs.add(ref);
        this.canStop = false;
        ref.start();
        LogFactory.logInfo(ref.id + " push into task pool");
        if(this.taskRefs.size() >= size) waitForRoom();
    }
    public synchronized void popFromTaskPool(Task ref){
        taskRefs.remove(ref);
        if(this.ifFull) continueListen();
        if(this.taskRefs.size() == 0) canStop = true;

        if(wiilStop && stopSq) this.notify();
        LogFactory.logInfo(ref.id + " pop from task pool");
    }

    @Override
    public void run() {
        //main loop
        ITask task = null;
        do {
            task = builder.build();
            if(task != null) pushIntoTaskPool(new Task(task,this));
        } while (!wiilStop);

        //start stop sequence,waiting for all tasks done
        waitUnitlAllTaskDone();
        LogFactory.logInfo("TaskPool "+ name +" stoped");
    }

    public void waitUnitlAllTaskDone(){
        stopSq = true;
        if(!this.canStop)
        try {
            this.wait();
        } catch (InterruptedException ignored) {
        }
    }

    public String getName() {
        return name;
    }

    public void start(){
        if(this.threadQ != null) return ;
        threadQ = new Thread(this);
        threadQ.start();
    }

    public boolean setSize(int size) {
        if(this.threadQ != null) return false;
        if(size < 1) return false;
        this.size = size;
        return true;
    }
}




//public class TaskPool implements Runnable{
//    private int POOL_SIZE = 1;
//    private List<Thread> taskPool = new ArrayList<>();
//    private boolean willStop = false;
//    private transient boolean full = false;
//
//    private String name = null;
//    public void setName(String name){this.name = name;}
//    public String getName(){return this.name;}
//
//    //private Thread listener = null;
//
//    private synchronized void popTaskFormTaskPool(Thread ref){
//        taskPool.remove(ref);
//        LogFactory.logInfo(ref.getName() + " pop from task pool "+name);
//        //if(full) listenerContinue();
//    }
//    private synchronized void pushTaskIntoTaskPool(Task task){
//        Thread taskThread = new Thread(task);
//        task.id = ((Long)System.currentTimeMillis()).toString();
//        taskThread.setName(task.id);
//        LogFactory.logInfo(task.id + " push into task pool");
//        taskThread.start();
//        taskPool.add(taskThread);
//    }
//    @Override
//    public void run (){
//        LogFactory.logInfo(name +" TaskManager is running....");
////        Thread listener = new Thread(() -> {
////            while (!(willStop && taskPool.size() == 0)) {
////                for (Thread taskThread : taskPool) {
////                    if (taskThread != null && !taskThread.isAlive()) popTaskFormTaskPool(taskThread);
////                }
////            }
////        });
////        listener.start();
//        while(!willStop) {
//            LogFactory.logInfo(taskPool.size()+" tasks is running");
//            if(taskPool.size()<POOL_SIZE){
//                Task t = null;
//                do{
//                    t = newTask();
//                }while (t == null);
//                pushTaskIntoTaskPool(t);
//            } //else listenerWait();
//        }
//        LogFactory.logInfo("waiting for all tasks done");
//        //while(listener.isAlive()) ;
//        this.willStop = false;
//    }
//    private Task newTask(){
//        return new Task(TaskBuilderFactory.build(name));
//    }
//    public void stop() {
//        willStop = true;
//    }
//
//    private Thread taskPoolThread = new Thread(this);
//    private boolean running = false;
//
//    public boolean start(){
//        if(this.running) return false;
//
//        this.running = true;
//        this.taskPoolThread.start();
//        return true;
//    }
//
//    private synchronized void taskPoolWait(){
//        full = true;
//        LogFactory.logInfo(name +" TaskManager is full,taskPool waiting");
//        try {
//            this.taskPoolThread.wait();
//        } catch (InterruptedException ignored) {
//        }
//    }
//    private synchronized void taskPoolContinue(){
//        full = false;
//        LogFactory.logInfo(name +" TaskManager is not full,taskPool working");
//        this.taskPoolThread.notify();
//    }
//
//    public boolean setPoolSize(int size){
//        if(running) return false;
//        POOL_SIZE = size>1?size:1;
//        return true;
//    }
//
//}
