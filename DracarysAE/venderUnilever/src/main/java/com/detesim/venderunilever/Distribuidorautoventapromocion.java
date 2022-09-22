package com.detesim.venderunilever;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import BLL.CanalRutaPrecioBLL;
import BLL.ClienteVentaBLL;
import BLL.CondicionTributariaBLL;
import BLL.DevolucionDistribuidorProductoBLL;
import BLL.DevolucionDistribuidorProductoTempBLL;
import BLL.DosificacionProveedorBLL;
import BLL.DraRebateSaldoBLL;
import BLL.MyLogBLL;
import BLL.ParametroGeneralBLL;
import BLL.PrecioListaBLL;
import BLL.ProductoBLL;
import BLL.PromocionBLL;
import BLL.PromocionCostoBLL;
import BLL.PromocionPrecioBLL;
import BLL.PromocionPrecioListaBLL;
import BLL.PromocionProductoBLL;
import BLL.PromocionTipoNegocioBLL;
import BLL.ProveedorBLL;
import BLL.SincronizacionVentaBLL;
import BLL.VentaBLL;
import BLL.VentaProductoBLL;
import Clases.CanalRutaPrecio;
import Clases.ClienteVenta;
import Clases.CondicionTributaria;
import Clases.DevolucionDistribuidorProducto;
import Clases.DevolucionDistribuidorProductoTemp;
import Clases.DosificacionProveedor;
import Clases.DraRebateSaldo;
import Clases.Fecha;
import Clases.LoginEmpleado;
import Clases.ParametroGeneral;
import Clases.PrecioLista;
import Clases.Producto;
import Clases.Promocion;
import Clases.PromocionCosto;
import Clases.PromocionPrecio;
import Clases.PromocionPrecioLista;
import Clases.PromocionProducto;
import Clases.PromocionTipoNegocio;
import Clases.Proveedor;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Distribuidorautoventapromocion extends Activity implements OnClickListener
{
	LinearLayout llDistribuidorAutoventaPromocion;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	LoginEmpleado loginEmpleado;
	MyLogBLL myLog = new MyLogBLL();
	
	ArrayList<DevolucionDistribuidorProductoTemp> listadoDevolucionDistribuidorProductoTemp;
	ArrayList<VentaProducto> listadoVentaProductoNoSincronizado;
	Venta venta;
	Promocion promocion;
	Producto producto;
	ClienteVenta clienteVenta;
	ParametroGeneral parametroGeneral;
	int clienteId;
	int tipoPagoId;
	String tipoNit;
	String factura;
	String nit;
	Boolean nitNuevo;
	int noAutoventa;
	int itemBorrarDispositivo;
	int itemBorrarServidor;
	long ventaIdDispositivo;
	boolean aplicarBonificacion;
	String observacion;
	PromocionPrecio promocionPrecio;
	int dosificacionId;
	boolean boolCondicionTributaria;
	float montoTributarioServer;
	boolean venderCredito;
	CanalRutaPrecio canalRutaPrecio;
	boolean aplicarProntoPago;
	DraRebateSaldo draRebateSaldo;
	
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
	TextView tvMontoTotalTxt;
	TextView tvMontoTotal;
	TextView tvObservacion;
	CheckBox cbAplicarBonificacion;
	EditText etObservacion;
	
	ImageView ivProductos;
	Button btnMostrarDetalles;
	Button btnAdicionarPromocion;
	Button btnConfirmarAutoventa;
	
	EditText etCantidad;
	Spinner spnProveedor;
	Spinner spnPromocion;
	ListView lvVentaPromocionTemp;
	Dialog dialog;
	
	ProgressDialog pdInsertarAutoventaProductoTemp; 
	ProgressDialog pdDeleteAlmacenDevolucionProductoTemp;
	ProgressDialog pdInsertarAutoventa;
	ProgressDialog pdVerificarMontoNitCliente;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_distribuidorautoventapromocion);
		
		llDistribuidorAutoventaPromocion = (LinearLayout)findViewById(R.id.DistribuidorAutoventaPromocionLinearLayout);
		ivProductos = (ImageView)findViewById(R.id.imgbtnVendedorVentaDirectaPromocion);
		ivProductos.setOnClickListener(this);
		tvClienteNombre = (TextView)findViewById(R.id.tvVendedorVentaDirectaPromocionClienteContenido);
		spnProveedor = (Spinner)findViewById(R.id.spnAutoventaPromocionProveedor);
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
		lvVentaPromocionTemp = (ListView)findViewById(R.id.lvVendedorVentaDirectaPromocionPreventas);
		tvTotaltxt = (TextView)findViewById(R.id.tvVendedorVentaDirectaPromocionTotalTxt);
		tvTotal = (TextView)findViewById(R.id.tvVendedorVentaDirectaPromocionTotal);
		tvDsctoObjetivoTxt = (TextView)findViewById(R.id.tvAutPromoDescuentoObjetivoTxt);
	    tvDsctoObjetivo = (TextView)findViewById(R.id.tvAutPromoDescuentoObjetivo);
	    tvDsctoObjetivoTotalTxt = (TextView)findViewById(R.id.tvAutPromoDescuentoObjetivoTotalTxt);
	    tvDsctoObjetivoTotal = (TextView)findViewById(R.id.tvAutPromoDescuentoObjetivoTotal);
	    tvMontoTotalTxt = (TextView)findViewById(R.id.tvAutPromoMontoTotalTxt);
	    tvMontoTotal = (TextView)findViewById(R.id.tvAutPromoMontoTotal);
		cbAplicarBonificacion = (CheckBox)findViewById(R.id.cbDistribuidorAutoventaPromocionAplicarBonificacion);
		cbAplicarBonificacion.setOnClickListener(this);
		tvObservacion = (TextView)findViewById(R.id.tvAutoventaPromocionObservacion);
		etObservacion = (EditText)findViewById(R.id.etAutoventaPromocionObservacion);
		btnConfirmarAutoventa = (Button)findViewById(R.id.btnVendedorEdicionPreventaPromocionConfirmarPreventa);
		btnConfirmarAutoventa.setOnClickListener(this);
		
		llDistribuidorAutoventaPromocion.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
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
	        DespliegueControlesConfirmarAutoventa(false);
	        CargarInformacion();
	    }
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.imgbtnVendedorVentaDirectaPromocion:
			MostrarPantallaAutoventaProducto();
			break;
		case R.id.btnVendedorVentaDirectaPromocionMostrarDetalles:
			ObtenerDatosSeleccion();
			break;		
		case R.id.btnVendedorVentaDirectaPromocionAdicionarPreventa:
			AdicionarAutoventaTemporal();
			break;
		case R.id.btnVendedorEdicionPreventaPromocionConfirmarPreventa:
			ConfirmarAutoventa();
			break;
		case R.id.cbDistribuidorAutoventaPromocionAplicarBonificacion:
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
	
	private void DespliegueControlesConfirmarAutoventa(boolean estado)
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
	    lvVentaPromocionTemp.setVisibility(visibilidad);
	    tvTotaltxt.setVisibility(visibilidad);
	    tvTotal.setVisibility(visibilidad);
	    tvDsctoObjetivoTxt.setVisibility(visibilidad);
	    tvDsctoObjetivo.setVisibility(visibilidad);
	    tvDsctoObjetivoTotalTxt.setVisibility(visibilidad);
	    tvDsctoObjetivoTotal.setVisibility(visibilidad);
	    cbAplicarBonificacion.setVisibility(visibilidad);
	    tvObservacion.setVisibility(visibilidad);
	    etObservacion.setVisibility(visibilidad);
	    btnConfirmarAutoventa.setVisibility(visibilidad);
    }

	public void CargarInformacion()
	{
		CargarParametroGeneral();
	    CargarClienteVenta();
	    CargarProveedores();
	    ActualizarListView();
	    ObtenerNoAutoventa();
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
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el cliente por clienteId.", 1);
	    	return;
	    }
	    else
	    {
	    	tvClienteNombre.setText(clienteVenta.get_nombreCompleto().toString());
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
			
			ArrayAdapter<Proveedor> localArrayAdapter = new ArrayAdapter<Proveedor>(this,R.layout.disenio_spinner,listadoProveedor);
		    spnProveedor.setAdapter(localArrayAdapter);
		    
		    spnProveedor.setOnItemSelectedListener(new OnItemSelectedListener() 
		    {	    	
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
				{
					Proveedor proveedor = (Proveedor)spnProveedor.getSelectedItem();

					ArrayList<Promocion> listadoPromocion = null;
					ArrayList<Promocion> listadoPromocionFinal = new ArrayList<Promocion>();
					try
					{
						listadoPromocion = new PromocionBLL().ObtenerPromocionesNoEnAlmacenDevolucionProductoTemp(clienteId, 
								loginEmpleado.get_empleadoId(),proveedor.get_proveedorId(),clienteVenta.get_precioListaId());
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
					else
				    {
				    	listadoPromocionFinal.add(new Promocion(0,"Seleccione una promocion ...","",true,0));
				    	for(Promocion item : listadoPromocion)
				    	{
			    			if(ExistePromocionesConSaldoEnDevolucionDistribuidorProducto(item.get_promocionId()))
			    			{
			    				listadoPromocionFinal.add(item);
			    			}
				    	}
				    }
    
				    ArrayAdapter<Promocion> localArrayAdapterFinal = new ArrayAdapter<Promocion>(getApplicationContext(),R.layout.disenio_spinner,listadoPromocionFinal);
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
		
		ArrayList<Promocion> listadoPromocionFinal = new ArrayList<Promocion>();
	    ArrayList<Promocion> listadoPromocion = null;
	    
	    try
	    {
	    	listadoPromocion = new PromocionBLL().ObtenerPromocionesNoEnAlmacenDevolucionProductoTemp(clienteId,
	    			loginEmpleado.get_empleadoId(),proveedorId,clienteVenta.get_precioListaId());
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
	    else
	    {
	    	listadoPromocionFinal.add(new Promocion(0,"Seleccione una promocion ...","",true,0));
	    	for(Promocion item : listadoPromocion)
	    	{
    			if(ExistePromocionesConSaldoEnDevolucionDistribuidorProducto(item.get_promocionId()))
    			{
    				listadoPromocionFinal.add(item);
    			}
	    	}
	    }
	    
	    ArrayAdapter<Promocion> localArrayAdapter = new ArrayAdapter<Promocion>(this,R.layout.disenio_spinner,listadoPromocionFinal);
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
	
	public boolean ExistePromocionesConSaldoEnDevolucionDistribuidorProducto(int promocionId)
	{
		boolean existencia = false;
		try
		{
			existencia = new PromocionBLL().ExistePromocionesConSaldoEnDevolucionDistribuidorProducto(promocionId, 1);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las existencias de la promocion en almacenProducto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las existencias de la promocion en almacenProducto: " + localException.getMessage());
			}
		}
		return existencia;
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
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolcuionProductoTemp por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las productos del almacenDevolucionProductoTemp por clienteId: " + localException.getMessage());
			}
		}
		      
		if(listadoDevolucionDistribuidorProductoTemp == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No existen autoventas temporales.", 1);
			DespliegueControlesConfirmarAutoventa(false);
	    	lvVentaPromocionTemp.setAdapter(null);
		}
		else
		{
			DespliegueControlesConfirmarAutoventa(true);
			CalcularTotalAutoventa();
			lvVentaPromocionTemp.setVisibility(View.VISIBLE);
		    LlenarAutoventaListView();
		    EliminarItemListView();	
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
	
	private void CalcularTotalAutoventa()
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
				tvMontoTotal.setText(String.valueOf(new BigDecimal(total - draRebateSaldo.get_saldo()).setScale(2,RoundingMode.HALF_UP)));
			}
			else
			{
				tvDsctoObjetivo.setText(String.valueOf(new BigDecimal(saldoObjetivo).setScale(2,RoundingMode.HALF_UP)));
				tvDsctoObjetivoTotal.setText(String.valueOf(new BigDecimal(draRebateSaldo.get_saldo()).setScale(2,RoundingMode.HALF_UP)));
				tvMontoTotal.setText(String.valueOf(new BigDecimal(total - saldoObjetivo).setScale(2,RoundingMode.HALF_UP)));
			}
		}
		else
		{
			tvDsctoObjetivo.setText("0.00");
			tvDsctoObjetivoTotal.setText("0.00");
			tvMontoTotal.setText(String.valueOf(new BigDecimal(total).setScale(2,RoundingMode.HALF_UP)));
		}
	}
	
	private void LlenarAutoventaListView()
	{
	    MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    	    
	    if(listadoDevolucionDistribuidorProductoTemp == null)
	    {
	    	lvVentaPromocionTemp.setAdapter(null);
	    }
	    else
	    {
		    ViewGroup.LayoutParams localLayoutParams = lvVentaPromocionTemp.getLayoutParams();
		    localLayoutParams.height = ((int)(50*getApplicationContext().getResources().getDisplayMetrics().density) * listadoDevolucionDistribuidorProductoTemp.size());
		    lvVentaPromocionTemp.setLayoutParams(localLayoutParams);
		    lvVentaPromocionTemp.setAdapter(localMiAdaptadorLista);
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
		((ListView)findViewById(R.id.lvVendedorVentaDirectaPromocionPreventas)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
			public void onItemClick(AdapterView<?> parent, View view,final int position, long id)
			{
				dialog = new Dialog(Distribuidorautoventapromocion.this);
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
										CargarPromociones();
									} 
									else 
									{
										theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo eliminar el item de la venta.", 1);
									}
								}
				
								if(localAlmacenDevolucionProductoTemp != null) 
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
	    				CargarPromociones();
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
		 ClienteVenta localClienteVenta;
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
		 
		 localClienteVenta = null;
		 try
		 {
			 localClienteVenta = new ClienteVentaBLL().ObtenerClienteVentaPor(clienteId);
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
	    
		 if(localClienteVenta == null)
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo obtener los detalles del cliente.",1);
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
				 if(item.get_tipoNegocioId() == localClienteVenta.get_clienteTipoNegocioId())
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
			 precioPromocion = ObtenerPrecioPromocionPor(promocion.get_promocionId(),localClienteVenta.get_precioListaId());
		 }
		 else
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(), "No existe un precio de lista definido para la promocion.", 1);
			 return;
		 }
	          
	     DespliegueControlesAdicionarPromocion(true);
	     
	     tvPromocion.setText(promocion.get_descripcion());
	     tvPrecio.setText(String.valueOf(precioPromocion));
	     tvDescuento.setText(String.valueOf(localClienteVenta.get_descuento()));
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

	private float ObtenerPrecioPromocionPor(int promocionId,int precioListaId)
	{
		float precioPromocion = 0;
		
		promocionPrecio = null;
		
		try
		{
			promocionPrecio = new PromocionPrecioBLL().ObtenerPromocionPrecioPor(promocionId, precioListaId);
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
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el precio de la promocion.", 1);
			return 0;
		}
		else
		{
			precioPromocion = promocionPrecio.get_precio();
		}
		
		return precioPromocion;	
	}
	
	private void AdicionarAutoventaTemporal()
	{
		  int cantidad=0;
		  float precio;
		  float monto=0;
		  float descuento;
		  float montoFinal=0;
		  ParametroGeneral parametroGeneral;
		  float costo=0;
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
         
         descuento = clienteVenta.get_descuento();

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
          
          precioSinDescuento = ObtenerPrecioPromocionSinDescuentoPorLista(promocion.get_promocionId(),clienteVenta.get_precioListaId());
          montoSinDescuento = cantidad * precioSinDescuento;
          descuentoFinal = montoSinDescuento - montoFinal - descuento;
          
          costoId = ObtenerCostoIdPromocion(promocion.get_promocionId());
          
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
	      
          DevolucionDistribuidorProductoTemp localDevolucionDistribuidorProductoTemp = new DevolucionDistribuidorProductoTemp();
          localDevolucionDistribuidorProductoTemp.set_tempId(0);
          localDevolucionDistribuidorProductoTemp.set_clienteId(clienteVenta.get_clienteId());
          localDevolucionDistribuidorProductoTemp.set_promocionId(promocion.get_promocionId());
          localDevolucionDistribuidorProductoTemp.set_cantidad(cantidad);
          localDevolucionDistribuidorProductoTemp.set_cantidadPaquete(0);
          localDevolucionDistribuidorProductoTemp.set_monto(montoSinDescuento);
          localDevolucionDistribuidorProductoTemp.set_descuento(descuentoFinal);
          localDevolucionDistribuidorProductoTemp.set_montoFinal(montoFinal);
          localDevolucionDistribuidorProductoTemp.set_empleadoId(loginEmpleado.get_empleadoId());
          localDevolucionDistribuidorProductoTemp.set_productoId(0);
          localDevolucionDistribuidorProductoTemp.set_costo(costo);
          localDevolucionDistribuidorProductoTemp.set_costoId(costoId);
          localDevolucionDistribuidorProductoTemp.set_precioId(promocionPrecio.get_precioId());
          localDevolucionDistribuidorProductoTemp.set_noAutoventa(noAutoventa);
    	  
	      if (ValidarExistenciasDevolucionDistribuidorProductoDispositivo(promocion.get_promocionId(),cantidad))
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
	    			  theUtilidades.MostrarMensaje(getApplicationContext(), "No se puede ingresar el producto, ya que el monto excede el definido por el de bancarizacion.", 1);
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
							CargarPromociones();
				    		DespliegueControlesAdicionarPromocion(false);
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
							CargarPromociones();
				    		DespliegueControlesAdicionarPromocion(false);
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
						CargarPromociones();
			    		DespliegueControlesAdicionarPromocion(false);
						ActualizarListView();
		    		}
					else 
					{
						theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar la promocion de la autoventa en el dispositivo.",1);
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
	
	private float ObtenerPrecioPromocionSinDescuentoPorLista(int promocionId,int precioListaId)
	{
		float precioPromocion = 0;
		
		ArrayList<PromocionProducto> listadoPromocionProducto = null;
		try
		{
			listadoPromocionProducto = new PromocionProductoBLL().ObtenerPromocionesProductoPor(promocionId);
		}
		catch (Exception localException)
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
		
		if(listadoPromocionProducto == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los productos de la promocion.", 1);
			return 0;
		}
		
		for(PromocionProducto itemPromo : listadoPromocionProducto)
		{
			PrecioLista localPrecioLista = null;
			try
			{
				localPrecioLista = new PrecioListaBLL().ObtenerPrecioListaPor(precioListaId,itemPromo.get_productoId());
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precio de lista del producto, de la promocion: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precio de lista del producto, de la promocion: " + localException.getMessage());
				} 
			}
			
			if(localPrecioLista == null)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El producto de la promocion no tiene un precio de lista asociado.", 1);
	            return 0;
			}
			else
			{
				if(itemPromo.get_cantidad() > 0)
				{
					precioPromocion += itemPromo.get_cantidad() * localPrecioLista.get_precio();
				}
				else
				{
					precioPromocion += itemPromo.get_cantidadPaquete() * localPrecioLista.get_precioPaquete();
				}					
			}
		}
		
		return precioPromocion;		
	}
		
	private int ObtenerCostoIdPromocion(int promocionId)
	{
		PromocionCosto localPromocionCosto = null;
		
		try
		{
			localPromocionCosto = new PromocionCostoBLL().ObtenerPromocionCostoPorPromocionId(promocionId);
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
			return 0;
		}
		else
		{
			return localPromocionCosto.get_costoId();
		}			
	}
	
	private boolean ValidarExistenciasDevolucionDistribuidorProductoDispositivo(int promocionId, int cantidad)
	{
		boolean validado = false;
		try
		{
			validado = new PromocionBLL().ExistePromocionesConSaldoEnDevolucionDistribuidorProducto(promocionId,cantidad);
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

    	return validado;
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
	    	localSoapObject.addProperty("precioId",_ventaProducto.get_precioId());
	    	localSoapObject.addProperty("nroAutoventa",noAutoventa);
       
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
	
	private void ConfirmarAutoventa()
	{
		btnConfirmarAutoventa.setVisibility(View.INVISIBLE);
		
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
								theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los productos no sincronizados de la venta", 1);
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
					theUtilidades.MostrarMensaje(getApplicationContext(), "La autoventa no se genero correctamente.", 1);
					return;
				}
			}
		}
		else
		{
			dialog = new Dialog(Distribuidorautoventapromocion.this);
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
				btnConfirmarAutoventa.setVisibility(View.VISIBLE);
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
		if(listadoDevolucionDistribuidorProductoTemp != null && listadoDevolucionDistribuidorProductoTemp.size() > 0)
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
	    	
	    	venta = new Venta(0,0,fecha.get_dia(),fecha.get_mes(),fecha.get_anio(),clienteId,loginEmpleado.get_empleadoId(),
	    			monto,descuento,montoFinal,0,tipoPagoId,latitud,longitud,false,false,fecha.get_hora(),fecha.get_minuto(),
	    			etObservacion.getText().toString(),aplicarBonificacion,dosificacionId,tipoNit,descuentoCanal,descuentoAjuste,
	    			0, descuentoProntoPagoUni, descuentoObjetivo, 1, "", true, false);
	    	return true;
	    }
	    else
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No existe items en la autoventa.", 1);
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

	private boolean InsertarDevolucionDistribuidorProductoTempDispositivo(DevolucionDistribuidorProductoTemp paramDevolucionDistribuidorProductoTemp)
	{
		long l = 0;
		try
		{
			l = new DevolucionDistribuidorProductoTempBLL().InsertarDevolucionDistribuidorProductoTemp(paramDevolucionDistribuidorProductoTemp.get_tempId(),
					0,paramDevolucionDistribuidorProductoTemp.get_promocionId(),
					paramDevolucionDistribuidorProductoTemp.get_cantidad(),paramDevolucionDistribuidorProductoTemp.get_cantidadPaquete(),
					paramDevolucionDistribuidorProductoTemp.get_monto(),paramDevolucionDistribuidorProductoTemp.get_descuento(),
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
	    	
	    if(l<=0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el producto temporal de la autoventa en el dispositivo.", 1);
	  	     return false;		
	    }
	    else
	    {
	    	return true;
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
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la sincronizacionVenta por ventaId: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la SincronizacionVenta por ventaId: " + localException.getMessage());
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
					venta.get_tipoNit(),venta.get_descuentoCanal(),venta.get_descuentoAjuste(),venta.get_prontoPagoId(),
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
		 
		if(ventaIdDispositivo == 0)
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
					cantidadExistenteEnUnidades = (localDevolucionDistribuidorProducto.get_cantidadPaquete() * producto.get_conversion()) +
							 						localDevolucionDistribuidorProducto.get_cantidad();
					cantidadSolicitadaEnUnidades = (item.get_cantidadPaquete() * producto.get_conversion()) + item.get_cantidad();
					
					if(cantidadSolicitadaEnUnidades <= cantidadExistenteEnUnidades)
					{
						int cantidadSaldoEnUnidades = cantidadExistenteEnUnidades - cantidadSolicitadaEnUnidades;
						int cantidadPaqueteConsolidada = cantidadSaldoEnUnidades / producto.get_conversion();
						int cantidadConsolidada = cantidadSaldoEnUnidades % producto.get_conversion();
						  
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
					nit,nitNuevo,0,0,(int)ventaIdDispositivo,0,venta.get_observacion(),0,aplicarBonificacion,noAutoventa,
					dosificacionId,tipoNit,venta.get_descuentoCanal(),venta.get_descuentoAjuste(),0,venta.get_descuentoProntoPago(),
					venta.get_prontoPagoId(), venta.get_descuentoObjetivo(), venta.get_formaPagoId(), venta.get_codTransferencia(), venta.is_fromShopp());
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
			 bool = new DevolucionDistribuidorProductoTempBLL().BorrarDevolucionDistribuidorProductoTempPorEmpleadoIdYClienteId(loginEmpleado.get_empleadoId(),clienteId);
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
			l = new ClienteVentaBLL().ModificarClienteVentaEstadoAtendido(clienteId, true);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estadoAtencion del clienteVenta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estadoAtencion del clienteVenta: " + localException.getMessage());
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

	public void MostrarPantallaMenuVendedor()
	{
		startActivity(new Intent(this, Menuvendedor.class));
	}
	
	public void MostrarPantallaAutoventaProducto()
	{
		Intent localIntent = new Intent(this, Distribuidorautoventa.class);
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
    	localIntent.putExtra("aplicarProntoPago",aplicarProntoPago);
		startActivity(localIntent);
	}

	public void MostrarPantallaPreventasMapa()
	{
	    Intent localIntent = new Intent(this, Vendedormapaclientes.class);
	    localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

	private void MostrarPantallaMenuDistribuidor()
	{
		Intent localIntent = new Intent(this, Menudistribuidor.class);
	    localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(localIntent);
	}
}
