package cl.foodland.foodlandapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {

	// tiempo en que se ve la pantalla de splash, en milisegundos
	private static int TIEMPO_ESPERA = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_main);

		// handler está con delay, luego abre foodland
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// cuando acabe el timer, se abre la app de foodland
				Intent i = new Intent(MainActivity.this, ListFoodActivity.class);
				startActivity(i);

				// cierra la actividad
				finish();
			}
		}, TIEMPO_ESPERA);
	}

	/*
	 * 
	 * @Override protected void onCreate(Bundle savedInstanceState) {
	 * super.onCreate(savedInstanceState);
	 * this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	 * setContentView(R.layout.activity_main); }
	 * 
	 * public void buttonClick(View v) { Toast.makeText(this,
	 * "Button1 clicked.", Toast.LENGTH_SHORT).show();
	 * 
	 * //cuando acabe el timer, se abre la app de foodland Intent i = new
	 * Intent(MainActivity.this, ListFoodActivity.class); startActivity(i);
	 * 
	 * // cierra la actividad finish(); }
	 */

}
