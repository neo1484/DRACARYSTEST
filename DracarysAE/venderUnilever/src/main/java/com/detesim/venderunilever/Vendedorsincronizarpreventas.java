package com.detesim.venderunilever;

import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import BLL.ClientePreventaBLL;
import BLL.MyLogBLL;
import BLL.PrecioListaBLL;
import BLL.PreventaBLL;
import BLL.PreventaProductoBLL;
import BLL.PreventaProductoCambioBLL;
import BLL.PreventaProductoPOPBLL;
import BLL.ProductoBLL;
import BLL.PromocionBLL;
import BLL.PromocionProductoBLL;
import Clases.ClientePreventa;
import Clases.LoginEmpleado;
import Clases.PrecioLista;
import Clases.Preventa;
import Clases.PreventaProducto;
import Clases.PreventaProductoCambio;
import Clases.PreventaProductoPOP;
import Clases.Producto;
import Clases.Promocion;
import Clases.PromocionProducto;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Vendedorsincronizarpreventas extends Activity implements OnClickListener
{
	LinearLayout llVendedorSincronizarPreventas;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
	ListView lvPreventa;
	ListView lvPreventaProducto;
	TextView tvPreventa;
	TextView tvPreventaProducto;
	Button btnSincronizarPreventa;
	ProgressDialog pdInsertarPreventa;
	ProgressDialog pdInsertarPreventaProducto;
	Dialog dialog;
	  
	ArrayList<Preventa> listadoPreventaNoSincronizadas;
	ArrayList<PreventaProducto> listadoPreventaProductoNoSincronizadas;
	int totalPreventasProducto;
	int clienteId;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedorsincronizarpreventas);
		
		llVendedorSincronizarPreventas = (LinearLayout)findViewById(R.id.VendedorSincronizarPreventasLinearLayout);
		tvPreventa = (TextView)findViewById(R.id.tvSincronizacionPreventaPreventa);
		tvPreventaProducto = (TextView)findViewById(R.id.tvSincronizacionPreventaPreventaProducto);
		lvPreventa = (ListView)findViewById(R.id.lvSincronizacionPreventaPreventas);
	    lvPreventaProducto = (ListView)findViewById(R.id.lvSincronizacionPreventaPreventaProductos);
	    btnSincronizarPreventa = (Button)findViewById(R.id.btnSincronizacionPreventaSincronizarPreventa);
	    btnSincronizarPreventa.setOnClickListener(this);
	    
	    llVendedorSincronizarPreventas.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
	    
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
	    
	    MostrarControlesPreventa(false);
	    MostrarControlesPeventaProducto(false);
	    
	    ObtenerPreventasNoSincronizadasForDisplay();
	}

	@Override
	public void onClick(View v) 
	{		
		switch(v.getId())
		{
		case R.id.btnSincronizacionPreventaSincronizarPreventa:
			SincronizarPreventaProducto();
			break;
		}
	}

	private void MostrarControlesPreventa(boolean estado)
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
	} 
	
	private void MostrarControlesPeventaProducto(boolean estado)
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
		
		tvPreventaProducto.setVisibility(visibility);
		lvPreventaProducto.setVisibility(visibility);
		btnSincronizarPreventa.setVisibility(visibility);		
	} 
	
	private void ObtenerPreventasNoSincronizadasForDisplay()
	{
		listadoPreventaNoSincronizadas = null;
	    try
	    {
	    	listadoPreventaNoSincronizadas = new PreventaBLL().ObtenerPreventasNoSincronizadas();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener preventas no sincronizadas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener preventas no sincronizadas: " + localException.getMessage());
	    	} 
	    }
	    
	    if(listadoPreventaNoSincronizadas == null) 
	    {
	    	MostrarControlesPreventa(false);
	    	MostrarControlesPeventaProducto(false);
	    	theUtilidades.MostrarMensaje(getApplicationContext(),"No existen preventas que sincronizar.", 1);
	    	return;
	    }
	    else
	    {
	    	MostrarControlesPeventaProducto(false);
	    	MostrarControlesPreventa(true);
	    	LlenarPreventaListView();
		    PreventasItemOnClick();	    	
	    }
	}
		
	private void LlenarPreventaListView()
	{
		MiAdaptadorPreventa localMiAdaptadorPreventa = new MiAdaptadorPreventa();
		ListView localListView = lvPreventa;
		if(listadoPreventaNoSincronizadas == null)
		{
			localListView.setAdapter(null);
		}
		else
		{
			ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    	localLayoutParams.height = ((int)(30*getApplicationContext().getResources().getDisplayMetrics().density) * listadoPreventaNoSincronizadas.size());
	    	localListView.setLayoutParams(localLayoutParams);
			localListView.setAdapter(localMiAdaptadorPreventa);
		}
	}
	
	class MiAdaptadorPreventa extends ArrayAdapter<Preventa>
	{
		public MiAdaptadorPreventa()
		{
			super(getApplicationContext(),R.layout.disenio_vendedorsincronizacionpreventa,listadoPreventaNoSincronizadas);
		}
    
		public View getView(int position, View view, ViewGroup viewGroup)
		{
			View localView = view;
			
			if (localView == null) 
			{
				localView = getLayoutInflater().inflate(R.layout.disenio_vendedorsincronizacionpreventa, viewGroup, false);
			}
			
			Preventa localpreventa = null;
			
			if(listadoPreventaNoSincronizadas.size() > 0)
			{
				localpreventa = (Preventa)listadoPreventaNoSincronizadas.get(position);
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
			((TextView)localView.findViewById(R.id.tvVendedorSincronizacionPreventaNombre)).setText(clientePreventa.get_nombreCompleto());
        
			return localView;
		}
	}

	private void PreventasItemOnClick()
	{
	    ((ListView)findViewById(R.id.lvSincronizacionPreventaPreventas)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
	    	Preventa localPreventa = null;
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	    	{
	    		localPreventa = ((Preventa)listadoPreventaNoSincronizadas.get(position));
	    		
	    		if(localPreventa.get_clienteId()<0)
	    		{
	    			clienteId = localPreventa.get_clienteId();
	    			theUtilidades.MostrarMensaje(getApplicationContext(), "Antes de sincronizar esta preventa debe sincronizar el cliente.", 1);
	    			btnSincronizarPreventa.setVisibility(View.INVISIBLE);
	    		}
	    		else
	    		{
	    			clienteId = localPreventa.get_clienteId();
	    			ObtenerPreventasProductoNoSincronizadasForDisplay(localPreventa.get_Id());
	    		}
	        }
	    });
	}
	
	private boolean ObtenerPreventasProductoNoSincronizadas(int preventaId)
	{
		listadoPreventaProductoNoSincronizadas = null;
	    try
	    {
	    	listadoPreventaProductoNoSincronizadas = new PreventaProductoBLL().ObtenerPreventasProductoNoSincronizadasPor(preventaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la preventa no sincronizada por preventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la preventa no sincronizada por preventaId: " + localException.getMessage());
	    	}
	    }
	    
	    if (listadoPreventaProductoNoSincronizadas == null) 
	    {
 	        return false;
	    }  
	    else
	    {
	    	return true;
	    }
	}
	
	private void LlenarPreventaProductoListView()
	{
	    MiAdaptadorPreventaProducto localMiAdaptadorPreventaProducto = new MiAdaptadorPreventaProducto();
	    ListView localListView = lvPreventaProducto;
	    if(listadoPreventaProductoNoSincronizadas == null)
	    {
	    	localListView.setAdapter(null);
	    }
	    else
	    {
	    	ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    	localLayoutParams.height = ((int)(50*getApplicationContext().getResources().getDisplayMetrics().density) * listadoPreventaProductoNoSincronizadas.size());
	    	localListView.setLayoutParams(localLayoutParams);
	    	localListView.setAdapter(localMiAdaptadorPreventaProducto);
	    }
	}
	
	class MiAdaptadorPreventaProducto extends ArrayAdapter<PreventaProducto>
	{
		public MiAdaptadorPreventaProducto()
		{
			super(getApplicationContext(), R.layout.disenio_vendedorsincronizacionpreventa,listadoPreventaProductoNoSincronizadas);
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
			
			if(listadoPreventaProductoNoSincronizadas.size() > 0)
			{
				localPreventaProducto = ((PreventaProducto)listadoPreventaProductoNoSincronizadas.get(position));
				
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
					
					((TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoProducto)).setText(localProducto.get_descripcion25());
					((TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoUnidad)).setText(String.valueOf(localPreventaProducto.get_cantidad()));
					((TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoPaquete)).setText(String.valueOf(localPreventaProducto.get_cantidadPaquete()));
					((TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoMontoFinal)).setText(String.valueOf(localPreventaProducto.get_montoFinal()));
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
					
					ArrayList<PromocionProducto> listadoPromocionProducto = null;
					try
					{
						listadoPromocionProducto = new PromocionProductoBLL().ObtenerPromocionesProductoPor(localPreventaProducto.get_promocionId());
					}
					catch (Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la promocion por productoId: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la promocion por productoId: " + localException.getMessage());
						} 
					}
					
					if(listadoPromocionProducto == null)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los productos de la promocion.", 1);
						return null;
					}
					
					ClientePreventa localClientePreventa = null;
					try
					{
						localClientePreventa = new ClientePreventaBLL().ObtenerClientePreventaPor(clienteId);
					}
					catch (Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de los clientes: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de los clientes: " + localException.getMessage());
						} 
					}
					
					if(localClientePreventa == null)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del cliente.", 1);
			            return null;
					}
					
					float precioPromocion = 0;
					
					for(PromocionProducto itemPromo : listadoPromocionProducto)
					{
						PrecioLista localPrecioLista = null;
						try
						{
							localPrecioLista = new PrecioListaBLL().ObtenerPrecioListaPor(localClientePreventa.get_precioListaId(),itemPromo.get_productoId());
						}
						catch (Exception localException)
						{
							if(localException.getMessage() == null || localException.getMessage().isEmpty())
							{
						        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precio de lista del producto: vacio");
							}
							else
							{
								myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precio de lista del producto: " + localException.getMessage());
							} 
						}
						
						if(localPrecioLista == null)
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "El producto de la promocion no tiene un precio de lista asociado.", 1);
				            return null;
						}
						else
						{
							if(itemPromo.get_cantidad() > 0)
							{
								precioPromocion += itemPromo.get_cantidad() * localPrecioLista.get_precio() * (1 - itemPromo.get_descuento()/100);
							}
							else
							{
								precioPromocion += itemPromo.get_cantidadPaquete() + localPrecioLista.get_precioPaquete() * (1 - itemPromo.get_cantidad());
							}					
						}
					}
					
					TextView itemProducto = (TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoProducto);
					itemProducto.setText(localPromocion.get_descripcion());
					
					((TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoUnidad)).setText(String.valueOf(localPreventaProducto.get_cantidad()));
					((TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoPaquete)).setText(String.valueOf(localPreventaProducto.get_cantidadPaquete()));
					
					TextView itemPrecio = (TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoMontoFinal);
					itemPrecio.setText(String.valueOf(precioPromocion*localPreventaProducto.get_cantidad()));
				}
			}
			
			return localView;
		}	
	}
	
	private void SincronizarPreventaProducto() 
	{
		if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
		{
			if(listadoPreventaProductoNoSincronizadas != null && listadoPreventaProductoNoSincronizadas.size()>0)
			{
				InsertarPreventaProductoTemp(listadoPreventaProductoNoSincronizadas.get(0));
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No existen productos de la preventa que sincronizar.", 1);
				return;
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No esta conectado a internet, intentelo mas tarde.", 1);
			return;
		}
	}
	
	public void InsertarPreventaProductoTemp(PreventaProducto localPreventaProducto)
	{
		pdInsertarPreventaProducto = new ProgressDialog(this);
		pdInsertarPreventaProducto.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdInsertarPreventaProducto.setMessage("Insertando productos preventa ...");
	    pdInsertarPreventaProducto.setCancelable(false);
		pdInsertarPreventaProducto.setCanceledOnTouchOutside(false);

	    WSInsertarPreventaProductoTemp localWSInsertarPreventaProductoTemp = new WSInsertarPreventaProductoTemp(localPreventaProducto);
	    
	    try
	    {
	    	localWSInsertarPreventaProductoTemp.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventaProductoTemp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventaProductoTemp: " + localException.getMessage());
	    	} 
	    }
	}
	
	public class WSInsertarPreventaProductoTemp extends AsyncTask<Void, Integer, Boolean>
	{
		String PREVENTAPRODUCTO_METHOD_NAME = "InsertPreVentaProductoTemp";
		String PREVENTAPRODUCTO_SOAP_ACTION = NAMESPACE + PREVENTAPRODUCTO_METHOD_NAME;
		
		private PreventaProducto _preventaProducto;
		
		boolean WSInsertarPreVentaProductoTemp;
		int resultadoInt;
		String resultadoString;
		SoapObject localSoapObjects;
		int clienteId;
		
		public WSInsertarPreventaProductoTemp(PreventaProducto _preventaProducto) 
		{
			super();
			this._preventaProducto = _preventaProducto;
		}

		protected void onPreExecute()
	    {
			clienteId = 0;
			clienteId = ObtenerClienteIdPreventaPor(_preventaProducto.get_preventaId());
			if(clienteId == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el clienteId de la preventa.", 1);
				return;
			}
			pdInsertarPreventaProducto.show();
	    }
		
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSInsertarPreVentaProductoTemp = false;
			
			SoapObject localSoapObject = new SoapObject(NAMESPACE,PREVENTAPRODUCTO_METHOD_NAME);
			localSoapObject.addProperty("productoId", Integer.valueOf(_preventaProducto.get_productoId()));
			localSoapObject.addProperty("promocionId", Integer.valueOf(_preventaProducto.get_promocionId()));
			localSoapObject.addProperty("cantidad", Integer.valueOf(_preventaProducto.get_cantidad()));
			localSoapObject.addProperty("cantidadPaquete", Integer.valueOf(_preventaProducto.get_cantidadPaquete()));
			localSoapObject.addProperty("monto", String.valueOf(_preventaProducto.get_monto()));
			localSoapObject.addProperty("descuento", String.valueOf(_preventaProducto.get_descuento()));
			localSoapObject.addProperty("montoFinal", String.valueOf(_preventaProducto.get_montoFinal()));
			localSoapObject.addProperty("clienteId", Integer.valueOf(clienteId));
			localSoapObject.addProperty("empleadoId", Integer.valueOf(_preventaProducto.get_empleadoId()));
			localSoapObject.addProperty("costoId", String.valueOf(_preventaProducto.get_costoId()));
			localSoapObject.addProperty("almacenId",(loginEmpleado.get_almacenId()));
			localSoapObject.addProperty("nroPreVenta",(_preventaProducto.get_noPreventa()));
			localSoapObject.addProperty("precioId",(_preventaProducto.get_precioId()));
			localSoapObject.addProperty("descuentoCanal",String.valueOf(_preventaProducto.get_descuentoCanal()));
    		localSoapObject.addProperty("descuentoAjuste",String.valueOf(_preventaProducto.get_descuentoAjuste()));
    		localSoapObject.addProperty("canalRutaPrecioId",_preventaProducto.get_canalPrecioRutaId());
    		localSoapObject.addProperty("descuentoProntoPago",String.valueOf(_preventaProducto.get_descuentoProntoPago()));
        
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			
			try
			{
				localHttpTransportSE.call(PREVENTAPRODUCTO_SOAP_ACTION,localSoapSerializationEnvelope);
			
				localSoapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
				if(localSoapObjects != null)
				{
					resultadoInt = Integer.parseInt(localSoapObjects.getPropertyAsString("Id"));
					resultadoString = localSoapObjects.getPropertyAsString("Resultado");
				}
        
				if(resultadoString.equals("OK") && resultadoInt > 0) 
				{
					WSInsertarPreVentaProductoTemp = true;
				}
				return true;
			}
			catch (Exception localException)
			{
				WSInsertarPreVentaProductoTemp = false;
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
			if(pdInsertarPreventaProducto.isShowing())
			{
				pdInsertarPreventaProducto.dismiss();
			}
			
			if(ejecutado)
			{
				if(WSInsertarPreVentaProductoTemp || (resultadoString != null && resultadoString.equals("Preventa Producto Temp Repetido") && resultadoInt <= 0))
				{
					long l = 0;
					try
					{
						l = new PreventaProductoBLL().ModificarPreventaProductoNoSincronizadaPor(_preventaProducto.get_id());
					}
					catch (Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del producto de la preventa: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del producto de la preventa: " + localException.getMessage());
						} 
					}
					
					if(l==0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del producto de la preventa.", 1);
						return;
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "Producto de la preventa sincronizado.", 1);
						
						if(ObtenerPreventasProductoNoSincronizadas(_preventaProducto.get_preventaId()))
						{
							SincronizarPreventaProducto();
						}
						else
						{
							SincronizarPreventa(_preventaProducto.get_preventaId());
						}
					}
				}
				else
				{
					if(resultadoString != null && resultadoString.equals("Producto Inexistente") && resultadoInt <=0)
					{
						InformarInexistenciaProducto(_preventaProducto);
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), resultadoString , 1);
				        return;
					}
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo ejecutar el webService WSInsertPreVentaProductoTemp.", 1);
				return;
			}
		}
	}
	
	private void InformarInexistenciaProducto(final PreventaProducto preventaProducto)
	{
		dialog = new Dialog(Vendedorsincronizarpreventas.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.diseniodialogo_vendedorcierredia);
		dialog.setTitle("Producto Inexistente");
		dialog.setCancelable(false);
		
		TextView tvMensaje = (TextView)dialog.findViewById(R.id.tvDialogoMensaje);
		
		//Verificamos si el item inexistente es un producto o una promocion
		if(preventaProducto.get_productoId()>0)
		{
			Producto producto = null;
			try
			{
				producto = new ProductoBLL().ObtenerProductoPor(preventaProducto.get_productoId());
			}
			catch(Exception localException)
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
			
			if(producto==null)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo obtener los detalles del producto.",1);
				return;
			}
			else
			{
				tvMensaje.setText("No existe " + producto.get_descripcion25() + " en el almacen. Desea elimarlo de la preventa?");
			}
		}
		else
		{
			Promocion promocion = null;
			try
			{
				promocion = new PromocionBLL().ObtenerPromocionPor(preventaProducto.get_promocionId());
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
			
			if(promocion==null)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo obtener los detalles de la promocion.",1);
				return;
			}
			else
			{
				tvMensaje.setText("No existe " + promocion.get_descripcion() + " en el almacen. Desea elimarlo de la preventa?");
			}
		}
		
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
						EliminarItemPreventaProducto(preventaProducto);
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
	
	private void EliminarItemPreventaProducto(PreventaProducto preventaProducto)
	{
		boolean eliminarPreventaProducto = false;
		try
		{
			eliminarPreventaProducto = new PreventaProductoBLL().BorrarPreventaProductoPorId(preventaProducto.get_id());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventaProducto por Id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventaProducto por Id: " + localException.getMessage());
			}
		}
		
		if(eliminarPreventaProducto == true)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "Producto de la preventa eliminado.", 1);
			
			ArrayList<PreventaProducto> listadoPreventaProducto = null;
			try
			{
				listadoPreventaProducto = new PreventaProductoBLL().ObtenerPreventasProductoPor(preventaProducto.get_preventaId());
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"No se pudo obtener el listado de preventasProducto por preventaId: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"No se pudo obtener el listado de preventasProducto por preventaId:" + localException.getMessage());
				}
			}
			
			if(listadoPreventaProducto == null)
			{
				boolean preventaEliminada = false;
				try
				{
					preventaEliminada = new PreventaBLL().BorrarPreventasPorId(preventaProducto.get_preventaId());
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
				        myLog.InsertarLog("App",this.toString(),1,"No se pudo borrar la preventa por id: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"No se pudo borrar la preventa por id:" + localException.getMessage());
					}
				}
				if(preventaEliminada)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Preventa eliminada.", 1);
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo eliminar la preventa por preventaId.", 1);
				}
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo eliminar la preventa producto por su Id.",1);
			return;
		}
		
		if(ObtenerPreventasProductoNoSincronizadas(preventaProducto.get_preventaId()))
		{
			SincronizarPreventaProducto();
		}
		else
		{
			if(ObtenerPreventasNoSincronizadas(preventaProducto.get_preventaId()))
			{
				SincronizarPreventa(preventaProducto.get_preventaId());
			}
			else
			{
				ObtenerPreventasNoSincronizadasForDisplay();
			}
			
		}
	}
	
	private boolean ObtenerPreventasNoSincronizadas(int preventaId)
	{
		ArrayList<Preventa> listadoPreventaNoSincronizadas = null;
	    try
	    {
	    	listadoPreventaNoSincronizadas = new PreventaBLL().ObtenerPreventasNoSincronizadasPorRowId(preventaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas no sincronizadas por preventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas no sincronizadas por preventaId: " + localException.getMessage());
	    	}
	    }
	    
	    if (listadoPreventaNoSincronizadas == null) 
	    {
 	        return false;
	    }  
	    else
	    {
	    	return true;
	    }
	}
	
	private int ObtenerClienteIdPreventaPor(int id)
	{
		int clienteId = 0;
		Preventa localPreventa = null;
		try
		{
			localPreventa = new PreventaBLL().ObtenerPreventaPorId(id);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el clienteId de la preventa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el clienteId de la preventa: " + localException.getMessage());
			} 
			return 0;
		}
		
		if(localPreventa != null)
		{
			clienteId = localPreventa.get_clienteId();
		}
		
		return clienteId;
	}
	
	private void ObtenerPreventasProductoNoSincronizadasForDisplay(int id)
	{
		listadoPreventaProductoNoSincronizadas = null;
	    try
	    {
	    	listadoPreventaProductoNoSincronizadas = new PreventaProductoBLL().ObtenerPreventasProductoNoSincronizadasPor(id);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la preventa no sincronizada por preventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la preventa no sincronizada por preventaId: " + localException.getMessage());
	    	}
	    }
	    
	    if (listadoPreventaProductoNoSincronizadas == null) 
	    {
	    	MostrarControlesPeventaProducto(false);
	    	SincronizarPreventa(id);
	    }  
	    else
	    {
	    	MostrarControlesPeventaProducto(true);
	    	LlenarPreventaProductoListView();
	    }
	}
				
	private void SincronizarPreventa(int id)
	{
		if(listadoPreventaNoSincronizadas != null && listadoPreventaNoSincronizadas.size()>0)
		{
			int numeroPreventasProducto = ObtenerNumeroPreventasProductoPorPreventaId(id);
			if(numeroPreventasProducto != 0)
			{
				Preventa localPreventa = null;
				try
				{
					localPreventa = new PreventaBLL().ObtenerPreventaPorId(id);
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
				        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por id: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por id: " + localException.getMessage());
					} 
				}

				InsertarPreventa(localPreventa,numeroPreventasProducto,ObtenerClientePreventa());
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el numero total "
																		+ "de productos, en la preventa.", 1);
			}				
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No existen preventas que sincronizar.", 1);
			return;
		}
	}
	
	public void InsertarPreventa(Preventa localPreventa,int totalPreventasProducto,ClientePreventa clientePreventa)
	{
	    pdInsertarPreventa = new ProgressDialog(this);
	    pdInsertarPreventa.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdInsertarPreventa.setMessage("Insertando preventa ...");
	    pdInsertarPreventa.setCancelable(false);
	    pdInsertarPreventa.setCanceledOnTouchOutside(false);
	    
	    WSInsertarPreventa localWSInsertarPreventa = new WSInsertarPreventa(localPreventa,totalPreventasProducto,clientePreventa);
	    try
	    {
	    	localWSInsertarPreventa.execute();
        }
        catch (Exception localException)
        {
        	if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventa: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventa: " + localException.getMessage());
        	} 
        	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservie WSInsertPreventa. ", 1);
        }
	}
	
	private class WSInsertarPreventa extends AsyncTask<Void, Integer, Boolean>
	{
		String INSERTPREVENTA_METHOD_NAME = "InsertPreVenta";
		String INSERTPREVENTA_SOAP_ACTION = NAMESPACE + INSERTPREVENTA_METHOD_NAME;
		
		boolean WSInsertarPreventa;
		int intResultado;
		SoapObject localSoapObjects;
		String stringResultado;
		
		private Preventa _preventa;		
		private int _totalPreventasProducto;
		private ClientePreventa _clientePreventa;

		public WSInsertarPreventa(Preventa _localPreventa,int totalPreventasProducto,ClientePreventa clientePreventa) 
		{
			super();
			this._preventa = _localPreventa;
			this._totalPreventasProducto = totalPreventasProducto;
			this._clientePreventa = clientePreventa;
		}

		protected void onPreExecute()
	    {
			pdInsertarPreventa.show();
	    }
       
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSInsertarPreventa = false;
	
			SoapObject localSoapObject = new SoapObject(NAMESPACE,INSERTPREVENTA_METHOD_NAME);
			localSoapObject.addProperty("clienteId", Integer.valueOf(_preventa.get_clienteId()));
			localSoapObject.addProperty("empleadoId", Integer.valueOf(_preventa.get_empleadoId()));
			localSoapObject.addProperty("tipoPagoId", Integer.valueOf(_preventa.get_tipoPagoId()));
			localSoapObject.addProperty("monto", String.valueOf(_preventa.get_monto()));
			localSoapObject.addProperty("descuento", String.valueOf(_preventa.get_descuento()));
			localSoapObject.addProperty("montoFinal", String.valueOf(_preventa.get_montoFinal()));
			localSoapObject.addProperty("latitud", String.valueOf(_preventa.get_latitud()));
			localSoapObject.addProperty("longitud", String.valueOf(_preventa.get_longitud()));
			localSoapObject.addProperty("numeroItems", Integer.valueOf(_totalPreventasProducto));
			localSoapObject.addProperty("dia", String.valueOf(_preventa.get_dia()));
			localSoapObject.addProperty("mes", String.valueOf(_preventa.get_mes()));
			localSoapObject.addProperty("anio", String.valueOf(_preventa.get_anio()));
			localSoapObject.addProperty("hora", String.valueOf(_preventa.get_hora()));
			localSoapObject.addProperty("minuto", String.valueOf(_preventa.get_minuto()));
			localSoapObject.addProperty("nombreFactura", String.valueOf(_preventa.get_factura()));
			localSoapObject.addProperty("nit", String.valueOf(_preventa.get_nit()));
			localSoapObject.addProperty("nitNuevo",String.valueOf(_preventa.is_nitNuevo()));
			localSoapObject.addProperty("almacenId",loginEmpleado.get_almacenId());
			localSoapObject.addProperty("rutaId",_clientePreventa.get_rutaId());
			localSoapObject.addProperty("diaId",_clientePreventa.get_diaId());
			localSoapObject.addProperty("nroPreVenta",_preventa.get_noPreventa());
			localSoapObject.addProperty("observacion",_preventa.get_observacion());
			localSoapObject.addProperty("aplicaBonificacion",String.valueOf(_preventa.is_aplicarBonificacion()));
			localSoapObject.addProperty("diaEntrega",_preventa.get_diaEntrega());
			localSoapObject.addProperty("mesEntrega",_preventa.get_mesEntrega());
			localSoapObject.addProperty("anioEntrega",_preventa.get_anioEntrega());
			localSoapObject.addProperty("dosificacionId",_preventa.get_dosificacionId());
			localSoapObject.addProperty("tipoNit",_preventa.get_tipoNit());
			localSoapObject.addProperty("ruta",_preventa.get_ruta());
			localSoapObject.addProperty("tipoVisita",_preventa.get_tipoVisita());
			localSoapObject.addProperty("zonaVentaId",_preventa.get_zonaVentaId());
			localSoapObject.addProperty("prontoPagoId",_preventa.get_prontoPagoId());
			localSoapObject.addProperty("descuentoCanal",String.valueOf(_preventa.get_descuentoCanal()));
			localSoapObject.addProperty("descuentoAjuste",String.valueOf(_preventa.get_descuentoAjuste()));
			localSoapObject.addProperty("descuentoProntoPago",String.valueOf(_preventa.get_descuentoProntoPago()));
			localSoapObject.addProperty("formaPagoId",_preventa.get_formaPagoId());
			localSoapObject.addProperty("codTransferencia",String.valueOf(_preventa.get_codTransferencia()));
			localSoapObject.addProperty("fromApk",String.valueOf(_preventa.is_fromApk()));
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			
			try
			{
				localHttpTransportSE.call(INSERTPREVENTA_SOAP_ACTION, localSoapSerializationEnvelope);
			
				localSoapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
        
				if(localSoapObjects != null)
				{
					intResultado = Integer.parseInt(localSoapObjects.getPropertyAsString("Id"));
			        stringResultado = localSoapObjects.getPropertyAsString("Resultado");					
				}
				
				if(intResultado > 0) 
				{
					WSInsertarPreventa = true;
				}
				return true;
			}
			catch (Exception localException)
			{
				WSInsertarPreventa = true;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventa: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventa: " + localException.getMessage());
				} 
				return false;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdInsertarPreventa.isShowing())
			{
				pdInsertarPreventa.dismiss();
			}
			
			if(ejecutado)
			{
				String[] newStringResultado = null;
				if(stringResultado != null)
				{ 
					newStringResultado = stringResultado.split("\\|");
				}
					
				if(WSInsertarPreventa || newStringResultado[0].equals("Preventa Repetida"))
				{
					Float monto;
					Float descuento;
					Float montoFinal;
					
					if(newStringResultado.length == 4 && newStringResultado[0].equals("Preventa Repetida"))
					{
						monto = Float.valueOf(newStringResultado[1].toString().replace(",", ""));
						descuento = Float.valueOf(newStringResultado[2].toString().replace(",", ""));
						montoFinal = Float.valueOf(newStringResultado[3].toString().replace(",", ""));
					}
					else
					{
						monto = Float.valueOf(newStringResultado[0].toString().replace(",", ""));
						descuento = Float.valueOf(newStringResultado[1].toString().replace(",", ""));
						montoFinal = Float.valueOf(newStringResultado[2].toString().replace(",", ""));
					}
					 
					long l=0;
					try
					{
						l = new PreventaBLL().ModificarPreventaNoSincronizadaPor(_preventa.get_Id(),monto,descuento,montoFinal);
					}
					catch (Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al sincronizar la preventa: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al sincronizar la preventa: " + localException.getMessage());
						}
					}
					
					if(l==0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo sincronizar la preventa.", 1);
						return;
					}
					
					l = 0;
					 
					 try
					 {
						 l = new PreventaBLL().ModificarPreventaIdServer(_preventa.get_Id(),intResultado);
					 }
					 catch (Exception localException)
					 {
						 if(localException.getMessage() == null || localException.getMessage().isEmpty())
						 {
							 myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventaIdServer de la preventa: vacio");
						 }
						 else
						 {
							 myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventaIdServer de la preventa: " + localException.getMessage());
						 } 
					}
							
					 if(l==0)
					 {
						 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la preventaIdServer de la preventa.", 1);
						 return;
					 }
					 
					 ArrayList<PreventaProductoPOP> listadoPreventaProductoPOP = null;
					 try
					 {
						 listadoPreventaProductoPOP = new PreventaProductoPOPBLL().ObtenerPreventasPorductoPOPPorPreventaPOPId(_preventa.get_Id());
					 }
					 catch(Exception localException)
					 {
						 if(localException.getMessage() == null || localException.getMessage().isEmpty())
						 {
							 myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasPOP no sincronizadas: vacio");
						 }
						 else
						 {
							 myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasPOP no sincronizadas: " + localException.getMessage());
						 } 
					 }
					 
					 if(listadoPreventaProductoPOP != null)
					 {
						 l =0;
						 try
						 {
							 l = new PreventaProductoPOPBLL().ModificarPreventaProductosPOPNoSincronizadaPor(_preventa.get_Id(), intResultado);
						 }
						 catch(Exception localException)
						 {
							 if(localException.getMessage() == null || localException.getMessage().isEmpty())
							 {
								 myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventaPOPIdServer de la preventaPOP: vacio");
							 }
							 else
							 {
								 myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventaPOPIdServer de la preventaPOP: " + localException.getMessage());
							 }  
						 }
						 
						 if(l==0)
						 {
							 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la preventaIdServer de la preventaPOP.", 1);
							 return;
						 }
					}
					 
					 ArrayList<PreventaProductoCambio> listadoPreventaProductoCambio = null;
					 try
					 {
						 listadoPreventaProductoCambio = new PreventaProductoCambioBLL().ObtenerPreventasPorductoCambioPorPreventaId(_preventa.get_Id());
					 }
					 catch(Exception localException)
					 {
						 if(localException.getMessage() == null || localException.getMessage().isEmpty())
						 {
							 myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas con cambio no sincronizadas: vacio");
						 }
						 else
						 {
							 myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas con cambio no sincronizadas: " + localException.getMessage());
						 } 
					 }
					 
					 if(listadoPreventaProductoCambio != null)
					 {
						 l =0;
						 try
						 {
							 l = new PreventaProductoCambioBLL().ModificarPreventaProductoCambioPreventaIdServer(_preventa.get_Id(), intResultado);
						 }
						 catch(Exception localException)
						 {
							 if(localException.getMessage() == null || localException.getMessage().isEmpty())
							 {
								 myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventaIdServer de la preventaCambio: vacio");
							 }
							 else
							 {
								 myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventaIdServer de la preventaCambio: " + localException.getMessage());
							 }  
						 }
						 
						 if(l==0)
						 {
							 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la preventaIdServer de la preventaCambio.", 1);
							 return;
						 }
					 }
					
					theUtilidades.MostrarMensaje(getApplicationContext(), "Preventa sincronizada.", 1);
					ObtenerPreventasNoSincronizadasForDisplay();
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),stringResultado,1);
					return;
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo ejecutar el webservice InsertarPreventa.", 1);
				return;
			}
		}
	}
    	  
	public ClientePreventa ObtenerClientePreventa()
	{
		ClientePreventa theClientePreventa = null;
		try
		{
			theClientePreventa = new ClientePreventaBLL().ObtenerClientePreventaPor(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del clientePreventa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del clientePreventa: " + localException.getMessage());
			}
		}
		
		if(theClientePreventa == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo obtener el cliente de la preventa.", 1);
			return null;
		}
		else
		{
			return theClientePreventa;
		}
	}	
 
	public int ObtenerNumeroPreventasProductoPorPreventaId(int preventaId)
	{				
		ArrayList<PreventaProducto> listadoPreventaProducto = null;
		try
		{
			listadoPreventaProducto = new PreventaProductoBLL().ObtenerPreventasProductoPor(preventaId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la preventa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la preventa: " + localException.getMessage());
			} 
		}
		
		if(listadoPreventaProducto == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los productos de la preventa por preventaId.", 1);
			return 0;
		}
		
		return listadoPreventaProducto.size();
	}
	
	public void MostrarPantallaMenuPrincipal()
	{
	    startActivity(new Intent(this, Menuvendedor.class));
	}
	
	@Override
	public void onBackPressed() 
	{
		Intent intent = new Intent(this,Menuvendedor.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
}
