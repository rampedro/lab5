package eecs1022.lab5;

import android.widget.Switch;

public class Bank {
    Client[] arrayOfCleints;
    String c = "";
    String b = "";
    int noc = 1;
    final int MAX_NUM = 7;
    int i = 0;
    int n = 0;
    Boolean error;
    String errorMsg;
    String name;
    Double balance;
    String nameTo;
    String nameFrom;
    double amount;


    Bank() {

        arrayOfCleints = new Client[MAX_NUM];
    }

    ////////////////////////////////////////ERROR HANDLING////////////////////////////////////////

    void setCreateError() {
        this.error = true;

        if (indexOfClient(name) >= 1 && noc >= 7 && balance < 0) {
            errorMsg = "Maximum numner of Clients";
        }
        if (balance < 0 && indexOfClient(name) >= 1) {
            errorMsg = "This Client name Already Exists";

        }
        if (balance < 0 && noc >= 7) {
            errorMsg = "Maximum number of Clients";

        }
        if (balance < 0 && !(noc >= 7) && !(indexOfClient(name) >= 1)) {
            errorMsg = "Negative Balance is not Allowed";
        }
        if (noc >= 7 && !(balance < 0) && !(indexOfClient(name) >= 1)) {
            errorMsg = "Maximum number of Clients";
        }
        if (indexOfClient(name) >= 1 && !(noc >= 7) && !(balance < 0)) {
            errorMsg = "This Client name Already Exists";
        }


    }

    void setErrorExist(String account,String type){

        if(type.equals("Deposit")){
            if (indexOfClient(account) <= 0) {
                setErrorNoClient(account);
            } else {
                setErrorNotValidAmount(type);
            }
        }else if(type.equals("Withdraw")){
            if (indexOfClient(account) <= 0) {
                setErrorNoClient(account);
            } else if(amount<0) {
                setErrorNotValidAmount(type);
            }else{
                setErrorFromRequest(account,amount);
            }
        }
    }

    void setErrorExistTransfer(String accountFrom, String accountTo, double amount){
        if (indexOfClient(accountFrom) <= 0) {
            setErrorNoClient(accountFrom);
        } else if(indexOfClient(accountTo)<=0) {
            setErrorNoClient(accountTo);
        }else if(amount<0){
            setErrorNotValidAmount("Transfer");
        }else{
            setErrorFromRequest(accountFrom,amount);
        }
    }




    void setErrorNotExist(String nameTo, String nameFrom){
        this.error = true;
        if(indexOfClient(nameTo) <= 0){
            this.errorMsg = nameTo + " Does not Exist";
        }else if(indexOfClient(nameFrom)<=0){
            this.errorMsg = nameFrom + " Does not Exist";
        }

    }

    void setErrorFromRequest(String accountFrom, double amount) {
        this.error = true;
        this.errorMsg = amount + " is larger than " + accountFrom + " balance";
    }


    void setErrorNoClient(String name) {
        this.error = true;
        this.errorMsg = "Client " + name + " does not exist!";
    }

    String setErrorNoClientString(String name) {
        this.error = true;
        this.errorMsg = "Client " + name + " does not exist!";
        return errorMsg;
    }

    void setErrorNotValidAmount(String type) {
        this.error = true;

                this.errorMsg = type + " Cannot be Negative";

    }



    void resetError() {
        this.error = false;
        this.errorMsg = "";
    }


    void addClient(String name, double balance) {
        this.name = name;
        this.balance = balance;

        if (indexOfClient(name) >= 1 || noc >= 7 || balance < 0) {
            setCreateError();
        } else {
            Client cl = new Client(name, balance);
            arrayOfCleints[noc] = cl;
            noc++;
            resetError();
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////

    void myDeposit(String amount, int clientIndex) {
        double depositAmount = Double.parseDouble(amount);
        arrayOfCleints[clientIndex]._balance += depositAmount;
        arrayOfCleints[clientIndex].addTransaction("Deposit", amount);
    }

    void myWithdraw(String amount, int clientIndex) {
        double withdrawAmount = Double.parseDouble(amount);
        arrayOfCleints[clientIndex]._balance -= withdrawAmount;
        arrayOfCleints[clientIndex].addTransaction("Withdraw", amount);
    }


    void performAction(String type, String amount, String acountTo, String acountFrom) {
        this.nameTo = acountTo;
        this.nameFrom = acountFrom;
        this.amount = Double.parseDouble(amount);

//        if ( indexOfClient(acountTo) <= 0 || indexOfClient(acountFrom) <= 0|| Double.parseDouble(amount) < 0 ) {
//            setErrorNotValidAmount(type);
//            return;
//        }

        if (type.equals("Deposit")) {
            int clientIndex = indexOfClient(acountTo);
            this.error = false;
            if (error || clientIndex <= 0 || Double.parseDouble(amount) <= 0) {
                error = true;
                setErrorExist(acountTo, "Deposit");
                return;
            } else {
                myDeposit(amount, clientIndex);
            }
        }

        if (type.equals("Withdraw")) {
            int clientIndex = indexOfClient(acountFrom);
            if (indexOfClient(acountFrom) == -1) {
                setErrorNoClient(acountFrom);
            } else {
                double personBal = arrayOfCleints[indexOfClient(acountFrom)]._balance;
                this.error = false;
                if (error || clientIndex <= 0 || Double.parseDouble(amount) <= 0 || Double.parseDouble(amount) > personBal) {
                    setErrorExist(acountFrom, "Withdraw");
                    return;
                } else {
                    myWithdraw(amount, clientIndex);
                }
            }
        }

        if (type.equals("Transfer")) {
            int withdrawClientIndex = indexOfClient(acountFrom);
            int depositClientIndex = indexOfClient(acountTo);
            double myAmount = Double.parseDouble(amount);
            this.error = false;
            if (indexOfClient(acountFrom) == -1 || indexOfClient(acountTo) == -1) {
                setErrorExistTransfer(acountFrom,acountTo,myAmount);
            } else {
                double personBal = arrayOfCleints[indexOfClient(acountFrom)]._balance;
                if (error || withdrawClientIndex <= 0 || depositClientIndex <= 0 || myAmount < 0 || myAmount > personBal) {
                    setErrorExistTransfer(acountFrom, acountTo, myAmount);
                    return;
                } else {
                    myWithdraw(amount, withdrawClientIndex);
                    myDeposit(amount, depositClientIndex);
                }
            }
        }
    }


//        catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }


    @Override
    public String toString(){
        String s = "";

        if (this.error) {
            s = this.errorMsg;
        } else {

            int i;
            for (i = 1; i < noc; i++) {
                s += arrayOfCleints[i]._name + "  " + arrayOfCleints[i]._balance + "\n";
            }
        }
        return s;
    }


    public int indexOfClient(String name) {
        int index = 0;
        for (int i = 1; i < noc; i++) {
            if (arrayOfCleints[i]._name.equals(name)) {
                return i;
            }

        }
        return -1;
    }


    String checkError(String account){
        String s= "";
        if(indexOfClient(account)<= 0){
           s = setErrorNoClientString(account);
        }else{
            s = arrayOfCleints[indexOfClient(account)].toString();
        }
        return s;
    }
}



