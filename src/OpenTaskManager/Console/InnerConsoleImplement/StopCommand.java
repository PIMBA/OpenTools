package OpenTaskManager.Console.InnerConsoleImplement;


import OpenTaskManager.Console.IConsole;
import OpenTaskManager.Log.LogFactory;
import OpenTaskManager.TaskManager.PoolManager;
import OpenTaskManager.TaskManager.TaskPool;


/**
 * Created by WangYH on 2016/8/8.
 */
public class StopCommand implements IConsole {
    @Override
    public Object excute(String[] args) {
        TaskPool taskPool= PoolManager.getInstance().getTaskPool(args[1]);
        if(taskPool == null) {
            LogFactory.logError("Server "+args[1]+ "not exist");
            return false;
        }
        taskPool.stop();
        LogFactory.logInfo("Server"+args[1]+"stopped");
        return null;
    }
//    @Override
//    public Object excute(String[] args) {
//        if(Main.taskPool == null)
//            System.out.println("Server has been stoped");
//        else{
//            System.out.println("waiting for server to stop");
//            Main.taskPool.stop();
//            while(Main.backgroundThread.isAlive()) ;
//            System.out.println("server stoped");
//            Main.taskPool = null;
//        }
//        return null;
//    }
}
