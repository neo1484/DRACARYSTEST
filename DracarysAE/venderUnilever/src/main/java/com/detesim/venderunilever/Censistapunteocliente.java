package com.detesim.venderunilever;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import BLL.CanalRutaBLL;
import BLL.CiudadBLL;
import BLL.ClienteBLL;
import BLL.ClienteFotoBLL;
import BLL.ClienteNitBLL;
import BLL.ClientePreventaBLL;
import BLL.ClienteVentaBLL;
import BLL.FotoCategoriaBLL;
import BLL.MercadoBLL;
import BLL.MyLogBLL;
import BLL.ParametroGeneralBLL;
import BLL.PrecioListaNombreBLL;
import BLL.ProvinciaBLL;
import BLL.TipoNegocioBLL;
import BLL.TipoNitBLL;
import BLL.VendedorZonaVentaBLL;
import BLL.ZonaBLL;
import BLL.ZonaMercadoBLL;
import Clases.CanalRuta;
import Clases.Ciudad;
import Clases.Cliente;
import Clases.ClienteFoto;
import Clases.Fecha;
import Clases.FotoCategoria;
import Clases.LoginEmpleado;
import Clases.ParametroGeneral;
import Clases.PrecioListaNombre;
import Clases.Provincia;
import Clases.TipoNegocio;
import Clases.TipoNit;
import Clases.TipoPago;
import Clases.Ubicacion;
import Clases.VendedorZonaVenta;
import Clases.Zona;
import Clases.ZonaMercado;
import Clases.Mercado;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Censistapunteocliente extends Activity implements OnClickListener
{
	static final int REQUEST_IMAGE_CAPTURE = 1;
	
	LinearLayout llCensistaPunteoCliente;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
	ArrayList<ClienteFoto> listadoClienteFoto;
	ArrayList<Cliente> listadoCliente;
	ArrayList<VendedorZonaVenta> listadoVendedorZonaVenta;
	Cliente theCliente;
	double latitudCreador = 0;
	double longitudCreador = 0;
	double latitudMapa = 0;
	double longitudMapa = 0;
	String nombres;
	String paterno;	
	String materno;
	String ci;
	String direccion;
	boolean tieneCelular;
	String celular;
	int tipoNegocioId = 0;
	int zonaId = 0;
	int rutaId = 0;
	int diaRutaId = 0;
	int mercadoId = 0;
	String tipoNit;
	String nombreFactura;
	String nit;
	long newClienteRowId;
	long newClientePreventaRowId;
	long newClienteVentaRowId;
	String Origen;
	boolean rolVendedor = false;
	boolean rolVendedorProvincia = false;
	boolean rolDistribuidor = false;
	ParametroGeneral parametroGeneral;
	boolean clienteVisita;
	int zonaVentaId;
	int zonaMercadoId;
	int cbAVal;
	int cbBVal;
	int cbCVal;
	int cbDVal;
	int cbEVal;
	int cbFVal;
	int cbGVal;
	int cbHVal;
	int cbIVal;
	int cbJVal;
	int cbKVal;
	int cbLVal;
	int cbMVal;
	int cbNVal;
	int cbOVal;
	int cbPVal;
	int cbQVal;
	int cbRVal;
	int ivs;
	int secuenciaPreventa;
	int secuenciaVenta;	
	int categoriaId;
	int provinciaId;
	int canalRutaId;
	int tipoPagoId;
	int precioListaId;
	
	ArrayList<FotoCategoria> listadoFotoCategoria;
	
	EditText etNombre;
	EditText etPaterno;
	EditText etMaterno;
	EditText etCi;
	EditText etDireccion;
	EditText etCelular;
	Spinner spnCiudad;
	Spinner spnProvincia;
	Spinner spnTipoPago;
	TextView tvCanalRuta;
	Spinner spnCanalRuta;
	Spinner spnClienteFotopunteoTipoNegocio;
	TextView tvZona;
	Spinner spnClienteFotopunteoZona;
	TextView tvPrecioLista;
	Spinner spnPrecioLista;
	TextView tvTipoNit;
	Spinner spnTiposNit;
	EditText etNombreFactura;
	EditText etNit;
	TextView tvZonaMercado;
	Spinner spnZonaMercado;
	TextView tvDiasVisita;
	CheckBox cbA;
	CheckBox cbB;
	CheckBox cbC;
	CheckBox cbD;
	CheckBox cbE;
	CheckBox cbF;
	CheckBox cbG;
	CheckBox cbH;
	CheckBox cbI;
	CheckBox cbJ;
	CheckBox cbK;
	CheckBox cbL;
	CheckBox cbM;
	CheckBox cbN;
	CheckBox cbO;
	CheckBox cbP;
	CheckBox cbQ;
	CheckBox cbR;
	TextView tvSecuenciaPreventa;
	EditText etSecuenciaPreventa;
	TextView tvSecuenciaVenta;
	EditText etSecuenciaVenta;
	Button btnInsertarCliente;
	Button btnTomarFoto;
	Button btnInsertarFotos;
	ListView lvFotos;
	ProgressDialog pdAltaClienteSinDatos;
	ProgressDialog pdInsertarFoto;
	TextView tvClientePunteoMercado;
	Spinner spnClientePunteoMercado;
	TextView tvFotoCategorias;
	Spinner spnFotoCategorias;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_censistapunteocliente);
		
		llCensistaPunteoCliente = (LinearLayout)findViewById(R.id.CensistaPunteoClienteLinearLayout);
		etNombre = (EditText)findViewById(R.id.etPunteoNombre);
		etPaterno = (EditText)findViewById(R.id.etPunteoApellidoPaterno);
		etMaterno = (EditText)findViewById(R.id.etPunteoClienteMaterno);
		etCi = (EditText)findViewById(R.id.etPunteoClienteCi);
		etDireccion = (EditText)findViewById(R.id.etPunteoDireccion);
		etCelular = (EditText)findViewById(R.id.etPunteoCelular);
		spnCiudad = ((Spinner)findViewById(R.id.spnPunteoClienteCiudad));
		spnProvincia = ((Spinner)findViewById(R.id.spnPunteoClienteProvincia));
		spnCanalRuta = (Spinner)findViewById(R.id.spnPunteoCanalRuta);
		spnTipoPago = ((Spinner)findViewById(R.id.spnPunteoClienteTipoPago));
	    spnClienteFotopunteoTipoNegocio = ((Spinner)findViewById(R.id.spnPunteoTipoNegocio));
	    tvZona = (TextView)findViewById(R.id.tvPunteoZona);
	    spnClienteFotopunteoZona = ((Spinner)findViewById(R.id.spnPunteoZona));
	    tvPrecioLista = (TextView)findViewById(R.id.tvPunteoClientePrecioLista);
	    spnPrecioLista = (Spinner)findViewById(R.id.spnPunteoClientePrecioLista);
	    tvTipoNit = (TextView)findViewById(R.id.tvCencistaPunteoTipoNit);
	    spnTiposNit = (Spinner)findViewById(R.id.spnCencistaPunteoTipoNit);
	    etNombreFactura = (EditText)findViewById(R.id.etPunteoNombreFactura);
	    etNit = (EditText)findViewById(R.id.etPunteoNit);
	    tvClientePunteoMercado = ((TextView)findViewById(R.id.tvCencistaPunteoMercado));
	    spnClientePunteoMercado = ((Spinner)findViewById(R.id.spnCensistaPunteoMercado));
	    tvZonaMercado = (TextView)findViewById(R.id.tvPunteoClienteZonaMercado);
		spnZonaMercado = (Spinner)findViewById(R.id.spnCencistaPunteoZonaMercado);
		tvDiasVisita = (TextView)findViewById(R.id.tvCencistaPunteoDiasVisita);
		cbA = (CheckBox)findViewById(R.id.cbCencistaPunteoA);
		cbB = (CheckBox)findViewById(R.id.cbCencistaPunteoB);
		cbC = (CheckBox)findViewById(R.id.cbCencistaPunteoC);
		cbD = (CheckBox)findViewById(R.id.cbCencistaPunteoD);
		cbE = (CheckBox)findViewById(R.id.cbCencistaPunteoE);
		cbF = (CheckBox)findViewById(R.id.cbCencistaPunteoF);
		cbG = (CheckBox)findViewById(R.id.cbCencistaPunteoG);
		cbH = (CheckBox)findViewById(R.id.cbCencistaPunteoH);
		cbI = (CheckBox)findViewById(R.id.cbCencistaPunteoI);
		cbJ = (CheckBox)findViewById(R.id.cbCencistaPunteoJ);
		cbK = (CheckBox)findViewById(R.id.cbCencistaPunteoK);
		cbL = (CheckBox)findViewById(R.id.cbCencistaPunteoL);
		cbM = (CheckBox)findViewById(R.id.cbCencistaPunteoM);
		cbN = (CheckBox)findViewById(R.id.cbCencistaPunteoN);
		cbO = (CheckBox)findViewById(R.id.cbCencistaPunteoO);
		cbP = (CheckBox)findViewById(R.id.cbCencistaPunteoP);
		cbQ = (CheckBox)findViewById(R.id.cbCencistaPunteoQ);
		cbR = (CheckBox)findViewById(R.id.cbCencistaPunteoR);
		tvSecuenciaPreventa = (TextView)findViewById(R.id.tvCencistaPunteoSecuenciaPreventa);
		etSecuenciaPreventa  = (EditText)findViewById(R.id.etCencistaPunteoSecuenciaPreventa);
		tvSecuenciaVenta = (TextView)findViewById(R.id.tvCencistaPunteoSecuenciaVenta);
		etSecuenciaVenta  = (EditText)findViewById(R.id.etCencistaPunteoSecuenciaVenta);
	    btnInsertarCliente = ((Button)findViewById(R.id.btnPunteoInsertarCliente));
	    btnInsertarCliente.setOnClickListener(this);
	    tvFotoCategorias = (TextView)findViewById(R.id.tvPunteoCategoriaFoto);
	    spnFotoCategorias = (Spinner)findViewById(R.id.spnPunteoCategoriaFoto);
	    btnTomarFoto = ((Button)findViewById(R.id.btnPunteoTomarFoto));
	    btnTomarFoto.setOnClickListener(this);
	    btnInsertarFotos = ((Button)findViewById(R.id.btnPunteoInsertarFotos));
	    btnInsertarFotos.setOnClickListener(this);
	    lvFotos = ((ListView)findViewById(R.id.lvPunteoListadoFotos));

	    llCensistaPunteoCliente.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
	    
	    Origen = "";
	    Bundle localBundle = getIntent().getExtras();
	    latitudMapa = Double.parseDouble(localBundle.getString("Latitud"));
	    longitudMapa = Double.parseDouble(localBundle.getString("Longitud"));
	    Origen = localBundle.getString("Origen");
	    
	    if ((latitudMapa == 0) || (longitudMapa == 0)) 
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la latitud o longitud del cliente.", 1);
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
	        if (localException.getMessage().isEmpty()) 
	        {
	        	myLog.InsertarLog("App", toString(), 1, "Error al obtener loginEmpleado supervisor: vacio");
          	} 
	        else 
	        {
	            myLog.InsertarLog("App", toString(), 1, "Error al obtener loginEmpleado supervisor: " + localException.getMessage());
	        }
	    }
	        
	    if (loginEmpleado != null)
        {
	    	if(ValidarFecha()) 
	    	{
	    		if(CargarClasificacionesFoto())
		    	{
		    		CargarInformacion();
		    		
		    		if(!parametroGeneral.is_mercadoRequerido())
		    		{
		    			tvClientePunteoMercado.setVisibility(View.INVISIBLE);
		    			spnClientePunteoMercado.setVisibility(View.INVISIBLE);
		    		}
		    	}
		    	else
		    	{
		    		theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las clasificaciones de las fotos.", 1);
		    	}
	        }
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(),"El usuario no se logeo correctamente.", 1);
		    	return;	    	
	    	}
        }
	    else
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(),"El usuario no se logeo correctamente.", 1);
	    	return;	    	
	    }	
    	
	    try 
	    {
			rolVendedor = theUtilidades.VerificarExistenciaRolEnUsuario(loginEmpleado.get_empleadoId(), "Vendedor");
		} 
	    catch (Exception localException) 
	    {
	    	if (localException.getMessage().isEmpty()) 
	        {
	        	myLog.InsertarLog("App", toString(), 1, "Error al verificar el rol vendedor: vacio");
          	} 
	        else 
	        {
	            myLog.InsertarLog("App", toString(), 1, "Error al verificar el rol vendedor: " + localException.getMessage());
	        }
		}
	    
	    try 
	    {
			rolVendedorProvincia = theUtilidades.VerificarExistenciaRolEnUsuario(loginEmpleado.get_empleadoId(), "VendedorProvincia");
		} 
	    catch (Exception localException) 
	    {
	    	if (localException.getMessage().isEmpty()) 
	        {
	        	myLog.InsertarLog("App", toString(), 1, "Error al verificar el rol vendedorProvincia: vacio");
          	} 
	        else 
	        {
	            myLog.InsertarLog("App", toString(), 1, "Error al verificar el rol vendedorProvincia: " + localException.getMessage());
	        }
		}
	    
	    try 
	    {
			rolDistribuidor = theUtilidades.VerificarExistenciaRolEnUsuario(loginEmpleado.get_empleadoId(), "Distribuidor");
		} 
	    catch (Exception localException) 
	    {
	    	if (localException.getMessage().isEmpty()) 
	        {
	        	myLog.InsertarLog("App", toString(), 1, "Error al verificar el rol distribuidor: vacio");
          	} 
	        else 
	        {
	            myLog.InsertarLog("App", toString(), 1, "Error al verificar el rol distribuidor: " + localException.getMessage());
	        }
		}
	    
	    /*if(rolVendedor || rolVendedorProvincia || rolDistribuidor)
	    {

	    }
	    else
	    {

	    }*/
    	
	    DesplegarControlesFoto(false);
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.btnPunteoInsertarCliente:
			InsertarClienteSinDatos();
			break;
		case R.id.btnPunteoTomarFoto:
			if(categoriaId > 0)
			{
				TomarFoto();
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "Debe seleccionar una categoria para clasificar la foto.", 1);
			}
			break;
		case R.id.btnPunteoInsertarFotos:
			InsertarFotos();
			break;
		}
	}
					
	private void DesplegarControlesZona(boolean estado)
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
		
		tvZona.setVisibility(visibility);
		spnClienteFotopunteoZona.setVisibility(visibility);
	}
	
	private void DesplegarControlesFoto(boolean estado) 
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
		
		tvFotoCategorias.setVisibility(visibility);
		spnFotoCategorias.setVisibility(visibility);
		btnTomarFoto.setVisibility(visibility);
		lvFotos.setVisibility(visibility);
		btnInsertarFotos.setVisibility(visibility);
	}
	
	private void DesplegarControlesClienteVisita()
	{
		cbA.setEnabled(false);
		cbB.setEnabled(false);
		cbC.setEnabled(false);
		cbD.setEnabled(false);
		cbE.setEnabled(false);
		cbF.setEnabled(false);
		cbG.setEnabled(false);
		cbH.setEnabled(false);
		cbI.setEnabled(false);
		cbJ.setEnabled(false);
		cbK.setEnabled(false);
		cbL.setEnabled(false);
		cbM.setEnabled(false);
		cbN.setEnabled(false);
		cbO.setEnabled(false);
		cbP.setEnabled(false);
		cbQ.setEnabled(false);
		cbR.setEnabled(false);
		
		for(VendedorZonaVenta item : listadoVendedorZonaVenta)
		{	
			if(item.get_rutaId().equals("A"))
			{
				cbA.setEnabled(true);
				cbA.setTextColor(Color.RED);
			};
			
			if(item.get_rutaId().equals("B"))
			{
				cbB.setEnabled(true);
				cbB.setTextColor(Color.RED);
			};
			
			if(item.get_rutaId().equals("C"))
			{
				cbC.setEnabled(true);
				cbC.setTextColor(Color.RED);
			};
			
			if(item.get_rutaId().equals("D"))
			{
				cbD.setEnabled(true);
				cbD.setTextColor(Color.RED);
			};
			
			if(item.get_rutaId().equals("E"))
			{
				cbE.setEnabled(true);
				cbE.setTextColor(Color.RED);
			};
			
			if(item.get_rutaId().equals("F"))
			{
				cbF.setEnabled(true);
				cbF.setTextColor(Color.RED);
			};
			
			if(item.get_rutaId().equals("G"))
			{
				cbG.setEnabled(true);
				cbG.setTextColor(Color.RED);
			};
			
			if(item.get_rutaId().equals("H"))
			{
				cbH.setEnabled(true);
				cbH.setTextColor(Color.RED);
			};
			
			if(item.get_rutaId().equals("I"))
			{
				cbI.setEnabled(true);
				cbI.setTextColor(Color.RED);
			};
			
			if(item.get_rutaId().equals("J"))
			{
				cbJ.setEnabled(true);
				cbJ.setTextColor(Color.RED);
			};
			
			if(item.get_rutaId().equals("K"))
			{
				cbK.setEnabled(true);
				cbK.setTextColor(Color.RED);
			};
			
			if(item.get_rutaId().equals("L"))
			{
				cbL.setEnabled(true);
				cbL.setTextColor(Color.RED);
			};
			
			if(item.get_rutaId().equals("M"))
			{
				cbM.setEnabled(true);
				cbM.setTextColor(Color.RED);
			};
			
			if(item.get_rutaId().equals("N"))
			{
				cbN.setEnabled(true);
				cbN.setTextColor(Color.RED);
			};
			
			if(item.get_rutaId().equals("O"))
			{
				cbO.setEnabled(true);
				cbO.setTextColor(Color.RED);
			};
			
			if(item.get_rutaId().equals("P"))
			{
				cbP.setEnabled(true);
				cbP.setTextColor(Color.RED);
			};
			
			if(item.get_rutaId().equals("Q"))
			{
				cbQ.setEnabled(true);
				cbQ.setTextColor(Color.RED);
			};
			
			if(item.get_rutaId().equals("R"))
			{
				cbR.setEnabled(true);
				cbR.setTextColor(Color.RED);
			};
		}
	}
	
	private void DesplegarControlesZonaMercado(boolean estado)
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
		
		tvZonaMercado.setVisibility(visibility);
		spnZonaMercado.setVisibility(visibility);
	}
	
	private void DesplegarControlesListaPrecio(boolean estado)
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
		
		tvPrecioLista.setVisibility(visibility);
		spnPrecioLista.setVisibility(visibility);
	}
		
	public boolean ValidarFecha()
	{
	    if(theUtilidades.ValidarFecha(loginEmpleado.get_dia(),loginEmpleado.get_mes(),loginEmpleado.get_anio()))
	    {
	      return true;
	    }
	    else
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(),"La fecha del celular, no concuerda con la del servidor.", 1);
		    return false;	    	
	    }
	  }

	private boolean CargarClasificacionesFoto()
	{
		listadoFotoCategoria = null;
		
		try
		{
			listadoFotoCategoria = new FotoCategoriaBLL().ObtenerFotosCategoria();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener las cotegorias de las fotos: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener las categorias de las fotos: " + localException.getMessage());
	    	}  
		}
		
		if(listadoFotoCategoria == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las categorias de las fotos.", 1);
			return false;
		}
		else
		{				
			ArrayAdapter<FotoCategoria> localArrayAdapter = new ArrayAdapter<FotoCategoria>(this,R.layout.disenio_spinner,listadoFotoCategoria);
	        spnFotoCategorias.setAdapter(localArrayAdapter);
	        	        
	        spnFotoCategorias.setOnItemSelectedListener(new OnItemSelectedListener() 
		    {	    	
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
				{
					categoriaId = ((FotoCategoria)spnFotoCategorias.getSelectedItem()).get_categoriaId();
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent){}
		    	
			});	
	        
	        return true;
		}
	}
	
	private void CargarInformacion()
	{
		CargarParametroGeneral();
	    CargarCiudades();
	    CargarProvincias();
	    CargarTiposPago();
		CargarTipoNegocio();
		//CargarRutasVendedor();
		CargarCanalesRuta();
		CargarVendedorZonaVenta();
		DesplegarControlesClienteVisita();
	    
	    if(parametroGeneral.is_zonaRequerida())
	    {
	    	CargarZonas();
	    	DesplegarControlesZona(true);
	    }
	    else
	    {
	    	DesplegarControlesZona(false);
	    }
	    
	    if(parametroGeneral.is_mostrarListaPrecio())
	    {
	    	CargarListasPrecio();
	    	DesplegarControlesListaPrecio(true);
	    }
	    else
	    {
	    	DesplegarControlesListaPrecio(false);
	    }
	    
	    CargarMercados();
	    CargarTiposNit();
	    
	    if(Origen.equals("Menuvendedor"))
	    {
	    	CargarVendedorZonaVenta();
	    }
	    if(Origen.equals("Menudistribuidor"))
	    {
	    }
	    
	    /*if(parametroGeneral.is_clienteVisitaRequerida())
	    {
	    	//CargarZonaVenta();
	    	DesplegarControlesClienteVisita(true);
	    	clienteVisita = true;
	    }
	    else
	    {
	    	DesplegarControlesClienteVisita(false);
	    }*/
	    
	    if(parametroGeneral.is_zonaMercadoRequerida())
	    {
	    	CargarZonaMercado();
	    	DesplegarControlesZonaMercado(true);
	    }
	    else
	    {
	    	DesplegarControlesZonaMercado(false);
	    }
	    
	    etNombreFactura.setText("Control tributario");
	    etNit.setText("0");
	    
	    CargarSecuenciaVisita();
	}
	
	private void CargarCiudades()
	{
		ArrayList<Ciudad> listadoCiudad = new ArrayList<Ciudad>();
		try
	    {
			listadoCiudad = new CiudadBLL().ObtenerCiudades();
	    }
	    catch (Exception localException)
	    {
	    	if (localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener las ciudades: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener las ciudades: " + localException.getMessage());
	    	}
	    }
			
		if (listadoCiudad !=null && listadoCiudad.size() > 0)
	    {
	        ArrayAdapter<Ciudad> localArrayAdapter = new ArrayAdapter<Ciudad>(this,R.layout.disenio_spinner,listadoCiudad);
	        spnCiudad.setAdapter(localArrayAdapter);
	        
	        spnCiudad.setOnItemSelectedListener(new OnItemSelectedListener() 
		    {	    	
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
				{
					Ciudad ciudad = (Ciudad)spnCiudad.getSelectedItem();
					
					if(ciudad.get_ciudadId() > 0)
					{
						ArrayList<Provincia> listadoProvincia = null;
						try
						{
							listadoProvincia = new ProvinciaBLL().ObtenerProvinciasPorCiudadId(ciudad.get_ciudadId());
						}
						catch(Exception localException)
						{
							if(localException.getMessage() == null || localException.getMessage().isEmpty())
							{
						        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las provinicias por ciudadId: vacio");
							}
							else
							{
								myLog.InsertarLog("App",this.toString(),1,"Error al obtener los provincias por ciudadId: " + localException.getMessage());
							} 
						}
						
						if(listadoProvincia == null)
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las provincias por la ciudad seleccionada.", 1);
							return;
						}
						
						ArrayAdapter<Provincia> arrayAdapter = new ArrayAdapter<Provincia>(getApplicationContext(),R.layout.disenio_spinner,listadoProvincia);
					    spnProvincia.setAdapter(arrayAdapter);
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent){}
		    	
			});
	    }
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron ciudades que desplegar.", 1);
		    return;
		}
	}
	
	private void CargarProvincias()
	{
		ArrayList<Provincia> listadoProvincia = new ArrayList<Provincia>();
		try
	    {
			listadoProvincia = new ProvinciaBLL().ObtenerProvincias();
	    }
	    catch (Exception localException)
	    {
	    	if (localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener las provincias: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener las provincias: " + localException.getMessage());
	    	}
	    }
			
		if (listadoProvincia !=null && listadoProvincia.size() > 0)
	    {
	        ArrayAdapter<Provincia> localArrayAdapter = new ArrayAdapter<Provincia>(this,R.layout.disenio_spinner,listadoProvincia);
	        spnProvincia.setAdapter(localArrayAdapter);
	        
	        spnProvincia.setOnItemSelectedListener(new OnItemSelectedListener() 
		    {	    	
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
				{
					Provincia provincia = (Provincia)spnProvincia.getSelectedItem();
					provinciaId = provincia.get_provinciaId();
					
					if(provincia.get_provinciaId() > 0)
					{
						provinciaId = provincia.get_provinciaId();
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent){}
		    	
			});
	    }
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron provincias que desplegar.", 1);
		}
	}
	
	private void CargarCanalesRuta()
	{
		ArrayList<CanalRuta> listadoCanalRuta = new ArrayList<CanalRuta>();
		try
	    {
			listadoCanalRuta = new CanalRutaBLL().ObtenerCanalesRuta();
	    }
	    catch (Exception localException)
	    {
	    	if (localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los canales ruta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los canales ruta: " + localException.getMessage());
	    	}
	    }
			
		if (listadoCanalRuta !=null && listadoCanalRuta.size() > 0)
	    {
	        ArrayAdapter<CanalRuta> localArrayAdapter = new ArrayAdapter<CanalRuta>(this,R.layout.disenio_spinner,listadoCanalRuta);
	        spnCanalRuta.setAdapter(localArrayAdapter);
	        
	        spnCanalRuta.setOnItemSelectedListener(new OnItemSelectedListener() 
		    {	    	
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
				{
					CanalRuta canalRuta = (CanalRuta)spnCanalRuta.getSelectedItem();
					canalRutaId = canalRuta.get_canalRutaId();					
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent){}
		    	
			});
	    }
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron canales ruta que desplegar.", 1);
		}
	}
	
	private void CargarTiposPago()
	{
		ArrayList<TipoPago> listadoTipoPago = new ArrayList<TipoPago>();
		listadoTipoPago.add(new TipoPago(0,"[Seleccione tipo pago ...]"));
		listadoTipoPago.add(new TipoPago(1,"Contado"));
		listadoTipoPago.add(new TipoPago(2,"Credito"));
		
		if(!parametroGeneral.is_mostrarTipoPago())
		{
			spnTipoPago.setEnabled(false);
		}
		
		ArrayAdapter<TipoPago> localArrayAdapter = new ArrayAdapter<TipoPago>(this,R.layout.disenio_spinner,listadoTipoPago);
	    spnTipoPago.setAdapter(localArrayAdapter);
	    
	    spnTipoPago.setSelection(1);
	    
	    spnTipoPago.setOnItemSelectedListener(new OnItemSelectedListener() 
	    {	    	
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
			{
				TipoPago tipoPago = (TipoPago)spnTipoPago.getSelectedItem();
				
				if(tipoPago.get_tipoPagoId() > 0)
				{
					tipoPagoId = tipoPago.get_tipoPagoId();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent){}
	    	
		});
	}
	
	private void CargarListasPrecio()
	{
		ArrayList<PrecioListaNombre> listadoPrecioListaNombre = new ArrayList<PrecioListaNombre>();
		try
	    {
			listadoPrecioListaNombre = new PrecioListaNombreBLL().ObtenerPreciosListaNombre();
	    }
	    catch (Exception localException)
	    {
	    	if (localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los precios de lista: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los precios de lista: " + localException.getMessage());
	    	}
	    }
			
		if (listadoPrecioListaNombre !=null && listadoPrecioListaNombre.size() > 0)
	    {
	        ArrayAdapter<PrecioListaNombre> localArrayAdapter = new ArrayAdapter<PrecioListaNombre>(this,R.layout.disenio_spinner,listadoPrecioListaNombre);
	        spnPrecioLista.setAdapter(localArrayAdapter);
	        
	        spnPrecioLista.setOnItemSelectedListener(new OnItemSelectedListener() 
		    {	    	
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
				{
					PrecioListaNombre precioListaNombre = (PrecioListaNombre)spnPrecioLista.getSelectedItem();
					
					if(precioListaNombre.get_precioListaNombreId() > 0)
					{
						precioListaId = precioListaNombre.get_precioListaNombreId();
						
						if(precioListaNombre.get_nombre().toUpperCase().equals("TT"))
						{
							spnClientePunteoMercado.setVisibility(View.VISIBLE);
							spnClienteFotopunteoZona.setVisibility(View.INVISIBLE);
						}
						else if (precioListaNombre.get_nombre().toUpperCase().equals("TDB"))
						{
							spnClientePunteoMercado.setVisibility(View.INVISIBLE);
							spnClienteFotopunteoZona.setVisibility(View.VISIBLE);
						}
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent){}
		    	
			});
	    }
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron precios de lista que desplegar.", 1);
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
	        spnClienteFotopunteoTipoNegocio.setAdapter(localArrayAdapter);
	    }
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron tipos de negocio que desplegar.", 1);
		    return;
		}
	}
	
	private void CargarVendedorZonaVenta()
	{
		listadoVendedorZonaVenta = null;
		try
	    {
			listadoVendedorZonaVenta = new VendedorZonaVentaBLL().ObtenerVendedoresZonaVenta();
	    }
	    catch (Exception localException)
	    {
	    	if (localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los vendedores zona venta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los vendedores zona venta: " + localException.getMessage());
	    	}
	    }
			
		if (listadoVendedorZonaVenta == null)
	    {
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron vendedores zona venta que descargar.", 1);
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
	        spnClienteFotopunteoZona.setAdapter(localArrayAdapter);
	    }
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron zonas que desplegar.", 1);
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
	    
	    if(listadoMercado != null && listadoMercado.size() > 0)
	    {
	    	ArrayAdapter<Mercado> localArrayAdapter = new ArrayAdapter<Mercado>(this,R.layout.disenio_spinner,listadoMercado);
	    	spnClientePunteoMercado.setAdapter(localArrayAdapter);
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

	private void CargarZonaMercado()
	{
		ArrayList<ZonaMercado> listadoZonaMercado = new ArrayList<ZonaMercado>();
	    
	    try
	    {
	    	listadoZonaMercado = new ZonaMercadoBLL().ObtenerZonasMercado();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener las zonasMercado: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los zonasMercado: " + localException.getMessage());
	    	}    		
	    }
	    
	    if(listadoZonaMercado != null && listadoZonaMercado.size() > 0)
	    {
	    	ArrayAdapter<ZonaMercado> localArrayAdapter = new ArrayAdapter<ZonaMercado>(this,R.layout.disenio_spinner,listadoZonaMercado);
	    	spnZonaMercado.setAdapter(localArrayAdapter);
	    }
	    else
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron zonas mercados que desplegar.", 1);
		    return;
	    } 
	}
	
	private void CargarSecuenciaVisita()
	{
		if(parametroGeneral.is_mostrarSecuenciaVisita())
		{
			tvSecuenciaPreventa.setVisibility(View.VISIBLE);
			etSecuenciaPreventa.setVisibility(View.VISIBLE);
			tvSecuenciaVenta.setVisibility(View.VISIBLE);
			etSecuenciaVenta.setVisibility(View.VISIBLE);
		}
		else
		{
			tvSecuenciaPreventa.setVisibility(View.INVISIBLE);
			etSecuenciaPreventa.setVisibility(View.INVISIBLE);
			tvSecuenciaVenta.setVisibility(View.INVISIBLE);
			etSecuenciaVenta.setVisibility(View.INVISIBLE);
		}
	}
	
	private void ObtenerFotosCliente()
	{
	    listadoClienteFoto = null;
	    try
	    {
	    	listadoClienteFoto = new ClienteFotoBLL().ObtenerClientesFotoPorClienteIdAndroid((int)newClienteRowId);
	    }
	    catch (Exception localException)
	    {
	    	if (localException.getMessage().isEmpty()) 
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener las fotos de los clientes. " + localException.getMessage());
	        } 
	    	else 
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener las fotos de los clientes. " + localException.getMessage());
	        }
	    }
	    	    
	    if (listadoClienteFoto == null)
	    {
	    	btnTomarFoto.setVisibility(View.VISIBLE);
	    	lvFotos.setVisibility(View.INVISIBLE);
	    	btnInsertarFotos.setVisibility(View.INVISIBLE);
	    }
	    else
	    {
	    	if (listadoClienteFoto.size() >= 4) 
		    {
		    	btnTomarFoto.setVisibility(View.INVISIBLE);
		    }
		    else
		    {
		      btnTomarFoto.setVisibility(View.VISIBLE);
		      lvFotos.setVisibility(View.VISIBLE);
		      btnInsertarFotos.setVisibility(View.VISIBLE);
		    }
	    	
	    	LlenarListViewFotos();
		    EliminarItemListViewFotos();
	    }
	}
	
	private void LlenarListViewFotos()
	{
		MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    ListView localListView = (ListView)findViewById(R.id.lvPunteoListadoFotos);
	    if(listadoClienteFoto == null)
	    {
	    	localListView.setAdapter(null);
	    }
	    else
	    {
	    	ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    	localLayoutParams.height = ((int)(110*getApplicationContext().getResources().getDisplayMetrics().density) * listadoClienteFoto.size());
	    	localListView.setLayoutParams(localLayoutParams);
	    	localListView.setAdapter(localMiAdaptadorLista);
	    }
	}
	
	class MiAdaptadorLista extends ArrayAdapter<ClienteFoto>
	{
		private Context _context;
		
		public MiAdaptadorLista(Context context)
		{
			super(context,R.layout.disenio_censistaclientefoto,listadoClienteFoto);
			this._context = context;
		}
    
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View localView = convertView;
			
			if (convertView == null) 
			{
				LayoutInflater layoutInflater = (LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
				localView = layoutInflater.inflate(R.layout.disenio_censistaclientefoto,parent,false);
			}
			
			ClienteFoto localClienteFoto = (ClienteFoto)listadoClienteFoto.get(position);
			
			TextView textView = (TextView)localView.findViewById(R.id.tvDisenioClienteFotoNombre);
			ImageView imageView = (ImageView)localView.findViewById(R.id.ivDisenioClienteFotoFoto);
			
			textView.setText(theCliente.get_nombres()+ " " + theCliente.get_paterno() + "_" + String.valueOf(position+1));
			imageView.setImageBitmap(ConvertirVectorDeBytesABitmap(localClienteFoto.get_foto()));
			
			return localView;
		}
	}
	
	public Bitmap ConvertirVectorDeBytesABitmap(byte[] paramArrayOfByte)
	{
	    return BitmapFactory.decodeByteArray(paramArrayOfByte, 0, paramArrayOfByte.length);
	}

	private void EliminarItemListViewFotos()
	{
	    ((ListView)findViewById(R.id.lvPunteoListadoFotos)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
	      public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	      {
	    	  ClienteFoto localClienteFoto = (ClienteFoto)listadoClienteFoto.get(position);
	    	  boolean bool =false;
	    	  try
	    	  {
	    		  bool = new ClienteFotoBLL().BorrarClientesFotoPor(localClienteFoto.get_id());
	    	  }
	    	  catch (Exception localException)
	    	  {
	    		  if (localException.getMessage().isEmpty())
	    		  {
	    			  myLog.InsertarLog("App", toString(), 1, "Error al borrar la foto del cliente por fotoClienteId: vacio");
	    		  }
	    		  else
	    		  {
	    			  myLog.InsertarLog("App", toString(), 1, "Error al borrar la foto del cliente por fotoClienteId: " + localException.getMessage());
	    		  }
			  }
	    	  
	    	  if(bool)
	    	  {
	    		  theUtilidades.MostrarMensaje(getApplicationContext(), "Foto eliminada.", 1);
	    		  ObtenerFotosCliente();
	    	  }
	    	  else
	    	  {
	    		  theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar la foto del dispositivo.", 1);				
	    	  }
	      }
	    });
	  }

	private void InsertarClienteSinDatos()
	{
		Cliente insertCliente = InsertarCliente();
		
		if(insertCliente != null)
		{
			pdAltaClienteSinDatos = new ProgressDialog(this);
		    pdAltaClienteSinDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		    pdAltaClienteSinDatos.setMessage("Insertando cliente ...");
		    pdAltaClienteSinDatos.setCancelable(false);
			pdAltaClienteSinDatos.setCanceledOnTouchOutside(false);
		    
		    WSInsertarClienteSinDatos localWSInsertarClienteSinDatos = new WSInsertarClienteSinDatos(insertCliente);
		    try
		    {
		    	localWSInsertarClienteSinDatos.execute();
		    }
		    catch (Exception localException)
		    {
		    	if (localException.getMessage().isEmpty())
		    	{
		    		myLog.InsertarLog("App",this.toString(), 1, "Error al ejecutar webservice WSInsertarClienteSinDatos: vacio");
		    	}
		    	else
		    	{
		    		myLog.InsertarLog("App", toString(), 1, "Error al ejecutar webservice WSInsertarClienteSinDatos: " + localException.getMessage());
		    	}
		    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertarClienteSinDatos.", 1);
		    }
		}
	}
	
	private Cliente InsertarCliente()
	{	
		ObtenerCoordenadasCreador();
		
	    Fecha localFecha = theUtilidades.ObtenerFecha();
	    
	    if(etNombre.getText().toString().isEmpty())
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El nombre es requerido.", 1);
	    	return null;
	    }
	    
	    if(etPaterno.getText().toString().isEmpty())
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El apellido paterno es requerido.", 1);
	    	return null;
	    }
	    
	    if(etMaterno.getText().toString().isEmpty())
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El apellido materno es requerido.", 1);
	    	return null;
	    }
	    
	    if(etCi.getText().toString().isEmpty())
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El C.I. es requerido.", 1);
	    	return null;
	    }
	    
	    if(etCelular.getText().toString().isEmpty())
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El celular es requerido.", 1);
	    	return null;
	    }
	    
	    /*if(rolVendedor || rolVendedorProvincia)
	    {
	    }
	    else if(rolDistribuidor)
	    {
	    
	    }
	    else
	    {

	    }*/
	    
	    if(parametroGeneral.is_zonaRequerida())
	    {
	    	zonaId = ((Zona)spnClienteFotopunteoZona.getSelectedItem()).get_zonaId();
	    }
	    
	    if(provinciaId <= 0)
	    {	
	    	if(parametroGeneral.is_provinciaRequerida())
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "La provincia es requerida.", 1);
	    		return null;
	    	}
	    }
	    
	    if(zonaId <= 0)
	    {	
	    	if(parametroGeneral.is_zonaRequerida())
	    	{
	    		if(spnClienteFotopunteoZona.getVisibility() == View.VISIBLE)
	    		{
	    			if(((Zona)spnClienteFotopunteoZona.getSelectedItem()).get_zonaId()<=0)
		    		{
		    			theUtilidades.MostrarMensaje(getApplicationContext(), "El barrio es requerido.", 1);
		    	    	return null;
		    		}
	    		}
	    	}
	    }
	    
	    if(mercadoId<=0)
	    {
	    	if(parametroGeneral.is_mercadoRequerido())
	    	{
	    		if(spnClientePunteoMercado.getVisibility() == View.VISIBLE)
	    		{
	    			if(((Mercado)spnClientePunteoMercado.getSelectedItem()).get_mercadoId()<=0)
		    		{
		    			theUtilidades.MostrarMensaje(getApplicationContext(), "El mercado es requerido.", 1);
		    	    	return null;
		    		}
	    		}
	    	}
	    }
	    
	    if(zonaMercadoId <= 0)
	    {	
	    	if(parametroGeneral.is_zonaMercadoRequerida())
	    	{
	    		if(((ZonaMercado)spnZonaMercado.getSelectedItem()).get_zonaMercadoId()<=0)
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(), "La zona mercado es requerida.", 1);
	    	    	return null;
	    		}
	    	}
	    }
	    
	    if(etDireccion.getText().toString().isEmpty())
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "La direccion es requerida.", 1);
	    	return null;
	    }
	    
	    tipoNegocioId = ((TipoNegocio)spnClienteFotopunteoTipoNegocio.getSelectedItem()).get_tipoNegocioId();

	    if(tipoNegocioId == 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El tipo de negocio es requerido.", 1);
	    	return null;
	    }
	    
	    if(canalRutaId <= 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El canal ruta es requerido.", 1);
	    	return null;
	    }
	    
	    /*else if(rutaId == 0 && !rolVendedor)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "La ruta es requerida.", 1);
	    	return null;
	    }
	    else if(diaRutaId == 0 && !rolVendedor)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El dia de ruta es requerido.", 1);
	    	return null;
	    }*/
	    
	    tipoPagoId = ((TipoPago)(spnTipoPago.getSelectedItem())).get_tipoPagoId();
	    
	    if(tipoPagoId <= 0)
	    {		    	
    		theUtilidades.MostrarMensaje(getApplicationContext(), "El tipo de pago es requerido.", 1);
    		return null;
	    }
	    
	    if(precioListaId <= 0)
	    {	
	    	if(parametroGeneral.is_mostrarListaPrecio())
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "El sub canal es requerido.", 1);
	    		return null;
	    	}
	    }
	    
	    tipoNit = ((TipoNit)(spnTiposNit.getSelectedItem())).get_tipoNit();
		
		if(tipoNit.equals("Seleccione un tipo de NIT"))
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "El tipo de nit es requerido.", 1);
			return null;
		}
	    
	    if(etNombreFactura.getText().toString().length() > 0)
	    {
	    	nombreFactura = etNombreFactura.getText().toString();
	    }
	    else
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El nombre de factura es requerido.", 1);
    		return null;
	    }
	    
	    if(etNit.getText().toString().length()>0)
	    {
	    	nit = etNit.getText().toString();
	    }
	    else
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El nit es requerido.", 1);
    		return null;
	    }
	    
	    cbAVal = cbA.isChecked()?1:0;
    	cbBVal = cbB.isChecked()?1:0;
    	cbCVal = cbC.isChecked()?1:0;
    	cbDVal = cbD.isChecked()?1:0;
    	cbEVal = cbE.isChecked()?1:0;
    	cbFVal = cbF.isChecked()?1:0;
    	cbGVal = cbG.isChecked()?1:0;
    	cbHVal = cbH.isChecked()?1:0;
    	cbIVal = cbI.isChecked()?1:0;
    	cbJVal = cbJ.isChecked()?1:0;
    	cbKVal = cbK.isChecked()?1:0;
    	cbLVal = cbL.isChecked()?1:0;
    	cbMVal = cbM.isChecked()?1:0;
    	cbNVal = cbN.isChecked()?1:0;
    	cbOVal = cbO.isChecked()?1:0;
    	cbPVal = cbP.isChecked()?1:0;
    	cbQVal = cbQ.isChecked()?1:0;
    	cbRVal = cbR.isChecked()?1:0;
    	
    	if((cbAVal + cbBVal + cbCVal + cbDVal + cbEVal + cbFVal + cbGVal + cbHVal + cbIVal + cbJVal + cbKVal + cbLVal + cbMVal + cbNVal + cbOVal + cbPVal + cbQVal + cbRVal) == 0)
    	{
    		theUtilidades.MostrarMensaje(getApplicationContext(), "Debe seleccionar una ruta.", 1);
	    	return null;
    	}
    	
    	int rutasSemanales = 0;
    	int rutasQuincenales = 0;
    	int rutasMensuales = 0;
    	
    	rutasSemanales = cbAVal + cbBVal + cbCVal + cbDVal + cbEVal + cbFVal;
    	rutasQuincenales = cbGVal + cbHVal + cbIVal + cbJVal + cbKVal + cbLVal;
    	rutasMensuales = cbMVal + cbNVal + cbOVal + cbPVal + cbQVal + cbRVal;
    	
    	if(rutasSemanales > 0 && rutasQuincenales > 0)
    	{
    		theUtilidades.MostrarMensaje(getApplicationContext(), "Elija entre una ruta semanal o quincenal", 1);
	    	return null;
    	}
    	
    	if(rutasSemanales > 0 && rutasMensuales > 0)
    	{
    		theUtilidades.MostrarMensaje(getApplicationContext(), "Elija entre una ruta semanal o mensual", 1);
	    	return null;
    	}
    	
    	if(rutasQuincenales > 0 && rutasMensuales > 0)
    	{
    		theUtilidades.MostrarMensaje(getApplicationContext(), "Elija entre una ruta quincenal o mensual", 1);
	    	return null;
    	}
	    
	    nombres = "";
	    if(etNombre.getText().toString().length() > 0)
	    {
	    	nombres = etNombre.getText().toString();	    	
	    }
	    
	    paterno = "";
	    if(etPaterno.getText().toString().length() > 0)
	    {
	    	paterno = etPaterno.getText().toString();
	    }
	    
	    materno = "";
	    if(etMaterno.getText().toString().length() > 0)
	    {
	    	materno = etMaterno.getText().toString();
	    }
	    
	    ci = "";
	    if(etCi.getText().toString().length() > 0)
	    {
	    	ci = etCi.getText().toString();
	    }
	    
	    direccion = "";
	    if(etDireccion.getText().toString().length() > 0)
	    {
	    	direccion = etDireccion.getText().toString();
	    }
	    
	    celular = "";
	    tieneCelular = false;
	    if(etCelular.getText().toString().length() > 0)
	    {
	    	celular = etCelular.getText().toString();
	    	tieneCelular = true;
	    }
	    
	    String nombreCompleto = nombres + " " + paterno + " " + materno;
	    
	    mercadoId = ((Mercado)spnClientePunteoMercado.getSelectedItem()).get_mercadoId();
	    

    	//zonaVentaId = ((ZonaVenta)spnZonaVenta.getSelectedItem()).get_zonaVentaId();
    	if(parametroGeneral.is_zonaMercadoRequerida())
    	{
    		zonaMercadoId = ((ZonaMercado)spnZonaMercado.getSelectedItem()).get_zonaMercadoId();
    	}	    
	    
	    if(parametroGeneral.is_mostrarSecuenciaVisita())
    	{
    		if(etSecuenciaPreventa.getText().length()<=0)
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "Debe especificar una secuencia de visita preventa.", 1);
	    		return null;
	    	}
	    	else
	    	{
	    		secuenciaPreventa = Integer.parseInt(etSecuenciaPreventa.getText().toString());
	    	}
	    	
	    	if(etSecuenciaVenta.getText().length()<=0)
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "Debe especificar una secuencia de visita distribucion.", 1);
	    		return null;
	    	}
	    	else
	    	{
	    		secuenciaVenta = Integer.parseInt(etSecuenciaVenta.getText().toString());
	    	}
    	}
	    
	    Fecha fecha = theUtilidades.ObtenerFecha();
	    String rutaSeleccionada = "";
	    if(cbAVal==1 && fecha.get_diaSemana() == 2) rutaSeleccionada = "A";
	    if(cbBVal==1 && fecha.get_diaSemana() == 3) rutaSeleccionada = "B";
	    if(cbCVal==1 && fecha.get_diaSemana() == 4) rutaSeleccionada = "C";
	    if(cbDVal==1 && fecha.get_diaSemana() == 5) rutaSeleccionada = "D";
	    if(cbEVal==1 && fecha.get_diaSemana() == 6) rutaSeleccionada = "E";
	    if(cbFVal==1 && fecha.get_diaSemana() == 7) rutaSeleccionada = "F";
	    if(cbGVal==1 && fecha.get_diaSemana() == 2) rutaSeleccionada = "G";
	    if(cbHVal==1 && fecha.get_diaSemana() == 3) rutaSeleccionada = "H";
	    if(cbIVal==1 && fecha.get_diaSemana() == 4) rutaSeleccionada = "I";
	    if(cbJVal==1 && fecha.get_diaSemana() == 5) rutaSeleccionada = "J";
	    if(cbKVal==1 && fecha.get_diaSemana() == 6) rutaSeleccionada = "K";
	    if(cbLVal==1 && fecha.get_diaSemana() == 7) rutaSeleccionada = "L";
	    if(cbMVal==1 && fecha.get_diaSemana() == 2) rutaSeleccionada = "M";
	    if(cbNVal==1 && fecha.get_diaSemana() == 3) rutaSeleccionada = "N";
	    if(cbOVal==1 && fecha.get_diaSemana() == 4) rutaSeleccionada = "O";
	    if(cbPVal==1 && fecha.get_diaSemana() == 5) rutaSeleccionada = "P";
	    if(cbQVal==1 && fecha.get_diaSemana() == 6) rutaSeleccionada = "Q";
	    if(cbRVal==1 && fecha.get_diaSemana() == 7) rutaSeleccionada = "R";
	    
	    if(rutaSeleccionada.equals(""))
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo determinar la zona de venta del cliente.", 1);
	    	return null;
	    }
	    
	    VendedorZonaVenta vendedorZonaVenta = null;
	    
	    try
	    {
	    	vendedorZonaVenta = new VendedorZonaVentaBLL().ObtenerVendedoresZonaVenta().get(0);
	    }
	    catch(Exception localException)
	    {
	    	if (localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App",this.toString(), 1, "Error al obtener la zonaVentaId del cliente: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(), 1, "Error al obtener la zonaVentaId del cliente: " + localException.getMessage());  
	    	}
	    }
	    
	    if(vendedorZonaVenta == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo determinar la zona de venta del vendedor", 1);
	    	return null;
	    }
	    
	    Cliente localCliente = new Cliente(0,0,"", nombres, paterno, 
    			materno, "", nombreCompleto, false, ci, "", tieneCelular, 
    			celular, 0, direccion, "", "", 0, "", 0, "", "", "", "", "", "", 
    			nombreFactura, nit,	"", "", false, "", "", "", 
    			tipoNegocioId,zonaId,latitudMapa,longitudMapa,loginEmpleado.get_empleadoId(), 
    			latitudCreador,longitudCreador, tipoPagoId, 0, 0, localFecha.get_dia(), localFecha.get_mes(), 
    			localFecha.get_anio(), localFecha.get_hora(), localFecha.get_minuto(), false, false, false,rutaId,
    			diaRutaId,mercadoId,true,false,0,tipoNit,clienteVisita,zonaMercadoId,
    			cbAVal,cbBVal,cbCVal,cbDVal,cbEVal,cbFVal,cbGVal,cbHVal,cbIVal,cbJVal,cbKVal,cbLVal,
    			cbMVal,cbNVal,cbOVal,cbPVal,cbQVal,cbRVal,secuenciaPreventa,secuenciaVenta,
    			provinciaId,precioListaId,rutaSeleccionada,"No Programado",vendedorZonaVenta.get_zonaVentaId(),canalRutaId);
	    
	    	    
	    newClienteRowId=0;
	    try
	    {
	    	newClienteRowId = new ClienteBLL().InsertarCliente(localCliente.get_clienteId(),localCliente.get_codigo(),
	    			localCliente.get_nombres(),localCliente.get_paterno(),localCliente.get_materno(),localCliente.get_apellidoCasada(),
	    			localCliente.get_nombreCompleto(),localCliente.is_tieneCi(),localCliente.get_ci(),localCliente.get_expedidoId(),
	    			localCliente.is_tieneCelular(),localCliente.get_celular(),localCliente.get_tipoCalleId(),localCliente.get_direccion(),
	    			localCliente.get_numero(),localCliente.get_referencia(),localCliente.get_entreTipoCalle1Id(),localCliente.get_calle1(),
	    			localCliente.get_entreTipoCalle2Id(),localCliente.get_calle2(),localCliente.get_edificio(),localCliente.get_edificioPiso(),
	    			localCliente.get_edificioNumero(),localCliente.get_manzano(),localCliente.get_uv(),localCliente.get_nombreFactura(),
	    			localCliente.get_nit(),localCliente.get_razonSocial(),localCliente.get_contacto(),localCliente.is_tieneTelefono(),
	    			localCliente.get_telefono(),localCliente.get_paginaWeb(),localCliente.get_email(),localCliente.get_tipoNegocioId(),
	    			localCliente.get_zonaId(),localCliente.get_latitud(),localCliente.get_longitud(),localCliente.get_creadorId(),
	    			localCliente.get_latitudCreador(),localCliente.get_longitudCreador(),localCliente.get_tipoPagoId(),localCliente.get_diasPago(),
	    			localCliente.get_topeCredito(),localCliente.get_dia(),localCliente.get_mes(),localCliente.get_anio(),localCliente.get_hora(),
	    			localCliente.get_minuto(),localCliente.is_verificado(),localCliente.is_completo(),localCliente.is_sincronizacion(),localCliente.get_rutaId(),
	    			localCliente.get_rutaDiaId(),localCliente.get_mercadoId(),localCliente.is_activo(),localCliente.is_editado(),
	    			localCliente.get_tatId(),localCliente.get_tipoNit(),localCliente.is_clienteVisita(),localCliente.get_zonaMercadoId(),
	    			localCliente.get_a(),localCliente.get_b(),localCliente.get_c(),localCliente.get_d(),localCliente.get_e(),localCliente.get_f(),
	    			localCliente.get_g(),localCliente.get_h(),localCliente.get_i(),localCliente.get_j(),localCliente.get_k(),localCliente.get_l(),
	    			localCliente.get_m(),localCliente.get_n(),localCliente.get_o(),localCliente.get_p(),localCliente.get_q(),localCliente.get_r(),
	    			localCliente.get_secuenciaPreventa(),localCliente.get_secuenciaVenta(),localCliente.get_provinciaId(),localCliente.get_precioListaNombreId(),
	    			localCliente.get_ruta(),localCliente.get_tipoVisita(),localCliente.get_zonaVentaId(),localCliente.get_canalRutaId());
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
	    
	    if(newClienteRowId == 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el cliente.", 1);
	    	return null;
	    }
	    else
	    {
	    	btnInsertarCliente.setVisibility(View.INVISIBLE);
	    	tvFotoCategorias.setVisibility(View.VISIBLE);
	    	spnFotoCategorias.setVisibility(View.VISIBLE);
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "Cliente insertado en el dispositivo.", 1);
	    	
	    	if(rolVendedor || rolVendedorProvincia)
	    	{
	    		localCliente.set_clienteId((int)(newClienteRowId*(-1)));
	    		CopiarClienteAClientePreventa(localCliente);
	    	}
	    	
	    	if(rolDistribuidor)
	    	{
	    		localCliente.set_clienteId((int)newClienteRowId*(-1));
	    		CopiarClienteAClienteVenta(localCliente);
	    	}
	    	
	    	//Insertamos el nit del cliente
			long l = 0;
			try
			{
				l = new ClienteNitBLL().InsertarClienteNit(localCliente.get_clienteId(),localCliente.get_nombreFactura(),localCliente.get_nit(),
															loginEmpleado.get_empleadoId(), fecha.get_dia(),fecha.get_mes(),fecha.get_anio(),false,localCliente.get_tipoNit());
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los datos del nit al cliente preventa: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar los datos del nit al cliente preventa: " + localException.getMessage());
				} 
			}
			
			if(l<=0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar los datos del nit al clientePreventa.",1);
				return null;
			}
	    }
	    
	    return localCliente;
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
	
	private void CopiarClienteAClientePreventa(Cliente localCliente)
	{
		newClientePreventaRowId = 0;
		try
		{
			newClientePreventaRowId = new ClientePreventaBLL().InsertarClientePreventa(localCliente.get_clienteId(),localCliente.get_codigo(),
					localCliente.get_tipoNegocioId(),localCliente.get_nombreCompleto(),localCliente.get_latitud(),
					localCliente.get_longitud(),localCliente.get_direccion(),localCliente.get_telefono(),
					localCliente.get_celular(),parametroGeneral.get_listaPrecioId(),0,localCliente.get_tipoPagoId(),
					localCliente.get_diasPago(),0,rutaId,"",localCliente.get_razonSocial(),false,false,false,false,localCliente.get_nombres(),
					localCliente.get_paterno(),localCliente.get_materno(),localCliente.get_apellidoCasada(),localCliente.get_tipoNegocioId(),
					localCliente.get_zonaId(),localCliente.get_nombreFactura(),localCliente.get_nit(),diaRutaId,
					localCliente.get_referencia(),0,localCliente.get_ci(),localCliente.get_contacto(),0,localCliente.get_provinciaId(),
					localCliente.get_precioListaNombreId(),localCliente.get_ruta(),localCliente.get_tipoVisita(),localCliente.get_zonaVentaId(),
					"","",0,"",false);
			
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar al clientePreventa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el clientePreventa: " + localException.getMessage());
			} 
		}
		
		if(newClientePreventaRowId<=0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el clientePreventa.",1);
			return;
		}
	}
	
	private void CopiarClienteAClienteVenta(Cliente localCliente)
	{	
		newClienteVentaRowId = 0;
		try
		{
			newClienteVentaRowId = new ClienteVentaBLL().InsertarClienteVenta(localCliente.get_clienteId(),localCliente.get_codigo(),
					localCliente.get_tipoNegocioId(),localCliente.get_nombreCompleto(),localCliente.get_latitud(),
					localCliente.get_longitud(),localCliente.get_direccion(),localCliente.get_telefono(),
					localCliente.get_celular(),parametroGeneral.get_listaPrecioId(),0,localCliente.get_tipoPagoId(),
					localCliente.get_diasPago(),0,rutaId,"",localCliente.get_razonSocial(),false,false,false,false,
					localCliente.get_nombreFactura(),localCliente.get_nit(),diaRutaId,0,localCliente.get_provinciaId(), 
					localCliente.get_canalRutaId(), "", "");
			
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar al clienteVenta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el clienteVenta: " + localException.getMessage());
			} 
		}
		
		if(newClienteVentaRowId==0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el clienteVenta.",1);
			return;
		}
	}
	
	private class WSInsertarClienteSinDatos extends AsyncTask<Void, Integer, Boolean>
	{
		String CLIENTE_METHOD_NAME = "UNI_InsertClienteSinDatos";
		String CLIENTE_SOAP_ACTION = NAMESPACE + CLIENTE_METHOD_NAME;
		private Cliente _cliente;
		
		public WSInsertarClienteSinDatos(Cliente theCliente)
		{
			this._cliente = theCliente;
		}
		
		boolean WSInsertarClienteSinDatos = false;
		int resultadoInt = 0;
		String resultadoString = "";
		SoapObject soapObjects;
    
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
			localSoapObject.addProperty("creadorId", _cliente.get_creadorId());
			localSoapObject.addProperty("latitudCreador", String.valueOf(_cliente.get_latitudCreador()));
			localSoapObject.addProperty("longitudCreador", String.valueOf(_cliente.get_longitudCreador()));
			localSoapObject.addProperty("dia", _cliente.get_dia());
			localSoapObject.addProperty("mes", _cliente.get_mes());
			localSoapObject.addProperty("anio", _cliente.get_anio());
			localSoapObject.addProperty("hora", _cliente.get_hora());
			localSoapObject.addProperty("minuto", _cliente.get_minuto());
			localSoapObject.addProperty("tipoNegocioId", _cliente.get_tipoNegocioId());
			localSoapObject.addProperty("zonaId", _cliente.get_zonaId());
			localSoapObject.addProperty("nombreFactura", _cliente.get_nombreFactura());
			localSoapObject.addProperty("nit", _cliente.get_nit());
			localSoapObject.addProperty("nombres", _cliente.get_nombres());
			localSoapObject.addProperty("apellidoPaterno", _cliente.get_paterno());
			localSoapObject.addProperty("apellidoMaterno", _cliente.get_materno());
			localSoapObject.addProperty("apellidoCasada", _cliente.get_apellidoCasada());
			localSoapObject.addProperty("mercadoId", _cliente.get_mercadoId());
			localSoapObject.addProperty("tatId", _cliente.get_tatId());
			localSoapObject.addProperty("tipoNit", _cliente.get_tipoNit());
			localSoapObject.addProperty("celular", _cliente.get_celular());
			localSoapObject.addProperty("direccion", _cliente.get_direccion());
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
            //MarshalBase64 marshalBase64 = new MarshalBase64();
            //marshalBase64.register(localSoapSerializationEnvelope);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
            try
            {
            	localHttpTransportSE.call(CLIENTE_SOAP_ACTION,localSoapSerializationEnvelope);
            	
            	soapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
            	if(soapObjects!=null)
            	{
            		resultadoString = soapObjects.getPropertyAsString("Resultado");
            		resultadoInt = Integer.parseInt(soapObjects.getPropertyAsString("Id"));
            	}
                
                if (resultadoString.equals("OK") && resultadoInt > 0) 
                {
                	WSInsertarClienteSinDatos = true;
                }
                return true;
            }
            catch (Exception localException)
            {
            	resultadoString = "Error al ejecutar el WSInsertClienteSinDatos.";
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
			
			if (ejecutado)
			{
				if(WSInsertarClienteSinDatos)
				{
					long sincroCliente = 0;
					try
					{
						sincroCliente = new ClienteBLL().ModificarSincronizacionCliente((int)newClienteRowId, resultadoInt, true);
					}
					catch (Exception localException)
					{
						if (localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(), 1, "Error al insertarr el cliente: vacio");
						} 
						else 
						{
							myLog.InsertarLog("App",this.toString(), 1, "Error al insertar el cliente: " + localException.getMessage());
						}
					}
					
					if(sincroCliente<=0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el cliente.", 1);
			            return;
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "Cliente insertado en el servidor.", 1);
					}
					
					if(rolVendedor)
					{
						long sincroClientePreventa=0;
						try
						{
							sincroClientePreventa = new ClientePreventaBLL().
									ModificarClientePreventaSincronizacionDesdeVendedor((int)newClienteRowId*(-1),
																						resultadoInt,true);
						}
						catch(Exception localException)
						{

							if (localException.getMessage().isEmpty())
							{
								myLog.InsertarLog("App",this.toString(), 1, "Error al modificar la sincronizacion del clientePreventa desde el vendedor: vacio");
							} 
							else 
							{
								myLog.InsertarLog("App",this.toString(), 1, "Error al modificar la sincronizacion del clientePreventa desde el vendedor: " + localException.getMessage());
							}
						}
						
						if(sincroClientePreventa==0)
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacin del clientePreventa.", 1);
							return;
						}
						
					}
					
					if(rolDistribuidor)
					{
						long sincroClienteVenta=0;
						try
						{
							sincroClienteVenta = new ClienteVentaBLL().
									ModificarClienteVentaSincronizacionDesdeDistribuidor((int)newClienteRowId*(-1),
																						resultadoInt,true);
						}
						catch(Exception localException)
						{

							if (localException.getMessage().isEmpty())
							{
								myLog.InsertarLog("App",this.toString(), 1, "Error al modificar la sincronizacion del clienteVenta desde el vendedor: vacio");
							} 
							else 
							{
								myLog.InsertarLog("App",this.toString(), 1, "Error al modificar la sincronizacion del clienteVenta desde el vendedor: " + localException.getMessage());
							}
						}
						
						if(sincroClienteVenta==0)
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del clienteVenta.", 1);
							return;
						}
					}
					
					long sincroNit = 0;
					try
					{
						sincroNit = new ClienteNitBLL().ModificarClienteNitSincro((int)newClienteRowId*(-1), resultadoInt);
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
					
					ObtenerFotosCliente();
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), resultadoString, 1);
					
					ObtenerFotosCliente();			
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice InsertarCliente no se ejecuto correctamente.", 1);
				return;
			}
		}
	}
	
	private void ObtenerdetallesCliente()
	{
		theCliente =null;
	    try
	    {
	    	theCliente = new ClienteBLL().ObtenerClientePorRowId((int)newClienteRowId);
	    }
	    catch (Exception localException)
	    {
	    	if (localException.getMessage().isEmpty()) 
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los detalles del cliente por clienteId: vacio");
	        } 
	    	else 
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los detalles del cliente por clienteId: " + localException.getMessage());
	        }
	    }
	    
	    if (theCliente == null)
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del cliente.", 1);
	        return;
	    }
	}
	
	private void TomarFoto()
	{
	    Intent localIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	    if (localIntent.resolveActivity(getPackageManager()) != null) 
	    {
	      startActivityForResult(localIntent, REQUEST_IMAGE_CAPTURE);
	    }
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
	    byte[] foto=null;
	    
	    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK)
	    {
	    	foto = ConvertirBitmapAVectorDeBytes((Bitmap)data.getExtras().get("data"));
	    }
	    
	    ObtenerdetallesCliente();
	    
	    long l=0;
	    try
	    {
	       l = new ClienteFotoBLL().InsertarClienteFoto((int)newClienteRowId,theCliente.get_clienteId(),foto,false,categoriaId,0);
	    }
	    catch (Exception localException)
	    {
	    	if (localException.getMessage().isEmpty())
	        {
	    		myLog.InsertarLog("App", toString(), 1, "Error al insertar la foto del cliente: vacio");
	        }
	        else
	        {
	        	myLog.InsertarLog("App", toString(), 1, "Error al insertar la foto del cliente: " + localException.getMessage());
	        }
	    }
	    if (l <= 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la foto del cliente.", 1);
	    	return;
	    }
	    else
	    {
	    	ObtenerFotosCliente();
	    }
	}
	
	public static byte[] ConvertirBitmapAVectorDeBytes(Bitmap paramBitmap)
	{
	    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
	    paramBitmap.compress(Bitmap.CompressFormat.PNG, 0, localByteArrayOutputStream);
	    return localByteArrayOutputStream.toByteArray();
	}

	private void InsertarFotos()
	{
		if(listadoClienteFoto.size() < 3)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "Debe introducir las 3 fotos requeridas (Especificadas en las categorias de las fotos).", 1);
			return;
		}
		
		if(parametroGeneral.is_sincronizarWifi())
		{
			if (theUtilidades.VerificarConexionInternetWifi(this))
		    {
				int i = 0;
				for(ClienteFoto item : listadoClienteFoto)
				{
					if(item.get_clienteIdServer() != 0)
					{
						i++;
						InsertarFoto(item.get_foto(),item.get_id(),i,listadoClienteFoto.size(),item.get_fotoCategoriaId());
					}
					else
			        {
						theUtilidades.MostrarMensaje(getApplicationContext(),"Primero debe sincronizar el cliente.", 1);
						MostrarPantallaMenuCensista();
			        }	
				}
		    }
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No esta conectado a una red WiFi para sincronizar las fotos.", 1);
			    MostrarPantallaMenuCensista();
			}
		}
		else
		{
			if(theUtilidades.VerificarConexionInternet(this))
			{
				int i = 0;
				for(ClienteFoto item : listadoClienteFoto)
				{
					if(item.get_clienteIdServer() != 0)
					{
						i++;
						InsertarFoto(item.get_foto(),item.get_id(),i,listadoClienteFoto.size(),item.get_fotoCategoriaId());
					}
					else
			        {
						theUtilidades.MostrarMensaje(getApplicationContext(),"Primero debe sincronizar el cliente.", 1);
						MostrarPantallaMenuCensista();
			        }	
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se encuentra conectado a internet, intentelo mas tarde..", 1);
				MostrarPantallaMenuCensista();
			}
		}
	}

	private void InsertarFoto(byte[] foto, int rowId, int posicionActual, int posicionFinal,int fotoCategoriaId)
	{
	    pdInsertarFoto = new ProgressDialog(this);
	    pdInsertarFoto.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdInsertarFoto.setMessage("Insertando imagen ...");
	    pdInsertarFoto.setCancelable(false);
	    pdInsertarFoto.setCanceledOnTouchOutside(false);

	    WSInsertarFoto wsInsertarFoto = new WSInsertarFoto(foto,rowId,posicionActual,posicionFinal,fotoCategoriaId);
	    try
	    {
	    	wsInsertarFoto.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
        		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar WSInsertarFoto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar WSInsertarFoto: " + localException.getMessage());
			}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertarFoto.", 1);
	    }
	}
	
	private class WSInsertarFoto extends AsyncTask<Void, Integer, Boolean>
	{
		String INSERTFOTO_METHOD_NAME = "InsertFotoCliente";
		String INSERTFOTO_SOAP_ACTION = NAMESPACE + INSERTFOTO_METHOD_NAME;
		
		boolean WSInsertarFoto = false;
		private byte[] _foto;
		private int _posicionActual;
		private int _posicionFinal;
		private int _fotoCategoriaId;
		private int _rowId;
		int intResultado;
		String stringResultado;
		SoapObject soapObjects;
    
	    public WSInsertarFoto(byte[] foto, int rowId, int posicionActual, int posicionFinal, int fotoCategoriaId)
	    {
	    	this._foto = foto;
	    	this._rowId = rowId;
	    	this._posicionActual = posicionActual;
	    	this._posicionFinal = posicionFinal;
	    	this._fotoCategoriaId = fotoCategoriaId;
	    }

	    protected void onPreExecute()
	    {
	    	pdInsertarFoto.show();
	    }
    
	    protected Boolean doInBackground(Void... paramVarArgs)
	    {
	    	WSInsertarFoto = false;
			
			SoapObject localSoapObject = new SoapObject(NAMESPACE,INSERTFOTO_METHOD_NAME);
			localSoapObject.addProperty("clienteId",theCliente.get_clienteId());
			localSoapObject.addProperty("imagen", this._foto);
			localSoapObject.addProperty("fileSize", this._foto.length);
			localSoapObject.addProperty("categoriaId", this._fotoCategoriaId);
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			MarshalBase64 marshalBase64 = new MarshalBase64();
			marshalBase64.register(localSoapSerializationEnvelope);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(INSERTFOTO_SOAP_ACTION, localSoapSerializationEnvelope);
				
				soapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
				
				if(soapObjects != null)
				{
					intResultado = Integer.parseInt(soapObjects.getPropertyAsString("Id"));
					stringResultado = soapObjects.getPropertyAsString("Resultado");					
				}
				
				if(stringResultado.equals("OK") && (intResultado > 0)) 
				{
					WSInsertarFoto = true;
				}
				return true;
			}
			catch (Exception localException)
			{
				WSInsertarFoto = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSInsertFoto: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSInsertFoto: " + localException.getMessage());
				}
				return true;
			}		
	    }
    
	    protected void onPostExecute(Boolean ejecutado)
	    {
	    	if(pdInsertarFoto.isShowing())
			{
				pdInsertarFoto.dismiss();
			}
			
			if (ejecutado)
			{
				if(WSInsertarFoto)
				{
					int i = 0;
					try
					{
						i = new ClienteFotoBLL().ModificarSincronizacionClienteFoto(this._rowId, true);
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion cliente foto: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion cliente foto: " + localException.getMessage());
						}
					}
					if (i == 0) 
					{
						theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo modificar el estado de sincronizacion de la foto cliente.", 1);
						return;
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(),"Foto_" + String.valueOf(this._posicionActual) + " insertada en el servidor.", 1);
					}
	      
					if (this._posicionActual == this._posicionFinal)
					{
						MostrarPantallaMenuCensista();
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la foto en el servidor.", 1);
			        MostrarPantallaMenuCensista();
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "Error al ejecutar el WSInsertFoto.", 1);
			}
		}
	}
		
	private void MostrarPantallaMenuCensista()
	{
		Intent localIntent = new Intent(this, Menucensista.class);
	    localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    localIntent.putExtra("Origen", Origen);
	    startActivity(localIntent);
	}
	 
	@Override
	public void onBackPressed() 
	{
		if(listadoClienteFoto == null && !btnInsertarCliente.isShown())
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "Debe sacar al menos 1 fotografia al cliente.", 1);
			return;
		}
		else
		{
			Intent intent = new Intent(this,Menucensista.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("Origen", Origen);
			startActivity(intent);
		}
	}

}