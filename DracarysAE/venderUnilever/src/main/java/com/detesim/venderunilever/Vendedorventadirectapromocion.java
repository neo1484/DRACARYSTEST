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
import BLL.ClientePreventaBLL;
import BLL.CondicionTributariaBLL;
import BLL.DosificacionProveedorBLL;
import BLL.DraRebateSaldoBLL;
import BLL.MyLogBLL;
import BLL.ParametroGeneralBLL;
import BLL.PrecioListaBLL;
import BLL.PreventaBLL;
import BLL.ProductoBLL;
import BLL.PromocionBLL;
import BLL.PromocionCostoBLL;
import BLL.PromocionPrecioBLL;
import BLL.PromocionPrecioListaBLL;
import BLL.PromocionTipoNegocioBLL;
import BLL.ProveedorBLL;
import BLL.RolBLL;
import BLL.VentaDirectaBLL;
import BLL.VentaDirectaProductoBLL;
import BLL.VentaDirectaProductoTempBLL;
import Clases.AES;
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
import Clases.PrecioLista;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Vendedorventadirectapromocion extends Activity implements OnClickListener
{
	LinearLayout llVendedorVentaDirectaPromocion;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	String URLJSON = theUtilidades.get_URLJSON();
	LoginEmpleado loginEmpleado;
	MyLogBLL myLog = new MyLogBLL();
	
	ArrayList<VentaDirectaProductoTemp> listadoVentaDirectaProductoTemp ;
	ArrayList<VentaDirectaProductoTemp> listadoVentaDirectaProductoTempNoSincronizados;
	VentaDirecta ventaDirecta;
	Promocion promocion;
	ArrayList<Proveedor> listadoProveedor;
	ClientePreventa clientePreventa;
	int clienteId;
	int tipoPagoId;
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
	PromocionPrecio promocionPrecio;
	boolean aplicarBonificacion;
	int dosificacionId;
	boolean boolCondicionTributaria = false;
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
	TextView tvDsctoObjetivoTxt;
	TextView tvDsctoObjetivo;
	TextView tvDsctoObjetivoTotalTxt;
	TextView tvDsctoObjetivoTotal;
	TextView tvMontoFinalTxt;
	TextView tvMontoFinal;
	TextView tvObservacion;
	EditText etObservacion;
	
	ImageView ivProductos;
	Button btnMostrarDetalles;
	Button btnAdicionarPromocion;
	Button btnConfirmarVentaDirecta;
	CheckBox cbAplicarBonificacion;

	EditText etCantidad;
	Spinner spnProveedor;
	Spinner spnPromocion;
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
		setContentView(R.layout.activity_vendedorventadirectapromocion);
		
		llVendedorVentaDirectaPromocion = (LinearLayout)findViewById(R.id.llVendedorVentaDirectaPromocion);
		ivProductos = (ImageView)findViewById(R.id.imgbtnVendedorVentaDirectaPromocion);
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
		lvVentaDirectaProductoTemp = (ListView)findViewById(R.id.lvVendedorVentaDirectaPromocionPreventas);
		tvTotaltxt = (TextView)findViewById(R.id.tvVendedorVentaDirectaPromocionTotalTxt);
		tvTotal = (TextView)findViewById(R.id.tvVendedorVentaDirectaPromocionTotal);
		tvDsctoObjetivoTxt = (TextView)findViewById(R.id.tvVenVenDirPreProPromoDescuentoObjetivoTxt);
	    tvDsctoObjetivo = (TextView)findViewById(R.id.tvVenVenDirPreProPromoDescuentoObjetivo);
	    tvDsctoObjetivoTotalTxt = (TextView)findViewById(R.id.tvVenVenDirPreProPromoDescuentoObjetivoTotalTxt);
	    tvDsctoObjetivoTotal = (TextView)findViewById(R.id.tvVenVenDirPreProPromoDescuentoObjetivoTotal);
	    tvMontoFinalTxt = (TextView)findViewById(R.id.tvVenVenDirMontoFinalTxt);
	    tvMontoFinal = (TextView)findViewById(R.id.tvVenVenDirMontoFinal);
		cbAplicarBonificacion = (CheckBox)findViewById(R.id.cbVendedorVentaDirectaAplicarBonificacion);
		cbAplicarBonificacion.setOnClickListener(this);
		tvObservacion = ((TextView)findViewById(R.id.tvVendedorVentaDirectaPromocionObservacion));
	    etObservacion = ((EditText)findViewById(R.id.etVendedorVentaDirectaPromocionObservacion));
		btnConfirmarVentaDirecta = (Button)findViewById(R.id.btnVendedorVentaDirectaPromocionConfirmarVentaDirecta);
		btnConfirmarVentaDirecta.setOnClickListener(this);
		
		llVendedorVentaDirectaPromocion.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
		clienteId = 0;
	    clienteId = getIntent().getExtras().getInt("clienteId");
	    if(clienteId == 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el clienteId.", 1);
	    	return;
	    }
	    
	    ventaDirectaIdServer = 0;
	    ventaDirectaIdServer = getIntent().getExtras().getInt("ventaDirectaIdServer");
	    
	    tipoPagoId=0;
	    tipoPagoId = getIntent().getExtras().getInt("tipoPagoId");
	    if(tipoPagoId == 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el tipoPagoId.", 1);
	    	return;
	    }
	    
	    venderCredito = false;
	    venderCredito = getIntent().getExtras().getBoolean("venderCredito");
	    
	    distribuidorId=0;
	    distribuidorId = getIntent().getExtras().getInt("distribuidorId");
	    if(distribuidorId == 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el distribuidorId.", 1);
	    	return;
	    }
	    
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
	    	return;
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
		case R.id.imgbtnVendedorVentaDirectaPromocion:
			MostrarPantallaVentaDirectaProducto();
			break;
		case R.id.btnVendedorVentaDirectaPromocionMostrarDetalles:
			ObtenerDatosSeleccion();
			break;		
		case R.id.btnVendedorVentaDirectaPromocionAdicionarPreventa:
			AdicionarVentaDirectaProductoTemporal();
			break;
		case R.id.btnVendedorVentaDirectaPromocionConfirmarVentaDirecta:
			ConfirmarVentaDirecta();
			break;
		case R.id.cbVendedorVentaDirectaAplicarBonificacion:
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
	    lvVentaDirectaProductoTemp.setVisibility(visibilidad);
	    tvTotaltxt.setVisibility(visibilidad);
	    tvTotal.setVisibility(visibilidad);
	    tvDsctoObjetivoTxt.setVisibility(visibilidad);
	    tvDsctoObjetivo.setVisibility(visibilidad);
	    tvDsctoObjetivoTotalTxt.setVisibility(visibilidad);
	    tvDsctoObjetivoTotal.setVisibility(visibilidad);
		tvMontoFinalTxt.setVisibility(visibilidad);
		tvMontoFinal.setVisibility(visibilidad);
	    cbAplicarBonificacion.setVisibility(visibilidad);
	    cbAplicarBonificacion.setVisibility(visibilidad);
	    tvObservacion.setVisibility(visibilidad);
	    etObservacion.setVisibility(visibilidad);
	    btnConfirmarVentaDirecta.setVisibility(visibilidad);
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
						listadoPromocion = new PromocionBLL().ObtenerPromocionesNoEnVentaDirectaProductoTemp(clienteId, 
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
	    	listadoPromocion = new PromocionBLL().ObtenerPromocionesNoEnVentaDirectaProductoTemp(clienteId,
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
		listadoVentaDirectaProductoTemp = null;
		try
		{
			listadoVentaDirectaProductoTemp = new VentaDirectaProductoTempBLL().ObtenerVentasDirectasProductoTempPorClienteId(clienteId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasDirectasProductoTemporales por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasDirectasProductoTemporales por clienteId: " + localException.getMessage());
			}
		}
		      
		if(listadoVentaDirectaProductoTemp == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No existen preventas temporales.", 1);
			DespliegueControlesConfirmarPreventa(false);
	    	lvVentaDirectaProductoTemp.setAdapter(null);
		}
		else
		{
			DespliegueControlesConfirmarPreventa(true);
			CalcularTotalPreventa();
			lvVentaDirectaProductoTemp.setVisibility(View.VISIBLE);
		    LlenarPreventaListView();
		    EliminarItemListView();	
		}    
	}
	
	private void ObtenerNoPreventa()
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
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas directas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas directas: " + localException.getMessage());
	    	}
	    	noVentaDirecta = 0;
	    }
	    
	    if (noVentaDirecta == -1)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los ventas directas.", 1);
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
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasDirectas por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasDiractas por clienteId: " + localException.getMessage());
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
	
	private void CalcularTotalPreventa()
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
	
	private void LlenarPreventaListView()
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
					return null;
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
		((ListView)findViewById(R.id.lvVendedorVentaDirectaPromocionPreventas)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				itemBorrarDispositivo = 0;
				
				VentaDirectaProductoTemp localVentaDirectaProductoTemp = listadoVentaDirectaProductoTemp.get(position);
				itemBorrarDispositivo = localVentaDirectaProductoTemp.get_id();
				
				if(BorrarVentaDirectaProductoTempDispositivo())
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la venta directa eliminado del dispositivo.", 1);
					CargarPromociones();
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
			 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar el item de la venta directa.", 1);
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
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el precio de la promocion, precio promocion <= 0.", 1);
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

	private void AdicionarVentaDirectaProductoTemporal()
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
          
          VentaDirectaProductoTemp localVentaDirectaProductoTemp = new VentaDirectaProductoTemp();
    	  localVentaDirectaProductoTemp.set_cantidad(cantidad);
    	  localVentaDirectaProductoTemp.set_cantidadPaquete(0);
    	  localVentaDirectaProductoTemp.set_clienteId(clientePreventa.get_clienteId());
    	  localVentaDirectaProductoTemp.set_empleadoId(loginEmpleado.get_empleadoId());
    	  localVentaDirectaProductoTemp.set_monto(montoSinDescuento);
    	  localVentaDirectaProductoTemp.set_descuento(descuentoFinal);
    	  localVentaDirectaProductoTemp.set_montoFinal(montoFinal);
    	  localVentaDirectaProductoTemp.set_productoId(0);
    	  localVentaDirectaProductoTemp.set_promocionId(promocion.get_promocionId());
    	  localVentaDirectaProductoTemp.set_costoId(costoId);
    	  localVentaDirectaProductoTemp.set_noVentaDirecta(noVentaDirecta);
    	  localVentaDirectaProductoTemp.set_precioId(promocionPrecio.get_precioId());
          
	      if (ValidarExistenciasAlmacenProductoDispositivo(promocion.get_promocionId(),cantidad))
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
	    		  if(listadoVentaDirectaProductoTemp == null || listadoVentaDirectaProductoTemp.size() <= 34)
	    		  {
			    	  if(InsertarVentaDirectaProductoTempDispositivo(localVentaDirectaProductoTemp))
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
	    		  if(InsertarVentaDirectaProductoTempDispositivo(localVentaDirectaProductoTemp))
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
					paramVentaDirectaProductoTemp.get_empleadoId(), paramVentaDirectaProductoTemp.get_descuentoCanal(),
					paramVentaDirectaProductoTemp.get_descuentoAjuste(), paramVentaDirectaProductoTemp.get_canalPrecioRutaId(),
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
								}
							}
						}
						else
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "La venta directa no se genero correctamente.", 1);
						}
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "Para ingresar la venta directa debe sincronizar el cliente.", 1);
						MostrarPantallaMenuVendedor();
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No esta conectado a internet, intentelo mas tarde.", 1);
					
					if((parametroGeneral.is_habilitarPop() || parametroGeneral.is_habilitarCambio()) && ObtenerRolEmpleado() == 3)//Rol 3 = vendedor
    				{
    					MostrarPantallaMenu();;
    				}
    				else
    				{
    					MostrarPantallaMenuVendedor();
    				}
				}
			}
		}
		else
		{
			dialog = new Dialog(Vendedorventadirectapromocion.this);
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
				validado = false;
				btnConfirmarVentaDirecta.setVisibility(View.VISIBLE);
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
		if(listadoVentaDirectaProductoTemp != null && listadoVentaDirectaProductoTemp.size() >0)
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
					clientePreventa.get_tipoVisita(),clientePreventa.get_zonaVentaId(), 0, descuentoCanal, descuentoAjuste,
					descuentoProntoPagoUni, descuentoObjetivo, 1, "", 0);
		   return true;
   	   }
	   else
	   {
		   theUtilidades.MostrarMensaje(getApplicationContext(), "No existe items en la venta directa.", 1);
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
	    			MostrarPantallaMenuVendedor();
	        	}
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "El WebService InsertVentaDirectaProductoTemp no se ejecuto correctamente. ", 1);
	    	}
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
									ventaDirectaProductoTemp.get_noVentaDirecta(), ventaDirectaProductoTemp.get_descuentoCanal(),
									ventaDirectaProductoTemp.get_descuentoAjuste(), ventaDirectaProductoTemp.get_canalPrecioRutaId());
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

	private void InsertarVentaDirecta()
	{
		 pdInsertarVentaDirecta = new ProgressDialog(this);
		 pdInsertarVentaDirecta.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		 pdInsertarVentaDirecta.setTitle("Preventa");
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

					 if(InsertarVentaDirectaDispositivo(intResultado))
					 {
						 theUtilidades.MostrarMensaje(getApplicationContext(), "Venta directa insertada en el dispositivo.", 1);
						 ActualizarClientePreventaMontoCredito();
						 BorrarVentasDirectaProductoTempDispositivo();
						 ModificarVentaDirectaProductoIdServer(intResultado);
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
			 		MostrarPantallaMenuVendedor();
				 }
			 }
			 else
			 {
				 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertVentaDirecta.", 1);
			 }
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
	    	
	    	if(!ejecutado){
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetProductosByAlmacenTemp no se ejecuto correctamente. ", 1);
				MostrarPantallaMenuVendedor();
				return;
	    	}

			if(!WSObtenerAlmacenProducto)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los productos del almacen. ", 1);
				MostrarPantallaMenuVendedor();
				return;
			}

			if(!BorrarAlmacenesProducto())
			{
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

			if((parametroGeneral.is_habilitarPop() || parametroGeneral.is_habilitarCambio()) && ObtenerRolEmpleado() == 3)//Rol 3 = vendedor
			{
				MostrarPantallaMenu();;
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
					ventaDirecta.get_descuentoCanal(),ventaDirecta.get_descuentoAjuste(), ventaDirecta.get_descuentoProntoPago(),
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
		else if(!boolCondicionTributaria && montoAcumulado <= 0)
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

	private void GetIncentivosByCliente(int ventaDirectaIdServer, float monto, float descuento, float montofinal)
	{
		pdClienteIncentivo = new ProgressDialog(this);
		pdClienteIncentivo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdClienteIncentivo.setMessage("Verificando Incentivos ...");
		pdClienteIncentivo.setCancelable(false);
		pdClienteIncentivo.setCanceledOnTouchOutside(false);

		WSGetIncentivosByCliente localWSGetIncentivosByCliente = new WSGetIncentivosByCliente(ventaDirectaIdServer, monto, descuento, montofinal);
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
		dialogIncentivo = new Dialog(this);
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

	private void InsertClienteIncentivo(int ventaDirectaIdServer, float montoFinal, float incentivo, String incentivosId)
	{
		pdInsertClienteIncentivo = new ProgressDialog(this);
		pdInsertClienteIncentivo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdInsertClienteIncentivo.setMessage("Registrando Incentivos ...");
		pdInsertClienteIncentivo.setCancelable(false);
		pdInsertClienteIncentivo.setCanceledOnTouchOutside(false);

		WSInsertClienteIncentivo localWSInsertClienteIncentivo = new WSInsertClienteIncentivo(ventaDirectaIdServer, montoFinal, incentivo, incentivosId);
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
							myLog.InsertarLog("App",this.toString(),1,"Error al insertar en la venta directa el descuento incentivo del cliente: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al insertar en la venta directa el descuento incentivo del cliente: " + localException.getMessage());
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
	
	public void MostrarPantallaVentaDirectaProducto()
	{
		Intent localIntent = new Intent(this, Vendedorventadirectaproducto.class);
		localIntent.putExtra("clienteId", clienteId);
		localIntent.putExtra("tipoPagoId", tipoPagoId);
		localIntent.putExtra("distribuidorId", distribuidorId);
		localIntent.putExtra("tipoNit", tipoNit);
		localIntent.putExtra("factura", factura);
		localIntent.putExtra("nit", nit);
		localIntent.putExtra("nitNuevo", nitNuevo);
		localIntent.putExtra("observacion",etObservacion.getText().toString());
		localIntent.putExtra("aplicarBonificacion",aplicarBonificacion);
		localIntent.putExtra("dosificacionId",dosificacionId);
		localIntent.putExtra("aplicarProntoPago",aplicarProntoPago);
		startActivity(localIntent);	 
	}
	
	public void MostrarPantallaMenuVendedor()
	{
		startActivity(new Intent(this, Menuvendedor.class));
	}

	public void MostrarPantallaMenu()
	{
		Intent localIntent = new Intent(this, Vendedorpreventamenuopciones.class);
		localIntent.putExtra("ventaDirectaIdDispositivo", ventaDirectaIdDispositivo);
		localIntent.putExtra("ventaDirectaIdServer", ventaDirectaIdServer);
		localIntent.putExtra("clienteId", clienteId);
		localIntent.putExtra("ventaDirecta", true);
		startActivity(localIntent);
	}
}
