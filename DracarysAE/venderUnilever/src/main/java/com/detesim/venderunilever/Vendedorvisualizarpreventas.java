package com.detesim.venderunilever;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import BLL.ClientePreventaBLL;
import BLL.MyLogBLL;
import BLL.PreventaBLL;
import BLL.PreventaProductoBLL;
import BLL.ProductoBLL;
import BLL.PromocionBLL;
import Clases.ClientePreventa;
import Clases.LoginEmpleado;
import Clases.Preventa;
import Clases.PreventaProducto;
import Clases.Producto;
import Clases.Promocion;
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

public class Vendedorvisualizarpreventas extends Activity 
{
	LinearLayout llVendedorVisorPreventas;
	Utilidades theUtilidades = new Utilidades();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
	TextView tvNoPreventas;
	TextView tvMontoFinal;
	ListView lvPreventa;
	TextView tvPreventaProducto;
	ListView lvPreventaProducto;
	
	ArrayList<Preventa> listadoPreventa;
	ArrayList<PreventaProducto> listadoPreventaProducto;
	int clienteId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedorvisorpreventas);
		
		llVendedorVisorPreventas = (LinearLayout)findViewById(R.id.VendedorVisorPreventasLinearLayout);
		tvNoPreventas = (TextView)findViewById(R.id.tvVisorPreventasNoPreventas);
		tvMontoFinal = (TextView)findViewById(R.id.tvVisorPreventasMontoTotal);
		lvPreventa = (ListView)findViewById(R.id.lvVendedorVisorPreventasPreventa);
		tvPreventaProducto = (TextView)findViewById(R.id.tvVendedorVisorPreventaProducto);
		lvPreventaProducto = (ListView)findViewById(R.id.lvVendedorVisorPreventasPreventaProducto);
		
		llVendedorVisorPreventas.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
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
	    	    
	    ObtenerPreventasForDisplay();
	}
	
	private void ObtenerPreventasForDisplay()
	{
		listadoPreventa = null;
	    try
	    {
	    	listadoPreventa = new PreventaBLL().ObtenerPreventas();
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
	    }
	    
	    if(listadoPreventa == null) 
	    {
	    	MostrarControles(false);
	    	tvNoPreventas.setText("0");
	    	tvMontoFinal.setText("0.00");
	    	theUtilidades.MostrarMensaje(getApplicationContext(),"No existen preventas que visualizar.", 1);
	    	return;
	    }
	    else
	    {
	    	MostrarControles(true);
	    	tvPreventaProducto.setVisibility(View.INVISIBLE);
	    	LlenarPreventaListView();
		    PreventasItemOnClick();	   
		    CalcularTotales();
	    }
	}
	
	private void CalcularTotales()
	{
		double total = 0;
		tvNoPreventas.setText(String.valueOf(listadoPreventa.size()));
		for(Preventa item : listadoPreventa )
		{
			total = total + item.get_montoFinal();
		}
		tvMontoFinal.setText(String.valueOf(new BigDecimal(total).setScale(2,RoundingMode.HALF_UP)));
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
		
		lvPreventa.setVisibility(visibility);
		tvPreventaProducto.setVisibility(visibility);
		lvPreventaProducto.setVisibility(visibility);
	}
	
	private void LlenarPreventaListView()
	{
		MiAdaptadorPreventa localMiAdaptadorPreventa = new MiAdaptadorPreventa();
		ListView localListView = lvPreventa;
		if(listadoPreventa == null)
		{
			localListView.setAdapter(null);
		}
		else
		{
			ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    	localLayoutParams.height = ((int)(30*getApplicationContext().getResources().getDisplayMetrics().density) * listadoPreventa.size());
	    	localListView.setLayoutParams(localLayoutParams);
			localListView.setAdapter(localMiAdaptadorPreventa);
		}
	}
	
	class MiAdaptadorPreventa extends ArrayAdapter<Preventa>
	{
		public MiAdaptadorPreventa()
		{
			super(getApplicationContext(),R.layout.disenio_vendedorsincronizacionpreventa,listadoPreventa);
		}
    
		public View getView(int position, View view, ViewGroup viewGroup)
		{
			View localView = view;
			
			if (localView == null) 
			{
				localView = getLayoutInflater().inflate(R.layout.disenio_vendedorsincronizacionpreventa, viewGroup, false);
			}
			
			TextView tvCliente = (TextView)localView.findViewById(R.id.tvVendedorSincronizacionPreventaNombre);
			
			Preventa localpreventa = null;
			
			if(listadoPreventa.size() > 0)
			{
				localpreventa = (Preventa)listadoPreventa.get(position);
			}
      
			ClientePreventa clientePreventa=null;
			try
			{
				clientePreventa = new ClientePreventaBLL().ObtenerClientePreventaPor(localpreventa.get_clienteId());
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
			
			if(localpreventa.is_estado())
			{
				tvCliente.setText(clientePreventa.get_nombreCompleto() + " - " + String.valueOf(new BigDecimal(localpreventa.get_monto()).setScale(2,RoundingMode.HALF_UP)) 
									+ " - " + String.valueOf(new BigDecimal(localpreventa.get_descuento() + localpreventa.get_descuentoIncentivo()).setScale(2,RoundingMode.HALF_UP))
									+ " - " + String.valueOf(new BigDecimal(localpreventa.get_montoFinal()).setScale(2,RoundingMode.HALF_UP)));
			}
			else
			{				
				tvCliente.setText(clientePreventa.get_nombreCompleto() + " - " + String.valueOf(new BigDecimal(localpreventa.get_monto()).setScale(2,RoundingMode.HALF_UP))
									+ " - " + String.valueOf(new BigDecimal(localpreventa.get_descuento() + localpreventa.get_descuentoIncentivo()).setScale(2,RoundingMode.HALF_UP))
									+ " - " + String.valueOf(new BigDecimal(localpreventa.get_montoFinal()).setScale(2,RoundingMode.HALF_UP)));
				tvCliente.setTextAppearance(getApplicationContext(), R.style.Contenido);
			}
			
			return localView;
		}
	}
	
	private void PreventasItemOnClick()
	{
	    ((ListView)findViewById(R.id.lvVendedorVisorPreventasPreventa)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
	    	Preventa localPreventa = null;
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	    	{
	    		localPreventa = ((Preventa)listadoPreventa.get(position));
	    		
	    		clienteId = localPreventa.get_clienteId();
	    		ObtenerPreventaProductoForDisplay(localPreventa.get_Id());
	        }
	    });
	}
	
	private void ObtenerPreventaProductoForDisplay(int preventaId)
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
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la preventa por preventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la preventa por preventaId: " + localException.getMessage());
	    	}
	    }
	    
	    if (listadoPreventaProducto == null) 
	    {
	    	tvPreventaProducto.setVisibility(View.INVISIBLE);
	    	lvPreventaProducto.setVisibility(View.INVISIBLE);
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "La preventa seleccionada no tiene productos asociados.", 1);
	    	return;
	    }  
	    else
	    {
	    	tvPreventaProducto.setVisibility(View.VISIBLE);
	    	LlenarPreventaProductoListView();
	    }
	}
	
	private void LlenarPreventaProductoListView()
	{
	    MiAdaptadorPreventaProducto localMiAdaptadorPreventaProducto = new MiAdaptadorPreventaProducto();
	    ListView localListView = lvPreventaProducto;
	    if(listadoPreventaProducto == null)
	    {
	    	localListView.setAdapter(null);
	    }
	    else
	    {
	    	ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    	localLayoutParams.height = ((int)(30*getApplicationContext().getResources().getDisplayMetrics().density) * listadoPreventaProducto.size());
	    	localListView.setLayoutParams(localLayoutParams);
	    	localListView.setAdapter(localMiAdaptadorPreventaProducto);
	    }
	}
	
	class MiAdaptadorPreventaProducto extends ArrayAdapter<PreventaProducto>
	{
		public MiAdaptadorPreventaProducto()
		{
			super(getApplicationContext(), R.layout.disenio_vendedorsincronizacionpreventa,listadoPreventaProducto);
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
			
			PreventaProducto localPreventaProducto = null;
			
			TextView itemProducto = (TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoProducto);
			TextView itemUnidad = (TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoUnidad);
			TextView itemPaquete = (TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoPaquete);
			TextView itemPrecio = (TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoMontoFinal);
			
			if(listadoPreventaProducto.size() > 0)
			{
				localPreventaProducto = ((PreventaProducto)listadoPreventaProducto.get(position));
				
				if(localPreventaProducto.get_productoId() != 0)
				{
					localProducto = null;
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
					
					if(localProducto==null)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del producto.", 1);
						return null;
					}
					
					if(localPreventaProducto.is_estado())
					{
						itemProducto.setText(localProducto.get_descripcion25());
						itemUnidad.setText(String.valueOf(localPreventaProducto.get_cantidad()));
						itemPaquete.setText(String.valueOf(localPreventaProducto.get_cantidadPaquete()));
						itemPrecio.setText(String.valueOf(new BigDecimal(localPreventaProducto.get_montoFinal()).setScale(2,RoundingMode.HALF_UP)));
					}
					else
					{
						itemProducto.setText(localProducto.get_descripcion25());
						itemProducto.setTextAppearance(getApplicationContext(), R.style.Contenido);
						itemUnidad.setText(String.valueOf(localPreventaProducto.get_cantidad()));
						itemUnidad.setTextAppearance(getApplicationContext(), R.style.Contenido);
						itemPaquete.setText(String.valueOf(localPreventaProducto.get_cantidadPaquete()));
						itemPaquete.setTextAppearance(getApplicationContext(), R.style.Contenido);
						itemPrecio.setText(String.valueOf(new BigDecimal(localPreventaProducto.get_montoFinal()).setScale(2,RoundingMode.HALF_UP)));
						itemPrecio.setTextAppearance(getApplicationContext(), R.style.Contenido);
					}					
				}
				
				if(localPreventaProducto.get_promocionId()!=0)
				{
					localPromocion = null;
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
					}
					
					if (localPromocion == null)
			        {
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles de la promocion.", 1);
						return null;
			        }
					
					if(localPreventaProducto.is_estado())
					{
						itemProducto.setText(localPromocion.get_descripcion());
						itemUnidad.setText(String.valueOf(localPreventaProducto.get_cantidad()));
						itemPaquete.setText(String.valueOf(localPreventaProducto.get_cantidadPaquete()));
						itemPrecio.setText(String.valueOf(new BigDecimal(localPreventaProducto.get_montoFinal()).setScale(2,RoundingMode.HALF_UP)));
					}
					else
					{
						itemProducto.setText(localPromocion.get_descripcion());
						itemProducto.setTextAppearance(getApplicationContext(), R.style.Contenido);
						itemUnidad.setText(String.valueOf(localPreventaProducto.get_cantidad()));
						itemUnidad.setTextAppearance(getApplicationContext(), R.style.Contenido);
						itemPaquete.setText(String.valueOf(localPreventaProducto.get_cantidadPaquete()));
						itemPaquete.setTextAppearance(getApplicationContext(), R.style.Contenido);
						itemPrecio.setText(String.valueOf(new BigDecimal(localPreventaProducto.get_montoFinal()).setScale(2,RoundingMode.HALF_UP)));
						itemPrecio.setTextAppearance(getApplicationContext(), R.style.Contenido);
					}
				}
			}
			
			return localView;
		}	
	}
}
