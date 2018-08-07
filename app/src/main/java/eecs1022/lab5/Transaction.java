package eecs1022.lab5;



public class Transaction {

    String myType;
    String myAmount;
    boolean isValid;

    public Transaction (String type, String amount) {
        this.myType = type;
        this.myAmount = amount;
        this.isValid = true;
    }
}



