package com.detesim.venderunilever;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import BLL.ClientePreventaBLL;
import BLL.MyLogBLL;
import Clases.ClientePreventa;
import Clases.LoginEmpleado;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class Vendedorsincronizacionnombrecliente extends Activity implements OnClickListener
{
	LinearLayout llVendedorSincronizacionombreCliente;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
	ArrayList<ClientePreventa> listadoClientePreventa;
	
	ListView lvClientes;
	Button btnSincronizarNombres;
	ProgressDialog pdEsperaSincronizacionNombreCliente;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedorsincronizacionnombrecliente);
		
		llVendedorSincronizacionombreCliente = (LinearLayout)findViewById(R.id.VendedorSincronizacionombreClienteLinearLayout);
		lvClientes = (ListView)findViewById(R.id.lvSincronizacionNombreClienteNombres);
		btnSincronizarNombres = (Button)findViewById(R.id.btnSincronizacionNombreClienteSincronizar);
		btnSincronizarNombres.setOnClickListener(this);
		
		llVendedorSincronizacionombreCliente.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));

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
    		ObtenerClientesNoSincronizadosForDisplay();
    	}
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.btnSincronizacionNombreClienteSincronizar:
			SincronizarNombreClientes();
			break;
		}
	}
	
	private void ObtenerClientesNoSincronizadosForDisplay()
	{
		listadoClientePreventa = null;
	    try
	    {
	    	listadoClientePreventa = new ClientePreventaBLL().ObtenerClientesPreventaNoSincronizados();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no sincronizados: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no sincronizados: " + localException.getMessage());
	    	} 
	    }
	      
	    if(listadoClientePreventa == null)
	    {
	    	lvClientes.setAdapter(null);
	    	btnSincronizarNombres.setVisibility(View.INVISIBLE);
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No existen nombres de clientes por sincronizar.", 1);
	    	return;
	    }
	    else
	    {
	    	btnSincronizarNombres.setVisibility(View.VISIBLE);
	    	LlenarListViewClientesNoSync();
	    } 
	}
	
	private void LlenarListViewClientesNoSync()
	{
		MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    ListView localListView = (ListView)findViewById(R.id.lvSincronizacionNombreClienteNombres);
	    ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    localLayoutParams.height = ((int)(30*getApplicationContext().getResources().getDisplayMetrics().density) * listadoClientePreventa.size());
	    localListView.setLayoutParams(localLayoutParams);
	    localListView.setAdapter(localMiAdaptadorLista);
	}
	
	class MiAdaptadorLista extends ArrayAdapter<ClientePreventa>
	{
		private Context _context;
		
		public MiAdaptadorLista(Context context)
		{
			super(context,R.layout.disenio_censistasincronizacioncliente,listadoClientePreventa);
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
			
			ClientePreventa localClientePreventa = (ClientePreventa)listadoClientePreventa.get(position);
			
			TextView textView = (TextView)localView.findViewById(R.id.tvRestauracionPreventaTitulo);
			textView.setText("Id: " + localClientePreventa.get_id() + " - " + localClientePreventa.get_nombres() + " " + localClientePreventa.get_paterno());
			
			return localView;
		}
	}
	
	private void SincronizarNombreClientes() 
	{
		if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
		{
			if(listadoClientePreventa.size()>0)
			{
				SincronizarNombreCliente(listadoClientePreventa.get(0));
			}
			else
			{
				ObtenerClientesNoSincronizadosForDisplay();
			}		
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No esta conectado a internet, intentelo mas tarde.", 1);
			return;
		}
	}
	
	private boolean ObtenerClientesNoSincronizados()
	{
		listadoClientePreventa = null;
	    try
	    {
	    	listadoClientePreventa = new ClientePreventaBLL().ObtenerClientesPreventaNoSincronizados();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no sincronizados: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no sincronizados: " + localException.getMessage());
	    	} 
	    	return false;
	    }
	      
	    if(listadoClientePreventa == null)
	    {
	    	return false;
	    }
	    else
	    {
	    	return true;
	    }
	}
	
	private void SincronizarNombreCliente(ClientePreventa clientePreventa)
	{
		pdEsperaSincronizacionNombreCliente = new ProgressDialog(this);
		pdEsperaSincronizacionNombreCliente.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaSincronizacionNombreCliente.setMessage("Sincronizando nombre cliente ...");
		pdEsperaSincronizacionNombreCliente.setCancelable(false);
		pdEsperaSincronizacionNombreCliente.setCanceledOnTouchOutside(false);
	    
	    WSUpdateClienteNombre localWSUpdateClienteNombre = new WSUpdateClienteNombre(clientePreventa);
	    try
	    {
	    	localWSUpdateClienteNombre.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSUpdateClienteNombre: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSUpdateClienteNombre: " + localException.getMessage());
	    	} 
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSUpdateClienteNombre.", 1);
	    }
	}
	
	private class WSUpdateClienteNombre extends AsyncTask<Void, Integer, Boolean>
	{
		String UPDATECLIENTE_METHOD_NAME = "UpdateClienteNombre";
		String UPDATECLIENTE_SOAP_ACTION = NAMESPACE + UPDATECLIENTE_METHOD_NAME;
		
		boolean WSUpdateCliente = false;
		int intResultado;
		String stringResultado;
		SoapObject soapObjects;
		private ClientePreventa _clientePreventa;
    
		WSUpdateClienteNombre(ClientePreventa clientePreventa)
		{
			this._clientePreventa = clientePreventa;
		}
    
		protected void onPreExecute()
	    {
			pdEsperaSincronizacionNombreCliente.show();
	    }
		
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSUpdateCliente = false;
			
			SoapObject localSoapObject = new SoapObject(NAMESPACE,UPDATECLIENTE_METHOD_NAME);
	        localSoapObject.addProperty("clienteId", _clientePreventa.get_clienteId());
	        localSoapObject.addProperty("nombres", _clientePreventa.get_nombres());
	        localSoapObject.addProperty("paterno", _clientePreventa.get_paterno());
	        localSoapObject.addProperty("materno", _clientePreventa.get_materno());
	        localSoapObject.addProperty("apellidoCasada", _clientePreventa.get_apellidoCasada());
	        localSoapObject.addProperty("direccion", _clientePreventa.get_direccion());
	        localSoapObject.addProperty("referencia", _clientePreventa.get_referencia());
			
	        SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(UPDATECLIENTE_SOAP_ACTION, localSoapSerializationEnvelope);
				
				soapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
				if(soapObjects != null)
				{
					intResultado = Integer.parseInt(soapObjects.getPropertyAsString("Id"));
					stringResultado = soapObjects.getPropertyAsString("Resultado");
				}
				
				if(stringResultado.equals("OK") && (intResultado > 0)) 
				{
					WSUpdateCliente = true;
				}
				return true;
			}
			catch (Exception localException)
			{
				WSUpdateCliente = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSUpdateClienteNombre: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSUpdateClienteNombre: " + localException.getMessage());
				}
				return true;
			}		
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaSincronizacionNombreCliente.isShowing())
			{
				pdEsperaSincronizacionNombreCliente.dismiss();
			}
			
			if(ejecutado)
			{
				if(WSUpdateCliente)
				{
					long l = 0;
					try
					{
						l =new ClientePreventaBLL().ModificarClientePreventaNombreSincronizacion(_clientePreventa.get_clienteId(),true); 
					}
					catch (Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del clientePreventa: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del clientePreventa: " + localException.getMessage());
						}
					}
					
					if(l<=0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del clientePreventa.", 1);
						return;
					}
					
					theUtilidades.MostrarMensaje(getApplicationContext(), "Cliente sincronizado.", 1);
					
					if(ObtenerClientesNoSincronizados())
					{
						SincronizarNombreClientes();
					}
					else
					{
						ObtenerClientesNoSincronizadosForDisplay();
					}				
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),stringResultado.toString(),1);
					return;
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"El webservice WSUpdateClientenombre no se ejecuto correctamente.", 1);
				return;
			}
        }
    }	
}
