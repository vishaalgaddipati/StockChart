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
        int yOffsetTop = (int)(10 * windowSize.height/100);
        int yOffsetBot = (int)(5 * windowSize.height/100);
        float ySlope =  (yOffsetBot + yOffsetTop - windowSize.height)/(int)(max-min);
        int prevPoint = ((int)(Float.parseFloat(stockData[0][col])));
        int xOffset = 50;

        prevPoint = windowSize.height - yOffsetTop + (int)((prevPoint-min)*ySlope);
        
        gr.setColor(Color.BLACK);
        gr.fillRect(0, 0, windowSize.width, windowSize.height);
        gr.setColor(Color.WHITE);
        gr.drawString(stockName, 0, 10);
        gr.drawString("Min: " + min, 0, 20);
        gr.drawString("Max: " + max, 0, 30);
        gr.drawLine(xOffset, windowSize.height - yOffsetBot, windowSize.width, windowSize.height - yOffsetBot); // x axis
        gr.drawLine(xOffset, windowSize.height - yOffsetBot, xOffset, yOffsetTop); // y axis
        
        int dataStep = (int)((max - min) / 10);
        for (int i = 1; i <= 10; i++) { // creating horizontal lines
            gr.setColor(new Color(50, 50, 50));
            int yStep = (windowSize.height - yOffsetBot - yOffsetTop) * i / 10;
            gr.drawLine(xOffset, windowSize.height - yOffsetBot - yStep, windowSize.width, windowSize.height - yOffsetBot - yStep);
            gr.setColor(Color.WHITE);
            String dataStr = Integer.toString((int)min + dataStep * i);
            gr.drawString(dataStr, xOffset - 25, windowSize.height - yOffsetBot - yStep);
        }

        String prevMonth = stockData[0][0].substring(0, 7);
        for (int i = 1; i < length - 1; i++) {
            int currPoint = (int)(Float.parseFloat(stockData[i][col]));
            currPoint = windowSize.height - yOffsetTop + (int)((currPoint-min)*ySlope);
            
            gr.setStroke(new BasicStroke(1)); // Adding green for rise red for fall
            if (currPoint >= prevPoint) {
                gr.setColor(Color.RED);
            }
            else {
                gr.setColor(Color.GREEN);
            }

            int newX1 = ((windowSize.width - xOffset)*(i))/(int)length + xOffset;
            int newX2 = ((windowSize.width - xOffset)*(i + 1))/(int)length + xOffset;
            int newY1 = prevPoint;
            int newY2 = currPoint;

            gr.drawLine(newX1, newY1, newX2, newY2);
            prevPoint = currPoint;

            String currMonth = stockData[i][0].substring(0, 7);
            if (!currMonth.equals(prevMonth)) {
                prevMonth = currMonth;
                gr.setColor(new Color(50,50,50));
                gr.drawLine(newX2, windowSize.height - yOffsetBot, newX2, yOffsetTop);
                gr.setColor(Color.WHITE);
                gr.drawString(currMonth, newX2 - 25, windowSize.height - yOffsetBot + 20);
            }
        }
    }
}
