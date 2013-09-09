package cl.foodland.foodlandapp;

import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListFoodActivity extends Activity {

	ListView lista;

	public void llenarLista() {

		ArrayList<Comida> image_details = GetSearchResults();

		this.lista = (ListView) findViewById(R.id.listafood_main);

		lista.setAdapter(new ListaBaseAdapter(this, image_details));

		lista.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lista.getItemAtPosition(position);
				Comida obj_comida = (Comida) o;
				Toast.makeText(ListFoodActivity.this,
						"Has tocado" + " " + obj_comida.getNombre(),
						Toast.LENGTH_LONG).show();
			}
		});
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listafood);
		llenarLista();

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

	public void irArriba(View v) {
		this.lista.setSelection(0);
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
			if (srcBmp.getWidth() >= srcBmp.getHeight()) {

				dstBmp = Bitmap.createBitmap(srcBmp, srcBmp.getWidth() / 2
						- srcBmp.getHeight() / 2, 0, srcBmp.getHeight(),
						srcBmp.getHeight());

			} else {

				dstBmp = Bitmap.createBitmap(srcBmp, 0, srcBmp.getHeight() / 2
						- srcBmp.getWidth() / 2, srcBmp.getWidth(),
						srcBmp.getWidth());
			}
			return dstBmp;
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
				convertView = l_Inflater.inflate(
						R.layout.activity_sublistafood, null);
				holder = new ViewHolder();
				holder.txt_itemName = (TextView) convertView
						.findViewById(R.id.nombrecomida);
				holder.itemImage = (ImageView) convertView
						.findViewById(R.id.fotocomida);
				//Bitmap bmp = BitmapFactory.decodeResource(getResources(),imgid[itemDetailsrrayList.get(position).getNumero() - 1]);
				//holder.itemImage.setImageBitmap(getRoundedCornerBitmap(bmp));
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.txt_itemName.setText(itemDetailsrrayList.get(position)
					.getNombre());
			Bitmap bmp = BitmapFactory.decodeResource(getResources(),imgid[itemDetailsrrayList.get(position).getNumero() - 1]);
			holder.itemImage.setImageBitmap(getRoundedCornerBitmap(cropResize(bmp)));
			
			//if (holder.itemImage != null) {
			//	new LoadImage(holder.itemImage,position).execute();
			//}

			// holder.itemImage.setImageResource(imgid[itemDetailsrrayList.get(position).getNumero()
			// - 1]);
			// imageLoader.DisplayImage("http://192.168.1.28:8082/ANDROID/images/BEVE.jpeg",
			// holder.itemImage);

			return convertView;
		}

		/*
		class LoadImage extends AsyncTask<ImageView, Void, Bitmap> {

			private ImageView imv;
			private String path;
			private int position;

			public LoadImage(ImageView imagev,int p) {
				
				this.imv = imagev;
				this.path = imagev.getTag().toString();
				this.position = p;
				
			}

			@Override
			protected Bitmap doInBackground(ImageView... params) {
				Bitmap bitmap = null;
				bitmap = BitmapFactory
						.decodeResource(getResources(),
								imgid[itemDetailsrrayList.get(position)
										.getNumero() - 1]);
				return getRoundedCornerBitmap(bitmap);
			}

			@Override
			protected void onPostExecute(Bitmap result) {
				if (!imv.getTag().toString().equals(path)) {
					return;
				}

				if (result != null && imv != null) {
					imv.setVisibility(View.VISIBLE);
					imv.setImageBitmap(result);
				} else {
					imv.setVisibility(View.GONE);
				}
			}

		}

 */
		class ViewHolder {
			TextView txt_itemName;
			ImageView itemImage;
		}

	}

}