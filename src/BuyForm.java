import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class BuyForm extends JFrame {
    final private Font mainFont = new Font("comic sans", Font.PLAIN, 18);
    public void initialize(User user,int price) {
        //create a buy form which allows user to buy coins
        Blockchain blockchain = new Blockchain();
        Balance balance = new Balance();
        balance.initialize(user);
        user.getUserDetailsFromDatabase();
        blockchain.loadBlock();
        /*************** Buy Panel ***************/
        JPanel buyPanel = new JPanel();
        buyPanel.setLayout(new GridLayout(0, 2, 5, 5));
        buyPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        buyPanel.add(new JLabel("Amount"));
        JTextField amountField = new JTextField();
        buyPanel.add(new JLabel("Price"));
        JTextField priceField = new JTextField();
        buyPanel.add(amountField);
        buyPanel.add(priceField);
        Component[] labels = buyPanel.getComponents();
        for (int i = 0; i < labels.length; i++) {
            labels[i].setFont(new Font("Lexend", Font.PLAIN, 18));
        }
        add(buyPanel, BorderLayout.NORTH);
        /*************** Button Panel ***************/
        JPanel buttonPanel = new JPanel();
        JButton buyButton = new JButton("Buy");
        buyButton.setFont(mainFont);
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String temp = amountField.getText();
                int amount = Integer.parseInt(temp);
                temp = priceField.getText();
                String receiver = user.email;
                int prc=0;
                Boolean prflag;
                Transaction transaction = new Transaction();
                if(temp.equals("")){
                    transaction.buy(receiver, amount, price);
                    prflag = false;
                } else {
                    prc = Integer.parseInt(temp);
                    transaction.buy(receiver, amount, prc);
                    prflag = true;
                }
                transaction.enterBuyDetailsIntoDatabase();
                if(prflag) {
                    if (transaction.performTransBuy(receiver, amount, prc)) {
                        JOptionPane.showMessageDialog(null, "Transaction Successful");
                        blockchain.addBlock(transaction);
                    } else {
                        JOptionPane.showMessageDialog(null, "No Sell Order Found! Transaction Processing!");
                    }
                }
                else {
                    if (transaction.performTransBuy(receiver, amount, price)) {
                        JOptionPane.showMessageDialog(null, "Transaction Successful");
                        blockchain.addBlock(transaction);
                    } else {
                        JOptionPane.showMessageDialog(null, "No Sell Order Found! Transaction Processing!");
                    }
                }
                System.out.println("Traversing the blockchain...");
                blockchain.traverse();
                balance.displayBalance(balance.getBalanceFromDatabase());
                ProfileFrame profileFrame = new ProfileFrame();
                profileFrame.initialize(user);
                dispose();
            }
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(mainFont);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProfileFrame profileFrame = new ProfileFrame();
                profileFrame.initialize(user);
                dispose();
            }
        });
        buttonPanel.add(buyButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
        /*************** Frame Settings ***************/
        setTitle("Buy");
        setSize(450, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
