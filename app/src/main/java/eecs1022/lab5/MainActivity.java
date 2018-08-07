package eecs1022.lab5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    Bank bank = new Bank();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private String getItem(int id){
        View view = findViewById(id);
        EditText editText = (EditText) view;
        return String.valueOf(editText.getText());
    }

    private void setItem(int id, String newContents){
        View view = findViewById(id);
        TextView textView = (TextView) view;
        textView.setText(newContents);
    }
    private String getItemSelected(int id){
        View view = findViewById(id);
        Spinner spinner = (Spinner) view;
        return String.valueOf(spinner.getSelectedItem());
    }

    public void buttonCreate(View view){

       Double balance = Double.parseDouble(getItem(R.id.inputBalance));
       String name = (getItem(R.id.inputName));
        bank.addClient(name, balance);
       setItem(R.id.textOut, bank.toString()  +"   "  );


       }

    public void buttonTrans(View view){

        String inputF = (getItem(R.id.inputFrom));
        String inputT = (getItem(R.id.inputTO));
        String inputA  = (getItem(R.id.inputAmount));
        String type = getItemSelected(R.id.spinner);
        bank.performAction(type, inputA, inputT, inputF);


        setItem(R.id.textOut, bank.toString());

    }

    public void buttonOutput (View view){

        String wantedName = getItem(R.id.inputName2);
//        int index = bank.indexOfClient(wantedName);

        setItem(R.id.textOut,bank.checkError(wantedName));

    }



}


