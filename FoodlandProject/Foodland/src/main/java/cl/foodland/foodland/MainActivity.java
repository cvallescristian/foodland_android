package cl.foodland.foodland;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView miLabel;
    private Button miBotton;
    private boolean posh=false;
    private EditText input1;
    private EditText input2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        miLabel= (TextView) findViewById(R.id.resultado);
        miBotton=(Button) findViewById(R.id.button);
        input1=(EditText) findViewById(R.id.input1);
        input2= (EditText) findViewById(R.id.input2);


        miBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num1= Integer.parseInt(input1.getText().toString());
                int num2= Integer.parseInt(input2.getText().toString());
                int resultado = num1+num2;
                miLabel.setText("El resultado es : "+resultado);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
