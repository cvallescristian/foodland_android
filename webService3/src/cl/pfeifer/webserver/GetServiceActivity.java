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
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


public class GetServiceActivity extends Activity {

	TextView tv;
	Service service;
	
	
	private static final String accionSoap = "http://190.54.87.101/foodland_test2/service.php/getproductbygps";
	private static final String metodo = "getproductbygps";
	private static final String namespace = "190.54.87.101/foodland_test2";
	private static final String url = "http://190.54.87.101/foodland_test2/service.php"; 

	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//tv = (TextView) findViewById(R.id.text1);
		
		//parametros webserver
		ArrayList<String> var = new ArrayList();
		ArrayList<String> value = new ArrayList();
		
		//Lista de comida que se obtendra
		ArrayList<Comida> arregloComida;
		
		
		//GPS
		var.add("latitud"); value.add("-33.5353543");
		var.add("longitud"); value.add("-177.2313123");
		
		
		
		//crea servicio y lo llama retorna arreglo de objetos en json
		service = new Service(accionSoap,metodo,namespace,url);		
		String jsonString = service.GetService(var, value);
		
		//se parse json y retorna arrgelo de objetos comida
		arregloComida = GetFood(jsonString);

		//para mostrar resultado
		ScrollView sv = new ScrollView(this);
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		sv.addView(ll);
		
		
		for(int i = 0; i < arregloComida.size() ; i++) {
		    TextView tv = new TextView(this);
		    
		    
		    
		    tv.setText("Nombre: "+ arregloComida.get(i).getName() + "\n Descripcion: " + arregloComida.get(i).getDescription());
		    
		    ll.addView(tv);
		}

		this.setContentView(sv);

		
		//tv.setText(""+result);
		
	}
	
	
	
	
	
	//funcion que trasforma arreglo JSON en un arreglo de comidas
	public ArrayList<Comida> GetFood(String jsonString){
		
		ArrayList<Comida> arregloComida = new ArrayList<Comida>();
		JSONArray mJsonArray;
		
		try {
			mJsonArray = new JSONArray(jsonString);
			JSONObject mJsonObject = new JSONObject();
			for (int i = 0; i < mJsonArray.length(); i++) {
			    mJsonObject = mJsonArray.getJSONObject(i);
			    
			    String id =			mJsonObject.getString("ID");
			    String id_sector =	mJsonObject.getString("id_sector");
			    String name =		mJsonObject.getString("nombre");
			    String description =mJsonObject.getString("descripcion");
			    arregloComida.add(new Comida(id,id_sector,name,description));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return arregloComida;
	}
	
	
	
}
