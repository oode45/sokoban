import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Canvas extends JPanel {
    private Model model;
    private Image imageGamer;
    private Image imageWall;
    private Image imageBox;
    private Image imageGoal;

    public Canvas(Model model) {
        this.model = model;
        setBackground(Color.BLACK);
        setOpaque(true);
        File fileImageGamer = new File("src/images/gamer.png");
        File fileImageWall = new File("src/images/wall.png");
        File fileImageBox = new File("src/images/box.png");
        File fileImageGoal = new File("src/images/goal.png");
        try {
            imageGamer = ImageIO.read(fileImageGamer);
            imageWall = ImageIO.read(fileImageWall);
            imageBox = ImageIO.read(fileImageBox);
            imageGoal = ImageIO.read(fileImageGoal);
        } catch (IOException e){
            System.out.println(e);
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        int start = 50;
        int x = start;
        int y = start;
        int width = 50;
        int height = 50;
        int offset = 0;

        int[][] desktop = model.getDesktop();
        for(int i=0;i<desktop.length;i++){
            for(int j=0;j<desktop[i].length;j++){
                if(desktop[i][j]==1){
                    g.drawImage(imageGamer, x,y, null);
                }else if(desktop[i][j]==2){
                    g.drawImage(imageWall, x, y, null);
                }else if(desktop[i][j]==3){
                    g.drawImage(imageBox, x, y, null);
                }else if(desktop[i][j]==4){
                    g.drawImage(imageGoal, x, y, null);
                }
                x = x+width+offset;
            }
            x = start;
            y = y+height+offset;
        }

    }
}
