import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JPanel;
// import java.awt.Graphics;
// import java.awt.Graphics2D;
// import java.awt.BasicStroke;
import java.awt.*;

public class Graph extends JPanel {
    // Variables
    FileInputStream fileRead;
    long length;

    // Constructor
    public Graph(FileInputStream fr, long length) {
        setSize(500, 500);
        this.fileRead = fr;
        this.length = length;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D gr = (Graphics2D) g;
        int x1 = 0;
        int y1 = 500;
        int x2 = 1;
        int y2 = 499;
        for (int i = 0; i < 500; i++) {
            gr.setStroke(new BasicStroke(3));
            gr.setColor(Color.BLUE);
            gr.drawLine(x1 + i, y1 - i, x2 + i, y2 - i);
        }
    }
}
