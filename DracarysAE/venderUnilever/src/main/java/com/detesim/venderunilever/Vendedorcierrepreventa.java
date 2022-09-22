package com.detesim.venderunilever;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import BLL.ApkRutaClienteBLL;
import BLL.CierrePreventistaBLL;
import BLL.ClientePreventaBLL;
import BLL.MyLogBLL;
import BLL.PreventaBLL;
import BLL.PreventaProductoBLL;
import BLL.RolBLL;
import Clases.ApkRutaCliente;
import Clases.LoginEmpleado;
import Clases.Preventa;
import Clases.PreventaProducto;
import Clases.Rol;
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
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Vendedorcierrepreventa extends Activity implements OnClickListener
{
	LinearLayout llVendedorCierrePreventa;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
	TextView tvNroPreventas;
	TextView tvNroItems;
	TextView tvMontoTotal;
	TextView tvDescuentoTotal;
	TextView tvMontoTotalFinal;
	Button btnCierrePreventista;
	ProgressDialog pdCierreDiaVendedor;
	ProgressDialog pdCierreDiaVendedorProvincia;
	Dialog dialog;
	
	int nroPreventas = 0;
	int nroItems = 0;
	float montoTotal = 0;
	float descuentoTotal = 0;
	float descuentoIncentivo = 0;
	float montoTotalFinal = 0;
	ArrayList<Preventa> listadoPreventa;
	ArrayList<PreventaProducto> listadoPreventaProducto;
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedorcierrepreventa);
		
		llVendedorCierrePreventa = (LinearLayout)findViewById(R.id.VendedorCierrePreventaLinearLayout);
		tvNroPreventas = ((TextView)findViewById(R.id.tvCierrePreventaNumeroPreventa));
	    tvNroItems = ((TextView)findViewById(R.id.tvCierrePreventaNumeroItems));
	    tvMontoTotal = ((TextView)findViewById(R.id.tvCierrePreventaMonto));
	    tvDescuentoTotal = ((TextView)findViewById(R.id.tvCierrePreventaDescuento));
	    tvMontoTotalFinal = ((TextView)findViewById(R.id.tvCierrePreventaMontoFinal));
	    btnCierrePreventista = ((Button)findViewById(R.id.btnCierrePreventaCerrarPreventa));
	    btnCierrePreventista.setOnClickListener(new OnClickListener() 
	    	{
				@Override
				public void onClick(View v) 		
				{
					dialog = new Dialog(Vendedorcierrepreventa.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.diseniodialogo_vendedorcierredia);
					dialog.setTitle("Cierre vendedor dia");
					dialog.setCancelable(false);
					dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.colorBluePale));
					
					TextView tvMensaje = (TextView)dialog.findViewById(R.id.tvDialogoMensaje);
					tvMensaje.setText("Esta seguro de cerrar las preventas del  dia de hoy?");
					
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
									if(UsuarioEsVendedorProvincia())
									{
										CierreDiaPreventaProvincia();
									}
									else
									{
										CierreDiaPreventa();
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
	    
	    llVendedorCierrePreventa.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
	    
	    listadoPreventa = null;
	    listadoPreventaProducto = null;
	    
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
	    	return;
	    }
	    
	    if(listadoPreventa!=null)
	    {
	    	nroPreventas = listadoPreventa.size();
	    	tvNroPreventas.setText(String.valueOf(nroPreventas));

	    	for(Preventa itemPre : listadoPreventa)
	    	{
	    		descuentoIncentivo += itemPre.get_descuentoIncentivo();
			}
	    	
	    	listadoPreventaProducto=null;
	    	try
	        {
	    		listadoPreventaProducto = new PreventaProductoBLL().ObtenerPreventasProducto();
	        }
	        catch (Exception localException)
	        {
	        	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	        	{
	                myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProducto: vacio");
	        	}
	        	else
	        	{
	        		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProducto: " + localException.getMessage());
	        	}
	        	return;
	        }
	    	
	    	if(listadoPreventaProducto !=null)
	    	{
	    		nroItems = listadoPreventaProducto.size();
		        tvNroItems.setText(String.valueOf(nroItems));
		        
		        for(PreventaProducto item : listadoPreventaProducto)
		        {
		        	montoTotal += item.get_monto();
		  	      	descuentoTotal += item.get_descuento();
		  	      	montoTotalFinal += item.get_montoFinal();
		        }
		        
		        tvMontoTotal.setText(new BigDecimal(montoTotal).setScale(2,RoundingMode.HALF_UP).toString());
		        tvDescuentoTotal.setText(new BigDecimal(descuentoTotal + descuentoIncentivo).setScale(2,RoundingMode.HALF_UP).toString());
		        tvMontoTotalFinal.setText(new BigDecimal(montoTotalFinal - descuentoIncentivo).setScale(2,RoundingMode.HALF_UP).toString());
		        
		        if(VerificarExistenciaPreventasNoSincronizadas())
			    {
			    	btnCierrePreventista.setVisibility(View.INVISIBLE);
			    	theUtilidades.MostrarMensaje(getApplicationContext(),"Existen preventas no sincronizadas, no puede cerrar el dia.", 1);
			    }
			    else
			    {
			    	if(VerificarExistenciaCierre())
			    	{
			    		btnCierrePreventista.setVisibility(View.INVISIBLE);
			    		theUtilidades.MostrarMensaje(getApplicationContext(), "Ya se realizo el cierre.", 1);
			    	}
			    }
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron productos en la preventa.", 1);
	    	}
	    }
	    else
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se registro ninguna preventa",1);
	    	btnCierrePreventista.setVisibility(View.INVISIBLE);
	    }
	    
	    /*ApkRutaCliente localApkRutaCliente = null;
	    try
	    {
	    	localApkRutaCliente = new ApkRutaClienteBLL().ObtenerApkRutaClientePor(loginEmpleado.get_vendedorRutaId());
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ruta del cliente por rutaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ruta del cliente por rutaId: " + localException.getMessage());
	    	} 
	    }
	   
	    if(localApkRutaCliente == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la ruta del cliente.", 1);
	    	return;
	    }
	    else
	    {
	    	if(localApkRutaCliente.is_rutaCompleta())
	    	{
	    		int clientesNoAtendidos = 0;
	    		try
	    		{
	    			clientesNoAtendidos = new ClientePreventaBLL().obtenerClientesPreventaNoAtendidos();
	    		}
	    		catch(Exception localException)
	    		{
	    			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	    	{
	    	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes preventa no atendidos: vacio");
	    	    	}
	    	    	else
	    	    	{
	    	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes preventa no atendidos: " + localException.getMessage());
	    	    	} 
	    		}
	    		
	    		if(clientesNoAtendidos > 0)
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(), "No puede cerrar el dia, por que aun existen clientes sin atender.",1);
	    	    	btnCierrePreventista.setVisibility(View.INVISIBLE);
	    		}
	    	}
	    }*/
	}

	@Override
	public void onClick(View v) 
	{	
	}
	
	private boolean VerificarExistenciaPreventasNoSincronizadas() 
	{
		boolean verificadoPreventa = true;
		//boolean verificadoPreventaProducto = true;
		
		for(Preventa item : listadoPreventa)
		{
			verificadoPreventa = verificadoPreventa && item.is_estado();
		}
		
		/*for(PreventaProducto item : listadoPreventaProducto)
		{
			verificadoPreventaProducto = verificadoPreventaProducto && item.is_estado();
		}*/
		
		//if(verificadoPreventa == true && verificadoPreventaProducto == true)
		if(verificadoPreventa)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	private boolean VerificarExistenciaCierre()
	{
		boolean verificado = false;
		try
		{
			verificado = new CierrePreventistaBLL().VerificarCierrePreventista(
					loginEmpleado.get_empleadoId(),
					loginEmpleado.get_anio(),
					loginEmpleado.get_mes(),
					loginEmpleado.get_dia());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
                myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cierePreventista por empleadoId y fecha: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cierePreventista por empleadoId y fecha: " + localException.getMessage());
        	}
		}
		
		return verificado;
	}
			
	private void CierreDiaPreventa()
	{
		pdCierreDiaVendedor = new ProgressDialog(this);
	    pdCierreDiaVendedor.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdCierreDiaVendedor.setMessage("Verificando cierre ...");
	    pdCierreDiaVendedor.setCancelable(false);
	    pdCierreDiaVendedor.setCanceledOnTouchOutside(false);
	    
	    WSCierreDiaPreventa localWSCierreDiaPreventa = new WSCierreDiaPreventa();
	    try
	    {
	    	localWSCierreDiaPreventa.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSCierreDiaVendedor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSCierreDiaVendedor: " + localException.getMessage());
	    	}
	    	
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSCierreDiaVendedor.", 1);
	    }
	}
	
	private class WSCierreDiaPreventa extends AsyncTask<Void, Integer, Boolean>
	{
		String CIERREDIAVENDEDOR_METHOD_NAME = "CierreDiaVendedorFromApk";
		String CIERREDIAVENDEDOR_SOAP_ACTION = NAMESPACE + CIERREDIAVENDEDOR_METHOD_NAME;
		boolean WSCierreDiaPreventista = false;
		SoapObject soapObject;
		int intResultado;
		String stringResultado;
    
		protected void onPreExecute()
	    {
	      pdCierreDiaVendedor.show();
	    }
		
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSCierreDiaPreventista = false;
			
			float montoTotal2decimales = Float.parseFloat(((new BigDecimal(montoTotal)).setScale(2,RoundingMode.HALF_UP)).toString());
			float descuentoTotal2decimales = Float.parseFloat(((new BigDecimal(descuentoTotal)).setScale(2,RoundingMode.HALF_UP)).toString());
			float montoTotalFinal2decimales = Float.parseFloat(((new BigDecimal(montoTotalFinal)).setScale(2,RoundingMode.HALF_UP)).toString());
			
			SoapObject localSoapObject = new SoapObject(NAMESPACE,CIERREDIAVENDEDOR_METHOD_NAME);
			localSoapObject.addProperty("vendedorId", Integer.valueOf(loginEmpleado.get_empleadoId()));
			localSoapObject.addProperty("anio", Integer.valueOf(loginEmpleado.get_anio()));
			localSoapObject.addProperty("mes", Integer.valueOf(loginEmpleado.get_mes()));
			localSoapObject.addProperty("dia", Integer.valueOf(loginEmpleado.get_dia()));
			localSoapObject.addProperty("numeroPreVentas", Integer.valueOf(nroPreventas));
			localSoapObject.addProperty("numeroItems", Integer.valueOf(nroItems));
			localSoapObject.addProperty("monto", String.valueOf(montoTotal2decimales));
			localSoapObject.addProperty("descuento", String.valueOf(descuentoTotal2decimales));
			localSoapObject.addProperty("montoFinal", String.valueOf(montoTotalFinal2decimales));
     
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			
			try
			{
				localHttpTransportSE.call(CIERREDIAVENDEDOR_SOAP_ACTION, localSoapSerializationEnvelope);
				
				soapObject = (SoapObject)localSoapSerializationEnvelope.getResponse();
				if(soapObject != null)
				{
					intResultado = Integer.parseInt(soapObject.getPropertyAsString("Id"));
			        stringResultado = soapObject.getPropertyAsString("Resultado");
				}
				
				if(stringResultado.equals("OK") && intResultado == 1)
				{
					WSCierreDiaPreventista = true;
				}
				return true;
			}
			catch (Exception localException)
			{
				WSCierreDiaPreventista = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSCierreDiaVendedor: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSCierreDiaVendedor: " + localException.getMessage());
				} 
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdCierreDiaVendedor.isShowing())
			{
				pdCierreDiaVendedor.dismiss();
			}
			
			if(ejecutado)
			{
				if(WSCierreDiaPreventista)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Cierre realizado exitosamente.", 1);
					long i = 0;
					try
					{
						i = new CierrePreventistaBLL().InsertarCierrePreventista(loginEmpleado.get_empleadoId(),
								loginEmpleado.get_anio(),loginEmpleado.get_mes(),loginEmpleado.get_dia());
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cierrePreventista: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cierrePreventista: " + localException.getMessage());
						}
					}
					
					if(i<=0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el cierre preventista.", 1);
						return;
					}
					
					if(BorrarPreventas())
					{
						if(!BorrarPreventasProducto())
						{
							theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo borrar los productos de las preventas.", 1);
							return;
						}
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo borrar las preventas.", 1);
						return;
					}
					
					onBackPressed();
				}
				else
				{
					if(stringResultado == null)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webService WSCierreDiaVendedor.", 1);
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
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSCierreDiaVendedor.", 1);
				return;
			}
		}
	}
	
	private boolean BorrarPreventas()
	{
		boolean borradoPreventas = false;
		try
		{
			borradoPreventas = new PreventaBLL().BorrarPreventas();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventas: " + localException.getMessage());
			}
		}
		return borradoPreventas;
	}
	
	private boolean BorrarPreventasProducto()
	{
		boolean borradoPreventasProducto = false;
		try
		{
			borradoPreventasProducto = new PreventaProductoBLL().BorrarPreventasProducto();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasProducto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasProducto: " + localException.getMessage());
			}
		}
		return borradoPreventasProducto;
	}

	private boolean UsuarioEsVendedorProvincia()
	{
		boolean esVendedorProvincia = false;
		
		ArrayList<Rol> listadoRol = null;
		
		try
		{
			listadoRol = new RolBLL().ObtenerRolesPor(loginEmpleado.get_empleadoId());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los roles del vendedor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los roles del vendedor: " + localException.getMessage());
			}
		}
		
		if(listadoRol == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los roles del vendedor.", 1);
		}
		else
		{
			for(Rol item :listadoRol)
			{
				if(item.get_rol().equals("VendedorProvincia"))
				{
					esVendedorProvincia = true;
				}
			}
		}
		
		return esVendedorProvincia;
	}

	private void CierreDiaPreventaProvincia()
	{
		pdCierreDiaVendedorProvincia = new ProgressDialog(this);
	    pdCierreDiaVendedorProvincia.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdCierreDiaVendedorProvincia.setMessage("Verificando cierre ...");
	    pdCierreDiaVendedorProvincia.setCancelable(false);
	    pdCierreDiaVendedorProvincia.setCanceledOnTouchOutside(false);
	    
	    WSCierreDiaPreventaProvincia localWSCierreDiaPreventaProvincia = new WSCierreDiaPreventaProvincia();
	    try
	    {
	    	localWSCierreDiaPreventaProvincia.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSCierreDiaVendedorProvincia: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSCierreDiaVendedorProvincia: " + localException.getMessage());
	    	}
	    	
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSCierreDiaVendedorProvincia.", 1);
	    }
	}
	
	private class WSCierreDiaPreventaProvincia extends AsyncTask<Void, Integer, Boolean>
	{
		String CIERREDIAVENDEDORPROVINCIA_METHOD_NAME = "CierreDiaVendedorProvincia";
		String CIERREDIAVENDEDORPROVINCIA_SOAP_ACTION = NAMESPACE + CIERREDIAVENDEDORPROVINCIA_METHOD_NAME;
		boolean WSCierreDiaPreventaProvincia = false;
		SoapObject soapObject;
		int intResultado;
		String stringResultado;
    
		protected void onPreExecute()
	    {
			pdCierreDiaVendedorProvincia.show();
	    }
		
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSCierreDiaPreventaProvincia = false;
			
			float montoTotal2decimales = Float.parseFloat(((new BigDecimal(montoTotal)).setScale(2,RoundingMode.HALF_UP)).toString());
			float descuentoTotal2decimales = Float.parseFloat(((new BigDecimal(descuentoTotal)).setScale(2,RoundingMode.HALF_UP)).toString());
			float montoTotalFinal2decimales = Float.parseFloat(((new BigDecimal(montoTotalFinal)).setScale(2,RoundingMode.HALF_UP)).toString());
			
			SoapObject localSoapObject = new SoapObject(NAMESPACE,CIERREDIAVENDEDORPROVINCIA_METHOD_NAME);
			localSoapObject.addProperty("vendedorId", Integer.valueOf(loginEmpleado.get_empleadoId()));
			localSoapObject.addProperty("anio", Integer.valueOf(loginEmpleado.get_anio()));
			localSoapObject.addProperty("mes", Integer.valueOf(loginEmpleado.get_mes()));
			localSoapObject.addProperty("dia", Integer.valueOf(loginEmpleado.get_dia()));
			localSoapObject.addProperty("numeroPreVentas", Integer.valueOf(nroPreventas));
			localSoapObject.addProperty("numeroItems", Integer.valueOf(nroItems));
			localSoapObject.addProperty("monto", String.valueOf(montoTotal2decimales));
			localSoapObject.addProperty("descuento", String.valueOf(descuentoTotal2decimales));
			localSoapObject.addProperty("montoFinal", String.valueOf(montoTotalFinal2decimales));
     
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			
			try
			{
				localHttpTransportSE.call(CIERREDIAVENDEDORPROVINCIA_SOAP_ACTION, localSoapSerializationEnvelope);
				
				soapObject = (SoapObject)localSoapSerializationEnvelope.getResponse();
				if(soapObject != null)
				{
					intResultado = Integer.parseInt(soapObject.getPropertyAsString("Id"));
			        stringResultado = soapObject.getPropertyAsString("Resultado");
				}
				
				if(stringResultado.equals("OK") && intResultado == 1)
				{
					WSCierreDiaPreventaProvincia = true;
				}
				return true;
			}
			catch (Exception localException)
			{
				WSCierreDiaPreventaProvincia = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSCierreDiaVendedorProvincia: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSCierreDiaVendedorProvincia: " + localException.getMessage());
				} 
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdCierreDiaVendedorProvincia.isShowing())
			{
				pdCierreDiaVendedorProvincia.dismiss();
			}
			
			if(ejecutado)
			{
				if(WSCierreDiaPreventaProvincia)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Cierre provincia realizado exitosamente.", 1);
					long i = 0;
					try
					{
						i = new CierrePreventistaBLL().InsertarCierrePreventista(loginEmpleado.get_empleadoId(),
								loginEmpleado.get_anio(),loginEmpleado.get_mes(),loginEmpleado.get_dia());
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cierrePreventista: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cierrePreventista: " + localException.getMessage());
						}
					}
					
					if(i<=0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el cierre preventista.", 1);
						return;
					}
					
					if(BorrarPreventas())
					{
						if(!BorrarPreventasProducto())
						{
							theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo borrar los productos de las preventas.", 1);
							return;
						}
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo borrar las preventas.", 1);
						return;
					}
					
					onBackPressed();
				}
				else
				{
					if(stringResultado == null)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webService WSCierreDiaVendedorProvincia.", 1);
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
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSCierreDiaVendedorProvincia.", 1);
				return;
			}
		}
	}

	@Override
	public void onBackPressed() 
	{
		Intent intent = new Intent(this,Menuvendedor.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
}
