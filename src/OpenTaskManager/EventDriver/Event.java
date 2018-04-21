package OpenTaskManager.EventDriver;

/**
 * TODO:重构代码使得TaskManager使用事件驱动
 */

import OpenTaskManager.EventDriver.Interfaces.EventTrigger;

/**
 * 事件驱动使用的Event类
 * 这个类是所有事件的基类
 * Created by WangYH on 2017/1/17.
 */
public class Event{
    public Event(EventTrigger source) {
        this.source = source;
    }

    public EventTrigger getSource() {
        return source;
    }

    private EventTrigger source;
}
