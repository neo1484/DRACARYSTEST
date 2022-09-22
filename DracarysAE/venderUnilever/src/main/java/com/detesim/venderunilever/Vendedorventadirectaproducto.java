package com.detesim.venderunilever;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import BLL.AlmacenProductoBLL;
import BLL.ApkRutaClienteBLL;
import BLL.CanalRutaPrecioBLL;
import BLL.CategoriaBLL;
import BLL.ClientePreventaBLL;
import BLL.CondicionTributariaBLL;
import BLL.DistribuidorBLL;
import BLL.DosificacionProveedorBLL;
import BLL.DraRebateSaldoBLL;
import BLL.MyLogBLL;
import BLL.ParametroGeneralBLL;
import BLL.PrecioListaBLL;
import BLL.PreventaBLL;
import BLL.ProductoBLL;
import BLL.ProductoCostoBLL;
import BLL.PromocionBLL;
import BLL.ProntoPagoCanalRutaBLL;
import BLL.ProntoPagoClienteBLL;
import BLL.ProntoPagoJerarquiaBLL;
import BLL.ProveedorBLL;
import BLL.ProveedorPrecioListaMargenBLL;
import BLL.RolBLL;
import BLL.VentaDirectaBLL;
import BLL.VentaDirectaProductoBLL;
import BLL.VentaDirectaProductoTempBLL;
import Clases.AES;
import Clases.AlmacenProducto;
import Clases.AlmacenProductoWSResult;
import Clases.ApkRutaCliente;
import Clases.CanalRutaPrecio;
import Clases.Categoria;
import Clases.ClienteIncentivo;
import Clases.ClientePreventa;
import Clases.CondicionTributaria;
import Clases.Distribuidor;
import Clases.DosificacionProveedor;
import Clases.DraRebateSaldo;
import Clases.Fecha;
import Clases.JSonParser;
import Clases.LoginEmpleado;
import Clases.ParametroGeneral;
import Clases.PrecioLista;
import Clases.Producto;
import Clases.ProductoCosto;
import Clases.Promocion;
import Clases.ProntoPagoCanalRuta;
import Clases.ProntoPagoCliente;
import Clases.ProntoPagoJerarquia;
import Clases.Proveedor;
import Clases.ProveedorPrecioListaMargen;
import Clases.Rol;
import Clases.SingleId;
import Clases.TipoPago;
import Clases.Ubicacion;
import Clases.VendedorDiaWS;
import Clases.VentaDirecta;
import Clases.VentaDirectaProducto;
import Clases.VentaDirectaProductoTemp;
import Clases.WSResultado;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Vendedorventadirectaproducto extends Activity implements OnClickListener
{
	LinearLayout llVendedorVentaDirectaProducto;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	String URLJSON = theUtilidades.get_URLJSON();
	LoginEmpleado loginEmpleado;
	MyLogBLL myLog = new MyLogBLL();
	
	ArrayList<VentaDirectaProductoTemp> listadoVentaDirectaProductoTemp;
	ArrayList<VentaDirectaProductoTemp> listadoVentaDirectaProductoTempNoSincronizados;
	ArrayList<DosificacionProveedor> listadoDosificacionProvedor;
	VentaDirecta ventaDirecta;
	Producto producto;
	ClientePreventa clientePreventa;
	int clienteId;
	int tipoPagoId;
	boolean venderCredito;
	int distribuidorId;
	String tipoNit;
	String factura;
	String nit;
	boolean nitNuevo;
	int itemBorrarDispositivo;
	int ventaDirectaIdDispositivo;
	int ventaDirectaIdServer;
	ParametroGeneral parametroGeneral;
	int noVentaDirecta;
	String observacion;
	boolean aplicarBonificacion;
	int dosificacionId;
	boolean boolCondicionTributaria =false;
	float montoTributarioServer = 0;
	CanalRutaPrecio canalRutaPrecio;
	boolean aplicarProntoPago;
	float descuentoProntoPagoUni;
	int prontoPagoId;
	DraRebateSaldo draRebateSaldo;
	double d2 = 100.0;
	ArrayList<ClienteIncentivo> clienteIncentivos;
	float _monto;
	float _descuento;
	float _montoFinal;
	float _descuentoIncentivo;
	
	Spinner spnTipoPago;
	Spinner spnDistribuidor;
	TextView tvDosificacion;
	Spinner spnDosificacion;
	TextView tvProveedores;
	Spinner spnProveedores;
	TextView tvCategorias;
	Spinner spnCategorias;
	TextView tvProductos;
	Spinner spnProductos;
	
	ImageView ivPromociones;
	Button btnMostrarProducto;
	Button btnAdicionarVentaDirecta;
	Button btnConfirmarVentaDirecta;
	
	TextView tvNombre;
	TextView tvProductotxt;
	TextView tvProducto;
	TextView tvConversiontxt;
	TextView tvConversion;
	TextView tvPrecioUnitariotxt;
	TextView tvPrecioUnitario;
	TextView tvPrecioMayortxt;
	TextView tvPrecioMayor;
	TextView tvDescuentotxt;
	TextView tvDescuento;
	TextView tvDsctoCanalTxt;
	TextView tvDsctoCanal;
	TextView tvDsctoAjusteTxt;
	TextView tvDsctoAjuste;
	TextView tvDsctoProntoPagoTxt;
	TextView tvDsctoProntoPago;
	TextView tvDsctoObjetivoTxt;
	TextView tvDsctoObjetivo;
	TextView tvDsctoObjetivoTotalTxt;
	TextView tvDsctoObjetivoTotal;
	TextView tvMontoFinalTxt;
	TextView tvMontoFinal;
	TextView tvPrecioUnitarioFinalTxt;
	TextView tvPrecioUnitarioFinal;
	TextView tvPrecioMayorFinalTxt;
	TextView tvPrecioMayorFinal;
	TextView tvInventarioUniLabel;
	TextView tvInventarioUni;
	TextView tvInventarioMayLabel;
	TextView tvInventarioMay;
	TextView tvCantidadtxt;
	TextView tvProductolbl;
	TextView tvCantidadlbl;
	TextView tvPrecioUnitariolbl;
	TextView tvPrecioMayorlbl;
	TextView tvSubTotallbl;
	TextView tvTotaltxt;
	TextView tvTotal;
	TextView tvObservacion;
	EditText etObservacion;
	CheckBox chbAplicarBonificacion;
	CheckBox cbAplicarProntoPago;
	
	EditText etNombreFactura;
	EditText etNit;
	Button btnDatosFactura;
	
	EditText etvCantidad;
	RadioButton rbtMayor;
	RadioButton rbtUnidades;
	ListView lvVentaDirectaProductoTemp;
	Dialog dialog;

	TextView tvIncentivoMonto;
	TextView tvIncentivoDescuento;
	TextView tvIncentivoMontoFinal;
	Dialog dialogIncentivo;
	
	ProgressDialog pdEsperaObtenerAlmacenProducto;
	ProgressDialog pdInsertarVentaDirectaProductoTemp; 
	ProgressDialog pdInsertarVentaDirecta;
	ProgressDialog pdVerificarMontoNitCliente;
	ProgressDialog pdClienteIncentivo;
	ProgressDialog pdInsertClienteIncentivo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedorventadirectaproducto);
		
		llVendedorVentaDirectaProducto = (LinearLayout)findViewById(R.id.llVendedorVentaDirectaProducto);
		spnTipoPago = ((Spinner)findViewById(R.id.spnVendedorVentaDirectaTipoPago));
		spnDistribuidor = ((Spinner)findViewById(R.id.spnVendedorVentaDirectaDistribuidor));
		tvDosificacion = (TextView)findViewById(R.id.tvVendedorVentaDirectaDosificacion);
		spnDosificacion = (Spinner)findViewById(R.id.spnVendedorVentaDirectaDosificacion);
		cbAplicarProntoPago = ((CheckBox)findViewById(R.id.cbVenVenDirProAplicarProntoPago));
	    cbAplicarProntoPago.setOnClickListener(this);
		tvProveedores = (TextView)findViewById(R.id.tvVendedorPreventaProductoProveedor);
		spnProveedores = ((Spinner)findViewById(R.id.spnVendedorPreventaProductoProveedor));
		tvCategorias = (TextView)findViewById(R.id.tvVendedorPreventaProductoCategoria);
		spnCategorias = ((Spinner)findViewById(R.id.spnVendedorPreventaProductoCategoria));
		tvProductos = (TextView)findViewById(R.id.tvVendedorPreventaProductoProducto);
	    spnProductos = ((Spinner)findViewById(R.id.spnVendedorPreventaProductoProducto));
	    
	    ivPromociones = (ImageView)findViewById(R.id.imgbtnVendedorVentaDirectaProducto);
	    ivPromociones.setOnClickListener(this);
	    btnMostrarProducto = ((Button)findViewById(R.id.btnVendedorVentaDirectaProductoDatosSeleccion));
	    btnMostrarProducto.setOnClickListener(this);
	    btnAdicionarVentaDirecta = ((Button)findViewById(R.id.btnVendedorVentaDirectaProductoAdicionarPreventa));
	    btnAdicionarVentaDirecta.setOnClickListener(this);
	    btnConfirmarVentaDirecta = ((Button)findViewById(R.id.btnVendedorVentaDirectaConfirmarVenta));
	    btnConfirmarVentaDirecta.setOnClickListener(this);
		
	    tvNombre = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoClienteContenido));
	    tvProductotxt = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoProductoTxt));
	    tvProducto = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoProductoTxtContenido));
	    tvConversiontxt = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoConversion));
	    tvConversion = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoConversionContenido));
	    tvPrecioUnitariotxt = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoPrecioUnitario));
	    tvPrecioUnitario = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoPrecioUnitarioContenido));
	    tvPrecioMayortxt = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoPrecioMayor));
	    tvPrecioMayor = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoPrecioMayorContenido));
	    tvDescuentotxt = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoDescuento));
	    tvDescuento = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoDescuentoContenido));
	    tvDsctoCanalTxt = (TextView)findViewById(R.id.tvVenVenDirProDescuentoCanalTxt);
	    tvDsctoCanal = (TextView)findViewById(R.id.tvVenVenDirProDescuentoCanal);
	    tvDsctoAjusteTxt = (TextView)findViewById(R.id.tvVenVenDirProDescuentoAjusteTxt);
	    tvDsctoAjuste = (TextView)findViewById(R.id.tvVenVenDirProDescuentoAjuste);
	    tvDsctoProntoPagoTxt = (TextView)findViewById(R.id.tvVenVenDirProDescuentoProntoPagoTxt);
	    tvDsctoProntoPago = (TextView)findViewById(R.id.tvVenVenDirProDescuentoProntoPago);
	    tvDsctoObjetivoTxt = (TextView)findViewById(R.id.tvVenVenDirPreProDescuentoObjetivoTxt);
	    tvDsctoObjetivo = (TextView)findViewById(R.id.tvVenVenDirPreProDescuentoObjetivo);
	    tvDsctoObjetivoTotalTxt = (TextView)findViewById(R.id.tvVenVenDirPreProDescuentoObjetivoTotalTxt);
	    tvDsctoObjetivoTotal = (TextView)findViewById(R.id.tvVenVenDirPreProDescuentoObjetivoTotal);
	    tvMontoFinalTxt = (TextView)findViewById(R.id.tvVenVenDirPreProMontoFinalTxt);
	    tvMontoFinal = (TextView)findViewById(R.id.tvVenVenDirPreProMontoFinal);
	    tvPrecioUnitarioFinalTxt = (TextView)findViewById(R.id.tvVenVenDirProPrecioUnitarioFinalTxt);
	    tvPrecioUnitarioFinal = (TextView)findViewById(R.id.tvVenVenDirProPrecioUnitarioFinal);
	    tvPrecioMayorFinalTxt = (TextView)findViewById(R.id.tvVenVenDirProPrecioMayorFinalTxt);
	    tvPrecioMayorFinal = (TextView)findViewById(R.id.tvVenVenDirProPrecioMayorFinal);
	    tvInventarioUniLabel = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoUnidades));
	    tvInventarioUni = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoUnidadesContenido));
	    tvInventarioMayLabel = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoPaquetes));
	    tvInventarioMay = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoPaquetesContenido));
	    tvCantidadtxt = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoCantidad));
	    tvProductolbl = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoProductoLbl));
	    tvCantidadlbl = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoCantidadLbl));
	    tvPrecioUnitariolbl = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoPrecioUnitarioLbl));
	    tvPrecioMayorlbl = ((TextView)findViewById(R.id.tvVendedoVentaDirectaProductoPrecioMayorLbl));
	    tvSubTotallbl = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoSubTotalLbl));
	    tvTotaltxt = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoTotalLbl));
	    tvTotal = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoTotal));
	    chbAplicarBonificacion = ((CheckBox)findViewById(R.id.chbVentaDirectaProductoAplicarBonificacion));
	    chbAplicarBonificacion.setOnClickListener(this);
	    cbAplicarProntoPago = ((CheckBox)findViewById(R.id.cbVenVenDirProAplicarProntoPago));
	    cbAplicarProntoPago.setOnClickListener(this);
	    tvObservacion = ((TextView)findViewById(R.id.tvVendedorVentaDirectaObservacion));
	    etObservacion = ((EditText)findViewById(R.id.etVendedorVentaDirectaObservacion));
	    
	    etvCantidad = ((EditText)findViewById(R.id.etVendedorVentaDirectaProductoCantidad));
	    rbtUnidades = ((RadioButton)findViewById(R.id.rbtVendedorVentaDirectaProductoUnidades));
	    rbtMayor = ((RadioButton)findViewById(R.id.rbtVendedorVentaDirectaProductoMayores));
	    lvVentaDirectaProductoTemp = ((ListView)findViewById(R.id.lvVendedorVentaDirectaProductoPreventas));
	    
	    etNombreFactura = (EditText)findViewById(R.id.etVentaDirectaProductoNombreFactura);
	    etNit = (EditText)findViewById(R.id.etVentaDirectaProductoNit);
	    btnDatosFactura = ((Button)findViewById(R.id.btnVentaDirectaProductoDatosFactura));
	    btnDatosFactura.setOnClickListener(this);
	    
	    llVendedorVentaDirectaProducto.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
	
	    clienteId = 0;
	    clienteId = getIntent().getExtras().getInt("clienteId");
	    if(clienteId == 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el clienteId.", 1);
	    	return;
	    }
	    
	    tipoPagoId = 0;
	    tipoPagoId = getIntent().getExtras().getInt("tipoPagoId");
	    
	    venderCredito = false;
	    venderCredito = getIntent().getExtras().getBoolean("venderCredito");
	    
	    distribuidorId = 0;
	    distribuidorId = getIntent().getExtras().getInt("distribuidorId");
	    
	    tipoNit="";
	    tipoNit = getIntent().getExtras().getString("tipoNit");
	    
	    factura="";
	    factura = getIntent().getExtras().getString("factura");
	    
	    nit="";
	    nit = getIntent().getExtras().getString("nit");
	    
	    nitNuevo=false;
	    nitNuevo = getIntent().getExtras().getBoolean("nitNuevo");
	    
	    observacion="";
	    observacion = getIntent().getExtras().getString("observacion");
	    if(observacion != null && !observacion.isEmpty())
	    {
	    	etObservacion.setText(observacion);
	    }
	    
	    aplicarBonificacion = false;
	    aplicarBonificacion = getIntent().getExtras().getBoolean("aplicarBonificacion");
	    chbAplicarBonificacion.setChecked(aplicarBonificacion);
	    
	    aplicarProntoPago = false;
	    aplicarProntoPago = getIntent().getExtras().getBoolean("aplicarProntoPago");
	    cbAplicarProntoPago.setChecked(aplicarProntoPago);
	    
	    dosificacionId = 0;
	    dosificacionId = getIntent().getExtras().getInt("dosificacionId");
	    
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
	    	DespliegueControlesAdicionarPreventa(false);
	        DespliegueControlesConfirmarPreventa(false);
	        CargarInformacion();
	        
	        if(parametroGeneral.is_cambiarNit())
	        {
	        	HabilitarDatosFactura(true);
	        }
	        else
	        {
	        	HabilitarDatosFactura(false);
	        }
	    }
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.imgbtnVendedorVentaDirectaProducto:
			MostrarPantallaVentaDirectaPromocion();
			break;
		case R.id.btnVentaDirectaProductoDatosFactura:
			CambiarDatosFactura();
			break;
		case R.id.btnVendedorVentaDirectaProductoDatosSeleccion:
			ObtenerDatosSeleccion();
			break;		
		case R.id.btnVendedorVentaDirectaProductoAdicionarPreventa:
			AdicionarVentaDirectaTemporal();
			break;
		case R.id.chbVentaDirectaProductoAplicarBonificacion:
			AplicarBonificacion();
			break;
		case R.id.btnVendedorVentaDirectaConfirmarVenta:
			ConfirmarVentaDirecta();
			break;
		case R.id.cbVenVenDirProAplicarProntoPago:
			VerificarExistenciaProntoPago();
			if(aplicarProntoPago)
			{
				cbAplicarProntoPago.setChecked(true);
			}
			else
			{
				if(cbAplicarProntoPago.isChecked())
				{
					aplicarProntoPago = true;
				}
				else
				{
					aplicarProntoPago = false;
				}
			}
			break;
		}
	}
	
	private void CambiarDatosFactura()
	{
		if(etNombreFactura.getText().length()==0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "El nombre de la factura no puede estar vacio.", 1);
			return;
		}
		else
		{
			if(etNit.getText().length() > 12)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El nit puede contener maximo 12 digitos.", 1);
				return;	
			}
			
			if(etNit.getText().length()==0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El nit no puede estar vacio.", 1);
				return;
			}
			else
			{
				factura = etNombreFactura.getText().toString();
				nit = etNit.getText().toString();
				nitNuevo = true;
				theUtilidades.MostrarMensaje(getApplicationContext(), "Datos de la factura actualizados.", 1);
			}
		}
	}
	
	private void DespliegueControlesAdicionarPreventa(boolean estado)
	{
		int visibilidad = 0;
	    if(estado) 
	    {
	    	visibilidad = View.VISIBLE;
	    }
	    else
	    {
	    	visibilidad = View.INVISIBLE;
	    }
	    
	    tvProductotxt.setVisibility(visibilidad);
	    tvProducto.setVisibility(visibilidad);
	    tvConversiontxt.setVisibility(visibilidad);
	    tvConversion.setVisibility(visibilidad);
	    tvPrecioUnitariotxt.setVisibility(visibilidad);
	    tvPrecioUnitario.setVisibility(visibilidad);
	    tvPrecioMayortxt.setVisibility(visibilidad);
	    tvPrecioMayor.setVisibility(visibilidad);
	    tvDescuentotxt.setVisibility(visibilidad);
	    tvDescuento.setVisibility(visibilidad);
	    tvDsctoCanalTxt.setVisibility(visibilidad);
	    tvDsctoCanal.setVisibility(visibilidad);
	    tvDsctoAjusteTxt.setVisibility(visibilidad);
	    tvDsctoAjuste.setVisibility(visibilidad);
	    tvDsctoProntoPagoTxt.setVisibility(visibilidad);
	    tvDsctoProntoPago.setVisibility(visibilidad);
	    tvPrecioUnitarioFinalTxt.setVisibility(visibilidad);
	    tvPrecioUnitarioFinal.setVisibility(visibilidad);
	    tvPrecioMayorFinalTxt.setVisibility(visibilidad);
	    tvPrecioMayorFinal.setVisibility(visibilidad);
	    tvInventarioUniLabel.setVisibility(visibilidad);
	    tvInventarioUni.setVisibility(visibilidad);
	    tvInventarioMayLabel.setVisibility(visibilidad);
	    tvInventarioMay.setVisibility(visibilidad);
	    tvCantidadtxt.setVisibility(visibilidad);
	    etvCantidad.setVisibility(visibilidad);
	    rbtUnidades.setVisibility(visibilidad);
	    rbtMayor.setVisibility(visibilidad);
	    btnAdicionarVentaDirecta.setVisibility(visibilidad); 
	    etvCantidad.setText("");
	}
	
	private void DespliegueControlesConfirmarPreventa(boolean estado)
	{
		int visibilidad = 0;
	    if(estado) 
	    {
	    	visibilidad = View.VISIBLE;
	    }
	    else
	    {
	    	visibilidad = View.INVISIBLE;
	    }
	    
	    tvProductolbl.setVisibility(visibilidad);
	    tvCantidadlbl.setVisibility(visibilidad);
	    tvPrecioUnitariolbl.setVisibility(visibilidad);
	    tvPrecioMayorlbl.setVisibility(visibilidad);
	    tvSubTotallbl.setVisibility(visibilidad);
	    lvVentaDirectaProductoTemp.setVisibility(visibilidad);
	    tvTotaltxt.setVisibility(visibilidad);
	    tvTotal.setVisibility(visibilidad);
	    tvDsctoObjetivoTxt.setVisibility(visibilidad);
	    tvDsctoObjetivo.setVisibility(visibilidad);
	    tvDsctoObjetivoTotalTxt.setVisibility(visibilidad);
	    tvDsctoObjetivoTotal.setVisibility(visibilidad);
	    tvMontoFinalTxt.setVisibility(visibilidad);
	    tvMontoFinal.setVisibility(visibilidad);
	    tvObservacion.setVisibility(visibilidad);
	    chbAplicarBonificacion.setVisibility(visibilidad);
	    etObservacion.setVisibility(visibilidad);
	    btnConfirmarVentaDirecta.setVisibility(visibilidad);
    }

	public void CargarInformacion()
	{
		CargarParametroGeneral();
		CargarDistribuidores();
	    CargarCliente();
	    CargarTiposPago();
	    CargarDosificacionesProveedorVendedor();
	    CargarProveedores();
	    CargarCategorias();
	    CargarProductos();
	    ActualizarListView();
	    ObtenerNoVentaDirecta();
	    VerificarExistenciaProntoPago();
	}
	
	private void CargarParametroGeneral()
	{
		parametroGeneral = null;
	    try
	    {
	    	parametroGeneral = new ParametroGeneralBLL().ObtenerParamatroGeneral();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los parametros generales: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los parametros generales: " + localException.getMessage());
	    	} 
	    }
	    
	    if (parametroGeneral == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los parametros generales.", 1);
	    	return;
	    }
	}
	
	private void CargarDistribuidores()
	{
		ArrayList<Distribuidor> listadoDistribuidor = null;
		try
		{
			listadoDistribuidor = new DistribuidorBLL().ObtenerDistribuidores();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los distribuidores: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los distribuidores: " + localException.getMessage());
	    	}
		}
		
		if(listadoDistribuidor == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los distribuidores.", 1);
			return;
		}
	    
	    ArrayAdapter<Distribuidor> localArrayAdapter = new ArrayAdapter<Distribuidor>(getApplicationContext(), R.layout.disenio_spinner, listadoDistribuidor);
	    spnDistribuidor.setAdapter(localArrayAdapter);
	    
	    if(distribuidorId == 0)
	    {
	    	distribuidorId = ((Distribuidor)spnDistribuidor.getSelectedItem()).get_distribuidorId();
	    }
	    else
	    {
	    	ObtenerIndiceDistribuidor(listadoDistribuidor,distribuidorId);
	    }
	    
	    spnDistribuidor.setOnItemSelectedListener(new OnItemSelectedListener() 
	    {	    	
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
			{
				distribuidorId = ((Distribuidor)spnDistribuidor.getSelectedItem()).get_distribuidorId();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent){}
	    	
		});
	}
	
	private void ObtenerIndiceDistribuidor(ArrayList<Distribuidor> distribuidores,int distribuidorId)
	{
		for(int i=0; i<distribuidores.size();i++)
		{
			if(distribuidores.get(i).get_distribuidorId() == distribuidorId)
			{
				spnDistribuidor.setSelection(i);
				return;
			}
		}
	}
	
	private void CargarTiposPago()
	{
		ArrayList<TipoPago> listadoTipoPago = new ArrayList<TipoPago>();
		listadoTipoPago.add(new TipoPago(1,"Contado"));
		listadoTipoPago.add(new TipoPago(2,"Credito"));
		
		venderCredito = false;
	    
	    ArrayAdapter<TipoPago> localArrayAdapter = new ArrayAdapter<TipoPago>(getApplicationContext(), R.layout.disenio_spinner, listadoTipoPago);
	    spnTipoPago.setAdapter(localArrayAdapter);
	    
	    tipoPagoId = ((TipoPago)spnTipoPago.getSelectedItem()).get_tipoPagoId();
	    
	    //1:Contado	2:Credito
	    if(clientePreventa.get_tipoPagoId() == 1)
	    {
	    	spnTipoPago.setEnabled(false);
	    }
	    else if(clientePreventa.get_tipoPagoId() == 2 && parametroGeneral.is_respetarTipoPago() == false)
	    {
	    	spnTipoPago.setEnabled(true);
	    }
	    else if(clientePreventa.get_tipoPagoId() == 2 && parametroGeneral.is_respetarTipoPago() == true)
	    {
	    	spnTipoPago.setSelection(1);
	    	spnTipoPago.setEnabled(false);
	    }
	    
	    spnTipoPago.setOnItemSelectedListener(new OnItemSelectedListener() 
	    {	    	
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
			{
				tipoPagoId = ((TipoPago)spnTipoPago.getSelectedItem()).get_tipoPagoId();
				
				if(tipoPagoId==1)
				{
					venderCredito = false;
					cbAplicarProntoPago.setVisibility(View.VISIBLE);
				}
				else
				{
					venderCredito = true;
					aplicarProntoPago = false;
					cbAplicarProntoPago.setChecked(false);
					cbAplicarProntoPago.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent){}
	    	
		});
	}
	
	private void CargarCliente()
	{
		clientePreventa = null;
	    try
	    {
	    	clientePreventa = new ClientePreventaBLL().ObtenerClientePreventaPor(clienteId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente por clienteId: " + localException.getMessage());
	    	} 
	    }
	    
	    if (clientePreventa == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el cliente por clienteId.", 1);
	    	return;
	    }
	    else
	    {
	    	tvNombre.setText(clientePreventa.get_nombreCompleto().toString());
	    	etNombreFactura.setText(factura);
	    	etNit.setText(nit);
	    }
	}

	public void CargarDosificacionesProveedorVendedor()
	{
		listadoDosificacionProvedor = null;
		
	    try
	    {
	    	listadoDosificacionProvedor = new DosificacionProveedorBLL().ObtenerDosificacionesProveedorVendedor();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las dosificaciones proveedor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las dosificaciones proveedor: " + localException.getMessage());
	    	} 
	    }
	    
	    if (listadoDosificacionProvedor == null)
        {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontro ninguna dosificacion activa.", 1);
	    	MostrarControlesSeleccion(false);
	    	return;
        }
	    else
	    {	    
	    	MostrarControlesSeleccion(true);
	    	
	    	if(listadoDosificacionProvedor.size() == 1)
	    	{
	    		tvDosificacion.setVisibility(View.INVISIBLE);
				spnDosificacion.setVisibility(View.INVISIBLE);
	    	}
	    	
	    	ArrayAdapter<DosificacionProveedor> localArrayAdapter = new ArrayAdapter<DosificacionProveedor>(this,R.layout.disenio_spinner,listadoDosificacionProvedor);
		    spnDosificacion.setAdapter(localArrayAdapter);
		    
		    if(dosificacionId != 0)
		    {
		    	int i=0;
		    	for(DosificacionProveedor item : listadoDosificacionProvedor)
		    	{
		    		if(item.get_dosificacionId() == dosificacionId)
		    		{
		    			spnDosificacion.setSelection(i);
		    		}
		    		i++;
		    	}
		    }

		    spnDosificacion.setOnItemSelectedListener(new OnItemSelectedListener() 
		    {	    	
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
				{				
					DosificacionProveedor theDosificacionProveedor = (DosificacionProveedor)spnDosificacion.getSelectedItem();
					dosificacionId = theDosificacionProveedor.get_dosificacionId();
					
					listadoVentaDirectaProductoTemp = null;
					try
					{
						listadoVentaDirectaProductoTemp = new VentaDirectaProductoTempBLL().ObtenerVentasDirectasProductoTempPorClienteId(clienteId);
					}
					catch (Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas directas temporales: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas directas temporales: " + localException.getMessage());
						}
					}
					
					if(listadoVentaDirectaProductoTemp != null)
					{
						int proveedorId = 0;
						if(listadoVentaDirectaProductoTemp.get(0).get_productoId() > 0)
						{
							try
							{
								proveedorId = new ProductoBLL().ObtenerProductoPor(listadoVentaDirectaProductoTemp.get(0).get_productoId()).get_proveedorId();
							}
							catch(Exception localException)
							{
								if(localException.getMessage() == null || localException.getMessage().isEmpty())
								{
							        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el proveedorId por productoId: vacio");
								}
								else
								{
									myLog.InsertarLog("App",this.toString(),1,"Error al obtener el proveedorId por productoId: " + localException.getMessage());
								}
							}
						}
						else if(listadoVentaDirectaProductoTemp.get(0).get_promocionId() > 0) 
						{
							try
							{
								proveedorId = new PromocionBLL().ObtenerPromocionPor(listadoVentaDirectaProductoTemp.get(0).get_promocionId()).get_proveedorId();
							}
							catch(Exception localException)
							{
								if(localException.getMessage() == null || localException.getMessage().isEmpty())
								{
							        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el proveedorId por promocionId: vacio");
								}
								else
								{
									myLog.InsertarLog("App",this.toString(),1,"Error al obtener el proveedorId por promocionId: " + localException.getMessage());
								}
							}
						}
						
						try
						{
							dosificacionId = new DosificacionProveedorBLL().ObtenerDosificacionesProveedorPorProveedorId(proveedorId).get_dosificacionId();
						}
						catch(Exception localException)
						{
							if(localException.getMessage() == null || localException.getMessage().isEmpty())
							{
						        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la dosificacionId por proveedorId: vacio");
							}
							else
							{
								myLog.InsertarLog("App",this.toString(),1,"Error al obtener la dosificacionId por proveedorId: " + localException.getMessage());
							}
						}
					}
					
					ArrayList<DosificacionProveedor> dosificaciones = null;
					try
					{
						dosificaciones = new DosificacionProveedorBLL().ObtenerDosificacionesProveedorPor(dosificacionId);
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las dosificacionesProveedor por dosificacionId: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener las dosificacionesProveedor por dosificacionId: " + localException.getMessage());
						}
					}
					
					if(dosificaciones==null)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudieron obtener las dosificacionesProveedor por dosificacionId", 1);
					}
					else
					{
						String proveedoresId="";
						if(dosificaciones.size()>1)
						{
							int max=0;
							for(DosificacionProveedor item : dosificaciones)
							{
								max++;
								proveedoresId += String.valueOf(item.get_proveedorId());
								if(max < dosificaciones.size())
								{
									proveedoresId += ",";
								}
							}
						}
						else
						{
							proveedoresId = String.valueOf(dosificaciones.get(0).get_proveedorId());
						}
						
						if(listadoDosificacionProvedor != null)
						{
							int i = 0;
							for(DosificacionProveedor item : listadoDosificacionProvedor)
							{
								if(item.get_descripcion().equals(dosificaciones.get(0).get_descripcion()))
			    				{
									spnDosificacion.setSelection(i);
			    				}
								i++;
							}
						}
						
						ArrayList<Proveedor> listadoProveedor = null;
						try
						{
							listadoProveedor = new ProveedorBLL().ObtenerProveedoresPor(proveedoresId);
						}
						catch(Exception localException)
						{
							if(localException.getMessage() == null || localException.getMessage().isEmpty())
							{
						        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los proveedores por proveedoresId: vacio");
							}
							else
							{
								myLog.InsertarLog("App",this.toString(),1,"Error al obtener los proveedores por proveedoresId: " + localException.getMessage());
							} 
						}
						
						if(listadoProveedor == null)
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los proveedores por proveedoresId", 1);
							return;
						}
						
						ArrayAdapter<Proveedor> arrayAdapter = new ArrayAdapter<Proveedor>(getApplicationContext(),R.layout.disenio_spinner,listadoProveedor);
					    spnProveedores.setAdapter(arrayAdapter);
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent){}
		    	
			});
	    }	    
	}
	
	private void MostrarControlesSeleccion(boolean estado)
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
		
		tvDosificacion.setVisibility(visibility);
		spnDosificacion.setVisibility(visibility);
		tvProveedores.setVisibility(visibility);
		spnProveedores.setVisibility(visibility);
		tvCategorias.setVisibility(visibility);
		spnCategorias.setVisibility(visibility);
		tvProductos.setVisibility(visibility);
		spnProductos.setVisibility(visibility);
		btnMostrarProducto.setVisibility(visibility);
	}
	
	public void CargarProveedores()
	{
		ArrayList<Proveedor> listadoProvedor = null;
		
	    try
	    {
	    	listadoProvedor = new ProveedorBLL().ObtenerProveedores();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los proveedores: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los proveedores: " + localException.getMessage());
	    	} 
	    }
	    
	    if (listadoProvedor == null)
        {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo cargar los proveedores.", 1);
	    	return;
        }
	    
	    ArrayAdapter<Proveedor> localArrayAdapter = new ArrayAdapter<Proveedor>(this,R.layout.disenio_spinner,listadoProvedor);
	    spnProveedores.setAdapter(localArrayAdapter);

	    spnProveedores.setOnItemSelectedListener(new OnItemSelectedListener() 
	    {	    	
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
			{
				Proveedor proveedor = (Proveedor)spnProveedores.getSelectedItem();
				
				ArrayList<Categoria> listadoCategoria = null;
				try
				{
					listadoCategoria = new CategoriaBLL().ObtenerCategoriasPorProveedor(proveedor.get_proveedorId());
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
				        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las categorias por proveedorId: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener los categorias por proveedorId: " + localException.getMessage());
					} 
				}
				
				if(listadoCategoria == null)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las categorias por proveedorId", 1);
					return;
				}
				
				ArrayAdapter<Categoria> arrayAdapter = new ArrayAdapter<Categoria>(getApplicationContext(),R.layout.disenio_spinner,listadoCategoria);
			    spnCategorias.setAdapter(arrayAdapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent){}
	    	
		});
	}
	
	public void CargarCategorias()
	{
		int proveedorId = ((Proveedor)spnProveedores.getSelectedItem()).get_proveedorId();
	    
		ArrayList<Categoria> listadoCategoria = null;
	    
	    try
	    {
	    	listadoCategoria = new CategoriaBLL().ObtenerCategoriasPorProveedor(proveedorId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null|| localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las categorias por proveedorId: vacio");
	    	} 
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las categorias por proveedorId: " + localException.getMessage());
	    	} 
	    }
	    
	    if(listadoCategoria == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las categorias por el proveedor seleccionado.", 1);
	    	return;
	    }
	    
	    ArrayAdapter<Categoria> localArrayAdapter = new ArrayAdapter<Categoria>(this,R.layout.disenio_spinner,listadoCategoria);
	    spnCategorias.setAdapter(localArrayAdapter);
	    
	    spnCategorias.setOnItemSelectedListener(new OnItemSelectedListener() 
	    {	    	
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
			{
				Proveedor proveedor = (Proveedor)spnProveedores.getSelectedItem();
				Categoria categoria = (Categoria)spnCategorias.getSelectedItem();
				
				ArrayList<Producto> listadoProducto = null;
				try
				{
					listadoProducto = new ProductoBLL().ObtenerProductoPorProveedorNoEnVentaDirectaProductoTemp(
													proveedor.get_proveedorId(),categoria.get_categoriaId(),
													loginEmpleado.get_empleadoId(),clienteId);
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
				        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos por proveedorId: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos por proveedorId: " + localException.getMessage());
					} 
				}
				
				if(listadoProducto == null)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los productos por proveedorId", 1);
					return;
				}
				
				ArrayAdapter<Producto> arrayAdapter = new ArrayAdapter<Producto>(getApplicationContext(),R.layout.disenio_spinner,listadoProducto);
			    spnProductos.setAdapter(arrayAdapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent){}
	    	
		});
	}
	
	public void CargarProductos()
	{
		int proveedorId = ((Proveedor)spnProveedores.getSelectedItem()).get_proveedorId();
		int categoriaId = ((Categoria)spnCategorias.getSelectedItem()).get_categoriaId();
	    ArrayList<Producto> listadoProducto = null;
	    
	    try
	    {
	    	listadoProducto = new ProductoBLL().ObtenerProductoPorProveedorNoEnVentaDirectaProductoTemp(proveedorId,
	    													categoriaId,loginEmpleado.get_empleadoId(),clienteId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null|| localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos por proveedorId: vacio");
	    	} 
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos por proveedorId: " + localException.getMessage());
	    	} 
	    }
	    
	    if(listadoProducto == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los productos por el proveedor seleccionado.", 1);
	    	return;
	    }
	    
	    ArrayAdapter<Producto> localArrayAdapter = new ArrayAdapter<Producto>(this,R.layout.disenio_spinner,listadoProducto);
	    spnProductos.setAdapter(localArrayAdapter);
	    
	    spnProductos.setOnItemSelectedListener(new OnItemSelectedListener() 
	    {	    	
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
			{
				DespliegueControlesAdicionarPreventa(false);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent){}
	    	
		});
	}

	public void ActualizarListView()
	{
		listadoVentaDirectaProductoTemp = null;
		try
		{
			listadoVentaDirectaProductoTemp = new VentaDirectaProductoTempBLL().ObtenerVentasDirectasProductoTempPorClienteId(clienteId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas directas temporales: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas directas temporales: " + localException.getMessage());
			}
		}
		      
		if(listadoVentaDirectaProductoTemp == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No existen ventas directas temporales.", 1);
			spnDosificacion.setEnabled(true);
			DespliegueControlesConfirmarPreventa(false);
			LlenarVentaDirectaListView();
		}
		else
		{
			spnDosificacion.setEnabled(false);
			DespliegueControlesConfirmarPreventa(true);
			CalcularTotalVentaDirecta();
			lvVentaDirectaProductoTemp.setVisibility(View.VISIBLE);
		    LlenarVentaDirectaListView();
		    EliminarItemListView();	
		}    
		
		if(listadoVentaDirectaProductoTemp != null && listadoVentaDirectaProductoTemp.size()>0)
		{
			spnTipoPago.setEnabled(false);
		}
		else
		{
			spnTipoPago.setEnabled(true);
		}
	}
	
	private void ObtenerNoVentaDirecta()
	{
		noVentaDirecta = -1;
	    try
	    {
	    	noVentaDirecta = new VentaDirectaBLL().ObtenerVentasDirectas().size();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasDirectas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasDirectas: " + localException.getMessage());
	    	}
	    	noVentaDirecta = 0;
	    }
	    
	    if (noVentaDirecta == -1)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el numero de ventas directas.", 1);
	    	return;
	    }
	    else
	    {
	    	if(!VerificarExistenciaCliente(clienteId))
	    	{
	    		noVentaDirecta++;
	    	}
	    }
	}

	private void VerificarExistenciaProntoPago()
	{
		if(listadoVentaDirectaProductoTemp != null && listadoVentaDirectaProductoTemp.size() > 0)
		{
			float dsctoPP = 0;
			for(VentaDirectaProductoTemp item : listadoVentaDirectaProductoTemp)
			{
				dsctoPP += item.get_descuentoProntoPago();
			}
			if(dsctoPP > 0)
			{
				aplicarProntoPago = true;
				cbAplicarProntoPago.setChecked(true);
			}
			else
			{
				aplicarProntoPago = false;
			}
		}
		else
		{
			aplicarProntoPago = false;
		}
	}
	
	private boolean VerificarExistenciaCliente(int clienteId)
	{
		boolean verificado = false;
		ArrayList<VentaDirectaProductoTemp> listadoVentaDirectaProductoTemp = null;
		try
		{
			listadoVentaDirectaProductoTemp = new VentaDirectaProductoTempBLL().ObtenerVentasDirectasProductoTempPorClienteId(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasDirectas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasDirectas: " + localException.getMessage());
	    	}
		}
		
		if(listadoVentaDirectaProductoTemp == null)
		{
			return verificado;
		}
		else
		{
			noVentaDirecta = listadoVentaDirectaProductoTemp.get(0).get_noVentaDirecta();
			return true;
		}
	}
	
	private void HabilitarDatosFactura(boolean estado)
	{
		int visibility;
		if(estado)
		{
			visibility = View.VISIBLE;
			etNombreFactura.setVisibility(visibility);
			etNit.setVisibility(visibility);
			btnDatosFactura.setVisibility(visibility);
		}
		else
		{
			visibility = View.INVISIBLE;
			etNombreFactura.setEnabled(false);
			etNit.setEnabled(false);
			btnDatosFactura.setVisibility(visibility);
		}
	}
	
	private void CalcularTotalVentaDirecta()
	{
		float total = 0;
		for(VentaDirectaProductoTemp item : listadoVentaDirectaProductoTemp)
		{
			total += Float.parseFloat(new BigDecimal(item.get_montoFinal()).setScale(5,RoundingMode.HALF_UP).toString());
		}
		
		tvTotal.setText(String.valueOf(Float.parseFloat(new BigDecimal(total).setScale(2,RoundingMode.HALF_UP).toString())));
		
		//Verifico si el cliente tiene SaldoRebate
		draRebateSaldo = null;
		try
		{
			draRebateSaldo = new DraRebateSaldoBLL().ObtenerDraRebateSaldoPor(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el saldo del rebate por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el saldo del rebate por clienteId: " + localException.getMessage());
			} 
		}
	 
		if(draRebateSaldo != null)
		{
			float saldoObjetivo = total * (draRebateSaldo.get_maxDescuento() / 100);
			if(saldoObjetivo > draRebateSaldo.get_saldo())
			{
				tvDsctoObjetivo.setText(String.valueOf(new BigDecimal(draRebateSaldo.get_saldo()).setScale(2,RoundingMode.HALF_UP)));
				tvDsctoObjetivoTotal.setText(String.valueOf(new BigDecimal(draRebateSaldo.get_saldo()).setScale(2,RoundingMode.HALF_UP)));
				tvMontoFinal.setText(String.valueOf(new BigDecimal(total - draRebateSaldo.get_saldo()).setScale(2,RoundingMode.HALF_UP)));
			}
			else
			{
				tvDsctoObjetivo.setText(String.valueOf(new BigDecimal(saldoObjetivo).setScale(2,RoundingMode.HALF_UP)));
				tvDsctoObjetivoTotal.setText(String.valueOf(new BigDecimal(draRebateSaldo.get_saldo()).setScale(2,RoundingMode.HALF_UP)));
				tvMontoFinal.setText(String.valueOf(new BigDecimal(total - saldoObjetivo).setScale(2,RoundingMode.HALF_UP)));
			}
		}
		else
		{
			tvDsctoObjetivo.setText("0.00");
			tvDsctoObjetivoTotal.setText("0.00");
			tvMontoFinal.setText(String.valueOf(new BigDecimal(total).setScale(2,RoundingMode.HALF_UP)));
		}
	}

	private void LlenarVentaDirectaListView()
	{
	    MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    	    
	    if(listadoVentaDirectaProductoTemp == null)
	    {
	    	lvVentaDirectaProductoTemp.setAdapter(null);
	    }
	    else
	    {
		    ViewGroup.LayoutParams localLayoutParams = lvVentaDirectaProductoTemp.getLayoutParams();
		    localLayoutParams.height = ((int)(50*getApplicationContext().getResources().getDisplayMetrics().density) * listadoVentaDirectaProductoTemp.size());
		    lvVentaDirectaProductoTemp.setLayoutParams(localLayoutParams);
		    lvVentaDirectaProductoTemp.setAdapter(localMiAdaptadorLista);
	    }
	}

	class MiAdaptadorLista extends ArrayAdapter<VentaDirectaProductoTemp>
	{
		private Context _context;
		
		public MiAdaptadorLista(Context context)
		{
			super(context,R.layout.disenio_vendedorpreventaproducto,listadoVentaDirectaProductoTemp);
			this._context = context;
		}
    
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View localView = convertView;
			
			if (localView == null) 
			{				
				LayoutInflater layoutInflater =	(LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    		localView = layoutInflater.inflate(R.layout.disenio_vendedorpreventaproducto, parent, false);
			}
			
			VentaDirectaProductoTemp localVentaDirectaProductoTemp = (VentaDirectaProductoTemp)listadoVentaDirectaProductoTemp.get(position);
			
			TextView descripcion25 = (TextView)localView.findViewById(R.id.tvVendedorVentaDirectaProductoCliente);
			TextView cantidad = (TextView)localView.findViewById(R.id.tvPreventaDisenioCantidad);
			TextView precio = (TextView)localView.findViewById(R.id.tvPreventaDisenioPrecioUnitario);
			TextView precioPaquete = (TextView)localView.findViewById(R.id.tvVendedorPreventaProductoProveedor);
			TextView montoFinal = (TextView)localView.findViewById(R.id.tvVendedorPreventaProductoProducto);
			ImageView bullet = (ImageView)localView.findViewById(R.id.imgvLoginLogotipo);
      
			if(localVentaDirectaProductoTemp.get_productoId() != 0)
			{
				Producto localProducto = null;
				try
				{
					localProducto = new ProductoBLL().ObtenerProductoPor(localVentaDirectaProductoTemp.get_productoId());
				}
				catch (Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
				        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del producto: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del producto: " + localException.getMessage());
					} 
				}
				
				if(localProducto == null)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el producto.", 1);
		            return null;
				}
				
				PrecioLista localPrecioLista = null;
				try
				{
					localPrecioLista = new PrecioListaBLL().ObtenerPrecioListaPor(clientePreventa.get_precioListaId(),localProducto.get_productoId());
				}
				catch (Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
				        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precio del producto: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precio del producto: " + localException.getMessage());
					} 
				}
				
				if(localPrecioLista == null)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el precio del producto.", 1);
		            return null;
				}
				
				//Verifico si el producto tiene ruta de precio
				canalRutaPrecio = null;

				if(clientePreventa.get_canalRutaId() > 0)
				{
					try
					{
						canalRutaPrecio = new CanalRutaPrecioBLL().ObtenerCanalRutaPrecioPorCanalRutaIdYProductoId(clientePreventa.get_canalRutaId(),localProducto.get_productoId());
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener el canal ruta precio por canalRutaId y productoId: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener el canal ruta precio por canalRutaId y productoId: " + localException.getMessage());
						}  
					}
				}
				
				descripcion25.setText(localProducto.get_descripcion25());
				cantidad.setText(String.valueOf(localVentaDirectaProductoTemp.get_cantidad()==0?localVentaDirectaProductoTemp.get_cantidadPaquete():localVentaDirectaProductoTemp.get_cantidad()));
				
				if(localVentaDirectaProductoTemp.get_cantidad()==0)
				{
					precio.setText("0");
				}
				else
				{
					if(canalRutaPrecio != null)
					{
						float dsctoCanal = canalRutaPrecio.get_hpb() * (canalRutaPrecio.get_descuentoCanal() / 100);
						float dsctoAjuste = canalRutaPrecio.get_hpb() * (canalRutaPrecio.get_descuentoAjuste() / 100);
						float precioFD = (canalRutaPrecio.get_hpb() - dsctoCanal - dsctoAjuste) / localProducto.get_conversion();
						precio.setText(String.valueOf(new BigDecimal(precioFD).setScale(2,RoundingMode.HALF_UP)));
					}
					else
					{
						precio.setText(String.valueOf(new BigDecimal(localPrecioLista.get_precio()).setScale(2,RoundingMode.HALF_UP)));
					}
				}
				
				if(localVentaDirectaProductoTemp.get_cantidadPaquete()==0)
				{
					precioPaquete.setText("0");
				}
				else
				{
					if(canalRutaPrecio != null)
					{
						float dsctoCanal = canalRutaPrecio.get_hpb() * (canalRutaPrecio.get_descuentoCanal() / 100);
						float dsctoAjuste = canalRutaPrecio.get_hpb() * (canalRutaPrecio.get_descuentoAjuste() / 100);
						float precioPaqueteFD = canalRutaPrecio.get_hpb() - dsctoCanal - dsctoAjuste;
						precioPaquete.setText(String.valueOf(new BigDecimal(precioPaqueteFD).setScale(2,RoundingMode.HALF_UP)));
					}
					else
					{
						precioPaquete.setText(String.valueOf(new BigDecimal(localPrecioLista.get_precioPaquete()).setScale(2,RoundingMode.HALF_UP)));
					}
				}
				
				montoFinal.setText(String.valueOf(new BigDecimal(localVentaDirectaProductoTemp.get_montoFinal()).setScale(2,RoundingMode.HALF_UP)));
				
			}
			
			if(localVentaDirectaProductoTemp.get_promocionId() != 0) 
			{
				Promocion localPromocion = null;
				try
				{
					localPromocion = new PromocionBLL().ObtenerPromocionPor(localVentaDirectaProductoTemp.get_promocionId());
				}
				catch (Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
				        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de la promocion: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de la promocion: " + localException.getMessage());
					} 
				}
				
				if(localPromocion == null)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles de la promocion.", 1);
					return null;
				}
				
				
				descripcion25.setText(localPromocion.get_descripcion());
				cantidad.setText(String.valueOf(localVentaDirectaProductoTemp.get_cantidad()));
				precio.setText(String.valueOf(new BigDecimal(localVentaDirectaProductoTemp.get_monto()/localVentaDirectaProductoTemp.get_cantidad()).setScale(2, RoundingMode.HALF_UP)));
				precioPaquete.setText(" ");
				montoFinal.setText(String.valueOf(new BigDecimal(localVentaDirectaProductoTemp.get_montoFinal()).setScale(2, RoundingMode.HALF_UP)));
			}
			
			bullet.setImageResource(R.drawable.bullet_eliminar);

			return localView;
		}
	}

	private void EliminarItemListView()
	{
		((ListView)findViewById(R.id.lvVendedorVentaDirectaProductoPreventas)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				itemBorrarDispositivo = 0;
				
				VentaDirectaProductoTemp localVentaDirectaProductoTemp = listadoVentaDirectaProductoTemp.get(position);
				itemBorrarDispositivo = localVentaDirectaProductoTemp.get_id();
				
				if(BorrarVentaDirectaProductoTempDispositivo())
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la venta directa eliminado del dispositivo.", 1);
					CargarProductos();
				} 
				else 
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo eliminar el item de la venta directa.", 1);
				}

				if(listadoVentaDirectaProductoTemp != null) 
				{
					ActualizarListView();
				}
	        }
	    });
	}
		 
	private boolean BorrarVentaDirectaProductoTempDispositivo()
	 {
		 boolean bool = false;
		 try
		 {
			 bool = new VentaDirectaProductoTempBLL().BorrarVentaDirectaProductoTempPor(itemBorrarDispositivo);
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al borrar el item de la ventaDirectaProductoTemp: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al borrar el item de la ventaDirectaProductoTemp: " + localException.getMessage());
			 }
			 return false;
		 }
	    
		 if(bool)
		 {
			 return true;
		 }
		 else
		 {
			 return false;
		 }
	}

	public void ObtenerDatosSeleccion()
	 {
		 int productoId = 0;
		 int precioListaId = 0;
		 PrecioLista localPrecioLista;
		 
		 productoId = ((Producto)spnProductos.getSelectedItem()).get_productoId();
		 if(productoId == 0)
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(), "Debe seleccionar un producto.", 1);
			 return;
		 }
		 
		 producto = null;
		 try
		 {
			 producto = new ProductoBLL().ObtenerProductoPor(productoId);
		 }
		 catch(Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de los productos: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar los detalles de los productos: " + localException.getMessage());
			 } 
		 }
		 
		 if(producto == null)
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del producto", 1);
			 return;
		 }
		 
		//Verifico si aplica a descuento ProntoPago
		 if(aplicarProntoPago)
		 {
			 descuentoProntoPagoUni = ObtenerProntoPagoJerarquia();

		 }
		 else
		 {
			 descuentoProntoPagoUni = 0;
		 }
		 
		 //Verifico si es una variante de algun producto
		 if(producto.get_productoReferenciaId() > 0)
		 {
			 Producto productoMaestro = null;
			 try
			 {
				 productoMaestro = new ProductoBLL().ObtenerProductoPor(producto.get_productoReferenciaId());
			 }
			 catch(Exception localException)
			 {
				 if(localException.getMessage() == null || localException.getMessage().isEmpty())
				 {
					 myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del producto maestro: vacio");
				 }
				 else
				 {
					 myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del producto maestro:" + localException.getMessage());
				 } 
			 }
			 
			 if(productoMaestro == null)
			 {
				 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del producto maestro", 1);
				 return;
			 }
			 
			 AlmacenProducto almacenProductoMaestro = null;
			 try
			 {
				 almacenProductoMaestro = new AlmacenProductoBLL().ObtenerAlmacenProductoPor(loginEmpleado.get_almacenId(), productoMaestro.get_productoId());
			 }
			 catch(Exception localException)
			 {
				 if(localException.getMessage() == null || localException.getMessage().isEmpty())
				 {
					 myLog.InsertarLog("App",this.toString(),1,"Error al obtener el inventario del almacen del producto maestro por prouctoReferenciaId: vacio");
				 }
				 else
				 {
					 myLog.InsertarLog("App",this.toString(),1,"Error al obtener el inventario del almacen del producto maestro por productoReferenciaId: " + localException.getMessage());
				 }
			 }
			 
			 if(almacenProductoMaestro == null)
			 {
				 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el inventario del producto maestro.", 1);
				 return;
			 }
			 
			 float almacenMaestroUnidades = ((almacenProductoMaestro.get_saldoPaquete() * productoMaestro.get_conversion()) + almacenProductoMaestro.get_saldoUnitario()) / producto.get_conversion(); 
		     
			//Verifico si el producto tiene un canal ruta precio
			 canalRutaPrecio = null;
	          
			 if(clientePreventa.get_canalRutaId() > 0)
			 {
				 try
				 {
					 canalRutaPrecio = new CanalRutaPrecioBLL().ObtenerCanalRutaPrecioPorCanalRutaIdYProductoId(clientePreventa.get_canalRutaId(),producto.get_productoId());
				 }
				 catch(Exception localException)
				 {
					 if(localException.getMessage() == null || localException.getMessage().isEmpty())
					 {
						 myLog.InsertarLog("App",this.toString(),1,"Error al obtener el canal ruta precio por canalRutaId y productoId: vacio");
					 }
					 else
					 {
						 myLog.InsertarLog("App",this.toString(),1,"Error al obtener el canal ruta precio por canalRutaId y productoId: " + localException.getMessage());
					 }  
				 }
			 }
	          
			 if(canalRutaPrecio != null && canalRutaPrecio.get_canalPrecioRutaId() > 0)
			 {
				 DespliegueControlesAdicionarPreventa(true);
		    	 rbtMayor.setVisibility(View.INVISIBLE);
		    	 rbtUnidades.setChecked(true);
		    	 
			     tvProducto.setText(producto.get_descripcion25());
			     tvConversion.setText(String.valueOf(1));
			     float precioFinal = canalRutaPrecio.get_hpb() - (canalRutaPrecio.get_hpb() * (canalRutaPrecio.get_descuentoCanal() / 100)) 
			    		 									   - (canalRutaPrecio.get_hpb() * (canalRutaPrecio.get_descuentoAjuste() / 100))
			    		 									   - (canalRutaPrecio.get_hpb() * (descuentoProntoPagoUni / 100));
			     tvPrecioUnitario.setText(String.valueOf(new BigDecimal(precioFinal / producto.get_conversion()).setScale(2,RoundingMode.HALF_UP)));
			     tvPrecioMayor.setText(String.valueOf(new BigDecimal(precioFinal).setScale(2,RoundingMode.HALF_UP)));
			     tvDescuento.setText(String.valueOf(clientePreventa.get_descuento()));
			     tvDsctoAjuste.setText(String.valueOf(canalRutaPrecio.get_descuentoAjuste()) + " %");
			     tvDsctoCanal.setText(String.valueOf(canalRutaPrecio.get_descuentoCanal()) + " %");
			     tvDsctoProntoPago.setText(String.valueOf(descuentoProntoPagoUni) + " %");
			     tvInventarioUni.setText(String.valueOf(almacenMaestroUnidades));
			     tvInventarioMay.setText(String.valueOf(0));
			 }
			 else
			 {
				 localPrecioLista = null;
			     try
			     {
			    	 localPrecioLista = new PrecioListaBLL().ObtenerPrecioListaPor(clientePreventa.get_precioListaId(),producto.get_productoId());
			     }
			     catch (Exception localException)
			     {
			    	 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			    	 {
			    		 myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precioLista del producto mascara: vacio");
			    	 }
			    	 else
			    	 {
			    		 myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precioLista del producto mascara: " + localException.getMessage());
			    	 } 
		         }
			          
			     if(localPrecioLista!=null)
			     {
			    	 DespliegueControlesAdicionarPreventa(true);
			    	 rbtMayor.setVisibility(View.INVISIBLE);
			    	 rbtUnidades.setChecked(true);
				     
				     tvProducto.setText(producto.get_descripcion25());
				     tvConversion.setText(String.valueOf(1));
				     tvPrecioUnitario.setText(String.valueOf(localPrecioLista.get_precio()));
				     tvPrecioMayor.setText(String.valueOf(localPrecioLista.get_precioPaquete()));
				     tvDescuento.setText(String.valueOf(clientePreventa.get_descuento()));
				     tvDsctoAjuste.setText("0 %");
				     tvDsctoCanal.setText("0 %");
				     tvDsctoProntoPago.setText(String.valueOf(descuentoProntoPagoUni) + " %");
				     tvInventarioUni.setText(String.valueOf(almacenMaestroUnidades));
				     tvInventarioMay.setText(String.valueOf(0));
			     }
			     else
			     {
			    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el precio de lista del producto.", 1);
			    	return;
			     }
			 }
		 }
		 else
		 {
			 AlmacenProducto almacenProducto = null;
			 try
			 {
				 almacenProducto = new AlmacenProductoBLL().ObtenerAlmacenProductoPor(loginEmpleado.get_almacenId(), productoId);
			 }
			 catch(Exception localException)
			 {
				 if(localException.getMessage() == null || localException.getMessage().isEmpty())
				 {
					 myLog.InsertarLog("App",this.toString(),1,"Error al obtener el inventario del almacen por productoId: vacio");
				 }
				 else
				 {
					 myLog.InsertarLog("App",this.toString(),1,"Error al obtener el inventario del almacen por productoId: " + localException.getMessage());
				 }
			 }
			 
			 if(almacenProducto == null)
			 {
				 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el inventario del producto", 1);
				 return;
			 }
			 
			 precioListaId = clientePreventa.get_precioListaId();
			 if(precioListaId == 0) 
			 {
				 theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo obtener el id del precio de lista.", 1);
			     return;	          
		     }
			 
			 //Verifico si el producto tiene un canal ruta precio
			 canalRutaPrecio = null;
	          
			 if(clientePreventa.get_canalRutaId() > 0)
			 {
				 try
				 {
					 canalRutaPrecio = new CanalRutaPrecioBLL().ObtenerCanalRutaPrecioPorCanalRutaIdYProductoId(clientePreventa.get_canalRutaId(),producto.get_productoId());
				 }
				 catch(Exception localException)
				 {
					 if(localException.getMessage() == null || localException.getMessage().isEmpty())
					 {
						 myLog.InsertarLog("App",this.toString(),1,"Error al obtener el canal ruta precio por canalRutaId y productoId: vacio");
					 }
					 else
					 {
						 myLog.InsertarLog("App",this.toString(),1,"Error al obtener el canal ruta precio por canalRutaId y productoId: " + localException.getMessage());
					 }  
				 }
			 }
	          
			 if(canalRutaPrecio != null && canalRutaPrecio.get_canalPrecioRutaId() > 0)
			 {
				 DespliegueControlesAdicionarPreventa(true);
	        	  
				 tvProducto.setText(producto.get_descripcion25());
			     tvConversion.setText(String.valueOf(producto.get_conversion()));
			     tvPrecioUnitario.setText(String.valueOf(canalRutaPrecio.get_hpb() / producto.get_conversion()));
			     tvPrecioMayor.setText(String.valueOf(canalRutaPrecio.get_hpb()));
			     tvDescuento.setText(String.valueOf(clientePreventa.get_descuento()));
			     tvDsctoCanal.setText(String.valueOf(canalRutaPrecio.get_descuentoCanal()) + " %");
			     tvDsctoAjuste.setText(String.valueOf(canalRutaPrecio.get_descuentoAjuste()) + " %");
			     tvDsctoProntoPago.setText(String.valueOf(descuentoProntoPagoUni) + " %");
			     float precioFinal = canalRutaPrecio.get_hpb() - (canalRutaPrecio.get_hpb() * (canalRutaPrecio.get_descuentoCanal() / 100)) 
			    		 									   - (canalRutaPrecio.get_hpb() * (canalRutaPrecio.get_descuentoAjuste() / 100))
			    		 									   - (canalRutaPrecio.get_hpb() * (descuentoProntoPagoUni / 100));
			     tvPrecioUnitarioFinal.setText(String.valueOf(new BigDecimal(precioFinal / producto.get_conversion()).setScale(2,RoundingMode.HALF_UP)));
			     tvPrecioMayorFinal.setText(String.valueOf(new BigDecimal(precioFinal).setScale(2,RoundingMode.HALF_UP)));
			     tvInventarioUni.setText(String.valueOf(almacenProducto.get_saldoUnitario()));
			     tvInventarioMay.setText(String.valueOf(almacenProducto.get_saldoPaquete()));
			 }
			 else
			 {
				 //Obtengo el precio del producto, del precio de lista
				 localPrecioLista = null;
			     try
			     {
			    	 localPrecioLista = new PrecioListaBLL().ObtenerPrecioListaPor(precioListaId,productoId);
			     }
			     catch (Exception localException)
			     {
			    	 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			    	 {
			    		 myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precioLista del producto: vacio");
			    	 }
			    	 else
			    	 {
			    		 myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precioLista del producto: " + localException.getMessage());
			    	 } 
		        }
			          
			     if(localPrecioLista!=null)
			     {
			    	 DespliegueControlesAdicionarPreventa(true);
				     
				     tvProducto.setText(producto.get_descripcion25());
				     tvConversion.setText(String.valueOf(producto.get_conversion()));
				     tvPrecioUnitario.setText(String.valueOf(localPrecioLista.get_precio()));
				     tvPrecioMayor.setText(String.valueOf(localPrecioLista.get_precioPaquete()));
				     tvDescuento.setText(String.valueOf(clientePreventa.get_descuento()));
				     tvDsctoCanal.setText("0 %");
				     tvDsctoAjuste.setText("0 %");
				     tvDsctoProntoPago.setText(String.valueOf(descuentoProntoPagoUni) + " %");
				     tvPrecioUnitarioFinal.setText(String.valueOf(localPrecioLista.get_precio()));
				     tvPrecioMayorFinal.setText(String.valueOf(localPrecioLista.get_precioPaquete()));
				     tvInventarioUni.setText(String.valueOf(almacenProducto.get_saldoUnitario()));
				     tvInventarioMay.setText(String.valueOf(almacenProducto.get_saldoPaquete()));
			     }
			     else
			     {
			    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el precio de lista del producto.", 1);
			    	return;
			     }
			 }
		 }
	 }

	private void AplicarBonificacion()
	{
		if(chbAplicarBonificacion.isChecked())
		{
			aplicarBonificacion = true;
		}
		else
		{
			aplicarBonificacion = false;
		}
	}
	
	private void AdicionarVentaDirectaTemporal()
 	{
		int cantidad;
		int cantidadUnitaria=0;
		int cantidadPaquete=0;
		float precioUnitario;
		float precioMayor=0;
		float precioUnitarioFinal;
		float precioMayorFinal;
		ProductoCosto productoCosto;
		ProveedorPrecioListaMargen margenProveedorPrecioLista;
		float marginacionActual=0;
		float marginacionAlmacenada=0;
		float marginacionAlmacenadaPaquete=0;
		float monto=0;
		PrecioLista precioLista;
		float descuentoTotal=0;
		float descuentoUnitario=0;
		float descuentoPaquete=0;
		float montoFinal=0;
		int cantidadSolicitadaEnUnidades=0;
		int costoId = 0;
		
		if(etvCantidad.getText().length() <= 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "La cantidad es requerida.", 1);
			return;
		}
		  
		cantidad = 0;
		try
		{
			cantidad = Integer.parseInt(etvCantidad.getText().toString());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al convertir la cantidad de string a int: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al convertir la cantidad de string a int: " + localException.getMessage());
			}
			return;
		}
		  
		if(cantidad <= 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "La cantidad debe ser mayor a cero.", 1);
			return;
		}
         
		productoCosto = null;
		try
		{
			productoCosto = new ProductoCostoBLL().ObtenerProductosCosto(producto.get_productoId());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el costo del producto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el costo del producto: " + localException.getMessage());
			}
			return;
		}
         
		if(productoCosto == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo obtener los detalles del productoCosto.",1);
			return;
		}
         
		if(productoCosto.get_costoId() <= 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo obtener el costo del producto, costoId<=0.",1);
   	  		return;
		}
         
		//Obtengo el precio de lista del producto
		precioLista = null;
		try
		{
			precioLista = new PrecioListaBLL().ObtenerPrecioListaPor(clientePreventa.get_precioListaId(),producto.get_productoId());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
       	  	{
       	  		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precio del producto: vacio");
       	  	}
       	  	else
       	  	{
       	  		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precio del producto: " + localException.getMessage());
       	  	}
       	  	return;
		}
         
		if(precioLista == null)
		{
			//Verifico si el producto tiene ruta de precio
			canalRutaPrecio = null;

			if(clientePreventa.get_canalRutaId() > 0)
			{
				try
				{
					canalRutaPrecio = new CanalRutaPrecioBLL().ObtenerCanalRutaPrecioPorCanalRutaIdYProductoId(clientePreventa.get_canalRutaId(),producto.get_productoId());
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el canal ruta precio por canalRutaId y productoId: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el canal ruta precio por canalRutaId y productoId: " + localException.getMessage());
					}  
				}
			}

			if(canalRutaPrecio != null)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener un precio de lista o ruta de precios para el producto.", 1);
				return;
			}
		}
		
		VentaDirectaProductoTemp localVentaDirectaProductoTemp = new VentaDirectaProductoTemp();
        
        //Verifico si el producto tiene ruta de precio
        canalRutaPrecio = null;
        
        if(clientePreventa.get_canalRutaId() > 0)
        {
      	  try
      	  {
      		  canalRutaPrecio = new CanalRutaPrecioBLL().ObtenerCanalRutaPrecioPorCanalRutaIdYProductoId(clientePreventa.get_canalRutaId(),producto.get_productoId());
      	  }
      	  catch(Exception localException)
      	  {
      		  if(localException.getMessage() == null || localException.getMessage().isEmpty())
      		  {
      			  myLog.InsertarLog("App",this.toString(),1,"Error al obtener el canal ruta precio por canalRutaId y productoId: vacio");
      		  }
      		  else
      		  {
      			  myLog.InsertarLog("App",this.toString(),1,"Error al obtener el canal ruta precio por canalRutaId y productoId: " + localException.getMessage());
      		  }  
      	  }
        }
        
        if(canalRutaPrecio != null)
        {
      	  float descuentoCanal = canalRutaPrecio.get_hpb() * (canalRutaPrecio.get_descuentoCanal() / 100);
      	  float descuentoAjuste = canalRutaPrecio.get_hpb() * (canalRutaPrecio.get_descuentoAjuste() / 100);
      	  float dsctoProntoPago = canalRutaPrecio.get_hpb() * (descuentoProntoPagoUni / 100);
      	  
      	  if(rbtUnidades.isChecked())
  	      {
      		  precioUnitarioFinal = 0;
  	    	  
  	    	  try
  	    	  {
  	    		  precioUnitarioFinal = (canalRutaPrecio.get_hpb() - descuentoCanal - descuentoAjuste) / producto.get_conversion();
  	    	  }
  	    	  catch(Exception localException)
  	    	  {
  	    		  if(localException.getMessage() == null || localException.getMessage().isEmpty())
  	    		  {
  	    			  myLog.InsertarLog("App",this.toString(),1,"Error al convertir el precio unitario de string a float: vacio");
  	    		  }
  	    		  else
  	    		  {
  	    			  myLog.InsertarLog("App",this.toString(),1,"Error al convertir el precio unitario de string a float: " + localException.getMessage());
  	    		  }
  	    		  return;
      		  }
  	    	  
  	          if (precioUnitarioFinal <= 0) 
  	          {
  	        	  theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontro el precio unitario final", 1);
  	        	  return;
  	          }
  	          
  	          /*marginacionActual = 100 - ((productoCosto.get_cpp()*100)/precioUnitarioFinal); 
  	          
  	          if (marginacionActual < marginacionAlmacenada) 
  	          {
  	        	  theUtilidades.MostrarMensaje(getApplicationContext(), "La marginacion de la unidad es menor a la establecida.", 1);
  		          return;
  	          }*/
  	          
  	          cantidadUnitaria = cantidad;
  	          cantidadPaquete = 0;
  	          monto = Float.parseFloat(new BigDecimal(cantidad * (canalRutaPrecio.get_hpb() / producto.get_conversion())).setScale(5,RoundingMode.HALF_UP).toString());
  	          
  	          //Verificar si es producto variante
  	          if(producto.get_productoReferenciaId() > 0)
  	          {
  	        	  cantidadSolicitadaEnUnidades = cantidadUnitaria * producto.get_conversion();
  	          }
  	          else
  	          {
  	        	  cantidadSolicitadaEnUnidades = cantidadUnitaria;
  	          }
  	          
  	          if(descuentoUnitario > 0)
  	          {
  	        	  descuentoTotal = descuentoTotal + (monto * (descuentoUnitario/100)); 
  	          }
  	          
  	          if(clientePreventa.get_descuento() > 0)
  	          {
  	        	  descuentoTotal = descuentoTotal + (monto * (clientePreventa.get_descuento()/100)); 
  	          }
  	        	  
  	      	  montoFinal = monto - descuentoTotal - (descuentoCanal/producto.get_conversion())*cantidadSolicitadaEnUnidades 
  	      			  							  - (descuentoAjuste/producto.get_conversion())* cantidadSolicitadaEnUnidades
  	      			  							  - (dsctoProntoPago / producto.get_conversion() * cantidadSolicitadaEnUnidades);

  	          costoId = productoCosto.get_costoId();
  	      }
      	  
      	  if(rbtMayor.isChecked())
  	      {
  	    	  precioMayorFinal = 0;
  	    	  try
  	    	  {
  	    		  precioMayorFinal = canalRutaPrecio.get_hpb() - descuentoCanal - descuentoAjuste - dsctoProntoPago;
  	    	  }
  	    	  catch(Exception localException)
  	    	  {
  	    		  if(localException.getMessage() == null || localException.getMessage().isEmpty())
  	    		  {
  	    			  myLog.InsertarLog("App",this.toString(),1,"Error al convertir el precio mayor de string a float: vacio");
  	    		  }
  	    		  else
  	    		  {
  	    			  myLog.InsertarLog("App",this.toString(),1,"Error al convertir el precio mayor de string a float: " + localException.getMessage());
  	    		  }
  	    		  return;
      		  }
  	    	  
  	          if (precioMayorFinal <= 0) 
  	          {
  	        	  theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontro el precio mayor final", 1);
  	        	  return;
  	          }
  	          
  	          if(producto.get_productoReferenciaId() > 0)
  	          {
  	        	  marginacionActual = 100 - ((productoCosto.get_cpp()*100)/precioMayor);
  	          }
  	          else
  	          {
  	        	  marginacionActual = 100 - ((productoCosto.get_cpp()*producto.get_conversion()*100)/precioMayorFinal);
  	          }
  	           
  	          /*if (marginacionActual < marginacionAlmacenadaPaquete)
  	          {
  	        	  this.theUtilidades.MostrarMensaje(getApplicationContext(), "La marginacion del mayor es menor a la establecida.", 1);
  		          return;
  	          }*/
  	          
  	          cantidadUnitaria = 0;
  	          cantidadPaquete = cantidad;
  	          monto = Float.parseFloat(new BigDecimal(cantidad * canalRutaPrecio.get_hpb()).setScale(5,RoundingMode.HALF_UP).toString());
  	          cantidadSolicitadaEnUnidades = cantidadPaquete * producto.get_conversion();
  	          
  	          if(descuentoPaquete > 0)
  	          {
  	        	  descuentoTotal = descuentoTotal + (monto  * (descuentoPaquete/100)); 
  	          }
  	          
  	          if(clientePreventa.get_descuento() > 0)
  	          {
  	        	  descuentoTotal = descuentoTotal + (monto * (clientePreventa.get_descuento()/100)); 
  	          }
  	          
  	          float dsctoCanal = cantidadPaquete * descuentoCanal;
	          float dsctoAjuste = cantidadPaquete * descuentoAjuste;
	          float dctoProntoPago = cantidadPaquete * dsctoProntoPago;
  	        	  
  	          montoFinal = monto - descuentoTotal - dsctoCanal - dsctoAjuste - dctoProntoPago;
  	              	          
  	          costoId = productoCosto.get_costoId();
  	      }
  	      
  	      if(!ValidarMontoCondicionTributariaCon(montoFinal))
  	      {
  	    	  return;
  	      }
  	      
  	      if(venderCredito)
  	      {
  	    	  if(!ValidarCondicionCredito(montoFinal))
  	    	  {
  	    		  return;
  	    	  }
  	      }
  	      
  	      localVentaDirectaProductoTemp.set_tempId(0);
  	      localVentaDirectaProductoTemp.set_clienteId(clientePreventa.get_clienteId());
  	      localVentaDirectaProductoTemp.set_productoId(producto.get_productoId());
  	      localVentaDirectaProductoTemp.set_cantidad(cantidadUnitaria);
  	      localVentaDirectaProductoTemp.set_cantidadPaquete(cantidadPaquete);
  	      localVentaDirectaProductoTemp.set_monto(monto);
  	      localVentaDirectaProductoTemp.set_descuento(descuentoTotal);
  	      localVentaDirectaProductoTemp.set_montoFinal(montoFinal);
  	      localVentaDirectaProductoTemp.set_empleadoId(loginEmpleado.get_empleadoId());
  	      localVentaDirectaProductoTemp.set_promocionId(0);
  	      localVentaDirectaProductoTemp.set_costoId(costoId);
  	      localVentaDirectaProductoTemp.set_noVentaDirecta(noVentaDirecta);
  	      
  	      if(precioLista == null)
  	      {
  	    	  localVentaDirectaProductoTemp.set_precioId(0);
  	      }
  	      else
  	      {
  	    	  localVentaDirectaProductoTemp.set_precioId(precioLista.get_precioId());
  	      }
  	      
  	      float dsctoCanal = canalRutaPrecio.get_hpb() * (canalRutaPrecio.get_descuentoCanal() / 100);
  	      if(cantidadUnitaria > 0)
  	      {
  	    	  localVentaDirectaProductoTemp.set_descuentoCanal((dsctoCanal * cantidadUnitaria) / producto.get_conversion());
  	      }
  	      else
  	      {
  	    	  localVentaDirectaProductoTemp.set_descuentoCanal(dsctoCanal * cantidadPaquete);
  	      }
  	      
  	      float dsctoAjuste = canalRutaPrecio.get_hpb() * (canalRutaPrecio.get_descuentoAjuste() / 100);
  	      if(cantidadPaquete > 0)
  	      {
  	    	  localVentaDirectaProductoTemp.set_descuentoAjuste(dsctoAjuste * cantidadPaquete);
  	      }
  	      else
  	      {
  	    	  localVentaDirectaProductoTemp.set_descuentoAjuste((dsctoAjuste * cantidadUnitaria) / producto.get_conversion());
  	      }
  	      
	  	    float dctoProntoPago = canalRutaPrecio.get_hpb() * (descuentoProntoPagoUni / 100);
			if(cantidadUnitaria > 0)
			{
				localVentaDirectaProductoTemp.set_descuentoProntoPago((dctoProntoPago * cantidadUnitaria) / producto.get_conversion());
			}
			else
			{
				localVentaDirectaProductoTemp.set_descuentoProntoPago(dctoProntoPago * cantidadPaquete);
			}
  	      
  	      localVentaDirectaProductoTemp.set_canalPrecioRutaId(canalRutaPrecio.get_canalPrecioRutaId());
        }
        else
        {  
        	if(precioLista.get_precioId() <= 0)
    		{
           	  	theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo obtener el precioId del producto, precioId<=0.",1);
           	  	return;
    		}
        	
        	float dsctoProntoPago = 0;
	
	   	  	if(rbtUnidades.isChecked())
	   	  	{
		   	  	descuentoUnitario = precioLista.get_descuentoUnidad();
	             
	    		marginacionAlmacenada = 0;
	    		marginacionAlmacenada = precioLista.get_margenUnidad();
	                       
	    		if(marginacionAlmacenada <= 0)
	    		{
	    			margenProveedorPrecioLista = null;
	           	  	try
	           	  	{
	           	  		margenProveedorPrecioLista = new ProveedorPrecioListaMargenBLL().ObtenerMargenProveedorPrecioListaMargenPor(producto.get_proveedorId(), precioLista.get_precioListaId());
	           	  	}
	           	  	catch(Exception localException)
	           	  	{
	           	  		if(localException.getMessage() == null || localException.getMessage().isEmpty())
	           	  		{
	           	  			myLog.InsertarLog("App",this.toString(),1,"Error al obtener el margen del proveedorPrecioListaMargen: vacio");
	       	  			}
	           	  		else
	           	  		{
	           	  			myLog.InsertarLog("App",this.toString(),1,"Error al obtener el margen del proveedorPrecioListaMargen: " + localException.getMessage());
	           	  		}
	           	  		return;
	           	  	}
	           	  
	           	  	if(margenProveedorPrecioLista == null)
	           	  	{
	           	  		ParametroGeneral localParametroGeneral = null;
	           		  
	           	  		try
	           	  		{
	           	  			localParametroGeneral = new ParametroGeneralBLL().ObtenerParamatroGeneral();
	           	  		}
	           	  		catch(Exception localException)
	           	  		{
	           	  			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	           	  			{
	           	  				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del parametroGeneral: vacio");
	           	  			}
	           	  			else
	           	  			{
	           	  				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del parametroGeneral: " + localException.getMessage());
	           	  			}
	           	  			return;
	           	  		}
	           		  
	           	  		if(localParametroGeneral == null)
	           	  		{
	           	  			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontro ninguna marginacion para aplicar al producto.", 1);
	           	  			return;
	           	  		}
	           	  		else
	           	  		{
	           	  			marginacionAlmacenada = localParametroGeneral.get_margenMinimo();
	           	  		}
	           	  	}
	           	  	else
	           	  	{
	           	  		marginacionAlmacenada = margenProveedorPrecioLista.get_margen();
	           	  	}
	    		}
	   	  	
	   	  		precioUnitario = 0;
		    	  
	   	  		try
	   	  		{
	   	  			precioUnitario = Float.parseFloat(tvPrecioUnitario.getText().toString());
	   	  		}
	   	  		catch(Exception localException)
	   	  		{
	   	  			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	   	  			{
	   	  				myLog.InsertarLog("App",this.toString(),1,"Error al convertir el precio unitario de string a float: vacio");
	   	  			}
	   	  			else
	   	  			{
	   	  				myLog.InsertarLog("App",this.toString(),1,"Error al convertir el precio unitario de string a float: " + localException.getMessage());
	   	  			}
	   	  			return;
	   	  		}
		    	  
	   	  		if (precioUnitario <= 0) 
	   	  		{
	   	  			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontro el precio unitario", 1);
	   	  			return;
	   	  		}
		          
	   	  		marginacionActual = 100 - ((productoCosto.get_cpp()*100)/precioUnitario); 
	   	  		
	   	  		if (marginacionActual < marginacionAlmacenada) 
	   	  		{
	   	  			theUtilidades.MostrarMensaje(getApplicationContext(), "La marginacion de la unidad es menor a la establecida.", 1);
	   	  			return;
	   	  		}
		          
	   	  		cantidadUnitaria = cantidad;
	   	  		cantidadPaquete = 0;
	   	  		monto = Float.parseFloat(new BigDecimal(cantidad * precioUnitario).setScale(5,RoundingMode.HALF_UP).toString());
	   	  		
	   	  		//Verificar si es producto variante
	   	  		if(producto.get_productoReferenciaId() > 0)
	   	  		{
	   	  			cantidadSolicitadaEnUnidades = cantidadUnitaria * producto.get_conversion();
	   	  		}
	   	  		else
	   	  		{
		        	  cantidadSolicitadaEnUnidades = cantidadUnitaria;
	          	}
		          
	   	  		if(descuentoUnitario > 0)
	   	  		{
	   	  			descuentoTotal = descuentoTotal + (monto * (descuentoUnitario/100)); 
	   	  		}
		          
	   	  		if(clientePreventa.get_descuento() > 0)
	   	  		{
	   	  			descuentoTotal = descuentoTotal + (monto * (clientePreventa.get_descuento()/100)); 
	   	  		}
		        	  
	   	  		dsctoProntoPago = monto * (descuentoProntoPagoUni / 100);

	   	  		montoFinal = monto - descuentoTotal - dsctoProntoPago;
		          
	   	  		costoId = productoCosto.get_costoId();
	   	  	}
		      
	   	  	if(rbtMayor.isChecked())
	   	  	{
		   	  	descuentoPaquete = precioLista.get_descuentoPaquete();
	    		
		        marginacionAlmacenadaPaquete = 0;
		  		marginacionAlmacenadaPaquete = precioLista.get_margenPaquete();  
		   	  
		   	  	if(marginacionAlmacenadaPaquete <= 0)
		   	  	{
		   	  		margenProveedorPrecioLista = null;
		   	  		try
		   	  		{
		   	  			margenProveedorPrecioLista = new ProveedorPrecioListaMargenBLL().ObtenerMargenProveedorPrecioListaMargenPor(producto.get_proveedorId(), precioLista.get_precioListaId());
		   	  		}
		   	  		catch(Exception localException)
		   	  		{
		   	  			if(localException.getMessage() == null || localException.getMessage().isEmpty())
		   	  			{
		   	  				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el margen del proveedorPrecioListaMargen: vacio");
		   	  			}
		   	  			else
		   	  			{
		   	  				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el margen del proveedorPrecioListaMargen: " + localException.getMessage());
		   	  			}
		   	  			return;
		   	  		}
		       	  
		   	  		if(margenProveedorPrecioLista == null)
		   	  		{
		   	  			ParametroGeneral localParametroGeneral = null;
		       		  
		   	  			try
		   	  			{
		   	  				localParametroGeneral = new ParametroGeneralBLL().ObtenerParamatroGeneral();
		   	  			}	
		   	  			catch(Exception localException)
		   	  			{
		   	  				if(localException.getMessage() == null || localException.getMessage().isEmpty())
		   	  				{
		   	  					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del parametroGeneral: vacio");
		   	  				}
		   	  				else
		   	  				{
		   	  					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del parametroGeneral: " + localException.getMessage());
		   	  				}
		   	  				return;
		   	  			}
		       		  
		   	  			if(localParametroGeneral == null)
		   	  			{
		   	  				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontro ninguna marginacion para aplicar al producto.", 1);
		   	  				return;
		   	  			}
		   	  			else
		   	  			{
		   	  				marginacionAlmacenadaPaquete = localParametroGeneral.get_margenMinimo();
		   	  			}
		   	  		}
		   	  		else
		   	  		{
		   	  			marginacionAlmacenadaPaquete = margenProveedorPrecioLista.get_margen();
		   	  		}
		   	  	}
	   	  	
	   	  		precioMayor = 0;
	   	  		try
	   	  		{
	   	  			precioMayor = Float.parseFloat(tvPrecioMayor.getText().toString());
	   	  		}
	   	  		catch(Exception localException)
	   	  		{
	   	  			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	   	  			{
	   	  				myLog.InsertarLog("App",this.toString(),1,"Error al convertir el precio mayor de string a float: vacio");
	   	  			}
	   	  			else
	   	  			{
	   	  				myLog.InsertarLog("App",this.toString(),1,"Error al convertir el precio mayor de string a float: " + localException.getMessage());
	   	  			}
	   	  			return;
	   	  		}
		    	  
	   	  		if (precioMayor <= 0) 
		        {
	   	  			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontro el precio mayor", 1);
	   	  			return;
		        }
	   	  		
	   	  		if(producto.get_productoReferenciaId() > 0)
	   	  		{
	   	  			marginacionActual = 100 - ((productoCosto.get_cpp()*100)/precioMayor);
	   	  		}
	   	  		else
	   	  		{
	   	  			marginacionActual = 100 - ((productoCosto.get_cpp()*producto.get_conversion()*100)/precioMayor);
	   	  		} 
	   	  		
	   	  		if (marginacionActual < marginacionAlmacenadaPaquete)
	   	  		{
	   	  			this.theUtilidades.MostrarMensaje(getApplicationContext(), "La marginacion del mayor es menor a la establecida.", 1);
	   	  			return;
	   	  		}
		          
	   	  		cantidadUnitaria = 0;
	   	  		cantidadPaquete = cantidad;
	   	  		monto = Float.parseFloat(new BigDecimal(cantidad * precioMayor).setScale(5,RoundingMode.HALF_UP).toString());
	   	  		cantidadSolicitadaEnUnidades = cantidadPaquete * producto.get_conversion();
		          
	   	  		if(descuentoPaquete > 0)
	   	  		{
	   	  			descuentoTotal = descuentoTotal + (monto  * (descuentoPaquete/100)); 
	   	  		}
		          
	   	  		if(clientePreventa.get_descuento() > 0)
	   	  		{
	   	  			descuentoTotal = descuentoTotal + (monto * (clientePreventa.get_descuento()/100)); 
	   	  		}
		        	  
		   	  	dsctoProntoPago = monto * (descuentoProntoPagoUni / 100);
	
				montoFinal = monto - descuentoTotal - dsctoProntoPago;
		          
	   	  		costoId = productoCosto.get_costoId();
	   	  	}
	   	  	
	   	  	if(!ValidarMontoCondicionTributariaCon(montoFinal))
	   	  	{
	   	  		return;
	   	  	}
	   	  	
		   	 if(venderCredito)
		     {
		   		if(!ValidarCondicionCredito(montoFinal))
		    	  {
		    		  return;
		    	  }
		     }
		      
	   	  	localVentaDirectaProductoTemp.set_tempId(0);
	   	  	localVentaDirectaProductoTemp.set_clienteId(clientePreventa.get_clienteId());
		   	localVentaDirectaProductoTemp.set_productoId(producto.get_productoId());
		   	localVentaDirectaProductoTemp.set_cantidad(cantidadUnitaria);
		   	localVentaDirectaProductoTemp.set_cantidadPaquete(cantidadPaquete);
		   	localVentaDirectaProductoTemp.set_monto(monto);
		   	localVentaDirectaProductoTemp.set_descuento(descuentoTotal);
		   	localVentaDirectaProductoTemp.set_montoFinal(montoFinal);
		   	localVentaDirectaProductoTemp.set_empleadoId(loginEmpleado.get_empleadoId());
		   	localVentaDirectaProductoTemp.set_promocionId(0);
		   	localVentaDirectaProductoTemp.set_costoId(costoId);
		   	localVentaDirectaProductoTemp.set_noVentaDirecta(noVentaDirecta);
		   	localVentaDirectaProductoTemp.set_precioId(precioLista.get_precioId());
		   	localVentaDirectaProductoTemp.set_descuentoCanal(0);
		   	localVentaDirectaProductoTemp.set_descuentoAjuste(0);
		   	localVentaDirectaProductoTemp.set_descuentoProntoPago(dsctoProntoPago);
		   	localVentaDirectaProductoTemp.set_canalPrecioRutaId(0);
        }
        
	    int productoIdAValidar = 0;
	    if(producto.get_productoReferenciaId() > 0)
	    {
	    	productoIdAValidar = producto.get_productoReferenciaId();
	    }
	    else
	    {
	    	productoIdAValidar = producto.get_productoId(); 
	    }
	      
   	  	if (ValidarExistenciasAlmacenTemporalDispositivo(loginEmpleado.get_almacenId(),productoIdAValidar,
   	  			producto.get_conversion(),cantidadSolicitadaEnUnidades))
   	  	{
	   	  	if(parametroGeneral.get_montoCi() > 0)
	   	  	{
	   	  		if(ObtenerVentaDirectaProductoTempMontoFinalAlmacenadoActual()+localVentaDirectaProductoTemp.get_montoFinal() >= parametroGeneral.get_montoCi())
	   	  		{
	   	  			theUtilidades.MostrarMensaje(getApplicationContext(), "No se puede ingresar el producto, ya que el monto excede el definido por el CI.", 1);
	   	  			return;
	   	  		}
	   	  	}
	  	  
	   	  	if(parametroGeneral.get_montoBancarizacion() > 0)
	   	  	{
	   	  		if(ObtenerVentaDirectaProductoTempMontoFinalAlmacenadoActual()+localVentaDirectaProductoTemp.get_montoFinal() >= parametroGeneral.get_montoBancarizacion())
	   	  		{
	   	  			theUtilidades.MostrarMensaje(getApplicationContext(), "No se puede ingresar el producto, ya que el monto excede el definido por el de bancarizacion.", 1);
	   	  			return;
	   	  		}
	   	  	}
   	  		
   	  		if(parametroGeneral.get_tipoImpresionFactura().equals("Medio Oficio"))
   	  		{
   	  			if(listadoVentaDirectaProductoTemp == null || listadoVentaDirectaProductoTemp.size() <= 14)
   	  			{
   	  				if(InsertarVentaDirectaProductoTempDispositivo(localVentaDirectaProductoTemp))
   	  				{
   	  					theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la venta directa insertada en el dispositivo.", 1);
   	  					CargarProductos();
   	  					DespliegueControlesAdicionarPreventa(false);
   	  					ActualizarListView();
   	  				}
   	  				else 
   	  				{
   	  					theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el producto de la venta directa en el dispositivo.",1);
   	  					return;
   	  				}
    		  }
   	  			else
   	  			{
   	  				theUtilidades.MostrarMensaje(getApplicationContext(),"Se alcanzo el tope de items para la factura medio oficio, confirme la preventa e ingrese una nueva.",1);
   	  				return;
   	  			}
   	  		}
   	  		else if(parametroGeneral.get_tipoImpresionFactura().equals("Oficio"))
   	  		{
   	  			if(listadoVentaDirectaProductoTemp == null || listadoVentaDirectaProductoTemp.size() <= 34)
	    		  {
	    			  if(InsertarVentaDirectaProductoTempDispositivo(localVentaDirectaProductoTemp))
			    	  {
			    		  theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la venta directa insertada en el dispositivo.", 1);
			    		  CargarProductos();
			    		  DespliegueControlesAdicionarPreventa(false);
			    		  ActualizarListView();
			    	  }
			    	  else 
			    	  {
			    		  theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el producto de la venta directa en el dispositivo.",1);
			    		  return;
			    	  }
	    		  }
	    		  else
	    		  {
	    			  theUtilidades.MostrarMensaje(getApplicationContext(),"Se alcanzo el tope de items para la factura oficio, confirme la venta directa e ingrese una nueva.",1);
		    		  return;
	    		  }
	    	  }
	    	  else
	    	  {
	    		  if(InsertarVentaDirectaProductoTempDispositivo(localVentaDirectaProductoTemp))
		    	  {
		    		  theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la venta directa insertada en el dispositivo.", 1);
		    		  CargarProductos();
		    		  DespliegueControlesAdicionarPreventa(false);
		    		  ActualizarListView();
		    	  }
		    	  else 
		    	  {
		    		  theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el producto de la venta directa en el dispositivo.",1);
		    		  return;
		    	  }
	    	  }
	      }
	      else
	      {
	    	  int saldoUnidades = ObtenerExistenciaProductoEnAlmacenPor(loginEmpleado.get_almacenId(),productoIdAValidar,producto.get_conversion());
	    	  theUtilidades.MostrarMensaje(getApplicationContext(), "No existe producto en el almacen del dispositivo."
	    	  									+ "Cantidad actual: " + String.valueOf(saldoUnidades) + " unidades.", 1);
	    	  return;
	      }
	 }
	
	private float ObtenerVentaDirectaProductoTempMontoFinalAlmacenadoActual()
	{
		float montoFinal = 0;
		ArrayList<VentaDirectaProductoTemp> listadoVentaTemporal = null;
		try
		{
			listadoVentaTemporal = new VentaDirectaProductoTempBLL().ObtenerVentasDirectasProductoTemporClienteIdNoVentaDirecta(clienteId, noVentaDirecta);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
  		  	{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtner las ventasDirectasTemporales por clienteId y numeroVenta: vacio");
  		  	}
  		  	else
  		  	{
  		  		myLog.InsertarLog("App",this.toString(),1,"Error al obtner las ventasDirectasTemporales por clienteId y numeroVenta" + localException.getMessage());
  		  	}
		}
		
		if(listadoVentaTemporal != null)
		{
			for(VentaDirectaProductoTemp item : listadoVentaTemporal)
			{
				montoFinal = montoFinal + item.get_montoFinal();
			}
		}
		
		return montoFinal;		
	}
	
	private float ObtenerProntoPagoJerarquia()
	{
		float descuentoJerarquia = 0;
		//1. Verificamos si el cliente tiene prontoPago directo
		ProntoPagoCliente prontoPagoCliente = null;
		try
		{
			prontoPagoCliente = new ProntoPagoClienteBLL().ObtenerProntoPagoClientePor(clientePreventa.get_clienteId());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoCliente por clienteId: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoCliente por clienteId: " + localException.getMessage());
			 }
		}
		
		if(prontoPagoCliente == null)
		{
			//2. Verificamos si el cliente tiene ProntoPago por CanalRuta
			ProntoPagoCanalRuta prontoPagoCanalRuta = null;
			try
			{
				prontoPagoCanalRuta = new ProntoPagoCanalRutaBLL().ObtenerProntoPagoCanalRutaPor(clientePreventa.get_canalRutaId());
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				 {
					 myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoCanalRuta por canalRutaId: vacio");
				 }
				 else
				 {
					 myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoCanalRuta por canlRutaoId: " + localException.getMessage());
				 }
			}
			
			if(prontoPagoCanalRuta ==  null)
			{
				return 0;
			}
			else
			{
				//Obtenemos el descuento de las jerarquias
				int jerarquia1Id = 0;
				try
				{
					jerarquia1Id = new ProntoPagoJerarquiaBLL().ObtenerProntoPagoJerarquia1IdPorProductoId(producto.get_productoId());
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia1 por productoId: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia1 por productoId: " + localException.getMessage());
					}
				}

				if(jerarquia1Id > 0)
				{
					ProntoPagoJerarquia prontoPagoJerarquia1 = null;
					try
					{
						prontoPagoJerarquia1 = new ProntoPagoJerarquiaBLL().ObtenerProntoPagoJerarquia1Por(jerarquia1Id);
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia por jerarquia1Id: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia por jerarquia1Id: " + localException.getMessage());
						}
					}

					if(prontoPagoJerarquia1 != null)
					{
						descuentoJerarquia = prontoPagoJerarquia1.get_descuento();
						prontoPagoId = prontoPagoJerarquia1.get_prontoPagoId();
					}
				}

				if(descuentoJerarquia == 0)
				{
					int jerarquia2Id = 0;
					try
					{
						jerarquia2Id = new ProntoPagoJerarquiaBLL().ObtenerProntoPagoJerarquia2IdPorProductoId(producto.get_productoId());
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia2 por productoId: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia2 por productoId: " + localException.getMessage());
						}
					}

					ProntoPagoJerarquia prontoPagoJerarquia2 = null;
					try
					{
						prontoPagoJerarquia2 = new ProntoPagoJerarquiaBLL().ObtenerProntoPagoJerarquia2Por(jerarquia2Id);
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia por jerarquia2Id: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia por jerarquia2Id: " + localException.getMessage());
						}
					}

					if(prontoPagoJerarquia2 != null)
					{
						descuentoJerarquia = prontoPagoJerarquia2.get_descuento();
						prontoPagoId = prontoPagoJerarquia2.get_prontoPagoId();
					}
				}

				if(descuentoJerarquia == 0)
				{
					int jerarquia3Id = 0;
					try
					{
						jerarquia3Id = new ProntoPagoJerarquiaBLL().ObtenerProntoPagoJerarquia3IdPorProductoId(producto.get_productoId());
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia3 por productoId: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia3 por productoId: " + localException.getMessage());
						}
					}

					ProntoPagoJerarquia prontoPagoJerarquia3 = null;
					try
					{
						prontoPagoJerarquia3 = new ProntoPagoJerarquiaBLL().ObtenerProntoPagoJerarquia3Por(jerarquia3Id);
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia por jerarquia3Id: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia por jerarquia3Id: " + localException.getMessage());
						}
					}

					if(prontoPagoJerarquia3 != null)
					{
						descuentoJerarquia = prontoPagoJerarquia3.get_descuento();
						prontoPagoId = prontoPagoJerarquia3.get_prontoPagoId();
					}
				}

				if(descuentoJerarquia == 0)
				{
					int jerarquia5Id = 0;
					try
					{
						jerarquia5Id = new ProntoPagoJerarquiaBLL().ObtenerProntoPagoJerarquia5IdPorProductoId(producto.get_productoId());
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia5 por productoId: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia5 por productoId: " + localException.getMessage());
						}
					}

					ProntoPagoJerarquia prontoPagoJerarquia5 = null;
					try
					{
						prontoPagoJerarquia5 = new ProntoPagoJerarquiaBLL().ObtenerProntoPagoJerarquia5Por(jerarquia5Id);
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia por jerarquia5Id: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia por jerarquia5Id: " + localException.getMessage());
						}
					}

					if(prontoPagoJerarquia5 != null)
					{
						descuentoJerarquia = prontoPagoJerarquia5.get_descuento();
						prontoPagoId = prontoPagoJerarquia5.get_prontoPagoId();
					}
				}

				if(descuentoJerarquia == 0)
				{
					int categoriaId = 0;
					try
					{
						categoriaId = new ProntoPagoJerarquiaBLL().ObtenerProntoPagoCategoriaIdPorProductoId(producto.get_productoId());
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoCategoriaId por productoId: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoCategoriaId por productoId: " + localException.getMessage());
						}
					}

					ProntoPagoJerarquia prontoPagoCategoria = null;
					try
					{
						prontoPagoCategoria = new ProntoPagoJerarquiaBLL().ObtenerProntoPagoCategoriaIdPor(categoriaId);
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia por categoriaId: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia por categoriaId: " + localException.getMessage());
						}
					}

					if(prontoPagoCategoria != null)
					{
						descuentoJerarquia = prontoPagoCategoria.get_descuento();
						prontoPagoId = prontoPagoCategoria.get_prontoPagoId();
					}
				}
			}
		}
		else
		{
			//Obtenemos el descuento de las jerarquias
			int jerarquia1Id = 0;
			try
			{
				jerarquia1Id = new ProntoPagoJerarquiaBLL().ObtenerProntoPagoJerarquia1IdPorProductoId(producto.get_productoId());
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia1 por productoId: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia1 por productoId: " + localException.getMessage());
				}
			}

			if(jerarquia1Id > 0)
			{
				ProntoPagoJerarquia prontoPagoJerarquia1 = null;
				try
				{
					prontoPagoJerarquia1 = new ProntoPagoJerarquiaBLL().ObtenerProntoPagoJerarquia1Por(jerarquia1Id);
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia por jerarquia1Id: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia por jerarquia1Id: " + localException.getMessage());
					}
				}

				if(prontoPagoJerarquia1 != null)
				{
					descuentoJerarquia = prontoPagoJerarquia1.get_descuento();
					prontoPagoId = prontoPagoJerarquia1.get_prontoPagoId();
				}
			}

			if(descuentoJerarquia == 0)
			{
				int jerarquia2Id = 0;
				try
				{
					jerarquia2Id = new ProntoPagoJerarquiaBLL().ObtenerProntoPagoJerarquia2IdPorProductoId(producto.get_productoId());
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia2 por productoId: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia2 por productoId: " + localException.getMessage());
					}
				}

				ProntoPagoJerarquia prontoPagoJerarquia2 = null;
				try
				{
					prontoPagoJerarquia2 = new ProntoPagoJerarquiaBLL().ObtenerProntoPagoJerarquia2Por(jerarquia2Id);
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia por jerarquia2Id: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia por jerarquia2Id: " + localException.getMessage());
					}
				}

				if(prontoPagoJerarquia2 != null)
				{
					descuentoJerarquia = prontoPagoJerarquia2.get_descuento();
					prontoPagoId = prontoPagoJerarquia2.get_prontoPagoId();
				}
			}

			if(descuentoJerarquia == 0)
			{
				int jerarquia3Id = 0;
				try
				{
					jerarquia3Id = new ProntoPagoJerarquiaBLL().ObtenerProntoPagoJerarquia3IdPorProductoId(producto.get_productoId());
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia3 por productoId: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia3 por productoId: " + localException.getMessage());
					}
				}

				ProntoPagoJerarquia prontoPagoJerarquia3 = null;
				try
				{
					prontoPagoJerarquia3 = new ProntoPagoJerarquiaBLL().ObtenerProntoPagoJerarquia3Por(jerarquia3Id);
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia por jerarquia3Id: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia por jerarquia3Id: " + localException.getMessage());
					}
				}

				if(prontoPagoJerarquia3 != null)
				{
					descuentoJerarquia = prontoPagoJerarquia3.get_descuento();
					prontoPagoId = prontoPagoJerarquia3.get_prontoPagoId();
				}
			}

			if(descuentoJerarquia == 0)
			{
				int jerarquia5Id = 0;
				try
				{
					jerarquia5Id = new ProntoPagoJerarquiaBLL().ObtenerProntoPagoJerarquia5IdPorProductoId(producto.get_productoId());
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia5 por productoId: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia5 por productoId: " + localException.getMessage());
					}
				}

				ProntoPagoJerarquia prontoPagoJerarquia5 = null;
				try
				{
					prontoPagoJerarquia5 = new ProntoPagoJerarquiaBLL().ObtenerProntoPagoJerarquia5Por(jerarquia5Id);
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia por jerarquia5Id: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia por jerarquia5Id: " + localException.getMessage());
					}
				}

				if(prontoPagoJerarquia5 != null)
				{
					descuentoJerarquia = prontoPagoJerarquia5.get_descuento();
					prontoPagoId = prontoPagoJerarquia5.get_prontoPagoId();
				}
			}

			if(descuentoJerarquia == 0)
			{
				int categoriaId = 0;
				try
				{
					categoriaId = new ProntoPagoJerarquiaBLL().ObtenerProntoPagoCategoriaIdPorProductoId(producto.get_productoId());
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoCategoriaId por productoId: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoCategoriaId por productoId: " + localException.getMessage());
					}
				}

				ProntoPagoJerarquia prontoPagoCategoria = null;
				try
				{
					prontoPagoCategoria = new ProntoPagoJerarquiaBLL().ObtenerProntoPagoCategoriaIdPor(categoriaId);
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia por categoriaId: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prontoPagoJerarquia por categoriaId: " + localException.getMessage());
					}
				}

				if(prontoPagoCategoria != null)
				{
					descuentoJerarquia = prontoPagoCategoria.get_descuento();
					prontoPagoId = prontoPagoCategoria.get_prontoPagoId();
				}
			}
		}

		return descuentoJerarquia;
	}
	
	private DraRebateSaldo VerificarClienteTieneSaldoRebate()
	{
		DraRebateSaldo draRebateSaldo = null;
		
		try
		{
			draRebateSaldo = new DraRebateSaldoBLL().ObtenerDraRebateSaldoPor(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el saldo del rebate dracaris por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el saldo del rebate dracaris por clienteId: " + localException.getMessage());
			}
		}
		
		return draRebateSaldo;
	}
	
	private void ActualizarDraRebateSaldo(float saldo)
	{
		long l=0;
		try
		{
			l = new DraRebateSaldoBLL().ModificarSaldoDraRebateSaldo(clienteId, saldo);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el saldo del rebate dracaris por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el saldo del rebate dracaris por clienteId: " + localException.getMessage());
			}
		}
		
		if(l <= 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo actualizar el saldo del rebate dracaris", 1);
		}
	}
	
	private void InsertarVentaDirectaProductoTemp(VentaDirectaProductoTemp localVentaDirectaProductoTemp)
	{
	    pdInsertarVentaDirectaProductoTemp = new ProgressDialog(this);
	    pdInsertarVentaDirectaProductoTemp.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdInsertarVentaDirectaProductoTemp.setMessage("Insertando productos venta directa ...");
	    pdInsertarVentaDirectaProductoTemp.setCancelable(false);
	    pdInsertarVentaDirectaProductoTemp.setCanceledOnTouchOutside(false);
	    
	    WSInsertarVentaDirectaProductoTemp localWSInsertarVentaDirectaProductoTemp = new WSInsertarVentaDirectaProductoTemp(localVentaDirectaProductoTemp);
	    try
	    {
	    	localWSInsertarVentaDirectaProductoTemp.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarVentaDirectaProductoTemp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarVentaDirectaProductoTemp: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertarVentaDirectaProductoTemp", 1);
	    }
	 }
	 
	public class WSInsertarVentaDirectaProductoTemp extends AsyncTask<Void, Integer, Boolean>
	 {
		 String VENTADIRECTAPRODUCTO_METHOD_NAME = "InsertVentaDirectaProductoTemp";
		 String VENTADIRECTAPRODUCTO_SOAP_ACTION = NAMESPACE + VENTADIRECTAPRODUCTO_METHOD_NAME;
		 
		 private VentaDirectaProductoTemp _ventaDirectaProductoTemp;
		 
		 boolean WSInsertarVentaDirectaProductoTemp;
		 int resultadoInt;
		 String resultadoString;
		 SoapObject localSoapObjects;
	    
		 public WSInsertarVentaDirectaProductoTemp(VentaDirectaProductoTemp paramVentaDirectaProductoTemp)
		 {
			 _ventaDirectaProductoTemp = paramVentaDirectaProductoTemp;
		 }
	    
		 protected void onPreExecute()
		 {
			 pdInsertarVentaDirectaProductoTemp.show();
		 }
	    
		 protected Boolean doInBackground(Void... paramVarArgs)
		 {
			 WSInsertarVentaDirectaProductoTemp = false;
	      
			 SoapObject localSoapObject = new SoapObject(NAMESPACE, this.VENTADIRECTAPRODUCTO_METHOD_NAME);
			 localSoapObject.addProperty("productoId", _ventaDirectaProductoTemp.get_productoId());
			 localSoapObject.addProperty("promocionId", _ventaDirectaProductoTemp.get_promocionId());
			 localSoapObject.addProperty("cantidad", _ventaDirectaProductoTemp.get_cantidad());
			 localSoapObject.addProperty("cantidadPaquete", _ventaDirectaProductoTemp.get_cantidadPaquete());
			 localSoapObject.addProperty("monto", String.valueOf(_ventaDirectaProductoTemp.get_monto()));
			 localSoapObject.addProperty("descuento", String.valueOf(_ventaDirectaProductoTemp.get_descuento()));
			 localSoapObject.addProperty("montoFinal", String.valueOf(_ventaDirectaProductoTemp.get_montoFinal()));
			 localSoapObject.addProperty("costoId",_ventaDirectaProductoTemp.get_costoId());
			 localSoapObject.addProperty("precioId",_ventaDirectaProductoTemp.get_precioId());
			 localSoapObject.addProperty("clienteId", clienteId);
			 localSoapObject.addProperty("vendedorId", loginEmpleado.get_empleadoId());
			 localSoapObject.addProperty("almacenId",loginEmpleado.get_almacenId());
			 localSoapObject.addProperty("nroVenta",noVentaDirecta);
			 localSoapObject.addProperty("descuentoCanal",String.valueOf(_ventaDirectaProductoTemp.get_descuentoCanal()));
			 localSoapObject.addProperty("descuentoAjuste",String.valueOf(_ventaDirectaProductoTemp.get_descuentoAjuste()));
			 localSoapObject.addProperty("canalRutaPrecioId",_ventaDirectaProductoTemp.get_canalPrecioRutaId());
			 localSoapObject.addProperty("descuentoProntoPago",String.valueOf(_ventaDirectaProductoTemp.get_descuentoProntoPago()));
			
			 SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			 localSoapSerializationEnvelope.dotNet = true;
			 localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			 HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			 try
		 	 {
				 localHttpTransportSE.call(VENTADIRECTAPRODUCTO_SOAP_ACTION, localSoapSerializationEnvelope);
   			
				 localSoapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
				 if(localSoapObjects!=null)
				 {
					 resultadoInt = Integer.parseInt(localSoapObjects.getPropertyAsString("Id"));
					 resultadoString = localSoapObjects.getPropertyAsString("Resultado");    				
				 }
   			
				 if(resultadoString.equals("OK") && resultadoInt > 0)
				 {
					 WSInsertarVentaDirectaProductoTemp = true;
				 }
				 return true;
		 	 }
			 catch (Exception localException)
			 {
				 WSInsertarVentaDirectaProductoTemp = false;
				 if(localException.getMessage() == null || localException.getMessage().isEmpty())
				 {
					 myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertVentaDirectaProductoTemp: vacio");
				 }
				 else
				 {
					 myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertVentaDirectaProductoTemp: " + localException.getMessage());
				 } 
				 return true;
			 }
		 }
	    
		 protected void onPostExecute(Boolean ejecutado)
		 {
			 if(pdInsertarVentaDirectaProductoTemp.isShowing())
			 {
				 pdInsertarVentaDirectaProductoTemp.dismiss();
			 }
	    	
			 if(ejecutado) 
			 {
				 if(WSInsertarVentaDirectaProductoTemp || (resultadoString != null && resultadoString.equals("Venta Directa Producto Temp Repetido") && resultadoInt <= 0))
				 {
					 if(resultadoInt <= 0)
					 {
						 resultadoInt = 1;
					 }
					 long l = 0;
					 try
					 {
						 l = new VentaDirectaProductoTempBLL().ModificarVentaDirectaProductoTempNoSincronizadaPor(_ventaDirectaProductoTemp.get_id(),resultadoInt);
					 }
					 catch (Exception localException)
					 {
						 if(localException.getMessage() == null || localException.getMessage().isEmpty())
						 {
							 myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del producto de la venta directa: vacio");
						 }
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del producto de la venta directa: " + localException.getMessage());
						} 
					 }
						
					 if(l==0)
					 {
						 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del producto de la venta directa.", 1);
						 return;
					 }
					 
					 if(InsertarVentaDirectaProductoDispositivo(_ventaDirectaProductoTemp))
					 {
						 theUtilidades.MostrarMensaje(getApplicationContext(), "Producto de la venta directa sincronizado.", 1);
						 
						 if(ObtenerVentasDirectasProductoTempNoSincronizadas())
						 {
							 SincronizarVentaDirectaProducto();
						 }
						 else
						 {
							 InsertarVentaDirecta();
						 }
					 }
					 else
					 {	
						 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el producto de la venta directa.", 1);
					 }
	    		}
	    		else 
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo ejecutar el webservice WSInsertVentaDirectaProductoTemp.", 1);
	    			if((parametroGeneral.is_habilitarPop()) && ObtenerRolEmpleado() == 3)//Rol 3 = vendedor
    				{
    					MostrarPantallaMenuOpciones();;
    				}
    				else
    				{
    					MostrarPantallaMenuVendedor();
    				}
	        	}
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "El WebService InsertVentaDirectaProductoTemp no se ejecuto correctamente. ", 1);
	    	}
	    }
	 }

	private boolean ValidarExistenciasAlmacenTemporalDispositivo(int almacenId, int productoId, int productoConversion, int cantidadSolicitadaEnUnidades)
	{
		AlmacenProducto localAlmacenProducto = null;
		try
		{
			localAlmacenProducto = new AlmacenProductoBLL().ObtenerExistenciaProductoAlmacenProducto(almacenId,productoId,productoConversion,cantidadSolicitadaEnUnidades);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el producto del almacen: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el producto del almacen: " + localException.getMessage());
			}
			return false;
		}
	    if (localAlmacenProducto == null)
	    {
	    	return false;
	    }
	    else
	    {
	    	return true;
	    }
	}
	
	private int ObtenerExistenciaProductoEnAlmacenPor(int almacenId, int productoId, int productoConversion)
	{
		int saldoEnUnidades = 0;
		try
		{
			saldoEnUnidades = new AlmacenProductoBLL().ObtenerExistenciaProductoEnAlmacenPor(almacenId,productoId,productoConversion);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el saldo en unidades del producto del almacen: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el saldo en unidades del producto del almacen: " + localException.getMessage());
			}
			return 0;
		}
	    
		return saldoEnUnidades;
	}

	private boolean InsertarVentaDirectaProductoTempDispositivo(VentaDirectaProductoTemp paramVentaDirectaProductoTemp)
	{
		long l = 0;
		try
		{
			l = new VentaDirectaProductoTempBLL().InsertarVentaDirectaProductoTemp(paramVentaDirectaProductoTemp.get_ventaId(),
					paramVentaDirectaProductoTemp.get_productoId(),paramVentaDirectaProductoTemp.get_promocionId(),  
					paramVentaDirectaProductoTemp.get_cantidad(), paramVentaDirectaProductoTemp.get_cantidadPaquete(),
					paramVentaDirectaProductoTemp.get_monto(), paramVentaDirectaProductoTemp.get_descuento(),
					paramVentaDirectaProductoTemp.get_montoFinal(),paramVentaDirectaProductoTemp.get_costoId(),
					paramVentaDirectaProductoTemp.get_precioId(),paramVentaDirectaProductoTemp.get_noVentaDirecta(),
					paramVentaDirectaProductoTemp.get_clienteId(),paramVentaDirectaProductoTemp.get_tempId(),
					paramVentaDirectaProductoTemp.get_empleadoId(),paramVentaDirectaProductoTemp.get_descuentoCanal(),
					paramVentaDirectaProductoTemp.get_descuentoAjuste(),paramVentaDirectaProductoTemp.get_canalPrecioRutaId(),
					paramVentaDirectaProductoTemp.get_descuentoProntoPago());
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ventaDirectaProductoTemp en el dispositivo: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ventaDirectaProductoTemp en el dispositivo: " + localException.getMessage());
	    	}
	    	return false;
	    }
	    	
	    if(l==0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el producto de la venta directa temporal en el dispositivo.", 1);
	    	return false;		
	    }
	    else
	    {
	    	return true;
	    }
	}
	 
	private void ConfirmarVentaDirecta()
	{
		btnConfirmarVentaDirecta.setVisibility(View.INVISIBLE);
		
		if(ValidarMontoNit())
		{
			if(ValidarMontoCondicionTributaria(montoTributarioServer))
			{
				if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
				{
					if(clienteId > 0)
					{
						if(PrepararVentaDirectaParaInsercion())
						{
							if(ObtenerVentasDirectasProductoTempNoSincronizadas())
							{
								SincronizarVentaDirectaProducto();
							}
							else
							{
								if(VerificarVentasDirectasProductoTotalSincronizadas())
								{
									InsertarVentaDirecta();
								}
								else
								{
									theUtilidades.MostrarMensaje(getApplicationContext(), "No existen productos de la venta directa que sincronizar.", 1);
									return;	
								}
							}
						}
						else
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "La venta directa no se genero correctamente.", 1);
							return;
						}
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "Para ingresar la venta directa debe sincronizar el cliente.", 1);
						if(parametroGeneral.is_habilitarPop())
						{
							MostrarPantallaMenuOpciones();
						}
						else
						{
							MostrarPantallaMenuVendedor();
						}						
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No esta conectado a internet, intentelo mas tarde.", 1);
					MostrarPantallaMenuVendedor();
				}
			}
		}
		else
		{
			dialog = new Dialog(Vendedorventadirectaproducto.this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.diseniodialogo_vendedorcierredia);
			dialog.setTitle("NIT Invalido !");
			dialog.setCancelable(false);
			dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.colorBluePale));
			
			TextView tvMensaje = (TextView)dialog.findViewById(R.id.tvDialogoMensaje);
			tvMensaje.setText("Por el monto de la venta (" + parametroGeneral.get_montoNit() + ") Bs."
					+ " debe registrar un nombre de factura y nit valido. Por favor verifique y cambie los datos.");
			
			Button btnAceptar = (Button)dialog.findViewById(R.id.btnDialogoAceptar);
			btnAceptar.setOnClickListener(new OnClickListener() 
				{		
					@Override
					public void onClick(View v) 
					{
						switch(v.getId())
						{
							case R.id.btnDialogoAceptar:
							
								if(dialog.isShowing())
								{
									dialog.dismiss();
								}
							break;
						}	
					}
				});
			
			Button btnCancelar = (Button)dialog.findViewById(R.id.btnDialogoCancelar);
			btnCancelar.setOnClickListener(new OnClickListener() 
				{
					@Override
					public void onClick(View v) 
					{
						switch(v.getId())
						{
							case R.id.btnDialogoCancelar:
								if(dialog.isShowing())
								{
									dialog.dismiss();
								}
							}							
						}
				});
			
			dialog.show();
		}		
	}
	
	private boolean ValidarMontoNit()
	{
		boolean validado = false;
		if(parametroGeneral.get_montoNit() <= 0)
		{
			validado = true;
		}
		else
		{
			if(ObtenerMontoPreventaTemp() >= parametroGeneral.get_montoNit())
			{
				if(etNombreFactura.getText().toString().equals("Control Tributario") || etNombreFactura.getText().toString().isEmpty() 
						|| etNit.getText().toString().isEmpty() || etNit.getText().toString().equals("0"))
				{
					validado = false;
					
					ClientePreventa theCliente = null;
					try
					{
						theCliente = new ClientePreventaBLL().ObtenerClientePreventaPor(clienteId);
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
				    	{
				            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del cliente por clienteId: vacio");
				    	}
				    	else
				    	{
				    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del cliente por clienteId: " + localException.getMessage());
				    	} 
					}
					
					if(theCliente.get_paterno().equals("anyType{}") || theCliente.get_ci().equals("Sin Ci") || theCliente.get_paterno().isEmpty())
					{
						etNombreFactura.setText("");
					}
					else
					{
						etNombreFactura.setText(theCliente.get_paterno());
					}
					
					if(theCliente.get_ci().equals("anyType{}") || theCliente.get_paterno().isEmpty())
					{
						etNit.setText("");
					}
					else
					{
						etNit.setText(theCliente.get_ci());
					}
					
					btnConfirmarVentaDirecta.setVisibility(View.VISIBLE);
				}
				else
				{
					validado = true;
				}
			}
			else
			{
				validado = true;
			}
		}
		
		return validado;
	}
	
	private float ObtenerMontoPreventaTemp()
	{
		float montoFinal = 0;
		if(listadoVentaDirectaProductoTemp != null && listadoVentaDirectaProductoTemp.size()>0)
		{
			for(VentaDirectaProductoTemp item : listadoVentaDirectaProductoTemp)
	    	{
	    		montoFinal += item.get_montoFinal();
	    	}
		}
		return montoFinal;
	}
		
	private boolean VerificarVentasDirectasProductoTotalSincronizadas()
	{
		ArrayList<VentaDirectaProducto> listadoVentasDirectasProductoNoSync = null;
		try
		{
			listadoVentasDirectasProductoNoSync = new VentaDirectaProductoBLL().ObtenerVentasDirectasProductoNoSincronizadasPorNoVentaDirecta(noVentaDirecta);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasDirectasProducto no sincronizadas por noVentaDirecta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasDirectasProducto no sincronizadas por noVentaDirecta: " + localException.getMessage());
	    	}
		}
		
		if(listadoVentasDirectasProductoNoSync == null)
		{
			return false;
		}
		else
		{
			if(listadoVentasDirectasProductoNoSync.size() == listadoVentaDirectaProductoTemp.size())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	 
	private boolean PrepararVentaDirectaParaInsercion()
	{
		float monto=0;
    	float descuento=0;
	    float montoFinal=0;
	    Ubicacion localUbicacion=null;
	    double latitud=0;
	    double longitud=0;
	    float descuentoCanal = 0;
	    float descuentoAjuste = 0;
	    float descuentoProntoPagoUni = 0;
	    float descuentoObjetivo = 0;

	    distribuidorId = ((Distribuidor)spnDistribuidor.getSelectedItem()).get_distribuidorId();
	    
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
		   return false;
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
			   return false;
		   }
	   }
	   
	   if(parametroGeneral.is_activarGps() && latitud != 0 && longitud != 0 && parametroGeneral.get_distanciaCliente() > 0)
	   {
		   Location ubicacionActual = new Location("");
		   ubicacionActual.setLatitude(latitud);
		   ubicacionActual.setLongitude(longitud);
		   
		   Location ubicacionCliente = new Location("");
		   ubicacionCliente.setLatitude(clientePreventa.get_latitud());
		   ubicacionCliente.setLongitude(clientePreventa.get_longitud());
		   
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
				   return false;
			   }
			   else
			   {
				   if(localApkRutaCliente.is_bloquearPreventaDistancia())
				   {
					   return false;
				   }
			   }
		   }
	   }
	    
	   if(listadoVentaDirectaProductoTemp.size() > 0)
	   {
		   for(VentaDirectaProductoTemp item : listadoVentaDirectaProductoTemp)
		   {
			   descuento += item.get_descuento();
			   monto += item.get_monto();
			   montoFinal += item.get_montoFinal();
			   descuentoCanal += item.get_descuentoCanal();
			   descuentoAjuste += item.get_descuentoAjuste();
			   descuentoProntoPagoUni += item.get_descuentoProntoPago();
		   }
		   
		   	//Verificamos si tiene saldo del rebate dracaris
			DraRebateSaldo draRebateSaldo = VerificarClienteTieneSaldoRebate(); 
			if(draRebateSaldo != null)
			{
				if(draRebateSaldo.get_saldo() > 0)
				{
					float dsctoDracaris = montoFinal * draRebateSaldo.get_maxDescuento() / 100;
					
					if(dsctoDracaris <= draRebateSaldo.get_saldo())
					{
						descuentoObjetivo = dsctoDracaris;
						montoFinal = montoFinal - dsctoDracaris;
						ActualizarDraRebateSaldo(draRebateSaldo.get_saldo() - dsctoDracaris);
					}
					else
					{
						descuentoObjetivo = draRebateSaldo.get_saldo();
						montoFinal = montoFinal - draRebateSaldo.get_saldo();
						ActualizarDraRebateSaldo(0);
					}
				}
			}
	    	
		   Fecha fecha = theUtilidades.ObtenerFecha();
		   ventaDirecta = new VentaDirecta(0, 0, fecha.get_dia(),fecha.get_mes(),fecha.get_anio(),
				   	clienteId, monto,descuento,montoFinal, tipoPagoId, latitud, longitud, fecha.get_hora(),
					fecha.get_minuto(),etObservacion.getText().toString(),loginEmpleado.get_empleadoId(),
					factura,nit,nitNuevo,noVentaDirecta,false,aplicarBonificacion,tipoNit,clientePreventa.get_ruta(),
					clientePreventa.get_tipoVisita(), clientePreventa.get_zonaVentaId(), prontoPagoId,
					descuentoCanal,	descuentoAjuste, descuentoProntoPagoUni, descuentoObjetivo, 1, "", 0);
		   return true;
   	   }
	   else
	   {
		   theUtilidades.MostrarMensaje(getApplicationContext(), "No existe items en la venta directa.", 1);
		   return false;
	   }
	}

	private void InsertarVentaDirecta()
	{
		 pdInsertarVentaDirecta = new ProgressDialog(this);
		 pdInsertarVentaDirecta.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		 pdInsertarVentaDirecta.setMessage("Insertando venta directa ...");
		 pdInsertarVentaDirecta.setCancelable(false);
		 pdInsertarVentaDirecta.setCanceledOnTouchOutside(false);
		 	 
		 WSInsertarVentaDirecta localWSInsertarVentaDirecta = new WSInsertarVentaDirecta();
		 try
		 {
			 localWSInsertarVentaDirecta.execute();
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertVentaDirecta: vacio");
			 }
	 		 else
		 	 {
	 			 myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertVentaDirecta: " + localException.getMessage());
		 	 }
			 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice insertVentaDirecta.", 1);
		 }
	}
	 
	private class WSInsertarVentaDirecta extends AsyncTask<Void, Integer, Boolean>
	{
		String INSERTVENTADIRECTA_METHOD_NAME = "InsertVentaDirectaApk";
		String INSERTVENTADIRECTA_SOAP_ACTION = NAMESPACE + INSERTVENTADIRECTA_METHOD_NAME;
		 
		boolean WSInsertarVentaDirecta = false;
		int intResultado;
		SoapObject soapResultado;
		String stringResultado;
		 
		protected void onPreExecute()
		{
			pdInsertarVentaDirecta.show();
		}
	    	
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSInsertarVentaDirecta = false;
			 
			SoapObject localSoapObject = new SoapObject(NAMESPACE,INSERTVENTADIRECTA_METHOD_NAME);
			localSoapObject.addProperty("clienteId", ventaDirecta.get_clienteId());
			localSoapObject.addProperty("vendedorId", ventaDirecta.get_empleadoId());
			localSoapObject.addProperty("distribuidorId", distribuidorId);
			localSoapObject.addProperty("tipoPagoId", ventaDirecta.get_tipoPagoId());
			localSoapObject.addProperty("monto", String.valueOf(ventaDirecta.get_monto()));
			localSoapObject.addProperty("descuento", String.valueOf(ventaDirecta.get_descuento()));
			localSoapObject.addProperty("montoFinal", String.valueOf(ventaDirecta.get_montoFinal()));
			localSoapObject.addProperty("latitud", String.valueOf(ventaDirecta.get_latitud()));
			localSoapObject.addProperty("longitud", String.valueOf(ventaDirecta.get_longitud()));
			localSoapObject.addProperty("numeroItems", listadoVentaDirectaProductoTemp.size());
			localSoapObject.addProperty("anio", ventaDirecta.get_anio());
			localSoapObject.addProperty("mes", ventaDirecta.get_mes());
			localSoapObject.addProperty("dia", ventaDirecta.get_dia());
			localSoapObject.addProperty("hora", ventaDirecta.get_hora());
			localSoapObject.addProperty("minuto", ventaDirecta.get_minuto());
			localSoapObject.addProperty("nombreFactura", String.valueOf(ventaDirecta.get_factura()));
			localSoapObject.addProperty("nit", String.valueOf(ventaDirecta.get_nit()));
			localSoapObject.addProperty("nitNuevo",String.valueOf(ventaDirecta.is_nitNuevo()));
			localSoapObject.addProperty("almacenId",(loginEmpleado.get_almacenId()));
			localSoapObject.addProperty("rutaId",(clientePreventa.get_rutaId()));
			localSoapObject.addProperty("diaId",(clientePreventa.get_diaId()));
			localSoapObject.addProperty("observacion",(ventaDirecta.get_observacion()));
			localSoapObject.addProperty("nroVenta",(ventaDirecta.get_noVentaDirecta()));
			localSoapObject.addProperty("aplicaBonificacion",(ventaDirecta.is_aplicarBonificacion()));
			localSoapObject.addProperty("dosificacionId",dosificacionId);
			localSoapObject.addProperty("tipoNit",ventaDirecta.get_tipoNit());
			//localSoapObject.addProperty("ruta",ventaDirecta.get_ruta());
			//localSoapObject.addProperty("tipoVisita",ventaDirecta.get_tipoVisita());
			//localSoapObject.addProperty("zonaVentaId",ventaDirecta.get_zonaVentaId());
			localSoapObject.addProperty("prontoPagoId",ventaDirecta.get_prontoPagoId());
			localSoapObject.addProperty("descuentoCanal",String.valueOf(ventaDirecta.get_descuentoCanal()));
			localSoapObject.addProperty("descuentoAjuste",String.valueOf(ventaDirecta.get_descuentoAjuste()));
			localSoapObject.addProperty("descuentoProntoPago",String.valueOf(ventaDirecta.get_descuentoProntoPago()));
			localSoapObject.addProperty("descuentoObjetivo",String.valueOf(ventaDirecta.get_descuentoObjetivo()));
			localSoapObject.addProperty("formaPagoId", ventaDirecta.get_formaPagoId());
			localSoapObject.addProperty("codTransferencia", ventaDirecta.get_codTransferencia());
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(INSERTVENTADIRECTA_SOAP_ACTION, localSoapSerializationEnvelope);
				
				soapResultado = ((SoapObject)localSoapSerializationEnvelope.getResponse());
				if(soapResultado!=null)
				{
					intResultado = Integer.parseInt(soapResultado.getPropertyAsString("Id"));
			        stringResultado = soapResultado.getPropertyAsString("Resultado");
				}
				
				if(intResultado > 0)
				{
					WSInsertarVentaDirecta = true;
					ventaDirectaIdServer = intResultado;
				}
	            return true;
			}
			catch (Exception localException)
			{
				WSInsertarVentaDirecta = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertVentaDirecta: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertVentaDirecta: " + localException.getMessage());
				}
				return true;
			}
		 }
		 
		 protected void onPostExecute(Boolean ejecutado)
		 {
			 if(pdInsertarVentaDirecta.isShowing())
			 {
				 pdInsertarVentaDirecta.dismiss();
			 }
			 
			 if(ejecutado)
			 {
				 if(WSInsertarVentaDirecta) 
				 {
					 String[] datos = stringResultado.split("\\|");
					 float monto = Float.parseFloat(datos[0].toString().replace(",", ""));
					 float descuento = Float.parseFloat(datos[1].toString().replace(",", ""));
					 float montoFinal = Float.parseFloat(datos[2].toString().replace(",", ""));
					 ventaDirecta.set_monto(monto);
					 ventaDirecta.set_descuento(descuento);
					 ventaDirecta.set_montoFinal(montoFinal);

					 if(InsertarVentaDirectaDispositivo(ventaDirectaIdServer))
					 {
						 theUtilidades.MostrarMensaje(getApplicationContext(), "Venta directa insertada en el dispositivo.", 1);
						 ActualizarClientePreventaMontoCredito();
						 BorrarVentasDirectaProductoTempDispositivo();
						 ModificarVentaDirectaProductoIdServer(ventaDirectaIdServer);
						 ModificarEstadoAtencionClientePreventa();
						 //ObtenerAlmacenProducto();
						 GetIncentivosByCliente(intResultado, monto, descuento, montoFinal);
					 }
					 else
					 {
						 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la venta directa en el dispositivo.", 1);
					 }						 
				 }
				 else
				 {
			 		theUtilidades.MostrarMensaje(getApplicationContext(), stringResultado, 1);
			 		if((parametroGeneral.is_habilitarPop()) && ObtenerRolEmpleado() == 3)//Rol 3 = vendedor
    				{
    					MostrarPantallaMenuOpciones();;
    				}
    				else
    				{
    					MostrarPantallaMenuVendedor();
    				}
				 }
			 }
			 else
			 {
				 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertVentaDirecta.", 1);
			 }
		 }
	}
	 
	private boolean InsertarVentaDirectaDispositivo(int ventaDirectaIdServer)
	{
		Fecha fecha = theUtilidades.ObtenerFecha();
		try
		{
			ventaDirectaIdDispositivo = ((int) new VentaDirectaBLL().InsertarVentaDirecta(ventaDirectaIdServer,fecha.get_dia(),
					fecha.get_mes(),fecha.get_anio(),clienteId,ventaDirecta.get_monto(),ventaDirecta.get_descuento(),
					ventaDirecta.get_montoFinal(),ventaDirecta.get_tipoPagoId(),ventaDirecta.get_latitud(),
					ventaDirecta.get_longitud(),fecha.get_hora(),fecha.get_minuto(),ventaDirecta.get_observacion(),
					loginEmpleado.get_empleadoId(),ventaDirecta.get_factura(),ventaDirecta.get_nit(),ventaDirecta.is_nitNuevo(),
					ventaDirecta.get_noVentaDirecta(),true,ventaDirecta.is_aplicarBonificacion(),ventaDirecta.get_tipoNit(),
					ventaDirecta.get_ruta(),ventaDirecta.get_tipoVisita(),ventaDirecta.get_zonaVentaId(),ventaDirecta.get_prontoPagoId(),
					ventaDirecta.get_descuentoCanal(),ventaDirecta.get_descuentoAjuste(),ventaDirecta.get_descuentoProntoPago(),
					ventaDirecta.get_descuentoObjetivo(), 1, "", 0));
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App", toString(), 1, "Error al insertar la venta directa en el dispositivo: vacio");
			} 
			else 
			{
				myLog.InsertarLog("App", toString(), 1, "Error al insertar la venta directa en el dispositivo: " + localException.getMessage());
			}
		}
			 
		if(ventaDirectaIdDispositivo <= 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	 
	private boolean InsertarVentaDirectaProductoDispositivo(VentaDirectaProductoTemp ventaDirectaProductoTemp)
	{
		long l = 0;
		 
		try
		{
			l = new VentaDirectaProductoBLL().InsertarVentaDirectaProducto(0,ventaDirectaProductoTemp.get_productoId(),
									ventaDirectaProductoTemp.get_promocionId(),ventaDirectaProductoTemp.get_cantidad(),
									ventaDirectaProductoTemp.get_cantidadPaquete(),ventaDirectaProductoTemp.get_monto(),
									ventaDirectaProductoTemp.get_descuento(),ventaDirectaProductoTemp.get_montoFinal(),0,
									ventaDirectaProductoTemp.get_costoId(),ventaDirectaProductoTemp.get_precioId(),
									ventaDirectaProductoTemp.get_noVentaDirecta(),ventaDirectaProductoTemp.get_descuentoCanal(),
									ventaDirectaProductoTemp.get_descuentoAjuste(),ventaDirectaProductoTemp.get_canalPrecioRutaId());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ventaDirectaProducto en el dispositivo: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ventaDirectaProducto en el dispositivo: " + localException.getMessage());
			} 
		}
		 
		if(l <= 0)
		{
			return false; 
		}
	
		return true;
	}
	 
	boolean ActualizarClientePreventaMontoCredito()
	{
		if(venderCredito == true)
		{
			long a = 0;
			
			try
			{
				a = new ClientePreventaBLL().ModificarClientePreventaMontoCredito(clientePreventa.get_clienteId(), 
						clientePreventa.get_montoPendienteCredito() + ventaDirecta.get_montoFinal());
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty()) 
				{
					myLog.InsertarLog("App", toString(), 1, "Error al actualizar el monto del credito: vacio");
				} 
				else 
				{
					myLog.InsertarLog("App", toString(), 1, "Error al actualizar el monto del credito: " + localException.getMessage());
				}
			}
			
			if(a==0)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		else
		{
			return true;
		}
	}
	
	private void BorrarVentasDirectaProductoTempDispositivo()
	{
		boolean bool = false;
		try
		{
			bool = new VentaDirectaProductoTempBLL().BorrarVentaDirectaProductoTempPorClienteIdEmpleadoIdNoVentaDirecta(clienteId, loginEmpleado.get_empleadoId(), noVentaDirecta);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventasDirectasProductoTemp: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventaProductoTemp: " + localException.getMessage());
			}
		}
	    
		if(!bool)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar las preventas temporales del dispositivo.", 1);
			return;
		}
	}
	
	private void ModificarVentaDirectaProductoIdServer(int ventaDirectaIdServer)
	{
		long l = 0;
		try
		{
			l = new VentaDirectaProductoBLL().ModificarVentaDirectaProductoIdServerPor(noVentaDirecta, ventaDirectaIdServer);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el IdServer de la venta directa producto por noVentaDirecta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el IdServer de la venta directa producto por noVentaDirecta: " + localException.getMessage());
			}
		}
	    
		if(l==0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el idServer de la venta directa producto.", 1);
			return;
		}
	}
	
	private void ModificarEstadoAtencionClientePreventa()
	{
		long m = 0;
		try
		{
			m = new ClientePreventaBLL().ModificarClientePreventaEstadoAtencion(clienteId, true);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estadoAtencion del clientePreventa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estadoAtencion del clientePreventa " + localException.getMessage());
			} 
		}
		 
		if(m<=0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el estado de atencion del cliente.", 1);
			return;			 
		}
	}
	 
	private void ObtenerAlmacenProducto()
	{
		pdEsperaObtenerAlmacenProducto = new ProgressDialog(this);
		pdEsperaObtenerAlmacenProducto.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerAlmacenProducto.setMessage("Obteniendo productos del almacen ...");
		pdEsperaObtenerAlmacenProducto.setCancelable(false);
		pdEsperaObtenerAlmacenProducto.setCanceledOnTouchOutside(false);
	         
	    WSObtenerAlmacenProducto localWSObtenerAlmacenProducto = new WSObtenerAlmacenProducto();
	    try
	    {
	    	localWSObtenerAlmacenProducto.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetAlmacenTemp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetAlmacenTemp: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetAlmacenTemp.", 1);
	    }
	}
	 
	private class WSObtenerAlmacenProducto extends AsyncTask<Void, Integer, Boolean>
	{
		String ALMACENPRODUCTO_METHOD_NAME = "GetProductosByAlmacenTemp";
	    String ALMACENPRODUCTO_SOAP_ACTION = NAMESPACE + ALMACENPRODUCTO_METHOD_NAME;
	    
	    boolean WSObtenerAlmacenProducto = false;
		ArrayList<AlmacenProductoWSResult> almacenProductoWSResults;
		String error;
	    
	    protected void onPreExecute()
	    {
	    	pdEsperaObtenerAlmacenProducto.show();
	    }
	    
	    protected Boolean doInBackground(Void... paramVarArgs)
	    {
	    	WSObtenerAlmacenProducto = false;
	      
	    	SoapObject localSoapObject = new SoapObject(NAMESPACE,ALMACENPRODUCTO_METHOD_NAME);
			SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerProductos = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerProductos);

	    	SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
	    	localSoapSerializationEnvelope.dotNet = true;
	    	localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
	    	HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
	      
	    	try
	    	{
	    		localHttpTransportSE.call(ALMACENPRODUCTO_SOAP_ACTION,localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<AlmacenProductoWSResult>>(){ }.getType();
					almacenProductoWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerAlmacenProducto = (almacenProductoWSResults.size() > 0);
				}
	    		return true;
	    	}
	    	catch (Exception localException)
	    	{
	    	  	WSObtenerAlmacenProducto = false;
	    	  
	    	  	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	  	{
	    	  		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPermisosByEmpleado: vacio");
	    	  	}
	    	  	else
	    	  	{
	    	  		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPermisosByEmpleado: " + localException.getMessage());
	    	  	} 
	    	  
	    	  	return true;
	    	}
	    }
	    
	    protected void onPostExecute(Boolean ejecutado)
	    {
	    	if(pdEsperaObtenerAlmacenProducto.isShowing())
	    	{
	    		pdEsperaObtenerAlmacenProducto.dismiss();
	    	}
	    	
	    	if(!ejecutado)
	    	{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetProductosByAlmacenTemp no se ejecuto correctamente. ", 1);
				return;
			}

			if(!WSObtenerAlmacenProducto) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron productos del almacen que descargar. ", 1);
				if((parametroGeneral.is_habilitarPop()) && ObtenerRolEmpleado() == 3)//Rol 3 = vendedor
				{
					MostrarPantallaMenuOpciones();
				}
				else
				{
					MostrarPantallaMenuVendedor();
				}
				return;
			}

			if(!BorrarAlmacenesProducto()) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo eliminar los productos del almacen.", 1);
				return;
			}

			long l = 0;

			try
			{
				l = new AlmacenProductoBLL().InsertarAlmacenProducto( almacenProductoWSResults );
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar los datos de los productos del almacen: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar los datos de los productos del almacen: " + localException.getMessage());
				}
			}

			if(l==0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar los datos de los productos del almacen. ", 1);
				return;
			}

			theUtilidades.MostrarMensaje(getApplicationContext(), "Almacen dispositivo actualizado.", 1);

			if((parametroGeneral.is_habilitarPop()) && ObtenerRolEmpleado() == 3)//Rol 3 = vendedor
			{
				MostrarPantallaMenuOpciones();;
			}
			else
			{
				MostrarPantallaMenuVendedor();
			}
	    }
	}
	 
	private boolean BorrarAlmacenesProducto() 
	{
		try
		{
			return new AlmacenProductoBLL().BorrarAlmacenProductos();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los : vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los : " + localException.getMessage());
			} 
		}
		return false;
	}
		
	private boolean ObtenerVentasDirectasProductoTempNoSincronizadas()
	{
		listadoVentaDirectaProductoTempNoSincronizados = null;
	    try
	    {
	    	listadoVentaDirectaProductoTempNoSincronizados = new VentaDirectaProductoTempBLL().ObtenerVentasDirectasProductoTempNoSincronizadasPorClienteIdNoVentaDirecta(clienteId, noVentaDirecta);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta directa no sincronizada por preventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la preventa no sincronizada por preventaId: " + localException.getMessage());
	    	}
	    }
	    
	    if (listadoVentaDirectaProductoTempNoSincronizados == null) 
	    {
	        return false;
	    }  
	    else
	    {
	    	return true;
	    }
	}
	
	private void SincronizarVentaDirectaProducto() 
	{
		if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
		{
			if(listadoVentaDirectaProductoTempNoSincronizados != null && listadoVentaDirectaProductoTempNoSincronizados.size()>0)
			{
				InsertarVentaDirectaProductoTemp(listadoVentaDirectaProductoTempNoSincronizados.get(0));
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No existen productos de la venta directa que sincronizar.", 1);
				return;
			}	
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No esta conectado a internet, intentelo mas tarde.", 1);
			MostrarPantallaMenuVendedor();
		}
	}
	
	private int ObtenerRolEmpleado()
	{
		int rol = 0;
		ArrayList<Rol> listadoRol = null;
		
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
	    
	    if(listadoRol != null)
	    {
	    	for(Rol item :listadoRol)
	    	{
	    		if(item.get_rol().equals("Vendedor"))
	    		{
	    			rol = 3;
	    		}
	    	}
	    }
	    
	    return rol;
	}
	
	private boolean ValidarMontoCondicionTributariaCon(float montoIncremental)
	{
		boolean validado = false;
		
		if(tipoNit.equals("RG") || nit.equals("0"))
		{
			boolCondicionTributaria = true;
			validado = true;
		}
		else
		{
			if(parametroGeneral.is_bloquearCondicionTributaria() == false)
			{
				CondicionTributaria condicionTributaria = null;
				try
				{
					condicionTributaria = new CondicionTributariaBLL().ObtenerCondicionTributariaPor(nit);
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
			    	{
			            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la condicion tributaria por nit: vacio");
			    	}
			    	else
			    	{
			    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la condicion tributaria por nit: " + localException.getMessage());
			    	} 
				}
				
				if(condicionTributaria != null)
				{
					if(ObtenerMontoPreventaTemp() + condicionTributaria.get_montoAcumulado() + montoIncremental >= parametroGeneral.get_montoCondicionTributaria())
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "ATENCION!, El monto supera la Condicion Tributaria del NIT del cliente.", 2);
						validado = true;
					}
					else
					{
						validado = true;
					}
					
					boolCondicionTributaria = true;
				}
				else
				{
					boolCondicionTributaria = false;
					validado = true;
				}
			}
			else
			{
				CondicionTributaria condicionTributaria = null;
				try
				{
					condicionTributaria = new CondicionTributariaBLL().ObtenerCondicionTributariaPor(nit);
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
			    	{
			            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la condicion tributaria por nit: vacio");
			    	}
			    	else
			    	{
			    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la condicion tributaria por nit: " + localException.getMessage());
			    	} 
				}
				
				if(condicionTributaria != null)
				{
					if(ObtenerMontoPreventaTemp() + condicionTributaria.get_montoAcumulado() + montoIncremental >= parametroGeneral.get_montoCondicionTributaria())
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "El monto supera la Condicion Tributaria del NIT del cliente, no puede ingresar mas productos.", 2);
						validado = false;
					}
					else
					{
						validado = true;
					}
					
					boolCondicionTributaria = true;
				}
				else
				{
					boolCondicionTributaria = false;
					validado = true;
				}
			}
		}
	
		return validado;
	}
	
	private boolean ValidarMontoCondicionTributaria(float montoAcumulado)
	{
		boolean validado = false;
		if(boolCondicionTributaria && montoAcumulado <= 0)
		{
			validado = true;;
		}
		else if(boolCondicionTributaria == false && montoAcumulado <= 0)
		{
			VerificarMontoNitNoAdministrado();
		}
		else if(montoAcumulado > 0)
		{
			if(ObtenerMontoPreventaTemp() + montoAcumulado >= parametroGeneral.get_montoCondicionTributaria())
			{
				if(parametroGeneral.is_bloquearCondicionTributaria())
				{
					validado = false;
					theUtilidades.MostrarMensaje(getApplicationContext(), "El monto supera la Condicion Tributaria del NIT del cliente, no puede registrar la preventa.", 2);
				}
				else
				{
					validado = true;
					theUtilidades.MostrarMensaje(getApplicationContext(), "ATENCION!, El monto supera la Condicion Tributaria del NIT del cliente.", 2);
				}
			}
			else
			{
				validado = true;
			}
		}
		
		return validado;
	}
	
	private boolean ValidarCondicionCredito(float montoFinal)
	{
		if(clientePreventa.get_montoPendienteCredito() > 0 && parametroGeneral.is_creditoSobreCredito() == false)
  	  	{
			theUtilidades.MostrarMensaje(getApplicationContext(), "El cliente tiene un credito pendiente, no puede venderle otro.", 1);
			return false;
  	  	}
		
		if(clientePreventa.get_montoPendienteCredito() >= 0 && parametroGeneral.is_creditoSobreCredito() == true)
		{
			float acumulado = 0;
			
			if(listadoVentaDirectaProductoTemp !=null && listadoVentaDirectaProductoTemp.size() >0)
			{
				for(VentaDirectaProductoTemp item : listadoVentaDirectaProductoTemp)
				{
					acumulado += item.get_montoFinal();
				}
			}
			
			if(acumulado + montoFinal + clientePreventa.get_montoPendienteCredito() > clientePreventa.get_topeCredito())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No puede incluir el producto, por que sobrepasaria el monto de credito permitido.", 1);
				return false;
			}
		}
		
		if(clientePreventa.get_montoPendienteCredito() == 0 && !parametroGeneral.is_creditoSobreCredito())
		{
			float acumulado = 0;
			
			if(listadoVentaDirectaProductoTemp !=null && listadoVentaDirectaProductoTemp.size() >0)
			{
				for(VentaDirectaProductoTemp item : listadoVentaDirectaProductoTemp)
				{
					acumulado += item.get_montoFinal();
				}
			}
			
			if(acumulado + montoFinal + clientePreventa.get_montoPendienteCredito() > clientePreventa.get_topeCredito())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No puede incluir el producto, por que sobrepasaria el monto de credito permitido.", 1);
				return false;
			}
		}
		
		return true;
	}
	
	private void VerificarMontoNitNoAdministrado()
	{
		pdVerificarMontoNitCliente = new ProgressDialog(this);
		pdVerificarMontoNitCliente.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdVerificarMontoNitCliente.setMessage("Verificando acumulado NIT cliente ...");
		pdVerificarMontoNitCliente.setCancelable(false);
	         
	    WSVerificarNitCliente localWSVerificarNitCliente = new WSVerificarNitCliente();
	    try
	    {
	    	localWSVerificarNitCliente.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetNitMontoAcumulado: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetNitMontoAcumulado: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetNitMontoAcumulado.", 1);
	    	return;
	    }
	 }
	 
	private class WSVerificarNitCliente extends AsyncTask<Void, Integer, Boolean>
	{
		String NITCLIENTE_METHOD_NAME = "GetNitMontoAcumulado";
	    String NITCLIENTE_SOAP_ACTION = NAMESPACE + NITCLIENTE_METHOD_NAME;
	    
	    boolean WSNitCliente = false;
	    SoapPrimitive soapNitCliente;
	    
	    protected void onPreExecute()
	    {
	    	pdVerificarMontoNitCliente.show();
	    }
	    
	    protected Boolean doInBackground(Void... paramVarArgs)
	    {
	    	WSNitCliente = false;
	      
	    	Fecha fecha = theUtilidades.ObtenerFecha();
	    	SoapObject localSoapObject = new SoapObject(NAMESPACE,NITCLIENTE_METHOD_NAME);
	    	localSoapObject.addProperty("nit", String.valueOf(nit));
	    	localSoapObject.addProperty("anio", fecha.get_anio());
	    	
	    	SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
	    	localSoapSerializationEnvelope.dotNet = true;
	    	localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
	    	HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
	      
	    	try
	    	{
	    		localHttpTransportSE.call(NITCLIENTE_SOAP_ACTION,localSoapSerializationEnvelope);
	        
	    		soapNitCliente = (SoapPrimitive)localSoapSerializationEnvelope.getResponse();
	    		if(soapNitCliente != null)
	    		{
	    			WSNitCliente = true;
	    		}
	    		return true;
	      }
	      catch (Exception localException)
	      {
	    	  WSNitCliente = false;
	    	  
	    	  if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	  {
	    		  myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetNitAcumulado: vacio");
	    	  }
	    	  else
	    	  {
	    		  myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetNitAcumulado: " + localException.getMessage());
	    	  } 
	    	  
	    	  return true;
	      }
	    }
	    
	    protected void onPostExecute(Boolean ejecutado)
	    {
	    	if(pdVerificarMontoNitCliente.isShowing())
	    	{
	    		pdVerificarMontoNitCliente.dismiss();
	    	}
	    	
	    	if(ejecutado)
	    	{
	    		if(WSNitCliente)
	    		{					
	    			boolCondicionTributaria = true;
	    			montoTributarioServer = Float.parseFloat(soapNitCliente.toString());
    				ConfirmarVentaDirecta();
				}	
   				else
   				{
   					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el nit acumulado.", 1);
   			        boolCondicionTributaria = true;
   			        ConfirmarVentaDirecta();
   				}
    		}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetNitAcumulado no se ejecuto correctamente. ", 1);
	    		return;
    		}
	    }
	}

	private void GetIncentivosByCliente(int preventaIdServer, float monto, float descuento, float montofinal)
	{
		pdClienteIncentivo = new ProgressDialog(this);
		pdClienteIncentivo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdClienteIncentivo.setMessage("Verificando Incentivos ...");
		pdClienteIncentivo.setCancelable(false);
		pdClienteIncentivo.setCanceledOnTouchOutside(false);

		WSGetIncentivosByCliente localWSGetIncentivosByCliente = new WSGetIncentivosByCliente(preventaIdServer, monto, descuento, montofinal);
		try
		{
			localWSGetIncentivosByCliente.execute();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetIncentivosByCliente: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetIncentivosByCliente: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice GetIncentivosByCliente.", 1);
		}
	}

	private class WSGetIncentivosByCliente extends AsyncTask<Void, Integer, Boolean>
	{
		String INCENTIVOSCLIENTE_METHOD_NAME = "GetIncentivosByCliente";
		String INCENTIVOSCLIENTE_SOAP_ACTION = NAMESPACE + INCENTIVOSCLIENTE_METHOD_NAME;

		boolean WSGetIncentivosCliente = false;
		int wsPreventaIdServer;
		float wsMonto;
		float wsDescuento;
		float wsMontoFinal;
		String error;

		public WSGetIncentivosByCliente(int preventaIdServer, float monto, float desuento, float montoFinal)
		{
			wsPreventaIdServer = preventaIdServer;
			wsMonto = monto;
			wsDescuento = desuento;
			wsMontoFinal = montoFinal;
		}

		protected void onPreExecute()
		{
			pdClienteIncentivo.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSGetIncentivosCliente = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,INCENTIVOSCLIENTE_METHOD_NAME);
			VendedorDiaWS vendedorDiaWs = new VendedorDiaWS(clienteId, 0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String vendedorDiaWSJson = new JSonParser().GenerarVendedorDiaJson(vendedorDiaWs);
			String encriptedString = new AES().Encriptar(vendedorDiaWSJson ,theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedString);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URLJSON);
			try
			{
				localHttpTransportSE.call(INCENTIVOSCLIENTE_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<ClienteIncentivo>>(){ }.getType();
					clienteIncentivos = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSGetIncentivosCliente = (clienteIncentivos.size() > 0);
				}

				return true;

			}
			catch (Exception localException)
			{
				WSGetIncentivosCliente = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetIncentivosByCliente: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetIncentivosByCliente: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdClienteIncentivo.isShowing())
			{
				pdClienteIncentivo.dismiss();
			}

			if(ejecutado)
			{
				if(WSGetIncentivosCliente)
				{
					DesplegarClienteIncentivos(wsPreventaIdServer, wsMonto, wsDescuento, wsMontoFinal);
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron incentivos que descargar", 1);
					ObtenerAlmacenProducto();
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetIncentivosByCliente.", 1);
			}
		}
	}

	private void DesplegarClienteIncentivos(final int preventaIdServer, float monto, float descuento, float montoFinal)
	{
		dialogIncentivo = new Dialog(Vendedorventadirectaproducto.this);
		dialogIncentivo.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogIncentivo.setContentView(R.layout.diseniodialogo_clienteincentivo);
		dialogIncentivo.setCancelable(false);
		dialogIncentivo.setCanceledOnTouchOutside(false);
		dialogIncentivo.getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.colorBluePale));

		ListView lvIncentivos = (ListView) dialogIncentivo.findViewById(R.id.lvDisCliIncIncentivos);
		tvIncentivoMonto = (TextView) dialogIncentivo.findViewById(R.id.tvDisCliIncMonto);
		tvIncentivoDescuento = (TextView) dialogIncentivo.findViewById(R.id.tvDisCliIncDescuento);
		tvIncentivoMontoFinal = (TextView) dialogIncentivo.findViewById(R.id.tvDisCliIncMontofinal);
		Button btnCancelar = (Button) dialogIncentivo.findViewById(R.id.btnDisCliIncCancelar);
		Button btnAplicar = (Button) dialogIncentivo.findViewById(R.id.btnDisCliIncAplicar);

		_monto = monto;
		_descuento = descuento;
		_montoFinal = montoFinal;

		double mon = Math.round(_monto * d2) / d2;
		double des = Math.round(_descuento * d2) / d2;
		double monFin = Math.round(_montoFinal * d2) / d2;
		tvIncentivoMonto.setText(String.valueOf(mon));
		tvIncentivoDescuento.setText(String.valueOf(des));
		tvIncentivoMontoFinal.setText(String.valueOf(monFin));

		btnCancelar.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(dialogIncentivo.isShowing())
				{
					dialogIncentivo.dismiss();
				}
				ObtenerAlmacenProducto();
			}
		});

		btnAplicar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				String idsIncentivos = "";
				for(ClienteIncentivo item : clienteIncentivos)
				{
					if(item.isCheck())
					{
						idsIncentivos += String.valueOf(item.getIncentivoId()) + ";";
					}
				}
				if(idsIncentivos.equals(""))
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"Para aplicar un descuento debe seleccionar al menos uno.", 1);
				} else {
					if(dialogIncentivo.isShowing())
					{
						dialogIncentivo.dismiss();
					}
					InsertClienteIncentivo(preventaIdServer, _montoFinal, _descuentoIncentivo, idsIncentivos);
				}
			}
		});

		MiAdaptadorIncentivoLista localMiAdaptadorIncentivoLista = new MiAdaptadorIncentivoLista(getApplicationContext());

		if(clienteIncentivos == null)
		{
			lvIncentivos.setAdapter(null);
		}
		else
		{
			ViewGroup.LayoutParams localLayoutParams = lvIncentivos.getLayoutParams();
			localLayoutParams.height = ((int)(35*getApplicationContext().getResources().getDisplayMetrics().density) * clienteIncentivos.size());
			lvIncentivos.setLayoutParams(localLayoutParams);
			lvIncentivos.setAdapter(localMiAdaptadorIncentivoLista);
		}

		dialogIncentivo.show();
	}

	class MiAdaptadorIncentivoLista extends ArrayAdapter<ClienteIncentivo>
	{
		private Context _context;

		public MiAdaptadorIncentivoLista(Context context)
		{
			super(context, R.layout.disenio_clienteincentivo, clienteIncentivos);
			this._context = context;
		}

		public View getView(final int position, View convertView, ViewGroup parent)
		{
			View localView = convertView;

			if (localView == null)
			{
				LayoutInflater layoutInflater =	(LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				localView = layoutInflater.inflate(R.layout.disenio_clienteincentivo, parent, false);
			}

			final ClienteIncentivo clienteIncentivo = (ClienteIncentivo) clienteIncentivos.get(position);

			final CheckBox cbAplicar = (CheckBox) localView.findViewById(R.id.cbDisCliIncAplicar);
			TextView tvCodigo = (TextView) localView.findViewById(R.id.tvDisCliIncCodigo);
			TextView tvDescripcion = (TextView) localView.findViewById(R.id.tvDisCliIncDescripcion);
			TextView tvMonto = (TextView) localView.findViewById(R.id.tvDisCliIncMonto);

			cbAplicar.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					if(cbAplicar.isChecked()){
						if((_monto - (_descuento + clienteIncentivo.getMonto())) < 0){
							theUtilidades.MostrarMensaje(getApplicationContext(),"El descuento es mayor al monto de la preventa.",1);
							cbAplicar.setChecked(false);
						} else {
							_descuento = _descuento + clienteIncentivo.getMonto();
							_montoFinal = _monto - _descuento;
							_descuentoIncentivo = _descuentoIncentivo + clienteIncentivo.getMonto();
							clienteIncentivos.get(position).setCheck(true);
						}
					} else {
						_descuento = _descuento - clienteIncentivo.getMonto();
						_montoFinal = _montoFinal + clienteIncentivo.getMonto();
						_descuentoIncentivo = _descuentoIncentivo - clienteIncentivo.getMonto();
						clienteIncentivos.get(position).setCheck(false);
					}
					double incDes = Math.round(_descuento * d2) / d2;
					double incMonFin = Math.round(_montoFinal * d2) / d2;
					tvIncentivoDescuento.setText(String.valueOf(incDes));
					tvIncentivoMontoFinal.setText(String.valueOf(incMonFin));
				}
			});

			tvCodigo.setText(clienteIncentivo.getCodigoIncentivo());
			tvDescripcion.setText(clienteIncentivo.getDescripcionIncentivo());
			tvMonto.setText(String.valueOf(clienteIncentivo.getMonto()));

			return localView;
		}
	}

	private void InsertClienteIncentivo(int preventaId, float montoFinal, float incentivo, String incentivosId)
	{
		pdInsertClienteIncentivo = new ProgressDialog(this);
		pdInsertClienteIncentivo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdInsertClienteIncentivo.setMessage("Registrando Incentivos ...");
		pdInsertClienteIncentivo.setCancelable(false);
		pdInsertClienteIncentivo.setCanceledOnTouchOutside(false);

		WSInsertClienteIncentivo localWSInsertClienteIncentivo = new WSInsertClienteIncentivo(preventaId, montoFinal, incentivo, incentivosId);
		try
		{
			localWSInsertClienteIncentivo.execute();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertClienteIncentivo: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertClienteIncentivo: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice InsertClienteIncentivo.", 1);
		}
	}

	private class WSInsertClienteIncentivo extends AsyncTask<Void, Integer, Boolean>
	{
		String INSERTARINCENTIVOSCLIENTE_METHOD_NAME = "AplicarDescuentoIncentivoVenta";
		String INSERTARINCENTIVOSCLIENTE_SOAP_ACTION = NAMESPACE + INSERTARINCENTIVOSCLIENTE_METHOD_NAME;

		WSResultado wsResulatdo;
		boolean WSInsertIncentivosCliente = false;
		int _ventaDirectaIdServer;
		float _montoFinal;
		float _incentivo;
		String _incentivosId;
		String error;

		public WSInsertClienteIncentivo(int ventaDirectaIdServer, float montoFinal, float incentivo, String incentivosId)
		{
			_ventaDirectaIdServer = ventaDirectaIdServer;
			_montoFinal = montoFinal;
			_incentivo = incentivo;
			_incentivosId = incentivosId;
		}

		protected void onPreExecute()
		{
			pdInsertClienteIncentivo.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSInsertIncentivosCliente = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,INSERTARINCENTIVOSCLIENTE_METHOD_NAME);
			String json = new JSonParser().GenerarAplicacionIncentivo(_ventaDirectaIdServer, _incentivo, _incentivosId);
			String encriptedString = new AES().Encriptar(json, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedString);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URLJSON);
			try
			{
				localHttpTransportSE.call(INSERTARINCENTIVOSCLIENTE_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadena;
				}
				else
				{
					Type objectType = new TypeToken<WSResultado>(){ }.getType();
					wsResulatdo = new Gson().fromJson(cadenaDesencriptada, objectType);
					if(wsResulatdo != null && wsResulatdo.getResultado().equals("OK"))
					{
						WSInsertIncentivosCliente = true;
					}

				}
				return true;

			}
			catch (Exception localException)
			{
				WSInsertIncentivosCliente = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertClienteIncentivo: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertClienteIncentivo: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdInsertClienteIncentivo.isShowing())
			{
				pdInsertClienteIncentivo.dismiss();
			}

			if(ejecutado)
			{
				if(WSInsertIncentivosCliente)
				{
					long l = 0;
					try
					{
						l = new VentaDirectaBLL().ModificarVentaDirectaDescuentoIncentivo(_ventaDirectaIdServer, _montoFinal, _incentivo);
					}
					catch (Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar el monto final y descuento incentivo: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar el monto final y descuento incentivo: " + localException.getMessage());
						}
					}

					if(l==0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el descuento incentivo del cliente.", 1);
					} else {
						ObtenerAlmacenProducto();
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron incentivos que descargar", 1);
					ObtenerAlmacenProducto();
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetIncentivosByCliente.", 1);
			}
		}
	}
	
	public void MostrarPantallaMenuVendedor()
	{
		startActivity(new Intent(this, Menuvendedor.class));
	}

	public void MostrarPantallaPreventaFactura()
	{
		Intent localIntent = new Intent(this, Vendedorpreventanits.class);
		localIntent.putExtra("clienteId", clienteId);
		localIntent.putExtra("tipoPagoId", tipoPagoId);
		localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(localIntent);
	}
	
	public void MostrarPantallaVentaDirectaPromocion()	{
		Intent localIntent = new Intent(this, Vendedorventadirectapromocion.class);
		localIntent.putExtra("clienteId", clienteId);
		localIntent.putExtra("tipoPagoId", tipoPagoId);
		localIntent.putExtra("distribuidorId", distribuidorId);
		localIntent.putExtra("tipoNit", tipoNit);
		localIntent.putExtra("factura", factura);
		localIntent.putExtra("nit", nit);
		localIntent.putExtra("nitNuevo", nitNuevo);
		localIntent.putExtra("observacion",etObservacion.getText().toString());
		localIntent.putExtra("aplicarBonificacion", aplicarBonificacion);
		localIntent.putExtra("dosificacionId",dosificacionId);
		localIntent.putExtra("venderCredito",venderCredito);
		localIntent.putExtra("ventaDirectaIdServer", ventaDirectaIdServer);
		localIntent.putExtra("aplicarProntoPago", aplicarProntoPago);
		startActivity(localIntent);	 
	}
	 
	public void MostrarPantallaMenuOpciones()
	{
		Intent localIntent = new Intent(this, Vendedorpreventamenuopciones.class);
		localIntent.putExtra("ventaDirectaIdDispositivo", ventaDirectaIdDispositivo);
		localIntent.putExtra("ventaDirectaIdServer", ventaDirectaIdServer);
		localIntent.putExtra("clienteId", clienteId);
		localIntent.putExtra("ventaDirecta", true);
		startActivity(localIntent);
	}
	
	@Override
	public void onBackPressed() 
	{
		super.onBackPressed();
		MostrarPantallaMenuVendedor();
	 }
}
