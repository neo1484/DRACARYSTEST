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
import BLL.CanalRutaPrecioBLL;
import BLL.CategoriaBLL;
import BLL.ClientePreventaBLL;
import BLL.CondicionTributariaBLL;
import BLL.DosificacionProveedorBLL;
import BLL.DraRebateSaldoBLL;
import BLL.MyLogBLL;
import BLL.ParametroGeneralBLL;
import BLL.PrecioListaBLL;
import BLL.PreventaBLL;
import BLL.PreventaProductoBLL;
import BLL.ProductoBLL;
import BLL.ProductoCostoBLL;
import BLL.PromocionBLL;
import BLL.ProntoPagoCanalRutaBLL;
import BLL.ProntoPagoClienteBLL;
import BLL.ProntoPagoJerarquiaBLL;
import BLL.ProveedorBLL;
import BLL.ProveedorPrecioListaMargenBLL;
import BLL.RolBLL;
import Clases.AES;
import Clases.AlmacenProducto;
import Clases.AlmacenProductoWSResult;
import Clases.CanalRutaPrecio;
import Clases.Categoria;
import Clases.ClientePreventa;
import Clases.CondicionTributaria;
import Clases.DosificacionProveedor;
import Clases.DraRebateSaldo;
import Clases.JSonParser;
import Clases.LoginEmpleado;
import Clases.ParametroGeneral;
import Clases.PrecioLista;
import Clases.Preventa;
import Clases.PreventaProducto;
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
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

public class Vendedoredicionpreventaproducto extends Activity implements OnClickListener 
{
	LinearLayout llVendedorEdicionPreventaProducto;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	LoginEmpleado loginEmpleado;
	MyLogBLL myLog = new MyLogBLL();
	
	ArrayList<PreventaProducto> listadoPreventaProducto;
	ArrayList<DosificacionProveedor> listadoDosificacionProvedor;
	Producto producto;
	ClientePreventa clientePreventa;
	int clienteId;
	String factura;
	String nit;
	String tipoNit;
	ParametroGeneral parametroGeneral;
	int noPreventa;
	Preventa preventa;
	int preventaId;
	int preventaIdServer;
	int preventaIdDispositivo;
	boolean unidades;
	long preventaProductoId;
	int dosificacionId;
	boolean boolCondicionTributaria=false;
	CanalRutaPrecio canalRutaPrecio;
	boolean aplicarProntoPago;
	float descuentoProntoPagoUni;
	int prontoPagoId;
	DraRebateSaldo draRebateSaldo;
	
	TextView tvDosificaciones;
	Spinner spnDosificaciones;
	CheckBox cbAplicarProntoPago;
	TextView tvProveedores;
	Spinner spnProveedores;
	TextView tvCategorias;
	Spinner spnCategorias;
	TextView tvProductos;
	Spinner spnProductos;
	
	ImageView ivPromociones;
	Button btnMostrarProducto;
	Button btnAdicionarPreventa;
	
	TextView tvNombre;
	TextView tvFactura;
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
	
	EditText etvCantidad;
	RadioButton rbtMayor;
	RadioButton rbtUnidades;
	ListView lvPreventaProducto;
	Dialog dialogItemPreventa;
	TextView tvNombreFactura;
	TextView tvNit;
	Dialog dialog;

	ProgressDialog pdEsperaObtenerAlmacenProducto;
	ProgressDialog pdInsertarPreventaProducto; 
	ProgressDialog pdDeletePreventaProducto;
	ProgressDialog pdInsertarPreventa;
	ProgressDialog pdVerificarMontoNitCliente;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedoredicionpreventaproducto);
		
		llVendedorEdicionPreventaProducto = (LinearLayout)findViewById(R.id.llVendedorEdicionPreventaProducto);
		tvDosificaciones = (TextView)findViewById(R.id.tvVendedorPreventaEdicionDosificacion);
		spnDosificaciones = (Spinner)findViewById(R.id.spnVendedorPreventaEdicionDosificacion);
		cbAplicarProntoPago = ((CheckBox)findViewById(R.id.cbVendedorEdicionPreventaProductoAplicarProntoPago));
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
	    btnAdicionarPreventa = ((Button)findViewById(R.id.btnVendedorVentaDirectaProductoAdicionarPreventa));
	    btnAdicionarPreventa.setOnClickListener(this);
		
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
	    tvDsctoCanalTxt = (TextView)findViewById(R.id.tvVenEdiPreProDescuentoCanalTxt);
	    tvDsctoCanal = (TextView)findViewById(R.id.tvVenEdiPreProDescuentoCanal);
	    tvDsctoAjusteTxt = (TextView)findViewById(R.id.tvVenEdiPreProDescuentoAjusteTxt);
	    tvDsctoAjuste = (TextView)findViewById(R.id.tvVenEdiPreProDescuentoAjuste);
	    tvDsctoProntoPagoTxt = (TextView)findViewById(R.id.tvVenEdiPreProDescuentoProntoPagoTxt);
	    tvDsctoProntoPago = (TextView)findViewById(R.id.tvVenEdiPreProDescuentoProntoPago);
	    tvPrecioUnitarioFinalTxt = (TextView)findViewById(R.id.tvVenEdiPreProPrecioUnitarioFinalTxt);
	    tvPrecioUnitarioFinal = (TextView)findViewById(R.id.tvVenEdiPreProPrecioUnitarioFinal);
	    tvPrecioMayorFinalTxt = (TextView)findViewById(R.id.tvVenEdiPreProPrecioMayorFinalTxt);
	    tvPrecioMayorFinal = (TextView)findViewById(R.id.tvVenEdiPreProPrecioMayorFinal);
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
	    tvDsctoObjetivoTxt = (TextView)findViewById(R.id.tvVenEdiPreProDescuentoObjetivoTxt);
	    tvDsctoObjetivo = (TextView)findViewById(R.id.tvVenEdiPreProDescuentoObjetivo);
	    tvDsctoObjetivoTotalTxt = (TextView)findViewById(R.id.tvVenEdiPreProDescuentoObjetivoTotalTxt);
	    tvDsctoObjetivoTotal = (TextView)findViewById(R.id.tvVenEdiPreProDescuentoObjetivoTotal);
	    tvMontoFinalTxt = (TextView)findViewById(R.id.tvVenEdiPreProMontoFinalTxt);
	    tvMontoFinal = (TextView)findViewById(R.id.tvVenEdiPreProMontoFinal);
	    
	    etvCantidad = ((EditText)findViewById(R.id.etVendedorVentaDirectaProductoCantidad));
	    rbtUnidades = ((RadioButton)findViewById(R.id.rbtVendedorVentaDirectaProductoUnidades));
	    rbtMayor = ((RadioButton)findViewById(R.id.rbtVendedorVentaDirectaProductoMayores));
	    lvPreventaProducto = ((ListView)findViewById(R.id.lvVendedorVentaDirectaProductoPreventas));
	    
	    tvNombreFactura = (TextView)findViewById(R.id.tvVendedorVentaDirectaProductoFacturaContenido);
	    tvNit = (TextView)findViewById(R.id.tvVendedorVentaDirectaProductoNitContenido);
	    
	    llVendedorEdicionPreventaProducto.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
	    
	    preventaId = 0;
	    preventaId = getIntent().getExtras().getInt("preventaId");
	    if(preventaId == 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la preventaId.", 1);
	    	return;
	    }
	    
	    preventaIdServer = 0;
	    preventaIdServer = getIntent().getExtras().getInt("preventaIdServer");
	    
	    clienteId = 0;
	    clienteId = getIntent().getExtras().getInt("clienteId");
	    if(clienteId == 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el clienteId.", 1);
	    	return;
	    }
	    
	    factura = "";
	    factura = getIntent().getExtras().getString("factura");
	    if(factura == "")
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la factura.", 1);
	    	return;
	    }
	    
	    nit = "";
	    nit = getIntent().getExtras().getString("nit");
	    if(nit == "")
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el nit.", 1);
	    	return;
	    }
	    
	    tipoNit = "";
	    tipoNit = getIntent().getExtras().getString("tipoNit");
	    if(tipoNit == "")
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el tipo de nit.", 1);
	    	return;
	    }
	    
	    aplicarProntoPago = false;
	    aplicarProntoPago = getIntent().getExtras().getBoolean("aplicarProntoPago");
	    cbAplicarProntoPago.setChecked(aplicarProntoPago);
	    
	    ObtenerPreventa();
	    
	    dosificacionId = 0;
	    dosificacionId = getIntent().getExtras().getInt("dosificacionId");
	    if(dosificacionId == 0)
	    {
	    	if(preventa != null)
	    	{
	    		dosificacionId = preventa.get_dosificacionId();
	    	}
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
	    	tvNombreFactura.setText(factura);
	    	tvNit.setText(nit);
	    	DespliegueControlesAdicionarPreventa(false);
	        DespliegueControlesConfirmarPreventa(false);
	        CargarInformacion();
	    }
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.imgbtnVendedorVentaDirectaProducto:
			MostrarPantallaEdicionPreventaPromocion();
			break;
		case R.id.btnVendedorVentaDirectaProductoDatosSeleccion:
			ObtenerDatosSeleccion();
			break;		
		case R.id.btnVendedorVentaDirectaProductoAdicionarPreventa:
			AdicionarPreventa();
			break;
		case R.id.cbVendedorEdicionPreventaProductoAplicarProntoPago:
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
	    btnAdicionarPreventa.setVisibility(visibilidad); 
	    etvCantidad.setText("");
	}
	
	private void DespliegueControlesConfirmarPreventa(boolean estado)
	{
		int visibilidad = View.VISIBLE;
	    if(!estado)
	    {
			visibilidad = View.INVISIBLE;
	    }
	    
	    tvProductolbl.setVisibility(visibilidad);
	    tvCantidadlbl.setVisibility(visibilidad);
	    tvPrecioUnitariolbl.setVisibility(visibilidad);
	    tvPrecioMayorlbl.setVisibility(visibilidad);
	    tvSubTotallbl.setVisibility(visibilidad);
	    lvPreventaProducto.setVisibility(visibilidad);
	    tvTotaltxt.setVisibility(visibilidad);
	    tvTotal.setVisibility(visibilidad);
	    tvDsctoObjetivoTxt.setVisibility(visibilidad);
	    tvDsctoObjetivo.setVisibility(visibilidad);
	    tvDsctoObjetivoTotalTxt.setVisibility(visibilidad);
	    tvDsctoObjetivoTotal.setVisibility(visibilidad);
	    tvMontoFinalTxt.setVisibility(visibilidad);
	    tvMontoFinal.setVisibility(visibilidad);
    }

	private void ObtenerPreventa()
	{
		preventa = null;
    	try
    	{
    		preventa = new PreventaBLL().ObtenerPreventaPorId(preventaId);
    	}
    	catch(Exception localException)
    	{
    		if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por preventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener al preventa por preventaId: " + localException.getMessage());
	    	} 
    	}
    	
    	if(preventa.get_tipoPagoId() == 2)
    	{
    		cbAplicarProntoPago.setVisibility(View.INVISIBLE);
    	}
	}
	
	public void CargarInformacion()
	{
		CargarParametroGeneral();
	    CargarCliente();
	    CargarDosificacionesProveedorVendedor();
	    CargarProveedores();
	    CargarCategorias();
	    CargarProductos();
	    ActualizarListView();
	    ObtenerNoPreventa();
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
	    }
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
	    }
	    else
	    {
	    	tvNombre.setText(clientePreventa.get_nombreCompleto().toString());
	    	tvNombreFactura.setText(factura);
	    	tvNit.setText(nit);
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
        }
	    else
	    {	    
	    	MostrarControlesSeleccion(true);
	    	
	    	if(listadoDosificacionProvedor.size() == 1)
	    	{
	    		tvDosificaciones.setVisibility(View.INVISIBLE);
				spnDosificaciones.setVisibility(View.INVISIBLE);
	    	}
	    	
	    	ArrayAdapter<DosificacionProveedor> localArrayAdapter = new ArrayAdapter<DosificacionProveedor>(this,R.layout.disenio_spinner,listadoDosificacionProvedor);
		    spnDosificaciones.setAdapter(localArrayAdapter);
		    
		    if(dosificacionId != 0)
		    {
		    	int i=0;
		    	for(DosificacionProveedor item : listadoDosificacionProvedor)
		    	{
		    		if(item.get_dosificacionId() == dosificacionId)
		    		{
		    			spnDosificaciones.setSelection(i);
		    		}
		    		i++;
		    	}
		    }

		    spnDosificaciones.setOnItemSelectedListener(new OnItemSelectedListener() 
		    {	    	
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
				{					
					DosificacionProveedor theDosificacionProveedor = (DosificacionProveedor)spnDosificaciones.getSelectedItem();
					dosificacionId = theDosificacionProveedor.get_dosificacionId();
					
					ArrayList<DosificacionProveedor> dosificaciones = new ArrayList<DosificacionProveedor>();
					try
					{
						dosificaciones = new DosificacionProveedorBLL().ObtenerDosificacionesProveedorPor(theDosificacionProveedor.get_dosificacionId());
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
		
		tvDosificaciones.setVisibility(visibility);
		spnDosificaciones.setVisibility(visibility);
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
					listadoProducto = new ProductoBLL().ObtenerProductoPorProveedorNoEnPreventaProducto(
													proveedor.get_proveedorId(),categoria.get_categoriaId(),
													preventaId);
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
	    	listadoProducto = new ProductoBLL().ObtenerProductoPorProveedorNoEnPreventaProducto(proveedorId,
	    													categoriaId,preventaId);
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
		listadoPreventaProducto = null;
		try
		{
			listadoPreventaProducto = new PreventaProductoBLL().ObtenerPreventasProductoPor(preventaId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProducto por preventaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProducto por preventaId: " + localException.getMessage());
			}
		}
		      
		if(listadoPreventaProducto == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No existen productos para la preventa.", 1);
			spnDosificaciones.setEnabled(true);
			DespliegueControlesConfirmarPreventa(false);
	    	lvPreventaProducto.setAdapter(null);
		}
		else
		{
			spnDosificaciones.setEnabled(false);
			DespliegueControlesConfirmarPreventa(true);
			CalcularTotalPreventa();
			lvPreventaProducto.setVisibility(View.VISIBLE);
		    LlenarPreventaListView();
		    EliminarItemListView();	
		}    
	}
	
	private void ObtenerNoPreventa()
	{
		noPreventa = -1;
	    try
	    {
	    	noPreventa = new PreventaBLL().ObtenerPreventaPorId(preventaId).get_noPreventa();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el nro de la preventa: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el nro de la preventa: " + localException.getMessage());
	    	}
	    	noPreventa = 0;
	    }
	    
	    if (noPreventa == -1)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el numero de preventa.", 1);
	    }
	}
	
	private void VerificarExistenciaProntoPago()
	{
		if(listadoPreventaProducto != null && listadoPreventaProducto.size() > 0)
		{
			float dsctoPP = 0;
			for(PreventaProducto item : listadoPreventaProducto)
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
	
	private void CalcularTotalPreventa()
	{
		float total = 0;
		for(PreventaProducto item : listadoPreventaProducto)
		{
			total += Float.parseFloat(BigDecimal.valueOf(item.get_montoFinal()).setScale(5,RoundingMode.HALF_UP).toString());
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
				tvDsctoObjetivo.setText(String.valueOf(BigDecimal.valueOf(draRebateSaldo.get_saldo()).setScale(2,RoundingMode.HALF_UP)));
				tvDsctoObjetivoTotal.setText(String.valueOf(BigDecimal.valueOf(draRebateSaldo.get_saldo()).setScale(2,RoundingMode.HALF_UP)));
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

	private void LlenarPreventaListView()
	{
	    MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    	    
	    if(listadoPreventaProducto == null)
	    {
	    	lvPreventaProducto.setAdapter(null);
	    }
	    else
	    {
		    ViewGroup.LayoutParams localLayoutParams = lvPreventaProducto.getLayoutParams();
		    localLayoutParams.height = ((int)(50*getApplicationContext().getResources().getDisplayMetrics().density) * listadoPreventaProducto.size());
		    lvPreventaProducto.setLayoutParams(localLayoutParams);
		    lvPreventaProducto.setAdapter(localMiAdaptadorLista);
	    }
	}

	class MiAdaptadorLista extends ArrayAdapter<PreventaProducto>
	{
		private Context _context;
		
		public MiAdaptadorLista(Context context)
		{
			super(context,R.layout.disenio_vendedorpreventaproducto,listadoPreventaProducto);
			this._context = context;
		}
    
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View localView = convertView;
			
			if (localView == null) 
			{				
				LayoutInflater layoutInflater =	(LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				assert layoutInflater != null;
				localView = layoutInflater.inflate(R.layout.disenio_vendedorpreventaproducto, parent, false);
			}
			
			PreventaProducto localPreventaProducto = (PreventaProducto)listadoPreventaProducto.get(position);
			
			TextView descripcion25 = (TextView)localView.findViewById(R.id.tvVendedorVentaDirectaProductoCliente);
			TextView cantidad = (TextView)localView.findViewById(R.id.tvPreventaDisenioCantidad);
			TextView precio = (TextView)localView.findViewById(R.id.tvPreventaDisenioPrecioUnitario);
			TextView precioPaquete = (TextView)localView.findViewById(R.id.tvVendedorPreventaProductoProveedor);
			TextView montoFinal = (TextView)localView.findViewById(R.id.tvVendedorPreventaProductoProducto);
			ImageView bullet = (ImageView)localView.findViewById(R.id.imgvLoginLogotipo);
      
			if(localPreventaProducto.get_productoId() != 0)
			{
				Producto localProducto = null;
				try
				{
					localProducto = new ProductoBLL().ObtenerProductoPor(localPreventaProducto.get_productoId());
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
				cantidad.setText(String.valueOf(localPreventaProducto.get_cantidad()==0?localPreventaProducto.get_cantidadPaquete():localPreventaProducto.get_cantidad()));
				
				if(localPreventaProducto.get_cantidad()==0)
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
				
				if(localPreventaProducto.get_cantidadPaquete()==0)
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
				
				montoFinal.setText(String.valueOf(BigDecimal.valueOf(localPreventaProducto.get_montoFinal()).setScale(2,RoundingMode.HALF_UP)));
			}
			
			if(localPreventaProducto.get_promocionId() != 0) 
			{
				Promocion localPromocion = null;
				try
				{
					localPromocion = new PromocionBLL().ObtenerPromocionPor(localPreventaProducto.get_promocionId());
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
				cantidad.setText(String.valueOf(localPreventaProducto.get_cantidad()));
				precio.setText(String.valueOf(new BigDecimal(localPreventaProducto.get_monto()/localPreventaProducto.get_cantidad()).setScale(2, RoundingMode.HALF_UP)));
				precioPaquete.setText(" ");
				montoFinal.setText(String.valueOf(new BigDecimal(localPreventaProducto.get_montoFinal()).setScale(2, RoundingMode.HALF_UP)));
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
				PreventaProducto localPreventaProducto = listadoPreventaProducto.get(position);
				preventaProductoId = localPreventaProducto.get_id();
				
				if(preventaIdServer > 0)
				{
					BorrarItemPreventa(true,preventaIdServer,localPreventaProducto.get_productoId(),localPreventaProducto.get_promocionId());
				}
				else
				{
					BorrarItemPreventa(false,(int)preventaProductoId,localPreventaProducto.get_productoId(),localPreventaProducto.get_promocionId());	
				}
			}
	    });
	}

	public void BorrarItemPreventa(final boolean server,final int preventaId,final int productoId, final int promocionId)
	{
		dialogItemPreventa = new Dialog(Vendedoredicionpreventaproducto.this);
		dialogItemPreventa.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogItemPreventa.setContentView(R.layout.diseniodialogo_vendedorcierredia);
		dialogItemPreventa.setTitle("Borrar Item Preventa");
		dialogItemPreventa.setCancelable(false);
		dialogItemPreventa.getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.colorBluePale));
		
		TextView tvMensaje = (TextView)dialogItemPreventa.findViewById(R.id.tvDialogoMensaje);
		tvMensaje.setText("Esta seguro que desea eliminar definitivamente el item de la preventa?");
		
		Button btnAceptar = (Button)dialogItemPreventa.findViewById(R.id.btnDialogoAceptar);
		btnAceptar.setOnClickListener(new OnClickListener() 
			{		
				@Override
				public void onClick(View v) 
				{
					switch(v.getId())
					{
					case R.id.btnDialogoAceptar:
						
						if(dialogItemPreventa.isShowing())
						{
							dialogItemPreventa.dismiss();
						}
						if(server)
						{
							if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
							{
								BorrarPreVentaProducto(preventaId, productoId, promocionId);
							}
							else
							{
								theUtilidades.MostrarMensaje(getApplicationContext(), "No se encuentra conectado a internet, intentelo mas tarde.", 1);
								return;
							}							
						}
						else
						{
							BorrarPreventaProductoDispositivo(preventaId);
							ModificarNuevosMontosPreventa();
							theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la preventa eliminado.", 1);
							CargarProductos();
			    			ActualizarListView();
						}
						break;
					}	
				}
			});
		
		Button btnCancelar = (Button)dialogItemPreventa.findViewById(R.id.btnDialogoCancelar);
		btnCancelar.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					switch(v.getId())
					{
					case R.id.btnDialogoCancelar:
						if(dialogItemPreventa.isShowing())
						{
							dialogItemPreventa.dismiss();
						}
					}							
				}
			});
		
		dialogItemPreventa.show();
	}
	
	private void BorrarPreVentaProducto(int preventaId,int productoId,int promocionId)
	{
		pdDeletePreventaProducto = new ProgressDialog(this);
		pdDeletePreventaProducto.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdDeletePreventaProducto.setMessage("Borrando item preventa ...");
		pdDeletePreventaProducto.setCancelable(false);
		pdDeletePreventaProducto.setCanceledOnTouchOutside(false);
		 
		WSDeletePreventaProducto localWSDeletePreventaProducto = new WSDeletePreventaProducto(preventaId,productoId,promocionId);
		try
		{
			localWSDeletePreventaProducto.execute();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSDeletePreventaProducto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSDeletePreventaProducto: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSDeletePreventaProducto.", 1);
	 	}
	}
	 
	private class WSDeletePreventaProducto extends AsyncTask<Void, Integer, Boolean>
	{
	    String BORRARPREVENTAPRODUCTO_METHOD_NAME = "DeletePreVentaProducto";
	    String BORRARPREVENTAPRODUCTO_SOAP_ACTION = NAMESPACE + BORRARPREVENTAPRODUCTO_METHOD_NAME;
	    
	    private int _preventaId;
	    private int _productoId;
	    private int _promocionId;
	    
	    boolean WSBorrarPreVentaProducto;
	    int resultadoInt;
	    String resultadoString;
	    SoapObject soapResultado;
	    
	    public WSDeletePreventaProducto(int _preventaId,int _productoId,int _promocionId)
	    {
	    	this._preventaId = _preventaId;
	    	this._productoId = _productoId;
	    	this._promocionId = _promocionId;
	    }
	    
	    protected void onPreExecute()
	    {
	    	pdDeletePreventaProducto.show();
	    }
	    
	    protected Boolean doInBackground(Void... paramVarArgs)
	    {
	    	WSBorrarPreVentaProducto = false;
	    	
	    	SoapObject localSoapObject = new SoapObject(NAMESPACE,BORRARPREVENTAPRODUCTO_METHOD_NAME);
	    	localSoapObject.addProperty("preVentaId", _preventaId);
	    	localSoapObject.addProperty("productoId", _productoId);
	    	localSoapObject.addProperty("promocionId", _promocionId);
	    	SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
	    	localSoapSerializationEnvelope.dotNet = true;
	    	localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
	    	HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
	    		
	    	try
	    	{
	    		localHttpTransportSE.call(BORRARPREVENTAPRODUCTO_SOAP_ACTION, localSoapSerializationEnvelope);
	    		
	    		soapResultado = ((SoapObject)localSoapSerializationEnvelope.getResponse());
	    		
	    		if(soapResultado!=null)
	    		{
	    			resultadoInt = Integer.parseInt(soapResultado.getPropertyAsString("Id"));
		    	    resultadoString = String.valueOf(soapResultado.getPropertyAsString("Resultado"));	    			
	    		}
	    	        
	    	    if (resultadoString.equals("OK") && resultadoInt > 0) 
	    	    {
	    	      	WSBorrarPreVentaProducto = true;
	    	    }
	    	    return true;	        
			}
	    	catch (Exception localException)
	    	{
    			WSBorrarPreVentaProducto = false;
    			if(localException.getMessage() == null || localException.getMessage().isEmpty())
    			{
    		        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSDeletePreventaProducto: vacio");
    			}
    			else
    			{
    				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSDeletePreventaProducto: " + localException.getMessage());
    			} 
    			return true;
    		}
    	}
	    
	    protected void onPostExecute(Boolean ejecutado)
	    {
	    	if(pdDeletePreventaProducto.isShowing())
	    	{
	    		pdDeletePreventaProducto.dismiss();
	    	}
	    	
	    	if(ejecutado)
	    	{
	    		if(WSBorrarPreVentaProducto) 
	    		{
	    			BorrarPreventaProductoDispositivo((int)preventaProductoId);
	    			ModificarNuevosMontosPreventa();
	    			theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la preventa eliminado.", 1);
	    			CargarProductos();
	    			ActualizarListView();
	    		}
	    		else
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo eliminar el producto, posiblemente su conexion a internet no sea buena.", 1);
	    		}
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSDeletePreventaProducto.", 1);
	    	}
	    }
	 }
		 
	private void BorrarPreventaProductoDispositivo(int id)
	{
		boolean borrado = false;
		try
		{
			borrado = new PreventaProductoBLL().BorrarPreventaProductoPorId(id);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
    		{
    			myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventaProducto por rowId: vacio");
    		}
    		else
    		{
    			myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventaProdu to por rowId: " + localException.getMessage());
    		} 
		}
		
		if(!borrado)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar el producto de la preventa del dispositivo.", 1);
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
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el inventario del producto", 1);
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
			     float precioFinal = canalRutaPrecio.get_hpb() - (canalRutaPrecio.get_hpb() * (canalRutaPrecio.get_descuentoCanal() / 100)) - (canalRutaPrecio.get_hpb() * (canalRutaPrecio.get_descuentoAjuste() / 100));
			     tvPrecioUnitario.setText(String.valueOf(precioFinal / producto.get_conversion()));
			     tvPrecioMayor.setText(String.valueOf(precioFinal));
			     tvDescuento.setText(String.valueOf(clientePreventa.get_descuento()));
			     String strDsctoAjuste = String.valueOf(canalRutaPrecio.get_descuentoAjuste()) + " %";
			     tvDsctoAjuste.setText(strDsctoAjuste);
			     String strDsctoCanal = String.valueOf(canalRutaPrecio.get_descuentoCanal()) + " %";
			     tvDsctoCanal.setText(strDsctoCanal);
			     String DsctoProntoPago = String.valueOf(descuentoProntoPagoUni) + " %";
			     tvDsctoProntoPago.setText(DsctoProntoPago);
			     tvInventarioUni.setText(String.valueOf(almacenMaestroUnidades));
			     tvInventarioMay.setText(String.valueOf(0));
			 }
			 else
			 {
				localPrecioLista = null;
				try
				{
					localPrecioLista = new PrecioListaBLL().ObtenerPrecioListaPor(clientePreventa.get_precioListaId(),productoId);
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
					rbtMayor.setVisibility(View.INVISIBLE);
			    	rbtUnidades.setChecked(true);
				     
					tvProducto.setText(producto.get_descripcion25());
					tvConversion.setText(String.valueOf(1));
					tvPrecioUnitario.setText(String.valueOf(localPrecioLista.get_precio()));
					tvPrecioMayor.setText(String.valueOf(localPrecioLista.get_precioPaquete()));
					tvDescuento.setText(String.valueOf(clientePreventa.get_descuento()));
					tvDsctoAjuste.setText("0 %");
				    tvDsctoCanal.setText("0 %");
				    String strDsctoProntoPago = String.valueOf(descuentoProntoPagoUni) + " %";
				    tvDsctoProntoPago.setText(strDsctoProntoPago);
					tvInventarioUni.setText(String.valueOf(almacenMaestroUnidades));
					tvInventarioMay.setText(String.valueOf(0));
				}
				else
				{
			    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el precio de lista del producto.", 1);
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

	private void AdicionarPreventa()
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

			if(canalRutaPrecio == null)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener un precio de lista o ruta de precios para el producto.", 1);
				return;
			}
		}
		
		PreventaProducto localPreventaProducto = new PreventaProducto();

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
				unidades = true;
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
	
				/*marginacionActual = 100 - ((productoCosto.get_cpp()*100)/precioUnitario); 
	
				if (marginacionActual < marginacionAlmacenada) 
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "La marginacion de la unidad es menor a la establecida.", 1);
					return;
				}*/
	
				cantidadUnitaria = cantidad;
				cantidadPaquete = 0;
				monto = Float.parseFloat(new BigDecimal(cantidad * canalRutaPrecio.get_hpb() / producto.get_conversion()).setScale(5,RoundingMode.HALF_UP).toString());
	
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
	
				montoFinal = monto - descuentoTotal - (descuentoCanal / producto.get_conversion()) * cantidadSolicitadaEnUnidades 
													- (descuentoAjuste / producto.get_conversion())* cantidadSolicitadaEnUnidades
													- (dsctoProntoPago / producto.get_conversion() * cantidadSolicitadaEnUnidades);
	
				costoId = productoCosto.get_costoId();
			}
	
			if(rbtMayor.isChecked())
			{
				unidades = false;
				precioMayorFinal = 0;
				try
				{
					precioMayorFinal = canalRutaPrecio.get_hpb() - descuentoCanal - descuentoAjuste;
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
	
				/*if(marginacionActual < marginacionAlmacenadaPaquete)
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
	
			if(clientePreventa.get_tipoPagoId() == 2)
			{
				if(!ValidarCondicionCredito(montoFinal))
				{
					return;
				}
			}
	
			localPreventaProducto.set_preventaId(preventaId);
			localPreventaProducto.set_productoId(producto.get_productoId());
			localPreventaProducto.set_cantidad(cantidadUnitaria);
			localPreventaProducto.set_cantidadPaquete(cantidadPaquete);
			localPreventaProducto.set_monto(monto);
			localPreventaProducto.set_descuento(descuentoTotal);
			localPreventaProducto.set_montoFinal(montoFinal);
			localPreventaProducto.set_empleadoId(loginEmpleado.get_empleadoId());
			localPreventaProducto.set_promocionId(0);
			localPreventaProducto.set_costo(0);
			localPreventaProducto.set_costoId(costoId);
			localPreventaProducto.set_noPreventa(noPreventa);
			
			if(precioLista == null)
			{
				localPreventaProducto.set_precioId(0);
			}
			else
			{
				localPreventaProducto.set_precioId(precioLista.get_precioId());
			}
			
			float dsctoCanal = canalRutaPrecio.get_hpb() * (canalRutaPrecio.get_descuentoCanal() / 100);
			if(cantidadUnitaria > 0)
			{
				localPreventaProducto.set_descuentoCanal((dsctoCanal * cantidadUnitaria) / producto.get_conversion());
			}
			else
			{
				localPreventaProducto.set_descuentoCanal(dsctoCanal * cantidadPaquete);
			}

			float dsctoAjuste = canalRutaPrecio.get_hpb() * (canalRutaPrecio.get_descuentoAjuste() / 100);
			if(cantidadPaquete > 0)
			{
				localPreventaProducto.set_descuentoAjuste(dsctoAjuste * cantidadPaquete);
			}
			else
			{
				localPreventaProducto.set_descuentoAjuste((dsctoAjuste * cantidadUnitaria) / producto.get_conversion());
			}
			
			float dctoProntoPago = canalRutaPrecio.get_hpb() * (descuentoProntoPagoUni / 100);
			if(cantidadUnitaria > 0)
			{
				localPreventaProducto.set_descuentoProntoPago((dctoProntoPago * cantidadUnitaria) / producto.get_conversion());
			}
			else
			{
				localPreventaProducto.set_descuentoProntoPago(dctoProntoPago * cantidadPaquete);
			}

			localPreventaProducto.set_canalPrecioRutaId(canalRutaPrecio.get_canalPrecioRutaId());
		}
		else
		{
			assert precioLista != null;
			if(precioLista.get_precioId() <= 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo obtener el precioId del producto, precioId<=0.",1);
				return;
			}
			
			float dsctoProntoPago = 0;

			if(rbtUnidades.isChecked())
			{
				unidades = true;
				
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
	
				//if (marginacionActual < marginacionAlmacenada)
				if (BigDecimal.valueOf(marginacionActual).setScale(1,RoundingMode.CEILING).compareTo(BigDecimal.valueOf(marginacionAlmacenada)) < 0)
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
				unidades = false;
				
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
	
				if(precioMayor <= 0) 
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
	
				//if(marginacionActual < marginacionAlmacenadaPaquete)
				if (BigDecimal.valueOf(marginacionActual).setScale(1,RoundingMode.CEILING).compareTo(BigDecimal.valueOf(marginacionAlmacenadaPaquete)) < 0)
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
	
			if(clientePreventa.get_tipoPagoId() == 2)
			{
				if(preventa.get_tipoPagoId() == 2)
				{
					if(!ValidarCondicionCredito(montoFinal))
					{
						return;
					}
				}
			}
	
			localPreventaProducto.set_preventaId(preventaId);
			localPreventaProducto.set_productoId(producto.get_productoId());
			localPreventaProducto.set_cantidad(cantidadUnitaria);
			localPreventaProducto.set_cantidadPaquete(cantidadPaquete);
			localPreventaProducto.set_monto(monto);
			localPreventaProducto.set_descuento(descuentoTotal);
			localPreventaProducto.set_montoFinal(montoFinal);
			localPreventaProducto.set_empleadoId(loginEmpleado.get_empleadoId());
			localPreventaProducto.set_promocionId(0);
			localPreventaProducto.set_costo(0);
			localPreventaProducto.set_costoId(costoId);
			localPreventaProducto.set_noPreventa(noPreventa);
			localPreventaProducto.set_precioId(precioLista.get_precioId());
			localPreventaProducto.set_descuentoCanal(0);
			localPreventaProducto.set_descuentoAjuste(0);
			localPreventaProducto.set_descuentoProntoPago(dsctoProntoPago);
			localPreventaProducto.set_canalPrecioRutaId(0);
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

		if(ValidarExistenciasAlmacenTemporalDispositivo(loginEmpleado.get_almacenId(),productoIdAValidar,
				producto.get_conversion(),cantidadSolicitadaEnUnidades))
		{
			if(ValidarMontoNit(montoFinal))
			{
				if(parametroGeneral.get_montoCi() > 0)
				{
					if(ObtenerPreventaProductoMontoFinalAlmacenadoActual()+localPreventaProducto.get_montoFinal() >= parametroGeneral.get_montoCi())
					{
						if(nit.equals("") || nit.equals("0") || factura.equals("") || factura.equals("s/n") || factura.equals("S/N"))
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "No se puede ingresar el producto, ya que el monto excede el definido por el CI.", 1);
							return;
						}
					}
				}

				if(parametroGeneral.get_montoBancarizacion() > 0)
				{
					if(ObtenerPreventaProductoMontoFinalAlmacenadoActual()+localPreventaProducto.get_montoFinal() >= parametroGeneral.get_montoBancarizacion())
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se puede ingresar el producto, ya que el monto excede el definido por el de bancarizacion.", 1);
						return;
					}
				}

				if(parametroGeneral.get_tipoImpresionFactura().equals("Medio Oficio"))
				{
					if(listadoPreventaProducto == null || listadoPreventaProducto.size() <= 14)
					{
						if(preventaIdServer > 0)
						{
							if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
							{
								if(InsertarPreventaProductoDispositivo(localPreventaProducto,true))
								{
									InsertarPreventaProducto(localPreventaProducto);
								}
								else 
								{
									theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el producto de la preventa en el dispositivo.",1);
								}
							}
							else
							{
								theUtilidades.MostrarMensaje(getApplicationContext(), "No se encuentra conectado a internet, intentelo mas tarde.", 1);
							}
						}
						else
						{
							if(InsertarPreventaProductoDispositivo(localPreventaProducto,false))
							{
								ModificarNuevosMontosPreventa();
								theUtilidades.MostrarMensaje(getApplicationContext(), "Producto insertado, sincronice la preventa.", 1);
								CargarProductos();
								DespliegueControlesAdicionarPreventa(false);
								ActualizarListView();
							}
						}
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(),"Se alcanzo el tope de items para la factura medio oficio, ingrese una nueva preventa.",1);
					}
				}
				else if(parametroGeneral.get_tipoImpresionFactura().equals("Oficio"))
				{
					if(listadoPreventaProducto == null || listadoPreventaProducto.size() <= 34)
					{
						if(preventaIdServer > 0)
						{
							if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
							{
								if(InsertarPreventaProductoDispositivo(localPreventaProducto,true))
								{
									InsertarPreventaProducto(localPreventaProducto);
								}
								else 
								{
									theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el producto de la preventa en el dispositivo.",1);
								}
							}
							else
							{
								theUtilidades.MostrarMensaje(getApplicationContext(), "No se encuentra conectado a internet, intentelo mas tarde.", 1);
							}
						}
						else
						{
							if(InsertarPreventaProductoDispositivo(localPreventaProducto,false))
							{
								ModificarNuevosMontosPreventa();
								theUtilidades.MostrarMensaje(getApplicationContext(), "Producto insertado, sincronice la preventa.", 1);
								CargarProductos();
								DespliegueControlesAdicionarPreventa(false);
								ActualizarListView();
							}
						}
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(),"Se alcanzo el tope de items para la factura oficio, ingrese una nueva preventa.",1);
					}
				}
				else
				{
					if(preventaIdServer > 0)
					{
						if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
						{
							if(InsertarPreventaProductoDispositivo(localPreventaProducto,true))
							{
								InsertarPreventaProducto(localPreventaProducto);
							}
							else 
							{
								theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el producto de la preventa en el dispositivo.",1);
							}
						}
						else
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "No se encuentra conectado a internet, intentelo mas tarde.", 1);
						}
					}
					else
					{
						if(InsertarPreventaProductoDispositivo(localPreventaProducto,false))
						{
							ModificarNuevosMontosPreventa();
							theUtilidades.MostrarMensaje(getApplicationContext(), "Producto insertado, sincronice la preventa.", 1);
							CargarProductos();
							DespliegueControlesAdicionarPreventa(false);
							ActualizarListView();
						}
					}
				}
			}
			else
			{
				dialog = new Dialog(Vendedoredicionpreventaproducto.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.diseniodialogo_vendedorcierredia);
				dialog.setTitle("NIT Invalido !");
				dialog.setCancelable(false);
				dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.colorBluePale));

				TextView tvMensaje = (TextView)dialog.findViewById(R.id.tvDialogoMensaje);
				String strText = "Por el monto de la preventa (" + parametroGeneral.get_montoNit() + ") Bs. debe registrar un nombre de factura y nit valido. Por favor ingrese nuevamente la preventa.";
				tvMensaje.setText(strText);

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
		else
		{
			int saldoUnidades = ObtenerExistenciaProductoEnAlmacenPor(loginEmpleado.get_almacenId(),productoIdAValidar,producto.get_conversion());
			theUtilidades.MostrarMensaje(getApplicationContext(), "No existe producto en el almacen del dispositivo."
					+ "Cantidad actual: " + String.valueOf(saldoUnidades) + " unidades.", 1);
			return;
		}
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

		return  descuentoJerarquia;
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
	
	private boolean ValidarMontoNit(float montoFinalItem)
	{
		boolean validado = false;
		if(parametroGeneral.get_montoNit() <= 0)
		{
			validado = true;
		}
		else
		{
			if(ObtenerMontoPreventa(montoFinalItem) >= parametroGeneral.get_montoNit())
			{
				if(tvNombreFactura.getText().toString().equals("S/N") || tvNombreFactura.getText().toString().isEmpty() 
						|| tvNit.getText().toString().isEmpty() || tvNit.getText().toString().equals("0"))
				{
					validado = false;
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
	
	private float ObtenerMontoPreventa(float montoFinalItem)
	{
		float montoFinal = 0;
		
		if(listadoPreventaProducto != null && listadoPreventaProducto.size() > 0)
		{
			for(PreventaProducto item : listadoPreventaProducto)
	    	{
	    		montoFinal += item.get_montoFinal();
	    	}
		}
		
		return montoFinal + montoFinalItem;
	}
	
	private float ObtenerPreventaProductoMontoFinalAlmacenadoActual()
	{
		float montoFinal = 0;
		ArrayList<PreventaProducto> listadoPreventaProductoActual = null;
		try
		{
			listadoPreventaProductoActual = new PreventaProductoBLL().ObtenerPreventasProductoPor(preventaId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
  		  	{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtner las preventasProducto por preventaId: vacio");
  		  	}
  		  	else
  		  	{
  		  		myLog.InsertarLog("App",this.toString(),1,"Error al obtner las preventasProducto por preventaId" + localException.getMessage());
  		  	}
		}
		
		if(listadoPreventaProductoActual != null)
		{
			for(PreventaProducto item : listadoPreventaProductoActual)
			{
				montoFinal = montoFinal + item.get_montoFinal();
			}
		}
		
		return montoFinal;		
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
	 
	private void InsertarPreventaProducto(PreventaProducto localPreventaProducto)
	{
		pdInsertarPreventaProducto = new ProgressDialog(this);
	    pdInsertarPreventaProducto.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdInsertarPreventaProducto.setMessage("Insertando item preventa ...");
	    pdInsertarPreventaProducto.setCancelable(false);
	    pdInsertarPreventaProducto.setCanceledOnTouchOutside(false);
	    
	    WSInsertarPreventaProducto localWSInsertarPreventaProducto = new WSInsertarPreventaProducto(localPreventaProducto);
	    try
	    {
	    	localWSInsertarPreventaProducto.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarPreventaProducto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarPreventaProducto: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertarPreventaProducto", 1);
	    }
	 }
	 
	public class WSInsertarPreventaProducto extends AsyncTask<Void, Integer, Boolean>
	{
		 String PREVENTAPRODUCTO_METHOD_NAME = "InsertPreVentaProducto";
		 String PREVENTAPRODUCTO_SOAP_ACTION = NAMESPACE + PREVENTAPRODUCTO_METHOD_NAME;
		 
		 private PreventaProducto _preventaProducto;
		 
		 boolean WSInsertarPreVentaProductoTemp;
		 int resultadoInt;
		 String resultadoString;
		 SoapObject localSoapObjects;
	    
	    public WSInsertarPreventaProducto(PreventaProducto paramPreventaProducto)
	    {
	    	_preventaProducto = paramPreventaProducto;
	    }
	    
	    protected void onPreExecute()
	    {
	    	pdInsertarPreventaProducto.show();
	    }
	    
	    protected Boolean doInBackground(Void... paramVarArgs)
	    {
	    	WSInsertarPreVentaProductoTemp = false;
	      
	    	SoapObject localSoapObject = new SoapObject(NAMESPACE, this.PREVENTAPRODUCTO_METHOD_NAME);

	    	localSoapObject.addProperty("preVentaId", preventaIdServer);
	    	localSoapObject.addProperty("productoId", _preventaProducto.get_productoId());
	    	localSoapObject.addProperty("cantidad", _preventaProducto.get_cantidad());
	    	localSoapObject.addProperty("cantidadPaquete", _preventaProducto.get_cantidadPaquete());
	    	localSoapObject.addProperty("unidades", unidades);
	    	
	    	SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
	    	localSoapSerializationEnvelope.dotNet = true;
	    	localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
	    	HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
	    	try
			{
	    		localHttpTransportSE.call(PREVENTAPRODUCTO_SOAP_ACTION, localSoapSerializationEnvelope);
   			
	    		localSoapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
	    		if(localSoapObjects!=null)
	    		{
	    			resultadoInt = Integer.parseInt(localSoapObjects.getPropertyAsString("Id"));
	    			resultadoString = localSoapObjects.getPropertyAsString("Resultado");    				
	    		}
   			
	    		if(resultadoString.equals("OK") && resultadoInt >= 0)
	    		{
	    			WSInsertarPreVentaProductoTemp = true;
	    		}
	    		return true;
	    	}
	    	catch (Exception localException)
	    	{
	    		WSInsertarPreVentaProductoTemp = false;
	    		if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    		{
	    			myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventaProductoTemp: vacio");
	    		}
	    		else
	    		{
	    			myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventaProductoTemp: " + localException.getMessage());
	    		} 
	    		return true;
	    	}
	    }
	    
	    protected void onPostExecute(Boolean ejecutado)
	    {
	    	if(pdInsertarPreventaProducto.isShowing())
	    	{
	    		pdInsertarPreventaProducto.dismiss();
	    	}
	    	
	    	if(ejecutado) 
	    	{
	    		if(WSInsertarPreVentaProductoTemp || (resultadoString != null && resultadoString.equals("Preventa Producto Repetido") && resultadoInt < 0))
	    		{
	    			ModificarNuevosMontosPreventa();
	    			theUtilidades.MostrarMensaje(getApplicationContext(), "Producto de la preventa sincronizado.", 1);
	    			CargarProductos();
	    			DespliegueControlesAdicionarPreventa(false);
	    			ActualizarListView();
	    		}
	    		else 
	    		{
	    			BorrarPreventaProductoDispositivo((int)preventaProductoId);
	    			if(resultadoString.equals(""))
	    			{
	    				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo ingresar el producto, posiblemente su conexion a internet no sea la adecuada o ya se cerro su dia.", 1);
	    			}
	    			else
	    			{
	    				theUtilidades.MostrarMensaje(getApplicationContext(),resultadoString, 1);
	    			}
	    			
	        	}
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "El WebService InsertPreVentaProducto no se ejecuto correctamente. ", 1);
	    		return;
	    	}
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
	 
	private boolean InsertarPreventaProductoDispositivo(PreventaProducto paramPreventaProducto,boolean estado)
	 {
		preventaProductoId = 0;
		 try
		 {
			 preventaProductoId = new PreventaProductoBLL().InsertarPreventaProducto(paramPreventaProducto.get_preventaId(),
					 paramPreventaProducto.get_productoId(),paramPreventaProducto.get_cantidad(), 
					 paramPreventaProducto.get_cantidadPaquete(),paramPreventaProducto.get_monto(),
					 paramPreventaProducto.get_descuento(),paramPreventaProducto.get_montoFinal(),
					 paramPreventaProducto.get_empleadoId(),0,estado,paramPreventaProducto.get_costo(),
					 paramPreventaProducto.get_costoId(),paramPreventaProducto.get_noPreventa(),
					 paramPreventaProducto.get_precioId(),paramPreventaProducto.get_descuentoCanal(),
					 paramPreventaProducto.get_descuentoAjuste(),paramPreventaProducto.get_canalPrecioRutaId(),
					 paramPreventaProducto.get_descuentoProntoPago());
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preVentaProducto en el dispositivo: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preVentaProducto en el dispositivo: " + localException.getMessage());
	    	}
	    	return false;
	    }
	    	
	    if(preventaProductoId==0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la preventa producto en el dispositivo.", 1);
	    	return false;		
	    }
	    else
	    {
	    	return true;
	    }
	 }
	
	private void ModificarNuevosMontosPreventa()
	{
		ArrayList<PreventaProducto> listadoPreventaProducto = null;
		float monto = 0;
		float descuento = 0;
		float montoFinal = 0;
		float descuentoCanal = 0;
		float descuentoAjuste = 0;
		float descuentoProntoPago = 0;
		float descuentoObjetivo = 0;
		
		try
		{
			listadoPreventaProducto = new PreventaProductoBLL().ObtenerPreventasProductoPor(preventaId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductos por preventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProducto por preventaId: " + localException.getMessage());
	    	}
		}
		
		if(listadoPreventaProducto == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los productos de las preventas por preventaId", 1);
		}
		else
		{
			for(PreventaProducto item : listadoPreventaProducto)
			{
				monto += item.get_monto();
				descuento += item.get_descuento();
				montoFinal += item.get_montoFinal();
				descuentoCanal += item.get_descuentoCanal();
				descuentoProntoPago += item.get_descuentoProntoPago();
				descuentoAjuste += item.get_descuentoAjuste();
			}
			
			//Verificamos si tiene saldo del rebate dracaris
			DraRebateSaldo draRebateSaldo = VerificarClienteTieneSaldoRebate(); 
			if(draRebateSaldo != null)
			{
				if(draRebateSaldo.get_saldo() > 0)
				{
					float dsctoDracaris = montoFinal * draRebateSaldo.get_maxDescuento() / 100;
					float dsctoDracarisRecalculado = draRebateSaldo.get_saldo() + preventa.get_descuentoObjetivo();
					
					if(dsctoDracaris <= dsctoDracarisRecalculado)
					{
						descuentoObjetivo = dsctoDracaris;
						montoFinal -= dsctoDracaris;
						ActualizarDraRebateSaldo(dsctoDracarisRecalculado - dsctoDracaris);
					}
					else
					{
						descuentoObjetivo = dsctoDracarisRecalculado;
						montoFinal -= dsctoDracarisRecalculado;
						ActualizarDraRebateSaldo(0);
					}
				}
			}
			
			long l = 0;
			
			try
			{
				l = new PreventaBLL().ModificarPreventaMontosPorPreventaId(preventaId, monto, descuento, montoFinal, descuentoCanal, descuentoAjuste, descuentoProntoPago, descuentoObjetivo);
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
		    	{
		            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductos por preventaId: vacio");
		    	}
		    	else
		    	{
		    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProducto por preventaId: " + localException.getMessage());
		    	}
			}
			
			if(l == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar los montos de la preventa.", 1);
			}
			
			ObtenerPreventa();
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
	    	
	    	if(!ejecutado){
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetProductosByAlmacenTemp no se ejecuto correctamente. ", 1);
				return;
			}
			if(!WSObtenerAlmacenProducto){
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron productos del almacen que descargar.", 1);
				if((parametroGeneral.is_habilitarPop() || parametroGeneral.is_habilitarCambio()) && ObtenerRolEmpleado() == 3)//Rol 3 = vendedor
				{
					MostrarPantallaMenuOpciones();;
				}
				else
				{
					MostrarPantallaMenuVendedor();
				}
				return;
			}

			if(!BorrarAlmacenesProducto()) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo eliminar los prodcutos del almacen.", 1);
				return;
			}

			long l = 0;
			try
			{
				l = new AlmacenProductoBLL().InsertarAlmacenProducto(almacenProductoWSResults);

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
			if((parametroGeneral.is_habilitarPop() || parametroGeneral.is_habilitarCambio()) && ObtenerRolEmpleado() == 3)//Rol 3 = vendedor
			{
				MostrarPantallaMenuOpciones();;
			}
			else
			{
				MostrarPantallaMenuVendedor();
			}
	    }
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
			
			if(listadoPreventaProducto !=null && listadoPreventaProducto.size() >0)
			{
				for(PreventaProducto item : listadoPreventaProducto)
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
		
		if(clientePreventa.get_montoPendienteCredito() == 0 && parametroGeneral.is_creditoSobreCredito() == false)
		{
			float acumulado = 0;
			
			if(listadoPreventaProducto !=null && listadoPreventaProducto.size() >0)
			{
				for(PreventaProducto item : listadoPreventaProducto)
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
	
	private float ObtenerMontoPreventaTemp()
	{
		float montoFinal = 0;
		for(PreventaProducto item : listadoPreventaProducto)
    	{
    		montoFinal += item.get_montoFinal();
    	}
		return montoFinal;
	}
	
	private boolean BorrarAlmacenesProducto() 
	{
		try
		{
			boolean bool = new AlmacenProductoBLL().BorrarAlmacenProductos();
			return bool;
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

	public void MostrarPantallaMenuOpciones()
	{
		Intent localIntent = new Intent(this, Vendedorpreventamenuopciones.class);
		localIntent.putExtra("preventaIdDispositivo", preventaIdDispositivo);
		localIntent.putExtra("preventaIdServer", preventaIdServer);
		localIntent.putExtra("clienteId", clienteId);
		startActivity(localIntent);
	}

	public void MostrarPantallaMenuVendedor()
	 {
	    startActivity(new Intent(this, Menuvendedor.class));
	 }

	public void MostrarPantallaEdicionPreventaPromocion()
	 {
		 Intent localIntent = new Intent(this, Vendedoredicionpreventapromocion.class);
		 localIntent.putExtra("preventaId", preventaId);
		 localIntent.putExtra("preventaIdServer", preventaIdServer);
		 localIntent.putExtra("clienteId", clienteId);
		 localIntent.putExtra("factura", factura);
		 localIntent.putExtra("nit", nit);
		 localIntent.putExtra("tipoNit", tipoNit);
		 localIntent.putExtra("dosificacionId",dosificacionId);
		 localIntent.putExtra("aplicarProntoPago", aplicarProntoPago);
		 startActivity(localIntent);	 
	 }
	 
	@Override
	public void onBackPressed() 
	{
		if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
		{
			ObtenerAlmacenProducto();
		}
		else
		{
			MostrarPantallaMenuVendedor();
		}
 	}
}
