import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.util.Scanner;

// Use array data structure to store data
public class StockChart {
    
    // Variables
    //String downloadURL = "https://query1.finance.yahoo.com/v7/finance/download/GOOG?period1=1643320177&period2=1674856177&interval=1d&events=history&includeAdjustedClose=true";
    

    // Constructor
    public StockChart(String name) {
        
    }

    // Functions
    public void setName() {
        return;
    }

    public void displayData() {
        return;
    }

    // main class
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter stock: ");
        String stockName = in.nextLine();

        //File file1 = new File("Data/GOOG.csv");
        File file1 = new File("Data/" + stockName + ".csv");
        FileInputStream fileRead = new FileInputStream(file1);
        long length = file1.length();
        // for (int i = 0; i < length; i++) {
        //     System.out.print((char)fileRead.read());
        // }
        fileRead.close();

        JFrame frame = new JFrame(stockName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Graph testGraph = new Graph(fileRead, length, stockName);
        //JLabel emptyLabel = new JLabel();
        frame.getContentPane().add(testGraph);
        //frame.setSize(500, 500);
        //frame.setBounds(10, 10, 500, 500);
        frame.getContentPane().setPreferredSize(new Dimension(500, 500));
        frame.pack();
        frame.setVisible(true);

    }
}