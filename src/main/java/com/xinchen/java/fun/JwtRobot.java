package com.xinchen.java.fun;


import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * Robot类模拟鼠标、键盘事件
 * 模拟移动鼠标，在某些长时间未操作会断开的终端上，保持操作事件，防止断开
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2020/11/23 21:28
 */
public class JwtRobot extends Frame {
    static Robot robot;
    /** 创建一个窗口对象 */
    static Frame frame = new JwtRobot("测试窗口");

    private static final int SIZE = 888;

    public JwtRobot(String title){
        super(title);
    }

    static {
        try {
            robot = new Robot();
            //设置Robot产生一个动作后的休眠时间,否则执行过快
            robot.setAutoDelay(3000);


        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    static void initFrame(){

        // 必须设置为true，否则看不见
        frame.setVisible(true);
        // 设置窗口标题，否则窗口无标题
        frame.setTitle("测试标题");
        // 必须设置宽高，否则没有窗体
        frame.setSize(SIZE, SIZE);

        // 将窗口居中。若无该方法，窗口将位于屏幕左上角
        frame.setLocationRelativeTo(null);
        // 设置窗口背景色。默认白色
        frame.setBackground(Color.BLACK);

        // 为窗口注册监听器，实现窗口关闭功能
        frame.addWindowListener(new WindowAdapter() {
            // 点击了窗口右上角的叉号按钮
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose(); // 关闭窗口
            }
        });
    }

    static void action(){
        // 将鼠标位移到中央
        Point location = frame.getLocation();

        int offsetX = frame.getWidth() / 2;
        int offsetY = frame.getHeight() / 2;

        // 鼠标偏移量 - 以中心点为原点
        int offsetAdd = SIZE / 2;
        for (int i =1; i < offsetAdd; i++) {
            robot.mouseMove(location.x + offsetX + i,  location.y +offsetY +i);
            System.out.format("当前鼠标位置： %d - %d",location.x + offsetX + i,  location.y +offsetY +i);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            System.out.println("按住鼠标左键.");
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            System.out.println("释放鼠标左键.");
        }
    }

    static void actionRandom(){
        // 将鼠标位移到中央
        Point location = frame.getLocation();
        // 鼠标偏移量 - 以左上角为原点 0,0
        int offset = SIZE - 1 ;

        int x;
        int y;

        for (;;){
            x = location.x  + ThreadLocalRandom.current().nextInt(offset);
            y = location.y + ThreadLocalRandom.current().nextInt(offset);
            System.out.format("移动鼠标至位置： %d,%d\n",x,y);
            robot.mouseMove(x,y);

            System.out.println("按住鼠标左键.");
            robot.mousePress(InputEvent.BUTTON1_MASK);

            System.out.println("释放鼠标左键.");
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        }
    }

    public static void main(String[] args) {
        initFrame();
//        action();
        actionRandom();
    }
}
