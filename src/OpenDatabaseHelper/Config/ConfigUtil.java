package OpenDatabaseHelper.Config;

import OpenDatabaseHelper.LiteIOC.Inject;
import OpenDatabaseHelper.LiteIOC.LiteIncject;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by WangYH on 2017/1/30.
 */
public class ConfigUtil{
    public static Map<String,Object> loadFromXML(String fromFile,String [] nameList){
        try {
            String fileName = fromFile;
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            builder = documentBuilderFactory.newDocumentBuilder();
            Document document = builder.parse(new File(fileName));
            Map<String,Object> injectMap = new HashMap<>();
            for (String name:nameList) {
                injectMap.put(name,document.getElementsByTagName(name).item(0).getFirstChild().getNodeValue());
            }
            return injectMap;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void loadFromXML(String fromFile, Serializable bean){
        Field[] fields = bean.getClass().getDeclaredFields();
        List<String> nameList = new LinkedList<>();
        for (Field field : fields) {
            boolean flag = field.isAccessible();
            field.setAccessible(true);
            Inject inject = field.getAnnotation(Inject.class);
            String name = inject.value();
            if (name.isEmpty()) {
                name = field.getName();
            }
            nameList.add(name);
            field.setAccessible(flag);
        }
        String [] names = (String[]) nameList.toArray();
        Map<String,Object> injectMap = loadFromXML(fromFile,names);
        LiteIncject.inject(bean,injectMap);
    }
}
