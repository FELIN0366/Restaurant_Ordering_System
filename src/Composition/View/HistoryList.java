package Composition.View;

import Composition.Model.Food;
import Composition.Model.Order_food;
import Composition.Model.Order_info;
import Composition.Model.User;
import Composition.Operation.FoodOpe;
import Composition.Operation.OrderOpe;
import Composition.Util.JDBUtil;
import Composition.Util.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoryList extends JFrame{
    public HistoryList() throws Exception {
        initComponents();
        this.setTitle("国际校区点餐系统");
        this.setBounds(450,250,750,500);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayeredPane(layeredPane);
    }
    private void initComponents() throws Exception {
        //设置背景
        layeredPane=new JLayeredPane();
        JPanel background=new JPanel();
        background.setBounds(0,0,750,500);
        JLabel bg=new JLabel(itran("src/Composition/util_images/Entrance.jpg",750,500));
        background.add(bg);
        layeredPane.add(background,JLayeredPane.DEFAULT_LAYER);

        //设置返回键
        JLabel back=new JLabel();
        back.setIcon(itran("src/Composition/util_images/back.jpg",60,30));
        back.setBounds(520,370,60,30);
        back.setBackground(Color.WHITE);
        layeredPane.add(back,JLayeredPane.POPUP_LAYER);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
                new UserMenu().setVisible(true);
            }
        });

        //设置panel
        Panel.setBackground(new Color(255,255,255,200));
        Panel.setBounds(180,70,400,300);//panel一定要设置大小才能显出来
        layeredPane.add(Panel,JLayeredPane.MODAL_LAYER);
        panel.setBackground(new Color(255,255,255,0));
        panel2.setBackground(new Color(255,255,255,0));

        //设置布局
        getOrderList();

    }
    private void getOrderList() throws Exception {
        //根据User_id获得Order_info&&Order_food
        JDBUtil jdbUtil=new JDBUtil();
        Connection con=null;
        try {
            con=jdbUtil.getCon();
            String userid=UserLogin.current_user.getUser_id();

            //设置布局
            GridBagLayout gbl=new GridBagLayout();//实例化布局对象
            panel.setLayout(gbl);
            GridBagConstraints bgc=new GridBagConstraints();//实例化约束对象
            bgc.gridwidth=GridBagConstraints.REMAINDER;
            bgc.gridheight=20;
            bgc.insets=new Insets(10,20,5,20);

            //订单检索
            ResultSet info= OrderOpe.OrderinfoList(con,new Order_info(userid));
            while(info.next()){
                Order_info oif=new Order_info(info.getInt("Order_id"),info.getInt("Order_table"),info.getInt("Order_status"),info.getInt("Order_Amount"),info.getFloat("Order_debt"),info.getString("User_id"),info.getString("Order_date"),info.getString("Order_time"));
                JPanel jp=new JPanel();
                jp.setBackground(Color.WHITE);
                setOrderList(jp,con,oif);//设置每个panel的子布局
                gbl.setConstraints(jp,bgc);
                panel.add(jp);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jdbUtil.closeCon(con);
        }
    }
    private void setOrderList(JPanel jp,Connection con,Order_info oi) throws Exception {
        GridBagLayout gbl=new GridBagLayout();
        jp.setLayout(gbl);
        GridBagConstraints bgc=new GridBagConstraints();
        bgc.insets=new Insets(5,20,5,5);

        //第一行
        bgc.gridheight=2;
        bgc.gridwidth=10;
        JLabel orderid=new JLabel();
        orderid.setText("订单号："+ StringUtil.order_id(oi.getOrder_id()));
        orderid.setHorizontalAlignment(SwingConstants.CENTER);
        orderid.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(121,121,121)));
        gbl.setConstraints(orderid,bgc);
        jp.add(orderid);

        bgc.gridwidth=GridBagConstraints.REMAINDER;
        JLabel status=new JLabel();
        if(oi.getOrder_Status()==1) status.setText("已完成");
        else {status.setText("进行中");status.setForeground(Color.red);}
        status.setHorizontalAlignment(2);//靠右
        status.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(121,121,121)));
        gbl.setConstraints(status,bgc);
        jp.add(status);

        //第二行：菜单
        ResultSet re=OrderOpe.OrderfoodList(con,new Order_food(oi.getOrder_id()));
        while(re.next()){
            Order_food of=new Order_food(re.getInt("Order_id"),re.getString("Food_name"),re.getFloat("Food_price"),re.getInt("Food_amount"),re.getFloat("Food_sum_price"));

            ResultSet rs= FoodOpe.FoodlistSearch(con,new Food(of.getFood_name()));
            while(rs.next()) {
                Food f=new Food(rs.getString("Food_name"),rs.getString("Food_type"),rs.getFloat("Food_price"),rs.getString("Food_image"),rs.getString("Food_detail"));
                bgc.gridwidth=5;
                bgc.gridheight=5;
                JLabel image=new JLabel();
                image.setIcon(itran(f.getFood_image(),60,50));
                gbl.setConstraints(image,bgc);
                jp.add(image);
            }

            bgc.gridwidth=10;
            bgc.gridheight=5;
            JLabel name=new JLabel();
            name.setText(of.getFood_name());
            name.setHorizontalAlignment(0);
            name.setVerticalAlignment(1);
            gbl.setConstraints(name,bgc);
            jp.add(name);//一定记得把组件加进去

            bgc.gridwidth=GridBagConstraints.REMAINDER;
            bgc.gridheight=5;
            JLabel amount=new JLabel();
            amount.setText("x"+of.getFood_amount());
            amount.setHorizontalAlignment(SwingConstants.CENTER);
            amount.setVerticalAlignment(1);
            gbl.setConstraints(amount,bgc);
            jp.add(amount);
        }

        //第三行
        bgc.gridwidth=10;
        bgc.gridheight=1;
        JLabel time=new JLabel();
        time.setText(oi.getOrder_Date()+" "+oi.getOrder_Time());
        time.setForeground(new Color(121,121,121));
        time.setHorizontalAlignment(0);
        gbl.setConstraints(time,bgc);
        jp.add(time);

        bgc.gridwidth=GridBagConstraints.REMAINDER;
        bgc.gridheight=1;
        JLabel sum=new JLabel();
        sum.setText("共"+oi.getOrder_Amount()+"件菜品  合计： "+StringUtil.FtoY(oi.getOrder_debt()));
        sum.setHorizontalAlignment(2);
        gbl.setConstraints(sum,bgc);
        jp.add(sum);

    }
    private Icon itran(String name,int w,int h){
        ImageIcon image=new ImageIcon(name);
        Image img=image.getImage();
        img=img.getScaledInstance(w,h,Image.SCALE_DEFAULT);
        image.setImage(img);
        return image;
    }
//    public static void main(String[]args) throws Exception {
//        new HistoryList().setVisible(true);
//    }
    private  JLayeredPane layeredPane;
    private JPanel Panel;
    private JScrollPane jspanel;
    private JPanel panel;
    private JPanel panel2;
}
