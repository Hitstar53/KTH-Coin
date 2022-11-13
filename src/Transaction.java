import java.sql.*;;
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
    public void performTransaction() {
        try {
            //connect to database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cryptodb?serverTimezone=UTC", "sqluser", "password");
            //create statement
            Statement stmt = con.createStatement();
            //execute query
            ResultSet rs = stmt.executeQuery("select * from buy");
            //get buy details
            while (rs.next()) {
                receiver = rs.getString("receiver");
                amount = rs.getInt("amount");
                price = rs.getInt("price");
                //execute query
                Statement stmt1 = con.createStatement();
                ResultSet rs1 = stmt1.executeQuery("select * from sell");
                //get sell details
                while (rs1.next()) {
                    sender = rs1.getString("sender");
                    int amount1 = rs1.getInt("amount");
                    int price1 = rs1.getInt("price");
                    //check if buy and sell orders match
                    if (amount == amount1 && price == price1) {
                        //execute query
                        stmt.executeUpdate("delete from buy where receiver = '" + receiver + "' and amount = " + amount + " and price = " + price);
                        //execute query
                        stmt.executeUpdate("delete from sell where sender = '" + sender + "' and amount = " + amount1 + " and price = " + price1);
                        //execute query
                        stmt.executeUpdate("update users set balance = balance + " + amount + " where name = '" + receiver + "'");
                        //execute query
                        stmt.executeUpdate("update users set balance = balance - " + amount + " where name = '" + sender + "'");
                    }
                }
            }
            //close connection
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    //toString method
    public String toString() {
        return "Sender: " + sender + ", Receiver: " + receiver + ", Amount: " + amount;
    }
}
