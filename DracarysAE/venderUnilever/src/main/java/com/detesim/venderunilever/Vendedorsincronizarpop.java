package com.detesim.venderunilever;

import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import BLL.ClientePreventaBLL;
import BLL.MyLogBLL;
import BLL.PreventaProductoPOPBLL;
import BLL.ProductoPOPCostoBLL;
import BLL.VentaProductoPOPBLL;
import Clases.ClientePreventa;
import Clases.LoginEmpleado;
import Clases.PreventaProductoPOP;
import Clases.ProductoPOPCosto;
import Clases.VentaProductoPOP;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Vendedorsincronizarpop extends Activity implements OnClickListener
{
	LinearLayout llVendedorSincronizarPOP;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
	ArrayList<PreventaProductoPOP> listadoPreventasProductoPOPNoSync;
	ArrayList<VentaProductoPOP> listadoVentasProductoPOPNoSync;
	int preventaProductoPOPIdDispositivo;
	int ventaProductoPOPIdDispositivo;
	
	ListView lvPreventasPOPNoSync;
	ListView lvVentasPOPNoSync;
	Button btnSincronizarPreventaProductoPOP;
	Button btnSincronizarVentaProductoPOP;
	ProgressDialog pdInsertarPrevenProductoPOP;
	ProgressDialog pdInsertarVenProductoPOP;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedorsincronizarpop);
		
		llVendedorSincronizarPOP = (LinearLayout)findViewById(R.id.llSincronizarPOP);
		lvPreventasPOPNoSync = (ListView)findViewById(R.id.lvSincronizarPOPPreventas);
		lvVentasPOPNoSync = (ListView)findViewById(R.id.lvSincronizarPOPVentas);
		btnSincronizarPreventaProductoPOP = (Button)findViewById(R.id.btnSincronizarPOPSincronizar);
		btnSincronizarPreventaProductoPOP.setOnClickListener(this);
		btnSincronizarVentaProductoPOP = (Button)findViewById(R.id.btnSincronizarPOPVentas);
		btnSincronizarVentaProductoPOP.setOnClickListener(this);
		
		llVendedorSincronizarPOP.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
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
    		ObtenerPreventasProductoPOPNoSincronizadosForDisplay();
    		ObtenerVentasProductoPOPNoSincronizadosForDisplay();
    	}
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.btnSincronizarPOPSincronizar:
			if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
			{
				SincronizarPreventasProductoPOP();
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No esta conectado a internet, intentelo mas tarde.", 1);
			}
			break;
		case R.id.btnSincronizarPOPVentas:
			if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
			{
				SincronizarVentasProductoPOP();
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No esta conectado a internet, intentelo mas tarde.", 1);
			}
			break;
		}
	}
	
	private void ObtenerPreventasProductoPOPNoSincronizadosForDisplay()
	{
	    listadoPreventasProductoPOPNoSync = null;
	    try
	    {
	    	listadoPreventasProductoPOPNoSync = new PreventaProductoPOPBLL().ObtenerPreventasPorductoPOPNoSincronizadas();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas POP no sincronizados: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas POP no sincronizados: " + localException.getMessage());
	    	} 
	    }
	      
	    if(listadoPreventasProductoPOPNoSync == null)
	    {
	    	lvPreventasPOPNoSync.setAdapter(null);
	    	btnSincronizarPreventaProductoPOP.setVisibility(View.INVISIBLE);
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No existen preventas POP para sincronizar.", 1);
	        return;
	    }
	    else
	    {
	    	btnSincronizarPreventaProductoPOP.setVisibility(View.VISIBLE);
	    	LlenarListViewPreventasPOPsNoSync();
	    }
	}
	
	private void LlenarListViewPreventasPOPsNoSync()
	{
		MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    ListView localListView = (ListView)findViewById(R.id.lvSincronizarPOPPreventas);
	    ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    localLayoutParams.height = ((int)(30*getApplicationContext().getResources().getDisplayMetrics().density) * listadoPreventasProductoPOPNoSync.size());
	    localListView.setLayoutParams(localLayoutParams);
	    localListView.setAdapter(localMiAdaptadorLista);
	}
	
	class MiAdaptadorLista extends ArrayAdapter<PreventaProductoPOP>
	{
		private Context _context;
		
		public MiAdaptadorLista(Context context)
		{
			super(context,R.layout.disenio_censistasincronizacioncliente,listadoPreventasProductoPOPNoSync);
			this._context = context;
		}
    
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View localView = convertView;
			
			if (convertView == null) 
			{
				LayoutInflater layoutInflater = (LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
				localView = layoutInflater.inflate(R.layout.disenio_censistasincronizacioncliente,parent,false);
			}
			
			PreventaProductoPOP localPreventaPOP = (PreventaProductoPOP)listadoPreventasProductoPOPNoSync.get(position);
			
			TextView textView = (TextView)localView.findViewById(R.id.tvRestauracionPreventaTitulo);
			
			ClientePreventa clientePreventa = null;
			try
			{
				clientePreventa = new ClientePreventaBLL().ObtenerClientePreventaPor(localPreventaPOP.get_clienteId()); 
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
			
			if(clientePreventa==null)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del cliente.", 1);
				return null;
			}
			else
			{
				textView.setText(clientePreventa.get_nombreCompleto());
			}
			
			return localView;
		}
	}
	
	private void SincronizarPreventasProductoPOP()
	{
		if(listadoPreventasProductoPOPNoSync.get(0).get_preventaPOPIdServer() > 0)
		{
			ProductoPOPCosto productoPOPCosto = null;
			try
			{
				productoPOPCosto = new ProductoPOPCostoBLL().ObtenerProductoPOPCostoPorProductoId(listadoPreventasProductoPOPNoSync.get(0).get_productoPOPId());
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
		    	{
		            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el productoCostoPOP: vacio");
		    	}
		    	else
		    	{
		    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el productoCostoPOP: " + localException.getMessage());
		    	}
			}
			
			if(productoPOPCosto == null)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el costo dle productoPOP.", 1);
				return;
			}
			else
			{
				InsertarPreventaProductoPOP(listadoPreventasProductoPOPNoSync.get(0),productoPOPCosto);
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "La preventa debe ser sincronizada antes que el material POP.", 1);
		}
	}
	
	private void InsertarPreventaProductoPOP(PreventaProductoPOP localPreventaProductoPOP, ProductoPOPCosto productoPOPCosto)
	 {
		pdInsertarPrevenProductoPOP = new ProgressDialog(this);
		pdInsertarPrevenProductoPOP.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdInsertarPrevenProductoPOP.setMessage("Insertando productos preventa POP ...");
		pdInsertarPrevenProductoPOP.setCancelable(false);
		pdInsertarPrevenProductoPOP.setCanceledOnTouchOutside(false);
	    
	    WSInsertarPreventaProductoPOP localWSInsertarPreventaProductoPOP = new WSInsertarPreventaProductoPOP(localPreventaProductoPOP,productoPOPCosto);
	    try
	    {
	      localWSInsertarPreventaProductoPOP.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventaProductoPop: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarPreVentaProductoPop: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertarPreventaProductoPop", 1);
	    }
	 }
	 
	public class WSInsertarPreventaProductoPOP extends AsyncTask<Void, Integer, Boolean>
	 {
		 String PREVENTAPRODUCTOPOP_METHOD_NAME = "InsertPreVentaProductoPop";
		 String PREVENTAPRODUCTOPOP_SOAP_ACTION = NAMESPACE + PREVENTAPRODUCTOPOP_METHOD_NAME;
		 
		 private PreventaProductoPOP _preventaProductoPOP;
		 private ProductoPOPCosto _productoPOPCosto;
		 
		 boolean WSInsertarPreVentaProductoPOP;
		 int resultadoInt;
		 String resultadoString;
		 SoapObject localSoapObjects;
	    
	    public WSInsertarPreventaProductoPOP(PreventaProductoPOP paramPreventaProductoPOP,ProductoPOPCosto paramProductoPOPCosto)
	    {
	    	this._preventaProductoPOP = paramPreventaProductoPOP;
	    	this._productoPOPCosto = paramProductoPOPCosto;
	    }
	    
	    protected void onPreExecute()
	    {
	    	pdInsertarPrevenProductoPOP.show();
	    }
	    
	    protected Boolean doInBackground(Void... paramVarArgs)
	    {
	    	WSInsertarPreVentaProductoPOP = false;
	      
	    	float monto = _preventaProductoPOP.get_cantidad() * _productoPOPCosto.get_costo();
	    	
	    	SoapObject localSoapObject = new SoapObject(NAMESPACE, this.PREVENTAPRODUCTOPOP_METHOD_NAME);
	    	localSoapObject.addProperty("preVentaId", _preventaProductoPOP.get_preventaPOPIdServer());
	    	localSoapObject.addProperty("productoId", _preventaProductoPOP.get_productoPOPId());
	    	localSoapObject.addProperty("cantidad", _preventaProductoPOP.get_cantidad());
	    	localSoapObject.addProperty("monto", String.valueOf(monto));
	   		localSoapObject.addProperty("empleadoId", loginEmpleado.get_empleadoId());
	   		localSoapObject.addProperty("costoId",_productoPOPCosto.get_costoId());
	   		localSoapObject.addProperty("almacenId",(loginEmpleado.get_almacenId()));
	   		localSoapObject.addProperty("motivoPopId",_preventaProductoPOP.get_motivoPopId());
	       
	   		SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
	   		localSoapSerializationEnvelope.dotNet = true;
	   		localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
	   		HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
	   		try
		    	{
	   			localHttpTransportSE.call(PREVENTAPRODUCTOPOP_SOAP_ACTION, localSoapSerializationEnvelope);
	   			
	   			localSoapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
	   			if(localSoapObjects!=null)
	   			{
	   				resultadoInt = Integer.parseInt(localSoapObjects.getPropertyAsString("Id"));
	       			resultadoString = localSoapObjects.getPropertyAsString("Resultado");    				
	   			}
	   			
	   			if(resultadoString.equals("OK") && resultadoInt > 0)
	   			{
	   				WSInsertarPreVentaProductoPOP = true;
	   			}
	   			return true;
		    	}
	   		catch (Exception localException)
	   		{
	   			WSInsertarPreVentaProductoPOP = false;
	   			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	   			{
	   		        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventaProductoPOP: vacio");
	   			}
	   			else
	   			{
	   				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventaProductoPOP: " + localException.getMessage());
	   			} 
	   			return true;
	   		}
	   	}
	    
	    protected void onPostExecute(Boolean ejecutado)
	    {
	    	if(pdInsertarPrevenProductoPOP.isShowing())
	    	{
	    		pdInsertarPrevenProductoPOP.dismiss();
	    	}
	    	
	    	if(ejecutado) 
	    	{
	    		if(WSInsertarPreVentaProductoPOP || (resultadoString != null && resultadoString.equals("Preventa Producto Pop Repetido") && resultadoInt <= 0))
	    		{
	    			long l = 0;
	    			try
	    			{
	    				l = new PreventaProductoPOPBLL().ModificarPreventaProductoPOPNoSincronizadaPor(_preventaProductoPOP.get_id(), resultadoInt);
					}
					catch (Exception localException)
	    			{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del producto de la preventa POP: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del producto de la preventa POP: " + localException.getMessage());
						} 
					}
						
	    			if(l==0)
	    			{
	    				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del producto de la preventa POP.", 1);
	    				return;
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "Producto de la preventa POP sincronizado.", 1);
							
						if(ObtenerPreventasProductoPOPNoSincronizadas())
						{
							SincronizarPreventasProductoPOP();
						}
						else
						{
							ObtenerPreventasProductoPOPNoSincronizadosForDisplay();
						}
					}
	    		}
	    		else 
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo ejecutar el webservice WSInsertPreVentaProductoTemp.", 1);
	    			MostrarPantallaMenuVendedor();
	        	}
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "El WebService InsertPreVentaProductoTemp no se ejecuto correctamente. ", 1);
	    		return;
	    	}
	    }
	 }
	
	private boolean ObtenerPreventasProductoPOPNoSincronizadas()
	{
		listadoPreventasProductoPOPNoSync = null;
	    try
	    {
	    	listadoPreventasProductoPOPNoSync = new PreventaProductoPOPBLL().ObtenerPreventasPorductoPOPNoSincronizadas();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la preventaPOP no sincronizadas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la preventaPOP no sincronizadas: " + localException.getMessage());
	    	}
	    }
	    
	    if (listadoPreventasProductoPOPNoSync == null) 
	    {
 	        return false;
	    }  
	    else
	    {
	    	return true;
	    }
	}

	private boolean ObtenerVentasProductoPOPNoSincronizadas()
	{
		listadoVentasProductoPOPNoSync = null;
	    try
	    {
	    	listadoVentasProductoPOPNoSync = new VentaProductoPOPBLL().ObtenerVentasPorductoPOPNoSincronizadas();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la ventaPOP no sincronizadas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la ventaPOP no sincronizadas: " + localException.getMessage());
	    	}
	    }
	    
	    if (listadoVentasProductoPOPNoSync == null) 
	    {
 	        return false;
	    }  
	    else
	    {
	    	return true;
	    }
	}

	private void ObtenerVentasProductoPOPNoSincronizadosForDisplay()
	{
	    listadoVentasProductoPOPNoSync = null;
	    try
	    {
	    	listadoVentasProductoPOPNoSync = new VentaProductoPOPBLL().ObtenerVentasPorductoPOPNoSincronizadas();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas POP no sincronizados: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas POP no sincronizados: " + localException.getMessage());
	    	} 
	    }
	      
	    if(listadoVentasProductoPOPNoSync == null)
	    {
	    	lvVentasPOPNoSync.setAdapter(null);
	    	btnSincronizarVentaProductoPOP.setVisibility(View.INVISIBLE);
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No existen ventas POP para sincronizar.", 1);
	        return;
	    }
	    else
	    {
	    	btnSincronizarVentaProductoPOP.setVisibility(View.VISIBLE);
	    	LlenarListViewVentasPOPsNoSync();
	    }
	}
	
	private void LlenarListViewVentasPOPsNoSync()
	{
		MiAdaptadorListaVenta localMiAdaptadorListaVenta = new MiAdaptadorListaVenta(getApplicationContext());
	    ListView localListView = (ListView)findViewById(R.id.lvSincronizarPOPVentas);
	    ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    localLayoutParams.height = ((int)(30*getApplicationContext().getResources().getDisplayMetrics().density) * listadoVentasProductoPOPNoSync.size());
	    localListView.setLayoutParams(localLayoutParams);
	    localListView.setAdapter(localMiAdaptadorListaVenta);
	}
	
	class MiAdaptadorListaVenta extends ArrayAdapter<VentaProductoPOP>
	{
		private Context _context;
		
		public MiAdaptadorListaVenta(Context context)
		{
			super(context,R.layout.disenio_censistasincronizacioncliente,listadoVentasProductoPOPNoSync);
			this._context = context;
		}
    
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View localView = convertView;
			
			if (convertView == null) 
			{
				LayoutInflater layoutInflater = (LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
				localView = layoutInflater.inflate(R.layout.disenio_censistasincronizacioncliente,parent,false);
			}
			
			VentaProductoPOP localVentaPOP = (VentaProductoPOP)listadoVentasProductoPOPNoSync.get(position);
			
			TextView textView = (TextView)localView.findViewById(R.id.tvRestauracionPreventaTitulo);
			
			ClientePreventa clientePreventa = null;
			try
			{
				clientePreventa = new ClientePreventaBLL().ObtenerClientePreventaPor(localVentaPOP.get_clienteId()); 
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
			
			if(clientePreventa==null)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del cliente.", 1);
				return null;
			}
			else
			{
				textView.setText(clientePreventa.get_nombreCompleto());
			}
			
			return localView;
		}
	}
	
	private void SincronizarVentasProductoPOP()
	{
		if(listadoVentasProductoPOPNoSync.get(0).get_ventaPOPIdServer() > 0)
		{
			ProductoPOPCosto productoPOPCosto = null;
			try
			{
				productoPOPCosto = new ProductoPOPCostoBLL().ObtenerProductoPOPCostoPorProductoId(listadoVentasProductoPOPNoSync.get(0).get_productoPOPId());
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
		    	{
		            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el productoCostoPOP: vacio");
		    	}
		    	else
		    	{
		    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el productoCostoPOP: " + localException.getMessage());
		    	}
			}
			
			if(productoPOPCosto == null)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el costo dle productoPOP.", 1);
				return;
			}
			else
			{
				InsertarVentaProductoPOP(listadoVentasProductoPOPNoSync.get(0),productoPOPCosto);
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "La venta debe ser sincronizada antes que el material POP.", 1);
		}
	}
	
	private void InsertarVentaProductoPOP(VentaProductoPOP localVentaProductoPOP, ProductoPOPCosto productoPOPCosto)
	 {
		pdInsertarVenProductoPOP = new ProgressDialog(this);
		pdInsertarVenProductoPOP.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdInsertarVenProductoPOP.setMessage("Insertando productos preventa POP ...");
		pdInsertarVenProductoPOP.setCancelable(false);
		pdInsertarVenProductoPOP.setCanceledOnTouchOutside(false);
	    
	    WSInsertarVentaProductoPOP localWSInsertarVentaProductoPOP = new WSInsertarVentaProductoPOP(localVentaProductoPOP,productoPOPCosto);
	    try
	    {
	    	localWSInsertarVentaProductoPOP.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertVentaProductoPop: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarVentaProductoPop: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertarVentaProductoPop", 1);
	    }
	 }
	 
	public class WSInsertarVentaProductoPOP extends AsyncTask<Void, Integer, Boolean>
	 {
		 String VENTAPRODUCTOPOP_METHOD_NAME = "InsertVentaProductoPop";
		 String VENTAPRODUCTOPOP_SOAP_ACTION = NAMESPACE + VENTAPRODUCTOPOP_METHOD_NAME;
		 
		 private VentaProductoPOP _ventaProductoPOP;
		 private ProductoPOPCosto _productoPOPCosto;
		 
		 boolean WSInsertarVentaProductoPOP;
		 int resultadoInt;
		 String resultadoString;
		 SoapObject localSoapObjects;
	    
	    public WSInsertarVentaProductoPOP(VentaProductoPOP paramVentaProductoPOP,ProductoPOPCosto paramProductoPOPCosto)
	    {
	    	this._ventaProductoPOP = paramVentaProductoPOP;
	    	this._productoPOPCosto = paramProductoPOPCosto;
	    }
	    
	    protected void onPreExecute()
	    {
	    	pdInsertarVenProductoPOP.show();
	    }
	    
	    protected Boolean doInBackground(Void... paramVarArgs)
	    {
	    	WSInsertarVentaProductoPOP = false;
	      
	    	float monto = _ventaProductoPOP.get_cantidad() * _productoPOPCosto.get_costo();
	    	
	    	SoapObject localSoapObject = new SoapObject(NAMESPACE, this.VENTAPRODUCTOPOP_METHOD_NAME);
	    	localSoapObject.addProperty("ventaId", _ventaProductoPOP.get_ventaPOPIdServer());
	    	localSoapObject.addProperty("productoId", _ventaProductoPOP.get_productoPOPId());
	    	localSoapObject.addProperty("cantidad", _ventaProductoPOP.get_cantidad());
	    	localSoapObject.addProperty("monto", String.valueOf(monto));
	   		localSoapObject.addProperty("empleadoId", loginEmpleado.get_empleadoId());
	   		localSoapObject.addProperty("costoId",_productoPOPCosto.get_costoId());
	   		localSoapObject.addProperty("almacenId",(loginEmpleado.get_almacenId()));
	   		localSoapObject.addProperty("motivoPopId",_ventaProductoPOP.get_motivoPopId());
	       
	   		SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
	   		localSoapSerializationEnvelope.dotNet = true;
	   		localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
	   		HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
	   		try
		    	{
	   			localHttpTransportSE.call(VENTAPRODUCTOPOP_SOAP_ACTION, localSoapSerializationEnvelope);
	   			
	   			localSoapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
	   			if(localSoapObjects!=null)
	   			{
	   				resultadoInt = Integer.parseInt(localSoapObjects.getPropertyAsString("Id"));
	       			resultadoString = localSoapObjects.getPropertyAsString("Resultado");    				
	   			}
	   			
	   			if(resultadoString.equals("OK") && resultadoInt > 0)
	   			{
	   				WSInsertarVentaProductoPOP = true;
	   			}
	   			return true;
		    	}
	   		catch (Exception localException)
	   		{
	   			WSInsertarVentaProductoPOP = false;
	   			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	   			{
	   		        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertVentaProductoPOP: vacio");
	   			}
	   			else
	   			{
	   				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertVentaProductoPOP: " + localException.getMessage());
	   			} 
	   			return true;
	   		}
	   	}
	    
	    protected void onPostExecute(Boolean ejecutado)
	    {
	    	if(pdInsertarVenProductoPOP.isShowing())
	    	{
	    		pdInsertarVenProductoPOP.dismiss();
	    	}
	    	
	    	if(ejecutado) 
	    	{
	    		if(WSInsertarVentaProductoPOP || (resultadoString != null && resultadoString.equals("Venta Producto Pop Repetido") && resultadoInt <= 0))
	    		{
	    			long l = 0;
	    			try
	    			{
	    				l = new VentaProductoPOPBLL().ModificarVentaProductoPOPNoSincronizadaPor(_ventaProductoPOP.get_id(), resultadoInt);
					}
					catch (Exception localException)
	    			{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del producto de la venta POP: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del producto de la venta POP: " + localException.getMessage());
						} 
					}
						
	    			if(l==0)
	    			{
	    				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del producto de la venta POP.", 1);
	    				return;
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "Producto de la venta POP sincronizado.", 1);
							
						if(ObtenerVentasProductoPOPNoSincronizadas())
						{
							SincronizarVentasProductoPOP();
						}
						else
						{
							ObtenerVentasProductoPOPNoSincronizadosForDisplay();
						}
					}
	    		}
	    		else 
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo ejecutar el webservice WSInsertVentaProductoTemp.", 1);
	    			MostrarPantallaMenuVendedor();
	        	}
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "El WebService InsertVentaProductoTemp no se ejecuto correctamente. ", 1);
	    		return;
	    	}
	    }
	 }
	
	public void MostrarPantallaMenuVendedor()
	{
		startActivity(new Intent(this, Menuvendedor.class));
	}
	
	@Override
	public void onBackPressed() 
	{
		Intent intent = new Intent(this,Menuvendedor.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
}
