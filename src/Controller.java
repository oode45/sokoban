import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener, ActionListener {
    private Model model;

    public Controller(Viewer viewer) {
        model = new Model(viewer);
    }

    public Model getModel() {
        return model;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        switch (keyCode){
            case 37:
                model.move("left");
                break;
            case 38:
                model.move("up");
                break;
            case 39:
                model.move("right");
                break;
            case 40:
                model.move("down");
                break;
            default:
                return;
        }
    }

    public void cancelGame() {
        model.closeServerConnection();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int level = Integer.parseInt(actionEvent.getActionCommand());
        model.startChoosedLevel(level);
    }

}
