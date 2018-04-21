package OpenTaskManager.Console.InnerConsoleImplement;

import OpenTaskManager.Console.IConsole;
import OpenTaskManager.Log.LogFactory;
import OpenTaskManager.TaskManager.PoolManager;
import OpenTaskManager.TaskManager.TaskPool;

/**
 * run
 * start server
 * Created by WangYH on 2016/8/8.
 */
public class StartCommand implements IConsole {
//    @Override
//    public Object excute(String[] args) {
//        if(Main.taskPool != null) System.out.println("Server is running");
//        else{
//            System.out.println("Server starting...");
//            Main.taskPool = new TaskPool();
//            Main.taskPool.setName(args[1]);
//            Main.backgroundThread = new Thread(Main.taskPool);
//            Main.backgroundThread.start();
//            System.out.println("Server started");
//        }
//        return null;
//    }
    public Object excute(String [] agrs){
        TaskPool taskPool = PoolManager.getInstance().newTaskPool(agrs[1]);
        if(taskPool == null) {
            LogFactory.logError("Server "+ agrs[1] +" is running");
            return false;
        }
        taskPool.start();
        LogFactory.logInfo("Server "+agrs[1]+" started");
        return true;
    }
}
