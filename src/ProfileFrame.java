import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//import java.sql.*;
//import com.formdev.flatlaf.FlatDarculaLaf;
public class ProfileFrame extends JFrame {
    final private Font mainFont = new Font("comic sans", Font.PLAIN, 18);
    public void initialize(User user) {
        //create a profile frame which shows user balance and allows user to buy/sell coins
        /*************** Info Panel ***************/
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0, 2, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        infoPanel.add(new JLabel("Name"));
        infoPanel.add(new JLabel(user.name));
        infoPanel.add(new JLabel("Email"));
        infoPanel.add(new JLabel(user.email));
        infoPanel.add(new JLabel("Phone"));
        infoPanel.add(new JLabel(user.phone));
        infoPanel.add(new JLabel("Address"));
        infoPanel.add(new JLabel(user.address));
        infoPanel.add(new JLabel("Balance"));
        infoPanel.add(new JLabel(String.valueOf(user.balance)));
        Component[] labels = infoPanel.getComponents();
        for (int i = 0; i < labels.length; i++) {
            labels[i].setFont(new Font("Lexend", Font.PLAIN, 18));
        }
        add(infoPanel, BorderLayout.NORTH);
        /*************** Buy/Sell Panel ***************/
        JPanel buySellPanel = new JPanel();
        buySellPanel.setLayout(new GridLayout(0, 2, 10, 10));
        buySellPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        buySellPanel.add(new JLabel("Buy/Sell"));
        buySellPanel.add(new JLabel("Amount"));
        buySellPanel.add(new JLabel("KTH"));
        buySellPanel.add(new JTextField());
        
        Component[] labels2 = buySellPanel.getComponents();
        for (int i = 0; i < labels2.length; i++) {
            labels2[i].setFont(new Font("Lexend", Font.PLAIN, 18));
        }
        add(buySellPanel, BorderLayout.CENTER);
        /*************** Button Panel ***************/
        JPanel buttonPanel = new JPanel();
        JButton buyButton = new JButton("Buy");
        buyButton.setFont(mainFont);
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //String amount = buySellPanel.getComponent(3).toString();
                User user = new User();
                Balance balance = new Balance();
                balance.initialize(user);
                user.getUserDetailsFromDatabase();
                //Transaction transaction = new Transaction();
                /*transaction.buy(receiver, amount, price);
                transaction.enterBuyDetailsIntoDatabase();
                if (transaction.performTransBuy(receiver, amount, price)) {
                    blockchain.addBlock(transaction);
                }*/
            }
        });
        JButton sellButton = new JButton("Sell");
        sellButton.setFont(mainFont);
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //String amount = buySellPanel.getComponent(3).toString();
                User user = new User();
                Balance balance = new Balance();
                balance.initialize(user);
                user.getUserDetailsFromDatabase();
                //Transaction transaction = new Transaction();
                /*transaction.sell(receiver, amount, price);
                transaction.enterSellDetailsIntoDatabase();
                if (transaction.performTransSell(receiver, amount, price)) {
                    blockchain.addBlock(transaction);
                }*/
            }
        });
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        buttonPanel.add(buyButton);
        buttonPanel.add(sellButton);
        add(buttonPanel, BorderLayout.SOUTH);
        setTitle("Profile");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
