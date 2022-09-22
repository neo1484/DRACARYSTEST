package com.detesim.venderunilever;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import BLL.AlmacenPOPProductoBLL;
import BLL.CategoriaPOPBLL;
import BLL.ClientePreventaBLL;
import BLL.MotivoPopBLL;
import BLL.MyLogBLL;
import BLL.ParametroGeneralBLL;
import BLL.PreventaProductoPOPBLL;
import BLL.ProductoPOPBLL;
import BLL.ProductoPOPCostoBLL;
import BLL.ProveedorBLL;
import BLL.VentaProductoPOPBLL;
import Clases.AES;
import Clases.AlmacenPOPProducto;
import Clases.AlmacenPOPProductoWSResult;
import Clases.CategoriaPOP;
import Clases.ClientePreventa;
import Clases.JSonParser;
import Clases.LoginEmpleado;
import Clases.MotivoPop;
import Clases.ParametroGeneral;
import Clases.PreventaPOP;
import Clases.PreventaProductoPOP;
import Clases.ProductoPOP;
import Clases.ProductoPOPCosto;
import Clases.Proveedor;
import Clases.SingleId;
import Clases.VentaProductoPOP;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Vendedorpreventaproductopop extends Activity implements OnClickListener
{
	LinearLayout llVendedorPreventaProductoPOP;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	LoginEmpleado loginEmpleado;
	MyLogBLL myLog = new MyLogBLL();
	
	ArrayList<PreventaProductoPOP> listadoPreventaProductoPOP ;
	ArrayList<PreventaProductoPOP> listadoPreventaProductoPOPNoSincronizadas;
	ArrayList<VentaProductoPOP> listadoVentaProductoPOP ;
	ArrayList<VentaProductoPOP> listadoVentaProductoPOPNoSincronizadas;
	PreventaPOP preventaPOP;
	ProductoPOP productoPOP;
	ClientePreventa clientePreventa;
	int preventaIdPOP;
	int preventaIdPOPServer;
	int clienteId;
	int ventaDirectaIdPOP;
	int ventaDirectaIdPOPServer;
	int motivoPopId;
	boolean ventaDirecta;
	ParametroGeneral parametroGeneral;

	RadioButton rbtRegistrar;
	RadioButton rbtNoRegistrar;
	TextView tvCliente;
	TextView tvClienteTxt;
	Spinner spnProveedores;
	Spinner spnCategorias;
	Spinner spnProductos;
	TextView tvMotivoPop;
	Spinner spnMotivosPop;
	TextView tvProductoPOPTxt;
	TextView tvProductoPOP;
	TextView tvInventarioTxt;
	TextView tvInventario;
	TextView tvCantidad;
	EditText etCantidad;
	Button btnMostrar;
	TextView tvCantidadPOPListview;
	TextView tvProductoPOPListview;
	ListView lvPreventasProductosPOP;
	Button btnAdicionar;
	Button btnConfirmar;
	Dialog dialog;
	
	ProgressDialog pdInsertarPrevenProductoPOP;
	ProgressDialog pdInsertarVenProductoPOP; 
	ProgressDialog pdEsperaObtenerProductoAlmacenPOP;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedorpreventaproductopop);
		
		llVendedorPreventaProductoPOP = (LinearLayout)findViewById(R.id.llVendedorPreventaProductoPOP);
		tvCliente = ((TextView)findViewById(R.id.tvPreventaProductoPOPCliente));
		rbtRegistrar = (RadioButton)findViewById(R.id.rbtPreventaProductoPOPRegistrarPOP);
		rbtRegistrar.setOnClickListener(this);
		rbtNoRegistrar = (RadioButton)findViewById(R.id.rbtPreventaProductoPOPNoRegistrarPOP);
		rbtNoRegistrar.setOnClickListener(this);
		spnProveedores = ((Spinner)findViewById(R.id.spnPreventaProductoPOPProveedor));
		spnCategorias = ((Spinner)findViewById(R.id.spnPreventaProductoPOPCategoria));
	    spnProductos = ((Spinner)findViewById(R.id.spnPreventaProductoPOPProductoPOP));
	    tvMotivoPop = (TextView)findViewById(R.id.tvPreventaProductoPOPMotivoPOP);
	    spnMotivosPop = ((Spinner)findViewById(R.id.spnPreventaProductoPOPMotivoPOP));
	    btnMostrar = ((Button)findViewById(R.id.btnPreventaProductoPOPMostrarDetalles));
	    btnMostrar.setOnClickListener(this);
	    tvProductoPOPTxt = ((TextView)findViewById(R.id.tvPreventaProductoPOPProductoPOPTxt));
	    tvProductoPOP = ((TextView)findViewById(R.id.tvPreventaProductoPOPProductoPOP));
	    tvInventarioTxt = ((TextView)findViewById(R.id.tvPreventaProductoPOPInventarioUnidadesText));
	    tvInventario = ((TextView)findViewById(R.id.tvPreventaProductoPOPInventarioUnidades));
	    tvCantidad = ((TextView)findViewById(R.id.tvPreventaProductoPOPCantidadTxt));
	    etCantidad = ((EditText)findViewById(R.id.etvPreventaProductoPOPCantidad));
	    btnAdicionar = ((Button)findViewById(R.id.btnPreventaProductoPOPAdicionarProducto));
	    btnAdicionar.setOnClickListener(this);
	    lvPreventasProductosPOP = (ListView)findViewById(R.id.lvPreventaProductoPOPProductos);
	    tvProductoPOPListview = ((TextView)findViewById(R.id.tvPreventaProductoPOPProductoPOPLv));
	    tvCantidadPOPListview = ((TextView)findViewById(R.id.tvPreventaProductoPOPCantidadLv));
	    btnConfirmar = ((Button)findViewById(R.id.btnPreventaProductoPOPConfirmarPreventaPOP));
	    btnConfirmar.setOnClickListener(this);
	    
	    llVendedorPreventaProductoPOP.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
	    
	    ventaDirecta = false;
	    ventaDirecta = getIntent().getExtras().getBoolean("ventaDirecta");
	    
	    if(ventaDirecta)
	    {	    
	    	ventaDirectaIdPOP = 0;
		    ventaDirectaIdPOP = getIntent().getExtras().getInt("ventaDirectaIdPOP");
		    if(ventaDirectaIdPOP == 0)
		    {
		    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la ventaDirectaIdPOP.", 1);
		    	return;
		    }
		    		    
		    ventaDirectaIdPOPServer = 0;
		    ventaDirectaIdPOPServer = getIntent().getExtras().getInt("ventaDirectaIdPOPServer");
	    }
	    else
	    {	    	
	    	preventaIdPOP = 0;
		    preventaIdPOP = getIntent().getExtras().getInt("preventaIdPOP");
		    if(preventaIdPOP == 0)
		    {
		    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la preventaIdPOP.", 1);
		    	return;
		    }
		    
		    preventaIdPOPServer = 0;
		    preventaIdPOPServer = getIntent().getExtras().getInt("preventaIdPOPServer");
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
	    }
	    else
	    {
	    	DespliegueControlesAdicionarPreventaPOP(false);
	        DespliegueControlesConfirmarPreventaPOP(false);
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
			AdicionarPreventaProductoPOP();
			break;
		case R.id.btnPreventaProductoPOPConfirmarPreventaPOP:
			ConfirmarPreventaPOP();
			break;
		}
	}
	
	private void DespliegueControlesAdicionarPreventaPOP(boolean estado)
	{
		int visibilidad = View.VISIBLE;
	    if(!estado)
	    {
			visibilidad = View.INVISIBLE;
	    }
	    
	    tvProductoPOPTxt.setVisibility(visibilidad);
	    tvProductoPOP.setVisibility(visibilidad);
	    tvInventarioTxt.setVisibility(visibilidad);
	    tvInventario.setVisibility(visibilidad);
	    tvCantidad.setVisibility(visibilidad);
	    etCantidad.setVisibility(visibilidad);
	    tvMotivoPop.setVisibility(visibilidad);
	    spnMotivosPop.setVisibility(visibilidad);
	    btnAdicionar.setVisibility(visibilidad); 
	    etCantidad.setText("");
	}
	
	private void DespliegueControlesConfirmarPreventaPOP(boolean estado)
	{
		int visibilidad = View.VISIBLE;
	    if(!estado)
	    {
			visibilidad = View.INVISIBLE;
	    }
	    
	    tvProductoPOPListview.setVisibility(visibilidad);
	    tvCantidadPOPListview.setVisibility(visibilidad);
	    lvPreventasProductosPOP.setVisibility(visibilidad);
	    btnConfirmar.setVisibility(visibilidad);
    }

	public void CargarInformacion()
	{
		CargarParametroGeneral();
	    CargarCliente();
	    CargarProveedores();
	    if(CargarCategoriasPOP())
	    {
	    	CargarProductos();
		    CargarMotivosPOP();
	    }
	    
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
				
				ArrayList<CategoriaPOP> listadoCategoria = null;
				try
				{
					listadoCategoria = new CategoriaPOPBLL().ObtenerCategoriasPOPPorProveedorId(proveedor.get_proveedorId());
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
					spnCategorias.setAdapter(null);
					spnProductos.setAdapter(null);
					return;
				}

				CargarCategoriasPOP();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent){}
	    	
		});
	    
	}
	
	public boolean CargarCategoriasPOP()
	{
		int proveedorId = ((Proveedor)spnProveedores.getSelectedItem()).get_proveedorId();
	    
		ArrayList<CategoriaPOP> listadoCategoriaPOP = null;
	    
	    try
	    {
	    	listadoCategoriaPOP = new CategoriaPOPBLL().ObtenerCategoriasPOPPorProveedorId(proveedorId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null|| localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las categoriasPOP por proveedorId: vacio");
	    	} 
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las categoriasPOP por proveedorId: " + localException.getMessage());
	    	} 
	    }
	    
	    if(listadoCategoriaPOP == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las categoriasPOP por el proveedor seleccionado.", 1);
	    	spnProductos.setAdapter(null);
	    	return false;
	    }
	    
	    ArrayAdapter<CategoriaPOP> localArrayAdapter = new ArrayAdapter<CategoriaPOP>(this,R.layout.disenio_spinner,listadoCategoriaPOP);
	    spnCategorias.setAdapter(localArrayAdapter);
	    
	    spnCategorias.setOnItemSelectedListener(new OnItemSelectedListener() 
	    {	    	
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
			{
				Proveedor proveedor = (Proveedor)spnProveedores.getSelectedItem();
				CategoriaPOP categoriaPOP = (CategoriaPOP)spnCategorias.getSelectedItem();
				
				ArrayList<ProductoPOP> listadoProductoPOP = null;
				try
				{
					listadoProductoPOP = new ProductoPOPBLL().ObtenerProductosPOPPorProveedorNoEnPreventaProductoPOP(proveedor.get_proveedorId(),
																		categoriaPOP.get_categoriaId(),preventaIdPOP);
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
				        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productosPOP por proveedorId: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productosPOP por proveedorId: " + localException.getMessage());
					} 
				}
				
				if(listadoProductoPOP == null)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los productosPOP por proveedorId", 1);
					return;
				}

				CargarProductos();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent){}
	    	
		});
	    
	    return true;
	}
	
	public void CargarProductos()
	{
		int proveedorId = ((Proveedor)spnProveedores.getSelectedItem()).get_proveedorId();
		int categoriaId = ((CategoriaPOP)spnCategorias.getSelectedItem()).get_categoriaId();
	    ArrayList<ProductoPOP> listadoProductoPOP = null;
	    
	    try
	    {
	    	listadoProductoPOP = new ProductoPOPBLL().ObtenerProductosPOPPorProveedorNoEnPreventaProductoPOP(proveedorId,
	    													categoriaId,preventaIdPOP);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null|| localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productosPOP por proveedorId: vacio");
	    	} 
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productosPOP por proveedorId: " + localException.getMessage());
	    	} 
	    }
	    
	    if(listadoProductoPOP == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los productosPOP por el proveedor seleccionado.", 1);
	    	return;
	    }
	    
	    ArrayAdapter<ProductoPOP> localArrayAdapter = new ArrayAdapter<ProductoPOP>(this,R.layout.disenio_spinner,listadoProductoPOP);
	    spnProductos.setAdapter(localArrayAdapter);
	    
	    spnProductos.setOnItemSelectedListener(new OnItemSelectedListener() 
	    {	    	
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
			{
				DespliegueControlesAdicionarPreventaPOP(false);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent){}
	    	
		});
	}

	public void CargarMotivosPOP()
	{
	    ArrayList<MotivoPop> listadoMotivoPOP = null;
	    
	    try
	    {
	    	listadoMotivoPOP = new MotivoPopBLL().ObtenerMotivosPop();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null|| localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos POP: vacio");
	    	} 
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos POP: " + localException.getMessage());
	    	} 
	    }
	    
	    if(listadoMotivoPOP == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los motivos POP.", 1);
	    	return;
	    }
	    
	    ArrayAdapter<MotivoPop> localArrayAdapter = new ArrayAdapter<MotivoPop>(this,R.layout.disenio_spinner,listadoMotivoPOP);
	    spnMotivosPop.setAdapter(localArrayAdapter);
	    
	    spnMotivosPop.setOnItemSelectedListener(new OnItemSelectedListener() 
	    {	    	
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
			{
				motivoPopId = ((MotivoPop)spnMotivosPop.getSelectedItem()).get_motivoPopId();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent){}
	    	
		});
	}
	
	public void ActualizarPreventaListView()
	{
		listadoPreventaProductoPOP = null;
		try
		{
			listadoPreventaProductoPOP = new PreventaProductoPOPBLL().ObtenerPreventasPorductoPOPPorPreventaPOPId(preventaIdPOP);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventaProductoPOP por preventaIdPOP : vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventaProductoPOP por preventaIdPOP: " + localException.getMessage());
			}
		}
		      
		if(listadoPreventaProductoPOP == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No existen preventaProductoPOP temporales.", 1);
			DespliegueControlesConfirmarPreventaPOP(false);
			lvPreventasProductosPOP.setAdapter(null);
		}
		else
		{
			DespliegueControlesConfirmarPreventaPOP(true);
			lvPreventasProductosPOP.setVisibility(View.VISIBLE);
		    LlenarPreventaListView();
		    EliminarPreventaItemListView();	
		}  
	}
	
	private void LlenarPreventaListView()
	{
	    MiAdaptadorPreventaLista localMiAdaptadorPreventaLista = new MiAdaptadorPreventaLista(getApplicationContext());
	    	    
    	if(listadoPreventaProductoPOP == null)
	    {
	    	lvPreventasProductosPOP.setAdapter(null);
	    }
	    else
	    {
		    ViewGroup.LayoutParams localLayoutParams = lvPreventasProductosPOP.getLayoutParams();
		    localLayoutParams.height = ((int)(50*getApplicationContext().getResources().getDisplayMetrics().density) * listadoPreventaProductoPOP.size());
		    lvPreventasProductosPOP.setLayoutParams(localLayoutParams);
		    lvPreventasProductosPOP.setAdapter(localMiAdaptadorPreventaLista);
	    }
	}

	class MiAdaptadorPreventaLista extends ArrayAdapter<PreventaProductoPOP>
	{
		private Context _context;
		
		public MiAdaptadorPreventaLista(Context context)
		{
			super(context,R.layout.disenio_vendedorpreventaproducto,listadoPreventaProductoPOP);
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
			
			TextView descripcion25 = (TextView)localView.findViewById(R.id.tvVendedorVentaDirectaProductoCliente);
			TextView cantidad = (TextView)localView.findViewById(R.id.tvPreventaDisenioCantidad);
			TextView monto = (TextView)localView.findViewById(R.id.tvVendedorPreventaProductoProveedor);
			TextView montoPaquete = (TextView)localView.findViewById(R.id.tvVendedorPreventaProductoProducto);
			TextView precio = (TextView)localView.findViewById(R.id.tvPreventaDisenioPrecioUnitario);
			ImageView bullet = (ImageView)localView.findViewById(R.id.imgvLoginLogotipo);
			bullet.setImageResource(R.drawable.bullet_eliminar);
			
			PreventaProductoPOP localPreventaProductoPOP = (PreventaProductoPOP)listadoPreventaProductoPOP.get(position);
      
			if(localPreventaProductoPOP.get_productoPOPId() != 0)
			{
				ProductoPOP localProductoPOP = null;
				try
				{
					localProductoPOP = new ProductoPOPBLL().ObtenerProductoPOPPorProductoId(localPreventaProductoPOP.get_productoPOPId());
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
				
				if(localProductoPOP == null)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el productoPOP.", 1);
		            return null;
				}
				
				descripcion25.setText(localProductoPOP.get_descripcion25());
				cantidad.setText("");
				precio.setText("");
				monto.setText(String.valueOf(localPreventaProductoPOP.get_cantidad()));
				montoPaquete.setText("");
			}

			return localView;
		}
	}
	
	private void EliminarPreventaItemListView()
	{
		((ListView)findViewById(R.id.lvPreventaProductoPOPProductos)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
			public void onItemClick(AdapterView<?> parent, View view,final int position, long id)
			{
				dialog = new Dialog(Vendedorpreventaproductopop.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.diseniodialogo_vendedorcierredia);
				dialog.setTitle("Eliminando item");
				dialog.setCancelable(false);
				dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.colorBluePale));
				
				TextView tvMensaje = (TextView)dialog.findViewById(R.id.tvDialogoMensaje);
				tvMensaje.setText("Esta seguro de eliminar el producto POP?");
				
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
								
								PreventaProductoPOP localPreventaProductoPOP = listadoPreventaProductoPOP.get(position);
								
								if(BorrarPreventaProductoPOPDispositivo(localPreventaProductoPOP.get_id()))
								{
									theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la preventaPOP eliminado del dispositivo.", 1);
									CargarProductos();
									ActualizarPreventaListView();
								} 
								else 
								{
									theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo eliminar el producto POP de la preventa.", 1);
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
		listadoVentaProductoPOP = null;
		try
		{
			listadoVentaProductoPOP = new VentaProductoPOPBLL().ObtenerVentasPorductoPOPPorVentaPOPId(ventaDirectaIdPOP);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventaProductoPOP por ventaIdPOP : vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventaProductoPOP por ventaIdPOP: " + localException.getMessage());
			}
		}
		      
		if(listadoVentaProductoPOP == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No existen ventaProductoPOP temporales.", 1);
			DespliegueControlesConfirmarPreventaPOP(false);
			lvPreventasProductosPOP.setAdapter(null);
		}
		else
		{
			DespliegueControlesConfirmarPreventaPOP(true);
			lvPreventasProductosPOP.setVisibility(View.VISIBLE);
		    LlenarVentaListView();
		    EliminarVentaItemListView();	
		}		    
	}
	
	private void LlenarVentaListView()
	{
	    MiAdaptadorVentaLista localMiAdaptadorVentaLista = new MiAdaptadorVentaLista(getApplicationContext());
	    	    
    	if(listadoVentaProductoPOP == null)
	    {
	    	lvPreventasProductosPOP.setAdapter(null);
	    }
	    else
	    {
		    ViewGroup.LayoutParams localLayoutParams = lvPreventasProductosPOP.getLayoutParams();
		    localLayoutParams.height = ((int)(50*getApplicationContext().getResources().getDisplayMetrics().density) * listadoVentaProductoPOP.size());
		    lvPreventasProductosPOP.setLayoutParams(localLayoutParams);
		    lvPreventasProductosPOP.setAdapter(localMiAdaptadorVentaLista);
	    }
	}

	class MiAdaptadorVentaLista extends ArrayAdapter<VentaProductoPOP>
	{
		private Context _context;
		
		public MiAdaptadorVentaLista(Context context)
		{
			super(context,R.layout.disenio_vendedorpreventaproducto,listadoVentaProductoPOP);
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
			
			TextView descripcion25 = (TextView)localView.findViewById(R.id.tvVendedorVentaDirectaProductoCliente);
			TextView cantidad = (TextView)localView.findViewById(R.id.tvPreventaDisenioCantidad);
			TextView monto = (TextView)localView.findViewById(R.id.tvVendedorPreventaProductoProveedor);
			TextView montoPaquete = (TextView)localView.findViewById(R.id.tvVendedorPreventaProductoProducto);
			TextView precio = (TextView)localView.findViewById(R.id.tvPreventaDisenioPrecioUnitario);
			ImageView bullet = (ImageView)localView.findViewById(R.id.imgvLoginLogotipo);
			bullet.setImageResource(R.drawable.bullet_eliminar);
			
			VentaProductoPOP localVentaProductoPOP = (VentaProductoPOP)listadoVentaProductoPOP.get(position);
			
			if(localVentaProductoPOP.get_productoPOPId() != 0)
			{
				ProductoPOP localProductoPOP = null;
				try
				{
					localProductoPOP = new ProductoPOPBLL().ObtenerProductoPOPPorProductoId(localVentaProductoPOP.get_productoPOPId());
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
				
				if(localProductoPOP == null)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el productoPOP.", 1);
		            return null;
				}
				
				descripcion25.setText(localProductoPOP.get_descripcion25());
				cantidad.setText("");
				precio.setText("");
				monto.setText(String.valueOf(localVentaProductoPOP.get_cantidad()));
				montoPaquete.setText("");
			}
		
			return localView;
		}
	}
	
	private void EliminarVentaItemListView()
	{
		((ListView)findViewById(R.id.lvPreventaProductoPOPProductos)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
			public void onItemClick(AdapterView<?> parent, View view,final int position, long id)
			{
				dialog = new Dialog(Vendedorpreventaproductopop.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.diseniodialogo_vendedorcierredia);
				dialog.setTitle("Eliminando item");
				dialog.setCancelable(false);
				dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.colorBluePale));
				
				TextView tvMensaje = (TextView)dialog.findViewById(R.id.tvDialogoMensaje);
				tvMensaje.setText("Esta seguro de eliminar el producto POP?");
				
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

								VentaProductoPOP localVentaProductoPOP = listadoVentaProductoPOP.get(position);
								
								if(BorrarVentaProductoPOPDispositivo(localVentaProductoPOP.get_id()))
								{
									theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la ventaPOP eliminado del dispositivo.", 1);
									CargarProductos();
									ActualizarVentaListView();
								} 
								else 
								{
									theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo eliminar el producto POP de la preventa.", 1);
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
	
	private boolean BorrarPreventaProductoPOPDispositivo(int id)
	 {
		 boolean bool = false;
		 try
		 {
			 bool = new PreventaProductoPOPBLL().BorrarPreventaProductoPOPPor(id);
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al borrar el item de la preventaProductoPOP por Id: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al borrar el item de la preventaProductoPOP por Id: " + localException.getMessage());
			 }
			 return false;
		 }

		 return bool;
	}
	
	private boolean BorrarVentaProductoPOPDispositivo(int id)
	{
		boolean bool = false;
		try
		{
			bool = new VentaProductoPOPBLL().BorrarVentaProductoPOPPor(id);
	 	}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar el item de la ventaProductoPOP por Id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar el item de la ventaProductoPOP por Id: " + localException.getMessage());
			}
			return false;
		}

		return bool;
	}
	
	public void ObtenerDatosSeleccion()
	 {
		 int productoId = 0;
		 
		 productoId = ((ProductoPOP)spnProductos.getSelectedItem()).get_productoId();
		 if(productoId == 0)
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(), "Debe seleccionar un producto.", 1);
			 return;
		 }
		 
		 productoPOP = null;
		 try
		 {
			 productoPOP = new ProductoPOPBLL().ObtenerProductoPOPPorProductoId(productoId);
		 }
		 catch(Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del productoPOP: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del productoPOP: " + localException.getMessage());
			 } 
		 }
		 
		 if(productoPOP == null)
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del productoPOP", 1);
			 return;
		 }
		 
		 AlmacenPOPProducto almacenPOPProducto = null;
		 try
		 {
			 almacenPOPProducto = new AlmacenPOPProductoBLL().ObtenerAlmacenPOPProductoPorProductoId(productoPOP.get_productoId());
		 }
		 catch(Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al obtener el inventario del almacenPOP por productoId: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al obtener el inventario del almacenPOP por productoId: " + localException.getMessage());
			 }
		 }
		 
		 if(almacenPOPProducto == null)
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el inventarioPOP del producto", 1);
			 return;
		 }
		        
	     DespliegueControlesAdicionarPreventaPOP(true);
	     
	     tvProductoPOP.setText(productoPOP.get_descripcion25());
	     tvInventario.setText(String.valueOf(almacenPOPProducto.get_saldo()));

		 CargarMotivosPOP();
	 }

	private void AdicionarPreventaProductoPOP()
	{
		  int cantidad;
		  
		  if(motivoPopId <= 0)
		  {
			  theUtilidades.MostrarMensaje(getApplicationContext(), "El motivo POP es requerido.", 1);
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
			  VentaProductoPOP localVentaProductoPOP = new VentaProductoPOP();
		      localVentaProductoPOP.set_ventaPOPId(ventaDirectaIdPOP);
		      localVentaProductoPOP.set_ventaPOPIdServer(ventaDirectaIdPOPServer);
		      localVentaProductoPOP.set_productoPOPId(productoPOP.get_productoId());
		      localVentaProductoPOP.set_cantidad(cantidad);
		      localVentaProductoPOP.set_clienteId(clientePreventa.get_clienteId());
		      localVentaProductoPOP.set_syncro(false);
		      localVentaProductoPOP.set_motivoPopId(motivoPopId);
		      
		      if (ValidarExistenciasAlmacenPOPDispositivo(productoPOP.get_productoId(),cantidad))
		      {	  	    	  
		    	  if(InsertarVentaProductoPOPDispositivo(localVentaProductoPOP))
			      {
			    	  theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la venta insertado en el dispositivo.", 1);
			    	  CargarProductos();
			    	  DespliegueControlesAdicionarPreventaPOP(false);
			    	  ActualizarVentaListView();
			      }
			      else 
			      {
			    	  theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el producto de la venta en el dispositivo.",1);
			      }
		      }
		      else
		      {
		    	  int saldoUnidades = ObtenerExistenciaProductoEnAlmacenPOPPorProductoId(productoPOP.get_productoId());
		    	  theUtilidades.MostrarMensaje(getApplicationContext(), "No existe productoPOP en el almacen del dispositivo."
		    	  									+ "Cantidad actual: " + String.valueOf(saldoUnidades) + " unidades.", 1);
		      }
		  }
		  else
		  {
			  PreventaProductoPOP localPreventaProductoPOP = new PreventaProductoPOP();
		      localPreventaProductoPOP.set_preventaPOPId(preventaIdPOP);
		      localPreventaProductoPOP.set_preventaPOPIdServer(preventaIdPOPServer);
		      localPreventaProductoPOP.set_productoPOPId(productoPOP.get_productoId());
		      localPreventaProductoPOP.set_cantidad(cantidad);
		      localPreventaProductoPOP.set_clienteId(clientePreventa.get_clienteId());
		      localPreventaProductoPOP.set_syncro(false);
		      localPreventaProductoPOP.set_motivoPopId(motivoPopId);
		      
		      if (ValidarExistenciasAlmacenPOPDispositivo(productoPOP.get_productoId(),cantidad))
		      {	  	    	  
		    	  if(InsertarPreventaProductoPOPDispositivo(localPreventaProductoPOP))
			      {
			    	  theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la preventa insertado en el dispositivo.", 1);
			    	  CargarProductos();
			    	  DespliegueControlesAdicionarPreventaPOP(false);
			    	  ActualizarPreventaListView();
			      }
			      else 
			      {
			    	  theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el producto de la preventa en el dispositivo.",1);
			      }
		      }
		      else
		      {
		    	  int saldoUnidades = ObtenerExistenciaProductoEnAlmacenPOPPorProductoId(productoPOP.get_productoId());
		    	  theUtilidades.MostrarMensaje(getApplicationContext(), "No existe productoPOP en el almacen del dispositivo."
		    	  									+ "Cantidad actual: " + String.valueOf(saldoUnidades) + " unidades.", 1);
		      }
		  }
	 }
	
	private boolean ValidarExistenciasAlmacenPOPDispositivo(int productoId, int cantidad)
	{
		AlmacenPOPProducto localAlmacenPOPProducto = null;
		 try
		 {
			 localAlmacenPOPProducto = new AlmacenPOPProductoBLL().ValidarExistenciasAlmacenPOPDispositivo(productoId,cantidad);
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
		return localAlmacenPOPProducto != null;
	 }
	
	private boolean InsertarPreventaProductoPOPDispositivo(PreventaProductoPOP paramPreventaProductoPOP)
	{
		long l = 0;
		try
		{
			l = new PreventaProductoPOPBLL().InsertarPreventaProductoPOP(paramPreventaProductoPOP.get_preventaPOPId(),paramPreventaProductoPOP.get_preventaPOPIdServer(),
					paramPreventaProductoPOP.get_productoPOPId(),paramPreventaProductoPOP.get_cantidad(),paramPreventaProductoPOP.get_clienteId(),
					paramPreventaProductoPOP.is_syncro(),"",paramPreventaProductoPOP.get_motivoPopId());
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preVentaProductoPOP en el dispositivo: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preVentaProductoPOP en el dispositivo: " + localException.getMessage());
	    	}
	    	return false;
	    }
	    	
	    if(l==0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la preventaProductoPOP en el dispositivo.", 1);
	  	     return false;		
	    }
	    else
	    {
	    	return true;
	    }
	}
	
	private boolean InsertarVentaProductoPOPDispositivo(VentaProductoPOP paramVentaProductoPOP)
	{
		long l = 0;
		try
		{
			l = new VentaProductoPOPBLL().InsertarVentaProductoPOP(paramVentaProductoPOP.get_ventaPOPId(),paramVentaProductoPOP.get_ventaPOPIdServer(),
					paramVentaProductoPOP.get_productoPOPId(),paramVentaProductoPOP.get_cantidad(),paramVentaProductoPOP.get_clienteId(),
					paramVentaProductoPOP.is_syncro(),"",paramVentaProductoPOP.get_motivoPopId());
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ventaProductoPOP en el dispositivo: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ventaProductoPOP en el dispositivo: " + localException.getMessage());
	    	}
	    	return false;
	    }
	    	
	    if(l==0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la ventaProductoPOP en el dispositivo.", 1);
	  	     return false;		
	    }
	    else
	    {
	    	return true;
	    }
	}
	
	private int ObtenerExistenciaProductoEnAlmacenPOPPorProductoId(int productoId)
	{
		AlmacenPOPProducto almacenPOPProducto = null;
		try
		{
			almacenPOPProducto = new AlmacenPOPProductoBLL().ObtenerAlmacenPOPProductoPorProductoId(productoId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el saldo en unidades del productoPOP del almacen: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el saldo en unidades del productoPOP del almacen: " + localException.getMessage());
			}
			return 0;
		}
		
		if(almacenPOPProducto==null)
		{
			return 0;
		}
		else
		{
			return almacenPOPProducto.get_saldo();
		}
	}

	private void ConfirmarPreventaPOP()
	{
		btnConfirmar.setVisibility(View.INVISIBLE);
		
		if(ventaDirecta)
		{
			if(listadoVentaProductoPOP != null && listadoVentaProductoPOP.size() > 0)
			{
				if(clienteId > 0)
				{
					if(ObtenerVentasProductoPOPNoSincronizadas())
					{
						SincronizarVentaProductoPOP();
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No existen productos de la venta POP que sincronizar.", 1);
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Para ingresar los productos POP debe sincronizar el cliente.", 1);
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
			}
		}
		else
		{
			if(listadoPreventaProductoPOP != null && listadoPreventaProductoPOP.size() > 0)
			{
				if(clienteId > 0)
				{
					if(ObtenerPreventasProductoPOPNoSincronizadas())
					{
						SincronizarPreventaProductoPOP();
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No existen productos de la venta POP que sincronizar.", 1);
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Para ingresar los productos POP debe sincronizar el cliente.", 1);
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
			}
		}
	}
		
	private boolean ObtenerPreventasProductoPOPNoSincronizadas()
	{
		listadoPreventaProductoPOPNoSincronizadas = null;
	    try
	    {
	    	listadoPreventaProductoPOPNoSincronizadas = new PreventaProductoPOPBLL().ObtenerPreventasPorductoPOPNoSincronizadasPor(preventaIdPOP);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la preventaPOP no sincronizada por preventaIdPOP: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la preventaPOP no sincronizada por preventaIdPOP: " + localException.getMessage());
	    	}
	    }

		return listadoPreventaProductoPOPNoSincronizadas != null;
	}
	
	private boolean ObtenerVentasProductoPOPNoSincronizadas()
	{
		listadoVentaProductoPOPNoSincronizadas = null;
	    try
	    {
	    	listadoVentaProductoPOPNoSincronizadas = new VentaProductoPOPBLL().ObtenerVentasPorductoPOPNoSincronizadasPor(ventaDirectaIdPOP);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la ventaPOP no sincronizada por ventaIdPOP: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la ventaPOP no sincronizada por ventaIdPOP: " + localException.getMessage());
	    	}
	    }

		return listadoVentaProductoPOPNoSincronizadas != null;
	}
	
	private void SincronizarPreventaProductoPOP() 
	{
		if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
		{
			if(listadoPreventaProductoPOPNoSincronizadas != null && listadoPreventaProductoPOPNoSincronizadas.size()>0)
			{
				if(listadoPreventaProductoPOPNoSincronizadas.get(0).get_preventaPOPIdServer()!=0)
				{
					ProductoPOPCosto productoPOPCosto=null;
					try
					{
						productoPOPCosto = new ProductoPOPCostoBLL().ObtenerProductoPOPCostoPorProductoId(listadoPreventaProductoPOPNoSincronizadas.get(0).get_productoPOPId());
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
				    	{
				            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el costoProductoPOP por productoId: vacio");
				    	}
				    	else
				    	{
				    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el costoProductoPOP por productoId: " + localException.getMessage());
				    	}
					}
					
					if(productoPOPCosto == null)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el costo del productoPOP.", 1);
					}
					else
					{
						InsertarPreventaProductoPOP(listadoPreventaProductoPOPNoSincronizadas.get(0),productoPOPCosto);	
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Para sincronizar el producto POP, primero debes sincronizar la preventa.", 1);
					MostrarPantallaMenuVendedor();
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No existen productos de la preventa POP que sincronizar.", 1);
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
	
	private void SincronizarVentaProductoPOP() 
	{
		if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
		{
			if(listadoVentaProductoPOPNoSincronizadas != null && listadoVentaProductoPOPNoSincronizadas.size()>0)
			{
				if(listadoVentaProductoPOPNoSincronizadas.get(0).get_ventaPOPIdServer()!=0)
				{
					ProductoPOPCosto productoPOPCosto=null;
					try
					{
						productoPOPCosto = new ProductoPOPCostoBLL().ObtenerProductoPOPCostoPorProductoId(listadoVentaProductoPOPNoSincronizadas.get(0).get_productoPOPId());
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
				    	{
				            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el costoProductoPOP por productoId: vacio");
				    	}
				    	else
				    	{
				    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el costoProductoPOP por productoId: " + localException.getMessage());
				    	}
					}
					
					if(productoPOPCosto == null)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el costo del productoPOP.", 1);
					}
					else
					{
						InsertarVentaProductoPOP(listadoVentaProductoPOPNoSincronizadas.get(0),productoPOPCosto);	
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Para sincronizar el producto POP, primero debes sincronizar la venta.", 1);
					MostrarPantallaMenuVendedor();
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No existen productos de la venta POP que sincronizar.", 1);
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No esta conectado a internet, intentelo mas tarde.", 1);
			MostrarPantallaMenuVendedor();
		}
	}
	
	private void InsertarPreventaProductoPOP(PreventaProductoPOP localPreventaProductoPOP, ProductoPOPCosto productoPOPCosto)
	 {
		pdInsertarPrevenProductoPOP = new ProgressDialog(this);
		pdInsertarPrevenProductoPOP.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdInsertarPrevenProductoPOP.setMessage("Insertando productos preventa POP ...");
		pdInsertarPrevenProductoPOP.setCancelable(false);
		pdInsertarPrevenProductoPOP.setCanceledOnTouchOutside(false);
	    
	    WSInsertarPreventaProductoPOP localWSInsertarPreventaProductoPOP = new WSInsertarPreventaProductoPOP(localPreventaProductoPOP,productoPOPCosto);
	    try
	    {
	      localWSInsertarPreventaProductoPOP.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventaProductoPop: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarPreVentaProductoPop: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertarPreventaProductoPop", 1);
	    }
	 }
	 
	public class WSInsertarPreventaProductoPOP extends AsyncTask<Void, Integer, Boolean>
	 {
		 String PREVENTAPRODUCTOPOP_METHOD_NAME = "InsertPreVentaProductoPop";
		 String PREVENTAPRODUCTOPOP_SOAP_ACTION = NAMESPACE + PREVENTAPRODUCTOPOP_METHOD_NAME;
		 
		 private PreventaProductoPOP _preventaProductoPOP;
		 private ProductoPOPCosto _productoPOPCosto;
		 
		 boolean WSInsertarPreVentaProductoPOP;
		 int resultadoInt;
		 String resultadoString;
		 SoapObject localSoapObjects;
	    
	    public WSInsertarPreventaProductoPOP(PreventaProductoPOP paramPreventaProductoPOP,ProductoPOPCosto paramProductoPOPCosto)
	    {
	    	this._preventaProductoPOP = paramPreventaProductoPOP;
	    	this._productoPOPCosto = paramProductoPOPCosto;
	    }
	    
	    protected void onPreExecute()
	    {
	    	pdInsertarPrevenProductoPOP.show();
	    }
	    
	    protected Boolean doInBackground(Void... paramVarArgs)
	    {
	    	WSInsertarPreVentaProductoPOP = false;
	      
	    	float monto = _preventaProductoPOP.get_cantidad() * _productoPOPCosto.get_costo();
	    	
	    	SoapObject localSoapObject = new SoapObject(NAMESPACE, this.PREVENTAPRODUCTOPOP_METHOD_NAME);
	    	localSoapObject.addProperty("preVentaId", _preventaProductoPOP.get_preventaPOPIdServer());
	    	localSoapObject.addProperty("productoId", _preventaProductoPOP.get_productoPOPId());
	    	localSoapObject.addProperty("cantidad", _preventaProductoPOP.get_cantidad());
	    	localSoapObject.addProperty("monto", String.valueOf(monto));
	   		localSoapObject.addProperty("empleadoId", loginEmpleado.get_empleadoId());
	   		localSoapObject.addProperty("costoId",_productoPOPCosto.get_costoId());
	   		localSoapObject.addProperty("almacenId",(loginEmpleado.get_almacenId()));
	   		localSoapObject.addProperty("motivoPopId",(_preventaProductoPOP.get_motivoPopId()));

	   		SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
	   		localSoapSerializationEnvelope.dotNet = true;
	   		localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
	   		HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
	   		try
		    	{
	   			localHttpTransportSE.call(PREVENTAPRODUCTOPOP_SOAP_ACTION, localSoapSerializationEnvelope);
	   			
	   			localSoapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
	   			if(localSoapObjects!=null)
	   			{
	   				resultadoInt = Integer.parseInt(localSoapObjects.getPropertyAsString("Id"));
	       			resultadoString = localSoapObjects.getPropertyAsString("Resultado");    				
	   			}
	   			
	   			if(resultadoString.equals("OK") && resultadoInt > 0)
	   			{
	   				WSInsertarPreVentaProductoPOP = true;
	   			}
	   			return true;
		    	}
	   		catch (Exception localException)
	   		{
	   			WSInsertarPreVentaProductoPOP = false;
	   			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	   			{
	   		        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventaProductoPOP: vacio");
	   			}
	   			else
	   			{
	   				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventaProductoPOP: " + localException.getMessage());
	   			} 
	   			return true;
	   		}
	   	}
	    
	    protected void onPostExecute(Boolean ejecutado)
	    {
	    	if(pdInsertarPrevenProductoPOP.isShowing())
	    	{
	    		pdInsertarPrevenProductoPOP.dismiss();
	    	}
	    	
	    	if(ejecutado) 
	    	{
	    		if(WSInsertarPreVentaProductoPOP || (resultadoString != null && resultadoString.equals("Preventa Producto Pop Repetido") && resultadoInt <= 0))
	    		{
	    			long l = 0;
	    			try
	    			{
	    				l = new PreventaProductoPOPBLL().ModificarPreventaProductoPOPNoSincronizadaPor(_preventaProductoPOP.get_id(), resultadoInt);
					}
					catch (Exception localException)
	    			{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del producto de la preventa POP: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del producto de la preventa POP: " + localException.getMessage());
						} 
					}
						
	    			if(l==0)
	    			{
	    				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del producto de la preventa POP.", 1);
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "Producto de la preventa POP sincronizado.", 1);
							
						if(ObtenerPreventasProductoPOPNoSincronizadas())
						{
							SincronizarPreventaProductoPOP();
						}
						else
						{
							ObtenerProductosAlmacenPOP();
						}
					}
	    		}
	    		else 
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo ejecutar el webservice WSInsertPreVentaProductoTemp.", 1);
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
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "El WebService InsertPreVentaProductoTemp no se ejecuto correctamente. ", 1);
	    	}
	    }
	 }
	
	private void InsertarVentaProductoPOP(VentaProductoPOP localVentaProductoPOP, ProductoPOPCosto productoPOPCosto)
	 {
		pdInsertarVenProductoPOP = new ProgressDialog(this);
		pdInsertarVenProductoPOP.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdInsertarVenProductoPOP.setMessage("Insertando productos venta POP ...");
		pdInsertarVenProductoPOP.setCancelable(false);
		pdInsertarVenProductoPOP.setCanceledOnTouchOutside(false);
	    
	    WSInsertarVentaProductoPOP localWSInsertarVentaProductoPOP = new WSInsertarVentaProductoPOP(localVentaProductoPOP,productoPOPCosto);
	    try
	    {
	      localWSInsertarVentaProductoPOP.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertVentaProductoPop: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarVentaProductoPop: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertarVentaProductoPop", 1);
	    }
	 }
	 
	public class WSInsertarVentaProductoPOP extends AsyncTask<Void, Integer, Boolean>
	 {
		 String VENTAPRODUCTOPOP_METHOD_NAME = "InsertVentaProductoPop";
		 String VENTAPRODUCTOPOP_SOAP_ACTION = NAMESPACE + VENTAPRODUCTOPOP_METHOD_NAME;
		 
		 private VentaProductoPOP _ventaProductoPOP;
		 private ProductoPOPCosto _productoPOPCosto;
		 
		 boolean WSInsertarVentaProductoPOP;
		 int resultadoInt;
		 String resultadoString;
		 SoapObject localSoapObjects;
	    
	    public WSInsertarVentaProductoPOP(VentaProductoPOP paramVentaProductoPOP,ProductoPOPCosto paramProductoPOPCosto)
	    {
	    	this._ventaProductoPOP = paramVentaProductoPOP;
	    	this._productoPOPCosto = paramProductoPOPCosto;
	    }
	    
	    protected void onPreExecute()
	    {
	    	pdInsertarVenProductoPOP.show();
	    }
	    
	    protected Boolean doInBackground(Void... paramVarArgs)
	    {
	    	WSInsertarVentaProductoPOP = false;
	      
	    	float monto = _ventaProductoPOP.get_cantidad() * _productoPOPCosto.get_costo();
	    	
	    	SoapObject localSoapObject = new SoapObject(NAMESPACE, this.VENTAPRODUCTOPOP_METHOD_NAME);
	    	localSoapObject.addProperty("ventaId", _ventaProductoPOP.get_ventaPOPIdServer());
	    	localSoapObject.addProperty("productoId", _ventaProductoPOP.get_productoPOPId());
	    	localSoapObject.addProperty("cantidad", _ventaProductoPOP.get_cantidad());
	    	localSoapObject.addProperty("monto", String.valueOf(monto));
	   		localSoapObject.addProperty("empleadoId", loginEmpleado.get_empleadoId());
	   		localSoapObject.addProperty("costoId",_productoPOPCosto.get_costoId());
	   		localSoapObject.addProperty("almacenId",(loginEmpleado.get_almacenId()));
	   		localSoapObject.addProperty("motivoPopId",(_ventaProductoPOP.get_motivoPopId()));
	       
	   		SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
	   		localSoapSerializationEnvelope.dotNet = true;
	   		localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
	   		HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
	   		try
	    	{
	   			localHttpTransportSE.call(VENTAPRODUCTOPOP_SOAP_ACTION, localSoapSerializationEnvelope);
	   			
	   			localSoapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
	   			if(localSoapObjects!=null)
	   			{
	   				resultadoInt = Integer.parseInt(localSoapObjects.getPropertyAsString("Id"));
	       			resultadoString = localSoapObjects.getPropertyAsString("Resultado");    				
	   			}
	   			
	   			if(resultadoString.equals("OK") && resultadoInt > 0)
	   			{
	   				WSInsertarVentaProductoPOP = true;
	   			}
	   			return true;
	    	}
	   		catch (Exception localException)
	   		{
	   			WSInsertarVentaProductoPOP = false;
	   			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	   			{
	   		        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertVentaProductoPOP: vacio");
	   			}
	   			else
	   			{
	   				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertVentaProductoPOP: " + localException.getMessage());
	   			} 
	   			return true;
	   		}
	   	}
	    
	    protected void onPostExecute(Boolean ejecutado)
	    {
	    	if(pdInsertarVenProductoPOP.isShowing())
	    	{
	    		pdInsertarVenProductoPOP.dismiss();
	    	}
	    	
	    	if(ejecutado) 
	    	{
	    		if(WSInsertarVentaProductoPOP || (resultadoString != null && resultadoString.equals("Venta Producto Pop Repetido") && resultadoInt <= 0))
	    		{
	    			long l = 0;
	    			try
	    			{
	    				l = new VentaProductoPOPBLL().ModificarVentaProductoPOPNoSincronizadaPor(_ventaProductoPOP.get_id(), resultadoInt);
					}
					catch (Exception localException)
	    			{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del producto de la venta POP: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del producto de la venta POP: " + localException.getMessage());
						} 
					}
						
	    			if(l==0)
	    			{
	    				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del producto de la venta POP.", 1);
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "Producto de la venta POP sincronizado.", 1);
							
						if(ObtenerVentasProductoPOPNoSincronizadas())
						{
							SincronizarVentaProductoPOP();
						}
						else
						{
							ObtenerProductosAlmacenPOP();
						}
					}
	    		}
	    		else 
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo ejecutar el webservice WSInsertVentaProductoTemp.", 1);
	    			MostrarPantallaMenuVendedor();
	        	}
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "El WebService InsertVentaProductoTemp no se ejecuto correctamente. ", 1);
	    	}
	    }
	 }
	
	private void ObtenerProductosAlmacenPOP()
	{
		pdEsperaObtenerProductoAlmacenPOP = new ProgressDialog(this);
		pdEsperaObtenerProductoAlmacenPOP.setProgressStyle(0);
		pdEsperaObtenerProductoAlmacenPOP.setMessage("Descargando almacen de productos POP ...");
		pdEsperaObtenerProductoAlmacenPOP.setCancelable(false);
		pdEsperaObtenerProductoAlmacenPOP.setCanceledOnTouchOutside(false);
		  
		WSObtenerProductosAlmacenPOP wsObtenerProductosAlmacenPOP = new WSObtenerProductosAlmacenPOP();
		  
		try
		{
			wsObtenerProductosAlmacenPOP.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
		    {
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosByAlmacenPOP: vacio");
		   	}
		   	else
		   	{
		   		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosByAlmacenPOP: " + localException.getMessage());
		   	}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetProductosByAlmacenPOP.", 1);
		}
	}
	
	private class WSObtenerProductosAlmacenPOP extends AsyncTask<Void, Integer, Boolean>
	{
		String ALMACENPRODUCTOPOP_METHOD_NAME = "GetProductosByAlmacenPop";
		String ALMACENPRODUCTOPOP_SOAP_ACTION = NAMESPACE + ALMACENPRODUCTOPOP_METHOD_NAME;

		boolean WSObtenerProductosAlmacenPOP = false;
		ArrayList<AlmacenPOPProductoWSResult> almacenPOPProductoWSResults;
		String error;
    
		protected void onPreExecute()
	    {
			pdEsperaObtenerProductoAlmacenPOP.show();
	    }
		
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerProductosAlmacenPOP = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,ALMACENPRODUCTOPOP_METHOD_NAME);
			SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerInfo);
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			
			try
			{
				localHttpTransportSE.call(this.ALMACENPRODUCTOPOP_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<AlmacenPOPProductoWSResult>>(){ }.getType();
					almacenPOPProductoWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerProductosAlmacenPOP = (almacenPOPProductoWSResults != null);
				}
				return true;
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
		        	myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosByAlmacenPop: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosByAlmacenPop: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerProductoAlmacenPOP.isShowing())
			{
				pdEsperaObtenerProductoAlmacenPOP.dismiss();
			}
			
			if(!ejecutado) {
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo ejecutar el WSGetProductosByAlmacenPop",1);
				return;
			}

			if (!WSObtenerProductosAlmacenPOP)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No existen productos del almacen POP que descargar.", 2);
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

			if(!borrarProductosAlmacenPOP()) {
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo eborrar los productos del almacen POP",1);
				return;
			}
			long l=0;
			try
			{
				l = new AlmacenPOPProductoBLL().InsertarAlmacenPOPProducto( almacenPOPProductoWSResults );
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosByAlmacenPop: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosByAlmacenPop: " + localException.getMessage());
				}
				return;
			}

			if(l<=0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar los productos del almacen POP.", 2);
				return;
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
	}
	
	private boolean borrarProductosAlmacenPOP()
	{
		AlmacenPOPProductoBLL theBLL = new AlmacenPOPProductoBLL();
		boolean borrado = false;
		try
		{
			borrado = theBLL.BorrarAlmacenesPOPProducto();
			return borrado;
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosAlmacenPOP: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosAlmacenPOP: " + localException.getMessage());
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
		localIntent.putExtra("preventaIdDispositivo", preventaIdPOP);
		localIntent.putExtra("preventaIdServer", preventaIdPOPServer);
		localIntent.putExtra("clienteId", clienteId);
		localIntent.putExtra("ventaIdDispositivo", ventaDirectaIdPOP);
		localIntent.putExtra("ventaIdServer", ventaDirectaIdPOPServer);
		startActivity(localIntent);
	}
	
	@Override
 	public void onBackPressed() 
 	 {
 		 if(listadoPreventaProductoPOP != null)
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
