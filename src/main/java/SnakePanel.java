import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.Random;

public class SnakePanel extends JPanel implements KeyListener, ActionListener {
    private int[] snakeX = new int[500];
    private int[] snakeY = new int[500];
    private int foodX;
    private int foodY;
    private int length;
    private String direction;
    int score;

    private boolean isStarted;
    private boolean isFailed;

    Random r = new Random();
    Timer timer = new Timer(100,this);

    public SnakePanel() {
        init();
        this.setFocusable(true);
        this.addKeyListener(this);
        timer.start();
    }

    private void init() {
        length = 3;
        snakeX[0] = 100;
        snakeX[1] = 75;
        snakeX[2] = 50;
        snakeY[0] = 100;
        snakeY[1] = 100;
        snakeY[2] = 100;
        eat();
        direction = "R";
        isFailed = false;
        isStarted = false;
        score = 0;

    }

    private void eat() {
        int x= 25 + 25 * r.nextInt(34);
        int y= 75 + 25 * r.nextInt(24);
        for (int i = 0; i < length; i++) {
            while (snakeX[i] == x && snakeY[i] == y) {
                x = 25 + 25 * r.nextInt(34);
                y = 75 + 25 * r.nextInt(24);
            }
        }
        foodX = x;
        foodY = y;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.gray);
        //head
        g.setColor(Color.red);
        g.fillRect(snakeX[0], snakeY[0], 25, 25);
        //body
        g.setColor(Color.green);
        for (int i = 1; i < length; i++) {
            g.fillRect(snakeX[i], snakeY[i], 25, 25);
        }
        //food
        g.setColor(Color.white);
        g.fillRect(foodX, foodY, 25, 25);


        System.out.println(snakeX[0] + ": " + snakeY[0]);
        g.setColor(Color.blue);
        g.setFont(new Font("幼圓",Font.BOLD,20));
        g.drawString("長度："+length,730,30);
        g.drawString("得分："+score,730,60);

        if (isStarted==false){
            g.setColor(Color.BLUE);
            g.setFont(new Font("幼圓",Font.BOLD,40));
            g.drawString("按空格鍵開始遊戲",5,750);
        }

        if (isFailed){
            g.setColor(Color.RED);
            g.setFont(new Font("幼圓",Font.BOLD,40));
            g.drawString("遊戲失敗，按空格鍵重新開始",5,750);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isStarted && !isFailed){
            //移動身體
            for (int i = length-1; i > 0; i--) {
                snakeX[i] = snakeX[i-1];
                snakeY[i] = snakeY[i-1];
            }
            //移動頭部
            if (direction == "R") {
                snakeX[0] += 25;
                if (snakeX[0] >= 1000) {
                    snakeX[0] = 0;
                }
            }
            else if (direction == "L") {
                snakeX[0] -= 25;
                if (snakeX[0] <= -1){
                    snakeX[0] = 975;
                }
            }
            else if (direction == "U") {
                snakeY[0] -= 25;
                if (snakeY[0] <= -1){
                    snakeY[0] = 750;
                }
            }
            else if (direction == "D") {
                snakeY[0] += 25;
                if(snakeY[0] > 750){
                    snakeY[0] = 0;
                }
            }
            //吃食物
            if (snakeX[0] == foodX && snakeY[0] == foodY) {
                length++;
                snakeX[length -1] = snakeX[length -2];
                snakeY[length -1] = snakeY[length -2];
                score += 10;
                eat();
            }
            //死亡判定
            for (int i = 1; i < length; i++) {
                if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                    isFailed=true;
                }
            }
            repaint();
        }
        timer.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
            if (isFailed) {
                init();
            } else {
                isStarted = !isStarted;
            }
            repaint();
        }

        if (key == KeyEvent.VK_LEFT && direction != "R"){
            direction = "L";
        }
        else if (key == KeyEvent.VK_RIGHT && direction != "L"){
            direction = "R";
        }
        else if (key == KeyEvent.VK_UP && direction != "D"){
            direction = "U";
        }
        else if (key == KeyEvent.VK_DOWN && direction!= "U"){
            direction = "D";
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
