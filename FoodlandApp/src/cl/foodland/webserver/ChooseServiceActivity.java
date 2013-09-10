package cl.foodland.webserver;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cl.foodland.foodlandapp.R;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseServiceActivity extends Activity {

	Service serviceGetIdSector;
	
	//10.10.5.179
	
	private static final String accionSoap_idSector = "http://10.10.5.179/foodland_test2/service.php/getSectores";
	private static final String metodo_idSector = "getSectores";

	private static final String accionSoap_idSubsector = "http://10.10.5.179/foodland_test2/service.php/getSubsectorsByIdSector";
	private static final String metodo_idSubsector = "getSubsectorsByIdSector";

	private static final String namespace = "10.10.5.179/foodland_test2";
	private static final String url = "http://10.10.5.179/foodland_test2/service.php";

	// parametros webserver
	ArrayList<String> var = new ArrayList();
	ArrayList<String> value = new ArrayList();

	// string respuesta a los servicios
	String jsonString;
	String idSector;
	String idSubsector;

	// spinner
	private Spinner spinnerSector;
	private Spinner spinnerSubsector;
	private Button buttonGetProducts;

	ArrayList<Element> arraySectors;
	ArrayList<Element> arraySubsectors;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose);

		spinnerSector = (Spinner) findViewById(R.id.sector_spinner);
		spinnerSubsector = (Spinner) findViewById(R.id.subsector_spinner);
		buttonGetProducts = (Button) findViewById(R.id.obtenerProductos);

		new Thread(new Runnable() {
			public void run() {
				Log.d("prueba", "service sector");
				// crea servicio y lo llama retorna arreglo de objetos en json
				serviceGetIdSector = new Service(accionSoap_idSector,
						metodo_idSector, namespace, url);
				jsonString = serviceGetIdSector.GetService(var, value);
			}
		}).run();

		Log.d("prueba", jsonString);
		// pasa json a arreglo de sectores
		arraySectors = getSectors(jsonString);

		// obtienen un arreglo de String para mostrarlo en spinners
		ArrayList<String> arraySectorNames = justName(arraySectors);
		String[] simpleSectorArray = new String[arraySectorNames.size()];
		arraySectorNames.toArray(simpleSectorArray);

		ArrayAdapter<String> adapterSector;
		adapterSector = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, simpleSectorArray);
		spinnerSector.setAdapter(adapterSector);
		

		// cuando se selecciona un sector se hace una consulta para obtener
		// subsectores
		spinnerSector.setOnItemSelectedListener(new OnItemSelectedListener() {

			ArrayAdapter<String> adapterSubsector;

			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {

				// se hace consulta para obtener id y nombres e los subsectores
				idSector = arraySectors.get(position).id;

				Log.d("prueba", "id es " + idSector);

				var.add("id_setor");
				value.add(idSector);
				new Thread(new Runnable() {
					public void run() {
						// crea servicio y lo llama retorna arreglo de objetos
						// en json
						serviceGetIdSector = new Service(
								accionSoap_idSubsector, metodo_idSubsector,
								namespace, url);
						jsonString = serviceGetIdSector.GetService(var, value);
					}
				}).run();

				// se vacian listas para siguiente consulta
				var.clear();
				value.clear();

				Log.d("prueba", jsonString);

				// json to arraylist<elements>
				arraySubsectors = getSubsectors(jsonString);

				// obtienen un arreglo de String para mostrarlo en spinners
				ArrayList<String> arraySubsectorNames = justName(arraySubsectors);
				String[] simpleSubsectorArray = new String[arraySubsectorNames
						.size()];
				arraySubsectorNames.toArray(simpleSubsectorArray);

				// y se muestran
				adapterSubsector = new ArrayAdapter<String>(
						ChooseServiceActivity.this,
						android.R.layout.simple_spinner_item,
						simpleSubsectorArray);
				spinnerSubsector.setAdapter(adapterSubsector);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// TODO Auto-generated method stub

			}

		});

		
		spinnerSubsector.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				// TODO Auto-generated method stub
				idSubsector = arraySubsectors.get(position).id;
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// TODO Auto-generated method stub
				
			}
		
		});
		
		buttonGetProducts.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ChooseServiceActivity.this, ProductBySubsectorActivity.class);
				
				//pasa valor del subsector a la actividad que busca los productos
				Bundle bundle = new Bundle();
				bundle.putString("IdSubSector", idSubsector); 
				i.putExtras(bundle);
				
				//pasa subsector para buscar los productos
				
				startActivity(i);
				
			}
		});
		
		
	}

	// funcion obtiene un arraylist con el nombre e id de los sectores
	public ArrayList<Element> getSectors(String json) {

		ArrayList<Element> arraySector = new ArrayList<Element>();
		JSONArray mJsonArray;

		// caso de que se retorne un json vacio
		if (jsonString == "[]") {
			arraySector = null;
		} else {
			try {
				mJsonArray = new JSONArray(jsonString);
				JSONObject mJsonObject = new JSONObject();
				for (int i = 0; i < mJsonArray.length(); i++) {
					mJsonObject = mJsonArray.getJSONObject(i);

					String idSector = mJsonObject.getString("id_sector");
					String nombreSector = mJsonObject
							.getString("nombre_sector");

					arraySector.add(new Element(idSector, nombreSector));

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return arraySector;
	}

	// funcion obtiene un arraylist con el nombre e id de los subsectores
	public ArrayList<Element> getSubsectors(String json) {

		ArrayList<Element> arraySubsector = new ArrayList<Element>();
		JSONArray mJsonArray;

		// caso de que se retorne un json vacio
		if (jsonString == "[]") {
			arraySubsector = null;
		} else {
			try {
				mJsonArray = new JSONArray(jsonString);
				JSONObject mJsonObject = new JSONObject();
				for (int i = 0; i < mJsonArray.length(); i++) {
					mJsonObject = mJsonArray.getJSONObject(i);

					String idSector = mJsonObject.getString("id_subsector");
					String nombreSector = mJsonObject
							.getString("nombre_subsector");

					arraySubsector.add(new Element(idSector, nombreSector));

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return arraySubsector;
	}

	// transforma arraylist de elementos a arraylist de string
	public ArrayList<String> justName(ArrayList<Element> arr) {
		ArrayList<String> names = new ArrayList<String>();

		for (int i = 0; i < arr.size(); i++) {
			names.add(arr.get(i).nombre);
		}

		return names;
	}

	// clase que almacena sectores y subsectores
	public class Element {
		public String id;
		public String nombre;

		public Element(String id, String nombre) {
			this.id = id;
			this.nombre = nombre;
		}

	}

}
