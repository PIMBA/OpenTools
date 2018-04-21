package OpenDatabaseHelper.DatabseConnection;

import OpenDatabaseHelper.Util.Database;
import OpenDatabaseHelper.Util.DatabaseConfig;

import java.sql.DriverManager;

/**
 * Created by WangYH on 2016/11/5.
 */
public class MySQLDatabase  extends Database {
    public boolean connect() {
        if(connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            try {
                String connStr = "jdbc:mysql://" + DatabaseConfig.getInstance().getDbServer()+"/"+DatabaseConfig.getInstance().getDbName();
                //System.out.println(connStr);
                connection = DriverManager.getConnection(connStr,DatabaseConfig.getInstance().getDbID(),DatabaseConfig.getInstance().getDbPassword());
                System.out.println("Connect to MySQL");
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return true;
    }
}
