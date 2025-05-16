import javax.swing.*;
import java.awt.*;

public class GaussSeidel_UI extends JFrame {
    JLabel eq1, eq2, eq3;
    JTextField eq1Fl, eq2FL, eq3FL;

    public GaussSeidel_UI() throws HeadlessException {







        this.setVisible(true);
        this.setTitle("Gauss-Seidel Method");
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
