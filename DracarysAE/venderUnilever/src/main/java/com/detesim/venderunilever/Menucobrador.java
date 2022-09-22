package com.detesim.venderunilever;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import BLL.ClienteCobranzaBLL;
import BLL.MyLogBLL;
import Clases.ClienteCobranza;
import Clases.LoginEmpleado;
import Clases.SincronizacionDatos;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Menucobrador extends Activity implements OnClickListener
{
	MyLogBLL myLog = new MyLogBLL();
	Utilidades theUtilidades = new Utilidades();
	
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = this.theUtilidades.get_URL();
	
	LoginEmpleado loginEmpleado;
	SincronizacionDatos sincronizacionDatos;
	LinearLayout llMenuCobrador;
	ArrayList<ClienteCobranza> listadoCobranzas;
	
	TextView tvUsuario;
	ImageView ivSincronizarDatos;
	ImageView ivVerCobranzas;
	ImageView ivCerrarCobranzas;

	ProgressDialog pdEsperaObtenerDatos;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menucobrador);
		
		llMenuCobrador = (LinearLayout)findViewById(R.id.llMenuCobrador);
		tvUsuario = (TextView)findViewById(R.id.tvMenuCobradorUsuario);
		ivSincronizarDatos = (ImageView)findViewById(R.id.ibtMenCobSincronizar);
		ivSincronizarDatos.setOnClickListener(this);
		ivVerCobranzas = (ImageView)findViewById(R.id.ibtMenCobVerCobranzas);
		ivVerCobranzas.setOnClickListener(this);
		ivCerrarCobranzas = (ImageView)findViewById(R.id.ibtMenCobCerrarCobranzas);
		ivCerrarCobranzas.setOnClickListener(this);
	    
	    llMenuCobrador.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
	    
	    loginEmpleado = null;
	    try
	    {
	    	loginEmpleado = theUtilidades.ObtenerLoginEmpleado();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener loginEmpleado cobrador: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener loginEmpleado cobrador: " + localException.getMessage());
	    	}	
	    }
	    
	    if(loginEmpleado == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El cobrador no se logeo correctamente.", 1);
	    }
	    else
	    {
	    	tvUsuario.setText(loginEmpleado.get_empleadoNombre());
	    }
	}
	
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.ibtMenCobSincronizar:
			if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
			{
				ObtenerClientesByCobrador();
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se encuentra conectado a internet, intentelo mas tarde.",1);
			}			
			break;
		case R.id.ibtMenCobVerCobranzas:
			if(ValidarIngreso())
			{
				MostrarPantallaMapaCobranzas();
			} else{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontraron clientes a cobrar.",1);
			}
			break;
		case R.id.ibtMenCobCerrarCobranzas:
			MostrarPantallaCierreCobranzas();
			break;
		}
	}
	
	private void ObtenerClientesByCobrador()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo clientes ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);
	    
	    WSObtenerClientesByCobrador localWSObtenerClientesByCobrador = new WSObtenerClientesByCobrador();
	    
	    try
	    {
	    	localWSObtenerClientesByCobrador.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerClientesByCobrador: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerClientesByCobrador: " + localException.getMessage());
	    	} 	
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSObtenerClientesByCobrador.", 1);
	    }    
	}
	
	private class WSObtenerClientesByCobrador extends AsyncTask<Void, Integer, Boolean>
	{
		String COBRADOR_METHOD_NAME = "GetClientesByCobrador";
		String COBRADOR_SOAP_ACTION = NAMESPACE + COBRADOR_METHOD_NAME;
		boolean WSObtenerClientes;
		SoapObject soapClientes;
		
		protected void onPreExecute()
	    {
			pdEsperaObtenerDatos.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerClientes = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,COBRADOR_METHOD_NAME);
			localSoapObject.addProperty("cobradorId", loginEmpleado.get_empleadoId());
			localSoapObject.addProperty("dia", loginEmpleado.get_dia());
			localSoapObject.addProperty("mes", loginEmpleado.get_mes());
			localSoapObject.addProperty("anio", loginEmpleado.get_anio());
						
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			
			try
			{
				localHttpTransportSE.call(COBRADOR_SOAP_ACTION, localSoapSerializationEnvelope);
				
				soapClientes = ((SoapObject)((SoapObject)localSoapSerializationEnvelope.bodyIn).getProperty("GetClientesByCobradorResult"));
				if(soapClientes != null && soapClientes.getPropertyCount() > 0)
				{
					WSObtenerClientes = true;
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerClientes = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error en el webservice ObtenerClientesByCobrador: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice ObtenerClientesByCobrador: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}
			
			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El WebService GetClientesCobranzaByCobrador no se ejecuto correctamente. ", 1);
				return;
			}

			if(!WSObtenerClientes)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron clientes de cobranza que descargar.", 1);
				return;
			}

			if(!BorrarClientesCobranza())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo borrar los clientes de cobranza.", 1);
				return;
			}

			long l = 0;
			try
			{
				l = new ClienteCobranzaBLL().InsertarClienteCobranza( soapClientes );
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar los clientes de cobranza: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar los clientes de cobranza: " + localException.getMessage());
				}
			}

			if(l <= 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el cliente cobranza.", 1);
			}

			theUtilidades.MostrarMensaje(getApplicationContext(), "Clientes cobranza descargados", 1);
		}
	}
    
	private boolean BorrarClientesCobranza()
	{	        
		try
		{
			return new ClienteCobranzaBLL().BorrarClientesCobranza();
        }
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes cobranza: vacio");
        	}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes cobranza: " + localException.getMessage());
        	}
			return false;
        }
	}

	private boolean ValidarIngreso()
	{
		listadoCobranzas = null;
		try
		{
			listadoCobranzas = new ClienteCobranzaBLL().ObtenerClientesCobranza();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes cobranza: vacio");
        	}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes cobranza: " + localException.getMessage());
        	}
		}

		return listadoCobranzas != null;
	}
	
	private void MostrarPantallaMapaCobranzas()
	{
		startActivity(new Intent(this,Cobradormapaclientes.class));
	}
	
	private void MostrarPantallaCierreCobranzas()
	{
		
	}
}
