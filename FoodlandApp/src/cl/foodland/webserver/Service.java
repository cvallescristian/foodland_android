package cl.foodland.webserver;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

public class Service implements Runnable  {

	private  String accionSoap;
	private  String metodo;
	private  String namespace;
	private  String url; // ?wsdl


	// constructor puede recibir nombre del metodo y url
	public Service(String accionSoap, String metodo, String namespace, String url) {
		this.accionSoap = accionSoap;
		this.metodo = metodo;
		this.namespace = namespace;
		this.url = url;
		
	}

	
	
	
	// funcion que llama al servicio
	//como parametros recibe una lista de variables que emplea la funcion del servicio
	//y otra de valores de esta variable
	public String GetService(ArrayList<String> var ,ArrayList<String> value) {
		// se entregan datos al servicio y se obtiene los resultados
		try {
			
			// inicializa soap request ingresa parametros
			SoapObject request = new SoapObject(namespace, metodo);
			Log.i("service.java", "1");
			//ingresa parametros
			if(var.size() == value.size())
				for(int i = 0 ; i < var.size(); i++)
					request.addProperty(var.get(i), value.get(i));
			Log.i("service.java", "2");
			// modelo del sobre
			SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			sobre.setOutputSoapObject(request);
			Log.i("service.java", "3");
			// transporte
			HttpTransportSE transporte = new HttpTransportSE(url);
			Log.i("service.java", "4");
			Log.i("service.java", accionSoap);
			Log.i("service.java", metodo);
			Log.i("service.java", namespace);
			Log.i("service.java", url);
			// llamada
			transporte.call(accionSoap, sobre);
			Log.i("service.java", "5");
			// resultado
			SoapObject resultado = (SoapObject)sobre.bodyIn; 
			Log.i("service.java", "6");
			
						
			return  resultado.getProperty(0).toString();

		} catch (Exception e) {
			Log.i("service.java", "error de servicio: " + e.getMessage());
			return e.getMessage();
		}
	}




	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
