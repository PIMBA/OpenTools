package OpenDatabaseHelper.Util;

import OpenDatabaseHelper.LiteIOC.Inject;
import OpenDatabaseHelper.LiteIOC.LiteIncject;
import OpenDatabaseHelper.LiteIOC.SimpleInjectHandlerImp.String2BooleanHandler;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * DatabaseConfig
 * 数据库设置类
 * Created by WangYH on 2016/8/5.
 */
public class DatabaseConfig implements Serializable{
    //DB_CONFIG
    @Inject(handler = String2BooleanHandler.class) private boolean debug;
    @Inject private String dbServer;
    @Inject private String dbID;
    @Inject private String dbPassword;
    @Inject private String dbName;
    //INSTANCE
    private static DatabaseConfig instance = null;
    public static DatabaseConfig getInstance(){
        if(instance == null)
            instance = loadConfigFromXML();
        return instance;
    }

    //getter
    public String getDbServer() {
        return dbServer;
    }
    public String getDbID() {
        return dbID;
    }
    public String getDbPassword() {
        return dbPassword;
    }
    public String getDbName() {
        return dbName;
    }
    public boolean isDebug(){return debug;}

    private static DatabaseConfig loadConfigFromXML(){
        try {
            System.out.println("Loading main.Database settings");
            String fileName = System.getProperty("user.dir")+"\\Config\\config.xml";
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            Document document = builder.parse(new File(fileName));
            //Read doc and build a config
            DatabaseConfig config = new DatabaseConfig();

            //Use LiteIOC
            Map<String,Object> injectMap = new HashMap<>();
            injectMap.put("dbServer",document.getElementsByTagName("SERVER").item(0).getFirstChild().getNodeValue());
            injectMap.put("dbID",document.getElementsByTagName("USERNAME").item(0).getFirstChild().getNodeValue());
            injectMap.put("dbPassword",document.getElementsByTagName("PASSWORD").item(0).getFirstChild().getNodeValue());
            injectMap.put("dbName",document.getElementsByTagName("DBNAME").item(0).getFirstChild().getNodeValue());
            injectMap.put("debug",document.getElementsByTagName("DEBUG").item(0).getFirstChild().getNodeValue());

            LiteIncject.inject(config,injectMap);

            if(config != null) System.out.println("main.Database setting loaded");
            return config;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

