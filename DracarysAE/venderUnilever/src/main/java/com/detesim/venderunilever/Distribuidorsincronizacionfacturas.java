package com.detesim.venderunilever;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import BLL.FacturaBLL;
import BLL.FacturaProductoBLL;
import BLL.MyLogBLL;
import BLL.ProductoBLL;
import BLL.PromocionBLL;
import Clases.Factura;
import Clases.FacturaProducto;
import Clases.LoginEmpleado;
import Clases.Producto;
import Clases.Promocion;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class Distribuidorsincronizacionfacturas extends Activity implements OnClickListener
{
	LinearLayout llDistribuidorSincronizacionFacturas;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
	ListView lvFactura;
	ListView lvFacturaProducto;
	Button btnSincronizar;
	ProgressDialog pdEsperaInsertarFacturaProductoTemp;
	ProgressDialog pdEsperaInsertarFactura;
	
	int facturaId;
	ArrayList<Factura> listadoFacturaNoSincronizadas;
	ArrayList<FacturaProducto> listadoFacturaProductoNoSincronizadas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_distribuidorsincronizacionfacturas);
		
		llDistribuidorSincronizacionFacturas= (LinearLayout)findViewById(R.id.DistribuidorSincronizacionFacturasLinearLayout);
		lvFactura = (ListView)findViewById(R.id.lvSincronizacionFacturasFacturas);
		lvFacturaProducto = (ListView)findViewById(R.id.lvSincronizacionFacturasFacturaProducto);
		btnSincronizar = (Button)findViewById(R.id.btnSincronizacionFacturasSincronizar);
		btnSincronizar.setOnClickListener(this);
		
		llDistribuidorSincronizacionFacturas.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
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
	    
	    ObtenerFacturasNoSincronizadasForDisplay();
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.btnSincronizacionFacturasSincronizar:
			SincronizarFacturaProducto();
			break;
		}		
	}
	
	private void ObtenerFacturasNoSincronizadasForDisplay()
	{
		listadoFacturaNoSincronizadas = null;
	    try
	    {
	    	listadoFacturaNoSincronizadas = new FacturaBLL().ObtenerFacturasNoSincronizadas();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturas no sincronizadas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturas no sincronizadas: " + localException.getMessage());
	    	} 
	    }
	    
	    if(listadoFacturaNoSincronizadas == null) 
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(),"No existen facturas que sincronizar.", 1);
	    	lvFactura.setAdapter(null);
	    	lvFacturaProducto.setAdapter(null);
	    }
	    else
	    {
	    	lvFacturaProducto.setAdapter(null);
	    	LlenarFacturaListView();
		    FacturasItemOnClick();	    	
	    }
	    
	    btnSincronizar.setVisibility(View.INVISIBLE);
	}
	
	private void LlenarFacturaListView()
	{
		MiAdaptadorPreventa localMiAdaptadorPreventa = new MiAdaptadorPreventa();
		ListView localListView = lvFactura;
		if(listadoFacturaNoSincronizadas == null)
		{
			localListView.setAdapter(null);
		}
		else
		{
			ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    	localLayoutParams.height = ((int)(30*getApplicationContext().getResources().getDisplayMetrics().density) * listadoFacturaNoSincronizadas.size());
	    	localListView.setLayoutParams(localLayoutParams);
			localListView.setAdapter(localMiAdaptadorPreventa);
		}
	}
	
	class MiAdaptadorPreventa extends ArrayAdapter<Factura>
	{
		public MiAdaptadorPreventa()
		{
			super(getApplicationContext(),R.layout.disenio_vendedorsincronizacionpreventa,listadoFacturaNoSincronizadas);
		}
    
		public View getView(int position, View view, ViewGroup viewGroup)
		{
			View localView = view;
			
			if (localView == null) 
			{
				localView = getLayoutInflater().inflate(R.layout.disenio_vendedorsincronizacionpreventa, viewGroup, false);
			}
			
			Factura localFactura = null;
			
			if(listadoFacturaNoSincronizadas.size() > 0)
			{
				localFactura = (Factura)listadoFacturaNoSincronizadas.get(position);
			}
      
			((ImageView)localView.findViewById(R.id.imgVendedorSincronizacionPreventaBullet)).setImageResource(R.drawable.bullet_seleccionar);
			((TextView)localView.findViewById(R.id.tvVendedorSincronizacionPreventaNombre)).setText(localFactura.get_nombre() + " - " + localFactura.get_nit() + " : " + String.valueOf(localFactura.get_montoFinal()));
        
			return localView;
		}
	}

	private void FacturasItemOnClick()
	{
	    ((ListView)findViewById(R.id.lvSincronizacionFacturasFacturas)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
	    	Factura localFactura = null;
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	    	{
	    		localFactura = ((Factura)listadoFacturaNoSincronizadas.get(position));
	    		
	    		facturaId = localFactura.get_rowId();
    			ObtenerFacturaProductoNoSincronizadasForDisplay(facturaId);
    			btnSincronizar.setVisibility(View.VISIBLE);
	        }
	    });
	}
	
	private void ObtenerFacturaProductoNoSincronizadasForDisplay(int facturaId)
	{
		listadoFacturaProductoNoSincronizadas = null;
	    try
	    {
	    	listadoFacturaProductoNoSincronizadas = new FacturaProductoBLL().ObtenerFacturasProductoNoSincronizadasPorFacturaId(facturaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la factura no sincronizada por facturaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la factura no sincronizada por facturaId: " + localException.getMessage());
	    	}
	    }
	    
	    if (listadoFacturaProductoNoSincronizadas == null) 
	    {
	    	SincronizarFactura(facturaId);
	    }  
	    else
	    {
	    	LlenarFacturaProductoListView();
	    }
	}
	
	private void LlenarFacturaProductoListView()
	{
	    MiAdaptadorPreventaProducto localMiAdaptadorPreventaProducto = new MiAdaptadorPreventaProducto();
	    ListView localListView = lvFacturaProducto;
	    if(listadoFacturaProductoNoSincronizadas == null)
	    {
	    	localListView.setAdapter(null);
	    }
	    else
	    {
	    	ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    	localLayoutParams.height = ((int)(30*getApplicationContext().getResources().getDisplayMetrics().density) * listadoFacturaProductoNoSincronizadas.size());
	    	localListView.setLayoutParams(localLayoutParams);
	    	localListView.setAdapter(localMiAdaptadorPreventaProducto);
	    }
	    
	}
	
	private void SincronizarFacturaProducto() 
	{
		if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
		{
			if(listadoFacturaProductoNoSincronizadas != null)
			{
				if(listadoFacturaProductoNoSincronizadas.size()>0)
				{
					InsertarFacturaProductoTemp(listadoFacturaProductoNoSincronizadas.get(0));
				}	
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No existen productos de la factura que sincronizar.", 1);
					return;
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No existen productos de la factura que sincronizar.", 1);
				return;
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No esta conectado a internet, intentelo mas tarde.", 1);
			return;
		}
	}
	
	class MiAdaptadorPreventaProducto extends ArrayAdapter<FacturaProducto>
	{
		public MiAdaptadorPreventaProducto()
		{
			super(getApplicationContext(), R.layout.disenio_vendedorsincronizacionpreventa,listadoFacturaProductoNoSincronizadas);
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
			
			FacturaProducto localFacturaProducto = null;
			
			if(listadoFacturaProductoNoSincronizadas.size() > 0)
			{
				localFacturaProducto = ((FacturaProducto)listadoFacturaProductoNoSincronizadas.get(position));
				
				if(localFacturaProducto.get_productoId() != 0)
				{
					localProducto = null;
					try
					{
						localProducto = new ProductoBLL().ObtenerProductoPor(localFacturaProducto.get_productoId());
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
					((TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoUnidad)).setText(String.valueOf(localFacturaProducto.get_cantidad()));
					((TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoPaquete)).setText(String.valueOf(localFacturaProducto.get_cantidadPaquete()));
					((TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoMontoFinal)).setText(String.valueOf(localFacturaProducto.get_montoFinal()));
				}
				
				if(localFacturaProducto.get_promocionId()!=0)
				{
					localPromocion = null;
					try
					{
						localPromocion = new PromocionBLL().ObtenerPromocionPor(localFacturaProducto.get_promocionId());
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
					
					TextView itemProducto = (TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoProducto);
					itemProducto.setText(localPromocion.get_descripcion());
					
					TextView itemUnidad = (TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoUnidad);
					itemUnidad.setText(String.valueOf(localFacturaProducto.get_cantidad()));
					
					TextView itemPaquete = (TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoPaquete);
					itemPaquete.setText(String.valueOf(localFacturaProducto.get_cantidadPaquete()));
					
					TextView itemPrecio = (TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoMontoFinal);
					itemPrecio.setText(String.valueOf(localFacturaProducto.get_montoFinal()));
				}
			}
			
			return localView;
		}	
	}
	
	private void InsertarFacturaProductoTemp(FacturaProducto localFacturaProducto)
	 {
		pdEsperaInsertarFacturaProductoTemp = new ProgressDialog(this);
		pdEsperaInsertarFacturaProductoTemp.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaInsertarFacturaProductoTemp.setMessage("Insertando productos factura ...");
		pdEsperaInsertarFacturaProductoTemp.setCancelable(false);
		 pdEsperaInsertarFacturaProductoTemp.setCanceledOnTouchOutside(false);
	    
	    WSInsertarFacturaProductoTemp localWSInsertarFacturaProductoTemp = new WSInsertarFacturaProductoTemp(localFacturaProducto);
	    try
	    {
	    	localWSInsertarFacturaProductoTemp.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarFacturaProductoTemp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarFacturaProductoTemp: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertarFacturaProductoTemp", 1);
	    }
	 }
	
	public class WSInsertarFacturaProductoTemp extends AsyncTask<Void, Integer, Boolean>
	{
		String FACTURAPRODUCTO_METHOD_NAME = "InsertFacturaProductoTemp";
		String FACTURAPRODUCTO_SOAP_ACTION = NAMESPACE + FACTURAPRODUCTO_METHOD_NAME;
		 
		private FacturaProducto _facturaProductoTemp;
		 
		boolean WSInsertarFacturaProductoTemp;
		int resultadoInt;
		String resultadoString;
		SoapObject localSoapObjects;
	    
		public WSInsertarFacturaProductoTemp(FacturaProducto paramFacturaProductoTemp)
		{
			_facturaProductoTemp = paramFacturaProductoTemp;
	    }
		    
		protected void onPreExecute()
		{
			pdEsperaInsertarFacturaProductoTemp.show();
	    }
		    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSInsertarFacturaProductoTemp = false;
		      
	   		SoapObject localSoapObject = new SoapObject(NAMESPACE, this.FACTURAPRODUCTO_METHOD_NAME);
	   		localSoapObject.addProperty("productoId", _facturaProductoTemp.get_productoId());
	   		localSoapObject.addProperty("promocionId", _facturaProductoTemp.get_promocionId());
	   		localSoapObject.addProperty("cantidad", _facturaProductoTemp.get_cantidad());
	   		localSoapObject.addProperty("cantidadPaquete", _facturaProductoTemp.get_cantidadPaquete());
	   		localSoapObject.addProperty("monto", String.valueOf(_facturaProductoTemp.get_monto()));
	   		localSoapObject.addProperty("descuento", String.valueOf(_facturaProductoTemp.get_descuento()));
	   		localSoapObject.addProperty("montoFinal", String.valueOf(_facturaProductoTemp.get_montoFinal()));
	   		localSoapObject.addProperty("clienteId", _facturaProductoTemp.get_clienteId());
	   		localSoapObject.addProperty("empleadoId", _facturaProductoTemp.get_empleadoId());
	   		localSoapObject.addProperty("precioId", _facturaProductoTemp.get_precioId());
	   		localSoapObject.addProperty("nroFactura", _facturaProductoTemp.get_noFactura());
	
	   		SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
	   		localSoapSerializationEnvelope.dotNet = true;
	   		localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
	   		HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
	   		try
	   		{
	   			localHttpTransportSE.call(FACTURAPRODUCTO_SOAP_ACTION, localSoapSerializationEnvelope);
	   				
	   			localSoapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
	   			if(localSoapObjects!=null)
	   			{
	   				resultadoInt = Integer.parseInt(localSoapObjects.getPropertyAsString("Id"));
	       			resultadoString = localSoapObjects.getPropertyAsString("Resultado");    				
	   			}
	   			
	   			if(resultadoString.equals("OK") && resultadoInt > 0)
	   			{
	   				WSInsertarFacturaProductoTemp = true;
	   			}
	   			return true;
	    	}
  			catch (Exception localException)
  			{
  				WSInsertarFacturaProductoTemp = false;
	   			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	   			{
	   		        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarFacturaProductoTemp: vacio");
	   			}
	   			else
	   			{
	   				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarFacturaProductoTemp: " + localException.getMessage());
	   			} 
	   			return true;
	   		}
	   	}
	    
	    protected void onPostExecute(Boolean ejecutado)
	    {
	    	if(pdEsperaInsertarFacturaProductoTemp.isShowing())
	    	{
	    		pdEsperaInsertarFacturaProductoTemp.dismiss();
	    	}
	    	
	    	if(ejecutado) 
	    	{
	    		if(WSInsertarFacturaProductoTemp || (resultadoString != null && resultadoString.equals("Factura Producto Temp Repetido") && resultadoInt <=0)) 
	    		{
	    			if(ModificarSincronizacionFacturaProducto(_facturaProductoTemp.get_rowId(),true))
	    			{
	    				theUtilidades.MostrarMensaje(getApplicationContext(), "Producto de la factura insertado.", 1);
		    			
		    			if(ObtenerFacturasProductoNoSincronizadas(_facturaProductoTemp.get_facturaId()))
						{
							SincronizarFacturaProducto();
						}
						else
						{
							SincronizarFactura(_facturaProductoTemp.get_facturaId());
						}
	    			}
	    			else
	    			{
	    				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion de la factura.", 1);
	    			}
  				}
	    		else
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el producto de la factura.", 1);
	    		}
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "El WebService WSInsertFacturaProductoTemp no se ejecuto correctamente. ", 1);
	    		return;
	    	}
	    }
	}
	
	private void SincronizarFactura(int rowId)
	{
		if(listadoFacturaNoSincronizadas != null)
		{
			if(listadoFacturaNoSincronizadas.size()>0)
			{
				Factura localFactura = null;
				try
				{
					localFactura = new FacturaBLL().ObtenerFacturaPorRowId(rowId);
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener la factura por id: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener la factura por id: " + localException.getMessage());
					} 
				}
				
				SincronizarFactura(localFactura);				
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No existen facturas que sincronizar.", 1);
				return;
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No existen facturas que sincronizar.", 1);
			return;
		}
	}
	
	private boolean ObtenerFacturasProductoNoSincronizadas(int facturaId)
	{
		listadoFacturaProductoNoSincronizadas = null;
	    try
	    {
	    	listadoFacturaProductoNoSincronizadas = new FacturaProductoBLL().ObtenerFacturasProductoNoSincronizadasPorFacturaId(facturaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la factura no sincronizada por facturaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la factura no sincronizada por facturaId: " + localException.getMessage());
	    	}
	    }
	    
	    if (listadoFacturaProductoNoSincronizadas == null) 
	    {
 	        return false;
	    }  
	    else
	    {
	    	return true;
	    }
	}
	
	private boolean ModificarSincronizacionFacturaProducto(int rowId,boolean estado)
	{
		int modificado = 0;
		try
		{
			modificado = new FacturaProductoBLL().ModificarFacturaProducto(rowId, estado);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
  			{
  		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la facturaProducto: vacio");
  			}
  			else
  			{
  				myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la facturaProducto: " + localException.getMessage());
  			}
			return false;
		}
		
		if(modificado ==0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	private void SincronizarFactura(Factura factura)
	 {
		pdEsperaInsertarFactura = new ProgressDialog(this);
		pdEsperaInsertarFactura.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaInsertarFactura.setMessage("Insertando factura ...");
		pdEsperaInsertarFactura.setCancelable(false);
		 pdEsperaInsertarFactura.setCanceledOnTouchOutside(false);
		 	 
		 WSInsertarFactura localWSInsertarFactura = new WSInsertarFactura(factura);
		 try
		 {
			 localWSInsertarFactura.execute();
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarFactura: vacio");
			 }
	 		 else
		 	 {
	 			 myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarFactura: " + localException.getMessage());
		 	 }
			 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertarFactura.", 1);
		 }
	 }
	
	private class WSInsertarFactura extends AsyncTask<Void, Integer, Boolean>
	 {
		 String INSERTFACTURA_METHOD_NAME = "InsertFacturaApk";
		 String INSERTFACTURA_SOAP_ACTION = NAMESPACE + INSERTFACTURA_METHOD_NAME;
		 
		 private Factura _factura;
		 
		 public WSInsertarFactura(Factura factura)
		 {
			 this._factura = factura;		 
		 }
		 
		 boolean WSInsertarFactura = false;
		 int intResultado;
		 SoapObject soapResultado;
		 String stringResultado;
		 
		 protected void onPreExecute()
		 {
			 pdEsperaInsertarFactura.show();
		 }
	    
		 protected Boolean doInBackground(Void... paramVarArgs)
		 {
			 WSInsertarFactura = false;
			 
			SoapObject localSoapObject = new SoapObject(NAMESPACE,INSERTFACTURA_METHOD_NAME);
			localSoapObject.addProperty("numero", String.valueOf(_factura.get_numero()));
			localSoapObject.addProperty("nombre", String.valueOf(_factura.get_nombre()));
			localSoapObject.addProperty("nit", String.valueOf(_factura.get_nit()));
			localSoapObject.addProperty("fechaDia", Integer.valueOf(_factura.get_fechaDia()));
			localSoapObject.addProperty("fechaMes", Integer.valueOf(_factura.get_fechaMes()));
			localSoapObject.addProperty("fechaAnio", Integer.valueOf(_factura.get_fechaAnio()));
			localSoapObject.addProperty("fechaHora", Integer.valueOf(_factura.get_fechaHora()));
			localSoapObject.addProperty("fechaMinuto", Integer.valueOf(_factura.get_fechaMinuto()));
			localSoapObject.addProperty("fechaLimiteEmisionDia", Integer.valueOf(_factura.get_fechaLimiteEmisionDia()));
			localSoapObject.addProperty("fechaLimiteEmisionMes", Integer.valueOf(_factura.get_fechaLimiteEmisionMes()));
			localSoapObject.addProperty("fechaLimiteEmisionAnio", Integer.valueOf(_factura.get_fechaLimiteEmisionAnio()));
			localSoapObject.addProperty("fechaLimiteEmisionHora", Integer.valueOf(_factura.get_fechaLimiteEmisionHora()));
			localSoapObject.addProperty("fechaLimiteEmisionMinuto", Integer.valueOf(_factura.get_fechaLimiteEmisionMinuto()));
			localSoapObject.addProperty("montoTotal", String.valueOf(_factura.get_montoTotal()));
			localSoapObject.addProperty("descuento", String.valueOf(_factura.get_descuento()));
			localSoapObject.addProperty("montoFinal", String.valueOf(_factura.get_montoFinal()));
			localSoapObject.addProperty("montoPalabras", String.valueOf(_factura.get_montoPalabras()));
			localSoapObject.addProperty("codigoAutorizacion", String.valueOf(_factura.get_codigoAutorizacion()));
			localSoapObject.addProperty("codigoControl", String.valueOf(_factura.get_codigoControl()));
			localSoapObject.addProperty("facturaTipoId", String.valueOf(_factura.get_facturaTipoId()));
			localSoapObject.addProperty("almacenId", Integer.valueOf(_factura.get_almacenId()));
			localSoapObject.addProperty("anulada", Boolean.valueOf(false));
			localSoapObject.addProperty("anulacionUsuarioId", Integer.valueOf(_factura.get_anulacionUsuarioId()));
			localSoapObject.addProperty("anulacionMotivo", String.valueOf(_factura.get_anulacionMotivo()));
			localSoapObject.addProperty("anulacionFechaDia", Integer.valueOf(_factura.get_anulacionFechaDia()));
			localSoapObject.addProperty("anulacionFechaMes", Integer.valueOf(_factura.get_anulacionFechaMes()));
			localSoapObject.addProperty("anulacionFechaAnio", Integer.valueOf(_factura.get_anulacionFechaAnio()));
			localSoapObject.addProperty("dosificacionId", Integer.valueOf(_factura.get_dosificacionId()));
			localSoapObject.addProperty("empleadoId", Integer.valueOf(_factura.get_empleadoId()));
			localSoapObject.addProperty("clienteId", Integer.valueOf(_factura.get_clienteId()));
			localSoapObject.addProperty("numeroItems", Integer.valueOf(_factura.get_numeroItems()));
			localSoapObject.addProperty("ventaId", Integer.valueOf(_factura.get_serverVentaId()));
			localSoapObject.addProperty("nitNuevo", Boolean.valueOf(_factura.is_nitNuevo()));
			localSoapObject.addProperty("nroFactura", Integer.valueOf(_factura.get_noFactura()));
			localSoapObject.addProperty("tipoNit", String.valueOf(_factura.get_tipoNit()));
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(INSERTFACTURA_SOAP_ACTION, localSoapSerializationEnvelope);
				
				soapResultado = ((SoapObject)localSoapSerializationEnvelope.getResponse());
				if(soapResultado!=null)
				{
					intResultado = Integer.parseInt(soapResultado.getPropertyAsString("Id"));
			        stringResultado = soapResultado.getPropertyAsString("Resultado");
				}
				
				if(stringResultado.equals("OK") && intResultado > 0)
				{
					WSInsertarFactura = true;
				}
	            return true;
			}
			catch (Exception localException)
			{
				WSInsertarFactura = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarFactura: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarFactura: " + localException.getMessage());
				}
				return true;
			}
		 }
		 
		 protected void onPostExecute(Boolean ejecutado)
		 {
			 if(pdEsperaInsertarFactura.isShowing())
			 {
				 pdEsperaInsertarFactura.dismiss();
			 }
			 
			 if(ejecutado)
			 {
				 if(WSInsertarFactura || (stringResultado != null && stringResultado.equals("Factura Repetida") && intResultado <=0)) 
				 {
					ModificarSincronizacionFactura(_factura.get_rowId(),true);
					theUtilidades.MostrarMensaje(getApplicationContext(), "Factura insertada.", 1);
					ObtenerFacturasNoSincronizadasForDisplay();
				 }
				 else
				 {
					 if(stringResultado != null)
					 {
						 theUtilidades.MostrarMensaje(getApplicationContext(), stringResultado, 1);
					 }
					 else
					 {
						 theUtilidades.MostrarMensaje(getApplicationContext(), "Factura no insertada.", 1);
					 }					 
				 }
			 }
			 else
			 {
				 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertarFactura.", 1);
				 return;
			 }
		 }
	 }
	
	private void ModificarSincronizacionFactura(int rowId,boolean estado)
	{
		int modificado = 0;
		try
		{
			modificado = new FacturaBLL().ModificarSincronizacionFactura(rowId, estado);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la factura: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al al modificar la sincronizacion de la factura: " + localException.getMessage());
			} 
		}
		
		if(modificado == 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion de la factura.", 1);
			return;
		}
	}
	
}
