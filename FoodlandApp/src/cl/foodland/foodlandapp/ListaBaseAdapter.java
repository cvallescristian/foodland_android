package cl.foodland.foodlandapp;

import java.util.ArrayList;

import cl.foodland.foodlandapp.R;
import cl.foodland.webserver.Comida;

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
	
	private LayoutInflater l_Inflater;

	public ListaBaseAdapter(Context context, ArrayList<Comida> arrComida) {
		itemDetailsrrayList = arrComida;
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
			//holder.txt_itemDescription = (TextView) convertView.findViewById(R.id.descripcion);
			//holder.txt_itemPrice = (TextView) convertView.findViewById(R.id.preciocomida);
			
			holder.itemImage = (ImageView) convertView.findViewById(R.id.fotocomida);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_itemName.setText(itemDetailsrrayList.get(position).getTituloProducto());
		//holder.txt_itemDescription.setText(itemDetailsrrayList.get(position).getDescripcion());
		//holder.txt_itemPrice.setText(itemDetailsrrayList.get(position).getPrecio());
		holder.itemImage.setImageBitmap(itemDetailsrrayList.get(position).getBitmap()); 

		return convertView;
	}

	static class ViewHolder {
		TextView txt_itemName;
		TextView txt_itemDescription;
		TextView txt_itemPrice;
		ImageView itemImage;
	}
}
