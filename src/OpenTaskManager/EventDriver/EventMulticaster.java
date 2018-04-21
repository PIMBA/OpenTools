package OpenTaskManager.EventDriver;

import OpenTaskManager.EventDriver.Interfaces.EventListener;
import OpenTaskManager.Log.LogFactory;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * EventMulticaster 类
 * 用来发布任务事件
 * Created by WangYH on 2017/1/17.
 */
public class EventMulticaster{
    private List<Event> eventList = new LinkedList<>();
    private List<EventListener> eventListeners = new LinkedList<>();
    public boolean regist(EventListener eventListener){
        final boolean[] flag = {false};
        eventListeners.forEach(listenerClass1 -> {
            if (eventListener.getClass().equals(listenerClass1)) flag[0] = true;
        });
        if(flag[0]) {
            LogFactory.logWarning("listener is already exist");
            return false;
        }
        eventListeners.add(eventListener);
        return true;
    }
    public void unRegist(EventListener eventListener){
        eventListeners.removeIf(eventListener::equals);
    }
    private static EventMulticaster instance;
    public static EventMulticaster getInstance() {
        if(instance == null) instance = new EventMulticaster();
        return instance;
    }
    public boolean eventOccurs(Event e) {
        eventList.add(e);
        for (EventListener listener : eventListeners) {
            try {
                Type[] genericInterfaces =  listener.getClass().getGenericInterfaces();  //取得实现的接口
                for (Type t: genericInterfaces) {
                    String typeName = t.getTypeName();
                    String [] matchs = typeName.split("[<>]");
                    if(matchs[0].equals(EventListener.class.getName()) && matchs[1].equals(e.getClass().getName())) listener.onEvent(e);
                }
            } catch (Exception ex) {
                LogFactory.logException(ex.getStackTrace());
                return false;
            }
        }
        eventList.remove(e);
        return true;
    }
}
