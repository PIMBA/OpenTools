package OpenTaskManager.EventDriver.Interfaces;

import OpenTaskManager.EventDriver.Event;

/**
 * Created by WangYH on 2017/1/17.
 */
public interface EventListener<E extends Event>{
    public void onEvent(E event);
}
