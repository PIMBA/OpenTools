package OpenDatabaseHelper.Util;

import OpenDatabaseHelper.LiteIOC.LiteIncject;

import java.io.FileInputStream;
import java.io.Serializable;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Database抽象类
 * 所有的Database都继承这个抽象类
 * 实现其中的connect方法即可
 * 这个方法将建立connection对象
 * Created by Administrator on 2016/7/28.
 */
public abstract class Database{
    private static int instanceCount = 0;
    protected Connection connection = null;
    protected PreparedStatement preparedStatement = null;
    protected int index = 0;
    public abstract boolean connect();
    public boolean goQuery(String sql){
        return goQuery(sql,null);
    }
    public boolean goQuery(String sql,IDataJob iDataJob){
        Statement statement = null;
        ResultSet res = null;
        if(connection == null) connect();
            try {
                statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
                res = statement.executeQuery(sql);
            while (res.next()) if(iDataJob != null) iDataJob.call(res);
        }catch (Exception e){
            e.printStackTrace();
                try {
                    res.close();
                    statement.close();
                } catch (SQLException ee) {
                    ee.printStackTrace();
                }
            return false;
        }
        try {
            res.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("SQL ERROR:"+e.getErrorCode());
            e.printStackTrace();
        }
        return true;
    }
    public boolean goQuery()  {
        try {
            this.preparedStatement.execute();
            this.preparedStatement.close();
            this.index = 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean goUpdate(String sql){
        Statement statement = null;
        if(connection == null) connect();
        try{
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            try {
                statement.close();
            } catch (SQLException ee) {
                ee.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public boolean close(){
        if(connection != null)
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
    }
    public Database(){
        instanceCount ++;
    }
    public Connection getConnection(){return this.connection;}
    public Database setPreparedStatement(String sql){
        if(preparedStatement == null){
            if(this.connection == null) connect();
            try {
                preparedStatement = this.connection.prepareStatement(sql);
                this.index = 1;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return this;
    }
    public Database add(Object x,Class c){
        try {
            if(c == int.class) this.preparedStatement.setInt(index,(int)x);
            if(c == double.class) this.preparedStatement.setDouble(index,(double)x);
            if(c == Date.class) this.preparedStatement.setDate(index,(Date)x);
            if(c == String.class) this.preparedStatement.setString(index,(String)x);
            if(c == FileInputStream.class) this.preparedStatement.setBinaryStream(index,(FileInputStream)x);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        index ++;
        return this;
    }
    public void goQueryOneBean(String sql, Serializable bean){
        this.goQuery(sql, o -> {
            try {
                Map<String,Object> map = new HashMap<>();
                for(int i = 1;i <= o.getMetaData().getColumnCount();i++){
                    String cName = o.getMetaData().getColumnName(i);
                    Object value = o.getObject(o.getMetaData().getColumnName(i));
                    map.put(cName,value);
                }
                LiteIncject.inject(bean,map);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void finalize() throws Throwable {
        if(this.connection != null)
            connection.close();
        connection = null;
        instanceCount--;
        super.finalize();
    }
    public static int getInstanceCount(){return instanceCount;}
}
