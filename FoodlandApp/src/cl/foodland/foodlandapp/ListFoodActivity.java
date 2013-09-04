package cl.foodland.foodlandapp;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListFoodActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listafood);

		ArrayList<Comida> image_details = GetSearchResults();

		final ListView lista = (ListView) findViewById(R.id.listafood_main);
		
		lista.setAdapter(new ListaBaseAdapter(this, image_details));

		lista.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lista.getItemAtPosition(position);
				Comida obj_comida = (Comida) o;
				Toast.makeText(ListFoodActivity.this,
						"Has tocado " + " " + obj_comida.getNombre(),
						Toast.LENGTH_LONG).show();
			}
		});
	}

	private ArrayList<Comida> GetSearchResults() {
		ArrayList<Comida> results = new ArrayList<Comida>();

		Comida c = new Comida();
		c.setNombre("Pizza");
		c.setDescripcion("pizaaaaa");
		c.setPrecio("$6990");
		c.setNumero(1);
		results.add(c);

		c = new Comida();
		c.setNombre("Sushi");
		c.setDescripcion("HAY TABLA!");
		c.setPrecio("$9990");
		c.setNumero(2);
		results.add(c);

		c = new Comida();
		c.setNombre("Sushi 2");
		c.setDescripcion("mas sushi");
		c.setPrecio("$14990");
		c.setNumero(3);
		results.add(c);

		c = new Comida();
		c.setNombre("Barros Luco");
		c.setDescripcion("from Chile with love");
		c.setPrecio("$2990");
		c.setNumero(4);
		results.add(c);

		c = new Comida();
		c.setNombre("Cubo de Soya");
		c.setDescripcion("cuadrado, como un sansano");
		c.setPrecio("$3990");
		c.setNumero(5);
		results.add(c);

		return results;
	}
}