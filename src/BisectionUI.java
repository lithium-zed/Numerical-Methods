import javax.swing.*;
import java.awt.*;

public class BisectionUI extends JFrame {
    JToggleButton two_decimal, three_decimal, four_decimal;
    JTable tableContent;
    JLabel x0, x1, terminating_condition;
    JTextField x0_field, x1_field, terminating_condition_field;
    Container container;
    BorderLayout layout;

    public BisectionUI() throws HeadlessException {
        container = this.getContentPane();
        layout = new BorderLayout();
        container.setLayout(layout);







        this.setVisible(true);
        this.setTitle("Bisection Method");
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
