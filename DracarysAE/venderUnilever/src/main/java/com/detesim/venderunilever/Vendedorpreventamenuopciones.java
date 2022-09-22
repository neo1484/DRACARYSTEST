package com.detesim.venderunilever;

import BLL.ClientePreventaBLL;
import BLL.MyLogBLL;
import BLL.ParametroGeneralBLL;
import Clases.ClientePreventa;
import Clases.LoginEmpleado;
import Clases.ParametroGeneral;
import Utilidades.Utilidades;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Vendedorpreventamenuopciones extends Activity implements OnClickListener
{
	LinearLayout llVendedorPreventaMenu;
	Utilidades theUtilidades = new Utilidades();
	LoginEmpleado loginEmpleado;
	MyLogBLL myLog = new MyLogBLL();
	
	ParametroGeneral parametroGeneral;
	int preventaIdDispositivo;
	int preventaIdServer;
	int ventaDirectaIdDispositivo;
	int ventaDirectaIdServer;
	int clienteId;
	boolean ventaDirecta;

	ImageView ivMaterialPOP;
	ImageView ivCambios;
	Button btnMapa;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedorpreventamenuopciones);
		
		llVendedorPreventaMenu = (LinearLayout)findViewById(R.id.llVendedorMenuOpciones);
		ivMaterialPOP = (ImageView) findViewById(R.id.ivVendedorMenuOpcionesPOP);
		ivMaterialPOP.setOnClickListener(this);
		ivCambios = (ImageView) findViewById(R.id.ivVendedorMenuOpcionesDevoluciones);
		ivCambios.setOnClickListener(this);
		btnMapa = (Button)findViewById(R.id.btnVendedorMenuOpcionesVerMapa);
		btnMapa.setOnClickListener(this);
		
		llVendedorPreventaMenu.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
		ventaDirecta = false;
	    ventaDirecta = getIntent().getExtras().getBoolean("ventaDirecta");
		
	    if(ventaDirecta)
	    {
	    	ventaDirectaIdDispositivo = 0;
			ventaDirectaIdDispositivo = getIntent().getExtras().getInt("ventaDirectaIdDispositivo");
		    if(ventaDirectaIdDispositivo == 0)
		    {
		    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la ventaDirectaIdDispositivo.", 1);
		    	ivCambios.setVisibility(View.INVISIBLE);
		    	ivMaterialPOP.setVisibility(View.INVISIBLE);
		    	return;
		    }
		    
		    ventaDirectaIdServer = 0;
			ventaDirectaIdServer = getIntent().getExtras().getInt("ventaDirectaIdServer");
			ivCambios.setVisibility(View.INVISIBLE);
	    }
	    else
	    {
	    	preventaIdDispositivo = 0;
			preventaIdDispositivo = getIntent().getExtras().getInt("preventaIdDispositivo");
		    if(preventaIdDispositivo == 0)
		    {
		    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la preventaIdDispositivo.", 1);
		    	return;
		    }
		    
			preventaIdServer = 0;
			preventaIdServer = getIntent().getExtras().getInt("preventaIdServer");
	    }

	    clienteId = 0;
	    clienteId = getIntent().getExtras().getInt("clienteId");
	    if(clienteId == 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el clienteId.", 1);
	    	return;
	    }
	    
	    ClientePreventa theCliente = null;
	    try
	    {
	    	theCliente = new ClientePreventaBLL().ObtenerClientePreventaPor(clienteId);
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del clientePreventa: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del clientePreventa: " + localException.getMessage());
	    	} 
	    }
	    
	    if(theCliente==null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se Pudo Obtener el cliente preventa.", 1);
	    }
	    else
	    {
	    	DeterminarPantallaMostrar();
	    }
	}
	
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.ivVendedorMenuOpcionesPOP:
			MostrarPantallaPOP();
			break;
		case R.id.ivVendedorMenuOpcionesDevoluciones:
			MostrarPantallaCambios();
			break;
		case R.id.btnVendedorMenuOpcionesVerMapa:
			MostrarPantallaMapaPreventas();
			break;
		}		
	}
	
	private void DeterminarPantallaMostrar()
	{
		parametroGeneral = null;
	    try
	    {
	    	parametroGeneral = new ParametroGeneralBLL().ObtenerParamatroGeneral();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los parametros generales: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los parametros generales: " + localException.getMessage());
	    	} 
	    }
	    
	    if (parametroGeneral == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los parametros generales.", 1);
	    }
	    else
	    {
	    	if(!(parametroGeneral.is_habilitarPop() && parametroGeneral.is_habilitarCambio()))
	    	{
	    		if(parametroGeneral.is_habilitarPop() && (parametroGeneral.is_habilitarCambio()==false))
	    		{
	    			MostrarPantallaPOP();
	    		}
	    		
	    		if((parametroGeneral.is_habilitarPop() == false) && parametroGeneral.is_habilitarCambio())
	    		{
	    			MostrarPantallaCambios();
	    		}
	    	}
	    }
	}
	
	public void MostrarPantallaPOP()
	{
		Intent localIntent = new Intent(this, Vendedorpreventaproductopop.class);
		localIntent.putExtra("preventaIdPOP", preventaIdDispositivo);
		localIntent.putExtra("preventaIdPOPServer", preventaIdServer);
		localIntent.putExtra("clienteId", clienteId);
		localIntent.putExtra("ventaDirectaIdPOP", ventaDirectaIdDispositivo);
		localIntent.putExtra("ventaDirectaIdPOPServer", ventaDirectaIdServer);
		localIntent.putExtra("ventaDirecta", ventaDirecta);
		startActivity(localIntent);
	}

	public void MostrarPantallaCambios()
	{
		Intent localIntent = new Intent(this, Vendedorpreventaproductocambio.class);
		localIntent.putExtra("preventaIdCambio", preventaIdDispositivo);
		localIntent.putExtra("preventaIdCambioServer", preventaIdServer);
		localIntent.putExtra("clienteId", clienteId);
		startActivity(localIntent);
	}
	
	public void MostrarPantallaMapaPreventas()
	{
		if(ventaDirecta)
		{
			startActivity(new Intent(this, Vendedorventadirectamapa.class));
		}
		else
		{
			startActivity(new Intent(this, Vendedormapaclientes.class));
		}
	}
	
	@Override
 	public void onBackPressed() 
	{
		super.onBackPressed();
		MostrarPantallaMapaPreventas();
	}
}
