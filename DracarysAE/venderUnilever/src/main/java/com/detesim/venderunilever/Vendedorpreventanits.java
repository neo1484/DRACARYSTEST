package com.detesim.venderunilever;

import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import BLL.ApkRutaClienteBLL;
import BLL.ClienteNitBLL;
import BLL.ClienteNoAtendidoBLL;
import BLL.ClientePreventaBLL;
import BLL.ClienteVentaBLL;
import BLL.MotivoNoAtencionBLL;
import BLL.MotivoNoEntregaBLL;
import BLL.MyLogBLL;
import BLL.ParametroGeneralBLL;
import BLL.RolBLL;
import BLL.SincronizacionVentaBLL;
import BLL.TipoNitBLL;
import Clases.ApkRutaCliente;
import Clases.ClienteNit;
import Clases.ClienteNoAtendido;
import Clases.ClientePreventa;
import Clases.Fecha;
import Clases.LoginEmpleado;
import Clases.MotivoNoAtencion;
import Clases.MotivoNoEntrega;
import Clases.ParametroGeneral;
import Clases.Rol;
import Clases.TipoNit;
import Clases.Ubicacion;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class Vendedorpreventanits extends Activity implements OnClickListener
{
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	MyLogBLL myLog = new MyLogBLL();	
	LoginEmpleado loginEmpleado;
	
	int clienteId;
	String origenSolicitud;
	ArrayList<ClienteNit> listadoClienteNit;
	double latitud;
	double longitud;
	long sincronizacionVentaId;
	boolean modificacionNit=false;
	ParametroGeneral parametroGeneral;
	ClientePreventa clientePreventa;

	LinearLayout llVendedorPreventaFactura;

	RadioButton rbtFacturar;
	RadioButton rbtAtender;
	RadioButton rbtEdicionCliente;
	RadioButton rbtAdminFotos;
	
	TextView tvMotivoNoAtencion;
	Spinner spnMotivoNoAtencion;
	Button btnInsertarMotivoNoAtencion;
		
	TextView tvSeleccionarItem;
	ListView lvItems;
	CheckBox chbAdicionarnit;
	TextView tvTipoNit;
	Spinner spnTiposNit;
	TextView tvNombre;
	EditText etNombre;
	TextView tvNit;
	EditText etNit;
	Button btnAdicionarNit;
	
	ProgressDialog pdInsertarClienteNoAtendido;
	ProgressDialog pdVentaNoEntregada;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedorpreventanits);
		
		llVendedorPreventaFactura = (LinearLayout)findViewById(R.id.VendedorPreventaFacturaLinearLayout);
		rbtFacturar = (RadioButton)findViewById(R.id.rbtVentaDirectaNitsFacturar);
		rbtFacturar.setOnClickListener(this);
		rbtAtender = (RadioButton)findViewById(R.id.rbtVentaDirectaNitsAtencionCliente);
		rbtAtender.setOnClickListener(this);
		rbtEdicionCliente = (RadioButton)findViewById(R.id.rbtVenPreNitEdicionCliente);
		rbtEdicionCliente.setOnClickListener(this);
		rbtAdminFotos = (RadioButton)findViewById(R.id.rbtVenPreNitAdminFotos);
		rbtAdminFotos.setOnClickListener(this);
		
		tvMotivoNoAtencion = (TextView)findViewById(R.id.tvVentaDirectaNitsMotivoNoAtencion);
		spnMotivoNoAtencion = (Spinner)findViewById(R.id.spnVentaDirectaNitsMotivoNoAtencion);
		btnInsertarMotivoNoAtencion = (Button)findViewById(R.id.btnVentaDirectaNitsInsertarMotivoNoAtencion);
		btnInsertarMotivoNoAtencion.setOnClickListener(this);
		
		tvSeleccionarItem = (TextView)findViewById(R.id.tvPreventaProductoSeleccionarNit);
		lvItems = (ListView)findViewById(R.id.lvVentaDirectaNitsListadoNits);
		chbAdicionarnit = (CheckBox)findViewById(R.id.chbVentaDirectaNitsMostrarDatosFactura);
		chbAdicionarnit.setOnClickListener(this);
		tvTipoNit = (TextView)findViewById(R.id.tvPreventaProductoNitTipoNit);
		spnTiposNit = (Spinner)findViewById(R.id.spnPreventaProductoNitTiposNit);
		tvNombre = (TextView)findViewById(R.id.tvPreventaProductoNit);
		etNombre = (EditText)findViewById(R.id.etVentaDirectaNitsNombreFactura);
		tvNit = (TextView)findViewById(R.id.tvVentaDirectaNitsNit);
		etNit = (EditText)findViewById(R.id.etVentaDirectaNitsNit);
		btnAdicionarNit = (Button)findViewById(R.id.btnVentaDirectaNitsInsertarNit);
		btnAdicionarNit.setOnClickListener(this);
		
		llVendedorPreventaFactura.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
		clienteId = 0;
	    clienteId = getIntent().getExtras().getInt("clienteId");
	    if(clienteId == 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el clienteId.", 1);
	    	return;
	    }
	    
	    origenSolicitud = "";
	    origenSolicitud = getIntent().getExtras().getString("origenSolicitud");
	    if(origenSolicitud == "")
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el origenSolicitud.", 1);
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
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el loginEmpleado vendedor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el loginEmpleado vendedor: " + localException.getMessage());
	    	} 
	    }
	    
	    if(loginEmpleado == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El usuario no se logeo correctamente.", 1);
	    	return;
	    }
	    else
	    {
	    	clientePreventa = null;
	    	try
	    	{
	    		clientePreventa = new ClientePreventaBLL().ObtenerClientePreventaPor(clienteId);
	    	}
	    	catch(Exception localException)
	    	{
	    		if(localException.getMessage() == null || localException.getMessage().isEmpty())
		    	{
		            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente de preventa por clienteId: vacio");
		    	}
		    	else
		    	{
		    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente de preventa por clienteId: " + localException.getMessage());
		    	}	
	    	}
	    	
	    	if(clientePreventa == null)
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el cliente de preventa por clienteId", 1);
	    		return;
	    	}
	    	
	    	CargarParametrosGenerales();
	    	
	    	if(parametroGeneral.is_edicionCliente())
	    	{
	    		rbtEdicionCliente.setVisibility(View.VISIBLE);
	    	}
	    	else
	    	{
	    		rbtEdicionCliente.setVisibility(View.INVISIBLE);
	    	}
	    	
	    	if(rbtFacturar.isChecked())
	    	{
	    		CargarDatosFacturacion();
	    	}
	    	else
	    	{
	    		CargarDatosAtencion();
	    	}
	    }
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.rbtVentaDirectaNitsFacturar:
			rbtFacturar.setChecked(true);
			rbtAtender.setChecked(false);
			rbtEdicionCliente.setChecked(false);
			rbtAdminFotos.setChecked(false);
			CargarDatosFacturacion();
			break;
		case R.id.rbtVentaDirectaNitsAtencionCliente:
			rbtFacturar.setChecked(false);
			rbtAtender.setChecked(true);
			rbtEdicionCliente.setChecked(false);
			rbtAdminFotos.setChecked(false);
			CargarDatosAtencion();
			break;
		case R.id.rbtVenPreNitEdicionCliente:
			rbtFacturar.setChecked(false);
			rbtAtender.setChecked(false);
			rbtEdicionCliente.setChecked(true);
			rbtAdminFotos.setChecked(false);
			MostrarPantallaEdicionCliente();
			break;
		case R.id.rbtVenPreNitAdminFotos:
			rbtFacturar.setChecked(false);
			rbtAtender.setChecked(false);
			rbtEdicionCliente.setChecked(false);
			rbtAdminFotos.setChecked(true);
			MostrarPantallaAdminFotos();
			break;
		case R.id.chbVentaDirectaNitsMostrarDatosFactura:
			DespliegueControlesAdicionarNit(chbAdicionarnit.isChecked());
			break;
		case R.id.btnVentaDirectaNitsInsertarMotivoNoAtencion:
			if(VerificarRolVendedor())
			{
				InsertarMotivoNoAtencion();
			}
			else
			{
				ObtenerCoordenadas();
				if(theUtilidades.VerificarConexionInternet(this))
				{
					InsertarAutoVentaNoEntregada();
				}
				else
				{
					InsertarVentaNoEntregadaDispositivo(false);
					theUtilidades.MostrarMensaje(getApplicationContext(), "AutoVenta No entregada registrada en el dispositivo.", 1);
					ModificarEstadoEntregaClienteVenta();
					RegistrarConfirmacionVenta(false);
					MostrarPantallaMapa();
				}
			}
			break;
		case R.id.btnVentaDirectaNitsInsertarNit:
			ObtenerNombreFacturaNit();
			break;
		}
	}

	private void CargarDatosFacturacion()
	{
		DespliegueControlesMotivoNoAtencion(false);
		DespliegueControlesListadoNits(true);
		DespliegueControlesAdicionarNit(false);
		
		if(parametroGeneral.is_mostrarAdicionarNit())
		{
			chbAdicionarnit.setVisibility(View.VISIBLE);
		}
		else
		{
			chbAdicionarnit.setVisibility(View.INVISIBLE);
		}
		
		if(ObtenerNitsCliente())
		{
			LlenarNitsListView();
			NitsItemOnClick();
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No exixten nits registrados pra el cliente.", 1);			
		}
		
		CargarTiposNit();
	}
	
	private void LlenarNitsListView()
	{
	    MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    	    
	    if(listadoClienteNit == null)
	    {
	    	lvItems.setAdapter(null);
	    }
	    else
	    {
		    ViewGroup.LayoutParams localLayoutParams = lvItems.getLayoutParams();
		    localLayoutParams.height = ((int)(30*getApplicationContext().getResources().getDisplayMetrics().density) * listadoClienteNit.size());
		    lvItems.setLayoutParams(localLayoutParams);
		    lvItems.setAdapter(localMiAdaptadorLista);
	    }
	}

	class MiAdaptadorLista extends ArrayAdapter<ClienteNit>
	{
		private Context _context;
		
		public MiAdaptadorLista(Context context)
		{
			super(context,R.layout.disenio_vendedorpreventanits,listadoClienteNit);
			this._context = context;
		}
    
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View localView = convertView;
			
			if (localView == null) 
			{				
				LayoutInflater layoutInflater =	(LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    		localView = layoutInflater.inflate(R.layout.disenio_vendedorpreventanits, parent, false);
			}
				
			ClienteNit localClienteNit = (ClienteNit)listadoClienteNit.get(position);
			
			TextView nombre = (TextView)localView.findViewById(R.id.tvDisenioNitNombreFactura);
			TextView nit = (TextView)localView.findViewById(R.id.tvDisenioNitNit);
			TextView tipo = (TextView)localView.findViewById(R.id.tvDisenioNitTipo);
			
			nombre.setText(localClienteNit.get_nombreFactura());
			nit.setText(localClienteNit.get_nit());
			tipo.setText(localClienteNit.get_tipoNit());
  	
			return localView;
		}
	}
	
	private void NitsItemOnClick()
	{
	    ((ListView)findViewById(R.id.lvVentaDirectaNitsListadoNits)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
	    	ClienteNit localClienteNit = null;
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	    	{
	    		localClienteNit = ((ClienteNit)listadoClienteNit.get(position));
	    		
	    		if(localClienteNit.get_tipoNit().equals(""))
	    		{
	    			modificacionNit = true;
	    			etNombre.setText(localClienteNit.get_nombreFactura());
	    			etNit.setText(localClienteNit.get_nit());
	    			DespliegueControlesAdicionarNit(true);
	    			theUtilidades.MostrarMensaje(getApplicationContext(), "Por favor especifique el tipo NIT al que corresponde.", 1);
	    		}
	    		else
	    		{
	    			modificacionNit = false;
	    			MostrarPantallaPreventaProducto(localClienteNit.get_nombreFactura(),localClienteNit.get_nit(),false,localClienteNit.get_tipoNit());
	    		}
	        }
	    });
	}
		
	private boolean ObtenerNitsCliente()
	{
		boolean obtenido = false;
		try
		{
			listadoClienteNit = new ClienteNitBLL().ObtenerClienteNitPor(clienteId);
			obtenido = true;
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los nits de los clientes por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los nits de los clientes por clienteId: " + localException.getMessage());
	    	}	
		}
		return obtenido;
	}
	
	private void CargarTiposNit()
	{
		ArrayList<TipoNit> listadoTipoNit = null;
	    
	    try
	    {
	    	listadoTipoNit = new TipoNitBLL().ObtenerTiposNit();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null|| localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los tipos nit: vacio");
	    	} 
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los tipos nit: " + localException.getMessage());
	    	} 
	    }
	    
	    if(listadoTipoNit == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los tipos nit.", 1);
	    	return;
	    }
	    
	    ArrayAdapter<TipoNit> localArrayAdapter = new ArrayAdapter<TipoNit>(this,R.layout.disenio_spinner,listadoTipoNit);
	    spnTiposNit.setAdapter(localArrayAdapter);

	}
	
	private void CargarParametrosGenerales()
	{
		parametroGeneral = null;
	    
	    try
	    {
	    	parametroGeneral = new ParametroGeneralBLL().ObtenerParamatroGeneral();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null|| localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los parametros generales: vacio");
	    	} 
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los parametros generales: " + localException.getMessage());
	    	} 
	    }
	    
	    if(parametroGeneral == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los parametros generales.", 1);
	    }
	}
	
	private void CargarDatosAtencion()
	{
		DespliegueControlesMotivoNoAtencion(true);
		DespliegueControlesAdicionarNit(false);
		if(VerificarRolVendedor())
		{
			CargarMotivosNoAtencion();
		}
		else
		{
			CargarMotivosNoEntrega();
		}
	}
	
	private void DespliegueControlesMotivoNoAtencion(boolean estado)
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
		
		tvMotivoNoAtencion.setVisibility(visibility);
		spnMotivoNoAtencion.setVisibility(visibility);
		btnInsertarMotivoNoAtencion.setVisibility(visibility);		
	}
	
	private void DespliegueControlesListadoNits(boolean estado)
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
		
		tvSeleccionarItem.setVisibility(visibility);
		lvItems.setVisibility(visibility);
	}
	
	private void DespliegueControlesAdicionarNit(boolean estado)
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
		
		tvTipoNit.setVisibility(visibility);
		spnTiposNit.setVisibility(visibility);
		tvNombre.setVisibility(visibility);
		etNombre.setVisibility(visibility);
		tvNit.setVisibility(visibility);
		etNit.setVisibility(visibility);
		btnAdicionarNit.setVisibility(visibility);
	}

	private boolean VerificarRolVendedor()
	{
		boolean rolVendedor = false;
		ArrayList<Rol> listadoRol = null;
		try
		{
			listadoRol = new RolBLL().ObtenerRolesPor(loginEmpleado.get_empleadoId());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los roles del empleado por empleadoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los roles del empleado por empleadoId: " + localException.getMessage());
		  	}
		}
		
		if(listadoRol == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontro ningun rol asociado el empleado. ", 1);
		}
		else
		{
			for(Rol item : listadoRol)
			{
				if(item.get_rol().equals("Vendedor") || item.get_rol().equals("VendedorProvincia"))
				{
					rolVendedor = true;
				}
			}
		}
		
		return rolVendedor;
	}
	
	public void CargarMotivosNoAtencion()
	{ 
	    ArrayList<MotivoNoAtencion> listadoMotivoNoAtencion = null;
	    
	    try
	    {
	    	listadoMotivoNoAtencion = new MotivoNoAtencionBLL().ObtenerMotivosNoAtencion();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null|| localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos no atencion: vacio");
	    	} 
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos no atencion: " + localException.getMessage());
	    	} 
	    }
	    
	    if(listadoMotivoNoAtencion == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los motivos no atencion.", 1);
	    	return;
	    }
	    
	    ArrayAdapter<MotivoNoAtencion> localArrayAdapter = new ArrayAdapter<MotivoNoAtencion>(this,R.layout.disenio_spinner,listadoMotivoNoAtencion);
	    spnMotivoNoAtencion.setAdapter(localArrayAdapter);
	  }
	
	public void CargarMotivosNoEntrega()
	{ 
	    ArrayList<MotivoNoEntrega> listadoMotivoNoEntrega = null;
	    
	    try
	    {
	    	listadoMotivoNoEntrega = new MotivoNoEntregaBLL().ObtenerMotivosNoEntrega();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null|| localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos no entrega: vacio");
	    	} 
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos no entrega: " + localException.getMessage());
	    	} 
	    }
	    
	    if(listadoMotivoNoEntrega == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los motivos no entrega.", 1);
	    	return;
	    }
	    
	    ArrayAdapter<MotivoNoEntrega> localArrayAdapter = new ArrayAdapter<MotivoNoEntrega>(this,R.layout.disenio_spinner,listadoMotivoNoEntrega);
	    spnMotivoNoAtencion.setAdapter(localArrayAdapter);
	    
	  }

	private void InsertarMotivoNoAtencion()
	{
		long insertado = 0;
		int motivoId = ((MotivoNoAtencion)(spnMotivoNoAtencion.getSelectedItem())).get_motivoId();
		
		if(motivoId == 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "Debe seleccionar un motivo de no atencion.", 1);
			return;
		}
		
		Fecha fecha = theUtilidades.ObtenerFecha();
		
		//Obtenemos GeoLocalizacion
		
		Ubicacion localUbicacion=null;
	    
	    try
	    {
	        localUbicacion = new Ubicacion(this);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ubicacion del preventista: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ubicacion del preventista: " + localException.getMessage());
	    	} 
	    }
	      
	   if (localUbicacion == null) 
	   {
		   theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la ubicacion del dispositivo. ", 1);
		   return;
	   }
	   
	   if(localUbicacion.canGetLocation()) 
	   {
		   latitud = localUbicacion.getLatitude();
		   longitud = localUbicacion.getLongitude();
	    }
	   
	   if(parametroGeneral.is_activarGps())
	   {
		   if(latitud == 0 || longitud == 0)
		   {
			   theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo determinar su ubicacion, por favor active su GPS.", 1);
			   return;
		   }
	   }
	   
	   if(parametroGeneral.is_activarGps() && latitud != 0 && longitud != 0 && parametroGeneral.get_distanciaCliente() > 0)
	   {
		   Location ubicacionActual = new Location("");
		   ubicacionActual.setLatitude(latitud);
		   ubicacionActual.setLongitude(longitud);
		   
		   ClientePreventa theClientePreventa = null;
		   try
		   {
			   theClientePreventa = new ClientePreventaBLL().ObtenerClientePreventaPor(clienteId);
		   }
		   catch(Exception localException)
		   {
			   if(localException.getMessage() == null || localException.getMessage().isEmpty())
		    	{
		            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente preventa por clienteId: vacio");
		    	}
		    	else
		    	{
		    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente preventa por clienteId: " + localException.getMessage());
		    	} 
		   }
		   
		   if(theClientePreventa == null)
		   {
			   theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el cliente por clienteId.", 1);
			   return;
		   }
		   
		   Location ubicacionCliente = new Location("");
		   ubicacionCliente.setLatitude(theClientePreventa.get_latitud());
		   ubicacionCliente.setLongitude(theClientePreventa.get_longitud());
		   
		   float distanceInMeters = ubicacionActual.distanceTo(ubicacionCliente);
		   
		   if(distanceInMeters > parametroGeneral.get_distanciaCliente())
		   {
			   theUtilidades.MostrarMensaje(getApplicationContext(), "Su ubicacion se encuentra lejos del cliente (" + String.valueOf(distanceInMeters) + " m.), " +
					   						"la distancia minima es de: " + String.valueOf(parametroGeneral.get_distanciaCliente()) + " m.", 1);
			   
			   ApkRutaCliente localApkRutaCliente = null;
			   try
			   {
				   localApkRutaCliente = new ApkRutaClienteBLL().ObtenerApkRutaClientePor(loginEmpleado.get_vendedorRutaId());
			   }
			   catch(Exception localException)
			   {
				   if(localException.getMessage() == null || localException.getMessage().isEmpty())
			    	{
			            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ruta del cliente por rutaId: vacio");
			    	}
			    	else
			    	{
			    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ruta del cliente por rutaId: " + localException.getMessage());
			    	} 
			   }
			   
			   if(localApkRutaCliente == null)
			   {
				   theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la ruta del cliente.", 1);
				   return;
			   }
			   else
			   {
				   if(localApkRutaCliente.is_bloquearPreventaDistancia())
				   {
					   return;
				   }
			   }
		   }
	   }
		
		ClienteNoAtendido localClienteNoatendido = new ClienteNoAtendido(0,loginEmpleado.get_empleadoId(),clienteId,motivoId,
													fecha.get_dia(),fecha.get_mes(),fecha.get_anio(),fecha.get_hora(),
													fecha.get_minuto(),latitud, longitud, false);
		
		try
		{
			insertado = new ClienteNoAtendidoBLL().InsertarClienteNoAtendido(localClienteNoatendido.get_empleadoId(),
					localClienteNoatendido.get_clienteId(),localClienteNoatendido.get_motivoId(),
					localClienteNoatendido.get_dia(),localClienteNoatendido.get_mes(),localClienteNoatendido.get_anio(),
					localClienteNoatendido.get_hora(),localClienteNoatendido.get_minuto(),localClienteNoatendido.get_latitud(),
					localClienteNoatendido.get_longitud(),localClienteNoatendido.is_sincronizacion());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null|| localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar el motivoNoAtencion: vacio");
	    	} 
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar el motivoNoAtencion: " + localException.getMessage());
	    	} 
		}
		
		if(insertado ==0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el motivo no atencion.", 1);
			return;
		}
		else
		{
			InsertClienteNoAtendido(localClienteNoatendido);	
		}
	}
	
	private void InsertClienteNoAtendido(ClienteNoAtendido localClienteNoatendido)
	{
		pdInsertarClienteNoAtendido = new ProgressDialog(this);
		pdInsertarClienteNoAtendido.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdInsertarClienteNoAtendido.setMessage("Insertando motivo de no atencion ...");
		pdInsertarClienteNoAtendido.setCancelable(false);
		pdInsertarClienteNoAtendido.setCanceledOnTouchOutside(false);
		 	 
		WSInsertarClienteNoAtendido localWSInsertarClienteNoAtendido = new WSInsertarClienteNoAtendido(localClienteNoatendido);
		try
		{
			localWSInsertarClienteNoAtendido.execute();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarClienteNoAtendido: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarClienteNoAtendido: " + localException.getMessage());
	 	 }
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice InsertarClienteNoAtendido.", 1);
		 }
	 }
	 
	private class WSInsertarClienteNoAtendido extends AsyncTask<Void, Integer, Boolean>
	{
		String INSERTCLIENTENOATENDIDOMETHOD_NAME = "InsertClienteNoAtendido";
		String INSERTCLIENTENOATENDIDO_SOAP_ACTION = NAMESPACE + INSERTCLIENTENOATENDIDOMETHOD_NAME;
		 
		private ClienteNoAtendido _clienteNoAtendido;
		 
		public WSInsertarClienteNoAtendido(ClienteNoAtendido _localClienteNoAtendido)
		 {
			 _clienteNoAtendido = _localClienteNoAtendido;
		 }

		boolean WSInsertarClienteNoAtendido = false;
		int intResultado;
		SoapObject soapResultado;
		String stringResultado;
		 
		protected void onPreExecute()
		 {
			 pdInsertarClienteNoAtendido.show();
		 }
	    
		protected Boolean doInBackground(Void... paramVarArgs)
		 {
			 WSInsertarClienteNoAtendido = false;
			 
			SoapObject localSoapObject = new SoapObject(NAMESPACE,INSERTCLIENTENOATENDIDOMETHOD_NAME);
			localSoapObject.addProperty("clienteId", Integer.valueOf(_clienteNoAtendido.get_clienteId()));
			localSoapObject.addProperty("motivoId", Integer.valueOf(_clienteNoAtendido.get_motivoId()));
			localSoapObject.addProperty("empleadoId", Integer.valueOf(_clienteNoAtendido.get_empleadoId()));
			localSoapObject.addProperty("latitud", String.valueOf(_clienteNoAtendido.get_latitud()));
			localSoapObject.addProperty("longitud", String.valueOf(_clienteNoAtendido.get_longitud()));
			localSoapObject.addProperty("anio", Integer.valueOf(_clienteNoAtendido.get_anio()));
			localSoapObject.addProperty("mes", Integer.valueOf(_clienteNoAtendido.get_mes()));
			localSoapObject.addProperty("dia", Integer.valueOf(_clienteNoAtendido.get_dia()));
			localSoapObject.addProperty("hora", Integer.valueOf(_clienteNoAtendido.get_hora()));
			localSoapObject.addProperty("minuto", Integer.valueOf(_clienteNoAtendido.get_minuto()));

			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(INSERTCLIENTENOATENDIDO_SOAP_ACTION, localSoapSerializationEnvelope);
				
				soapResultado = ((SoapObject)localSoapSerializationEnvelope.getResponse());
				if(soapResultado!=null)
				{
					intResultado = Integer.parseInt(soapResultado.getPropertyAsString("Id"));
			        stringResultado = soapResultado.getPropertyAsString("Resultado");
				}
				
				if(stringResultado.equals("OK") && intResultado > 0)
				{
					WSInsertarClienteNoAtendido = true;
				}
	            return true;
			}
			catch (Exception localException)
			{
				WSInsertarClienteNoAtendido = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertClienteNoAtendido: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertClienteNoAtendido: " + localException.getMessage());
				}
				return true;
			}
		 }
		 
		protected void onPostExecute(Boolean ejecutado)
		 {
			 if(pdInsertarClienteNoAtendido.isShowing())
			 {
				 pdInsertarClienteNoAtendido.dismiss();
			 }
			 
			 if(ejecutado)
			 {
				 if(WSInsertarClienteNoAtendido) 
				 {
					 if(SincronizarClienteNoAtendido(_clienteNoAtendido.get_clienteId()))
					 {
						 theUtilidades.MostrarMensaje(getApplicationContext(), "Cliente no atendido insertado en el server.", 1);
					 }
					 else
					 {
						 theUtilidades.MostrarMensaje(getApplicationContext(), "Cliente no atendido insertado en el dispositivo.", 1);
					 }
				 }
				 else
				 {
					 theUtilidades.MostrarMensaje(getApplicationContext(), "Cliente no atendido insertado en el dispositivo.", 1);
				 }
				 
				 MostrarPantallaMapa();
			 }
			 else
			 {
				 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertClienteNoAtendido.", 1);
				 return;
			 }
		 }
	}
	 
	private boolean SincronizarClienteNoAtendido(int clienteId)
	{
		boolean sincronizado = false;
		long modificado = 0;
		try
		{
			modificado = new ClienteNoAtendidoBLL().ModificarClienteNoAtendidoSincronizacion(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertClienteNoAtendido: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertClienteNoAtendido: " + localException.getMessage());
			}
		}
		 
		if(modificado != 0)
		{
			sincronizado = true;
		}
		return sincronizado;		 
	}
	
	private void ObtenerCoordenadas()
	{
		Ubicacion localUbicacion = null;
		try
	    {
	        localUbicacion = new Ubicacion(this);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ubicacion del vendedor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ubicacion del vendedor: " + localException.getMessage());
	    	} 
	    }
	      
	   if (localUbicacion == null) 
	   {
		   theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la ubicacion del dispositivo. ", 1);
		   return;
	   }
	   
	   if(localUbicacion.canGetLocation()) 
	   {
		   latitud = localUbicacion.getLatitude();
		   longitud = localUbicacion.getLongitude();
	    }
	}
	
	private void ObtenerNombreFacturaNit()
	{
		String nombreFactura;
		String nit;
		String tipoNit;
		
		nombreFactura = etNombre.getText().toString();
		nit = etNit.getText().toString();
		tipoNit = ((TipoNit)(spnTiposNit.getSelectedItem())).get_tipoNit();
		
		if(tipoNit.equals("Seleccione un tipo de NIT"))
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "Los datos de tipo de NIT, Nombre Factura y nit, son requeridos.", 1);
			return;
		}
		
		if(nit.length() > 12)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "El nit puede contener maximo 12 digitos.", 1);
			return;	
		}
		
		if(nombreFactura.length()<=0 || nit.length()<=0)
		{
			nombreFactura = parametroGeneral.get_textoSinNombre();
			nit = "0";
			MostrarPantallaPreventaProducto(nombreFactura, nit, false, tipoNit);	
		}
		else
		{
			Fecha fecha = theUtilidades.ObtenerFecha();
			
			if(modificacionNit)
			{
				long modificado = 0;
				try
				{
					modificado = new ClienteNitBLL().ModificarClienteNit(clienteId, nit, tipoNit);
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null|| localException.getMessage().isEmpty())
			    	{
			            myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nit del cliente: vacio");
			    	} 
			    	else
			    	{
			    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nit del cliente: " + localException.getMessage());
			    	} 
				}
				
				if(modificado == 0)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el nit del cliente.", 1);
					return;
				}
				else
				{
					MostrarPantallaPreventaProducto(nombreFactura, nit, false, tipoNit);
				}
			}
			else
			{
				long insertado = 0;
				try
				{
					insertado = new ClienteNitBLL().InsertarClienteNit(
													clienteId, nombreFactura, nit, loginEmpleado.get_empleadoId(), 
													fecha.get_dia(),fecha.get_mes(),fecha.get_anio(),true,tipoNit);
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null|| localException.getMessage().isEmpty())
			    	{
			            myLog.InsertarLog("App",this.toString(),1,"Error al insertar el clienteNit: vacio");
			    	} 
			    	else
			    	{
			    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar el clienteNit: " + localException.getMessage());
			    	} 
				}
				
				if(insertado == 0)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el nit del cliente.", 1);
					return;
				}
				else
				{					
					MostrarPantallaPreventaProducto(nombreFactura, nit, true, tipoNit);
				}
			}
		}	
	}
	
	private void InsertarAutoVentaNoEntregada()
	{
		pdVentaNoEntregada = new ProgressDialog(this);
		pdVentaNoEntregada.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdVentaNoEntregada.setMessage("Insertando venta no entregada ...");
		pdVentaNoEntregada.setCancelable(false);
		pdVentaNoEntregada.setCanceledOnTouchOutside(false);
		
		int motivoNoEntregaId = ((MotivoNoEntrega)(spnMotivoNoAtencion.getSelectedItem())).get_motivoId();
		
		WSAutoVentaNoEntregada localWSAutoVentaNoEntregada = new WSAutoVentaNoEntregada(motivoNoEntregaId);
		try
		{
			localWSAutoVentaNoEntregada.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSAutoVentaNoEntregada: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSAutoVentaNoEntregada: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSAutoVentaNoEntregada", 1);
		}
	}
	
	private class WSAutoVentaNoEntregada extends AsyncTask<Void, Integer, Boolean>
	{
		String AUTOVENTANOENTREGADA_METHOD_NAME = "AutoVentaNoEntregada";
		String AUTOVENTANOENTREGADA_SOAP_ACTION = NAMESPACE + AUTOVENTANOENTREGADA_METHOD_NAME;
		boolean WSAutoVentaNoEntregada = false;
		private int _motivoId;
		SoapObject soapObjects;
		int resultadoInt;
		String resultadoString;
    
		public WSAutoVentaNoEntregada(int motivoNoEntregaId)
		{
			this._motivoId = motivoNoEntregaId;
		}
		
		protected void onPreExecute()
	    {
	    	pdVentaNoEntregada.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSAutoVentaNoEntregada = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,AUTOVENTANOENTREGADA_METHOD_NAME);
			localSoapObject.addProperty("distribuidorId", Integer.valueOf(loginEmpleado.get_empleadoId()));
			localSoapObject.addProperty("clienteId", Integer.valueOf(clienteId));
			localSoapObject.addProperty("anio", Integer.valueOf(loginEmpleado.get_anio()));
			localSoapObject.addProperty("mes", Integer.valueOf(loginEmpleado.get_mes()));
			localSoapObject.addProperty("dia", Integer.valueOf(loginEmpleado.get_dia()));
			localSoapObject.addProperty("motivoId", Integer.valueOf(this._motivoId));
    		
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
    			
			try
			{
				localHttpTransportSE.call(AUTOVENTANOENTREGADA_SOAP_ACTION, localSoapSerializationEnvelope);
				soapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
					
				if(soapObjects != null)
				{
					resultadoInt = Integer.parseInt(soapObjects.getPropertyAsString("Id"));
					resultadoString = soapObjects.getPropertyAsString("Resultado"); 
				}
					
				if(resultadoInt > 0 && resultadoString.equals("OK"))
				{
					WSAutoVentaNoEntregada = true;
				}
				
				return true;
			}
			catch(Exception localException)
			{
				WSAutoVentaNoEntregada = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSAutoVentaNoEntregada: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSAutoVentaNoEntregada: " + localException.getMessage());
				} 
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdVentaNoEntregada.isShowing())
			{
				pdVentaNoEntregada.dismiss();
			}
    	
			if(ejecutado)
			{
				if(WSAutoVentaNoEntregada
						|| (resultadoString != null && resultadoString.equals("No Entrega Repetida") && resultadoInt <=0)) 
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"AutoVenta No entregada, registrada en el server.", 1);
					InsertarVentaNoEntregadaDispositivo(true);
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"AutoVenta NO entregada, registrada en el dispositivo.", 1);
					InsertarVentaNoEntregadaDispositivo(false);
				}
		
				ModificarEstadoEntregaClienteVenta();
				RegistrarConfirmacionVenta(WSAutoVentaNoEntregada);
				MostrarPantallaMapa();
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservise WSAutoVentaNoEntregada no se ejecuto correctamente..", 1);
				return;
			}
		}
	}
	
	private void InsertarVentaNoEntregadaDispositivo(boolean estadoSincronizacion)
	{
		boolean sincronizacion;
		
		if(estadoSincronizacion)
		{
			sincronizacion = true;
		}
		else
		{
			sincronizacion = false;
		}
		
		Fecha fecha = theUtilidades.ObtenerFecha();
		
		sincronizacionVentaId = 0;
		try
		{
			sincronizacionVentaId = new SincronizacionVentaBLL().InsertarSincronizacionVenta(0,0,clienteId,
											loginEmpleado.get_empleadoId(),0,0,0,0,0,0,0,0,0,0,0,
											0,0,0,((MotivoNoEntrega)spnMotivoNoAtencion.getSelectedItem()).get_motivoId(),
											loginEmpleado.get_dia(),loginEmpleado.get_mes(),loginEmpleado.get_anio(),
											latitud,longitud,false,6,sincronizacion,fecha.get_hora(),
											fecha.get_minuto(),true,"","",false,0,0,0,0,"",
											0,false,0,0,"",0,0,0,0,
											0,0,0,"", false);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacionVenta (Venta No Entregada): vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacionVenta (Venta No Entregada): " + localException.getMessage());
			} 
		}
		
		if(sincronizacionVentaId<=0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la sincronizacion venta: EliminarProducto", 1);
			return;
		}
	}
	
	private void ModificarEstadoEntregaClienteVenta()
	{
		long l=0;
	    try
	    {
	    	l= new ClienteVentaBLL().ModificarClienteVentaEstadoAtendido(clienteId, true);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estadoAtendido del clienteVenta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estadoAtendido del clienteVenta: " + localException.getMessage());
	    	} 
	    }
	    
	    if(l==0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo modificar el estado atendido del cliente venta.", 1);
	    	return;
	    }
	}
	
	private void RegistrarConfirmacionVenta(boolean sincronizacion)
	{
		int registrado = 0;
		try
		{
			registrado = new SincronizacionVentaBLL().ModificarSincronizacionVentaConfirmacionYSincronizacionPorId(
								(int)sincronizacionVentaId,6,true,sincronizacion);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al registrar la confirmacion de la autoventa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al registrar la confirmacion de la autoventa): " + localException.getMessage());
			} 
		}
		
		if(registrado==0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo registrar la confirmacion de la autoventa.",1);
			return;
		}
	}
	
	private void MostrarPantallaPreventaProducto(String nombreFactura,String nit,boolean nuevo,String tipoNit)
	{
		Intent localIntent = null;
		if(origenSolicitud.equals("Preventa"))
		{
			localIntent = new Intent(this, Vendedorpreventaproducto.class);
			localIntent.putExtra("clienteId", clienteId);
	    	localIntent.putExtra("factura", nombreFactura);
	    	localIntent.putExtra("nit", nit);
	    	localIntent.putExtra("nitNuevo", nuevo);
	    	localIntent.putExtra("tipoNit", tipoNit);
		}
		else
		{
			localIntent = new Intent(this, Distribuidorautoventa.class);
			localIntent.putExtra("clienteId", clienteId);
	    	localIntent.putExtra("factura", nombreFactura);
	    	localIntent.putExtra("nit", nit);
	    	localIntent.putExtra("nitNuevo", nuevo);
	    	localIntent.putExtra("tipoNit", tipoNit);
		}
    	
    	startActivity(localIntent);
	}
	
	private void MostrarPantallaMapa()
	{
		Intent localIntent = null;
		if(origenSolicitud.equals("Preventa"))
		{
			localIntent = new Intent(this, Vendedormapaclientes.class);    	
		}
		else
		{
			localIntent = new Intent(this, Distribuidormapaentregas.class);
		}
		startActivity(localIntent);	
	}

	private void MostrarPantallaEdicionCliente()
	 {
		 if(clienteId > 0)
		 {
			 if(clientePreventa.is_verificadoFoto())
			 {
				theUtilidades.MostrarMensaje(getApplicationContext(), "El cliente se encuentra verificado, no podra modificarlo.", 1);
				return;
			 }
			 else
			 {
				 Intent localIntent = new Intent(this,Censistaeditarcliente.class);
				 localIntent.putExtra("clienteId", clienteId);
				 localIntent.putExtra("peticion", "Mapa");
				 localIntent.putExtra("Origen", "Menuvendedor");
				 startActivity(localIntent);
			 }
		 }
		 else
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(), "Para editar los datos de un cliente, primero debe sincronizar el cliente.", 1);
			 return;
		 }
	 }
	
	private void MostrarPantallaAdminFotos()
	 {
		 if(clienteId > 0)
		 {
			 if(clientePreventa.is_verificadoFoto())
			 {
				theUtilidades.MostrarMensaje(getApplicationContext(), "El cliente se encuentra verificado, no podra modificarlo.", 1);
				return;
			 }
			 else
			 {
				 Intent localIntent = new Intent(this,Vendedoradminfotosclientes.class);
				 localIntent.putExtra("clienteId", clienteId);
				 startActivity(localIntent);
			 }
		 }
		 else
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(), "Para administrar las fotos del cliente, primero debe sincronizar el cliente.", 1);
			 return;
		 }
	 }
	
	@Override
 	public void onBackPressed() 
	{
		MostrarPantallaMapa();
	}
}
