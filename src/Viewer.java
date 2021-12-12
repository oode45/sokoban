import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Viewer {
    private Canvas canvas;
    private JFrame frame;
    private Controller controller;

    public Viewer() {
        controller = new Controller(this);
        Model model = controller.getModel();
        JMenuBar menuBar = createJMenuBar(controller);
        canvas = new Canvas(model);

        frame = new JFrame("Sokoban MVC");

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cancelGame();
            }
        });

        frame.setSize(700, 700);
        frame.add("Center", canvas);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addKeyListener(controller);
        frame.setJMenuBar(menuBar);
    }

    private JMenuBar createJMenuBar(Controller controller) {
        JMenu levelChooserMenu = new JMenu("Level");

        for (int i=1;i<=9;i++){
            levelChooserMenu.add(createLevel(i+"", controller));
        }

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(levelChooserMenu);
        return menuBar;
    }

    public JMenuItem createLevel(String levelNumber, Controller controller){
        JMenuItem item = new JMenuItem(String.format("Level %s", levelNumber));
        item.addActionListener(controller);
        item.setActionCommand(levelNumber);
        return item;
    }

    public void update(){
        canvas.repaint();
    }

    public boolean showDialogWon(){
        JOptionPane.showMessageDialog(frame, "you win");
        return true;
    }

    public void showDialogBadServerResponse(){
        JOptionPane.showMessageDialog(frame, "Cannot get level from server");
    }

    public void cancelGame(){
//        System.out.println("exit game");
        controller.cancelGame();
    }
}
