import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpsonsUI extends JFrame implements ActionListener {
    JRadioButton two_decimal, three_decimal, four_decimal;
    JTable tableContent;
    JButton compute;
    SimpsonsTableModel tableModel;
    JLabel a, b, n, functionLabel, iValue;
    JTextField aField, bField, nField, functionField;
    JPanel inputPanel, decimalPanel, functionPanel;
    Container container;
    BorderLayout layout;
    private final String function;

    public SimpsonsUI(String functionFromMenu) throws HeadlessException {
        this.function = functionFromMenu;
        container = this.getContentPane();
        layout = new BorderLayout();
        container.setLayout(layout);

        functionLabel = new JLabel("f(x)=");
        functionField = new JTextField(15);
        functionField.setText(this.function);
        functionField.setEditable(false);

        a = new JLabel("a=");
        b = new JLabel("b=");
        n = new JLabel("n=");
        iValue = new JLabel("Value of I: ");
        aField = new JTextField(5);
        bField = new JTextField(5);
        nField = new JTextField(5);
        two_decimal = new JRadioButton("2 Decimal places");
        three_decimal = new JRadioButton("3 Decimal places");
        four_decimal = new JRadioButton("4 Decimal places");
        compute = new JButton("Get estimated value of I");
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
        addToPanel(a, 0, 1, 1);
        addToPanel(aField, 1, 1, 1);
        addToPanel(b, 0, 2, 1);
        addToPanel(bField, 1, 2, 1);
        addToPanel(n, 0, 3, 1);
        addToPanel(nField, 1, 3, 1);

        functionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        functionPanel.add(functionLabel);
        functionPanel.add(functionField);
        functionPanel.add(iValue);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(functionPanel, BorderLayout.NORTH);
        northPanel.add(inputPanel, BorderLayout.CENTER);
        container.add(northPanel, BorderLayout.NORTH);
        container.add(decimalPanel, BorderLayout.CENTER);

        tableModel = new SimpsonsTableModel();
        tableContent = new JTable(tableModel);
        JScrollPane tablePane = new JScrollPane(tableContent);
        container.add(tablePane, BorderLayout.SOUTH);

        this.setVisible(true);
        this.setTitle("Simpsons 1/3 Rule Method");
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
        if(e.getSource()==compute){
            double a=Double.parseDouble(aField.getText());
            double b=Double.parseDouble(bField.getText());
            double n=Double.parseDouble(nField.getText());
            int decimalPlaces=0;
            if(two_decimal.isSelected()){
                decimalPlaces=2;
            }else if (three_decimal.isSelected()){
                decimalPlaces=3;
            }else if(four_decimal.isSelected()){
                decimalPlaces=4;
            }
            tableModel.computeSimpsons(function,a,b,n,decimalPlaces);
            iValue.setText(String.format("Value of I: %s", tableModel.iValue));
        }
    }
}
