import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BisectionUI extends JFrame implements ActionListener {
    JRadioButton two_decimal, three_decimal, four_decimal;
    JTable tableContent;
    JButton compute;
    BisectionTableModel tableModel;
    JLabel x0, x1, terminating_condition, functionLabel;
    JTextField x0_field, x1_field, terminating_condition_field, functionField;
    JPanel inputPanel, decimalPanel, functionPanel;
    Container container;
    BorderLayout layout;
    private final String function;

    public BisectionUI(String functionFromMenu) throws HeadlessException {
        this.function = functionFromMenu;
        container = this.getContentPane();
        layout = new BorderLayout();
        container.setLayout(layout);

        functionLabel = new JLabel("f(x)=");
        functionField = new JTextField(15);
        functionField.setText(this.function);
        functionField.setEditable(false);

        x0 = new JLabel("x0=");
        x1 = new JLabel("x1=");
        terminating_condition = new JLabel("EA=");
        x1_field = new JTextField(5);
        x0_field = new JTextField(5);
        terminating_condition_field = new JTextField(5);
        two_decimal = new JRadioButton("2 Decimal places");
        three_decimal = new JRadioButton("3 Decimal places");
        four_decimal = new JRadioButton("4 Decimal places");
        compute = new JButton("Get estimated root");
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
        addToPanel(x0, 0, 1, 1);
        addToPanel(x0_field, 1, 1, 1);
        addToPanel(x1, 0, 2, 1);
        addToPanel(x1_field, 1, 2, 1);
        addToPanel(terminating_condition, 0, 3, 1);
        addToPanel(terminating_condition_field, 1, 3, 1);

        functionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        functionPanel.add(functionLabel);
        functionPanel.add(functionField);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(functionPanel, BorderLayout.NORTH);
        northPanel.add(inputPanel, BorderLayout.CENTER);
        container.add(northPanel, BorderLayout.NORTH);
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
        if (e.getSource() == compute) {
            double x0Value = Double.parseDouble(x0_field.getText());
            double x1Value = Double.parseDouble(x1_field.getText());
            double tolerance = Double.parseDouble(terminating_condition_field.getText());
            int decimalPlaces = 0;

            if (two_decimal.isSelected()) {
                decimalPlaces = 2;
            } else if (three_decimal.isSelected()) {
                decimalPlaces = 3;
            } else if (four_decimal.isSelected()) {
                decimalPlaces = 4;
            }
            
            tableModel.computeBisection(function, x0Value, x1Value, tolerance, decimalPlaces);
        }
    }

}
