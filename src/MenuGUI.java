import javax.swing.*;
import java.awt.*;

public class MenuGUI extends JFrame {
    JTextField functionsFL;
    JLabel function;
    JButton bisection, secant, doolittle, gauss_seidel, simpson1_3;
    JPanel panel2;
    Container container;
    BorderLayout layout;

    public MenuGUI() throws HeadlessException {
        container = this.getContentPane();
        layout = new BorderLayout();
        container.setLayout(layout);

        function = new JLabel("f(x)=");
        functionsFL = new JTextField(10);
        bisection = new JButton("Bisection Method");
        secant = new JButton("Secant Method");
        doolittle = new JButton("Doolittle Method");
        gauss_seidel = new JButton("Gauss-Seidel Method");
        simpson1_3 = new JButton("Simpon 1/3 Rule Method");

        JPanel panel1 = new JPanel(new FlowLayout());
        panel1.add(function);
        panel1.add(functionsFL);
        container.add(panel1, BorderLayout.NORTH);

        panel2 = new JPanel(new GridBagLayout());
        addToPanel2(bisection,0,0,1);
        addToPanel2(secant,0,1,1);
        addToPanel2(doolittle,0,2,1);
        addToPanel2(gauss_seidel,0,3,1);
        addToPanel2(simpson1_3,0,4,1);
        container.add(panel2, BorderLayout.CENTER);



        this.setVisible(true);
        this.setTitle("Numerical Methods");
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void addToPanel2(Component component, int gridx, int gridy, int gridW){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridW;
        panel2.add(component,gbc);
    }

}
