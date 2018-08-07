package eecs1022.lab5;

/**
 * Created by Etienne on 3/15/2018.
 */

public class Client {
    String _name;
    double _balance;

    double id;
    String nname;
    String type;
    String secondName = "";
    String amount;
    String inputF;
    String inputT;




    Transaction[] _history;
    int transactionINdex = 0;

    int not;
    final int MAX_NUM = 10;


    public Client() {

    }

    public Client(String name, double balance) {

        _history = new Transaction[MAX_NUM];
        this._name = name;
        this._balance = balance;

    }

    void addTransaction(String type, String amount) {

        this.secondName = secondName;
        this.nname = nname;
        this.type = type;

        Transaction tr = new Transaction(type, amount);

        _history[transactionINdex ++] = tr;
    }

    public String toString() {
        String s = "";
        int i;
        for (i = 0; i < MAX_NUM; i++) {
            if (_history[i] != null && _history[i].isValid) {
                s += _history[i].myType + " " + _history[i].myAmount + "\n";
            }
        }
        return s;
    }
}
