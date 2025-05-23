import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        MenuGUI menuGUI = new MenuGUI();
        MathJSAPIConnection functionReader = new MathJSAPIConnection();

        menuGUI.bisection.addActionListener(new ActionListener() {
            BisectionUI bisectionUI;
            @Override
            public void actionPerformed(ActionEvent e) {
                this.bisectionUI = new BisectionUI(menuGUI.functionsFL.getText());
                menuGUI.dispose();

            }
        });
        menuGUI.simpson1_3.addActionListener(new ActionListener() {
            SimpsonsUI simpsonsUI;
            @Override
            public void actionPerformed(ActionEvent e) {
                this.simpsonsUI = new SimpsonsUI(menuGUI.functionsFL.getText());
                menuGUI.dispose();
            }
        });
        menuGUI.secant.addActionListener(new ActionListener() {
            SecantUI secantUI;
            @Override
            public void actionPerformed(ActionEvent e) {
                this.secantUI = new SecantUI(menuGUI.functionsFL.getText());
                menuGUI.dispose();

            }
        });
        menuGUI.gauss_seidel.addActionListener(new ActionListener() {
            GaussSeidel_UI gaussSeidelUi;
            @Override
            public void actionPerformed(ActionEvent e) {
                this.gaussSeidelUi = new GaussSeidel_UI();
                menuGUI.dispose();
            }
        });

        menuGUI.doolittle.addActionListener(new ActionListener() {
            DooLittleUI dooLittleUI;
            @Override
            public void actionPerformed(ActionEvent e) {
                this.dooLittleUI = new DooLittleUI();
                menuGUI.dispose();
            }
        });

    }
}