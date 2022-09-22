package com.detesim.venderunilever;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import BLL.ClientePreventaBLL;
import BLL.MyLogBLL;
import BLL.PreventaBLL;
import BLL.PreventaProductoBLL;
import Clases.ClientePreventa;
import Clases.LoginEmpleado;
import Clases.Preventa;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Vendedorrestauracionpreventa extends Activity
{
	LinearLayout llVendedorRestauracionPreventa;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	MyLogBLL myLog = new MyLogBLL();
	
	Preventa preventaActual;
	LoginEmpleado loginEmpleado;
	
	ArrayList<Preventa> listadoPreventasNoSync;	
	ListView lvPreventasNoSync;
	ProgressDialog pdRestaurarPreventa;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedorrestauracionpreventa);
		
		llVendedorRestauracionPreventa = (LinearLayout)findViewById(R.id.llVendedorRestauracionPreventa);
		lvPreventasNoSync = (ListView)findViewById(R.id.lvRestauracionPreventaPreventas);
		
		llVendedorRestauracionPreventa.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
		loginEmpleado = null;
	    try
	    {
	    	loginEmpleado = theUtilidades.ObtenerLoginEmpleado();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el loginEmpleado: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el loginEmpleado: " + localException.getMessage());
	    	} 
	      
	    }
    	if(loginEmpleado == null)
    	{
    		theUtilidades.MostrarMensaje(getApplicationContext(), "El usuario no se logeo correctamente.", 1);
    		return;
    	}
    	else
    	{
    		ObtenerPreventasNoSincronizadas();
    	}
	}
	
	private void ObtenerPreventasNoSincronizadas()
	{
		listadoPreventasNoSync = null;
	    try
	    {
	    	listadoPreventasNoSync = new PreventaBLL().ObtenerPreventasNoSincronizadas();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas no sincronizadas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas no sincronizadas: " + localException.getMessage());
	    	} 
	    }
	      
	    if(listadoPreventasNoSync == null)
	    {
	    	lvPreventasNoSync.setAdapter(null);
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No existen preventas para restaurar.", 1);
	        return;
	    }
	    else
	    {
	    	LlenarListViewPreventasNoSync();
	    	PreventaItemListView();
	    }
	}
	
	private void LlenarListViewPreventasNoSync()
	{
		MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    ListView localListView = (ListView)findViewById(R.id.lvRestauracionPreventaPreventas);
	    ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    localLayoutParams.height = ((int)(30*getApplicationContext().getResources().getDisplayMetrics().density) * listadoPreventasNoSync.size());
	    localListView.setLayoutParams(localLayoutParams);
	    localListView.setAdapter(localMiAdaptadorLista);
	}
	
	class MiAdaptadorLista extends ArrayAdapter<Preventa>
	{
		private Context _context;
		
		public MiAdaptadorLista(Context context)
		{
			super(context,R.layout.disenio_vendedorsincronizacionpreventa,listadoPreventasNoSync);
			this._context = context;
		}
    
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View localView = convertView;
			
			if (convertView == null) 
			{
				LayoutInflater layoutInflater = (LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
				localView = layoutInflater.inflate(R.layout.disenio_vendedorsincronizacionpreventa,parent,false);
			}
			
			Preventa localPreventa = (Preventa)listadoPreventasNoSync.get(position);
			
			TextView textView = (TextView)localView.findViewById(R.id.tvVendedorSincronizacionPreventaNombre);
			
			ClientePreventa theClientePreventa =null;
			try
			{
				theClientePreventa = new ClientePreventaBLL().ObtenerClientePreventaPor(localPreventa.get_clienteId());
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
		    	{
		            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el clientePreventa por clienteId: vacio");
		    	}
		    	else
		    	{
		    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el clientePreventa por clienteId: " + localException.getMessage());
		    	}
			}
			
			if(theClientePreventa == null)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el  cliente.", 1);
				return null;
			}
						
			textView.setText("Cliente: " + theClientePreventa.get_nombreCompleto() + " - Monto: " + localPreventa.get_montoFinal());
			
			return localView;
		}
	}

	private void PreventaItemListView()
	{
	    ((ListView)findViewById(R.id.lvRestauracionPreventaPreventas)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	    	{
	    		preventaActual = listadoPreventasNoSync.get(position);
	    		if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
	    		{
	    			RestaurarPreventa(preventaActual.get_empleadoId(),preventaActual.get_clienteId(),preventaActual.get_noPreventa());
	    		}
	    		else
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(), "Para restaurar la preventa debe tener conexion a internet.", 1);
	    			return;
	    		}
	      }
	    });
	}
	
	private void RestaurarPreventa(int empleadoId,int clienteId,int noAutoVenta)
	{
		pdRestaurarPreventa = new ProgressDialog(this);
		pdRestaurarPreventa.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdRestaurarPreventa.setMessage("Restaurando preventa ...");
		 	 
		 WSRestaurarPreventa localWSRestaurarPreventa = new WSRestaurarPreventa(empleadoId,clienteId,noAutoVenta);
		 try
		 {
			 localWSRestaurarPreventa.execute();
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSRestaurarPreventa: vacio");
			 }
	 		 else
		 	 {
	 			 myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSRestaurarAutoventa: " + localException.getMessage());
		 	 }
			 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSRestaurarPreventa.", 1);
		     return;
		 }
	 }
	 
	private class WSRestaurarPreventa extends AsyncTask<Void, Integer, Boolean>
	{
		String RESTAURARPREVENTA_METHOD_NAME = "RestaurarPreVenta";
		String RESTAURARPREVENTA_SOAP_ACTION = NAMESPACE + RESTAURARPREVENTA_METHOD_NAME;
		
		private int _empleadoId;
		private int _clienteId;
		private int _noAutoPreVenta;
		
		public WSRestaurarPreventa(int empleadoId,int clienteId,int noAutoVenta)
		{
			this._empleadoId = empleadoId;
			this._clienteId = clienteId;
			this._noAutoPreVenta = noAutoVenta;
		}
		 
		boolean WSRestaurarPreventa = false;
		int intResultado;
		SoapObject soapResultado;
		String stringResultado;
		 
		protected void onPreExecute()
		{
			pdRestaurarPreventa.show();
		}
	    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			 WSRestaurarPreventa = false;
			 
			 SoapObject localSoapObject = new SoapObject(NAMESPACE,RESTAURARPREVENTA_METHOD_NAME);
			 localSoapObject.addProperty("empleadoId", Integer.valueOf(_empleadoId));
			 localSoapObject.addProperty("clienteId", Integer.valueOf(_clienteId));
			 localSoapObject.addProperty("nroPreVenta", Integer.valueOf(_noAutoPreVenta));
			 
			 SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			 localSoapSerializationEnvelope.dotNet = true;
			 localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			 HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			 try
			 {
				 localHttpTransportSE.call(RESTAURARPREVENTA_SOAP_ACTION, localSoapSerializationEnvelope);
				
				 soapResultado = ((SoapObject)((SoapObject)localSoapSerializationEnvelope.bodyIn).getProperty("RestaurarPreVentaResult"));
				 if(soapResultado!=null)
				 {
					 intResultado = Integer.parseInt(soapResultado.getPropertyAsString("Id"));
					 stringResultado = soapResultado.getPropertyAsString("Resultado");
				 }
				
				 if(stringResultado.equals("OK") && intResultado > 0)
				 {
					 WSRestaurarPreventa = true;
				 }
				 return true;
			 }
			 catch (Exception localException)
			 {
				 WSRestaurarPreventa = false;
				 if(localException.getMessage() == null || localException.getMessage().isEmpty())
				 {
					 myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSRestaurarPreventa: vacio");
				 }
				 else
				 {
					 myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSRestaurarPreventa: " + localException.getMessage());
				 }
				 return true;
			 }
	 	}
		
		protected void onPostExecute(Boolean ejecutado)
		{			 
			if(ejecutado)
			{
				if(WSRestaurarPreventa) 
				{
					long l = 0;
					try
					{
						l = new PreventaBLL().ModificarPreventaNoSincronizadaPor(preventaActual.get_Id(),false);
					}
					catch (Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la preventa: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la preventa: " + localException.getMessage());
						} 
					}
							
					if(l==0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion de la venta.", 1);
						return;
					}
					else
					{
						l = 0;
						try
						{
							l = new PreventaProductoBLL().ModificarPreventaProductoNoSincronizadaPor(preventaActual.get_Id(), false);
						}
						catch (Exception localException)
						{
							if(localException.getMessage() == null || localException.getMessage().isEmpty())
							{
								myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de las preventasProducto por preventaId: vacio");
							}
							else
							{
								myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de las preventasProducto por preventaId: " + localException.getMessage());
							} 
						}
								
						if(l==0)
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion de los productos de la preventa.", 1);
							return;
						}
					}
					
					if(pdRestaurarPreventa.isShowing())
					{
						pdRestaurarPreventa.dismiss();
					}
					
					theUtilidades.MostrarMensaje(getApplicationContext(), "Preventa restaurada.", 1);
					MostrarPantallaMenuVendedor();
					
				 }
				 else
				 {
					 if(pdRestaurarPreventa.isShowing())
					 {
						 pdRestaurarPreventa.dismiss();
					 }
					 
					 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSRestaurarAutoVenta.", 1);
				 }
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSRestaurarAutoventa.", 1);
				return;
			}
		}
	}
	
	public void MostrarPantallaMenuVendedor()
	{
	    startActivity(new Intent(this, Menuvendedor.class));
	}
}
