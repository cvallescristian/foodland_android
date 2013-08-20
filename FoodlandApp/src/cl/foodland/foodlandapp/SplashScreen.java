package cl.foodland.foodlandapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashScreen extends Activity {

	// tiempo en que se ve la pantalla de splash, en milisegundos
	private static int TIEMPO_ESPERA = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_splash);

		//handler está con delay, luego abre foodland
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				//cuando acabe el timer, se abre la app de foodland
				Intent i = new Intent(SplashScreen.this, MainActivity.class);
				startActivity(i);

				// cierra la actividad
				finish();
			}
		}, TIEMPO_ESPERA);
	}

}
