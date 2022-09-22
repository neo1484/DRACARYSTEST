package com.detesim.venderunilever;

import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import BLL.AlmacenCHANGEProductoBLL;
import BLL.CategoriaCHANGEBLL;
import BLL.ClientePreventaBLL;
import BLL.MotivoCHANGEBLL;
import BLL.MyLogBLL;
import BLL.ParametroGeneralBLL;
import BLL.PreventaProductoCHANGEBLL;
import BLL.ProductoCHANGEBLL;
import BLL.ProductoCHANGECostoBLL;
import BLL.ProveedorBLL;
import BLL.VentaProductoCHANGEBLL;
import Clases.AlmacenCHANGEProducto;
import Clases.CategoriaCHANGE;
import Clases.CategoriaPOP;
import Clases.ClientePreventa;
import Clases.LoginEmpleado;
import Clases.MotivoCHANGE;
import Clases.MotivoPop;
import Clases.ParametroGeneral;
import Clases.PreventaCHANGE;
import Clases.PreventaProductoCHANGE;
import Clases.ProductoCHANGE;
import Clases.ProductoCHANGECosto;
import Clases.Proveedor;
import Clases.VentaProductoCHANGE;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Vendedorpreventaproductochange extends Activity implements OnClickListener
{
	LinearLayout llVendedorPreventaProductoCHANGE;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	LoginEmpleado loginEmpleado;
	MyLogBLL myLog = new MyLogBLL();
	
	ArrayList<PreventaProductoCHANGE> listadoPreventaProductoCHANGE ;
	ArrayList<PreventaProductoCHANGE> listadoPreventaProductoCHANGENoSincronizadas;
	ArrayList<VentaProductoCHANGE> listadoVentaProductoCHANGE;
	ArrayList<VentaProductoCHANGE> listadoVentaProductoCHANGENoSincronizadas;
	PreventaCHANGE preventaCHANGE;
	ProductoCHANGE productoCHANGE;
	ClientePreventa clientePreventa;
	int preventaIdCHANGE;
	int preventaIdCHANGEServer;
	int clienteId;
	int ventaDirectaIdCHANGE;
	int ventaDirectaIdCHANGEServer;
	int motivoCHANGEId;
	boolean ventaDirecta;
	ParametroGeneral parametroGeneral;

	RadioButton rbtRegistrar;
	RadioButton rbtNoRegistrar;
	TextView tvCliente;
	TextView tvClienteTxt;
	Spinner spnProveedores;
	Spinner spnCategorias;
	Spinner spnProductos;
	TextView tvMotivoCHANGE;
	Spinner spnMotivosCHANGE;
	TextView tvProductoCHANGETxt;
	TextView tvProductoCHANGE;
	TextView tvInventarioTxt;
	TextView tvInventario;
	TextView tvCantidad;
	EditText etCantidad;
	Button btnMostrar;
	TextView tvCantidadCHANGEListview;
	TextView tvProductoCHANGEListview;
	ListView lvPreventasProductosCHANGE;
	Button btnAdicionar;
	Button btnConfirmar;
	Dialog dialog;
	
	ProgressDialog pdInsertarPrevenProductoCHANGE;
	ProgressDialog pdInsertarVenProductoCHANGE; 
	ProgressDialog pdDeletePreventaProductoCHANGE;
	ProgressDialog pdEsperaObtenerProductoAlmacenCHANGE;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedorpreventaproductochange);
		
		llVendedorPreventaProductoCHANGE = (LinearLayout)findViewById(R.id.llVendedorPreventaProductoCHANGE);
		tvCliente = ((TextView)findViewById(R.id.tvPreventaProductoCHANGECliente));
		rbtRegistrar = (RadioButton)findViewById(R.id.rbtPreventaProductoCHANGERegistrarCHANGE);
		rbtRegistrar.setOnClickListener(this);
		rbtNoRegistrar = (RadioButton)findViewById(R.id.rbtPreventaProductoCHANGENoRegistrarCHANGE);
		rbtNoRegistrar.setOnClickListener(this);
		spnProveedores = ((Spinner)findViewById(R.id.spnPreventaProductoCHANGEProveedor));
		spnCategorias = ((Spinner)findViewById(R.id.spnPreventaProductoCHANGECategoria));
	    spnProductos = ((Spinner)findViewById(R.id.spnPreventaProductoCHANGEProductoCHANGE));
	    tvMotivoCHANGE = (TextView)findViewById(R.id.tvPreventaProductoCHANGEMotivoCHANGETxt);
	    spnMotivosCHANGE = ((Spinner)findViewById(R.id.spnPreventaProductoCHANGEMotivoCHANGE));
	    btnMostrar = ((Button)findViewById(R.id.btnPreventaProductoCHANGEMostrarDetalles));
	    btnMostrar.setOnClickListener(this);
	    tvProductoCHANGETxt = ((TextView)findViewById(R.id.tvPreventaProductoCHANGEProductoTxt));
	    tvProductoCHANGE = ((TextView)findViewById(R.id.tvPreventaProductoCHANGEProductoCHANGE));
	    tvInventarioTxt = ((TextView)findViewById(R.id.tvPreventaProductoCHANGEInventarioUnidadesTxt));
	    tvInventario = ((TextView)findViewById(R.id.tvPreventaProductoCHANGEInventarioUnidades));
	    tvCantidad = ((TextView)findViewById(R.id.tvPreventaProductoCHANGECantidadTxt));
	    etCantidad = ((EditText)findViewById(R.id.etvPreventaProductoCHANGECantidad));
	    btnAdicionar = ((Button)findViewById(R.id.btnPreventaProductoCHANGEAdicionarProducto));
	    btnAdicionar.setOnClickListener(this);
	    lvPreventasProductosCHANGE = (ListView)findViewById(R.id.lvPreventaProductoCHANGEProductos);
	    tvProductoCHANGEListview = ((TextView)findViewById(R.id.tvPreventaProductoCHANGEProductoCHANGELv));
	    tvCantidadCHANGEListview = ((TextView)findViewById(R.id.tvPreventaProductoCHANGECantidadLv));
	    btnConfirmar = ((Button)findViewById(R.id.btnPreventaProductoCHANGEConfirmarPreventaCHANGE));
	    btnConfirmar.setOnClickListener(this);
	    
	    llVendedorPreventaProductoCHANGE.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
	    
	    ventaDirecta = false;
	    ventaDirecta = getIntent().getExtras().getBoolean("ventaDirecta");
	    
	    if(ventaDirecta)
	    {	    
	    	ventaDirectaIdCHANGE = 0;
		    ventaDirectaIdCHANGE = getIntent().getExtras().getInt("ventaDirectaIdCHANGE");
		    if(ventaDirectaIdCHANGE == 0)
		    {
		    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la ventaDirectaIdCambio.", 1);
		    	return;
		    }
		    		    
		    ventaDirectaIdCHANGEServer = 0;
		    ventaDirectaIdCHANGEServer = getIntent().getExtras().getInt("ventaDirectaIdCHANGEServer");
	    }
	    else
	    {	    	
	    	preventaIdCHANGE = 0;
		    preventaIdCHANGE = getIntent().getExtras().getInt("preventaIdCHANGE");
		    if(preventaIdCHANGE == 0)
		    {
		    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la preventaIdCambio.", 1);
		    	return;
		    }
		    
		    preventaIdCHANGEServer = 0;
		    preventaIdCHANGEServer = getIntent().getExtras().getInt("preventaIdCHANGEServer");
	    }
	    
	    clienteId = 0;
	    clienteId = getIntent().getExtras().getInt("clienteId");
	    if(clienteId == 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el clienteId.", 1);
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
	    	DespliegueControlesAdicionarPreventaCHANGE(false);
	        DespliegueControlesConfirmarPreventaCHANGE(false);
	        CargarInformacion();
	    }

	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.rbtPreventaProductoPOPNoRegistrarPOP:
			MostrarPantallaMapaPreventas();
			break;
		case R.id.btnPreventaProductoPOPMostrarDetalles:
			ObtenerDatosSeleccion();
			break;		
		case R.id.btnPreventaProductoPOPAdicionarProducto:
			AdicionarPreventaProductoCHANGE();
			break;
		case R.id.btnPreventaProductoPOPConfirmarPreventaPOP:
			ConfirmarPreventaCHANGE();
			break;
		}
	}
	
	private void DespliegueControlesAdicionarPreventaCHANGE(boolean estado)
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
	    
	    tvProductoCHANGETxt.setVisibility(visibilidad);
	    tvProductoCHANGE.setVisibility(visibilidad);
	    tvInventarioTxt.setVisibility(visibilidad);
	    tvInventario.setVisibility(visibilidad);
	    tvCantidad.setVisibility(visibilidad);
	    etCantidad.setVisibility(visibilidad);
	    tvMotivoCHANGE.setVisibility(visibilidad);
	    spnMotivosCHANGE.setVisibility(visibilidad);
	    btnAdicionar.setVisibility(visibilidad); 
	    etCantidad.setText("");
	}
	
	private void DespliegueControlesConfirmarPreventaCHANGE(boolean estado)
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
	    
	    tvProductoCHANGEListview.setVisibility(visibilidad);
	    tvCantidadCHANGEListview.setVisibility(visibilidad);
	    lvPreventasProductosCHANGE.setVisibility(visibilidad);
	    btnConfirmar.setVisibility(visibilidad);
    }

	public void CargarInformacion()
	{
		CargarParametroGeneral();
	    CargarCliente();
	    CargarProveedores();
	    CargarCategoriasChange();
	    CargarProductos();
	    CargarMotivosCHANGE();
	    if(ventaDirecta)
	    {
	    	ActualizarVentaListView();
	    }
	    else
	    {
	    	ActualizarPreventaListView();
	    }
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
	    	tvCliente.setText(clientePreventa.get_nombreCompleto().toString());
	    }
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
				
				ArrayList<CategoriaCHANGE> listadoCategoria = null;
				try
				{
					listadoCategoria = new CategoriaCHANGEBLL().ObtenerCategoriasCHANGEPorProveedorId(proveedor.get_proveedorId());
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
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las categorias CHANGE por proveedorId", 1);
					return;
				}
				
				ArrayAdapter<CategoriaCHANGE> arrayAdapter = new ArrayAdapter<CategoriaCHANGE>(getApplicationContext(),R.layout.disenio_spinner,listadoCategoria);
			    spnCategorias.setAdapter(arrayAdapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent){}
	    	
		});
	    
	}
	
	public void CargarCategoriasChange()
	{
		int proveedorId = ((Proveedor)spnProveedores.getSelectedItem()).get_proveedorId();
	    
		ArrayList<CategoriaCHANGE> listadoCategoriaCHANGE = null;
	    
	    try
	    {
	    	listadoCategoriaCHANGE = new CategoriaCHANGEBLL().ObtenerCategoriasCHANGEPorProveedorId(proveedorId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null|| localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las categoriasCHANGE por proveedorId: vacio");
	    	} 
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las categoriasCHANGE por proveedorId: " + localException.getMessage());
	    	} 
	    }
	    
	    if(listadoCategoriaCHANGE == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las categorias CHANGE por el proveedor seleccionado.", 1);
	    	return;
	    }
	    
	    ArrayAdapter<CategoriaCHANGE> localArrayAdapter = new ArrayAdapter<CategoriaCHANGE>(this,R.layout.disenio_spinner,listadoCategoriaCHANGE);
	    spnCategorias.setAdapter(localArrayAdapter);
	    
	    spnCategorias.setOnItemSelectedListener(new OnItemSelectedListener() 
	    {	    	
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
			{
				Proveedor proveedor = (Proveedor)spnProveedores.getSelectedItem();
				CategoriaCHANGE categoriaCHANGE = (CategoriaCHANGE)spnCategorias.getSelectedItem();
				
				ArrayList<ProductoCHANGE> listadoProductoCHANGE = null;
				try
				{
					listadoProductoCHANGE = new ProductoCHANGEBLL().ObtenerProductosCHANGEPorProveedorNoEnPreventaProductoCHANGE(proveedor.get_proveedorId(),
																		categoriaCHANGE.get_categoriaId(),preventaIdCHANGE);
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
				        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productosCHANGE por proveedorId: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productosCHANGE por proveedorId: " + localException.getMessage());
					} 
				}
				
				if(listadoProductoCHANGE == null)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los productos cambio por proveedorId", 1);
					return;
				}
				
				ArrayAdapter<ProductoCHANGE> arrayAdapter = new ArrayAdapter<ProductoCHANGE>(getApplicationContext(),R.layout.disenio_spinner,listadoProductoCHANGE);
			    spnProductos.setAdapter(arrayAdapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent){}
	    	
		});
	}
	
	public void CargarProductos()
	{
		int proveedorId = ((Proveedor)spnProveedores.getSelectedItem()).get_proveedorId();
		int categoriaId = ((CategoriaPOP)spnCategorias.getSelectedItem()).get_categoriaId();
	    ArrayList<ProductoCHANGE> listadoProductoCHANGE = null;
	    
	    try
	    {
	    	listadoProductoCHANGE = new ProductoCHANGEBLL().ObtenerProductosCHANGEPorProveedorNoEnPreventaProductoCHANGE(proveedorId,
	    													categoriaId,preventaIdCHANGE);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null|| localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productosCHANGE por proveedorId: vacio");
	    	} 
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productosCHANGE por proveedorId: " + localException.getMessage());
	    	} 
	    }
	    
	    if(listadoProductoCHANGE == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los productosCHANGE por el proveedor seleccionado.", 1);
	    	return;
	    }
	    
	    ArrayAdapter<ProductoCHANGE> localArrayAdapter = new ArrayAdapter<ProductoCHANGE>(this,R.layout.disenio_spinner,listadoProductoCHANGE);
	    spnProductos.setAdapter(localArrayAdapter);
	    
	    spnProductos.setOnItemSelectedListener(new OnItemSelectedListener() 
	    {	    	
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
			{
				DespliegueControlesAdicionarPreventaCHANGE(false);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent){}
	    	
		});
	}

	public void CargarMotivosCHANGE()
	{
	    ArrayList<MotivoCHANGE> listadoMotivoCHANGE = null;
	    
	    try
	    {
	    	listadoMotivoCHANGE = new MotivoCHANGEBLL().ObtenerMotivosCHANGE();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null|| localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos CHANGE: vacio");
	    	} 
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos CHANGE: " + localException.getMessage());
	    	} 
	    }
	    
	    if(listadoMotivoCHANGE == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los motivos CHANGE.", 1);
	    	return;
	    }
	    
	    ArrayAdapter<MotivoCHANGE> localArrayAdapter = new ArrayAdapter<MotivoCHANGE>(this,R.layout.disenio_spinner,listadoMotivoCHANGE);
	    spnMotivosCHANGE.setAdapter(localArrayAdapter);
	    
	    spnMotivosCHANGE.setOnItemSelectedListener(new OnItemSelectedListener() 
	    {	    	
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
			{
				motivoCHANGEId = ((MotivoPop)spnMotivosCHANGE.getSelectedItem()).get_motivoPopId();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent){}
	    	
		});
	}
	
	public void ActualizarPreventaListView()
	{
		listadoPreventaProductoCHANGE = null;
		try
		{
			listadoPreventaProductoCHANGE = new PreventaProductoCHANGEBLL().ObtenerPreventasProductoCHANGEPorPreventaCHANGEId(preventaIdCHANGE);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventaProductoCHANGE por preventaIdCHANGE: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventaProductoCHANGE por preventaIdCHANGE: " + localException.getMessage());
			}
		}
		      
		if(listadoPreventaProductoCHANGE == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No existen preventaProductoCHANGE temporales.", 1);
			DespliegueControlesConfirmarPreventaCHANGE(false);
			lvPreventasProductosCHANGE.setAdapter(null);
		}
		else
		{
			DespliegueControlesConfirmarPreventaCHANGE(true);
			lvPreventasProductosCHANGE.setVisibility(View.VISIBLE);
		    LlenarPreventaListView();
		    EliminarPreventaItemListView();	
		}  
	}
	
	private void LlenarPreventaListView()
	{
	    MiAdaptadorPreventaLista localMiAdaptadorPreventaLista = new MiAdaptadorPreventaLista(getApplicationContext());
	    	    
    	if(listadoPreventaProductoCHANGE == null)
	    {
	    	lvPreventasProductosCHANGE.setAdapter(null);
	    }
	    else
	    {
		    ViewGroup.LayoutParams localLayoutParams = lvPreventasProductosCHANGE.getLayoutParams();
		    localLayoutParams.height = ((int)(50*getApplicationContext().getResources().getDisplayMetrics().density) * listadoPreventaProductoCHANGE.size());
		    lvPreventasProductosCHANGE.setLayoutParams(localLayoutParams);
		    lvPreventasProductosCHANGE.setAdapter(localMiAdaptadorPreventaLista);
	    }
	}

	class MiAdaptadorPreventaLista extends ArrayAdapter<PreventaProductoCHANGE>
	{
		private Context _context;
		
		public MiAdaptadorPreventaLista(Context context)
		{
			super(context,R.layout.disenio_vendedorpreventaproducto,listadoPreventaProductoCHANGE);
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
			
			TextView descripcion25 = (TextView)localView.findViewById(R.id.tvVendedorVentaDirectaProductoCliente);
			TextView cantidad = (TextView)localView.findViewById(R.id.tvPreventaDisenioCantidad);
			TextView monto = (TextView)localView.findViewById(R.id.tvVendedorPreventaProductoProveedor);
			TextView montoPaquete = (TextView)localView.findViewById(R.id.tvVendedorPreventaProductoProducto);
			TextView precio = (TextView)localView.findViewById(R.id.tvPreventaDisenioPrecioUnitario);
			ImageView bullet = (ImageView)localView.findViewById(R.id.imgvLoginLogotipo);
			bullet.setImageResource(R.drawable.bullet_eliminar);
			
			PreventaProductoCHANGE localPreventaProductoCHANGE = (PreventaProductoCHANGE)listadoPreventaProductoCHANGE.get(position);
      
			if(localPreventaProductoCHANGE.get_productoCHANGEId() != 0)
			{
				ProductoCHANGE localProductoCHANGE = null;
				try
				{
					localProductoCHANGE = new ProductoCHANGEBLL().ObtenerProductoCHANGEPorProductoId(localPreventaProductoCHANGE.get_productoCHANGEId());
				}
				catch (Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
				        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del producto CHANGE: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del producto CHANGE: " + localException.getMessage());
					} 
				}
				
				if(localProductoCHANGE == null)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el productoCHANGE.", 1);
		            return null;
				}
				
				descripcion25.setText(localProductoCHANGE.get_descripcion25());
				cantidad.setText("");
				precio.setText("");
				monto.setText(String.valueOf(localPreventaProductoCHANGE.get_cantidad()));
				montoPaquete.setText("");
			}

			return localView;
		}
	}
	
	private void EliminarPreventaItemListView()
	{
		((ListView)findViewById(R.id.lvPreventaProductoCHANGEProductos)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
			public void onItemClick(AdapterView<?> parent, View view,final int position, long id)
			{
				dialog = new Dialog(Vendedorpreventaproductochange.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.diseniodialogo_vendedorcierredia);
				dialog.setTitle("Eliminando item");
				dialog.setCancelable(false);
				dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.colorBluePale));
				
				TextView tvMensaje = (TextView)dialog.findViewById(R.id.tvDialogoMensaje);
				tvMensaje.setText("Esta seguro de eliminar el producto CHANGE?");
				
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
								
								PreventaProductoCHANGE localPreventaProductoCHANGE = listadoPreventaProductoCHANGE.get(position);
								
								if(BorrarPreventaProductoCHANGEDispositivo(localPreventaProductoCHANGE.get_id()))
								{
									theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la preventaCHANGE eliminado del dispositivo.", 1);
									CargarProductos();
									ActualizarPreventaListView();
								} 
								else 
								{
									theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo eliminar el producto CHANGE de la preventa.", 1);
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
	
	public void ActualizarVentaListView()
	{		
		listadoVentaProductoCHANGE = null;
		try
		{
			listadoVentaProductoCHANGE = new VentaProductoCHANGEBLL().ObtenerVentasPorductoCHANGEPorVentaCHANGEId(ventaDirectaIdCHANGE);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventaProductoCHANGE por ventaIdCHANGE : vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventaProductoCHANGE por ventaIdCHANGE: " + localException.getMessage());
			}
		}
		      
		if(listadoVentaProductoCHANGE == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No existen ventaProductoCHANGE temporales.", 1);
			DespliegueControlesConfirmarPreventaCHANGE(false);
			lvPreventasProductosCHANGE.setAdapter(null);
		}
		else
		{
			DespliegueControlesConfirmarPreventaCHANGE(true);
			lvPreventasProductosCHANGE.setVisibility(View.VISIBLE);
		    LlenarVentaListView();
		    EliminarVentaItemListView();	
		}		    
	}
	
	private void LlenarVentaListView()
	{
	    MiAdaptadorVentaLista localMiAdaptadorVentaLista = new MiAdaptadorVentaLista(getApplicationContext());
	    	    
    	if(listadoVentaProductoCHANGE == null)
	    {
	    	lvPreventasProductosCHANGE.setAdapter(null);
	    }
	    else
	    {
		    ViewGroup.LayoutParams localLayoutParams = lvPreventasProductosCHANGE.getLayoutParams();
		    localLayoutParams.height = ((int)(50*getApplicationContext().getResources().getDisplayMetrics().density) * listadoVentaProductoCHANGE.size());
		    lvPreventasProductosCHANGE.setLayoutParams(localLayoutParams);
		    lvPreventasProductosCHANGE.setAdapter(localMiAdaptadorVentaLista);
	    }
	}

	class MiAdaptadorVentaLista extends ArrayAdapter<VentaProductoCHANGE>
	{
		private Context _context;
		
		public MiAdaptadorVentaLista(Context context)
		{
			super(context,R.layout.disenio_vendedorpreventaproducto,listadoVentaProductoCHANGE);
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
			
			TextView descripcion25 = (TextView)localView.findViewById(R.id.tvVendedorVentaDirectaProductoCliente);
			TextView cantidad = (TextView)localView.findViewById(R.id.tvPreventaDisenioCantidad);
			TextView monto = (TextView)localView.findViewById(R.id.tvVendedorPreventaProductoProveedor);
			TextView montoPaquete = (TextView)localView.findViewById(R.id.tvVendedorPreventaProductoProducto);
			TextView precio = (TextView)localView.findViewById(R.id.tvPreventaDisenioPrecioUnitario);
			ImageView bullet = (ImageView)localView.findViewById(R.id.imgvLoginLogotipo);
			bullet.setImageResource(R.drawable.bullet_eliminar);
			
			VentaProductoCHANGE localVentaProductoCHANGE = (VentaProductoCHANGE)listadoVentaProductoCHANGE.get(position);
			
			if(localVentaProductoCHANGE.get_productoCHANGEId() != 0)
			{
				ProductoCHANGE localProductoCHANGE = null;
				try
				{
					localProductoCHANGE = new ProductoCHANGEBLL().ObtenerProductoCHANGEPorProductoId(localVentaProductoCHANGE.get_productoCHANGEId());
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
				
				if(localProductoCHANGE == null)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el productoCHANGE.", 1);
		            return null;
				}
				
				descripcion25.setText(localProductoCHANGE.get_descripcion25());
				cantidad.setText("");
				precio.setText("");
				monto.setText(String.valueOf(localVentaProductoCHANGE.get_cantidad()));
				montoPaquete.setText("");
			}
		
			return localView;
		}
	}
	
	private void EliminarVentaItemListView()
	{
		((ListView)findViewById(R.id.lvPreventaProductoCHANGEProductos)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
			public void onItemClick(AdapterView<?> parent, View view,final int position, long id)
			{
				dialog = new Dialog(Vendedorpreventaproductochange.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.diseniodialogo_vendedorcierredia);
				dialog.setTitle("Eliminando item");
				dialog.setCancelable(false);
				dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.colorBluePale));
				
				TextView tvMensaje = (TextView)dialog.findViewById(R.id.tvDialogoMensaje);
				tvMensaje.setText("Esta seguro de eliminar el producto CHANGE?");
				
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

								VentaProductoCHANGE localVentaProductoCHANGE = listadoVentaProductoCHANGE.get(position);
								
								if(BorrarVentaProductoCHANGEDispositivo(localVentaProductoCHANGE.get_id()))
								{
									theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la ventaCHANGE eliminado del dispositivo.", 1);
									CargarProductos();
									ActualizarVentaListView();
								} 
								else 
								{
									theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo eliminar el producto CHANGE de la preventa.", 1);
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
	
	private boolean BorrarPreventaProductoCHANGEDispositivo(int id)
	 {
		 boolean bool = false;
		 try
		 {
			 bool = new PreventaProductoCHANGEBLL().BorrarPreventaProductoCHANGEPor(id);
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al borrar el item de la preventaProductoCHANGE por Id: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al borrar el item de la preventaProductoCHANGE por Id: " + localException.getMessage());
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
	
	private boolean BorrarVentaProductoCHANGEDispositivo(int id)
	{
		boolean bool = false;
		try
		{
			bool = new VentaProductoCHANGEBLL().BorrarVentaProductoCHANGEPor(id);
	 	}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar el item de la ventaProductoCHANGE por Id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar el item de la ventaProductoCHANGE por Id: " + localException.getMessage());
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
		 
		 productoId = ((ProductoCHANGE)spnProductos.getSelectedItem()).get_productoId();
		 if(productoId == 0)
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(), "Debe seleccionar un producto.", 1);
			 return;
		 }
		 
		 productoCHANGE = null;
		 try
		 {
			 productoCHANGE = new ProductoCHANGEBLL().ObtenerProductoCHANGEPorProductoId(productoId);
		 }
		 catch(Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del productoCHANGE: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del productoCHANGE: " + localException.getMessage());
			 } 
		 }
		 
		 if(productoCHANGE == null)
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del productoCHANGE", 1);
			 return;
		 }
		 
		 AlmacenCHANGEProducto almacenCHANGEProducto = null;
		 try
		 {
			 almacenCHANGEProducto = new AlmacenCHANGEProductoBLL().ObtenerAlmacenCHANGEProductoPorProductoId(productoCHANGE.get_productoId());
		 }
		 catch(Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al obtener el inventario del almacenCHANGE por productoId: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al obtener el inventario del almacenCHANGE por productoId: " + localException.getMessage());
			 }
		 }
		 
		 if(almacenCHANGEProducto == null)
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el inventarioCHANGE del producto", 1);
			 return;
		 }
		        
	     DespliegueControlesAdicionarPreventaCHANGE(true);
	     
	     tvProductoCHANGE.setText(productoCHANGE.get_descripcion25());
	     tvInventario.setText(String.valueOf(almacenCHANGEProducto.get_saldo()));
	 }

	private void AdicionarPreventaProductoCHANGE()
	{
		  int cantidad;
		  
		  if(motivoCHANGEId <= 0)
		  {
			  theUtilidades.MostrarMensaje(getApplicationContext(), "El motivo CHANGE es requerido.", 1);
			  return;
		  }
		  
		  if(etCantidad.getText().length() <= 0)
		  {
			  theUtilidades.MostrarMensaje(getApplicationContext(), "La cantidad es requerida.", 1);
			  return;
		  }
		  
		  cantidad = 0;
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
		  
		  if(cantidad <= 0)
		  {
			  theUtilidades.MostrarMensaje(getApplicationContext(), "La cantidad debe ser mayor a cero.", 1);
			  return;
		  }
		  
		  if(ventaDirecta)
		  {
			  VentaProductoCHANGE localVentaProductoCHANGE = new VentaProductoCHANGE();
		      localVentaProductoCHANGE.set_ventaCHANGEId(ventaDirectaIdCHANGE);
		      localVentaProductoCHANGE.set_ventaCHANGEIdServer(ventaDirectaIdCHANGEServer);
		      localVentaProductoCHANGE.set_productoCHANGEId(productoCHANGE.get_productoId());
		      localVentaProductoCHANGE.set_cantidad(cantidad);
		      localVentaProductoCHANGE.set_clienteId(clientePreventa.get_clienteId());
		      localVentaProductoCHANGE.set_syncro(false);
		      localVentaProductoCHANGE.set_motivoCHANGEId(motivoCHANGEId);
		      
		      if (ValidarExistenciasAlmacenCHANGEDispositivo(productoCHANGE.get_productoId(),cantidad))
		      {	  	    	  
		    	  if(InsertarVentaProductoCHANGEDispositivo(localVentaProductoCHANGE))
			      {
			    	  theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la venta insertado en el dispositivo.", 1);
			    	  CargarProductos();
			    	  DespliegueControlesAdicionarPreventaCHANGE(false);
			    	  ActualizarVentaListView();
			      }
			      else 
			      {
			    	  theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el producto de la venta en el dispositivo.",1);
			    	  return;
			      }
		      }
		      else
		      {
		    	  int saldoUnidades = ObtenerExistenciaProductoEnAlmacenCHANGEPorProductoId(productoCHANGE.get_productoId());
		    	  theUtilidades.MostrarMensaje(getApplicationContext(), "No existe productoPOP en el almacen del dispositivo."
		    	  									+ "Cantidad actual: " + String.valueOf(saldoUnidades) + " unidades.", 1);
		    	  return;
		      }
		  }
		  else
		  {
			  PreventaProductoCHANGE localPreventaProductoCHANGE = new PreventaProductoCHANGE();
		      localPreventaProductoCHANGE.set_preventaCHANGEId(preventaIdCHANGE);
		      localPreventaProductoCHANGE.set_preventaCHANGEIdServer(preventaIdCHANGEServer);
		      localPreventaProductoCHANGE.set_productoCHANGEId(productoCHANGE.get_productoId());
		      localPreventaProductoCHANGE.set_cantidad(cantidad);
		      localPreventaProductoCHANGE.set_clienteId(clientePreventa.get_clienteId());
		      localPreventaProductoCHANGE.set_syncro(false);
		      localPreventaProductoCHANGE.set_motivoCHANGEId(motivoCHANGEId);
		      
		      if (ValidarExistenciasAlmacenCHANGEDispositivo(productoCHANGE.get_productoId(),cantidad))
		      {	  	    	  
		    	  if(InsertarPreventaProductoCHANGEDispositivo(localPreventaProductoCHANGE))
			      {
			    	  theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la preventa insertado en el dispositivo.", 1);
			    	  CargarProductos();
			    	  DespliegueControlesAdicionarPreventaCHANGE(false);
			    	  ActualizarPreventaListView();
			      }
			      else 
			      {
			    	  theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el producto de la preventa en el dispositivo.",1);
			    	  return;
			      }
		      }
		      else
		      {
		    	  int saldoUnidades = ObtenerExistenciaProductoEnAlmacenCHANGEPorProductoId(productoCHANGE.get_productoId());
		    	  theUtilidades.MostrarMensaje(getApplicationContext(), "No existe producto CHANGE en el almacen del dispositivo."
		    	  									+ "Cantidad actual: " + String.valueOf(saldoUnidades) + " unidades.", 1);
		    	  return;
		      }
		  }
	 }
	
	private boolean ValidarExistenciasAlmacenCHANGEDispositivo(int productoId, int cantidad)
	{
		AlmacenCHANGEProducto localAlmacenCHANGEProducto = null;
		 try
		 {
			 localAlmacenCHANGEProducto = new AlmacenCHANGEProductoBLL().ValidarExistenciasAlmacenCHANGEDispositivo(productoId,cantidad);
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al obtener el producto CHANGE del almacen: vacio");
			 }
			 else
			 {
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el producto CHANGE del almacen: " + localException.getMessage());
			 }
			 return false;
		}
	    if (localAlmacenCHANGEProducto == null)
	    {
	    	return false;
	    }
	    else
	    {
	    	return true;
	    }
	 }
	
	private boolean InsertarPreventaProductoCHANGEDispositivo(PreventaProductoCHANGE paramPreventaProductoCHANGE)
	{
		long l = 0;
		try
		{
			l = new PreventaProductoCHANGEBLL().InsertarPreventaProductoCHANGE(paramPreventaProductoCHANGE.get_preventaCHANGEId(),paramPreventaProductoCHANGE.get_preventaCHANGEIdServer(),
					paramPreventaProductoCHANGE.get_productoCHANGEId(),paramPreventaProductoCHANGE.get_cantidad(),paramPreventaProductoCHANGE.get_clienteId(),
					paramPreventaProductoCHANGE.is_syncro(),"",paramPreventaProductoCHANGE.get_motivoCHANGEId());
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preVentaProductoCHANGE en el dispositivo: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preVentaProductoCHANGE en el dispositivo: " + localException.getMessage());
	    	}
	    	return false;
	    }
	    	
	    if(l==0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la preventaProductoCHANGE en el dispositivo.", 1);
	  	     return false;		
	    }
	    else
	    {
	    	return true;
	    }
	}
	
	private boolean InsertarVentaProductoCHANGEDispositivo(VentaProductoCHANGE paramVentaProductoCHANGE)
	{
		long l = 0;
		try
		{
			l = new VentaProductoCHANGEBLL().InsertarVentaProductoCHANGE(paramVentaProductoCHANGE.get_ventaCHANGEId(),paramVentaProductoCHANGE.get_ventaCHANGEIdServer(),
					paramVentaProductoCHANGE.get_productoCHANGEId(),paramVentaProductoCHANGE.get_cantidad(),paramVentaProductoCHANGE.get_clienteId(),
					paramVentaProductoCHANGE.is_syncro(),"",paramVentaProductoCHANGE.get_motivoCHANGEId());
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ventaProductoCHANGE en el dispositivo: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ventaProductoCHANGE en el dispositivo: " + localException.getMessage());
	    	}
	    	return false;
	    }
	    	
	    if(l==0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la ventaProductoCHANGE en el dispositivo.", 1);
	  	     return false;		
	    }
	    else
	    {
	    	return true;
	    }
	}
	
	private int ObtenerExistenciaProductoEnAlmacenCHANGEPorProductoId(int productoId)
	{
		AlmacenCHANGEProducto almacenCHANGEProducto = null;
		try
		{
			almacenCHANGEProducto = new AlmacenCHANGEProductoBLL().ObtenerAlmacenCHANGEProductoPorProductoId(productoId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el saldo en unidades del productoCHANGE del almacen: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el saldo en unidades del productoCHANGE del almacen: " + localException.getMessage());
			}
			return 0;
		}
		
		if(almacenCHANGEProducto==null)
		{
			return 0;
		}
		else
		{
			return almacenCHANGEProducto.get_saldo();
		}
	}

	private void ConfirmarPreventaCHANGE()
	{
		btnConfirmar.setVisibility(View.INVISIBLE);
		
		if(ventaDirecta)
		{
			if(listadoVentaProductoCHANGE != null && listadoVentaProductoCHANGE.size() > 0)
			{
				if(clienteId > 0)
				{
					if(ObtenerVentasProductoCHANGENoSincronizadas())
					{
						SincronizarVentaProductoCHANGE();
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No existen productos de la venta CHANGE que sincronizar.", 1);
						return;
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Para ingresar los productos CHANGE debe sincronizar el cliente.", 1);
					if(parametroGeneral.is_habilitarCambio())
					{
						MostrarPantallaMenuOpciones();
					}
					else
					{
						MostrarPantallaVentaDirecta();
					}
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No Existen items que sincronizar.", 1);
				return;
			}
		}
		else
		{
			if(listadoPreventaProductoCHANGE != null && listadoPreventaProductoCHANGE.size() > 0)
			{
				if(clienteId > 0)
				{
					if(ObtenerPreventasProductoCHANGENoSincronizadas())
					{
						SincronizarPreventaProductoCHANGE();
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No existen productos de la venta CHANGE que sincronizar.", 1);
						return;
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Para ingresar los productos CHANGE debe sincronizar el cliente.", 1);
					if(parametroGeneral.is_habilitarCambio())
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
				theUtilidades.MostrarMensaje(getApplicationContext(), "No Existen items que sincronizar.", 1);
				return;
			}
		}
	}
		
	private boolean ObtenerPreventasProductoCHANGENoSincronizadas()
	{
		listadoPreventaProductoCHANGENoSincronizadas = null;
	    try
	    {
	    	listadoPreventaProductoCHANGENoSincronizadas = new PreventaProductoCHANGEBLL().ObtenerPreventasPorductoCHANGENoSincronizadasPor(preventaIdCHANGE);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la preventaCHANGE no sincronizada por preventaIdCHANGE: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la preventaCHANGE no sincronizada por preventaIdCHANGE: " + localException.getMessage());
	    	}
	    }
	    
	    if (listadoPreventaProductoCHANGENoSincronizadas == null) 
	    {
 	        return false;
	    }  
	    else
	    {
	    	return true;
	    }
	}
	
	private boolean ObtenerVentasProductoCHANGENoSincronizadas()
	{
		listadoVentaProductoCHANGENoSincronizadas = null;
	    try
	    {
	    	listadoVentaProductoCHANGENoSincronizadas = new VentaProductoCHANGEBLL().ObtenerVentasPorductoCHANGENoSincronizadasPor(ventaDirectaIdCHANGE);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la ventaCHANGE no sincronizada por ventaIdCHANGE: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la ventaCHANGE no sincronizada por ventaIdCHANGE: " + localException.getMessage());
	    	}
	    }
	    
	    if (listadoVentaProductoCHANGENoSincronizadas == null) 
	    {
 	        return false;
	    }  
	    else
	    {
	    	return true;
	    }
	}
	
	private void SincronizarPreventaProductoCHANGE() 
	{
		if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
		{
			if(listadoPreventaProductoCHANGENoSincronizadas != null && listadoPreventaProductoCHANGENoSincronizadas.size()>0)
			{
				if(listadoPreventaProductoCHANGENoSincronizadas.get(0).get_preventaCHANGEIdServer()!=0)
				{
					ProductoCHANGECosto productoCHANGECosto=null;
					try
					{
						productoCHANGECosto = new ProductoCHANGECostoBLL().ObtenerProductoCHANGECostoPorProductoId(listadoPreventaProductoCHANGENoSincronizadas.get(0).get_productoCHANGEId());
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
				    	{
				            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el costoProductoCHANGE por productoId: vacio");
				    	}
				    	else
				    	{
				    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el costoProductoCHANGE por productoId: " + localException.getMessage());
				    	}
					}
					
					if(productoCHANGECosto == null)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el costo del productoCHANGE.", 1);
				    	return;
					}
					else
					{
						InsertarPreventaProductoCHANGE(listadoPreventaProductoCHANGENoSincronizadas.get(0),productoCHANGECosto);	
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Para sincronizar el producto CHANGE, primero debes sincronizar la preventa.", 1);
					MostrarPantallaMenuVendedor();
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No existen productos de la preventa CHANGE que sincronizar.", 1);
				return;
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No esta conectado a internet, intentelo mas tarde.", 1);
			if(parametroGeneral.is_habilitarCambio())
			{
				MostrarPantallaMenuOpciones();
			}
			else
			{
				MostrarPantallaMenuVendedor();
			}
		}
	}
	
	private void SincronizarVentaProductoCHANGE() 
	{
		if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
		{
			if(listadoVentaProductoCHANGENoSincronizadas != null && listadoVentaProductoCHANGENoSincronizadas.size()>0)
			{
				if(listadoVentaProductoCHANGENoSincronizadas.get(0).get_ventaCHANGEIdServer()!=0)
				{
					ProductoCHANGECosto productoCHANGECosto=null;
					try
					{
						productoCHANGECosto = new ProductoCHANGECostoBLL().ObtenerProductoCHANGECostoPorProductoId(listadoVentaProductoCHANGENoSincronizadas.get(0).get_productoCHANGEId());
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
				    	{
				            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el costoProductoCHANGE por productoId: vacio");
				    	}
				    	else
				    	{
				    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el costoProductoCHANGE por productoId: " + localException.getMessage());
				    	}
					}
					
					if(productoCHANGECosto == null)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el costo del productoPOP.", 1);
				    	return;
					}
					else
					{
						InsertarVentaProductoCHANGE(listadoVentaProductoCHANGENoSincronizadas.get(0),productoCHANGECosto);	
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Para sincronizar el producto CHANGE, primero debes sincronizar la venta.", 1);
					MostrarPantallaMenuVendedor();
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No existen productos de la venta CHANGE que sincronizar.", 1);
				return;
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No esta conectado a internet, intentelo mas tarde.", 1);
			MostrarPantallaMenuVendedor();
		}
	}
	
	private void InsertarPreventaProductoCHANGE(PreventaProductoCHANGE localPreventaProductoCHANGE, ProductoCHANGECosto productoCHANGECosto)
	 {
		pdInsertarPrevenProductoCHANGE = new ProgressDialog(this);
		pdInsertarPrevenProductoCHANGE.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				pdInsertarPrevenProductoCHANGE.setMessage("Insertando productos preventa CHANGE ...");
		pdInsertarPrevenProductoCHANGE.setCancelable(false);
		pdInsertarPrevenProductoCHANGE.setCanceledOnTouchOutside(false);
	    
	    WSInsertarPreventaProductoCHANGE localWSInsertarPreventaProductoCHANGE = new WSInsertarPreventaProductoCHANGE(localPreventaProductoCHANGE,productoCHANGECosto);
	    try
	    {
	      localWSInsertarPreventaProductoCHANGE.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventaProductoCHANGE: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarPreVentaProductoCHANGE: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertarPreventaProductoCHANGE", 1);
	    }
	 }
	 
	public class WSInsertarPreventaProductoCHANGE extends AsyncTask<Void, Integer, Boolean>
	 {
		 String PREVENTAPRODUCTOCHANGE_METHOD_NAME = "InsertPreVentaProductoChange";
		 String PREVENTAPRODUCTOCHANGE_SOAP_ACTION = NAMESPACE + PREVENTAPRODUCTOCHANGE_METHOD_NAME;
		 
		 private PreventaProductoCHANGE _preventaProductoCHANGE;
		 private ProductoCHANGECosto _productoCHANGECosto;
		 
		 boolean WSInsertarPreVentaProductoCHANGE;
		 int resultadoInt;
		 String resultadoString;
		 SoapObject localSoapObjects;
	    
	    public WSInsertarPreventaProductoCHANGE(PreventaProductoCHANGE paramPreventaProductoCHANGE,ProductoCHANGECosto paramProductoCHANGECosto)
	    {
	    	this._preventaProductoCHANGE = paramPreventaProductoCHANGE;
	    	this._productoCHANGECosto = paramProductoCHANGECosto;
	    }
	    
	    protected void onPreExecute()
	    {
	    	pdInsertarPrevenProductoCHANGE.show();
	    }
	    
	    protected Boolean doInBackground(Void... paramVarArgs)
	    {
	    	WSInsertarPreVentaProductoCHANGE = false;
	      
	    	float monto = _preventaProductoCHANGE.get_cantidad() * _productoCHANGECosto.get_costo();
	    	
	    	SoapObject localSoapObject = new SoapObject(NAMESPACE, this.PREVENTAPRODUCTOCHANGE_METHOD_NAME);
	    	localSoapObject.addProperty("preVentaId", _preventaProductoCHANGE.get_preventaCHANGEIdServer());
	    	localSoapObject.addProperty("productoId", _preventaProductoCHANGE.get_productoCHANGEId());
	    	localSoapObject.addProperty("cantidad", _preventaProductoCHANGE.get_cantidad());
	    	localSoapObject.addProperty("monto", String.valueOf(monto));
	   		localSoapObject.addProperty("empleadoId", loginEmpleado.get_empleadoId());
	   		localSoapObject.addProperty("costoId",_productoCHANGECosto.get_costoId());
	   		localSoapObject.addProperty("almacenId",(loginEmpleado.get_almacenId()));
	   		localSoapObject.addProperty("motivoPopId",(_preventaProductoCHANGE.get_motivoCHANGEId()));
	       
	   		SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
	   		localSoapSerializationEnvelope.dotNet = true;
	   		localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
	   		HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
	   		try
		    	{
	   			localHttpTransportSE.call(PREVENTAPRODUCTOCHANGE_SOAP_ACTION, localSoapSerializationEnvelope);
	   			
	   			localSoapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
	   			if(localSoapObjects!=null)
	   			{
	   				resultadoInt = Integer.parseInt(localSoapObjects.getPropertyAsString("Id"));
	       			resultadoString = localSoapObjects.getPropertyAsString("Resultado");    				
	   			}
	   			
	   			if(resultadoString.equals("OK") && resultadoInt > 0)
	   			{
	   				WSInsertarPreVentaProductoCHANGE = true;
	   			}
	   			return true;
		    	}
	   		catch (Exception localException)
	   		{
	   			WSInsertarPreVentaProductoCHANGE = false;
	   			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	   			{
	   		        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventaProductoCHANGE: vacio");
	   			}
	   			else
	   			{
	   				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventaProductoCHANGE: " + localException.getMessage());
	   			} 
	   			return true;
	   		}
	   	}
	    
	    protected void onPostExecute(Boolean ejecutado)
	    {
	    	if(pdInsertarPrevenProductoCHANGE.isShowing())
	    	{
	    		pdInsertarPrevenProductoCHANGE.dismiss();
	    	}
	    	
	    	if(ejecutado) 
	    	{
	    		if(WSInsertarPreVentaProductoCHANGE || (resultadoString != null && resultadoString.equals("Preventa Producto Change Repetido") && resultadoInt <= 0))
	    		{
	    			long l = 0;
	    			try
	    			{
	    				l = new PreventaProductoCHANGEBLL().ModificarPreventaProductoCHANGENoSincronizadaPor(_preventaProductoCHANGE.get_id(), resultadoInt);
					}
					catch (Exception localException)
	    			{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del producto de la preventa CHANGE: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del producto de la preventa CHANGE: " + localException.getMessage());
						} 
					}
						
	    			if(l==0)
	    			{
	    				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del producto de la preventa CHANGE.", 1);
	    				return;
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "Producto de la preventa CHANGE sincronizado.", 1);
							
						if(ObtenerPreventasProductoCHANGENoSincronizadas())
						{
							SincronizarPreventaProductoCHANGE();
						}
						else
						{
							ObtenerProductosAlmacenCHANGE();
						}
					}
	    		}
	    		else 
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo ejecutar el webservice WSInsertPreVentaProductoCHANGE.", 1);
	    			if(parametroGeneral.is_habilitarCambio())
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
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "El WebService InsertPreVentaProductoCHANGE no se ejecuto correctamente. ", 1);
	    		return;
	    	}
	    }
	 }
	
	private void InsertarVentaProductoCHANGE(VentaProductoCHANGE localVentaProductoCHANGE, ProductoCHANGECosto productoCHANGECosto)
	 {
		pdInsertarVenProductoCHANGE = new ProgressDialog(this);
		pdInsertarVenProductoCHANGE.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdInsertarVenProductoCHANGE.setMessage("Insertando productos venta CHANGE ...");
		pdInsertarVenProductoCHANGE.setCancelable(false);
		pdInsertarVenProductoCHANGE.setCanceledOnTouchOutside(false);
	    
	    WSInsertarVentaProductoCHANGE localWSInsertarVentaProductoCHANGE = new WSInsertarVentaProductoCHANGE(localVentaProductoCHANGE,productoCHANGECosto);
	    try
	    {
	      localWSInsertarVentaProductoCHANGE.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertVentaProductoCHANGE: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarVentaProductoCHANGE: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertarVentaProductoCHANGE", 1);
	    }
	 }
	 
	public class WSInsertarVentaProductoCHANGE extends AsyncTask<Void, Integer, Boolean>
	 {
		 String VENTAPRODUCTOCHANGE_METHOD_NAME = "InsertVentaProductoChange";
		 String VENTAPRODUCTOCHANGE_SOAP_ACTION = NAMESPACE + VENTAPRODUCTOCHANGE_METHOD_NAME;
		 
		 private VentaProductoCHANGE _ventaProductoCHANGE;
		 private ProductoCHANGECosto _productoCHANGECosto;
		 
		 boolean WSInsertarVentaProductoCHANGE;
		 int resultadoInt;
		 String resultadoString;
		 SoapObject localSoapObjects;
	    
	    public WSInsertarVentaProductoCHANGE(VentaProductoCHANGE paramVentaProductoCHANGE,ProductoCHANGECosto paramProductoCHANGECosto)
	    {
	    	this._ventaProductoCHANGE = paramVentaProductoCHANGE;
	    	this._productoCHANGECosto = paramProductoCHANGECosto;
	    }
	    
	    protected void onPreExecute()
	    {
	    	pdInsertarVenProductoCHANGE.show();
	    }
	    
	    protected Boolean doInBackground(Void... paramVarArgs)
	    {
	    	WSInsertarVentaProductoCHANGE = false;
	      
	    	float monto = _ventaProductoCHANGE.get_cantidad() * _productoCHANGECosto.get_costo();
	    	
	    	SoapObject localSoapObject = new SoapObject(NAMESPACE, this.VENTAPRODUCTOCHANGE_METHOD_NAME);
	    	localSoapObject.addProperty("ventaId", _ventaProductoCHANGE.get_ventaCHANGEIdServer());
	    	localSoapObject.addProperty("productoId", _ventaProductoCHANGE.get_productoCHANGEId());
	    	localSoapObject.addProperty("cantidad", _ventaProductoCHANGE.get_cantidad());
	    	localSoapObject.addProperty("monto", String.valueOf(monto));
	   		localSoapObject.addProperty("empleadoId", loginEmpleado.get_empleadoId());
	   		localSoapObject.addProperty("costoId",_productoCHANGECosto.get_costoId());
	   		localSoapObject.addProperty("almacenId",(loginEmpleado.get_almacenId()));
	   		localSoapObject.addProperty("motivoPopId",(_ventaProductoCHANGE.get_motivoCHANGEId()));
	       
	   		SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
	   		localSoapSerializationEnvelope.dotNet = true;
	   		localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
	   		HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
	   		try
	    	{
	   			localHttpTransportSE.call(VENTAPRODUCTOCHANGE_SOAP_ACTION, localSoapSerializationEnvelope);
	   			
	   			localSoapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
	   			if(localSoapObjects!=null)
	   			{
	   				resultadoInt = Integer.parseInt(localSoapObjects.getPropertyAsString("Id"));
	       			resultadoString = localSoapObjects.getPropertyAsString("Resultado");    				
	   			}
	   			
	   			if(resultadoString.equals("OK") && resultadoInt > 0)
	   			{
	   				WSInsertarVentaProductoCHANGE = true;
	   			}
	   			return true;
	    	}
	   		catch (Exception localException)
	   		{
	   			WSInsertarVentaProductoCHANGE = false;
	   			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	   			{
	   		        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertVentaProductoCHANGE: vacio");
	   			}
	   			else
	   			{
	   				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertVentaProductoCHANGE: " + localException.getMessage());
	   			} 
	   			return true;
	   		}
	   	}
	    
	    protected void onPostExecute(Boolean ejecutado)
	    {
	    	if(pdInsertarVenProductoCHANGE.isShowing())
	    	{
	    		pdInsertarVenProductoCHANGE.dismiss();
	    	}
	    	
	    	if(ejecutado) 
	    	{
	    		if(WSInsertarVentaProductoCHANGE || (resultadoString != null && resultadoString.equals("Venta Producto Change Repetido") && resultadoInt <= 0))
	    		{
	    			long l = 0;
	    			try
	    			{
	    				l = new VentaProductoCHANGEBLL().ModificarVentaProductoCHANGENoSincronizadaPor(_ventaProductoCHANGE.get_id(), resultadoInt);
					}
					catch (Exception localException)
	    			{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del producto de la venta CHANGE: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del producto de la venta CHANGE: " + localException.getMessage());
						} 
					}
						
	    			if(l==0)
	    			{
	    				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del producto de la venta CHANGE.", 1);
	    				return;
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "Producto de la venta CHANGE sincronizado.", 1);
							
						if(ObtenerVentasProductoCHANGENoSincronizadas())
						{
							SincronizarVentaProductoCHANGE();
						}
						else
						{
							ObtenerProductosAlmacenCHANGE();
						}
					}
	    		}
	    		else 
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo ejecutar el webservice WSInsertVentaProductoCHANGE.", 1);
	    			MostrarPantallaMenuVendedor();
	        	}
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "El WebService InsertVentaProductoCHANGE no se ejecuto correctamente. ", 1);
	    		return;
	    	}
	    }
	 }
	
	private void ObtenerProductosAlmacenCHANGE()
	{
		pdEsperaObtenerProductoAlmacenCHANGE = new ProgressDialog(this);
		pdEsperaObtenerProductoAlmacenCHANGE.setProgressStyle(0);
		pdEsperaObtenerProductoAlmacenCHANGE.setMessage("Descargando almacen de productos CHANGE ...");
		pdEsperaObtenerProductoAlmacenCHANGE.setCancelable(false);
		pdEsperaObtenerProductoAlmacenCHANGE.setCanceledOnTouchOutside(false);
		  
		WSObtenerProductosAlmacenCHANGE wsObtenerProductosAlmacenCHANGE = new WSObtenerProductosAlmacenCHANGE();
		try
		{
			wsObtenerProductosAlmacenCHANGE.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
		    {
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosByAlmacenCHANGE: vacio");
		   	}
		   	else
		   	{
		   		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosByAlmacenCHANGE: " + localException.getMessage());
		   	}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetProductosByAlmacenCHANGE.", 1);
		}
	}
	
	private class WSObtenerProductosAlmacenCHANGE extends AsyncTask<Void, Integer, Boolean>
	{
		String ALMACENPRODUCTOCHANGE_METHOD_NAME = "GetProductosByAlmacenChange";
		String ALMACENPRODUCTOCHANGE_SOAP_ACTION = NAMESPACE + ALMACENPRODUCTOCHANGE_METHOD_NAME;
		boolean WSObtenerProductosAlmacenCHANGE = false; 
		SoapObject soapObjects;
		int totalItems;
    
		protected void onPreExecute()
	    {
			pdEsperaObtenerProductoAlmacenCHANGE.show();
	    }
		
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerProductosAlmacenCHANGE = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,ALMACENPRODUCTOCHANGE_METHOD_NAME);
			localSoapObject.addProperty("almacenId", Integer.valueOf(loginEmpleado.get_almacenId()));
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			
			try
			{
				localHttpTransportSE.call(this.ALMACENPRODUCTOCHANGE_SOAP_ACTION, localSoapSerializationEnvelope);
				soapObjects = (SoapObject)((SoapObject)localSoapSerializationEnvelope.bodyIn).getProperty("GetProductosByAlmacenChangeResult");
				if(soapObjects!=null)
				{
					totalItems = soapObjects.getPropertyCount();
				}
				
				if(totalItems>0)
				{
					WSObtenerProductosAlmacenCHANGE = true;		
				}
				return true;
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
		        	myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosByAlmacenCHANGE: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosByAlmacenCHANGE: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerProductoAlmacenCHANGE.isShowing())
			{
				pdEsperaObtenerProductoAlmacenCHANGE.dismiss();
			}
			
			if(ejecutado)
			{
				if (WSObtenerProductosAlmacenCHANGE)
				{
					if(borrarProductosAlmacenCHANGE())
					{
						for(int i=0;i<totalItems;i++)
						{
							SoapObject soapObject = (SoapObject)soapObjects.getProperty(i);
							
							long l=0;
							try
							{
								l = new AlmacenCHANGEProductoBLL().InsertarAlmacenCHANGEProducto(
										Integer.parseInt(soapObject.getPropertyAsString("AlmacenId")),
										Integer.parseInt(soapObject.getPropertyAsString("ProductoId")),
										Integer.parseInt(soapObject.getPropertyAsString("Saldo")));
							}
							catch(Exception localException)
							{
								if(localException.getMessage() == null || localException.getMessage().isEmpty())
								{
							        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosByAlmacenCHANGE: vacio");
								}
								else
								{
									myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosByAlmacenCHANGE: " + localException.getMessage());
								}
								return;
							}
							
							if(l<=0)
							{
								theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar los productos del almacen CHANGE.", 2);
								return;								
							}
						}
					}
					if(ventaDirecta)
					{
						if(parametroGeneral.is_habilitarCambio())
		    			{
							MostrarPantallaMenuOpciones();
		    			}
						else
		    			{
		    				MostrarPantallaVentaDirecta();
		    			}
					}
					else
					{
						if(parametroGeneral.is_habilitarCambio())
		    			{
		    				MostrarPantallaMenuOpciones();
		    			}
		    			else
		    			{
		    				MostrarPantallaMapaPreventas();
		    			}
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"No existen productos del almacen CHANGE que descargar.", 2);
					if(ventaDirecta)
					{
						if(parametroGeneral.is_habilitarCambio())
		    			{
							MostrarPantallaMenuOpciones();
		    			}
						else
		    			{
		    				MostrarPantallaVentaDirecta();
		    			}
					}
					else
					{
						if(parametroGeneral.is_habilitarCambio())
		    			{
		    				MostrarPantallaMenuOpciones();
		    			}
		    			else
		    			{
		    				MostrarPantallaMapaPreventas();
		    			}
					};
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"El WebService GetProductosByAlmacenCHANGE no se ejecuto correctamente. ", 1);
				return;
			}
		}
	}
	
	private boolean borrarProductosAlmacenCHANGE()
	{
		AlmacenCHANGEProductoBLL theBLL = new AlmacenCHANGEProductoBLL();
		boolean borrado = false;
		try
		{
			borrado = theBLL.BorrarAlmacenesCHANGEProducto();
			return borrado;
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosAlmacenCHANGE: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosAlmacenCHANGE: " + localException.getMessage());
			}
			return false;
		}	
	}
	
	public void MostrarPantallaMapaPreventas()
	{
		startActivity(new Intent(this, Vendedormapaclientes.class));
	}
	
	public void MostrarPantallaMenuVendedor()
	 {
	    startActivity(new Intent(this, Menuvendedor.class));
	 }
	
	public void MostrarPantallaVentaDirecta()
	{
		startActivity(new Intent(this, Vendedorventadirectamapa.class));
	}

	private void MostrarPantallaMenuOpciones()
	{
		Intent localIntent = new Intent(this, Vendedorpreventamenuopciones.class);
		localIntent.putExtra("preventaIdDispositivo", preventaIdCHANGE);
		localIntent.putExtra("preventaIdServer", preventaIdCHANGEServer);
		localIntent.putExtra("clienteId", clienteId);
		localIntent.putExtra("ventaIdDispositivo", ventaDirectaIdCHANGE);
		localIntent.putExtra("ventaIdServer", ventaDirectaIdCHANGEServer);
		startActivity(localIntent);
	}
	
	@Override
 	public void onBackPressed() 
 	 {
 		 if(listadoPreventaProductoCHANGE != null)
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
