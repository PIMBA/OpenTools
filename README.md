> 这个项目由 [这个项目](https://coding.net/u/microJW/p/OpenTaskManager) 和 [这个项目](https://coding.net/u/microJW/p/OpenDatabaseHelper) 迁移合并.

# OpenTaskManager
> 某科研立项的子项目之一，旨在简化代码编写，本质上是一个任务队列管理器。
> 旨在提供一个简单的交互式控制台和可扩展的指令系统以及一个简单的任务队列管理器。
> 这个项目并不包含任何机器学习的部分，是纯JAVA的。
> 这个项目在已经开源。

## 使用TaskManager
> 使用TaskManager来管理任务可以让开发者更专注于业务而不用去考虑底层的多任务细节。

### 基础设置:
导入JAR包或项目源代码
``` java
    import OpenTaskManager;
```
启动监听:
``` java
    String [] args = new String [1];
    Main.run(args);
```
### 使用内置命令：
``` shell
    > start {name}
    > stop {name}
    > restart {name}
    > size {name} {size}
    > all
```
TaskManager内置了5个基础命令来执行一些关于服务的操作:
1. start{name}开启某个服务
2. stop {name}关闭服务
3. restart {name} 重启服务。
4. size {name} {size} 改变某个服务的线程数
5. all 查看所有正在执行的任务管理器
### 任务实现：
> TaskManager 使用一组接口和一组管理器来管理整个任务的调度。
只需要实现这些接口并在管理器中进行注册便可以完成从任务的执行到释放的整个过程。

#### 实现ITask接口：
> ITask接口是定义任务具体行动的接口，一共由4个方法组成。
``` java
public class fooTask implements ITask {
    void beforeRun(){
        //init body
    }
    boolean run(){
        if(1=1) return true;//进入success();
        return false;//进入fail();
    }
    void success(){
        //success body
    }
    void fail(){
        //fail body
    }
}
```
#### 实现ITaskBuilder：
> ITaskBuilder 是定义任务如何构建的接口，其中只有一个build方法。
> 以每3秒查询数据库并构建一个模型任务为例：
```java
public class ModelTrainBuilder implements ITaskBuilder,IDataJob{
    static Database database = new MySQLDatabase();
    private int id = 0;
    @Override
    public ITask build() {
        database.goQuery("SELECT * FROM task WHERE state = 0",this);
        if(id == 0){
            try{
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        return new ModelTrainTask(id);
    }
    @Override
    public void call(ResultSet o) {
        try{
            id = o.getInt("id");
        } catch (SQLException e) {
            id = 0;
        }
    }
}
```
#### 注册TaskBuilder：
```java
    TaskBuilderFactory.regist(ModelTrainBuilder.class,"trainModel");
```
现在就可以使用trainModel这个名字进行任务了。
``` shell
    > start trainModel
```

###扩展命令：
> 只需要实现IConsole接口并在ConsoleEntry中注册名字就可以使用该命令了

```java
public class FooCommand implements IConsole{
    @Override
        public void excute(String[] args){
            //TODO: DO YOUR JOB WITH ARGS
        }
}
```

```java
    ConsoleEntry.regist(FooCommand.class,"foo");
```


### 使用JavaScript扩展命令
> TaskManager中内置了一个JavaScript解释器
> 使用JavaScript来扩展一些简单的指令而省去繁琐的暂停服务器和重新编译的步骤
> 详细的教程请参阅[Nashron](https://en.wikipedia.org/wiki/Nashorn_(JavaScript_engine))官方提供的[教程](http://winterbe.com/posts/2014/04/05/java8-nashorn-tutorial/)，这里也有[中文版](https://segmentfault.com/a/1190000006041626)

在Console中新建文件:foo.js，在文件中如下书写：
```javascript
load("nashorn:mozilla_compat.js");
function excute(args) {
  //TODO: DO YOUR JOB
}
```
实现excute方法即可，之后就可以使用：
``` shell
    > foo [args]
```
来执行命令了。

## Release Note

### 2017-1-24
> 以后也许会再用EventDriver重构？

#### 更新内容：

    1. 加入了尚未使用的事件驱动框架。
    2. 重写了TaskPool，现在不会有高额的CPU开销了。
    3. 现在可以同时运行多个任务。


# OpenDatabaseHelper
> DatabaseHelper旨在提供一个简单的数据库访问接口，屏蔽底层的访问细节并保留原始的访问性能。
> 这个项目是某科研立项的一部分，但这部分代码并不涉及任何的机器学习框架，是纯JAVA的。
> 也许将来某一天这个项目会进行开源。

## 使用

### 基础设置
导入jar或DatabseHepler源码包：
```java
    import OpenDatabaseHepler;
```
配置数据库设置在Config/config.xml中：
```xml
<SETTING>
        <SERVER>localhost:1366</SERVER>
        <USERNAME>root</USERNAME>
        <PASSWORD> </PASSWORD>
        <DBNAME>CNN</DBNAME>
        <DEBUG>true</DEBUG>
</SETTING>
```
创建托管的Database实例(以MYSQL 为例)：
```java
    MySQLDatabase database = new MySQLDatabase();
```
这个实例是由DatabaseHepler自动管理的，你只需要查询即可。


### 查询：
> OpenDatabaseHelper使用原生的SQL进行查询，这要求了后端人员必须掌握一定的SQL语法基础。

OpenDatabaseHelper提供了一些很好用的查询方法：
```java
//查询并不进行任务操作
public boolean goQuery(String sql);//查询并不进行任务操作
public boolean goQuery(String sql,IDataJob iDataJob);//查询并对数据进行一些操作
public boolean goQuery();//查询已经准备好的语句。
public boolean goUpdate(String sql);//对数据库进行一些更改
public boolean close();//手动关闭链接
public Connection getConnection();//使用原生的connection进行操作。
public Database setPreparedStatement(String sql);//设置准备语句
public Database add(Object x,Class c);//在准备语句中填入参数
public void goQueryOneBean(String sql,Serializable bean);//使用查询结果填充一个bean对象。
```

使用preparedStatement传递二进制数据实例：
```java
Database database = new MySQLDatabase();
String sql = "INSERT INTO task (name,params)VALUE (?,?)";
 database.setPreparedStatement(sql)
                .add(modelName,String.class)
                .add(new FileInputStream(new File(modelName)),FileInputStream.class)
                .goQuery();
```

### 扩展
使用IDataJob（以打印任务为例）：
```java
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
```
Job在查询出的每一行中都会执行，在Job中**不需要**手动循环每一列（事实上也没有提供相应的方式）。

扩展数据库种类(以MYSQL为例)：
```java
public class MySQLDatabase  extends Database {
    public boolean connect() {
        if(connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                System.out.println(e);
            }
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + DatabaseConfig.getInstance().getDbServer()+"/"+DatabaseConfig.getInstance().getDbName(), DatabaseConfig.getInstance().getDbID(), DatabaseConfig.getInstance().getDbPassword());
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
```
只需要定义connet()函数就好，如此简单。当然***别忘了引入相应的JDBC驱动***。

## Release Note

### 2017-1-24
> 加入了LiteIOC,似乎越来越像Spring了。

#### 更新内容：
    1. 加入了新的依赖注入框架-LiteIOC以注入bean对象
    2. 修复了一些BUG

#### LiteIOC
> LiteIOC是一个轻量级的依赖注入组件，能够快速的注入bean对象。
> 并提供了灵活的接口，使得注入过程是用户可控的。

LiteIOC使用是非常简单的：
```java
    class Foo implements Serializable{
        @Inject String name;
        @Inject(value = "fName",handler = fooHandler.class) Integer intNmae ;
    }
    
    /**
    * fooHandler 用来指定如何将一个对象注入到bean中
    */
    class fooHandler implements LiteIOC.IInjectHandler<Integer>{
        @Override
        public Integer excute(Object x){
            return (Integer)x;
        }
        
        public static void main(String [] agrs){
            Map<String,Object> map = new HashMap<>();
            map.put("name","nameVal");
            map.put("fName",Integer.parseInt("4"));
            Foo foo = new Foo();
            LiteIncject.inject(foo,map);
        }
    }
```
