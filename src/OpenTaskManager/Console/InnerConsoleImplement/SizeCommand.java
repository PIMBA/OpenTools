package OpenTaskManager.Console.InnerConsoleImplement;

import OpenTaskManager.Console.IConsole;
import OpenTaskManager.Log.LogFactory;
import OpenTaskManager.TaskManager.PoolManager;
import OpenTaskManager.TaskManager.TaskPool;

/**
 * Created by WangYH on 2017/1/15.
 */
public class SizeCommand implements IConsole {
    @Override
    public Object excute(String[] args) {
        TaskPool taskPool= PoolManager.getInstance().getTaskPool(args[1]);
        if(taskPool == null) {
            LogFactory.logError("Server "+args[1]+ "not exist");
            return false;
        }
        if(!taskPool.setSize(Integer.parseInt(args[2]))) {
            LogFactory.logError("Resize "+args[1]+ "cause some errors");
            return false;
        }
        return true;
    }
}
