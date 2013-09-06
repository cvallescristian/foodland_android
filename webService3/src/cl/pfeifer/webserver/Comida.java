package cl.pfeifer.webserver;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;


/*
 * Luego que se recibe el json del servidor cada objeto de la lista
 * se inicializa como objeto de la actividad
 * 
 * */
public class Comida {
	
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
	
	
	public Comida(String idLocal, String precio, String tituloProducto, 
			String description, String numeroPersonas, String telefono, String local,String sector, String subSector){
		this.idLocal = idLocal;
		this.precio = precio;
		this.tituloProducto = tituloProducto;
		this.description = description;
		this.numeroPersonas = numeroPersonas;
		this.telefono = telefono;
		this.local = local;
		this.sector = sector;
		this.subSector = subSector;
		
	}
	
	
	
	//GET atributos
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
	
	
	
	//parametro pasado es la actividad donde se creara el boton
	//este parametro sirve tambien para asociar el intent de llamado a la actividad
	public void createCallButton(final Activity mainActivity){
		button = new Button(mainActivity);
	    button.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                call(telefono,mainActivity);
             }
        });
	    
	    button.setText("LLamar");
	}
	
	
	
	//metodo para hacer llamada
	private void call(String telefono,Activity mainActivity) {
		    try {
		        Intent callIntent = new Intent(Intent.ACTION_CALL);	
		        callIntent.setData(Uri.parse("tel:"+telefono));
		        mainActivity.startActivity(callIntent);
		    } catch (ActivityNotFoundException e) {
		        Log.e("helloandroid dialing example", "Call failed", e);
		    }
		}
	
	
	
}
