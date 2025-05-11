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
    }
}