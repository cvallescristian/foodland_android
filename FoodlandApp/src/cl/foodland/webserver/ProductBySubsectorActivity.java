package cl.foodland.webserver;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cl.foodland.foodlandapp.ProductActivity;
import cl.foodland.foodlandapp.R;
import cl.foodland.webserver.ListFoodActivity.ListaBaseAdapter;
import cl.foodland.webserver.ListFoodActivity.ListaBaseAdapter.ViewHolder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ProductBySubsectorActivity extends Activity {

	
	
	private static final String accionSoap_getProductById = "http://10.10.5.179/foodland_test2/service.php/getProductbyIdSubsector";
	private static final String metodo_getProductById = "getProductbyIdSubsector";

	private static final String namespace = "localhost/foodland_test2";
	private static final String url = "http://10.10.5.179/foodland_test2/service.php";

	ArrayList<String> var = new ArrayList();
	ArrayList<String> value = new ArrayList();

	Service serviceGetProduct;
	TextView tv;
	String jsonString;

	ArrayList<Comida> arregloComida;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listafood);

		
		Bundle bundle = getIntent().getExtras();

		String idSubSector = bundle.getString("IdSubSector");

		var.add("subsector");
		value.add(idSubSector);
		new Thread(new Runnable() {
			public void run() {
				// crea servicio y lo llama retorna arreglo de objetos
				// en json
				serviceGetProduct = new Service(accionSoap_getProductById,
						metodo_getProductById, namespace, url);
				jsonString = serviceGetProduct.GetService(var, value);
			}
		}).run();

		Log.d("prueba",jsonString);
		// se vacian listas para siguiente consulta
		var.clear();
		value.clear();

		arregloComida = GetFood(jsonString);
		Log.d("prueba", "se tiene el arreglo de comida");


		/*
		 * Ingresa arreglo de comida a la vista vista es click listener para
		 * mostrar detalles de productos si se clickea
		 */
		TextView criterioTitulo = (TextView) findViewById(R.id.criterio_title);
		criterioTitulo.setText("Productos "
				+ arregloComida.get(0).getSubsector() + " - "
				+ arregloComida.get(0).getSector());

		final ListView lista = (ListView) findViewById(R.id.listafood_main);
		lista.setAdapter(new ListaBaseAdapter(this, arregloComida));
		lista.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lista.getItemAtPosition(position);
				Comida obj_comida = (Comida) o;

				Intent i = new Intent(ProductBySubsectorActivity.this,
						ProductActivity.class);
				i.putExtra("Comida", obj_comida);

				startActivity(i);
			}
		});
	}

	//destruye arreglo de comida pause
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
	    if ((keyCode == KeyEvent.KEYCODE_BACK))
	    {
	    	arregloComida.clear();
	    	
	        finish();
	    }
	    return super.onKeyDown(keyCode, event);
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

					// crea boton de llamado
					//arregloComida.get(i).createCallButton(this);
					//arregloComida.get(i).createImageView(this);

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return arregloComida;
	}
	
	
	public class ListaBaseAdapter extends BaseAdapter {
		private ArrayList<Comida> itemDetailsrrayList;

		public Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
			Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(output);

			final int color = 0xff424242;
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmap.getWidth(),
					bitmap.getHeight());
			final RectF rectF = new RectF(rect);
			final float roundPx = 12;

			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);

			return output;
		}

		public Bitmap cropResize(Bitmap srcBmp) {
			Bitmap dstBmp;
			int ht_px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 480, getResources().getDisplayMetrics());
			int wt_px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 360, getResources().getDisplayMetrics());
			Log.i("cropresize", "w y h son " + srcBmp.getWidth() + " " + srcBmp.getHeight());
			
			float div = ( (float) srcBmp.getWidth() ) / ( (float) srcBmp.getHeight() );
			float posx =  (((float) srcBmp.getWidth() / (float) 2 ) - (((float) srcBmp.getHeight()) * (float) 1.33333) / (float) 2);
			Log.i("cropresize", "div es " + div);
			Log.i("cropresize", "posx es " + posx);
			Log.i("cropresize", "posx en int es " + (int) posx);
			
			if (div > 1.34){
				
				Log.i("cropresize", "crop, division es " + (srcBmp.getWidth()/srcBmp.getHeight()));
				dstBmp = Bitmap.createBitmap(srcBmp, (int) posx , 0, srcBmp.getWidth() - (int) posx,
						srcBmp.getHeight());
				dstBmp = Bitmap.createScaledBitmap(dstBmp, ht_px, wt_px,true);
			}
			else{
				Log.i("cropresize", "NOTCROP, division es " + (srcBmp.getWidth()/srcBmp.getHeight()));
				dstBmp = Bitmap.createScaledBitmap(srcBmp, ht_px, wt_px,true);
				
			}
			
			 float radius = (float) (dstBmp.getWidth()/1.5);
			    RadialGradient gradient = new RadialGradient(dstBmp.getWidth()/2, dstBmp.getHeight()/2, radius, Color.TRANSPARENT, getApplicationContext().getResources().getColor(R.color.negro50), Shader.TileMode.CLAMP);

			    Canvas canvas = new Canvas(dstBmp);
			    canvas.drawARGB(1, 0, 0, 0);

			    final Paint paint = new Paint();
			    paint.setAntiAlias(true);
			    paint.setColor(Color.BLACK);
			    paint.setShader(gradient);

			    final Rect rect = new Rect(0, 0, dstBmp.getWidth(), dstBmp.getHeight());
			    final RectF rectf = new RectF(rect);

			    canvas.drawRect(rectf, paint);

			    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			    canvas.drawBitmap(dstBmp, rect, rect, paint);
			    return dstBmp;
			
			//srcBmp.getWidth()
		}

		private Integer[] imgid = { R.drawable.pizza, R.drawable.sushi1,
				R.drawable.sushi2, R.drawable.barrosluco, R.drawable.soy };

		private LayoutInflater l_Inflater;

		public ListaBaseAdapter(Context context, ArrayList<Comida> results) {
			itemDetailsrrayList = results;
			l_Inflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return itemDetailsrrayList.size();
		}

		public Object getItem(int position) {
			return itemDetailsrrayList.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = l_Inflater.inflate(R.layout.activity_sublistafood, null);
				holder = new ViewHolder();
				holder.txt_itemName = (TextView) convertView.findViewById(R.id.nombrecomida);
				holder.itemImage = (ImageView) convertView.findViewById(R.id.fotocomida);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.txt_itemName.setText(itemDetailsrrayList.get(position).getTituloProducto());
			//holder.itemImage.setImageBitmap((getRoundedCornerBitmap(cropResize(itemDetailsrrayList.get(position).getBitmap()))));
			
			
			
			class BitmapTask extends AsyncTask<Object, Void, ViewHolder> {
			    private ProgressDialog dialog = new ProgressDialog(ProductBySubsectorActivity.this);

			    protected ViewHolder doInBackground(Object... params) {
			    	ViewHolder v = (ViewHolder) params[0];
			    	int position = (Integer) params[1];
			    	v.itemImage.setImageBitmap((getRoundedCornerBitmap(cropResize(itemDetailsrrayList.get(position).getBitmap()))));
			    	return v;
			    }
			    
			}
			
			try {
				holder = (new BitmapTask().execute(holder,position)).get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return convertView;
		}
		
		class ViewHolder {
			TextView txt_itemName;
			ImageView itemImage;
		}

	}

}