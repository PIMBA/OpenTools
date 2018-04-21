package OpenTaskManager.Log;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by WangYH on 2017/1/14.
 */
public class LogFactory {
    private static final String DEBUG = "debug";
    private static final String INFO = "info";
    private static final String WARNING = "warning";
    private static final String EXCEPTION = "exception";
    private static final String ERROR = "error";
    private static final String logFilePath = "OpenTaskManager/Log";
    private static final int batchSize = 500;

    private int batchCount = 0;
    private List<String> logList = null;
    BufferedWriter bw = null;
    private int mod = 3;

    public static final int NO_LOG = 0;
    public static final int ONLY_ERROR = 1;
    public static final int ONLY_INFO = 2;
    public static final int ALL = 3;

    private LogFactory init(){
        try {
            logList = new LinkedList<>();
            bw = new BufferedWriter(new FileWriter (logFilePath+"/logFile.log"));
        } catch (IOException ignored) {
        }
        return this;
    }

    public static synchronized void setLevel(int i){
        getInstance().mod = i;
    }
    private void saveToFile(){
        if(bw == null) return;
        try {
            for (String log : logList) {
                bw.write((log + " \n "));
            }
            bw.flush();
        } catch (IOException ignored) {
        }

        logList.clear();
        batchCount = 0;

    }
    private void log(String mothed,String log){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time = df.format(new Date());// new Date()为获取当前系统时间
        batchCount ++;
        switch (mod){
            case ALL:{
                if(batchCount>=batchSize) saveToFile();
                System.out.println(time + " - "+ mothed +" - "+log);
                break;
            }
            case NO_LOG: break;
            case ONLY_ERROR:{
                if(batchCount>=batchSize) saveToFile();
                if(mothed == ERROR) System.out.println(time + " - "+ mothed +" - "+log);
                break;
            }
            case ONLY_INFO:{
                if(batchCount>=batchSize) saveToFile();
                if(mothed == INFO) System.out.println(time + " - "+ mothed +" - "+log);
                break;
            }
        }
    }
    public static void logDebug(String log){
        getInstance().log(DEBUG,log);
    }
    public static void logInfo(String log){
        getInstance().log(INFO,log);
    }
    public static void logWarning(String log){
        getInstance().log(WARNING,log);
    }
    public static void logException(String log){
        getInstance().log(EXCEPTION,log);
    }
    public static void logException(StackTraceElement[] log){
        String str = "";
        for (StackTraceElement s : log) {
            str += s.toString()+"/n";
        }
        getInstance().log(EXCEPTION,str);
    }
    public static void logError(String log){
        getInstance().log(ERROR,log);
    }
    private static LogFactory instance = null;
    private static LogFactory getInstance(){
        if(instance == null) instance = new LogFactory().init();
        return instance;
    }
    @Override
    public void finalize() throws Throwable {
        try {
            this.bw.flush();
            this.bw.close();
        } catch (IOException ignored) {
        }
        super.finalize();
    }
}
