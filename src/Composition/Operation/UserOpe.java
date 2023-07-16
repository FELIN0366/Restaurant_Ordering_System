package Composition.Operation;

import Composition.Model.User;
import Composition.Util.JDBUtil;
import Composition.Util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserOpe {
    //账号添加
    public static int UserAdd(Connection con, User user) throws SQLException {
        String sql="insert into `user` values(?,?,?,?)";
        //add：先添加语句，再进行改写
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, user.getUser_id());
        pstmt.setInt(2,user.getUser_pwd());
        pstmt.setString(3, user.getE_mail());
        pstmt.setString(4,user.getPhone());
        return pstmt.executeUpdate();
    }

    //账号查找
    public static ResultSet UserList(Connection con,User user) throws SQLException {
        //search:先完整语句，再查找
        StringBuffer sql=new StringBuffer("select * from `user`");
        if(!StringUtil.isEmpty(user.getUser_id())){
            sql.append(" and `User_id` like '%"+user.getUser_id()+"%'");
        }
        PreparedStatement pstmt=con.prepareStatement(sql.toString().replaceFirst("and","where"));
        return pstmt.executeQuery();
    }

    //账号登录
    public static ResultSet UserExist(Connection con,User user) throws SQLException {
        String sql="select * from `user` where `User_id`=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, user.getUser_id());
        return pstmt.executeQuery();
    }

    //账号修改
    public static int UserModify(Connection con,User bemodi,User modi) throws SQLException {
        String sql="update `user` set `User_id`=?,`User_pwd`=?,`User_mail`=?,`User_phone`=? where `User_id`=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,bemodi.getUser_id());
        pstmt.setInt(2,bemodi.getUser_pwd());
        pstmt.setString(3,bemodi.getE_mail());
        pstmt.setString(4,bemodi.getPhone());
        pstmt.setString(5,modi.getUser_id());
        return pstmt.executeUpdate();
    }

    public static void main(String[]args) throws Exception {
        User user=new User("Felin");

        JDBUtil jdbUtil=new JDBUtil();
        Connection con= jdbUtil.getCon();

        //UserAdd(con,user);
        ResultSet re=UserExist(con,user);
        if(re.next()){
            System.out.println(re.getString("User_id"));
        }else{
            System.out.println("no");
        }
        jdbUtil.closeCon(con);
    }
}
