import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {
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
}
