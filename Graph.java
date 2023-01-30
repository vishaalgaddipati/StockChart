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
        Dimension windowSize = getSize();
        int yOffset = 100;
        float ySlope =  (yOffset - windowSize.height)/(int)(max-min);
        int prevPoint = ((int)(Float.parseFloat(stockData[0][col])));
        int xOffset = 50;

        prevPoint = windowSize.height - 100 + (int)((prevPoint-min)*ySlope);

        gr.drawString(stockName, 0, 10);
        gr.drawString("Min: " + min, 0, 20);
        gr.drawString("Max: " + max, 0, 30);

        for (int i = 1; i < length - 1; i++) {
            int currPoint = (int)(Float.parseFloat(stockData[i][col]));
            currPoint = windowSize.height - 100 + (int)((currPoint-min)*ySlope);
            gr.setStroke(new BasicStroke(1));
            gr.setColor(Color.BLUE);

            int newX1 = ((windowSize.width - xOffset)*(i))/(int)length + xOffset;
            int newX2 = ((windowSize.width - xOffset)*(i + 1))/(int)length + xOffset;
            int newY1 = prevPoint;
            int newY2 = currPoint;
            
            gr.drawLine(newX1, newY1, newX2, newY2);
            prevPoint = currPoint;
        }
    }
}
