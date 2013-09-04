package cl.foodland.foodlandapp;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListaBaseAdapter extends BaseAdapter {
	private static ArrayList<Comida> itemDetailsrrayList;
	
	
	public Bitmap cropResize(Bitmap srcBmp) {
		Bitmap dstBmp;
		if (srcBmp.getWidth() >= srcBmp.getHeight()){

			  dstBmp = Bitmap.createBitmap(
			     srcBmp, 
			     srcBmp.getWidth()/2 - srcBmp.getHeight()/2,
			     0,
			     srcBmp.getHeight(), 
			     srcBmp.getHeight()
			     );

			}else{

			  dstBmp = Bitmap.createBitmap(
			     srcBmp,
			     0, 
			     srcBmp.getHeight()/2 - srcBmp.getWidth()/2,
			     srcBmp.getWidth(),
			     srcBmp.getWidth() 
			     );
			}
		return dstBmp;
	}
	
	private Integer[] imgid = {
			R.drawable.pizza,
			R.drawable.sushi1,
			R.drawable.sushi2,
			R.drawable.barrosluco,
			R.drawable.soy
			};
	
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
			holder.txt_itemDescription = (TextView) convertView.findViewById(R.id.descripcion);
			holder.txt_itemPrice = (TextView) convertView.findViewById(R.id.preciocomida);
			
			Bitmap bm = BitmapFactory.decodeResource(ListFoodActivity.getResources(), R.drawable.pizza)
			holder.itemImage = (ImageView) convertView.findViewById(R.id.fotocomida);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_itemName.setText(itemDetailsrrayList.get(position).getNombre());
		holder.txt_itemDescription.setText(itemDetailsrrayList.get(position).getDescripcion());
		holder.txt_itemPrice.setText(itemDetailsrrayList.get(position).getPrecio());
		holder.itemImage.setImageResource(imgid[itemDetailsrrayList.get(position).getNumero() - 1]);
//		imageLoader.DisplayImage("http://192.168.1.28:8082/ANDROID/images/BEVE.jpeg", holder.itemImage);

		return convertView;
	}

	static class ViewHolder {
		TextView txt_itemName;
		TextView txt_itemDescription;
		TextView txt_itemPrice;
		ImageView itemImage;
	}
}
