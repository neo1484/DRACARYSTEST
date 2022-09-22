package com.detesim.venderunilever;

import java.util.ArrayList;

import BLL.DevolucionDistribuidorBLL;
import BLL.DevolucionDistribuidorProductoBLL;
import BLL.MyLogBLL;
import BLL.ProductoBLL;
import Clases.DevolucionDistribuidor;
import Clases.DevolucionDistribuidorProducto;
import Clases.LoginEmpleado;
import Clases.Producto;
import Utilidades.Utilidades;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Distribuidorinventarioautoventa extends Activity 
{
	LinearLayout llDistribuidorInventarioAutoventa;
	Utilidades theUtilidades = new Utilidades();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
	ArrayList<DevolucionDistribuidorProducto> listadoDevolucionDistribuidorProducto;

	ListView lvProductos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_distribuidorinventarioautoventa);
		
		llDistribuidorInventarioAutoventa = (LinearLayout)findViewById(R.id.DistribuidorInventarioAutoventaLinearLayout);
		lvProductos = (ListView)findViewById(R.id.lvDistribuidorInventarioAutoventaProductos);
		
		llDistribuidorInventarioAutoventa.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
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
			theUtilidades.MostrarMensaje(getApplicationContext(),"El distribuidor no se logeo correctamente.", 1);
	        return;
		}
		else
		{
			ObtenerProductosForDisplay();
		}
	}
	
	private void ObtenerProductosForDisplay()
	{
		DevolucionDistribuidor localDevolucionDistribuidor=null;
		try
		{
			localDevolucionDistribuidor = new DevolucionDistribuidorBLL().ObtenerDevolucionDistribuidorPorDistribuidor(
																						loginEmpleado.get_empleadoId());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidor por distribuidorId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidor por distribuidorId: " + localException.getMessage());
			} 
		}
		
		if(localDevolucionDistribuidor == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontro un almacen para el distribuidor.",1);
			return;
		}
		
		listadoDevolucionDistribuidorProducto = null;
		try
		{
			listadoDevolucionDistribuidorProducto = new DevolucionDistribuidorProductoBLL().ObtenerDevolucionesDistribuidorProductoPor(
					localDevolucionDistribuidor.get_almacenDevolucionId());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidorProducto por devolucionDistribuidorId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidorProducto por devolucionDistribuidorId: " + localException.getMessage());
			} 
		}
		
		if(listadoDevolucionDistribuidorProducto == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No existen productos en el almacen distribuidor.",1);
			return;
		}
		
		MostrarDevolucionesDistribuidorProductoListView();
	}
	
	private void MostrarDevolucionesDistribuidorProductoListView()
	{
		MiAdaptadorDevolucionDistribuidorProducto localDevolucionDistribuidorProducto  = new MiAdaptadorDevolucionDistribuidorProducto(getApplicationContext());
		if(listadoDevolucionDistribuidorProducto == null || listadoDevolucionDistribuidorProducto.size()<=0)
		{
			lvProductos.setAdapter(null);
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron productos en el almacen del distribuidor.", 1);
		}
		else
		{
			ViewGroup.LayoutParams localLayoutParams = lvProductos.getLayoutParams();
		    localLayoutParams.height = ((int)(40*getApplicationContext().getResources().getDisplayMetrics().density) * listadoDevolucionDistribuidorProducto.size());
		    lvProductos.setLayoutParams(localLayoutParams);
		    lvProductos.setAdapter(localDevolucionDistribuidorProducto);
		}
	}
	
	class MiAdaptadorDevolucionDistribuidorProducto extends ArrayAdapter<DevolucionDistribuidorProducto>
	{
		private Context _context;
		
		public MiAdaptadorDevolucionDistribuidorProducto(Context context)
		{
			super(context,R.layout.disenio_distribuidorinventarioautoventa, listadoDevolucionDistribuidorProducto);
			this._context = context;
		}
    
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View localView = convertView;
			
			if (localView == null) 
			{
				LayoutInflater layoutInflater = (LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				localView = layoutInflater.inflate(R.layout.disenio_distribuidorinventarioautoventa, parent, false);
			}
			
			TextView tvProducto = (TextView)localView.findViewById(R.id.tvDistribuidorInventarioAutoventaProducto);
			TextView tvCantidadPaquete = (TextView)localView.findViewById(R.id.tvDistribuidorInventarioAutoventaCantidadPaquetes);
			TextView tvCantidadUnidades = (TextView)localView.findViewById(R.id.tvDistribuidorInventarioAutoventaCantidadUnidades);
			
			DevolucionDistribuidorProducto actualDevolucionDistribuidorProducto = listadoDevolucionDistribuidorProducto.get(position);
			
			Producto localProducto = null;
			try
			{
				localProducto = new ProductoBLL().ObtenerProductoPor(actualDevolucionDistribuidorProducto.get_productoId());
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
				return null;
			}
			
			tvProducto.setText(localProducto.get_descripcion25());
			tvCantidadPaquete.setText(String.valueOf(actualDevolucionDistribuidorProducto.get_cantidadPaquete()));
			tvCantidadUnidades.setText(String.valueOf(actualDevolucionDistribuidorProducto.get_cantidad()));
			
			
			return localView;
		}
	}
}
