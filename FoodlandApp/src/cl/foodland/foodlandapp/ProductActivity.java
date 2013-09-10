package cl.foodland.foodlandapp;

import cl.foodland.webserver.Comida;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ScrollView;
import android.widget.LinearLayout;

public class ProductActivity extends Activity {


	Comida comida;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

   
        
        //Intent i = getIntent();
        //Comida comida = (Comida)i.getSerializableExtra("Comida");
        
        Bundle b = getIntent().getExtras();
        comida = b.getParcelable("Comida");
        
        //vuelve a cargar imagen
        comida.LoadProduct(comida.getIdProducto());
        
        
        
        
        TextView tvTituloProducto = (TextView)findViewById(R.id.titulo_producto);
        TextView tvPrecioProducto = (TextView)findViewById(R.id.precio_producto);
        TextView tvDescripcionProducto = (TextView)findViewById(R.id.descripcion_producto);
        ImageView ivImagenProducto = (ImageView)findViewById(R.id.imageView);
        Button btCall = (Button)findViewById(R.id.button); 
        
       
        tvTituloProducto.setText(comida.getTituloProducto());
       	tvPrecioProducto.setText(comida.getPrecio());
       	tvDescripcionProducto.setText(comida.getDescription());
       	
       	ivImagenProducto.setImageBitmap(comida.getBitmap());
       	
       	
       	btCall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               comida.call(ProductActivity.this);
            }
       });
    
    }

    
  //destruye arreglo de comida pause
  	public boolean onKeyDown(int keyCode, KeyEvent event)
  	{
  	    if ((keyCode == KeyEvent.KEYCODE_BACK))
  	    {
  	    	finish();
  	    }
  	    return super.onKeyDown(keyCode, event);
  	}

  	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
