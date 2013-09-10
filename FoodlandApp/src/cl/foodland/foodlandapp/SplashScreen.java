package cl.foodland.foodlandapp;

import cl.foodland.foodlandapp.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashScreen extends Activity {

	// tiempo en que se ve la pantalla de splash, en milisegundos
	private static int TIEMPO_ESPERA = 1000;


	private boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null) {
			// There are no active networks.
			return false;
		} else
			return true;
	}
	
	public void isGPSon() {
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    if( !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle("GPS Inactivo");  // GPS not found
	        builder.setMessage("Para buscar comida cerca tuyo automaticamente necesitamos usar tu GPS. ¿Deseas activarlo?"); // Want to enable?
	        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialogInterface, int i) {
	                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	            }
	        });
	        builder.setNegativeButton("No", null);
	        builder.create().show();
	        
	        return;
	    }
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_splash);
		
		
		

		// handler está con delay, luego abre foodland
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// cuando acabe el timer, se abre la app de foodland
				Intent i = new Intent(SplashScreen.this, MainActivity.class);
				startActivity(i);

				// cierra la actividad
				finish();
			}
		}, TIEMPO_ESPERA);
	}

}
