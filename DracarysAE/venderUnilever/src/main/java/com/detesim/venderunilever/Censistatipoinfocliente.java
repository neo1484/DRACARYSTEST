package com.detesim.venderunilever;

import java.util.ArrayList;

import BLL.MyLogBLL;
import BLL.RelevamientoBLL;
import BLL.RolBLL;
import Clases.LoginEmpleado;
import Clases.Relevamiento;
import Clases.Rol;
import Utilidades.Utilidades;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Censistatipoinfocliente extends Activity implements OnClickListener
{
	LinearLayout llCensistaTipoInfoCliente;
	MyLogBLL myLog = new MyLogBLL();
	Utilidades theUtilidades = new Utilidades();
	LoginEmpleado loginEmpleado;
	  
	double latitudMapa = 0;
	double longitudMapa = 0;
	boolean rolCensista;
	String Origen;
	  
	ImageView ivTipoInfoRegistrarCliente;
	ImageView ivTipoInfoPuntearCliente;
	ImageView ivRelevamiento;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_censistatipoinfocliente);
		
		llCensistaTipoInfoCliente = (LinearLayout)findViewById(R.id.CensistaTipoInfoClienteLinearLayout);
		ivTipoInfoRegistrarCliente = (ImageView)findViewById(R.id.imgbtnTipoInfoRegistrarCliente);
		ivTipoInfoRegistrarCliente.setOnClickListener(this);
		ivTipoInfoPuntearCliente = (ImageView)findViewById(R.id.imgbtnTipoInfoPuntearCliente);
		ivTipoInfoPuntearCliente.setOnClickListener(this);
		ivRelevamiento = (ImageView)findViewById(R.id.ibCenTipInfClieRelevamiento);
		ivRelevamiento.setOnClickListener(this);
		
		llCensistaTipoInfoCliente.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		ivTipoInfoRegistrarCliente.setVisibility(View.INVISIBLE);
		
		Origen = "";
	    Bundle localBundle = getIntent().getExtras();
	    latitudMapa = Double.parseDouble(localBundle.getString("Latitud"));
	    longitudMapa = Double.parseDouble(localBundle.getString("Longitud"));
	    Origen = localBundle.getString("Origen");
	    	    
	    if(latitudMapa == 0 || longitudMapa == 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las coordenadas del cliente.", 2);
	    	return;
	    }
	    
	    if (Origen == "") 
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el origen de la peticion.", 1);
	    	return;
	    }
	    
	    loginEmpleado = null;
	    
	    try
	    {
	    	loginEmpleado = theUtilidades.ObtenerLoginEmpleado();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el LoginEmpleado: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el LoginEmpleado: " + localException.getMessage());
	    	} 
	    }
	    
	    if (loginEmpleado == null)
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los datos del supervisor. ", 2);
	        return;
	    }
	    
	    if(TieneRolCensista())
	    {
	    	ivTipoInfoRegistrarCliente.setVisibility(View.VISIBLE);
	    }
	    
	    if(TieneRelevamientosActivos())
	    {
	    	ivRelevamiento.setVisibility(View.VISIBLE);
	    }
	    else
	    {
	    	ivRelevamiento.setVisibility(View.INVISIBLE);
	    }
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.imgbtnTipoInfoRegistrarCliente:
			MostrarPantallaRegistrarCliente();
			break;
		
		case R.id.imgbtnTipoInfoPuntearCliente:
			MostrarPantallaPunteoCliente();
			break;
			
		case R.id.ibCenTipInfClieRelevamiento:
			MostrarPantallaRelevamiento();
			break;
		}
	}
	
	private boolean TieneRolCensista()
	{	
		ArrayList<Rol> listadoRol = null;
		
		try
		{
			listadoRol = new RolBLL().ObtenerRolesPor(loginEmpleado.get_empleadoId());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los rolesEmpleado por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los rolesEmpleado por clienteId: " + localException.getMessage());
			} 
		}
		
		rolCensista = false;
		
		if(listadoRol != null && listadoRol.size()>0)
		{
			for(Rol item : listadoRol)
			{
				if(item.get_rol().equals("Censista"))
				{
					rolCensista = true;
				}
			}
		}
		
		return rolCensista;
	}
	
	private boolean TieneRelevamientosActivos()
	{
		ArrayList<Relevamiento> listadoRelevamiento = null;
		try
		{
			listadoRelevamiento = new RelevamientoBLL().ObtenerRelevamientos();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los relevamientos activos: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los relevamientos activos: " + localException.getMessage());
	    	}
		}
		
		if(listadoRelevamiento == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
		
	private void MostrarPantallaRegistrarCliente()
	  {
	    Intent localIntent = new Intent(this, Censistainfocliente.class);
	    localIntent.putExtra("Latitud", String.valueOf(latitudMapa));
	    localIntent.putExtra("Longitud", String.valueOf(longitudMapa));
	    localIntent.putExtra("Origen", Origen);
	    startActivity(localIntent);
	  }
	  
	private void MostrarPantallaPunteoCliente()
	  {
	    Intent localIntent = new Intent(this, Censistapunteocliente.class);
	    localIntent.putExtra("Latitud", String.valueOf(latitudMapa));
	    localIntent.putExtra("Longitud", String.valueOf(longitudMapa));
	    localIntent.putExtra("Origen", Origen);
	    startActivity(localIntent);
	  }
	
	private void MostrarPantallaRelevamiento()
	  {
	    Intent localIntent = new Intent(this, Censistarelevamiento.class);
	    localIntent.putExtra("Latitud", String.valueOf(latitudMapa));
	    localIntent.putExtra("Longitud", String.valueOf(longitudMapa));
	    localIntent.putExtra("Origen", Origen);
	    startActivity(localIntent);
	  }
	
	@Override
	public void onBackPressed() 
	{
		Intent intent = new Intent(this,Menucensista.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("Origen", Origen);
		startActivity(intent);
	}

}
