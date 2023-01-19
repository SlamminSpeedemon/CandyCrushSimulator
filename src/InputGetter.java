import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@SuppressWarnings("serial")
public class InputGetter extends JPanel {

    KeyListener listener;

    public InputGetter(Environment ui) {
        listener = new MyKeyListener(ui);
        addKeyListener(listener);
        setFocusable(true);
    }

    public void startInputGetter() {

    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Mini Tennis");
//        KeyboardExample keyboardExample = new KeyboardExample();
//        frame.add(keyboardExample);
//        frame.setSize(200, 200);
//        frame.setVisible(true);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }

    public class MyKeyListener implements KeyListener {
        Environment UI;

        public MyKeyListener(Environment ui) {
            UI = ui;
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            String key = KeyEvent.getKeyText(e.getKeyCode());
            //System.out.println("keyPressed = "+KeyEvent.getKeyText(e.getKeyCode()));


        }

        @Override
        public void keyReleased(KeyEvent e) {
            //System.out.println("keyReleased="+KeyEvent.getKeyText(e.getKeyCode()));
        }

    }
}