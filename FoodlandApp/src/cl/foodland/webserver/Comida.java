package cl.foodland.webserver;

import java.io.Serializable;

import cl.foodland.webserver.Imagen;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


/*
 * Luego que se recibe el json del servidor cada objeto de la lista
 * se inicializa como objeto de la actividad
 * 
 * */
public class Comida implements Parcelable {
	
	/*este modo es pensado para reducir tiempo en las pruebas,
	 * siempre va a mostrar la consulta de plan - valparaiso,
	 * no necesita ni gps ni coneccion a intenet,
	 * variable esta en las clases ListfoodActivity y comida*/
	private Boolean MODE_OFFLINE = true; 
	
	
	private String idProducto;
	private String idLocal;
	private String precio;
	private String tituloProducto;
	private String description;
	private String numeroPersonas;
	private String telefono;
	private String local;
	private String sector;
	private String subSector;
	
	Button button;
	Imagen imagen;
	ImageView imgview;
	
	
	public Comida(String idProducto, String idLocal, String precio, String tituloProducto, 
			String description, String numeroPersonas, String telefono, String local,String sector, String subSector){
		this.idProducto = idProducto;
		this.idLocal = idLocal;
		this.precio = precio;
		this.tituloProducto = tituloProducto;
		this.description = description;
		this.numeroPersonas = numeroPersonas;
		this.telefono = telefono;
		this.local = local;
		this.sector = sector;
		this.subSector = subSector;
		
		Log.d("prueba", idProducto);
		
		
		LoadProduct(idProducto);
			
	}
	
	public Comida(Parcel in){
		readFromParcel(in);
	}
	
	
	public void LoadProduct(String idProducto){
		if(MODE_OFFLINE)
			imagen = new Imagen("plan-valparaiso/" + idProducto+".jpeg","offline");
		else
			imagen = new Imagen("http://foodland.cl/img/locales/"+idProducto+".jpeg","online");
	}
	
	
	
	//GET atributos
	public String getIdProducto(){			return idProducto;		}
	public String getIdLocal(){				return idLocal;			}
	public String getPrecio(){				return precio;			}
	public String getTituloProducto(){		return tituloProducto;	}
	public String getDescription(){			return description;		}
	public String getNumeroPersonas(){		return numeroPersonas;	}
	public String getTelefono(){			return telefono;		}
	public String getLocal(){ 				return local;			}
	public String getSector(){				return sector;			}
	public String getSubsector(){				return subSector;	}
	
	//Otros GEt
	public Button getButtonCall(){			return button;			}
	public ImageView getImageView(){		return imgview;			}
	public Bitmap getBitmap(){				return imagen.getBitmap(); }
	
	
	
	
	
	
	//metodo para hacer llamada
	public void call(Activity mainActivity) {
		    try {
		        Intent callIntent = new Intent(Intent.ACTION_CALL);	
		        callIntent.setData(Uri.parse("tel:"+telefono));
		        mainActivity.startActivity(callIntent);
		    } catch (ActivityNotFoundException e) {
		        Log.e("helloandroid dialing example", "Call failed", e);
		    }
	}


	public void writeToParcel(Parcel dest, int flags) {  
		
		
		dest.writeString(idProducto); 
		dest.writeString(idLocal); 
		dest.writeString(precio);
		dest.writeString(tituloProducto); 
		dest.writeString(description );
		dest.writeString(numeroPersonas);
		dest.writeString(telefono);
		dest.writeString(local);
		dest.writeString(sector);
		dest.writeString(subSector);
		
	} 
	
	
	
	
	private void readFromParcel(Parcel in) {   
		
		idProducto = in.readString();
		idLocal = in.readString();
		precio = in.readString();
		tituloProducto = in.readString();
		description = in.readString();
		numeroPersonas = in.readString();
		telefono = in.readString();
		local = in.readString();
		sector = in.readString();
		subSector = in.readString();
		
	}
	
	
	

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { 
		public Comida createFromParcel(Parcel in) { return new Comida(in); }  
		public Comida[] newArray(int size) { return new Comida[size]; } }; 
		
}