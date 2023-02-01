import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

// Use array data structure to store data
public class StockChart {
    
    // Variables
    static String[][] stockData;
    static JTextField textBox;
    static Graph testGraph;
    static URL stockURL;
    static String stockName;    

    // Constructor
    public StockChart(String name) {
        return;
    }

    // Functions
    static float findMin(String[][] stockData, int col) {
        float min = Float.parseFloat(stockData[0][col]);
        float f = 0;
        for (int i = 0; i < stockData.length - 1; i++) {
            f = Float.parseFloat(stockData[i][col]);
            if (f < min) {
                min = f;
            }
        }
        return min;
    }

    static float findMax(String[][] stockData, int col) {
        float max = Float.parseFloat(stockData[0][col]);
        float f = 0;
        for (int i = 0; i < stockData.length - 1; i++) {
            f = Float.parseFloat(stockData[i][col]);
            if (f > max) {
                max = f;
            }
        }
        return max;
    }

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
        in.close();
        return lines;
    }

    static String[][] readCSV(File file, long length) throws FileNotFoundException {
        // Split the CSV file into an array using .split()
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

    static void actionSetup() throws IOException, FileNotFoundException, Exception {
        stockName = textBox.getText();

        URL currURL = generateUrl();
        if (new File(stockName + ".csv").exists() != true) {
            downloadFile(currURL, stockName + ".csv"); // downloading the csv into user's computer from web
        }
        
        File file1 = new File(stockName + ".csv"); // reading csv file
        FileInputStream fileRead = new FileInputStream(file1);
        long length = getFileLength(file1);
        
        String[][] currStockData = getStockData();
        currStockData = readCSV(file1, length);
        
        fileRead.close();
        float min = findMin(currStockData, 4);
        float max = findMax(currStockData, 4);

        testGraph.setStockData(length, stockName, min, max, stockData, 4);
    }

    static URL generateUrl() throws MalformedURLException {
        stockURL = new URL("https://query1.finance.yahoo.com/v7/finance/download/" + stockName + "?period1=1643704725&period2=1675240725&interval=1d&events=history&includeAdjustedClose=true");
        return stockURL;
    }

    public static void downloadFile(URL url, String fileName) throws Exception {
        try (InputStream in = url.openStream()) {
            Files.copy(in, Paths.get(fileName));
        }
    }

    // main class
    public static void main(String[] args) throws IOException, FileNotFoundException {
        JFrame frame = new JFrame("StockChart"); // creating frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testGraph = new Graph();

        frame.getContentPane().add(testGraph);
        JPanel frame2 = new JPanel();
        JLabel label = new JLabel("Input stock symbol: ");
        textBox = new JTextField();
        textBox.setPreferredSize(new Dimension(50, 20));
        
        JButton button = new JButton("Enter");
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    actionSetup();
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            }  
        };
        
        button.addActionListener(action);
        frame2.add(label);
        frame2.add(textBox);
        frame2.add(button);
        frame2.setLayout(new FlowLayout());
        frame.getContentPane().add(frame2, BorderLayout.SOUTH);
        frame.getContentPane().setPreferredSize(new Dimension(1200, 600));
        frame2.getRootPane().setDefaultButton(button);
        frame.pack();
        frame.setVisible(true);
    }
}