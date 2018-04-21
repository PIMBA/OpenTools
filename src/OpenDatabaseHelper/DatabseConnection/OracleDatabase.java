package OpenDatabaseHelper.DatabseConnection;

import OpenDatabaseHelper.Util.Database;

import java.sql.DriverManager;

/**
 * 连接Oracle数据库
 * Created by WangYH on 2016/8/2.
 */
public class OracleDatabase extends Database {
    @Override
    public boolean connect() {
        if(connection == null) {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                System.out.println(e);
            }
            try {
                connection = DriverManager.getConnection("jdbc:oracle:thin:@" + "192.168.0.188:1521"+":"+"orcl", "daspzk", "daspzk");
                System.out.println("Connect to Oracle");
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return true;
    }
}
