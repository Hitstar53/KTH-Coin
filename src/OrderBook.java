import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.formdev.flatlaf.FlatDarculaLaf;
public class OrderBook extends JFrame {
    final private Font mainFont = new Font("comic sans", Font.PLAIN, 18);
    public void initialize(User user, int price) {
        /*************** Title Panel ***************/
        JLabel lbTitle = new JLabel("Order Book", SwingConstants.CENTER);
        lbTitle.setFont(mainFont);
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayout(0, 1, 5, 5));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 5, 50));
        titlePanel.add(lbTitle);
        /*************** Orders Panel ***************/
        JLabel lbSub1 = new JLabel("Bid", SwingConstants.CENTER);
        lbSub1.setFont(mainFont);
        JLabel lbSub2 = new JLabel("Price", SwingConstants.CENTER);
        lbSub2.setFont(mainFont);
        JLabel lbSub3 = new JLabel("Offer", SwingConstants.CENTER);
        lbSub3.setFont(mainFont);
        JLabel lbSub4 = new JLabel("Price", SwingConstants.CENTER);
        lbSub4.setFont(mainFont);
        JPanel ordersPanel = new JPanel();
        ordersPanel.setLayout(new GridLayout(0, 4, 2, 2));
        ordersPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        ordersPanel.add(lbSub1);
        ordersPanel.add(lbSub2);
        ordersPanel.add(lbSub3);
        ordersPanel.add(lbSub4);
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cryptodb?serverTimezone=UTC",
                    "sqluser", "password");
            Statement stmt1 = con.createStatement();
            ResultSet rs = stmt1.executeQuery("select * from buy order by price desc");
            Statement stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery("select * from sell order by price asc");
            //display both buy and sell orders
            while (rs.next() && rs2.next()) {
                ordersPanel.add(new JLabel(rs.getString(3), SwingConstants.CENTER));
                ordersPanel.add(new JLabel(rs.getString(4), SwingConstants.CENTER));
                ordersPanel.add(new JLabel(rs2.getString(3), SwingConstants.CENTER));
                ordersPanel.add(new JLabel(rs2.getString(4), SwingConstants.CENTER));
            }
            con.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        Component[] orders = ordersPanel.getComponents();
        for (int i = 0; i < orders.length; i++) {
            orders[i].setFont(new Font("Lexend", Font.PLAIN, 18));
        }
        /*************** button Panel ***************/
        JPanel buttonPanel = new JPanel();
        JButton buyButton = new JButton("Buy");
        buyButton.setFont(mainFont);
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuyForm buyForm = new BuyForm();
                buyForm.initialize(user, price);
                dispose();
            }
        });
        JButton sellButton = new JButton("Sell");
        sellButton.setFont(mainFont);
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SellForm sellForm = new SellForm();
                sellForm.initialize(user, price);
                dispose();
            }
        });
        JButton backButton = new JButton("Back");
        backButton.setFont(mainFont);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProfileFrame profileFrame = new ProfileFrame();
                profileFrame.initialize(user);
                dispose();
            }
        });
        buttonPanel.setLayout(new GridLayout(1, 3, 2, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        buttonPanel.add(buyButton);
        buttonPanel.add(sellButton);
        buttonPanel.add(backButton);
        //add panels to frame using border layout
        add(titlePanel, BorderLayout.NORTH);
        add(ordersPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        //set frame properties
        setTitle("Order Book");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        LoginForm loginForm = new LoginForm();
        loginForm.initialize();
    }
}
