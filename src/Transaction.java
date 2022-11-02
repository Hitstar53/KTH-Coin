public class Transaction {
    public String sender;
    public String receiver;
    public int amount;
    //constructor
    public Transaction() {
        sender = "";
        receiver = "";
        amount = 0;
    }
    //buy method
    public void buy(String receiver, int amount) {
        this.receiver = receiver;
        this.amount = amount;
    }
    //sell method
    public void sell(String sender, int amount) {
        this.sender = sender;
        this.amount = amount;
    }
    //toString method
    public String toString() {
        return "Sender: " + sender + ", Receiver: " + receiver + ", Amount: " + amount;
    }
}
