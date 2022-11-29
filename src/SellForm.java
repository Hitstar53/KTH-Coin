import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class SellForm extends JFrame {
    final private Font mainFont = new Font("comic sans", Font.PLAIN, 18);
    public void initialize(User user,int price) {
        //create a buy form which allows user to buy coins
        Blockchain blockchain = new Blockchain();
        Balance balance = new Balance();
        balance.initialize(user);
        user.getUserDetailsFromDatabase();
        blockchain.loadBlock();
        /*************** Buy Panel ***************/
        JPanel sellPanel = new JPanel();
        sellPanel.setLayout(new GridLayout(0, 2, 5, 5));
        sellPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        sellPanel.add(new JLabel("Amount"));
        JTextField amountField = new JTextField();
        sellPanel.add(new JLabel("Price"));
        JTextField priceField = new JTextField();
        sellPanel.add(amountField);
        sellPanel.add(priceField);
        Component[] labels = sellPanel.getComponents();
        for (int i = 0; i < labels.length; i++) {
            labels[i].setFont(new Font("Lexend", Font.PLAIN, 18));
        }
        add(sellPanel, BorderLayout.NORTH);
        /*************** Button Panel ***************/
        JPanel buttonPanel = new JPanel();
        JButton sellButton = new JButton("Sell");
        sellButton.setFont(mainFont);
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String temp = amountField.getText();
                int amount = Integer.parseInt(temp);
                temp = priceField.getText();
                String sender = user.email;
                int flag = balance.getBalanceFromDatabase();
                Boolean prflag;
                int prc=0;
                if (flag<amount) {
                    JOptionPane.showMessageDialog(null, "Insufficient Balance!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    Transaction transaction = new Transaction();
                    if(temp.equals("")){
                        transaction.sell(sender, amount, price);
                        prflag = false;
                    } else {
                        prc = Integer.parseInt(temp);
                        transaction.sell(sender, amount, prc);
                        prflag = true;
                    }
                    transaction.enterSellDetailsIntoDatabase();
                    balance.updateBalanceInDatabase(flag-amount);
                    if(prflag) {
                        if (transaction.performTransSell(sender,amount,prc)) {
                            JOptionPane.showMessageDialog(null, "Transaction Successful");
                            blockchain.addBlock(transaction);
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "No Buy Order Found! Transaction Processing!");
                        }
                    }
                    else {
                        if (transaction.performTransSell(sender,amount,price)) {
                            JOptionPane.showMessageDialog(null, "Transaction Successful");
                            blockchain.addBlock(transaction);
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "No Buy Order Found! Transaction Processing!");
                        }
                    }
                    System.out.println("Traversing the blockchain...");
                    blockchain.traverse();
                    balance.displayBalance(balance.getBalanceFromDatabase());
                }
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
        buttonPanel.add(sellButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
        /*************** Frame Settings ***************/
        setTitle("Sell");
        setSize(450, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
