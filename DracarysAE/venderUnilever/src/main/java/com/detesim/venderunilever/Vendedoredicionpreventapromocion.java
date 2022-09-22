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
import BLL.PreventaProductoBLL;
import BLL.ProductoBLL;
import BLL.PromocionBLL;
import BLL.PromocionCostoBLL;
import BLL.PromocionPrecioBLL;
import BLL.PromocionPrecioListaBLL;
import BLL.PromocionTipoNegocioBLL;
import BLL.ProveedorBLL;
import BLL.RolBLL;
import Clases.AES;
import Clases.AlmacenProductoWSResult;
import Clases.CanalRutaPrecio;
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
import Clases.Promocion;
import Clases.PromocionCosto;
import Clases.PromocionPrecio;
import Clases.PromocionPrecioLista;
import Clases.PromocionTipoNegocio;
import Clases.Proveedor;
import Clases.Rol;
import Clases.SingleId;
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
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class Vendedoredicionpreventapromocion extends Activity implements OnClickListener
{
	LinearLayout llVendedorEdicionPreventaPromocion;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	LoginEmpleado loginEmpleado;
	MyLogBLL myLog = new MyLogBLL();
	
	ArrayList<PreventaProducto> listadoPreventaProducto;
	ArrayList<PreventaProducto> listadoPreventaProductoNoSincronizadas;
	Preventa preventa;
	Promocion promocion;
	ArrayList<Proveedor> listadoProveedor;
	ClientePreventa clientePreventa;
	int clienteId;
	int preventaId;
	int preventaIdServer;
	String factura;
	String nit;
	String tipoNit;
	int preventaIdDispositivo;
	ParametroGeneral parametroGeneral;
	int noPreventa;
	PromocionPrecio promocionPrecio;
	long preventaProductoId;
	int dosificacionId;
	boolean boolCondicionTributaria=false;
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
	TextView tvMontoFinalTxt;
	TextView tvMontoFinal;
	ImageView ivProductos;
	Button btnMostrarDetalles;
	Button btnAdicionarPromocion;
	
	EditText etCantidad;
	Spinner spnProveedor;
	Spinner spnPromocion;
	ListView lvPreventaPromocion;
	Dialog dialogItemPreventa;
	Dialog dialog;
	
	ProgressDialog pdEsperaObtenerAlmacenProducto;
	ProgressDialog pdInsertarPreventaPromocion; 
	ProgressDialog pdDeletePreventaPromocion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedoredicionpreventapromocion);
		
		llVendedorEdicionPreventaPromocion = (LinearLayout)findViewById(R.id.llVendedorEdicionPreventaPromocion);
		ivProductos = (ImageView)findViewById(R.id.imgbtnVendedorVentaDirectaPromocion);
		ivProductos.setOnClickListener(this);
		tvClienteNombre = (TextView)findViewById(R.id.tvVendedorVentaDirectaPromocionClienteContenido);
		spnProveedor = (Spinner)findViewById(R.id.spnVendedorVentaDirectaPromocionProveedor);
		spnPromocion = (Spinner)findViewById(R.id.spnVendedorVentaDirectaPromocionPromocion);
		lvPreventaPromocion = (ListView)findViewById(R.id.lvVendedorVentaDirectaPromocionPreventas);
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
		tvTotaltxt = (TextView)findViewById(R.id.tvVendedorVentaDirectaPromocionTotalTxt);
		tvTotal = (TextView)findViewById(R.id.tvVendedorVentaDirectaPromocionTotal);
		tvDsctoObjetivoTxt = (TextView)findViewById(R.id.tvVenEdiPrePromoDescuentoObjetivoTxt);
	    tvDsctoObjetivo = (TextView)findViewById(R.id.tvVenEdiPrePromoDescuentoObjetivo);
	    tvDsctoObjetivoTotalTxt = (TextView)findViewById(R.id.tvVenEdiPrePromoDescuentoObjetivoTotalTxt);
	    tvDsctoObjetivoTotal = (TextView)findViewById(R.id.tvVenEdiPrePromoDescuentoObjetivoTotal);
	    tvMontoFinalTxt = (TextView)findViewById(R.id.tvVenEdiPrePromoMontoFinalTxt);
	    tvMontoFinal = (TextView)findViewById(R.id.tvVenEdiPrePromoMontoFinal);

		llVendedorEdicionPreventaPromocion.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
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
	    
	    factura="";
	    factura = getIntent().getExtras().getString("factura");
	    if(factura == "")
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la factura.", 1);
	    	return;
	    }
	    
	    nit="";
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
			MostrarPantallaEdicionPreventaProducto();
			break;
		case R.id.btnVendedorVentaDirectaPromocionMostrarDetalles:
			ObtenerDatosSeleccion();
			break;		
		case R.id.btnVendedorVentaDirectaPromocionAdicionarPreventa:
			AdicionarPreventaPromocionTemporal();
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
	    tvTotaltxt.setVisibility(visibilidad);
	    tvTotal.setVisibility(visibilidad);
	    tvDsctoObjetivoTxt.setVisibility(visibilidad);
	    tvDsctoObjetivo.setVisibility(visibilidad);
	    tvDsctoObjetivoTotalTxt.setVisibility(visibilidad);
	    tvDsctoObjetivoTotal.setVisibility(visibilidad);
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
					try
					{
						listadoPromocion = new PromocionBLL().ObtenerPromocionesNoEnPreventaProducto(preventaId,proveedor.get_proveedorId(),clientePreventa.get_precioListaId());
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
	    	listadoPromocion = new PromocionBLL().ObtenerPromocionesNoEnPreventaProducto(preventaId,
	    				proveedorId,clientePreventa.get_precioListaId());
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
			theUtilidades.MostrarMensaje(getApplicationContext(),"No existen productos en la preventa.", 1);
			DespliegueControlesConfirmarPreventa(false);
	    	lvPreventaPromocion.setAdapter(null);
		}
		else
		{
			DespliegueControlesConfirmarPreventa(true);
			CalcularTotalPreventa();
			lvPreventaPromocion.setVisibility(View.VISIBLE);
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
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas: " + localException.getMessage());
	    	}
	    	noPreventa = 0;
	    }
	    
	    if (noPreventa == -1)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el nro de preventa.", 1);
	    	return;
	    }
	}
		
	private void CalcularTotalPreventa()
	{
		float total = 0;
		for(PreventaProducto item : listadoPreventaProducto)
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
	    	    
	    if(listadoPreventaProducto == null)
	    {
	    	lvPreventaPromocion.setAdapter(null);
	    }
	    else
	    {
		    ViewGroup.LayoutParams localLayoutParams = lvPreventaPromocion.getLayoutParams();
		    localLayoutParams.height = ((int)(50*getApplicationContext().getResources().getDisplayMetrics().density) * listadoPreventaProducto.size());
		    lvPreventaPromocion.setLayoutParams(localLayoutParams);
		    lvPreventaPromocion.setAdapter(localMiAdaptadorLista);
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
				
				montoFinal.setText(String.valueOf(new BigDecimal(localPreventaProducto.get_montoFinal()).setScale(2,RoundingMode.HALF_UP)));
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
					return null;
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
		((ListView)findViewById(R.id.lvVendedorVentaDirectaPromocionPreventas)).setOnItemClickListener(new AdapterView.OnItemClickListener()
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
		dialogItemPreventa = new Dialog(Vendedoredicionpreventapromocion.this);
		dialogItemPreventa.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogItemPreventa.setContentView(R.layout.diseniodialogo_vendedorcierredia);
		dialogItemPreventa.setTitle("Borrar Item Preventa");
		dialogItemPreventa.setCancelable(false);
		dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.colorBluePale));
		
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
							CargarPromociones();
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
		pdDeletePreventaPromocion = new ProgressDialog(this);
		pdDeletePreventaPromocion.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdDeletePreventaPromocion.setMessage("Borrando item preventa ...");
		pdDeletePreventaPromocion.setCancelable(false);
		pdDeletePreventaPromocion.setCanceledOnTouchOutside(false);
		 
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
	    	pdDeletePreventaPromocion.show();
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
	    	if(pdDeletePreventaPromocion.isShowing())
	    	{
	    		pdDeletePreventaPromocion.dismiss();
	    	}
	    	
	    	if(ejecutado)
	    	{
	    		if(WSBorrarPreVentaProducto) 
	    		{
	    			BorrarPreventaProductoDispositivo((int)preventaProductoId);
	    			ModificarNuevosMontosPreventa();
	    			theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la preventa eliminado.", 1);
	    			CargarPromociones();
	    			ActualizarListView();
	    		}
	    		else
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo eliminar el producto, posiblemente su conexion a internet no sea buena.", 1);
	    			return;
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
		
		if(borrado == false)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar el producto de la preventa del dispositivo.", 1);
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
          
          if(clientePreventa.get_tipoPagoId() == 2)
   		  {
        	  if(!ValidarCondicionCredito(montoFinal))
	    	  {
	    		  return;
	    	  }
   		  }
          
          PreventaProducto localPreventaProducto = new PreventaProducto();
          localPreventaProducto.set_preventaId(preventaId);
    	  localPreventaProducto.set_cantidad(cantidad);
    	  localPreventaProducto.set_cantidadPaquete(0);
    	  localPreventaProducto.set_empleadoId(loginEmpleado.get_empleadoId());
    	  localPreventaProducto.set_monto(montoSinDescuento);
    	  localPreventaProducto.set_descuento(descuentoFinal);
    	  localPreventaProducto.set_montoFinal(montoFinal);
    	  localPreventaProducto.set_productoId(0);
    	  localPreventaProducto.set_promocionId(promocion.get_promocionId());
    	  localPreventaProducto.set_costo(0);
    	  localPreventaProducto.set_costoId(costoId);
    	  localPreventaProducto.set_noPreventa(noPreventa);
    	  localPreventaProducto.set_precioId(promocionPrecio.get_precioId());
          
	      if (ValidarExistenciasAlmacenProductoDispositivo(promocion.get_promocionId(),cantidad))
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
			    					  InsertarPreventaPromocion(localPreventaProducto);
						    	  }
						    	  else 
						    	  {
						    		  theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar la promocion en la preventa en el dispositivo.", 1);
						    		  return;
						    	  }
		    				  }
		    				  else
		    				  {
		    					  theUtilidades.MostrarMensaje(getApplicationContext(), "No se encuentra conectado a internet, intentelo mas tarde.", 1);
		    					  return;
		    				  }
		    			  }
		    			  else
		    			  {
		    				  if(InsertarPreventaProductoDispositivo(localPreventaProducto,false))
			    				{
		    					  	ModificarNuevosMontosPreventa();
			    					theUtilidades.MostrarMensaje(getApplicationContext(), "Promocion insertada, sincronice la preventa.", 1);
			    					CargarPromociones();
			    					DespliegueControlesAdicionarPromocion(false);
			    					ActualizarListView();
			    				}
		    			  }
		    		  }
		    		  else
		    		  {
		    			  theUtilidades.MostrarMensaje(getApplicationContext(),"Se alcanzo el tope de items para la factura medio oficio, ingrese una nueva preventa.",1);
			    		  return;
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
			    					  InsertarPreventaPromocion(localPreventaProducto);
			    				  }
			    				  else 
			    				  {
			    					  theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el producto de la preventa en el dispositivo.",1);
			    					  return;
			    				  }
		    				  }
		    				  else
		    				  {
		    					  theUtilidades.MostrarMensaje(getApplicationContext(), "No se encuentra conectado a internet, intentelo mas tarde.", 1);
		    					  return;
		    				  }
		    			  }
		    			  else
		    			  {
		    				  if(InsertarPreventaProductoDispositivo(localPreventaProducto,false))
		    				  {
		    					  ModificarNuevosMontosPreventa();
		    					  theUtilidades.MostrarMensaje(getApplicationContext(), "Promocion insertada, sincronice la preventa.", 1);
		    					  CargarPromociones();
			    				  DespliegueControlesAdicionarPromocion(false);
			    				  ActualizarListView();
		    				  }
		    			  }
		    		  }
		    		  else
		    		  {
		    			  theUtilidades.MostrarMensaje(getApplicationContext(),"Se alcanzo el tope de items para la factura oficio, ingrese una nueva preventa.",1);
			    		  return;
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
			    				  InsertarPreventaPromocion(localPreventaProducto);
			    			  }
			    			  else 
			    			  {
			    				  theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el producto de la preventa en el dispositivo.",1);
			    				  return;
			    			  }
			    		  }
						  else
						  {
							  theUtilidades.MostrarMensaje(getApplicationContext(), "No se encuentra conectado a internet, intentelo mas tarde.", 1);
							  return;
						  }
		    		  }
		    		  else
		    		  {
		    			  if(InsertarPreventaProductoDispositivo(localPreventaProducto,false))
		    			  {
		    				  ModificarNuevosMontosPreventa();
		    				  theUtilidades.MostrarMensaje(getApplicationContext(), "Producto insertada, sincronice la preventa.", 1);
		    				  CargarPromociones();
		    				  DespliegueControlesAdicionarPromocion(false);
		    				  ActualizarListView();
		    			  }
		    		  }
		    	  }
	    	  }
	    	  else
	    	  {
	    		  dialog = new Dialog(Vendedoredicionpreventapromocion.this);
	    		  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    		  dialog.setContentView(R.layout.diseniodialogo_vendedorcierredia);
	    		  dialog.setTitle("NIT Invalido !");
	    		  dialog.setCancelable(false);
				  dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.colorBluePale));
					
	    		  TextView tvMensaje = (TextView)dialog.findViewById(R.id.tvDialogoMensaje);
	    		  tvMensaje.setText("Por el monto de la preventa (" + parametroGeneral.get_montoNit() + ") Bs."
							+ " debe registrar un nombre de factura y nit valido. Por favor ingrese nuevamente la preventa.");
					
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
	    	  theUtilidades.MostrarMensaje(getApplicationContext(), "No existe producto en el almacen del dispositivo.", 1);
	    	  return;
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
				validado = false;
				btnAdicionarPromocion.setVisibility(View.VISIBLE);
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
		
		if(listadoPreventaProducto!=null)
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

	private void InsertarPreventaPromocion(PreventaProducto localPreventaProducto)
	{
		pdInsertarPreventaPromocion = new ProgressDialog(this);
		pdInsertarPreventaPromocion.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdInsertarPreventaPromocion.setMessage("Insertando item preventa ...");
		pdInsertarPreventaPromocion.setCancelable(false);
		pdInsertarPreventaPromocion.setCanceledOnTouchOutside(false);
	    
	    WSInsertarPreventaPromocion localWSInsertarPreventaPromocion = new WSInsertarPreventaPromocion(localPreventaProducto);
	    try
	    {
	    	localWSInsertarPreventaPromocion.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreVentaPromocion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreVentaPromocion: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertPreVentaPromocion", 1);
	    }
	 }
	 
	public class WSInsertarPreventaPromocion extends AsyncTask<Void, Integer, Boolean>
	{
		 String PREVENTAPRODUCTO_METHOD_NAME = "InsertPreVentaPromocion";
		 String PREVENTAPRODUCTO_SOAP_ACTION = NAMESPACE + PREVENTAPRODUCTO_METHOD_NAME;
		 
		 private PreventaProducto _preventaProducto;
		 
		 boolean WSInsertarPreVentaProducto;
		 int resultadoInt;
		 String resultadoString;
		 SoapObject localSoapObjects;
	    
	    public WSInsertarPreventaPromocion(PreventaProducto paramPreventaProducto)
	    {
	    	_preventaProducto = paramPreventaProducto;
	    }
	    
	    protected void onPreExecute()
	    {
	    	pdInsertarPreventaPromocion.show();
	    }
	    
	    protected Boolean doInBackground(Void... paramVarArgs)
	    {
	    	WSInsertarPreVentaProducto = false;
	      
	    	SoapObject localSoapObject = new SoapObject(NAMESPACE, this.PREVENTAPRODUCTO_METHOD_NAME);

	    	localSoapObject.addProperty("preVentaId", preventaIdServer);
	    	localSoapObject.addProperty("promocionId", _preventaProducto.get_promocionId());
	    	localSoapObject.addProperty("cantidad", _preventaProducto.get_cantidad());
	    	
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
	    			WSInsertarPreVentaProducto = true;
	    		}
	    		return true;
	    	}
	    	catch (Exception localException)
	    	{
	    		WSInsertarPreVentaProducto = false;
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
	    		if(WSInsertarPreVentaProducto || (resultadoString != null && resultadoString.equals("Preventa Promocion Repetida") && resultadoInt < 0))
	    		{
	    			ModificarNuevosMontosPreventa();
	    			theUtilidades.MostrarMensaje(getApplicationContext(), "Producto de la preventa sincronizado.", 1);
	    			CargarPromociones();
	    			DespliegueControlesAdicionarPromocion(false);
	    			ActualizarListView();
	    		}
	    		else 
	    		{
	    			BorrarPreventaProductoDispositivo((int)preventaProductoId);
	    			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo ingresar el producto, posiblemente su conexion a internet no sea la adecuada o ya se cerro su dia.", 1);
	        	}
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "El WebService InsertPreVentaProducto no se ejecuto correctamente. ", 1);
	    		return;
	    	}
	    }
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
					 paramPreventaProducto.get_empleadoId(),paramPreventaProducto.get_promocionId(),estado,
					 paramPreventaProducto.get_costo(),paramPreventaProducto.get_costoId(),
					 paramPreventaProducto.get_noPreventa(),paramPreventaProducto.get_precioId(),
					 paramPreventaProducto.get_descuentoCanal(),paramPreventaProducto.get_descuentoAjuste(),
					 paramPreventaProducto.get_canalPrecioRutaId(), paramPreventaProducto.get_descuentoProntoPago());
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar la promocionProducto en el dispositivo: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la promocionProducto en el dispositivo: " + localException.getMessage());
	    	}
	    	return false;
	    }
	    	
	    if(preventaProductoId <= 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la promocion de la preventa en el dispositivo.", 1);
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
				descuentoAjuste += item.get_descuentoAjuste();
				descuentoProntoPago += item.get_descuentoProntoPago();
			}
			
			//Verificamos si cliente tiene saldo del rebate dracaris
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

			if(!WSObtenerAlmacenProducto)
			{
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
		
		if(clientePreventa.get_montoPendienteCredito() == 0 && !parametroGeneral.is_creditoSobreCredito())
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
		if(listadoPreventaProducto != null && listadoPreventaProducto.size() > 0)
		{	
			for(PreventaProducto item : listadoPreventaProducto)
	    	{
	    		montoFinal += item.get_montoFinal();
	    	}
		}
		return montoFinal;
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
	
	public void MostrarPantallaEdicionPreventaProducto()
	{
		Intent localIntent = new Intent(this, Vendedoredicionpreventaproducto.class);
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
