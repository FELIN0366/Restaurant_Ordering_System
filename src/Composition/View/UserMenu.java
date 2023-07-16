package Composition.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserMenu extends JFrame{
    private JPanel panel;
    private JLabel jl1;
    private JLabel jl2;
    private JLabel jl3;
    private JLabel jl4;
    private JLabel Title;

    public UserMenu(){
        initComponents();
        this.setTitle("国际校区点餐系统");
        this.setBounds(450,250,750,500);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayeredPane(layeredPane);

    }
    private void initComponents(){
        //设置背景
        layeredPane=new JLayeredPane();
        JPanel background=new JPanel();
        background.setBounds(0,0,750,500);
        JLabel bg=new JLabel(itran("src/Composition/util_images/Entrance.jpg",750,500));
        background.add(bg);
        layeredPane.add(background,JLayeredPane.DEFAULT_LAYER);

        //设置panel
        panel.setBackground(new Color(255,255,255,200));
        panel.setBounds(180,100,400,250);//panel一定要设置大小才能显出来
        layeredPane.add(panel,JLayeredPane.MODAL_LAYER);

        Title.setPreferredSize(new Dimension(10,40));

        jl1.setIcon(itran("src/Composition/util_images/dine.png",150,110));
        jl2.setIcon(itran("src/Composition/util_images/historydebt.png",150,110));
        jl3.setIcon(itran("src/Composition/util_images/info.png",150,110));
        jl4.setIcon(itran("src/Composition/util_images/exit.png",150,110));

        //到店点餐
        jl1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
                try {
                    new OrderFood().setVisible(true);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        //历史账单
        jl2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
                try {
                    new HistoryList().setVisible(true);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        //个人信息
        jl3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
                new UserInfo().setVisible(true);
            }
        });
        //退出登录
        jl4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                UserLogin.current_user=null;//注销用户
                dispose();
                new Extrance().setVisible(true);
            }
        });
    }
    private Icon itran(String name,int w,int h){
        ImageIcon image=new ImageIcon(name);
        Image img=image.getImage();
        img=img.getScaledInstance(w,h,Image.SCALE_DEFAULT);
        image.setImage(img);
        return image;
    }

    public static void main(String[]args){
        new UserMenu().setVisible(true);
    }
    private JLayeredPane layeredPane;
}
