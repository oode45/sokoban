import javax.swing.*;

public class Viewer {
    private Canvas canvas;
    private JFrame frame;

    public Viewer() {
        Controller controller = new Controller(this);
        Model model = controller.getModel();
        canvas = new Canvas(model);

        frame = new JFrame("Sokoban MVC");
        frame.setSize(700, 700);
        frame.add("Center", canvas);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addKeyListener(controller);
    }

    public void update(){
        canvas.repaint();
    }

    public boolean showDialogWon(){
        JOptionPane.showMessageDialog(frame, "you win");
        return true;
    }
}
