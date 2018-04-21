package OpenDatabaseHelper.DatabseConnection;



import OpenDatabaseHelper.Util.Database;
import OpenDatabaseHelper.Util.IDataJob;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 连接Postgresql数据库
 * Created by WangYH on 2016/7/28.
 */
public class PostgreSQLDatabase extends Database {
    @Override
    public boolean connect() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "admin");
            System.out.println("Connect to postgreSQL");
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
}

class PrintJob implements IDataJob {
    @Override
    public void call(ResultSet o) {
        try {
            for(int i = 1;i <= o.getMetaData().getColumnCount();i++)
                System.out.print(o.getMetaData().getColumnName(i)+" ");
            System.out.print("\n");
            for(int i = 1;i <= o.getMetaData().getColumnCount();i++)
                System.out.print(o.getString(o.getMetaData().getColumnName(i))+" ");
            System.out.print("\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class PostgreSQLTestToolMain{
    public static void main (String [] args){
        new PostgreSQLDatabase().goQuery("select * from pg_tables where tablename='SH_D_MODEL'",new PrintJob());
        new PostgreSQLDatabase().goQuery("select * from \"SH_D_MODEL\"");
    }
}
