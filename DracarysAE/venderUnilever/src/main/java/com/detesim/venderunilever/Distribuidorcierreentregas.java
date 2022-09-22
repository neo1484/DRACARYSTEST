package com.detesim.venderunilever;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import BLL.CierreDistribuidorBLL;
import BLL.Descuento2BLL;
import BLL.DosificacionBLL;
import BLL.FacturaBLL;
import BLL.MyLogBLL;
import BLL.VentaBLL;
import BLL.VentaProductoBLL;
import Clases.Descuento2;
import Clases.Dosificacion;
import Clases.Factura;
import Clases.LoginEmpleado;
import Clases.Venta;
import Clases.VentaProducto;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Distribuidorcierreentregas extends Activity implements OnClickListener
{
	LinearLayout llDistribuidorCierreEntregas;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
	TextView tvVentas;
	TextView tvItems;
	TextView tvMonto;
	TextView tvDescuento;
	TextView tvDescuento2;
	TextView tvMontoFinal;
	Button btnCierreEntregas;
	ProgressDialog pdCierreDiaDistribuidor;
	ProgressDialog pdEsperaObtenerDescuento2ByDistribuidor;
	Dialog dialog;
	
	int nroVentas = 0;
	int nroItems = 0;
	float montoTotal = 0;
	float descuento = 0;
	float descuento2 = 0;
	float montoFinal = 0;
	Dosificacion dosificacion;
	ArrayList<Venta> listadoVenta;
	ArrayList<VentaProducto> listadoVentaProducto;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_distribuidorcierreentregas);
		
		llDistribuidorCierreEntregas = (LinearLayout)findViewById(R.id.DistribuidorCierreEntregasLinearLayout);
		tvVentas = (TextView)findViewById(R.id.tvCierreEntregasVentas);
		tvItems = (TextView)findViewById(R.id.tvCierreEntregasItems);
		tvMonto = (TextView)findViewById(R.id.tvCierreEntregasMonto);
		tvDescuento = (TextView)findViewById(R.id.tvCierreEntregasDescuento);
		tvDescuento2 = (TextView)findViewById(R.id.tvDistribuidorCierreEntregasDescuento2);
		tvMontoFinal = (TextView)findViewById(R.id.tvCierreEntregasVentaTotal);
		btnCierreEntregas = (Button)findViewById(R.id.btnDistribuidorCierreEntregas);
		btnCierreEntregas.setOnClickListener(new OnClickListener() 
    	{
			@Override
			public void onClick(View v) 		
			{
				dialog = new Dialog(Distribuidorcierreentregas.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.diseniodialogo_vendedorcierredia);
				dialog.setTitle("Cierre distribuidor dia");
				dialog.setCancelable(false);
				dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.colorBluePale));
				
				TextView tvMensaje = (TextView)dialog.findViewById(R.id.tvDialogoMensaje);
				tvMensaje.setText("Esta seguro de cerrar las entregas del  dia de hoy?");
				
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
								if(dosificacion != null)
								{
									CierreDiaDistribuidor();
								}
								else
								{
									theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontro una dosificacion para el cierre.", 1);
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
	    
		llDistribuidorCierreEntregas.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
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
	    
	    ObtenerDescuanto2ByDistribuidor();
	}

	@Override
	public void onClick(View v)
	{
	}
	
	private boolean VerificarExistenciaVentasNoSincronizadas() 
	{
		boolean verificadoVenta = true;
		boolean verificadoVentaProducto = true;
		
		for(Venta item : listadoVenta)
		{
			verificadoVenta = verificadoVenta && item.is_estadoSincronizacion();
		}
		
		for(VentaProducto item : listadoVentaProducto)
		{
			verificadoVentaProducto = verificadoVentaProducto && item.is_estadoSincronizacion();
		}
		
		if(verificadoVenta == true && verificadoVentaProducto == true)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	private boolean VerificarExistenciasFacturasNoSincronizadas()
	{
		ArrayList<Factura> listadoFactura = null;
		
		try
		{
			listadoFactura = new FacturaBLL().ObtenerFacturasNoSincronizadas();
		}
		catch(Exception localException)
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
		
		if(listadoFactura == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	private boolean VerificarExistenciasFacturasNoImpresas()
	{
		ArrayList<Factura> listadoFactura = null;
		
		try
		{
			listadoFactura = new FacturaBLL().ObtenerFacturasNoImpresas();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
                myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturas no impresas: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturas no impresas: " + localException.getMessage());
        	}
		}
		
		if(listadoFactura == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	private boolean VerificarExistenciaCierreEntregas()
	{
		boolean verificado = false;
		try
		{
			verificado = new CierreDistribuidorBLL().VerificarCierreDistribuidor(
					loginEmpleado.get_empleadoId(),
					loginEmpleado.get_anio(),
					loginEmpleado.get_mes(),
					loginEmpleado.get_dia());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
                myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cierreDistribuidor por empleadoId y fecha: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cierreDistribuidor por empleadoId y fecha: " + localException.getMessage());
        	}
		}
		
		return verificado;
	}

	private void CierreDiaDistribuidor()
	{
		pdCierreDiaDistribuidor = new ProgressDialog(this);
		pdCierreDiaDistribuidor.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdCierreDiaDistribuidor.setMessage("Verificando cierre ...");
		pdCierreDiaDistribuidor.setCancelable(false);
		pdCierreDiaDistribuidor.setCanceledOnTouchOutside(false);
	    
	    WSCierreDiaDistribuidor localWSCierreDiaDistribuidor = new WSCierreDiaDistribuidor();
	    try
	    {
	    	localWSCierreDiaDistribuidor.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSCierreDiaDistribuidorFromApk: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSCierreDiaDistribuidorFromApk: " + localException.getMessage());
	    	}
	    	
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSCierreDiaDistribuidorFromApk.", 1);
	    }
	}
	
	private class WSCierreDiaDistribuidor extends AsyncTask<Void, Integer, Boolean>
	{
		String CIERREDIADISTRIBUIDOR_METHOD_NAME = "CierreDiaDistribuidorFromApk";
		String CIERREDIADISTRIBUIDOR_SOAP_ACTION = NAMESPACE + CIERREDIADISTRIBUIDOR_METHOD_NAME;
		boolean WSCierreDiaDistribuidor = false;
		SoapObject soapObject;
		int intResultado;
		String stringResultado;
    
		protected void onPreExecute()
	    {
			pdCierreDiaDistribuidor.show();
	    }
		
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSCierreDiaDistribuidor = false;
			
			SoapObject localSoapObject = new SoapObject(NAMESPACE,CIERREDIADISTRIBUIDOR_METHOD_NAME);
			localSoapObject.addProperty("distribuidorId", Integer.valueOf(loginEmpleado.get_empleadoId()));
			localSoapObject.addProperty("anio", Integer.valueOf(loginEmpleado.get_anio()));
			localSoapObject.addProperty("mes", Integer.valueOf(loginEmpleado.get_mes()));
			localSoapObject.addProperty("dia", Integer.valueOf(loginEmpleado.get_dia()));
			localSoapObject.addProperty("dosificacionId", Integer.valueOf(dosificacion.get_dosificacionId()));
			localSoapObject.addProperty("numeroFinal", Integer.valueOf(dosificacion.get_numeroInicial()));
     
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			
			try
			{
				localHttpTransportSE.call(CIERREDIADISTRIBUIDOR_SOAP_ACTION, localSoapSerializationEnvelope);
				
				soapObject = (SoapObject)localSoapSerializationEnvelope.getResponse();
				if(soapObject != null)
				{
					intResultado = Integer.parseInt(soapObject.getPropertyAsString("Id"));
			        stringResultado = soapObject.getPropertyAsString("Resultado");
				}
				
				if(stringResultado.equals("OK") && intResultado > 0)
				{
					WSCierreDiaDistribuidor = true;
				}
				return true;
			}
			catch (Exception localException)
			{
				WSCierreDiaDistribuidor = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSCierreDiaDistribuidorFromApk: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSCierreDiaDistribuidorFromApk: " + localException.getMessage());
				} 
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdCierreDiaDistribuidor.isShowing())
			{
				pdCierreDiaDistribuidor.dismiss();
			}
			
			if(ejecutado)
			{
				if(WSCierreDiaDistribuidor)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Cierre realizado exitosamente.", 1);
					long i = 0;
					try
					{
						i = new CierreDistribuidorBLL().InsertarCierreDistribuidor(loginEmpleado.get_empleadoId(),
								loginEmpleado.get_anio(),loginEmpleado.get_mes(),loginEmpleado.get_dia());
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cierreDistribuidor: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cierreDistribuidor: " + localException.getMessage());
						}
					}
					
					if(i<=0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el cierre distribuidor.", 1);
						return;
					}
					
					if(BorrarVentas())
					{
						if(!BorrarVentasProducto())
						{
							theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo borrar los productos de las ventas.", 1);
							return;
						}
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo borrar las ventas.", 1);
						return;
					}
					
					onBackPressed();
				}
				else
				{
					if(stringResultado == null)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webService WSCierreDiaDistribuidorFromApk.", 1);
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(),stringResultado, 1);
					}
			        return;
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSCierreDiaDistribuidorFromApk.", 1);
				return;
			}
		}
	}
	
	private boolean BorrarVentas()
	{
		boolean borradoVentas = false;
		try
		{
			borradoVentas = new VentaBLL().BorrarVentas();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventas: " + localException.getMessage());
			}
		}
		return borradoVentas;
	}
	
	private boolean BorrarVentasProducto()
	{
		boolean borradoVentasProducto = false;
		try
		{
			borradoVentasProducto = new VentaProductoBLL().BorrarVentasProducto();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventasProducto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventasProducto: " + localException.getMessage());
			}
		}
		return borradoVentasProducto;
	}

	private void ObtenerDescuanto2ByDistribuidor()
	{
		pdEsperaObtenerDescuento2ByDistribuidor = new ProgressDialog(this);
		pdEsperaObtenerDescuento2ByDistribuidor.setProgressStyle(0);
		pdEsperaObtenerDescuento2ByDistribuidor.setMessage("Descargando descuentos  ...");
		pdEsperaObtenerDescuento2ByDistribuidor.setCancelable(false);
		pdEsperaObtenerDescuento2ByDistribuidor.setCanceledOnTouchOutside(false);
		  
		WSObtenerDescuento2ByDistribuidor wsObtenerDescuento2ByDistribuidor = new WSObtenerDescuento2ByDistribuidor();
		  
		try
		{
			wsObtenerDescuento2ByDistribuidor.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
		    {
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetDescuento2ByDistribuidor: vacio");
		   	}
		   	else
		   	{
		   		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetDescuento2ByDistribuidor: " + localException.getMessage());
		   	}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetDescuento2ByDistribuidor.", 1);
		}
	}
	
	private class WSObtenerDescuento2ByDistribuidor extends AsyncTask<Void, Integer, Boolean>
	{
		String DESCUENTODISTRIBUIDOR_METHOD_NAME = "GetDescuento2ByDistribuidor";
		String DESCUENTODISTRIBUIDOR_SOAP_ACTION = NAMESPACE + DESCUENTODISTRIBUIDOR_METHOD_NAME;
		boolean WSDescuentoDistribuidor = false; 
		SoapPrimitive soapObjects;
    
		protected void onPreExecute()
	    {
			pdEsperaObtenerDescuento2ByDistribuidor.show();
	    }
		
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSDescuentoDistribuidor = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,DESCUENTODISTRIBUIDOR_METHOD_NAME);
			localSoapObject.addProperty("distribuidorId", Integer.valueOf(loginEmpleado.get_empleadoId()));
			localSoapObject.addProperty("almacenId", Integer.valueOf(loginEmpleado.get_almacenId()));
			localSoapObject.addProperty("dia", Integer.valueOf(loginEmpleado.get_dia()));
			localSoapObject.addProperty("mes", Integer.valueOf(loginEmpleado.get_mes()));
			localSoapObject.addProperty("anio", Integer.valueOf(loginEmpleado.get_anio()));
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			
			try
			{
				localHttpTransportSE.call(this.DESCUENTODISTRIBUIDOR_SOAP_ACTION, localSoapSerializationEnvelope);
				soapObjects = (SoapPrimitive)localSoapSerializationEnvelope.getResponse();
				if(soapObjects!=null)
				{
					WSDescuentoDistribuidor = true;
				}

				return true;
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
		        	myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetDescuento2ByDistribuidors: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetDescuento2ByDistribuidor: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDescuento2ByDistribuidor.isShowing())
			{
				pdEsperaObtenerDescuento2ByDistribuidor.dismiss();
			}
			
			if(ejecutado)
			{
				if (WSDescuentoDistribuidor)
				{
					if(borrarDescuento2Distribuidor())
					{	
						long l=0;
						try
						{
							l = new Descuento2BLL().InsertarDescuentos(loginEmpleado.get_empleadoId(),Float.parseFloat(soapObjects.toString()));
						}
						catch(Exception localException)
						{
							if(localException.getMessage() == null || localException.getMessage().isEmpty())
							{
						        myLog.InsertarLog("App",this.toString(),1,"Error al insertar el descuento2 del distribuidor: vacio");
							}
							else
							{
								myLog.InsertarLog("App",this.toString(),1,"Error al insertar el descuento2 del distribuidor: " + localException.getMessage());
							}
							return;
						}
						
						if(l<=0)
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el descuento2 del distribuidor.", 2);
							return;								
						}	
						else
						{
							CalcularValoresCierre();
						}
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontraron descuentos para el distribuidor.", 2);
					CalcularValoresCierre();
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"El WebService WSGetDescuento2ByDistribuidor no se ejecuto correctamente. ", 1);
				return;
			}
		}
	}
	
	private boolean borrarDescuento2Distribuidor()
	{
		try
		{
			return new Descuento2BLL().BorrarDescuentos2();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los descuentos2 del distribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los descuentos2 del distribuidor: " + localException.getMessage());
			}
			return false;
		}	
	}
	
	private void CalcularValoresCierre()
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
	    	return;
	    }
	    
	    if(listadoVenta!=null)
	    {
	    	nroVentas = listadoVenta.size();
	    	tvVentas.setText(String.valueOf(nroVentas));
	    	
	    	listadoVentaProducto=null;
	    	try
	        {
	    		listadoVentaProducto = new VentaProductoBLL().ObtenerVentasProducto();
	        }
	        catch (Exception localException)
	        {
	        	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	        	{
	                myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProducto: vacio");
	        	}
	        	else
	        	{
	        		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProducto: " + localException.getMessage());
	        	}
	        	return;
	        }
	    	
	    	if(listadoVentaProducto !=null)
	    	{
	    		nroItems = listadoVentaProducto.size();
		        tvItems.setText(String.valueOf(nroItems));
		        
		        for(VentaProducto item : listadoVentaProducto)
		        {
		        	montoTotal += item.get_monto();
		  	      	descuento += item.get_descuento();
		  	      	montoFinal += item.get_montoFinal();
		        }
		        
		        Descuento2 descuento2Distribuidor = null;
			    try
			    {
			    	descuento2Distribuidor = new Descuento2BLL().ObtenerDescuento2Por(loginEmpleado.get_empleadoId());
			    }
			    catch(Exception localException)
			    {
			    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
		        	{
		                myLog.InsertarLog("App",this.toString(),1,"Error al obtener el descuento2 por distribuidorId: vacio");
		        	}
		        	else
		        	{
		        		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el descuento 2 por distribuidorId: " + localException.getMessage());
		        	}
			    }
			    
			    if(descuento2Distribuidor == null)
			    {
			    	tvDescuento2.setText("0.00");
			    }
			    else
			    {
			    	descuento2 = descuento2Distribuidor.get_monto();
			    	tvDescuento2.setText(String.valueOf(descuento2));
			    	montoFinal = montoFinal - descuento2;
			    }
		        
		        tvMonto.setText(new BigDecimal(montoTotal).setScale(2,RoundingMode.HALF_UP).toString());
		        tvDescuento.setText(new BigDecimal(descuento).setScale(2,RoundingMode.HALF_UP).toString());
		        tvMontoFinal.setText(new BigDecimal(montoFinal).setScale(2,RoundingMode.HALF_UP).toString());
		        
		        if(VerificarExistenciaVentasNoSincronizadas())
			    {
			    	btnCierreEntregas.setVisibility(View.INVISIBLE);
			    	theUtilidades.MostrarMensaje(getApplicationContext(),"Existen ventas no sincronizadas, no puede cerrar el dia.", 1);
			    	return;
			    }
			    
		        if(VerificarExistenciasFacturasNoSincronizadas())
		    	{
			    	btnCierreEntregas.setVisibility(View.INVISIBLE);
			    	theUtilidades.MostrarMensaje(getApplicationContext(),"Existen facturas no sincronizadas, no puede cerrar el dia.", 1);
			    	return;
		    	}
		        
		        if(VerificarExistenciasFacturasNoImpresas())
		    	{
			    	btnCierreEntregas.setVisibility(View.INVISIBLE);
			    	theUtilidades.MostrarMensaje(getApplicationContext(),"Existen facturas no impresas, no puede cerrar el dia.", 1);
			    	return;
		    	}
			    
		        if(VerificarExistenciaCierreEntregas())
		    	{
			    	btnCierreEntregas.setVisibility(View.INVISIBLE);
			    	theUtilidades.MostrarMensaje(getApplicationContext(), "Ya se realizo el cierre.", 1);
			    	return;
		    	}
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron productos en la venta.", 1);
	    		return;
	    	}
	    }
	    else
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se registro ninguna venta",1);
	    	btnCierreEntregas.setVisibility(View.INVISIBLE);
	    }
	    
	    dosificacion = null;
	    try
	    {
	    	dosificacion = new DosificacionBLL().ObtenerDosificacion();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
                myLog.InsertarLog("App",this.toString(),1,"Error al obtener la dosificacion: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la dosificacion: " + localException.getMessage());
        	}
	    }
	    
	    if(dosificacion == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la dosificacion.",1);
	    }
	}
	
	@Override
	public void onBackPressed() 
	{
		Intent intent = new Intent(this,Menudistribuidor.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
}
