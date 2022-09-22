package com.detesim.venderunilever;

import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import BLL.ApkRutaClienteBLL;
import BLL.ClienteCensoBLL;
import BLL.ClientePreventaBLL;
import BLL.MercadoBLL;
import BLL.MotivoEliminacionMatchBLL;
import BLL.MyLogBLL;
import BLL.ParametroGeneralBLL;
import BLL.TipoNegocioBLL;
import BLL.UltimaCoordenadaBLL;
import BLL.ZonaBLL;
import Clases.ApkRutaCliente;
import Clases.ClienteCenso;
import Clases.ClientePreventa;
import Clases.LoginEmpleado;
import Clases.Mercado;
import Clases.MotivoEliminacionMatch;
import Clases.ParametroGeneral;
import Clases.TipoNegocio;
import Clases.Ubicacion;
import Clases.Zona;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class Censistaunificacionclientes extends Activity implements OnClickListener 
{
	Utilidades theUtilidades = new Utilidades();
	MyLogBLL myLog = new MyLogBLL();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	String URLUNILEVER = theUtilidades.get_URLUNILEVER();
	LoginEmpleado loginEmpleado;
	
	LinearLayout llUnificacionClientes;
	RadioButton rbCenso;
	RadioButton rbVender;
	RadioButton rbNuevo;
	ImageView ivCoordenadas;
	TextView tvLatitudTxt;
	TextView tvLatitud;
	TextView tvLongitudTxt;
	TextView tvLongitud;
	TextView tvMotivoElimarCliente;
	Spinner spnMotivoEliminarCliente;
	Button btnEliminar;
	TextView tvNombres;
	EditText etNombres;
	TextView tvPaterno;
	EditText etPaterno;
	TextView tvTipoNegocio;
	Spinner spnTipoNegocio;
	TextView tvZona;
	Spinner spnZona;
	TextView tvRuta;
	Spinner spnRuta;
	TextView tvMercado;
	Spinner spnMercado;
	TextView tvCodigoTxt;
	TextView tvCodigo;
	TextView tvReferencia;
	EditText etReferencia;
	TextView tvContacto;
	EditText etContacto;
	Button btnRegistrarCliente;
	
	ParametroGeneral parametroGeneral;
	int clienteCensoId;
	int clienteVenderId;
	String origen;
	int coordenadaNueva;
	double latitudNueva;
	double longitudNueva;
	int motivoEliminacionId;
	ClienteCenso clienteCenso;
	ClientePreventa clienteVender;
	double latitudCreador;
	double longitudCreador;
	boolean Insercion;
	String referencia;
	String contacto;
	
	ProgressDialog pdEliminarClienteCenso;
	ProgressDialog pdInsertarClienteCenso;
	ProgressDialog pdModificarClienteCenso;
	ProgressDialog pdModificarClienteVender;
	ProgressDialog pdModificarClienteCensoCoordenada;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_censistaunificacionclientes);
		
		llUnificacionClientes  =(LinearLayout)findViewById(R.id.llUnificacionCenso);
		rbCenso = (RadioButton)findViewById(R.id.rbtCensoUnificacionClientesCenso);
		rbCenso.setOnClickListener(this);
		rbVender = (RadioButton)findViewById(R.id.rbtCensoUnificacionClientesVender);
		rbVender.setOnClickListener(this);
		rbNuevo = (RadioButton)findViewById(R.id.rbtCensoUnificacionClientesNuevo);
		rbNuevo.setOnClickListener(this);
		ivCoordenadas = (ImageView)findViewById(R.id.imgbtnCensoUnificacionClientesCoordenada);
		ivCoordenadas.setOnClickListener(this);
		tvLatitudTxt = (TextView)findViewById(R.id.tvCensoUnificacionClienteLatitudTxt);
		tvLatitud = (TextView)findViewById(R.id.tvCensoUnificacionClientesLatitud);
		tvLongitudTxt = (TextView)findViewById(R.id.tvCensoUnificacionClienteLongitudTxt);
		tvLongitud = (TextView)findViewById(R.id.tvCensoUnificacionClientesLongitud);
		tvMotivoElimarCliente = (TextView)findViewById(R.id.tvCensoUnificacionClienteMotivoEliminacion);
		spnMotivoEliminarCliente = (Spinner)findViewById(R.id.spnCensoUnificacionClienteMotivoEliminacion);
		btnEliminar = (Button)findViewById(R.id.btnCensoUnificacionClienteEliminarCliente);
		btnEliminar.setOnClickListener(this);
		tvNombres = (TextView)findViewById(R.id.tvCensoUnificacionClientesNombre);
		etNombres = (EditText)findViewById(R.id.etCensoUnificacionClientesNombre);
		tvPaterno = (TextView)findViewById(R.id.tvCensoUnificacionClientesPaterno);
		etPaterno = (EditText)findViewById(R.id.etCensoUnificacionClientesPaterno);
		tvTipoNegocio = (TextView)findViewById(R.id.tvCensoUnificacionClientesTipoNegocio);
		spnTipoNegocio = (Spinner)findViewById(R.id.spnCensoUnificacionClientesTipoNegocio);
		tvZona = (TextView)findViewById(R.id.tvCensoUnificacionClientesZona);
		spnZona = (Spinner)findViewById(R.id.spnCensoUnificacionClientesZona);
		tvRuta = (TextView)findViewById(R.id.tvCensoUnificacionClientesRuta);
		spnRuta = (Spinner)findViewById(R.id.spnCensoUnificacionClientesRutaDia);
		tvMercado = (TextView)findViewById(R.id.tvCensoUnificacionClientesMercado);
		spnMercado = (Spinner)findViewById(R.id.spnCensoUnificacionClientesMercado);
		tvCodigoTxt = (TextView)findViewById(R.id.tvCensoUnificacionClientesCodigoTxt);
		tvCodigo = (TextView)findViewById(R.id.tvCensoUnificacionClientesCodigo);
		tvReferencia = (TextView)findViewById(R.id.tvCensoUnificacionClientesReferencia);
		etReferencia = (EditText)findViewById(R.id.etCensoUnificacionClientesReferencia);
		tvContacto = (TextView)findViewById(R.id.tvCensoUnificacionClientesContacto);
		etContacto = (EditText)findViewById(R.id.etCensoUnificacionClientesContacto);
		btnRegistrarCliente = (Button)findViewById(R.id.btnCensoUnificacionClientesRegistrarCliente);
		btnRegistrarCliente.setOnClickListener(this);
		
		llUnificacionClientes.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
				
		clienteCensoId = 0;
	    clienteCensoId = getIntent().getExtras().getInt("ClienteCensoId");
	    if(clienteCensoId == 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el id del cliente del censo", 1);
	    	MostrarControlesOpciones(false);
	    	MostrarControlCoordenada(false);
    		MostrarControlesEliminar(false);
    		MostrarControlesInsertar(false);
    		MostrarControlesEditarCenso(false);
    		MostrarControlesEditarVender(false);
	    	return;
	    }
	    
	    clienteVenderId = 0;
	    clienteVenderId = getIntent().getExtras().getInt("ClienteVenderId");
	    
	    origen="";
	    origen = getIntent().getExtras().getString("Origen");
	    
	    coordenadaNueva = 0;
	    coordenadaNueva = getIntent().getExtras().getInt("CoordenadaNueva");
	    
		latitudNueva = 0;
		latitudNueva = getIntent().getExtras().getDouble("LatitudNueva");
		
		longitudNueva = 0;
		longitudNueva = getIntent().getExtras().getDouble("LongitudNueva");
	    
	    try
	    {
	    	loginEmpleado = this.theUtilidades.ObtenerLoginEmpleado();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el LoginEmpleado del censista: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el LoginEmpleado del censista: " + localException.getMessage());
	    	}	      
	    }
	    
	    if (loginEmpleado == null)
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los datos de login del censista.", 2);
	    }
	    else
	    {
	    	MostrarControlCoordenada(false);
	    	ObtenerClienteCenso();
	    	CargarInformacion();
	    	
	    	if(coordenadaNueva==0)
	    	{
		    	if(clienteCensoId != 0 && clienteVenderId == 0)
		    	{
		    		Insercion = true;
		    		MostrarControlesOpciones(false);
		    		MostrarControlesEliminar(true);
		    		MostrarControlesInsertar(true);
		    	}
		    	else if(clienteCensoId != 0 && clienteVenderId != 0)
		    	{
		    		MostrarControlesOpciones(true);
		    		MostrarControlCoordenada(false);
		    		MostrarControlesEliminar(false);
		    		MostrarControlesInsertar(false);
		    		MostrarControlesEditarCenso(true);
		    		EstablecerCoordenadaCenso();
		    		ObtenerClienteVender();
		    	}
	    	}
	    	else
	    	{
	    		ObtenerClienteVender();
	    		EstablecerCoordenadaNueva(latitudNueva, longitudNueva);
	    		MostrarControlesEliminar(false);
	    		MostrarControlesInsertar(false);
	    		MostrarControlesEditarVender(false);
				MostrarControlesEditarCenso(true);
				MostrarControlCoordenada(true);
				rbNuevo.setChecked(true);
	    	}
	    }
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.btnCensoUnificacionClienteEliminarCliente:
			EliminarCliente();
			break;
		case R.id.btnCensoUnificacionClientesRegistrarCliente:
			if(Insercion)
			{
				InsertarCliente();
			}
			else
			{
				EditarCliente();
			}
			break;
		case R.id.rbtCensoUnificacionClientesCenso:
			EstablecerCoordenadaCenso();
			MostrarControlCoordenada(false);
			MostrarControlesEditarVender(false);
			MostrarControlesEditarCenso(true);
			break;
		case R.id.rbtCensoUnificacionClientesVender:
			EstablecerCoordenadaVender();
			MostrarControlCoordenada(false);
			MostrarControlesEditarCenso(false);
			MostrarControlesEditarVender(true);
			break;
		case R.id.rbtCensoUnificacionClientesNuevo:
			EstablecerCoordenadaNueva(0.0,0.0);
			MostrarControlesEditarVender(false);
			MostrarControlesEditarCenso(true);
			MostrarControlCoordenada(true);
			break;
		case R.id.imgbtnCensoUnificacionClientesCoordenada:
			MostrarMapaNuevaCoordenada();
			break;
		}
	}
	
	private void MostrarControlesEliminar(boolean estado)
	{
		int visibility;
		if(estado)
		{
			visibility = View.VISIBLE;
		}
		else
		{
			visibility = View.INVISIBLE;
		}
		
		tvMotivoElimarCliente.setVisibility(visibility);
		spnMotivoEliminarCliente.setVisibility(visibility);
		btnEliminar.setVisibility(visibility);
	}
	
	private void MostrarControlesOpciones(boolean estado)
	{
		int visibility;
		if(estado)
		{
			visibility = View.VISIBLE;
		}
		else
		{
			visibility = View.INVISIBLE;
		}
		
		rbCenso.setVisibility(visibility);
		rbVender.setVisibility(visibility);
		rbNuevo.setVisibility(visibility);
	}
	
	private void MostrarControlCoordenada(boolean estado)
	{
		int visibility;
		if(estado)
		{
			visibility = View.VISIBLE;
		}
		else
		{
			visibility = View.INVISIBLE;
		}
		
		ivCoordenadas.setVisibility(visibility);
	}
	
	private void MostrarControlesInsertar(boolean estado)
	{
		int visibility;
		if(estado)
		{
			visibility = View.VISIBLE;
		}
		else
		{
			visibility = View.INVISIBLE;
		}
		tvLatitudTxt.setVisibility(visibility);
		tvLatitud.setVisibility(visibility);
		tvLongitudTxt.setVisibility(visibility);
		tvLongitud.setVisibility(visibility);
		tvNombres.setVisibility(visibility);
		etNombres.setVisibility(visibility);
		tvPaterno.setVisibility(visibility);
		etPaterno.setVisibility(visibility);
		tvTipoNegocio.setVisibility(visibility);
		spnTipoNegocio.setVisibility(visibility);
		tvZona.setVisibility(visibility);
		spnZona.setVisibility(visibility);
		tvRuta.setVisibility(visibility);
		spnRuta.setVisibility(visibility);
		if(parametroGeneral.is_mercadoRequerido())
		{
			tvMercado.setVisibility(View.VISIBLE);
			spnMercado.setVisibility(View.VISIBLE);
		}
		else
		{
			tvMercado.setVisibility(View.INVISIBLE);
			spnMercado.setVisibility(View.INVISIBLE);
		}
		tvCodigoTxt.setVisibility(visibility);
		tvCodigo.setVisibility(visibility);
		tvReferencia.setVisibility(visibility);
		etReferencia.setVisibility(visibility);
		tvContacto.setVisibility(visibility);
		etContacto.setVisibility(visibility);
		btnRegistrarCliente.setVisibility(visibility);
	}
	
	private void MostrarControlesEditarCenso(boolean estado)
	{
		int visibility;
		if(estado)
		{
			visibility = View.VISIBLE;
		}
		else
		{
			visibility = View.INVISIBLE;
		}
		tvLatitudTxt.setVisibility(visibility);
		tvLatitud.setVisibility(visibility);
		tvLongitudTxt.setVisibility(visibility);
		tvLongitud.setVisibility(visibility);
		tvCodigoTxt.setVisibility(visibility);
		tvCodigo.setVisibility(visibility);
		tvReferencia.setVisibility(visibility);
		etReferencia.setVisibility(visibility);
		tvContacto.setVisibility(visibility);
		etContacto.setVisibility(visibility);
		btnRegistrarCliente.setVisibility(visibility);
	}
	
	private void MostrarControlesEditarVender(boolean estado)
	{
		int visibility;
		if(estado)
		{
			visibility = View.VISIBLE;
		}
		else
		{
			visibility = View.INVISIBLE;
		}
		tvLatitudTxt.setVisibility(visibility);
		tvLatitud.setVisibility(visibility);
		tvLongitudTxt.setVisibility(visibility);
		tvLongitud.setVisibility(visibility);
		tvCodigoTxt.setVisibility(visibility);
		tvCodigo.setVisibility(visibility);
		tvReferencia.setVisibility(visibility);
		etReferencia.setVisibility(visibility);
		tvContacto.setVisibility(visibility);
		etContacto.setVisibility(visibility);
		btnRegistrarCliente.setVisibility(visibility);
	}
	
	public void CargarInformacion()
	{
		CargarParametroGeneral();
		CargarMotivosEliminacion();
	    CargarTipoNegocio();
	    CargarZonas();
	    CargarRutaCliente();
	    CargarMercados();
	}
	
	public void ObtenerClienteCenso()
	{
		clienteCenso = null;
		try
		{
			clienteCenso = new ClienteCensoBLL().ObtenerClienteCensoPor(clienteCensoId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty() || localException == null)
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al cargar el cliente censo: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al cargar el cliente censo: " + localException.getMessage());
	    	} 
		}
		
		if(clienteCenso == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el clinete censo", 1);
		}
		else
		{
			tvLatitud.setText(String.valueOf(clienteCenso.get_latitud()));
    		tvLongitud.setText(String.valueOf(clienteCenso.get_longitud()));
    		tvCodigo.setText(String.valueOf(clienteCenso.get_codigo()));
    		etReferencia.setText(String.valueOf(clienteCenso.get_referencia()));
    		etContacto.setText(String.valueOf(clienteCenso.get_contacto()));
		}
	}
	
	private void ObtenerClienteVender()
	{
		clienteVender = null;
		try
		{
			clienteVender = new ClientePreventaBLL().ObtenerClientePreventaPor(clienteVenderId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty() || localException == null)
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente vender: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente vender: " + localException.getMessage());
	    	} 
		}
		
		if(clienteVender == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el clinete vender", 1);
		}
	}
		
	private void CargarParametroGeneral()
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
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el parametroGeneral: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el parametroGeneral: " + localException.getMessage());
			} 
		}
		
		if(parametroGeneral == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo obtener los parametros generales.",1);
			return;
		}
	}
	
	private void CargarMotivosEliminacion()
	{
		ArrayList<MotivoEliminacionMatch> listadoMotivosEliminacion = new ArrayList<MotivoEliminacionMatch>();
		try
	    {
			listadoMotivosEliminacion = new MotivoEliminacionMatchBLL().ObtenerMotivoseliminacionMatch();
		}
	    catch (Exception localException)
	    {
	    	if (localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los motivos de eliminacion del match: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los motivos de eliminacion del match: " + localException.getMessage());
	    	}
	    }
			
		if (listadoMotivosEliminacion != null && listadoMotivosEliminacion.size() > 0)
	    {
	        ArrayAdapter<MotivoEliminacionMatch> localArrayAdapter = new ArrayAdapter<MotivoEliminacionMatch>(this,R.layout.disenio_spinner,listadoMotivosEliminacion);
	        spnMotivoEliminarCliente.setAdapter(localArrayAdapter);
	    }
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron motivos de eliminacion del match que desplegar.", 1);
		    return;
		}
	}
	
	private void CargarTipoNegocio()
	{
		ArrayList<TipoNegocio> listadoTipoNegocio = new ArrayList<TipoNegocio>();
		try
	    {
			listadoTipoNegocio = new TipoNegocioBLL().ObtenerTiposNegocio();
	    }
	    catch (Exception localException)
	    {
	    	if (localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los tipos de negocios: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los tipos de negocios: " + localException.getMessage());
	    	}
	    }
			
		if (listadoTipoNegocio !=null && listadoTipoNegocio.size() > 0)
	    {
	        ArrayAdapter<TipoNegocio> localArrayAdapter = new ArrayAdapter<TipoNegocio>(this,R.layout.disenio_spinner,listadoTipoNegocio);
	        spnTipoNegocio.setAdapter(localArrayAdapter);
	    }
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron tipos de negocio que desplegar.", 1);
		    return;
		}
	}
	
	private void CargarZonas()
	{
		ArrayList<Zona> listadoZona = new ArrayList<Zona>();
		try
	    {
			listadoZona = new ZonaBLL().ObtenerZonas();
	    }
	    catch (Exception localException)
	    {
	    	if (localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener las zonas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener las zonas: " + localException.getMessage());
	    	}
	    }
			
		if (listadoZona!=null && listadoZona.size() > 0)
	    {
	        ArrayAdapter<Zona> localArrayAdapter = new ArrayAdapter<Zona>(this,R.layout.disenio_spinner,listadoZona);
	        spnZona.setAdapter(localArrayAdapter);
	    }
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron zonas que desplegar.", 1);
		    return;
		}
	}
	
	private void CargarRutaCliente()
	{
		ArrayList<ApkRutaCliente> listadoApkRutaCliente = new ArrayList<ApkRutaCliente>();
		try
	    {
			listadoApkRutaCliente = new ApkRutaClienteBLL().ObtenerApksRutaCliente();
	    }
	    catch (Exception localException)
	    {
	    	if (localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener las apksRutaCliente: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener las apkRutasCliente: " + localException.getMessage());
	    	}
	    }
			
		if (listadoApkRutaCliente !=null && listadoApkRutaCliente.size() > 0)
	    {
	        ArrayAdapter<ApkRutaCliente> localArrayAdapter = new ArrayAdapter<ApkRutaCliente>(this,R.layout.disenio_spinner,listadoApkRutaCliente);
	        spnRuta.setAdapter(localArrayAdapter);
	    }
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron apkRutasCliente que desplegar.", 1);
		    return;
		}
	}

	private void CargarMercados()
	{
		ArrayList<Mercado> listadoMercado = new ArrayList<Mercado>();
	    
	    try
	    {
	    	listadoMercado = new MercadoBLL().ObtenerMercados();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los mercados: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los mercados: " + localException.getMessage());
	    	}    		
	    }
	    
	    if(listadoMercado.size() > 0)
	    {
	    	ArrayAdapter<Mercado> localArrayAdapter = new ArrayAdapter<Mercado>(this,R.layout.disenio_spinner,listadoMercado);
	    	spnMercado.setAdapter(localArrayAdapter);
	    }
	    else
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron mercados que desplegar.", 1);
		    return;
	    } 
	}

	private void ObtenerCoordenadasCreador()
	{
		Ubicacion localUbicacion =null;
	    try
	    {
	    	localUbicacion = new Ubicacion(this);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	        {
	    		myLog.InsertarLog("App",this.toString(), 1, "Error al inicializar la ubicacion del dispositivo: vacio");
	        }
	        else
	        {
	        	myLog.InsertarLog("App",this.toString(), 1, "Error al inicializar la ubicacion del dispositivo: " + localException.getMessage());
	        }
	    }
	    
	    if (localUbicacion == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo inicializar la ubicacion del dispositivo. ", 1);
	    	return;
	    }
	      
	    if (localUbicacion.canGetLocation()) 
	    {
	    	latitudCreador = localUbicacion.getLatitude();
	    	longitudCreador = localUbicacion.getLongitude();
	    }
	    else
	    {
	    	latitudCreador = 0;
		    longitudCreador = 0;
	    }
	}
	
	private void EliminarCliente()
	{
		int eliminado = 0;
		motivoEliminacionId = ((MotivoEliminacionMatch)spnMotivoEliminarCliente.getSelectedItem()).get_motivoId();
		try
		{
			eliminado = new ClienteCensoBLL().ModificarClienteCensoEstado(clienteCensoId,4,"","","",motivoEliminacionId);//codigo 4=Eliminacion
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	        {
	    		myLog.InsertarLog("App",this.toString(), 1, "Error al modificar el estado de eliminacion del cliente censo: vacio");
	        }
	        else
	        {
	        	myLog.InsertarLog("App",this.toString(), 1, "Error al modificar el estado de eliminacion del cliente censo: " + localException.getMessage());
	        }
		}
		
		if(eliminado == 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el estado de eliminacion del cliente censo", 1);
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "Cliente eliminado.", 1);
			
			GrabarUltimaCoordenada(clienteCenso.get_clienteId(), clienteCenso.get_latitud(), clienteCenso.get_longitud());
			
			if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
			{
				EliminarClientesCenso();
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encuentra conectado a internet, sincronicelo mas tarde.", 1);
				MostrarPantallaMenuVendedor();
			}
		}
	}
	
	private void EliminarClientesCenso()
	{
		pdEliminarClienteCenso = new ProgressDialog(this);
		pdEliminarClienteCenso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEliminarClienteCenso.setMessage("Elimnando cliente censo ...");
		pdEliminarClienteCenso.setCancelable(false);
		pdEliminarClienteCenso.setCanceledOnTouchOutside(false);
	    
		WSDeleteClientesCenso localWSDeleteClientesCenso = new WSDeleteClientesCenso();
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
			localSoapObject.addProperty("codigo",clienteCenso.get_codigo());
			localSoapObject.addProperty("motivoId",motivoEliminacionId);
			
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
						l = new ClienteCensoBLL().ModificarClienteCensoincronizacion(clienteCenso.get_id(), true);
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
						MostrarPantallaMenuVendedor();
					}
				}	
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSDeleteClienteCenso no se ejecuto correctamente. ", 1);
			} 
		}
	}
	
	private void InsertarCliente()
	{
		if(ValidarInsercion())
		{
			clienteCenso.set_creadorId(loginEmpleado.get_empleadoId());
			clienteCenso.set_diaCreacion(loginEmpleado.get_dia());
			clienteCenso.set_mesCreacion(loginEmpleado.get_mes());
			clienteCenso.set_anioCreacion(loginEmpleado.get_anio());
			
			long modificado = 0;
			try
			{
				modificado = new ClienteCensoBLL().ModificarClienteCenso(clienteCenso.get_id(),clienteCenso.get_codigo(),clienteCenso.get_referencia(), 
																		clienteCenso.get_tipoNegocioIdVender(), clienteCenso.get_tipoNegocio(),
																		clienteCenso.get_contacto(),clienteCenso.get_latitud(),
																		clienteCenso.get_longitud(), clienteCenso.get_nombres(), 
																		clienteCenso.get_paterno(),clienteCenso.get_creadorId(),
																		latitudCreador,longitudCreador,clienteCenso.get_zonaId(), 
																		clienteCenso.get_rutaId(),clienteCenso.get_diaId(), 
																		clienteCenso.get_mercadoId(),clienteCenso.get_diaCreacion(),
																		clienteCenso.get_mesCreacion(),clienteCenso.get_anioCreacion(),
																		1,0,false);//1:insertar		
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
		    	{
		            myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cliente censo: vacio");
		    	}
		    	else
		    	{
		    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cliente censo: " + localException.getMessage());
		    	} 	
			}
			if(modificado == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el cliente censo.", 1);
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "Cliente insertado.", 1);
				
				GrabarUltimaCoordenada(clienteCenso.get_clienteId(), clienteCenso.get_latitud(), clienteCenso.get_longitud());
				
				if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
				{
					InsertarClienteCenso();
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se encuentra conectado a internet, sincronicelo mas tarde.", 1);
					MostrarPantallaMenuVendedor();
				}
			}
		}
	}
	
	private boolean ValidarInsercion()
	{
		boolean validado = false;
		
		ObtenerCoordenadasCreador();
		
		if(!etNombres.getText().toString().isEmpty())
		{
			clienteCenso.set_nombres(etNombres.getText().toString());
			if(!etPaterno.getText().toString().isEmpty())
			{
				clienteCenso.set_paterno(etPaterno.getText().toString());
				if(((TipoNegocio)spnTipoNegocio.getSelectedItem()).get_tipoNegocioId() != 0)
				{
					clienteCenso.set_tipoNegocioIdVender(((TipoNegocio)spnTipoNegocio.getSelectedItem()).get_tipoNegocioId());
					if(((Zona)spnZona.getSelectedItem()).get_zonaId() != 0)
					{
						clienteCenso.set_zonaId(((Zona)spnZona.getSelectedItem()).get_zonaId());
						if(((ApkRutaCliente)spnRuta.getSelectedItem()).get_rutaId() != 0)
						{
							clienteCenso.set_rutaId(((ApkRutaCliente)spnRuta.getSelectedItem()).get_rutaId());
							clienteCenso.set_diaId(((ApkRutaCliente)spnRuta.getSelectedItem()).get_diaId());
							if(parametroGeneral.is_mercadoRequerido())
							{
								if(((Mercado)spnMercado.getSelectedItem()).get_mercadoId() != 0)
								{
									clienteCenso.set_mercadoId(((Mercado)spnMercado.getSelectedItem()).get_mercadoId());
									clienteCenso.set_referencia(etReferencia.getText().toString());
									clienteCenso.set_contacto(etContacto.getText().toString());
									validado = true;
								}
								else
								{
									theUtilidades.MostrarMensaje(getApplicationContext(), "El mercado es requerido.", 1);
								}
							}
							else
							{
								clienteCenso.set_mercadoId(0);
								clienteCenso.set_referencia(etReferencia.getText().toString());
								clienteCenso.set_contacto(etContacto.getText().toString());
								validado = true;
							}
						}
						else
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "La ruta es requerida.", 1);
						}
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "La zona es requerida.", 1);
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "El tipo de negocio es requerido.", 1);
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El apellido paterno es requerido.", 1);
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "El nombre es requerido.", 1);
		}
		
		return validado;
	}
	
	private void InsertarClienteCenso()
	{
		pdInsertarClienteCenso = new ProgressDialog(this);
		pdInsertarClienteCenso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdInsertarClienteCenso.setMessage("Insertando cliente censo ...");
		pdInsertarClienteCenso.setCancelable(false);
		pdInsertarClienteCenso.setCanceledOnTouchOutside(false);
	    
		WSInsertClientesCenso localWSInsertClientesCenso = new WSInsertClientesCenso();
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
			localSoapObject.addProperty("nombres",clienteCenso.get_nombres());
			localSoapObject.addProperty("paterno",clienteCenso.get_paterno());
			localSoapObject.addProperty("codigo",clienteCenso.get_codigo());
			localSoapObject.addProperty("referencia",clienteCenso.get_referencia());
			localSoapObject.addProperty("tipoNegocioId",Integer.valueOf(clienteCenso.get_tipoNegocioIdVender()));
			localSoapObject.addProperty("contacto",clienteCenso.get_contacto());
			localSoapObject.addProperty("latitud",String.valueOf(clienteCenso.get_latitud()));
			localSoapObject.addProperty("longitud",String.valueOf(clienteCenso.get_longitud()));
			localSoapObject.addProperty("creadorId",Integer.valueOf(clienteCenso.get_creadorId()));
			localSoapObject.addProperty("latitudCreador",String.valueOf(clienteCenso.get_latitudCreador()));
			localSoapObject.addProperty("longitudCreador",String.valueOf(clienteCenso.get_longitudCreador()));
			localSoapObject.addProperty("zonaId",Integer.valueOf(clienteCenso.get_zonaId()));
			localSoapObject.addProperty("rutaId",Integer.valueOf(clienteCenso.get_rutaId()));
			localSoapObject.addProperty("diaId",Integer.valueOf(clienteCenso.get_diaId()));
			localSoapObject.addProperty("mercadoId",Integer.valueOf(clienteCenso.get_mercadoId()));
			localSoapObject.addProperty("diaCreacion",Integer.valueOf(clienteCenso.get_diaCreacion()));
			localSoapObject.addProperty("mesCreacion",Integer.valueOf(clienteCenso.get_mesCreacion()));
			localSoapObject.addProperty("anioCreacion",Integer.valueOf(clienteCenso.get_anioCreacion()));
			
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
						l = new ClienteCensoBLL().ModificarClienteCensoincronizacion(clienteCenso.get_id(), true);
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
						l = new ClientePreventaBLL().InsertarClientePreventa(intRespuesta,clienteCenso.get_codigo(),clienteCenso.get_tipoNegocioIdVender(),
								clienteCenso.get_nombres() + " " + clienteCenso.get_paterno(),clienteCenso.get_latitud(),clienteCenso.get_longitud(),
								"","","",1,0,1,0,0,clienteCenso.get_rutaId(),"","",false,false,true,true,clienteCenso.get_nombres(),clienteCenso.get_paterno(),
								"","",clienteCenso.get_tipoNegocioIdVender(),clienteCenso.get_zonaId(),"","",clienteCenso.get_diaId(),
								clienteCenso.get_referencia(),0,"",clienteCenso.get_contacto(),0,0,0,"","",0,"","",0,"",false);
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
					MostrarPantallaMenuVendedor();

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
	
	private void EstablecerCoordenadaCenso()
	{
		tvLatitud.setText(String.valueOf(clienteCenso.get_latitud()));
		tvLatitud.setTextColor(Color.CYAN);
		tvLongitud.setText(String.valueOf(clienteCenso.get_longitud()));
		tvLongitud.setTextColor(Color.CYAN);
	}
	
	private void EstablecerCoordenadaVender()
	{
		tvLatitud.setText(String.valueOf(clienteVender.get_latitud()));
		tvLatitud.setTextColor(Color.GREEN);
		tvLongitud.setText(String.valueOf(clienteVender.get_longitud()));
		tvLongitud.setTextColor(Color.GREEN);
	}
	
	private void EstablecerCoordenadaNueva(double lat,double lon)
	{
		tvLatitud.setText(String.valueOf(lat));
		tvLatitud.setTextColor(Color.YELLOW);
		tvLongitud.setText(String.valueOf(lon));
		tvLongitud.setTextColor(Color.YELLOW);
	}
	
	private void EditarCliente()
	{		
		if(etReferencia.getText().length()>0)
		{
			referencia = etReferencia.getText().toString();
		}
		else
		{
			referencia = "";
		}
		
		if(etContacto.getText().length()>0)
		{
			contacto = etContacto.getText().toString();
		}
		else
		{
			contacto = "";
		}
		
		if(rbCenso.isChecked())
		{
			//if(((TipoNegocio)spnTipoNegocio.getSelectedItem()).get_tipoNegocioId()==0)
			//{
			//	theUtilidades.MostrarMensaje(getApplicationContext(), "El tipo de negocio es Requerido", 1);
			//	return;
			//}
			//else
			//{
				//clienteCenso.set_tipoNegocioIdVender(((TipoNegocio)spnTipoNegocio.getSelectedItem()).get_tipoNegocioId());
				int l = 0;
				
				try
				{
					l = new ClienteCensoBLL().ModificarClienteCenso(clienteCenso.get_id(),clienteCenso.get_codigo(),referencia,
							0,"",contacto,clienteCenso.get_latitud(),clienteCenso.get_longitud(),
							"","",0,0,0,0,0,0,0,0,0,0,2,clienteVender.get_clienteId(),false);//estado 2 = Unilever
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
			    	{
			            myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteCenso coordenada censo: vacio");
			    	}
			    	else
			    	{
			    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteCenso coordenada censo: " + localException.getMessage());
			    	} 	
				}
				
				if(l==0)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el cliente del censo.", 1);
					return;
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Cliente modificado.", 1);
					
					GrabarUltimaCoordenada(clienteCenso.get_clienteId(), clienteCenso.get_latitud(), clienteCenso.get_longitud());
					
					if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
					{
						ModificarClienteDesdeCenso();
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se encuentra conectado a internet, sincronicelo mas tarde.", 1);
						MostrarPantallaMenuVendedor();
					}
				}
			//}
		}
		else if(rbVender.isChecked())
		{
			int l = 0;
			
			try
			{
				l = new ClienteCensoBLL().ModificarClienteCensoEstado(clienteCensoId, 3, clienteCenso.get_codigo(),referencia,contacto,0);//Estado=3 Vender
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
		    	{
		            myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estado del cliente censo coordenada vender: vacio");
		    	}
		    	else
		    	{
		    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estado del cliente censo coordenada vender: " + localException.getMessage());
		    	} 	
			}
			
			if(l==0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el estado del cliente censo.", 1);
				return;
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "Cliente modificado.", 1);
				
				GrabarUltimaCoordenada(clienteCenso.get_clienteId(), clienteCenso.get_latitud(), clienteCenso.get_longitud());
				
				if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
				{
					ModificarClienteDesdeVender();
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se encuentra conectado a internet, sincronicelo mas tarde.", 1);
					MostrarPantallaMenuVendedor();
				}
			}
		}
		else if(rbNuevo.isChecked())
		{
			//if(((TipoNegocio)spnTipoNegocio.getSelectedItem()).get_tipoNegocioId()==0)
			//{
			//	theUtilidades.MostrarMensaje(getApplicationContext(), "El tipo de negocio es Requerido", 1);
			//	return;
			//}
			//else
			//{
			//	clienteCenso.set_tipoNegocioIdVender(((TipoNegocio)spnTipoNegocio.getSelectedItem()).get_tipoNegocioId());
				if(tvLatitud.getText().equals("0.0") || tvLongitud.getText().equals("0.0"))
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Debe establecer una coordenada para unificar.", 1);
				}
				else
				{
					latitudNueva = Double.parseDouble(tvLatitud.getText().toString());
					longitudNueva = Double.parseDouble(tvLongitud.getText().toString());
					clienteCenso.set_latitud(latitudNueva);
					clienteCenso.set_longitud(longitudNueva);
					
					int l = 0;
					
					try
					{
						l = new ClienteCensoBLL().ModificarClienteCenso(clienteCenso.get_id(),clienteCenso.get_codigo(),referencia,
								0,"",contacto,clienteCenso.get_latitud(),clienteCenso.get_longitud(),"","",0,0,0,0,0,0,0,0,0,0,5,clienteVender.get_clienteId(),
								false);//estado 5 = Unilever nueva coordenada
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
				    	{
				            myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estado del cliente censo coordenada nueva: vacio");
				    	}
				    	else
				    	{
				    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estado del cliente censo coordenada nueva: " + localException.getMessage());
				    	} 	
					}
					
					if(l==0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el estado del cliente censo.", 1);
						return;
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "Cliente modificado.", 1);
						
						GrabarUltimaCoordenada(clienteCenso.get_clienteId(), clienteCenso.get_latitud(),clienteCenso.get_longitud());
						
						if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
						{
							ModificarClienteDesdeCensoCoordenadaNueva();
						}
						else
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "No se encuentra conectado a internet, sincronicelo mas tarde.", 1);
							MostrarPantallaMenuVendedor();
						}
					}
				}
			//}
		}
	}
	
	private void ModificarClienteDesdeCenso()
	{
		pdModificarClienteCenso = new ProgressDialog(this);
		pdModificarClienteCenso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdModificarClienteCenso.setMessage("Modificando cliente censo ...");
		pdModificarClienteCenso.setCancelable(false);
	    
		WSModificarClienteCenso localWSModificarClienteCenso = new WSModificarClienteCenso();
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
			localSoapObject.addProperty("clienteId",clienteVender.get_clienteId());
			localSoapObject.addProperty("codigo",clienteCenso.get_codigo());
			localSoapObject.addProperty("referencia",referencia);
			localSoapObject.addProperty("contacto",contacto);
			localSoapObject.addProperty("latitud",String.valueOf(clienteCenso.get_latitud()));
			localSoapObject.addProperty("longitud",String.valueOf(clienteCenso.get_longitud()));
			
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
						l = new ClienteCensoBLL().ModificarClienteCensoincronizacion(clienteCenso.get_id(), true);
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
						l = new ClientePreventaBLL().ModificarClienteDatosCenso(clienteVender.get_clienteId(),clienteCenso.get_codigo(),
								clienteCenso.get_referencia(),clienteCenso.get_tipoNegocioIdVender(),clienteCenso.get_contacto(),
								clienteCenso.get_latitud(),clienteCenso.get_longitud());
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
					MostrarPantallaMenuVendedor();

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
	
	private void ModificarClienteDesdeVender()
	{
		pdModificarClienteVender = new ProgressDialog(this);
		pdModificarClienteVender.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdModificarClienteVender.setMessage("Modificando cliente vender ...");
		pdModificarClienteVender.setCancelable(false);
		pdModificarClienteVender.setCanceledOnTouchOutside(false);
	    
		WSModificarClienteVender localWSModificarClienteVender = new WSModificarClienteVender();
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
			localSoapObject.addProperty("clienteId",clienteVender.get_clienteId());
			localSoapObject.addProperty("codigo",clienteCenso.get_codigo());
			localSoapObject.addProperty("referencia",referencia);
			localSoapObject.addProperty("contacto",contacto);
			
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
						l = new ClienteCensoBLL().ModificarClienteCensoincronizacion(clienteCenso.get_id(), true);
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
					MostrarPantallaMenuVendedor();

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

	private void ModificarClienteDesdeCensoCoordenadaNueva()
	{
		pdModificarClienteCensoCoordenada = new ProgressDialog(this);
		pdModificarClienteCensoCoordenada.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdModificarClienteCensoCoordenada.setMessage("Modificando coordenada cliente censo ...");
		pdModificarClienteCensoCoordenada.setCancelable(false);
		pdModificarClienteCensoCoordenada.setCanceledOnTouchOutside(false);

		WSModificarClienteCensoCoordenada localWSModificarClienteCensoCoordenada = new WSModificarClienteCensoCoordenada();
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
			localSoapObject.addProperty("clienteId",clienteVender.get_clienteId());
			localSoapObject.addProperty("codigo",clienteCenso.get_codigo());
			localSoapObject.addProperty("referencia",referencia);
			localSoapObject.addProperty("contacto",contacto);
			localSoapObject.addProperty("latitud",String.valueOf(latitudNueva));
			localSoapObject.addProperty("longitud",String.valueOf(longitudNueva));
			
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
						l = new ClienteCensoBLL().ModificarClienteCensoincronizacion(clienteCenso.get_id(), true);
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
						l = new ClientePreventaBLL().ModificarClienteDatosCenso(clienteVender.get_clienteId(),clienteCenso.get_codigo(),
								clienteCenso.get_referencia(),clienteCenso.get_tipoNegocioIdVender(),clienteCenso.get_contacto(),
								clienteCenso.get_latitud(),clienteCenso.get_longitud());
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
					MostrarPantallaMenuVendedor();

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
	
	private void GrabarUltimaCoordenada(int clienteId,double latitud,double longitud)
	{
		boolean borrado = false;
		try
		{
			borrado = new UltimaCoordenadaBLL().BorrarUltimasCoordenada();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ultimas coordenadas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ultimas coordenadas: " + localException.getMessage());
	    	}
		}
		
		if(borrado)
		{
			long l = 0;
			try
			{
				l = new UltimaCoordenadaBLL().InsertarUltimaCoordenada(clienteId, latitud, longitud);
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
		    	{
		            myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ultima coordenada: vacio");
		    	}
		    	else
		    	{
		    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ultima coordenada: " + localException.getMessage());
		    	}
			}
			
			if(l==0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la ultima coordenada.", 1);
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo eliminar las ultimas coordenadas.", 1);
		}
	}
	
	private void MostrarMapaNuevaCoordenada()
	{
		Intent intent = new Intent(this, Censistaunificacionclientesmapacoordenada.class);
		intent.putExtra("ClienteCensoId",clienteCensoId);
		intent.putExtra("ClienteVenderId",clienteVenderId);
		startActivity(intent);
	}

	private void MostrarPantallaMenuVendedor()
	{
		Intent intent = null;
		if(origen.equals("Ventas"))
		{
			intent = new Intent(this, Menuvendedor.class);
		}
		else
		{
			intent = new Intent(this, Menucensista.class);
			intent.putExtra("Origen", "Menuvendedor");
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	@Override
	public void onBackPressed() 
	{
		MostrarPantallaMenuVendedor();
	}
}
