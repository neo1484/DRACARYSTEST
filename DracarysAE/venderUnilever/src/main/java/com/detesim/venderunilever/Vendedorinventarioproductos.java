package com.detesim.venderunilever;

import java.util.ArrayList;
import BLL.AlmacenProductoBLL;
import BLL.CategoriaBLL;
import BLL.MyLogBLL;
import BLL.ProductoBLL;
import BLL.ProveedorBLL;
import Clases.AlmacenProducto;
import Clases.Categoria;
import Clases.LoginEmpleado;
import Clases.Producto;
import Clases.Proveedor;
import Utilidades.Utilidades;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Vendedorinventarioproductos extends Activity 
{
	LinearLayout llVendedorInventarioProductos;
	Utilidades theUtilidades = new Utilidades();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
	Spinner spnProveedores;
	Spinner spnCategorias;
	ListView lvProductos;
	
	ArrayList<AlmacenProducto> listadoAlmacenProducto ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedorinventarioproductos);
		
		llVendedorInventarioProductos = (LinearLayout)findViewById(R.id.llVendedorInventarioProductos);
		spnProveedores = (Spinner)findViewById(R.id.spnInventarioProductosProveedor);
		spnCategorias = (Spinner)findViewById(R.id.spnInventarioProductosCategoria);
		lvProductos = (ListView)findViewById(R.id.lvInventarioProductosProductos);
		
		llVendedorInventarioProductos.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
		loginEmpleado = null;	    
	    try
	    {
	    	loginEmpleado = theUtilidades.ObtenerLoginEmpleado();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener loginEmpleado vendedor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener loginEmpleado vendedor: " + localException.getMessage());
	    	} 
	    	return;
	    }
	      
	    if(loginEmpleado == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El usuario no se logeo correctamente.", 1);
	        return;
	    }
	    else
	    {
	    	CargarInformacion();
	    }
	}
	
	private void CargarInformacion()
	{
		CargarProveedores();
		CargarCategorias();
	}
	
	private void CargarProveedores()
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
				
				listadoAlmacenProducto = null;
				try
				{
					listadoAlmacenProducto = new AlmacenProductoBLL().ObtenerInventarioAlmacenProductoPor(proveedor.get_proveedorId(), categoria.get_categoriaId());
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
				        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el inventario del almacenProducto: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el inventario del almacenProducto: " + localException.getMessage());
					} 
				}
				
				if(listadoAlmacenProducto == null)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No existen productos en el inventario.", 1);
				}
				
				LlenarAlmacenProductoListView();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent){}
	    	
		});
	}
	
	private void LlenarAlmacenProductoListView()
	{
	    MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    	    
	    if(listadoAlmacenProducto == null)
	    {
	    	lvProductos.setAdapter(null);
	    }
	    else
	    {
		    ViewGroup.LayoutParams localLayoutParams = lvProductos.getLayoutParams();
		    localLayoutParams.height = ((int)(30*getApplicationContext().getResources().getDisplayMetrics().density) * listadoAlmacenProducto.size());
		    lvProductos.setLayoutParams(localLayoutParams);
		    lvProductos.setAdapter(localMiAdaptadorLista);
	    }
	}

	class MiAdaptadorLista extends ArrayAdapter<AlmacenProducto>
	{
		private Context _context;
		
		public MiAdaptadorLista(Context context)
		{
			super(context,R.layout.disenio_distribuidorinventarioautoventa,listadoAlmacenProducto);
			this._context = context;
		}
    
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View localView = convertView;
			
			if (localView == null) 
			{				
				LayoutInflater layoutInflater =	(LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    		localView = layoutInflater.inflate(R.layout.disenio_distribuidorinventarioautoventa, parent, false);
			}
			
			AlmacenProducto localAlmacenProducto = (AlmacenProducto)listadoAlmacenProducto.get(position);
			
			TextView producto = (TextView)localView.findViewById(R.id.tvDistribuidorInventarioAutoventaProducto);
			TextView unidades = (TextView)localView.findViewById(R.id.tvDistribuidorInventarioAutoventaCantidadUnidades);
			TextView paquetes = (TextView)localView.findViewById(R.id.tvDistribuidorInventarioAutoventaCantidadPaquetes);
			      
			Producto localProducto = null;
			try
			{
				localProducto = new ProductoBLL().ObtenerProductoPor(localAlmacenProducto.get_productoId());
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
          
			producto.setText(localProducto.get_descripcion25());
			unidades.setText(String.valueOf(localAlmacenProducto.get_saldoUnitario()));
			paquetes.setText(String.valueOf(localAlmacenProducto.get_saldoPaquete()));
				
			return localView;
		}
	}
}
