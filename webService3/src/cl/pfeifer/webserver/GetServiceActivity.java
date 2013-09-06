package cl.pfeifer.webserver;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class GetServiceActivity extends Activity {

	TextView tv;
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
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//tv = (TextView) findViewById(R.id.text1);
		
		
		//Lista de comida que se obtendra
		final ArrayList<Comida> arregloComida;
		
		
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
		
		//Log.d("prueba",jsonString);
		Log.d("prueba","se tiene el json");
		
		//se parse json y retorna arrgelo de objetos comida
		arregloComida = GetFood(jsonString);
		Log.d("prueba","se tiene el arreglo de comida");
		
		//para mostrar resultado
		ScrollView sv = new ScrollView(this);
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		sv.addView(ll);
		
		
		//prueba imagen--- ¿¿¿¿¿como obtener url????
		Imagen imagen = new Imagen("http://foodland.cl/img/locales/65.jpeg");
		ImageView imgview = new ImageView(this);
		imgview.setImageBitmap(imagen.getBitmap());
		ll.addView(imgview);
		
		
		for( int i = 0; i < arregloComida.size() ; i++) {
		    TextView tv = new TextView(this);
		    
		    
		    tv.setText("Nombre: "+ arregloComida.get(i).getTituloProducto() + 
		    		"\n Descripcion: " + arregloComida.get(i).getDescription() +
		    		"\n	precio:	"	+ arregloComida.get(i).getPrecio()  +
		    		"\n sector: " + arregloComida.get(i).getSubsector() + " - " + arregloComida.get(i).getSector()
		    		);
		    
		    
		    ll.addView(tv);
		    ll.addView(arregloComida.get(i).getButtonCall());
		}

		this.setContentView(sv);

		
		//tv.setText(""+result);
		
	}

	// funcion que trasforma arreglo JSON en un arreglo de comidas
	public ArrayList<Comida> GetFood(String jsonString) {

		ArrayList<Comida> arregloComida = new ArrayList<Comida>();
		JSONArray mJsonArray;

		try {
			mJsonArray = new JSONArray(jsonString);
			JSONObject mJsonObject = new JSONObject();
			for (int i = 0; i < mJsonArray.length(); i++) {
				mJsonObject = mJsonArray.getJSONObject(i);

				String idLocal = mJsonObject.getString("id_local");
				String precio = mJsonObject.getString("precio");
				String titulo = mJsonObject.getString("titulo");
				String description = mJsonObject.getString("descripcion");
				String numeroPersonas = mJsonObject.getString("numero_personas");
				String telefono = mJsonObject.getString("telefono");
				String local = mJsonObject.getString("local");
				String sector = mJsonObject.getString("nombreSector");
				String subSector = mJsonObject.getString("nombreSubsector");
				
				arregloComida.add(new Comida(idLocal, precio, titulo,
						description, numeroPersonas, telefono, local,sector,subSector));

				// crea boton de llamado
				arregloComida.get(i).createCallButton(this);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arregloComida;
	}

}
