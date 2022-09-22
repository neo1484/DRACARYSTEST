package com.detesim.venderunilever;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import BLL.DevolucionDistribuidorProductoTempBLL;
import BLL.DosificacionProveedorBLL;
import BLL.DraRebateSaldoBLL;
import BLL.CanalRutaPrecioBLL;
import BLL.CategoriaBLL;
import BLL.ClienteVentaBLL;
import BLL.CondicionTributariaBLL;
import BLL.DevolucionDistribuidorProductoBLL;
import BLL.MyLogBLL;
import BLL.ParametroGeneralBLL;
import BLL.PrecioListaBLL;
import BLL.ProductoBLL;
import BLL.ProductoCostoBLL;
import BLL.PromocionBLL;
import BLL.PromocionProductoBLL;
import BLL.ProntoPagoCanalRutaBLL;
import BLL.ProntoPagoClienteBLL;
import BLL.ProntoPagoJerarquiaBLL;
import BLL.ProveedorBLL;
import BLL.ProveedorPrecioListaMargenBLL;
import BLL.SincronizacionVentaBLL;
import BLL.VentaBLL;
import BLL.VentaProductoBLL;
import Clases.DevolucionDistribuidorProductoTemp;
import Clases.DosificacionProveedor;
import Clases.DraRebateSaldo;
import Clases.CanalRutaPrecio;
import Clases.Categoria;
import Clases.ClienteVenta;
import Clases.CondicionTributaria;
import Clases.DevolucionDistribuidorProducto;
import Clases.Fecha;
import Clases.LoginEmpleado;
import Clases.ParametroGeneral;
import Clases.PrecioLista;
import Clases.Producto;
import Clases.ProductoCosto;
import Clases.Promocion;
import Clases.PromocionProducto;
import Clases.ProntoPagoCanalRuta;
import Clases.ProntoPagoCliente;
import Clases.ProntoPagoJerarquia;
import Clases.Proveedor;
import Clases.ProveedorPrecioListaMargen;
import Clases.TipoPago;
import Clases.Ubicacion;
import Clases.Venta;
import Clases.VentaProducto;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Distribuidorautoventa extends Activity implements OnClickListener
{
	LinearLayout llDistribuidorAutoventa;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	LoginEmpleado loginEmpleado;
	MyLogBLL myLog = new MyLogBLL();
	
	ArrayList<DevolucionDistribuidorProductoTemp> listadoDevolucionDistribuidorProductoTemp;
	ArrayList<VentaProducto> listadoVentaProductoNoSincronizado;
	ArrayList<DosificacionProveedor> listadoDosificacionProvedor;
	Venta venta;
	Producto producto;
	ClienteVenta clienteVenta;
	int clienteId;
	ParametroGeneral parametroGeneral;
	int tipoPagoId;
	boolean venderCredito;
	String tipoNit;
	String factura;
	String nit;
	boolean nitNuevo;
	int noAutoventa;
	int itemBorrarDispositivo;
	int itemBorrarServidor;
	long ventaIdDispositivo;
	boolean aplicarBonificacion;
	String observacion;
	int dosificacionId;
	boolean boolCondicionTributaria=false;
	float montoTributarioServer = 0;
	CanalRutaPrecio canalRutaPrecio;
	boolean aplicarProntoPago;
	float descuentoProntoPagoUni;
	int prontoPagoId;
	DraRebateSaldo draRebateSaldo;
	
	Dialog dialog;
	Spinner spnTipoPago;
	TextView tvDosificaciones;
	Spinner spnDosificaciones;
	TextView tvProveedores;
	Spinner spnProveedores;
	TextView tvCategorias;
	Spinner spnCategorias;
	TextView tvProductos;
	Spinner spnProductos;
	
	ImageView ivPromociones;
	
	Button btnMostrarProducto;
	Button btnAdicionarVenta;
	Button btnConfirmarVenta;
	
	EditText etNombreFactura;
	EditText etNit;
	Button btnCambiarFactura;
	
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
	
	TextView tvDescuentoCanalTxt;
	TextView tvDescuentoCanal;
	TextView tvDescuentoAjusteTxt;
	TextView tvDescuentoAjuste;
	TextView tvDescuentoProntoPagoTxt;
	TextView tvDescuentoProntoPago;
	TextView tvPrecioUnitarioFinalTxt;
	TextView tvPrecioUnitarioFinal;
	TextView tvPrecioMayorFinalTxt;
	TextView tvPrecioMayorFinal;
	
	TextView tvInventarioUnidadestxt;
	TextView tvInventarioUnidades;
	TextView tvInventarioPaquetestxt;
	TextView tvInventarioPaquetes;
	TextView tvCantidadtxt;
	TextView tvProductolbl;
	TextView tvCantidadlbl;
	TextView tvPrecioUnitariolbl;
	TextView tvPrecioMayorlbl;
	TextView tvSubTotallbl;
	TextView tvTotaltxt;
	TextView tvTotal;
	TextView tvDsctoObjetivoTxt;
	TextView tvDsctoObjetivo;
	TextView tvDsctoObjetivoTotalTxt;
	TextView tvDsctoObjetivoTotal;
	TextView tvMontoFinalTxt;
	TextView tvMontoFinal;
	CheckBox cbAplicarBonificacion;
	CheckBox cbAplicarProntoPago;
	TextView tvObservacion;
	EditText etObservacion;
	
	EditText etvCantidad;
	RadioButton rbtMayor;
	RadioButton rbtUnidades;
	ListView lvVentaProductoTemp;

	ProgressDialog pdEsperaObtenerAlmacenProducto;
	ProgressDialog pdInsertarVentaProducto; 
	ProgressDialog pdDeleteVentaProducto;
	ProgressDialog pdInsertarVenta;
	ProgressDialog pdEsperaObtenerAvanceVentasDiaByVendedor;
	ProgressDialog pdDeleteAlmacenDevolucionProductoTemp;
	ProgressDialog pdInsertarAutoventaProductoTemp;
	ProgressDialog pdInsertarAutoventa;
	ProgressDialog pdVerificarMontoNitCliente;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_distribuidorautoventa);
		
		llDistribuidorAutoventa = (LinearLayout)findViewById(R.id.DistribuidorAutoventaLinearLayout);
		
		spnTipoPago = (Spinner)findViewById(R.id.spnAutoventaTipoPago);
		tvDosificaciones = (TextView)findViewById(R.id.tvAutoventaProductoDosificacion);
		spnDosificaciones = (Spinner)findViewById(R.id.spnAutoventaProductoDosificacion);
		tvProveedores = (TextView)findViewById(R.id.tvVendedorPreventaProductoProveedor);
		cbAplicarProntoPago = ((CheckBox)findViewById(R.id.cbDisAutProAplicarProntoPago));
		cbAplicarProntoPago.setOnClickListener(this);
		spnProveedores = ((Spinner)findViewById(R.id.spnVendedorPreventaProductoProveedor));
		tvCategorias = (TextView)findViewById(R.id.tvVendedorPreventaProductoCategoria);
		spnCategorias = ((Spinner)findViewById(R.id.spnVendedorPreventaProductoCategoria));
		tvProductos = (TextView)findViewById(R.id.tvVendedorPreventaProductoProducto);
	    spnProductos = ((Spinner)findViewById(R.id.spnVendedorPreventaProductoProducto));	    
	    
	    ivPromociones = (ImageView)findViewById(R.id.imgbtnVendedorVentaDirectaProducto);
	    ivPromociones.setOnClickListener(this);
	    btnCambiarFactura = ((Button)findViewById(R.id.btnAutoventaCambiarDatosFactura));
	    btnCambiarFactura.setOnClickListener(this);
	    btnMostrarProducto = ((Button)findViewById(R.id.btnVendedorVentaDirectaProductoDatosSeleccion));
	    btnMostrarProducto.setOnClickListener(this);
	    btnAdicionarVenta = ((Button)findViewById(R.id.btnVendedorVentaDirectaProductoAdicionarPreventa));
	    btnAdicionarVenta.setOnClickListener(this);
	    btnConfirmarVenta = ((Button)findViewById(R.id.btnVendedorEdicionPreventaProductoConfirmarVenta));
	    btnConfirmarVenta.setOnClickListener(this);
		
	    etNombreFactura = (EditText)findViewById(R.id.etAutoventaNombreFactura);
	    etNit = (EditText)findViewById(R.id.etAutoventaNit);
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
	    
	    tvDescuentoCanalTxt = (TextView) findViewById(R.id.tvDisAutDescuentoCanalTxt);
	    tvDescuentoCanal = (TextView) findViewById(R.id.tvDisAutDescuentoCanal);
	    tvDescuentoAjusteTxt = (TextView) findViewById(R.id.tvDisAutDescuentoAjusteTxt);
	    tvDescuentoAjuste = (TextView) findViewById(R.id.tvDisAutDescuentoAjuste);
	    tvDescuentoProntoPagoTxt = (TextView) findViewById(R.id.tvDisAutDescuentoProntoPagoTxt);
	    tvDescuentoProntoPago = (TextView) findViewById(R.id.tvDisAutDescuentoProntoPago);
	    tvPrecioUnitarioFinalTxt = (TextView) findViewById(R.id.tvDisAutPrecioUnitarioFinalTxt);
	    tvPrecioUnitarioFinal = (TextView) findViewById(R.id.tvDisAutPrecioUnitarioFinal);
	    tvPrecioMayorFinalTxt = (TextView) findViewById(R.id.tvDisAutPrecioMayorFinalTxt);
	    tvPrecioMayorFinal = (TextView) findViewById(R.id.tvDisAutPrecioMayorFinal);
		
	    tvInventarioUnidadestxt = (TextView)findViewById(R.id.tvAutoventaInventarioUnidadesLabel);
	    tvInventarioUnidades = (TextView)findViewById(R.id.tvAutoventaInventarioUnidades);
	    tvInventarioPaquetestxt = (TextView)findViewById(R.id.tvAutoventaInventarioPaquetesLabel);
	    tvInventarioPaquetes = (TextView)findViewById(R.id.tvAutoventaInventarioPaquetes);
	    tvCantidadtxt = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoCantidad));
	    tvProductolbl = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoProductoLbl));
	    tvCantidadlbl = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoCantidadLbl));
	    tvPrecioUnitariolbl = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoPrecioUnitarioLbl));
	    tvPrecioMayorlbl = ((TextView)findViewById(R.id.tvVendedoVentaDirectaProductoPrecioMayorLbl));
	    tvSubTotallbl = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoSubTotalLbl));
	    tvTotaltxt = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoTotalLbl));
	    tvTotal = ((TextView)findViewById(R.id.tvVendedorVentaDirectaProductoTotal));
	    tvDsctoObjetivoTxt = (TextView)findViewById(R.id.tvAutProDescuentoObjetivoTxt);
	    tvDsctoObjetivo = (TextView)findViewById(R.id.tvAutProDescuentoObjetivo);
	    tvDsctoObjetivoTotalTxt = (TextView)findViewById(R.id.tvAutProDescuentoObjetivoTotalTxt);
	    tvDsctoObjetivoTotal = (TextView)findViewById(R.id.tvAutProDescuentoObjetivoTotal);
	    tvMontoFinalTxt = (TextView)findViewById(R.id.tvAutProMontoFinalTxt);
	    tvMontoFinal = (TextView)findViewById(R.id.tvAutProMontoFinal);
	    cbAplicarBonificacion = (CheckBox)findViewById(R.id.cbDistribuidorAutoventaProductoAplicarBonificacion);
	    cbAplicarBonificacion.setOnClickListener(this);
	    tvObservacion = ((TextView)findViewById(R.id.tvAutoventaObservacion));
	    etObservacion = ((EditText)findViewById(R.id.etAutoventaObservacion));
	    
	    etvCantidad = ((EditText)findViewById(R.id.etVendedorVentaDirectaProductoCantidad));
	    rbtUnidades = ((RadioButton)findViewById(R.id.rbtVendedorVentaDirectaProductoUnidades));
	    rbtMayor = ((RadioButton)findViewById(R.id.rbtVendedorVentaDirectaProductoMayores));
	    lvVentaProductoTemp = ((ListView)findViewById(R.id.lvVendedorVentaDirectaProductoPreventas));
	    
	    llDistribuidorAutoventa.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
	    
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
	    	    
	    aplicarBonificacion = false;
	    aplicarBonificacion = getIntent().getExtras().getBoolean("aplicarBonificacion");
	    cbAplicarBonificacion.setChecked(aplicarBonificacion);
	    
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
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el loginEmpleado distribuidor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el loginEmpleado distribuidor: " + localException.getMessage());
	    	} 
	    }
	    
	    if(loginEmpleado == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El usuario no se logeo correctamente.", 1);
	    	return;
	    }
	    else
	    {
	    	DespliegueControlesAdicionarVenta(false);
	        DespliegueControlesConfirmarVenta(false);
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
			MostrarPantallaAutoventaPromocion();
			break;
		case R.id.btnAutoventaCambiarDatosFactura:
			CambiarDatosFactura();
			break;
		case R.id.btnVendedorVentaDirectaProductoDatosSeleccion:
			ObtenerDatosSeleccion();
			break;		
		case R.id.btnVendedorVentaDirectaProductoAdicionarPreventa:
			AdicionarAutoventaTemporal();
			break;
		case R.id.btnVendedorEdicionPreventaProductoConfirmarVenta:
			ConfirmarAutoventa();			
			break;
		case R.id.cbDistribuidorAutoventaProductoAplicarBonificacion:
			AplicarBonificacion();
			break;
		case R.id.cbDisAutProAplicarProntoPago:
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
	
	private void DespliegueControlesAdicionarVenta(boolean estado)
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
	    tvInventarioUnidadestxt.setVisibility(visibilidad);
	    tvInventarioUnidades.setVisibility(visibilidad);
	    tvInventarioPaquetestxt.setVisibility(visibilidad);
	    tvInventarioPaquetes.setVisibility(visibilidad);
	    tvCantidadtxt.setVisibility(visibilidad);
	    etvCantidad.setVisibility(visibilidad);
	    rbtUnidades.setVisibility(visibilidad);
	    rbtMayor.setVisibility(visibilidad);
	    btnAdicionarVenta.setVisibility(visibilidad); 
	    etvCantidad.setText("");
	}
	
	private void DespliegueControlesConfirmarVenta(boolean estado)
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
	    lvVentaProductoTemp.setVisibility(visibilidad);
	    tvTotaltxt.setVisibility(visibilidad);
	    tvTotal.setVisibility(visibilidad);
	    tvDsctoObjetivoTxt.setVisibility(visibilidad);
	    tvDsctoObjetivo.setVisibility(visibilidad);
	    tvDsctoObjetivoTotalTxt.setVisibility(visibilidad);
	    tvDsctoObjetivoTotal.setVisibility(visibilidad);
	    tvMontoFinalTxt.setVisibility(visibilidad);
	    tvMontoFinal.setVisibility(visibilidad);
	    cbAplicarBonificacion.setVisibility(visibilidad);
	    tvObservacion.setVisibility(visibilidad);
	    etObservacion.setVisibility(visibilidad);
	    btnConfirmarVenta.setVisibility(visibilidad);
	      
    }

	public void CargarInformacion()
	{
		CargarParametroGeneral();
		CargarClienteVenta();
		CargarTiposPago();
	    CargarDosificacionesProveedorVendedor();
	    CargarProveedores();
	    CargarCategorias();
	    CargarProductos();
	    ActualizarListView();
	    ObtenerNoAutoventa();
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
	    if(clienteVenta.get_tipoPagoId() == 1)
	    {
	    	spnTipoPago.setEnabled(false);
	    }
	    else if(clienteVenta.get_tipoPagoId() == 2 && parametroGeneral.is_respetarTipoPago() == false)
	    {
	    	spnTipoPago.setEnabled(true);
	    }
	    else if(clienteVenta.get_tipoPagoId() == 2 && parametroGeneral.is_respetarTipoPago() == true)
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
		
	private void CargarClienteVenta()
	{
		clienteVenta = null;
	    try
	    {
	    	clienteVenta = new ClienteVentaBLL().ObtenerClienteVentaPor(clienteId);
	    }
	    catch (Exception localException)
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
	    
	    if (clienteVenta == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el clienteVenta por clienteId.", 1);
	    	return;
	    }
	    else
	    {
	    	tvNombre.setText(clienteVenta.get_nombreCompleto().toString());
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

	private void HabilitarDatosFactura(boolean estado)
	{
		int visibility;
		if(estado)
		{
			visibility = View.VISIBLE;
			etNombreFactura.setVisibility(visibility);
			etNit.setVisibility(visibility);
			btnCambiarFactura.setVisibility(visibility);
		}
		else
		{
			visibility = View.INVISIBLE;
			etNombreFactura.setEnabled(false);
			etNit.setEnabled(false);
			btnCambiarFactura.setVisibility(visibility);
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
					listadoProducto = new ProductoBLL().ObtenerProductoPorProveedorNoEnAlmacenDevolucionProductoTemp(
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
	    	listadoProducto = new ProductoBLL().ObtenerProductoPorProveedorNoEnAlmacenDevolucionProductoTemp(proveedorId,
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
				DespliegueControlesAdicionarVenta(false);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent){}
	    	
		});
	}
	
	public void ActualizarListView()
	{
		listadoDevolucionDistribuidorProductoTemp = null;
		try
		{
			listadoDevolucionDistribuidorProductoTemp = new DevolucionDistribuidorProductoTempBLL().ObtenerDevolucionDistribuidorProductoTempPor(clienteId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolucionProductoTemp por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolucionProductoTemp por clienteId: " + localException.getMessage());
			}
		}
		      
		if(listadoDevolucionDistribuidorProductoTemp == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No existen productos temporales.", 1);
			spnDosificaciones.setEnabled(true);
			DespliegueControlesConfirmarVenta(false);
			lvVentaProductoTemp.setAdapter(null);
		}
		else
		{
			spnDosificaciones.setEnabled(false);
			DespliegueControlesConfirmarVenta(true);
			CalcularTotalVenta();
			lvVentaProductoTemp.setVisibility(View.VISIBLE);
		    LlenarVentaListView();
		    EliminarItemListView();	
		}    
		
		if(listadoDevolucionDistribuidorProductoTemp != null && listadoDevolucionDistribuidorProductoTemp.size()>0)
		{
			spnTipoPago.setEnabled(false);
		}
		else
		{
			spnTipoPago.setEnabled(true);
		}
	}
	
	private void ObtenerNoAutoventa()
	{
		noAutoventa = -1;
	    try
	    {
	    	noAutoventa = new VentaBLL().ObtenerVentas().size();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las autoventas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las autoventas: " + localException.getMessage());
	    	}
	    	noAutoventa = 0;
	    }
	    
	    if (noAutoventa == -1)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el numero de autoventas.", 1);
	    	return;
	    }
	    else
	    {
	    	if(!VerificarExistenciaCliente(clienteId))
	    	{
	    		noAutoventa++;
	    	}
	    }
	}

	private void VerificarExistenciaProntoPago()
	{
		if(listadoDevolucionDistribuidorProductoTemp != null && listadoDevolucionDistribuidorProductoTemp.size() > 0)
		{
			float dsctoPP = 0;
			for(DevolucionDistribuidorProductoTemp item : listadoDevolucionDistribuidorProductoTemp)
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
		ArrayList<DevolucionDistribuidorProductoTemp> listadoDevolucionDistribuidorProductoTemp = null;
		try
		{
			listadoDevolucionDistribuidorProductoTemp = new DevolucionDistribuidorProductoTempBLL().ObtenerDevolucionDistribuidorProductoTempPor(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidorProductoTemp por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidorProductoTemp por clienteId: " + localException.getMessage());
	    	}
		}
		
		if(listadoDevolucionDistribuidorProductoTemp == null)
		{
			return verificado;
		}
		else
		{
			noAutoventa = listadoDevolucionDistribuidorProductoTemp.get(0).get_noAutoventa();
			return true;
		}
	}
	
	private void CalcularTotalVenta()
	{
		float total = 0;
		for(DevolucionDistribuidorProductoTemp item : listadoDevolucionDistribuidorProductoTemp)
		{
			total += Float.parseFloat(new BigDecimal(item.get_montoFinal()).setScale(5,RoundingMode.HALF_UP).toString());
		}
		
		tvTotal.setText(String.valueOf(new BigDecimal(total).setScale(2,RoundingMode.HALF_UP).toString()));	
		
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
	
	private void LlenarVentaListView()
	{
	    MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    	    
	    if(listadoDevolucionDistribuidorProductoTemp == null)
	    {
	    	lvVentaProductoTemp.setAdapter(null);
	    }
	    else
	    {
		    ViewGroup.LayoutParams localLayoutParams = lvVentaProductoTemp.getLayoutParams();
		    localLayoutParams.height = ((int)(50*getApplicationContext().getResources().getDisplayMetrics().density) * listadoDevolucionDistribuidorProductoTemp.size());
		    lvVentaProductoTemp.setLayoutParams(localLayoutParams);
		    lvVentaProductoTemp.setAdapter(localMiAdaptadorLista);
	    }
	}

	class MiAdaptadorLista extends ArrayAdapter<DevolucionDistribuidorProductoTemp>
	{
		private Context _context;
		
		public MiAdaptadorLista(Context context)
		{
			super(context,R.layout.disenio_vendedorpreventaproducto,listadoDevolucionDistribuidorProductoTemp);
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
			
			DevolucionDistribuidorProductoTemp localAlmacenDevolucionProductoTemp = (DevolucionDistribuidorProductoTemp)listadoDevolucionDistribuidorProductoTemp.get(position);
			
			TextView descripcion25 = (TextView)localView.findViewById(R.id.tvVendedorVentaDirectaProductoCliente);
			TextView cantidad = (TextView)localView.findViewById(R.id.tvPreventaDisenioCantidad);
			TextView precio = (TextView)localView.findViewById(R.id.tvPreventaDisenioPrecioUnitario);
			TextView precioPaquete = (TextView)localView.findViewById(R.id.tvVendedorPreventaProductoProveedor);
			TextView montoFinal = (TextView)localView.findViewById(R.id.tvVendedorPreventaProductoProducto);
			ImageView bullet = (ImageView)localView.findViewById(R.id.imgvLoginLogotipo);
      
			if(localAlmacenDevolucionProductoTemp.get_productoId() != 0)
			{
				Producto localProducto = null;
				try
				{
					localProducto = new ProductoBLL().ObtenerProductoPor(localAlmacenDevolucionProductoTemp.get_productoId());
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
					localPrecioLista = new PrecioListaBLL().ObtenerPrecioListaPor(clienteVenta.get_precioListaId(),localProducto.get_productoId());
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

				if(clienteVenta.get_canalRutaId() > 0)
				{
					try
					{
						canalRutaPrecio = new CanalRutaPrecioBLL().ObtenerCanalRutaPrecioPorCanalRutaIdYProductoId(clienteVenta.get_canalRutaId(),localProducto.get_productoId());
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
				cantidad.setText(String.valueOf(localAlmacenDevolucionProductoTemp.get_cantidad()==0?localAlmacenDevolucionProductoTemp.get_cantidadPaquete():localAlmacenDevolucionProductoTemp.get_cantidad()));
				
				if(localAlmacenDevolucionProductoTemp.get_cantidad()==0)
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
				
				if(localAlmacenDevolucionProductoTemp.get_cantidadPaquete()==0)
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
				
				montoFinal.setText(String.valueOf(localAlmacenDevolucionProductoTemp.get_montoFinal()));
				
			}
			
			if(localAlmacenDevolucionProductoTemp.get_promocionId() != 0) 
			{
				Promocion localPromocion = null;
				try
				{
					localPromocion = new PromocionBLL().ObtenerPromocionPor(localAlmacenDevolucionProductoTemp.get_promocionId());
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
					return null;
				}
				
				if(localPromocion == null)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles de la promocion.", 1);
					return null;
				}
				
				descripcion25.setText(localPromocion.get_descripcion());
				cantidad.setText(String.valueOf(localAlmacenDevolucionProductoTemp.get_cantidad()));
				precio.setText(String.valueOf(new BigDecimal(localAlmacenDevolucionProductoTemp.get_monto()/localAlmacenDevolucionProductoTemp.get_cantidad()).setScale(2, RoundingMode.HALF_UP)));
				precioPaquete.setText(" ");
				montoFinal.setText(String.valueOf(new BigDecimal(localAlmacenDevolucionProductoTemp.get_montoFinal()).setScale(2, RoundingMode.HALF_UP)));
			}
			
			bullet.setImageResource(R.drawable.bullet_eliminar);
			
			return localView;
		}
	}
	
	private void EliminarItemListView()
	{
		((ListView)findViewById(R.id.lvVendedorVentaDirectaProductoPreventas)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
			public void onItemClick(AdapterView<?> parent, View view,final int position, long id)
			{
				dialog = new Dialog(Distribuidorautoventa.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.diseniodialogo_vendedorcierredia);
				dialog.setTitle("Eliminando item");
				dialog.setCancelable(false);
				dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.colorBluePale));
				
				TextView tvMensaje = (TextView)dialog.findViewById(R.id.tvDialogoMensaje);
				tvMensaje.setText("Esta seguro de eliminar el item?");
				
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
								
								itemBorrarServidor = 0;
								itemBorrarDispositivo = 0;
								
								DevolucionDistribuidorProductoTemp localAlmacenDevolucionProductoTemp = listadoDevolucionDistribuidorProductoTemp.get(position);
								itemBorrarDispositivo = localAlmacenDevolucionProductoTemp.get_id();
								
								if(localAlmacenDevolucionProductoTemp.get_tempId() != 0)
								{
									itemBorrarServidor = localAlmacenDevolucionProductoTemp.get_tempId();
									BorrarAlmacenDevolucionProductoTemp();
								}
								else
								{
									if(BorrarAutoventaProductoTempDispositivo())
									{
										theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la venta eliminado del dispositivo.", 1);
										CargarProductos();
									} 
									else 
									{
										theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo eliminar el item de la venta.", 1);
									}
								}

								if(listadoDevolucionDistribuidorProductoTemp != null) 
								{
									ActualizarListView();
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
	    });
	}
	
	private void BorrarAlmacenDevolucionProductoTemp()
	{
		pdDeleteAlmacenDevolucionProductoTemp = new ProgressDialog(this);
		pdDeleteAlmacenDevolucionProductoTemp.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdDeleteAlmacenDevolucionProductoTemp.setMessage("Borrando item autoventa ...");
		pdDeleteAlmacenDevolucionProductoTemp.setCancelable(false);
		pdDeleteAlmacenDevolucionProductoTemp.setCanceledOnTouchOutside(false);
		 
		WSDeleteAutoventaProductoTemp localWSDeleteAutoventaProductoTemp= new WSDeleteAutoventaProductoTemp();
		try
		{
			localWSDeleteAutoventaProductoTemp.execute();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSDeleteAutoventaProductoTemp: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSDeleteAutoventaProductoTemp: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSDeleteAutoventaProductoTemp.", 1);
	 	}
	}
	 
	private class WSDeleteAutoventaProductoTemp extends AsyncTask<Void, Integer, Boolean>
	 {
	    String DELETEAUTOVENTAPRODUCTOTEMP_METHOD_NAME = "DeleteAutoVentaProductoTemp";
	    String DELETEAUTOVENTAPRODUCTOTEMP_SOAP_ACTION = NAMESPACE + DELETEAUTOVENTAPRODUCTOTEMP_METHOD_NAME;
	    boolean WSDeleteAutoventaProductoTemp;
	    int resultadoInt;
	    String resultadoString;
	    SoapObject soapResultado;
	    
	    protected void onPreExecute()
	    {
	    	pdDeleteAlmacenDevolucionProductoTemp.show();
	    }
	    
	    protected Boolean doInBackground(Void... paramVarArgs)
	    {
	    	WSDeleteAutoventaProductoTemp = false;
	    	
	    	SoapObject localSoapObject = new SoapObject(NAMESPACE,DELETEAUTOVENTAPRODUCTOTEMP_METHOD_NAME);
	    	localSoapObject.addProperty("tempId", Integer.valueOf(itemBorrarServidor));
	    	SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
	    	localSoapSerializationEnvelope.dotNet = true;
	    	localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
	    	HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
	    		
	    	try
	    	{
	    		localHttpTransportSE.call(DELETEAUTOVENTAPRODUCTOTEMP_SOAP_ACTION, localSoapSerializationEnvelope);
	    		
	    		soapResultado = ((SoapObject)localSoapSerializationEnvelope.getResponse());
	    		
	    		if(soapResultado!=null)
	    		{
	    			resultadoInt = Integer.parseInt(soapResultado.getPropertyAsString("Id"));
		    	    resultadoString = String.valueOf(soapResultado.getPropertyAsString("Resultado"));	    			
	    		}
	    	        
	    	    if (resultadoString.equals("OK") && resultadoInt > 0) 
	    	    {
	    	      	WSDeleteAutoventaProductoTemp = true;
	    	    }
	    	    return true;	        
			}
	    	catch (Exception localException)
	    	{
    			WSDeleteAutoventaProductoTemp = false;
    			if(localException.getMessage() == null || localException.getMessage().isEmpty())
    			{
    		        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSDeleteAutoventaProductoTemp: vacio");
    			}
    			else
    			{
    				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSDeleteAutoventaProductoTemp: " + localException.getMessage());
    			} 
    			return true;
    		}
    	}
	    
	    protected void onPostExecute(Boolean ejecutado)
	    {
	    	if(pdDeleteAlmacenDevolucionProductoTemp.isShowing())
	    	{
	    		pdDeleteAlmacenDevolucionProductoTemp.dismiss();
	    	}
	    	
	    	if(ejecutado)
	    	{
	    		if(WSDeleteAutoventaProductoTemp) 
	    		{
	    			if(BorrarAutoventaProductoTempDispositivo()) 
	    			{
	    				theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la autoventa eliminado.", 1);
	    				CargarProductos();
    				}
	    			else
	    			{
	    				theUtilidades.MostrarMensaje(getApplicationContext(), "No se elimino el item de la autoventa del dispositivo.", 1);
	    			}
	    			
	    			ActualizarListView();
	    		}
	    		else
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(), this.resultadoString, 1);
	    			return;
	    		}
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSDeletePreventaProductoTemp.", 1);
	    	}
	    }
	 }
	 
	private boolean BorrarAutoventaProductoTempDispositivo()
	{
		boolean bool = false;
		try
		{
			bool = new DevolucionDistribuidorProductoTempBLL().BorrarDevolucionDistribuidorProductoTempPorId(itemBorrarDispositivo);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar el item del almacenDevolucionProductoTemp: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar el item del almacenDevolucionProductoTemp: " + localException.getMessage());
			}
			return false;
		}
	    
		if(bool)
		{
			return true;
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar el item de la autoventa.", 1);
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
			 descuentoProntoPagoUni = ObtenerProntoPagoJerarquia();;
		 }
		 else
		 {
			 descuentoProntoPagoUni = 0;
		 }
		
		DevolucionDistribuidorProducto localDevolucion = null;
		try
		{
			localDevolucion = new DevolucionDistribuidorProductoBLL().ObtenerDevolucionDistribuidorProductoPorProductoId(productoId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el inventario del almacenDevolucion por productoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el inventario del almacenDevolucion por productoId: " + localException.getMessage());
			}
		}
		 
		 if(localDevolucion == null)
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el inventario del producto", 1);
			 return;
		 }
		 
		precioListaId = clienteVenta.get_precioListaId();
		if(precioListaId == 0) 
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo obtener el id del precio de lista.", 1);
			return;	          
		}
		
		//Verifico si el producto tiene un canal ruta precio
		 canalRutaPrecio = null;
         
		 if(clienteVenta.get_canalRutaId() > 0)
		 {
			 try
			 {
				 canalRutaPrecio = new CanalRutaPrecioBLL().ObtenerCanalRutaPrecioPorCanalRutaIdYProductoId(clienteVenta.get_canalRutaId(),producto.get_productoId());
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
			 DespliegueControlesAdicionarVenta(true);
       	  
			 tvProducto.setText(producto.get_descripcion25());
		     tvConversion.setText(String.valueOf(producto.get_conversion()));
		     tvPrecioUnitario.setText(String.valueOf(new BigDecimal(canalRutaPrecio.get_hpb() / producto.get_conversion()).setScale(2,RoundingMode.HALF_UP)));
		     tvPrecioMayor.setText(String.valueOf(new BigDecimal(canalRutaPrecio.get_hpb()).setScale(2,RoundingMode.HALF_UP)));
		     tvDescuento.setText(String.valueOf(clienteVenta.get_descuento()));
		     tvDescuentoCanal.setText(String.valueOf(canalRutaPrecio.get_descuentoCanal()) + " %");
		     tvDescuentoAjuste.setText(String.valueOf(canalRutaPrecio.get_descuentoAjuste()) + " %");
		     tvDescuentoProntoPago.setText(String.valueOf(descuentoProntoPagoUni) + " %");
		     float precioFinal = canalRutaPrecio.get_hpb() - (canalRutaPrecio.get_hpb() * (canalRutaPrecio.get_descuentoCanal() / 100)) 
		    		 										- (canalRutaPrecio.get_hpb() * (canalRutaPrecio.get_descuentoAjuste() / 100))
		    		 										- (canalRutaPrecio.get_hpb() * (descuentoProntoPagoUni / 100));
		     tvPrecioUnitarioFinal.setText(String.valueOf(new BigDecimal(precioFinal / producto.get_conversion()).setScale(2,RoundingMode.HALF_UP)));
		     tvPrecioMayorFinal.setText(String.valueOf(new BigDecimal(precioFinal).setScale(2,RoundingMode.HALF_UP)));
		     tvInventarioUnidades.setText(String.valueOf(localDevolucion.get_cantidad()));
		     tvInventarioPaquetes.setText(String.valueOf(localDevolucion.get_cantidadPaquete()));
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
				 DespliegueControlesAdicionarVenta(true);
				 
				 tvProducto.setText(producto.get_descripcion25());
				 tvConversion.setText(String.valueOf(producto.get_conversion()));
				 tvPrecioUnitario.setText(String.valueOf(localPrecioLista.get_precio()));
				 tvPrecioMayor.setText(String.valueOf(localPrecioLista.get_precioPaquete()));
				 tvDescuento.setText(String.valueOf(clienteVenta.get_descuento()));
				 tvDescuentoCanal.setText("0 %");
			     tvDescuentoAjuste.setText("0 %");
			     tvDescuentoProntoPago.setText(String.valueOf(descuentoProntoPagoUni) + " %");
			     tvPrecioUnitarioFinal.setText(String.valueOf(localPrecioLista.get_precio()));
			     tvPrecioMayorFinal.setText(String.valueOf(localPrecioLista.get_precioPaquete()));
				 tvInventarioUnidades.setText(String.valueOf(localDevolucion.get_cantidad()));
				 tvInventarioPaquetes.setText(String.valueOf(localDevolucion.get_cantidadPaquete()));
			 }
			 else
			 {
				 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el precio de lista del producto.", 1);
				 return;
			 }
		 }
	}
	
	private float ObtenerProntoPagoJerarquia()
	{
		float descuentoJerarquia = 0;
		//1. Verificamos si el cliente tiene prontoPago directo
		ProntoPagoCliente prontoPagoCliente = null;
		try
		{
			prontoPagoCliente = new ProntoPagoClienteBLL().ObtenerProntoPagoClientePor(clienteVenta.get_clienteId());
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
				prontoPagoCanalRuta = new ProntoPagoCanalRutaBLL().ObtenerProntoPagoCanalRutaPor(clienteVenta.get_canalRutaId());
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
	
	private void AplicarBonificacion()
	{
		if(cbAplicarBonificacion.isChecked())
		{
			aplicarBonificacion = true;
		}
		else
		{
			aplicarBonificacion = false;
		}
	}
	
	private void AdicionarAutoventaTemporal()
	{
		int cantidad;
		int cantidadUnitaria=0;
		int cantidadPaquete=0;
		float precioUnitario=0;
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
		int costoId=0;
		  
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
		
		if(cantidad<=0)
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
			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo obtener el costo del producto.",1);
       	  	return;
		}
		
		if(productoCosto.get_costoId() <= 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo obtener el costo del producto, costoId <= 0.",1);
			return;
		}
        
		//Obtengo el precio de lista del producto
		precioLista = null;
		try
		{
			precioLista = new PrecioListaBLL().ObtenerPrecioListaPor(clienteVenta.get_precioListaId(),producto.get_productoId());
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

			if(clienteVenta.get_canalRutaId() > 0)
			{
				try
				{
					canalRutaPrecio = new CanalRutaPrecioBLL().ObtenerCanalRutaPrecioPorCanalRutaIdYProductoId(clienteVenta.get_canalRutaId(),producto.get_productoId());
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
		
		DevolucionDistribuidorProductoTemp localDevolucionDistribuidorProductoTemp = new DevolucionDistribuidorProductoTemp();
         
		//Verifico si el producto tiene ruta de precio
		canalRutaPrecio = null;

		if(clienteVenta.get_canalRutaId() > 0)
		{
			try
			{
				canalRutaPrecio = new CanalRutaPrecioBLL().ObtenerCanalRutaPrecioPorCanalRutaIdYProductoId(clienteVenta.get_canalRutaId(),producto.get_productoId());
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
			float descuentoProntoPago = canalRutaPrecio.get_hpb() * (descuentoProntoPagoUni / 100);
		
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
		          
				/*marginacionActual = 100 - ((productoCosto.get_cpp()*100)/precioUnitario);
		          
				if (marginacionActual < marginacionAlmacenada) 
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "La marginacion de la unidad es menor a la establecida.", 1);
					return;
				}*/
		          
				cantidadUnitaria = cantidad;
				cantidadPaquete = 0;
				monto = Float.parseFloat(new BigDecimal(cantidadUnitaria * (canalRutaPrecio.get_hpb() / producto.get_conversion())).setScale(5,RoundingMode.HALF_UP).toString());
				
				//Como en Autoventa se cargan unidades de variantes no se multiplica por la conversion a diferencia de la preventa
				cantidadSolicitadaEnUnidades = cantidadUnitaria;
		          
				if(descuentoUnitario > 0)
				{
					descuentoTotal = descuentoTotal + (monto * (descuentoUnitario/100)); 
				}
		          
				if(clienteVenta.get_descuento() > 0)
				{
		        	  descuentoTotal = descuentoTotal + (monto * (clienteVenta.get_descuento()/100)); 
				}
		        	  
				montoFinal = monto - descuentoTotal - (descuentoCanal/producto.get_conversion())*cantidadSolicitadaEnUnidades 
													- (descuentoAjuste/producto.get_conversion())* cantidadSolicitadaEnUnidades
													- (descuentoProntoPago / producto.get_conversion() * cantidadSolicitadaEnUnidades);
				
				costoId = productoCosto.get_costoId();
			}
			
			if(rbtMayor.isChecked())
			{
				precioMayorFinal = 0;
				try
				{
					precioMayorFinal = canalRutaPrecio.get_hpb() - descuentoCanal - descuentoAjuste - descuentoProntoPago;
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
				monto = Float.parseFloat(new BigDecimal(cantidadPaquete * canalRutaPrecio.get_hpb()).setScale(5,RoundingMode.HALF_UP).toString());
				cantidadSolicitadaEnUnidades = cantidadPaquete * producto.get_conversion();

				if(descuentoPaquete > 0)
				{
					descuentoTotal = descuentoTotal + (monto  * (descuentoPaquete/100)); 
				}

				if(clienteVenta.get_descuento() > 0)
				{
					descuentoTotal = descuentoTotal + (monto * (clienteVenta.get_descuento()/100)); 
				}

				float dsctoCanal = cantidadPaquete * descuentoCanal;
				float dsctoAjuste = cantidadPaquete * descuentoAjuste;
				float dctoProntoPago = cantidadPaquete * descuentoProntoPago;

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
		      
			localDevolucionDistribuidorProductoTemp.set_tempId(0);
			localDevolucionDistribuidorProductoTemp.set_clienteId(clienteVenta.get_clienteId());
			localDevolucionDistribuidorProductoTemp.set_productoId(producto.get_productoId());
			localDevolucionDistribuidorProductoTemp.set_cantidad(cantidadUnitaria);
			localDevolucionDistribuidorProductoTemp.set_cantidadPaquete(cantidadPaquete);
			localDevolucionDistribuidorProductoTemp.set_monto(monto);
			localDevolucionDistribuidorProductoTemp.set_descuento(descuentoTotal);
			localDevolucionDistribuidorProductoTemp.set_montoFinal(montoFinal);
			localDevolucionDistribuidorProductoTemp.set_empleadoId(loginEmpleado.get_empleadoId());
			localDevolucionDistribuidorProductoTemp.set_promocionId(0);
			localDevolucionDistribuidorProductoTemp.set_costo(0);
			localDevolucionDistribuidorProductoTemp.set_costoId(costoId);
			localDevolucionDistribuidorProductoTemp.set_noAutoventa(noAutoventa);
			
			if(precioLista == null)
			{
				localDevolucionDistribuidorProductoTemp.set_precioId(0);
			}
			else
			{
				localDevolucionDistribuidorProductoTemp.set_precioId(precioLista.get_precioId());
			}

			float dsctoCanal = canalRutaPrecio.get_hpb() * (canalRutaPrecio.get_descuentoCanal() / 100);
			if(cantidadUnitaria > 0)
			{
				localDevolucionDistribuidorProductoTemp.set_descuentoCanal((dsctoCanal * cantidadUnitaria) / producto.get_conversion());
			}
			else
			{
				localDevolucionDistribuidorProductoTemp.set_descuentoCanal(dsctoCanal * cantidadPaquete);
			}

			float dsctoAjuste = canalRutaPrecio.get_hpb() * (canalRutaPrecio.get_descuentoAjuste() / 100);
			if(cantidadPaquete > 0)
			{
				localDevolucionDistribuidorProductoTemp.set_descuentoAjuste(dsctoAjuste * cantidadPaquete);
			}
			else
			{
				localDevolucionDistribuidorProductoTemp.set_descuentoAjuste((dsctoAjuste * cantidadUnitaria) / producto.get_conversion());
			}

			float dctoProntoPago = canalRutaPrecio.get_hpb() * (descuentoProntoPagoUni / 100);
			if(cantidadUnitaria > 0)
			{
				localDevolucionDistribuidorProductoTemp.set_descuentoProntoPago((dctoProntoPago * cantidadUnitaria) / producto.get_conversion());
			}
			else
			{
				localDevolucionDistribuidorProductoTemp.set_descuentoProntoPago(dctoProntoPago * cantidadPaquete);
			}

			localDevolucionDistribuidorProductoTemp.set_canalPrecioRutaId(canalRutaPrecio.get_canalPrecioRutaId());
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

				if(clienteVenta.get_descuento() > 0)
				{
					descuentoTotal = descuentoTotal + (monto * (clienteVenta.get_descuento()/100)); 
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
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontro el precio mayor.", 1);
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
		          
				//if (marginacionActual < marginacionAlmacenadaPaquete)
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
					descuentoTotal = descuentoTotal + (monto * (descuentoPaquete/100)); 
				}
		          
				if(clienteVenta.get_descuento() > 0)
				{
					descuentoTotal = descuentoTotal + (monto * (clienteVenta.get_descuento()/100)); 
				}
		        	  
				dsctoProntoPago = monto * (descuentoProntoPagoUni / 100);
				
				montoFinal = monto - descuentoPaquete - dsctoProntoPago;
	          
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
	      
			localDevolucionDistribuidorProductoTemp.set_tempId(0);
			localDevolucionDistribuidorProductoTemp.set_clienteId(clienteVenta.get_clienteId());
			localDevolucionDistribuidorProductoTemp.set_productoId(producto.get_productoId());
			localDevolucionDistribuidorProductoTemp.set_cantidad(cantidadUnitaria);
			localDevolucionDistribuidorProductoTemp.set_cantidadPaquete(cantidadPaquete);
			localDevolucionDistribuidorProductoTemp.set_monto(monto);
			localDevolucionDistribuidorProductoTemp.set_descuento(descuentoTotal);
			localDevolucionDistribuidorProductoTemp.set_montoFinal(montoFinal);
			localDevolucionDistribuidorProductoTemp.set_empleadoId(loginEmpleado.get_empleadoId());
			localDevolucionDistribuidorProductoTemp.set_promocionId(0);
			localDevolucionDistribuidorProductoTemp.set_costo(0);
			localDevolucionDistribuidorProductoTemp.set_costoId(costoId);
			localDevolucionDistribuidorProductoTemp.set_noAutoventa(noAutoventa);
			localDevolucionDistribuidorProductoTemp.set_precioId(precioLista.get_precioId());
			localDevolucionDistribuidorProductoTemp.set_descuentoCanal(0);
			localDevolucionDistribuidorProductoTemp.set_descuentoAjuste(0);
			localDevolucionDistribuidorProductoTemp.set_descuentoProntoPago(dsctoProntoPago);
			localDevolucionDistribuidorProductoTemp.set_canalPrecioRutaId(0);
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
	      
		if (ValidarExistenciasDevolucionDistribuidorProductoDispositivo(productoIdAValidar,
	        		producto.get_conversion(),cantidadSolicitadaEnUnidades))
		{
			if(parametroGeneral.get_montoCi() > 0)
			{
				if(ObtenerAutoventaProductoTempMontoFinalAlmacenadoActual()+localDevolucionDistribuidorProductoTemp.get_montoFinal() >= parametroGeneral.get_montoCi())
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
				if(ObtenerAutoventaProductoTempMontoFinalAlmacenadoActual()+localDevolucionDistribuidorProductoTemp.get_montoFinal() >= parametroGeneral.get_montoBancarizacion())
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se puede ingresar el producto, ya que el monto excede el definido por el de Bancarizacion.", 1);
					return;
				}
			}
			
			if(parametroGeneral.get_tipoImpresionFactura().equals("Medio Oficio"))
			{
				if(listadoDevolucionDistribuidorProductoTemp == null || listadoDevolucionDistribuidorProductoTemp.size() <= 14)
				{
					if(InsertarDevolucionDistribuidorProductoTempDispositivo(localDevolucionDistribuidorProductoTemp))
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la autoventa insertado en el dispositivo.", 1);
						CargarProductos();
						DespliegueControlesAdicionarVenta(false);
						ActualizarListView();
		    		}
					else 
					{
						theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el producto de la autoventa en el dispositivo.",1);
						return;
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"Se alcanzo el tope de items para la factura medio oficio, confirme la autoventa e ingrese una nueva.",1);
					return;
				}
			}
			else if(parametroGeneral.get_tipoImpresionFactura().equals("Oficio"))
			{
				if(listadoDevolucionDistribuidorProductoTemp == null || listadoDevolucionDistribuidorProductoTemp.size() <= 34)
				{
					if(InsertarDevolucionDistribuidorProductoTempDispositivo(localDevolucionDistribuidorProductoTemp))
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la autoventa insertado en el dispositivo.", 1);
						CargarProductos();
						DespliegueControlesAdicionarVenta(false);
						ActualizarListView();
		    		}
					else 
					{
						theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el producto de la autoventa en el dispositivo.",1);
						return;
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"Se alcanzo el tope de items para la factura oficio, confirme la autoventa e ingrese una nueva.",1);
					return;
				}
			}
			else
	    	{
				if(InsertarDevolucionDistribuidorProductoTempDispositivo(localDevolucionDistribuidorProductoTemp))
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la autoventa insertado en el dispositivo.", 1);
					CargarProductos();
					DespliegueControlesAdicionarVenta(false);
					ActualizarListView();
	    		}
				else 
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el producto de la autoventa en el dispositivo.",1);
					return;
				}
	    	}
		}
		else
		{
			int saldoUnidades = ObtenerExistenciaProductoEnDevolucionDistribuidorProductoPor(productoIdAValidar,producto.get_conversion());
			theUtilidades.MostrarMensaje(getApplicationContext(), "No existe producto en el almacen del dispositivo."
	    			  							+ "Cantidad actual: " + String.valueOf(saldoUnidades) + " unidades.", 1);
			return;
		}
	}
	
	private float ObtenerAutoventaProductoTempMontoFinalAlmacenadoActual()
	{
		float montoFinal = 0;
		ArrayList<DevolucionDistribuidorProductoTemp> listadoAutoventaTemporal = null;
		try
		{
			listadoAutoventaTemporal = new DevolucionDistribuidorProductoTempBLL().ObtenerDevolucionDistribuidorProductoTempPorClienteIdNoAutoventa(clienteId, noAutoventa);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
  		  	{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtner las preventasTemporales por clienteId y noAutoventa: vacio");
  		  	}
  		  	else
  		  	{
  		  		myLog.InsertarLog("App",this.toString(),1,"Error al obtner las preventasTemporales por clienteId y noAutoventa" + localException.getMessage());
  		  	}
		}
		
		if(listadoAutoventaTemporal != null)
		{
			for(DevolucionDistribuidorProductoTemp item : listadoAutoventaTemporal)
			{
				montoFinal = montoFinal + item.get_montoFinal();
			}
		}
		
		return montoFinal;		
	}
	
	private void InsertarAutoventaProductoTemp(VentaProducto localVentaProducto)
	{
	    pdInsertarAutoventaProductoTemp = new ProgressDialog(this);
	    pdInsertarAutoventaProductoTemp.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdInsertarAutoventaProductoTemp.setMessage("Insertando producto de la autoventa ...");
	    pdInsertarAutoventaProductoTemp.setCancelable(false);
	    pdInsertarAutoventaProductoTemp.setCanceledOnTouchOutside(false);
	    
	    WSInsertarAutoventaProductoTemp localWSInsertarAutoventaProductoTemp = new WSInsertarAutoventaProductoTemp(localVentaProducto);
	    try
	    {
	    	localWSInsertarAutoventaProductoTemp.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarAutoventaProductoTemp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarAutoventaProductoTemp: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertarAutoventaProductoTemp", 1);
	    }
	}
	 
	public class WSInsertarAutoventaProductoTemp extends AsyncTask<Void, Integer, Boolean>
	{
		String AUTOVENTAPRODUCTOTEMP_METHOD_NAME = "InsertAutoVentaProductoTemp";
		String AUTOVENTAPRODUCTOTEMP_SOAP_ACTION = NAMESPACE + AUTOVENTAPRODUCTOTEMP_METHOD_NAME;
		 
		 private VentaProducto _ventaProducto;
		 
		 boolean WSinsertarAutoventaProductoTemp;
		 int resultadoInt;
		 String resultadoString;
		 SoapObject localSoapObjects;
	    
	    public WSInsertarAutoventaProductoTemp(VentaProducto paramVentaProducto)
	    {
	    	_ventaProducto = paramVentaProducto;
	    }
	    
	    protected void onPreExecute()
	    {
	    	pdInsertarAutoventaProductoTemp.show();
	    }
	    
	    protected Boolean doInBackground(Void... paramVarArgs)
	    {
	    	WSinsertarAutoventaProductoTemp = false;
	      
	    	SoapObject localSoapObject = new SoapObject(NAMESPACE, this.AUTOVENTAPRODUCTOTEMP_METHOD_NAME);
	    	localSoapObject.addProperty("productoId", _ventaProducto.get_productoId());
	    	localSoapObject.addProperty("promocionId", _ventaProducto.get_promocionId());
	    	localSoapObject.addProperty("cantidad", _ventaProducto.get_cantidad());
	    	localSoapObject.addProperty("cantidadPaquete", _ventaProducto.get_cantidadPaquete());
	    	localSoapObject.addProperty("monto", String.valueOf(_ventaProducto.get_monto()));
	    	localSoapObject.addProperty("descuento", String.valueOf(_ventaProducto.get_descuento()));
	    	localSoapObject.addProperty("montoFinal", String.valueOf(_ventaProducto.get_montoFinal()));
	    	localSoapObject.addProperty("clienteId", clienteId);
	    	localSoapObject.addProperty("empleadoId", loginEmpleado.get_empleadoId());
	    	localSoapObject.addProperty("dia", loginEmpleado.get_dia());
	    	localSoapObject.addProperty("mes", loginEmpleado.get_mes());
	    	localSoapObject.addProperty("anio", loginEmpleado.get_anio());
	    	localSoapObject.addProperty("costoId", String.valueOf(_ventaProducto.get_costoId()));
	    	localSoapObject.addProperty("precioId", String.valueOf(_ventaProducto.get_precioId()));
	    	localSoapObject.addProperty("nroAutoventa", String.valueOf(noAutoventa));
	    	localSoapObject.addProperty("descuentoCanal", String.valueOf(_ventaProducto.get_descuentoCanal()));
	    	localSoapObject.addProperty("descuentoAjuste", String.valueOf(_ventaProducto.get_descuentoAjuste()));
	    	localSoapObject.addProperty("canalRutaPrecioId", String.valueOf(_ventaProducto.get_canalPrecioRutaId()));
	    	localSoapObject.addProperty("descuentoProntoPago",String.valueOf(_ventaProducto.get_descuentoProntoPago()));
       
	    	SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
	    	localSoapSerializationEnvelope.dotNet = true;
	    	localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
	    	HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
	    	try
	    	{
	    		localHttpTransportSE.call(AUTOVENTAPRODUCTOTEMP_SOAP_ACTION, localSoapSerializationEnvelope);
   			
	    		localSoapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
	    		if(localSoapObjects!=null)
	    		{
	    			resultadoInt = Integer.parseInt(localSoapObjects.getPropertyAsString("Id"));
	    			resultadoString = localSoapObjects.getPropertyAsString("Resultado");    				
	    		}
   			
	    		if(resultadoString.equals("OK") && resultadoInt > 0)
	    		{
	    			WSinsertarAutoventaProductoTemp = true;
	    		}
	    		return true;
	    	}
	    	catch (Exception localException)
	    	{
	    		WSinsertarAutoventaProductoTemp = false;
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
	    	if(pdInsertarAutoventaProductoTemp.isShowing())
	    	{
	    		pdInsertarAutoventaProductoTemp.dismiss();
	    	}
	    	
	    	if(ejecutado) 
	    	{
	    		if(WSinsertarAutoventaProductoTemp || (resultadoString != null && resultadoString.equals("Autoventa Producto Temp Repetido") && resultadoInt <= 0)) 
	    		{
	    			long l = 0;
	    			try
	    			{
	    				l = new VentaProductoBLL().ModificarVentaProducto(_ventaProducto.get_id(), true);
					}
					catch (Exception localException)
	    			{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del producto de la venta: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del producto de la venta: " + localException.getMessage());
						} 
					}
						
	    			if(l==0)
	    			{
	    				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del producto de la venta.", 1);
	    				return;
					}
	    			else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "Producto de la venta sincronizado.", 1);
							
						if(ObtenerVentasProductoNoSincronizadas(_ventaProducto.get_ventaId()))
						{
							SincronizarVentaProducto();
						}
						else
						{
							InsertarAutoventa();
						}
					}
	    		}
	    		else
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo ejecutar el webservice WSInsertAutoVentaProductoTemp.", 1);
	    			MostrarPantallaMenuDistribuidor();
	    		}
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "El WebService WSInsertAutoventaProductoTemp no se ejecuto correctamente.", 1);
	    		return;
	    	}
	    }
	}
	
	private boolean ValidarExistenciasDevolucionDistribuidorProductoDispositivo(int productoId, int productoConversion, int cantidadSolicitadaEnUnidades)
	{
		DevolucionDistribuidorProducto localDevolucionDistribuidorProducto = null;
		try
		{
			localDevolucionDistribuidorProducto = new DevolucionDistribuidorProductoBLL().ObtenerExistenciaDevolucionDistribuidorProducto(productoId, productoConversion, cantidadSolicitadaEnUnidades);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el producto de la devolucionDistribuidorProducto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el producto de la devolucionDistribuidorProducto: " + localException.getMessage());
			}
			return false;
		}
	    if (localDevolucionDistribuidorProducto == null)
	    {
	    	return false;
	    }
	    else
	    {
	    	return true;
	    }
	}
	
	private int ObtenerExistenciaProductoEnDevolucionDistribuidorProductoPor(int productoId, int productoConversion)
	 {
		 int saldoEnUnidades = 0;
		 try
		 {
			 saldoEnUnidades = new DevolucionDistribuidorProductoBLL().ObtenerDevolucionDistribuidorProductoSaldoPorProductoId(productoId, productoConversion);
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
	
	private boolean InsertarDevolucionDistribuidorProductoTempDispositivo(DevolucionDistribuidorProductoTemp paramDevolucionDistribuidorProductoTemp)
	{
		 long l = 0;
		 try
		 {
			 l = new DevolucionDistribuidorProductoTempBLL().InsertarDevolucionDistribuidorProductoTemp(paramDevolucionDistribuidorProductoTemp.get_tempId(),
					 paramDevolucionDistribuidorProductoTemp.get_productoId(),0,  
					 paramDevolucionDistribuidorProductoTemp.get_cantidad(), paramDevolucionDistribuidorProductoTemp.get_cantidadPaquete(),
					 paramDevolucionDistribuidorProductoTemp.get_monto(), paramDevolucionDistribuidorProductoTemp.get_descuento(),
					 paramDevolucionDistribuidorProductoTemp.get_montoFinal(),paramDevolucionDistribuidorProductoTemp.get_empleadoId(),
					 paramDevolucionDistribuidorProductoTemp.get_clienteId(),paramDevolucionDistribuidorProductoTemp.get_costo(),
					 paramDevolucionDistribuidorProductoTemp.get_costoId(),paramDevolucionDistribuidorProductoTemp.get_precioId(),
					 paramDevolucionDistribuidorProductoTemp.get_noAutoventa(), paramDevolucionDistribuidorProductoTemp.get_descuentoCanal(),
					 paramDevolucionDistribuidorProductoTemp.get_descuentoAjuste(), paramDevolucionDistribuidorProductoTemp.get_canalPrecioRutaId(),
					 paramDevolucionDistribuidorProductoTemp.get_descuentoProntoPago());
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar la devolucionDistribuidorProductoTemp en el dispositivo: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la devolucionDistribuidorProductoTemp en el dispositivo: " + localException.getMessage());
	    	}
	    	return false;
   	}
	    	
	    if(l==0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el producto temporal de la autoventa en el dispositivo.", 1);
	  	     return false;		
	    }
	    else
	    {
	    	return true;
	    }
	 }
	
	private void ConfirmarAutoventa()
	{
		btnConfirmarVenta.setVisibility(View.INVISIBLE);
		
		if(ValidarMontoNit())
		{	
			if(ValidarMontoCondicionTributaria(montoTributarioServer))
			{
				if(PrepararAutoventaParaInsercion())
				{
					if(InsertarAutoventaCompletaDispositivo())
					{
						if(ModificarEstadoAtencionClienteVenta())
						{
							if(ObtenerVentasProductoNoSincronizadas((int)ventaIdDispositivo))
							{
								SincronizarVentaProducto();
							}
							else
							{
								theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los productos no sincronizados de la venta.", 1);
								return;
							}
						}
						else
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el estado de atencion del cliente en peventa.", 1);
							return;
						}
					}	
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "La venta no se genero correctamente.", 1);
					return; 
				}
			}
		}
		else
		{
			dialog = new Dialog(Distribuidorautoventa.this);
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
				if(etNombreFactura.getText().toString().equals("S/N") || etNombreFactura.getText().toString().isEmpty() 
						|| etNit.getText().toString().isEmpty() || etNit.getText().toString().equals("0"))
				{
					validado = false;
					
					ClienteVenta theCliente = null;
					try
					{
						theCliente = new ClienteVentaBLL().ObtenerClienteVentaPor(clienteId);
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
					
					if(theCliente.get_nombreCompleto().equals("anyType{}") || theCliente.get_nombreCompleto().isEmpty())
					{
						etNombreFactura.setText("");
					}
					else
					{
						etNombreFactura.setText(theCliente.get_nombreCompleto());
					}
					
					if(theCliente.get_nit().equals("anyType{}") || theCliente.get_nit().isEmpty())
					{
						etNit.setText("");
					}
					else
					{
						etNit.setText(theCliente.get_nit());
					}
					
					btnConfirmarVenta.setVisibility(View.VISIBLE);
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
		if(listadoDevolucionDistribuidorProductoTemp != null && listadoDevolucionDistribuidorProductoTemp.size()>0)
		{
			for(DevolucionDistribuidorProductoTemp item : listadoDevolucionDistribuidorProductoTemp)
	    	{
	    		montoFinal += item.get_montoFinal();
	    	}
		}
		return montoFinal;
	}
	
	private boolean ObtenerVentasProductoNoSincronizadas(int ventaId)
	{
		listadoVentaProductoNoSincronizado = null;
	    try
	    {
	    	listadoVentaProductoNoSincronizado = new VentaProductoBLL().ObtenerVentasProductoNoSincronizados(ventaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta no sincronizados por preventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta no sincronizados por preventaId: " + localException.getMessage());
	    	}
	    }
	    
	    if (listadoVentaProductoNoSincronizado == null) 
	    {
 	        return false;
	    }  
	    else
	    {
	    	return true;
	    }
	}
	
	private void SincronizarVentaProducto() 
	{
		if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
		{
			if(listadoVentaProductoNoSincronizado != null && listadoVentaProductoNoSincronizado.size()>0)
			{
				InsertarAutoventaProductoTemp(listadoVentaProductoNoSincronizado.get(0));
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No existen productos de la autoventa que sincronizar.", 1);
				return;
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No esta conectado a internet, intentelo mas tarde.", 1);
			MostrarPantallaTipoImpresion();
		}
	}
	
	private boolean PrepararAutoventaParaInsercion()
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
	    
	    try
	    {
	        localUbicacion = new Ubicacion(this);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ubicacion del distribuidor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ubicacion del distribuidor: " + localException.getMessage());
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
	    
	    if(listadoDevolucionDistribuidorProductoTemp.size() > 0)
	    {
	    	for(DevolucionDistribuidorProductoTemp item : listadoDevolucionDistribuidorProductoTemp)
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
	    	
	    	venta = new Venta(0, 0, fecha.get_dia(), fecha.get_mes(), fecha.get_anio(), clienteId, loginEmpleado.get_empleadoId(),
	    			monto, descuento, montoFinal, 0, tipoPagoId, latitud, longitud, false, false, fecha.get_hora(), fecha.get_minuto(),
	    			etObservacion.getText().toString(), aplicarBonificacion, dosificacionId, tipoNit, descuentoCanal, descuentoAjuste,
	    			prontoPagoId, descuentoProntoPagoUni, descuentoObjetivo,
	    			1, "", true, false);
	    	return true;
	    }
	    else
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No existe items en la venta.", 1);
		    return false;
	    }
	 }
	
	private void InsertarAutoventa()
	{
		 pdInsertarAutoventa = new ProgressDialog(this);
		 pdInsertarAutoventa.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		 pdInsertarAutoventa.setMessage("Insertando autoventa ...");
		 pdInsertarAutoventa.setCancelable(false);
		 pdInsertarAutoventa.setCanceledOnTouchOutside(false);
		 	 
		 WSInsertarAutoventa localWSInsertarAutoventa = new WSInsertarAutoventa();
		 try
		 {
			 localWSInsertarAutoventa.execute();
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertAutoventa: vacio");
			 }
	 		 else
		 	 {
	 			 myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertAutoventa: " + localException.getMessage());
		 	 }
			 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertAutoventa.", 1);
		 }
	 }
	 
	private class WSInsertarAutoventa extends AsyncTask<Void, Integer, Boolean>
	{
		String INSERTAUTOVENTA_METHOD_NAME = "InsertAutoVenta";
		String INSERTAUTOVENTA_SOAP_ACTION = NAMESPACE + INSERTAUTOVENTA_METHOD_NAME;
		 
		boolean WSInsertarAutoventa = false;
		int intResultado;
		SoapObject soapResultado;
		String stringResultado;
		 
		protected void onPreExecute()
		{
			pdInsertarAutoventa.show();
		}
	    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			 WSInsertarAutoventa = false;
			 
			 SoapObject localSoapObject = new SoapObject(NAMESPACE,INSERTAUTOVENTA_METHOD_NAME);
			 localSoapObject.addProperty("clienteId", Integer.valueOf(venta.get_clienteId()));
			 localSoapObject.addProperty("distribuidorId", Integer.valueOf(venta.get_distribuidorId()));
			 localSoapObject.addProperty("vendedorId", Integer.valueOf(venta.get_distribuidorId()));
			 localSoapObject.addProperty("tipoPagoId", Integer.valueOf(venta.get_tipoPagoId()));
			 localSoapObject.addProperty("monto", String.valueOf(venta.get_monto()));
			 localSoapObject.addProperty("descuento", String.valueOf(venta.get_descuento()));
			 localSoapObject.addProperty("montoFinal", String.valueOf(venta.get_montoFinal()));
			 localSoapObject.addProperty("latitud", String.valueOf(venta.get_latitud()));
			 localSoapObject.addProperty("longitud", String.valueOf(venta.get_longitud()));
			 localSoapObject.addProperty("numeroItems", Integer.valueOf(listadoDevolucionDistribuidorProductoTemp.size()));
			 localSoapObject.addProperty("anio", Integer.valueOf(venta.get_anio()));
			 localSoapObject.addProperty("mes", Integer.valueOf(venta.get_mes()));
			 localSoapObject.addProperty("dia", Integer.valueOf(venta.get_dia()));
			 localSoapObject.addProperty("hora", Integer.valueOf(venta.get_hora()));
			 localSoapObject.addProperty("minuto", Integer.valueOf(venta.get_minuto()));
			 localSoapObject.addProperty("nombreFactura", String.valueOf(factura));
			 localSoapObject.addProperty("nit", String.valueOf(nit));
			 localSoapObject.addProperty("nitNuevo",String.valueOf(nitNuevo));
			 localSoapObject.addProperty("almacenId",loginEmpleado.get_almacenId());
			 localSoapObject.addProperty("rutaId",(clienteVenta.get_rutaId()));
			 localSoapObject.addProperty("diaId",(clienteVenta.get_diaId()));
			 localSoapObject.addProperty("observacion", String.valueOf(venta.get_observacion()));
			 localSoapObject.addProperty("aplicaBonificacion", String.valueOf(venta.is_aplicarBonificacion()));
			 localSoapObject.addProperty("nroAutoventa", Integer.valueOf(noAutoventa));
			 localSoapObject.addProperty("dosificacionId", Integer.valueOf(venta.get_dosificacionId()));
			 localSoapObject.addProperty("tipoNit", String.valueOf(venta.get_tipoNit()));
			 localSoapObject.addProperty("prontoPagoId", String.valueOf(venta.get_prontoPagoId()));
			 localSoapObject.addProperty("descuentoCanal", String.valueOf(venta.get_descuentoCanal()));
			 localSoapObject.addProperty("descuentoAjuste", String.valueOf(venta.get_descuentoAjuste()));
			 localSoapObject.addProperty("descuentoProntoPago", String.valueOf(venta.get_descuentoProntoPago()));
			 localSoapObject.addProperty("descuentoObjetivo", String.valueOf(venta.get_descuentoObjetivo()));
			 localSoapObject.addProperty("formaPagoId", venta.get_formaPagoId());
			 localSoapObject.addProperty("codTransferencia", venta.get_codTransferencia());
			 localSoapObject.addProperty("fromApk",String.valueOf(venta.is_fromApk()));
			 localSoapObject.addProperty("fromShopping",String.valueOf(venta.is_fromShopp()));
			
			 SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			 localSoapSerializationEnvelope.dotNet = true;
			 localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			 HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			 try
			 {
				 localHttpTransportSE.call(INSERTAUTOVENTA_SOAP_ACTION, localSoapSerializationEnvelope);
				
				 soapResultado = ((SoapObject)localSoapSerializationEnvelope.getResponse());
				 if(soapResultado!=null)
				 {
					 intResultado = Integer.parseInt(soapResultado.getPropertyAsString("Id"));
					 stringResultado = soapResultado.getPropertyAsString("Resultado");
				 }
				
				 if(stringResultado.equals("OK") && intResultado > 0)
				 {
					 WSInsertarAutoventa = true;
				 }
				 return true;
			 }
			 catch (Exception localException)
			 {
				 WSInsertarAutoventa = false;
				 if(localException.getMessage() == null || localException.getMessage().isEmpty())
				 {
					 myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertAutoventa: vacio");
				 }
				 else
				 {
					 myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertAutoventa: " + localException.getMessage());
				 }
				 return true;
			 }
	 	}
		
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdInsertarAutoventa.isShowing())
			{
				pdInsertarAutoventa.dismiss();
			}
			 
			if(ejecutado)
			{
				if(WSInsertarAutoventa || (stringResultado != null && stringResultado.equals("Autoventa Repetida") && intResultado <= 0)) 
				{
					long l = 0;
					try
					{
						l = new VentaBLL().ModificarVentaIdServer((int)ventaIdDispositivo, intResultado);
					}
					catch (Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar ventaIdServer por ventaRowId: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar ventaIdServer por ventaRowId: " + localException.getMessage());
						} 
					}
							
					if(l==0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la ventaId del servicor.", 1);
						return;
					}
					
					l = 0;
					try
					{
						l = new VentaBLL().ModificarVentaSincronizacion((int)ventaIdDispositivo, true);
					}
					catch (Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la venta por rowId: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la venta por rowId: " + localException.getMessage());
						} 
					}
							
					if(l==0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion de la venta.", 1);
						return;
					}
					 
					l=0;
					try
					{
						l = new SincronizacionVentaBLL().ModificarSincronizacionAutoventaSincronizacionPorVentaId((int)ventaIdDispositivo, true);
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la sincronizacionAutoventa por ventaId: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la SincronizacionAutoventa por ventaId: " + localException.getMessage());
						} 
					}
					 
					if(l==0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion de la sincronizacion venta por VentaId.", 1);
						return;
					}

					theUtilidades.MostrarMensaje(getApplicationContext(), "Autoventa insertada.", 1);
					MostrarPantallaTipoImpresion();
				 }
				 else
				 {
					 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertAutoVenta.", 1);
					 MostrarPantallaTipoImpresion();
				 }
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertAutoventa.", 1);
				return;
			}
		}
	}
	
	private boolean InsertarAutoventaCompletaDispositivo()
	{
		if(ActualizarClientePreventaMontoCredito())
		{
			if(InsertarAutoventaDispositivo())
			{
				if(InsertarAutoventaProductoDispositivo())
				{
					if(BorrarDevolucionDistribuidorProductoTempDispositivo())
					{	
						return true;
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se elimino los items de la autoventa temporal en el dispositivo.", 1);
						return false;
					}
		 		}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se inserto los items de la autoventa en el dispositivo.", 1);
					return false;
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se inserto la autoventa en el dispositivo.", 1);
				return false;
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se actualizo el monto actual del credito del cliente.", 1);
			return false;
		}
	}
	
	boolean ActualizarClientePreventaMontoCredito()
	{
		if(venderCredito == true)
		{
			long a = 0;
			
			try
			{
				a = new ClienteVentaBLL().ModificarClienteVentaMontoCredito(clienteVenta.get_clienteId(), 
						clienteVenta.get_montoPendienteCredito() + venta.get_montoFinal());
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
	
	private boolean InsertarAutoventaDispositivo()
	{
		ventaIdDispositivo = 0;
		Fecha fecha = theUtilidades.ObtenerFecha();
		
		try
		{
			ventaIdDispositivo = ((int) new VentaBLL().InsertarVenta(0,fecha.get_dia(),fecha.get_mes(),fecha.get_anio(),
					clienteId,loginEmpleado.get_empleadoId(),venta.get_monto(),venta.get_descuento(),venta.get_montoFinal(),
					0,venta.get_tipoPagoId(),venta.get_latitud(),venta.get_longitud(),false,false,fecha.get_hora(),
					fecha.get_minuto(),venta.get_observacion(),venta.is_aplicarBonificacion(),venta.get_dosificacionId(),
					venta.get_tipoNit(),venta.get_descuentoCanal(),venta.get_descuentoAjuste(), venta.get_prontoPagoId(),
					venta.get_descuentoProntoPago(), venta.get_descuentoObjetivo(), venta.get_formaPagoId(), 
					venta.get_codTransferencia(), venta.is_fromApk(), venta.is_fromShopp()));
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty()) 
			{
				myLog.InsertarLog("App", toString(), 1, "Error al insertar la autoventa en el dispositivo: vacio");
			} 
			else 
			{
				myLog.InsertarLog("App", toString(), 1, "Error al insertar la autoventa en el dispositivo: " + localException.getMessage());
			}
		}
		 
		if(ventaIdDispositivo <= 0)
		{
			return false;
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "Autoventa insertada en el dispositivo.", 1);
			return true;
		}
	}
	
	private boolean InsertarAutoventaProductoDispositivo()
	{
		for(DevolucionDistribuidorProductoTemp item : listadoDevolucionDistribuidorProductoTemp)
		{
			long l = 0;
			
			try
			{
				l = new VentaProductoBLL().InsertarVentaProducto((int)ventaIdDispositivo,
											item.get_productoId(),item.get_promocionId(),item.get_cantidad(),
						 					item.get_cantidadPaquete(),item.get_monto(),item.get_descuento(),
						 					item.get_montoFinal(),0,false,item.get_costo(),item.get_costoId(),
						 					item.get_precioId(), item.get_descuentoCanal(), item.get_descuentoAjuste(),
						 					item.get_canalPrecioRutaId(), item.get_descuentoProntoPago());
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar la autoventaProducto en el dispositivo: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar la autoventaProducto en el dispositivo: " + localException.getMessage());
				} 
			}
			 
			if(l == 0)
			{
				return false; 
			}
			 
			//Actualizamos la devolucionDistribuidorProducto
			int cantidadExistenteEnUnidades = 0;
			int cantidadSolicitadaEnUnidades = 0;
			 
			if(item.get_productoId()>0)
			{
				producto = null;
				try
				{
					producto = new ProductoBLL().ObtenerProductoPor(item.get_productoId());
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de los producto: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar los detalles de los producto: " + localException.getMessage());
					} 
				}
				
				if(producto == null)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del producto", 1);
					return false;
				}
 			
				DevolucionDistribuidorProducto localDevolucionDistribuidorProducto = null;
				try
				{
					localDevolucionDistribuidorProducto = new DevolucionDistribuidorProductoBLL().ObtenerDevolucionDistribuidorProductoPorProductoId(
							 producto.get_productoId());
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidorProducto por productoId: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidorProducto por productoId: " + localException.getMessage());
					} 
				}
				 
				if(localDevolucionDistribuidorProducto == null)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el producto del almacen autoventa.", 1);
					return false;
				}
				else
				{
					if(producto.get_productoReferenciaId() > 0)
					{
						cantidadExistenteEnUnidades = localDevolucionDistribuidorProducto.get_cantidadPaquete() + localDevolucionDistribuidorProducto.get_cantidad();
						cantidadSolicitadaEnUnidades = item.get_cantidadPaquete() + item.get_cantidad();
					}
					else
					{
						cantidadExistenteEnUnidades = (localDevolucionDistribuidorProducto.get_cantidadPaquete() * producto.get_conversion()) +
		 						localDevolucionDistribuidorProducto.get_cantidad();
						cantidadSolicitadaEnUnidades = (item.get_cantidadPaquete() * producto.get_conversion()) + item.get_cantidad();
					}
					
					if(cantidadSolicitadaEnUnidades <= cantidadExistenteEnUnidades)
					{
						int cantidadSaldoEnUnidades = 0;
						int cantidadPaqueteConsolidada = 0;
						int cantidadConsolidada = 0;
						
						if(producto.get_productoReferenciaId() > 0)
						{
							cantidadSaldoEnUnidades = cantidadExistenteEnUnidades - cantidadSolicitadaEnUnidades;
							//cantidadPaqueteConsolidada = cantidadSaldoEnUnidades;
							cantidadConsolidada = cantidadSaldoEnUnidades;
						}
						else
						{
							cantidadSaldoEnUnidades = cantidadExistenteEnUnidades - cantidadSolicitadaEnUnidades;
							cantidadPaqueteConsolidada = cantidadSaldoEnUnidades / producto.get_conversion();
							cantidadConsolidada = cantidadSaldoEnUnidades % producto.get_conversion();
						}
						  
						long update = 0;
						try
						{
							update = new DevolucionDistribuidorProductoBLL().ModificarCantidadesDevolucionDistribuidorProducto(
									 producto.get_productoId(),cantidadConsolidada,cantidadPaqueteConsolidada);
						}
						catch(Exception localException)
						{
							if(localException.getMessage() == null || localException.getMessage().isEmpty())
							{
								myLog.InsertarLog("App",this.toString(),1,"Error al modificar las cantidades de la devolucionDistribuidorProducto: vacio");
							}
							else
							{
								myLog.InsertarLog("App",this.toString(),1,"Error al modificar las cantidades de la devolucionDistribuidorProducto: " + localException.getMessage());
							}
						}
						 
						if(update == 0)
						{
							theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo consolidar el almacen autoventa.",1);
							return false;
						}
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(),"No existe suficiente producto en el almacen autoventa.",1);
						return false;
					}
				}	
			}
			 
			if(item.get_promocionId()>0)
			{
				ArrayList<PromocionProducto> listadoPromocionProducto = null;
				try
				{
					listadoPromocionProducto = new PromocionProductoBLL().ObtenerPromocionesProductoPor(
								 														item.get_promocionId());
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la promocion por promocionId: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la promocion por promocionId: " + localException.getMessage());
					} 
		 		}
						
			 	if(listadoPromocionProducto==null)
			 	{
			 		theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los productos de la promocion por promocionId", 1);
			 		return false;
			 	}
			 	else
			 	{
			 		for(PromocionProducto itemPromocion : listadoPromocionProducto)
			 		{
			 			Producto localProducto = null;
			 			try
			 			{
							 localProducto = new ProductoBLL().ObtenerProductoPor(itemPromocion.get_productoId());
			 			}
			 			catch(Exception localException)
			 			{
			 				if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 				{
			 					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del producto por productoId: vacio");
 							}
			 				else
			 				{
			 					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del producto por productoId: " + localException.getMessage());
			 				}
			 			}
							 
			 			if(localProducto == null)
			 			{
			 				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del producto.", 1);
			 				return false;
			 			}
							  
			 			DevolucionDistribuidorProducto localDevolucionDistribuidorProducto = null;
			 			try
			 			{
			 				localDevolucionDistribuidorProducto = new DevolucionDistribuidorProductoBLL()
								 										.ObtenerDevolucionDistribuidorProductoPorProductoId(
								 																itemPromocion.get_productoId());
			 			}
			 			catch(Exception localException)
			 			{
			 				if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 				{
			 					myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidorProducto por productoId: vacio");
			 				}
			 				else
			 				{
			 					myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidorProducto por productoId: " + localException.getMessage());
			 				} 
			 			}
							 
			 			if(localDevolucionDistribuidorProducto == null)
			 			{
			 				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el producto del almacen autoventa.", 1);
			 				return false;
			 			}
			 			else
			 			{
			 				cantidadExistenteEnUnidades = (localDevolucionDistribuidorProducto.get_cantidadPaquete() * localProducto.get_conversion()) +
										 						localDevolucionDistribuidorProducto.get_cantidad();
			 				cantidadSolicitadaEnUnidades = (item.get_cantidad() * itemPromocion.get_cantidadPaquete() * localProducto.get_conversion()) + (item.get_cantidad() * itemPromocion.get_cantidad());
								 
			 				if(cantidadSolicitadaEnUnidades <= cantidadExistenteEnUnidades)
			 				{
			 					int cantidadSaldoEnUnidades = cantidadExistenteEnUnidades - cantidadSolicitadaEnUnidades;
			 					int cantidadPaqueteConsolidada = cantidadSaldoEnUnidades / localProducto.get_conversion();
			 					int cantidadConsolidada = cantidadSaldoEnUnidades % localProducto.get_conversion();
									  
			 					long update = 0;
			 					try
			 					{
			 						update = new DevolucionDistribuidorProductoBLL().ModificarCantidadesDevolucionDistribuidorProducto(
												 localProducto.get_productoId(),cantidadConsolidada,cantidadPaqueteConsolidada);
			 					}
			 					catch(Exception localException)
			 					{
			 						if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 						{
			 							myLog.InsertarLog("App",this.toString(),1,"Error al modificar las cantidades de la devolucionDistribuidorProducto: vacio");
			 						}
			 						else
			 						{
			 							myLog.InsertarLog("App",this.toString(),1,"Error al modificar las cantidades de la devolucionDistribuidorProducto: " + localException.getMessage());
			 						}
			 					}
									 
			 					if(update == 0)
			 					{
			 						theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo consolidar el almacen autoventa.",1);
			 						return false;
			 					}
			 				}
			 				else
			 				{
			 					theUtilidades.MostrarMensaje(getApplicationContext(),"No existe suficiente producto en el almacen autoventa.",1);
			 					return false;
			 				}
			 			}
				 	}
		 		}
		 	}
		}
		
		long insertar = 0;
		Fecha fecha = theUtilidades.ObtenerFecha();
		
		try
		{
			insertar = new SincronizacionVentaBLL().InsertarSincronizacionVenta(0,0,clienteId,loginEmpleado.get_empleadoId(),
					0,0,0,0,venta.get_monto(),venta.get_descuento(),venta.get_montoFinal(),tipoPagoId,0,0,0,0,0,
					listadoDevolucionDistribuidorProductoTemp.size(),0,fecha.get_dia(),fecha.get_mes(),fecha.get_anio(),
					venta.get_latitud(),venta.get_longitud(),false,5,false,fecha.get_hora(),fecha.get_minuto(),true,factura,
					nit,nitNuevo,0,0,(int)ventaIdDispositivo,0,venta.get_observacion(),0,aplicarBonificacion,noAutoventa,dosificacionId,tipoNit,
					venta.get_descuentoCanal(),venta.get_descuentoAjuste(), 0, venta.get_descuentoProntoPago(), venta.get_prontoPagoId(),
					venta.get_descuentoObjetivo(), venta.get_formaPagoId(), venta.get_codTransferencia(), venta.is_fromShopp());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacionVenta de la autoventa: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacionVenta de la autoventa: " + localException.getMessage());
			 }
		}
		
		if(insertar == 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la venta al registro de sincronizaciones.", 1);
			return false;
		}
		
		return true;
 	}
		
	private boolean BorrarDevolucionDistribuidorProductoTempDispositivo()
	{
		 boolean bool = false;
		 try
		 {
			 bool = new DevolucionDistribuidorProductoTempBLL().BorrarDevolucionDistribuidorProductoTempPorEmpleadoIdYClienteId(loginEmpleado.get_empleadoId(), clienteId);
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al borrar las devolucionesDistribuidorProductoTemp: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al borrar las devolucionesDistribuidorProductoTemp: " + localException.getMessage());
			 }
			 return false;
		 }
	    
		 if(bool)
		 {
			 return true;
		 }
		 else
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar las autoventas temporales del dispositivo.", 1);
			 return false;
		 }
	}
	 
	private boolean ModificarEstadoAtencionClienteVenta()
	{
		long l = 0;
		try
		{
			l = new ClienteVentaBLL().ModificarClienteVentaEstadoAtendido(clienteId,true);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estadoAtencion del clienteVenta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estadoAtencion del clienteVenta " + localException.getMessage());
			} 
		}
		 
		if(l<=0)
		{
			return false;			 
		}
		else
		{
			return true;
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
		if(clienteVenta.get_montoPendienteCredito() > 0 && parametroGeneral.is_creditoSobreCredito() == false)
  	  	{
			theUtilidades.MostrarMensaje(getApplicationContext(), "El cliente tiene un credito pendiente, no puede venderle otro.", 1);
			return false;
  	  	}
		
		if(clienteVenta.get_montoPendienteCredito() >= 0 && parametroGeneral.is_creditoSobreCredito() == true)
		{
			float acumulado = 0;
			
			if(listadoDevolucionDistribuidorProductoTemp !=null && listadoDevolucionDistribuidorProductoTemp.size() >0)
			{
				for(DevolucionDistribuidorProductoTemp item : listadoDevolucionDistribuidorProductoTemp)
				{
					acumulado += item.get_montoFinal();
				}
			}
			
			if(acumulado + montoFinal + clienteVenta.get_montoPendienteCredito() > clienteVenta.get_topeCredito())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No puede incluir el producto, por que sobrepasaria el monto de credito permitido.", 1);
				return false;
			}
		}
		
		if(clienteVenta.get_montoPendienteCredito() == 0 && !parametroGeneral.is_creditoSobreCredito())
		{
			float acumulado = 0;
			
			if(listadoDevolucionDistribuidorProductoTemp !=null && listadoDevolucionDistribuidorProductoTemp.size() >0)
			{
				for(DevolucionDistribuidorProductoTemp item : listadoDevolucionDistribuidorProductoTemp)
				{
					acumulado += item.get_montoFinal();
				}
			}
			
			if(acumulado + montoFinal + clienteVenta.get_montoPendienteCredito() > clienteVenta.get_topeCredito())
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
		pdVerificarMontoNitCliente.setCanceledOnTouchOutside(false);
	         
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
	    	localSoapObject.addProperty("anio", Integer.valueOf(fecha.get_anio()));
	    	
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
	    			ConfirmarAutoventa();
				}	
   				else
   				{
   					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el nit acumulado.", 1);
   			        boolCondicionTributaria = true;
   			        ConfirmarAutoventa();
   				}
    		}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetNitAcumulado no se ejecuto correctamente. ", 1);
	    		return;
    		}
	    }
	}
	
	private void MostrarPantallaAutoventaPromocion()
	{
		Intent localIntent = new Intent(this, Distribuidorautoventapromocion.class);
		localIntent.putExtra("clienteId", clienteId);
    	localIntent.putExtra("tipoPagoId", tipoPagoId);
    	localIntent.putExtra("tipoNit", tipoNit);
    	localIntent.putExtra("factura", factura);
    	localIntent.putExtra("nit", nit);
    	localIntent.putExtra("nitNuevo", nitNuevo);
    	localIntent.putExtra("observacion", etObservacion.getText().toString());
    	localIntent.putExtra("aplicarBonificacion", aplicarBonificacion);
    	localIntent.putExtra("dosificacionId",dosificacionId);
    	localIntent.putExtra("venderCredito",venderCredito);
    	localIntent.putExtra("aplicarProntoPago", aplicarProntoPago);
		startActivity(localIntent);
	}
	
	private void MostrarPantallaMenuDistribuidor()
	{
		Intent localIntent = new Intent(this, Menudistribuidor.class);
	    localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(localIntent);
	}
	
	private void MostrarPantallaTipoImpresion()
	{
		Intent localIntent = new Intent(this,Distribuidortipoimpresion.class);
		if(ventaIdDispositivo!=0)
		{
			localIntent.putExtra("ventaId", ventaIdDispositivo);
			localIntent.putExtra("nit", nit);
			localIntent.putExtra("factura", factura);
			localIntent.putExtra("nitNuevo", nitNuevo);
			localIntent.putExtra("autoventa", true);
		}
		
		startActivity(localIntent);
	}
	
	@Override
 	public void onBackPressed() 
 	 {
 		 if(listadoDevolucionDistribuidorProductoTemp != null)
 		 {
 			 theUtilidades.MostrarMensaje(getApplicationContext(),"Debe confirmar la venta o eliminar todos los productos registrados.", 1);
 			 return;
 		 }
 		 else
 		 {
 			super.onBackPressed();
 			MostrarPantallaMenuDistribuidor();
 		 }
 	 }
}
