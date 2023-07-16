package Composition.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Extrance extends JFrame{

    private JButton User;
    private JButton 员工入口Button;
    private JPanel panel;
    private JPanel jp1;
    private JPanel jp2;
    private JPanel jp3;
    private JLabel icon;

    private JLayeredPane layeredPane;

    public Extrance(){
        initComponets();
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("国际校区点餐系统");
        this.setLayeredPane(layeredPane);
        this.setBounds(450,250,750,500);
    }
    private void initComponets(){
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

        jp1.setBackground(new Color(255,255,255,0));
        jp2.setBackground(new Color(255,255,255,0));
        jp3.setBackground(new Color(255,255,255,0));

        icon.setIcon(itran("src/Composition/util_images/icon.png",80,70));

        User.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new UserLogin().setVisible(true);
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

//    public static void main(String[]args){
//        new Extrance().setVisible(true);
//    }
}
