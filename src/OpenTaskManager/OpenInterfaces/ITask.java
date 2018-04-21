package OpenTaskManager.OpenInterfaces;

/**
 * Created by WangYH on 2016/12/22.
 */
public interface ITask {
    void beforeRun();
    boolean run();
    void success();
    void fail();
}
