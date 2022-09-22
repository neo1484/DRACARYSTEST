package com.detesim.venderunilever;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import BLL.CiudadBLL;
import BLL.ClienteBLL;
import BLL.ClienteFotoBLL;
import BLL.ClientePreventaBLL;
import BLL.ClienteVentaBLL;
import BLL.FotoCategoriaBLL;
import BLL.MercadoBLL;
import BLL.MyLogBLL;
import BLL.ParametroGeneralBLL;
import BLL.PrecioListaNombreBLL;
import BLL.ProvinciaBLL;
import BLL.RolBLL;
import BLL.TipoNegocioBLL;
import BLL.TipoNitBLL;
import BLL.ZonaBLL;
import BLL.ZonaMercadoBLL;
import Clases.Ciudad;
import Clases.Cliente;
import Clases.ClienteFoto;
import Clases.FotoCategoria;
import Clases.LoginEmpleado;
import Clases.Mercado;
import Clases.ParametroGeneral;
import Clases.PrecioListaNombre;
import Clases.Provincia;
import Clases.Rol;
import Clases.TipoNegocio;
import Clases.TipoNit;
import Clases.TipoPago;
import Clases.VendedorZonaVenta;
import Clases.Zona;
import Clases.ZonaMercado;
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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Censistaeditarcliente extends Activity implements OnClickListener
{
	static final int REQUEST_IMAGE_CAPTURE = 1;
	Cliente theCliente;
	ArrayList<ClienteFoto> listadoClienteFoto;
	
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	MyLogBLL myLog = new MyLogBLL();
	
	LoginEmpleado loginEmpleado;
	int clienteId;
	String Origen;
	String peticion;
	String nombres;
	String paterno;
	String materno;
	String celular;
	String ci;
	String tipoNit;
	String direccion;
	double latitud;
	double longitud;
	String nombreFactura;
	String nit;
	boolean activo;
	int tipoNegocioId;
	int categoriaId;
	int provinciaId;
	int tipoPagoId;
	int precioListaId;
	int zonaId;
	int mercadoId;
	int zonaMercadoId;
	int ciudadId;
	int tipoNitId;
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
	float secuenciaPreventa;
	float secuenciaVenta;
	boolean actualizoCliente=false;
	
	ArrayList<Rol> listadoRol;
	ArrayList<TipoNegocio> listadoTipoNegocio;
	ArrayList<FotoCategoria> listadoFotoCategoria;
	ParametroGeneral parametroGeneral;
	ArrayList<VendedorZonaVenta> listadoVendedorZonaVenta;

	LinearLayout llCencistaEdicionCliente;
	EditText etNombres;
	EditText etPaterno;
	EditText etMaterno;
	EditText etCi;
	EditText etCelular;
	Spinner spnCiudad;
	Spinner spnProvincia;
	TextView tvZona;
	TextView tvZonaMercado;
	Spinner spnZona;
	Spinner spnMercado;
	Spinner spnZonaMercado;
	EditText etDireccion;
	TextView tvTipoNegocio;
	Spinner spnTipoNegocio;
	Spinner spnTipoPago;
	TextView tvPrecioLista;
	Spinner spnPrecioLista;
	Spinner spnTipoNit;
	EditText etNombreFactura;
	EditText etNit;
	EditText etLatitud;
	EditText etLongitud;
	ImageView btnCoordenadas;
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
	Switch swActivo;
	Button btnActualizar;
	TextView tvFotoCategorias;
	Spinner spnFotoCategorias;
	Button btnTomarFoto;
	ListView lvFotos;
	Button btnInsertarFotos;
	ProgressDialog pdUpdateCliente;
	ProgressDialog pdInsertarFoto;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cencistaeditarcliente);
		
		llCencistaEdicionCliente = (LinearLayout)findViewById(R.id.llCensistaEdicionCliente);
		etNombres = (EditText)findViewById(R.id.etCencistaEdicionClienteNombres);
		etPaterno = (EditText)findViewById(R.id.etCencistaEdicionClientePaterno);
		etMaterno = (EditText)findViewById(R.id.etCencistaEdicionClienteMaterno);
		etCi = (EditText)findViewById(R.id.etCencistaEdicionClienteCi);
		etCelular = (EditText)findViewById(R.id.etCencistaEdicionClienteCelular);
		spnCiudad = (Spinner)findViewById(R.id.spnCencistaEdicionClienteCiudad);
		spnProvincia = (Spinner)findViewById(R.id.spnCencistaEdicionClienteProvincia);
		tvZona = (TextView)findViewById(R.id.tvCencistaEdicionClienteZona);
		spnZona = (Spinner)findViewById(R.id.spnCencistaEdicionClienteZona);
		tvZonaMercado = (TextView)findViewById(R.id.tvCencistaEdicionClienteZonaMercado);
		spnMercado = (Spinner)findViewById(R.id.spnCencistaEdicionClienteMercado);
		spnZonaMercado = (Spinner)findViewById(R.id.spnCencistaEdicionClienteZonaMercado);
		etDireccion = (EditText)findViewById(R.id.etCencistaEdicionClienteDireccion);
		tvTipoNegocio = (TextView)findViewById(R.id.tvMapaNombreClientePromedioVentaMensual);
		spnTipoNegocio = (Spinner)findViewById(R.id.spnCencistaEdicionClienteTipoCliente);
		spnTipoPago = (Spinner)findViewById(R.id.spnCencistaEdicionClienteTipoPago);
		tvPrecioLista = (TextView)findViewById(R.id.tvCencistaEdicionClientePrecioLista);
		spnPrecioLista = (Spinner)findViewById(R.id.spnCencistaEdicionClientePrecioLista);
		spnTipoNit = (Spinner)findViewById(R.id.spnCencistaEdicionClienteTipoNit);
		etNombreFactura = (EditText)findViewById(R.id.etCencistaEdicionClienteNombreFactura);
		etNit = (EditText)findViewById(R.id.etCencistaEdicionClienteNit);
		etLatitud = (EditText)findViewById(R.id.etCencistaEdicionClienteLatitud);
		etLongitud = (EditText)findViewById(R.id.etCencistaEdicionClienteLongitud);
		btnCoordenadas = (ImageView)findViewById(R.id.ivCencistaEdicionClienteEditarCoordenadas);
		btnCoordenadas.setOnClickListener(this);
		tvDiasVisita = (TextView)findViewById(R.id.tvCenEdiCliDiasVisita);
		cbA = (CheckBox)findViewById(R.id.cbCenEdiCliA);
		cbA.setOnClickListener(this);
		cbB = (CheckBox)findViewById(R.id.cbCenEdiCliB);
		cbB.setOnClickListener(this);
		cbC = (CheckBox)findViewById(R.id.cbCenEdiCliC);
		cbC.setOnClickListener(this);
		cbD = (CheckBox)findViewById(R.id.cbCenEdiCliD);
		cbD.setOnClickListener(this);
		cbE = (CheckBox)findViewById(R.id.cbCenEdiCliE);
		cbE.setOnClickListener(this);
		cbF = (CheckBox)findViewById(R.id.cbCenEdiCliF);
		cbF.setOnClickListener(this);
		cbG = (CheckBox)findViewById(R.id.cbCenEdiCliG);
		cbG.setOnClickListener(this);
		cbH = (CheckBox)findViewById(R.id.cbCenEdiCliH);
		cbH.setOnClickListener(this);
		cbI = (CheckBox)findViewById(R.id.cbCenEdiCliI);
		cbI.setOnClickListener(this);
		cbJ = (CheckBox)findViewById(R.id.cbCenEdiCliJ);
		cbJ.setOnClickListener(this);
		cbK = (CheckBox)findViewById(R.id.cbCenEdiCliK);
		cbK.setOnClickListener(this);
		cbL = (CheckBox)findViewById(R.id.cbCenEdiCliL);
		cbL.setOnClickListener(this);
		cbM = (CheckBox)findViewById(R.id.cbCenEdiCliM);
		cbM.setOnClickListener(this);
		cbN = (CheckBox)findViewById(R.id.cbCenEdiCliN);
		cbN.setOnClickListener(this);
		cbO = (CheckBox)findViewById(R.id.cbCenEdiCliO);
		cbO.setOnClickListener(this);
		cbP = (CheckBox)findViewById(R.id.cbCenEdiCliP);
		cbP.setOnClickListener(this);
		cbQ = (CheckBox)findViewById(R.id.cbCenEdiCliQ);
		cbQ.setOnClickListener(this);
		cbR = (CheckBox)findViewById(R.id.cbCenEdiCliR);
		cbR.setOnClickListener(this);
		tvSecuenciaPreventa = (TextView)findViewById(R.id.tvCenEdiCliSecuenciaPreventa);
		etSecuenciaPreventa = (EditText)findViewById(R.id.etCenEdiCliSecuenciaPreventa);
		tvSecuenciaVenta = (TextView)findViewById(R.id.tvCenEdiCliSecuenciaVenta);
		etSecuenciaVenta = (EditText)findViewById(R.id.etCenEdiCliSecuenciaVenta);
		swActivo = (Switch)findViewById(R.id.swCencistaEdicionClienteActivo);
		btnActualizar = (Button)findViewById(R.id.btnCencistaEdicionClienteActualizar);
		btnActualizar.setOnClickListener(this);
		tvFotoCategorias = (TextView)findViewById(R.id.tvCenEdiCliCategoriaFoto);
		spnFotoCategorias = (Spinner)findViewById(R.id.spnCenEdiCliCategoriasFoto);
		btnTomarFoto = (Button)findViewById(R.id.btnCenEdiCliTomarFoto);
		btnTomarFoto.setOnClickListener(this);
		lvFotos = (ListView)findViewById(R.id.lvCenEdiCliFotos);
		btnInsertarFotos = (Button)findViewById(R.id.btnCenEdiCliInsertarFoto);
		btnInsertarFotos.setOnClickListener(this);
		
		llCencistaEdicionCliente.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
		Bundle localBundle = getIntent().getExtras();
		
		Origen = "";
		Origen = localBundle.getString("Origen");
		if (Origen == "") 
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el origen de la peticion.", 1);
			return;
		}
		
		clienteId = 0;
		clienteId = localBundle.getInt("clienteId");
		if(clienteId == 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del cliente.", 2);
	    	return;
	    }
		
		peticion = "";
		peticion = localBundle.getString("peticion");
		if (peticion == "") 
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo establecer el origen peticion.", 1);
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
	    else
	    {
	    	btnInsertarFotos.setVisibility(View.INVISIBLE);
	    	MostrarControlesFoto(false);
	    	
	    	if(CargarParametroGeneral())
	    	{
	    		CargarInformacion();
	    	
	    		if(peticion.equals("Mapa"))
		    	{
		    		CargarDatosCliente();
		    	}
		    	else
		    	{
		    		etNombres.setText(localBundle.getString("nombres"));
					etPaterno.setText(localBundle.getString("paterno"));
					etMaterno.setText(localBundle.getString("materno"));
					etCi.setText(localBundle.getString("ci"));
					etCelular.setText(localBundle.getString("celular"));
					ciudadId = localBundle.getInt("ciudadId");
					provinciaId = localBundle.getInt("provinciaId");
					for(int i = 0; i < spnProvincia.getCount(); i++)
					{
						if(((Provincia)spnProvincia.getItemAtPosition(i)).get_provinciaId() == provinciaId)
						{
							spnProvincia.setSelection(i);
						}
					}
					
					zonaId = localBundle.getInt("zonaId");
					for(int i = 0; i < spnZona.getCount(); i++)
					{
						if(((Zona)spnZona.getItemAtPosition(i)).get_zonaId() == zonaId)
						{
							spnZona.setSelection(i);
						}
					}
					
					mercadoId = localBundle.getInt("mercadoId");
					for(int i = 0; i < spnMercado.getCount(); i++)
					{
						if(((Mercado)spnMercado.getItemAtPosition(i)).get_mercadoId() == mercadoId)
						{
							spnMercado.setSelection(i);
						}
					}
					
					zonaMercadoId = localBundle.getInt("zonaMercadoId");
					for(int i = 0; i < spnZonaMercado.getCount(); i++)
					{
						if(((ZonaMercado)spnZonaMercado.getItemAtPosition(i)).get_zonaMercadoId() == zonaMercadoId)
						{
							spnZonaMercado.setSelection(i);
						}
					}
					
					etDireccion.setText(localBundle.getString("direccion"));
					
					tipoNegocioId = localBundle.getInt("tipoNegocioId");
					for (int i = 0; i < spnTipoNegocio.getCount(); i++) 
			        {
			            if (((TipoNegocio)spnTipoNegocio.getItemAtPosition(i)).get_tipoNegocioId() == tipoNegocioId) 
			            {
			            	spnTipoNegocio.setSelection(i);
			            }
			        }
					
					precioListaId = localBundle.getInt("precioListaId");
					for (int i = 0; i < spnPrecioLista.getCount(); i++) 
			        {
			            if (((PrecioListaNombre)spnPrecioLista.getItemAtPosition(i)).get_precioListaNombreId() == precioListaId) 
			            {
			            	spnPrecioLista.setSelection(i);
			            }
			        }
					
					tipoPagoId = localBundle.getInt("tipoPagoId");
					for (int i = 0; i < spnTipoPago.getCount(); i++) 
			        {
			            if (((TipoPago)spnTipoPago.getItemAtPosition(i)).get_tipoPagoId() == tipoPagoId) 
			            {
			            	spnTipoPago.setSelection(i);
			            }
			        }
					
					tipoNit = localBundle.getString("tipoNit");
					for (int i = 0; i < spnTipoNit.getCount(); i++) 
			        {
			            if (((TipoNit)spnTipoNit.getItemAtPosition(i)).get_tipoNit().equals(tipoNit)) 
			            {
			            	spnTipoNit.setSelection(i);
			            }
			        }
					
					etNombreFactura.setText(localBundle.getString("nombreFactura"));
					etNit.setText(localBundle.getString("nit"));
					etLatitud.setText(String.valueOf(localBundle.getDouble("latitud")));
					etLatitud.setTextColor(Color.GREEN);
					etLongitud.setText(String.valueOf(localBundle.getDouble("longitud")));
					etLongitud.setTextColor(Color.GREEN);
					cbAVal = localBundle.getInt("a");
					cbBVal = localBundle.getInt("b");
					cbCVal = localBundle.getInt("c");
					cbDVal = localBundle.getInt("d");
					cbEVal = localBundle.getInt("e");
					cbFVal = localBundle.getInt("f");
					cbGVal = localBundle.getInt("g");
					cbHVal = localBundle.getInt("h");
					cbIVal = localBundle.getInt("i");
					cbJVal = localBundle.getInt("j");
					cbKVal = localBundle.getInt("k");
					cbLVal = localBundle.getInt("l");
					cbMVal = localBundle.getInt("m");
					cbNVal = localBundle.getInt("n");
					cbOVal = localBundle.getInt("o");
					cbPVal = localBundle.getInt("p");
					cbQVal = localBundle.getInt("q");
					cbRVal = localBundle.getInt("r");
					etSecuenciaPreventa.setText(String.valueOf(localBundle.getInt("secuenciaPreventa")));
					etSecuenciaVenta.setText(String.valueOf(localBundle.getInt("secuenciaVenta")));
					swActivo.setChecked(localBundle.getBoolean("activo"));

					DesplegarControlesClienteVisita(cbAVal,cbBVal,cbCVal,cbDVal,cbEVal,cbFVal,cbGVal,cbHVal,cbIVal,cbJVal,cbKVal,cbLVal,cbMVal,cbNVal,cbOVal,cbPVal,cbQVal,cbRVal);

		    	}
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los parametros generales.", 1);
	    	}
   	    }
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.ivCencistaEdicionClienteEditarCoordenadas:
				MostrarPantallaMapaEdicionCoordenadas();
			break;
		case R.id.btnCencistaEdicionClienteActualizar:
			if(ValidacionDatos())
			{
				ActualizarClienteDispositivo();
			}
			break;
		case R.id.btnCenEdiCliTomarFoto:
			if(categoriaId > 0)
			{
				TomarFoto();
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "Debe seleccionar una categoria para clasificar la foto.", 1);
			}
			break;
		case R.id.btnCenEdiCliInsertarFoto:
			InsertarFotos();
			break;
		
		case R.id.cbCenEdiCliA:
			if(cbA.isChecked())
			{
				cbAVal = 1;
			}
			else
			{
				cbAVal = 0;
			}
			break;
			
		case R.id.cbCenEdiCliB:
			if(cbB.isChecked())
			{
				cbBVal = 1;
			}
			else
			{
				cbBVal = 0;
			}
			break;
			
		case R.id.cbCenEdiCliC:
			if(cbC.isChecked())
			{
				cbCVal = 1;
			}
			else
			{
				cbCVal = 0;
			}
			break;
			
		case R.id.cbCenEdiCliD:
			if(cbD.isChecked())
			{
				cbDVal = 1;
			}
			else
			{
				cbDVal = 0;
			}
			break;
			
		case R.id.cbCenEdiCliE:
			if(cbE.isChecked())
			{
				cbEVal = 1;
			}
			else
			{
				cbEVal = 0;
			}
			break;
			
		case R.id.cbCenEdiCliF:
			if(cbF.isChecked())
			{
				cbFVal = 1;
			}
			else
			{
				cbFVal = 0;
			}
			break;
			
		case R.id.cbCenEdiCliG:
			if(cbG.isChecked())
			{
				cbGVal = 1;
			}
			else
			{
				cbGVal = 0;
			}
			break;
			
		case R.id.cbCenEdiCliH:
			if(cbH.isChecked())
			{
				cbHVal = 1;
			}
			else
			{
				cbHVal = 0;
			}
			break;
			
		case R.id.cbCenEdiCliI:
			if(cbI.isChecked())
			{
				cbIVal = 1;
			}
			else
			{
				cbIVal = 0;
			}
			break;
			
		case R.id.cbCenEdiCliJ:
			if(cbJ.isChecked())
			{
				cbJVal = 1;
			}
			else
			{
				cbJVal = 0;
			}
			break;
			
		case R.id.cbCenEdiCliK:
			if(cbK.isChecked())
			{
				cbKVal = 1;
			}
			else
			{
				cbKVal = 0;
			}
			break;
			
		case R.id.cbCenEdiCliL:
			if(cbL.isChecked())
			{
				cbLVal = 1;
			}
			else
			{
				cbLVal = 0;
			}
			break;
			
		case R.id.cbCenEdiCliM:
			if(cbM.isChecked())
			{
				cbMVal = 1;
			}
			else
			{
				cbMVal = 0;
			}
			break;
			
		case R.id.cbCenEdiCliN:
			if(cbN.isChecked())
			{
				cbNVal = 1;
			}
			else
			{
				cbNVal = 0;
			}
			break;
			
		case R.id.cbCenEdiCliO:
			if(cbO.isChecked())
			{
				cbOVal = 1;
			}
			else
			{
				cbOVal = 0;
			}
			break;
			
		case R.id.cbCenEdiCliP:
			if(cbP.isChecked())
			{
				cbPVal = 1;
			}
			else
			{
				cbPVal = 0;
			}
			break;
			
		case R.id.cbCenEdiCliQ:
			if(cbQ.isChecked())
			{
				cbQVal = 1;
			}
			else
			{
				cbQVal = 0;
			}
			break;
		}		
	}
	
	private void CargarInformacion()
	{
		DesplegarControlesClienteVisita(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		CargarClasificacionesFoto();
		CargarCiudades();
		CargarProvincias();
		CargarTiposPago();
		
		CargarTipoNegocio();
		if(parametroGeneral.is_modificarTipoNegocio())
		{
			spnTipoNegocio.setEnabled(true);
	    }
	    else
	    {
	    	spnTipoNegocio.setEnabled(false);
	    }
		
		if(parametroGeneral.is_zonaRequerida())
		{
			CargarZonas();
			DesplegarControlesZona(true);
		}
		else
		{
			DesplegarControlesZona(false);
		}
	    
		CargarMercados();
		
		if(parametroGeneral.is_zonaMercadoRequerida())
		{
			CargarZonasMercado();
			DesplegarControlesZonasMercado(true);
		}
		else
		{
			DesplegarControlesZonasMercado(false);
		}
		
		
		if(parametroGeneral.is_mostrarListaPrecio())
		{
			CargarListasPrecio();
	    	DesplegarListaDePrecio(true);
	    }
	    else
	    {
	    	DesplegarListaDePrecio(false);
	    }
		
		CargarTiposNit();
		if(parametroGeneral.is_mostrarSecuenciaVisita())
		{
			CargarSecuenciaVisita();
			DesplegarSecuenciasVista(true);
		}
		else
		{
			DesplegarSecuenciasVista(false);
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
		spnZona .setVisibility(visibility);
	}
	
	private void DesplegarControlesZonasMercado(boolean estado)
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
	
	private void DesplegarListaDePrecio(boolean estado)
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
	
	private void DesplegarSecuenciasVista(boolean estado)
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
		
		tvSecuenciaPreventa.setVisibility(visibility);
		etSecuenciaPreventa.setVisibility(visibility);
		tvSecuenciaVenta.setVisibility(visibility);
		etSecuenciaVenta.setVisibility(visibility);
	}
		
	private void DesplegarControlesClienteVisita(int A, int B, int C, int D, int E, int F, int G, int H, int I, int J, int K, int L,
												int M, int N, int O, int P, int Q, int R)
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
		
		if(String.valueOf(A).equals("1"))
		{
			cbA.setEnabled(true);
			cbA.setChecked(true);
			cbA.setTextColor(Color.RED);
			cbAVal = 1;
		}
		
		if(String.valueOf(B).equals("1"))
		{
			cbB.setEnabled(true);
			cbB.setChecked(true);
			cbB.setTextColor(Color.RED);
			cbBVal = 1;
		}
		
		if(String.valueOf(C).equals("1"))
		{
			cbC.setEnabled(true);
			cbC.setChecked(true);
			cbC.setTextColor(Color.RED);
			cbCVal = 1;
		}
		
		if(String.valueOf(D).equals("1"))
		{
			cbD.setEnabled(true);
			cbD.setChecked(true);
			cbD.setTextColor(Color.RED);
			cbDVal = 1;
		}
		
		if(String.valueOf(E).equals("1"))
		{
			cbE.setEnabled(true);
			cbE.setChecked(true);
			cbE.setTextColor(Color.RED);
			cbEVal = 1;
		}
		
		if(String.valueOf(F).equals("1"))
		{
			cbF.setEnabled(true);
			cbF.setChecked(true);
			cbF.setTextColor(Color.RED);
			cbFVal = 1;
		}
		
		if(String.valueOf(G).equals("1"))
		{
			cbG.setEnabled(true);
			cbG.setChecked(true);
			cbG.setTextColor(Color.RED);
			cbGVal = 1;
		}
		
		if(String.valueOf(H).equals("1"))
		{
			cbH.setEnabled(true);
			cbH.setChecked(true);
			cbH.setTextColor(Color.RED);
			cbHVal = 1;
		}
		if(String.valueOf(I).equals("1"))
		{
			cbI.setEnabled(true);
			cbI.setChecked(true);
			cbI.setTextColor(Color.RED);
			cbIVal = 1;
		}
		
		if(String.valueOf(J).equals("1"))
		{
			cbJ.setEnabled(true);
			cbJ.setChecked(true);
			cbJ.setTextColor(Color.RED);
			cbJVal = 1;
		}
		
		if(String.valueOf(K).equals("1"))
		{
			cbK.setEnabled(true);
			cbK.setChecked(true);
			cbK.setTextColor(Color.RED);
			cbKVal = 1;
		}
		
		if(String.valueOf(L).equals("1"))
		{
			cbL.setEnabled(true);
			cbL.setChecked(true);
			cbL.setTextColor(Color.RED);
			cbLVal = 1;
		}
		
		if(String.valueOf(M).equals("1"))
		{
			cbM.setEnabled(true);
			cbM.setChecked(true);
			cbM.setTextColor(Color.RED);
			cbMVal = 1;
		}
		
		if(String.valueOf(N).equals("1"))
		{
			cbN.setEnabled(true);
			cbN.setChecked(true);
			cbN.setTextColor(Color.RED);
			cbNVal = 1;
		}
		
		if(String.valueOf(O).equals("1"))
		{
			cbO.setEnabled(true);
			cbO.setChecked(true);
			cbO.setTextColor(Color.RED);
			cbOVal = 1;
		}
		
		if(String.valueOf(P).equals("1"))
		{
			cbP.setEnabled(true);
			cbP.setChecked(true);
			cbP.setTextColor(Color.RED);
			cbPVal = 1;
		}
		
		if(String.valueOf(Q).equals("1"))
		{
			cbQ.setEnabled(true);
			cbQ.setChecked(true);
			cbQ.setTextColor(Color.RED);
			cbQVal = 1;
		}
		
		if(String.valueOf(R).equals("1"))
		{
			cbR.setEnabled(true);
			cbR.setChecked(true);
			cbR.setTextColor(Color.RED);
			cbRVal = 1;
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
	
	private boolean CargarTipoNegocio()
	{
		listadoTipoNegocio = null;
		
		try
		{
			listadoTipoNegocio = new TipoNegocioBLL().ObtenerTiposNegocio();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los tipos de negocio: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los tipos de negocio: " + localException.getMessage());
	    	}  
		}
		
		if(listadoTipoNegocio == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los tipos de negocio.", 1);
			return false;
		}
		else
		{
			ArrayAdapter<TipoNegocio> localArrayAdapter = new ArrayAdapter<TipoNegocio>(this,R.layout.disenio_spinner,listadoTipoNegocio);
	        spnTipoNegocio.setAdapter(localArrayAdapter);
	        
	        spnTipoNegocio.setOnItemSelectedListener(new OnItemSelectedListener() 
		    {	    	
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
				{
					tipoNegocioId = ((TipoNegocio)spnTipoNegocio.getSelectedItem()).get_tipoNegocioId();
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent){}
		    	
			});
	        
	        return true;
		}
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
	        
	        spnZona.setOnItemSelectedListener(new OnItemSelectedListener() 
		    {	    	
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
				{
					zonaId = ((Zona)spnZona.getSelectedItem()).get_zonaId();
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent){}
		    	
			});
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
	    
	    if(listadoMercado.size() > 0)
	    {
	    	ArrayAdapter<Mercado> localArrayAdapter = new ArrayAdapter<Mercado>(this,R.layout.disenio_spinner,listadoMercado);
	    	spnMercado.setAdapter(localArrayAdapter);
	    	
	    	spnMercado.setOnItemSelectedListener(new OnItemSelectedListener() 
		    {	    	
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
				{
					mercadoId = ((Mercado)spnMercado.getSelectedItem()).get_mercadoId();
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent){}
		    	
			});
	    }
	    else
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron mercados que desplegar.", 1);
		    return;
	    } 
	}
	
	private void CargarZonasMercado()
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
	    	
	    	spnZonaMercado.setOnItemSelectedListener(new OnItemSelectedListener() 
		    {	    	
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
				{
					zonaMercadoId = ((ZonaMercado)spnZonaMercado.getSelectedItem()).get_zonaMercadoId();
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent){}
		    	
			});
	    }
	    else
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron zonas mercados que desplegar.", 1);
		    return;
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
							spnMercado.setVisibility(View.VISIBLE);
							spnZona.setVisibility(View.INVISIBLE);
						}
						else if (precioListaNombre.get_nombre().toUpperCase().equals("TDB"))
						{
							spnMercado.setVisibility(View.INVISIBLE);
							spnZona.setVisibility(View.VISIBLE);
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
	    spnTipoNit.setAdapter(localArrayAdapter);
	    
	    spnTipoNit.setOnItemSelectedListener(new OnItemSelectedListener() 
	    {	    	
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
			{
				tipoNit = ((TipoNit)spnTipoNit.getSelectedItem()).get_tipoNit();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent){}
	    	
		});
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

	private void MostrarControlesFoto(boolean estado)
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
	}
	
	private void CargarDatosCliente()
	{
		theCliente = null;
		
		try
		{
			theCliente = new ClienteBLL().ObtenerClientePor(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los detalles del cliente por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los detalles del cliente por clienteId: " + localException.getMessage());
	    	}  
		}
		
		if(theCliente == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del cliente.", 1);
		}
		else
		{					
			etNombres.setText(theCliente.get_nombres());
			etPaterno.setText(theCliente.get_paterno());
			etMaterno.setText(theCliente.get_materno());
			etCi.setText(theCliente.get_ci());
			etCelular.setText(theCliente.get_celular());
			etDireccion.setText(theCliente.get_direccion());
			etNombreFactura.setText(theCliente.get_nombreFactura());
			etNit.setText(theCliente.get_nit());
			etLatitud.setText(String.valueOf(theCliente.get_latitud()));
			etLongitud.setText(String.valueOf(theCliente.get_longitud()));
			swActivo.setChecked(theCliente.is_activo()?true:false);
			etSecuenciaPreventa.setText(String.valueOf(theCliente.get_secuenciaPreventa()));
			etSecuenciaVenta.setText(String.valueOf(theCliente.get_secuenciaVenta()));
			
			for(int i = 0; i < spnProvincia.getCount(); i++)
			{
				if(((Provincia)spnProvincia.getItemAtPosition(i)).get_provinciaId() == theCliente.get_provinciaId())
				{
					spnProvincia.setSelection(i);
				}
			}
			
			for(int i = 0; i < spnZona.getCount(); i++)
			{
				if(((Zona)spnZona.getItemAtPosition(i)).get_zonaId() == theCliente.get_zonaId())
				{
					spnZona.setSelection(i);
				}
			}
			
			for(int i = 0; i < spnMercado.getCount(); i++)
			{
				if(((Mercado)spnMercado.getItemAtPosition(i)).get_mercadoId() == theCliente.get_mercadoId())
				{
					spnMercado.setSelection(i);
				}
			}
			
			for(int i = 0; i < spnZonaMercado.getCount(); i++)
			{
				if(((ZonaMercado)spnZonaMercado.getItemAtPosition(i)).get_zonaMercadoId() == theCliente.get_zonaMercadoId())
				{
					spnZonaMercado.setSelection(i);
				}
			}
			
			for (int i = 0; i < spnTipoNegocio.getCount(); i++) 
	        {
	            if (((TipoNegocio)spnTipoNegocio.getItemAtPosition(i)).get_tipoNegocioId() == theCliente.get_tipoNegocioId()) 
	            {
	            	spnTipoNegocio.setSelection(i);
	            }
	        }
			
			for (int i = 0; i < spnTipoPago.getCount(); i++) 
	        {
	            if (((TipoPago)spnTipoPago.getItemAtPosition(i)).get_tipoPagoId() == theCliente.get_tipoPagoId()) 
	            {
	            	spnTipoPago.setSelection(i);
	            }
	        }
			
			for (int i = 0; i < spnPrecioLista.getCount(); i++) 
	        {
	            if (((PrecioListaNombre)spnPrecioLista.getItemAtPosition(i)).get_precioListaNombreId() == theCliente.get_precioListaNombreId()) 
	            {
	            	spnPrecioLista.setSelection(i);
	            }
	        }
			
			for (int i = 0; i < spnTipoNit.getCount(); i++) 
	        {
	            if (((TipoNit)spnTipoNit.getItemAtPosition(i)).get_tipoNit().equals(theCliente.get_tipoNit())) 
	            {
	            	spnTipoNit.setSelection(i);
	            }
	        }
			
			DesplegarControlesClienteVisita(theCliente.get_a(),theCliente.get_b(),theCliente.get_c(),theCliente.get_d(),theCliente.get_e(),theCliente.get_f(),
												theCliente.get_g(),theCliente.get_h(),theCliente.get_i(),theCliente.get_j(),theCliente.get_k(),theCliente.get_l(),
												theCliente.get_m(),theCliente.get_n(),theCliente.get_o(),theCliente.get_p(),theCliente.get_q(),theCliente.get_r());
		}
	}
	
	private boolean ValidacionDatos()
	{
		if(etNombres.getText().toString().isEmpty())
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "El nombre es requerido.", 1);
			return false;
		}
		
		if(etPaterno.getText().toString().isEmpty())
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "El paterno es requerido.", 1);
			return false;
		}
		
		if(etMaterno.getText().toString().isEmpty())
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "El materno es requerido.", 1);
			return false;
		}
		
		if(etCi.getText().toString().isEmpty())
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "El C.I. es requerido.", 1);
			return false;
		}
		
		if(etCelular.getText().toString().isEmpty())
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "El celular es requerido.", 1);
			return false;
		}
		
		if(parametroGeneral.is_provinciaRequerida())
		{
			if(((Provincia)spnProvincia.getSelectedItem()).get_provinciaId() == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "Debe seleccionar una provincia.", 1);
				return false;
			}
			else
			{
				provinciaId = ((Provincia)spnProvincia.getSelectedItem()).get_provinciaId();
			}
		}
		
		if(parametroGeneral.is_zonaRequerida())
		{
			if(spnZona.getVisibility() == View.VISIBLE)
			{
				if(((Zona)spnZona.getSelectedItem()).get_zonaId() == 0)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Debe seleccionar una zona.", 1);
					return false;
				}
				else
				{
					zonaId = ((Zona)spnZona.getSelectedItem()).get_zonaId(); 
				}
			}
		}
		
		if(parametroGeneral.is_mercadoRequerido())
		{
			if(spnMercado.getVisibility() == View.VISIBLE)
			{
				if(((Mercado)spnMercado.getSelectedItem()).get_mercadoId() == 0)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Debe seleccionar un mercado.", 1);
					return false;
				}
				else
				{
					mercadoId = ((Mercado)spnMercado.getSelectedItem()).get_mercadoId(); 
				}	
			}
		}
		
		if(parametroGeneral.is_zonaMercadoRequerida())
		{
			if(((ZonaMercado)spnZonaMercado.getSelectedItem()).get_zonaMercadoId() == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "Debe seleccionar una zona mercado.", 1);
				return false;
			}
			else
			{
				zonaMercadoId = ((ZonaMercado)spnZonaMercado.getSelectedItem()).get_zonaMercadoId(); 
			}
		}
		
		if(etDireccion.getText().toString().isEmpty())
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "La direccion es requerida.", 1);
			return false;
		}
		
		if(((TipoPago)spnTipoPago.getSelectedItem()).get_tipoPagoId() == 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "El tipo de pago es requerido.", 1);
			return false;
		}
		
		if(((TipoNit)spnTipoNit.getSelectedItem()).get_tipoNit().equals("Seleccione un tipo de NIT"))
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "El tipo de nit es requerido.", 1);
			return false;
		}
		else
		{
			tipoNit = ((TipoNit)spnTipoNit.getSelectedItem()).get_tipoNit(); 
		}
		
		if(etNombreFactura.getText().toString().isEmpty())
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "El nombre de la factura es requerida.", 1);
			return false;
		}
		
		if(etNit.getText().toString().isEmpty())
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "El nit de la factura es requerida.", 1);
			return false;
		}
		
		if(etLatitud.getText().toString().isEmpty())
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "La latitud es requerida.", 1);
			return false;
		}
		
		if(etLongitud.getText().toString().isEmpty())
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "La longitud es requerida.", 1);
			return false;
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
	    	return false;
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
	    	return false;
    	}
    	
    	if(rutasSemanales > 0 && rutasMensuales > 0)
    	{
    		theUtilidades.MostrarMensaje(getApplicationContext(), "Elija entre una ruta semanal o mensual", 1);
	    	return false;
    	}
    	
    	if(rutasQuincenales > 0 && rutasMensuales > 0)
    	{
    		theUtilidades.MostrarMensaje(getApplicationContext(), "Elija entre una ruta quincenal o mensual", 1);
	    	return false;
    	}
	    
		
		if(parametroGeneral.is_mostrarSecuenciaVisita())
    	{
    		if(etSecuenciaPreventa.getText().length()<=0)
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "Debe especificar una secuencia de visita preventa.", 1);
	    		return false;
	    	}
	    	else
	    	{
	    		secuenciaPreventa = Float.parseFloat(etSecuenciaPreventa.getText().toString());
	    	}
	    	
	    	if(etSecuenciaVenta.getText().length()<=0)
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "Debe especificar una secuencia de visita distribucion.", 1);
	    		return false;
	    	}
	    	else
	    	{
	    		secuenciaVenta = Float.parseFloat(etSecuenciaVenta.getText().toString());
	    	}
    	}
		
		nombres = etNombres.getText().toString();
		paterno = etPaterno.getText().toString();
		materno = etMaterno.getText().toString();
		ci = etCi.getText().toString();
		celular = etCelular.getText().toString();
		direccion = etDireccion.getText().toString();
		nombreFactura = etNombreFactura.getText().toString();
		nit = etNit.getText().toString();
		latitud = Double.parseDouble(etLatitud.getText().toString());
		longitud = Double.parseDouble(etLongitud.getText().toString());
		activo = swActivo.isChecked();
		return true;	
		
	}
	
	private void ActualizarClienteDispositivo()
	{
		long update = 0;
		try
		{
			update = new ClienteBLL().ModificarClienteDatosPorClienteId(clienteId, nombres, paterno, materno, ci, "", celular, direccion, "", 
												latitud, longitud,activo,tipoNegocioId, ci, provinciaId, zonaId, mercadoId, zonaMercadoId,
												tipoPagoId,precioListaId,tipoNit,nombreFactura,nit,cbAVal,cbBVal,cbCVal,cbDVal,cbEVal,cbFVal,
												cbGVal,cbHVal,cbIVal,cbJVal,cbKVal,cbLVal,cbMVal,cbNVal,cbOVal,cbPVal,cbQVal,cbRVal,
												secuenciaPreventa,secuenciaVenta);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al actualizar los datos del cliente: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al actualizar los datos del cliente: " + localException.getMessage());
	    	}  
		}
		
		if(update == 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo actualizar los datos del cliente.", 1);
			return;
		}
		else
		{
			actualizoCliente = true;
			UpdateClienteFromApk();
		}
	}
	
	private void UpdateClienteFromApk()
	{
		pdUpdateCliente = new ProgressDialog(this);
		pdUpdateCliente.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdUpdateCliente.setMessage("Actualizando Cliente ...");
		pdUpdateCliente.setCancelable(false);
		pdUpdateCliente.setCanceledOnTouchOutside(false);

		WSUpdateClienteFromApk wsUpdateClienteFromApk = new WSUpdateClienteFromApk();
	    try
	    {
	    	wsUpdateClienteFromApk.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
        		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar WSUpdateClienteFromApk: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar WSUpdateClienteFromApk: " + localException.getMessage());
			}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSUpdateClienteFromApk.", 1);
	    }
	}
	
	private class WSUpdateClienteFromApk extends AsyncTask<Void, Integer, Boolean>
	{
		String UPDATECLIENTE_METHOD_NAME = "UNI_UpdateClienteFromApk";
		String UPDATECLIENTE_SOAP_ACTION = NAMESPACE + UPDATECLIENTE_METHOD_NAME;
		
		boolean WSActualizarCliente = false;	
		int intResultado;
		String stringResultado;
		SoapObject soapObjects;
    
		protected void onPreExecute()
	    {
			pdUpdateCliente.show();
	    }
		
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSActualizarCliente = false;
			
			SoapObject localSoapObject = new SoapObject(NAMESPACE,UPDATECLIENTE_METHOD_NAME);
			localSoapObject.addProperty("clienteId", clienteId);
			localSoapObject.addProperty("nombres", nombres);
			localSoapObject.addProperty("paterno", paterno);
			localSoapObject.addProperty("materno", materno);
			localSoapObject.addProperty("apellidoCasada", "");
			localSoapObject.addProperty("telefono", "");
			localSoapObject.addProperty("celular", celular);
			localSoapObject.addProperty("direccion", direccion);
			localSoapObject.addProperty("referencia", "");
			localSoapObject.addProperty("latidud", String.valueOf(latitud));
			localSoapObject.addProperty("longitud", String.valueOf(longitud));
			localSoapObject.addProperty("estadoId", (activo)?1:2);
			localSoapObject.addProperty("tipoNegocioId", tipoNegocioId);
			localSoapObject.addProperty("ci", ci);
			localSoapObject.addProperty("provinciaId", provinciaId);
			localSoapObject.addProperty("zonaId", zonaId);
			localSoapObject.addProperty("mercadoId", mercadoId);
			localSoapObject.addProperty("zonaMercadoId", zonaMercadoId);
			localSoapObject.addProperty("tipoPagoId", tipoPagoId);
			localSoapObject.addProperty("precioListaId", precioListaId);
			localSoapObject.addProperty("tipoNit", tipoNit);
			localSoapObject.addProperty("nombreFactura", nombreFactura);
			localSoapObject.addProperty("nit", nit);
			localSoapObject.addProperty("a", (cbAVal==1)?true:false);
			localSoapObject.addProperty("b", (cbBVal==1)?true:false);
			localSoapObject.addProperty("c", (cbCVal==1)?true:false);
			localSoapObject.addProperty("d", (cbDVal==1)?true:false);
			localSoapObject.addProperty("e", (cbEVal==1)?true:false);
			localSoapObject.addProperty("f", (cbFVal==1)?true:false);
			localSoapObject.addProperty("g", (cbGVal==1)?true:false);
			localSoapObject.addProperty("h", (cbHVal==1)?true:false);
			localSoapObject.addProperty("i", (cbIVal==1)?true:false);
			localSoapObject.addProperty("j", (cbJVal==1)?true:false);
			localSoapObject.addProperty("k", (cbKVal==1)?true:false);
			localSoapObject.addProperty("l", (cbLVal==1)?true:false);
			localSoapObject.addProperty("m", (cbMVal==1)?true:false);
			localSoapObject.addProperty("n", (cbNVal==1)?true:false);
			localSoapObject.addProperty("o", (cbOVal==1)?true:false);
			localSoapObject.addProperty("p", (cbPVal==1)?true:false);
			localSoapObject.addProperty("q", (cbQVal==1)?true:false);
			localSoapObject.addProperty("r", (cbRVal==1)?true:false);
			localSoapObject.addProperty("secuenciaVenta",String.valueOf(secuenciaPreventa));
			localSoapObject.addProperty("secuenciaDistribucion",String.valueOf(secuenciaVenta));
						
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
					WSActualizarCliente = true;
				}
				return true;
			}
			catch (Exception localException)
			{
				WSActualizarCliente = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSUpdateClienteFromApk: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSUpdateClienteFromApk: " + localException.getMessage());
				}
				return true;
			}		
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdUpdateCliente.isShowing())
			{
				pdUpdateCliente.dismiss();
			}
			
			if (ejecutado)
			{
				if(WSActualizarCliente)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Cliente actualizado.", 1);
					
					if(ObtenerRoles())
					{
						for(Rol rol : listadoRol)
						{
							if(rol.get_rol().equals("Vendedor"))
							{
								if(!ActualizarClientePreventa())
								{
									theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el cliente Preventa.", 1);
								}
							}
							
							if(rol.get_rol().equals("Distribuidor"))
							{
								if(!ActualizarClienteVenta())
								{
									theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el cliente Venta.", 1);
								}
							}
						}
						
						ActualizarSincronizacionClienteEditado(true, true);
						MostrarControlesFoto(true);
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el rol del empleado.", 1);
						return;
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Cliente actualizado en el dispositivo.", 1);
					ActualizarSincronizacionClienteEditado(false,true);
			        MostrarControlesFoto(true);
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "Error al ejecutar el WSUpdateClienteFromApk.", 1);
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
	
	private boolean ActualizarClientePreventa()
	{
		long update = 0;
		try
		{
			update = new ClientePreventaBLL().ModificarClientePreventaDatos(clienteId, nombres, paterno, materno, ci, 
																			direccion, "", latitud, longitud, tipoNegocioId,"",celular);
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
	
	private boolean ActualizarClienteVenta()
	{
		long update = 0;
		try
		{
			update = new ClienteVentaBLL().ModificarClienteVentaDatos(clienteId, nombres, paterno, materno, ci,
																	direccion, latitud, longitud, tipoNegocioId,"",celular);
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
	
	private void ActualizarSincronizacionClienteEditado(boolean sincronizacion,boolean edicion)
	{
		long update = 0;
		try
		{
			update = new ClienteBLL().ModificarSincronizacionClienteEditadoPorClienteId(clienteId,sincronizacion,edicion);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty() || localException == null)
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del cliente: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del cliente: " + localException.getMessage());
	    	} 
		}
		
		if(update == 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del cliente.", 1);
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
	       l = new ClienteFotoBLL().InsertarClienteFoto((int)clienteId, theCliente.get_clienteId(), foto, false, categoriaId, 0);
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
	
	private void ObtenerdetallesCliente()
	{
		theCliente =null;
	    try
	    {
	    	theCliente = new ClienteBLL().ObtenerClientePor(clienteId);
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
	
	private void ObtenerFotosCliente()
	{
	    listadoClienteFoto = null;
	    try
	    {
	    	listadoClienteFoto = new ClienteFotoBLL().ObtenerClientesFotoPorClienteIdAndroid((int)clienteId);
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
	    	if (listadoClienteFoto.size() >= 3) 
		    {
		    	//btnTomarFoto.setVisibility(View.INVISIBLE);
		    	btnInsertarFotos.setVisibility(View.VISIBLE);
		    }
		    else
		    {
		      btnTomarFoto.setVisibility(View.VISIBLE);
		      lvFotos.setVisibility(View.VISIBLE);
		      
		    }
	    	
	    	LlenarListViewFotos();
		    EliminarItemListViewFotos();
	    }
	}

	private void LlenarListViewFotos()
	{
		MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    ListView localListView = (ListView)findViewById(R.id.lvCenEdiCliFotos);
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
	    ((ListView)findViewById(R.id.lvCenEdiCliFotos)).setOnItemClickListener(new AdapterView.OnItemClickListener()
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
	
	private void InsertarFotos()
	{
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

	private void InsertarFoto(byte[] foto, int rowId, int posicionActual, int posicionFinal, int fotoClasificacionId)
	{
	    pdInsertarFoto = new ProgressDialog(this);
	    pdInsertarFoto.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdInsertarFoto.setMessage("Insertando imagen ...");
	    pdInsertarFoto.setCancelable(false);
		pdInsertarFoto.setCanceledOnTouchOutside(false);

	    WSInsertarFoto wsInsertarFoto = new WSInsertarFoto(foto,rowId,posicionActual,posicionFinal,fotoClasificacionId);
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
		private int _rowId;
		private int _fotoClasificacionId;
		int intResultado;
		String stringResultado;
		SoapObject soapObjects;
    
	    public WSInsertarFoto(byte[] foto, int rowId, int posicionActual, int posicionFinal, int fotoClasificacionId)
	    {
	    	this._foto = foto;
	    	this._rowId = rowId;
	    	this._posicionActual = posicionActual;
	    	this._posicionFinal = posicionFinal;
	    	this._fotoClasificacionId = fotoClasificacionId;
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
			localSoapObject.addProperty("categoriaId", _fotoClasificacionId);
			
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
			
	private void MostrarPantallaMapaEdicionCoordenadas()
	{
	    Intent localIntent = new Intent(this, Cencistaedicioncoordenadamapa.class);
	    localIntent.putExtra("Origen", Origen);
	    localIntent.putExtra("clienteId", clienteId);
	    localIntent.putExtra("peticion", "Mapa");
	    localIntent.putExtra("nombres", etNombres.getText().toString());
	    localIntent.putExtra("paterno", etPaterno.getText().toString());
	    localIntent.putExtra("materno", etMaterno.getText().toString());
	    localIntent.putExtra("ci", etCi.getText().toString());
	    localIntent.putExtra("celular", etCelular.getText().toString());
	    localIntent.putExtra("ciudadId", ciudadId);
	    localIntent.putExtra("provinciaId", provinciaId);
	    localIntent.putExtra("zonaId", zonaId);
	    localIntent.putExtra("mercadoId", mercadoId);
	    localIntent.putExtra("zonaMercadoId", zonaMercadoId);
	    localIntent.putExtra("direccion", etDireccion.getText().toString());
	    localIntent.putExtra("tipoNegocioId", tipoNegocioId);
	    localIntent.putExtra("tipoPagoId", tipoPagoId);
	    localIntent.putExtra("precioListaId", precioListaId);
	    localIntent.putExtra("tipoNit", tipoNit);
	    localIntent.putExtra("nombreFactura", etNombreFactura.getText().toString());
	    localIntent.putExtra("nit", etNit.getText().toString());	    
	    localIntent.putExtra("latitud",Double.parseDouble(etLatitud.getText().toString()));
	    localIntent.putExtra("longitud",Double.parseDouble(etLongitud.getText().toString()));
	    localIntent.putExtra("a", cbAVal);
	    localIntent.putExtra("b", cbBVal);
	    localIntent.putExtra("c", cbCVal);
	    localIntent.putExtra("d", cbDVal);
	    localIntent.putExtra("e", cbEVal);
	    localIntent.putExtra("f", cbFVal);
	    localIntent.putExtra("g", cbGVal);
	    localIntent.putExtra("h", cbHVal);
	    localIntent.putExtra("i", cbIVal);
	    localIntent.putExtra("j", cbJVal);
	    localIntent.putExtra("k", cbKVal);
	    localIntent.putExtra("l", cbLVal);
	    localIntent.putExtra("m", cbMVal);
	    localIntent.putExtra("n", cbNVal);
	    localIntent.putExtra("o", cbOVal);
	    localIntent.putExtra("p", cbPVal);
	    localIntent.putExtra("q", cbQVal);
	    localIntent.putExtra("r", cbRVal);
	    localIntent.putExtra("secuenciaPreventa", etSecuenciaPreventa.getText().toString());
	    localIntent.putExtra("secuenciaVenta", etSecuenciaVenta.getText().toString());
	    localIntent.putExtra("activo", swActivo.isChecked()?true:false);
	    startActivity(localIntent);
	}
	
	private void MostrarPantallaMenuCensista()
	{
		if(Origen.equals("Menuvendedor"))
		{
			Intent localIntent = new Intent(this, Menuvendedor.class);
		    localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    startActivity(localIntent);
		}
		else
		{
			Intent localIntent = new Intent(this, Menucensista.class);
		    localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    localIntent.putExtra("Origen", Origen);
		    startActivity(localIntent);
		}
	}
	
	@Override
	public void onBackPressed()
	{
		/*if(actualizoCliente && !btnInsertarFotos.isShown())
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "Debe sacar al menos 3 fotografias al cliente.", 1);
			return;
		}
		else*/ if(Origen.equals("Menuvendedor"))
		{
			Intent localIntent = new Intent(this, Menuvendedor.class);
		    localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    startActivity(localIntent);
		}
		else
		{
			Intent localIntent = new Intent(this, Censistamapaeditarcliente.class);
		    localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    localIntent.putExtra("Origen", Origen);
		    startActivity(localIntent);
		}
	}
}
