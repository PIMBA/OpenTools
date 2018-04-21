package OpenTaskManager.EventDriver.Interfaces;

import OpenTaskManager.EventDriver.Event;
import OpenTaskManager.EventDriver.EventMulticaster;

/**
 * Created by WangYH on 2017/1/17.
 */
public interface EventTrigger<E extends Event> {
    E newEvent();
    default public void launchEvent(){
        EventMulticaster.getInstance().eventOccurs(newEvent());
    }
}
