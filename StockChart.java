import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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

    static long getBufferLength(URL currURL) throws IOException {
        URLConnection connection = currURL.openConnection();
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(reader);
        long lines = 0;
        while(bufferedReader.readLine() != null) {
            lines++;
        }
        bufferedReader.close();
        return lines;
    }

    static String[][] readCSVBuffer(URL currURL, long length) throws IOException {
            // Split the CSV file into an array using .split()
            URLConnection connection = currURL.openConnection();
            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(reader);
            String[] header = bufferedReader.readLine().split(","); // nextLine() skips white space
            int size = header.length;
            stockData = new String[(int)length][size];
            int i = 0;
            String line;
            while((line = bufferedReader.readLine()) != null) { // loop scanning csv line by line
                stockData[i] = line.split(","); // setting row in stockData to csv array
                i++;
            }
            bufferedReader.close();
            return stockData;
        }

    static void actionSetup() throws IOException, FileNotFoundException, Exception {
        stockName = textBox.getText();

        URL currURL = generateUrl();
        long length = getBufferLength(currURL);
        
        String[][] currStockData = getStockData();
        currStockData = readCSVBuffer(currURL, length);
        
        float min = findMin(currStockData, 4);
        float max = findMax(currStockData, 4);

        testGraph.setStockData(length, stockName, min, max, stockData, 4);
    }

    static URL generateUrl() throws MalformedURLException {        
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.add(Calendar.YEAR, -1);
        Date lastYear = cal.getTime();
        String period1 = String.valueOf(lastYear.getTime()).substring(0, 10);
        String period2 = String.valueOf(today.getTime()).substring(0, 10);
        
        stockURL = new URL("https://query1.finance.yahoo.com/v7/finance/download/" + stockName + "?period1=" + period1 + "&period2=" + period2 +"&interval=1d&events=history&includeAdjustedClose=true");
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