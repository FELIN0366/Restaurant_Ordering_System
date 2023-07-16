package Composition.View;

import Composition.Model.Food;
import Composition.Model.Order_food;
import Composition.Operation.FoodOpe;
import Composition.Operation.OrderOpe;
import Composition.Util.JDBUtil;
import Composition.Util.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;

//JInternalFrame用于设置了JDesktopPane上，主要优点是通过小窗口模式，可不断在同一个页面打开与关闭
//但是对于该程序本身已有JPanel作底而言，则显得非常不方便，因为add顺序是从上往下排的，无法做程序更新，也不知道怎么调回去
//处于尊重，对该方法实现留底

//public class CheckOrder extends JInternalFrame{
public class CheckOrder extends JFrame{
    private JPanel panel;
    private JLabel Title;
    private JButton delete;
    private JButton confirm;
    private JPanel jpdetail;
    private JLabel Amount;
    private JLabel Sum;
    private JPanel jp1;
    private JPanel jp2;
    private JScrollPane js1;
    private JButton back;
    private JLayeredPane layeredPane;

//    public CheckOrder() throws Exception {
//        setContentPane(panel1);
//        setBounds(650, 250, 300, 500);
//        setResizable(false);
//        setClosable(true);
//        setMaximizable(false);
//        setIconifiable(false);//最小化
//        setTitle("账单详情");
//
//    }

    public CheckOrder() throws Exception {
        initComponents();
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("国际校区点餐系统");
        this.setLayeredPane(layeredPane);
        this.setBounds(450,250,750,500);
    }

    private void initComponents() throws Exception {
        //设置背景
        layeredPane=new JLayeredPane();
        JPanel background=new JPanel();
        background.setBounds(0,0,750,500);
        JLabel bg=new JLabel(itran("src/Composition/util_images/Entrance.jpg",750,500));
        background.add(bg);
        layeredPane.add(background,JLayeredPane.DEFAULT_LAYER);

        //设置panel
        panel.setBackground(new Color(255,255,255,200));
        panel.setBounds(180,70,400,320);//panel一定要设置大小才能显出来
        layeredPane.add(panel,JLayeredPane.MODAL_LAYER);

        jp1.setBackground(new Color(255,255,255,0));
        jp2.setBackground(new Color(255,255,255,0));
        js1.setBackground(new Color(255,255,255,0));

        detail_view();

        back=new JButton();
        back.setBounds(320,390,150,30);
        back.setText("返回首页");
        back.setFont(new Font(null,Font.BOLD,18));
        layeredPane.add(back,JLayeredPane.POPUP_LAYER);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new UserMenu().setVisible(true);
            }
        });
    }

    //可见：通过order_id获取order_food中的food_name,food_amount,food_sum_price
    //创建panel,将这些数据用panel排列好加入到jpdetail当中
    public void detail_view() throws Exception {
        JDBUtil jdbUtil=new JDBUtil();
        Connection con=null;
        GridLayout layout=new GridLayout(0,1);
        layout.setVgap(10);
        jpdetail.setLayout(layout);
        int num=0;
        float sum=0;
        try{
            con= jdbUtil.getCon();
            ResultSet re= OrderOpe.OrderfoodList(con,new Order_food(OrderFood.Order_id));
            while(re.next()){
                String name=re.getString("Food_name");
                String amount="X"+Integer.toString(re.getInt("Food_amount"));
                String price= StringUtil.FtoY(re.getFloat("Food_sum_price"));//xx元

                String url="";
                ResultSet rs= FoodOpe.Foodlist(con,new Food(name));
                while (rs.next()){ url=rs.getString("Food_image"); }

                //使用GridBagLayout进行布局，通过设置每个组件的相对位置来实现
                GridBagLayout gridbag=new GridBagLayout();//布局管理器
                GridBagConstraints c=new GridBagConstraints();//约束
                JPanel jp=new JPanel(gridbag);

                JLabel img=new JLabel(itran(url,100,75));//照片
                JLabel names=new JLabel("   "+name);//名字
                names.setVerticalAlignment(SwingConstants.CENTER);
                names.setFont(new Font(null,Font.BOLD,14));
                names.setPreferredSize(new Dimension(100,75));

                JLabel jl1=new JLabel(amount);
                jl1.setFont(new Font(null,Font.PLAIN,12));
                jl1.setVerticalAlignment(SwingConstants.CENTER);
                jl1.setHorizontalAlignment(SwingConstants.CENTER);
                JLabel jl2=new JLabel(price);
                jl2.setForeground(Color.red);
                jl2.setVerticalAlignment(SwingConstants.CENTER);
                jl2.setHorizontalAlignment(SwingConstants.CENTER);

                c.gridheight=2;
                c.gridwidth=4;
                c.fill=GridBagConstraints.BOTH;
                gridbag.addLayoutComponent(img,c);

                c.gridheight=2;
                c.gridwidth=8;
                c.fill=GridBagConstraints.BOTH;
                gridbag.addLayoutComponent(names,c);

                c.gridheight=1;
                c.gridwidth=GridBagConstraints.REMAINDER;
                gridbag.addLayoutComponent(jl1,c);

                c.gridheight=1;
                c.gridwidth=GridBagConstraints.REMAINDER;
                c.fill=GridBagConstraints.BOTH;
                gridbag.addLayoutComponent(jl2,c);

                jp.add(img);
                jp.add(names);
                jp.add(jl1);
                jp.add(jl2);
                jpdetail.add(jp);

                num+=re.getInt("Food_amount");
                sum+=re.getFloat("Food_sum_price");
            }
            Amount.setText("共计菜品"+num+"件");
            Sum.setText("应付"+StringUtil.FtoY(sum));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jdbUtil.closeCon(con);
        }
    }
    private Icon itran(String name,int w,int h){
        ImageIcon image=new ImageIcon(name);
        Image img=image.getImage();
        img=img.getScaledInstance(w,h,Image.SCALE_DEFAULT);
        image.setImage(img);
        return image;
    }

//    public static void main(String[]args) throws Exception {
//        new CheckOrder().setVisible(true);
//    }

}
