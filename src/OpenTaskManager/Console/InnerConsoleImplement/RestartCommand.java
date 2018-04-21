package OpenTaskManager.Console.InnerConsoleImplement;

import OpenTaskManager.Console.IConsole;

/**
 * Created by Administrator on 2016/12/22.
 */
public class RestartCommand implements IConsole {
    @Override
    public Object excute(String[] args) {
        new StopCommand().excute(args);
        new StartCommand().excute(args);
        return null;
    }
}
