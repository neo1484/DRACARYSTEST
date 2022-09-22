package com.detesim.venderunilever;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import BLL.ClientePreventaBLL;
import BLL.MyLogBLL;
import BLL.ProductoBLL;
import BLL.PromocionBLL;
import BLL.VentaDirectaBLL;
import BLL.VentaDirectaProductoBLL;
import Clases.ClientePreventa;
import Clases.LoginEmpleado;
import Clases.Producto;
import Clases.Promocion;
import Clases.VentaDirecta;
import Clases.VentaDirectaProducto;
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


public class Vendedorvisorventasdirectas extends Activity 
{
	LinearLayout llVisorVentasDirectas;
	Utilidades theUtilidades = new Utilidades();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
	TextView tvNoVentas;
	TextView tvMontoTotal;
	ListView lvVentas;
	TextView tvProductos;
	ListView lvVentasProducto;
	
	ArrayList<VentaDirecta> listadoVentas;
	ArrayList<VentaDirectaProducto> listadoVentasProducto;
	int clienteId;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedorvisorventasdirectas);
		
		llVisorVentasDirectas = (LinearLayout)findViewById(R.id.llVisorVentasDirectas);
		tvNoVentas = (TextView)findViewById(R.id.tvVisorVentaDirectaNumeroVentas);
		tvMontoTotal = (TextView)findViewById(R.id.tvVisorVentasDirectasMontoTotal);
		lvVentas = (ListView)findViewById(R.id.lvVisorVentasDirectasVentas);
		tvProductos = (TextView)findViewById(R.id.tvVisorVentasDirectasProductos);
		lvVentasProducto = (ListView)findViewById(R.id.lvVisorVentasDirectasVentaProducto);
		
		llVisorVentasDirectas.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
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
	    	    
	    ObtenerVentasForDisplay();
	}
	
	private void ObtenerVentasForDisplay()
	{
		listadoVentas = null;
	    try
	    {
	    	listadoVentas = new VentaDirectaBLL().ObtenerVentasDirectas();
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
	    }
	    
	    if(listadoVentas == null) 
	    {
	    	MostrarControles(false);
	    	tvNoVentas.setText("0");
	    	tvMontoTotal.setText("0.00");
	    	theUtilidades.MostrarMensaje(getApplicationContext(),"No existen preventas que visualizar.", 1);
	    	return;
	    }
	    else
	    {
	    	MostrarControles(true);
	    	tvProductos.setVisibility(View.INVISIBLE);
	    	LlenarVentasListView();
		    VentasItemOnClick();	 
		    CalcularTotales();
	    }
	}
	
	private void CalcularTotales()
	{
		double total = 0;
		tvNoVentas.setText(String.valueOf(listadoVentas.size()));
		for(VentaDirecta item : listadoVentas )
		{
			total = total + item.get_montoFinal();
		}
		tvMontoTotal.setText(String.valueOf(new BigDecimal(total).setScale(2,RoundingMode.HALF_UP)));
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
		
		lvVentas.setVisibility(visibility);
		tvProductos.setVisibility(visibility);
		lvVentasProducto.setVisibility(visibility);
	}
	
	private void LlenarVentasListView()
	{
		MiAdaptadorVentas localMiAdaptadorVentas = new MiAdaptadorVentas();
		ListView localListView = lvVentas;
		if(listadoVentas == null)
		{
			localListView.setAdapter(null);
		}
		else
		{
			ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    	localLayoutParams.height = ((int)(30*getApplicationContext().getResources().getDisplayMetrics().density) * listadoVentas.size());
	    	localListView.setLayoutParams(localLayoutParams);
			localListView.setAdapter(localMiAdaptadorVentas);
		}
	}
	
	class MiAdaptadorVentas extends ArrayAdapter<VentaDirecta>
	{
		public MiAdaptadorVentas()
		{
			super(getApplicationContext(),R.layout.disenio_vendedorsincronizacionpreventa,listadoVentas);
		}
    
		public View getView(int position, View view, ViewGroup viewGroup)
		{
			View localView = view;
			
			if (localView == null) 
			{
				localView = getLayoutInflater().inflate(R.layout.disenio_vendedorsincronizacionpreventa, viewGroup, false);
			}
			
			TextView tvCliente = (TextView)localView.findViewById(R.id.tvVendedorSincronizacionPreventaNombre);
			
			VentaDirecta localVentaDirecta = null;
			
			if(listadoVentas.size() > 0)
			{
				localVentaDirecta = (VentaDirecta)listadoVentas.get(position);
			}
      
			ClientePreventa clientePreventa=null;
			try
			{
				clientePreventa = new ClientePreventaBLL().ObtenerClientePreventaPor(localVentaDirecta.get_clienteId());
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
			
			if(clientePreventa == null)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo obtener los detalles del cliente.", 1);
		        return null;    
			}
			
			((ImageView)localView.findViewById(R.id.imgVendedorSincronizacionPreventaBullet)).setImageResource(R.drawable.bullet_seleccionar);
			
			if(localVentaDirecta.is_estado())
			{
				tvCliente.setText(clientePreventa.get_nombreCompleto() + " - " + String.valueOf(new BigDecimal(localVentaDirecta.get_monto()).setScale(2,RoundingMode.HALF_UP))
						+ " - " + String.valueOf(new BigDecimal(localVentaDirecta.get_descuento() + localVentaDirecta.get_descuentoIncentivo()).setScale(2,RoundingMode.HALF_UP))
						+ " - " + String.valueOf(new BigDecimal(localVentaDirecta.get_montoFinal()).setScale(2,RoundingMode.HALF_UP)));
			}
			else
			{				
				tvCliente.setText(clientePreventa.get_nombreCompleto() + " - " + String.valueOf(new BigDecimal(localVentaDirecta.get_monto()).setScale(2,RoundingMode.HALF_UP))
						+ " - " + String.valueOf(new BigDecimal(localVentaDirecta.get_descuento() + localVentaDirecta.get_descuentoIncentivo()).setScale(2,RoundingMode.HALF_UP))
						+ " - " + String.valueOf(new BigDecimal(localVentaDirecta.get_montoFinal()).setScale(2,RoundingMode.HALF_UP)));
				tvCliente.setTextAppearance(getApplicationContext(), R.style.Contenido);
			}
			
			return localView;
		}
	}
	
	private void VentasItemOnClick()
	{
	    ((ListView)findViewById(R.id.lvVisorVentasDirectasVentas)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
	    	VentaDirecta localVentaDirecta = null;
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	    	{
	    		localVentaDirecta = ((VentaDirecta)listadoVentas.get(position));
	    		
	    		clienteId = localVentaDirecta.get_clienteId();
	    		ObtenerVentasProductoForDisplay(localVentaDirecta.get_noVentaDirecta());
	        }
	    });
	}
	
	private void ObtenerVentasProductoForDisplay(int noVentaDirecta)
	{
		listadoVentasProducto = null;
	    try
	    {
	    	listadoVentasProducto = new VentaDirectaProductoBLL().ObtenerVentasDirectasProductoPorNoVentaDirecta(noVentaDirecta);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta directa por ventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta directa por ventaId: " + localException.getMessage());
	    	}
	    }
	    
	    if (listadoVentasProducto == null) 
	    {
	    	tvProductos.setVisibility(View.INVISIBLE);
	    	lvVentasProducto.setVisibility(View.INVISIBLE);
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "La venta seleccionada no tiene productos asociados.", 1);
	    	return;
	    }  
	    else
	    {
	    	tvProductos.setVisibility(View.VISIBLE);
	    	LlenarVentasProductoListView();
	    }
	}
	
	private void LlenarVentasProductoListView()
	{
	    MiAdaptadorVentasProducto localMiAdaptadorVentasProducto = new MiAdaptadorVentasProducto();
	    ListView localListView = lvVentasProducto;
	    if(listadoVentasProducto == null)
	    {
	    	localListView.setAdapter(null);
	    }
	    else
	    {
	    	ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    	localLayoutParams.height = ((int)(30*getApplicationContext().getResources().getDisplayMetrics().density) * listadoVentasProducto.size());
	    	localListView.setLayoutParams(localLayoutParams);
	    	localListView.setAdapter(localMiAdaptadorVentasProducto);
	    }
	}
	
	class MiAdaptadorVentasProducto extends ArrayAdapter<VentaDirectaProducto>
	{
		public MiAdaptadorVentasProducto()
		{
			super(getApplicationContext(), R.layout.disenio_vendedorsincronizacionpreventa,listadoVentasProducto);
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
			
			VentaDirectaProducto localVentasProducto = null;
			
			TextView itemProducto = (TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoProducto);
			TextView itemUnidad = (TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoUnidad);
			TextView itemPaquete = (TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoPaquete);
			TextView itemPrecio = (TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoMontoFinal);
			
			if(listadoVentasProducto.size() > 0)
			{
				localVentasProducto = ((VentaDirectaProducto)listadoVentasProducto.get(position));
				
				if(localVentasProducto.get_productoId() != 0)
				{
					localProducto = null;
					try
					{
						localProducto = new ProductoBLL().ObtenerProductoPor(localVentasProducto.get_productoId());
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
					
					
					itemProducto.setText(localProducto.get_descripcion25());
					itemUnidad.setText(String.valueOf(localVentasProducto.get_cantidad()));
					itemPaquete.setText(String.valueOf(localVentasProducto.get_cantidadPaquete()));
					itemPrecio.setText(String.valueOf(new BigDecimal(localVentasProducto.get_montoFinal()).setScale(2,RoundingMode.HALF_UP)));
					
				}
				
				if(localVentasProducto.get_promocionId()!=0)
				{
					localPromocion = null;
					try
					{
						localPromocion = new PromocionBLL().ObtenerPromocionPor(localVentasProducto.get_promocionId());
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
					
					itemProducto.setText(localPromocion.get_descripcion());
					itemUnidad.setText(String.valueOf(localVentasProducto.get_cantidad()));
					itemPaquete.setText(String.valueOf(localVentasProducto.get_cantidadPaquete()));
					itemPrecio.setText(String.valueOf(new BigDecimal(localVentasProducto.get_montoFinal()).setScale(2,RoundingMode.HALF_UP)));
					
				}
			}
			
			return localView;
		}	
	}
}
