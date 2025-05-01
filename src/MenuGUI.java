import javax.swing.*;
import java.awt.*;

public class MenuGUI extends JFrame {
    JTextField functions;
    JButton bisection, secant, doolittle, gauss_seidel, simpson1_3;
    Container container;
    BorderLayout layout;

    public MenuGUI() throws HeadlessException {
        container = this.getContentPane();
        layout = new BorderLayout();
        container.setLayout(layout);

        this.setVisible(true);
        this.setTitle("Numerical Methods");
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
