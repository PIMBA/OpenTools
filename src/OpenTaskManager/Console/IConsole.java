package OpenTaskManager.Console;

/**
 * Console接口
 * 定义一个统一的控制台命令接口
 * Created by WangYH on 2016/8/8.
 */
public interface IConsole{
    Object excute(String[] args);
    default Object excute(){
        return this.excute(null);
    }
}
