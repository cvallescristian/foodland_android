package cl.foodland.webserver;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cl.foodland.foodlandapp.ListaBaseAdapter;
import cl.foodland.foodlandapp.MainActivity;
import cl.foodland.foodlandapp.ProductActivity;
import cl.foodland.foodlandapp.R;
import cl.foodland.foodlandapp.SplashScreen;
import cl.foodland.foodlandapp.R.id;
import cl.foodland.foodlandapp.R.layout;
import cl.foodland.webserver.Comida;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListFoodActivity extends Activity {
	
	/*este modo es pensado para reducir tiempo en las pruebas
	 * siempre va a mostrar la consulta de plan - valparaiso
	 * no necesita ni gps ni coneccion a intenet
	 *variable esta en las clases ListfoodActivity y comida*/
	Boolean MODE_OFFLINE = true; 
	
	Service service;

	private static final String accionSoap = "http://186.34.227.34/foodland_test2/service.php/getproductbygps";
	private static final String metodo = "getproductbygps";
	private static final String namespace = "186.34.227.34/foodland_test2";
	private static final String url = "http://186.34.227.34/foodland_test2/service.php";

	// parametros webserver
	ArrayList<String> var = new ArrayList();
	ArrayList<String> value = new ArrayList();

	//string respuesta con los productos
	String jsonString;
	
	
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listafood);

		//*********************
		//Obtiene comida del servidor
		//***********************
		final ArrayList<Comida> arregloComida;
		
		
		//obtiene json dependiendo del modo 
		if (MODE_OFFLINE){
			Imagen.setContext(this);	//para cargar imagenes de la carpeta Assets
			setResultQueryOffline();
		}
		else
			setResultQueryOnline();
		
		
		
		//Log.d("prueba",jsonString);
		
		//se parse json y retorna arrgelo de objetos comida
		arregloComida = GetFood(jsonString);
		Log.d("prueba","se tiene el arreglo de comida");
		
		
		
		/*
		 * Ingresa arreglo de comida a la vista
		 * vista es click listener para mostrar detalles de productos si se clickea
		 * 
		 * */
		TextView criterioTitulo = (TextView) findViewById(R.id.criterio_title);
		criterioTitulo.setText("Productos " + arregloComida.get(0).getSubsector() + " - " + arregloComida.get(0).getSector());
		
		
		final ListView lista = (ListView) findViewById(R.id.listafood_main);
		lista.setAdapter(new ListaBaseAdapter(this, arregloComida));
		lista.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lista.getItemAtPosition(position);
				Comida obj_comida = (Comida) o;
				
				Intent i = new Intent(ListFoodActivity.this, ProductActivity.class);
				i.putExtra("Comida", obj_comida);
				
				startActivity(i);
			}
		});
	}
	
	
	//obtiene json modo offline
	public void setResultQueryOffline(){
		Log.d("prueba","offline");
		try {
	            // get input stream for text
	            InputStream is = getAssets().open("plan-valparaiso.txt");
	            // check size
	            int size = is.available();
	            // create buffer for IO
	            byte[] buffer = new byte[size];
	            // get data to buffer
	            is.read(buffer);
	            // close stream
	            is.close();
	            // set result to TextView
	            jsonString = new String(buffer);
		 	}
		 catch (IOException ex) {
	     }
	}
	
	
	
	//obtienen Json modo online
	public void setResultQueryOnline(){
			Log.d("prueba","online");	
			//GPS
			GPSTracker gps = new GPSTracker(this);
			double latitud = gps.getLatitude();
			double longitud = gps.getLongitude();
			Log.d("prueba",""+latitud +","+longitud);
			
			String slatitud = String.valueOf(latitud);
			String slongitud = String.valueOf(longitud);
			
			
			var.add("latitud"); value.add(slatitud);
			var.add("longitud"); value.add(slongitud);
			
			
			//hebra que llama al servicio
			new Thread(new Runnable() {
				public void run() {
					//crea servicio y lo llama retorna arreglo de objetos en json
					service = new Service(accionSoap,metodo,namespace,url);		
					jsonString = service.GetService(var, value);
				}
			}).run();
	}
	
	

	// funcion que trasforma arreglo JSON en un arreglo de comidas
	public ArrayList<Comida> GetFood(String jsonString) {

		ArrayList<Comida> arregloComida = new ArrayList<Comida>();
		JSONArray mJsonArray;

		// caso de que se retorne un json vacio
		if (jsonString == "[]") {
			arregloComida = null;
		} else {
			try {
				mJsonArray = new JSONArray(jsonString);
				JSONObject mJsonObject = new JSONObject();
				for (int i = 0; i < mJsonArray.length(); i++) {
					mJsonObject = mJsonArray.getJSONObject(i);

					String idProducto = mJsonObject.getString("id_producto");
					String idLocal = mJsonObject.getString("id_local");
					String precio = mJsonObject.getString("precio");
					String titulo = mJsonObject.getString("titulo");
					String description = mJsonObject.getString("descripcion");
					String numeroPersonas = mJsonObject
							.getString("numero_personas");
					String telefono = mJsonObject.getString("telefono");
					String local = mJsonObject.getString("local");
					String sector = mJsonObject.getString("nombreSector");
					String subSector = mJsonObject.getString("nombreSubsector");

					arregloComida.add(new Comida(idProducto, idLocal, precio,
							titulo, description, numeroPersonas, telefono,
							local, sector, subSector));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return arregloComida;
	}
	
}