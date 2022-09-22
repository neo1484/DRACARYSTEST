package com.detesim.venderunilever;

import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import BLL.ClienteBLL;
import BLL.ClientePreventaBLL;
import BLL.ClienteVentaBLL;
import BLL.MyLogBLL;
import BLL.ParametroGeneralBLL;
import BLL.RolBLL;
import Clases.Cliente;
import Clases.LoginEmpleado;
import Clases.ParametroGeneral;
import Clases.Rol;
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


public class Censistasincronizacionedicionclientes extends Activity implements OnClickListener
{
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	MyLogBLL myLog = new MyLogBLL();
	
	LoginEmpleado loginEmpleado;
	ArrayList<Cliente> listadoClientesNoSync;
	int clienteIdDispositivo;
	String Origen;
	ArrayList<Rol> listadoRol;
	ParametroGeneral parametroGeneral;
	
	LinearLayout llSincroEdicionClientes;
	ListView lvClientesNoSync;
	Button btnSincronizarcliente;
	ProgressDialog pdSincronizarCliente;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_censistasincronizacionedicionclientes);
		
		llSincroEdicionClientes = (LinearLayout)findViewById(R.id.llSincroEdicionClientes);
		lvClientesNoSync = (ListView)findViewById(R.id.lvSincroEdicionClientesClientes);
		btnSincronizarcliente = (Button)findViewById(R.id.btnSincroEdicionClientesSincronizar);
		btnSincronizarcliente.setOnClickListener(this);
		
		llSincroEdicionClientes.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
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
    		if(CargarParametroGeneral())
    		{
    			ObtenerClientesEditadosNoSincronizadosForDisplay();
    		}
    	}
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.btnSincroEdicionClientesSincronizar:
			SincronizarClientesEditados();
			break;
		}
	}
	
	private boolean CargarParametroGeneral()
	{
		parametroGeneral = null;
		
		try
		{
			parametroGeneral = new ParametroGeneralBLL().ObtenerParamatroGeneral();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener el parametro general: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener el parametro general: " + localException.getMessage());
	    	}  
		}
		
		if(parametroGeneral == null)
		{
			return false;
		}
		else
		{				
			return true;		        
		}
	}
	
	private void ObtenerClientesEditadosNoSincronizadosForDisplay()
	{
	    listadoClientesNoSync = null;
	    try
	    {
	    	listadoClientesNoSync = new ClienteBLL().ObtenerClientesEditadosNoSincronizados();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes editados no sincronizados: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes editados no sincronizados: " + localException.getMessage());
	    	} 
	    }
	      
	    if(listadoClientesNoSync == null)
	    {
	    	lvClientesNoSync.setAdapter(null);
	    	btnSincronizarcliente.setVisibility(View.INVISIBLE);
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No existen clientes editados para sincronizar.", 1);
	        return;
	    }
	    else
	    {
	    	btnSincronizarcliente.setVisibility(View.VISIBLE);
	    	LlenarListViewClientesNoSync();
	    }
	}
	
	private void LlenarListViewClientesNoSync()
	{
		MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    ListView localListView = (ListView)findViewById(R.id.lvSincroEdicionClientesClientes);
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
			textView.setText("Id: " + localCliente.get_clienteId() + " - " + localCliente.get_nombres() + " " + localCliente.get_paterno());
			
			return localView;
		}
	}
	
	private void SincronizarClientesEditados() 
	{
		if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
		{
			if(listadoClientesNoSync.size() > 0)
			{
				WSSincronizarClientesEditados(listadoClientesNoSync.get(0));
			}
			else
			{
				ObtenerClientesEditadosNoSincronizadosForDisplay();
			}		
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No esta conectado a internet, intentelo mas tarde.", 1);
			return;
		}
	}
	
	private void WSSincronizarClientesEditados(Cliente cliente)
	{
		pdSincronizarCliente = new ProgressDialog(this);
		pdSincronizarCliente.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdSincronizarCliente.setMessage("Editando cliente ...");
		pdSincronizarCliente.setCancelable(false);
		pdSincronizarCliente.setCanceledOnTouchOutside(false);
	    
	    WSUpdateClienteFromApk localWSUpdateClienteFromApk = new WSUpdateClienteFromApk(cliente);
	    try
	    {
	    	localWSUpdateClienteFromApk.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSUpdtaeclienteFromApk: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSUpdtaeclienteFromApk: " + localException.getMessage());
	    	} 
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSUpdtaeclienteFromApk.", 1);
	    }
	}
	
	private class WSUpdateClienteFromApk extends AsyncTask<Void, Integer, Boolean>
	{
		String INSERTCLIENTE_METHOD_NAME = "UNI_UpdateClienteFromApk";
		String INSERTCLIENTE_SOAP_ACTION = NAMESPACE + INSERTCLIENTE_METHOD_NAME;
		
		boolean WSInsertarCliente = false;
		int intResultado;
		String stringResultado;
		SoapObject soapObjects;
		private Cliente _cliente;
    
		WSUpdateClienteFromApk(Cliente cliente)
		{
			this._cliente = cliente;
		}
    
		protected void onPreExecute()
	    {
			pdSincronizarCliente.show();
	    }
		
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSInsertarCliente = false;
			
			SoapObject localSoapObject = new SoapObject(NAMESPACE,INSERTCLIENTE_METHOD_NAME);
	        localSoapObject.addProperty("clienteId", _cliente.get_clienteId());
	        localSoapObject.addProperty("nombres", _cliente.get_nombres());
	        localSoapObject.addProperty("paterno", _cliente.get_paterno());
	        localSoapObject.addProperty("materno", _cliente.get_materno());
	        localSoapObject.addProperty("apellidoCasada", _cliente.get_apellidoCasada());
	        localSoapObject.addProperty("telefono", String.valueOf(_cliente.get_telefono()));
	        localSoapObject.addProperty("celular", String.valueOf(_cliente.get_celular()));
	        localSoapObject.addProperty("direccion", _cliente.get_direccion());
	        localSoapObject.addProperty("referencia", _cliente.get_referencia());
	        localSoapObject.addProperty("latidud", String.valueOf(_cliente.get_latitud()));
	        localSoapObject.addProperty("longitud", String.valueOf(_cliente.get_longitud()));
	        localSoapObject.addProperty("estadoId", Integer.valueOf(_cliente.is_activo()?1:2));
	        localSoapObject.addProperty("tipoNegocioId", Integer.valueOf(_cliente.get_tipoNegocioId()));
	        localSoapObject.addProperty("ci", String.valueOf(_cliente.get_ci()));
	        localSoapObject.addProperty("provinciaId", Integer.valueOf(_cliente.get_provinciaId()));
	        localSoapObject.addProperty("zonaId", Integer.valueOf(_cliente.get_zonaId()));
	        localSoapObject.addProperty("mercadoId", Integer.valueOf(_cliente.get_mercadoId()));
	        localSoapObject.addProperty("zonaMercadoId", Integer.valueOf(_cliente.get_zonaMercadoId()));
	        localSoapObject.addProperty("tipoPagoId", Integer.valueOf(_cliente.get_tipoPagoId()));
	        localSoapObject.addProperty("precioListaId", Integer.valueOf(_cliente.get_precioListaNombreId()));
	        localSoapObject.addProperty("tipoNit", String.valueOf(_cliente.get_tipoNit()));
	        localSoapObject.addProperty("nombreFactura", String.valueOf(_cliente.get_nombreFactura()));
	        localSoapObject.addProperty("nit", String.valueOf(_cliente.get_nit()));
	        localSoapObject.addProperty("a", Boolean.valueOf((_cliente.get_a()==1)?true:false));
	        localSoapObject.addProperty("b", Boolean.valueOf((_cliente.get_b()==1)?true:false));
	        localSoapObject.addProperty("c", Boolean.valueOf((_cliente.get_c()==1)?true:false));
	        localSoapObject.addProperty("d", Boolean.valueOf((_cliente.get_d()==1)?true:false));
	        localSoapObject.addProperty("e", Boolean.valueOf((_cliente.get_e()==1)?true:false));
	        localSoapObject.addProperty("f", Boolean.valueOf((_cliente.get_f()==1)?true:false));
	        localSoapObject.addProperty("g", Boolean.valueOf((_cliente.get_g()==1)?true:false));
	        localSoapObject.addProperty("h", Boolean.valueOf((_cliente.get_h()==1)?true:false));
	        localSoapObject.addProperty("i", Boolean.valueOf((_cliente.get_i()==1)?true:false));
	        localSoapObject.addProperty("j", Boolean.valueOf((_cliente.get_j()==1)?true:false));
	        localSoapObject.addProperty("k", Boolean.valueOf((_cliente.get_k()==1)?true:false));
	        localSoapObject.addProperty("l", Boolean.valueOf((_cliente.get_l()==1)?true:false));
	        localSoapObject.addProperty("m", Boolean.valueOf((_cliente.get_m()==1)?true:false));
	        localSoapObject.addProperty("n", Boolean.valueOf((_cliente.get_n()==1)?true:false));
	        localSoapObject.addProperty("o", Boolean.valueOf((_cliente.get_o()==1)?true:false));
	        localSoapObject.addProperty("p", Boolean.valueOf((_cliente.get_p()==1)?true:false));
	        localSoapObject.addProperty("q", Boolean.valueOf((_cliente.get_q()==1)?true:false));
	        localSoapObject.addProperty("r", Boolean.valueOf((_cliente.get_r()==1)?true:false));
	        localSoapObject.addProperty("secuenciaVenta", String.valueOf(_cliente.get_secuenciaPreventa()));
	        localSoapObject.addProperty("secuenciaDistribucion", String.valueOf(_cliente.get_secuenciaVenta()));
	        			
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
			if(pdSincronizarCliente.isShowing())
			{
				pdSincronizarCliente.dismiss();
			}
			
			if(ejecutado)
			{
				if(WSInsertarCliente || (intResultado > 0 && stringResultado.equals("Repetido")))
				{
					long sincroCliente = 0;
					try
					{
						sincroCliente =new ClienteBLL().ModificarSincronizacionClienteEditadoPorClienteId(_cliente.get_clienteId(), true, true); 
					}
					catch (Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del cliente editado: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del cliente editado: " + localException.getMessage());
						}
					}
					
					if(sincroCliente <= 0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del cliente editado.", 1);
						return;
					}
					
					theUtilidades.MostrarMensaje(getApplicationContext(), "Cliente sincronizado.", 1);
					
					if(ObtenerRoles())
					{
						for(Rol rol : listadoRol)
						{
							if(rol.get_rol().equals("Vendedor"))
							{
								if(!ActualizarClientePreventa(_cliente))
								{
									theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el cliente Preventa.", 1);
								}
							}
							
							if(rol.get_rol().equals("Distribuidor"))
							{
								if(!ActualizarClienteVenta(_cliente))
								{
									theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el cliente Venta.", 1);
								}
							}
						}
						
						if(ObtenerClientesEditadosNoSincronizados())
						{
							SincronizarClientesEditados();
						}
						else
						{
							ObtenerClientesEditadosNoSincronizadosForDisplay();
						}
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el rol del empleado.", 1);
						return;
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

	private boolean ObtenerRoles()
	{
		listadoRol = null;
		
	    try
	    {
	    	listadoRol = new RolBLL().ObtenerRolesPor(loginEmpleado.get_empleadoId());
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener roles del empleado: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener roles del empleado: " + localException.getMessage());
	    	} 
	    }
	    
	    if(listadoRol == null) 
	    {
	    	return false;
		}
	    else
	    {
	    	return true;
	    }
	}
	
	private boolean ActualizarClientePreventa(Cliente theCliente)
	{
		long update = 0;
		try
		{
			update = new ClientePreventaBLL().ModificarClientePreventaDatos(theCliente.get_clienteId(), 
					theCliente.get_nombres(),theCliente.get_paterno(),theCliente.get_materno(),
					theCliente.get_apellidoCasada(),theCliente.get_direccion(),theCliente.get_referencia(),
					theCliente.get_latitud(),theCliente.get_longitud(),theCliente.get_tipoNegocioId(),theCliente.get_telefono(),
					theCliente.get_celular());
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty() || localException == null)
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar el cliente preventa: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar el cliente preventa: " + localException.getMessage());
	    	} 
		}
		
		if(update == 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo actualizar los datos del cliente preventa.", 1);
			return false;
		}
		else
		{
			return true;
		}
	}
	
	private boolean ActualizarClienteVenta(Cliente theCliente)
	{
		long update = 0;
		try
		{
			update = new ClienteVentaBLL().ModificarClienteVentaDatos(theCliente.get_clienteId(), 
					theCliente.get_nombres(),theCliente.get_paterno(),theCliente.get_materno(),
					theCliente.get_apellidoCasada(),theCliente.get_direccion(),theCliente.get_latitud(),
					theCliente.get_longitud(),theCliente.get_tipoNegocioId(),theCliente.get_telefono(),
					theCliente.get_celular());
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty() || localException == null)
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar el cliente venta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar el cliente venta: " + localException.getMessage());
	    	} 
		}
		
		if(update == 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo actualizar los datos del cliente venta.", 1);
			return false;
		}
		else
		{
			return true;
		}
	}
		
	private boolean ObtenerClientesEditadosNoSincronizados()
	{
	    listadoClientesNoSync = null;
	    try
	    {
	    	listadoClientesNoSync = new ClienteBLL().ObtenerClientesEditadosNoSincronizados();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes editados no sincronizados: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes editados no sincronizados: " + localException.getMessage());
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

	@Override
	public void onBackPressed() 
	{
		if(Origen.equals("Menuvendedor"))
		{
			Intent intent = new Intent(this,Menuvendedor.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
		else
		{
			Intent intent = new Intent(this,Menucensista.class);
			intent.putExtra("Origen", "Menucensista");
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	}
}
