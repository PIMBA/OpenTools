package OpenTaskManager.TaskManager;

import OpenTaskManager.Log.LogFactory;
import OpenTaskManager.OpenInterfaces.ITask;
import OpenTaskManager.OpenInterfaces.ITaskBuilder;

import java.util.HashMap;
import java.util.Map;

/***
 * TaskBuilder类
 * 这个类用来返回模型接口
 * Created by WangYH on 2016/8/1.
 */
public class TaskBuilderFactory {
    private static Map<String,ITaskBuilder> builderMap = new HashMap<>();
    public static boolean regist(Class<? extends ITaskBuilder> builderClass,String name){
        try{
            builderMap.put(name, (ITaskBuilder) builderClass.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            LogFactory.logException(e.getStackTrace());
            return false;
        }
        return true;
    }
    public static boolean regist(ITaskBuilder taskBuilder,String name) {
        builderMap.put(name,taskBuilder);
        return true;
    }
    public static ITask build(String name){
        return builderMap.get(name).build();
    }
    public static ITaskBuilder getBuilder(String name){
        return builderMap.get(name);
    }
}
