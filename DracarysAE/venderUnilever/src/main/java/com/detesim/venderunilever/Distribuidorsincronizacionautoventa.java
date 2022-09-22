package com.detesim.venderunilever;

import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import BLL.ClienteVentaBLL;
import BLL.FacturaBLL;
import BLL.MyLogBLL;
import BLL.SincronizacionVentaBLL;
import BLL.VentaBLL;
import BLL.VentaProductoBLL;
import Clases.ClienteVenta;
import Clases.Factura;
import Clases.LoginEmpleado;
import Clases.SincronizacionVenta;
import Clases.VentaProducto;
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

public class Distribuidorsincronizacionautoventa extends Activity implements OnClickListener
{
	LinearLayout llDistribuidorSincronizacionAutoventa;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;

	ArrayList<SincronizacionVenta> listadoSincronizacionVenta;
	ArrayList<SincronizacionVenta> listadoSincronizacionVentaNoSincronizadas;
	ArrayList<VentaProducto> listadoVentaProductoNoSincronizado;
	
	ListView lvAutoventas;
	ProgressDialog pdInsertarAutoventa;
	ProgressDialog pdInsertarAutoventaProductoTemp;
	ProgressDialog pdVentaNoEntregada;
	
	int preventaIdCopiada;	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_distribuidorsincronizacionautoventa);
		
		llDistribuidorSincronizacionAutoventa = (LinearLayout)findViewById(R.id.DistribuidorSincronizacionAutoventaLinearLayout);
		lvAutoventas = (ListView)findViewById(R.id.lvDistribuidorSincronizacionAutoventas);
		
		llDistribuidorSincronizacionAutoventa.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
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
			ObtenerAutoventasNoSincronizadasForDisplay();
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
		
		lvAutoventas.setVisibility(visibility);
	}

	private void ObtenerAutoventasNoSincronizadasForDisplay()
	{
		listadoSincronizacionVenta = null;
		try
		{
			listadoSincronizacionVenta = new SincronizacionVentaBLL().ObtenerSincronizacionesAutoVentaNosincronizadasAgrupadas();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la sincronizacionAutoventa no sincronizadas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la sincronizacionAutoventa no sincronizadas: " + localException.getMessage());
			} 
		}
		
		if(listadoSincronizacionVenta == null)
		{
			MostrarControlesVenta(false);
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron autoventas que sincronizar.", 1);
			return;
		}
		else
		{
			LlenarSincronizacionesAutoventaListVIew();
			SincronizacionAutoventaItemListView();
		}	
	}

	private void LlenarSincronizacionesAutoventaListVIew()
	{
		MiAdaptadorSincronizacionVenta localMiAdaptadorSincronizacionVenta = new MiAdaptadorSincronizacionVenta(getApplicationContext());
		ListView localListView = lvAutoventas;
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

			tvNombreCompleto.setText(localClienteVenta.get_nombreCompleto() + " - " + String.valueOf(actualSincronizacionVenta.get_montoFinal()));

			return localView;
		}
	}
	
	private void SincronizacionAutoventaItemListView()
	{
	    ((ListView)findViewById(R.id.lvDistribuidorSincronizacionAutoventas)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	    	{
	    		if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
	    		{
	    			preventaIdCopiada = 0;
	    			if(listadoSincronizacionVenta.get(position).get_ventaId() > 0)
	    			{
	    				ObtenerSincronizacionesVentaNoSincronizadasPorVentaId(listadoSincronizacionVenta.get(position).get_ventaId());
	    			}
	    			else
	    			{
	    				SincronizarAutoventaNoEntregada(listadoSincronizacionVenta.get(position));
	    			}
	    		}
	    		else
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encuentra conectado a internet, intentelo mas tarde.", 1);
	    			return;
	    		}
	      }
	    });
	}
	
	private void ObtenerSincronizacionesVentaNoSincronizadasPorVentaId(int ventaId)
	{
		listadoSincronizacionVentaNoSincronizadas = null;
		try
		{
			listadoSincronizacionVentaNoSincronizadas = new SincronizacionVentaBLL().ObtenerSincronizacionesVentaNosincronizadaPorVentaId(ventaId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionVentas no sincronizadas por ventaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionVentas no sincronizadas por ventaId: " + localException.getMessage());
			} 
		}
		
		if(listadoSincronizacionVentaNoSincronizadas != null)
		{
			SincronizarVenta();
		}
		else
		{
			ObtenerAutoventasNoSincronizadasForDisplay();
		}
	}
	
	private void SincronizarVenta()
	{
		if(listadoSincronizacionVentaNoSincronizadas.get(0).get_clienteId() < 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "Antes de sincronizar la autoventa, debe sincronizar el cliente.", 1);
			return;
		}
		else
		{
			if(preventaIdCopiada != listadoSincronizacionVentaNoSincronizadas.get(0).get_ventaId())
			{
				if(ObtenerVentaProductoNoSincronizados(listadoSincronizacionVentaNoSincronizadas.get(0).get_ventaId()))
				{
					CopiarVentaAAutoventaTemp();
				}
				else
				{
					switch(listadoSincronizacionVentaNoSincronizadas.get(0).get_tipoSincronizacion())
					{
					case 5:
						SincronizarAutoventa(listadoSincronizacionVentaNoSincronizadas.get(0),ObtenerClienteVenta(listadoSincronizacionVentaNoSincronizadas.get(0).get_clienteId()));
						break;
					}
				}
			}
			else
			{
				switch(listadoSincronizacionVentaNoSincronizadas.get(0).get_tipoSincronizacion())
				{
				case 5:
					SincronizarAutoventa(listadoSincronizacionVentaNoSincronizadas.get(0),ObtenerClienteVenta(listadoSincronizacionVentaNoSincronizadas.get(0).get_clienteId()));
					break;
				}
			}
		}
	}
	
	private void CopiarVentaAAutoventaTemp() 
	{
		if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
		{
			InsertarAutoventaProductoTemp(listadoVentaProductoNoSincronizado.get(0));	
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No esta conectado a internet, intentelo mas tarde.", 1);
			MostrarPantallaMenuDistribuidor();
		}
	}
		
	private boolean ObtenerVentaProductoNoSincronizados(int ventaId)
	{
		listadoVentaProductoNoSincronizado = null;
		try
		{
			listadoVentaProductoNoSincronizado = new VentaProductoBLL().ObtenerVentasProductoNoSincronizados(ventaId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos no sincronizados de la venta por ventaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos no sincronizados de la venta por ventaId: " + localException.getMessage());
			} 
		}
		
		if(listadoVentaProductoNoSincronizado != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private void InsertarAutoventaProductoTemp(VentaProducto localVentaProducto)
	{
	    pdInsertarAutoventaProductoTemp = new ProgressDialog(this);
	    pdInsertarAutoventaProductoTemp.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdInsertarAutoventaProductoTemp.setMessage("Insertando producto de la autoventa ...");
	    pdInsertarAutoventaProductoTemp.setCancelable(false);
	    pdInsertarAutoventaProductoTemp.setCanceledOnTouchOutside(false);
	    
	    WSInsertarAutoventaProductoTemp localWSInsertarAutoventaProductoTemp = new WSInsertarAutoventaProductoTemp(localVentaProducto);
	    try
	    {
	    	localWSInsertarAutoventaProductoTemp.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarAutoventaProductoTemp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertarAutoventaProductoTemp: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertarAutoventaProductoTemp", 1);
	    }
	}

	public class WSInsertarAutoventaProductoTemp extends AsyncTask<Void, Integer, Boolean>
	{
		String AUTOVENTAPRODUCTOTEMP_METHOD_NAME = "InsertAutoVentaProductoTemp";
		String AUTOVENTAPRODUCTOTEMP_SOAP_ACTION = NAMESPACE + AUTOVENTAPRODUCTOTEMP_METHOD_NAME;
		 
		 private VentaProducto _ventaProducto;
		 
		 boolean WSinsertarAutoventaProductoTemp;
		 int resultadoInt;
		 String resultadoString;
		 SoapObject localSoapObjects;
	    
	    public WSInsertarAutoventaProductoTemp(VentaProducto paramVentaProducto)
	    {
	    	_ventaProducto = paramVentaProducto;
	    }
	    
	    protected void onPreExecute()
	    {
	    	pdInsertarAutoventaProductoTemp.show();
	    }
	    
	    protected Boolean doInBackground(Void... paramVarArgs)
	    {
	    	WSinsertarAutoventaProductoTemp = false;
	      
	    	SoapObject localSoapObject = new SoapObject(NAMESPACE, this.AUTOVENTAPRODUCTOTEMP_METHOD_NAME);
	    	localSoapObject.addProperty("productoId", _ventaProducto.get_productoId());
	    	localSoapObject.addProperty("promocionId", _ventaProducto.get_promocionId());
	    	localSoapObject.addProperty("cantidad", _ventaProducto.get_cantidad());
	    	localSoapObject.addProperty("cantidadPaquete", _ventaProducto.get_cantidadPaquete());
	    	localSoapObject.addProperty("monto", String.valueOf(_ventaProducto.get_monto()));
	    	localSoapObject.addProperty("descuento", String.valueOf(_ventaProducto.get_descuento()));
	    	localSoapObject.addProperty("montoFinal", String.valueOf(_ventaProducto.get_montoFinal()));
	    	localSoapObject.addProperty("clienteId", listadoSincronizacionVentaNoSincronizadas.get(0).get_clienteId());
	    	localSoapObject.addProperty("empleadoId", loginEmpleado.get_empleadoId());
	    	localSoapObject.addProperty("dia", loginEmpleado.get_dia());
	    	localSoapObject.addProperty("mes", loginEmpleado.get_mes());
	    	localSoapObject.addProperty("anio", loginEmpleado.get_anio());
	    	localSoapObject.addProperty("costoId", String.valueOf(_ventaProducto.get_costoId()));
	    	localSoapObject.addProperty("precioId", String.valueOf(_ventaProducto.get_precioId()));
	    	localSoapObject.addProperty("nroAutoventa", listadoSincronizacionVentaNoSincronizadas.get(0).get_noAutoventa());
	    	localSoapObject.addProperty("descuentoCanal", String.valueOf(_ventaProducto.get_descuentoCanal()));
	    	localSoapObject.addProperty("descuentoAjuste", String.valueOf(_ventaProducto.get_descuentoAjuste()));
	    	localSoapObject.addProperty("canalRutaPrecioId", String.valueOf(_ventaProducto.get_canalPrecioRutaId()));
	    	localSoapObject.addProperty("descuentoProntoPago",String.valueOf(_ventaProducto.get_descuentoProntoPago()));
	   
	    	SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
	    	localSoapSerializationEnvelope.dotNet = true;
	    	localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
	    	HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
	    	try
	    	{
	    		localHttpTransportSE.call(AUTOVENTAPRODUCTOTEMP_SOAP_ACTION, localSoapSerializationEnvelope);
				
	    		localSoapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
	    		if(localSoapObjects!=null)
	    		{
	    			resultadoInt = Integer.parseInt(localSoapObjects.getPropertyAsString("Id"));
	    			resultadoString = localSoapObjects.getPropertyAsString("Resultado");    				
	    		}
				
	    		if(resultadoString.equals("OK") && resultadoInt > 0)
	    		{
	    			WSinsertarAutoventaProductoTemp = true;
	    		}
	    		return true;
	    	}
	    	catch (Exception localException)
	    	{
	    		WSinsertarAutoventaProductoTemp = false;
	    		if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    		{
	    			myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventaProductoTemp: vacio");
	    		}
	    		else
	    		{
	    			myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventaProductoTemp: " + localException.getMessage());
	    		} 
	    		return true;
	    	}
	    }
    
	    protected void onPostExecute(Boolean ejecutado)
	    {
	    	if(pdInsertarAutoventaProductoTemp.isShowing())
	    	{
	    		pdInsertarAutoventaProductoTemp.dismiss();
	    	}
	    	
	    	if(ejecutado) 
	    	{
	    		if(WSinsertarAutoventaProductoTemp || (resultadoString != null && resultadoString.equals("Autoventa Producto Temp Repetido") && resultadoInt <= 0)) 
	    		{
	    			long l = 0;
	    			try
	    			{
	    				l = new VentaProductoBLL().ModificarVentaProducto(_ventaProducto.get_id(),true);		
	    			}
	    			catch(Exception localException)
	    			{
	    				if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	    		{
	    	    			myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la ventaProducto por rowId: vacio");
	    	    		}
	    	    		else
	    	    		{
	    	    			myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la ventaProducto por rowId: " + localException.getMessage());
	    	    		} 
	    			}
	    			
	    			if(l==0)
	    			{
	    				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del producto de la venta.", 1);
	    				return;
	    			}
	    			else
	    			{
	    				theUtilidades.MostrarMensaje(getApplicationContext(), "Producto de la autoventa insertado.", 1);
	    				
	    				if(ObtenerVentaProductoNoSincronizados(_ventaProducto.get_ventaId()))
						{
	    					CopiarVentaAAutoventaTemp();
						}
						else
						{
							preventaIdCopiada = _ventaProducto.get_ventaId();
							SincronizarVenta();
						}
	    			}
	    			    	        
	    		}
	    		else
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo ejectutar el webservice WSInsertAutoVentaProductoTemp.", 1);
	    			MostrarPantallaMenuDistribuidor();
	    		}
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "El WebService WSInsertAutoventaProductoTemp no se ejecuto correctamente.", 1);
	    		return;
	    	}
	    }
	}

	private void SincronizarAutoventa(SincronizacionVenta localSincronizacionVenta,ClienteVenta clienteVenta)
	{
		 pdInsertarAutoventa = new ProgressDialog(this);
		 pdInsertarAutoventa.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		 pdInsertarAutoventa.setMessage("Insertando autoventa ...");
		 pdInsertarAutoventa.setCancelable(false);
		pdInsertarAutoventa.setCanceledOnTouchOutside(false);
		 	 
		 WSInsertarAutoventa localWSInsertarAutoventa = new WSInsertarAutoventa(localSincronizacionVenta,clienteVenta);
		 try
		 {
			 localWSInsertarAutoventa.execute();
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertAutoventa: vacio");
			 }
	 		 else
		 	 {
	 			 myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertAutoventa: " + localException.getMessage());
		 	 }
			 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertAutoventa.", 1);

		 }
	 }
 
	private class WSInsertarAutoventa extends AsyncTask<Void, Integer, Boolean>
	{
		String INSERTAUTOVENTA_METHOD_NAME = "InsertAutoVenta";
		String INSERTAUTOVENTA_SOAP_ACTION = NAMESPACE + INSERTAUTOVENTA_METHOD_NAME;
		 
		private SincronizacionVenta _sincronizacionVenta;
		private ClienteVenta _clienteVenta; 
		boolean WSInsertarAutoventa = false;
		int intResultado;
		SoapObject soapResultado;
		String stringResultado;
		
		public WSInsertarAutoventa(SincronizacionVenta paramSincronizacionVenta,ClienteVenta paramClienteVenta)
		{
			this._sincronizacionVenta = paramSincronizacionVenta;
			this._clienteVenta = paramClienteVenta;
		}
		 
		protected void onPreExecute()
		{
			pdInsertarAutoventa.show();
		}
	    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			 WSInsertarAutoventa = false;
			 
			 SoapObject localSoapObject = new SoapObject(NAMESPACE,INSERTAUTOVENTA_METHOD_NAME);
			 localSoapObject.addProperty("clienteId", Integer.valueOf(_sincronizacionVenta.get_clienteId()));
			 localSoapObject.addProperty("distribuidorId", Integer.valueOf(loginEmpleado.get_empleadoId()));
			 localSoapObject.addProperty("vendedorId", Integer.valueOf(loginEmpleado.get_empleadoId()));
			 localSoapObject.addProperty("tipoPagoId", Integer.valueOf(_sincronizacionVenta.get_tipoPagoId()));
			 localSoapObject.addProperty("monto", String.valueOf(_sincronizacionVenta.get_monto()));
			 localSoapObject.addProperty("descuento", String.valueOf(_sincronizacionVenta.get_descuento()));
			 localSoapObject.addProperty("montoFinal", String.valueOf(_sincronizacionVenta.get_montoFinal()));
			 localSoapObject.addProperty("latitud", String.valueOf(_sincronizacionVenta.get_latitud()));
			 localSoapObject.addProperty("longitud", String.valueOf(_sincronizacionVenta.get_longitud()));
			 localSoapObject.addProperty("numeroItems", Integer.valueOf(_sincronizacionVenta.get_numeroDeItems()));
			 localSoapObject.addProperty("anio", Integer.valueOf(_sincronizacionVenta.get_anio()));
			 localSoapObject.addProperty("mes", Integer.valueOf(_sincronizacionVenta.get_mes()));
			 localSoapObject.addProperty("dia", Integer.valueOf(_sincronizacionVenta.get_dia()));
			 localSoapObject.addProperty("hora", Integer.valueOf(_sincronizacionVenta.get_hora()));
			 localSoapObject.addProperty("minuto", Integer.valueOf(_sincronizacionVenta.get_minuto()));
			 localSoapObject.addProperty("nombrefactura", String.valueOf(_sincronizacionVenta.get_nombreFactura()));
			 localSoapObject.addProperty("nit", String.valueOf(_sincronizacionVenta.get_nit()));
			 localSoapObject.addProperty("nitNuevo",String.valueOf(_sincronizacionVenta.is_nitNuevo()));
			 localSoapObject.addProperty("almacenId", Integer.valueOf(loginEmpleado.get_almacenId()));
			 localSoapObject.addProperty("rutaId",(_clienteVenta.get_rutaId()));
			 localSoapObject.addProperty("diaId",(_clienteVenta.get_diaId()));
			 localSoapObject.addProperty("observacion",(_sincronizacionVenta.get_observacion()));
			 localSoapObject.addProperty("aplicaBonificacion",(_sincronizacionVenta.is_aplicarBonificacion()));
			 localSoapObject.addProperty("nroAutoventa",(_sincronizacionVenta.get_noAutoventa()));
			 localSoapObject.addProperty("dosificacionId", Integer.valueOf(_sincronizacionVenta.get_dosificacionId()));
			 localSoapObject.addProperty("tipoNit", String.valueOf(_sincronizacionVenta.get_tipoNit()));
			 localSoapObject.addProperty("prontoPagoId", String.valueOf(_sincronizacionVenta.get_prontoPagoId()));
			 localSoapObject.addProperty("descuentoCanal", String.valueOf(_sincronizacionVenta.get_descuentoCanal()));
			 localSoapObject.addProperty("descuentoAjuste", String.valueOf(_sincronizacionVenta.get_descuentoAjuste()));
			 localSoapObject.addProperty("descuentoProntoPago", String.valueOf(_sincronizacionVenta.get_descuentoProntoPago()));
			 localSoapObject.addProperty("descuentoObjetivo", String.valueOf(_sincronizacionVenta.get_descuentoObjetivo()));
			 localSoapObject.addProperty("formaPagoId", _sincronizacionVenta.get_formaPagoId());
			 localSoapObject.addProperty("codTransferencia", _sincronizacionVenta.get_codTransferencia());
			 localSoapObject.addProperty("fromApk",String.valueOf(true));
			 localSoapObject.addProperty("fromShopping",String.valueOf(false));
			
			 SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			 localSoapSerializationEnvelope.dotNet = true;
			 localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			 HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			 try
			 {
				 localHttpTransportSE.call(INSERTAUTOVENTA_SOAP_ACTION, localSoapSerializationEnvelope);
				
				 soapResultado = ((SoapObject)localSoapSerializationEnvelope.getResponse());
				 if(soapResultado!=null)
				 {
					 intResultado = Integer.parseInt(soapResultado.getPropertyAsString("Id"));
					 stringResultado = soapResultado.getPropertyAsString("Resultado");
				 }
				
				 if(stringResultado.equals("OK") && intResultado > 0)
				 {
					 WSInsertarAutoventa = true;
				 }
				 return true;
			 }
			 catch (Exception localException)
			 {
				 WSInsertarAutoventa = false;
				 if(localException.getMessage() == null || localException.getMessage().isEmpty())
				 {
					 myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertAutoventa: vacio");
				 }
				 else
				 {
					 myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertAutoventa: " + localException.getMessage());
				 }
				 return true;
			 }
	 	}
		 
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdInsertarAutoventa.isShowing())
			{
				pdInsertarAutoventa.dismiss();
			}
			 
			if(ejecutado)
			{
				if(WSInsertarAutoventa || (stringResultado != null && stringResultado.equals("Autoventa Repetida") && intResultado <= 0)) 
				{
					long l = 0;
					
					try
					{
						l = new VentaBLL().ModificarVentaIdServer(_sincronizacionVenta.get_ventaId(), intResultado);
					}
					catch (Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar ventaIdServer de la venta por ventaId: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar ventaIdServer de la venta por ventaId: " + localException.getMessage());
						} 
					}
					
					if(l==0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la ventaIdServer de la venta.", 1);
						return;
					}
					
					//Verificamos si la venta fue facturada
					Factura factura=null;
					try
					{
						factura = new FacturaBLL().ObtenerFacturaPorVentaId(_sincronizacionVenta.get_ventaId());
					}
					catch (Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar ventaIdServer de la factura por ventaId: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar ventaIdServer de la factura por ventaId: " + localException.getMessage());
						} 
					}
					
					if(factura != null)
					{
						l = 0;
						try
						{
							l = new FacturaBLL().ModificarFacturaServerVentaIdPorVentaId(_sincronizacionVenta.get_ventaId(), intResultado);
						}
						catch (Exception localException)
						{
							if(localException.getMessage() == null || localException.getMessage().isEmpty())
							{
								myLog.InsertarLog("App",this.toString(),1,"Error al modificar ventaIdServer de la factura por ventaId: vacio");
							}
							else
							{
								myLog.InsertarLog("App",this.toString(),1,"Error al modificar ventaIdServer de la factura por ventaId: " + localException.getMessage());
							} 
						}
						
						if(l==0)
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la ventaIdServer de la factura.", 1);
							return;
						}
					}				
					
					l=0;
					try
					{
						l = new VentaBLL().ModificarVentaSincronizacion(_sincronizacionVenta.get_ventaId(), true);
				 	}
					catch (Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la venta por rowId: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la venta por rowId: " + localException.getMessage());
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
						 l = new SincronizacionVentaBLL().ModificarSincronizacionAutoventaSincronizacionPorVentaId(_sincronizacionVenta.get_ventaId(), true);
					 }
					 catch(Exception localException)
					 {
						 if(localException.getMessage() == null || localException.getMessage().isEmpty())
						 {
							 myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la sincronizacionAutoventa por ventaId: vacio");
						 }
						 else
						 {
							 myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la SincronizacionAutoventa por ventaId: " + localException.getMessage());
						 } 
					 }
					 
					 if(l==0)
					 {
						 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion de la sincronizacion venta por VentaId.", 1);
						 return;
					 }

					theUtilidades.MostrarMensaje(getApplicationContext(), "Autoventa insertada.", 1);
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), this.stringResultado, 1);
					return;
				}
				 
				ObtenerSincronizacionesVentaNoSincronizadasPorVentaId(listadoSincronizacionVentaNoSincronizadas.get(0).get_ventaId());
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertAutoventa.", 1);
				return;
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

	public void SincronizarAutoventaNoEntregada(SincronizacionVenta sincronizacionVenta)
	{
		if(sincronizacionVenta.get_clienteId() < 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "Antes de sincronizar la autoventa, debe sincronizar el cliente.", 1);
			return;
		}
		else
		{
			switch(sincronizacionVenta.get_tipoSincronizacion())
			{
			case 6:
				InsertarAutoVentaNoEntregada(sincronizacionVenta);
				break;
			}
		}
	}
	
	private void InsertarAutoVentaNoEntregada(SincronizacionVenta sincroVenta)
	{
		pdVentaNoEntregada = new ProgressDialog(this);
		pdVentaNoEntregada.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdVentaNoEntregada.setMessage("Insertando venta no entregada ...");
		pdVentaNoEntregada.setCancelable(false);
		pdVentaNoEntregada.setCanceledOnTouchOutside(false);
				
		WSAutoVentaNoEntregada localWSAutoVentaNoEntregada = new WSAutoVentaNoEntregada(sincroVenta);
		try
		{
			localWSAutoVentaNoEntregada.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSAutoVentaNoEntregada: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSAutoVentaNoEntregada: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSAutoVentaNoEntregada", 1);
		}
	}
	
	private class WSAutoVentaNoEntregada extends AsyncTask<Void, Integer, Boolean>
	{
		String AUTOVENTANOENTREGADA_METHOD_NAME = "AutoVentaNoEntregada";
		String AUTOVENTANOENTREGADA_SOAP_ACTION = NAMESPACE + AUTOVENTANOENTREGADA_METHOD_NAME;
		boolean WSAutoVentaNoEntregada = false;
		private SincronizacionVenta _sincronizacionVenta;
		SoapObject soapObjects;
		int resultadoInt;
		String resultadoString;
    
		public WSAutoVentaNoEntregada(SincronizacionVenta sincroVenta)
		{
			this._sincronizacionVenta = sincroVenta;
		}
		
		protected void onPreExecute()
	    {
	    	pdVentaNoEntregada.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSAutoVentaNoEntregada = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,AUTOVENTANOENTREGADA_METHOD_NAME);
			localSoapObject.addProperty("distribuidorId", Integer.valueOf(_sincronizacionVenta.get_distribuidorId()));
			localSoapObject.addProperty("clienteId", Integer.valueOf(_sincronizacionVenta.get_clienteId()));
			localSoapObject.addProperty("anio", Integer.valueOf(_sincronizacionVenta.get_anio()));
			localSoapObject.addProperty("mes", Integer.valueOf(_sincronizacionVenta.get_mes()));
			localSoapObject.addProperty("dia", Integer.valueOf(_sincronizacionVenta.get_dia()));
			localSoapObject.addProperty("motivoId", Integer.valueOf(_sincronizacionVenta.get_motivoId()));
    		
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
    			
			try
			{
				localHttpTransportSE.call(AUTOVENTANOENTREGADA_SOAP_ACTION, localSoapSerializationEnvelope);
				soapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
					
				if(soapObjects != null)
				{
					resultadoInt = Integer.parseInt(soapObjects.getPropertyAsString("Id"));
					resultadoString = soapObjects.getPropertyAsString("Resultado"); 
				}
					
				if(resultadoInt > 0 && resultadoString.equals("OK"))
				{
					WSAutoVentaNoEntregada = true;
				}
				
				return true;
			}
			catch(Exception localException)
			{
				WSAutoVentaNoEntregada = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSAutoVentaNoEntregada: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSAutoVentaNoEntregada: " + localException.getMessage());
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
    	
			if(ejecutado)
			{
				if(WSAutoVentaNoEntregada
						|| (resultadoString != null && resultadoString.equals("No Entrega Repetida") && resultadoInt <=0)) 
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"AutoVenta No entregada, registrada en el server.", 1);
					ModificarSincronizacionAutoVentaSincronizacionPorClienteId(_sincronizacionVenta.get_clienteId(),true);
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo sincronizar la autoventa NO entregada.", 1);
				}
		
				ObtenerAutoventasNoSincronizadasForDisplay();
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservise WSAutoVentaNoEntregada no se ejecuto correctamente..", 1);
				return;
			}
		}
	}
	
	private void ModificarSincronizacionAutoVentaSincronizacionPorClienteId(int clienteId,boolean sincronizacion)
	{
		int i=0;
		try
		{
			i = new SincronizacionVentaBLL().ModificarSincronizacionAutoventaSincronizacionPorClienteId(clienteId, sincronizacion);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSAutoVentaNoEntregada: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSAutoVentaNoEntregada: " + localException.getMessage());
			} 
		}

		if(i == 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo actualizar la sincronizacion de la sincronizacionAutoVenta", 1);
		}
	}
	
	private void MostrarPantallaMenuDistribuidor()
	{
		Intent localIntent = new Intent(this, Menudistribuidor.class);
	    localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(localIntent);
	}
}
