import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        MenuGUI menuGUI = new MenuGUI();

        menuGUI.bisection.addActionListener(new ActionListener() {
            BisectionUI bisectionUI;
            @Override
            public void actionPerformed(ActionEvent e) {
                this.bisectionUI = new BisectionUI();
                menuGUI.dispose();
            }
        });
    }
}