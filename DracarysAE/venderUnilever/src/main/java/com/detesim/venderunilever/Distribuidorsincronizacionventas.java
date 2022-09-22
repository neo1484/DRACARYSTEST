package com.detesim.venderunilever;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import BLL.ClienteVentaBLL;
import BLL.FacturaBLL;
import BLL.MyLogBLL;
import BLL.PreventaProductoDistBLL;
import BLL.SincronizacionVentaBLL;
import BLL.VentaBLL;
import BLL.VentaProductoTempBLL;
import Clases.ClienteVenta;
import Clases.Factura;
import Clases.LoginEmpleado;
import Clases.PreventaProductoDist;
import Clases.SincronizacionVenta;
import Clases.VentaProductoTemp;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Distribuidorsincronizacionventas extends Activity implements OnClickListener
{
	LinearLayout llDistribuidorSincronizacionVentas;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
	ArrayList<SincronizacionVenta> listadoSincronizacionVenta;
	ArrayList<SincronizacionVenta> listadoSincronizacionVentaNoSincronizadas;
	
	ListView lvVentas;
	
	ProgressDialog pdCopiarPreVentaAVentaTemp;
	ProgressDialog pdObtenerVentaProductoTemp;
	ProgressDialog pdEliminarVentaProductoTemp;
	ProgressDialog pdVentaNoEntregada;
	ProgressDialog pdInsertarVenta;
	ProgressDialog pdModificarVentaProductoTemp;
	
	ClienteVenta clienteVenta;
	int preventaIdCopiada;
	boolean productosObtenidos;
	
	int nroItems = 0;
	float monto;
	float descuento;
	float montoFinal;
	double latitud;
	double longitud;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_distribuidorsincronizacionventas);
		
		llDistribuidorSincronizacionVentas = (LinearLayout)findViewById(R.id.DistribuidorSincronizacionVentasLinearLayout);
		lvVentas = (ListView)findViewById(R.id.lvDistribuidorSincronizacionVentasVentas);
		
		llDistribuidorSincronizacionVentas.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
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
			MostrarControlesVenta(false);
			return;
		}
		else
		{
			ObtenerVentasNoSincronizadasForDisplay();
		}
	}

	@Override
	public void onClick(View v)
	{
	}
	
	private void MostrarControlesVenta(boolean estado)
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
	}
	
	private void ObtenerVentasNoSincronizadasForDisplay()
	{
		listadoSincronizacionVenta = null;
		try
		{
			listadoSincronizacionVenta = new SincronizacionVentaBLL().ObtenerSincronizacionesVentaNosincronizadasAgrupadas();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la sincronizacionVentas no sincronizadas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la sincronizacionVentas no sincronizadas: " + localException.getMessage());
			} 
		}
		
		if(listadoSincronizacionVenta == null)
		{
			MostrarControlesVenta(false);
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron ventas que sincronizar.", 1);
			return;
		}
		else
		{
			LlenarSincronizacionesVentaListVIew();
			SincronizacionVentaItemListView();
		}	
	}
	
	private void LlenarSincronizacionesVentaListVIew()
	{
		MiAdaptadorSincronizacionVenta localMiAdaptadorSincronizacionVenta = new MiAdaptadorSincronizacionVenta(getApplicationContext());
		ListView localListView = lvVentas;
		if(listadoSincronizacionVenta == null)
		{
			localListView.setAdapter(null);
		}
		else
		{
			ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    	localLayoutParams.height = ((int)(30*getApplicationContext().getResources().getDisplayMetrics().density) * listadoSincronizacionVenta.size());
	    	localListView.setLayoutParams(localLayoutParams);
			localListView.setAdapter(localMiAdaptadorSincronizacionVenta);
		}
	}
	
	class MiAdaptadorSincronizacionVenta extends ArrayAdapter<SincronizacionVenta>
	{
		private Context _context;
		
		public MiAdaptadorSincronizacionVenta(Context context)
		{
			super(context,R.layout.disenio_distribuidorsincronizacionventa, listadoSincronizacionVenta);
			this._context = context;
		}
    
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View localView = convertView;
			
			if (localView == null) 
			{
				LayoutInflater layoutInflater = (LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				localView = layoutInflater.inflate(R.layout.disenio_distribuidorsincronizacionventa, parent, false);
			}
			
			TextView tvNombreCompleto = (TextView)localView.findViewById(R.id.tvDistribuidorSincronizacionVentaNombreCompleto);
			
			SincronizacionVenta actualSincronizacionVenta = listadoSincronizacionVenta.get(position);
			
			ClienteVenta localClienteVenta = null;
			try
			{
				localClienteVenta = new ClienteVentaBLL().ObtenerClienteVentaPor(actualSincronizacionVenta.get_clienteId());
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del clienteVenta por clienteId: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del clienteVenta por clienteId: " + localException.getMessage());
				} 
			}
			
			if(localClienteVenta == null)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del cliente venta.", 1);
				return null;
			}
			else
			{
				String tipoSincro = "";
				switch(actualSincronizacionVenta.get_tipoSincronizacion())
				{
				case 1:
					tipoSincro = "Venta total";
					break;
				case 2:
					tipoSincro = "Venta no entregada";
					break;
				case 3:
					tipoSincro = "Producto eliminado";
					break;
				case 4:
					tipoSincro = "Producto modificado";
					break;
				}
				tvNombreCompleto.setText(localClienteVenta.get_nombreCompleto() + " - " + tipoSincro);
			}
			
			return localView;
		}
	}
	
	private void SincronizacionVentaItemListView()
	{
	    ((ListView)findViewById(R.id.lvDistribuidorSincronizacionVentasVentas)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	    	{
	    		if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
	    		{
	    			preventaIdCopiada = 0;
	    			productosObtenidos = false;
    				ObtenerSincronizacionesVentaNoSincronizadasPorPreventaId(listadoSincronizacionVenta.get(position).get_preventaId());
	    		}
	    		else
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encuentra conectado a internet, intentelo mas tarde.", 1);
	    			return;
	    		}
	      }
	    });
	}
	
	private void ObtenerSincronizacionesVentaNoSincronizadasPorPreventaId(int preventaId)
	{
		listadoSincronizacionVentaNoSincronizadas = null;
		try
		{
			listadoSincronizacionVentaNoSincronizadas = new SincronizacionVentaBLL().ObtenerSincronizacionesVentaNosincronizadasPorPreventaId(
																preventaId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionVentasNoSincronizadas por preventaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionVentasNoSincronizadas por preventaId: " + localException.getMessage());
			} 
		}
		
		if(listadoSincronizacionVentaNoSincronizadas != null)
		{
			clienteVenta = ObtenerClienteVenta(listadoSincronizacionVentaNoSincronizadas.get(0).get_clienteId());
			SincronizarVenta();
		}
		else
		{
			ObtenerVentasNoSincronizadasForDisplay();
		}
	}
	
	private void SincronizarVenta()
	{
		if(preventaIdCopiada != listadoSincronizacionVentaNoSincronizadas.get(0).get_preventaId())
		{
			CopiarPreVentaAVentaTemp();
		}
		else
		{
			switch(listadoSincronizacionVentaNoSincronizadas.get(0).get_tipoSincronizacion())
			{
			case 1:
				FijarParametrosVenta(1);
				InsertarVenta();
				break;
			case 2:
				InsertarVentaNoEntregada();
				break;
			case 3:
				if(productosObtenidos)
				{
					if(listadoSincronizacionVentaNoSincronizadas.size() == 1)
					{
						FijarParametrosVenta(3);
					}
					
					EliminarProducto();
				}
				else
				{
					ObtenerVentaProductoTemp();
				}
				break;
			case 4:
				if(productosObtenidos)
				{
					if(listadoSincronizacionVentaNoSincronizadas.size() == 1)
					{
						FijarParametrosVenta(4);
					}
					
					ModificarProducto();
				}
				else
				{
					ObtenerVentaProductoTemp();
				}
				break;
			}
		}
	}
	
	public ClienteVenta ObtenerClienteVenta(int clienteId)
	{
		ClienteVenta theClienteVenta = null;
		try
		{
			theClienteVenta = new ClienteVentaBLL().ObtenerClienteVentaPor(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del clienteVenta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del clienteVenta: " + localException.getMessage());
			}
		}
		
		if(theClienteVenta == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo obtener el cliente de la venta.", 1);
			return null;
		}
		else
		{
			return theClienteVenta;
		}
	}

	private void CopiarPreVentaAVentaTemp()
	{
		pdCopiarPreVentaAVentaTemp = new ProgressDialog(this);
	    pdCopiarPreVentaAVentaTemp.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdCopiarPreVentaAVentaTemp.setMessage("Copiando informacion ...");
	    pdCopiarPreVentaAVentaTemp.setCancelable(false);
		pdCopiarPreVentaAVentaTemp.setCanceledOnTouchOutside(false);

	    WSCopiarPreVentaAVentaTemp localWSCopiarPreVentaAVentatemp = new WSCopiarPreVentaAVentaTemp();
	    try
	    {
	    	localWSCopiarPreVentaAVentatemp.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSCopairPreVentaAVentaTemp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSCopairPreVentaAVentaTemp: " + localException.getMessage());
	    	} 
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSCopairPreVentaAVentaTemp", 1);
	    }
	}
	
	private class WSCopiarPreVentaAVentaTemp extends AsyncTask<Void, Integer, Boolean>
	{
		String COPIARPREVENTA_METHOD_NAME = "CopiarPreVentaAVentaTemp";
		String COPIARPREVENTA_SOAP_ACTION = NAMESPACE + COPIARPREVENTA_METHOD_NAME;
		boolean WSCopiarPreVentaAVentaTemp = false;
		SoapObject soapObjects;
		int resultadoInt;
	    String resultadoString;
	    
	    protected void onPreExecute()
	    {
	    	pdCopiarPreVentaAVentaTemp.show();
	    }
	    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSCopiarPreVentaAVentaTemp = false;
			
			SoapObject localSoapObject = new SoapObject(NAMESPACE,COPIARPREVENTA_METHOD_NAME);
			localSoapObject.addProperty("preVentaId", listadoSincronizacionVentaNoSincronizadas.get(0).get_preventaId());
			localSoapObject.addProperty("distribuidorId", listadoSincronizacionVentaNoSincronizadas.get(0).get_distribuidorId());
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(COPIARPREVENTA_SOAP_ACTION, localSoapSerializationEnvelope);
				soapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
				if(soapObjects != null)
				{
					resultadoInt = Integer.parseInt(soapObjects.getPropertyAsString("Id"));;
					resultadoString = soapObjects.getPropertyAsString("Resultado");
				}
				
				if(resultadoString.equals("OK") && resultadoInt > 0)
				{
					WSCopiarPreVentaAVentaTemp = true;
				}
				return true;
			}
			catch (Exception localException)
			{
				WSCopiarPreVentaAVentaTemp = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSCopiarPreVentaAVentaTemp: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSCopiarPreVentaAVentaTemp: " + localException.getMessage());
				} 
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdCopiarPreVentaAVentaTemp.isShowing())
			{
				pdCopiarPreVentaAVentaTemp.dismiss();
			}

			if(WSCopiarPreVentaAVentaTemp)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"Copia de preventa insertada en el servidor.", 1);
				preventaIdCopiada = listadoSincronizacionVentaNoSincronizadas.get(0).get_preventaId();
				SincronizarVenta();
			}
		}
	}
	
	private void ObtenerVentaProductoTemp()
	{
		pdObtenerVentaProductoTemp = new ProgressDialog(this);
	    pdObtenerVentaProductoTemp.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdObtenerVentaProductoTemp.setMessage("Obteniendo productos venta ...");
	    pdObtenerVentaProductoTemp.setCancelable(false);
		pdObtenerVentaProductoTemp.setCanceledOnTouchOutside(false);

	    WSObtenerVentaProductoTemp localGetPreVentaProductoTemp = new WSObtenerVentaProductoTemp();
	    try
	    {
	    	localGetPreVentaProductoTemp.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPreVentaProductoTemp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPWSGetPreVentaProductoTemp: " + localException.getMessage());
	    	} 
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetPreVentaProductoTemp", 1);
	    }
	}
	
	private class WSObtenerVentaProductoTemp extends AsyncTask<Void, Integer, Boolean>
	{
		String GETPREVENTAPRODUCTOTEMP_METHOD_NAME = "GetVentaProductoTempByEmpleadoAndCliente";
		String GETPREVENTAPRODUCTOTEMP_SOAP_ACTION = NAMESPACE + GETPREVENTAPRODUCTOTEMP_METHOD_NAME;
		boolean WSObtenerVentaProductoTemp = false;
		SoapObject soapObjects;
		int totalItems;
		
		protected void onPreExecute()
	    {
			pdObtenerVentaProductoTemp.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerVentaProductoTemp = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,GETPREVENTAPRODUCTOTEMP_METHOD_NAME);
			localSoapObject.addProperty("clienteId", listadoSincronizacionVentaNoSincronizadas.get(0).get_clienteId());
			localSoapObject.addProperty("empleadoId", listadoSincronizacionVentaNoSincronizadas.get(0).get_distribuidorId());
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
	        localSoapSerializationEnvelope.dotNet = true;
	        localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
	        HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(GETPREVENTAPRODUCTOTEMP_SOAP_ACTION, localSoapSerializationEnvelope);
				soapObjects = (SoapObject)((SoapObject)localSoapSerializationEnvelope.bodyIn).getProperty("GetVentaProductoTempByEmpleadoAndClienteResult");
				if(soapObjects!=null)
				{
					totalItems = soapObjects.getPropertyCount();
				}
				
				if(totalItems > 0)
				{
					WSObtenerVentaProductoTemp = true;
				}
				
				return true;
			}
			catch(Exception localException)
			{
				WSObtenerVentaProductoTemp = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetVentaProductoTempBy: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetVentaProductoTempBy: " + localException.getMessage());
				} 
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdObtenerVentaProductoTemp.isShowing())
			{
				pdObtenerVentaProductoTemp.dismiss();
			}
			
			if(WSObtenerVentaProductoTemp)
			{
				if(BorrarVentasProductoTemp())
				{
					for(int i=0; i<totalItems; i++)
					{
						SoapObject localSoapObject = (SoapObject)soapObjects.getProperty(i);
						long l=0;
						try
						{
							
							l = new VentaProductoTempBLL().InsertarVentaProductoTemp(
									Integer.parseInt(localSoapObject.getPropertyAsString("TempId")), 
									Integer.parseInt(localSoapObject.getPropertyAsString("ProductoId")), 
									Integer.parseInt(localSoapObject.getPropertyAsString("PromocionId")), 
									Integer.parseInt(localSoapObject.getPropertyAsString("Cantidad")), 
									Integer.parseInt(localSoapObject.getPropertyAsString("CantidadNueva")), 
									Integer.parseInt(localSoapObject.getPropertyAsString("CantidadPaquete")), 
									Integer.parseInt(localSoapObject.getPropertyAsString("CantidadPaqueteNueva")), 
									Float.parseFloat(localSoapObject.getPropertyAsString("Monto")), 
									Float.parseFloat(localSoapObject.getPropertyAsString("MontoNuevo")), 
									Float.parseFloat(localSoapObject.getPropertyAsString("Descuento")), 
									Float.parseFloat(localSoapObject.getPropertyAsString("DescuentoNuevo")), 
									Float.parseFloat(localSoapObject.getPropertyAsString("MontoFinal")), 
									Float.parseFloat(localSoapObject.getPropertyAsString("MontoFinalNuevo")), 
									Integer.parseInt(localSoapObject.getPropertyAsString("EmpleadoId")), 
									Integer.parseInt(localSoapObject.getPropertyAsString("ClienteId")), 
									Integer.parseInt(localSoapObject.getPropertyAsString("MotivoId")),
									0,
									Integer.parseInt(localSoapObject.getPropertyAsString("CostoId")),
									Integer.parseInt(localSoapObject.getPropertyAsString("PrecioId")),
									Float.parseFloat(localSoapObject.getPropertyAsString("DescuentoCanal")),
									Float.parseFloat(localSoapObject.getPropertyAsString("DescuentoAjuste")),
									Integer.parseInt(localSoapObject.getPropertyAsString("CanalRutaPrecioId")),
									Float.parseFloat(localSoapObject.getPropertyAsString("DescuentoProntoPago")));
							
						}
						catch(Exception localException)
						{
							if(localException.getMessage() == null || localException.getMessage().isEmpty())
							{
						        myLog.InsertarLog("App",this.toString(),1,"Error al insertar el ventaProductoTemp: vacio");
							}
							else
							{
								myLog.InsertarLog("App",this.toString(),1,"Error al insertar el ventaProductoTemp: " + localException.getMessage());
							} 
						}
						
						if(l==0)
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la venta producto temporal.", 1);
							return;
						}
					}
					
					productosObtenidos = true;
					theUtilidades.MostrarMensaje(getApplicationContext(),"Items de la preventa obtenidos del servidor.", 1);
					SincronizarVenta();
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar la venta producto temporal.", 1);
					return;
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los productos de la venta.", 1);
				return;
			}
		}
    }
	
	private boolean BorrarVentasProductoTemp()
	{
		try
		{
			boolean bool = new VentaProductoTempBLL().BorrarVentasProductoTemp();
			return bool;
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventasProductoTemp: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las venatsProductoTemp: " + localException.getMessage());
			} 
			return false;
		}
	}
	
	private void EliminarProducto()
	{
		VentaProductoTemp ventaProductoTemp = null;
		try
		{
			ventaProductoTemp = new VentaProductoTempBLL().ObtenerVentaProductoTempPorProductoPromocion(
					listadoSincronizacionVentaNoSincronizadas.get(0).get_productoId(),
					listadoSincronizacionVentaNoSincronizadas.get(0).get_promocionId());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ventaProductoTemp por productoId y promocionId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ventaProductoTemp por productoId y promocionId: " + localException.getMessage());
			} 
		}
		
		if(ventaProductoTemp == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la venta temporal por porductoId y promocionId.", 1);
			return;
		}
		else
		{
			EliminarVentaProductoTemp(ventaProductoTemp.get_tempId());
		}
	}
	
	private void EliminarVentaProductoTemp(int tempId)
	{
		pdEliminarVentaProductoTemp = new ProgressDialog(this);
		pdEliminarVentaProductoTemp.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEliminarVentaProductoTemp.setMessage("Eliminando producto ...");
		pdEliminarVentaProductoTemp.setCancelable(false);
		pdEliminarVentaProductoTemp.setCanceledOnTouchOutside(false);

		WSEliminarVentaProductoTemp localWSEliminarVentaProductoTemp = new WSEliminarVentaProductoTemp(tempId);
		try
		{
			localWSEliminarVentaProductoTemp.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSEliminarVentaProductoTemp: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSEliminarVentaProductoTemp: " + localException.getMessage());
			} 
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSEliminarVentaProductoTemp.", 1);
		}
	}
	
	private class WSEliminarVentaProductoTemp extends AsyncTask<Void, Integer, Boolean>
	{
		String ELIMINARVENTAPRODUCTOTEMP_METHOD_NAME = "DeleteVentaProductoTemp";
		String ELIMINARVENTAPRODUCTOTEMP_SOAP_ACTION = NAMESPACE + ELIMINARVENTAPRODUCTOTEMP_METHOD_NAME;
		
		boolean WSEliminarVentaProductoTemp ;
		SoapObject soapObjects;
		int resultadoInt;
		String resultadoString;
		
		private int _tempId;
		
		public WSEliminarVentaProductoTemp(int tempId)
		{
			this._tempId = tempId;
		}
    
		protected void onPreExecute()
	    {
			pdEliminarVentaProductoTemp.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSEliminarVentaProductoTemp = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,ELIMINARVENTAPRODUCTOTEMP_METHOD_NAME);
	        localSoapObject.addProperty("tempId", _tempId);
	        localSoapObject.addProperty("motivoId", listadoSincronizacionVentaNoSincronizadas.get(0).get_motivoId());
			
	        SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
	        localSoapSerializationEnvelope.dotNet = true;
	        localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
	        HttpTransportSE localHttpTransportSE =new HttpTransportSE(URL); 
	        try
	        {
	        	localHttpTransportSE.call(ELIMINARVENTAPRODUCTOTEMP_SOAP_ACTION, localSoapSerializationEnvelope);
	        	
	        	soapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
	        	if(soapObjects !=null)
	        	{
	        		resultadoInt = Integer.parseInt(soapObjects.getPropertyAsString("Id"));
	        		resultadoString = soapObjects.getPropertyAsString("Resultado");
	        	}
	        	
	        	if(resultadoInt > 0 && resultadoString.equals("OK"))
	        	{
	        		WSEliminarVentaProductoTemp = true;
	        	}
	        	return true;
	        }
	        catch (Exception localException)
	        {
	        	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	        	{
	                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSDeleteVentaProductoTemp: vacio");
	        	}
	        	else
	        	{
	        		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSDeleteVentaProductoTemp: " + localException.getMessage());
	        	} 
	        	return true;
	        }
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEliminarVentaProductoTemp.isShowing())
			{
				pdEliminarVentaProductoTemp.dismiss();
			}

			if(WSEliminarVentaProductoTemp) 
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"Producto eliminado del servidor.",1);
				
				if(listadoSincronizacionVentaNoSincronizadas.size()==1)
				{
					InsertarVenta();
				}
				else
				{
					if(ModificarSincronizacionVenta())
					{
						ObtenerSincronizacionesVentaNoSincronizadasPorPreventaId(listadoSincronizacionVentaNoSincronizadas.get(0).get_preventaId());
					}
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"El producto no pudo ser eliminado del servidor.", 1);
				return;
			}	
		}
	}
	
	private void InsertarVenta()
	{
	    pdInsertarVenta = new ProgressDialog(this);
    	pdInsertarVenta.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdInsertarVenta.setMessage("Insertando venta ...");
	    pdInsertarVenta.setCancelable(false);
	    pdInsertarVenta.setCanceledOnTouchOutside(false);

	    WSInsertarVenta localWSInsertarVenta = new WSInsertarVenta();
	    try
	    {
	    	localWSInsertarVenta.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarVenta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarVenta: " + localException.getMessage());
	    	} 
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSInsertarVenta no se ejecuto correctamente.", 1);
	    }
	}
	
	private class WSInsertarVenta extends AsyncTask<Void, Integer, Boolean>
	{
		String VENTA_METHOD_NAME = "InsertVenta";
		String VENTA_SOAP_ACTION = NAMESPACE + VENTA_METHOD_NAME;
		
		boolean WSInsertarVenta;
		SoapObject soapObjects;
		int resultadoInt;
		String resultadoString;
	
    
		protected void onPreExecute()
	    {
			pdInsertarVenta.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSInsertarVenta = false;
      
            SoapObject localSoapObject = new SoapObject(NAMESPACE,VENTA_METHOD_NAME);
            localSoapObject.addProperty("clienteId", listadoSincronizacionVentaNoSincronizadas.get(0).get_clienteId());
            localSoapObject.addProperty("distribuidorId", listadoSincronizacionVentaNoSincronizadas.get(0).get_distribuidorId());
            localSoapObject.addProperty("monto", String.valueOf(monto));
            localSoapObject.addProperty("descuento", String.valueOf(descuento));
            localSoapObject.addProperty("montoFinal", String.valueOf(montoFinal));
            localSoapObject.addProperty("tipoPagoId", listadoSincronizacionVentaNoSincronizadas.get(0).get_tipoPagoId());
            localSoapObject.addProperty("latitud", String.valueOf(latitud));
            localSoapObject.addProperty("longitud", String.valueOf(longitud));
            localSoapObject.addProperty("dia", listadoSincronizacionVentaNoSincronizadas.get(0).get_dia());
            localSoapObject.addProperty("mes", listadoSincronizacionVentaNoSincronizadas.get(0).get_mes());
            localSoapObject.addProperty("anio", listadoSincronizacionVentaNoSincronizadas.get(0).get_anio());
            localSoapObject.addProperty("preventaId", listadoSincronizacionVentaNoSincronizadas.get(0).get_preventaId());
            localSoapObject.addProperty("numeroItems", nroItems);
            localSoapObject.addProperty("almacenId", loginEmpleado.get_almacenId());
            localSoapObject.addProperty("hora", listadoSincronizacionVentaNoSincronizadas.get(0).get_hora());
            localSoapObject.addProperty("minuto", listadoSincronizacionVentaNoSincronizadas.get(0).get_minuto());
            localSoapObject.addProperty("rutaId",(clienteVenta.get_rutaId()));
			localSoapObject.addProperty("diaId",clienteVenta.get_diaId());
			localSoapObject.addProperty("observacion",listadoSincronizacionVentaNoSincronizadas.get(0).get_observacion());
			localSoapObject.addProperty("dosificacionId",listadoSincronizacionVentaNoSincronizadas.get(0).get_dosificacionId());
			localSoapObject.addProperty("descuentoCanal",String.valueOf(listadoSincronizacionVentaNoSincronizadas.get(0).get_descuentoCanal()));
			localSoapObject.addProperty("descuentoAjuste",String.valueOf(listadoSincronizacionVentaNoSincronizadas.get(0).get_descuentoAjuste()));
			localSoapObject.addProperty("descuentoProntoPago",String.valueOf(listadoSincronizacionVentaNoSincronizadas.get(0).get_descuentoProntoPago()));
			localSoapObject.addProperty("descuentoObjetivo",String.valueOf(listadoSincronizacionVentaNoSincronizadas.get(0).get_descuentoObjetivo()));
			localSoapObject.addProperty("formaPagoId",listadoSincronizacionVentaNoSincronizadas.get(0).get_formaPagoId());
			localSoapObject.addProperty("codTransferencia",listadoSincronizacionVentaNoSincronizadas.get(0).get_codTransferencia());
			localSoapObject.addProperty("fromApk",String.valueOf(true));
			localSoapObject.addProperty("fromShopping",String.valueOf(listadoSincronizacionVentaNoSincronizadas.get(0).is_fromShopping()));
      
            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
            try
            {
            	localHttpTransportSE.call(VENTA_SOAP_ACTION, localSoapSerializationEnvelope);
            	soapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
            	if(soapObjects !=null)
            	{
            		resultadoInt = Integer.parseInt(soapObjects.getPropertyAsString("Id"));
            		resultadoString = soapObjects.getPropertyAsString("Resultado"); 
            	}
            	
            	if(resultadoInt > 0 && resultadoString.equals("OK"))
            	{
            		WSInsertarVenta = true;
            	}
        
            	return true;
            }
            catch (Exception localException)
            {
            	if(localException.getMessage() == null || localException.getMessage().isEmpty())
            	{
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarVenta: vacio");
            	}
            	else
            	{
            		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarVenta: " + localException.getMessage());
            	} 

            	return true;
            }
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdInsertarVenta.isShowing())
			{
				pdInsertarVenta.dismiss();
			}
    	
			if(ejecutado)
			{
				if(WSInsertarVenta 
						|| (resultadoString != null && resultadoString.equals("Venta Repetida"))
						|| (resultadoString != null && resultadoString.equals("Entrega Repetida")))
				{
					long l = 0;
					 try
					 {
						 l = new VentaBLL().ModificarSincronizacionVenta(listadoSincronizacionVentaNoSincronizadas.get(0).get_preventaId(),resultadoInt,true);
					 }
					 catch (Exception localException)
					 {
						 if(localException.getMessage() == null || localException.getMessage().isEmpty())
						 {
							 myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la venta por RowId: vacio");
						 }
						 else
						 {
							 myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la venta por RowId: " + localException.getMessage());
						 } 
					 }
							
					 if(l==0)
					 {
						 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion de la venta.", 1);
						 return;
					 }
					 
					 l=0;
					 try
					 {
						 l = new SincronizacionVentaBLL().ModificarSincronizacionVentaSincronizacion(listadoSincronizacionVentaNoSincronizadas.get(0).get_id(), true);
					 }
					 catch(Exception localException)
					 {
						 if(localException.getMessage() == null || localException.getMessage().isEmpty())
						 {
							 myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la sincronizacionVenta por ventaId: vacio");
						 }
						 else
						 {
							 myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la SincronizacionVenta por ventaId: " + localException.getMessage());
						 } 
					 }
					 
					 if(l==0)
					 {
						 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion de la sincronizacion venta por VentaId.", 1);
						 return;
					 }
					 
					 l=0;
					 try
					 {
						 l = new SincronizacionVentaBLL().ModificarSincronizacionVentaVentaIdServerPorVentaId(
								 										listadoSincronizacionVentaNoSincronizadas.get(0).get_ventaId(), resultadoInt);
					 }
					 catch(Exception localException)
					 {
						 if(localException.getMessage() == null || localException.getMessage().isEmpty())
						 {
							 myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaIdServer de la sincronizacionVenta por ventaId: vacio");
						 }
						 else
						 {
							 myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaIdServer de la SincronizacionVenta por ventaId: " + localException.getMessage());
						 } 
					 }
					 
					 if(l==0)
					 {
						 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la ventaIdServer de la sincronizacionVenta por VentaId.", 1);
						 return;
					 }
					 
					 if(ObtenerFacturaPorVentaId(listadoSincronizacionVentaNoSincronizadas.get(0).get_ventaId()))
					 {
						 l=0;
						 try
						 {
							 l = new FacturaBLL().ModificarFacturaServerVentaIdPorVentaId(listadoSincronizacionVentaNoSincronizadas.get(0).get_ventaId(),resultadoInt);
						 }
						 catch(Exception localException)
						 {
							 if(localException.getMessage() == null || localException.getMessage().isEmpty())
							 {
								 myLog.InsertarLog("App",this.toString(),1,"Error al modificar ventaIdServer de la sincronizacionVenta por ventaId: vacio");
							 }
							 else
							 {
								 myLog.InsertarLog("App",this.toString(),1,"Error al modificar ventaIdServer de la sincronizacionVenta por ventaId: " + localException.getMessage());
							 } 
						 }
						 
						 if(l==0)
						 {
							 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar ventaIdServer de la sincronizacionVenta por VentaId.", 1);
							 return;
						 } 
					 }

					theUtilidades.MostrarMensaje(getApplicationContext(), "Venta insertada.", 1);
					ObtenerVentasNoSincronizadasForDisplay();
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo ejecutar el webservice WSInsertVenta", 1);
					MostrarPantallaMenuDistribuidor();
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertVenta.", 1);
				return;
			}
		}
	}
	
	private boolean ObtenerFacturaPorVentaId(int ventaId)
	{
		boolean verificado = false;
		Factura localFactura = null;
		try
		{
			localFactura = new FacturaBLL().ObtenerFacturaPorVentaId(ventaId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al obtener la factura por ventaId: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al obtener la factura por ventaId: " + localException.getMessage());
			 } 
		}
		
		if(localFactura == null)
		{
			return verificado;
		}
		else
		{
			return true;
		}
	}
	
	private void InsertarVentaNoEntregada()
	{
		pdVentaNoEntregada = new ProgressDialog(this);
		pdVentaNoEntregada.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdVentaNoEntregada.setMessage("Insertando venta no entregada ...");
		pdVentaNoEntregada.setCancelable(false);
		pdVentaNoEntregada.setCanceledOnTouchOutside(false);
		
		WSVentaNoEntregada localWSVentaNoEntregada = new WSVentaNoEntregada();
		try
		{
			localWSVentaNoEntregada.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSVentaNoEntregada: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSVentaNoEntregada: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSVentaNoEntregada", 1);
		}
	}
	
	private class WSVentaNoEntregada extends AsyncTask<Void, Integer, Boolean>
	{
		String VENTANOENTREGADA_METHOD_NAME = "VentaNoEntregada";
		String VENTANOENTREGADA_SOAP_ACTION = NAMESPACE + VENTANOENTREGADA_METHOD_NAME;
		
		boolean WSVentaNoEntregada = false;
		SoapObject soapObjects;
		int resultadoInt;
		String resultadoString;
		
		protected void onPreExecute()
	    {
	    	pdVentaNoEntregada.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSVentaNoEntregada = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,VENTANOENTREGADA_METHOD_NAME);
			
			localSoapObject.addProperty("preVentaId", listadoSincronizacionVentaNoSincronizadas.get(0).get_preventaId());
			localSoapObject.addProperty("distribuidorId", listadoSincronizacionVentaNoSincronizadas.get(0).get_distribuidorId());
			localSoapObject.addProperty("clienteId", listadoSincronizacionVentaNoSincronizadas.get(0).get_clienteId());
			localSoapObject.addProperty("anio", listadoSincronizacionVentaNoSincronizadas.get(0).get_anio());
			localSoapObject.addProperty("mes", listadoSincronizacionVentaNoSincronizadas.get(0).get_mes());
			localSoapObject.addProperty("dia", listadoSincronizacionVentaNoSincronizadas.get(0).get_dia());
			localSoapObject.addProperty("motivoId", listadoSincronizacionVentaNoSincronizadas.get(0).get_motivoId());
    		
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
    			
			try
			{
				localHttpTransportSE.call(VENTANOENTREGADA_SOAP_ACTION, localSoapSerializationEnvelope);
				soapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
					
				if(soapObjects != null)
				{
					resultadoInt = Integer.parseInt(soapObjects.getPropertyAsString("Id"));
					resultadoString = soapObjects.getPropertyAsString("Resultado"); 
				}
					
				if(resultadoInt > 0 && resultadoString.equals("OK"))
				{
					WSVentaNoEntregada = true;
				}
				
				return true;
			}
			catch(Exception localException)
			{
				WSVentaNoEntregada = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSVentaNoEntregada: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSVentaNoEntregada: " + localException.getMessage());
				} 
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdVentaNoEntregada.isShowing())
			{
				pdVentaNoEntregada.dismiss();
			}
    	
			if(WSVentaNoEntregada 
					|| (resultadoString != null && resultadoString.equals("No Entrega Repetida") && resultadoInt <=0)) 
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"Venta No entregada, registrada en el server.", 1);
		
				if(ModificarSincronizacionVenta())
				{
					ObtenerSincronizacionesVentaNoSincronizadasPorPreventaId(listadoSincronizacionVentaNoSincronizadas.get(0).get_preventaId());
				}  
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), resultadoString, 1);
				return;
			}
		}
	}
	
	private void ModificarProducto()
	{
		VentaProductoTemp ventaProductoTemp = null;
		try
		{
			ventaProductoTemp = new VentaProductoTempBLL().ObtenerVentaProductoTempPorProductoPromocion(
					listadoSincronizacionVentaNoSincronizadas.get(0).get_productoId(),
					listadoSincronizacionVentaNoSincronizadas.get(0).get_promocionId());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ventaProductoTemp por productoId y promocionId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ventaProductoTemp por productoId y promocionId: " + localException.getMessage());
			} 
		}
		
		if(ventaProductoTemp == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la venta temporal por porductoId y promocionId.", 1);
			return;
		}
		else
		{
			ModificarVentaProductoTemp(ventaProductoTemp.get_tempId());
		}
	}
	
	private void ModificarVentaProductoTemp(int tempId)
	{
		pdModificarVentaProductoTemp = new ProgressDialog(this);
	    pdModificarVentaProductoTemp.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdModificarVentaProductoTemp.setMessage("Modificando venta ...");
	    pdModificarVentaProductoTemp.setCancelable(false);
	    pdModificarVentaProductoTemp.setCanceledOnTouchOutside(false);

	    WSModificarVentaProductoTemp localWSModificarVentaProductoTemp = new WSModificarVentaProductoTemp(tempId);
	    try
	    {
	    	localWSModificarVentaProductoTemp.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSModificarVentaProductoTemp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSModificarVentaProductoTemp: " + localException.getMessage());
	    	} 
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejectuar el webservice WSModificarVentaProductoTemp.", 1);
	    }
	}
	
	private class WSModificarVentaProductoTemp extends AsyncTask<Void, Integer, Boolean>
	{
		String MODIFICARVENTAPRODUCTOTEMP_METHOD_NAME = "UpdateVentaProductoTemp";
		String MODIFICARVENTAPRODUCTOTEMP_SOAP_ACTION = NAMESPACE + MODIFICARVENTAPRODUCTOTEMP_METHOD_NAME;
		
		private int _tempId;
		
		boolean WSModificarVentaProductoTemp = false;
		SoapObject soapObjects;
		int resultadoInt;
		String resultadoString;
		
		public WSModificarVentaProductoTemp(int tempId)
		{
			this._tempId = tempId;
		}
    
		protected void onPreExecute()
	    {
			pdModificarVentaProductoTemp.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSModificarVentaProductoTemp = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,MODIFICARVENTAPRODUCTOTEMP_METHOD_NAME);
	        localSoapObject.addProperty("tempId", _tempId);
	        localSoapObject.addProperty("cantidadNueva", listadoSincronizacionVentaNoSincronizadas.get(0).get_cantidadNueva());
	        localSoapObject.addProperty("cantidadPaqueteNueva", listadoSincronizacionVentaNoSincronizadas.get(0).get_cantidadPaqueteNueva());
	        localSoapObject.addProperty("montoNuevo", String.valueOf(listadoSincronizacionVentaNoSincronizadas.get(0).get_montoNuevo()));
	        localSoapObject.addProperty("descuentoNuevo", String.valueOf(listadoSincronizacionVentaNoSincronizadas.get(0).get_descuentoNuevo()));
	        localSoapObject.addProperty("montoFinalNuevo", String.valueOf(listadoSincronizacionVentaNoSincronizadas.get(0).get_montoFinalNuevo()));
	        localSoapObject.addProperty("motivoId", listadoSincronizacionVentaNoSincronizadas.get(0).get_motivoId());
	        localSoapObject.addProperty("costoNuevoId", String.valueOf(listadoSincronizacionVentaNoSincronizadas.get(0).get_costoId()));
	        localSoapObject.addProperty("precioNuevoId", String.valueOf(listadoSincronizacionVentaNoSincronizadas.get(0).get_precioId()));
	        localSoapObject.addProperty("descuentoCanalNuevo", String.valueOf(listadoSincronizacionVentaNoSincronizadas.get(0).get_descuentoCanal()));
	        localSoapObject.addProperty("descuentoAjusteNuevo", String.valueOf(listadoSincronizacionVentaNoSincronizadas.get(0).get_descuentoAjuste()));
	        localSoapObject.addProperty("canalRutaPrecioNuevoId", listadoSincronizacionVentaNoSincronizadas.get(0).get_canalPrecioRutaId());
        
	        SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
	        localSoapSerializationEnvelope.dotNet = true;
	        localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
	        HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
	        try
	        {
	        	localHttpTransportSE.call(MODIFICARVENTAPRODUCTOTEMP_SOAP_ACTION,localSoapSerializationEnvelope);
	        	
	        	soapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
	        	if(soapObjects !=null)
	        	{
	        		resultadoInt = Integer.parseInt(soapObjects.getPropertyAsString("Id"));
	        		resultadoString = soapObjects.getPropertyAsString("Resultado");
	        	}
	        	
	        	if(resultadoInt > 0 && resultadoString.equals("OK"))
	        	{
	        		WSModificarVentaProductoTemp = true;
	        	}
	        	
	        	return true;
	        }
	        catch (Exception localException)
	        {
	        	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	        	{
	                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSUpdateVentaProductoTemp: vacio");
	        	}
	        	else
	        	{
	        		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSUpdateVentaProductoTemp: " + localException.getMessage());
	        	} 
	        	return true;
	        }
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdModificarVentaProductoTemp.isShowing())
			{
				pdModificarVentaProductoTemp.dismiss();
			}
			
			if(WSModificarVentaProductoTemp)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "Cantidad del producto modificada en el servidor.", 1);
				
				if(listadoSincronizacionVentaNoSincronizadas.size()==1)
				{
					InsertarVenta();
				}
				else
				{
					if(ModificarSincronizacionVenta())
					{
						ObtenerSincronizacionesVentaNoSincronizadasPorPreventaId(listadoSincronizacionVentaNoSincronizadas.get(0).get_preventaId());
					}
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la cantidad del producto del servidor.", 1);
				return;
			}
		}
    }
	
	private boolean ModificarSincronizacionVenta()
	{
		boolean modificado = false;
		
		int intModificado = 0;
		try
		{
			intModificado = new SincronizacionVentaBLL().ModificarSincronizacionVentaSincronizacion(
					listadoSincronizacionVentaNoSincronizadas.get(0).get_id(), true);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la ventaSincronizacion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la ventaSincronizacion: " + localException.getMessage());
	    	}
		}
		
		if(intModificado==0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo sincronizar la venta.", 1);
			return false;
		}
		else
		{
			modificado = true;
		}
		
		return modificado;
	}

	private void FijarParametrosVenta(int tipoSincro)
	{
		ArrayList<PreventaProductoDist> listadoPreventaProductoDist = null;
		try
		{
			listadoPreventaProductoDist = new PreventaProductoDistBLL().ObtenerPreventasProductoDistPor(listadoSincronizacionVentaNoSincronizadas.get(0).get_preventaId());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la sincronizacionVentas por preventaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la sincronizacionVentas por preventaId: " + localException.getMessage());
			} 
		}
		
		if(listadoPreventaProductoDist == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la sincronizacion venta por preventaId.", 1);
			return;
		}
		else
		{
			if(tipoSincro == 1)
			{
				nroItems = listadoSincronizacionVentaNoSincronizadas.get(0).get_numeroDeItems();
				monto = listadoSincronizacionVentaNoSincronizadas.get(0).get_monto();
				descuento = listadoSincronizacionVentaNoSincronizadas.get(0).get_descuento();
				montoFinal = listadoSincronizacionVentaNoSincronizadas.get(0).get_montoFinal();
				latitud = listadoSincronizacionVentaNoSincronizadas.get(0).get_latitud();
				longitud = listadoSincronizacionVentaNoSincronizadas.get(0).get_longitud();
			}
			
			if(tipoSincro > 1)
			{
				monto = 0;
				descuento = 0;
				montoFinal = 0;
				nroItems = 0;
				
				for(PreventaProductoDist item : listadoPreventaProductoDist)
				{
					SincronizacionVenta sincroVentaModificada = null;
					try
					{
						sincroVentaModificada = new SincronizacionVentaBLL().ObtenerSincronizacionesVentasModificadasPor(
								item.get_preventaId(),item.get_productoId(),item.get_promocionId());
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la sincronizacionVentas modificadas: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener la sincronizacionVentas modificadas: " + localException.getMessage());
						} 
					}
					
					if(sincroVentaModificada == null)
					{
						monto += item.get_monto();
						descuento += item.get_descuento();
						montoFinal += item.get_montoFinal();
						nroItems++;
					}
					else
					{
						if(sincroVentaModificada.get_tipoSincronizacion() == 4)
						{
							monto += sincroVentaModificada.get_montoNuevo();
							descuento +=sincroVentaModificada.get_descuentoNuevo();
							montoFinal +=sincroVentaModificada.get_montoFinalNuevo();
							nroItems++;
						}
					}
				}
							
				latitud = listadoSincronizacionVentaNoSincronizadas.get(0).get_latitud();
				longitud = listadoSincronizacionVentaNoSincronizadas.get(0).get_longitud();
			}
		}
	}
	
	private void MostrarPantallaMenuDistribuidor()
	{
		Intent localIntent = new Intent(this, Menudistribuidor.class);
	    localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(localIntent);
	}
}
