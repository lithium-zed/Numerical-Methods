import javax.swing.*;
import java.awt.*;

public class BisectionUI extends JFrame {
    JRadioButton two_decimal, three_decimal, four_decimal;
    JTable tableContent;
    BisectionTableModel tableModel;
    JLabel x0, x1, terminating_condition;
    JTextField x0_field, x1_field, terminating_condition_field;
    JPanel panel, decimalPanel;
    Container container;
    BorderLayout layout;

    public BisectionUI() throws HeadlessException {
        container = this.getContentPane();
        layout = new BorderLayout();
        container.setLayout(layout);


        x0 = new JLabel("x0=");
        x1 = new JLabel("x1=");
        terminating_condition = new JLabel("EA=");
        x1_field = new JTextField(5);
        x0_field = new JTextField(5);
        terminating_condition_field = new JTextField(5);
        two_decimal = new JRadioButton("2 Decimal places");
        three_decimal = new JRadioButton("3 Decimal places");
        four_decimal = new JRadioButton("4 Decimal places");

        ButtonGroup toggleGroup = new ButtonGroup();
        toggleGroup.add(two_decimal);
        toggleGroup.add(three_decimal);
        toggleGroup.add(four_decimal);

        decimalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        decimalPanel.add(two_decimal);
        decimalPanel.add(three_decimal);
        decimalPanel.add(four_decimal);

        panel = new JPanel(new GridBagLayout());
        addToPanel(x0,0,0,1);
        addToPanel(x0_field,1,0,1);
        addToPanel(x1,0,1,1);
        addToPanel(x1_field,1,1,1);
        addToPanel(terminating_condition,0,2,1);
        addToPanel(terminating_condition_field,1,2,1);
        container.add(panel, BorderLayout.NORTH);
        container.add(decimalPanel, BorderLayout.CENTER);

        tableModel = new BisectionTableModel();
        tableContent = new JTable(tableModel);
        JScrollPane tablePane = new JScrollPane(tableContent);
        container.add(tablePane, BorderLayout.SOUTH);




        this.setVisible(true);
        this.setTitle("Bisection Method");
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void addToPanel(Component component, int gridx, int gridy, int gridW){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridW;
        panel.add(component,gbc);
    }
}
