package com.detesim.venderunilever;

import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import BLL.ClienteCensoBLL;
import BLL.ClientePreventaBLL;
import BLL.MyLogBLL;
import Clases.ClienteCenso;
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

public class Censistaunificacionclientessincronizacion extends Activity implements OnClickListener 
{
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URLUNILEVER = theUtilidades.get_URLUNILEVER();
	
	LinearLayout llCensistaUnificacionClientesSincro;
	ListView lvUnificacionClientesNoSincro;
	Button btnSincronizar;
	
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	ArrayList<ClienteCenso> listadoClienteCenso;
	
	ProgressDialog pdEliminarClienteCenso;
	ProgressDialog pdInsertarClienteCenso;
	ProgressDialog pdModificarClienteCenso;
	ProgressDialog pdModificarClienteVender;
	ProgressDialog pdModificarClienteCensoCoordenada;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_censistaunificacionclientessincronizacion);
		
		llCensistaUnificacionClientesSincro = (LinearLayout )findViewById(R.id.llCensistaUnificacionClienteSincronizacion);
		lvUnificacionClientesNoSincro = (ListView)findViewById(R.id.lvCensistaUnificacionClientesSincroClientes);
		btnSincronizar =(Button)findViewById(R.id.btnCensistaUnificacionClienteSincroUnificar);
		btnSincronizar.setOnClickListener(this);
		
		llCensistaUnificacionClientesSincro.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
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
		case R.id.btnCensistaUnificacionClienteSincroUnificar:
			SincronizarClientes();
			break;
		}
	}
	
	private void ObtenerClientesNoSincronizadosForDisplay()
	{
	    listadoClienteCenso = null;
	    try
	    {
	    	listadoClienteCenso = new ClienteCensoBLL().ObtenerClientesCensoNoSincronizados();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes censo no sincronizados: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes censo no sincronizados: " + localException.getMessage());
	    	} 
	    }
	      
	    if(listadoClienteCenso == null)
	    {
	    	lvUnificacionClientesNoSincro.setAdapter(null);
	    	btnSincronizar.setVisibility(View.INVISIBLE);
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No existen clientes para sincronizar.", 1);
	        return;
	    }
	    else
	    {
	    	btnSincronizar.setVisibility(View.VISIBLE);
	    	LlenarListViewUnificacionClientesNoSincro();
	    }
	}
	
	private void LlenarListViewUnificacionClientesNoSincro()
	{
		MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    ListView localListView = (ListView)findViewById(R.id.lvCensistaUnificacionClientesSincroClientes);
	    ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    localLayoutParams.height = ((int)(30*getApplicationContext().getResources().getDisplayMetrics().density) * listadoClienteCenso.size());
	    localListView.setLayoutParams(localLayoutParams);
	    localListView.setAdapter(localMiAdaptadorLista);
	}
	
	class MiAdaptadorLista extends ArrayAdapter<ClienteCenso>
	{
		private Context _context;
		
		public MiAdaptadorLista(Context context)
		{
			super(context,R.layout.disenio_censistasincronizacioncliente,listadoClienteCenso);
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
			
			ClienteCenso localCliente = (ClienteCenso)listadoClienteCenso.get(position);
			String modalidad = "";
			
			switch(localCliente.get_estado())
			{
				case 1:
					modalidad = "Cliente nuevo";
					break;
				case 2:
					modalidad = "Cliente censo";
					break;
				case 3:
					modalidad = "Cliente Vender";
					break;
				case 4:
					modalidad = "Cliente eliminado";
					break;
				case 5:
					modalidad = "Cliente coordenada nueva";
					break;
			}
			
			TextView textView = (TextView)localView.findViewById(R.id.tvRestauracionPreventaTitulo);
			textView.setText("Codigo: " + localCliente.get_codigo() + " - " + modalidad);
			
			return localView;
		}
	}

	private boolean ObtenerClientesNoSincronizados()
	{
	    listadoClienteCenso = null;
	    try
	    {
	    	listadoClienteCenso = new ClienteCensoBLL().ObtenerClientesCensoNoSincronizados();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes censo no sincronizados: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes censo no sincronizados: " + localException.getMessage());
	    	} 
	    	return false;
	    }
	      
	    if(listadoClienteCenso == null)
	    {
	    	return false;
	    }
	    else
	    {
	    	return true;
	    }
	}
	
	private void SincronizarClientes() 
	{
		if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
		{
			if(listadoClienteCenso.size()>0)
			{
				switch(listadoClienteCenso.get(0).get_estado())
				{
				case 1:
					InsertarClienteCenso(listadoClienteCenso.get(0));
					break;
				case 2:
					ModificarClienteDesdeCenso(listadoClienteCenso.get(0));
					break;
				case 3:
					ModificarClienteDesdeVender(listadoClienteCenso.get(0));
					break;
				case 4:
					EliminarClientesCenso(listadoClienteCenso.get(0));
					break;
				case 5:
					ModificarClienteDesdeCensoCoordenadaNueva(listadoClienteCenso.get(0));
					break;
				}
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

	private void InsertarClienteCenso(ClienteCenso clienteCenso)
	{
		pdInsertarClienteCenso = new ProgressDialog(this);
		pdInsertarClienteCenso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdInsertarClienteCenso.setMessage("Insertando cliente censo ...");
		pdInsertarClienteCenso.setCancelable(false);
	    
		WSInsertClientesCenso localWSInsertClientesCenso = new WSInsertClientesCenso(clienteCenso);
	    try
	    {
	    	localWSInsertClientesCenso.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSInsertClienteCenso: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSInsertClienteCenso: " + localException.getMessage());
	    	} 	
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertClientesCenso.", 1);
	    }    
	}
	
	private class WSInsertClientesCenso extends AsyncTask<Void, Integer, Boolean>
	{
		String INSERTCLIENTECENSO_METHOD_NAME = "InsertClienteFromUnilever";
		String INSERTCLIENTECENSO_SOAP_ACTION = NAMESPACE + INSERTCLIENTECENSO_METHOD_NAME;
		
		private ClienteCenso _clienteCenso;
		
		public WSInsertClientesCenso(ClienteCenso _clienteCenso)
		{
			this._clienteCenso = _clienteCenso;
		}
		
		boolean WSInsertClientesCenso;
		SoapObject soapClienteCenso;
		int intRespuesta;
		String stringRespuesta;
		
		protected void onPreExecute()
	    {
			pdInsertarClienteCenso.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSInsertClientesCenso = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,INSERTCLIENTECENSO_METHOD_NAME);
			localSoapObject.addProperty("nombres",_clienteCenso.get_nombres());
			localSoapObject.addProperty("paterno",_clienteCenso.get_paterno());
			localSoapObject.addProperty("codigo",_clienteCenso.get_codigo());
			localSoapObject.addProperty("referencia",_clienteCenso.get_referencia());
			localSoapObject.addProperty("tipoNegocioId",Integer.valueOf(_clienteCenso.get_tipoNegocioIdVender()));
			localSoapObject.addProperty("contacto",_clienteCenso.get_contacto());
			localSoapObject.addProperty("latitud",String.valueOf(_clienteCenso.get_latitud()));
			localSoapObject.addProperty("longitud",String.valueOf(_clienteCenso.get_longitud()));
			localSoapObject.addProperty("creadorId",Integer.valueOf(_clienteCenso.get_creadorId()));
			localSoapObject.addProperty("latitudCreador",String.valueOf(_clienteCenso.get_latitudCreador()));
			localSoapObject.addProperty("longitudCreador",String.valueOf(_clienteCenso.get_longitudCreador()));
			localSoapObject.addProperty("zonaId",Integer.valueOf(_clienteCenso.get_zonaId()));
			localSoapObject.addProperty("rutaId",Integer.valueOf(_clienteCenso.get_rutaId()));
			localSoapObject.addProperty("diaId",Integer.valueOf(_clienteCenso.get_diaId()));
			localSoapObject.addProperty("mercadoId",Integer.valueOf(_clienteCenso.get_mercadoId()));
			localSoapObject.addProperty("diaCreacion",Integer.valueOf(_clienteCenso.get_diaCreacion()));
			localSoapObject.addProperty("mesCreacion",Integer.valueOf(_clienteCenso.get_mesCreacion()));
			localSoapObject.addProperty("anioCreacion",Integer.valueOf(_clienteCenso.get_anioCreacion()));
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URLUNILEVER);
			
			try
			{				
				localHttpTransportSE.call(INSERTCLIENTECENSO_SOAP_ACTION, localSoapSerializationEnvelope);
				
				soapClienteCenso = ((SoapObject)localSoapSerializationEnvelope.getResponse());
				
				if(soapClienteCenso!=null)
				{
					stringRespuesta = soapClienteCenso.getPropertyAsString("Resultado");
					intRespuesta = Integer.parseInt(soapClienteCenso.getPropertyAsString("Id"));
				}				
				
				if (stringRespuesta.equals("OK") && intRespuesta >= 0) 
				{
					WSInsertClientesCenso = true;
				}
				return true;
			}
			catch (Exception localException)
			{
				WSInsertClientesCenso = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSInsertClienteCenso: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSInsertClienteCenso: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdInsertarClienteCenso.isShowing())
			{
				pdInsertarClienteCenso.dismiss();
			}
			
			if(ejecutado)
			{							
				if(WSInsertClientesCenso)
				{					
					long l = 0;
					try
					{
						l = new ClienteCensoBLL().ModificarClienteCensoincronizacion(_clienteCenso.get_id(), true);
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacion del clienteCenso: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al insertar el sincronizacion del clienteCenso: " + localException.getMessage());
						}
					}
				
					if(l<=0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo actualizar la sincronizacion del cliente censo.", 1);
						return;
					}
					
					l = 0;
					try
					{
						l = new ClientePreventaBLL().InsertarClientePreventa(intRespuesta,_clienteCenso.get_codigo(),_clienteCenso.get_tipoNegocioIdVender(),
								_clienteCenso.get_nombres() + " " + _clienteCenso.get_paterno(),_clienteCenso.get_latitud(),_clienteCenso.get_longitud(),
								"","","",1,0,1,0,0,_clienteCenso.get_rutaId(),"","",false,false,true,true,_clienteCenso.get_nombres(),_clienteCenso.get_paterno(),
								"","",_clienteCenso.get_tipoNegocioIdVender(),_clienteCenso.get_zonaId(),"","",_clienteCenso.get_diaId(),
								_clienteCenso.get_referencia(),0,"",_clienteCenso.get_contacto(),0,0,0,"","",0,"","",0,"",false);
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al insertar el clienteCenso en clientePreventa: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al insertar el clienteCenso en clientePreventa: " + localException.getMessage());
						}
					}
					
					if(l<=0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el clienteCenso en clientePreventa.", 1);
						return;
					}
					
					theUtilidades.MostrarMensaje(getApplicationContext(), "Cliente insertado.", 1);
					
					if(ObtenerClientesNoSincronizados())
					{
						SincronizarClientes();
					}
					else
					{
						ObtenerClientesNoSincronizadosForDisplay();
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo registrar el cliente censo. ", 1);
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSInsertClienteFromUnilever no se ejecuto correctamente. ", 1);
			} 
		}
	}
	
	private void ModificarClienteDesdeCenso(ClienteCenso clienteCenso)
	{
		pdModificarClienteCenso = new ProgressDialog(this);
		pdModificarClienteCenso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdModificarClienteCenso.setMessage("Modificando cliente censo ...");
		pdModificarClienteCenso.setCancelable(false);
		pdModificarClienteCenso.setCanceledOnTouchOutside(false);
	    
		WSModificarClienteCenso localWSModificarClienteCenso = new WSModificarClienteCenso(clienteCenso);
	    try
	    {
	    	localWSModificarClienteCenso.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSUpdateClienteCenso: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSUpdateClienteCenso: " + localException.getMessage());
	    	} 	
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSUpdateClienteCenso.", 1);
	    }    
	}
		
	private class WSModificarClienteCenso extends AsyncTask<Void, Integer, Boolean>
	{
		String MODIFICARCLIENTECENSO_METHOD_NAME = "UpdateClienteFromUnilever";
		String MODIFICARCLIENTECENSO_SOAP_ACTION = NAMESPACE + MODIFICARCLIENTECENSO_METHOD_NAME;
		
		private ClienteCenso _clienteCenso;
		
		public WSModificarClienteCenso(ClienteCenso _clienteCenso) 
		{
			this._clienteCenso = _clienteCenso;
		}

		boolean WSModificarClientesCenso;
		SoapObject soapClienteCenso;
		int intRespuesta;
		String stringRespuesta;
		
		protected void onPreExecute()
	    {
			pdModificarClienteCenso.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSModificarClientesCenso = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,MODIFICARCLIENTECENSO_METHOD_NAME);
			localSoapObject.addProperty("clienteId",_clienteCenso.get_clienteId());
			localSoapObject.addProperty("codigo",_clienteCenso.get_codigo());
			localSoapObject.addProperty("referencia",_clienteCenso.get_referencia());
			localSoapObject.addProperty("contacto",_clienteCenso.get_contacto());
			localSoapObject.addProperty("latitud",String.valueOf(_clienteCenso.get_latitud()));
			localSoapObject.addProperty("longitud",String.valueOf(_clienteCenso.get_longitud()));
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URLUNILEVER);
			
			try
			{				
				localHttpTransportSE.call(MODIFICARCLIENTECENSO_SOAP_ACTION, localSoapSerializationEnvelope);
				
				soapClienteCenso = ((SoapObject)localSoapSerializationEnvelope.getResponse());
				
				if(soapClienteCenso!=null)
				{
					stringRespuesta = soapClienteCenso.getPropertyAsString("Resultado");
					intRespuesta = Integer.parseInt(soapClienteCenso.getPropertyAsString("Id"));
				}				
				
				if (stringRespuesta.equals("OK") && intRespuesta >= 0) 
				{
					WSModificarClientesCenso = true;
				}
				return true;
			}
			catch (Exception localException)
			{
				WSModificarClientesCenso = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSUpdateClienteCenso: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSUpdatetClienteCenso: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdModificarClienteCenso.isShowing())
			{
				pdModificarClienteCenso.dismiss();
			}
			
			if(ejecutado)
			{							
				if(WSModificarClientesCenso)
				{					
					long l = 0;
					try
					{
						l = new ClienteCensoBLL().ModificarClienteCensoincronizacion(_clienteCenso.get_id(), true);
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacion del clienteCenso: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al insertar el sincronizacion del clienteCenso: " + localException.getMessage());
						}
					}
				
					if(l<=0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo actualizar la sincronizacion del cliente censo.", 1);
						return;
					}
					
					l = 0;
					try
					{
						l = new ClientePreventaBLL().ModificarClienteDatosCenso(_clienteCenso.get_clienteId(),_clienteCenso.get_codigo(),
								_clienteCenso.get_referencia(),_clienteCenso.get_tipoNegocioIdVender(),_clienteCenso.get_contacto(),
								_clienteCenso.get_latitud(),_clienteCenso.get_longitud());
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al modificar los datos del cliente: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar los datos del cliente: " + localException.getMessage());
						}
					}
					
					if(l<=0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar los datos del cliente preventa.", 1);
						return;
					}
					
					theUtilidades.MostrarMensaje(getApplicationContext(), "Cliente modificado.", 1);
					
					if(ObtenerClientesNoSincronizados())
					{
						SincronizarClientes();
					}
					else
					{
						ObtenerClientesNoSincronizadosForDisplay();
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el cliente vender. ", 1);
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSModificarClienteCenso no se ejecuto correctamente. ", 1);
			} 
		}
	}
	
	private void ModificarClienteDesdeVender(ClienteCenso clienteCenso)
	{
		pdModificarClienteVender = new ProgressDialog(this);
		pdModificarClienteVender.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdModificarClienteVender.setMessage("Modificando cliente vender ...");
		pdModificarClienteVender.setCancelable(false);
		pdModificarClienteVender.setCanceledOnTouchOutside(false);
	    
		WSModificarClienteVender localWSModificarClienteVender = new WSModificarClienteVender(clienteCenso);
	    try
	    {
	    	localWSModificarClienteVender.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSUpdateClienteVender: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSUpdateClienteVender: " + localException.getMessage());
	    	} 	
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSUpdateClienteVender.", 1);
	    }    
	}
	
	private class WSModificarClienteVender extends AsyncTask<Void, Integer, Boolean>
	{
		String MODIFICARCLIENTEVENDER_METHOD_NAME = "UpdateMatch";
		String MODIFICARCLIENTEVENDER_SOAP_ACTION = NAMESPACE + MODIFICARCLIENTEVENDER_METHOD_NAME;
		
		private ClienteCenso _clienteCenso;
		
		public WSModificarClienteVender(ClienteCenso _clienteCenso)
		{
			this._clienteCenso = _clienteCenso;
		}
		
		boolean WSModificarClientesVender;
		SoapObject soapClienteVender;
		int intRespuesta;
		String stringRespuesta;
		
		protected void onPreExecute()
	    {
			pdModificarClienteVender.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSModificarClientesVender = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,MODIFICARCLIENTEVENDER_METHOD_NAME);
			localSoapObject.addProperty("clienteId",_clienteCenso.get_clienteId());
			localSoapObject.addProperty("codigo",_clienteCenso.get_codigo());
			localSoapObject.addProperty("referencia",_clienteCenso.get_referencia());
			localSoapObject.addProperty("contacto",_clienteCenso.get_contacto());
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URLUNILEVER);
			
			try
			{				
				localHttpTransportSE.call(MODIFICARCLIENTEVENDER_SOAP_ACTION, localSoapSerializationEnvelope);
				
				soapClienteVender = ((SoapObject)localSoapSerializationEnvelope.getResponse());
				
				if(soapClienteVender!=null)
				{
					stringRespuesta = soapClienteVender.getPropertyAsString("Resultado");
					intRespuesta = Integer.parseInt(soapClienteVender.getPropertyAsString("Id"));
				}				
				
				if (stringRespuesta.equals("OK") && intRespuesta >= 0) 
				{
					WSModificarClientesVender = true;
				}
				return true;
			}
			catch (Exception localException)
			{
				WSModificarClientesVender = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSUpdateClienteVender: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSUpdatetClienteVender: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdModificarClienteVender.isShowing())
			{
				pdModificarClienteVender.dismiss();
			}
			
			if(ejecutado)
			{							
				if(WSModificarClientesVender)
				{					
					long l = 0;
					try
					{
						l = new ClienteCensoBLL().ModificarClienteCensoincronizacion(_clienteCenso.get_id(), true);
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacion del clienteCenso: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al insertar el sincronizacion del clienteCenso: " + localException.getMessage());
						}
					}
				
					if(l<=0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo actualizar la sincronizacion del cliente censo.", 1);
						return;
					}
										
					theUtilidades.MostrarMensaje(getApplicationContext(), "Cliente modificado.", 1);
					
					if(ObtenerClientesNoSincronizados())
					{
						SincronizarClientes();
					}
					else
					{
						ObtenerClientesNoSincronizadosForDisplay();
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el cliente vender. ", 1);
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSModificarClienteVender no se ejecuto correctamente. ", 1);
			} 
		}
	}

	private void EliminarClientesCenso(ClienteCenso clienteCenso)
	{
		pdEliminarClienteCenso = new ProgressDialog(this);
		pdEliminarClienteCenso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEliminarClienteCenso.setMessage("Elimnando cliente censo ...");
		pdEliminarClienteCenso.setCancelable(false);
		pdEliminarClienteCenso.setCanceledOnTouchOutside(false);
	    
		WSDeleteClientesCenso localWSDeleteClientesCenso = new WSDeleteClientesCenso(clienteCenso);
	    try
	    {
	    	localWSDeleteClientesCenso.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSDeleteClienteCenso: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSDeleteClienteCenso: " + localException.getMessage());
	    	} 	
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSDeleteClientesCenso.", 1);
	    }    
	}
	
	private class WSDeleteClientesCenso extends AsyncTask<Void, Integer, Boolean>
	{
		String DELETECLIENTECENSO_METHOD_NAME = "DeleteMatch";
		String DELETECLIENTECENSO_SOAP_ACTION = NAMESPACE + DELETECLIENTECENSO_METHOD_NAME;
		
		private ClienteCenso _clienteCenso;
		
		public WSDeleteClientesCenso(ClienteCenso _clienteCenso)
		{
			this._clienteCenso = _clienteCenso;
		}
		
		boolean WSDeleteClientesCenso;
		SoapObject soapClienteCenso;
		int intRespuesta;
		String stringRespuesta;
		
		protected void onPreExecute()
	    {
			pdEliminarClienteCenso.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSDeleteClientesCenso = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,DELETECLIENTECENSO_METHOD_NAME);
			localSoapObject.addProperty("codigo",_clienteCenso.get_codigo());
			localSoapObject.addProperty("motivoId",_clienteCenso.get_motivoEliminacionId());
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URLUNILEVER);
			
			try
			{				
				localHttpTransportSE.call(DELETECLIENTECENSO_SOAP_ACTION, localSoapSerializationEnvelope);
				
				soapClienteCenso = ((SoapObject)localSoapSerializationEnvelope.getResponse());
				
				if(soapClienteCenso!=null)
				{
					stringRespuesta = soapClienteCenso.getPropertyAsString("Resultado");
					intRespuesta = Integer.parseInt(soapClienteCenso.getPropertyAsString("Id"));
				}				
				
				if (stringRespuesta.equals("OK") && intRespuesta >= 0) 
				{
					WSDeleteClientesCenso = true;
				}
				return true;
			}
			catch (Exception localException)
			{
				WSDeleteClientesCenso = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSDeleteClienteCenso: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSDeleteClienteCenso: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEliminarClienteCenso.isShowing())
			{
				pdEliminarClienteCenso.dismiss();
			}
			
			if(ejecutado)
			{							
				if(WSDeleteClientesCenso)
				{					
					long l = 0;
					try
					{
						l = new ClienteCensoBLL().ModificarClienteCensoincronizacion(_clienteCenso.get_id(), true);
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacion del clienteCenso: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al insertar el sincronizacion del clienteCenso: " + localException.getMessage());
						}
					}
				
					if(l<=0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo actualizar la sincronizacion del cliente censo.", 1);
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "Cliente eliminado.", 1);
						
						if(ObtenerClientesNoSincronizados())
						{
							SincronizarClientes();
						}
						else
						{
							ObtenerClientesNoSincronizadosForDisplay();
						}
					}
				}	
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSDeleteClienteCenso no se ejecuto correctamente. ", 1);
			} 
		}
	}

	private void ModificarClienteDesdeCensoCoordenadaNueva(ClienteCenso clienteCenso)
	{
		pdModificarClienteCensoCoordenada = new ProgressDialog(this);
		pdModificarClienteCensoCoordenada.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdModificarClienteCensoCoordenada.setMessage("Modificando coordenada cliente censo ...");
		pdModificarClienteCensoCoordenada.setCancelable(false);
		pdModificarClienteCensoCoordenada.setCanceledOnTouchOutside(false);
	    
		WSModificarClienteCensoCoordenada localWSModificarClienteCensoCoordenada = new WSModificarClienteCensoCoordenada(clienteCenso);
	    try
	    {
	    	localWSModificarClienteCensoCoordenada.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSUpdateClienteCensoFromUnileverCoordenadaNueva: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSUpdateClienteCensoFromUnileverCoordenadaNueva: " + localException.getMessage());
	    	} 	
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSUpdateClienteCensoFromUnileverCoordenadaNueva.", 1);
	    }    
	}
	
	private class WSModificarClienteCensoCoordenada extends AsyncTask<Void, Integer, Boolean>
	{
		String MODIFICARCLIENTECENSOCOORDENADA_METHOD_NAME = "UpdateClienteFromUnileverCoordenadaNueva";
		String MODIFICARCLIENTECENSOCOORDENADA_SOAP_ACTION = NAMESPACE + MODIFICARCLIENTECENSOCOORDENADA_METHOD_NAME;
		
		private ClienteCenso _clienteCenso;
		
		public WSModificarClienteCensoCoordenada(ClienteCenso _clienteCenso) 
		{
			this._clienteCenso = _clienteCenso;
		}


		boolean WSModificarClientesCenso;
		SoapObject soapClienteCenso;
		int intRespuesta;
		String stringRespuesta;
		
		protected void onPreExecute()
	    {
			pdModificarClienteCensoCoordenada.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSModificarClientesCenso = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,MODIFICARCLIENTECENSOCOORDENADA_METHOD_NAME);
			localSoapObject.addProperty("clienteId",_clienteCenso.get_clienteId());
			localSoapObject.addProperty("codigo",_clienteCenso.get_codigo());
			localSoapObject.addProperty("referencia",_clienteCenso.get_referencia());
			localSoapObject.addProperty("contacto",_clienteCenso.get_contacto());
			localSoapObject.addProperty("latitud",String.valueOf(_clienteCenso.get_latitud()));
			localSoapObject.addProperty("longitud",String.valueOf(_clienteCenso.get_longitud()));
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URLUNILEVER);
			
			try
			{				
				localHttpTransportSE.call(MODIFICARCLIENTECENSOCOORDENADA_SOAP_ACTION, localSoapSerializationEnvelope);
				
				soapClienteCenso = ((SoapObject)localSoapSerializationEnvelope.getResponse());
				
				if(soapClienteCenso!=null)
				{
					stringRespuesta = soapClienteCenso.getPropertyAsString("Resultado");
					intRespuesta = Integer.parseInt(soapClienteCenso.getPropertyAsString("Id"));
				}				
				
				if (stringRespuesta.equals("OK") && intRespuesta >= 0) 
				{
					WSModificarClientesCenso = true;
				}
				return true;
			}
			catch (Exception localException)
			{
				WSModificarClientesCenso = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSUpdateClienteCensoFromUnileverCoordenadaNueva: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSUpdateClienteCensoFromUnileverCoordenadaNueva: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdModificarClienteCensoCoordenada.isShowing())
			{
				pdModificarClienteCensoCoordenada.dismiss();
			}
			
			if(ejecutado)
			{							
				if(WSModificarClientesCenso)
				{					
					long l = 0;
					try
					{
						l = new ClienteCensoBLL().ModificarClienteCensoincronizacion(_clienteCenso.get_id(), true);
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacion del clienteCenso: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al insertar el sincronizacion del clienteCenso: " + localException.getMessage());
						}
					}
				
					if(l<=0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo actualizar la sincronizacion del cliente censo.", 1);
						return;
					}
					
					l = 0;
					try
					{
						l = new ClientePreventaBLL().ModificarClienteDatosCenso(_clienteCenso.get_clienteId(),_clienteCenso.get_codigo(),
								_clienteCenso.get_referencia(),_clienteCenso.get_tipoNegocioIdVender(),_clienteCenso.get_contacto(),
								_clienteCenso.get_latitud(),_clienteCenso.get_longitud());
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al modificar los datos del cliente: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar los datos del cliente: " + localException.getMessage());
						}
					}
					
					if(l<=0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar los datos del cliente preventa.", 1);
						return;
					}
					
					theUtilidades.MostrarMensaje(getApplicationContext(), "Cliente modificado.", 1);
					
					if(ObtenerClientesNoSincronizados())
					{
						SincronizarClientes();
					}
					else
					{
						ObtenerClientesNoSincronizadosForDisplay();
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el cliente vender. ", 1);
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSUpdateClienteCensoFromUnileverCoordenadaNueva no se ejecuto correctamente. ", 1);
			} 
		}
	}
	
}
