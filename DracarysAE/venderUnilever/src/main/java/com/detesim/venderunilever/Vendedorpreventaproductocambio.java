package com.detesim.venderunilever;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import BLL.CategoriaBLL;
import BLL.ClientePreventaBLL;
import BLL.MotivoCambioBLL;
import BLL.MyLogBLL;
import BLL.ParametroGeneralBLL;
import BLL.PreventaBLL;
import BLL.PreventaProductoCambioBLL;
import BLL.ProductoCambioBLL;
import BLL.ProveedorBLL;
import Clases.Categoria;
import Clases.ClientePreventa;
import Clases.LoginEmpleado;
import Clases.MotivoCambio;
import Clases.ParametroGeneral;
import Clases.Preventa;
import Clases.PreventaProductoCambio;
import Clases.ProductoCambio;
import Clases.Proveedor;
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

public class Vendedorpreventaproductocambio extends Activity implements OnClickListener
{
	LinearLayout llVendedorPreventaProductoCambio;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	LoginEmpleado loginEmpleado;
	MyLogBLL myLog = new MyLogBLL();

	ArrayList<PreventaProductoCambio> listadoPreventaProductoCambio;
	ArrayList<PreventaProductoCambio> listadoPreventaProductoCambioNoSincronizadas;
	PreventaProductoCambio preventaProductoCambio;
	ProductoCambio productoCambio;
	ClientePreventa clientePreventa;
	Preventa preventa;
	int clienteId;
	int preventaIdCambio;
	int preventaIdCambioServer;
	int motivoCambioId;
	ParametroGeneral parametroGeneral;
	
	RadioButton rbRegistrar;
	RadioButton rbNoRegistrar;
	TextView tvCliente;
	Spinner spnProveedores;
	Spinner spnCategorias;
	Spinner spnProductos;
	Spinner spnMotivosCambios;
	Button btnMostrar;
	TextView tvProductoCambioTxt;
	TextView tvProductoCambio;
	TextView tvPrecioTxt;
	TextView tvPrecio;
	TextView tvMotivoCambio;
	TextView tvCantidad;
	EditText etCantidad;
	Button btnAdicionar;
	TextView tvProductoCambioListview;
	TextView tvPrecioCambioListView;
	TextView tvCantidadCambioListview;
	TextView tvSubTotalCambioListView;
	ListView lvPreventasProductosCambio;
	TextView tvTotalTxt;
	TextView tvTotal;;
	Button btnConfirmar;
	Dialog dialog;
	
	ProgressDialog pdInsertarPrevenProductoCambio; 
	ProgressDialog pdDeletePreventaProductoCambio;
	ProgressDialog pdEsperaObtenerProductosCambio;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedorpreventaproductocambio);
		
		llVendedorPreventaProductoCambio = (LinearLayout)findViewById(R.id.llPreventaDevolucion);
		rbRegistrar = (RadioButton)findViewById(R.id.rbtPreventaProductoCambioRegistrar);
		rbRegistrar.setOnClickListener(this);
		rbNoRegistrar = (RadioButton)findViewById(R.id.rbtPreventaProductoCambioNoRegistrar);
		rbNoRegistrar.setOnClickListener(this);
		tvCliente = ((TextView)findViewById(R.id.tvPreventaDevolucionCliente));
		spnProveedores = ((Spinner)findViewById(R.id.spnPreventaDevolucionProveedor));
		spnCategorias = ((Spinner)findViewById(R.id.spnPreventaDevolucionCategoria));
	    spnProductos = ((Spinner)findViewById(R.id.spnPreventaDevolucionProducto));
	    spnMotivosCambios = (Spinner)findViewById(R.id.spnPreventaDevolucionMotivo);
	    btnMostrar = ((Button)findViewById(R.id.btnPreventaDevolucionMostrarDetalles));
	    btnMostrar.setOnClickListener(this);
	    tvProductoCambioTxt = ((TextView)findViewById(R.id.tvPreventaDevolucionProductoTxt));
	    tvProductoCambio = ((TextView)findViewById(R.id.tvPreventaDevolucionProducto));
	    tvPrecioTxt = (TextView)findViewById(R.id.tvPreventaDevolucionPrecioUnitarioTxt);
	    tvPrecio = (TextView)findViewById(R.id.tvPreventaDevolucionPrecioUnitario);
	    tvMotivoCambio = (TextView)findViewById(R.id.tvPreventaDevolucionMotivoTxt);
	    tvCantidad = ((TextView)findViewById(R.id.tvPreventaDevolucionCantidadUnitariaTxt));
	    etCantidad = ((EditText)findViewById(R.id.etPreventaDevolucionCantidadUnitaria));
	    btnAdicionar = ((Button)findViewById(R.id.btnPreventaDevolucionAdicionarProducto));
	    btnAdicionar.setOnClickListener(this);
	    tvProductoCambioListview = ((TextView)findViewById(R.id.tvPreventaDevolucionProductoDevueltoTxt));
	    tvPrecioCambioListView = ((TextView)findViewById(R.id.tvPreventaProductoCambioPrecioTxt));
	    tvSubTotalCambioListView = ((TextView)findViewById(R.id.tvPreventaProductoCambioSubTotalTxt));
	    tvCantidadCambioListview = ((TextView)findViewById(R.id.tvPreventaDevolucionCantidadDevueltaTxt));
	    lvPreventasProductosCambio = (ListView)findViewById(R.id.lvPreventaDevolucionDevoluciones);
	    tvTotalTxt = (TextView)findViewById(R.id.tvVendedorPreventaProductoCambioTotalTxt);
	    tvTotal = (TextView)findViewById(R.id.tvVendedorProductoCambioTotal);
	    btnConfirmar = ((Button)findViewById(R.id.btnPreventaDevolucionConfirmarDevolucion));
	    btnConfirmar.setOnClickListener(this);
	    
	    llVendedorPreventaProductoCambio.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
	    	    
	    preventaIdCambio = 0;
	    preventaIdCambio = getIntent().getExtras().getInt("preventaIdCambio");
	    if(preventaIdCambio == 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la preventaId cambio.", 1);
	    	return;
	    }
	    
	    preventaIdCambioServer = 0;
	    preventaIdCambioServer = getIntent().getExtras().getInt("preventaIdCambioServer");
	    
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
	    	DespliegueControlesAdicionarPreventaCambio(false);
	        DespliegueControlesConfirmarPreventaCambio(false);
	        CargarInformacion();
	    }

	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.rbtPreventaProductoCambioNoRegistrar:
			MostrarPantallaMenuVendedor();
			break;
		case R.id.btnPreventaDevolucionMostrarDetalles:
			ObtenerDatosSeleccion();
			break;		
		case R.id.btnPreventaDevolucionAdicionarProducto:
			AdicionarPreventaProductoCambio();
			break;
		case R.id.btnPreventaDevolucionConfirmarDevolucion:
			ConfirmarPreventaCambio();
			break;
		}
	}
	
	private void DespliegueControlesAdicionarPreventaCambio(boolean estado)
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
	    
	    tvProductoCambioTxt.setVisibility(visibilidad);
	    tvProductoCambio.setVisibility(visibilidad);
	    tvPrecioTxt.setVisibility(visibilidad);
	    tvPrecio.setVisibility(visibilidad);
	    tvMotivoCambio.setVisibility(visibilidad);
	    spnMotivosCambios.setVisibility(visibilidad);
	    tvCantidad.setVisibility(visibilidad);
	    etCantidad.setVisibility(visibilidad);
	    btnAdicionar.setVisibility(visibilidad);
	    etCantidad.setText("");
	}
	
	private void DespliegueControlesConfirmarPreventaCambio(boolean estado)
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
	    
	    tvProductoCambioListview.setVisibility(visibilidad);
	    tvPrecioCambioListView.setVisibility(visibilidad);
	    tvCantidadCambioListview.setVisibility(visibilidad);
	    tvSubTotalCambioListView.setVisibility(visibilidad);
	    lvPreventasProductosCambio.setVisibility(visibilidad);
	    tvTotalTxt.setVisibility(visibilidad);
	    tvTotal.setVisibility(visibilidad);
	    btnConfirmar.setVisibility(visibilidad);
    }

	public void CargarInformacion()
	{
		CargarParametroGeneral();
	    CargarCliente();
	    CargarProveedores();
	    CargarCategorias();
	    CargarProductos();
	    CargarMotivosCambio();
	    ActualizarListView();
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
				
				ArrayList<ProductoCambio> listadoProductoCambio = null;
				try
				{
					listadoProductoCambio = new ProductoCambioBLL().ObtenerProductosCambiosPorProvedorIdNoEnPreventaProductoCambioPor(
																	proveedor.get_proveedorId(),categoria.get_categoriaId(),clienteId);
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
				        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos cambio por proveedorId y categoriaId: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos cambio por proveedorId y categoriaId: " + localException.getMessage());
					} 
				}
				
				if(listadoProductoCambio == null)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los productos cambio por proveedorId y categoriaId", 1);
					return;
				}
				
				ArrayAdapter<ProductoCambio> arrayAdapter = new ArrayAdapter<ProductoCambio>(getApplicationContext(),R.layout.disenio_spinner,listadoProductoCambio);
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
	    ArrayList<ProductoCambio> listadoProductoCambio = null;
	    
	    try
	    {
	    	listadoProductoCambio = new ProductoCambioBLL().ObtenerProductosCambiosPorProvedorIdNoEnPreventaProductoCambioPor(
	    																							proveedorId, categoriaId,clienteId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null|| localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos cambio por proveedorId: vacio");
	    	} 
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos cambio por proveedorId: " + localException.getMessage());
	    	} 
	    }
	    
	    if(listadoProductoCambio == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los productos cambio por el proveedor y categoria seleccionado.", 1);
	    	return;
	    }
	    
	    ArrayAdapter<ProductoCambio> localArrayAdapter = new ArrayAdapter<ProductoCambio>(this,R.layout.disenio_spinner,listadoProductoCambio);
	    spnProductos.setAdapter(localArrayAdapter);
	    
	    spnProductos.setOnItemSelectedListener(new OnItemSelectedListener() 
	    {	    	
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
			{
				DespliegueControlesAdicionarPreventaCambio(false);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent){}
	    	
		});
	}
	
	public void CargarMotivosCambio()
	{
	    ArrayList<MotivoCambio> listadoMotivoCambio = null;
	    
	    try
	    {
	    	listadoMotivoCambio = new MotivoCambioBLL().ObtenerMotivosCambio();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null|| localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos cambio: vacio");
	    	} 
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos cambio: " + localException.getMessage());
	    	} 
	    }
	    
	    if(listadoMotivoCambio == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los motivos de cambio.", 1);
	    	return;
	    }
	    
	    ArrayAdapter<MotivoCambio> localArrayAdapter = new ArrayAdapter<MotivoCambio>(this,R.layout.disenio_spinner,listadoMotivoCambio);
	    spnMotivosCambios.setAdapter(localArrayAdapter);
	    
	    spnMotivosCambios.setOnItemSelectedListener(new OnItemSelectedListener() 
	    {	    	
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
			{
				motivoCambioId = ((MotivoCambio)spnMotivosCambios.getSelectedItem()).get_motivoCambioId();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent){}
	    	
		});
	}

	public void ActualizarListView()
	{
		listadoPreventaProductoCambio = null;
		try
		{
			listadoPreventaProductoCambio = new PreventaProductoCambioBLL().ObtenerPreventasPorductoCambioPorPreventaId(preventaIdCambio);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventa producto cambio por preventaIdCambio : vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventa producto cambio por preventaIdCambio: " + localException.getMessage());
			}
		}
		      
		if(listadoPreventaProductoCambio == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No existen preventas producto cambio.", 1);
			DespliegueControlesConfirmarPreventaCambio(false);
			lvPreventasProductosCambio.setAdapter(null);
		}
		else
		{
			DespliegueControlesConfirmarPreventaCambio(true);
			CalcularTotalPreventaCambio();
			lvPreventasProductosCambio.setVisibility(View.VISIBLE);
		    LlenarPreventaListView();
		    EliminarItemListView();	
		}    
	}
	
	private void CalcularTotalPreventaCambio()
	{
		float total = 0;
		for(PreventaProductoCambio item : listadoPreventaProductoCambio)
		{
			ProductoCambio localProductoCambio = null;
			try
			{
				localProductoCambio = new ProductoCambioBLL().ObtenerProductoCambioPorProductoId(item.get_productoId());
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del producto cambio: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del producto cambio: " + localException.getMessage());
				} 
			}
			
			if(localProductoCambio == null)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el producto cambio.", 1);
	            return;
			}
			
			total += Float.parseFloat(new BigDecimal(item.get_cantidad()*localProductoCambio.get_precio()).setScale(5,RoundingMode.HALF_UP).toString());
		}
		
		tvTotal.setText(String.valueOf(Float.parseFloat(new BigDecimal(total).setScale(2,RoundingMode.HALF_UP).toString())));	
	}
	
	private void LlenarPreventaListView()
	{
	    MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    	    
	    if(listadoPreventaProductoCambio == null)
	    {
	    	lvPreventasProductosCambio.setAdapter(null);
	    }
	    else
	    {
		    ViewGroup.LayoutParams localLayoutParams = lvPreventasProductosCambio.getLayoutParams();
		    localLayoutParams.height = ((int)(50*getApplicationContext().getResources().getDisplayMetrics().density) * listadoPreventaProductoCambio.size());
		    lvPreventasProductosCambio.setLayoutParams(localLayoutParams);
		    lvPreventasProductosCambio.setAdapter(localMiAdaptadorLista);
	    }
	}

	class MiAdaptadorLista extends ArrayAdapter<PreventaProductoCambio>
	{
		private Context _context;
		
		public MiAdaptadorLista(Context context)
		{
			super(context,R.layout.disenio_vendedorpreventaproducto,listadoPreventaProductoCambio);
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
			
			PreventaProductoCambio localPreventaProductoCambio = (PreventaProductoCambio)listadoPreventaProductoCambio.get(position);
			
			TextView descripcion25 = (TextView)localView.findViewById(R.id.tvVendedorVentaDirectaProductoCliente);
			TextView cantidad = (TextView)localView.findViewById(R.id.tvPreventaDisenioCantidad);
			TextView monto = (TextView)localView.findViewById(R.id.tvVendedorPreventaProductoProveedor);
			TextView montoPaquete = (TextView)localView.findViewById(R.id.tvVendedorPreventaProductoProducto);
			TextView precio = (TextView)localView.findViewById(R.id.tvPreventaDisenioPrecioUnitario);
			ImageView bullet = (ImageView)localView.findViewById(R.id.imgvLoginLogotipo);
      
			if(localPreventaProductoCambio.get_productoId() != 0)
			{
				ProductoCambio localProductoCambio = null;
				try
				{
					localProductoCambio = new ProductoCambioBLL().ObtenerProductoCambioPorProductoId(localPreventaProductoCambio.get_productoId());
				}
				catch (Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
				        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del producto cambio: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del producto cambio: " + localException.getMessage());
					} 
				}
				
				if(localProductoCambio == null)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el producto cambio.", 1);
		            return null;
				}
				
				descripcion25.setText(localProductoCambio.get_descripcion25());
				cantidad.setText(String.valueOf(localPreventaProductoCambio.get_cantidad()));
				precio.setText(String.valueOf(new BigDecimal(localProductoCambio.get_precio()).setScale(2,RoundingMode.HALF_UP)));
				monto.setText(String.valueOf(new BigDecimal(localPreventaProductoCambio.get_cantidad()*localProductoCambio.get_precio()).setScale(2,RoundingMode.HALF_UP)));
				montoPaquete.setText("");
			}
			
			bullet.setImageResource(R.drawable.bullet_eliminar);

			return localView;
		}
	}
	
	private void EliminarItemListView()
	{
		((ListView)findViewById(R.id.lvPreventaDevolucionDevoluciones)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
			public void onItemClick(AdapterView<?> parent, View view,final int position, long id)
			{
				dialog = new Dialog(Vendedorpreventaproductocambio.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.diseniodialogo_vendedorcierredia);
				dialog.setTitle("Eliminando item");
				dialog.setCancelable(false);
				dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.colorBluePale));
				
				TextView tvMensaje = (TextView)dialog.findViewById(R.id.tvDialogoMensaje);
				tvMensaje.setText("Esta seguro de eliminar el producto de cambio?");
				
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
								
								PreventaProductoCambio localPreventaProductoCambio = listadoPreventaProductoCambio.get(position);
								
								if(BorrarPreventaProductoCambioDispositivo(localPreventaProductoCambio.get_id()))
								{
									theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la preventa cambio eliminado del dispositivo.", 1);
									CargarProductos();
									ActualizarListView();
								} 
								else 
								{
									theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo eliminar el producto cambio de la preventa.", 1);
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
	
	private boolean BorrarPreventaProductoCambioDispositivo(int id)
	 {
		 boolean bool = false;
		 try
		 {
			 bool = new PreventaProductoCambioBLL().BorrarPreventaProductoCambioPor(id);
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al borrar el item de la preventa producto cambio por Id: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al borrar el item de la preventa producto cambio por Id: " + localException.getMessage());
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
		 
		 productoId = ((ProductoCambio)spnProductos.getSelectedItem()).get_productoId();
		 if(productoId == 0)
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(), "Debe seleccionar un producto.", 1);
			 return;
		 }
		 
		 if(EstablecerProductoCambio(productoId))
		 {
			 DespliegueControlesAdicionarPreventaCambio(true);
		     
		     tvProductoCambio.setText(productoCambio.get_descripcion25());
		     tvPrecio.setText(String.valueOf(productoCambio.get_precio()));
		 }
	 }

	private boolean EstablecerProductoCambio(int productoId)
	{
		boolean establecido = false;
		 productoCambio = null;
		 try
		 {
			 productoCambio = new ProductoCambioBLL().ObtenerProductoCambioPorProductoId(productoId);
		 }
		 catch(Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del producto cambio: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del producto cambio: " + localException.getMessage());
			 } 
		 }
		 
		 if(productoCambio == null)
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del producto cambio", 1);
		 }
		 else
		 {
			 establecido = true;
		 }
		 return establecido;
	}
	
	private void AdicionarPreventaProductoCambio()
	{
		  int cantidad;
		  
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
         
		  if(motivoCambioId > 0)
		  {
			  if(ValidarProductoMotivoCambio(productoCambio.get_productoId(),motivoCambioId))
			  {
				  PreventaProductoCambio localPreventaProductoCambio = new PreventaProductoCambio();
			      localPreventaProductoCambio.set_preventaId(preventaIdCambio);
			      localPreventaProductoCambio.set_preventaIdServer(preventaIdCambioServer);
			      localPreventaProductoCambio.set_productoId(productoCambio.get_productoId());
			      localPreventaProductoCambio.set_cantidad(cantidad);
			      localPreventaProductoCambio.set_clienteId(clientePreventa.get_clienteId());
			      localPreventaProductoCambio.set_sincro(false);
			      
		    	  if(InsertarPreventaProductoCambioDispositivo(localPreventaProductoCambio))
			      {
			    	  theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la preventa insertado en el dispositivo.", 1);
			    	  CargarProductos();
			    	  DespliegueControlesAdicionarPreventaCambio(false);
			    	  ActualizarListView();
			      }
			      else 
			      {
			    	  theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el producto de la preventa en el dispositivo.",1);
			    	  return;
			      }
			  }
			  else
			  {
				  theUtilidades.MostrarMensaje(getApplicationContext(), "El producto ya fue ingresado con el mismo motivo, verifique.", 1);
			  }
			  
		  }
		  else
		  {
			  theUtilidades.MostrarMensaje(getApplicationContext(), "Debe seleccionar un motivo de cambio.", 1);
		  }
	 }
	
	private boolean ValidarProductoMotivoCambio(int productoId,int motivoCambioId)
	{
		boolean validado = true;
		if(listadoPreventaProductoCambio != null && listadoPreventaProductoCambio.size() > 0)
		{
			for(PreventaProductoCambio item : listadoPreventaProductoCambio)
			{
				boolean producto = false;
				if(item.get_productoId() == productoId)
				{
					producto = true;
				}
				
				boolean motivoCambio = false;
				if((item.get_motivoCambioId()==motivoCambioId))
				{
					motivoCambio = true;
				}
				
				if(producto && motivoCambio)
				{
					validado = false;
				}
			}
		}
		
		return validado;
	}
	
	private boolean InsertarPreventaProductoCambioDispositivo(PreventaProductoCambio paramPreventaProductoCambio)
	{
		long l = 0;
		try
		{
			l = new PreventaProductoCambioBLL().InsertarPreventaProductoCambio(paramPreventaProductoCambio.get_preventaId(),
											paramPreventaProductoCambio.get_preventaIdServer(),paramPreventaProductoCambio.get_productoId(),
											paramPreventaProductoCambio.get_cantidad(),paramPreventaProductoCambio.get_clienteId(),
											paramPreventaProductoCambio.is_sincro(),motivoCambioId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preVenta producto cambio en el dispositivo: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preVenta producto cambio en el dispositivo: " + localException.getMessage());
	    	}
	    	return false;
	    }
	    	
	    if(l==0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la preventa producto cambio en el dispositivo.", 1);
	  	     return false;		
	    }
	    else
	    {
	    	return true;
	    }
	}
	
	private void ConfirmarPreventaCambio()
	{
		btnConfirmar.setVisibility(View.INVISIBLE);
		
		if(listadoPreventaProductoCambio.size()>0)
		{
			if(clienteId > 0)
			{
				if(ObtenerPreventasProductoCambioNoSincronizadas())
				{
					if(VerificarMontoCambio())
					{
						SincronizarPreventaProductoCambio();
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "El monto del cambio supera al valor de la preventa actual.", 1);
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No existen productos de la preventa cambio que sincronizar.", 1);
					return;
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "Para ingresar los productos cambio debe sincronizar el cliente.", 1);
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
			theUtilidades.MostrarMensaje(getApplicationContext(), "No Existen items que sincronizar.", 1);
			return;
		}
	}
		
	private boolean ObtenerPreventasProductoCambioNoSincronizadas()
	{
		listadoPreventaProductoCambioNoSincronizadas = null;
	    try
	    {
	    	listadoPreventaProductoCambioNoSincronizadas = new PreventaProductoCambioBLL().ObtenerPreventasProductoCambioNoSincronizadasPor(preventaIdCambio);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la preventa cambio no sincronizada por preventaId cambio: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la preventa cambio no sincronizada por preventaId cambio: " + localException.getMessage());
	    	}
	    }
	    
	    if (listadoPreventaProductoCambioNoSincronizadas == null) 
	    {
	        return false;
	    }  
	    else
	    {
	    	return true;
	    }
	}
	
	private boolean VerificarMontoCambio()
	{
		boolean verificado = false;
		preventa = null;
		float montoPreventa=0;
		try
		{
			preventa = new PreventaBLL().ObtenerPreventaPorId(preventaIdCambio);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por preventaRowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por preventaRowId: " + localException.getMessage());
	    	}
		}
		
		if(preventa == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo Obtener la preventa por preventaId.", 1);
		}
		else
		{
			for(PreventaProductoCambio itemPreventaCambio : listadoPreventaProductoCambio)
			{
				ProductoCambio theProductoCambio=null;
				try
				{
					theProductoCambio = new ProductoCambioBLL().ObtenerProductoCambioPorProductoId(itemPreventaCambio.get_productoId());
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
			    	{
			            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el prodcuto por productoId: vacio");
			    	}
			    	else
			    	{
			    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el producto por productoId: " + localException.getMessage());
			    	}
				}
				
				if(theProductoCambio == null)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo Obtener el productoCambio por productoId.", 1);
				}
				else
				{
					montoPreventa = montoPreventa + (itemPreventaCambio.get_cantidad() * theProductoCambio.get_precio());
				}
			}
			
			if(montoPreventa < preventa.get_montoFinal())
			{
				verificado = true;
			}
		}
		
		return verificado;
	}
	
	private void SincronizarPreventaProductoCambio() 
	{
		if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
		{
			if(listadoPreventaProductoCambioNoSincronizadas != null && listadoPreventaProductoCambioNoSincronizadas.size()>0)
			{
				if(listadoPreventaProductoCambioNoSincronizadas.get(0).get_preventaIdServer()!=0)
				{
					if(EstablecerProductoCambio(listadoPreventaProductoCambioNoSincronizadas.get(0).get_productoId()))
					{
						InsertarPreventaProductoCambio(listadoPreventaProductoCambioNoSincronizadas.get(0));
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Para sincronizar el producto cambio, primero debes sincronizar la preventa.", 1);
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
				theUtilidades.MostrarMensaje(getApplicationContext(), "No existen productos de la preventa cambio que sincronizar.", 1);
				return;
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No esta conectado a internet, intentelo mas tarde.", 1);
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
	
	private void InsertarPreventaProductoCambio(PreventaProductoCambio localPreventaProductoCambio)
	 {
		pdInsertarPrevenProductoCambio = new ProgressDialog(this);
		pdInsertarPrevenProductoCambio.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdInsertarPrevenProductoCambio.setMessage("Insertando productos preventa cambio ...");
		pdInsertarPrevenProductoCambio.setCancelable(false);
		pdInsertarPrevenProductoCambio.setCanceledOnTouchOutside(false);
	    
	    WSInsertarPreventaProductoCambio localWSInsertarPreventaProductoCambio = new WSInsertarPreventaProductoCambio(localPreventaProductoCambio);
	    
	    try
	    {
	    	localWSInsertarPreventaProductoCambio.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventaProductoCambio: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarPreVentaProductoCambio: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertarPreventaProductoCambio", 1);
	    }
	 }
	 
	public class WSInsertarPreventaProductoCambio extends AsyncTask<Void, Integer, Boolean>
	 {
		 String PREVENTAPRODUCTOCAMBIO_METHOD_NAME = "InsertPreVentaProductoCambio";
		 String PREVENTAPRODUCTOCAMBIO_SOAP_ACTION = NAMESPACE + PREVENTAPRODUCTOCAMBIO_METHOD_NAME;
		 
		 private PreventaProductoCambio _preventaProductoCambio;
		 
		 boolean WSInsertarPreVentaProductoCambio;
		 int resultadoInt;
		 String resultadoString;
		 SoapObject localSoapObjects;
	    
	    public WSInsertarPreventaProductoCambio(PreventaProductoCambio paramPreventaProductoCambio)
	    {
	    	this._preventaProductoCambio = paramPreventaProductoCambio;
	    }
	    
	    protected void onPreExecute()
	    {
	    	pdInsertarPrevenProductoCambio.show();
	    }
	    
	    protected Boolean doInBackground(Void... paramVarArgs)
	    {
	    	WSInsertarPreVentaProductoCambio = false;
	    	
	    	SoapObject localSoapObject = new SoapObject(NAMESPACE, this.PREVENTAPRODUCTOCAMBIO_METHOD_NAME);
	    	localSoapObject.addProperty("preVentaId", _preventaProductoCambio.get_preventaIdServer());
	    	localSoapObject.addProperty("productoId", _preventaProductoCambio.get_productoId());
	    	localSoapObject.addProperty("cantidad", _preventaProductoCambio.get_cantidad());
	    	localSoapObject.addProperty("precioId", productoCambio.get_precioId());
	    	localSoapObject.addProperty("costoId", productoCambio.get_costoId());
	    	localSoapObject.addProperty("motivoCambioId", _preventaProductoCambio.get_motivoCambioId());
	       
	   		SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
	   		localSoapSerializationEnvelope.dotNet = true;
	   		localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
	   		HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
	   		try
	    	{
	   			localHttpTransportSE.call(PREVENTAPRODUCTOCAMBIO_SOAP_ACTION, localSoapSerializationEnvelope);
	   			
	   			localSoapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
	   			if(localSoapObjects!=null)
	   			{
	   				resultadoInt = Integer.parseInt(localSoapObjects.getPropertyAsString("Id"));
	       			resultadoString = localSoapObjects.getPropertyAsString("Resultado");    				
	   			}
	   			
	   			if(resultadoString.equals("OK") && resultadoInt > 0)
	   			{
	   				WSInsertarPreVentaProductoCambio = true;
	   			}
	   			return true;
	    	}
	   		catch (Exception localException)
	   		{
	   			WSInsertarPreVentaProductoCambio = false;
	   			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	   			{
	   		        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventaProductoCambio: vacio");
	   			}
	   			else
	   			{
	   				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventaProductoCambio: " + localException.getMessage());
	   			} 
	   			return true;
	   		}
	   	}
	    
	    protected void onPostExecute(Boolean ejecutado)
	    {
	    	if(pdInsertarPrevenProductoCambio.isShowing())
	    	{
	    		pdInsertarPrevenProductoCambio.dismiss();
	    	}
	    	
	    	if(ejecutado) 
	    	{
	    		if(WSInsertarPreVentaProductoCambio || (resultadoString != null && resultadoString.equals("Preventa Producto Cambio Repetido") && resultadoInt <= 0))
	    		{
	    			long l = 0;
	    			try
	    			{
	    				l = new PreventaProductoCambioBLL().ModificarPreventaProductoCambioNoSincronizadaPor(_preventaProductoCambio.get_id(), resultadoInt);
					}
					catch (Exception localException)
	    			{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del producto de la preventa cambio: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del producto de la preventa cambio: " + localException.getMessage());
						} 
					}
						
	    			if(l==0)
	    			{
	    				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del producto de la preventa cambio.", 1);
	    				return;
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "Producto de la preventa cambio sincronizado.", 1);
							
						if(ObtenerPreventasProductoCambioNoSincronizadas())
						{
							SincronizarPreventaProductoCambio();
						}
						else
						{
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
	    		}
	    		else 
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo ejecutar el webservice WSInsertPreVentaProductoCambio.", 1);
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
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "El WebService InsertPreVentaProductoCambio no se ejecuto correctamente. ", 1);
	    		return;
	    	}
	    }
	 }
	
	public void MostrarPantallaMenuVendedor()
	{
		startActivity(new Intent(this, Menuvendedor.class));
	}

	private void MostrarPantallaMenuOpciones()
	{
		Intent localIntent = new Intent(this, Vendedorpreventamenuopciones.class);
		localIntent.putExtra("preventaIdDispositivo", preventaIdCambio);
		localIntent.putExtra("preventaIdServer", preventaIdCambioServer);
		localIntent.putExtra("clienteId", clienteId);
		localIntent.putExtra("ventaIdDispositivo", 0);
		localIntent.putExtra("ventaIdServer", 0);
		startActivity(localIntent);
	}
	
	@Override
	public void onBackPressed() 
	{
		if(listadoPreventaProductoCambio != null)
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
