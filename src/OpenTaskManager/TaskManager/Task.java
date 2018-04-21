package OpenTaskManager.TaskManager;

import OpenTaskManager.Log.LogFactory;
import OpenTaskManager.OpenInterfaces.ITask;

import java.util.logging.Logger;

/**
 * Task类
 * 这个类用来运行实际的模型
 * Created by WangYH on 2016/8/1.
 */
class Task extends Thread {
    ITask realTask = null;
    TaskPool ref = null;
    public String id = String.valueOf(System.currentTimeMillis());

    public Task(ITask init,TaskPool who) {
        realTask = init;
        ref = who;
        this.setName(id);
    }

    @Override
    public void run() {
        LogFactory.logInfo("Task "+id+" started");
        realTask.beforeRun();
        boolean flag = realTask.run();
        if (flag) {
            realTask.success();
        }
        else {
            realTask.fail();
        }

        //notify taskPool
        ref.popFromTaskPool(this);
        LogFactory.logInfo("Task "+id+" ended");
    }
}
