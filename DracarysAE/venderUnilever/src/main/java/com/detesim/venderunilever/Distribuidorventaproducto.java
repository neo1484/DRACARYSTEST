package com.detesim.venderunilever;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import BLL.CanalRutaPrecioBLL;
import BLL.ClienteVentaBLL;
import BLL.MotivoNoEntregaBLL;
import BLL.MyLogBLL;
import BLL.ParametroGeneralBLL;
import BLL.PrecioListaBLL;
import BLL.PreventaDistBLL;
import BLL.PreventaProductoDistBLL;
import BLL.ProductoBLL;
import BLL.ProductoCostoBLL;
import BLL.PromocionBLL;
import BLL.PromocionCostoBLL;
import BLL.PromocionPrecioBLL;
import BLL.PromocionPrecioListaBLL;
import BLL.PromocionProductoBLL;
import BLL.SincronizacionVentaBLL;
import BLL.VentaProductoTempBLL;
import Clases.CanalRutaPrecio;
import Clases.ClienteVenta;
import Clases.Fecha;
import Clases.LoginEmpleado;
import Clases.MotivoNoEntrega;
import Clases.ParametroGeneral;
import Clases.PrecioLista;
import Clases.PreventaDist;
import Clases.PreventaProductoDist;
import Clases.Producto;
import Clases.ProductoCosto;
import Clases.Promocion;
import Clases.PromocionCosto;
import Clases.PromocionPrecio;
import Clases.PromocionPrecioLista;
import Clases.PromocionProducto;
import Clases.Ubicacion;
import Clases.VentaProductoTemp;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class Distribuidorventaproducto extends Activity implements OnClickListener
{
	LinearLayout llDistribuidorVentaProducto;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
	int preventaId;
	int productoId;
	int promocionId;
	int ventaProductoTempRowId;
	Producto producto;
	Promocion promocion;
	VentaProductoTemp ventaProductoTemp;
	PreventaDist preventaDist;
	PreventaProductoDist preventaProductoDist;
	ClienteVenta clienteVenta;
	PrecioLista precioLista;
	PromocionPrecioLista promocionPrecioLista;
	PromocionPrecio promocionPrecio;
	ParametroGeneral parametroGeneral;
	double latitudDistribuidor;
	double longitudDistribuidor;
	int cantidadPaquetes;
	int cantidadUnidades;
	float nuevoMonto;
	float nuevoDescuento;
	float nuevoMontoFinal;
	float nuevoDescuentoCanal;
	float nuevoDescuentoAjuste;
	int nuevoCostoId;
	CanalRutaPrecio canalRutaPrecio;
	
	TextView tvProductoPromocionlbl;
	TextView tvProductoPromocion;
	TextView tvCantidadUnitaria;
	TextView tvCantidadPaquetelbl;
	TextView tvCantidadPaquete;
	TextView tvCantidadPaqueteNuevalbl;
	
	Button btnEliminarProducto;
	Button btnModificarCantidades;
	Spinner spnMotivoNoEntrega;
	EditText etCantidad;
	EditText etCantidadPaquete;
	
	ProgressDialog pdEliminarVentaProductoTemp;
	ProgressDialog pdModificarVentaProductoTemp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_distribuidorventaproducto);
		
		llDistribuidorVentaProducto = (LinearLayout)findViewById(R.id.DistribuidorVentaProductoLinearLayout);
		tvProductoPromocionlbl = (TextView)findViewById(R.id.tvVentaProductoProductoPromocionlbl);
		tvProductoPromocion = (TextView)findViewById(R.id.tvVentaProductoProductoPromocion);
		tvCantidadUnitaria = (TextView)findViewById(R.id.tvVentaProductoCantidadUnitaria);
		tvCantidadPaquetelbl = (TextView)findViewById(R.id.tvVentaProductoCantidadPaquetelbl);
		tvCantidadPaquete = (TextView)findViewById(R.id.tvVentaProductoCantidadPaquete);
		btnEliminarProducto = (Button)findViewById(R.id.btnVentaProductoEliminarProducto);
		btnEliminarProducto.setOnClickListener(this);
		etCantidad = (EditText)findViewById(R.id.etVentaProductoNuevaCantidad);
		tvCantidadPaqueteNuevalbl = (TextView)findViewById(R.id.tvVentaProductoNuevaCantidadPaquetelbl);
		etCantidadPaquete = (EditText)findViewById(R.id.etVentaProductoNuevaCantidadPaquete);
		spnMotivoNoEntrega = (Spinner)findViewById(R.id.spnVentaProductoMotivoNoEntrega);
		btnModificarCantidades = (Button)findViewById(R.id.btnVentaProductoModificarCantidades);
		btnModificarCantidades.setOnClickListener(this);
		
		Bundle localBundle = getIntent().getExtras();
		preventaId = localBundle.getInt("PreventaId");
		productoId = localBundle.getInt("ProductoId");
		promocionId = localBundle.getInt("PromocionId");
		ventaProductoTempRowId = localBundle.getInt("VentaProductoTempRowId");
	      
		llDistribuidorVentaProducto.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
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
			if(InicializarObjetos())
			{
				MostrarVentaProducto();
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo inicializar los objetos de la venta producto.", 1);
			}
		}
	}

	@Override
	public void onClick(View v) 
	{	
		switch(v.getId())
		{
		case R.id.btnVentaProductoEliminarProducto:
			if(((MotivoNoEntrega)spnMotivoNoEntrega.getSelectedItem()).get_motivoId() == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "Debe seleccionar un Motivo de No Entrega.", 1);
				return;
			}
			else
			{
				if(ValidarVenta())
				{
					EliminarVentaProductoTemp();
				}
			}				
			break;
		case R.id.btnVentaProductoModificarCantidades:
			if(((MotivoNoEntrega)spnMotivoNoEntrega.getSelectedItem()).get_motivoId() == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "Debe seleccionar un Motivo de No Entrega.", 1);
				return;
			}
			else
			{
				if(etCantidad.getText().toString().isEmpty() && etCantidadPaquete.getText().toString().isEmpty())
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Debe ingresar una cantidad.", 1);
					return;
				}
				else
				{
					if(ValidarVenta())
					{
						if(VerificarCantidadesIngresadas())
						{
							if(InsertarProductoModificadoDispositivo())
							{
								ModificarVentaProductoTemp();
							}
						}
						else
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "La cantidad introducida no puede ser 0, mayor o igual que la actual.", 1);
							return;
						}	
					}				
				}
			}
			break;
		}
	}
	
	private boolean InicializarObjetos()
	{		
		if(productoId !=0)
		{
			producto =null;
			try
			{
				producto = new ProductoBLL().ObtenerProductoPor(productoId);
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
			
			if(producto == null)
			{
				return false;
			}
			
			preventaProductoDist = null;
			try
			{
				preventaProductoDist = new PreventaProductoDistBLL().ObtenerPreventasProductoDistPor(preventaId,productoId);
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventaProductoDist por preventaId y productoId: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventaProductoDist por preventaId y productoId: " + localException.getMessage());
				}
			}
			
			if(preventaProductoDist == null)
			{
				return false;
			}
		}
		
		if(promocionId != 0)
		{
			promocion =null;
			try
			{
				promocion = new PromocionBLL().ObtenerPromocionPor(promocionId);
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
			
			if(promocion == null)
			{
				return false;
			}
			
			promocionPrecioLista =null;
			try
			{
				promocionPrecioLista = new PromocionPrecioListaBLL().ObtenerPromocionPrecioListaPor(promocionId);
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de la promocionPrecioLista: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de la promocionPrecioLista: " + localException.getMessage());
				} 
			}
			
			if(promocionPrecioLista == null)
			{
				return false;
			}
		}
		
		ventaProductoTemp = null;
		try
		{
			ventaProductoTemp = new VentaProductoTempBLL().ObtenerVentaProductoTempPor(ventaProductoTempRowId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de la ventaProductoTemp: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de la ventaProductoTemp: " + localException.getMessage());
			} 
		}
		
		if(ventaProductoTemp == null)
		{
			return false;
		}
		
		preventaDist = null;
		try
		{
			preventaDist = new PreventaDistBLL().ObtenerPreventaDistPor(preventaId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de la preventa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de la preventa: " + localException.getMessage());
			} 
		}
		
		if(preventaDist == null)
		{
			return false;
		}
		
		clienteVenta = null;
		try
		{
			clienteVenta = new ClienteVentaBLL().ObtenerClienteVentaPor(preventaDist.get_clienteId());
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
		
		if(clienteVenta == null)
		{
			return false;
		}
		
		if(productoId != 0)
		{
			precioLista = null;
			try
			{
				precioLista = new PrecioListaBLL().ObtenerPrecioListaPor(clienteVenta.get_precioListaId(),productoId,
																		preventaProductoDist.get_precioId());
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del precioLista: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del precioLista: " + localException.getMessage());
				} 
			}
			
			if(precioLista == null)
			{
				return false;
			}
		}
		
		parametroGeneral = null;
		try
		{
			parametroGeneral = new ParametroGeneralBLL().ObtenerParamatroGeneral();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del parametroGeneral: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del parametroGeneral: " + localException.getMessage());
			} 
		}
		
		if(parametroGeneral == null)
		{
			return false;
		}
		
		return true;
	}

	private void MostrarVentaProducto()
	{
		ArrayList<MotivoNoEntrega> listadoMotivoNoEntrega = new ArrayList<MotivoNoEntrega>();
		try
		{
			listadoMotivoNoEntrega = new MotivoNoEntregaBLL().ObtenerMotivosNoEntrega();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivosNoEntrega: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivosNoEntrega: " + localException.getMessage());
			}	 
		}
			
		if(listadoMotivoNoEntrega !=null && listadoMotivoNoEntrega.size()>0)
		{
			ArrayAdapter<MotivoNoEntrega> localArrayAdapter = new ArrayAdapter<MotivoNoEntrega>(getApplicationContext(), R.layout.disenio_spinner, listadoMotivoNoEntrega);
			spnMotivoNoEntrega.setAdapter(localArrayAdapter);
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No existen motivos de No entrega que mostrar.", 1);
			return;
		}
		 
		if(productoId !=0)
		{
			tvProductoPromocionlbl.setText("Producto:");
			tvProductoPromocion.setText(producto.get_descripcion25());
			tvCantidadPaquete.setText(String.valueOf(ventaProductoTemp.get_cantidadPaquete()));
			MostrarControlesProducto(true);
		}
		
		if(promocionId != 0)
		{
			tvProductoPromocionlbl.setText("Promocion");
			tvProductoPromocion.setText(promocion.get_descripcion());
			MostrarControlesProducto(false);
		}
		
		tvCantidadUnitaria.setText(String.valueOf(ventaProductoTemp.get_cantidad()));
	}
	
	private void MostrarControlesProducto(boolean estado)
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
		
		tvCantidadPaquetelbl.setVisibility(visibility);
		tvCantidadPaquete.setVisibility(visibility);
		tvCantidadPaqueteNuevalbl.setVisibility(visibility);
		etCantidadPaquete.setVisibility(visibility);
	}

	private void EliminarVentaProductoTemp()
	{
		pdEliminarVentaProductoTemp = new ProgressDialog(this);
		pdEliminarVentaProductoTemp.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEliminarVentaProductoTemp.setMessage("Eliminando producto ...");
		pdEliminarVentaProductoTemp.setCancelable(false);
		pdEliminarVentaProductoTemp.setCanceledOnTouchOutside(false);

		WSEliminarVentaProductoTemp localWSEliminarVentaProductoTemp = new WSEliminarVentaProductoTemp();
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
    
		protected void onPreExecute()
	    {
			pdEliminarVentaProductoTemp.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSEliminarVentaProductoTemp = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,ELIMINARVENTAPRODUCTOTEMP_METHOD_NAME);
	        localSoapObject.addProperty("tempId", Integer.valueOf(ventaProductoTemp.get_tempId()));
	        localSoapObject.addProperty("motivoId", Integer.valueOf(((MotivoNoEntrega)spnMotivoNoEntrega.getSelectedItem()).get_motivoId()));
			
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
			
			if(ejecutado)
			{
				if(WSEliminarVentaProductoTemp) 
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"Producto eliminado del servidor.",1);
					InsertarProductoEliminadoDispositivo(true);
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"El producto no pudo ser eliminado del servidor.", 1);
					InsertarProductoEliminadoDispositivo(false);
				}
				
				MostrarPantallaVenta();				
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"El webservice WSDeleteVentaProductoTemp no se ejecuto correctamente.", 1);
				return;
			}
		}
	}
	
	private void InsertarProductoEliminadoDispositivo(boolean estadoSincronizacion)
	{
		boolean sincronizacion;
		if(estadoSincronizacion)
		{
			sincronizacion = true;
		}
		else
		{
			sincronizacion = false;
		}
		
		Fecha fecha = theUtilidades.ObtenerFecha();
		
		long l=0;
		try
		{
			l = new SincronizacionVentaBLL().InsertarSincronizacionVenta(ventaProductoTempRowId,preventaId,
											preventaDist.get_clienteId(),loginEmpleado.get_empleadoId(),productoId, 
											promocionId,ventaProductoTemp.get_cantidad(),
											ventaProductoTemp.get_cantidadPaquete(),0,0,0,preventaDist.get_tipoPagoId(),
											0,0,0,0,0,0,((MotivoNoEntrega)spnMotivoNoEntrega.getSelectedItem()).get_motivoId(),
											loginEmpleado.get_dia(),loginEmpleado.get_mes(),loginEmpleado.get_anio(),
											latitudDistribuidor,longitudDistribuidor,false,3,sincronizacion,
											fecha.get_hora(),fecha.get_minuto(),false,"","",false,
											ventaProductoTemp.get_costo(),0,0,0,preventaDist.get_observacion(),0,false,0,
											preventaDist.get_dosificacionId(),"",ventaProductoTemp.get_descuentoCanal(),
											ventaProductoTemp.get_descuentoAjuste(),0,ventaProductoTemp.get_descuentoProntoPago(),0,0,0,"",
											preventaDist.is_fromShopp());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacionVenta (Eliminar Producto): vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacionVenta (Eliminar Producto): " + localException.getMessage());
			} 
		}
		
		if(l<=0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la sincronizacion venta: EliminarProducto", 1);
			return;
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "Eliminacion registrada en el dispositivo.", 1);
			return;
		}
	}
    
	private boolean InsertarProductoModificadoDispositivo()
	{
		int nuevaCantidad = 0;
		int nuevaCantidadPaquete = 0;
		float nuevoMontoUnidades = 0;
		float nuevoMontoPaquete = 0;
		nuevoMonto = 0;
		float nuevoDescuentoUnidades = 0;
		float nuevoDescuentoPaquete = 0;
		nuevoDescuento = 0;
		float nuevoMontoFinalUnidades = 0;
		float nuevoMontoFinalPaquete = 0;
		nuevoMontoFinal = 0;
		ProductoCosto productoCosto;
		float precioSinDescuentoPromocion = 0;
		nuevoCostoId = 0;
		float nuevoDescuentoCanalUnidades;
		float nuevoDescuentoCanalPaquete;
		nuevoDescuentoCanal = 0;
		float nuevoDescuentoAjusteUnidades;
		float nuevoDescuentoAjustePaquete;
		nuevoDescuentoAjuste = 0;
		
		if(!etCantidad.getText().toString().isEmpty())
		{
			nuevaCantidad = Integer.parseInt(etCantidad.getText().toString());
		}
		
		if(!etCantidadPaquete.getText().toString().isEmpty())
		{
			nuevaCantidadPaquete = Integer.parseInt(etCantidadPaquete.getText().toString());
		}		 
				
		if(productoId != 0)
		{		
			productoCosto = null;
	        try
	        {
        		productoCosto = new ProductoCostoBLL().ObtenerProductosCosto(productoId);
	        }
	        catch(Exception localException)
	        {
	      	  	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	      	  	{
	      	  		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el costo del producto: vacio");
	      	  	}
	      	  	else
	      	  	{
	      	  		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el costo del producto: " + localException.getMessage());
	      	  	}
	      	  	return false;
	        }
	        
	        if(productoCosto == null)
	        {
	      	  	theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo obtener el costo del producto.",1);
	      	  	return false;
	        }
	        
	        //Verifico si el producto tiene ruta de precio
	        canalRutaPrecio = null;
	          
	        if(clienteVenta.get_canalRutaId() > 0)
	        {
	        	try
	        	{
	        		canalRutaPrecio = new CanalRutaPrecioBLL().ObtenerCanalRutaPrecioPorCanalRutaIdYProductoId(clienteVenta.get_canalRutaId(),producto.get_productoId());
	        	}
	        	catch(Exception localException)
	        	{
	        		if(localException.getMessage() == null || localException.getMessage().isEmpty())
	        		{
	        			myLog.InsertarLog("App",this.toString(),1,"Error al obtener el canal ruta precio por canalRutaId y productoId: vacio");
	        		}
	        		else
	        		{
	        			myLog.InsertarLog("App",this.toString(),1,"Error al obtener el canal ruta precio por canalRutaId y productoId: " + localException.getMessage());
	        		}  
	        	}
	        }
	        
	        if(canalRutaPrecio != null)
	        {
	        	float descuentoCanal = canalRutaPrecio.get_hpb() * (canalRutaPrecio.get_descuentoCanal() / 100);
	        	float descuentoAjuste = canalRutaPrecio.get_hpb() * (canalRutaPrecio.get_descuentoAjuste() / 100);
	        	
	        	nuevoMontoUnidades = nuevaCantidad * (canalRutaPrecio.get_hpb() / producto.get_conversion());
	        	nuevoMontoPaquete = nuevaCantidadPaquete * (canalRutaPrecio.get_hpb());
	        	nuevoMonto = nuevoMontoUnidades + nuevoMontoPaquete;
	        	
	        	nuevoDescuentoUnidades = 0;
	        	nuevoDescuentoPaquete = 0;
	        	nuevoDescuento = nuevoDescuentoUnidades + nuevoDescuentoPaquete;
	        	
	        	nuevoDescuentoCanalUnidades = (nuevaCantidad * descuentoCanal) / producto.get_conversion();
	        	nuevoDescuentoCanalPaquete = (nuevaCantidadPaquete * descuentoCanal);
	        	nuevoDescuentoCanal = nuevoDescuentoCanalUnidades + nuevoDescuentoCanalPaquete;
	        	
	        	nuevoDescuentoAjusteUnidades = (nuevaCantidad * descuentoAjuste) / producto.get_conversion();
	        	nuevoDescuentoAjustePaquete = (nuevaCantidadPaquete * descuentoAjuste);
	        	nuevoDescuentoAjuste = nuevoDescuentoAjusteUnidades + nuevoDescuentoAjustePaquete;
	        	
	        	nuevoMontoFinalUnidades = nuevoMontoUnidades - nuevoDescuentoUnidades - nuevoDescuentoCanalUnidades - nuevoDescuentoAjusteUnidades;
	        	nuevoMontoFinalPaquete = nuevoMontoPaquete - nuevoDescuentoPaquete - nuevoDescuentoCanalPaquete - nuevoDescuentoAjustePaquete;
	        	nuevoMontoFinal = nuevoMontoFinalUnidades + nuevoMontoFinalPaquete;
	        	
	        	nuevoCostoId = productoCosto.get_costoId();
	        }
	        else
	        {
	        	nuevoMontoUnidades = nuevaCantidad * precioLista.get_precio();
				nuevoMontoPaquete = nuevaCantidadPaquete * precioLista.get_precioPaquete();
				nuevoMonto = nuevoMontoUnidades + nuevoMontoPaquete;
				
				nuevoDescuentoUnidades = nuevoMontoUnidades * precioLista.get_descuentoUnidad()/100;
				nuevoDescuentoPaquete = nuevoMontoPaquete * precioLista.get_descuentoPaquete()/100;
				nuevoDescuento = nuevoDescuentoUnidades + nuevoDescuentoPaquete;
				
				nuevoMontoFinalUnidades = nuevoMontoUnidades - nuevoDescuentoUnidades;
				nuevoMontoFinalPaquete = nuevoMontoPaquete - nuevoDescuentoPaquete;
				nuevoMontoFinal = nuevoMontoFinalUnidades + nuevoMontoFinalPaquete;
				
				nuevoCostoId = productoCosto.get_costoId();
	        }
		}
		
		if(promocionId != 0)
		{			
			ArrayList<PromocionProducto> listadoPromocionProducto = null;
			try
			{
				listadoPromocionProducto = new PromocionProductoBLL().ObtenerPromocionesProductoPor(promocionId);
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
				return false;
			}
			
			ClienteVenta localClienteVenta = null;
			try
			{
				localClienteVenta = new ClienteVentaBLL().ObtenerClienteVentaPor(clienteVenta.get_clienteId());
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del clienteVenta: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del clientesVenta: " + localException.getMessage());
				} 
			}
			
			if(localClienteVenta == null)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del cliente de la venta.", 1);
	            return false;
			}
			
			float precioPromocion = 0;
			promocionPrecio = null;
			try
			{
				promocionPrecio = new PromocionPrecioBLL().ObtenerPromocionPrecioPor(promocionId,promocionPrecioLista.get_precioListaId());
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precio de la promocion por promocionId y precioListaId: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precio de la promocion por promocionId y precioListaId: " + localException.getMessage());
				} 
			}
			
			if(promocionPrecio == null)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el precio de la promocion.", 1);
				return false;
			}
			else
			{
				precioPromocion = promocionPrecio.get_precio();
			}
			
			precioSinDescuentoPromocion = ventaProductoTemp.get_monto()/ventaProductoTemp.get_cantidad();
			
			nuevoMonto = nuevaCantidad * precioSinDescuentoPromocion;
			nuevoMontoFinal = nuevaCantidad * precioPromocion;
			
			nuevoCostoId = ObtenerCostoIdPromocion(promocion.get_promocionId()); 
			
			if(parametroGeneral.get_descuentoPromocion())
			{
				nuevoDescuento = nuevoMonto * clienteVenta.get_descuento()/100;
				nuevoMontoFinal = nuevoMontoFinal - nuevoDescuento;
			}
			
			nuevoDescuento = Float.parseFloat(String.valueOf(new BigDecimal(nuevoMonto - nuevoMontoFinal).setScale(5,RoundingMode.HALF_UP)));
		}
		
		Fecha fecha = theUtilidades.ObtenerFecha();
		
		//Borro el registro del producto en caso de que exista uno anterior
		try
		{
			new SincronizacionVentaBLL().BorrarSincronizacionesVentaPor(preventaId,preventaDist.get_clienteId(),productoId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar la sincronizacionVenta por (preventaId, clienteId y productoId): vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar la sincronizacionVenta por (preventaId, clienteId y productoId): " + localException.getMessage());
			} 
			return false;
		}
		
		long l=0;
		try
		{
			l = new SincronizacionVentaBLL().InsertarSincronizacionVenta(ventaProductoTempRowId,preventaId,
					preventaDist.get_clienteId(),loginEmpleado.get_empleadoId(),productoId,promocionId,
					ventaProductoTemp.get_cantidad(),ventaProductoTemp.get_cantidadPaquete(),0,0,0,
					preventaDist.get_tipoPagoId(),nuevaCantidad,nuevaCantidadPaquete,nuevoMonto,nuevoDescuento,
					nuevoMontoFinal,0,((MotivoNoEntrega)spnMotivoNoEntrega.getSelectedItem()).get_motivoId(),
					loginEmpleado.get_dia(),loginEmpleado.get_mes(),loginEmpleado.get_anio(),latitudDistribuidor,
					longitudDistribuidor,false,4,false,fecha.get_hora(),fecha.get_minuto(),false,"","",false,
					0,0,0,nuevoCostoId,preventaDist.get_observacion(),
					productoId!=0?precioLista.get_precioId():promocionPrecio.get_precioId(),false,0,
					preventaDist.get_dosificacionId(),"",nuevoDescuentoCanal,nuevoDescuentoAjuste,
					ventaProductoTemp.get_canalPrecioRutaId(),ventaProductoTemp.get_descuentoProntoPago(),0,0,0,"",
					preventaDist.is_fromShopp());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacionVenta (Modificar Producto): vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacionVenta (Modificar Producto): " + localException.getMessage());
			} 
			return false;
		}
		
		if(l==0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la modificacion del producto.", 1);
			return false;
		}
		
		return true;
	}
	
	private int ObtenerCostoIdPromocion(int promocionId)
	{
		PromocionCosto localPromocionCosto = null;
		
		try
		{
			localPromocionCosto = new PromocionCostoBLL().ObtenerPromocionCostoPorPromocionId(promocionId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de la promocionCosto por promocionId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de la promocionCosto por promocionId: " + localException.getMessage());
			} 
		}
		
		if(localPromocionCosto == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del costo de la promocion.", 1);
			return 0;
		}
		else
		{
			return localPromocionCosto.get_costoId();
		}		
	}
	
	private void ModificarVentaProductoTemp()
	{
		pdModificarVentaProductoTemp = new ProgressDialog(this);
	    pdModificarVentaProductoTemp.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdModificarVentaProductoTemp.setMessage("Modificando venta ...");
	    pdModificarVentaProductoTemp.setCancelable(false);
	    pdModificarVentaProductoTemp.setCanceledOnTouchOutside(false);

	    WSModificarVentaProductoTemp localWSModificarVentaProductoTemp = new WSModificarVentaProductoTemp();
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
		
		boolean WSModificarVentaProductoTemp = false;
		SoapObject soapObjects;
		int resultadoInt;
		int precioId;
		String resultadoString;
    
		protected void onPreExecute()
	    {
			if(productoId !=0)
			{
				precioId = precioLista.get_precioId();
			}
			else
			{
				precioId = promocionPrecio.get_precioId();
			}
			pdModificarVentaProductoTemp.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSModificarVentaProductoTemp = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,MODIFICARVENTAPRODUCTOTEMP_METHOD_NAME);
	        localSoapObject.addProperty("tempId", Integer.valueOf(ventaProductoTemp.get_tempId()));
	        localSoapObject.addProperty("cantidadNueva", Integer.valueOf(cantidadUnidades));
	        localSoapObject.addProperty("cantidadPaqueteNueva", Integer.valueOf(cantidadPaquetes));
	        localSoapObject.addProperty("montoNuevo", String.valueOf(nuevoMonto));
	        localSoapObject.addProperty("descuentoNuevo", String.valueOf(nuevoDescuento));
	        localSoapObject.addProperty("montoFinalNuevo", String.valueOf(nuevoMontoFinal));
	        localSoapObject.addProperty("motivoId", Integer.valueOf(((MotivoNoEntrega)spnMotivoNoEntrega.getSelectedItem()).get_motivoId()));
	        localSoapObject.addProperty("costoNuevoId", Integer.valueOf(nuevoCostoId));
	        localSoapObject.addProperty("precioNuevoId", Integer.valueOf(precioId));
	        localSoapObject.addProperty("descuentoCanalNuevo", String.valueOf(nuevoDescuentoCanal));
	        localSoapObject.addProperty("descuentoAjusteNuevo", String.valueOf(nuevoDescuentoAjuste));
	        localSoapObject.addProperty("canaRutaPrecioNuevoId",canalRutaPrecio==null?0:canalRutaPrecio.get_canalPrecioRutaId());
        
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
			
			if(ejecutado)
			{
				if(WSModificarVentaProductoTemp)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Cantidad del producto modificada en el servidor.", 1);
					ModificarSincronizacionVenta();
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la cantidad del producto del servidor.", 1);
				}
				
				MostrarPantallaVenta();
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSUpdateVentaProductoTemp no se ejecuto correctamente.", 1);
				return;
			}
		}
    }
	
	private boolean VerificarCantidadesIngresadas()
	{
		boolean verificado = false;
		int cantidadConsolidadaActual = 0;
		int cantidadConsolidadaNueva = 0;
		cantidadPaquetes = 0;
		cantidadUnidades = 0;
		
		if(!etCantidad.getText().toString().isEmpty())
		{
			cantidadUnidades = Integer.parseInt(etCantidad.getText().toString());
		}
		
		if(!etCantidadPaquete.getText().toString().isEmpty())
		{
			cantidadPaquetes = Integer.parseInt(etCantidadPaquete.getText().toString());
		}
		
		if(cantidadUnidades == 0 && cantidadPaquetes == 0)
		{
			return false;
		}
		
		if(ventaProductoTemp.get_productoId() > 0)
		{
			cantidadConsolidadaActual = (ventaProductoTemp.get_cantidadPaquete() * producto.get_conversion()) + ventaProductoTemp.get_cantidad();
			cantidadConsolidadaNueva = (cantidadPaquetes * producto.get_conversion()) + cantidadUnidades;
		}
		
		if(ventaProductoTemp.get_promocionId() > 0)
		{
			cantidadConsolidadaActual = ventaProductoTemp.get_cantidad();
			cantidadConsolidadaNueva = cantidadUnidades;
		}
		
		if(cantidadConsolidadaNueva < cantidadConsolidadaActual)
		{
			verificado = true;
		}

		return verificado;
	} 

	private void ModificarSincronizacionVenta()
	{
		long modificado = 0;
		try
		{
			modificado = new SincronizacionVentaBLL().ModificarSincronizacionVentaSincronizacionPorVentaProductoTempRowId(ventaProductoTempRowId, true);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacionVenta por ventaProductoTempRowId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacionVenta por ventaProductoTempRowId: " + localException.getMessage());
			}
		}
		
		if(modificado == 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion de la venta.", 1);
			return;
		}
	}
	
	private boolean ValidarVenta()
	{
		if(theUtilidades.ValidarFecha(loginEmpleado.get_dia(),loginEmpleado.get_mes(),loginEmpleado.get_anio()))
	    {
			Ubicacion localUbicacion = null;
			try
		    {
		        localUbicacion = new Ubicacion(this);
		    }
		    catch (Exception localException)
		    {
		    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
		    	{
		            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ubicacion del distribuidor: vacio");
		    	}
		    	else
		    	{
		    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ubicacion del distribuidor: " + localException.getMessage());
		    	} 
		    }
		      
			if(localUbicacion == null) 
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la ubicacion del dispositivo. ", 1);
				return false;
			}
		   
			if(localUbicacion.canGetLocation()) 
			{
				latitudDistribuidor = localUbicacion.getLatitude();
				longitudDistribuidor = localUbicacion.getLongitude();
	    	}
	    }
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "La fecha del servidor no coincide con la del dispositivo.", 1);
			return false;
		}

		return true;
	  }
	
	private void MostrarPantallaVenta()
	{
	    Intent localIntent = new Intent(this, Distribuidorventa.class);
	    localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    localIntent.putExtra("ClienteId", preventaDist.get_clienteId());
	    localIntent.putExtra("ItemModificado", true);
	    startActivity(localIntent);
	  }
}
