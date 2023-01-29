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
    String stockName;
    float min;
    float max;
    String[][] stockData;
    int col; // the col for data

    // Constructor
    public Graph(FileInputStream fr, long length, String stockName, float min, float max, String[][] stockData, int col) {
        setSize(500, 500);
        this.fileRead = fr;
        this.length = length;
        this.stockName = stockName;
        this.min = min;
        this.max = max;
        this.stockData = stockData;
        this.col = col;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D gr = (Graphics2D) g;
        int x1 = 0; //0
        int y1 = (int)max+50; //500
        int x2 = 1; //1
        int y2 = y1 - 1; //499
        int prevPoint = 0;
        gr.drawString(stockName, 0, 10);
        gr.drawString("Min: " + min, 0, 20);
        gr.drawString("Max: " + max, 0, 30);
        for (int i = 0; i < length - 1; i++) {
            int currPoint = (int)Float.parseFloat(stockData[i][col]);
            gr.setStroke(new BasicStroke(1));
            gr.setColor(Color.BLUE);
            gr.drawLine(x1 + i, y1 - prevPoint, x2 + i, y2 - currPoint);
            prevPoint = currPoint;
        }
    }
}
