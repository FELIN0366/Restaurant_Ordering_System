package Composition.Util;

import java.sql.DriverManager;
import java.sql.Connection;

public class JDBUtil {

    String myurl="jdbc:mysql://localhost:3306/restraurant_ordering?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    String myuser="root";
    String mypassword="220312";
    String myclassName="com.mysql.cj.jdbc.Driver";

    //建立数据库连接
    public Connection getCon() throws Exception{
        Class.forName(myclassName);//加载MySQL驱动
        Connection con=DriverManager.getConnection(myurl,myuser,mypassword);
        //建立到指定数据库的连接，并获取Connection接口
        return con;
    }

    //释放数据库连接
    public void closeCon(Connection con) throws Exception {
        if(con!=null)
            con.close();
    }

    public static void main(String[]args) throws Exception {
        JDBUtil jdbUtil=new JDBUtil();
        Connection con=null;
        try{
            con=jdbUtil.getCon();
            System.out.println("数据库连接成功！");
        }catch(Exception e){
            System.out.println("数据库连接失败！");
            e.printStackTrace();
        }finally {
            jdbUtil.closeCon(con);
        }
    }

}
