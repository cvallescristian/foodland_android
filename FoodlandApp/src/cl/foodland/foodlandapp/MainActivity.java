package cl.foodland.foodlandapp;

import cl.foodland.foodlandapp.R;
import cl.foodland.webserver.ListFoodActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
    }
    
    public void button1Click(View v) {
    	Toast.makeText(this, "Buscando comida cerca de tí...", Toast.LENGTH_SHORT).show();            	
    	
    	//cuando acabe el timer, se abre la app de foodland
		Intent i = new Intent(MainActivity.this, ListFoodActivity.class);
		startActivity(i);

		// cierra la actividad
		finish();        	
    }
    

}
