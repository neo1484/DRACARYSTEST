package com.detesim.venderunilever;

import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import BLL.ClientePreventaBLL;
import BLL.MyLogBLL;
import BLL.PreventaProductoCambioBLL;
import BLL.ProductoCambioBLL;
import Clases.ClientePreventa;
import Clases.LoginEmpleado;
import Clases.PreventaProductoCambio;
import Clases.ProductoCambio;
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

public class Vendedorsincronizacionpreventacambios extends Activity implements OnClickListener 
{
	LinearLayout llVendedorSincronizarCambios;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
	ArrayList<PreventaProductoCambio> listadoPreventasProductoCambioNoSinc;
	int preventaProductoCambioIdDispositivo;
	ProductoCambio productoCambio;
	
	ListView lvPreventasCambioNoSinc;
	Button btnSincronizarPreventaProductoCambio;
	ProgressDialog pdInsertarPrevenProductoCambio;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedorsincronizacionpreventascambio);
		
		llVendedorSincronizarCambios = (LinearLayout)findViewById(R.id.llSincronizarCambios);
		lvPreventasCambioNoSinc = (ListView)findViewById(R.id.lvVendedorSincronizacionCambioCambios);
		btnSincronizarPreventaProductoCambio = (Button)findViewById(R.id.btnVendedorSincronizacionCambioSincronizar);
		btnSincronizarPreventaProductoCambio.setOnClickListener(this);
		
		llVendedorSincronizarCambios.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
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
    		ObtenerPreventasProductoCambioNoSincronizadosForDisplay();
    	}
	}

	@Override
	public void onClick(View v) 
	{	
		switch(v.getId())
		{
		case R.id.btnVendedorSincronizacionCambioSincronizar:
			if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
			{
				SincronizarPreventasProductoCambio();
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No esta conectado a internet, intentelo mas tarde.", 1);
			}
			break;
		}
	}
	
	private void ObtenerPreventasProductoCambioNoSincronizadosForDisplay()
	{
	    listadoPreventasProductoCambioNoSinc = null;
	    try
	    {
	    	listadoPreventasProductoCambioNoSinc = new PreventaProductoCambioBLL().ObtenerPreventasProductoCambioNoSincronizadas();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas cambio no sincronizadas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas cambio no sincronizadas: " + localException.getMessage());
	    	} 
	    }
	      
	    if(listadoPreventasProductoCambioNoSinc == null)
	    {
	    	lvPreventasCambioNoSinc.setAdapter(null);
	    	btnSincronizarPreventaProductoCambio.setVisibility(View.INVISIBLE);
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No existen productos de cambio para sincronizar.", 1);
	        return;
	    }
	    else
	    {
	    	btnSincronizarPreventaProductoCambio.setVisibility(View.VISIBLE);
	    	LlenarListViewPreventasCambioNoSinc();
	    }
	}
	
	private void LlenarListViewPreventasCambioNoSinc()
	{
		MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    ListView localListView = (ListView)findViewById(R.id.lvVendedorSincronizacionCambioCambios);
	    ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    localLayoutParams.height = ((int)(30*getApplicationContext().getResources().getDisplayMetrics().density) * listadoPreventasProductoCambioNoSinc.size());
	    localListView.setLayoutParams(localLayoutParams);
	    localListView.setAdapter(localMiAdaptadorLista);
	}
	
	class MiAdaptadorLista extends ArrayAdapter<PreventaProductoCambio>
	{
		private Context _context;
		
		public MiAdaptadorLista(Context context)
		{
			super(context,R.layout.disenio_censistasincronizacioncliente,listadoPreventasProductoCambioNoSinc);
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
			
			PreventaProductoCambio localPreventaCambio = (PreventaProductoCambio)listadoPreventasProductoCambioNoSinc.get(position);
			
			TextView textView = (TextView)localView.findViewById(R.id.tvRestauracionPreventaTitulo);
			
			ClientePreventa clientePreventa = null;
			try
			{
				clientePreventa = new ClientePreventaBLL().ObtenerClientePreventaPor(localPreventaCambio.get_clienteId()); 
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

	private void SincronizarPreventasProductoCambio()
	{
		if(listadoPreventasProductoCambioNoSinc.get(0).get_preventaIdServer() > 0)
		{
			productoCambio = null;
			
			try
			{
				productoCambio = new ProductoCambioBLL().ObtenerProductoCambioPorProductoId(listadoPreventasProductoCambioNoSinc.get(0).get_productoId());
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
		    	{
		            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el producto cambio por productoId: vacio");
		    	}
		    	else
		    	{
		    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el producto cambio por productoId: " + localException.getMessage());
		    	}
			}
			
			if(productoCambio == null)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el producto cambio por productoId.", 1);
			}
			else
			{
				InsertarPreventaProductoCambio(listadoPreventasProductoCambioNoSinc.get(0));
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "La preventa debe ser sincronizada antes que el cambio.", 1);
		}
	}
	
	private void InsertarPreventaProductoCambio(PreventaProductoCambio localPreventaProductoCambio)
	 {
		pdInsertarPrevenProductoCambio = new ProgressDialog(this);
		pdInsertarPrevenProductoCambio.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdInsertarPrevenProductoCambio.setMessage("Insertando productos preventa cambio ...");
		pdInsertarPrevenProductoCambio.setCancelable(false);
		pdInsertarPrevenProductoCambio.setCanceledOnTouchOutside(false);
	    
	    WSInsertarPreventaProductoCambio localWSInsertarPreventaProductoCambio = new WSInsertarPreventaProductoCambio(localPreventaProductoCambio);
	    try
	    {
	    	localWSInsertarPreventaProductoCambio.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventaProductoCambio: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarPreVentaProductoCambio: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertarPreventaProductoCambio", 1);
	    }
	 }
	 
	public class WSInsertarPreventaProductoCambio extends AsyncTask<Void, Integer, Boolean>
	 {
		 String PREVENTAPRODUCTOCAMBIO_METHOD_NAME = "InsertPreVentaProductoCambio";
		 String PREVENTAPRODUCTOCAMBIO_SOAP_ACTION = NAMESPACE + PREVENTAPRODUCTOCAMBIO_METHOD_NAME;
		 
		 private PreventaProductoCambio _preventaProductoCambio;
		 
		 boolean WSInsertarPreVentaProductoCambio;
		 int resultadoInt;
		 String resultadoString;
		 SoapObject localSoapObjects;
	    
	    public WSInsertarPreventaProductoCambio(PreventaProductoCambio paramPreventaProductoCambio)
	    {
	    	this._preventaProductoCambio = paramPreventaProductoCambio;
	    }
	    
	    protected void onPreExecute()
	    {
	    	pdInsertarPrevenProductoCambio.show();
	    }
	    
	    protected Boolean doInBackground(Void... paramVarArgs)
	    {
	    	WSInsertarPreVentaProductoCambio = false;
	    	
	    	SoapObject localSoapObject = new SoapObject(NAMESPACE, this.PREVENTAPRODUCTOCAMBIO_METHOD_NAME);
	    	localSoapObject.addProperty("preVentaId", _preventaProductoCambio.get_preventaIdServer());
	    	localSoapObject.addProperty("productoId", _preventaProductoCambio.get_productoId());
	    	localSoapObject.addProperty("cantidad", _preventaProductoCambio.get_cantidad());
	    	localSoapObject.addProperty("precioId", productoCambio.get_precioId());
	    	localSoapObject.addProperty("costoId", productoCambio.get_costoId());
	    	localSoapObject.addProperty("motivoCambioId", _preventaProductoCambio.get_motivoCambioId());
	       
	   		SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
	   		localSoapSerializationEnvelope.dotNet = true;
	   		localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
	   		HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
	   		try
	    	{
	   			localHttpTransportSE.call(PREVENTAPRODUCTOCAMBIO_SOAP_ACTION, localSoapSerializationEnvelope);
	   			
	   			localSoapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
	   			if(localSoapObjects!=null)
	   			{
	   				resultadoInt = Integer.parseInt(localSoapObjects.getPropertyAsString("Id"));
	       			resultadoString = localSoapObjects.getPropertyAsString("Resultado");    				
	   			}
	   			
	   			if(resultadoString.equals("OK") && resultadoInt > 0)
	   			{
	   				WSInsertarPreVentaProductoCambio = true;
	   			}
	   			return true;
	    	}
	   		catch (Exception localException)
	   		{
	   			WSInsertarPreVentaProductoCambio = false;
	   			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	   			{
	   		        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventaProductoCambio: vacio");
	   			}
	   			else
	   			{
	   				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventaProductoCambio: " + localException.getMessage());
	   			} 
	   			return true;
	   		}
	   	}
	    
	    protected void onPostExecute(Boolean ejecutado)
	    {
	    	if(pdInsertarPrevenProductoCambio.isShowing())
	    	{
	    		pdInsertarPrevenProductoCambio.dismiss();
	    	}
	    	
	    	if(ejecutado) 
	    	{
	    		if(WSInsertarPreVentaProductoCambio || (resultadoString != null && resultadoString.equals("Preventa Producto Cambio Repetido") && resultadoInt <= 0))
	    		{
	    			long l = 0;
	    			try
	    			{
	    				l = new PreventaProductoCambioBLL().ModificarPreventaProductoCambioNoSincronizadaPor(_preventaProductoCambio.get_id(), resultadoInt);
					}
					catch (Exception localException)
	    			{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del producto de la preventa cambio: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del producto de la preventa cambio: " + localException.getMessage());
						} 
					}
						
	    			if(l==0)
	    			{
	    				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del producto de la preventa cambio.", 1);
	    				return;
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "Producto de la preventa cambio sincronizado.", 1);
							
						if(ObtenerPreventasProductoCambioNoSincronizadas())
						{
							SincronizarPreventasProductoCambio();
						}
						else
						{
							MostrarPantallaMenuVendedor();
						}
					}
	    		}
	    		else 
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo ejecutar el WSInsertPreVentaProductoCambio: " + resultadoString, 1);
	    			MostrarPantallaMenuVendedor();
	        	}
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "El WebService InsertPreVentaProductoCambio no se ejecuto correctamente. ", 1);
	    		return;
	    	}
	    }
	 }
	
	private boolean ObtenerPreventasProductoCambioNoSincronizadas()
	{
		listadoPreventasProductoCambioNoSinc = null;
	    try
	    {
	    	listadoPreventasProductoCambioNoSinc = new PreventaProductoCambioBLL().ObtenerPreventasProductoCambioNoSincronizadas();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la preventaCambio no sincronizadas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la preventaCambio no sincronizadas: " + localException.getMessage());
	    	}
	    }
	    
	    if (listadoPreventasProductoCambioNoSinc == null) 
	    {
 	        return false;
	    }  
	    else
	    {
	    	return true;
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
