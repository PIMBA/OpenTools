package OpenTaskManager.Console;

import OpenTaskManager.Log.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WangYH on 2016/12/23.
 */
public class ConsoleEntry {
    private static Map<String,IConsole> map = new HashMap<>();
    public static boolean regist(Class<? extends IConsole> iConsoleClass,String name){
        try{
            map.put(name, (IConsole) iConsoleClass.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            LogFactory.logException(e.getStackTrace());
            return false;
        }
        return true;
    }
    public static boolean regist(IConsole iConsole,String name) {
        map.put(name,iConsole);
        return true;
    }
    public static IConsole get(String name) throws IllegalAccessException, InstantiationException {
        return map.get(name).getClass().newInstance();
    }

}
