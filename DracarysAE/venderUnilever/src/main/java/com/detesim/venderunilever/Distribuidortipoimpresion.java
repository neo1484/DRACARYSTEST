package com.detesim.venderunilever;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import BLL.ClienteVentaBLL;
import BLL.DatosFacturaBLL;
import BLL.DosificacionBLL;
import BLL.EmpleadoBLL;
import BLL.FacturaBLL;
import BLL.FacturaProductoBLL;
import BLL.ImpresoraBLL;
import BLL.MyLogBLL;
import BLL.ParametroGeneralBLL;
import BLL.PrecioListaBLL;
import BLL.PreventaDistBLL;
import BLL.ProductoBLL;
import BLL.PromocionBLL;
import BLL.PromocionPrecioListaBLL;
import BLL.PromocionProductoBLL;
import BLL.VentaBLL;
import BLL.VentaProductoBLL;
import Clases.ClienteVenta;
import Clases.DatosFactura;
import Clases.Dosificacion;
import Clases.Empleado;
import Clases.Factura;
import Clases.FacturaProducto;
import Clases.Fecha;
import Clases.Impresora;
import Clases.LoginEmpleado;
import Clases.ParametroGeneral;
import Clases.PrecioLista;
import Clases.PreventaDist;
import Clases.PrinterCommands;
import Clases.Producto;
import Clases.Promocion;
import Clases.PromocionPrecioLista;
import Clases.PromocionProducto;
import Clases.Venta;
import Clases.VentaProducto;
import Utilidades.Facturacion;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Distribuidortipoimpresion extends Activity implements OnClickListener
{
	LinearLayout llDistribuidorTipoImpresion;
	LoginEmpleado loginEmpleado = new LoginEmpleado();
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	MyLogBLL myLog = new MyLogBLL();
	Facturacion facturacion = new Facturacion();
	
	ArrayList<VentaProducto> listadoVentaProducto;
	long ventaId;
	Venta venta;
	ClienteVenta clienteVenta;
	PreventaDist preventaDist;
	Dosificacion dosificacion;
	DatosFactura datosFactura;
	ParametroGeneral parametroGeneral;
	int numeroFactura;
	Fecha fecha;
	ArrayList<PromocionProducto> listadoPromocionProducto;
	ArrayList<Impresora> listadoImpresora;
	String montoEnPalabras;
	String codigoControl;
	long facturaId;
	boolean facturaAlmacenada=false;
	Factura factura;
	ArrayList<FacturaProducto> listadoFacturaProductoNoSincronizado;
	ArrayList<FacturaProducto> listadoFacturaProducto;
	String nit;
	String nombreFactura;
	boolean nitNuevo;
	Bitmap qr = null;
	boolean autoventa;
	BitSet dots = new BitSet();
	
	BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
	
	OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;
 
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;
    int tamanioTotal;
	
	Button btnNotaEntrega;
	Button btnFacturar;
	TextView tvNombreFactura;
	EditText etNombreFactura;
	TextView tvNit;
	EditText etNit;
	Button btnCambiarDatos;
	Button btnMantenerDatos;
	Button btnFactura;
	Button btnSincronizarFactura;
	Button btnVolverMapa;
	ProgressDialog pdEsperaInsertarFacturaProductoTemp;
	ProgressDialog pdEsperaInsertarFactura;
	ProgressDialog pdImprimiendoFactura;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_distribuidortipoimpresion);
		
		llDistribuidorTipoImpresion = (LinearLayout)findViewById(R.id.DistribuidorTipoImpresionLinearLayout);
		btnNotaEntrega = (Button)findViewById(R.id.btnDistribuidorTipoFacturaNotaEntrega);
		btnNotaEntrega.setOnClickListener(this);
		btnFacturar = (Button)findViewById(R.id.btnTipoImpresionFacturar);
		btnFacturar.setOnClickListener(this);
		tvNombreFactura = (TextView)findViewById(R.id.tvTipoImpresionNombreFactura);
		etNombreFactura = (EditText)findViewById(R.id.etTipoImpresionNombreFactura);
		tvNit = (TextView)findViewById(R.id.tvTipoImpresionNit);
		etNit = (EditText)findViewById(R.id.etTipoImpresionNit);
		btnCambiarDatos = (Button)findViewById(R.id.btnTipoImpresionCambiarDatos);
		btnCambiarDatos.setOnClickListener(this);
		btnMantenerDatos = (Button)findViewById(R.id.btnTipoImpresionMantenerDatos);
		btnMantenerDatos.setOnClickListener(this);
		btnFactura = (Button)findViewById(R.id.btnDistribuidorTipoFacturaFactura);
		btnFactura.setOnClickListener(this);
		btnSincronizarFactura = (Button)findViewById(R.id.btnTipoImpresionSincronizarFactura);
		btnSincronizarFactura.setOnClickListener(this);
		btnVolverMapa = (Button)findViewById(R.id.btnDistribuidorTipoFacturaVolverMapa);
		btnVolverMapa.setOnClickListener(this);
		
		llDistribuidorTipoImpresion.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
		ventaId = 0;
	    ventaId = getIntent().getExtras().getLong("ventaId");
	    if(ventaId == 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo Obtener un Id de Venta.", 1);
	    	return;
	    }
	    
	    autoventa = false;
	    autoventa = getIntent().getExtras().getBoolean("autoventa");
	    if(autoventa)
	    {
	    	nit = getIntent().getExtras().getString("nit");
			nombreFactura = getIntent().getExtras().getString("factura");
			nitNuevo = getIntent().getExtras().getBoolean("nitNuevo");
	    }
		
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
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El usuario no se logeo correctamente.", 1);
	    	return;
	    }
	    else
	    {
	    	btnFactura.setVisibility(View.INVISIBLE);
	    	btnSincronizarFactura.setVisibility(View.INVISIBLE);
	    	
	    	fecha = theUtilidades.ObtenerFecha();
	    	
	    	if(ObtenerVentaPor((int)ventaId))
	    	{
	    		if(ObtenerVentaProductoPor((int)ventaId))
				{
	    			if(ObtenerClienteVenta(venta.get_clienteId()))
	    			{
	    				if(autoventa == false)
	    				{
	    					if(ObtenerPreventaDist(venta.get_preventaId()))
			    			{
			    				nit = preventaDist.get_nit();
			    				nombreFactura = preventaDist.get_nombreFactura();
			    				nitNuevo = false;
			    				
			    				if(ObtenerImpresoras())
			    				{
			    					if(ObtenerDosificacion())
			    					{
			    						if(ObtenerDatosFactura())
			    						{   				
			    							if(ObtenerParametrosGenerales())
			    							{		    								
			    								if(parametroGeneral.is_facturarTodo())
			    								{
			    									MostrarControles(false);
			    									MostrarControlesCambioNit(true);
			    								}
			    								else
			    								{
			    									MostrarControles(true);
			    									MostrarControlesCambioNit(false);
			    								}
			    							}
			    						}
			    						else
			    						{
			    							theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los parametros generales.", 1);
			    						}
			    					}
			    					else
			    					{
			    						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la dosificacion.", 1);
			    						MostrarControles(true);
			    						MostrarControlesCambioNit(false);
			    					}
			    				}
			    				else
			    				{
			    					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las impresoras.", 1);
			    					MostrarControles(true);
			    					MostrarControlesCambioNit(false);
			    				}
							}
			    			else
			    			{
			    				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles de la preventa.", 1);
			    			}	
	    				}
	    				else
	    				{	
	    					if(ObtenerImpresoras())
    						{
			    				if(ObtenerDosificacion())
			    				{
			    					if(ObtenerDatosFactura())
			    					{   						
		    							if(ObtenerParametrosGenerales())
		    							{		    								
		    								if(parametroGeneral.is_facturarTodo())
		    								{
		    									MostrarControles(false);
		    									MostrarControlesCambioNit(true);
	    									}
		    								else
		    								{
		    									MostrarControles(true);
		    									MostrarControlesCambioNit(false);
		    								}	
				    					}
				    					else
				    					{
				    						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los parametros generales.", 1);
				    					}
			    					}
			    				}
			    				else
			    				{
			    					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la dosificacion.", 1);
			    					MostrarControles(true);
			    					MostrarControlesCambioNit(false);
			    				}
    						}
    						else
    						{
    							theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las impresoras.", 1);
    							MostrarControles(true);
    							MostrarControlesCambioNit(false);
    						}
	    				}
					}
	    			else
	    			{
	    				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del clienteVenta.", 1);
	    			}
				}
	    		else
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron productos en las venta.", 1);
	    		}
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo encontrar la venta.", 1);
	    	}
	    }
	}
	
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.btnDistribuidorTipoFacturaNotaEntrega:
			ImprimirNotaEntrega();
			break;
		case R.id.btnTipoImpresionFacturar:
			Facturar();
			break;
		case R.id.btnTipoImpresionCambiarDatos:
			CambiarDatos();			
			break;
		case R.id.btnTipoImpresionMantenerDatos:
			MantenerDatos();
			break;
		case R.id.btnDistribuidorTipoFacturaFactura:
			ImprimirFactura();
			break;
		case R.id.btnTipoImpresionSincronizarFactura:
			if(ObtenerFactura())
			{
				if(ObtenerFacturaProductoNoSincronizados())
				{
					SincronizarFacturaProducto();
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los productos de la factura.", 1);
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los datos de la factura.", 1);
			}
			break;
		case R.id.btnDistribuidorTipoFacturaVolverMapa:
			MostrarPantallaMapa();
			break;
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
		
		if(listadoImpresora == null)
		{
			btnNotaEntrega.setVisibility(View.INVISIBLE);
			btnFacturar.setVisibility(View.INVISIBLE);
		}
		else
		{
			btnNotaEntrega.setVisibility(visibility);
			btnFacturar.setVisibility(visibility);
		}
		
		btnVolverMapa.setVisibility(visibility);
	}
	
	private void MostrarControlesCambioNit(boolean estado)
	{
		int visibility;
		if(estado)
		{
			visibility = View.VISIBLE;
			etNombreFactura.setText(nombreFactura);
			etNit.setText(nit);
		}
		else
		{
			visibility = View.INVISIBLE;
		}
		tvNombreFactura.setVisibility(visibility);
		etNombreFactura.setVisibility(visibility);
		tvNit.setVisibility(visibility);
		etNit.setVisibility(visibility);
		btnCambiarDatos.setVisibility(visibility);
		btnMantenerDatos.setVisibility(visibility);
	}
	
	private void CambiarDatos()
	{
		if(etNombreFactura.getText().length() == 0 || etNit.getText().length() == 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "El nombre y el nit no pueden ser vacios.", 1);
			return;
		}
		nombreFactura = etNombreFactura.getText().toString();
		nit = etNit.getText().toString();
		nitNuevo = true;
		etNombreFactura.setEnabled(false);
		etNit.setEnabled(false);
		btnCambiarDatos.setVisibility(View.INVISIBLE);
		btnMantenerDatos.setVisibility(View.INVISIBLE);
		btnFactura.setVisibility(View.VISIBLE);
	}
	
	private void MantenerDatos()
	{
		etNombreFactura.setEnabled(false);
		etNit.setEnabled(false);
		btnCambiarDatos.setVisibility(View.INVISIBLE);
		btnMantenerDatos.setVisibility(View.INVISIBLE);
		btnFactura.setVisibility(View.VISIBLE);
	}
	
	private boolean ObtenerFactura()
	{
		factura = null;
		try
		{
			factura = new FacturaBLL().ObtenerFacturaPorVentaId((int)ventaId);
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
			return false;
		}
		
		if(factura != null)
		{
			listadoFacturaProducto = null;
			try
			{
				listadoFacturaProducto = new FacturaProductoBLL().ObtenerFacturasProductoPorFacturaId(factura.get_rowId());
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la factura por facturaId: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los Productos de la factura por facturaId: " + localException.getMessage());
				}
				return false;
			}
			
			if(listadoFacturaProducto != null)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		return false;
	}
	
	private boolean ObtenerVentaPor(int ventaId)
	{
		boolean obtenido = false;
		venta = null;
		try
		{
			venta = new VentaBLL().ObtenerVentaPor(ventaId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta por ventaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta por ventaId: " + localException.getMessage());
			} 
		}
		
		if(venta != null)
		{
			obtenido = true;
		}
		
		return obtenido;
	}
	
	private boolean ObtenerVentaProductoPor(int ventaId)
	{
		boolean obtenido = false;
		listadoVentaProducto = null;
		try
		{
			listadoVentaProducto = new VentaProductoBLL().ObtenerVentasProductoPor(ventaId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProducto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProducto: " + localException.getMessage());
			} 
		}
		
		if(listadoVentaProducto != null)
		{
			obtenido = true;
		}
		
		return obtenido;
	}

	private boolean ObtenerClienteVenta(int clienteId)
	{
		boolean obtenido = false;
		clienteVenta = null;
		try
		{
			clienteVenta = new ClienteVentaBLL().ObtenerClienteVentaPor(clienteId);
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
		
		if(clienteVenta != null)
		{
			obtenido = true;
		}
		
		return obtenido;
	}
	
	private boolean ObtenerPreventaDist(int preventaId)
	{
		boolean obtenido = false;
		preventaDist = null;
		try
		{
			preventaDist = new PreventaDistBLL().ObtenerPreventaDistPor(preventaId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventaDist: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventaDist: " + localException.getMessage());
			} 
		}
		
		if(preventaDist != null)
		{
			obtenido = true;
		}
		
		return obtenido;
	}
	
	private boolean ObtenerDosificacion()
	{
		boolean obtenido = false;
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
		
		if(dosificacion != null)
		{
			obtenido = true;
		}
		
		return obtenido;
	}
	
	private boolean ObtenerDatosFactura()
	{
		boolean obtenido = false;
		datosFactura = null;
		try
		{
			datosFactura = new DatosFacturaBLL().ObtenerDatosFactura();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los datos de la factura: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los datos de la factura: " + localException.getMessage());
			} 
		}
		
		if(datosFactura != null)
		{
			obtenido = true;
		}
		
		return obtenido;
	}
		
	private boolean ObtenerImpresoras()
	{
		boolean cargado = false;
		listadoImpresora = null;
		try
		{
			listadoImpresora = new ImpresoraBLL().ObtenerImpresoras();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las impresoras: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las impresoras: " + localException.getMessage());
			} 
		}
		
		if(listadoImpresora == null)
		{
			return cargado;
		}
		else
		{
			cargado = true;
		}
		
		return cargado;
	}
	
	private boolean ObtenerParametrosGenerales()
	{
		parametroGeneral = null;
		try
		{
			parametroGeneral = new ParametroGeneralBLL().ObtenerParamatroGeneral();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los parametros generales: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los parametros generales: " + localException.getMessage());
			} 
		}
		
		if(parametroGeneral == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los parametros generales.", 1);
			return false;
		}
		else
		{
			return true;
		}
	}
	
	private void ImprimirNotaEntrega()
	{
		if(BuscarImpresora())
		{
			if(ConectarBluetooth())
			{
				EnviarDatosRecibo();
				
				if(CerrarBluetooth())
				{
					MostrarPantallaMapa();
				}
			}
		}
	}
	
	private void Facturar()
	{
		MostrarControles(false);
		MostrarControlesCambioNit(true);
	}
	
	private void ImprimirFactura()
	{
		if(dosificacion != null)
		{
			if(facturaAlmacenada == false)
			{				
				if(GuardarDatosFactura())
				{
					if(ObtenerFactura())
					{
						if(GenerarCodigoQR())
						{
							if(BuscarImpresora())
							{
								if(ConectarBluetooth())
								{
									EnviarDatosFactura();
									
									if(CerrarBluetooth())
									{
										btnFactura.setVisibility(View.INVISIBLE);
										MostrarControlesCambioNit(false);
										btnSincronizarFactura.setVisibility(View.VISIBLE);
									}
								}
							}
						}
						else
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo generar el codigo QR.", 1);
							return;
						}
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo encontrar la factura.", 1);
						return;
					}
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "La factura ya fue almacenada, reimprima la factura.", 1);
				return;
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontro una dosificacion para procesar la factura.", 1);
			return;
		}
	}
	
	private boolean GenerarCodigoQR()
	{
		boolean generado = false;
		
		String nitEmisor = parametroGeneral.get_nit();
		String importeIC = "0";
		String importeVentasNoGravadas = "0";
		String importeNoSujetoACreditoFiscal = "0";
		
		String codigoQR = nitEmisor + "|" + factura.get_numero() + "|" + factura.get_codigoAutorizacion() + 
			"|" + factura.get_fechaDia() + "/" + factura.get_fechaMes() + "/" + factura.get_fechaAnio() + "|" +
			factura.get_montoTotal() + "|" +factura.get_montoFinal() + "|" +  factura.get_codigoControl() + "|" + 
			factura.get_nit() + "|" + importeIC + "|" + importeVentasNoGravadas + "|" + importeNoSujetoACreditoFiscal + "|" + factura.get_descuento();
		
		qr = GenerarQR(codigoQR,225);
		
		if(qr!=null)
		{	
			generado = true;
		}
		
		return generado;
	}
	
	private boolean BuscarImpresora() 
	{
		boolean impresoraEncontrada = false;
		try 
		{
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
 
            if (mBluetoothAdapter == null) 
            {
            	theUtilidades.MostrarMensaje(getApplicationContext(), "No existe un adaptador bluetooth disponible.", 1);
            	return false;
            }
 
            if (!mBluetoothAdapter.isEnabled()) 
            {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
            }
 
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) 
            {
                for (BluetoothDevice device : pairedDevices) 
                {
                	for(Impresora itemImpresora : listadoImpresora)
                	{
                		if (device.getName().equals(itemImpresora.get_nombre()) && device.getAddress().equals(itemImpresora.get_address())) 
                        {
                            mmDevice = device;
                            break;
                        }
                	}
                }
            }
            
            if(mmDevice!=null)
            {
            	theUtilidades.MostrarMensaje(getApplicationContext(), "Impresora encontrada.", 1);
                impresoraEncontrada = true;
            }
            else
            {
            	theUtilidades.MostrarMensaje(getApplicationContext(), "Impresora no encontrada.", 1);
            }
        } 
		catch (NullPointerException localException) 
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error NullPointerException al obtener un dispositivo bluetooth: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error NullPointerException al obtener un dispositivo bluetooth: " + localException.getMessage());
			} 
			theUtilidades.MostrarMensaje(getApplicationContext(), "Error al buscar la impresora.", 1);
			return false;
        } 
		catch (Exception localException) 
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener un dispositivo bluetooth: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener un dispositivo bluetooth: " + localException.getMessage());
			} 
			theUtilidades.MostrarMensaje(getApplicationContext(), "Error al buscar la impresora.", 1);
			return false;
        }
		
		return impresoraEncontrada;
    }
	
	private boolean ConectarBluetooth()
	{
		boolean conectado = false;
        try 
        {
            // Standard SerialPortServiceID UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        	UUID uuid = mmDevice.getUuids()[0].getUuid();
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();
 
            EscucharTransmision();
 
            theUtilidades.MostrarMensaje(getApplicationContext(), "Bluetooth conectado.", 1);
            conectado = true;
        } 
        catch (NullPointerException localException) 
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error NullPointerException al abrir la conexion bluetooth: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error NullPointerException al abrir la conexion bluetooth: " + localException.getMessage());
			} 
			theUtilidades.MostrarMensaje(getApplicationContext(), "Error al conectarse al Bluetooth.", 1);
			return false;
        } 
		catch (Exception localException) 
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al abrir la conexion bluetooth: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al abrir la conexion bluetooth: " + localException.getMessage());
			} 
			theUtilidades.MostrarMensaje(getApplicationContext(), "Error al conectarse al Bluetooth.", 1);
			return false;
        }
        
        return conectado;
    }
	
	private void EscucharTransmision() 
	{
		final Handler handler = new Handler();
        try 
        {
            // This is the ASCII code for a newline character
            final byte delimiter = 10;
 
            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];
 
            workerThread = new Thread(new Runnable() 
            {
                public void run() 
                {
                    while(!Thread.currentThread().isInterrupted() && !stopWorker) 
                    {
                         try 
                         {
                            int bytesAvailable = mmInputStream.available();
                            
                            if (bytesAvailable > 0) 
                            {
                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);
                                
                                for (int i = 0; i < bytesAvailable; i++) 
                                {
                                    byte b = packetBytes[i];
                                    if (b == delimiter) 
                                    {
                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(readBuffer,0,encodedBytes,0,encodedBytes.length);
                                        
                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;
 
                                        handler.post(new Runnable() 
                                        {
                                            public void run() 
                                            {
                                                theUtilidades.MostrarMensaje(getApplicationContext(),data,1);
                                            }
                                        });
                                    } 
                                    else 
                                    {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                        	}
                         } 
                         catch (IOException localException) 
                         {
                            stopWorker = true;
                            if(localException.getMessage() == null || localException.getMessage().isEmpty())
                        	{
                                myLog.InsertarLog("App",this.toString(),1,"Error al leer el buffer de datos: vacio");
                        	}
                        	else
                        	{
                        		myLog.InsertarLog("App",this.toString(),1,"Error al leer el buffer de datos: " + localException.getMessage());
                        	} 
                            theUtilidades.MostrarMensaje(getApplicationContext(), "Error al leer el buffer de datos.", 1);
                        	return;
                        }
                    }
                }
            });
 
            workerThread.start();
        } 
        catch (NullPointerException localException) 
        {
        	if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
                myLog.InsertarLog("App",this.toString(),1,"Error NullPointerException al leer el buffer de datos: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error NullPointerException al leer el buffer de datos: " + localException.getMessage());
        	} 
        	theUtilidades.MostrarMensaje(getApplicationContext(), "Error al leer el buffer de datos.", 1);
        	return;
        } 
        catch (Exception localException) 
        {
        	if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
                myLog.InsertarLog("App",this.toString(),1,"Error al leer el buffer de datos: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al leer el buffer de datos: " + localException.getMessage());
        	} 
        	theUtilidades.MostrarMensaje(getApplicationContext(), "Error al leer el buffer de datos.", 1);
        	return;
        }
    }

	private void EnviarDatosRecibo() 
	{		
		String cadenaFormateada="";
		int tamanioTotal = 48;
		
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal,"NOTA DE ENTREGA") + "\n";
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal,"===============") + "\n\n";
				
		cadenaFormateada += "Fecha:  " + theUtilidades.ObtenerFechaString() + "\n";
		cadenaFormateada += "Nombre: " + clienteVenta.get_nombreCompleto() + "\n";			
		cadenaFormateada += "________________________________________________\n";
		cadenaFormateada += "Cantidad Producto                       Subtotal\n";
		cadenaFormateada += "________________________________________________\n";
		
		String strCantidad = "";
		String strProducto = "";
		String strPrecio = "";
		String strTotal = "";
		
		Producto producto = null;
		Promocion promocion = null;
		PrecioLista precioLista = null;
		PromocionPrecioLista promocionPrecioLista = null;
		
		for(VentaProducto item : listadoVentaProducto)
		{
			strCantidad = "";
			strProducto = "";
			strPrecio = "";
			strTotal = "";
			
			if(item.get_productoId()>0)
			{
				producto = ObtenerProductoPor(item.get_productoId());
				precioLista = ObtenerPrecioListaPor(clienteVenta.get_precioListaId(),producto.get_productoId());
				
				
				if(item.get_cantidad()>0)
				{
					strCantidad = theUtilidades.FormatearTextoADimensionAnterior(String.valueOf(item.get_cantidad()),4);
					strProducto = theUtilidades.FormatearTextoADimensionPosterior(producto.get_descripcionUnidad25(),25);
					strPrecio = theUtilidades.FormatearTextoADimensionAnterior(String.valueOf(new BigDecimal(precioLista.get_precio()).setScale(2,RoundingMode.HALF_UP)),7);
					strTotal = theUtilidades.FormatearTextoADimensionAnterior(String.valueOf(new BigDecimal(item.get_monto()).setScale(2, RoundingMode.HALF_UP)),7);
					cadenaFormateada += strCantidad + "  " + strProducto + " " + strPrecio + " " + strTotal + "\n";
				}
				else
				{
					strCantidad = theUtilidades.FormatearTextoADimensionAnterior(String.valueOf(item.get_cantidadPaquete()),4);
					strProducto = theUtilidades.FormatearTextoADimensionPosterior(producto.get_descripcion25(),25);
					strPrecio = theUtilidades.FormatearTextoADimensionAnterior(String.valueOf(new BigDecimal(precioLista.get_precioPaquete()).setScale(2,RoundingMode.HALF_UP)),7);
					strTotal = theUtilidades.FormatearTextoADimensionAnterior(String.valueOf(new BigDecimal(item.get_monto()).setScale(2,RoundingMode.HALF_UP)),7);
					cadenaFormateada += strCantidad + "  " + strProducto + " " + strPrecio + " " + strTotal + "\n";
				}
			}
			
			if(item.get_promocionId()>0)
			{
				promocion = ObtenerPromocionPor(item.get_promocionId());
				promocionPrecioLista = ObtenerPromocionPrecioListaPor(promocion.get_promocionId());
				float precioPromo = ObtenerPrecioPromocionPorLista(promocion.get_promocionId(),promocionPrecioLista.get_precioListaId());					
				strPrecio = theUtilidades.FormatearTextoADimensionAnterior(String.valueOf(new BigDecimal(precioPromo).setScale(2,RoundingMode.HALF_UP)),7);
				
				if(item.get_cantidad()>0)
				{
					strCantidad = theUtilidades.FormatearTextoADimensionAnterior(String.valueOf(item.get_cantidad()),4);
					strProducto = theUtilidades.FormatearTextoADimensionPosterior(promocion.get_descripcion25(),25);
					strTotal = theUtilidades.FormatearTextoADimensionAnterior(String.valueOf(new BigDecimal(item.get_monto()).setScale(2,RoundingMode.HALF_UP)),7);
					cadenaFormateada += strCantidad + "  " + strProducto + " " + strTotal + " " + strPrecio + "\n";
					
					if(ObtenerPromocionProductoPorPromocionIdPrecioListaId(item.get_promocionId(),clienteVenta.get_precioListaId()))
					{
						for(PromocionProducto itemPromo : listadoPromocionProducto)
						{
							int cantidad = itemPromo.get_cantidad()==0?itemPromo.get_cantidadPaquete():itemPromo.get_cantidad();
							cadenaFormateada += theUtilidades.FormatearTextoADimensionAnterior("* " + String.valueOf(cantidad),11);
							Producto productoPromo = ObtenerProductoPor(itemPromo.get_productoId());
							cadenaFormateada += theUtilidades.FormatearTextoADimensionAnterior(productoPromo.get_descripcion25(),25) + "\n";
						}
					}
				}
			}
		}
		cadenaFormateada += "--------------------------------------------\n";
		cadenaFormateada += "Total: " + theUtilidades.FormatearTextoADimensionAnterior(String.valueOf(venta.get_montoFinal()),25) + "\n\n";
	
		String montoEnPalabras = facturacion.ObtenerMontoEnPalabras(Double.parseDouble(new BigDecimal(venta.get_montoFinal()).setScale(2,RoundingMode.HALF_UP).toString()));
		
		cadenaFormateada += "Son: " + montoEnPalabras + "\n\n";
		cadenaFormateada += "!!!GRACIAS POR SU COMPRA!!!\n\n\n\n\n";
		
        try 
        {
            mmOutputStream.write(cadenaFormateada.getBytes());   
        } 
        catch (NullPointerException localException) 
        {
        	if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
                myLog.InsertarLog("App",this.toString(),1,"Error NullPointerException al enviar los datos: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error NullPointerException al enviar los datos: " + localException.getMessage());
        	} 
        	
        	theUtilidades.MostrarMensaje(getApplicationContext(), "Error al enviar los datos.", 1);
        	return;
        } 
        catch (Exception localException) 
        {
        	if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
                myLog.InsertarLog("App",this.toString(),1,"Error al enviar los datos: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al enviar los datoss: " + localException.getMessage());
        	} 
        	
        	theUtilidades.MostrarMensaje(getApplicationContext(), "Error al enviar los datos.", 1);
        	return;
        }
        
        // tell the user data were sent
        theUtilidades.MostrarMensaje(getApplicationContext(), "Datos enviados.", 1);
    }
	
	private void EnviarDatosFactura() 
	{
		tamanioTotal = 48;
		String cadenaFormateada="";
		
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal,datosFactura.get_nombreEmpresa()) + "\n\n";
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal,dosificacion.get_sucursal()) + "\n\n";
		
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal,"SFC - " + dosificacion.get_sfc()) + "\n";
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal,"F A C T U R A") + "\n";
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal,"ORIGINAL") + "\n";
		
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal,"NIT: " + datosFactura.get_nit()) + "\n";
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal,"Factura No: " + 
		theUtilidades.FormatearTextoACerosAnterior(String.valueOf(Integer.parseInt(dosificacion.get_numeroInicial()) +1),8)) + "\n";
		
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal,"No. Autorizacion: " + dosificacion.get_codigoAutorizacion()) + "\n\n";
		
		cadenaFormateada += "Actividad: " + dosificacion.get_actividad() + "\n\n";
		
		cadenaFormateada += "Fecha : " + theUtilidades.ObtenerFechaLiteralString() + "\n";
		cadenaFormateada += "Nombre : " + nombreFactura.toUpperCase(Locale.ENGLISH) + "\n";
		cadenaFormateada += "NIT : " + nit + "\n\n";
		
		cadenaFormateada += "Cantidad Descripcion              Precio   Total\n";
		cadenaFormateada += "________________________________________________\n\n";			
		
		String strCantidad = "";
		String strProducto = "";
		String strPrecio = "";
		String strTotal = "";
		
		Producto producto = null;
		Promocion promocion = null;
		PrecioLista precioLista = null;
		PromocionPrecioLista promocionPrecioLista = null;
		
		for(FacturaProducto item : listadoFacturaProducto)
		{
			strCantidad = "";
			strProducto = "";
			strPrecio = "";
			strTotal = "";
			
			if(item.get_productoId()>0)
			{
				producto = ObtenerProductoPor(item.get_productoId());
				precioLista = ObtenerPrecioListaPor(clienteVenta.get_precioListaId(),producto.get_productoId());
				
				if(item.get_cantidad()>0)
				{
					strCantidad = theUtilidades.FormatearTextoADimensionAnterior(String.valueOf(item.get_cantidad()),4);
					strProducto = theUtilidades.FormatearTextoADimensionPosterior(producto.get_descripcionUnidad25(),25);
					strPrecio = theUtilidades.FormatearTextoADimensionAnterior(String.valueOf(new BigDecimal(precioLista.get_precio()).setScale(2,RoundingMode.HALF_UP)),7);
					strTotal = theUtilidades.FormatearTextoADimensionAnterior(String.valueOf(new BigDecimal(item.get_montoFinal()).setScale(2, RoundingMode.HALF_UP)),7);
					cadenaFormateada += strCantidad + "  " + strProducto + " " + strPrecio + " " + strTotal + "\n";
				}
				else
				{
					strCantidad = theUtilidades.FormatearTextoADimensionAnterior(String.valueOf(item.get_cantidadPaquete()),4);
					strProducto = theUtilidades.FormatearTextoADimensionPosterior(producto.get_descripcion25(),25);
					strPrecio = theUtilidades.FormatearTextoADimensionAnterior(String.valueOf(new BigDecimal(precioLista.get_precioPaquete()).setScale(2,RoundingMode.HALF_UP)),7);
					strTotal = theUtilidades.FormatearTextoADimensionAnterior(String.valueOf(new BigDecimal(item.get_monto()).setScale(2,RoundingMode.HALF_UP)),7);
					cadenaFormateada += strCantidad + "  " + strProducto + " " + strPrecio + " " + strTotal + "\n";
				}
			}
			if(item.get_promocionId()>0)
			{
				promocion = ObtenerPromocionPor(item.get_promocionId());
				promocionPrecioLista = ObtenerPromocionPrecioListaPor(promocion.get_promocionId());
				float precioPromo = ObtenerPrecioPromocionPorLista(promocion.get_promocionId(),promocionPrecioLista.get_precioListaId());					
				strPrecio = theUtilidades.FormatearTextoADimensionAnterior(String.valueOf(new BigDecimal(precioPromo).setScale(2,RoundingMode.HALF_UP)),7);
				
				if(item.get_cantidad()>0)
				{
					strCantidad = theUtilidades.FormatearTextoADimensionAnterior(String.valueOf(item.get_cantidad()),4);
					strProducto = theUtilidades.FormatearTextoADimensionPosterior(promocion.get_descripcion25(),25);
					strTotal = theUtilidades.FormatearTextoADimensionAnterior(String.valueOf(new BigDecimal(item.get_monto()).setScale(2,RoundingMode.HALF_UP)),7);
					cadenaFormateada += strCantidad + "  " + strProducto + " " + strPrecio + " " + strTotal + "\n";
					
					if(ObtenerPromocionProductoPorPromocionIdPrecioListaId(item.get_promocionId(),clienteVenta.get_precioListaId()))
					{
						for(PromocionProducto itemPromo : listadoPromocionProducto)
						{
							int cantidad = itemPromo.get_cantidad()==0?itemPromo.get_cantidadPaquete():itemPromo.get_cantidad();
							cadenaFormateada += theUtilidades.FormatearTextoADimensionAnterior("* " + String.valueOf(cantidad),11);
							Producto productoPromo = ObtenerProductoPor(itemPromo.get_productoId());
							cadenaFormateada += theUtilidades.FormatearTextoADimensionAnterior(productoPromo.get_descripcion25(),25) + "\n";
						}
					}
				}
			}
		}
		cadenaFormateada += "________________________________________________\n\n";
		cadenaFormateada += theUtilidades.FormatearTextoADimensionAnterior("Total Parcial: " + theUtilidades.FormatearTextoADimensionAnterior(String.valueOf(new BigDecimal(venta.get_monto()).setScale(2,RoundingMode.HALF_UP)),10),tamanioTotal) + "\n";
		cadenaFormateada += theUtilidades.FormatearTextoADimensionAnterior("Descuento: " + theUtilidades.FormatearTextoADimensionAnterior(String.valueOf(new BigDecimal(venta.get_descuento()).setScale(2,RoundingMode.HALF_UP)),10),tamanioTotal) + "\n";
		cadenaFormateada += theUtilidades.FormatearTextoADimensionAnterior("Total a pagar: " + theUtilidades.FormatearTextoADimensionAnterior(String.valueOf(new BigDecimal(venta.get_montoFinal()).setScale(2,RoundingMode.HALF_UP)),10),tamanioTotal) + "\n\n";
				
		cadenaFormateada += "Son: " + montoEnPalabras + "\n\n";
		
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal,"Codigo de control: " + codigoControl) + "\n";
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal, "Fecha Limite de Emision: " +
							theUtilidades.FormatearTextoACerosAnterior(String.valueOf(dosificacion.get_diaLimiteEmision()),2) + "/" + 
							theUtilidades.FormatearTextoACerosAnterior(String.valueOf(dosificacion.get_mesLimiteEmision()),2) + "/" +
							theUtilidades.FormatearTextoACerosAnterior(String.valueOf(dosificacion.get_anioLimiteEmision()),2)) + "\n\n";
        try 
        {
            mmOutputStream.write(cadenaFormateada.getBytes("CP852"));   
        } 
        catch (NullPointerException localException) 
        {
        	if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
                myLog.InsertarLog("App",this.toString(),1,"Error NullPointerException al enviar los datos: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error NullPointerException al enviar los datos: " + localException.getMessage());
        	} 
        	
        	theUtilidades.MostrarMensaje(getApplicationContext(), "Error al enviar los datos.", 1);
        	return;
        } 
        catch (Exception localException) 
        {
        	if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
                myLog.InsertarLog("App",this.toString(),1,"Error al enviar los datos: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al enviar los datoss: " + localException.getMessage());
        	} 
        	
        	theUtilidades.MostrarMensaje(getApplicationContext(), "Error al enviar los datos.", 1);
        	return;
        }	        
        
        //Tell the user data were sent
        if(imprimirQR())
        {
        	if(ImprimirPieFactura())
        	{	
                theUtilidades.MostrarMensaje(getApplicationContext(), "Impresion realizada.", 1);
                ModificarEstadoImpresion();
        	}
        	else
        	{
        		theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo imprimir el pie de factura.", 1);
        	}
        }
        
    }
	
	private boolean ImprimirPieFactura()
	{
		String cadenaFormateada="";
		
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal,dosificacion.get_mensaje1()) + "\n\n";
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal,dosificacion.get_ley()) + "\n\n";
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal,dosificacion.get_mensaje2()) + "\n\n";
		
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal, "---------------------------------------") + "\n\n";
		
		cadenaFormateada += theUtilidades.FormatearTextoACerosAnterior(String.valueOf(loginEmpleado.get_empleadoId()),8) + " - "  + loginEmpleado.get_empleadoNombre() + "\n";
		
		if(autoventa==false)
		{
			cadenaFormateada += ObtenerPreventistaPor(venta.get_preventaId()) + "\n";
		}
		else
		{
			cadenaFormateada += theUtilidades.FormatearTextoACerosAnterior(String.valueOf(loginEmpleado.get_empleadoId()),8) + " - "  + loginEmpleado.get_empleadoNombre() + "\n";
		}
		
		cadenaFormateada += theUtilidades.FormatearTextoACerosAnterior(String.valueOf(clienteVenta.get_clienteId()),8) + " - "  + clienteVenta.get_nombreCompleto() + "\n\n";
		cadenaFormateada += "\n\n\n\n\n\n";
		
		try 
		{
			mmOutputStream.write(cadenaFormateada.getBytes("CP852"));   
    	} 
		catch (NullPointerException localException) 
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error NullPointerException al enviar los datos: vacio");
        	}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error NullPointerException al enviar los datos: " + localException.getMessage());
        	} 
	        	
			theUtilidades.MostrarMensaje(getApplicationContext(), "Error al enviar los datos.", 1);
			return false;
        } 
		catch (Exception localException) 
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al enviar los datos: vacio");
    		}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al enviar los datoss: " + localException.getMessage());
        	} 
	        	
			theUtilidades.MostrarMensaje(getApplicationContext(), "Error al enviar los datos.", 1);
			return false;
        }	        
		return true;
	}

	private boolean CerrarBluetooth()
	{
		boolean cerrado = false;
        try 
        {
            stopWorker = true;
            mmOutputStream.close();
            mmInputStream.close();
            mmSocket.close();
            cerrado = true;
            
            theUtilidades.MostrarMensaje(getApplicationContext(), "Conexion Bluetooth cerrada.", 1);
        } 
        catch (NullPointerException localException) 
        {
        	if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
                myLog.InsertarLog("App",this.toString(),1,"Error NullPointerException al cerrar los datos: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error NullPointerException al cerrar los datos: " + localException.getMessage());
        	} 
        	theUtilidades.MostrarMensaje(getApplicationContext(), "Error al cerrar la conexion Bluethoot", 1);
        	return false;
        } 
        catch (Exception localException) 
        {
        	if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
                myLog.InsertarLog("App",this.toString(),1,"Error al cerrar los datos: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al cerrar los datoss: " + localException.getMessage());
        	} 
        	theUtilidades.MostrarMensaje(getApplicationContext(), "Error al cerrar la conexion Bluethoot", 1);
        	return false;
        }
        
        return cerrado;
    }
		
	private Producto ObtenerProductoPor(int productoId)
	{
		Producto localProducto = null;
		try
		{
			localProducto = new ProductoBLL().ObtenerProductoPor(productoId);
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
		
		return localProducto;
	}
	
	private PrecioLista ObtenerPrecioListaPor(int precioListaId,int productoId)
	{
		PrecioLista localPrecioLista = null;
		try
		{
			localPrecioLista = new PrecioListaBLL().ObtenerPrecioListaPor(precioListaId, productoId);
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
		
		return localPrecioLista;
	}
	
	private PromocionPrecioLista ObtenerPromocionPrecioListaPor(int promocionId)
	{
		PromocionPrecioLista localPromocionPrecioLista = null;
		try
		{
			localPromocionPrecioLista = new PromocionPrecioListaBLL().ObtenerPromocionPrecioListaPor(promocionId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la promocionPrecioLista: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la promocionPrecioLista: " + localException.getMessage());
			} 
		}
		
		return localPromocionPrecioLista;
	}
	
	private Promocion ObtenerPromocionPor(int promocionId)
	{
		Promocion localPromocion = null;
		try
		{
			localPromocion = new PromocionBLL().ObtenerPromocionPor(promocionId);
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
		
		return localPromocion;
	}

	private boolean GuardarDatosFactura()
	{
		boolean guardado = false;
		montoEnPalabras = facturacion.ObtenerMontoEnPalabras(Double.parseDouble(new BigDecimal(venta.get_montoFinal()).setScale(2,RoundingMode.HALF_UP).toString()));
		codigoControl = facturacion.GenerarcodigoControl(dosificacion.get_codigoAutorizacion().trim(),
														String.valueOf(Integer.parseInt(dosificacion.get_numeroInicial().trim())+1),
														nit.trim(),
														fecha,
														Double.parseDouble(String.valueOf(new BigDecimal(venta.get_montoFinal()).setScale(2,RoundingMode.HALF_UP))),
														dosificacion.get_llaveDosificacion().trim());
		
		int noFactura;
		ArrayList<Factura> listadoFactura = null;
		try
		{
			listadoFactura = new FacturaBLL().ObtenerFacturas();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el listado de facturas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el listado de facturas: " + localException.getMessage());
			}
		}
		
		if(listadoFactura == null)
		{
			noFactura = 1;
		}
		else
		{
			noFactura = listadoFactura.size() + 1;
		}
		
		facturaId = 0;
		try
		{
			facturaId = new FacturaBLL().InsertarFactura(String.valueOf(Integer.parseInt(dosificacion.get_numeroInicial())+1),
												nombreFactura,nit,fecha.get_dia(),fecha.get_mes(),fecha.get_anio(),
												fecha.get_hora(),fecha.get_minuto(),dosificacion.get_diaLimiteEmision(),
												dosificacion.get_mesLimiteEmision(),dosificacion.get_anioLimiteEmision(),
												0,0,venta.get_monto(),venta.get_descuento(),venta.get_montoFinal(), 
												montoEnPalabras,dosificacion.get_codigoAutorizacion(),codigoControl, 
												dosificacion.get_facturaTipoId(),dosificacion.get_almacenId(), 
												false,0,"",0,0,0,dosificacion.get_dosificacionId(),loginEmpleado.get_empleadoId(),
												0,"","","",venta.get_clienteId(),listadoVentaProducto.size(),false,false,
												(int)ventaId,venta.get_ventaIdServer(),nitNuevo,noFactura,venta.get_tipoNit());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la factura: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la factura: " + localException.getMessage());
			}
		}
		
		if(facturaId == 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar la factura.", 1);
			return guardado;
		}
		
		for(int i=0; i<listadoVentaProducto.size(); i++)
		{
			long l=0;
			VentaProducto ventaProducto = listadoVentaProducto.get(i);
			try
			{
				l = new FacturaProductoBLL().InsertarFacturaProducto(facturaId, ventaProducto.get_productoId(),
						ventaProducto.get_promocionId(),ventaProducto.get_cantidad(),ventaProducto.get_cantidadPaquete(),
						ventaProducto.get_monto(),ventaProducto.get_descuento(),ventaProducto.get_montoFinal(),
						venta.get_clienteId(), loginEmpleado.get_empleadoId(),false,ventaProducto.get_precioId(),noFactura);
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la facturaProducto: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar la facturaProducto: " + localException.getMessage());
				}
			}
			
			if(l == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la factura producto.", 1);
				return guardado;
			}
		}
		
		long i = 0;
		try
		{
			i = new DosificacionBLL().ModificarDosificacionNumeroFactura(dosificacion.get_dosificacionId(), 
								Integer.parseInt(dosificacion.get_numeroInicial())+1);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar el numero de factura de la dosificacion: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el numero de factura de la dosificacion: " + localException.getMessage());
			}
		}
		
		if(i == 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el numero de factura de la dosificacion.", 1);
			return guardado;
		}
		else
		{
			guardado = true;
		}
		
		facturaAlmacenada = true;
		return guardado;
	}
	
	private void ModificarEstadoImpresion()
	{
		int modificado = 0;
		try
		{
			modificado = new FacturaBLL().ModificarEstadoImpresion((int)ventaId, true);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
                myLog.InsertarLog("App",this.toString(),1,"Error al modificar estado impresion: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al modificar estado impresion: " + localException.getMessage());
        	} 
		}
		
		if(modificado == 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el estado de la impresion.", 1);
		}
	}
	
	private float ObtenerPrecioPromocionPorLista(int promocionId,int precioListaId)
	{
		float precioPromocion = 0;
		
		ArrayList<PromocionProducto> listadoPromocionProducto = null;
		try
		{
			listadoPromocionProducto = new PromocionProductoBLL().ObtenerPromocionesProductoPor(promocionId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la promocion por promocionId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la promocion por promocionId: " + localException.getMessage());
			} 
		}
		
		if(listadoPromocionProducto == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los productos de la promocion.", 1);
			return 0;
		}
		
		for(PromocionProducto itemPromo : listadoPromocionProducto)
		{
			PrecioLista localPrecioLista = null;
			try
			{
				localPrecioLista = new PrecioListaBLL().ObtenerPrecioListaPor(precioListaId,itemPromo.get_productoId());
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precio de lista del producto, de la promocion: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precio de lista del producto, de la promocion: " + localException.getMessage());
				} 
			}
			
			if(localPrecioLista == null)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El producto de la promocion no tiene un precio de lista asociado.", 1);
	            return 0;
			}
			else
			{
				if(itemPromo.get_cantidad() > 0)
				{
					precioPromocion += itemPromo.get_cantidad() * localPrecioLista.get_precio(); //* (1 - itemPromo.get_descuento()/100);
				}
				else
				{
					precioPromocion += itemPromo.get_cantidadPaquete() * localPrecioLista.get_precioPaquete();// * (1 - itemPromo.get_descuento()/100);
				}					
			}
		}
		
		return precioPromocion;		
	}
	
	private boolean ObtenerPromocionProductoPorPromocionIdPrecioListaId(int promocionId,int precioListaId)
	{
		boolean obtenido = false;
		
		listadoPromocionProducto = null;
		try
		{
			listadoPromocionProducto = new PromocionProductoBLL().ObtenerPromocionesProductoPorPromocionIdPrecioListaId(promocionId, precioListaId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
                myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productosPromocion: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al enviar los productosPromocion: " + localException.getMessage());
        	} 
		}
		
		if(listadoPromocionProducto == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los productos de la promocion.", 1);
			return false;
		}
		else
		{
			obtenido = true;
		}
		
		return obtenido;
	}
	
	private String ObtenerPreventistaPor(int preventaId)
	{
		String preventista = "";
		PreventaDist preventaDist =null;
		try
		{
			preventaDist = new PreventaDistBLL().ObtenerPreventaDistPor(preventaId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de la preventaDist: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de la preventaDist: " + localException.getMessage());
			} 
		}
		
		if(preventaDist == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los datos de la preventa del distribuidor.", 1);
			return "";
		}
		else
		{
			Empleado empleado = null;
			try
			{
				empleado = new EmpleadoBLL().ObtenerEmpleadoPor(preventaDist.get_empleadoId());
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del empleado: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del empleado: " + localException.getMessage());
				} 
			}
			
			if(empleado == null)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el empleado.", 1);
				return "";
			}
			else
			{
				preventista = theUtilidades.FormatearTextoACerosAnterior(String.valueOf(empleado.get_empleadoId()),8) + " - "  + empleado.get_nombreCompleto();
			}
		}
		
		return preventista;
		
	}
	
	private void SincronizarFacturaProducto()
	{
		if(theUtilidades.VerificarConexionInternet(this))
		{
			if(factura!=null && listadoFacturaProductoNoSincronizado.size()>0)
			{
				InsertarFacturaProductoTemp(listadoFacturaProductoNoSincronizado.get(0));
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles de la factura.", 1);
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No esta conectado a internet, intentelo mas tarde.", 1);
			MostrarPantallaMapa();
		}		
	} 
	
	private boolean ObtenerFacturaProductoNoSincronizados()
	{
		boolean obtenido = false;
		listadoFacturaProductoNoSincronizado = null;
		
		try
		{
			listadoFacturaProductoNoSincronizado = new FacturaProductoBLL().ObtenerFacturasProductoNoSincronizadasPorFacturaId(factura.get_rowId());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la factura por facturaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la factura por facturaId: " + localException.getMessage());
			}				
			return obtenido;
		}
		
		if(listadoFacturaProductoNoSincronizado != null)
		{
			obtenido = true;
		}
		
		return obtenido;
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
	    			ModificarSincronizacionFacturaProducto(_facturaProductoTemp.get_rowId(),true);
	    			theUtilidades.MostrarMensaje(getApplicationContext(), "Producto de la factura insertado.", 1);
	    			
	    			if(ObtenerFacturaProductoNoSincronizados())
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
	    			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el producto de la factura.", 1);
	    			MostrarPantallaMapa();
	    		}
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "El WebService WSInsertFacturaProductoTemp no se ejecuto correctamente. ", 1);
	    		MostrarPantallaMapa();
	    	}
	    }
	}
	
	private void ModificarSincronizacionFacturaProducto(int rowId,boolean estado)
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
		}
		
		if(modificado == 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del producto de la factura.", 1);
			return;
		}		
	}
	
	private void SincronizarFactura(int rowId)
	{
		if(factura != null)
		{			
			SincronizarFactura(factura);				
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No existe factura que sincronizar.", 1);
			return;
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
		     return;
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
					MostrarPantallaMapa();
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
					 MostrarPantallaMapa();
				 }
			 }
			 else
			 {
				 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertarFactura.", 1);
				 MostrarPantallaMapa();
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
	
	private Bitmap GenerarQR(String texto,int tamanioPx)
	{
		QRCodeWriter writer = new QRCodeWriter();
	    try 
	    {
	        BitMatrix bitMatrix = writer.encode(texto, BarcodeFormat.QR_CODE, tamanioPx, tamanioPx);
	        int width = bitMatrix.getWidth();
	        int height = bitMatrix.getHeight();
	        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
	        for (int x = 0; x < width; x++) 
	        {
	            for (int y = 0; y < height; y++) 
	            {
	                bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
	            }
	        }
	        
	        return bmp;
	    } 
	    catch (WriterException localException) 
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al generar el codigo Qr: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al generar el codigo Qr: " + localException.getMessage());
			} 
	    	return null;
	    }
	}
	
	private boolean imprimirQR() 
	{
		if(convertBitmap(qr))
		{
			try 
            {
				mmOutputStream.write(PrinterCommands.SET_LINE_SPACING_0);
			} 
            catch (IOException localException) 
            {
            	if(localException.getMessage() == null || localException.getMessage().isEmpty())
    			{
    		        myLog.InsertarLog("App",this.toString(),1,"Error al escribir el espacio de linea en la impresora: vacio");
    			}
    			else
    			{
    				myLog.InsertarLog("App",this.toString(),1,"Error al escribir el espacio de linea en la impresora: " + localException.getMessage());
    			}
            	return false;
			}
			
			int offset = 0;
	        while (offset < qr.getHeight()) 
	        {
	        	try 
	        	{
					mmOutputStream.write(PrinterCommands.SELECT_BIT_IMAGE_MODE);
				} 
	        	catch (IOException localException) 
	        	{
	        		if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    			{
	    		        myLog.InsertarLog("App",this.toString(),1,"Error al establecer el modo de imagen de la impresora: vacio");
	    			}
	    			else
	    			{
	    				myLog.InsertarLog("App",this.toString(),1,"Error al establecer el modo de imagen de la impresora: " + localException.getMessage());
	    			}
	        		return false;
				}
	        	
	            for (int x = 0; x < qr.getWidth(); ++x) 
	            {
	            	for (int k = 0; k < 3; ++k) 
	            	{
	            		byte slice = 0;
	            		for (int b = 0; b < 8; ++b) 
	            		{
	            			int y = (((offset / 8) + k) * 8) + b;
	            			int i = (y * qr.getWidth()) + x;
	                		boolean v = false;
	                		if (i < dots.length()) 
	                		{
	                            v = dots.get(i);
	                        }
	                		slice |= (byte) ((v ? 1 : 0) << (7 - b));
	                    }
	        			try 
	        			{
	        				mmOutputStream.write(slice);
						} 
	        			catch (IOException localException)
	        			{
	        				if(localException.getMessage() == null || localException.getMessage().isEmpty())
	            			{
	            		        myLog.InsertarLog("App",this.toString(),1,"Error al escribir el caracter del QR en la impresora: vacio");
	            			}
	            			else
	            			{
	            				myLog.InsertarLog("App",this.toString(),1,"Error al escribir el caracter del QR en la impresora: " + localException.getMessage());
	            			}
						}
	                }
	            }
	            offset += 24;
		            
	            try 
	            {
					mmOutputStream.write(PrinterCommands.FEED_LINE);
				} 
	            catch (IOException localException) 
	            {
	            	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    			{
	    		        myLog.InsertarLog("App",this.toString(),1,"Error al escribir salto de linea en la impresora: vacio");
	    			}
	    			else
	    			{
	    				myLog.InsertarLog("App",this.toString(),1,"Error al escribir salto de linea en la impresora: " + localException.getMessage());
	    			}
	            	return false;
				}
	        }
	        
	        try 
            {
				mmOutputStream.write(PrinterCommands.SET_LINE_SPACING_20);
			} 
            catch (IOException localException) 
            {
            	if(localException.getMessage() == null || localException.getMessage().isEmpty())
    			{
    		        myLog.InsertarLog("App",this.toString(),1,"Error al escribir el espacio de linea en la impresora: vacio");
    			}
    			else
    			{
    				myLog.InsertarLog("App",this.toString(),1,"Error al escribir el espacio de linea en la impresora: " + localException.getMessage());
    			}
			}
	        
	        return true;
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo generar el QR.", 1);
			return false;
		}
	}
	
	public Boolean convertBitmap(Bitmap inputBitmap) 
	{
	    int mWidth = inputBitmap.getWidth();
	    int mHeight = inputBitmap.getHeight();

	    if(convertArgbToGrayscale(inputBitmap, mWidth, mHeight))
	    {
	    	return true;
	    }
	    else
	    {
	    	return false;
	    }
	}

	private boolean convertArgbToGrayscale(Bitmap bmpOriginal, int width,int height) 
	{
	    int pixel;
	    int k = 0;
	    int B = 0, G = 0, R = 0;
	    dots = new BitSet();
	    try 
	    {
	        for (int x = 0; x < height; x++) 
	        {
	            for (int y = 0; y < width; y++) 
	            {
	                // get one pixel color
	                pixel = bmpOriginal.getPixel(y, x);

	                // retrieve color of all channels
	                R = Color.red(pixel);
	                G = Color.green(pixel);
	                B = Color.blue(pixel);
	                // take conversion up to one single value by calculating
	                // pixel intensity.
	                R = G = B = (int) (0.299 * R + 0.587 * G + 0.114 * B);
	                // set bit into bitset, by calculating the pixel's luma
	                if (R < 55)
	                {                       
	                    dots.set(k);//this is the bitset that i'm printing
	                }
	                k++;
	            }
	        }
	    } 
	    catch (Exception localException) 
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al convertir la imagen QR a escala de grises: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al convertir la imagen QR a escala de grises: " + localException.getMessage());
			}
	    	return false;
	    }
	    
	    return true;
	}
	
	private void MostrarPantallaMapa()
	{
		Intent localIntent = new Intent(this, Distribuidormapaentregas.class);
	    localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(localIntent);
	}

	@Override	
	public void onBackPressed() 
	{
		if(facturaAlmacenada)
		{
			MostrarPantallaMapa();
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "Debe imprimir la factura antes de ir al mapa.", 1);
		}
	}
}
