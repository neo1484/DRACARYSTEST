package com.detesim.venderunilever;

import BLL.MyLogBLL;
import BLL.SincronizacionDatosBLL;
import Clases.LoginEmpleado;
import Clases.SincronizacionDatos;
import Utilidades.Utilidades;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Menucensista extends Activity implements OnClickListener
{
	LinearLayout llMenuCensista;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	String Origen;
	
	TextView tvUsuario;
	ImageView ivSupervisorSincronizarDatos;
	ImageView ivSupervisorAltaCliente;
	ImageView ivSupervisorVerCliente;
	ImageView ivSupervisorVerFotos;
	ImageView ivMapaEdicionCliente;
	ImageView ivSincroEdicionCliente;
	ImageView ivMenuVendedor;
	ImageView ivMenuDistribuidor;
	ImageView ivSupervisorVerLogs;
	ImageView ivUnificacionClientes;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menucensista);
		
		llMenuCensista = (LinearLayout)findViewById(R.id.MenuCensistaLinearLayout);
		tvUsuario = (TextView)findViewById(R.id.tvMenuCensistaUsuario);
	    ivSupervisorSincronizarDatos = ((ImageView)findViewById(R.id.imgbtnMenuSupervisorSincronizarDatos));
	    ivSupervisorSincronizarDatos.setOnClickListener(this);
	    ivSupervisorAltaCliente = ((ImageView)findViewById(R.id.imgbtnMenuSupervisorAltaCliente));
	    ivSupervisorAltaCliente.setOnClickListener(this);
	    ivSupervisorVerCliente = ((ImageView)findViewById(R.id.imgbtnMenuSupervisorVerClientes));
	    ivSupervisorVerCliente.setOnClickListener(this);
	    ivSupervisorVerFotos = ((ImageView)findViewById(R.id.imgbtnMenuSupervisorVerFotos));
	    ivSupervisorVerFotos.setOnClickListener(this);
	    ivMenuVendedor = ((ImageView)findViewById(R.id.imgbtnMenuCensistaMenuVendedor));
	    ivMenuVendedor.setOnClickListener(this);
	    ivMapaEdicionCliente = ((ImageView)findViewById(R.id.imgbtnMenuCensistaMapaEdicionCliente));
	    ivMapaEdicionCliente.setOnClickListener(this);
	    ivSincroEdicionCliente = ((ImageView)findViewById(R.id.imgbtnMenuCensistaSincroEdicionClientes));
	    ivSincroEdicionCliente.setOnClickListener(this);
	    ivUnificacionClientes = ((ImageView)findViewById(R.id.imgbtnMenuCensistaUnificacionClientes));
	    ivUnificacionClientes.setOnClickListener(this);
	    ivMenuDistribuidor = ((ImageView)findViewById(R.id.imgbtnMenuCensistaMenuDistribuidor));
	    ivMenuDistribuidor.setOnClickListener(this);
	    ivSupervisorVerLogs = ((ImageView)findViewById(R.id.imgbtnMenuSupervisorVerLogs));
	    ivSupervisorVerLogs.setOnClickListener(this);
	    
	    llMenuCensista.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
	    
	    Origen = "";
	    Origen = getIntent().getExtras().getString("Origen");
	    
	    if(Origen == "")
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el origen de llamada del menu censista.", 1);
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
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener loginEmpleado censista: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener loginEmpleado censista: " + localException.getMessage());
	    	} 
	    }
	    
	    if (loginEmpleado == null) 
	    {
	    	theUtilidades.MostrarMensaje(this, "El censista no se logeo correctamente.", 2);
	    	MostrarControles(false);
	    	return;
	    }  
	    else
	    {
	    	MostrarControles(true);
	    	tvUsuario.setText(loginEmpleado.get_empleadoNombre());
	    	
	    	if(Origen.equals("Menuprincipal"))
	    	{
	    		ivMenuVendedor.setVisibility(View.INVISIBLE);
	    		ivMenuDistribuidor.setVisibility(View.INVISIBLE);
	    	}
	    	
	    	if(Origen.equals("Menuvendedor"))
	    	{
	    		ivMenuDistribuidor.setVisibility(View.INVISIBLE);
	    	}
	    	
	    	if(Origen.equals("Menudistribuidor"))
	    	{
	    		ivMenuVendedor.setVisibility(View.INVISIBLE);
	    	}
	    }
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId())
	    {
	    case R.id.imgbtnMenuSupervisorSincronizarDatos:
    		if(theUtilidades.VerificarConexionInternet(this))
    		{
    			MostrarPantallaCensistaMenuRutaDia();
    		}
    		else
    		{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "No esta conectado a internet, intentelo mas tarde.",1);
	    		return;
    		}
	    	break;
	    case R.id.imgbtnMenuSupervisorAltaCliente: 
	    	MostrarPantallaAltaClienteMapa();
	    	break;
	    case R.id.imgbtnMenuSupervisorVerClientes: 
	    	MostrarPantallaSincronizacionCliente();
	    	break;
	    case R.id.imgbtnMenuSupervisorVerFotos: 
	    	MostrarPantallaSincronizacionClienteFoto();
	    	break;
	    case R.id.imgbtnMenuCensistaMapaEdicionCliente:
	    	MostrarPantallaMapaEdicionCliente();
	    	break;
	    case R.id.imgbtnMenuCensistaSincroEdicionClientes:
	    	MostrarPantallaSincroEdicionCliente();
	    	break;
	    case R.id.imgbtnMenuCensistaUnificacionClientes:
	    	MostrarPantallaUnificacionClientesSeleccionDia();
	    	break;
	    case R.id.imgbtnMenuCensistaMenuVendedor:
	    	MostrarPantallaMenuVendedor();
	    	break;
	    case R.id.imgbtnMenuCensistaMenuDistribuidor:
	    	MostrarPantallaMenuDistribuidor();
	    	break;
	    case R.id.imgbtnMenuSupervisorVerLogs:
	    	MostrarPantallaVerLogs();
	    	break;
	    }
	}
	
	private void MostrarControles(boolean estado)
	{
		int visibility = 0;
		if(estado)
		{
			visibility = View.VISIBLE;
		}
		else
		{
			visibility = View.INVISIBLE;
		}
		
		ivSupervisorSincronizarDatos.setVisibility(visibility);
		ivSupervisorAltaCliente.setVisibility(visibility);
		ivSupervisorVerCliente.setVisibility(visibility);
		ivSupervisorVerFotos.setVisibility(visibility);
		ivSupervisorVerLogs.setVisibility(visibility);
		ivMenuVendedor.setVisibility(visibility);
		ivMenuDistribuidor.setVisibility(visibility);
	}
	
	private boolean VerificarSincronizacionDatosSupervisor()
	{
		SincronizacionDatos localSincronizacionDatos =null;
	    
		try
		{
			localSincronizacionDatos = new SincronizacionDatosBLL().ObtenerDetalleSincronizacionDatosPor(
			loginEmpleado.get_empleadoId(),loginEmpleado.get_dia(),loginEmpleado.get_mes(),loginEmpleado.get_anio(),2);//Rol 2 = Censista
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al verificar la sincronizacion de datos del supervisor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al verificar la sincronizacion de datos del supervisor: " + localException.getMessage());
		  	} 
		}
	    
		if (localSincronizacionDatos == null)
		{
			  return false;
		}
	    
		return true;
	}
			
	private void MostrarPantallaCensistaMenuRutaDia()
	{
		if(VerificarSincronizacionDatosCensista())
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "Los datos del censista ya fueron sincronizados.", 1);
		}
		else
		{
			Intent intent = new Intent(this, Censistamenurutadia.class);
			intent.putExtra("Origen", Origen);
			startActivity(intent);
		}
	}
	
	private boolean VerificarSincronizacionDatosCensista()
	{
		SincronizacionDatos localSincronizacionDatos =null;
	    
		try
		{
			localSincronizacionDatos = new SincronizacionDatosBLL().ObtenerDetalleSincronizacionDatosPor(
			loginEmpleado.get_empleadoId(),loginEmpleado.get_dia(),loginEmpleado.get_mes(),loginEmpleado.get_anio(),2);//Rol 2 = Censista
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al verificar la sincronizacion de datos del censista: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al verificar la sincronizacion de datos del censista: " + localException.getMessage());
		  	} 
		}
	    
		if (localSincronizacionDatos == null)
		{
			  return false;
		}
	    
		return true;
	}
		
	private void MostrarPantallaAltaClienteMapa()
	{
	    if (VerificarSincronizacionDatosSupervisor()) 
	    {
	    	Intent intent = new Intent(this, Censistaaltaclientemapa.class);
	    	intent.putExtra("Origen", Origen);
	    	startActivity(intent);
	    }
	    else
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "Debe sincronizar los datos, para dar de alta los clientes.", 1);
	    }
	}
		  
	private void MostrarPantallaSincronizacionCliente()
	{
		Intent intent = new Intent(this, Censistasincronizacioncliente.class);
    	intent.putExtra("Origen", Origen);
    	startActivity(intent);
	}
	  
	private void MostrarPantallaSincronizacionClienteFoto()
	{
		Intent intent = new Intent(this, Censistasincronizacionclientefoto.class);
    	intent.putExtra("Origen", "Menucensista");
    	startActivity(intent);
	}
	
	private void MostrarPantallaMapaEdicionCliente()
	{
		Intent intent = new Intent(this,Censistamapaeditarcliente.class);
		intent.putExtra("Origen", Origen);
	    startActivity(intent);
	}
	
	private void MostrarPantallaSincroEdicionCliente()
	{
		Intent intent = new Intent(this,Censistasincronizacionedicionclientes.class);
		intent.putExtra("Origen", "Menucensista");
	    startActivity(intent);
	}
	
	private void MostrarPantallaUnificacionClientesSeleccionDia()
	{
		Intent intent = new Intent(this,Censistaunificacionclienteselecciondia.class);
	    startActivity(intent);
	}
	
	private void MostrarPantallaMenuVendedor()
	{
	    startActivity(new Intent(this, Menuvendedor.class));
	}
	
	private void MostrarPantallaMenuDistribuidor()
	{
	    startActivity(new Intent(this, Menudistribuidor.class));
	}
	
	private void MostrarPantallaVerLogs()
	{
	    startActivity(new Intent(this, Log.class));
	}
	
	@Override
	public void onBackPressed() 
	{
		Intent intent = null;
		if(Origen.equals("Menuprincipal"))
		{
			intent = new Intent(this,Menuprincipal.class);
		}
		else if(Origen.equals("Menudistribuidor"))
		{
			intent = new Intent(this,Menudistribuidor.class);
		}
		else
		{
			intent = new Intent(this,Menuvendedor.class);
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
}