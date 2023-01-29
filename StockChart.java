import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.util.Scanner;

// Use array data structure to store data
public class StockChart {
    
    // Variables
    static String[][] stockData;
    //String downloadURL = "https://query1.finance.yahoo.com/v7/finance/download/GOOG?period1=1643320177&period2=1674856177&interval=1d&events=history&includeAdjustedClose=true";
    

    // Constructor
    public StockChart(String name) {
        return;
    }

    // Functions
    static float findMin(String[][] stockData, int col) {
        float min = 0;

        return min;
    }

    static float findMax();


    static String[][] getStockData() {
        return stockData;
    }

    static long getFileLength(File file) throws FileNotFoundException {
        Scanner in = new Scanner(file);
        long lines = 0;
        while(in.hasNext()) {
            in.nextLine();
            lines++;
        }
        return lines;
    }

    static String[][] readCSV(File file, long length) throws FileNotFoundException {
        //Split the CSV file into an array using .split()
        Scanner in = new Scanner(file);
        String[] header = in.nextLine().split(","); // nextLine() skips white space
        int size = header.length;
        stockData = new String[(int)length][size];
        int i = 0;
        while(in.hasNext()) {     // loop scanning csv line by line
            stockData[i] = in.nextLine().split(","); // setting row in stockData to csv array
            i++;
        }
        in.close();
        return stockData;
    }

    // main class
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in); // reading in the stock name 
        System.out.print("Enter stock: ");
        String stockName = in.nextLine();

        //File file1 = new File("Data/GOOG.csv");
        File file1 = new File("Data/" + stockName + ".csv");   // reading csv file
        FileInputStream fileRead = new FileInputStream(file1);
        long length = getFileLength(file1);
        // for (int i = 0; i < length; i++) {
        //     System.out.print((char)fileRead.read());
        // }
        String[][] currStockData = getStockData();
        currStockData = readCSV(file1, length);
        for (int i = 0; i < length - 1; i++) {
                System.out.print(currStockData[i][0] + " " + currStockData[i][4]);
                System.out.println();
        }
        fileRead.close();

        JFrame frame = new JFrame(stockName);                     // creating frame
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