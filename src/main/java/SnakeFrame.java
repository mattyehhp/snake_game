import javax.swing.*;

public class SnakeFrame extends JFrame {

    public SnakeFrame() {
        super();
        SnakePanel snakePanel = new SnakePanel();
        super.setSize(1000, 800);
        super.setLocationRelativeTo(null);
        super.add(snakePanel);
        super.setTitle("Snake");
        super.setVisible(true);

    }

}
