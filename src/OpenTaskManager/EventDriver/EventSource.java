package OpenTaskManager.EventDriver;

import OpenTaskManager.EventDriver.Interfaces.EventTrigger;

/**
 * Created by WangYH on 2017/1/17.
 */
public class EventSource<E extends Event> implements EventTrigger<E> {
    EventTrigger<E> target = null;

    public EventSource(EventTrigger<E> eventTrigger){
        this.target = eventTrigger;
    }
    public EventSource(){
        target = null;
    }

    public E newEvent(){
        if(this.target != null) return target.newEvent();
        return null;
    }
}
