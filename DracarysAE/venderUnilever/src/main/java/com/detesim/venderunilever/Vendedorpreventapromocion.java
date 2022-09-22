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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import BLL.AlmacenProductoBLL;
import BLL.CanalRutaPrecioBLL;
import BLL.ClientePreventaBLL;
import BLL.CondicionTributariaBLL;
import BLL.DosificacionProveedorBLL;
import BLL.DraRebateSaldoBLL;
import BLL.MyLogBLL;
import BLL.ParametroGeneralBLL;
import BLL.PrecioListaBLL;
import BLL.PreventaBLL;
import BLL.PreventaProductoBLL;
import BLL.PreventaProductoTempBLL;
import BLL.ProductoBLL;
import BLL.PromocionBLL;
import BLL.PromocionCostoBLL;
import BLL.PromocionPrecioBLL;
import BLL.PromocionPrecioListaBLL;
import BLL.PromocionTipoNegocioBLL;
import BLL.ProveedorBLL;
import BLL.RolBLL;
import Clases.AES;
import Clases.AlmacenProducto;
import Clases.AlmacenProductoWSResult;
import Clases.CanalRutaPrecio;
import Clases.ClienteIncentivo;
import Clases.ClientePreventa;
import Clases.CondicionTributaria;
import Clases.DosificacionProveedor;
import Clases.DraRebateSaldo;
import Clases.Fecha;
import Clases.JSonParser;
import Clases.LoginEmpleado;
import Clases.ParametroGeneral;
import Clases.ParseJSon;
import Clases.PrecioLista;
import Clases.Preventa;
import Clases.PreventaJS;
import Clases.PreventaProducto;
import Clases.PreventaProductoTemp;
import Clases.PreventaProductoTempJS;
import Clases.Producto;
import Clases.Promocion;
import Clases.PromocionCosto;
import Clases.PromocionPrecio;
import Clases.PromocionPrecioLista;
import Clases.PromocionTipoNegocio;
import Clases.Proveedor;
import Clases.Rol;
import Clases.SingleId;
import Clases.Ubicacion;
import Clases.VendedorDiaWS;
import Clases.WSResultado;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Vendedorpreventapromocion extends Activity implements OnClickListener
{
	LinearLayout llVendedorPreventaPromocion;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	String URLJSON = theUtilidades.get_URLJSON();
	LoginEmpleado loginEmpleado;
	MyLogBLL myLog = new MyLogBLL();
	
	ArrayList<PreventaProductoTemp> listadoPreventaProductoTemp ;
	ArrayList<PreventaProducto> listadoPreventaProductoNoSincronizadas;
	Preventa preventa;
	Promocion promocion;
	ArrayList<Proveedor> listadoProveedor;
	ClientePreventa clientePreventa;
	int clienteId;
	int tipoPagoId;
	String factura;
	String nit;
	String tipoNit;
	boolean nitNuevo;
	int itemBorrarDispositivo;
	int itemBorrarServidor;
	int preventaIdDispositivo;
	int preventaIdServer;
	ParametroGeneral parametroGeneral;
	int noPreventa;
	String observacion;
	PromocionPrecio promocionPrecio;
	boolean aplicarBonificacion;
	int diaEntrega;
	int mesEntrega;
	int anioEntrega;
	int dosificacionId;
	boolean boolCondicionTributaria=false;
	float montoTributarioServer = 0;
	boolean venderCredito;
	CanalRutaPrecio canalRutaPrecio;
	boolean aplicarProntoPago;
	DraRebateSaldo draRebateSaldo;
	double d2 = 100.0;
	ArrayList<ClienteIncentivo> clienteIncentivos;
	float _monto;
	float _descuento;
	float _montoFinal;
	float _descuentoIncentivo;
	
	TextView tvClienteNombre;
	TextView tvPromociontxt;
	TextView tvPromocion;
	TextView tvPreciotxt;
	TextView tvPrecio;
	TextView tvDescuentotxt;
	TextView tvDescuento;
	TextView tvCantidadtxt;
	TextView tvPromocionlbl;
	TextView tvCantidadlbl;
	TextView tvPreciolbl;
	TextView tvSubTotallbl;
	TextView tvTotaltxt;
	TextView tvTotal;
	TextView tvObservacion;
	EditText etObservacion;
	ImageView ivProductos;
	Button btnMostrarDetalles;
	TextView tvDsctoObjetivoTxt;
	TextView tvDsctoObjetivo;
	TextView tvDsctoObjetivoTotalTxt;
	TextView tvDsctoObjetivoTotal;
	TextView tvMontoFinalTxt;
	TextView tvMontoFinal;
	CheckBox cbAplicarBonificacion;
	Button btnAdicionarPromocion;
	Button btnConfirmarPreventa;
	
	EditText etCantidad;
	Spinner spnProveedor;
	Spinner spnPromocion;
	ListView lvPreventaPromocionTemp;
	Dialog dialog;

	TextView tvIncentivoMonto;
	TextView tvIncentivoDescuento;
	TextView tvIncentivoMontoFinal;
	Dialog dialogIncentivo;
	
	ProgressDialog pdEsperaObtenerAlmacenProducto;
	ProgressDialog pdInsertarPreventaPromocion; 
	ProgressDialog pdDeletePreventaPromocion;
	ProgressDialog pdInsertarPreventa;
	ProgressDialog pdVerificarMontoNitCliente;
	ProgressDialog pdInsertarPreventaJS;
	ProgressDialog pdClienteIncentivo;
	ProgressDialog pdInsertClienteIncentivo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedorpreventapromocion);
		
		llVendedorPreventaPromocion = (LinearLayout)findViewById(R.id.VendedorPreventaPromocionLinearLayout);
		ivProductos = (ImageView) findViewById(R.id.ivVendedorVentaDirectaPromocion);
		ivProductos.setOnClickListener(this);
		tvClienteNombre = (TextView)findViewById(R.id.tvVendedorVentaDirectaPromocionClienteContenido);
		spnProveedor = (Spinner)findViewById(R.id.spnVendedorVentaDirectaPromocionProveedor);
		spnPromocion = (Spinner)findViewById(R.id.spnVendedorVentaDirectaPromocionPromocion);
		btnMostrarDetalles = (Button)findViewById(R.id.btnVendedorVentaDirectaPromocionMostrarDetalles);
		btnMostrarDetalles.setOnClickListener(this);
		tvPromociontxt = (TextView)findViewById(R.id.tvVendedorVentaDirectaPromocionPromocionTxt);
		tvPromocion = (TextView)findViewById(R.id.tvVendedorVentaDirectaPromocionTxtContenido);
		tvPreciotxt = (TextView)findViewById(R.id.tvVendedorVentaDirectaPromocionPrecioTxt);
		tvPrecio = (TextView)findViewById(R.id.tvVendedorVentaDirectaPromocionPrecioTxtContenido);
		tvDescuentotxt = (TextView)findViewById(R.id.tvVendedorVentaDirectaPromocionDescuentoTxt);
		tvDescuento = (TextView)findViewById(R.id.tvVendedorVentaDirectaPromocionDescuentoTxtContenido);
		tvCantidadtxt = (TextView)findViewById(R.id.tvVendedorVentaDirectaPromocionCantidad);
		etCantidad = (EditText)findViewById(R.id.etVendedorVentaDirectaPromocionCantidad);
		btnAdicionarPromocion = (Button)findViewById(R.id.btnVendedorVentaDirectaPromocionAdicionarPreventa);
		btnAdicionarPromocion.setOnClickListener(this);
		tvPromocionlbl = (TextView)findViewById(R.id.tvVendedorVentaDirectaPromocionPromocionLbl);
		tvCantidadlbl = (TextView)findViewById(R.id.tvVendedorVentaDirectaPromocionCantidadLbl);
		tvPreciolbl = (TextView)findViewById(R.id.tvVendedorVentaDirectaPromocionPrecioLbl);
		tvSubTotallbl = (TextView)findViewById(R.id.tvVendedorVentaDirectaPromocionSubTotalLbl);
		lvPreventaPromocionTemp = (ListView)findViewById(R.id.lvVendedorVentaDirectaPromocionPreventas);
		tvTotaltxt = (TextView)findViewById(R.id.tvVendedorVentaDirectaPromocionTotalTxt);
		tvTotal = (TextView)findViewById(R.id.tvVendedorVentaDirectaPromocionTotal);
		tvDsctoObjetivoTxt = (TextView)findViewById(R.id.tvVenPrePromoDescuentoObjetivoTxt);
	    tvDsctoObjetivo = (TextView)findViewById(R.id.tvVenPrePromoDescuentoObjetivo);
	    tvDsctoObjetivoTotalTxt = (TextView)findViewById(R.id.tvVenPrePromoDescuentoObjetivoTotalTxt);
	    tvMontoFinalTxt = (TextView)findViewById(R.id.tvVenPrePromoMontoFinalTxt);
		tvMontoFinal  = (TextView)findViewById(R.id.tvVenPrePromoMontoFinal);
	    tvDsctoObjetivoTotal = (TextView)findViewById(R.id.tvVenPrePromoDescuentoObjetivoTotal);
		cbAplicarBonificacion = (CheckBox)findViewById(R.id.cbVendedorPreventaPromocionAplicarBonificacion);
		cbAplicarBonificacion.setOnClickListener(this);
		tvObservacion = ((TextView)findViewById(R.id.tvVendedorEdicionPreventaPromocionObservacion));
	    etObservacion = ((EditText)findViewById(R.id.etVendedorEdicionPreventaPromocionObservacion));
		btnConfirmarPreventa = (Button)findViewById(R.id.btnVendedorEdicionPreventaPromocionConfirmarPreventa);
		btnConfirmarPreventa.setOnClickListener(this);
		
		llVendedorPreventaPromocion.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
		clienteId = 0;
	    clienteId = getIntent().getExtras().getInt("clienteId");
	    if(clienteId == 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el clienteId.", 1);
	    	return;
	    }
	    
	    tipoPagoId=0;
	    tipoPagoId = getIntent().getExtras().getInt("tipoPagoId");
	    if(tipoPagoId == 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el tipoPagoId.", 1);
	    	return;
	    }
	    
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
	    if(observacion != null && !observacion.isEmpty())
	    {
	    	etObservacion.setText(observacion);
	    }
	    
	    aplicarBonificacion = false;
	    aplicarBonificacion = getIntent().getExtras().getBoolean("aplicarBonificacion");
	    cbAplicarBonificacion.setChecked(aplicarBonificacion);
	    
	    aplicarProntoPago = false;
	    aplicarProntoPago = getIntent().getExtras().getBoolean("aplicarProntoPago");
	    
	    diaEntrega=0;
	    diaEntrega = getIntent().getExtras().getInt("diaEntrega");
	    
	    mesEntrega=0;
	    mesEntrega = getIntent().getExtras().getInt("mesEntrega");
	    
	    anioEntrega=0;
	    anioEntrega = getIntent().getExtras().getInt("anioEntrega");
	    
	    dosificacionId = 0;
	    dosificacionId = getIntent().getExtras().getInt("dosificacionId");
	    if(dosificacionId == 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la dosificacionId.", 1);
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
	    }
	    else
	    {
	    	DespliegueControlesAdicionarPromocion(false);
	        DespliegueControlesConfirmarPreventa(false);
	        CargarInformacion();
	    }
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.ivVendedorVentaDirectaPromocion:
			MostrarPantallaPreventaProducto();
			break;
		case R.id.btnVendedorVentaDirectaPromocionMostrarDetalles:
			ObtenerDatosSeleccion();
			break;		
		case R.id.btnVendedorVentaDirectaPromocionAdicionarPreventa:
			AdicionarPreventaPromocionTemporal();
			break;
		case R.id.btnVendedorEdicionPreventaPromocionConfirmarPreventa:
			ConfirmarPreventa();
			break;
		case R.id.cbVendedorPreventaPromocionAplicarBonificacion:
			AplicarBonificacion();
			break;
		}		
	}
	
	private void DespliegueControlesAdicionarPromocion(boolean estado)
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
	    
	    tvPromociontxt.setVisibility(visibilidad);
	    tvPromocion.setVisibility(visibilidad);
	    tvPreciotxt.setVisibility(visibilidad);
	    tvPrecio.setVisibility(visibilidad);
	    tvDescuentotxt.setVisibility(visibilidad);
	    tvDescuento.setVisibility(visibilidad);
	    tvCantidadtxt.setVisibility(visibilidad);
	    etCantidad.setVisibility(visibilidad);
	    btnAdicionarPromocion.setVisibility(visibilidad); 
	    etCantidad.setText("");
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
	    
	    tvPromocionlbl.setVisibility(visibilidad);
	    tvCantidadlbl.setVisibility(visibilidad);
	    tvPreciolbl.setVisibility(visibilidad);
	    tvSubTotallbl.setVisibility(visibilidad);
	    lvPreventaPromocionTemp.setVisibility(visibilidad);
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
	    btnConfirmarPreventa.setVisibility(visibilidad);
    }

	public void CargarInformacion()
	{
		CargarParametroGeneral();
	    CargarCliente();
	    CargarProveedores();
	    ActualizarListView();
	    ObtenerNoPreventa();
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
	    	tvClienteNombre.setText(clientePreventa.get_nombreCompleto().toString());
	    }
	}

	public void CargarProveedores()
	{ 
		ArrayList<DosificacionProveedor> dosificaciones = new ArrayList<DosificacionProveedor>();
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
			
			ArrayList<Proveedor> listadoProveedor = null;
			try
			{
				listadoProveedor = new ProveedorBLL().ObtenerProveedoresTodosPor(proveedoresId);
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
			
			ArrayAdapter<Proveedor> localArrayAdapter = new ArrayAdapter<Proveedor>(this,R.layout.disenio_spinner,listadoProveedor);
		    spnProveedor.setAdapter(localArrayAdapter);
		    
		    spnProveedor.setOnItemSelectedListener(new OnItemSelectedListener() 
		    {	    	
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
				{
					Proveedor proveedor = (Proveedor)spnProveedor.getSelectedItem();

					ArrayList<Promocion> listadoPromocion = null;
					try
					{
						listadoPromocion = new PromocionBLL().ObtenerPromocionesNoEnPreventaProductoTemp(clienteId, 
								loginEmpleado.get_empleadoId(),proveedor.get_proveedorId(),clientePreventa.get_precioListaId());
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones por proveedorId: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones por proveedorId: " + localException.getMessage());
						} 
					}
					
					if(listadoPromocion == null)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las promociones por proveedorId", 1);
						return;
					}

				    
				    ArrayAdapter<Promocion> localArrayAdapterFinal = new ArrayAdapter<Promocion>(getApplicationContext(),R.layout.disenio_spinner,listadoPromocion);
				    spnPromocion.setAdapter(localArrayAdapterFinal);
				    
				    spnPromocion.setOnItemSelectedListener(new OnItemSelectedListener() 
				    {	    	
						@Override
						public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
						{
							DespliegueControlesAdicionarPromocion(false);
						}

						@Override
						public void onNothingSelected(AdapterView<?> parent){}
				    	
					});
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent){}
		    	
			});
		}    
	}
	
	public void CargarPromociones()
	{
		int proveedorId = ((Proveedor)spnProveedor.getSelectedItem()).get_proveedorId();
	    ArrayList<Promocion> listadoPromocion = null;
	    
	    try
	    {
	    	listadoPromocion = new PromocionBLL().ObtenerPromocionesNoEnPreventaProductoTemp(clienteId,
	    				loginEmpleado.get_empleadoId(),proveedorId,clientePreventa.get_precioListaId());
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null|| localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones por proveedorId: vacio");
	    	} 
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones por proveedorId: " + localException.getMessage());
	    	} 
	    }
	    
	    if(listadoPromocion == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las promociones por el proveedor seleccionado.", 1);
	    	return;
	    }
	    
	    ArrayAdapter<Promocion> localArrayAdapter = new ArrayAdapter<Promocion>(this,R.layout.disenio_spinner,listadoPromocion);
	    spnPromocion.setAdapter(localArrayAdapter);
	    
	    spnPromocion.setOnItemSelectedListener(new OnItemSelectedListener() 
	    {	    	
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
			{
				DespliegueControlesAdicionarPromocion(false);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent){}
	    	
		});
	}
	
	public boolean ExistePromocionesConSaldoEnAlmacenProducto(int promocionId)
	{
		boolean existencia = true;
		try
		{
			existencia = new PromocionBLL().ExistePromocionesConSaldoEnAlmacenProducto(promocionId, 1);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"No existe saldo en almacenProducto para la promocion: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"No existe saldo en almacenProducto para la promocion: " + localException.getMessage());
			} 
		}
		
		return existencia;
	}

	public void ActualizarListView()
	{
		listadoPreventaProductoTemp = null;
		try
		{
			listadoPreventaProductoTemp = new PreventaProductoTempBLL().ObtenerPreventasProductoTempPor(clienteId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas temporales: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas temporales: " + localException.getMessage());
			}
		}
		      
		if(listadoPreventaProductoTemp == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No existen preventas temporales.", 1);
			DespliegueControlesConfirmarPreventa(false);
	    	lvPreventaPromocionTemp.setAdapter(null);
		}
		else
		{
			DespliegueControlesConfirmarPreventa(true);
			CalcularTotalPreventa();
			lvPreventaPromocionTemp.setVisibility(View.VISIBLE);
		    LlenarPreventaListView();
		    EliminarItemListView();	
		}    
	}
	
	private void ObtenerNoPreventa()
	{
		noPreventa = -1;
	    try
	    {
	    	noPreventa = new PreventaBLL().ObtenerPreventas().size();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el nro de preventa: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el nro de preventas: " + localException.getMessage());
	    	}
	    	noPreventa = 0;
	    }
	    
	    if (noPreventa == -1)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los preventas.", 1);
	    	return;
	    }
	    else
	    {
	    	if(!VerificarExistenciaCliente(clienteId))
	    	{
	    		noPreventa++;
	    	}
	    }
	}
	
	private boolean VerificarExistenciaCliente(int clienteId)
	{
		boolean verificado = false;
		ArrayList<PreventaProductoTemp> listadoPreventaProductoTemp = null;
		try
		{
			listadoPreventaProductoTemp = new PreventaProductoTempBLL().ObtenerPreventasProductoTempPor(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas por clienteId: " + localException.getMessage());
	    	}
		}
		
		if(listadoPreventaProductoTemp == null)
		{
			return verificado;
		}
		else
		{
			noPreventa = listadoPreventaProductoTemp.get(0).get_noPreventa();
			return true;
		}
	}
	
	private void CalcularTotalPreventa()
	{
		float total = 0;
		for(PreventaProductoTemp item : listadoPreventaProductoTemp)
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
	
	private void LlenarPreventaListView()
	{
	    MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    	    
	    if(listadoPreventaProductoTemp == null)
	    {
	    	lvPreventaPromocionTemp.setAdapter(null);
	    }
	    else
	    {
		    ViewGroup.LayoutParams localLayoutParams = lvPreventaPromocionTemp.getLayoutParams();
		    localLayoutParams.height = ((int)(50*getApplicationContext().getResources().getDisplayMetrics().density) * listadoPreventaProductoTemp.size());
		    lvPreventaPromocionTemp.setLayoutParams(localLayoutParams);
		    lvPreventaPromocionTemp.setAdapter(localMiAdaptadorLista);
	    }
	}

	class MiAdaptadorLista extends ArrayAdapter<PreventaProductoTemp>
	{
		private Context _context;
		
		public MiAdaptadorLista(Context context)
		{
			super(context,R.layout.disenio_vendedorpreventaproducto,listadoPreventaProductoTemp);
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
			
			PreventaProductoTemp localPreventaProductoTemp = (PreventaProductoTemp)listadoPreventaProductoTemp.get(position);
			
			TextView descripcion25 = (TextView)localView.findViewById(R.id.tvVendedorVentaDirectaProductoCliente);
			TextView cantidad = (TextView)localView.findViewById(R.id.tvPreventaDisenioCantidad);
			TextView precio = (TextView)localView.findViewById(R.id.tvPreventaDisenioPrecioUnitario);
			TextView precioPaquete = (TextView)localView.findViewById(R.id.tvVendedorPreventaProductoProveedor);
			TextView montoFinal = (TextView)localView.findViewById(R.id.tvVendedorPreventaProductoProducto);
			ImageView bullet = (ImageView)localView.findViewById(R.id.imgvLoginLogotipo);
      
			if(localPreventaProductoTemp.get_productoId() != 0)
			{
				Producto localProducto = null;
				try
				{
					localProducto = new ProductoBLL().ObtenerProductoPor(localPreventaProductoTemp.get_productoId());
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
				cantidad.setText(String.valueOf(localPreventaProductoTemp.get_cantidad()==0?localPreventaProductoTemp.get_cantidadPaquete():localPreventaProductoTemp.get_cantidad()));
				
				if(localPreventaProductoTemp.get_cantidad()==0)
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
				
				if(localPreventaProductoTemp.get_cantidadPaquete()==0)
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
				
				montoFinal.setText(String.valueOf(new BigDecimal(localPreventaProductoTemp.get_montoFinal()).setScale(2,RoundingMode.HALF_UP)));
			}
			
			if(localPreventaProductoTemp.get_promocionId() != 0) 
			{
				Promocion localPromocion = null;
				try
				{
					localPromocion = new PromocionBLL().ObtenerPromocionPor(localPreventaProductoTemp.get_promocionId());
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
				cantidad.setText(String.valueOf(localPreventaProductoTemp.get_cantidad()));
				precio.setText(String.valueOf(new BigDecimal(localPreventaProductoTemp.get_monto()/localPreventaProductoTemp.get_cantidad()).setScale(2, RoundingMode.HALF_UP)));
				precioPaquete.setText(" ");
				montoFinal.setText(String.valueOf(new BigDecimal(localPreventaProductoTemp.get_montoFinal()).setScale(2, RoundingMode.HALF_UP)));
			}
			
			bullet.setImageResource(R.drawable.bullet_eliminar);
			
			return localView;
		}
	}

	private void EliminarItemListView()
	{
		((ListView)findViewById(R.id.lvVendedorVentaDirectaPromocionPreventas)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
			public void onItemClick(AdapterView<?> parent, View view,final int position, long id)
			{
				dialog = new Dialog(Vendedorpreventapromocion.this);
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
								
								PreventaProductoTemp localPreventaProductoTemp = listadoPreventaProductoTemp.get(position);
								itemBorrarDispositivo = localPreventaProductoTemp.get_id();
								
								if(localPreventaProductoTemp.get_tempId() != 0)
								{
									itemBorrarServidor = localPreventaProductoTemp.get_tempId();
									BorrarPreVentaProductoTemp();
								}
								else
								{
									if(BorrarPreventaProductoTempDispositivo())
									{
										theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la preventa eliminado del dispositivo.", 1);
										CargarPromociones();
									} 
									else 
									{
										theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo eliminar el item de la preventa.", 1);
									}
								}

								if(listadoPreventaProductoTemp != null) 
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
	
	private void BorrarPreVentaProductoTemp()
	 {
		 pdDeletePreventaPromocion = new ProgressDialog(this);
		 pdDeletePreventaPromocion.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		 pdDeletePreventaPromocion.setMessage("Borrando item preventa ...");
		 pdDeletePreventaPromocion.setCancelable(false);
		 pdDeletePreventaPromocion.setCanceledOnTouchOutside(false);
		 
		 WSBorrarPreventaProductoTemp localWSBorrarPreventaProductoTemp = new WSBorrarPreventaProductoTemp();
		 try
		 {
			 localWSBorrarPreventaProductoTemp.execute();
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSBorrarPreventaProductoTemp: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSBorrarPreventaProductoTemp: " + localException.getMessage());
			 }
			 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSBorrarPreventaProductoTemp.", 1);
		 }
	 }
	
	private class WSBorrarPreventaProductoTemp extends AsyncTask<Void, Integer, Boolean>
	 {
	    String BORRARPREVENTAPRODUCTO_METHOD_NAME = "DeletePreVentaProductoTemp";
	    String BORRARPREVENTAPRODUCTO_SOAP_ACTION = NAMESPACE + BORRARPREVENTAPRODUCTO_METHOD_NAME;
	    boolean WSBorrarPreVentaProductoTemp;
	    int resultadoInt;
	    String resultadoString;
	    SoapObject soapResultado;
	    
	    protected void onPreExecute()
	    {
	    	pdDeletePreventaPromocion.show();
	    }
	    
	    protected Boolean doInBackground(Void... paramVarArgs)
	    {
	    	WSBorrarPreVentaProductoTemp = false;
	    	
	    	SoapObject localSoapObject = new SoapObject(NAMESPACE,BORRARPREVENTAPRODUCTO_METHOD_NAME);
	    	localSoapObject.addProperty("tempId", itemBorrarServidor);
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
	    	      	WSBorrarPreVentaProductoTemp = true;
	    	    }
	    	    return true;	        
			}
	    	catch (Exception localException)
	    	{
   			WSBorrarPreVentaProductoTemp = false;
   			if(localException.getMessage() == null || localException.getMessage().isEmpty())
   			{
   		        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSDeletePreventaProductoTemp: vacio");
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
	    	if(pdDeletePreventaPromocion.isShowing())
	    	{
	    		pdDeletePreventaPromocion.dismiss();
	    	}
	    	
	    	if(ejecutado)
	    	{
	    		if(WSBorrarPreVentaProductoTemp) 
	    		{
	    			if(BorrarPreventaProductoTempDispositivo()) 
	    			{
	    				theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la preventa eliminado.", 1);
	    				CargarPromociones();
   				}
	    			else
	    			{
	    				theUtilidades.MostrarMensaje(getApplicationContext(), "No se elimino el item de la preventa del dispositivo.", 1);
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
	
	private boolean BorrarPreventaProductoTempDispositivo()
	 {
		 boolean bool = false;
		 try
		 {
			 bool = new PreventaProductoTempBLL().BorrarPreventaProductoTempPorId(itemBorrarDispositivo);
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al borrar el item de la preventaProductoTemp: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al borrar el item de la preventaProductoTemp: " + localException.getMessage());
			 }
			 return false;
		 }
	    
		 if(bool)
		 {
			 return true;
		 }
		 else
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar el item de la preventa.", 1);
			 return false;
		 }
	}

	public void ObtenerDatosSeleccion()
	 {
		 ClientePreventa localClientePreventa;
		 int promocionId = 0;
		 
		 promocionId = ((Promocion)spnPromocion.getSelectedItem()).get_promocionId();
		 if(promocionId == 0)
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(), "Debe seleccionar una promocion.", 1);
			 return;
		 }
		 
		 promocion = null;
		 try
		 {
			 promocion = new PromocionBLL().ObtenerPromocionPor(promocionId);
		 }
		 catch(Exception localException)
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
		 
		 if(promocion == null)
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles de la promocion", 1);
			 return;
		 }
		 
		 localClientePreventa = null;
		 try
		 {
			 localClientePreventa = new ClientePreventaBLL().ObtenerClientePreventaPor(clienteId);
		 }
		 catch(Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del cliente: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del cliente: " + localException.getMessage());
			 } 
		 }
	    
		 if(localClientePreventa == null)
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo obtener los detalles del cliente",1);
			 return;
		 }
		 
		 //------- Validacion promocion tipo negocio -----------
		 ArrayList<PromocionTipoNegocio> listadoPromocionTipoNegocio = null;
		 
		 try
		 {
			 listadoPromocionTipoNegocio = new PromocionTipoNegocioBLL().ObtenerPromocionTipoNegocioPor(promocionId);
		 }
		 catch(Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de la promocion tipo negocio: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de la promocion tipo negocio: " + localException.getMessage());
			 } 
		 }
		 
		 if(listadoPromocionTipoNegocio != null)
		 {
			 boolean habilitado = false;
			 for(PromocionTipoNegocio item : listadoPromocionTipoNegocio)
			 {
				 if(item.get_tipoNegocioId() == localClientePreventa.get_clienteTipoNegocioId())
				 {
					 habilitado = true;
				 }
			 }
			 
			 if(!habilitado)
			 {
				 theUtilidades.MostrarMensaje(getApplicationContext(), "La promocion no se encuentra habilitada para el tipo de negocio del cliente.", 1);
				 return;
			 }
		 }
		 //---------------- Fin Validacion --------------------
		 
		 float precioPromocion = 0;
		 
		 if(ValidarExistenciaPrecioListaPorPromocion(promocion.get_promocionId()))
		 {
			 promocionPrecio = null;
				
			 try
			 {
				 promocionPrecio = new PromocionPrecioBLL().ObtenerPromocionPrecioPor(promocion.get_promocionId(),localClientePreventa.get_precioListaId());
			 }
			 catch (Exception localException)
			 {
				 if(localException.getMessage() == null || localException.getMessage().isEmpty())
				 {
					 myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precio promocion por promocionId y precioListaId: vacio");
				 }
				 else
				 {
					 myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precio promocion por promocionId y precioListaId: " + localException.getMessage());
				 } 
			 }
				
			 if(promocionPrecio == null)
			 {
				 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del precio de la promocion.", 1);
				 return;
			 }
			 else
			 {
				 if(promocionPrecio.get_precio() < 0)
				 {
					 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el precio de la promocion, precioPromocion <= 0.", 1);
					 return;
				 }
				 else
				 {
					 precioPromocion = promocionPrecio.get_precio();
				 }
			}
		 }
		 else
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(), "No existe un precio de lista definido para la promocion.", 1);
			 return;
		 }
	          
	     DespliegueControlesAdicionarPromocion(true);
	     
	     tvPromocion.setText(promocion.get_descripcion());
	     tvPrecio.setText(String.valueOf(precioPromocion));
	     tvDescuento.setText(String.valueOf(localClientePreventa.get_descuento()));
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
	
	private boolean ValidarExistenciaPrecioListaPorPromocion(int promocionId)
	{
		boolean validado = false;
		PromocionPrecioLista promocionPrecioLista = null;
		try
		{
			promocionPrecioLista = new PromocionPrecioListaBLL().ObtenerPromocionPrecioListaPor(promocionId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de la promocionPrecioLista: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de la promocionPrecioLista: " + localException.getMessage());
			 }
		}
		
		if(promocionPrecioLista == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles de la promocionPrecioLista.", 1);
			return false;
		}
		else
		{
			validado = true;
		}
		
		return validado;
	}
	
	private void AdicionarPreventaPromocionTemporal()
	{
		  int cantidad=0;
		  float precio;
		  float monto=0;
		  float descuento;
		  float montoFinal=0;
		  ParametroGeneral parametroGeneral;
		  int costoId;
		  
		  if(etCantidad.getText().length() <= 0)
		  {
			  theUtilidades.MostrarMensaje(getApplicationContext(), "La cantidad es requerida.", 1);
			  return;
		  }
		  
		  try
		  {
			  cantidad = Integer.parseInt(etCantidad.getText().toString());
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
         
         descuento = clientePreventa.get_descuento();

         precio = 0;
    	  
         try
         {
        	 precio = Float.parseFloat(tvPrecio.getText().toString());
         }
         catch(Exception localException)
         {
        	 if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	 {
        		 myLog.InsertarLog("App",this.toString(),1,"Error al convertir el precio de string a float: vacio");
        	 }
        	 else
        	 {
        		 myLog.InsertarLog("App",this.toString(),1,"Error al convertir el precio de string a float: " + localException.getMessage());
        	 }
        	 return;
         }
    	  
         if (precio < 0) 
         {
        	 theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontro el precio de la promocion.", 1);
        	 return;
         }
          
          monto = cantidad * precio;
          
          parametroGeneral =null;
          try
          {
        	  parametroGeneral = new ParametroGeneralBLL().ObtenerParamatroGeneral();
          }
          catch(Exception localException)
          {
        	  if(localException.getMessage() == null || localException.getMessage().isEmpty())
    		  {
    			  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los parametros generales: vacio");
    		  }
    		  else
    		  {
    			  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los parametros generales: " + localException.getMessage());
    		  }
    		  return;
          }
          
          if (parametroGeneral == null) 
          {
        	  theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontro los parametros generales.", 1);
        	  return;
          }
          
          if(parametroGeneral.get_descuentoPromocion())
          {
        	  if(descuento > 0)
              {
            	  montoFinal = monto - (monto * (descuento/100)); 
              }
              else
              {
            	  montoFinal = monto;
              }
          }
          else
          {
        	  descuento = 0;
        	  montoFinal = monto;
          }
          
          float precioSinDescuento = 0;
          float montoSinDescuento = 0;
          float descuentoFinal = 0;
          
          if(promocionPrecio.get_precioOriginal() <= 0)
          {
        	  theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el precio original de la promocion, precioOriginal <= 0.", 1);
        	  return;
          }
          else
          {
        	  precioSinDescuento =  promocionPrecio.get_precioOriginal();
        	  montoSinDescuento = cantidad * precioSinDescuento;
              descuentoFinal = montoSinDescuento - montoFinal + descuento;
          }
          
          PromocionCosto localPromocionCosto = null;
  		
          try
          {
        	  localPromocionCosto = new PromocionCostoBLL().ObtenerPromocionCostoPorPromocionId(promocion.get_promocionId());
          }
          catch (Exception localException)
          {
        	  if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	  {
        		  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de la promocionCosto por promocionId: vacio");
        	  }
        	  else
        	  {
        		  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de la promocionCosto por promocionId: " + localException.getMessage());
        	  } 
          }
          
          if(localPromocionCosto == null)
          {
        	  theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del costo de la promocion.", 1);
        	  return;
          }
          else
          {
        	  if(localPromocionCosto.get_costoId() <= 0)
        	  {
        		  theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el costo de la promocion, costoId <= 0.", 1);
            	  return;
        	  }
        	  else
        	  {
        		  costoId = localPromocionCosto.get_costoId();
        	  }
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
          
          PreventaProductoTemp localPreventaProductoTemp = new PreventaProductoTemp();
    	  localPreventaProductoTemp.set_cantidad(cantidad);
    	  localPreventaProductoTemp.set_cantidadPaquete(0);
    	  localPreventaProductoTemp.set_clienteId(clientePreventa.get_clienteId());
    	  localPreventaProductoTemp.set_empleadoId(loginEmpleado.get_empleadoId());
    	  localPreventaProductoTemp.set_monto(montoSinDescuento);
    	  localPreventaProductoTemp.set_descuento(descuentoFinal);
    	  localPreventaProductoTemp.set_montoFinal(montoFinal);
    	  localPreventaProductoTemp.set_productoId(0);
    	  localPreventaProductoTemp.set_promocionId(promocion.get_promocionId());
    	  localPreventaProductoTemp.set_costo(0);
    	  localPreventaProductoTemp.set_costoId(costoId);
    	  localPreventaProductoTemp.set_noPreventa(noPreventa);
    	  localPreventaProductoTemp.set_precioId(promocionPrecio.get_precioId());
          
	      if (ValidarExistenciasAlmacenProductoDispositivo(promocion.get_promocionId(),cantidad))
	      {
	    	  if(parametroGeneral.get_montoCi() > 0)
	    	  {
	    		  if(ObtenerPreventaProductoTempMontoFinalAlmacenadoActual(noPreventa)+localPreventaProductoTemp.get_montoFinal() >= parametroGeneral.get_montoCi())
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
	    		  if(ObtenerPreventaProductoTempMontoFinalAlmacenadoActual(noPreventa)+localPreventaProductoTemp.get_montoFinal() >= parametroGeneral.get_montoBancarizacion())
	    		  {
	    			  theUtilidades.MostrarMensaje(getApplicationContext(), "No se puede ingresar el producto, ya que el monto excede el definido por el de Bancarizacion.", 1);
	    			  return;
	    		  }
	    	  }
	    	  
	    	  if(parametroGeneral.get_tipoImpresionFactura().equals("Medio Oficio"))
	    	  {	    		  
	    		  if(listadoPreventaProductoTemp == null || listadoPreventaProductoTemp.size() <= 14)
	    		  {
			    	  if(InsertarPreventaProductoTempDispositivo(localPreventaProductoTemp))
			    	  {
			    		  theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la promocion insertado en el dispositivo.", 1);
			    		  CargarPromociones();
			    		  DespliegueControlesAdicionarPromocion(false);
			    		  ActualizarListView();
			    	  }
			    	  else 
			    	  {
			    		  theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el item de la promocion.", 1);
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
	    		  if(listadoPreventaProductoTemp == null || listadoPreventaProductoTemp.size() <= 34)
	    		  {
			    	  if(InsertarPreventaProductoTempDispositivo(localPreventaProductoTemp))
			    	  {
			    		  theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la promocion insertado en el dispositivo.", 1);
			    		  CargarPromociones();
			    		  DespliegueControlesAdicionarPromocion(false);
			    		  ActualizarListView();
			    	  }
			    	  else 
			    	  {
			    		  theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el item de la promocion.", 1);
			    		  return;
			    	  }
	    		  }
	    		  else
	    		  {
	    			  theUtilidades.MostrarMensaje(getApplicationContext(),"Se alcanzo el tope de items para la factura oficio, confirme la preventa e ingrese una nueva.",1);
		    		  return;
	    		  }
	    	  }
	    	  else
	    	  {
	    		  if(InsertarPreventaProductoTempDispositivo(localPreventaProductoTemp))
		    	  {
		    		  theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la promocion insertado en el dispositivo.", 1);
		    		  CargarPromociones();
		    		  DespliegueControlesAdicionarPromocion(false);
		    		  ActualizarListView();
		    	  }
		    	  else 
		    	  {
		    		  theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el item de la promocion.", 1);
		    		  return;
		    	  }
	    	  }
	      }
	      else
	      {
	    	  theUtilidades.MostrarMensaje(getApplicationContext(), "No existe producto en el almacen del dispositivo.", 1);
	    	  return;
	      }
	}
	
	private float ObtenerPreventaProductoTempMontoFinalAlmacenadoActual(int numeroPreventa)
	{
		float montoFinal = 0;
		ArrayList<PreventaProductoTemp> listadoPreventaTemporal = null;
		try
		{
			listadoPreventaTemporal = new PreventaProductoTempBLL().ObtenerPreventasProductoTempPor(clienteId, numeroPreventa);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
  		  	{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtner las preventasTemporales por clienteId y numeroPreventa: vacio");
  		  	}
  		  	else
  		  	{
  		  		myLog.InsertarLog("App",this.toString(),1,"Error al obtner las preventasTemporales por clienteId y numeroPreventa" + localException.getMessage());
  		  	}
		}
		
		if(listadoPreventaTemporal != null)
		{
			for(PreventaProductoTemp item : listadoPreventaTemporal)
			{
				montoFinal = montoFinal + item.get_montoFinal();
			}
		}
		
		return montoFinal;		
	}
	
	private boolean ValidarExistenciasAlmacenProductoDispositivo(int promocionId, int cantidad)
	{
		boolean validado = false;
		try
		{
			validado = new PromocionBLL().ExistePromocionesConSaldoEnAlmacenProducto(promocionId,cantidad);
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

    	return validado;
	}

	/*
	private void InsertarPreventaPromocionTemp(PreventaProducto localPreventaProducto)
	{
	    pdInsertarPreventaPromocion = new ProgressDialog(this);
	    pdInsertarPreventaPromocion.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdInsertarPreventaPromocion.setTitle("Preventa promocion");
	    pdInsertarPreventaPromocion.setMessage("Insertando detalles preventa ...");
	    pdInsertarPreventaPromocion.setCancelable(false);
	    pdInsertarPreventaPromocion.setMax(100);
	    pdInsertarPreventaPromocion.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar",new DialogInterface.OnClickListener() 
	    {	
	    	@Override
	    	public void onClick(DialogInterface dialog, int which) 
	    	{
	    		if(pdInsertarPreventaPromocion.isShowing())
	    		{
	    			pdInsertarPreventaPromocion.dismiss();
	    		}
	    	}
	    });
	    
	   WSInsertarPreventaProductoTemp localWSInsertarPreventaProductoTemp = new WSInsertarPreventaProductoTemp(localPreventaProducto);
	    
	    try
	    {
	      localWSInsertarPreventaProductoTemp.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarPreventaProductoTemp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarPreventaProductoTemp: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertarPreventaProductoTemp", 1);
	    	return;
	    }
	}
	*/

	/*
	public class WSInsertarPreventaProductoTemp extends AsyncTask<Void, Integer, Boolean>
	{
		String PREVENTAPRODUCTO_METHOD_NAME = "InsertPreVentaProductoTemp";
		String PREVENTAPRODUCTO_SOAP_ACTION = NAMESPACE + PREVENTAPRODUCTO_METHOD_NAME;
		 
		private PreventaProducto _preventaProducto;
		 
		boolean WSinsertarPreVentaProductoTemp;
		int resultadoInt;
		String resultadoString;
		SoapObject localSoapObjects;
	    
	    public WSInsertarPreventaProductoTemp(PreventaProducto paramPreventaProducto)
	    {
	    	_preventaProducto = paramPreventaProducto;
	    }
	    	    
	    protected void onPreExecute()
	    {
	    	pdInsertarPreventaPromocion.show();
	    }
	    
	    protected Boolean doInBackground(Void... paramVarArgs)
	    {
	    	WSinsertarPreVentaProductoTemp = false;
	      
	   		SoapObject localSoapObject = new SoapObject(NAMESPACE, this.PREVENTAPRODUCTO_METHOD_NAME);
	   		localSoapObject.addProperty("productoId", _preventaProducto.get_productoId());
	   		localSoapObject.addProperty("promocionId", _preventaProducto.get_promocionId());
	   		localSoapObject.addProperty("cantidad", _preventaProducto.get_cantidad());
	   		localSoapObject.addProperty("cantidadPaquete", _preventaProducto.get_cantidadPaquete());
	   		localSoapObject.addProperty("monto", String.valueOf(_preventaProducto.get_monto()));
	   		localSoapObject.addProperty("descuento", String.valueOf(_preventaProducto.get_descuento()));
	   		localSoapObject.addProperty("montoFinal", String.valueOf(_preventaProducto.get_montoFinal()));
	   		localSoapObject.addProperty("costoId", _preventaProducto.get_costoId());
	   		localSoapObject.addProperty("clienteId", clienteId);
	   		localSoapObject.addProperty("empleadoId", loginEmpleado.get_empleadoId());
	   		localSoapObject.addProperty("precioId",_preventaProducto.get_precioId());
	   		localSoapObject.addProperty("almacenId",(loginEmpleado.get_almacenId()));
	   		localSoapObject.addProperty("nroPreVenta",noPreventa);
	   		localSoapObject.addProperty("descuentoCanal",String.valueOf(_preventaProducto.get_descuentoCanal()));
    		localSoapObject.addProperty("descuentoAjuste",String.valueOf(_preventaProducto.get_descuentoAjuste()));
    		localSoapObject.addProperty("canalRutaPrecioId",_preventaProducto.get_canalPrecioRutaId());
    		localSoapObject.addProperty("descuentoProntoPago",String.valueOf(_preventaProducto.get_descuentoProntoPago()));
	       
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
	   			
	   			if(resultadoString.equals("OK") && resultadoInt > 0)
	   			{
	   				WSinsertarPreVentaProductoTemp = true;
	   			}
	   			return true;
		    	}
	   		catch (Exception localException)
	   		{
   			WSinsertarPreVentaProductoTemp = false;
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
	    	if(pdInsertarPreventaPromocion.isShowing())
	    	{
	    		pdInsertarPreventaPromocion.dismiss();
	    	}
	    	
	    	if(ejecutado) 
	    	{
	    		if(WSinsertarPreVentaProductoTemp || (resultadoString != null && resultadoString.equals("Preventa Producto Temp Repetido") && resultadoInt <= 0))
	    		{
	    			long l = 0;
	    			try
	    			{
	    				l = new PreventaProductoBLL().ModificarPreventaProductoNoSincronizadaPor(_preventaProducto.get_id());
					}
					catch (Exception localException)
	    			{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del producto de la preventa: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del producto de la preventa: " + localException.getMessage());
						} 
					}
						
	    			if(l==0)
	    			{
	    				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del producto de la preventa.", 1);
	    				return;
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "Producto de la preventa sincronizado.", 1);
							
						if(ObtenerPreventasProductoNoSincronizadas(_preventaProducto.get_preventaId()))
						{
							SincronizarPreventaProducto();
						}
						else
						{
							InsertarPreVenta();
						}
					}
	    		}
	    		else 
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo ejecutar el webservice WSInsertPreVentaProductoTemp.", 1);
	    			MostrarPantallaMenuVendedor();
	        	}
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "El WebService InsertPreVentaProductoTemp no se ejecuto correctamente. ", 1);
	    		return;
	    	}
	    }
	 }
	 */
	
	private boolean InsertarPreventaProductoTempDispositivo(PreventaProductoTemp paramPreventaProductoTemp)
	{
		long l = 0;
		try
		{
			l = new PreventaProductoTempBLL().InsertarPreventaProductoTemp(paramPreventaProductoTemp.get_tempId(),
					 paramPreventaProductoTemp.get_clienteId(), paramPreventaProductoTemp.get_productoId(), 
					 paramPreventaProductoTemp.get_cantidad(), paramPreventaProductoTemp.get_cantidadPaquete(),
					 paramPreventaProductoTemp.get_monto(), paramPreventaProductoTemp.get_descuento(),
					 paramPreventaProductoTemp.get_montoFinal(), paramPreventaProductoTemp.get_empleadoId(),
					 paramPreventaProductoTemp.get_promocionId(),paramPreventaProductoTemp.get_costo(),
					 paramPreventaProductoTemp.get_costoId(),paramPreventaProductoTemp.get_noPreventa(),
					 paramPreventaProductoTemp.get_precioId(),paramPreventaProductoTemp.get_descuentoCanal(),
					 paramPreventaProductoTemp.get_descuentoAjuste(),paramPreventaProductoTemp.get_canalPrecioRutaId(),
					 paramPreventaProductoTemp.get_descuentoProntoPago());
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar la promocionProductoTemp en el dispositivo: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la promocionProductoTemp en el dispositivo: " + localException.getMessage());
	    	}
	    	return false;
	    }
	    	
	    if(l<=0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la promocionProductoTemp en el dispositivo.", 1);
	  	     return false;		
	    }
	    else
	    {
	    	return true;
	    }
	 }

	private void ConfirmarPreventa()
	{
		btnConfirmarPreventa.setVisibility(View.INVISIBLE);
		
		if(ValidarMontoNit())
		{
			if(ValidarMontoCondicionTributaria(montoTributarioServer))
			{
				if(PrepararPreventaParaInsercion())
				{
					if(InsertarPreventaCompletaDispositivo())
					{
						if(ModificarEstadoAtencionClientePreventa())
						 {
							if(clienteId > 0)
							{
								if(ObtenerPreventasProductoNoSincronizadas(preventaIdDispositivo))
								{
									//SincronizarPreventaProducto();
									SincronizarPreventaJS();
								}
								else
								{
									theUtilidades.MostrarMensaje(getApplicationContext(), "No existen productos de la preventa que sincronizar.", 1);
									return;
								}
							}
							else
							{
								theUtilidades.MostrarMensaje(getApplicationContext(), "Preventa registrada. Para ingresarla debe sincronizar el cliente.", 1);
								MostrarPantallaMenuVendedor();
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
					theUtilidades.MostrarMensaje(getApplicationContext(), "La preventa no se genero correctamente.", 1);
					return;
				}
			}
		}
		else
		{
			dialog = new Dialog(Vendedorpreventapromocion.this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.diseniodialogo_vendedorcierredia);
			dialog.setTitle("NIT Invalido !");
			dialog.setCancelable(false);
			dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.colorBluePale));
			
			TextView tvMensaje = (TextView)dialog.findViewById(R.id.tvDialogoMensaje);
			tvMensaje.setText("Por el monto de la preventa (" + parametroGeneral.get_montoNit() + ") Bs."
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
				validado = false;
				btnConfirmarPreventa.setVisibility(View.VISIBLE);
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
		if(listadoPreventaProductoTemp != null && listadoPreventaProductoTemp.size() > 0)
		{
			for(PreventaProductoTemp item : listadoPreventaProductoTemp)
	    	{
	    		montoFinal += item.get_montoFinal();
	    	}
		}
		return montoFinal;
	}
	
	private boolean PrepararPreventaParaInsercion()
	{
	    float monto = 0;
	    float descuento = 0;
	    float montoFinal = 0;
	    Ubicacion localUbicacion = null;
	    double latitud = 0;
	    double longitud = 0;
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
	    
	    if(listadoPreventaProductoTemp.size() > 0)
	    {
	    	for(PreventaProductoTemp item : listadoPreventaProductoTemp)
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
	    	
	    	preventa = new Preventa(0, 0, loginEmpleado.get_empleadoId(), clienteId,monto,
									descuento, montoFinal, tipoPagoId, latitud, longitud, false,
									fecha.get_dia(), fecha.get_mes(), fecha.get_anio(), fecha.get_hora(),
									fecha.get_minuto(), factura, nit, nitNuevo, noPreventa,
									etObservacion.getText().toString(), aplicarBonificacion, diaEntrega,
									mesEntrega, anioEntrega, dosificacionId, tipoNit, clientePreventa.get_ruta(),
									clientePreventa.get_tipoVisita(), clientePreventa.get_zonaVentaId(), 
									0, descuentoCanal, descuentoAjuste,	descuentoProntoPagoUni, descuentoObjetivo,
									1, "", true, false, 0);
	    	return true;
	    }
	    else
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No existe items en la preventa.", 1);
		    return false;
	    }
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

	/*
	private void InsertarPreVenta()
	{
		pdInsertarPreventa = new ProgressDialog(this);
		pdInsertarPreventa.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdInsertarPreventa.setTitle("Preventa");
		pdInsertarPreventa.setMessage("Insertando preventa ...");
		pdInsertarPreventa.setCancelable(false);
		pdInsertarPreventa.setMax(100);
		 	 
		WSInsertarPreventa localWSInsertarPreventa = new WSInsertarPreventa();
		try
		{
			localWSInsertarPreventa.execute();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventa: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice insertPreVenta.", 1);
			return;
		}
	}
	*/
	
	/*
	private class WSInsertarPreventa extends AsyncTask<Void, Integer, Boolean>
	 {
		 String INSERTPREVENTA_METHOD_NAME = "InsertPreVenta";
		 String INSERTPREVENTA_SOAP_ACTION = NAMESPACE + INSERTPREVENTA_METHOD_NAME;
		 
		 boolean WSInsertarPreventa = false;
		 int intResultado;
		 SoapObject soapResultado;
		 String stringResultado;
		 
		 protected void onPreExecute()
		 {
			 pdInsertarPreventa.show();
		 }
	    
		 protected Boolean doInBackground(Void... paramVarArgs)
		 {
			 WSInsertarPreventa = false;
			 
			SoapObject localSoapObject = new SoapObject(NAMESPACE,INSERTPREVENTA_METHOD_NAME);
			localSoapObject.addProperty("clienteId", Integer.valueOf(preventa.get_clienteId()));
			localSoapObject.addProperty("empleadoId", Integer.valueOf(preventa.get_empleadoId()));
			localSoapObject.addProperty("tipoPagoId", Integer.valueOf(preventa.get_tipoPagoId()));
			localSoapObject.addProperty("monto", String.valueOf(preventa.get_monto()));
			localSoapObject.addProperty("descuento", String.valueOf(preventa.get_descuento()));
			localSoapObject.addProperty("montoFinal", String.valueOf(preventa.get_montoFinal()));
			localSoapObject.addProperty("latitud", String.valueOf(preventa.get_latitud()));
			localSoapObject.addProperty("longitud", String.valueOf(preventa.get_longitud()));
			localSoapObject.addProperty("numeroItems", Integer.valueOf(listadoPreventaProductoTemp.size()));
			localSoapObject.addProperty("dia", String.valueOf(preventa.get_dia()));
			localSoapObject.addProperty("mes", String.valueOf(preventa.get_mes()));
			localSoapObject.addProperty("anio", String.valueOf(preventa.get_anio()));
			localSoapObject.addProperty("hora", String.valueOf(preventa.get_hora()));
			localSoapObject.addProperty("minuto", String.valueOf(preventa.get_minuto()));
			localSoapObject.addProperty("nombreFactura", String.valueOf(preventa.get_factura()));
			localSoapObject.addProperty("nit", String.valueOf(preventa.get_nit()));
			localSoapObject.addProperty("nitNuevo",String.valueOf(preventa.is_nitNuevo()));
			localSoapObject.addProperty("almacenId",(loginEmpleado.get_almacenId()));
			localSoapObject.addProperty("rutaId",(clientePreventa.get_rutaId()));
			localSoapObject.addProperty("diaId",(clientePreventa.get_diaId()));
			localSoapObject.addProperty("nroPreVenta",(preventa.get_noPreventa()));
			localSoapObject.addProperty("observacion",(preventa.get_observacion()));
			localSoapObject.addProperty("aplicaBonificacion",String.valueOf(preventa.is_aplicarBonificacion()));
			localSoapObject.addProperty("diaEntrega",diaEntrega);
			localSoapObject.addProperty("mesEntrega",mesEntrega);
			localSoapObject.addProperty("anioEntrega",anioEntrega);
			localSoapObject.addProperty("dosificacionId",preventa.get_dosificacionId());
			localSoapObject.addProperty("tipoNit",preventa.get_tipoNit());
			localSoapObject.addProperty("ruta",preventa.get_ruta());
			localSoapObject.addProperty("tipoVisita",preventa.get_tipoVisita());
			localSoapObject.addProperty("zonaVentaId",preventa.get_zonaVentaId());
			localSoapObject.addProperty("prontoPagoId",preventa.get_prontoPagoId());
			localSoapObject.addProperty("descuentoCanal",String.valueOf(preventa.get_descuentoCanal()));
			localSoapObject.addProperty("descuentoAjuste",String.valueOf(preventa.get_descuentoAjuste()));
			localSoapObject.addProperty("descuentoProntoPago",String.valueOf(preventa.get_descuentoProntoPago()));
			localSoapObject.addProperty("descuentoObjetivo",String.valueOf(preventa.get_descuentoObjetivo()));
			localSoapObject.addProperty("formaPagoId", preventa.get_formaPagoId());
			localSoapObject.addProperty("codTransferencia", String.valueOf(preventa.get_codTransferencia()));
			localSoapObject.addProperty("fromApk", String.valueOf(preventa.is_fromApk()));
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(INSERTPREVENTA_SOAP_ACTION, localSoapSerializationEnvelope);
				
				soapResultado = ((SoapObject)localSoapSerializationEnvelope.getResponse());
				if(soapResultado!=null)
				{
					intResultado = Integer.parseInt(soapResultado.getPropertyAsString("Id"));
			        stringResultado = soapResultado.getPropertyAsString("Resultado");
			        preventaIdServer = 0;
				}
				
				if(intResultado > 0)
				{
					preventaIdServer = intResultado;
					WSInsertarPreventa = true;
				}
	            return true;
			}
			catch (Exception localException)
			{
				WSInsertarPreventa = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventa: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventa: " + localException.getMessage());
				}
				return true;
			}
		 }
		 
		 protected void onPostExecute(Boolean ejecutado)
		 {
			 if(pdInsertarPreventa.isShowing())
			 {
				 pdInsertarPreventa.dismiss();
			 }
			 
			 if(ejecutado)
			 {
				 if(WSInsertarPreventa) 
				 {
					 String[] datos = stringResultado.split("\\|");
					 Float monto = Float.valueOf(datos[0].toString());
					 Float descuento = Float.valueOf(datos[1].toString());
					 Float montoFinal = Float.valueOf(datos[2].toString());
					 
					 long l = 0;
					 try
					 {
						 l = new PreventaBLL().ModificarPreventaNoSincronizadaPor(preventaIdDispositivo,monto,descuento,montoFinal);
					 }
					 catch (Exception localException)
					 {
						 if(localException.getMessage() == null || localException.getMessage().isEmpty())
						 {
							 myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la preventa: vacio");
						 }
						 else
						 {
							 myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la preventa: " + localException.getMessage());
						 } 
					}
							
					 if(l==0)
					 {
						 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion de la preventa.", 1);
						 return;
					 }
					 
					 l = 0;
					 
					 try
					 {
						 l = new PreventaBLL().ModificarPreventaIdServer(preventaIdDispositivo,intResultado);
					 }
					 catch (Exception localException)
					 {
						 if(localException.getMessage() == null || localException.getMessage().isEmpty())
						 {
							 myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la preventa: vacio");
						 }
						 else
						 {
							 myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la preventa: " + localException.getMessage());
						 } 
					}
							
					 if(l==0)
					 {
						 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la preventaIdServer de la preventa.", 1);
						 return;
					 }
					 
					 theUtilidades.MostrarMensaje(getApplicationContext(), "Preventa insertada.", 1);						 
					 ObtenerAlmacenProducto();					 
				 }
				 else
				 {
					 theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo ejecutar el webservice WSInsertPreVenta.", 1);
					 MostrarPantallaMenuVendedor();
				 }
			 }
			 else
			 {
				 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertPreventa.", 1);
				 return;
			 }
		 }
	 }
	 */
	
	private boolean InsertarPreventaCompletaDispositivo()
	{
		if(ActualizarClientePreventaMontoCredito())
		{
			if(InsertarPreventaDispositivo())
			{
				if(InsertarPreventaProductoDispositivo())
				{
					if(BorrarPreventasProductoTempDispositivo())
					{
						return true;
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se elimino los items de la preventa temporal en el dispositivo.", 1);
						return false;
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se inserto los items de la preventa en el dispositivo.", 1);
					return false;
				}
			 }
			 else
			 {
				 theUtilidades.MostrarMensaje(getApplicationContext(), "No se inserto la preventa en el dispositivo.", 1);
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
				a = new ClientePreventaBLL().ModificarClientePreventaMontoCredito(clientePreventa.get_clienteId(), 
						clientePreventa.get_montoPendienteCredito() + preventa.get_montoFinal());
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
	
	private boolean InsertarPreventaDispositivo()
	{
		preventaIdDispositivo = 0;
		Fecha fecha = theUtilidades.ObtenerFecha();
		
		try
		{
			preventaIdDispositivo = ((int) new PreventaBLL().InsertarPreventa(0, loginEmpleado.get_empleadoId(),clienteId,
					 preventa.get_monto(), preventa.get_descuento(), preventa.get_montoFinal(), preventa.get_tipoPagoId(),
					 preventa.get_latitud(), preventa.get_longitud(), false, fecha.get_dia(), fecha.get_mes(), fecha.get_anio(),
					 fecha.get_hora(), fecha.get_minuto(), preventa.get_factura(), preventa.get_nit(), preventa.is_nitNuevo(),
					 preventa.get_noPreventa(), preventa.get_observacion(), aplicarBonificacion, preventa.get_diaEntrega(),
					 preventa.get_mesEntrega(), preventa.get_anioEntrega(), preventa.get_dosificacionId(), preventa.get_tipoNit(),
					 preventa.get_ruta(), preventa.get_tipoVisita(), preventa.get_zonaVentaId(), preventa.get_prontoPagoId(),
					 preventa.get_descuentoCanal(), preventa.get_descuentoAjuste(), preventa.get_descuentoProntoPago(),
					 preventa.get_descuentoObjetivo(), preventa.get_formaPagoId(), preventa.get_codTransferencia(),
					 preventa.is_fromApk(), preventa.is_fromShopp(), preventa.get_descuentoIncentivo()));
			preventa.set_Id(preventaIdDispositivo);
		}
		catch (Exception localException)
		{
	    	  if(localException.getMessage() == null || localException.getMessage().isEmpty()) 
	    	  {
	    		  myLog.InsertarLog("App", toString(), 1, "Error al insertar la preventa en el dispositivo: vacio");
	    	  } 
	    	  else 
	    	  {
	    		  myLog.InsertarLog("App", toString(), 1, "Error al insertar la preventa en el dispositivo: " + localException.getMessage());
	    	  }
		}
		 
		if(preventaIdDispositivo == 0)
		{
			return false;
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "Preventa insertada en el dispositivo.", 1);
			return true;
		}
	 }

	private boolean InsertarPreventaProductoDispositivo()
	 {
		 for(PreventaProductoTemp item : listadoPreventaProductoTemp)
		 {
			 long l = 0;
			 try
			 {
				 l = new PreventaProductoBLL().InsertarPreventaProducto(preventaIdDispositivo,item.get_productoId(),
						 					item.get_cantidad(),item.get_cantidadPaquete(), item.get_monto(),item.get_descuento(), 
						 					item.get_montoFinal(),item.get_empleadoId(),item.get_promocionId(),false,
						 					item.get_costo(),item.get_costoId(),item.get_noPreventa(),item.get_precioId(),
						 					item.get_descuentoCanal(),item.get_descuentoAjuste(),item.get_canalPrecioRutaId(),
						 					item.get_descuentoProntoPago());
			 }
			 catch(Exception localException)
			 {
				 if(localException.getMessage() == null || localException.getMessage().isEmpty())
				 {
					 myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preventaProductoTemp en el dispositivo: vacio");
				 }
				 else
				 {
					 myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preventaProductoTemp en el dispositivo: " + localException.getMessage());
				 } 
			 }
			 
			 if(l == 0)
			 {
				return false; 
			 }
		 }
		 return true;
	 }

	private boolean BorrarPreventasProductoTempDispositivo()
	 {
		 boolean bool = false;
		 try
		 {
			 bool = new PreventaProductoTempBLL().BorrarPreventaProductoTempPorClienteIdEmpleadoId(clienteId, loginEmpleado.get_empleadoId());
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasProductoTemp: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasProductoTemp: " + localException.getMessage());
			 }
			 return false;
		 }
	    
		 if(bool)
		 {
			 return true;
		 }
		 else
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar las preventas temporales del dispositivo.", 1);
			 return false;
		 }
	}
	
	private boolean ModificarEstadoAtencionClientePreventa()
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
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estadoAtencion del clientePreventa: " + localException.getMessage());
			} 
		 }
		 
		 if(m<=0)
		 {
			 return false;			 
		 }
		 else
		 {
			 return true;
		 }
	 }
	
	/*
	private void ObtenerAlmacenProducto()
	 {
		 pdEsperaObtenerAlmacenProducto = new ProgressDialog(this);
		 pdEsperaObtenerAlmacenProducto.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		 pdEsperaObtenerAlmacenProducto.setTitle("Preventa producto");
	     pdEsperaObtenerAlmacenProducto.setMessage("Obteniendo productos del almacen ...");
	     pdEsperaObtenerAlmacenProducto.setCancelable(false);
	     pdEsperaObtenerAlmacenProducto.setMax(100);
	     pdEsperaObtenerAlmacenProducto.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar",new DialogInterface.OnClickListener() 
	     {	
	     	@Override
	     	public void onClick(DialogInterface dialog, int which) 
	     	{
	     		if(pdEsperaObtenerAlmacenProducto.isShowing())
	     		{
	     			pdEsperaObtenerAlmacenProducto.dismiss();
	     		}
	     	}
	     });
	         
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
	    	return;
	    }
	 }
	*/
	
	/*
	private class WSObtenerAlmacenProducto extends AsyncTask<Void, Integer, Boolean>
	 {
	    String ALMACENPRODUCTO_METHOD_NAME = "GetProductosByAlmacenTemp";
	    String ALMACENPRODUCTO_SOAP_ACTION = NAMESPACE + ALMACENPRODUCTO_METHOD_NAME;
	    
	    boolean WSObtenerAlmacenProducto = false;
	    SoapObject soapAlmacenProductos;
	    int totalItems;
	    
	    protected void onPreExecute()
	    {
	    	pdEsperaObtenerAlmacenProducto.show();
	    }
	    
	    protected Boolean doInBackground(Void... paramVarArgs)
	    {
	    	WSObtenerAlmacenProducto = false;
	      
	    	SoapObject localSoapObject = new SoapObject(NAMESPACE,ALMACENPRODUCTO_METHOD_NAME);
	    	localSoapObject.addProperty("almacenId", Integer.valueOf(loginEmpleado.get_almacenId()));
	    	SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
	    	localSoapSerializationEnvelope.dotNet = true;
	    	localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
	    	HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
	      
	    	try
	    	{
	    		localHttpTransportSE.call(ALMACENPRODUCTO_SOAP_ACTION,localSoapSerializationEnvelope);
	        
	    		soapAlmacenProductos = ((SoapObject)((SoapObject)localSoapSerializationEnvelope.bodyIn).getProperty("GetProductosByAlmacenTempResult"));
	    		if(soapAlmacenProductos != null)
	    		{
	    			totalItems = soapAlmacenProductos.getPropertyCount();
   			}
	    		
	    		if(totalItems > 0) 
	    		{
	    			WSObtenerAlmacenProducto = true;
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
	    	
	    	if(ejecutado)
	    	{
	    		if(WSObtenerAlmacenProducto)
	    		{
	    			if(BorrarAlmacenesProducto())
	    			{
	    				for(int i=0; i<totalItems; i++)
	    				{
	    					long l = 0;
	    					
	    					SoapObject localSoapObject = (SoapObject)soapAlmacenProductos.getProperty(i);
	    					
	    					try
	    					{
	    						l = new AlmacenProductoBLL().InsertarAlmacenProducto(
	    								Integer.parseInt(localSoapObject.getPropertyAsString("AlmacenId")), 
	    								Integer.parseInt(localSoapObject.getPropertyAsString("ProductoId")),
	    								Integer.parseInt(localSoapObject.getPropertyAsString("SaldoUnitario")), 
	    								Integer.parseInt(localSoapObject.getPropertyAsString("SaldoPaquete")), 
	    								Float.parseFloat(localSoapObject.getPropertyAsString("CostoUnitario")), 
	    								Float.parseFloat(localSoapObject.getPropertyAsString("CostoPaquete")));
	    						
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
	    				}
	    				
	    				theUtilidades.MostrarMensaje(getApplicationContext(), "Almacen dispositivo actualizado.", 1);
	    				
	    				if((parametroGeneral.is_habilitarPop() || parametroGeneral.is_habilitarCambio()) && ObtenerRolEmpleado() == 3)//Rol 3 = vendedor
	    				{
	    					MostrarPantallaMenu();;
	    				}
	    				else
	    				{
	    					MostrarPantallaMenuVendedor();
	    				}
	    			}
   				else
   				{
   					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo eliminar los prodcutos del almacen.", 1);
   			        return;
   				}
	    		}
	    		else
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetProductosByAlmacenTemp no se ejecuto correctamente. ", 1);
	    			MostrarPantallaMenuVendedor();
	    		}
	    	}
	    }
	}
	*/
	 
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

	private boolean ObtenerPreventasProductoNoSincronizadas(int preventaId)
	{
		listadoPreventaProductoNoSincronizadas = null;
	    try
	    {
	    	listadoPreventaProductoNoSincronizadas = new PreventaProductoBLL().ObtenerPreventasProductoNoSincronizadasPor(preventaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la preventa no sincronizada por preventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la preventa no sincronizada por preventaId: " + localException.getMessage());
	    	}
	    }
	    
	    if (listadoPreventaProductoNoSincronizadas == null) 
	    {
 	        return false;
	    }  
	    else
	    {
	    	return true;
	    }
	}
	
	/*
	private void SincronizarPreventaProducto() 
	{
		if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
		{
			if(listadoPreventaProductoNoSincronizadas != null && listadoPreventaProductoNoSincronizadas.size()>0)
			{
				InsertarPreventaPromocionTemp(listadoPreventaProductoNoSincronizadas.get(0));
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No existen productos de la preventa que sincronizar.", 1);
				return;
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No esta conectado a internet, intentelo mas tarde.", 1);
			
			if((parametroGeneral.is_habilitarPop() || parametroGeneral.is_habilitarCambio()) && ObtenerRolEmpleado() == 3)//Rol 3 = vendedor
			{
				MostrarPantallaMenu();
			}
			else
			{
				MostrarPantallaMenuVendedor();
			}
		}
	}
	*/
	
	private void SincronizarPreventaJS() 
	{
		if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
		{
			PreventaJS preventaJS = null;
			ArrayList<PreventaProducto> listaPreventaProducto = null;
			ArrayList<PreventaProductoTempJS> listaPptJS = null;
			
			preventaJS = new PreventaJS(preventa.get_empleadoId(), preventa.get_clienteId(), preventa.get_monto(), preventa.get_descuento(), preventa.get_montoFinal(),
					preventa.get_tipoPagoId(), preventa.get_latitud(), preventa.get_longitud(), preventa.get_dia(), preventa.get_mes(), preventa.get_anio(), preventa.get_hora(),
					preventa.get_minuto(), preventa.get_factura(), preventa.get_nit(), preventa.is_nitNuevo(), loginEmpleado.get_almacenId(), clientePreventa.get_rutaId(),
					clientePreventa.get_diaId(), preventa.get_noPreventa(), preventa.get_observacion(), preventa.is_aplicarBonificacion(), preventa.get_diaEntrega(), 
					preventa.get_mesEntrega(), preventa.get_anioEntrega(), preventa.get_dosificacionId(), preventa.get_tipoNit(), preventa.get_ruta(), preventa.get_tipoVisita(),
					preventa.get_zonaVentaId(), preventa.get_prontoPagoId(), preventa.get_descuentoCanal(), preventa.get_descuentoAjuste(), preventa.get_descuentoProntoPago(),
					preventa.get_descuentoObjetivo(), preventa.get_formaPagoId(), preventa.get_codTransferencia(), preventa.is_fromApk(), null);
			
			try
			{
				listaPreventaProducto = new PreventaProductoBLL().ObtenerPreventasProductoPor(preventa.get_Id()); 
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
		    	{
		            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la preventa por preventaId: vacio");
		    	}
		    	else
		    	{
		    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la preventa por preventaId: " + localException.getMessage());
		    	}
			}
			
			if(listaPreventaProducto != null)
			{
				listaPptJS = new ArrayList<PreventaProductoTempJS>();
				
				for(PreventaProducto item : listaPreventaProducto)
				{
					PreventaProductoTempJS pptJS = new PreventaProductoTempJS(item.get_productoId(), item.get_promocionId(), item.get_cantidad(), item.get_cantidadPaquete(),
							item.get_monto(), item.get_descuento(), item.get_montoFinal(), item.get_costoId(), item.get_precioId(), item.get_descuentoCanal(), item.get_descuentoAjuste(),
							item.get_canalPrecioRutaId(), item.get_descuentoProntoPago());
					
					listaPptJS.add(pptJS);
				}
				
				preventaJS.set_listaPreventaProductoTempJS(listaPptJS);
				
				String stringPreventaJS = new ParseJSon().GenerarPreventaJSString(preventaJS);
				String preventaJSencriptada = "";
				
				try
				{
					preventaJSencriptada = new AES().Encrypt(stringPreventaJS, theUtilidades.get_KEY());
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
			    	{
			            myLog.InsertarLog("App",this.toString(),1,"Error al encriptar la preventaJS: vacio");
			    	}
			    	else
			    	{
			    		myLog.InsertarLog("App",this.toString(),1,"Error al encriptar la preventaJS: " + localException.getMessage());
			    	}
				}
				
				InsertarPreVentaJS(preventaJSencriptada);
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No esta conectado a internet, intentelo mas tarde.", 1);
			
			if((parametroGeneral.is_habilitarPop() || parametroGeneral.is_habilitarCambio()) && ObtenerRolEmpleado() == 3)//Rol 3 = vendedor
			{
				MostrarPantallaMenu();
			}
			else
			{
				MostrarPantallaMenuVendedor();
			}
		}
	}
	
	private void InsertarPreVentaJS(String preventaJS)
	 {
		 pdInsertarPreventaJS = new ProgressDialog(this);
		 pdInsertarPreventaJS.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		 pdInsertarPreventaJS.setMessage("Insertando preventa ...");
		 pdInsertarPreventaJS.setCancelable(false);
		 pdInsertarPreventaJS.setCanceledOnTouchOutside(false);
		 	 
		 WSInsertarPreventaJS localWSInsertarPreventaJS = new WSInsertarPreventaJS(preventaJS);
		 try
		 {
			 localWSInsertarPreventaJS.execute();
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventaJS: vacio");
			 }
	 		 else
		 	 {
	 			 myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventaJS: " + localException.getMessage());
		 	 }
			 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice insertPreVentaJS.", 1);
		     return;
		 }
	 }
	 
	private class WSInsertarPreventaJS extends AsyncTask<Void, Integer, Boolean>
	{
		 String INSERTPREVENTAJS_METHOD_NAME = "InsertPreventa";
		 String INSERTPREVENTAJS_SOAP_ACTION = NAMESPACE + INSERTPREVENTAJS_METHOD_NAME;
		 
		 String _preventaJS;
		 boolean WSInsertarPreventaJS = false;
		 int intResultado;
		 String stringResultado;
		 WSResultado wsResultado;
		 
		 public WSInsertarPreventaJS(String preventaJS)
		 {
			 this._preventaJS = preventaJS;
		 }
		 
		 protected void onPreExecute()
		 {
			 pdInsertarPreventaJS.show();
		 }
	    
		 protected Boolean doInBackground(Void... paramVarArgs)
		 {
			 WSInsertarPreventaJS = false;
			 
			SoapObject localSoapObject = new SoapObject(NAMESPACE,INSERTPREVENTAJS_METHOD_NAME);
			localSoapObject.addProperty("givenPreventa", this._preventaJS);
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URLJSON);
			try
			{
				localHttpTransportSE.call(INSERTPREVENTAJS_SOAP_ACTION, localSoapSerializationEnvelope);
			
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				Type type = new TypeToken<WSResultado>(){}.getType();
	            wsResultado = new Gson().fromJson(cadena, type);

	            if(wsResultado != null)
	            {
	            	intResultado = wsResultado.getId();
	            	stringResultado = wsResultado.getResultado();
	            }
	            
	            if(intResultado > 0)
	            {
	            	preventaIdServer = intResultado;
	            	WSInsertarPreventaJS = true;
	            }

	            return true;

			}			
			catch (Exception localException)
			{
				WSInsertarPreventaJS = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventaJS: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventaJS: " + localException.getMessage());
				}
				return true;
			}
		 }
		 
		 protected void onPostExecute(Boolean ejecutado)
		 {
			 if(pdInsertarPreventaJS.isShowing())
			 {
				 pdInsertarPreventaJS.dismiss();
			 }
			 
			 if(ejecutado)
			 {
				 if(WSInsertarPreventaJS) 
				 {
					 String[] datos = stringResultado.split("\\|");
					 Float monto = Float.valueOf(datos[0].toString().replace(",", ""));
					 Float descuento = Float.valueOf(datos[1].toString().replace(",", ""));
					 Float montoFinal = Float.valueOf(datos[2].toString().replace(",", ""));
					 
					 long l = 0;
					 try
					 {
						 l = new PreventaBLL().ModificarPreventaNoSincronizadaPor(preventaIdDispositivo,monto,descuento,montoFinal);
					 }
					 catch (Exception localException)
					 {
						 if(localException.getMessage() == null || localException.getMessage().isEmpty())
						 {
							 myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la preventa: vacio");
						 }
						 else
						 {
							 myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la preventa: " + localException.getMessage());
						 } 
					}
							
					 if(l==0)
					 {
						 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion de la preventa.", 1);
						 return;
					 }
					 
					 l = 0;
					 
					 try
					 {
						 l = new PreventaBLL().ModificarPreventaIdServer(preventaIdDispositivo,intResultado);
					 }
					 catch (Exception localException)
					 {
						 if(localException.getMessage() == null || localException.getMessage().isEmpty())
						 {
							 myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la preventa: vacio");
						 }
						 else
						 {
							 myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la preventa: " + localException.getMessage());
						 } 
					}
							
					 if(l==0)
					 {
						 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la preventaIdServer de la preventa.", 1);
						 return;
					 }
					 
					 theUtilidades.MostrarMensaje(getApplicationContext(), "Preventa insertada.", 1);						 
					 //ObtenerAlmacenProductoJS();
					 GetIncentivosByCliente(intResultado, monto, descuento, montoFinal);
				 }
				 else
				 {
					 theUtilidades.MostrarMensaje(getApplicationContext(), stringResultado, 1);
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
			 else
			 {
				 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertPreventa.", 1);
				 return;
			 }
		 }
	 }
	
	private void ObtenerAlmacenProductoJS()
	{
		 pdEsperaObtenerAlmacenProducto = new ProgressDialog(this);
		 pdEsperaObtenerAlmacenProducto.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	     pdEsperaObtenerAlmacenProducto.setMessage("Obteniendo productos del almacen ...");
	     pdEsperaObtenerAlmacenProducto.setCancelable(false);
	     pdEsperaObtenerAlmacenProducto.setCanceledOnTouchOutside(false);
	         
	    WSObtenerAlmacenProductoJS localWSObtenerAlmacenProductoJS = new WSObtenerAlmacenProductoJS();
	    try
	    {
	    	localWSObtenerAlmacenProductoJS.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetAlmacenTempJS: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetAlmacenTempJS: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetAlmacenTempJS.", 1);
	    }
	 }
	 
	private class WSObtenerAlmacenProductoJS extends AsyncTask<Void, Integer, Boolean>
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
	    	
	    	if(!ejecutado) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetProductosByAlmacenTemp no se ejecuto correctamente. ", 1);
				return;
			}

			if(!WSObtenerAlmacenProducto) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron productos del almacen que descargar. ", 1);
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
			if(!BorrarAlmacenesProducto())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo eliminar los prodcutos del almacen.", 1);
				return;
			}

			long i = 0;
			try
			{
				i = new AlmacenProductoBLL().InsertarAlmacenProducto(almacenProductoWSResults);
			}
			catch (Exception localException)
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

			if(i <= 0)
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
	
	private boolean ConsolidarProductosLotes()
	{
		ArrayList<AlmacenProducto> listadoAlmacenProducto = null;
		try
		{
			listadoAlmacenProducto = new AlmacenProductoBLL().ConsolidarProductosLote();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al consolidar los productos del almacen: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al consolidar los productos del almacen: " + localException.getMessage());
			}
		}
		
		if(listadoAlmacenProducto != null && listadoAlmacenProducto.size() > 0)
		{
			int borrado = 0;
			try
			{
				borrado = new AlmacenProductoBLL().deleteProductosLote();
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al consolidar los productos del almacen: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al consolidar los productos del almacen: " + localException.getMessage());
				}
			}
			
			if(borrado == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo eliminar el almacen de productos consolidados. ", 1);
				return false;
			}
			else
			{
				for(AlmacenProducto item : listadoAlmacenProducto)
				{
					long i = 0;
					try
					{
						i = new AlmacenProductoBLL().InsertarAlmacenProducto(item.get_almacenId(), item.get_productoId(), item.get_saldoUnitario(),
																			item.get_saldoPaquete(), item.get_costoUnitario(), item.get_costoPaquete());
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al insertar el producto al almacen consolidado: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al insertar el producto al almacen consolidado: " + localException.getMessage());
						}	
					}
					
					if(i==0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el item al almacen de productos consolidados. ", 1);
						return false;
					}
				}
				
				return true;
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontraron productos que consolidar. ", 1);
			return false;
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
			
			if(listadoPreventaProductoTemp !=null && listadoPreventaProductoTemp.size() >0)
			{
				for(PreventaProductoTemp item : listadoPreventaProductoTemp)
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
			
			if(listadoPreventaProductoTemp !=null && listadoPreventaProductoTemp.size() >0)
			{
				for(PreventaProductoTemp item : listadoPreventaProductoTemp)
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
    				ConfirmarPreventa();
				}	
   				else
   				{
   					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el nit acumulado.", 1);
   			        boolCondicionTributaria = true;
    				ConfirmarPreventa();
   				}
    		}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetNitAcumulado no se ejecuto correctamente. ", 1);

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
					ObtenerAlmacenProductoJS();
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetIncentivosByCliente.", 1);
			}
		}
	}

	private void DesplegarClienteIncentivos(int preventaId, float monto, float descuento, float montoFinal)
	{
		dialogIncentivo = new Dialog(Vendedorpreventapromocion.this);
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
				ObtenerAlmacenProductoJS();
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
		String INSERTARINCENTIVOSCLIENTE_METHOD_NAME = "AplicarDescuentoIncentivoPreVenta";
		String INSERTARINCENTIVOSCLIENTE_SOAP_ACTION = NAMESPACE + INSERTARINCENTIVOSCLIENTE_METHOD_NAME;

		WSResultado wsResulatdo;
		boolean WSInsertIncentivosCliente = false;
		int _preventaId;
		float _montoFinal;
		float _incentivo;
		String _incentivosId;
		String error;

		public WSInsertClienteIncentivo(int preventaId, float montoFinal, float incentivo, String incentivosId)
		{
			_preventaId = preventaId;
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
			String json = new JSonParser().GenerarAplicacionIncentivo(_preventaId, _incentivo, _incentivosId);
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
						l = new PreventaBLL().ModificarPreventaClienteIncentivo(_preventaId, _montoFinal, _incentivo);
					}
					catch (Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al insertar el descuento incentivo del cliente: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al insertar el descuento incentivo del cliente: " + localException.getMessage());
						}
					}

					if(l==0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el descuento incentivo del cliente.", 1);
					} else {
						ObtenerAlmacenProductoJS();
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron incentivos que descargar", 1);
					ObtenerAlmacenProductoJS();
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
	
	public void MostrarPantallaPreventaProducto()
	{
		Intent localIntent = new Intent(this, Vendedorpreventaproducto.class);
		localIntent.putExtra("clienteId", clienteId);
		localIntent.putExtra("tipoPagoId", tipoPagoId);
		localIntent.putExtra("factura", factura);
		localIntent.putExtra("tipoNit", tipoNit);
		localIntent.putExtra("nit", nit);
		localIntent.putExtra("nitNuevo", nitNuevo);
		localIntent.putExtra("observacion", observacion);
		localIntent.putExtra("aplicarBonificacion", aplicarBonificacion);
		localIntent.putExtra("diaEntrega",diaEntrega);
		localIntent.putExtra("mesEntrega",mesEntrega);
		localIntent.putExtra("anioEntrega",anioEntrega);
		localIntent.putExtra("dosificacionId",dosificacionId);
		localIntent.putExtra("venderCredito",venderCredito);
		localIntent.putExtra("aplicarProntoPago",aplicarProntoPago);
		startActivity(localIntent);
	}

	public void MostrarPantallaPreventaFactura()
	 {
		 Intent localIntent = new Intent(this, Vendedorpreventanits.class);
		 localIntent.putExtra("clienteId", clienteId);
		 localIntent.putExtra("tipoPagoId", tipoPagoId);
		 localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		 startActivity(localIntent);
	 }
	
	public void MostrarPantallaMenu()
	{
		Intent localIntent = new Intent(this, Vendedorpreventamenuopciones.class);
		localIntent.putExtra("preventaIdDispositivo", preventaIdDispositivo);
		localIntent.putExtra("preventaIdServer", preventaIdServer);
		localIntent.putExtra("clienteId", clienteId);
		startActivity(localIntent);
	}
		
	public void MostrarPantallaMenuOpciones()
	{
		Intent localIntent = new Intent(this, Vendedorpreventamenuopciones.class);
		localIntent.putExtra("preventaIdDispositivo", preventaIdDispositivo);
		localIntent.putExtra("preventaIdServer", preventaIdServer);
		localIntent.putExtra("clienteId", clienteId);
		startActivity(localIntent);
	}
	
	@Override
	public void onBackPressed() 
	{
		if(listadoPreventaProductoTemp != null)
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(),"Debe confirmar la preventa o eliminar todos los productos registrados.", 1);
			 return;
		 }
		 else
		 {
			super.onBackPressed();
			MostrarPantallaMenuVendedor();
		 }
	}
}