package OpenTaskManager.Console.InnerConsoleImplement;

import OpenTaskManager.Console.IConsole;
import OpenTaskManager.Log.LogFactory;
import OpenTaskManager.TaskManager.PoolManager;

/**
 * Created by WangYH on 2017/1/15.
 */
public class AllCommand implements IConsole{
    @Override
    public Object excute(String[] args) {
        String [] names = PoolManager.getInstance().getAllName();
        if(names == null) {
            LogFactory.logInfo("no server exist");
            return null;
        }
        String str = "";
        for (String name: names) str+= name+"/n";
        LogFactory.logInfo(str);
        return null;
    }
}
