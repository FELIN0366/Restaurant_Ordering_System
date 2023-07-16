package Composition.Operation;

import Composition.Model.Order_food;
import Composition.Model.Order_info;
import Composition.Util.JDBUtil;
import Composition.Util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderOpe {
    //add:order_food
    public static int OrderFoodAdd(Connection con, Order_food od) throws SQLException {
        String sql="insert into `order_food` values(?,?,?,?,?,null)";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setInt(1,od.getOrder_id());
        pstmt.setString(2,od.getFood_name());
        pstmt.setFloat(3,od.getFood_price());
        pstmt.setInt(4,od.getFood_amount());
        pstmt.setFloat(5,od.getFood_sum_price());
        return pstmt.executeUpdate();
    }
    public static int OrderinfoAdd(Connection con, Order_info oi) throws SQLException {
        String sql="insert into `order_info` values(null,?,?,?,?,?,?,?)";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setInt(1,oi.getOrder_table());
        pstmt.setInt(2,oi.getOrder_Status());
        pstmt.setInt(3,oi.getOrder_Amount());
        pstmt.setFloat(4,oi.getOrder_debt());
        pstmt.setString(5,oi.getUser_id());
        pstmt.setString(6,oi.getOrder_Date());
        pstmt.setString(7,oi.getOrder_Time());
        return pstmt.executeUpdate();
    }

    //查询:OrderinfoList
    public static ResultSet OrderinfoList(Connection con, Order_info oi) throws SQLException {
        StringBuffer sql=new StringBuffer("select * from `order_info`");
        if(oi.getOrder_id()!=0){
            sql.append(" and `Order_id` ="+Integer.toString(oi.getOrder_id()));
        }
        if(oi.getOrder_table()!=0){
            sql.append(" and `Order_table` ="+Integer.toString(oi.getOrder_table()));
        }
        if(oi.getOrder_Status()!=-1){
            sql.append(" and `Order_status` ="+Integer.toString(oi.getOrder_Status()));
        }
        if(!StringUtil.isEmpty(oi.getUser_id())){
            sql.append(" and `User_id` like '%"+oi.getUser_id()+"%'");
        }
        if(!StringUtil.isEmpty(oi.getOrder_Date())){
            sql.append(" and `Order_date` like '%"+oi.getOrder_Date()+"%'");//字符串匹配的话最好用相似like%XX%
        }
        if(!StringUtil.isEmpty(oi.getOrder_Time())){
            sql.append(" and `Order_time` like '%"+oi.getOrder_Time()+"%'");
        }
        //date time未写
        PreparedStatement pstmt= con.prepareStatement(sql.toString().replaceFirst("and","where"));
        return pstmt.executeQuery();
    }
    //查询:OrderfoodList
    public static ResultSet OrderfoodList(Connection con,Order_food of) throws SQLException {
        StringBuffer sql=new StringBuffer("select * from `order_food`");
        if(of.getOrder_id()!=0){
            sql.append(" and `Order_id`="+of.getOrder_id());
        }
        if(!StringUtil.isEmpty(of.getFood_name())){
            sql.append(" and `Food_name` like'%"+of.getFood_name()+"%'");
        }
        PreparedStatement pstmt=con.prepareStatement(sql.toString().replaceFirst("and","where"));
        return pstmt.executeQuery();
    }


    //修改：Order_Info
    public static int OrderInfoModify(Connection con,Order_info bemodi,Order_info modi)throws Exception{
        StringBuffer sql1=new StringBuffer("update `order_info`");
        //修改：status amount debt
        if(bemodi.getOrder_Status()!=-1){
            sql1.append(" , `Order_status` ="+bemodi.getOrder_Status());
        }
        if(bemodi.getOrder_Amount()!=0){
            //得到的应该是修改后的数量
            sql1.append(" , `Order_Amount` ="+bemodi.getOrder_Amount());
        }
        if(bemodi.getOrder_debt()!=0){
            //得到的为修改后的量
            sql1.append(" , `Order_debt` ="+bemodi.getOrder_debt());
        }
        StringBuffer sql2=new StringBuffer();
        //查询：id
        if(modi.getOrder_id()!=0){
            sql2.append(" and `Order_id` ="+modi.getOrder_id());
        }
        String sql=sql1.toString().replaceFirst(",","set")+sql2.toString().replaceFirst("and","where");
        //System.out.println(sql);
        PreparedStatement pstmt=con.prepareStatement(sql);
        return pstmt.executeUpdate();
    }
    //修改:Order_food
    public static int OrderFoodModify(Connection con,Order_food bemodi,Order_food modi)throws Exception{
        StringBuffer sql1=new StringBuffer("update `order_food`");
        //修改:amount sumprice
        if(bemodi.getFood_amount()!=0){
            sql1.append(" , `Food_amount` ="+bemodi.getFood_amount());
        }
        if(bemodi.getFood_sum_price()!=0){
            sql1.append(" , `Food_sum_price`="+bemodi.getFood_sum_price());
        }
        //查询：Order_id Food_Name
        StringBuffer sql2=new StringBuffer();
        if(modi.getOrder_id()!=0){
            sql2.append(" and `Order_id`="+modi.getOrder_id());
        }
        if(!StringUtil.isEmpty(modi.getFood_name())){
            sql2.append(" and `Food_name` like '%"+modi.getFood_name()+"%'");
        }
        String sql=sql1.toString().replaceFirst(",","set")+sql2.toString().replaceFirst("and","where");
        //System.out.println(sql);
        PreparedStatement pstmt=con.prepareStatement(sql);
        return pstmt.executeUpdate();
    }

        public static void main(String[]args) throws Exception {
        Order_food bemodi=new Order_food(7,23.8F);
        Order_food modi=new Order_food(27,"鸡肉石锅拌饭");
        JDBUtil jdbUtil=new JDBUtil();
        Connection con= jdbUtil.getCon();
        //OrderFoodModify(con,bemodi,modi);
            OrderfoodList(con,modi);
        ResultSet re=OrderfoodList(con,modi);
        while(re.next()){
            System.out.println(re.getString("Food_name"));
//            System.out.println(re.getString("Order_amount"));
//            System.out.println(re.getString("Order_debt"));
        }
        jdbUtil.closeCon(con);
    }
}
