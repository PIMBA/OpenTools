package OpenTaskManager;

import OpenTaskManager.Console.IConsole;
import OpenTaskManager.Console.ConsoleEntry;
import OpenTaskManager.Console.InnerConsoleImplement.*;
import OpenTaskManager.Log.LogFactory;
import OpenTaskManager.TaskManager.TaskPool;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Created by WangYH on 2016/12/22.
 */
public class Main {
    public static Thread backgroundThread = null;
    public static TaskPool taskPool = null;
    private static ScriptEngineManager manager = new ScriptEngineManager();
    public static void run(String agrs[]){

        System.out.println("program started");

        ConsoleEntry.regist(StartCommand.class,"start");
        ConsoleEntry.regist(StopCommand.class,"stop");
        ConsoleEntry.regist(RestartCommand.class,"restart");
        ConsoleEntry.regist(SizeCommand.class,"size");
        ConsoleEntry.regist(AllCommand.class,"all");

        while(true){
            Scanner sc = new Scanner(System.in);
            String s = sc.nextLine();
            if(!s.equals("")) excuteConsole(s);
        }
    }
    private static void excuteConsole(String msg) {
        String[] msgs = msg.split(" ");
        IConsole iConsole = null;
        try {
            iConsole = (IConsole) ConsoleEntry.get(msgs[0]);
        } catch (Exception e) {
            LogFactory.logWarning("Cannot find JAVA implement @NAME:" + msgs[0] + " , finding in javascript");
            try {
                FileReader fileReader = new FileReader(System.getProperty("user.dir") + "\\Console\\" + msgs[0] + ".js");
                ScriptEngine engine = manager.getEngineByName("js");
                engine.eval(fileReader);
                iConsole = ((Invocable) engine).getInterface(IConsole.class);
            } catch (FileNotFoundException ex) {
                LogFactory.logWarning("Some error occur in finding " + msgs[0]);
                ex.printStackTrace();
                return ;
            } catch (ScriptException ex) {
                LogFactory.logWarning("Some error occur on excute " + msg);
                ex.printStackTrace();
                return ;
            }
        }
        if(iConsole != null) try{
            iConsole.excute(msgs);
        } catch (Exception ex) {
            LogFactory.logWarning("Some error occur on excute " + msg);
            ex.printStackTrace();
            return ;
        }
    }
}
