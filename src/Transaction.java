import java.sql.*;
public class Transaction {
    public String sender;
    public String receiver;
    public int amount;
    public int price;
    //constructor
    public Transaction() {
        sender = "";
        receiver = "";
        amount = 0;
    }
    //buy method
    public void buy(String receiver, int amount, int price) {
        this.receiver = receiver;
        this.amount = amount;
        this.price = price;
    }
    //sell method
    public void sell(String sender, int amount, int price) {
        this.sender = sender;
        this.amount = amount;
        this.price = price;
    }
    //enter details into buy database
    public void enterBuyDetailsIntoDatabase() {
        try {
            //connect to database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cryptodb?serverTimezone=UTC", "sqluser", "password");
            //create statement
            Statement stmt = con.createStatement();
            //execute query
            stmt.executeUpdate("insert into buy(receiver,amount,price) values('" + receiver + "', " + amount + ", " + price + ")");
            //close connection
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    //enter details into sell database
    public void enterSellDetailsIntoDatabase() {
        try {
            //connect to database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cryptodb?serverTimezone=UTC", "sqluser", "password");
            //create statement
            Statement stmt = con.createStatement();
            //execute query
            stmt.executeUpdate("insert into sell(sender,amount,price) values('" + sender + "', " + amount + ", " + price + ")");
            //close connection
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    // perform transaction by matching buy and sell orders
    public boolean performTransBuy(String trader,int amt,int pr) {
        try {
            //connect to database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cryptodb?serverTimezone=UTC", "sqluser", "password");
            //create statement
            Statement stmt1 = con.createStatement();
            //match buyer details with sell database
            ResultSet rs = stmt1.executeQuery("select * from sell where amount = " + amt + " and price = " + pr);
            //if match found
            if (rs.next()) {
                //update balance of user
                Statement stmt2 = con.createStatement();
                stmt2.executeUpdate("update users set balance = balance + " + amt + " where email = '" + trader + "'");
                //get email of seller
                sender = rs.getString(2);
                //delete entry from sell database
                stmt1.executeUpdate("delete from sell where amount = " + amt + " and price = " + pr);
                //delete entry from buy database
                stmt1.executeUpdate("delete from buy where receiver = '" + trader + "' and amount = " + amt + " and price = " + pr);
                System.out.println("Transaction successful!");
                con.close();
                return true;
            }
            else {
                //close connection
                System.out.println("No Sell Order Found! Transaction Processing!");
                con.close();
                return false;
            }
            //con.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    public boolean performTransSell(String trader, int amt, int pr) {
        try {
            // connect to database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cryptodb?serverTimezone=UTC",
                    "sqluser", "password");
            // create statement
            Statement stmt1 = con.createStatement();
            // match sender details with buy database
            ResultSet rs = stmt1.executeQuery("select * from buy where amount = " + amt + " and price = " + pr);
            // if match found
            if (rs.next()) {
                // update balance of user
                Statement stmt2 = con.createStatement();
                //update balance of buyer
                stmt2.executeUpdate("update users set balance = balance + " + amt + " where email = '" + rs.getString(2) + "'");
                // get email of buyer
                receiver = rs.getString(2);
                // delete entry from buy database
                stmt1.executeUpdate("delete from buy where amount = " + amt + " and price = " + pr);
                // delete entry from sell database
                stmt1.executeUpdate("delete from sell where sender = '" + trader + "' and amount = " + amt + " and price = " + pr);
                System.out.println("Transaction successful!");
                con.close();
                return true;
            } else {
                // close connection
                System.out.println("No Sell Order Found! Transaction Processing!");
                con.close();
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    //cancel buy order
    public boolean cancelBuyOrder(String trader, int amt, int pr) {
        try {
            //connect to database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cryptodb?serverTimezone=UTC",
                    "sqluser", "password");
            // create statement
            Statement stmt1 = con.createStatement();
            // match sender details with buy database
            ResultSet rs = stmt1.executeQuery("select * from buy where amount = " + amt + " and price = " + pr);
            // if match found
            if (rs.next()) {
                // delete entry from buy database
                stmt1.executeUpdate("delete from buy where amount = " + amt + " and price = " + pr);
                System.out.println("Order Cancelled!");
                con.close();
                return true;
            } else {
                // close connection
                System.out.println("No Buy Order Found!");
                con.close();
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
    //cancel sell order
    public boolean cancelSellOrder(String trader, int amt, int pr) {
        try {
            // connect to database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cryptodb?serverTimezone=UTC",
                    "sqluser", "password");
            // create statement
            Statement stmt1 = con.createStatement();
            // match sender details with sell database
            ResultSet rs = stmt1.executeQuery("select * from sell where amount = " + amt + " and price = " + pr);
            // if match found
            if (rs.next()) {
                // delete entry from sell database
                stmt1.executeUpdate("delete from sell where amount = " + amt + " and price = " + pr);
                System.out.println("Order Cancelled!");
                con.close();
                return true;
            } else {
                // close connection
                System.out.println("No Sell Order Found!");
                con.close();
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
    //toString method
    public String toString() {
        return "Sender: " + sender + ", Receiver: " + receiver + ", Amount: " + amount;
    }
}
