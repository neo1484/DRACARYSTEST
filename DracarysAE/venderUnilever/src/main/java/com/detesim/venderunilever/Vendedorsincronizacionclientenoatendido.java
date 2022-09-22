package com.detesim.venderunilever;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import BLL.ClienteNoAtendidoBLL;
import BLL.ClientePreventaBLL;
import BLL.MyLogBLL;
import Clases.ClienteNoAtendido;
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

public class Vendedorsincronizacionclientenoatendido extends Activity implements OnClickListener
{
	LinearLayout llVendedorSincronizacionClienteNoAtendido;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
	ArrayList<ClienteNoAtendido> listadoClienteNoAtendido;
	
	ListView lvClientesNoAtendidos;
	Button btnSincronizarClientesNoAtendidos;
	ProgressDialog pdEsperaSincronizacionClientesNoAtendidos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedorsincronizacionclientenoatendido);
		
		llVendedorSincronizacionClienteNoAtendido = (LinearLayout)findViewById(R.id.VendedorSincronizacionClienteNoAtendidoLinearLayout);
		lvClientesNoAtendidos = (ListView)findViewById(R.id.lvVendedorSincronizacionClientesNoAtendidosClientes);
		btnSincronizarClientesNoAtendidos = (Button)findViewById(R.id.btnVendedorSincronizacionClientesNoAtendidosSincronizar);
		btnSincronizarClientesNoAtendidos.setOnClickListener(this);	
		btnSincronizarClientesNoAtendidos.setVisibility(View.INVISIBLE);
		
		llVendedorSincronizacionClienteNoAtendido.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));

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
    		ObtenerClientesNoAtendidosForDisplay();
    	}
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.btnVendedorSincronizacionClientesNoAtendidosSincronizar:
			SincronizarClientesNoAtendidos();
			break;
		}
	}
	
	private void ObtenerClientesNoAtendidosForDisplay()
	{
		listadoClienteNoAtendido = null;
	    try
	    {
	    	listadoClienteNoAtendido = new ClienteNoAtendidoBLL().ObtenerClientesNoSincronizados();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientesNoAtendidos no sincronizados: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientesNoAtendidos no sincronizados: " + localException.getMessage());
	    	} 
	    }
	      
	    if(listadoClienteNoAtendido == null)
	    {
	    	lvClientesNoAtendidos.setAdapter(null);
	    	btnSincronizarClientesNoAtendidos.setVisibility(View.INVISIBLE);
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No existen clientes no atendidos por sincronizar.", 1);
	    	return;
	    }
	    else
	    {
	    	if(VerificarExistenciaClientesNoSincronizadosCensista())
	    	{
	    		btnSincronizarClientesNoAtendidos.setVisibility(View.VISIBLE);
		    	LlenarListViewClientesNoSync();	
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(),"Primero debe sincronizar los clientes del menu censista.",1);
	    		return;
	    	}	    	
	    } 
	}
	
	private boolean VerificarExistenciaClientesNoSincronizadosCensista()
	{
		boolean verificado = true;
		for(ClienteNoAtendido item : listadoClienteNoAtendido)
		{
			if(item.get_clienteId()<=0)
			{
				return false;
			}
		}
		return verificado;
	}
	
	private void LlenarListViewClientesNoSync()
	{
		MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    ListView localListView = (ListView)findViewById(R.id.lvVendedorSincronizacionClientesNoAtendidosClientes);
	    ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    localLayoutParams.height = ((int)(30*getApplicationContext().getResources().getDisplayMetrics().density) * listadoClienteNoAtendido.size());
	    localListView.setLayoutParams(localLayoutParams);
	    localListView.setAdapter(localMiAdaptadorLista);
	}
	
	class MiAdaptadorLista extends ArrayAdapter<ClienteNoAtendido>
	{
		private Context _context;
		
		public MiAdaptadorLista(Context context)
		{
			super(context,R.layout.disenio_censistasincronizacioncliente,listadoClienteNoAtendido);
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
			
			ClienteNoAtendido localClienteNoAtendido = (ClienteNoAtendido)listadoClienteNoAtendido.get(position);
			
			TextView textView = (TextView)localView.findViewById(R.id.tvRestauracionPreventaTitulo);
			textView.setText("Id: " + localClienteNoAtendido.get_id() + " - " + ObtenerNombreCliente(localClienteNoAtendido.get_clienteId()));
			
			return localView;
		}
	}
	
	private String ObtenerNombreCliente(int clienteId)
	{
		ClientePreventa clientePreventa = null;
		try
		{
			clientePreventa = new ClientePreventaBLL().ObtenerClientePreventaPor(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del clientePreveta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del clientePreventa: " + localException.getMessage());
	    	} 
			return "";
		}
		
		if(clientePreventa == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ontener los detalles del cliente de la preventa.", 1);
			return "";
		}
		else
		{
			return clientePreventa.get_nombreCompleto();
		}
	}

	private void SincronizarClientesNoAtendidos() 
	{
		if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
		{
			if(listadoClienteNoAtendido.size()>0)
			{
				SincronizarClientesNoAtendidos(listadoClienteNoAtendido.get(0));
			}
			else
			{
				ObtenerClientesNoAtendidosForDisplay();
			}		
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No esta conectado a internet, intentelo mas tarde.", 1);
			return;
		}
	}
	
	private boolean ObtenerClientesNoAtendidos()
	{
		listadoClienteNoAtendido = null;
	    try
	    {
	    	listadoClienteNoAtendido = new ClienteNoAtendidoBLL().ObtenerClientesNoSincronizados();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no atendidos no sincronizados: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no atendidos no sincronizados: " + localException.getMessage());
	    	} 
	    	return false;
	    }
	      
	    if(listadoClienteNoAtendido == null)
	    {
	    	return false;
	    }
	    else
	    {
	    	return true;
	    }
	}

	private void SincronizarClientesNoAtendidos(ClienteNoAtendido clienteNoAtendido)
	{
		pdEsperaSincronizacionClientesNoAtendidos = new ProgressDialog(this);
		pdEsperaSincronizacionClientesNoAtendidos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaSincronizacionClientesNoAtendidos.setMessage("Sincronizando cliente no atendido ...");
		pdEsperaSincronizacionClientesNoAtendidos.setCancelable(false);
		pdEsperaSincronizacionClientesNoAtendidos.setCanceledOnTouchOutside(false);
	    
	    WSInsertClienteNoAtendido localWSInsertClienteNoAtendido = new WSInsertClienteNoAtendido(clienteNoAtendido);
	    try
	    {
	    	localWSInsertClienteNoAtendido.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertClienteNoAtendido: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertClienteNoAtendido: " + localException.getMessage());
	    	} 
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertClienteNoAtendido.", 1);
	    }
	}
	
	private class WSInsertClienteNoAtendido extends AsyncTask<Void, Integer, Boolean>
	{
		String INSERTCLIENTE_METHOD_NAME = "InsertClienteNoAtendido";
		String INSERTCLIENTE_SOAP_ACTION = NAMESPACE + INSERTCLIENTE_METHOD_NAME;
		
		boolean WSInsertarCliente = false;
		int intResultado;
		String stringResultado;
		SoapObject soapObjects;
		private ClienteNoAtendido _clienteNoAtendido;
    
		WSInsertClienteNoAtendido(ClienteNoAtendido clienteNoAtendido)
		{
			this._clienteNoAtendido = clienteNoAtendido;
		}
    
		protected void onPreExecute()
	    {
			pdEsperaSincronizacionClientesNoAtendidos.show();
	    }
		
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSInsertarCliente = false;
			
			SoapObject localSoapObject = new SoapObject(NAMESPACE,INSERTCLIENTE_METHOD_NAME);
	        localSoapObject.addProperty("clienteId", _clienteNoAtendido.get_clienteId());
	        localSoapObject.addProperty("motivoId", _clienteNoAtendido.get_motivoId());
	        localSoapObject.addProperty("empleadoId", _clienteNoAtendido.get_empleadoId());
	        localSoapObject.addProperty("latitud", String.valueOf(_clienteNoAtendido.get_latitud()));
	        localSoapObject.addProperty("longitud", String.valueOf(_clienteNoAtendido.get_longitud()));
	        localSoapObject.addProperty("anio", _clienteNoAtendido.get_anio());
	        localSoapObject.addProperty("mes", _clienteNoAtendido.get_mes());
	        localSoapObject.addProperty("dia", _clienteNoAtendido.get_dia());
	        localSoapObject.addProperty("hora", _clienteNoAtendido.get_hora());
	        localSoapObject.addProperty("minuto", _clienteNoAtendido.get_minuto());	        
			
	        SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(INSERTCLIENTE_SOAP_ACTION, localSoapSerializationEnvelope);
				
				soapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
				if(soapObjects != null)
				{
					intResultado = Integer.parseInt(soapObjects.getPropertyAsString("Id"));
					stringResultado = soapObjects.getPropertyAsString("Resultado");
				}
				
				if(stringResultado.equals("OK") && (intResultado > 0)) 
				{
					WSInsertarCliente = true;
				}
				return true;
			}
			catch (Exception localException)
			{
				WSInsertarCliente = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSInsertClienteNoAtendido: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSInsertClienteNoAtendido: " + localException.getMessage());
				}
				return true;
			}		
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaSincronizacionClientesNoAtendidos.isShowing())
			{
				pdEsperaSincronizacionClientesNoAtendidos.dismiss();
			}
			
			if(ejecutado)
			{
				if(WSInsertarCliente || (stringResultado != null && stringResultado.equals("Cliente No Atendido Repetido") && intResultado > 0))
				{
					long l = 0;
					try
					{
						l =new ClienteNoAtendidoBLL().ModificarClienteNoAtendidoSincronizacion(_clienteNoAtendido.get_clienteId()); 
					}
					catch (Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del clienteNoAtendido: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del clienteNoAtendido: " + localException.getMessage());
						}
					}
					
					if(l<=0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del clienteNoAtendido.", 1);
						return;
					}
					
					theUtilidades.MostrarMensaje(getApplicationContext(), "Cliente sincronizado.", 1);
					
					if(ObtenerClientesNoAtendidos())
					{
						SincronizarClientesNoAtendidos();
					}
					else
					{
						ObtenerClientesNoAtendidosForDisplay();
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
				theUtilidades.MostrarMensaje(getApplicationContext(),"El webservice WSInsertClienteNoAtendido no se ejecuto correctamente.", 1);
				return;
			}
        }
    }
}
