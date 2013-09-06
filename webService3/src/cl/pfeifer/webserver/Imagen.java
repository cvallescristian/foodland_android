package cl.pfeifer.webserver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;


public class Imagen {
	
	Bitmap bmp;
	
	public Imagen(String url_imagen){
	
		URL url;
		try {
			url = new URL(url_imagen);
			bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//parametro es la vista donde se mostrara la imagen
	public Bitmap getBitmap(){ return bmp; }
	
	

}

