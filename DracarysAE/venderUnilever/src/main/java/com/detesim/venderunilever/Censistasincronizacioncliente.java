package com.detesim.venderunilever;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import BLL.ClienteBLL;
import BLL.ClienteFotoBLL;
import BLL.ClienteNitBLL;
import BLL.ClienteNoAtendidoBLL;
import BLL.ClientePreventaBLL;
import BLL.ClienteVentaBLL;
import BLL.MyLogBLL;
import BLL.PreventaBLL;
import BLL.SincronizacionVentaBLL;
import BLL.VentaBLL;
import Clases.Cliente;
import Clases.ClienteFoto;
import Clases.ClienteNoAtendido;
import Clases.ClientePreventa;
import Clases.ClienteVenta;
import Clases.LoginEmpleado;
import Clases.Preventa;
import Clases.SincronizacionVenta;
import Clases.Venta;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Censistasincronizacioncliente extends Activity implements OnClickListener
{
	LinearLayout llCensistaSincronizacionCliente;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
	ArrayList<Cliente> listadoClientesNoSync;
	int clienteIdDispositivo;
	String Origen;
	
	ListView lvClientesNoSync;
	Button btnSincronizarcliente;
	ProgressDialog pdAltaCliente;
	ProgressDialog pdAltaClienteSinDatos;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_censistasincronizacioncliente);
		
		llCensistaSincronizacionCliente = (LinearLayout)findViewById(R.id.llVendedorRestauracionPreventa);
		lvClientesNoSync = (ListView)findViewById(R.id.lvRestauracionPreventaPreventas);
		btnSincronizarcliente = (Button)findViewById(R.id.btnSupervisorSincronizacionClienteSincronizarClientes);
		btnSincronizarcliente.setOnClickListener(this);
		
		llCensistaSincronizacionCliente.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
		Origen = "";
		Origen = getIntent().getExtras().getString("Origen");
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
		case R.id.btnSupervisorSincronizacionClienteSincronizarClientes:
			SincronizarClientes();
			break;
		}
	}

	private void ObtenerClientesNoSincronizadosForDisplay()
	{
	    listadoClientesNoSync = null;
	    try
	    {
	    	listadoClientesNoSync = new ClienteBLL().ObtenerClientesNoSincronizados();
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
	      
	    if(listadoClientesNoSync == null)
	    {
	    	lvClientesNoSync.setAdapter(null);
	    	btnSincronizarcliente.setVisibility(View.INVISIBLE);
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No existen clientes para sincronizar.", 1);
	        return;
	    }
	    else
	    {
	    	btnSincronizarcliente.setVisibility(View.VISIBLE);
	    	LlenarListViewClientesNoSync();
	    }
	}
	
	private boolean ObtenerClientesNoSincronizados()
	{
	    listadoClientesNoSync = null;
	    try
	    {
	    	listadoClientesNoSync = new ClienteBLL().ObtenerClientesNoSincronizados();
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
	      
	    if(listadoClientesNoSync == null)
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
			if(listadoClientesNoSync.size()>0)
			{
				if((listadoClientesNoSync.get(0)).is_completo())
				{
					SincronizarCliente(listadoClientesNoSync.get(0));
				}
				else
				{
					SincronizarClienteSinDatos(listadoClientesNoSync.get(0));
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
	
	private void LlenarListViewClientesNoSync()
	{
		MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    ListView localListView = (ListView)findViewById(R.id.lvRestauracionPreventaPreventas);
	    ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    localLayoutParams.height = ((int)(30*getApplicationContext().getResources().getDisplayMetrics().density) * listadoClientesNoSync.size());
	    localListView.setLayoutParams(localLayoutParams);
	    localListView.setAdapter(localMiAdaptadorLista);
	}
	
	class MiAdaptadorLista extends ArrayAdapter<Cliente>
	{
		private Context _context;
		
		public MiAdaptadorLista(Context context)
		{
			super(context,R.layout.disenio_censistasincronizacioncliente,listadoClientesNoSync);
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
			
			Cliente localCliente = (Cliente)listadoClientesNoSync.get(position);
			
			TextView textView = (TextView)localView.findViewById(R.id.tvRestauracionPreventaTitulo);
			textView.setText("Id: " + localCliente.get_id() + " - " + localCliente.get_nombres() + " " + localCliente.get_paterno());
			
			return localView;
		}
	}

	private void SincronizarCliente(Cliente cliente)
	{
		pdAltaCliente = new ProgressDialog(this);
	    pdAltaCliente.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdAltaCliente.setMessage("Insertando cliente ...");
	    pdAltaCliente.setCancelable(false);
	    pdAltaCliente.setCanceledOnTouchOutside(false);
	    
	    WSSincronizacionCliente localWSSincronizacionCliente = new WSSincronizacionCliente(cliente);
	    try
	    {
	    	localWSSincronizacionCliente.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertCliente: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertCliente: " + localException.getMessage());
	    	} 
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertCliente.", 1);
	    }
	}

	private class WSSincronizacionCliente extends AsyncTask<Void, Integer, Boolean>
	{
		String INSERTCLIENTE_METHOD_NAME = "InsertCliente";
		String INSERTCLIENTE_SOAP_ACTION = NAMESPACE + INSERTCLIENTE_METHOD_NAME;
		
		boolean WSInsertarCliente = false;
		int intResultado;
		String stringResultado;
		SoapObject soapObjects;
		private Cliente _cliente;
    
		WSSincronizacionCliente(Cliente cliente)
		{
			this._cliente = cliente;
		}
    
		protected void onPreExecute()
	    {
			pdAltaCliente.show();
	    }
		
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSInsertarCliente = false;
			
			SoapObject localSoapObject = new SoapObject(NAMESPACE,INSERTCLIENTE_METHOD_NAME);
	        localSoapObject.addProperty("codigo", _cliente.get_codigo());
	        localSoapObject.addProperty("nombres", _cliente.get_nombres());
	        localSoapObject.addProperty("paterno", _cliente.get_paterno());
	        localSoapObject.addProperty("materno", _cliente.get_materno());
	        localSoapObject.addProperty("apellidoCasada", _cliente.get_apellidoCasada());
	        localSoapObject.addProperty("tieneCi", String.valueOf(_cliente.is_tieneCi()));
	        localSoapObject.addProperty("ci", _cliente.get_ci());
	        localSoapObject.addProperty("expedidoId", _cliente.get_expedidoId());
	        localSoapObject.addProperty("tieneCelular", _cliente.is_tieneCelular());
	        localSoapObject.addProperty("celular", _cliente.get_celular());
	        localSoapObject.addProperty("tieneTelefono", _cliente.is_tieneTelefono());
	        localSoapObject.addProperty("telefono", _cliente.get_telefono());
	        localSoapObject.addProperty("tipoCalleId", Integer.valueOf(_cliente.get_tipoCalleId()));
	        localSoapObject.addProperty("direccion", _cliente.get_direccion());
	        localSoapObject.addProperty("numero", _cliente.get_numero());
	        localSoapObject.addProperty("edificio", _cliente.get_edificio());
	        localSoapObject.addProperty("edificioPiso", _cliente.get_edificio());
	        localSoapObject.addProperty("edificioNumero", _cliente.get_edificioNumero());
	        localSoapObject.addProperty("referencia", _cliente.get_referencia());
	        localSoapObject.addProperty("manzano", _cliente.get_manzano());
	        localSoapObject.addProperty("uv", _cliente.get_uv());
	        localSoapObject.addProperty("entreTipoCalle1Id", Integer.valueOf(_cliente.get_entreTipoCalle1Id()));
	        localSoapObject.addProperty("calle1", _cliente.get_calle1());
	        localSoapObject.addProperty("entreTipoCalle2Id", Integer.valueOf(_cliente.get_entreTipoCalle2Id()));
	        localSoapObject.addProperty("calle2", _cliente.get_calle2());
	        localSoapObject.addProperty("nombreFactura", _cliente.get_nombreFactura());
	        localSoapObject.addProperty("nit", _cliente.get_nit());
	        localSoapObject.addProperty("razonSocial", _cliente.get_razonSocial());
	        localSoapObject.addProperty("contacto", _cliente.get_contacto());
	        localSoapObject.addProperty("email", _cliente.get_email());
	        localSoapObject.addProperty("tipoNegocioId", Integer.valueOf(_cliente.get_tipoNegocioId()));
	        localSoapObject.addProperty("zonaId", Integer.valueOf(_cliente.get_zonaId()));
	        localSoapObject.addProperty("latitud", String.valueOf(_cliente.get_latitud()));
	        localSoapObject.addProperty("longitud", String.valueOf(_cliente.get_longitud()));
	        localSoapObject.addProperty("creadorId", Integer.valueOf(_cliente.get_creadorId()));
	        localSoapObject.addProperty("latitudCreador", String.valueOf(_cliente.get_latitudCreador()));
	        localSoapObject.addProperty("longitudCreador", String.valueOf(_cliente.get_longitudCreador()));
	        localSoapObject.addProperty("dia", Integer.valueOf(_cliente.get_dia()));
	        localSoapObject.addProperty("mes", Integer.valueOf(_cliente.get_mes()));
	        localSoapObject.addProperty("anio", Integer.valueOf(_cliente.get_anio()));
	        localSoapObject.addProperty("hora", Integer.valueOf(_cliente.get_hora()));
	        localSoapObject.addProperty("minuto", Integer.valueOf(_cliente.get_minuto()));
	        localSoapObject.addProperty("rutaId", Integer.valueOf(_cliente.get_rutaId()));
			localSoapObject.addProperty("diaRutaId", Integer.valueOf(_cliente.get_rutaDiaId()));
			localSoapObject.addProperty("mercadoId", Integer.valueOf(_cliente.get_mercadoId()));
			localSoapObject.addProperty("tatId", Integer.valueOf(_cliente.get_tatId()));
			localSoapObject.addProperty("tipoNit", String.valueOf(_cliente.get_tipoNit()));
			localSoapObject.addProperty("tipoPagoId", String.valueOf(_cliente.get_tipoPagoId()));
			
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
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSInsertCliente: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSInsertCliente: " + localException.getMessage());
				}
				return true;
			}		
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdAltaCliente.isShowing())
			{
				pdAltaCliente.dismiss();
			}
			
			if(ejecutado)
			{
				if(WSInsertarCliente || (intResultado > 0 && stringResultado.equals("Repetido")))
				{
					long sincroCliente = 0;
					try
					{
						sincroCliente =new ClienteBLL().ModificarSincronizacionCliente(_cliente.get_id(),intResultado,true); 
					}
					catch (Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del cliente: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del cliente: " + localException.getMessage());
						}
					}
					
					if(sincroCliente <= 0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del cliente.", 1);
						return;
					}
					
					long sincroFoto = 0;
					try
					{
						sincroFoto= new ClienteFotoBLL().ModificarClienteIdClienteFoto(_cliente.get_id(),intResultado);
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al modificar el id de la foto del cliente: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar el id de la foto del cliente: " + localException.getMessage());
						}
					}
					
					if(sincroFoto <= 0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el id de la foto del cliente.", 1);
						return;
					}
					
					theUtilidades.MostrarMensaje(getApplicationContext(), "Cliente sincronizado.", 1);
					
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
					theUtilidades.MostrarMensaje(getApplicationContext(), stringResultado, 1);
					return;
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"El webservice InsertarCliente no se ejecuto correctamente.", 1);
				return;
			}
        }
    }	
	
	private void SincronizarClienteSinDatos(Cliente cliente)
	{
		pdAltaClienteSinDatos = new ProgressDialog(this);
	    pdAltaClienteSinDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdAltaClienteSinDatos.setMessage("Insertando cliente ...");
	    pdAltaClienteSinDatos.setCancelable(false);
		pdAltaClienteSinDatos.setCanceledOnTouchOutside(false);

	    WSInsertarClienteSinDatos localWSInsertarClienteSinDatos = new WSInsertarClienteSinDatos(cliente);
	    try
	    {
	    	localWSInsertarClienteSinDatos.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertClienteSinDatos: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertClienteSinDatos: " + localException.getMessage());
	    	} 
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el WSInsertClienteSinDatos", 1);
	    }
	}
	
	private class WSInsertarClienteSinDatos extends AsyncTask<Void, Integer, Boolean>
	{
		String CLIENTE_METHOD_NAME = "UNI_InsertClienteSinDatos";
		String CLIENTE_SOAP_ACTION = NAMESPACE + CLIENTE_METHOD_NAME;
		
		boolean WSInsertarClienteSinDatos = false;
		int resultadoInt = 0;
		String resultadoString = "";
		SoapObject soapResultado;
		private Cliente _cliente;
    
		WSInsertarClienteSinDatos(Cliente cliente)
		{
			this._cliente = cliente;
		}
		
		protected void onPreExecute()
		{
			pdAltaClienteSinDatos.show();
		}
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSInsertarClienteSinDatos = false;
			
			SoapObject localSoapObject = new SoapObject(NAMESPACE,CLIENTE_METHOD_NAME);
			localSoapObject.addProperty("latitud", String.valueOf(_cliente.get_latitud()));
			localSoapObject.addProperty("longitud", String.valueOf(_cliente.get_longitud()));
			localSoapObject.addProperty("creadorId", Integer.valueOf(loginEmpleado.get_empleadoId()));
			localSoapObject.addProperty("latitudCreador", String.valueOf(_cliente.get_latitudCreador()));
       		localSoapObject.addProperty("longitudCreador", String.valueOf(_cliente.get_longitudCreador()));
       		localSoapObject.addProperty("dia", Integer.valueOf(_cliente.get_dia()));
       		localSoapObject.addProperty("mes", Integer.valueOf(_cliente.get_mes()));
       		localSoapObject.addProperty("anio", Integer.valueOf(_cliente.get_anio()));
       		localSoapObject.addProperty("hora", Integer.valueOf(_cliente.get_hora()));
       		localSoapObject.addProperty("minuto", Integer.valueOf(_cliente.get_minuto()));
       		localSoapObject.addProperty("tipoNegocioId", Integer.valueOf(_cliente.get_tipoNegocioId()));
			localSoapObject.addProperty("zonaId", Integer.valueOf(_cliente.get_zonaId()));
			localSoapObject.addProperty("nombreFactura", String.valueOf(_cliente.get_nombreFactura()));
			localSoapObject.addProperty("nit", String.valueOf(_cliente.get_nit()));
			localSoapObject.addProperty("nombres", String.valueOf(_cliente.get_nombres()));
			localSoapObject.addProperty("apellidoPaterno", String.valueOf(_cliente.get_paterno()));
			localSoapObject.addProperty("apellidoMaterno", String.valueOf(_cliente.get_materno()));
			localSoapObject.addProperty("apellidoCasada", String.valueOf(_cliente.get_apellidoCasada()));
			localSoapObject.addProperty("mercadoId", Integer.valueOf(_cliente.get_mercadoId()));
			localSoapObject.addProperty("tatId", Integer.valueOf(_cliente.get_tatId()));
			localSoapObject.addProperty("tipoNit", _cliente.get_tipoNit());
			localSoapObject.addProperty("celular", String.valueOf(_cliente.get_celular()));
			localSoapObject.addProperty("direccion", String.valueOf(_cliente.get_direccion()));
			localSoapObject.addProperty("a", String.valueOf(_cliente.get_a()).equals("1")?true:false);
			localSoapObject.addProperty("b", String.valueOf(_cliente.get_b()).equals("1")?true:false);
			localSoapObject.addProperty("c", String.valueOf(_cliente.get_c()).equals("1")?true:false);
			localSoapObject.addProperty("d", String.valueOf(_cliente.get_d()).equals("1")?true:false);
			localSoapObject.addProperty("e", String.valueOf(_cliente.get_e()).equals("1")?true:false);
			localSoapObject.addProperty("f", String.valueOf(_cliente.get_f()).equals("1")?true:false);
			localSoapObject.addProperty("g", String.valueOf(_cliente.get_g()).equals("1")?true:false);
			localSoapObject.addProperty("h", String.valueOf(_cliente.get_h()).equals("1")?true:false);
			localSoapObject.addProperty("i", String.valueOf(_cliente.get_i()).equals("1")?true:false);
			localSoapObject.addProperty("j", String.valueOf(_cliente.get_j()).equals("1")?true:false);
			localSoapObject.addProperty("k", String.valueOf(_cliente.get_k()).equals("1")?true:false);
			localSoapObject.addProperty("l", String.valueOf(_cliente.get_l()).equals("1")?true:false);
			localSoapObject.addProperty("m", String.valueOf(_cliente.get_m()).equals("1")?true:false);
			localSoapObject.addProperty("n", String.valueOf(_cliente.get_n()).equals("1")?true:false);
			localSoapObject.addProperty("o", String.valueOf(_cliente.get_o()).equals("1")?true:false);
			localSoapObject.addProperty("p", String.valueOf(_cliente.get_p()).equals("1")?true:false);
			localSoapObject.addProperty("q", String.valueOf(_cliente.get_q()).equals("1")?true:false);
			localSoapObject.addProperty("r", String.valueOf(_cliente.get_r()).equals("1")?true:false);
			localSoapObject.addProperty("secuenciaVenta", String.valueOf(_cliente.get_secuenciaPreventa()));
			localSoapObject.addProperty("secuenciaDistribucion", String.valueOf(_cliente.get_secuenciaVenta()));
			localSoapObject.addProperty("zonaMercadoId", _cliente.get_zonaMercadoId());
			localSoapObject.addProperty("provinciaId", _cliente.get_provinciaId());
			localSoapObject.addProperty("listaPrecioId", _cliente.get_precioListaNombreId());
			localSoapObject.addProperty("ci", _cliente.get_ci());
			localSoapObject.addProperty("expedido", _cliente.get_expedidoId());
			localSoapObject.addProperty("tipoPagoId", _cliente.get_tipoPagoId());
			localSoapObject.addProperty("canalRutaId", _cliente.get_canalRutaId());
        
       		SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
            try
            {
            	localHttpTransportSE.call(CLIENTE_SOAP_ACTION, localSoapSerializationEnvelope);
            	
            	soapResultado = (SoapObject)localSoapSerializationEnvelope.getResponse();
            	if(soapResultado!=null)
            	{
            		resultadoString = soapResultado.getPropertyAsString("Resultado");
            		resultadoInt = Integer.parseInt(soapResultado.getPropertyAsString("Id"));
            	}
            	
            	if(resultadoString.equals("OK") && resultadoInt > 0) 
            	{
            		WSInsertarClienteSinDatos = true;
            	}
      
            	return true;
            }
            catch (Exception localException)
            {
            	resultadoString = "Error al ejecutar el webservice WSInsertClienteSinDatos.";
            	WSInsertarClienteSinDatos = false;
            	if(localException.getMessage() == null || localException.getMessage().isEmpty())
            	{
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSInsertClienteSinDatos: vacio");
            	}
            	else
            	{
            		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSInsertClienteSinDatos: " + localException.getMessage());
            	} 
            	return true;
            }
		}
		
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdAltaClienteSinDatos.isShowing())
			{
				pdAltaClienteSinDatos.dismiss();
			}
			
			if(ejecutado)
			{
				if(WSInsertarClienteSinDatos || (resultadoInt > 0 && resultadoString.equals("Repetido")))
				{
					//Sincronizamos el cliente
					long idSincrocliente = 0;
					
					try
					{
						idSincrocliente = new ClienteBLL().ModificarSincronizacionCliente(_cliente.get_id(),resultadoInt,true);
					}				
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del cliente: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del cliente: " + localException.getMessage());
						} 
					}
					
					if (idSincrocliente <= 0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del cliente.", 1);
						return;
					}
					
					//Verificamos si existe foto para Sincronizacion
					if(VerificarExistenciaFotoClientePorClienteId(_cliente.get_id()))
					{
						long idSincroFoto = 0;
						try
						{
							idSincroFoto = new ClienteFotoBLL().ModificarClienteIdClienteFoto(_cliente.get_id(),resultadoInt);
						}
						catch(Exception localException)
						{
							if(localException.getMessage() == null || localException.getMessage().isEmpty())
							{
						        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del cliente sin datos: vacio");
							}
							else
							{
								myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del cliente sin datos: " + localException.getMessage());
							} 
						}
						
						if (idSincroFoto <= 0)
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del cliente sin datos.", 1);
							return;
						}
					}
					
					//Verificamos la existencia del cliente en clientePreventa
					if(VerificarExistenciaClienteEnClientePreventaPorClienteId(_cliente.get_id()*(-1)))
					{
						long idClientePreventa = 0;
						try
						{
							idClientePreventa = new ClientePreventaBLL().ModificarClientePreventaSincronizacionDesdeVendedor(
																_cliente.get_id()*(-1),resultadoInt,true);//implicitamente se asigna true a nombreSincronizacion
						}
						catch(Exception localException)
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
						
						if(idClientePreventa==0)
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del cliente preventa.", 1);
							return;
						}
						
						//Verificamos si el clientePreveta tiene preventas
						if(VerificarExistenciaPreventasPorClienteId(_cliente.get_id()*(-1)))
						{
							long idSincroPreventa=0;
							
							try
							{
								idSincroPreventa= new PreventaBLL().ModificarPreventaClienteIdPorIdyClienteId(_cliente.get_id()*(-1),resultadoInt);
							}
							catch(Exception localException)
							{
								if(localException.getMessage() == null || localException.getMessage().isEmpty())
								{
							        myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteId de la preventa por id y clienteId: vacio");
								}
								else
								{
									myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteId de la preventa por id y clienteId: " + localException.getMessage());
								} 
							}
							
							if(idSincroPreventa==0)
							{
								theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el clienteId de la preventa por id y clienteId.", 1);
								return;
							}
						}
					
						//Verificamos la existencia del cliente en clienteNoAtendido
						if(VerificarExistenciaClienteEnClienteNoAtendidoPorClienteId(_cliente.get_id()*(-1)))
						{
							long idClienteNoAtendido = 0;
							try
							{
								idClienteNoAtendido = new ClienteNoAtendidoBLL().ModificarClienteNoAtendidoSincronizacionDesdeVendedor(_cliente.get_id()*(-1),resultadoInt,true); 
							}
							catch(Exception localException)
							{
								if(localException.getMessage() == null || localException.getMessage().isEmpty())
								{
							        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del clientePreventa desde vendedor: vacio");
								}
								else
								{
									myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del clientePreventa desde vendedor: " + localException.getMessage());
								} 
							}
							
							if(idClienteNoAtendido == 0)
							{
								theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del cliente no atendido.", 1);
								return;
							}
						}
					}
					
					//Verificamos la existencia del cliente en clienteVenta
					if(VerificarExistenciaClienteEnClienteVentaPorClienteId(_cliente.get_id()*(-1)))
					{
						long idClienteVenta = 0;
						try
						{
							idClienteVenta = new ClienteVentaBLL().ModificarClienteVentaSincronizacionDesdeDistribuidor(
																_cliente.get_id()*(-1),resultadoInt,true);
						}
						catch(Exception localException)
						{
							if(localException.getMessage() == null || localException.getMessage().isEmpty())
							{
						        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del clienteVenta: vacio");
							}
							else
							{
								myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del clienteVenta: " + localException.getMessage());
							} 
						}
						
						if(idClienteVenta==0)
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del cliente venta.", 1);
							return;
						}
						
						//Verificamos si el clienteVenta tiene Autoventas
						if(VerificarExistenciaVentasPorClienteId(_cliente.get_id()*(-1)))
						{
							long idSincroVenta=0;
							
							try
							{
								idSincroVenta= new VentaBLL().ModificarVentaClienteIdPorIdYClienteId(_cliente.get_id()*(-1),resultadoInt);
							}
							catch(Exception localException)
							{
								if(localException.getMessage() == null || localException.getMessage().isEmpty())
								{
							        myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteId de la venta por id y clienteId: vacio");
								}
								else
								{
									myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteId de la venta por id y clienteId: " + localException.getMessage());
								} 
							}
							
							if(idSincroVenta==0)
							{
								theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el clienteId de la preventa por id y clienteId.", 1);
								return;
							}
						}
						
						//Verificamos que la sincronizacionVenta
						if(VerificarExistenciaClienteIdEnSincronizacionVenta(_cliente.get_id()*(-1)))
						{
							long idSincroVenta=0;
							
							try
							{
								idSincroVenta= new SincronizacionVentaBLL().ModificarSincronizacionVentaClienteId(_cliente.get_id()*(-1),resultadoInt);
							}
							catch(Exception localException)
							{
								if(localException.getMessage() == null || localException.getMessage().isEmpty())
								{
							        myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteId de la sincronizacionVenta por id y clienteId: vacio");
								}
								else
								{
									myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteId de la sincronizacionVenta por id y clienteId: " + localException.getMessage());
								} 
							}
							
							if(idSincroVenta==0)
							{
								theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el clienteId de la sincronizacionVenta por id y clienteId.", 1);
								return;
							}
						}
					
						//Verificamos la existencia del cliente en clienteNoAtendido
						//if(VerificarExistenciaClienteEnClienteNoAtendidoPorClienteId(_cliente.get_id()*(-1)))
						//{
						//	long idClienteNoAtendido = 0;
						//	try
						//	{
						//		idClienteNoAtendido = new ClienteNoAtendidoBLL().ModificarClienteNoAtendidoSincronizacionDesdeVendedor(_cliente.get_id()*(-1),resultadoInt,true); 
						//	}
						//	catch(Exception localException)
						//	{
						//		if(localException.getMessage() == null || localException.getMessage().isEmpty())
						//		{
						//	        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del clientePreventa desde vendedor: vacio");
						//		}
						//		else
						//		{
						//			myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del clientePreventa desde vendedor: " + localException.getMessage());
						//		} 
						//	}
						//	
						//	if(idClienteNoAtendido == 0)
						//	{
						//		theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del cliente no atendido.", 1);
						//		return;
						//	}
						//}
					}
					
					long sincroNit = 0;
					try
					{
						sincroNit = new ClienteNitBLL().ModificarClienteNitSincro(_cliente.get_id()*(-1), resultadoInt);
					}
					catch (Exception localException)
					{
						if (localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(), 1, "Error al modificar el clienteId del cliente: vacio");
						} 
						else 
						{
							myLog.InsertarLog("App",this.toString(), 1, "Error al modificar el clienteId del cliente: " + localException.getMessage());
						}
					}
					
					if(sincroNit<=0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el clienteId del cliente.", 1);
			            return;
					}
					
					theUtilidades.MostrarMensaje(getApplicationContext(), "Cliente sincronizado.", 1);

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
					theUtilidades.MostrarMensaje(getApplicationContext(), resultadoString, 1);
					return;
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSInsertarClienteSinDatos no se ejecuto correctamente.", 1);
				return;
			}
        }
	}
	
	private boolean VerificarExistenciaFotoClientePorClienteId(int clienteId)
	{
		boolean verificado = false;
		ArrayList<ClienteFoto> listadoClienteFoto = null;
		
		try
		{
			listadoClienteFoto= new ClienteFotoBLL().ObtenerClientesFotoPorClienteIdAndroid(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las fotos del cliente por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las fotos del cliente por clienteId: " + localException.getMessage());
			} 
		}
		
		if(listadoClienteFoto != null)
		{
			verificado = true;
		}
		
		return verificado;
	}
	
	private boolean VerificarExistenciaClienteEnClientePreventaPorClienteId(int clienteId)
	{
		boolean verificado = false;
		ClientePreventa localClientePreventa = null;
		
		try
		{
			localClientePreventa = new ClientePreventaBLL().ObtenerClientePreventaPor(clienteId);
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
		
		if(localClientePreventa != null)
		{
			verificado = true;
		}
		
		return verificado;
	}
	
	private boolean VerificarExistenciaClienteEnClienteNoAtendidoPorClienteId(int clienteId)
	{
		boolean verificado = false;
		ClienteNoAtendido localClienteNoAtendido = null;
		
		try
		{
			localClienteNoAtendido = new ClienteNoAtendidoBLL().ObtenerClientesNoAtendidosPorClienteId(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el clienteNoAtendido por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el clienteNoAtendido por clienteId: " + localException.getMessage());
			} 
		}
		
		if(localClienteNoAtendido != null)
		{
			verificado = true;
		}
		
		return verificado;
	}

	private boolean VerificarExistenciaPreventasPorClienteId(int clienteId)
	{
		Preventa localPreventa = null;
		try
		{
			localPreventa = new PreventaBLL().ObtenerPreventaPorClienteId(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por clienteId: " + localException.getMessage());
			}
			return false;
		}
		if(localPreventa == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	private boolean VerificarExistenciaClienteEnClienteVentaPorClienteId(int clienteId)
	{
		boolean verificado = false;
		ClienteVenta localClienteVenta = null;
		
		try
		{
			localClienteVenta = new ClienteVentaBLL().ObtenerClienteVentaPor(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el clienteVenta por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el clienteVenta por clienteId: " + localException.getMessage());
			} 
		}
		
		if(localClienteVenta != null)
		{
			verificado = true;
		}
		
		return verificado;
	}
	
	private boolean VerificarExistenciaVentasPorClienteId(int clienteId)
	{
		Venta localVenta = null;
		try
		{
			localVenta = new VentaBLL().ObtenerVentaPorClienteId(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta por clienteId: " + localException.getMessage());
			}
			return false;
		}
		if(localVenta == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	private boolean VerificarExistenciaClienteIdEnSincronizacionVenta(int clienteId)
	{
		SincronizacionVenta sincroVenta = null;
		try
		{
			sincroVenta = new SincronizacionVentaBLL().ObtenerSincronizacionVentaPorClienteId(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la sincronizacionVenta por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la sincronizacionVenta por clienteId: " + localException.getMessage());
			}
			return false;
		}
		if(sincroVenta == null)
		{
			return false;
		}
		else
		{
			return true;
		}
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
