package cl.foodland.webserver;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;


public class Imagen {
	
	Bitmap bmp;
	static Context context;
	
	public Imagen(String url_imagen,String mode){
	
		Log.d("prueba","se creo imagen");
		
		if(mode.equals("online")){
			LoadWithScaleOnline(url_imagen);
			Log.d("prueba","online");
		}
		else{
			Log.d("prueba","offline");
			LoadWithScaleOffline(url_imagen);	//OBS, NO ES EL URL ES EL PATH EN EL DIRECTORIO
		}
		
	}
	
	//parametro es la vista donde se mostrara la imagen
	public Bitmap getBitmap(){ return bmp; }
	

	
	private void LoadWithScaleOnline(String url_imagen){
		URL url;
		BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
		try {
			url = new URL(url_imagen);
			bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream(),null,o);
			//The new size we want to scale to
	        final int REQUIRED_SIZE=200;

	        //Find the correct scale value. It should be the power of 2.
	        int scale=1;
	        while(o.outWidth/scale/2>=REQUIRED_SIZE && o.outHeight/scale/2>=REQUIRED_SIZE)
	            scale*=2;

	        //Decode with inSampleSize
	        BitmapFactory.Options o2 = new BitmapFactory.Options();
	        o2.inSampleSize=scale;
	        bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream(), null, o2);
	        
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	private void LoadWithScaleOffline(String path_imagen){
		BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        try {
            // get input stream
            InputStream ims = context.getAssets().open(path_imagen);
            
            if (ims == null) Log.d("prueba","no cargo ims");
            
            bmp = BitmapFactory.decodeStream(ims,null,o);
			//The new size we want to scale to
	        final int REQUIRED_SIZE=200;

	        //Find the correct scale value. It should be the power of 2.
	        int scale=1;
	        while(o.outWidth/scale/2>=REQUIRED_SIZE && o.outHeight/scale/2>=REQUIRED_SIZE)
	            scale*=2;

	        //Decode with inSampleSize
	        BitmapFactory.Options o2 = new BitmapFactory.Options();
	        o2.inSampleSize=scale;
	        bmp = BitmapFactory.decodeStream(ims, null, o2);

            ims.close();
        }
        catch(IOException ex) {
            return;
        }
		
	}
	
	public static void setContext(Context ctxt){
		context = ctxt;
	}
	
	
}



