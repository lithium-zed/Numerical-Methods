import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GaussSeidel_UI extends JFrame implements ActionListener {
    JLabel eq1, eq2, eq3, terminating_condition, x1,x2,x3;
    JTextField eq1Fl, eq2FL, eq3FL, terminating_condition_field;
    JRadioButton two_decimal, three_decimal, four_decimal;
    JButton compute;
    JPanel inputPanel, decimalPanel, functionPanel;
    GaussSeidelTableModel tableModel;
    JTable tableContent;
    Container container;
    BorderLayout layout;


    public GaussSeidel_UI() throws HeadlessException {
        container = this.getContentPane();
        layout = new BorderLayout();
        container.setLayout(layout);

        eq1 = new JLabel("Equation1:");
        eq2 = new JLabel("Equation2:");
        eq3 = new JLabel("Equation3:");
        x1 = new JLabel("x1 = ");
        x2 = new JLabel("x2 = ");
        x3 = new JLabel("x3 = ");


        eq1Fl = new JTextField(10);
        eq2FL = new JTextField(10);
        eq3FL = new JTextField(10);

        terminating_condition = new JLabel("EA=");
        terminating_condition_field = new JTextField(5);

        two_decimal = new JRadioButton("2 Decimal places");
        three_decimal = new JRadioButton("3 Decimal places");
        four_decimal = new JRadioButton("4 Decimal places");
        compute = new JButton("Get estimated roots");
        compute.addActionListener(this);

        ButtonGroup toggleGroup = new ButtonGroup();
        toggleGroup.add(two_decimal);
        toggleGroup.add(three_decimal);
        toggleGroup.add(four_decimal);

        decimalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        decimalPanel.add(two_decimal);
        decimalPanel.add(three_decimal);
        decimalPanel.add(four_decimal);
        decimalPanel.add(compute);

        inputPanel = new JPanel(new GridBagLayout());
        addToPanel(eq1, 0, 1, 1);
        addToPanel(eq1Fl, 1, 1, 1);
        addToPanel(eq2, 0, 2, 1);
        addToPanel(eq2FL, 1, 2, 1);
        addToPanel(eq3, 0, 3, 1);
        addToPanel(eq3FL, 1, 3, 1);
        addToPanel(terminating_condition,0,4,1);
        addToPanel(terminating_condition_field,1,4,1);

        functionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        functionPanel.add(x1);
        functionPanel.add(x2);
        functionPanel.add(x3);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(functionPanel, BorderLayout.NORTH);
        northPanel.add(inputPanel, BorderLayout.CENTER);
        container.add(northPanel, BorderLayout.NORTH);
        container.add(decimalPanel, BorderLayout.CENTER);

        tableModel = new GaussSeidelTableModel();
        tableContent = new JTable(tableModel);
        JScrollPane tablePane = new JScrollPane(tableContent);
        container.add(tablePane, BorderLayout.SOUTH);

        
        this.setVisible(true);
        this.setTitle("Gauss-Seidel Method");
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void addToPanel(Component component, int gridx, int gridy, int gridW) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridW;
        inputPanel.add(component, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
