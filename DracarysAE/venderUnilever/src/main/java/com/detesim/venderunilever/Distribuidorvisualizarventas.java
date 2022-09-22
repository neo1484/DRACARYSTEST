package com.detesim.venderunilever;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import BLL.ClienteVentaBLL;
import BLL.MyLogBLL;
import BLL.ProductoBLL;
import BLL.PromocionBLL;
import BLL.VentaBLL;
import BLL.VentaProductoBLL;
import Clases.ClienteVenta;
import Clases.LoginEmpleado;
import Clases.Producto;
import Clases.Promocion;
import Clases.Venta;
import Clases.VentaProducto;
import Utilidades.Utilidades;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Distribuidorvisualizarventas extends Activity 
{
	LinearLayout llDistribuidorVisualizadorVentas;
	Utilidades theUtilidades = new Utilidades();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
	ListView lvVenta;
	TextView tvVentaProducto;
	ListView lvVentaProducto;
	
	ArrayList<Venta> listadoVenta;
	ArrayList<VentaProducto> listadoVentaProducto;
	int clienteId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_distribuidorvisualizarventas);
		
		llDistribuidorVisualizadorVentas = (LinearLayout)findViewById(R.id.DistribuidorVisualizarVentasLinearLayout);
		lvVenta = (ListView)findViewById(R.id.lvDistribuidorVisualizadorVentasVentas);
		tvVentaProducto = (TextView)findViewById(R.id.tvDistribuidorVisualizadorVentasVentaProducto);
		lvVentaProducto = (ListView)findViewById(R.id.lvDistribuidorVisorVentasVentaProducto);
		
		llDistribuidorVisualizadorVentas.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
		loginEmpleado = null;	    
	    try
	    {
	    	loginEmpleado = theUtilidades.ObtenerLoginEmpleado();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener loginEmpleado distribuidor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener loginEmpleado distribuidor: " + localException.getMessage());
	    	} 
	    	return;
	    }
	      
	    if(loginEmpleado == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El usuario no se logeo correctamente.", 1);
	        return;
	    }
	    	    
	    ObtenerVentasForDisplay();
	}
	
	private void ObtenerVentasForDisplay()
	{
		listadoVenta = null;
	    try
	    {
	    	listadoVenta = new VentaBLL().ObtenerVentas();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas: " + localException.getMessage());
	    	} 
	    }
	    
	    if(listadoVenta == null) 
	    {
	    	MostrarControles(false);
	    	theUtilidades.MostrarMensaje(getApplicationContext(),"No existen ventas que visualizar.", 1);
	    	return;
	    }
	    else
	    {
	    	MostrarControles(true);
	    	tvVentaProducto.setVisibility(View.INVISIBLE);
	    	LlenarVentaListView();
		    VentasItemOnClick();	    	
	    }
	}
	
	private void MostrarControles(boolean estado)
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
		
		lvVenta.setVisibility(visibility);
		tvVentaProducto.setVisibility(visibility);
		lvVentaProducto.setVisibility(visibility);
	}

	private void LlenarVentaListView()
	{
		MiAdaptadorVenta localMiAdaptadorVenta = new MiAdaptadorVenta();
		ListView localListView = lvVenta;
		if(listadoVenta == null)
		{
			localListView.setAdapter(null);
		}
		else
		{
			ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    	localLayoutParams.height = ((int)(30*getApplicationContext().getResources().getDisplayMetrics().density) * listadoVenta.size());
	    	localListView.setLayoutParams(localLayoutParams);
			localListView.setAdapter(localMiAdaptadorVenta);
		}
	}
	
	class MiAdaptadorVenta extends ArrayAdapter<Venta>
	{
		public MiAdaptadorVenta()
		{
			super(getApplicationContext(),R.layout.disenio_vendedorsincronizacionpreventa,listadoVenta);
		}
    
		public View getView(int position, View view, ViewGroup viewGroup)
		{
			View localView = view;
			
			if (localView == null) 
			{
				localView = getLayoutInflater().inflate(R.layout.disenio_vendedorsincronizacionpreventa, viewGroup, false);
			}
			
			TextView tvCliente = (TextView)localView.findViewById(R.id.tvVendedorSincronizacionPreventaNombre);
			
			Venta localVenta = null;
			
			if(listadoVenta.size() > 0)
			{
				localVenta = (Venta)listadoVenta.get(position);
			}
      
			ClienteVenta clienteVenta=null;
			try
			{
				clienteVenta = new ClienteVentaBLL().ObtenerClienteVentaPor(localVenta.get_clienteId());
			}
			catch (Exception localException)
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
			
			if(clienteVenta == null)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo obtener los detalles del cliente.", 1);
		        return null;    
			}
			
			((ImageView)localView.findViewById(R.id.imgVendedorSincronizacionPreventaBullet)).setImageResource(R.drawable.bullet_seleccionar);
			
			String tipoVenta = "";
			if(localVenta.get_preventaId()>0)
			{
				tipoVenta = "Venta";
			}
			else
			{
				tipoVenta = "Autoventa";
			}
			
			if(localVenta.is_estadoSincronizacion())
			{
				tvCliente.setText(clienteVenta.get_nombreCompleto() + " - " + String.valueOf(new BigDecimal(localVenta.get_montoFinal()).setScale(2,RoundingMode.HALF_UP)) + " - " + tipoVenta);
			}
			else
			{				
				tvCliente.setText(clienteVenta.get_nombreCompleto() + " - " + String.valueOf(new BigDecimal(localVenta.get_montoFinal()).setScale(2,RoundingMode.HALF_UP)) + " - " + tipoVenta);
				tvCliente.setTextAppearance(getApplicationContext(), R.style.Contenido);
			}
			
			return localView;
		}
	}

	private void VentasItemOnClick()
	{
	    ((ListView)findViewById(R.id.lvDistribuidorVisualizadorVentasVentas)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
	    	Venta localVenta = null;
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	    	{
	    		localVenta = ((Venta)listadoVenta.get(position));
	    		
	    		clienteId = localVenta.get_clienteId();
	    		ObtenerVentaProductoForDisplay(localVenta.get_id());
	        }
	    });
	}
	
	private void ObtenerVentaProductoForDisplay(int ventaId)
	{
		listadoVentaProducto = null;
	    try
	    {
	    	listadoVentaProducto = new VentaProductoBLL().ObtenerVentasProductoPor(ventaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta por ventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta por ventaId: " + localException.getMessage());
	    	}
	    }
	    
	    if (listadoVentaProducto == null) 
	    {
	    	tvVentaProducto.setVisibility(View.INVISIBLE);
	    	lvVentaProducto.setVisibility(View.INVISIBLE);
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "La venta seleccionada no tiene productos asociados.", 1);
	    	return;
	    }  
	    else
	    {
	    	tvVentaProducto.setVisibility(View.VISIBLE);
	    	LlenarVentaProductoListView();
	    }
	}
	
	private void LlenarVentaProductoListView()
	{
	    MiAdaptadorVentaProducto localMiAdaptadorVentaProducto = new MiAdaptadorVentaProducto();
	    ListView localListView = lvVentaProducto;
	    if(listadoVentaProducto == null)
	    {
	    	localListView.setAdapter(null);
	    }
	    else
	    {
	    	ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    	localLayoutParams.height = (45 * listadoVentaProducto.size());
	    	localListView.setLayoutParams(localLayoutParams);
	    	localListView.setAdapter(localMiAdaptadorVentaProducto);
	    }
	}

	class MiAdaptadorVentaProducto extends ArrayAdapter<VentaProducto>
	{
		public MiAdaptadorVentaProducto()
		{
			super(getApplicationContext(), R.layout.disenio_vendedorsincronizacionpreventa,listadoVentaProducto);
		}
    
		public View getView(int position, View view, ViewGroup viewGroup)
		{
			View localView = view;
			
			if (localView == null) 
			{
				localView = getLayoutInflater().inflate(R.layout.disenio_vendedorsincronizacionpreventapreventaproducto, viewGroup, false);
			}
		
			Producto localProducto;
			Promocion localPromocion;
			
			VentaProducto localVentaProducto = null;
			
			TextView itemProducto = (TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoProducto);
			TextView itemUnidad = (TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoUnidad);
			TextView itemPaquete = (TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoPaquete);
			TextView itemPrecio = (TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoMontoFinal);
			
			if(listadoVentaProducto.size() > 0)
			{
				localVentaProducto = ((VentaProducto)listadoVentaProducto.get(position));
				
				if(localVentaProducto.get_productoId() != 0)
				{
					localProducto = null;
					try
					{
						localProducto = new ProductoBLL().ObtenerProductoPor(localVentaProducto.get_productoId());
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
					
					if(localProducto==null)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del producto.", 1);
						return null;
					}
					
					if(localVentaProducto.is_estadoSincronizacion())
					{
						itemProducto.setText(localProducto.get_descripcion25());
						itemUnidad.setText(String.valueOf(localVentaProducto.get_cantidad()));
						itemPaquete.setText(String.valueOf(localVentaProducto.get_cantidadPaquete()));
						itemPrecio.setText(String.valueOf(new BigDecimal(localVentaProducto.get_montoFinal()).setScale(2,RoundingMode.HALF_UP)));
					}
					else
					{
						itemProducto.setText(localProducto.get_descripcion25());
						itemProducto.setTextAppearance(getApplicationContext(), R.style.Contenido);
						itemUnidad.setText(String.valueOf(localVentaProducto.get_cantidad()));
						itemUnidad.setTextAppearance(getApplicationContext(), R.style.Contenido);
						itemPaquete.setText(String.valueOf(localVentaProducto.get_cantidadPaquete()));
						itemPaquete.setTextAppearance(getApplicationContext(), R.style.Contenido);
						itemPrecio.setText(String.valueOf(new BigDecimal(localVentaProducto.get_montoFinal()).setScale(2,RoundingMode.HALF_UP)));
						itemPrecio.setTextAppearance(getApplicationContext(), R.style.Contenido);
					}					
				}
				
				if(localVentaProducto.get_promocionId()!=0)
				{
					localPromocion = null;
					try
					{
						localPromocion = new PromocionBLL().ObtenerPromocionPor(localVentaProducto.get_promocionId());
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
					}
					
					if (localPromocion == null)
			        {
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles de la promocion.", 1);
						return null;
			        }
					
					if(localVentaProducto.is_estadoSincronizacion())
					{
						itemProducto.setText(localPromocion.get_descripcion());
						itemUnidad.setText(String.valueOf(localVentaProducto.get_cantidad()));
						itemPaquete.setText(String.valueOf(localVentaProducto.get_cantidadPaquete()));
						itemPrecio.setText(String.valueOf(new BigDecimal(localVentaProducto.get_montoFinal()).setScale(2,RoundingMode.HALF_UP)));
					}
					else
					{
						itemProducto.setText(localPromocion.get_descripcion());
						itemProducto.setTextAppearance(getApplicationContext(), R.style.Contenido);
						itemUnidad.setText(String.valueOf(localVentaProducto.get_cantidad()));
						itemUnidad.setTextAppearance(getApplicationContext(), R.style.Contenido);
						itemPaquete.setText(String.valueOf(localVentaProducto.get_cantidadPaquete()));
						itemPaquete.setTextAppearance(getApplicationContext(), R.style.Contenido);
						itemPrecio.setText(String.valueOf(new BigDecimal(localVentaProducto.get_montoFinal()).setScale(2,RoundingMode.HALF_UP)));
						itemPrecio.setTextAppearance(getApplicationContext(), R.style.Contenido);
					}
				}
			}
			
			return localView;
		}	
	}
}
