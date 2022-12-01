import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//import java.util.Random;
import java.sql.*;
import com.formdev.flatlaf.FlatDarculaLaf;
public class ProfileFrame extends JFrame {
    final private Font mainFont = new Font("comic sans", Font.PLAIN, 18);
    public void initialize(User user) {
        //create a profile frame which shows user balance and allows user to buy/sell coins
        Balance balance = new Balance();
        balance.initialize(user);
        user.getUserDetailsFromDatabase();
        /*************** Info Panel ***************/
        JPanel infoPanel = new JPanel();
        //Random rand = new Random();
        //infoPanel.setLayout(new GridLayout(0, 1, 10, 10));
        infoPanel.setLayout(new GridLayout(0, 2, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        infoPanel.add(new JLabel("Name"));
        infoPanel.add(new JLabel(user.name));
        infoPanel.add(new JLabel("Email"));
        infoPanel.add(new JLabel(user.email));
        infoPanel.add(new JLabel("KTH Balance"));
        infoPanel.add(new JLabel(String.valueOf(user.balance)));
        infoPanel.add(new JLabel("Current Price"));
        //int price = rand.nextInt(100);
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cryptodb?serverTimezone=UTC",
                    "sqluser", "password");
            Statement stmt1 = con.createStatement();
            ResultSet rs = stmt1.executeQuery("select max(price) from buy");
            rs.next();
            infoPanel.add(new JLabel("$"+rs.getString(1)));
            //price = Integer.parseInt(rs.getString(1));
        }
        catch (Exception e) {
            System.out.println(e);
        }
        //infoPanel.add(new JLabel("$"+String.valueOf(price)));
        Component[] labels = infoPanel.getComponents();
        for (int i = 0; i < labels.length; i++) {
            labels[i].setFont(new Font("Lexend", Font.PLAIN, 18));
        }
        add(infoPanel, BorderLayout.NORTH);
        /*************** Current Orders Panel ***************/
        JPanel currentOrdersPanel = new JPanel();
        currentOrdersPanel.setLayout(new GridLayout(0, 1, 2, 2));
        currentOrdersPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        currentOrdersPanel.add(new JLabel("Current Orders"));
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cryptodb?serverTimezone=UTC",
                    "sqluser", "password");
            Statement stmt1 = con.createStatement();
            ResultSet rs = stmt1.executeQuery("select * from buy where receiver = '" + user.email + "'");
            // display buy orders
            while (rs.next()) {
                currentOrdersPanel.add(new JLabel("Buy: " + rs.getString(3) + " KTH at $" + rs.getString(4)));
            }
            // close connection
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cryptodb?serverTimezone=UTC",
                    "sqluser", "password");
            Statement stmt1 = con.createStatement();
            ResultSet rs = stmt1.executeQuery("select * from sell where sender = '" + user.email + "'");
            // display sell orders
            while (rs.next()) {
                currentOrdersPanel.add(new JLabel("Sell: " + rs.getString(3) + " KTH at $" + rs.getString(4)));
            }
            // close connection
            con.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        Component[] orders = currentOrdersPanel.getComponents();
        for (int i = 0; i < orders.length; i++) {
            orders[i].setFont(new Font("Lexend", Font.PLAIN, 18));
        }
        add(currentOrdersPanel, BorderLayout.CENTER);
        /*************** Button Panel ***************/
        JPanel buttonPanel = new JPanel();
        int price = Integer.parseInt(((JLabel)infoPanel.getComponent(7)).getText().substring(1));
        JButton buyButton = new JButton("Buy");
        buyButton.setFont(mainFont);
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuyForm buyForm = new BuyForm();
                buyForm.initialize(user,price);
                dispose();
            }
        });
        JButton sellButton = new JButton("Sell");
        sellButton.setFont(mainFont);
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SellForm sellForm = new SellForm();
                sellForm.initialize(user,price);
                dispose();
            }
        });
        JButton orderButton = new JButton("Orders");
        orderButton.setFont(mainFont);
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderBook orderBook = new OrderBook();
                orderBook.initialize(user,price);
                dispose();
            }
        });
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        //buttonPanel.setLayout(new GridLayout(1, 2, 10, 0));
        //buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        buttonPanel.add(buyButton);
        buttonPanel.add(sellButton);
        buttonPanel.add(orderButton);
        add(buttonPanel, BorderLayout.SOUTH);
        setTitle("Profile");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(450, 550);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel( new FlatDarculaLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        ProfileFrame profileFrame = new ProfileFrame();
        User user = new User();
        user.email = "will@gmail.com";
        user.getUserDetailsFromDatabase();
        profileFrame.initialize(user);
    }
}
