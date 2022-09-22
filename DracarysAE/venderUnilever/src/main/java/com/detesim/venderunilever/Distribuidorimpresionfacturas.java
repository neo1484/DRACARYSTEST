package com.detesim.venderunilever;

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
import Utilidades.Facturacion;
import Utilidades.Utilidades;

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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Distribuidorimpresionfacturas extends Activity implements OnClickListener
{
	LinearLayout llImpresionFacturas;
	Utilidades theUtilidades = new Utilidades();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
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
    
    ListView lvFactura;
    ListView lvFacturaProducto;
    Button btnImprimirFactura;
	
	Facturacion facturacion = new Facturacion();
	ArrayList<Factura> listadoFactura;
	Factura facturaActual;
	FacturaProducto facturaProductoActual;
	ArrayList<FacturaProducto> listadoFacturaProducto;
	Fecha fecha;
	ClienteVenta clienteVenta;
	Venta venta;
	PreventaDist preventaDist;
	Dosificacion dosificacion;
	DatosFactura datosFactura;
	ArrayList<PromocionProducto> listadoPromocionProducto;
	ArrayList<Impresora> listadoImpresora;
	ParametroGeneral parametroGeneral;
	Bitmap qr = null;
	BitSet dots = new BitSet();

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_distribuidorimpresionfacturas);
		
		llImpresionFacturas = (LinearLayout)findViewById(R.id.DistribuidorImpresionFacturasLinearLayout);
		lvFactura = (ListView)findViewById(R.id.lvFactura);
		lvFacturaProducto = (ListView)findViewById(R.id.lvFacturaProducto);
		btnImprimirFactura = (Button)findViewById(R.id.btnDistribuidorImprimirFactura);
		btnImprimirFactura.setOnClickListener(this);

		llImpresionFacturas.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
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
	    else
	    {
	    	btnImprimirFactura.setVisibility(View.INVISIBLE);
	    	fecha = theUtilidades.ObtenerFecha();
		    MostrarFacturasNoImpresasForDisplay();
	    }	    
	}
	
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.btnDistribuidorImprimirFactura:
			ImprimirFactura();
			break;
		}
	}
	
	private void MostrarFacturasNoImpresasForDisplay()
	{
		listadoFactura = null;
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
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Facturas que desplegar.", 1);
			lvFactura.setAdapter(null);
			return;
		}
		else
		{
			LlenarFacturaListVIew();
			FacturaItemListView();
		}	
	}
	
	private void LlenarFacturaListVIew()
	{
		MiAdaptadorFactura localMiAdaptadorFactura = new MiAdaptadorFactura(getApplicationContext());
		ListView localListView = lvFactura;
		if(listadoFactura == null)
		{
			localListView.setAdapter(null);
		}
		else
		{
			ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    	localLayoutParams.height = ((int)(30*getApplicationContext().getResources().getDisplayMetrics().density) * listadoFactura.size());
	    	localListView.setLayoutParams(localLayoutParams);
			localListView.setAdapter(localMiAdaptadorFactura);
		}
	}
	
	class MiAdaptadorFactura extends ArrayAdapter<Factura>
	{
		private Context _context;
		
		public MiAdaptadorFactura(Context context)
		{
			super(context,R.layout.disenio_vendedorsincronizacionpreventa, listadoFactura);
			this._context = context;
		}
    
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View localView = convertView;
			
			if (localView == null) 
			{
				LayoutInflater layoutInflater = (LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				localView = layoutInflater.inflate(R.layout.disenio_vendedorsincronizacionpreventa, parent, false);
			}
			
			TextView tvNombreCompleto = (TextView)localView.findViewById(R.id.tvVendedorSincronizacionPreventaNombre);
			
			facturaActual = listadoFactura.get(position);
			
			tvNombreCompleto.setText(facturaActual.get_nombre() + " - " + facturaActual.get_nit() +  " : " + facturaActual.get_montoFinal());
			
			return localView;
		}
	}
	
	private void FacturaItemListView()
	{
	    ((ListView)findViewById(R.id.lvFactura)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	    	{
	    		facturaActual = listadoFactura.get(position);
    			ObtenerFacturaProductoPor(facturaActual.get_rowId());
	      }
	    });
	}
	
	private void ObtenerFacturaProductoPor(int facturaId)
	{
		listadoFacturaProducto = null;
		try
		{
			listadoFacturaProducto = new FacturaProductoBLL().ObtenerFacturasProductoPorFacturaId(facturaId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturasProducto por facturaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturasProducto por facturaId: " + localException.getMessage());
			} 
		}
		
		if(listadoFacturaProducto == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron productos en la factura.", 1);
			return;
		}
		else
		{
			LlenarFacturaProductoListVIew();
			btnImprimirFactura.setVisibility(View.VISIBLE);
		}
	}
	
	private void LlenarFacturaProductoListVIew()
	{
		MiAdaptadorFacturaProducto localMiAdaptadorFacturaProducto = new MiAdaptadorFacturaProducto(getApplicationContext());
		ListView localListView = lvFacturaProducto;
		if(listadoFacturaProducto == null)
		{
			localListView.setAdapter(null);
		}
		else
		{
			ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    	localLayoutParams.height = ((int)(30*getApplicationContext().getResources().getDisplayMetrics().density) * listadoFacturaProducto.size());
	    	localListView.setLayoutParams(localLayoutParams);
			localListView.setAdapter(localMiAdaptadorFacturaProducto);
		}
	}
	
	class MiAdaptadorFacturaProducto extends ArrayAdapter<FacturaProducto>
	{
		private Context _context;
		
		public MiAdaptadorFacturaProducto(Context context)
		{
			super(context,R.layout.disenio_vendedorsincronizacionpreventapreventaproducto, listadoFacturaProducto);
			this._context = context;
		}
    
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View localView = convertView;
			
			if (localView == null) 
			{
				LayoutInflater layoutInflater = (LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				localView = layoutInflater.inflate(R.layout.disenio_vendedorsincronizacionpreventapreventaproducto, parent, false);
			}
			
			TextView tvProducto = (TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoProducto);
			TextView tvUnidad = (TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoUnidad);
			TextView tvPaquete = (TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoPaquete);
			TextView tvMonto = (TextView)localView.findViewById(R.id.tvSincronizacionPreventaProductoMontoFinal);
			
			facturaProductoActual = listadoFacturaProducto.get(position);
			
			if(facturaProductoActual.get_productoId() != 0)
			{
				Producto localProducto = null;
				
				try
				{
					localProducto = new ProductoBLL().ObtenerProductoPor(facturaProductoActual.get_productoId());
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
				        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del producto por productoId: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del producto por productoId: " + localException.getMessage());
					} 
				}
				
				if(localProducto==null)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo encontrar el producto.", 1);
					return null;
				}
				else
				{				
					if(facturaProductoActual.get_cantidad()!=0)
					{
						tvProducto.setText(localProducto.get_descripcionUnidad25());
						tvUnidad.setText(String.valueOf(facturaProductoActual.get_cantidad()));
						tvPaquete.setText("0");
						tvMonto.setText(String.valueOf(facturaProductoActual.get_montoFinal()));
					}
					if(facturaProductoActual.get_cantidadPaquete()!=0)
					{
						tvProducto.setText(localProducto.get_descripcion25());
						tvUnidad.setText("0");
						tvPaquete.setText(String.valueOf(facturaProductoActual.get_cantidadPaquete()));
						tvMonto.setText(String.valueOf(facturaProductoActual.get_montoFinal()));
					}
				}	
			}
			
			if(facturaProductoActual.get_promocionId() != 0)
			{
				Promocion localPromocion = null;
				
				try
				{
					localPromocion = new PromocionBLL().ObtenerPromocionPor(facturaProductoActual.get_promocionId());
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
				        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de la promocion por promocionId: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de la promocion por promocionId: " + localException.getMessage());
					} 
				}
				
				if(localPromocion==null)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo encontrar la promocion.", 1);
					return null;
				}
				else
				{
					tvProducto.setText(localPromocion.get_descripcion25());
					tvUnidad.setText(String.valueOf(facturaProductoActual.get_cantidad()));
					tvPaquete.setText("0");
					tvMonto.setText(String.valueOf(facturaProductoActual.get_montoFinal()));
				}
			}
			
			return localView;
		}
	}
	
	private void ImprimirFactura()
	{
		if(ObtenerParametrosGenerales())
		{
			if(ObtenerClienteVenta(facturaActual.get_clienteId()))
			{
				if(ObtenerVentaPorRowId(facturaActual.get_ventaId()))
				{
					if(venta.get_preventaId() > 0)
					{
						if(ObtenerPreventaDist(venta.get_preventaId()))
						{
							if(ObtenerDosificacion())
							{
								if(ObtenerDatosFactura())
								{   	
									if(GenerarCodigoQR())
									{
										if(ObtenerImpresoras())
										{
											if(BuscarImpresora())
											{
												if(ConectarBluetooth())
												{
													EnviarDatosFactura();
													
													if(CerrarBluetooth())
													{
														lvFacturaProducto.setAdapter(null);
														btnImprimirFactura.setVisibility(View.INVISIBLE);
														MostrarFacturasNoImpresasForDisplay();
													}
												}
											}
											else
											{
												theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo encontrar la impresora..", 1);
											}
										}
										else
										{
											theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las impresoras.", 1);
										}
									}
									else
									{
										theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo generar el codigo QR.", 1);
									}
								}
								else
								{
									theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los datos de la factura.", 1);
								}
							}
							else
							{
								theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la dosificacion.", 1);
							}
						}
						else
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles de la preventa.", 1);
						}
					}
					else
					{
						if(ObtenerDosificacion())
						{
							if(ObtenerDatosFactura())
							{   	
								if(GenerarCodigoQR())
								{
									if(ObtenerImpresoras())
									{
										if(BuscarImpresora())
										{
											if(ConectarBluetooth())
											{
												EnviarDatosFactura();
												
												if(CerrarBluetooth())
												{
													lvFacturaProducto.setAdapter(null);
													btnImprimirFactura.setVisibility(View.INVISIBLE);
													MostrarFacturasNoImpresasForDisplay();
												}
											}
										}
										else
										{
											theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo encontrar la impresora..", 1);
										}
									}
									else
									{
										theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las impresoras.", 1);
									}
								}
								else
								{
									theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo generar el codigo QR.", 1);
								}
							}
							else
							{
								theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los datos de la factura.", 1);
							}
						}
						else
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la dosificacion.", 1);
						}
					}		
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles de la venta.", 1);
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del clienteVenta.", 1);
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los parametros generales.", 1);
		}
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
		
	private void EnviarDatosFactura() 
	{
		tamanioTotal = 48;
		String cadenaFormateada="";
		
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal,datosFactura.get_nombreEmpresa()) + "\n\n";
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal,dosificacion.get_sucursal().toString()) + "\n\n";
		
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal,"SFC - " + dosificacion.get_sfc()) + "\n";
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal,"F A C T U R A") + "\n";
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal,"ORIGINAL") + "\n\n";
		
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal,"NIT: " + datosFactura.get_nit()) + "\n";
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal,"Factura No: " + 
		theUtilidades.FormatearTextoACerosAnterior(facturaActual.get_numero(),8)) + "\n";
		
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal,"No. Autorizacion: " + dosificacion.get_codigoAutorizacion()) + "\n\n";
		
		cadenaFormateada += "Actividad: " + dosificacion.get_actividad() + "\n\n";
		
		cadenaFormateada += "Fecha : " + theUtilidades.ObtenerFechaLiteralString() + "\n";
		cadenaFormateada += "Nombre : " + facturaActual.get_nombre().toUpperCase(Locale.ENGLISH) + "\n";
		cadenaFormateada += "NIT : " + facturaActual.get_nit() + "\n\n";
		
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
		cadenaFormateada += theUtilidades.FormatearTextoADimensionAnterior("Total Parcial: " + theUtilidades.FormatearTextoADimensionAnterior(String.valueOf(new BigDecimal(facturaActual.get_montoTotal()).setScale(2,RoundingMode.HALF_UP)),10),tamanioTotal) + "\n";
		cadenaFormateada += theUtilidades.FormatearTextoADimensionAnterior("Descuento: " + theUtilidades.FormatearTextoADimensionAnterior(String.valueOf(new BigDecimal(facturaActual.get_descuento()).setScale(2,RoundingMode.HALF_UP)),10),tamanioTotal) + "\n";
		cadenaFormateada += theUtilidades.FormatearTextoADimensionAnterior("Total a pagar: " + theUtilidades.FormatearTextoADimensionAnterior(String.valueOf(new BigDecimal(facturaActual.get_montoFinal()).setScale(2,RoundingMode.HALF_UP)),10),tamanioTotal) + "\n\n";
				
		cadenaFormateada += "Son: " + facturaActual.get_montoPalabras() + "\n\n";
		
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal,"Codigo de control: " + facturaActual.get_codigoControl()) + "\n";
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal, "Fecha Limite de Emision: " +
							theUtilidades.FormatearTextoACerosAnterior(String.valueOf(facturaActual.get_fechaLimiteEmisionDia()),2) + "/" + 
							theUtilidades.FormatearTextoACerosAnterior(String.valueOf(facturaActual.get_fechaLimiteEmisionMes()),2) + "/" +
							theUtilidades.FormatearTextoACerosAnterior(String.valueOf(facturaActual.get_fechaLimiteEmisionAnio()),2)) + "\n";
				
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
        
        // tell the user data were sent
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

	private boolean ObtenerVentaPorRowId(int id)
	{
		venta = null;
		try
		{
			venta = new VentaBLL().ObtenerVentaPor(id);
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
		
		if(venta==null)
		{
			return false;
		}
		else
		{
			return true;
		}
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
	
	private void ModificarEstadoImpresion()
	{
		int modificado = 0;
		try
		{
			modificado = new FacturaBLL().ModificarEstadoImpresion(facturaActual.get_ventaId(), true);
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

	private boolean GenerarCodigoQR()
	{
		boolean generado = false;
		
		String nitEmisor = parametroGeneral.get_nit();
		String importeIC = "0";
		String importeVentasNoGravadas = "0";
		String importeNoSujetoACreditoFiscal = "0";
		
		String codigoQR = nitEmisor + "|" + facturaActual.get_numero() + "|" + facturaActual.get_codigoAutorizacion() + 
			"|" + facturaActual.get_fechaDia() + "/" + facturaActual.get_fechaMes() + "/" + facturaActual.get_fechaAnio() + "|" +
			facturaActual.get_montoTotal() + "|" +facturaActual.get_montoFinal() + "|" +  facturaActual.get_codigoControl() + "|" + 
			facturaActual.get_nit() + "|" + importeIC + "|" + importeVentasNoGravadas + "|" + importeNoSujetoACreditoFiscal + "|" + 
			facturaActual.get_descuento();
		
		qr = GenerarQR(codigoQR,225);
		
		if(qr!=null)
		{	
			generado = true;
		}
		
		return generado;
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
	
	private boolean ImprimirPieFactura()
	{
		String cadenaFormateada="";
		
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal,dosificacion.get_mensaje1()) + "\n\n";
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal,dosificacion.get_ley()) + "\n\n";
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal,dosificacion.get_mensaje2()) + "\n\n";
		
		cadenaFormateada += theUtilidades.CentrarTexto(tamanioTotal, "---------------------------------------") + "\n\n";
		
		cadenaFormateada += theUtilidades.FormatearTextoACerosAnterior(String.valueOf(loginEmpleado.get_empleadoId()),8) + " - "  + loginEmpleado.get_empleadoNombre() + "\n";
		
		if(venta.get_preventaId() > 0)
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
}
