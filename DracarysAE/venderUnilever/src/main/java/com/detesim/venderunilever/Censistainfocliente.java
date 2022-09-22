package com.detesim.venderunilever;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import BLL.ClienteBLL;
import BLL.DiaSemanaBLL;
import BLL.ExpedidoBLL;
import BLL.MercadoBLL;
import BLL.MyLogBLL;
import BLL.RutaBLL;
import BLL.TipoCalleBLL;
import BLL.TipoNegocioBLL;
import BLL.TipoNitBLL;
import BLL.ZonaBLL;
import Clases.Cliente;
import Clases.DiaSemana;
import Clases.Expedido;
import Clases.Fecha;
import Clases.LoginEmpleado;
import Clases.Mercado;
import Clases.Ruta;
import Clases.TipoCalle;
import Clases.TipoNegocio;
import Clases.TipoNit;
import Clases.Ubicacion;
import Clases.Zona;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class Censistainfocliente extends Activity implements OnClickListener
{
	LinearLayout llCensistaInfoCliente;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	MyLogBLL myLog = new MyLogBLL();
	
	LoginEmpleado loginEmpleado;
	double latitudMapa = 0;
	double longitudMapa = 0;
	double latitudCreador = 0;
	double longitudCreador = 0;
	int newAndroidRowId = 0;
	Cliente localCliente;
	String Origen;
	
	EditText etClienteNombres;
	EditText etClientePaterno;
	EditText etClienteMaterno;
	EditText etClienteApellidoCasada;
	CheckBox cbClienteCi;
	EditText etClienteCi;
	Spinner spnClienteExpedido;
	CheckBox cbClienteCelular;
	EditText etClienteCelular;
	CheckBox cbClienteTelefono;
	EditText etClienteTelefono;
	Spinner spnClienteDireccion;
	EditText etClienteDireccion;
	EditText etClienteNumero;
	EditText etClienteEdificio;
	EditText etClientePisoEdificio;
	EditText etClienteNumeroEdificio;
	EditText etClienteReferencia;
	EditText etClienteManzano;
	EditText etClienteUv;
	Spinner spnClienteEntreCalle;
	EditText etClienteEntreCalle;
	Spinner spnClienteYCalle;
	EditText etClienteYCalle;
	Spinner spnTiposNit;
	EditText etClienteNombreFacturar;
	EditText etClienteNit;
	EditText etClienteRazonSocial;
	EditText etClienteContacto;
	EditText etClienteEmail;
	EditText etClienteWebsite;
	Spinner spnClienteTipoNegocio;
	Spinner spnClienteZona;
	Spinner spnClienteRuta;
	Spinner spnClienteRutaDia;
	Spinner spnMercado;
	Button btnClienteInsertarCliente;
	ProgressDialog pdEsperaAltaCliente;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_censistainfocliente);
		
		llCensistaInfoCliente = (LinearLayout)findViewById(R.id.CensistaInfoClienteLayout);
		etClienteNombres = (EditText)findViewById(R.id.etClienteNombres);
		etClientePaterno = (EditText)findViewById(R.id.etClientePaterno);
		etClienteMaterno = (EditText)findViewById(R.id.etClienteMaterno);
		etClienteApellidoCasada = (EditText)findViewById(R.id.etClienteApellidoCasada);
		cbClienteCi = (CheckBox)findViewById(R.id.cbClienteCI);
		cbClienteCi.setChecked(true);
		etClienteCi = (EditText)findViewById(R.id.etClienteCi);
		spnClienteExpedido = (Spinner)findViewById(R.id.spnClienteExpedido);
		cbClienteCelular = (CheckBox)findViewById(R.id.cbClienteCelular);
		cbClienteCelular.setChecked(true);
		etClienteCelular = (EditText)findViewById(R.id.etClienteCelular);
		cbClienteTelefono = (CheckBox)findViewById(R.id.cbClienteTelefono);
		cbClienteTelefono.setChecked(true);
		etClienteTelefono = (EditText)findViewById(R.id.etClienteTelefono);
		spnClienteDireccion = (Spinner)findViewById(R.id.spnClienteTipoCalleDireccion);
		etClienteDireccion = (EditText)findViewById(R.id.etClienteDireccion);
		etClienteNumero = (EditText)findViewById(R.id.etClienteNumero);
		etClienteEdificio = (EditText)findViewById(R.id.etClienteEdificio);
		etClientePisoEdificio = (EditText)findViewById(R.id.etClientePisoEdificio);
		etClienteNumeroEdificio = (EditText)findViewById(R.id.etClienteNumeroEdificio);
		etClienteReferencia = (EditText)findViewById(R.id.etClienteReferencia);
		etClienteManzano = (EditText)findViewById(R.id.etClienteManzano);
		etClienteUv = (EditText)findViewById(R.id.etClienteUv);
		spnClienteEntreCalle = (Spinner)findViewById(R.id.spnClienteEntreCalle);
		etClienteEntreCalle = (EditText)findViewById(R.id.etClienteEntreCalle);
		spnClienteYCalle = (Spinner)findViewById(R.id.spnClienteYCalle);
		etClienteYCalle = (EditText)findViewById(R.id.etClienteYCalle);
		spnTiposNit = (Spinner)findViewById(R.id.spnCencistaInfoClienteTipoNit);
		etClienteNombreFacturar = (EditText)findViewById(R.id.etClienteNombreFacturar);
		etClienteNit = (EditText)findViewById(R.id.etClienteNit);
		etClienteRazonSocial = (EditText)findViewById(R.id.etClienteRazonSocial);
		etClienteContacto = (EditText)findViewById(R.id.etClienteContacto);
		etClienteEmail = (EditText)findViewById(R.id.etClienteEmail);
		etClienteWebsite = (EditText)findViewById(R.id.etClienteWebsite);
		spnClienteTipoNegocio = (Spinner)findViewById(R.id.spnClienteTipoNegocio);
		spnClienteZona = (Spinner)findViewById(R.id.spnClienteZona);
		spnClienteRuta = (Spinner)findViewById(R.id.spnClienteRuta);
		spnClienteRutaDia = (Spinner)findViewById(R.id.spnClienteDiaRuta);
		spnMercado = (Spinner)findViewById(R.id.spnCencistaMercado);
		btnClienteInsertarCliente = (Button)findViewById(R.id.btnClienteInsertarCliente);
		btnClienteInsertarCliente.setOnClickListener(this);
		
		llCensistaInfoCliente.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
		Origen = "";
		Bundle localBundle = getIntent().getExtras();
	    latitudMapa = Double.parseDouble(localBundle.getString("Latitud"));
	    longitudMapa = Double.parseDouble(localBundle.getString("Longitud"));
	    Origen = localBundle.getString("Origen");
		
		if(latitudMapa == 0 || longitudMapa == 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las coordenadas del cliente.", 2);
	    	return;
	    }
		
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
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los datos del loginEmpleado: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los datos del loginEmpleado: " + localException.getMessage());
	    	} 
	    }
	      
	    if (loginEmpleado == null)
	    {
	        this.theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los datos del loginEmpleado del supervisor. ", 2);
	        return;
	    }
	    
	    CargarInformacion();
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.btnClienteInsertarCliente:
			ValidarInsercionCliente();
			break;
		}
	}
	
	private void CargarInformacion()
	{
	    CargarTipoNegocio();
	    CargarZonas();
	    CargarRutas();
	    CargarExpedidos();
	    CargarTipoCalle();
	    CargarTipoCalle1();
	    CargarTipoCalle2();
	    CargarDiasSemana();
	    CargarMercados();
	    CargarTiposNit();
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
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los tipos de negocios: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los tipos de negocios: " + localException.getMessage());
	    	}    		
	    }
	    
	    if(listadoTipoNegocio.size() > 0)
	    {
	    	ArrayAdapter<TipoNegocio> localArrayAdapter = new ArrayAdapter<TipoNegocio>(this,R.layout.disenio_spinner,listadoTipoNegocio);
	        spnClienteTipoNegocio.setAdapter(localArrayAdapter);
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
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener las zonas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener las zonas: " + localException.getMessage());
	    	}    		
	    }
	    
	    if(listadoZona.size() > 0)
	    {
	    	ArrayAdapter<Zona> localArrayAdapter = new ArrayAdapter<Zona>(this,R.layout.disenio_spinner,listadoZona);
	        spnClienteZona.setAdapter(localArrayAdapter);
	    }
	    else
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron zonas que desplegar.", 1);
		    return;
	    } 
	}

	private void CargarRutas()
	{
		ArrayList<Ruta> listadoRuta = new ArrayList<Ruta>();
	    
	    try
	    {
	    	listadoRuta = new RutaBLL().ObtenerRutas();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener las rutas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener las rutas: " + localException.getMessage());
	    	}    		
	    }
	    
	    if(listadoRuta.size() > 0)
	    {
	    	ArrayAdapter<Ruta> localArrayAdapter = new ArrayAdapter<Ruta>(this,R.layout.disenio_spinner,listadoRuta);
	        spnClienteRuta.setAdapter(localArrayAdapter);
	    }
	    else
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron rutas que desplegar.", 1);
		    return;
	    } 
	  }

	private void CargarExpedidos()
	{
		ArrayList<Expedido> listadoExpedido = new ArrayList<Expedido>();
	    
	    try
	    {
	    	listadoExpedido = new ExpedidoBLL().ObtenerExpedidos();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los expedidos: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los expedidos: " + localException.getMessage());
	    	}    		
	    }
	    
	    if(listadoExpedido.size() > 0)
	    {
	    	ArrayAdapter<Expedido> localArrayAdapter = new ArrayAdapter<Expedido>(this,R.layout.disenio_spinner,listadoExpedido);
	        spnClienteExpedido.setAdapter(localArrayAdapter);
	    }
	    else
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron expedidos que desplegar.", 1);
		    return;
	    } 
	  }

	private void CargarTipoCalle()
	{
		ArrayList<TipoCalle> listadoTipoCalle = new ArrayList<TipoCalle>();
	    
	    try
	    {
	    	listadoTipoCalle = new TipoCalleBLL().ObtenerTiposCalle();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los tipos de calle: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los tipos de calle: " + localException.getMessage());
	    	}    		
	    }
	    
	    if(listadoTipoCalle.size() > 0)
	    {
	    	ArrayAdapter<TipoCalle> localArrayAdapter = new ArrayAdapter<TipoCalle>(this,R.layout.disenio_spinner,listadoTipoCalle);
	        spnClienteDireccion.setAdapter(localArrayAdapter);
	    }
	    else
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron tipos de calle que desplegar.", 1);
		    return;
	    } 
	  }

	private void CargarTipoCalle1()
	{
		ArrayList<TipoCalle> listadoTipoCalle = new ArrayList<TipoCalle>();
	    
	    try
	    {
	    	listadoTipoCalle = new TipoCalleBLL().ObtenerTiposCalle();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los tipos de entre calle: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los tipos de entre calle: " + localException.getMessage());
	    	}    		
	    }
	    
	    if(listadoTipoCalle.size() > 0)
	    {
	    	ArrayAdapter<TipoCalle> localArrayAdapter = new ArrayAdapter<TipoCalle>(this,R.layout.disenio_spinner,listadoTipoCalle);
	        spnClienteEntreCalle.setAdapter(localArrayAdapter);
	    }
	    else
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron tipos de entre calle que desplegar.", 1);
		    return;
	    } 
	  }

	private void CargarTipoCalle2()
	{
		ArrayList<TipoCalle> listadoTipoCalle = new ArrayList<TipoCalle>();
	    
	    try
	    {
	    	listadoTipoCalle = new TipoCalleBLL().ObtenerTiposCalle();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los tipos de y calle: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los tipos de y calle: " + localException.getMessage());
	    	}    		
	    }
	    
	    if(listadoTipoCalle.size() > 0)
	    {
	    	ArrayAdapter<TipoCalle> localArrayAdapter = new ArrayAdapter<TipoCalle>(this,R.layout.disenio_spinner,listadoTipoCalle);
	        spnClienteYCalle.setAdapter(localArrayAdapter);
	    }
	    else
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron tipos de y calle que desplegar.", 1);
		    return;
	    } 
	}

	private void CargarDiasSemana()
	{
		ArrayList<DiaSemana> listadoDiaSemana = new ArrayList<DiaSemana>();
	    
	    try
	    {
	    	listadoDiaSemana = new DiaSemanaBLL().ObtenerDiasSemana();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los dias de semana: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los dias de semana: " + localException.getMessage());
	    	}    		
	    }
	    
	    if(listadoDiaSemana.size() > 0)
	    {
	    	ArrayAdapter<DiaSemana> localArrayAdapter = new ArrayAdapter<DiaSemana>(this,R.layout.disenio_spinner,listadoDiaSemana);
	        spnClienteRutaDia.setAdapter(localArrayAdapter);
	    }
	    else
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron dias de ruta que desplegar.", 1);
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
	
	private void ValidarInsercionCliente()
	{
		Ubicacion localUbicacion;
		
	    if (!ValidarIngresoDatos())
	    {
	    	return;
	    }
	    
	    localUbicacion = null;
	    
	    try 
	    {
	    	localUbicacion = new Ubicacion(this);
		} 
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al inicilizar la ubicacionGPS: vacio");
    		}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error el inicilizar la ubicacionGPS: " + localException.getMessage());
	    	} 
		}
	    	
	   	if(localUbicacion == null)
	   	{
	   		theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo encontrar la localizacion GPS/RED", 2);
	   		return;
	   	}
	    	
	   	if(localUbicacion.canGetLocation())
	   	{
	   		latitudCreador = localUbicacion.getLatitude();
	   		longitudCreador = localUbicacion.getLongitude();
	   		
	   		InsertarClienteDispositivo();
	   		
	   		if(theUtilidades.VerificarConexionInternet(this)) 
	   		{
	   			InsertarCliente();
	        }
	   		else
	   		{
	   			MostrarPantallaFotoCliente();
	   		}
	    }    
    }
		
	public boolean ValidarIngresoDatos()
	{	    
	    Pattern numerosPattern = Pattern.compile("([0-9])*");
	    Pattern letrasPattern = Pattern.compile("([a-zA-ZÒ·ÈÌÛ˙—¡…Õ”⁄\\s*])*");
	    Pattern letrasNunmerosPattern = Pattern.compile("([a-zA-Z0-9/Ò·ÈÌÛ˙—¡…Õ”⁄\\s*])*");
	    Pattern direccionPattern = Pattern.compile("([a-zA-Z0-9Ò·ÈÌÛ˙—¡…Õ”⁄#.\\s*])*");
	    Pattern emailPattern = Patterns.EMAIL_ADDRESS;
	    Pattern websitePattern = Patterns.WEB_URL;
	    
	    if(etClienteNombres.getText().toString().isEmpty())
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El nombre es requerido.", 2);
	    	return false;
	    }
	    else if(etClientePaterno.getText().toString().isEmpty())
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "El apellido paterno es requerido.", 2);
	        return false;
	    }
	    else if(etClienteMaterno.getText().toString().isEmpty())
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "El apellido materno es requerido.", 2);
	        return false;
	    }
	    else if((etClienteCi.getText().toString().isEmpty()) && (cbClienteCi.isChecked()))
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(),"El C.I. es requerido", 2);
	        return false;
	    }
	    else if((etClienteCelular.getText().toString().isEmpty()) && (cbClienteCelular.isChecked()))
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "El celular es requerido", 2);
	        return false;
	    }
	    else if((etClienteTelefono.getText().toString().isEmpty()) && (cbClienteTelefono.isChecked()))
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "El telefono es requerido", 2);
	        return false;
	    }
	    else if (((TipoCalle)spnClienteDireccion.getSelectedItem()).get_tipoCalleId() == 0)
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(),"El tipo de via de la direccion es requerida", 2);
	        return false;
	    }
	    else if(etClienteDireccion.getText().toString().isEmpty())
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "La direccion es requerida", 2);
	        return false;
	    }
	    else if (etClienteNumero.getText().toString().isEmpty())
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "El numero es requerido", 2);
	        return false;
	    }
	    else if(etClienteReferencia.getText().toString().isEmpty())
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "La referencia es requerida", 2);
	        return false;
	    }
	    else if(((TipoCalle)spnClienteEntreCalle.getSelectedItem()).get_tipoCalleId() == 0)
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "El tipo de via de entre calle es requerida", 2);
	        return false;
	    }
	    else if(etClienteEntreCalle.getText().toString().isEmpty())
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "El campo entre calle es requerido", 2);
	        return false;
	    }
	    else if(((TipoCalle)spnClienteYCalle.getSelectedItem()).get_tipoCalleId() == 0)
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "El tipo de via de y calle es requerida", 2);
	        return false;
	    }
	    else if(etClienteYCalle.getText().toString().isEmpty())
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "El campo y calle es requerido", 2);
	        return false;
	    }
	    else if(etClienteNombreFacturar.getText().toString().isEmpty())
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "El nombre factura es requerido", 2);
	        return false;
	    }
	    else if(etClienteNit.getText().toString().isEmpty())
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "El nit es requerido", 2);
	        return false;
	    }
	    else if(((TipoNegocio)spnClienteTipoNegocio.getSelectedItem()).get_tipoNegocioId() == 0)
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "El tipo negocio es requerido", 2);
	        return false;
	    }
	    else if(((Zona)spnClienteZona.getSelectedItem()).get_zonaId() == 0)
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "La zona es requerida", 2);
	        return false;
	    }
	    else if(((Ruta)spnClienteRuta.getSelectedItem()).get_rutaId() == 0)
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "La ruta es requerida", 2);
	        return false;
	    }
	    else if(((DiaSemana)this.spnClienteRutaDia.getSelectedItem()).get_diaSemanaId() == 0)
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "El dia de ruta es requerido", 2);
	        return false;
	    }
	    else if((!etClienteNombres.getText().toString().isEmpty()) && (!letrasPattern.matcher(etClienteNombres.getText().toString()).matches()))
	    {        
	        this.theUtilidades.MostrarMensaje(getApplicationContext(), "Formato de nombres incorrecto.", 2);
	        return false;
	    }
	    else if((!etClientePaterno.getText().toString().isEmpty()) && (!letrasPattern.matcher(etClientePaterno.getText().toString()).matches()))
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "Formato de apellido paterno incorrecto.", 2);
	        return false;
	    }
	    else if((!etClienteMaterno.getText().toString().isEmpty()) && (!letrasPattern.matcher(etClienteMaterno.getText().toString()).matches()))
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "Formato de apellido materno incorrecto.", 2);
	        return false;
	    }
	    else if((!etClienteApellidoCasada.getText().toString().isEmpty()) && (!letrasPattern.matcher(etClienteApellidoCasada.getText().toString()).matches()))
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "Formato de apellido casada incorrecto.", 2);
	        return false;
	    }
	    else if ((!etClienteCi.getText().toString().isEmpty()) && (!numerosPattern.matcher(etClienteCi.getText().toString()).matches()))
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "Formato de carnet de identidad incorrecto.", 2);
	        return false;
	    }
	    else if((!etClienteCelular.getText().toString().isEmpty()) && (!numerosPattern.matcher(etClienteCelular.getText().toString()).matches()))
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "Formato de celular incorrecto.", 2);
	        return false;
	    }
	    else if((!etClienteTelefono.getText().toString().isEmpty()) && (!numerosPattern.matcher(etClienteTelefono.getText().toString()).matches()))
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "Formato de telefono incorrecto.", 2);
	    	return false;
	    }
	    else if((!etClienteDireccion.getText().toString().isEmpty()) && (!direccionPattern.matcher(etClienteDireccion.getText().toString()).matches()))
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "Formato de direccion incorrecto.", 2);
	        return false;
	    }
	    else if((!etClienteNumero.getText().toString().isEmpty()) && (!letrasNunmerosPattern.matcher(etClienteNumero.getText().toString()).matches()))
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "Formato de numero incorrecto.", 2);
	        return false;
	    }
	    else if((!etClienteEdificio.getText().toString().isEmpty()) && (!letrasNunmerosPattern.matcher(etClienteEdificio.getText().toString()).matches()))
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "Formato de nombre edificio incorrecto.", 2);
	        return false;
	    }
	    else if((!etClientePisoEdificio.getText().toString().isEmpty()) && (!letrasNunmerosPattern.matcher(etClientePisoEdificio.getText().toString()).matches()))
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "Formato de piso de edificio incorrecto.", 2);
	        return false;
	    }
	    else if((!etClienteNumeroEdificio.getText().toString().isEmpty()) && (!letrasNunmerosPattern.matcher(etClienteNumeroEdificio.getText().toString()).matches()))
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "Formato de numero de edificio incorrecto.", 2);
	        return false;
	    }
	    else if((!etClienteReferencia.getText().toString().isEmpty()) && (!letrasNunmerosPattern.matcher(etClienteReferencia.getText().toString()).matches()))
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "Formato de referencia incorrecto.", 2);
	        return false;
	    }
	    else if ((!etClienteManzano.getText().toString().isEmpty()) && (!letrasNunmerosPattern.matcher(etClienteManzano.getText().toString()).matches()))
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "Formato de manzano incorrecto.", 2);
	        return false;
	    }
	    else if((!etClienteUv.getText().toString().isEmpty()) && (!letrasNunmerosPattern.matcher(etClienteUv.getText().toString()).matches()))
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "Formato de uv incorrecto.", 2);
	        return false;
	    }
	    else if((!etClienteEntreCalle.getText().toString().isEmpty()) && (!letrasNunmerosPattern.matcher(etClienteEntreCalle.getText().toString()).matches()))
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "Formato de entre calle incorrecto.", 2);
	        return false;
	    }
	    else if((!etClienteYCalle.getText().toString().isEmpty()) && (!letrasNunmerosPattern.matcher(etClienteYCalle.getText().toString()).matches()))
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "Formato de y calle incorrecto.", 2);
	        return false;
	    }
	    else if((!etClienteNombreFacturar.getText().toString().isEmpty()) && (!letrasNunmerosPattern.matcher(etClienteNombreFacturar.getText().toString()).matches()))
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "Formato de nombre factura incorrecto.", 2);
	        return false;
	    }
	    else if((!etClienteNit.getText().toString().isEmpty()) && (!numerosPattern.matcher(etClienteNit.getText().toString()).matches()))
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "Formato de nit incorrecto.", 2);
	        return false;
	    }
	    else if((!etClienteRazonSocial.getText().toString().isEmpty()) && (!letrasNunmerosPattern.matcher(etClienteRazonSocial.getText().toString()).matches()))
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "Formato de razon social incorrecto.", 2);
	        return false;
	    }
	    else if((!etClienteContacto.getText().toString().isEmpty()) && (!letrasPattern.matcher(etClienteContacto.getText().toString()).matches()))
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "Formato de contacto incorrecto.", 2);
	        return false;
	    }
	    else if((!etClienteEmail.getText().toString().isEmpty()) && (!emailPattern.matcher(etClienteEmail.getText().toString()).matches()))
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "Formato de email incorrecto.", 2);
	    	return false;
	    }
	    else if((!etClienteWebsite.getText().toString().isEmpty()) && (!websitePattern.matcher(etClienteWebsite.getText().toString()).matches()))
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "Formato de website incorrecto.", 2);
	    	return false;
	    }
	    else
	    {
	    	return true;
	    }
	}

	private void InsertarClienteDispositivo()
	{
		Fecha fecha = theUtilidades.ObtenerFecha();
	    localCliente = new Cliente();
	    
	    localCliente.set_nombres(etClienteNombres.getText().toString());
	    localCliente.set_paterno(etClientePaterno.getText().toString());
	    localCliente.set_materno(etClienteMaterno.getText().toString());
	    localCliente.set_apellidoCasada(etClienteApellidoCasada.getText().toString());
	    
	    if (cbClienteCi.isChecked())
	    {
	    	localCliente.set_tieneCi(true);
	    	localCliente.set_ci(etClienteCi.getText().toString());
	    }
	    
	    localCliente.set_expedidoId(((Expedido)spnClienteExpedido.getSelectedItem()).get_descripcion());
	    
	    if (cbClienteCelular.isChecked())
        {
           
           localCliente.set_tieneCelular(true);
           localCliente.set_celular(etClienteCelular.getText().toString());
        }
	    
	    if (cbClienteTelefono.isChecked()) 
	    {
	    	localCliente.set_tieneTelefono(true);
	        localCliente.set_telefono(etClienteTelefono.getText().toString());
	    }
	    
	    localCliente.set_tipoCalleId(((TipoCalle)spnClienteDireccion.getSelectedItem()).get_tipoCalleId());
	    localCliente.set_direccion(etClienteDireccion.getText().toString());
	    localCliente.set_numero(etClienteNumero.getText().toString());
	    localCliente.set_edificio(etClienteEdificio.getText().toString());
	    localCliente.set_edificioPiso(etClientePisoEdificio.getText().toString());
	    localCliente.set_edificioNumero(etClienteNumeroEdificio.getText().toString());
	    localCliente.set_referencia(etClienteReferencia.getText().toString());
	    localCliente.set_manzano(etClienteManzano.getText().toString());
	    localCliente.set_uv(etClienteUv.getText().toString());
	    localCliente.set_entreTipoCalle1Id(((TipoCalle)spnClienteEntreCalle.getSelectedItem()).get_tipoCalleId());	              
	    localCliente.set_calle1(etClienteEntreCalle.getText().toString());
	    localCliente.set_entreTipoCalle2Id(((TipoCalle)spnClienteYCalle.getSelectedItem()).get_tipoCalleId());
	    localCliente.set_calle2(etClienteYCalle.getText().toString());
	    localCliente.set_nombreFactura(etClienteNombreFacturar.getText().toString());
	    localCliente.set_tipoNit(((TipoNit)spnTiposNit.getSelectedItem()).get_tipoNit().equals("Seleccione un tipo de NIT")?"":((TipoNit)spnTiposNit.getSelectedItem()).get_tipoNit());
	    localCliente.set_nit(etClienteNit.getText().toString());
	    localCliente.set_razonSocial(etClienteRazonSocial.getText().toString());
	    localCliente.set_contacto(etClienteContacto.getText().toString());
	    localCliente.set_email(etClienteEmail.getText().toString());
	    localCliente.set_paginaWeb(etClienteWebsite.getText().toString());
	    localCliente.set_tipoNegocioId(((TipoNegocio)spnClienteTipoNegocio.getSelectedItem()).get_tipoNegocioId());
	    localCliente.set_zonaId(((Zona)spnClienteZona.getSelectedItem()).get_zonaId());
	    localCliente.set_creadorId(loginEmpleado.get_empleadoId());
	    localCliente.set_dia(fecha.get_dia());
	    localCliente.set_mes(fecha.get_mes());
	    localCliente.set_anio(fecha.get_anio());
	    localCliente.set_hora(fecha.get_hora());
	    localCliente.set_minuto(fecha.get_minuto());
	    localCliente.set_latitud(latitudMapa);
	    localCliente.set_longitud(longitudMapa);
	    localCliente.set_latitudCreador(latitudCreador);
	    localCliente.set_longitudCreador(longitudCreador);
	    localCliente.set_rutaId(((Ruta)spnClienteRuta.getSelectedItem()).get_rutaId());
	    localCliente.set_rutaDiaId(((DiaSemana)spnClienteRutaDia.getSelectedItem()).get_diaSemanaId());
	    localCliente.set_mercadoId(((Mercado)spnMercado.getSelectedItem()).get_mercadoId());
	    localCliente.set_activo(true);
	    localCliente.set_editado(false);
	    localCliente.set_clienteVisita(false);
	    localCliente.set_zonaMercadoId(0);
	    localCliente.set_a(0);
	    localCliente.set_b(0);
	    localCliente.set_c(0);
	    localCliente.set_d(0);
	    localCliente.set_e(0);
	    localCliente.set_f(0);
	    localCliente.set_g(0);
	    localCliente.set_h(0);
	    localCliente.set_i(0);
	    localCliente.set_j(0);
	    localCliente.set_k(0);
	    localCliente.set_l(0);
	    localCliente.set_m(0);
	    localCliente.set_n(0);
	    localCliente.set_o(0);
	    localCliente.set_p(0);
	    localCliente.set_q(0);
	    localCliente.set_r(0);
	    localCliente.set_secuenciaPreventa(0);
	    localCliente.set_provinciaId(0);
	    localCliente.set_precioListaNombreId(0);
	    newAndroidRowId = 0;
	    try
	    {
	    	newAndroidRowId = ((int)new ClienteBLL().InsertarCliente(
	    		localCliente.get_clienteId(),localCliente.get_codigo(),localCliente.get_nombres(),
	    		localCliente.get_paterno(),localCliente.get_materno(),localCliente.get_apellidoCasada(),
	    		localCliente.get_nombreCompleto(),localCliente.is_tieneCi(), localCliente.get_ci(),
	    		localCliente.get_expedidoId(), localCliente.is_tieneCelular(), localCliente.get_celular(), 
	    		localCliente.get_tipoCalleId(), localCliente.get_direccion(), localCliente.get_numero(),
	    		localCliente.get_referencia(),localCliente.get_entreTipoCalle1Id(),localCliente.get_calle1(),
	    		localCliente.get_entreTipoCalle2Id(),localCliente.get_calle2(),localCliente.get_edificio(),
	    		localCliente.get_edificioPiso(),localCliente.get_edificioNumero(),localCliente.get_manzano(),
	    		localCliente.get_uv(),localCliente.get_nombreFactura(),localCliente.get_nit(),
	    		localCliente.get_razonSocial(),localCliente.get_contacto(),localCliente.is_tieneTelefono(),
	    		localCliente.get_telefono(),localCliente.get_paginaWeb(),localCliente.get_email(),
	    		localCliente.get_tipoNegocioId(),localCliente.get_zonaId(),localCliente.get_latitud(),
	    		localCliente.get_longitud(),localCliente.get_creadorId(),localCliente.get_latitudCreador(),
	    		localCliente.get_longitudCreador(),localCliente.get_tipoPagoId(),localCliente.get_diasPago(),
	    		localCliente.get_topeCredito(),localCliente.get_dia(),localCliente.get_mes(),localCliente.get_anio(),
	    		localCliente.get_hora(),localCliente.get_minuto(),false,true,false,localCliente.get_rutaId(),
	    		localCliente.get_rutaDiaId(),localCliente.get_mercadoId(),localCliente.is_activo(),
	    		localCliente.is_editado(),localCliente.get_tatId(),localCliente.get_tipoNit(),localCliente.is_clienteVisita(),
	    		localCliente.get_zonaMercadoId(),localCliente.get_a(),localCliente.get_b(),
	    		localCliente.get_c(),localCliente.get_d(),localCliente.get_e(),localCliente.get_f(),
	    		localCliente.get_g(),localCliente.get_h(),
	    		localCliente.get_i(),localCliente.get_j(),localCliente.get_k(),localCliente.get_l(),
	    		localCliente.get_m(),localCliente.get_n(),
	    		localCliente.get_o(),localCliente.get_p(),localCliente.get_q(),localCliente.get_r(),
	    		localCliente.get_secuenciaPreventa(),localCliente.get_secuenciaVenta(),localCliente.get_provinciaId(),
	    		localCliente.get_precioListaNombreId(),localCliente.get_ruta(),localCliente.get_tipoVisita(),
	    		localCliente.get_zonaVentaId(),localCliente.get_canalRutaId()));
	    }
	    catch (Exception localException)
	    {
	    	if (localException.getMessage().isEmpty()) 
	    	{
	    		myLog.InsertarLog("App",this.toString(), 1, "Error al insertar el cliente en el dispositivo: vacio");
	        } 
	    	else 
	    	{
	    		myLog.InsertarLog("App",this.toString(), 1, "Error al insertar el cliente en el dispositivo: " + localException.getMessage());
	        }
	    }
	    
	    if(newAndroidRowId <= 0)
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el cliente en el dispositivo.", 2);
	        return;
        }    
	    else
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "Cliente insertado en el dispositivo.", 2);
	    }
	}

	private void InsertarCliente()
	{
	    pdEsperaAltaCliente = new ProgressDialog(this);
	    pdEsperaAltaCliente.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdEsperaAltaCliente.setMessage("Insertando cliente ...");
	    pdEsperaAltaCliente.setCancelable(false);
		pdEsperaAltaCliente.setCanceledOnTouchOutside(false);

	    WSInsertarCliente localWSInsertarCliente = new WSInsertarCliente();
	    try
	    {
	    	localWSInsertarCliente.execute();
	    }
	    catch (Exception localException)
	    {
	    	if (localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App",this.toString(), 1, "Error al ejecutar el WSInsertarCliente: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(), 1, "Error al ejecutar el WSInsertarCliente: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertarCliente.", 1);
	    }
	}
	
	private class WSInsertarCliente extends AsyncTask<Void, Integer, Boolean>
	{
		String CLIENTE_METHOD_NAME = "InsertCliente";
		String CLIENTE_SOAP_ACTION = NAMESPACE + CLIENTE_METHOD_NAME;
		
		boolean WSInsertarCliente = false;
		SoapObject soapResultado;
		int resultadoInt = 0;
		String resultadoString = "";
		
		protected void onPreExecute()
		{
			pdEsperaAltaCliente.show();
			
			if(localCliente == null)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los datos del cliente.", 2);
				return;
			}
		}
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSInsertarCliente = false;
			
			SoapObject localSoapObject = new SoapObject(NAMESPACE,CLIENTE_METHOD_NAME);
			localSoapObject.addProperty("codigo", localCliente.get_codigo());
			localSoapObject.addProperty("nombres", localCliente.get_nombres());
			localSoapObject.addProperty("paterno", localCliente.get_paterno());
			localSoapObject.addProperty("materno", localCliente.get_materno());
			localSoapObject.addProperty("apellidoCasada", localCliente.get_apellidoCasada());
			localSoapObject.addProperty("tieneCi", Boolean.valueOf(localCliente.is_tieneCi()));
			localSoapObject.addProperty("ci", localCliente.get_ci());
			localSoapObject.addProperty("expedidoId", localCliente.get_expedidoId());
			localSoapObject.addProperty("tieneCelular", Boolean.valueOf(localCliente.is_tieneCelular()));
			localSoapObject.addProperty("celular", localCliente.get_celular());
			localSoapObject.addProperty("tieneTelefono", Boolean.valueOf(localCliente.is_tieneTelefono()));
			localSoapObject.addProperty("telefono", localCliente.get_telefono());
			localSoapObject.addProperty("tipoCalleId", Integer.valueOf(localCliente.get_tipoCalleId()));
			localSoapObject.addProperty("direccion", localCliente.get_direccion());
			localSoapObject.addProperty("numero", localCliente.get_numero());
			localSoapObject.addProperty("edificio", localCliente.get_edificio());
			localSoapObject.addProperty("edificioPiso", localCliente.get_edificioPiso());
			localSoapObject.addProperty("edificioNumero", localCliente.get_edificioNumero());
			localSoapObject.addProperty("referencia", localCliente.get_referencia());
			localSoapObject.addProperty("manzano", localCliente.get_manzano());
			localSoapObject.addProperty("uv", localCliente.get_uv());
			localSoapObject.addProperty("entreTipoCalle1Id", Integer.valueOf(localCliente.get_entreTipoCalle1Id()));
			localSoapObject.addProperty("calle1", localCliente.get_calle1());
			localSoapObject.addProperty("entreTipoCalle2Id", Integer.valueOf(localCliente.get_entreTipoCalle2Id()));
			localSoapObject.addProperty("calle2", localCliente.get_calle2());
			localSoapObject.addProperty("nombreFactura", localCliente.get_nombreFactura());
			localSoapObject.addProperty("nit", localCliente.get_nit());
			localSoapObject.addProperty("razonSocial", localCliente.get_razonSocial());
			localSoapObject.addProperty("contacto", localCliente.get_contacto());
			localSoapObject.addProperty("email", localCliente.get_email());
			localSoapObject.addProperty("tipoNegocioId", Integer.valueOf(localCliente.get_tipoNegocioId()));
			localSoapObject.addProperty("zonaId", Integer.valueOf(localCliente.get_zonaId()));
			localSoapObject.addProperty("latitud", String.valueOf(localCliente.get_latitud()));
			localSoapObject.addProperty("longitud", String.valueOf(localCliente.get_longitud()));
			localSoapObject.addProperty("creadorId", Integer.valueOf(localCliente.get_creadorId()));
			localSoapObject.addProperty("latitudCreador", String.valueOf(localCliente.get_latitudCreador()));
			localSoapObject.addProperty("longitudCreador", String.valueOf(localCliente.get_longitudCreador()));
			localSoapObject.addProperty("dia", Integer.valueOf(localCliente.get_dia()));
			localSoapObject.addProperty("mes", Integer.valueOf(localCliente.get_mes()));
			localSoapObject.addProperty("anio", Integer.valueOf(localCliente.get_anio()));
			localSoapObject.addProperty("hora", Integer.valueOf(localCliente.get_hora()));
			localSoapObject.addProperty("minuto", Integer.valueOf(localCliente.get_minuto()));
			localSoapObject.addProperty("rutaId", Integer.valueOf(localCliente.get_rutaId()));
			localSoapObject.addProperty("diaRutaId", Integer.valueOf(localCliente.get_rutaDiaId()));
			localSoapObject.addProperty("mercadoId", Integer.valueOf(localCliente.get_mercadoId()));
			localSoapObject.addProperty("tatId", Integer.valueOf(localCliente.get_tatId()));
			localSoapObject.addProperty("tipoNit", String.valueOf(localCliente.get_tipoNit()));
			
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
			if(pdEsperaAltaCliente.isShowing())
			{
				pdEsperaAltaCliente.dismiss();
			}
			
			if(ejecutado)
			{
				if (WSInsertarCliente)
				{
					long l=0;
	
					try
					{
						l = new ClienteBLL().ModificarSincronizacionCliente(newAndroidRowId,resultadoInt,true);
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
					
					if(l<=0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizaciÛn del cliente.", 2);
						return;
					}
					
					theUtilidades.MostrarMensaje(getApplicationContext(), "Cliente insertado en el servidor.", 2);
					MostrarPantallaFotoCliente();
		        }
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),resultadoString,2);
					MostrarPantallaFotoCliente();
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"El webservice WSInsertCliente no se ejecutÛ correctamente.", 2);
				MostrarPantallaFotoCliente();
			}
		    
		}
    }
	
	private void MostrarPantallaFotoCliente()
	{
	    Intent localIntent = new Intent(this, Censistafotocliente.class);
	    localIntent.putExtra("RowId", String.valueOf(newAndroidRowId));
	    localIntent.putExtra("Origen", Origen);
	    startActivity(localIntent);
	}
	
	@Override
	public void onBackPressed() 
	{
		super.onBackPressed();
		Intent intent = new Intent(this,Menucensista.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("Origen", Origen);
		startActivity(intent);
	}
		
}