package com.detesim.venderunilever;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import BLL.ClienteVentaBLL;
import BLL.DevolucionDistribuidorBLL;
import BLL.DevolucionDistribuidorProductoBLL;
import BLL.MotivoNoEntregaBLL;
import BLL.MyLogBLL;
import BLL.ParametroGeneralBLL;
import BLL.PrecioListaBLL;
import BLL.PreventaBonificacionBLL;
import BLL.PreventaCambioBLL;
import BLL.PreventaDistBLL;
import BLL.PreventaProductoCambioBLL;
import BLL.PreventaProductoDistBLL;
import BLL.PreventaProductoPOPBLL;
import BLL.ProductoBLL;
import BLL.ProductoCambioBLL;
import BLL.ProductoPOPBLL;
import BLL.PromocionBLL;
import BLL.PromocionProductoBLL;
import BLL.SincronizacionVentaBLL;
import BLL.VentaBLL;
import BLL.VentaProductoBLL;
import BLL.VentaProductoTempBLL;
import Clases.ClienteVenta;
import Clases.DevolucionDistribuidor;
import Clases.DevolucionDistribuidorProducto;
import Clases.Fecha;
import Clases.FormaPago;
import Clases.LoginEmpleado;
import Clases.MotivoNoEntrega;
import Clases.ParametroGeneral;
import Clases.PrecioLista;
import Clases.PreventaBonificacion;
import Clases.PreventaCambio;
import Clases.PreventaDist;
import Clases.PreventaProductoCambio;
import Clases.PreventaProductoDist;
import Clases.PreventaProductoPOP;
import Clases.Producto;
import Clases.ProductoCambio;
import Clases.ProductoPOP;
import Clases.Promocion;
import Clases.PromocionProducto;
import Clases.SincronizacionVenta;
import Clases.Ubicacion;
import Clases.VentaProducto;
import Clases.VentaProductoTemp;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Distribuidorventa extends Activity implements OnClickListener
{
	LinearLayout llDistribuidorVenta;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
	int clienteId;
	boolean itemModificado;
	PreventaDist preventaDist;
	ClienteVenta clienteVenta;
	ArrayList<VentaProductoTemp> listadoVentaProductoTemp;
	ArrayList<PreventaProductoPOP> listadoPreventaProductoPOP;
	ArrayList<PreventaProductoCambio> listadoPreventaProductoCambio;
	float monto;
	float descuento;
	float montoFinal;
	float descuentoCanal;
	float descuentoAjuste;
	float descuentoProntoPago;
	double latitudDistribuidor;
	double longitudDistribuidor;
	long ventaId;
	long ventaIdServer;
	long newVentaId;
	ParametroGeneral parametroGeneral;
	boolean preventaBonificada = false;
	boolean preventaCambio = false;
	boolean preventaConProntoPago = false;
	boolean preventaConObjetivo = false;
	int formaPagoId = 0;
	
	TextView tvNroPedidoTxt;
	TextView tvNroPedido;
	TextView tvCliente;
	TextView tvDireccion;
	TextView tvCelular;
	ImageView ivCelular;
	TextView tvTipoPago;
	TextView tvTotal;
	Button btnMotivoNoEntrega;
	Button btnConfirmarVenta;
	TextView tvPagadoTxt;
	TextView tvPagado;;
	Spinner spnFormaPago;
	EditText etCodTrans;
	Spinner spnMotivoNoEntrega;
	ListView lvVentaProductoTemp;
	ListView lvPreventaProductoPOP;
	ListView lvPreventaProductoCambio;
	
	TextView tvProductolbl;
	TextView tvCauntidadUnitarialbl;
	TextView tvCantidadPaquetelbl;
	TextView tvMontolbl;
	TextView tvMontoPreventaTxt;
	TextView tvMontoPreventa;
	TextView tvDescuentolbl;
	TextView tvDescuentoPaquetelbl;
	TextView tvSubTotal;
	TextView tvTotalCalculadolbl;
	TextView tvTotalCalculado;
	TextView tvDsctoOtrosTxt;
	TextView tvDsctoOtros;
	TextView tvDsctoAjusteTxt;
	TextView tvDsctoAjuste;
	TextView tvDsctoCanalTxt;
	TextView tvDsctoCanal;
	TextView tvDsctoProntoPagoTxt;
	TextView tvDsctoProntoPago;
	TextView tvDsctoObjetivoTxt;
	TextView tvDsctoObjetivo;
	TextView tvDsctoSocioTxt;
	TextView tvDsctoSocio;
	TextView tvDsctoIncentivoTxt;
	TextView tvDsctoIncentivo;
	TextView tvObservacion;
	TextView tvMaterialPOP;
	TextView tvProductoCambio;
	Dialog dialog;
	  
	ProgressDialog pdCopiarPreVentaAVentaTemp;
	ProgressDialog pdObtenerVentaProductoTemp;
	ProgressDialog pdVentaNoEntregada;
	ProgressDialog pdInsertarVenta;
	ProgressDialog pdDeletePreventaProductoPOP;
	ProgressDialog pdDeletePreventaProductoCambio;
	ProgressDialog pdObservacionPOP;
	ProgressDialog pdEliminarObservacionPOP;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_distribuidorventa);
		
		llDistribuidorVenta = (LinearLayout)findViewById(R.id.DistribuidorVentaLinearLayout);
		tvNroPedidoTxt = (TextView) findViewById(R.id.tvDisVenNroPedidoTxt);
		tvNroPedido = (TextView) findViewById(R.id.tvDisVenNroPedido);
		tvCliente = (TextView)findViewById(R.id.tvDistribuidorVentaCliente);
		tvDireccion = (TextView) findViewById(R.id.tvDisVenDireccion);
		tvCelular = (TextView)findViewById(R.id.tvDisVenCelular);
		ivCelular = (ImageView)findViewById(R.id.ivDisVenCelular);
		ivCelular.setOnClickListener(this);
		tvTipoPago = (TextView)findViewById(R.id.tvDistribuidorVentaTipoPago);
		tvTotal = (TextView)findViewById(R.id.tvDistribuidorVentaTotal);
		btnMotivoNoEntrega = (Button)findViewById(R.id.btnDistribuidorVentaMotivoNoEntrega);
		btnMotivoNoEntrega.setOnClickListener(this);
		btnConfirmarVenta = (Button)findViewById(R.id.btnDistribuidorVentaConfirmarVenta);
		btnConfirmarVenta.setOnClickListener(this);
		tvPagadoTxt = (TextView) findViewById(R.id.tvDisVenPagadoTxt);
		tvPagado = (TextView) findViewById(R.id.tvDisVenPagado);
		spnFormaPago = (Spinner)findViewById(R.id.spnDisVenFormaPago);
		etCodTrans = (EditText)findViewById(R.id.etDisVenCodTrans);
		spnMotivoNoEntrega = (Spinner)findViewById(R.id.spnDistribuidorVentaMotivoNoEntrega);
		btnMotivoNoEntrega.setOnClickListener(this);
		lvVentaProductoTemp = (ListView)findViewById(R.id.lvDistribuidorVentaPreventaProductos);
		tvMaterialPOP = (TextView)findViewById(R.id.tvDistribuidorVentaProductoPOP);
		lvPreventaProductoPOP = (ListView)findViewById(R.id.lvDistribuidorVentaPreventaProductoPOP);
		tvProductoCambio = (TextView)findViewById(R.id.tvDistribuidorVentaProductoCambio);
		lvPreventaProductoCambio = (ListView)findViewById(R.id.lvDistribuidorVentaProductosCambio);
		tvProductolbl = (TextView)findViewById(R.id.tvDistribuidorVentaProductolbl);
		tvCauntidadUnitarialbl = (TextView)findViewById(R.id.tvDistribuidorVentaUnidadeslbl);
		tvCantidadPaquetelbl = (TextView)findViewById(R.id.tvDistribuidorVentaPaquetelbl);
		tvMontolbl = (TextView)findViewById(R.id.tvDistribuidorVentaMontolbl);
		tvDescuentolbl = (TextView)findViewById(R.id.tvDistribuidorVentaDescuentolbl);
		tvSubTotal = (TextView)findViewById(R.id.tvDistribuidorVentaSubTotallbl);
		tvMontoPreventaTxt = (TextView)findViewById(R.id.tvDistribuidorVentaMontoTxt);
		tvMontoPreventa = (TextView)findViewById(R.id.tvDistribuidorVentaMonto);
		tvTotalCalculadolbl = (TextView)findViewById(R.id.tvDistribuidorVentaTotalCalculadolbl);
		tvTotalCalculado = (TextView)findViewById(R.id.tvDistribuidorVentaTotalCalculado);
		tvDsctoOtrosTxt = (TextView)findViewById(R.id.tvDistribuidorVentaDsctoOtrosTxt);
		tvDsctoOtros = (TextView)findViewById(R.id.tvDistribuidorVentaDsctoOtros);
		tvDsctoAjusteTxt = (TextView)findViewById(R.id.tvDistribuidorVentaDsctoAjusteTxt);
		tvDsctoAjuste = (TextView)findViewById(R.id.tvDistribuidorVentaDsctoAjuste);
		tvDsctoCanalTxt = (TextView)findViewById(R.id.tvDistribuidorVentaDsctoCanalTxt);
		tvDsctoCanal = (TextView)findViewById(R.id.tvDistribuidorVentaDsctoCanal);
		tvDsctoProntoPagoTxt = (TextView)findViewById(R.id.tvDistribuidorVentaDsctoProntoPagoTxt);
		tvDsctoProntoPago = (TextView)findViewById(R.id.tvDistribuidorVentaDsctoProntoPago);
		tvDsctoObjetivoTxt = (TextView)findViewById(R.id.tvDistribuidorVentaDsctoObjetivoTxt);
		tvDsctoObjetivo = (TextView)findViewById(R.id.tvDistribuidorVentaDsctoObjetivo);
		tvDsctoSocioTxt = (TextView)findViewById(R.id.tvDistribuidorVentaDsctoSocioTxt);
		tvDsctoSocio = (TextView)findViewById(R.id.tvDistribuidorVentaDsctoSocio);
		tvDsctoIncentivoTxt = (TextView)findViewById(R.id.tvDistribuidorVentaDsctoIncentivoTxt);
		tvDsctoIncentivo = (TextView)findViewById(R.id.tvDistribuidorVentaDsctoIncentivo);
		tvObservacion = (TextView)findViewById(R.id.tvDistribuidorVentaObservacionContenido);
		
		llDistribuidorVenta.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
		Bundle localBundle = getIntent().getExtras();
		clienteId = localBundle.getInt("ClienteId");
	    itemModificado = (Boolean)localBundle.get("ItemModificado");
	      
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
		}
		else
		{
			if(ObtenerParametros())
			{
				if(ObtenerPreventaDistNoEntregadas())
				{
					DeterminarPreventaBonificada();
					
					DeterminarPreventaCambio();
					
					DeterminarPreventaDracaris();
					
					MostrarDatosPreventa();
					
					if(parametroGeneral.is_habilitarPop())
					{
						ActualizarPOPListView();
					}
					
					if(parametroGeneral.is_habilitarCambio())
					{
						ActualizarProductoCambioListView();
					}
					
					if(itemModificado)
					{
						MostrarVentasProductoTemp(true);
					}
					else
					{
						BorrarTablasTemporales();
						
						if(VerificarVentaConfirmada(preventaDist.get_preventaId()))
						{
							btnConfirmarVenta.setVisibility(View.INVISIBLE);
							btnMotivoNoEntrega.setVisibility(View.INVISIBLE);
							lvVentaProductoTemp.setVisibility(View.INVISIBLE);						
							theUtilidades.MostrarMensaje(getApplicationContext(),"La venta ya fue confirmada.", 1);
							return;
						}
						else
						{					
							CopiarPreVentaAVentaTemp();
						}
					}
				}
				else
				{				
					MostrarPantallaAutoventa();
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los parametros.", 1);
			}
		}
	}
	
	private boolean ObtenerParametros()
	{
		boolean obtenido = false;
	
		parametroGeneral = null;		
		try
		{
			parametroGeneral = new ParametroGeneralBLL().ObtenerParamatroGeneral();
					
		}catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los parametros generales del sistema: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los parametros generales del sistema: " + localException.getMessage());
			}
		}
		
		if(parametroGeneral != null)
		{
			obtenido = true;
		}
		
		clienteVenta = null;
		try
		{
			clienteVenta = new ClienteVentaBLL().ObtenerClienteVentaPor(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del cliente de la venta por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del cliente de la venta por clienteId: " + localException.getMessage());
	    	}
		}
		
		if(clienteVenta == null)
		{
			//theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el cliente de la venta.", 1);
			obtenido = obtenido && false;
		}
		
		return obtenido;
	}
	
	private void DeterminarPreventaBonificada()
	{
		PreventaBonificacion preventaBonificacion = null;
		try
		{
			preventaBonificacion = new PreventaBonificacionBLL().ObtenerPreventaBonificacionPor(preventaDist.get_preventaId());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventaBonificada por preventaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventaBonificada por preventaId: " + localException.getMessage());
			}
		}
		
		if(preventaBonificacion!=null)
		{
			preventaBonificada = true;
		}
	}
	
	private void DeterminarPreventaCambio()
	{
		PreventaCambio thePreventaCambio = null;
		try
		{
			thePreventaCambio = new PreventaCambioBLL().ObtenerPreventaCambioPor(preventaDist.get_preventaId());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa con cambio por preventaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa con cambio por preventaId: " + localException.getMessage());
			}
		}
		
		if(thePreventaCambio != null)
		{
			preventaCambio = true;
		}
	}
	
	private void DeterminarPreventaDracaris()
	{
		PreventaDist localPreventaDist = null;
		try
		{
			localPreventaDist = new PreventaDistBLL().ObtenerPreventaDistPor(preventaDist.get_preventaId());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventaDist por preventaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventaDist por preventaId: " + localException.getMessage());
			}
		}
		
		if(localPreventaDist != null)
		{
			if(localPreventaDist.get_descuentoProntoPago() > 0)
			{
				preventaConProntoPago = true;
			}
			else if (localPreventaDist.get_descuentoObjetivo() > 0)
			{
				preventaConObjetivo = true;
			}
		}
	}
	
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.ivDisVenCelular:
			MostrarWhatsApp();
			break;
		case R.id.btnDistribuidorVentaMotivoNoEntrega:
			if(((MotivoNoEntrega)spnMotivoNoEntrega.getSelectedItem()).get_motivoId() == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "Debe seleccionar un Motivo de No Entrega.", 1);
				return;
			}
			else
			{
				if(ValidarVenta())
				{
					InsertarVentaNoEntregada();
				}
			}
			break;
		case R.id.btnDistribuidorVentaConfirmarVenta:
			if(ValidarVenta())
			{
				if(formaPagoId==2 && etCodTrans.getText().length() == 0)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Debe indicar un codigo de transferencia.", 1);
					return;
				}
				
				if(itemModificado)
				{
					InsertarVenta(monto,descuento,montoFinal);
				}
				else
				{
					InsertarVenta(preventaDist.get_monto(),preventaDist.get_descuento(),preventaDist.get_montoFinal());
				}
			}
			break;
		}		
	}
	
	private void MostrarWhatsApp()
	{
		try
        {
            Intent intentWA = new Intent(Intent.ACTION_VIEW);
            intentWA.setData(Uri.parse("http://api.whatsapp.com/send?phone=591" + clienteVenta.get_celular().trim() + "&text=Entregas SeLoLlevo.com: "));
            startActivity(intentWA);
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al contactarse con Whats App: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al contactarse con Whats App: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(),"No logramos solicitar automaticamente por WhatsApp, comuniquese via celular.",1);
        }
		
	}
	
	private boolean ObtenerPreventaDistNoEntregadas()
	{
		boolean obtenido = false;
		ArrayList<PreventaDist> listadoPreventaDist = null;
		preventaDist = null;
		
		try
		{
			listadoPreventaDist = new PreventaDistBLL().ObtenerPreventaDistNoEntregadaPorClienteId(clienteId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas no entregadas del distribuidor por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas no entregadas del distribuidor por clienteId: " + localException.getMessage());
			}
			return false;
		}
		
		if(listadoPreventaDist !=null && listadoPreventaDist.size() >0)
		{
			preventaDist = listadoPreventaDist.get(0);
			obtenido = true;
		}
		
		return obtenido;
	}

	private void MostrarDatosPreventa()
	{
		if(clienteVenta.get_pedidoExternoId().equals(""))
		{
			tvNroPedidoTxt.setVisibility(View.INVISIBLE);
			tvNroPedido.setVisibility(View.INVISIBLE);
		}
		else
		{
			tvNroPedido.setText(clienteVenta.get_pedidoExternoId());
		}
		
		tvCliente.setText(preventaDist.get_clienteFD());
		tvDireccion.setText(clienteVenta.get_direccion());
		
		if(clienteVenta.get_celular() == null || clienteVenta.get_celular().isEmpty() || clienteVenta.get_celular().equals("anyType{}"))
		{
			tvCelular.setText("");
			ivCelular.setVisibility(View.INVISIBLE);
		}
		else
		{
			tvCelular.setText(clienteVenta.get_celular());
		}
		
      	tvTipoPago.setText(preventaDist.get_tipoPagoFD());
      	tvTotal.setText(String.valueOf(BigDecimal.valueOf(preventaDist.get_montoFinal()).setScale(2,RoundingMode.HALF_UP)));
      	tvObservacion.setText(preventaDist.get_observacion());
      	
      	ArrayList<FormaPago> listadoFormaPago = new ArrayList<FormaPago>();
      	listadoFormaPago.add(new FormaPago(1,"Efectivo"));
      	listadoFormaPago.add(new FormaPago(2,"Transferencia"));
      	listadoFormaPago.add(new FormaPago(3,"Soli Pago"));
      	listadoFormaPago.add(new FormaPago(4,"Tarjeta"));
      	
      	ArrayAdapter<FormaPago> localArrayAdapterFP = new ArrayAdapter<FormaPago>(getApplicationContext(), R.layout.disenio_spinner, listadoFormaPago);
		spnFormaPago.setAdapter(localArrayAdapterFP);
		
		spnFormaPago.setOnItemSelectedListener(new OnItemSelectedListener() 
	    {	    	
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
			{
				formaPagoId = ((FormaPago)spnFormaPago.getSelectedItem()).get_formaPagoId();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent){}
	    	
		});
		 
      	if(preventaDist.get_codTransferencia() != null && preventaDist.get_codTransferencia().length() > 0)
      	{
      		tvPagado.setText("SI");
      		spnFormaPago.setSelection(1);
      		etCodTrans.setText(String.valueOf(preventaDist.get_codTransferencia()));
      		spnFormaPago.setEnabled(false);
      		etCodTrans.setEnabled(false);
      	}
      	else
      	{
      		tvPagado.setText("NO");
      		spnFormaPago.setSelection(0);
      	}
      	
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
	}
	
	private boolean VerificarVentaConfirmada(int preventaId)
	{
		boolean verificado = false;
		ArrayList<SincronizacionVenta> listadoSincronizacionVenta = null;
		try
		{
			listadoSincronizacionVenta = new SincronizacionVentaBLL().ObtenerSincronizacionVentaPor(preventaId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta por preventaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta por preventaId: " + localException.getMessage());
			} 
		}
		
		if(listadoSincronizacionVenta != null)
		{
			verificado = true;
		}
		
		return verificado;
	}
	
	private void MostrarVentaProductoTempListView()
	{
		MiAdaptadorVentaProductoTemp localMiAdaptadorVentaProductoTemp = new MiAdaptadorVentaProductoTemp(getApplicationContext());
		if(listadoVentaProductoTemp == null || listadoVentaProductoTemp.size()<=0)
		{
			lvVentaProductoTemp.setAdapter(null);
			MostrarTitulosVentaProductoTemp(false);
		}
		else
		{
			monto = 0;
			descuento = 0;
			montoFinal = 0;
			MostrarTitulosVentaProductoTemp(true);
			ViewGroup.LayoutParams localLayoutParams = lvVentaProductoTemp.getLayoutParams();
		    localLayoutParams.height = ((int)(50*getApplicationContext().getResources().getDisplayMetrics().density) * listadoVentaProductoTemp.size());
		    lvVentaProductoTemp.setLayoutParams(localLayoutParams);
			lvVentaProductoTemp.setAdapter(localMiAdaptadorVentaProductoTemp);
		}
	}
	
	class MiAdaptadorVentaProductoTemp extends ArrayAdapter<VentaProductoTemp>
	{
		private Context _context;
		
		public MiAdaptadorVentaProductoTemp(Context context)
		{
			super(context,R.layout.disenio_distribuidorventaproductotemp, listadoVentaProductoTemp);
			this._context = context;
		}
    
		public View getView(int position, View convertView, ViewGroup parent)
		{
			boolean itemMostrado = false;
			View localView = convertView;
			
			if (localView == null) 
			{
				LayoutInflater layoutInflater = (LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				localView = layoutInflater.inflate(R.layout.disenio_distribuidorventaproductotemp, parent, false);
			}
			
			TextView tvProducto = (TextView)localView.findViewById(R.id.tvDistribuidorVentaTempProducto);
			TextView tvCantidad = (TextView)localView.findViewById(R.id.tvDistribuidorVentaTempCantidad);
			TextView tvCantidadPaquete = (TextView)localView.findViewById(R.id.tvDistribuidorVentaTempCantidadPaquete);
			TextView tvMonto = (TextView)localView.findViewById(R.id.tvDistribuidorVentaTempMonto);
			TextView tvDescuento = (TextView)localView.findViewById(R.id.tvDistribuidorVentaTempDescuento);
			TextView tvMontoFinal = (TextView)localView.findViewById(R.id.tvDistribuidorVentaTempMontoFinal);
			ImageView ivBullet = (ImageView)localView.findViewById(R.id.ivDistribuidorVentaTempBullet);
				
			VentaProductoTemp actualVentaProductoTemp = listadoVentaProductoTemp.get(position);
			
			if(actualVentaProductoTemp.get_productoId() != 0)
			{
				Producto localProducto=null;
				try
				{
					localProducto = new ProductoBLL().ObtenerProductoPor(actualVentaProductoTemp.get_productoId());
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
				if(localProducto == null)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo obtener los detalles del producto.", 1);
					return null;
				}
				
				ClienteVenta localClienteVenta = null;
				try
				{
					localClienteVenta = new ClienteVentaBLL().ObtenerClienteVentaPor(actualVentaProductoTemp.get_clienteId());
				}
				catch (Exception localException)
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
				
				if(localClienteVenta == null)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo obtener los detalles del cliente venta.", 1);
					return null;
				}

				PrecioLista localPrecioLista =null;
				try
				{
					localPrecioLista = new PrecioListaBLL().ObtenerPrecioListaPor(localClienteVenta.get_precioListaId(), localProducto.get_productoId());
				}
				catch (Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
				        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precio lista: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precio lista: " + localException.getMessage());
					} 
				}
				
				if(localPrecioLista == null)
				{
					 theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo obtener el precio lista.", 1);
					 return null;
				}
				
				if(itemModificado)
				{
					SincronizacionVenta localSincroVenta = null;
					try
					{
						localSincroVenta = new SincronizacionVentaBLL().ObtenerSincronizacionVentaModificada(actualVentaProductoTemp.get_id());
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la sincronizacionVenta modificada por rowId: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener la sincronizacionVenta modificada por rowId: " + localException.getMessage());
						} 
					}
					
					if(localSincroVenta != null)
					{
						itemMostrado = true;
						tvProducto.setText(localProducto.get_descripcion25());
						tvProducto.setTextAppearance(getApplicationContext(),R.style.Contenido);
						tvCantidad.setText(String.valueOf(localSincroVenta.get_cantidadNueva()));
						tvCantidad.setTextAppearance(getApplicationContext(), R.style.Contenido);
						tvCantidadPaquete.setText(String.valueOf(localSincroVenta.get_cantidadPaqueteNueva()));
						tvCantidadPaquete.setTextAppearance(getApplicationContext(), R.style.Contenido);
						tvMonto.setText(String.valueOf(new BigDecimal(localSincroVenta.get_montoNuevo()).setScale(2,RoundingMode.HALF_UP)));
						tvMonto.setTextAppearance(getApplicationContext(), R.style.Contenido);
						tvDescuento.setText(String.valueOf(new BigDecimal(localSincroVenta.get_descuentoNuevo()).setScale(2, RoundingMode.HALF_UP)));
						tvDescuento.setTextAppearance(getApplicationContext(), R.style.Contenido);
						tvMontoFinal.setText(String.valueOf(new BigDecimal(localSincroVenta.get_montoFinalNuevo()).setScale(2,RoundingMode.HALF_UP)));
						tvMontoFinal.setTextAppearance(getApplicationContext(), R.style.Contenido);
						monto += localSincroVenta.get_montoNuevo();
						descuento += localSincroVenta.get_descuentoNuevo();
						montoFinal += localSincroVenta.get_montoFinalNuevo();
						descuentoCanal += localSincroVenta.get_descuentoCanal();
						descuentoAjuste += localSincroVenta.get_descuentoAjuste();
						descuentoProntoPago += localSincroVenta.get_descuentoProntoPago();
					}
				}
				
				if(!itemMostrado)
				{
					tvProducto.setText(localProducto.get_descripcion25());
					tvCantidad.setText(String.valueOf(actualVentaProductoTemp.get_cantidad()));
					tvCantidadPaquete.setText(String.valueOf(actualVentaProductoTemp.get_cantidadPaquete()));
					//if(actualVentaProductoTemp.get_cantidad()>0)
					//{
					//	tvMonto.setText(String.valueOf(new BigDecimal(localPrecioLista.get_precio()).setScale(2,RoundingMode.HALF_UP)));
					//}
					//else
					//{
					//	tvMonto.setText(String.valueOf(new BigDecimal(localPrecioLista.get_precioPaquete()).setScale(2,RoundingMode.HALF_UP)));
					//}
					tvMonto.setText(String.valueOf(new BigDecimal(actualVentaProductoTemp.get_monto()).setScale(2,RoundingMode.HALF_UP)));
					//tvDescuento.setText(String.valueOf(new BigDecimal(localPrecioLista.get_descuentoUnidad()+localPrecioLista.get_descuentoPaquete()).setScale(2, RoundingMode.HALF_UP)));
					float totalDescuento = actualVentaProductoTemp.get_descuento() + actualVentaProductoTemp.get_descuentoCanal() +
										actualVentaProductoTemp.get_descuentoAjuste() + actualVentaProductoTemp.get_descuentoProntoPago();
					tvDescuento.setText(String.valueOf(new BigDecimal(totalDescuento).setScale(2,RoundingMode.HALF_UP)));
					//tvDescuento.setText(String.valueOf(new BigDecimal(actualVentaProductoTemp.get_descuento()).setScale(2,RoundingMode.HALF_UP)));
					tvMontoFinal.setText(String.valueOf(new BigDecimal(actualVentaProductoTemp.get_montoFinal()).setScale(2,RoundingMode.HALF_UP)));
					monto += actualVentaProductoTemp.get_monto();
					descuento += actualVentaProductoTemp.get_descuento();
					montoFinal += actualVentaProductoTemp.get_montoFinal();
					descuentoCanal += actualVentaProductoTemp.get_descuentoCanal();
					descuentoAjuste += actualVentaProductoTemp.get_descuentoAjuste();
					descuentoProntoPago += actualVentaProductoTemp.get_descuentoProntoPago();
				}
			}
			if(actualVentaProductoTemp.get_promocionId() != 0) 
			{
				Promocion localPromocion =null;
				try
				{
					localPromocion = new PromocionBLL().ObtenerPromocionPor(actualVentaProductoTemp.get_promocionId());
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
				
				if(localPromocion == null)
		        {
					theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo obtener los detalles de la promocion.", 1);
					return null;
		        }
				
				if(itemModificado)
				{
					SincronizacionVenta localSincroVenta = null;
					try
					{
						localSincroVenta = new SincronizacionVentaBLL().ObtenerSincronizacionVentaModificada(actualVentaProductoTemp.get_id());
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la sincronizacionVenta modificada por rowId: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener la sincronizacionVenta modificada por rowId: " + localException.getMessage());
						} 
					}
					
					if(localSincroVenta != null)
					{				
						itemMostrado = true;
						tvProducto.setText(localPromocion.get_descripcion());
						tvProducto.setTextAppearance(getApplicationContext(),R.style.Contenido);
						tvCantidad.setText(String.valueOf(localSincroVenta.get_cantidadNueva()));
						tvCantidad.setTextAppearance(getApplicationContext(),R.style.Contenido);
						tvCantidadPaquete.setText("0");
						tvCantidadPaquete.setTextAppearance(getApplicationContext(),R.style.Contenido);
						tvMonto.setText(String.valueOf(new BigDecimal(localSincroVenta.get_montoNuevo()).setScale(2,RoundingMode.HALF_UP)));
						tvMonto.setTextAppearance(getApplicationContext(),R.style.Contenido);
						tvDescuento.setText(String.valueOf(new BigDecimal(localSincroVenta.get_descuentoNuevo()).setScale(2,RoundingMode.HALF_UP)));
						tvDescuento.setTextAppearance(getApplicationContext(),R.style.Contenido);
						tvMontoFinal.setText(String.valueOf(new BigDecimal(localSincroVenta.get_montoFinalNuevo()).setScale(2,RoundingMode.HALF_UP)));
						tvMontoFinal.setTextAppearance(getApplicationContext(),R.style.Contenido);
						monto += localSincroVenta.get_montoNuevo();
						descuento += localSincroVenta.get_descuentoNuevo();
						montoFinal += localSincroVenta.get_montoFinalNuevo();
						descuentoCanal += localSincroVenta.get_descuentoCanal();
						descuentoAjuste += localSincroVenta.get_descuentoAjuste();
						descuentoProntoPago += localSincroVenta.get_descuentoProntoPago();
					}
				}
				
				if(!itemMostrado)
				{
					tvProducto.setText(localPromocion.get_descripcion());
					tvCantidad.setText(String.valueOf(actualVentaProductoTemp.get_cantidad()));
					tvCantidadPaquete.setText("0");
					tvMonto.setText(String.valueOf(new BigDecimal(actualVentaProductoTemp.get_monto()).setScale(2,RoundingMode.HALF_UP)));
					float totalDescuento = actualVentaProductoTemp.get_descuento() + actualVentaProductoTemp.get_descuentoCanal() +
							actualVentaProductoTemp.get_descuentoAjuste() + actualVentaProductoTemp.get_descuentoProntoPago();
					tvDescuento.setText(String.valueOf(new BigDecimal(totalDescuento).setScale(2,RoundingMode.HALF_UP)));
					//tvDescuento.setText(String.valueOf(new BigDecimal(actualVentaProductoTemp.get_descuento()).setScale(2,RoundingMode.HALF_UP)));
					tvMontoFinal.setText(String.valueOf(new BigDecimal(actualVentaProductoTemp.get_montoFinal()).setScale(2,RoundingMode.HALF_UP)));
					monto += actualVentaProductoTemp.get_monto();
					descuento += actualVentaProductoTemp.get_descuento();
					montoFinal += actualVentaProductoTemp.get_montoFinal();
					descuentoCanal += actualVentaProductoTemp.get_descuentoCanal();
					descuentoAjuste += actualVentaProductoTemp.get_descuentoAjuste();
					descuentoProntoPago += actualVentaProductoTemp.get_descuentoProntoPago();
				}
			}
			
			tvMontoPreventa.setText(String.valueOf(new BigDecimal(monto-preventaDist.get_descuento2()).setScale(2,RoundingMode.HALF_UP)));
			tvDsctoOtros.setText(String.valueOf(new BigDecimal(descuento).setScale(2,RoundingMode.HALF_UP)));
			tvDsctoCanal.setText(String.valueOf(new BigDecimal(descuentoCanal).setScale(2,RoundingMode.HALF_UP)));
			tvDsctoAjuste.setText(String.valueOf(new BigDecimal(descuentoAjuste).setScale(2,RoundingMode.HALF_UP)));
			tvDsctoProntoPago.setText(String.valueOf(new BigDecimal(descuentoProntoPago).setScale(2,RoundingMode.HALF_UP)));
			tvDsctoObjetivo.setText(String.valueOf(new BigDecimal(preventaDist.get_descuentoObjetivo()).setScale(2,RoundingMode.HALF_UP)));
			tvDsctoSocio.setText(String.valueOf(new BigDecimal(preventaDist.get_descuentoSocio()).setScale(2,RoundingMode.HALF_UP)));
			tvDsctoIncentivo.setText(String.valueOf(new BigDecimal(preventaDist.get_descuentoIncentivo()).setScale(2,RoundingMode.HALF_UP)));
			tvTotalCalculado.setText(String.valueOf(new BigDecimal(monto - descuento - descuentoCanal - descuentoAjuste - descuentoProntoPago -
																		preventaDist.get_descuentoObjetivo() - preventaDist.get_descuentoSocio()-
																		preventaDist.get_descuentoIncentivo()).setScale(2,RoundingMode.HALF_UP)));
			
			if(actualVentaProductoTemp.get_tempId() == 0)
			{
				ivBullet.setImageResource(R.drawable.bullet_seleccionar_nosincronizado);
			}
			else
			{
				ivBullet.setImageResource(R.drawable.bullet_seleccionar);
			}
			
			return localView;
		}
	}

	private void SeleccionarItemListView()
	{
		lvVentaProductoTemp.setOnItemClickListener(new AdapterView.OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
				VentaProductoTemp localVentaProductoTemp = listadoVentaProductoTemp.get(position);
				if(preventaBonificada)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "La preventa esta bonificada, no puede realizar cambios.", 1);
				}
				else if(preventaCambio)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "La preventa tiene productos para cambio, no puede realizar modificaciones.", 1);
				}
				else if (preventaConProntoPago)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "La preventa tiene productos con descuento pronto pago, no puede realizar modificaciones.", 1);
				}
				else if(preventaConObjetivo)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "La preventa tiene productos con descuento por objetivos, no puede realizar modificaciones.", 1);
				}
				else if(formaPagoId == 2)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "La preventa ya se encuentra pagada, no puede realizar modificaciones.", 1);
				}
				else if(preventaDist.get_descuentoSocio() > 0)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "La preventa tiene descuento socio, no puede realizar modificaciones.", 1);
				}
				else if(preventaDist.get_descuentoIncentivo() > 0)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "La preventa tiene descuento incentivo, no puede realizar modificaciones.", 1);
				}
				else
				{
					MostrarPantallaVentaProducto(localVentaProductoTemp.get_productoId(),localVentaProductoTemp.get_promocionId(),
							localVentaProductoTemp.get_id());	
				}
			}
		});
	}
	
	private void MostrarTitulosVentaProductoTemp(boolean estado)
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
		
		tvProductolbl.setVisibility(visibility);
		tvCauntidadUnitarialbl.setVisibility(visibility);
		tvCantidadPaquetelbl.setVisibility(visibility);
		tvMontolbl.setVisibility(visibility);
		tvDescuentolbl.setVisibility(visibility);
		tvSubTotal.setVisibility(visibility);
		tvTotalCalculadolbl.setVisibility(visibility);
		tvTotalCalculado.setVisibility(visibility);		
	}
	
	public void BorrarTablasTemporales()
	{
		boolean boolVPT = false;
      	try
  		{
      		boolVPT = new VentaProductoTempBLL().BorrarVentasProductoTemp();
  		}
  		catch (Exception localException)
  		{
  			if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventasProductoTemp: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventasProductoTemp: " + localException.getMessage());
        	} 
        	return;
  		}
      	
      	if(boolVPT==false)
      	{
      		theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo borrar las ventas producto temporales.",1);
      		return;
      	}
      	
      	boolean boolSV = false;
      	try
  		{
      		boolSV = new SincronizacionVentaBLL().BorrarSincronizacionesVentaNoConfirmadas();
  		}
  		catch (Exception localException)
  		{
  			if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
               myLog.InsertarLog("App",this.toString(),1,"Error al borrar las sincronizacionesVentaNoConfirmadas: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las sincronizacionesVentaNoConfirmadas: " + localException.getMessage());
        	} 
        	return;
  		}
      	
      	if(boolSV==false)
      	{
      		theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo borrar las ventas producto temporales no confirmadas.",1);
      		return;
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
			localSoapObject.addProperty("preVentaId", Integer.valueOf(preventaDist.get_preventaId()));
			localSoapObject.addProperty("distribuidorId", Integer.valueOf(loginEmpleado.get_empleadoId()));
			
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
			
			if(ejecutado)
			{
				if(WSCopiarPreVentaAVentaTemp)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"Copia de preventa insertada en el servidor.", 1);
          
					ObtenerVentaProductoTemp();
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"La copia de preventa NO fue insertada en el servidor.", 1);
					MostrarVentasProductoTemp(false);
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"El webservise WSCopiarPreVentaAVentaTemp no se ejecuto correctamente.", 1);
				return;
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
			localSoapObject.addProperty("clienteId", Integer.valueOf(clienteId));
			localSoapObject.addProperty("empleadoId", Integer.valueOf(loginEmpleado.get_empleadoId()));
			
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
			
			if (ejecutado)
			{
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
										Integer.parseInt(localSoapObject.getPropertyAsString("MotivoId")),0,
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
						
						theUtilidades.MostrarMensaje(getApplicationContext(),"Items de la preventa obtenidos del servidor.", 1);
						MostrarVentasProductoTemp(true);
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar la venta producto temporal.", 1);
						return;
					}
				}
				else
				{
					MostrarVentasProductoTemp(false);
					theUtilidades.MostrarMensaje(getApplicationContext(), "Items de la preventa obtenidos del dispositivo.", 1);
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservise WSGetVentaProductoTempBy no se ejecuto correctamente.", 1);
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
		  	  
	private void MostrarVentasProductoTemp(boolean estadoVentaProductoTemp)
	{
		if(estadoVentaProductoTemp==false)
		{
			ArrayList<PreventaProductoDist> listadoPreventaProductoDist =null;
			try
			{
				listadoPreventaProductoDist = new PreventaProductoDistBLL().ObtenerPreventasProductoDistPor(preventaDist.get_preventaId());
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"No se pudo obtener las preventasProductoDist por preventaId: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"No se pudo obtener las preventasProductoDist por preventaId: " + localException.getMessage());
				} 
			}
			
			if(listadoPreventaProductoDist == null)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los productos de la preventa del distribuidor.", 1);
				return;
			}
			else
			{
				for(PreventaProductoDist item : listadoPreventaProductoDist)
				{
					long l=0;
					try
					{
						l = new VentaProductoTempBLL().InsertarVentaProductoTemp(0,item.get_productoId(),item.get_promocionId(),
								item.get_cantidad(),0,item.get_cantidadPaquete(),0,item.get_monto(),0,item.get_descuento(),0,
								item.get_montoFinal(),0,loginEmpleado.get_empleadoId(),clienteId,0,item.get_costo(),
								item.get_costoId(),item.get_precioId(),item.get_descuentoCanal(),item.get_descuentoAjuste(),
								item.get_canalPrecioRutaId(),item.get_descuentoProntoPago());
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ventaProductoTemp: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ventaProductoTemp: " + localException.getMessage());
						} 
					}
					
					if(l==0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar los productos de la venta temporal.", 1);
						return;
					}
				}				
			}
		}
		
		listadoVentaProductoTemp = null;
		try
		{
			listadoVentaProductoTemp = new VentaProductoTempBLL().ObtenerVentasProductoTempNoConfirmadas(clienteId, loginEmpleado.get_empleadoId());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ventaProductoTemp por clienteId y distribuidorId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ventaProductoTemp por clienteId y distribuidorId: " + localException.getMessage());
			} 
		}
		
		if(listadoVentaProductoTemp==null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se  pudo obtener las ventas producto temporales por clienteId y distribuidorId.", 1);
			return;
		}
		
		MostrarVentaProductoTempListView();
		SeleccionarItemListView();
	}
	
	public void ActualizarPOPListView()
	{
		listadoPreventaProductoPOP = null;
		try
		{
			listadoPreventaProductoPOP = new PreventaProductoPOPBLL().ObtenerPreventasPorductoPOPPorPreventaPOPIdServer(preventaDist.get_preventaId());
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventaProductoPOP por preventaIdPOPServer : vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventaProductoPOP por preventaIdPOPServer: " + localException.getMessage());
			}
		}
		      
		if(listadoPreventaProductoPOP == null)
		{
			tvMaterialPOP.setVisibility(View.INVISIBLE);
			lvPreventaProductoPOP.setVisibility(View.INVISIBLE);
			theUtilidades.MostrarMensaje(getApplicationContext(),"No existen preventaProductoPOP.", 1);
	    	lvPreventaProductoPOP.setAdapter(null);
		}
		else
		{
			tvMaterialPOP.setVisibility(View.VISIBLE);
			lvPreventaProductoPOP.setVisibility(View.VISIBLE);
		    LlenarPreventaListView();
		}    
	}
	
	private void LlenarPreventaListView()
	{
	    MiAdaptadorPOPLista localMiAdaptadorPOPLista = new MiAdaptadorPOPLista(getApplicationContext());
	    	    
	    if(listadoPreventaProductoPOP == null)
	    {
	    	lvPreventaProductoPOP.setAdapter(null);
	    }
	    else
	    {
		    ViewGroup.LayoutParams localLayoutParams = lvPreventaProductoPOP.getLayoutParams();
		    localLayoutParams.height = ((int)(50*getApplicationContext().getResources().getDisplayMetrics().density) * listadoPreventaProductoPOP.size());
		    lvPreventaProductoPOP.setLayoutParams(localLayoutParams);
		    lvPreventaProductoPOP.setAdapter(localMiAdaptadorPOPLista);
	    }
	}

	class MiAdaptadorPOPLista extends ArrayAdapter<PreventaProductoPOP>
	{
		private Context _context;
		
		public MiAdaptadorPOPLista(Context context)
		{
			super(context,R.layout.disenio_distribuidorventaproductotemp,listadoPreventaProductoPOP);
			this._context = context;
		}
    
		public View getView(final int position, View convertView, ViewGroup parent)
		{
			View localView = convertView;
			
			if (localView == null) 
			{				
				LayoutInflater layoutInflater =	(LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    		localView = layoutInflater.inflate(R.layout.disenio_edicionpreventas, parent, false);
			}
			
			final PreventaProductoPOP localPreventaProductoPOP = (PreventaProductoPOP)listadoPreventaProductoPOP.get(position);
			
			ImageView imgbtnEliminar= (ImageView)localView.findViewById(R.id.imgbtnDisenioEdicionPreventaEliminar);
			ImageView imgbtnObservacion= (ImageView)localView.findViewById(R.id.imgbtnDisenioEdicionPreventaDetalles);
			TextView tvProducto = (TextView)localView.findViewById(R.id.tvVendedorVentaDirectaCliente);
			TextView tvCantidad = (TextView)localView.findViewById(R.id.tvDisenioEdicionPreventaMonto);
			
			if(localPreventaProductoPOP.get_productoPOPId() != 0)
			{
				ProductoPOP localProductoPOP = null;
				try
				{
					localProductoPOP = new ProductoPOPBLL().ObtenerProductoPOPPorProductoId(localPreventaProductoPOP.get_productoPOPId());
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
				
				if(localProductoPOP == null)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el productoPOP.", 1);
		            return null;
				}
				
				tvProducto.setText(localProductoPOP.get_descripcion25());
				tvCantidad.setText(String.valueOf(localPreventaProductoPOP.get_cantidad()));	
			}
			
			imgbtnEliminar.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					dialog = new Dialog(Distribuidorventa.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.diseniodialogo_vendedorcierredia);
					dialog.setTitle("Eliminando item");
					dialog.setCancelable(false);
					dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.colorBluePale));
					
					TextView tvMensaje = (TextView)dialog.findViewById(R.id.tvDialogoMensaje);
					tvMensaje.setText("Esta seguro de eliminar el producto POP?");
					
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
									
									PreventaProductoPOP localPreventaProductoPOP = listadoPreventaProductoPOP.get(position);
									//itemBorrarDispositivo = localPreventaProductoPOP.get_id();
							
									if(localPreventaProductoPOP.get_preventaPOPIdServer() != 0)
									{	
										BorrarPreVentaProductoPOP(localPreventaProductoPOP.get_id(),localPreventaProductoPOP.get_preventaPOPIdServer(),localPreventaProductoPOP.get_productoPOPId());
									}
									else
									{
										if(BorrarPreventaProductoPOPDispositivo(localPreventaProductoPOP.get_id()))
										{
											theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la preventaPOP eliminado del dispositivo.", 1);
											//CargarProductos();
										} 
										else 
										{
											theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo eliminar el producto POP de la preventa.", 1);
										}
									}

									if(listadoPreventaProductoPOP != null) 
									{
										ActualizarPOPListView();
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
			
			imgbtnObservacion.setOnClickListener(new View.OnClickListener() 
			{	
				@Override
				public void onClick(View v) 
				{
					dialog = new Dialog(Distribuidorventa.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.diseniodialogo_distribuidorbservacionpop);
					dialog.setTitle("Eliminando item");
					dialog.setCancelable(false);
					dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.colorBluePale));
					
					TextView tvMensaje = (TextView)dialog.findViewById(R.id.tvDisenioDistribuidorPOPMensaje);
					final EditText etObservacion = (EditText)dialog.findViewById(R.id.etDisenioDistribuidorPOPObservacion);
										
					tvMensaje.setText("Ingrese la observacion para el material POP:");
					
					Button btnAceptar = (Button)dialog.findViewById(R.id.btnDisenioDistribuidorPOPAceptar);
					Button btnCancelar = (Button)dialog.findViewById(R.id.btnDisenioDistribuidorPOPCancelar);
					Button btnEliminar = (Button)dialog.findViewById(R.id.btnDisenioDistribuidorPOPEliminar);
					
					if(localPreventaProductoPOP.get_observacion().isEmpty())
					{
						btnEliminar.setVisibility(View.INVISIBLE);
					}
					else
					{
						
						btnEliminar.setVisibility(View.VISIBLE);
						etObservacion.setText(localPreventaProductoPOP.get_observacion());
					}
					
					btnAceptar.setOnClickListener(new OnClickListener() 
						{		
							@Override
							public void onClick(View v) 
							{
								switch(v.getId())
								{
								case R.id.btnDisenioDistribuidorPOPAceptar:
									
									if(dialog.isShowing())
									{
										dialog.dismiss();
									}
									
									PreventaProductoPOP localPreventaProductoPOP = listadoPreventaProductoPOP.get(position);
									
									long l=0;
									try
									{
										l = new PreventaProductoPOPBLL().ModificarPreventaProductosPOPor(localPreventaProductoPOP.get_id(),
																											etObservacion.getText().toString());
									}catch(Exception localException)
									{
										if(localException.getMessage() == null || localException.getMessage().isEmpty())
						            	{
						                    myLog.InsertarLog("App",this.toString(),1,"Error al modificar la observacion del ProductoPOP: vacio");
						            	}
						            	else
						            	{
						            		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la observacion del ProductoPOP: " + localException.getMessage());
						            	} 
									}
									
									if(l==0)
									{
										theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la observacion.", 1);
									}
									else
									{
										localPreventaProductoPOP.set_observacion(etObservacion.getText().toString());
										InsertarObservacionPOP(localPreventaProductoPOP.get_preventaPOPIdServer(),
												localPreventaProductoPOP.get_productoPOPId(),etObservacion.getText().toString());
									}
									
									break;
								}	
							}
						});
					
					
					btnCancelar.setOnClickListener(new OnClickListener() 
						{
							@Override
							public void onClick(View v) 
							{
								switch(v.getId())
								{
								case R.id.btnDisenioDistribuidorPOPCancelar:
									if(dialog.isShowing())
									{
										dialog.dismiss();
									}
								}							
							}
						});
					
					
					btnEliminar.setOnClickListener(new OnClickListener() 
					{
						@Override
						public void onClick(View v) 
						{
							switch(v.getId())
							{
							case R.id.btnDisenioDistribuidorPOPEliminar:
								
								if(dialog.isShowing())
								{
									dialog.dismiss();
								}
								
								PreventaProductoPOP localPreventaProductoPOP = listadoPreventaProductoPOP.get(position);
								
								long l=0;
								try
								{
									l = new PreventaProductoPOPBLL().ModificarPreventaProductosPOPor(localPreventaProductoPOP.get_id(),"");
								}
								catch(Exception localException)
								{
									if(localException.getMessage() == null || localException.getMessage().isEmpty())
					            	{
					                    myLog.InsertarLog("App",this.toString(),1,"Error al modificar la observacion del ProductoPOP: vacio");
					            	}
					            	else
					            	{
					            		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la observacion del ProductoPOP: " + localException.getMessage());
					            	} 
								}
								
								if(l==0)
								{
									theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la observacion.", 1);
								}
								else
								{
									EliminarrObservacionPOP(localPreventaProductoPOP.get_preventaPOPIdServer(), localPreventaProductoPOP.get_productoPOPId());
								}
								
								break;
							}
						}
					});
					
					dialog.show();
				}
			});
			
			return localView;
		}
	}
	
	private void BorrarPreVentaProductoPOP(int id,int preventaPOPIdServer,int productoPOPId)
	{
		pdDeletePreventaProductoPOP = new ProgressDialog(this);
		pdDeletePreventaProductoPOP.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdDeletePreventaProductoPOP.setMessage("Borrando item preventa pop ...");
		pdDeletePreventaProductoPOP.setCancelable(false);
		pdDeletePreventaProductoPOP.setCanceledOnTouchOutside(false);
		 
		WSBorrarPreventaProductoPOP localWSBorrarPreventaProductoPOP = new WSBorrarPreventaProductoPOP(id,preventaPOPIdServer,productoPOPId);
		try
		{
			localWSBorrarPreventaProductoPOP.execute();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSDeletePreVentaProductoPOP: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSDeletePreventaProductoPOP: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSDeletePreVentaProductoPOP.", 1);
	 	}
	}
	 
	private class WSBorrarPreventaProductoPOP extends AsyncTask<Void, Integer, Boolean>
	 {
	    String BORRARPREVENTAPRODUCTO_METHOD_NAME = "DeletePreVentaProductoPop";
	    String BORRARPREVENTAPRODUCTO_SOAP_ACTION = NAMESPACE + BORRARPREVENTAPRODUCTO_METHOD_NAME;
	    private int _id;
	    private int _preventaPOPIdServer;
	    private int _productoPOPId;
	    
	    public WSBorrarPreventaProductoPOP(int id,int preventaPOPIdServer,int productoPOPId)
	    {
	    	this._id = id;
	    	this._preventaPOPIdServer = preventaPOPIdServer;
	    	this._productoPOPId = productoPOPId;
	    }
	    
	    boolean WSBorrarPreVentaProductoPOP;
	    int resultadoInt;
	    String resultadoString;
	    SoapObject soapResultado;
	    
	    protected void onPreExecute()
	    {
	    	pdDeletePreventaProductoPOP.show();
	    }
	    
	    protected Boolean doInBackground(Void... paramVarArgs)
	    {
	    	WSBorrarPreVentaProductoPOP = false;
	    	
	    	SoapObject localSoapObject = new SoapObject(NAMESPACE,BORRARPREVENTAPRODUCTO_METHOD_NAME);
	    	localSoapObject.addProperty("preVentaId", Integer.valueOf(_preventaPOPIdServer));
	    	localSoapObject.addProperty("productoId", Integer.valueOf(_productoPOPId));
	    	
	    	SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
	    	localSoapSerializationEnvelope.dotNet = true;
	    	localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
	    	HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
	    		
	    	try
	    	{
	    		localHttpTransportSE.call(BORRARPREVENTAPRODUCTO_SOAP_ACTION, localSoapSerializationEnvelope);
	    		
	    		soapResultado = ((SoapObject)localSoapSerializationEnvelope.getResponse());
	    		
	    		if(soapResultado!=null)
	    		{
	    			resultadoInt = Integer.parseInt(soapResultado.getPropertyAsString("Id"));
		    	    resultadoString = String.valueOf(soapResultado.getPropertyAsString("Resultado"));	    			
	    		}
	    	        
	    	    if (resultadoString.equals("OK") && resultadoInt > 0) 
	    	    {
	    	      	WSBorrarPreVentaProductoPOP = true;
	    	    }
	    	    return true;	        
			}
	    	catch (Exception localException)
	    	{
    			WSBorrarPreVentaProductoPOP = false;
    			if(localException.getMessage() == null || localException.getMessage().isEmpty())
    			{
    		        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSDeletePreventaProductoPop: vacio");
    			}
    			else
    			{
    				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSDeletePreVentaProductoPOP: " + localException.getMessage());
    			} 
    			return true;
    		}
    	}
	    
	    protected void onPostExecute(Boolean ejecutado)
	    {
	    	if(pdDeletePreventaProductoPOP.isShowing())
	    	{
	    		pdDeletePreventaProductoPOP.dismiss();
	    	}
	    	
	    	if(ejecutado)
	    	{
	    		if(WSBorrarPreVentaProductoPOP) 
	    		{
	    			if(BorrarPreventaProductoPOPDispositivo(_id)) 
	    			{
	    				theUtilidades.MostrarMensaje(getApplicationContext(), "Item de la preventa eliminado.", 1);
	    				ActualizarPOPListView();
    				}
	    			else
	    			{
	    				theUtilidades.MostrarMensaje(getApplicationContext(), "No se elimino el item de la preventa del dispositivo.", 1);
	    			}
	    		}
	    		else
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar el material POP, no se encuentra con conexion estable a internet. " + this.resultadoString, 1);
	    			return;
	    		}
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSDeletePreventaProductoPOP.", 1);
	    	}
	    }
	 }
	
	private boolean BorrarPreventaProductoPOPDispositivo(int id)
	 {
		 boolean bool = false;
		 try
		 {
			 bool = new PreventaProductoPOPBLL().BorrarPreventaProductoPOPPor(id);
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al borrar el item de la preventaProductoPOP por Id: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al borrar el item de la preventaProductoPOP por Id: " + localException.getMessage());
			 }
			 return false;
		 }
	    
		 if(bool)
		 {
			 return true;
		 }
		 else
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar el productoPOP de la preventa.", 1);
			 return false;
		 }
	}
		
	public void ActualizarProductoCambioListView()
	{
		listadoPreventaProductoCambio = null;
		try
		{
			listadoPreventaProductoCambio = new PreventaProductoCambioBLL().ObtenerPreventasProductoCambioPorPreventaIdServer(preventaDist.get_preventaId());
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventaProductoCambio por preventaIdCambioServer : vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventaProductoCambio por preventaIdCambioServer: " + localException.getMessage());
			}
		}
		      
		if(listadoPreventaProductoCambio == null)
		{
			tvProductoCambio.setVisibility(View.INVISIBLE);
			lvPreventaProductoCambio.setVisibility(View.INVISIBLE);
			theUtilidades.MostrarMensaje(getApplicationContext(),"No existen preventas con productos para Cambio.", 1);
	    	lvPreventaProductoCambio.setAdapter(null);
		}
		else
		{
			tvProductoCambio.setVisibility(View.VISIBLE);
			lvPreventaProductoCambio.setVisibility(View.VISIBLE);
		    LlenarPreventaProductoCambioListView();
		}    
	}
	
	private void LlenarPreventaProductoCambioListView()
	{
	    MiAdaptadorProductoCambioLista localMiAdaptadorPOPLista = new MiAdaptadorProductoCambioLista(getApplicationContext());
	    	    
	    if(listadoPreventaProductoCambio == null)
	    {
	    	lvPreventaProductoCambio.setAdapter(null);
	    }
	    else
	    {
		    ViewGroup.LayoutParams localLayoutParams = lvPreventaProductoCambio.getLayoutParams();
		    localLayoutParams.height = ((int)(50*getApplicationContext().getResources().getDisplayMetrics().density) * listadoPreventaProductoCambio.size());
		    lvPreventaProductoCambio.setLayoutParams(localLayoutParams);
		    lvPreventaProductoCambio.setAdapter(localMiAdaptadorPOPLista);
	    }
	}

	class MiAdaptadorProductoCambioLista extends ArrayAdapter<PreventaProductoCambio>
	{
		private Context _context;
		
		public MiAdaptadorProductoCambioLista(Context context)
		{
			super(context,R.layout.disenio_distribuidorventaproductotemp,listadoPreventaProductoCambio);
			this._context = context;
		}
    
		public View getView(final int position, View convertView, ViewGroup parent)
		{
			View localView = convertView;
			
			if (localView == null) 
			{				
				LayoutInflater layoutInflater =	(LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    		localView = layoutInflater.inflate(R.layout.disenio_edicionpreventas, parent, false);
			}
			
			final PreventaProductoCambio localPreventaProductoCambio = (PreventaProductoCambio)listadoPreventaProductoCambio.get(position);

			ImageView imgbtnEliminar= (ImageView)localView.findViewById(R.id.imgbtnDisenioEdicionPreventaEliminar);
			ImageView imgbtnObservacion= (ImageView)localView.findViewById(R.id.imgbtnDisenioEdicionPreventaDetalles);
			TextView tvProducto = (TextView)localView.findViewById(R.id.tvVendedorVentaDirectaCliente);
			TextView tvCantidad = (TextView)localView.findViewById(R.id.tvDisenioEdicionPreventaMonto);
			
			if(localPreventaProductoCambio.get_productoId() != 0)
			{
				ProductoCambio localProductoCambio = null;
				try
				{
					localProductoCambio = new ProductoCambioBLL().ObtenerProductoCambioPorProductoId(localPreventaProductoCambio.get_productoId());
				}
				catch (Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
				        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del producto cambio: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del producto cambio: " + localException.getMessage());
					} 
				}
				
				if(localProductoCambio == null)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el producto para cambio.", 1);
		            return null;
				}
			
				imgbtnObservacion.setVisibility(View.INVISIBLE);
				imgbtnEliminar.setVisibility(View.INVISIBLE);
				tvProducto.setText(localProductoCambio.get_descripcion25());
				tvCantidad.setText(String.valueOf(localPreventaProductoCambio.get_cantidad()));	
			}
					
			return localView;
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
		
	private void InsertarVentaNoEntregada()
	{
		pdVentaNoEntregada = new ProgressDialog(this);
		pdVentaNoEntregada.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdVentaNoEntregada.setMessage("Insertando venta no entregada ...");
		pdVentaNoEntregada.setCancelable(false);
		pdVentaNoEntregada.setCanceledOnTouchOutside(false);
		
		int motivoNoEntregaId = ((MotivoNoEntrega)(spnMotivoNoEntrega.getSelectedItem())).get_motivoId();
		
		WSVentaNoEntregada localWSVentaNoEntregada = new WSVentaNoEntregada(motivoNoEntregaId);
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
		private int _motivoId;
		SoapObject soapObjects;
		int resultadoInt;
		String resultadoString;
    
		public WSVentaNoEntregada(int motivoNoEntregaId)
		{
			this._motivoId = motivoNoEntregaId;
		}
		
		protected void onPreExecute()
	    {
	    	pdVentaNoEntregada.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSVentaNoEntregada = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,VENTANOENTREGADA_METHOD_NAME);
			localSoapObject.addProperty("preVentaId", Integer.valueOf(preventaDist.get_preventaId()));
			localSoapObject.addProperty("distribuidorId", Integer.valueOf(loginEmpleado.get_empleadoId()));
			localSoapObject.addProperty("clienteId", Integer.valueOf(clienteId));
			localSoapObject.addProperty("anio", Integer.valueOf(loginEmpleado.get_anio()));
			localSoapObject.addProperty("mes", Integer.valueOf(loginEmpleado.get_mes()));
			localSoapObject.addProperty("dia", Integer.valueOf(loginEmpleado.get_dia()));
			localSoapObject.addProperty("motivoId", Integer.valueOf(this._motivoId));
    		
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
    	
			if(ejecutado)
			{
				if(WSVentaNoEntregada
						|| (resultadoString != null && resultadoString.equals("No Entrega Repetida") && resultadoInt <=0)) 
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"Venta No entregada, registrada en el server.", 1);
					InsertarVentaNoEntregadaDispositivo(true);
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"La venta NO entregada, no fue registrada en el server.", 1);
					InsertarVentaNoEntregadaDispositivo(false);
				}
		
				ModificarEstadoEntregaClienteVenta();
				ModificarEstadoEntregaPreventaDist();
				RegistrarConfirmacionVenta();
				MostrarPantallaMapa();
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservise WSVentaNoEntregada no se ejecuto correctamente..", 1);
				return;
			}
		}
	}
	
	private void InsertarVentaNoEntregadaDispositivo(boolean estadoSincronizacion)
	{
		boolean sincronizacion;
		
		long devolucionDistribuidorId;
		int cantidadExistente;
		int cantidadPaqueteExistente;
		int cantidadTotalEnUnidades;
		int cantidadConsolidada;
		int cantidadPaqueteConsolidada;
		
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
			l = new SincronizacionVentaBLL().InsertarSincronizacionVenta(0,preventaDist.get_preventaId(),clienteId,
											loginEmpleado.get_empleadoId(),0,0,0,0,0,0,0,preventaDist.get_tipoPagoId(),0,0,0,
											0,0,0,((MotivoNoEntrega)spnMotivoNoEntrega.getSelectedItem()).get_motivoId(),
											loginEmpleado.get_dia(),loginEmpleado.get_mes(),loginEmpleado.get_anio(),
											latitudDistribuidor,longitudDistribuidor,false,2,sincronizacion,fecha.get_hora(),
											fecha.get_minuto(),false,"","",false,0,0,0,0,preventaDist.get_observacion(),0,false,0,
											preventaDist.get_dosificacionId(),"",preventaDist.get_descuentoCanal(),
											preventaDist.get_descuentoAjuste(),0,preventaDist.get_descuentoProntoPago(),
											preventaDist.get_prontoPagoId(),preventaDist.get_descuentoObjetivo(), 
											formaPagoId, etCodTrans.getText().toString(), preventaDist.is_fromShopp());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacionVenta (Venta No Entregada): vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacionVenta (Venta No Entregada): " + localException.getMessage());
			} 
		}
		
		if(l<=0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la sincronizacion venta: EliminarProducto", 1);
			return;
		}
		else
		{
			//Verificamos la existencia de un almacenDevolucionDistribuidor
			DevolucionDistribuidor localDevolucionDistribuidor =null;
			try
			{
				localDevolucionDistribuidor = new DevolucionDistribuidorBLL().ObtenerDevolucionDistribuidorPorDistribuidor(
						loginEmpleado.get_empleadoId());
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidor por distribuidorId: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidor por distribuidorId: " + localException.getMessage());
				} 
			}
			
			if(localDevolucionDistribuidor==null)
			{
				devolucionDistribuidorId = 0;
				try
				{
					devolucionDistribuidorId = new DevolucionDistribuidorBLL().InsertarDevolucionDistribuidor(1,
										loginEmpleado.get_empleadoId(),loginEmpleado.get_anio(),loginEmpleado.get_mes(),
										loginEmpleado.get_dia(),0,false);
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
				        myLog.InsertarLog("App",this.toString(),1,"Error al insertar almacenDistribuidor: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al insertar almacenDistribuidor: " + localException.getMessage());
					} 
				}
				
				if(devolucionDistribuidorId == 0)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el almacen distribuidor.", 1);
					return;
				}
			}
			else
			{
				devolucionDistribuidorId = localDevolucionDistribuidor.get_id(); 
			}
					
			//Empezamos a recorrer la lista de VentaProductoTemp, para pasar los productos/promociones
			//al almacen devolucionDistribuidorProducto
			
			for(VentaProductoTemp itemVentaTemp : listadoVentaProductoTemp)
			{
				if(itemVentaTemp.get_productoId()>0)
				{
					//Verificamos si el producto existe en el almacenDistribuidor
					DevolucionDistribuidorProducto localDevolucionDistribuidorProducto = null;
					try
					{
						localDevolucionDistribuidorProducto = new DevolucionDistribuidorProductoBLL().
																	ObtenerDevolucionDistribuidorProductoPorProductoId(
																	(int)devolucionDistribuidorId, itemVentaTemp.get_productoId());
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidor por productoId: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidor por productoId: " + localException.getMessage());
						} 
					}
					
					if(localDevolucionDistribuidorProducto == null)
					{
						//Como el producto no existe lo insertamos directamente en unidades y paquetes
	
						long devDisProId=0;
						try
						{
							devDisProId = new DevolucionDistribuidorProductoBLL().
									InsertarDevolucionDistribuidorProducto((int)devolucionDistribuidorId, itemVentaTemp.get_productoId(),
									itemVentaTemp.get_cantidad(),itemVentaTemp.get_cantidadPaquete(),false);
						}
						catch(Exception localException)
						{
							if(localException.getMessage() == null || localException.getMessage().isEmpty())
							{
						        myLog.InsertarLog("App",this.toString(),1,"Error al insertar el producto en el almacen devolucionDistribuidor: vacio");
							}
							else
							{
								myLog.InsertarLog("App",this.toString(),1,"Error al insertar el producto en el almacen devolucionDistribuidor: " + localException.getMessage());
							} 
						}
						
						if(devDisProId == 0)
						{
							theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el producto en el almacen devolucion distribuidor.", 1);
							return;
						}
					}
					else
					{
						//Como el producto existe debemos consolidarlo y modificar las cantidades resultantes
						Producto localProducto = null;
						try
						{
							localProducto = new ProductoBLL().ObtenerProductoPor(itemVentaTemp.get_productoId());
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
						
						if(localProducto == null)
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del producto.", 1);
							return;
						}
						
						cantidadExistente = localDevolucionDistribuidorProducto.get_cantidad() + itemVentaTemp.get_cantidad();
						cantidadPaqueteExistente = localDevolucionDistribuidorProducto.get_cantidadPaquete() + 
																			itemVentaTemp.get_cantidadPaquete();
						
						cantidadTotalEnUnidades = cantidadPaqueteExistente * localProducto.get_conversion() + cantidadExistente;
						
						cantidadPaqueteConsolidada =  cantidadTotalEnUnidades / localProducto.get_conversion();
						cantidadConsolidada = cantidadTotalEnUnidades % localProducto.get_conversion();
						
						long update =0;
						try
						{
							update = new DevolucionDistribuidorProductoBLL().ModificarCantidadesDevolucionDistribuidorProducto(
												(int)devolucionDistribuidorId, itemVentaTemp.get_productoId(), cantidadConsolidada,
												cantidadPaqueteConsolidada);
						}
						catch(Exception localException)
						{
							if(localException.getMessage() == null || localException.getMessage().isEmpty())
							{
						        myLog.InsertarLog("App",this.toString(),1,"Error al modificar las cantidades del almacen devoluciondistribuidor: vacio");
							}
							else
							{
								myLog.InsertarLog("App",this.toString(),1,"Error al modificar las cantidades del almacen devoluciondistribuidor: " + localException.getMessage());
							} 
						}
						
						if(update == 0)
						{
							theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo modificar las cantidades del almacen devolucion distribuidor.", 1);
							return;
						}
					}
				}
				if(itemVentaTemp.get_promocionId()>0)
				{
					//Obtenemos los productos que componen la promocion
					ArrayList<PromocionProducto> listadoPromocionProducto = null;
					try
					{
						listadoPromocionProducto = new PromocionProductoBLL().ObtenerPromocionesProductoPor(itemVentaTemp.get_promocionId());
					}
					catch(Exception localException)
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
					
					if(listadoPromocionProducto==null)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los productos de la promocion por promocionId", 1);
						return;
					}
					else
					{
						//Recorremos los productos que componen la promocion para ingresarlos al almacen devolucionDistribuidor
						for(PromocionProducto itemPromocion : listadoPromocionProducto)
						{
							//Verificamos que el producto existe en el almacen devolucionDistribuidor
							DevolucionDistribuidorProducto localDevolucionDistribuidorProducto = null;
							try
							{
								localDevolucionDistribuidorProducto = new DevolucionDistribuidorProductoBLL().
																			ObtenerDevolucionDistribuidorProductoPorProductoId(
																			(int)devolucionDistribuidorId, itemPromocion.get_productoId());
							}
							catch(Exception localException)
							{
								if(localException.getMessage() == null || localException.getMessage().isEmpty())
								{
							        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidor por productoId: vacio");
								}
								else
								{
									myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidor por productoId: " + localException.getMessage());
								} 
							}
							
							if(localDevolucionDistribuidorProducto == null)
							{
								//Como el producto no existe lo insertamos directamente en unidades y paquetes 
								//multiplicados por la cantidad solicitada
								long devDisProId=0;
								try
								{
									devDisProId = new DevolucionDistribuidorProductoBLL().
											InsertarDevolucionDistribuidorProducto((int)devolucionDistribuidorId, itemPromocion.get_productoId(),
											itemPromocion.get_cantidad()*itemVentaTemp.get_cantidad(),
											itemPromocion.get_cantidadPaquete()*itemVentaTemp.get_cantidad(),false);
								}
								catch(Exception localException)
								{
									if(localException.getMessage() == null || localException.getMessage().isEmpty())
									{
								        myLog.InsertarLog("App",this.toString(),1,"Error al insertar el producto en el almacen devolucionDistribuidor: vacio");
									}
									else
									{
										myLog.InsertarLog("App",this.toString(),1,"Error al insertar el producto en el almacen devolucionDistribuidor: " + localException.getMessage());
									} 
								}
								
								if(devDisProId == 0)
								{
									theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el producto en el almacen devolucion distribuidor.", 1);
									return;
								}
							}
							else
							{
								//Como el producto existe debemos consolidarlo y modificar las cantidades resultantes
								Producto localProducto = null;
								try
								{
									localProducto = new ProductoBLL().ObtenerProductoPor(itemPromocion.get_productoId());
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
								
								if(localProducto == null)
								{
									theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del producto.", 1);
									return;
								}
								
								cantidadExistente = localDevolucionDistribuidorProducto.get_cantidad() + 
																		itemPromocion.get_cantidad()*itemVentaTemp.get_cantidad();
								cantidadPaqueteExistente = localDevolucionDistribuidorProducto.get_cantidadPaquete() + 
																		itemPromocion.get_cantidadPaquete()*itemVentaTemp.get_cantidad();
								
								cantidadTotalEnUnidades = cantidadPaqueteExistente * localProducto.get_conversion() + cantidadExistente;
								
								cantidadPaqueteConsolidada =  cantidadTotalEnUnidades / localProducto.get_conversion();
								cantidadConsolidada = cantidadTotalEnUnidades % localProducto.get_conversion();
								
								long update =0;
								try
								{
									update = new DevolucionDistribuidorProductoBLL().ModificarCantidadesDevolucionDistribuidorProducto(
														(int)devolucionDistribuidorId, itemPromocion.get_productoId(), cantidadConsolidada,
														cantidadPaqueteConsolidada);
								}
								catch(Exception localException)
								{
									if(localException.getMessage() == null || localException.getMessage().isEmpty())
									{
								        myLog.InsertarLog("App",this.toString(),1,"Error al modificar las cantidades del almacen devoluciondistribuidor: vacio");
									}
									else
									{
										myLog.InsertarLog("App",this.toString(),1,"Error al modificar las cantidades del almacen devoluciondistribuidor: " + localException.getMessage());
									} 
								}
								
								if(update == 0)
								{
									theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo modificar las cantidades del almacen devolucion distribuidor.", 1);
									return;
								}
							}
						}//fin for
					}
				}//fin promocionId
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "Venta no entregada registrada en el dispositivo.", 1);
			return;
		}
	}
	
	private void InsertarVenta(float monto,float descuento,float montoFinal)
	{
	    pdInsertarVenta = new ProgressDialog(this);
    	pdInsertarVenta.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdInsertarVenta.setMessage("Insertando venta ...");
	    pdInsertarVenta.setCancelable(false);
	    pdInsertarVenta.setCanceledOnTouchOutside(false);

	    WSInsertarVenta localWSInsertarVenta = new WSInsertarVenta(monto,descuento,montoFinal);
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
		
		private float _monto;
		private float _descuento;
		private float _montoFinal;
		
		public WSInsertarVenta(float monto,float descuento,float montoFinal)
		{
			this._monto = monto;
			this._descuento = descuento;
			this._montoFinal = montoFinal;
		}
		
		boolean WSInsertarVenta ;
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
			Fecha fecha = theUtilidades.ObtenerFecha();
			ventaIdServer = 0;
      
            SoapObject localSoapObject = new SoapObject(NAMESPACE,VENTA_METHOD_NAME);
            localSoapObject.addProperty("clienteId", Integer.valueOf(clienteId));
            localSoapObject.addProperty("distribuidorId", Integer.valueOf(loginEmpleado.get_empleadoId()));
            localSoapObject.addProperty("monto", String.valueOf(_monto));
            localSoapObject.addProperty("descuento", String.valueOf(_descuento));
            localSoapObject.addProperty("montoFinal", String.valueOf(_montoFinal));
            localSoapObject.addProperty("tipoPagoId", Integer.valueOf(preventaDist.get_tipoPagoId()));
            localSoapObject.addProperty("latitud", String.valueOf(latitudDistribuidor));
            localSoapObject.addProperty("longitud", String.valueOf(longitudDistribuidor));
            localSoapObject.addProperty("dia", Integer.valueOf(fecha.get_dia()));
            localSoapObject.addProperty("mes", Integer.valueOf(fecha.get_mes()));
            localSoapObject.addProperty("anio", Integer.valueOf(fecha.get_anio()));
            localSoapObject.addProperty("preventaId", Integer.valueOf(preventaDist.get_preventaId()));
            localSoapObject.addProperty("numeroItems", Integer.valueOf(listadoVentaProductoTemp.size()));
            localSoapObject.addProperty("almacenId", Integer.valueOf(loginEmpleado.get_almacenId()));
			localSoapObject.addProperty("hora", Integer.valueOf(fecha.get_hora()));
			localSoapObject.addProperty("minuto", Integer.valueOf(fecha.get_minuto()));
			localSoapObject.addProperty("rutaId",(clienteVenta.get_rutaId()));
			localSoapObject.addProperty("diaId",clienteVenta.get_diaId());
			localSoapObject.addProperty("observacion",preventaDist.get_observacion());
			localSoapObject.addProperty("dosificacionId",preventaDist.get_dosificacionId());
			localSoapObject.addProperty("descuentoCanal",String.valueOf(descuentoCanal));
			localSoapObject.addProperty("descuentoAjuste",String.valueOf(descuentoAjuste));
			localSoapObject.addProperty("descuentoProntoPago",String.valueOf(descuentoProntoPago));
			localSoapObject.addProperty("descuentoObjetivo",String.valueOf(preventaDist.get_descuentoObjetivo()));
			localSoapObject.addProperty("formaPagoId",formaPagoId);
			localSoapObject.addProperty("codTransferencia",etCodTrans.getText().toString());
			localSoapObject.addProperty("fromApk",String.valueOf(true));
			localSoapObject.addProperty("fromShopping",String.valueOf(preventaDist.is_fromShopp()));
      
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
            		ventaIdServer = resultadoInt;
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
    	
			if (ejecutado)
			{
				if(WSInsertarVenta
						|| (resultadoString != null && resultadoString.equals("Venta Repetida"))
						|| (resultadoString != null && resultadoString.equals("Entrega Repetida")))
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Venta insertada en el servidor.", 1);
					InsertarVentaDispositivo(true);
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "La venta NO fue insertada en el servidor.", 1);
					InsertarVentaDispositivo(false);
				}
				
				ModificarEstadoEntregaClienteVenta();
				ModificarEstadoEntregaPreventaDist();
				RegistrarConfirmacionVenta();
				MostrarPantallaTipoImpresion();
				
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSInsertVenta no se ejecut correctamente,", 1);
				return;
			}    
		}
	}
	
	private void InsertarVentaDispositivo(boolean estadoSincronizacion)
	{
		boolean sincronizacion;
		long devolucionDistribuidorId = 0;
		int cantidadExistente;
		int cantidadPaqueteExistente;
		int cantidadTotalEnUnidades;
		int cantidadConsolidada=0;
		int cantidadPaqueteConsolidada=0;
		
		if(estadoSincronizacion)
		{
			sincronizacion = true;
		}
		else
		{
			sincronizacion = false;
		}
		
		//Obtenemos registros modificados o eliminados de la preventa a traves de la sincronizacionVenta
		
		ArrayList<SincronizacionVenta> listadoSincronizacionVenta = null;
		try
		{
			listadoSincronizacionVenta = new SincronizacionVentaBLL().ObtenerSincronizacionVentaPor(preventaDist.get_preventaId());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la sincronizacionVenta por preventaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la sincronizacionVenta por preventaId: " + localException.getMessage());
			} 
		}
		
		if(listadoSincronizacionVenta == null)
		{
			//Al ser null es una Venta Directa
			Fecha fecha = theUtilidades.ObtenerFecha();
			
			long l=0;
			try
			{
				l = new SincronizacionVentaBLL().InsertarSincronizacionVenta(0,preventaDist.get_preventaId(),clienteId,
												loginEmpleado.get_empleadoId(),0,0,0,0,preventaDist.get_monto(),
												preventaDist.get_descuento(),preventaDist.get_montoFinal(),
												preventaDist.get_tipoPagoId(),0,0,0,0,0,listadoVentaProductoTemp.size(),
												((MotivoNoEntrega)spnMotivoNoEntrega.getSelectedItem()).get_motivoId(),
												loginEmpleado.get_dia(),loginEmpleado.get_mes(),loginEmpleado.get_anio(),
												latitudDistribuidor,longitudDistribuidor,false,1,sincronizacion,
												fecha.get_hora(),fecha.get_minuto(),false,"","",false,0,(int)ventaIdServer,
												0,0,preventaDist.get_observacion(),0,false,0,preventaDist.get_dosificacionId(),"",
												preventaDist.get_descuentoCanal(),preventaDist.get_descuentoCanal(),0,
												preventaDist.get_descuentoProntoPago(),	preventaDist.get_prontoPagoId(), 
												preventaDist.get_descuentoObjetivo(), formaPagoId, etCodTrans.getText().toString(), preventaDist.is_fromShopp());
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacionVenta (Venta Completa): vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacionVenta (Venta Completa): " + localException.getMessage());
				} 
			}
			
			if(l<=0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la sincronizacion venta: VentaCompleta", 1);
				return;
			}
			else
			{
				//Copiamos la preventaDist a la venta
				ventaId = 0;
				try
				{
					ventaId = new VentaBLL().InsertarVenta((int)ventaIdServer,loginEmpleado.get_dia(),loginEmpleado.get_mes(),
							loginEmpleado.get_anio(),clienteId,loginEmpleado.get_empleadoId(),preventaDist.get_monto(),
							preventaDist.get_descuento(),preventaDist.get_montoFinal(),preventaDist.get_preventaId(),
							preventaDist.get_tipoPagoId(),latitudDistribuidor,longitudDistribuidor,false,sincronizacion,
							fecha.get_hora(),fecha.get_minuto(),preventaDist.get_observacion(),false,preventaDist.get_dosificacionId(),"",
							preventaDist.get_descuentoCanal(), preventaDist.get_descuentoAjuste(),preventaDist.get_prontoPagoId(),
							preventaDist.get_descuentoProntoPago(),preventaDist.get_descuentoObjetivo(), formaPagoId, 
							etCodTrans.getText().toString(), preventaDist.is_fromApk(), preventaDist.is_fromShopp());
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
				        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la venta: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al insertar la venta: " + localException.getMessage());
					} 
				}
				
				if(ventaId == 0)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar la venta",1);
					return;
				}
				else
				{
					//Copiamos la preventaProductoDist a la ventaProducto
					for(VentaProductoTemp item : listadoVentaProductoTemp)
					{
						int ventaProductoId = 0;
						try
						{
							int ventaIdInt = (int)ventaId;
							ventaProductoId = (int) new VentaProductoBLL().InsertarVentaProducto(ventaIdInt,item.get_productoId(),
									item.get_promocionId(),item.get_cantidad(),item.get_cantidadPaquete(),item.get_monto(),
									item.get_descuento(),item.get_montoFinal(),item.get_motivoId(),sincronizacion,
									item.get_costo(),item.get_costoId(),item.get_precioId(),item.get_descuentoCanal(),
									item.get_descuentoAjuste(),item.get_canalPrecioRutaId(), item.get_descuentoProntoPago());
						}
						catch(Exception localException)
						{
							if(localException.getMessage() == null || localException.getMessage().isEmpty())
							{
						        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ventaProducto: vacio");
							}
							else
							{
								myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ventaProducto: " + localException.getMessage());
							} 
						}
						
						if(ventaProductoId == 0)
						{
							theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar la venta producto.",1);
							return;
						}
					}
				}
				
				//Actualizamos la ventaId de la sincronizacionVenta
				int i=0;
				try
				{
					i = new SincronizacionVentaBLL().ModificarSincronizacionVentaVentaIdPorPreventaId(preventaDist.get_preventaId(),(int)ventaId);
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
				        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaId de la sincronizacionVenta: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaId de la sincronizacionVenta: " + localException.getMessage());
					}
				}
				
				if(i==0)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la ventaId de la sincronizacionVenta.", 1);
					return;
				}
				
				theUtilidades.MostrarMensaje(getApplicationContext(), "Venta registrada en el dispositivo.", 1);
			}
		}
		else
		{
			//Existe productos modificados/eliminados y o sin modificar
			//Insertamos la venta
			Fecha fecha = theUtilidades.ObtenerFecha();
			newVentaId=0;
			try
			{
				newVentaId = new VentaBLL().InsertarVenta((int)ventaIdServer,loginEmpleado.get_dia(),loginEmpleado.get_mes(),
														loginEmpleado.get_anio(),clienteId,loginEmpleado.get_empleadoId(),
														preventaDist.get_monto(),preventaDist.get_descuento(),
														preventaDist.get_montoFinal(),preventaDist.get_preventaId(),
														preventaDist.get_tipoPagoId(),latitudDistribuidor,longitudDistribuidor,
														false,sincronizacion,fecha.get_hora(),fecha.get_minuto(),
														preventaDist.get_observacion(),false,preventaDist.get_dosificacionId(),"",
														preventaDist.get_descuentoCanal(), preventaDist.get_descuentoAjuste(),
														preventaDist.get_prontoPagoId(),preventaDist.get_descuentoProntoPago(),
														preventaDist.get_descuentoObjetivo(), formaPagoId, etCodTrans.getText().toString(),
														preventaDist.is_fromApk(), preventaDist.is_fromShopp());
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la venta: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar la venta: " + localException.getMessage());
				} 
			}
			
			if(newVentaId == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la venta.", 1);
				return;
			}
			
			//Obtenemos el listado completo de ventaProductoTemp
			ArrayList<VentaProductoTemp> listadoVentaProductoTemp = null;
			try
			{
				listadoVentaProductoTemp =  new VentaProductoTempBLL().ObtenerListadoVentaProductoTempPorClienteYDistribuidor(
																					clienteId,loginEmpleado.get_empleadoId());
																					
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoTemp por clienteId y empleadoId: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoTemp por clienteId y empleadoI: " + localException.getMessage());
				} 
			}
			
			if(listadoVentaProductoTemp == null)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo obtener las preventas del distribuidor.", 1);
				return;
			}
			
			for(VentaProductoTemp itemTemporal : listadoVentaProductoTemp)
			{
				SincronizacionVenta localSincronizacionVentaNoConfirmadas = null;
				try
				{
					localSincronizacionVentaNoConfirmadas = new SincronizacionVentaBLL().
							ObtenerSincronizacionVentaNoConfirmadaPorVentaProductoTempRowId(itemTemporal.get_id());
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
				        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta no confirmadas por RowId: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta no confirmadas por RowId: " + localException.getMessage());
					} 
				}
				
				if(localSincronizacionVentaNoConfirmadas == null)
				{
					//El producto no sufrio ningun cambio
					//Lo insertamos a la ventaProducto
					long l = 0;
					try
					{
						l = new VentaProductoBLL().InsertarVentaProducto((int)newVentaId,
								itemTemporal.get_productoId(),
								itemTemporal.get_promocionId(),
								itemTemporal.get_cantidad(),
								itemTemporal.get_cantidadPaquete(),
								itemTemporal.get_monto(),
								itemTemporal.get_descuento(),
								itemTemporal.get_montoFinal(),
								0,sincronizacion,
								itemTemporal.get_costo(),
								itemTemporal.get_costoId(),
								itemTemporal.get_precioId(),
								itemTemporal.get_descuentoCanal(),
								itemTemporal.get_descuentoAjuste(),
								itemTemporal.get_canalPrecioRutaId(),
								itemTemporal.get_descuentoProntoPago());
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ventaProducto: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ventaProducto: " + localException.getMessage());
						} 
					}
					
					if(l==0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el producto de la venta.", 1);
						return;
					}
				}
				else
				{
					//Verificamos si el producto fue eliminado
					if(localSincronizacionVentaNoConfirmadas.get_tipoSincronizacion()==3)
					{
						//Verificamos si existe un almacen devolucionDistribuidorProducto
						DevolucionDistribuidor localDevolucionDistribuidor =null;
						try
						{
							localDevolucionDistribuidor = new DevolucionDistribuidorBLL().ObtenerDevolucionDistribuidorPorDistribuidor(
									loginEmpleado.get_empleadoId());
						}
						catch(Exception localException)
						{
							if(localException.getMessage() == null || localException.getMessage().isEmpty())
							{
						        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidor por distribuidorId: vacio");
							}
							else
							{
								myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidor por distribuidorId: " + localException.getMessage());
							} 
						}
						
						if(localDevolucionDistribuidor==null)
						{
							devolucionDistribuidorId = 0;
							try
							{
								devolucionDistribuidorId = new DevolucionDistribuidorBLL().InsertarDevolucionDistribuidor(1,
													loginEmpleado.get_empleadoId(),loginEmpleado.get_anio(),loginEmpleado.get_mes(),
													loginEmpleado.get_dia(),0,false);
							}
							catch(Exception localException)
							{
								if(localException.getMessage() == null || localException.getMessage().isEmpty())
								{
							        myLog.InsertarLog("App",this.toString(),1,"Error al insertar almacenDistribuidor: vacio");
								}
								else
								{
									myLog.InsertarLog("App",this.toString(),1,"Error al insertar almacenDistribuidor: " + localException.getMessage());
								} 
							}
							
							if(devolucionDistribuidorId == 0)
							{
								theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el almacen distribuidor.", 1);
								return;
							}
						}
						else
						{
							devolucionDistribuidorId = localDevolucionDistribuidor.get_id(); 
						}
						
						//Verificamos si se elimino un producto
						if(localSincronizacionVentaNoConfirmadas.get_productoId()>0)
						{
							//Verificamos si el producto existe en el almacenDistribuidor
							DevolucionDistribuidorProducto localDevolucionDistribuidorProducto = null;
							try
							{
								localDevolucionDistribuidorProducto = new DevolucionDistribuidorProductoBLL().
																			ObtenerDevolucionDistribuidorProductoPorProductoId(
																			(int)devolucionDistribuidorId, 
																			localSincronizacionVentaNoConfirmadas.get_productoId());
							}
							catch(Exception localException)
							{
								if(localException.getMessage() == null || localException.getMessage().isEmpty())
								{
							        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidor por productoId: vacio");
								}
								else
								{
									myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidor por productoId: " + localException.getMessage());
								} 
							}
							
							if(localDevolucionDistribuidorProducto == null)
							{
								//Como el producto no existe lo insertamos directamente en unidades y paquetes
								long devDisProId=0;
								try
								{
									devDisProId = new DevolucionDistribuidorProductoBLL().
											InsertarDevolucionDistribuidorProducto((int)devolucionDistribuidorId,
													localSincronizacionVentaNoConfirmadas.get_productoId(),
													localSincronizacionVentaNoConfirmadas.get_cantidad(),
													localSincronizacionVentaNoConfirmadas.get_cantidadPaquete(),false);
								}
								catch(Exception localException)
								{
									if(localException.getMessage() == null || localException.getMessage().isEmpty())
									{
								        myLog.InsertarLog("App",this.toString(),1,"Error al insertar el producto en el almacen devolucionDistribuidor: vacio");
									}
									else
									{
										myLog.InsertarLog("App",this.toString(),1,"Error al insertar el producto en el almacen devolucionDistribuidor: " + localException.getMessage());
									} 
								}
								
								if(devDisProId == 0)
								{
									theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el producto en el almacen devolucion distribuidor.", 1);
									return;
								}
							}
							else
							{
								//Como el producto existe debemos consolidarlo y modificar las cantidades resultantes
								Producto localProducto = null;
								try
								{
									localProducto = new ProductoBLL().ObtenerProductoPor(
																	localSincronizacionVentaNoConfirmadas.get_productoId());
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
								
								if(localProducto == null)
								{
									theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del producto.", 1);
									return;
								}
								
								cantidadExistente = localDevolucionDistribuidorProducto.get_cantidad() + 
													localSincronizacionVentaNoConfirmadas.get_cantidad();
								
								cantidadPaqueteExistente = localDevolucionDistribuidorProducto.get_cantidadPaquete() + 
															localSincronizacionVentaNoConfirmadas.get_cantidadPaquete();
								
								cantidadTotalEnUnidades = cantidadPaqueteExistente * localProducto.get_conversion() + 
															cantidadExistente;
								
								cantidadPaqueteConsolidada =  cantidadTotalEnUnidades / localProducto.get_conversion();
								cantidadConsolidada = cantidadTotalEnUnidades % localProducto.get_conversion();
								
								long update =0;
								try
								{
									update = new DevolucionDistribuidorProductoBLL().ModificarCantidadesDevolucionDistribuidorProducto(
														(int)devolucionDistribuidorId, 
														localSincronizacionVentaNoConfirmadas.get_productoId(), 
														cantidadConsolidada,cantidadPaqueteConsolidada);
								}
								catch(Exception localException)
								{
									if(localException.getMessage() == null || localException.getMessage().isEmpty())
									{
								        myLog.InsertarLog("App",this.toString(),1,"Error al modificar las cantidades del almacen devoluciondistribuidor: vacio");
									}
									else
									{
										myLog.InsertarLog("App",this.toString(),1,"Error al modificar las cantidades del almacen devoluciondistribuidor: " + localException.getMessage());
									} 
								}
								
								if(update == 0)
								{
									theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo modificar las cantidades del almacen devolucion distribuidor.", 1);
									return;
								}
							}
							
							ModificarSincronizacionVentaVentaIdPorProducto(clienteId,preventaDist.get_preventaId(),localSincronizacionVentaNoConfirmadas.get_productoId(),(int)ventaIdServer);
							
						}//fin productoId
						
						//Verificamos si se elimino una promocion
						if(localSincronizacionVentaNoConfirmadas.get_promocionId()>0)
						{
							//Obtenemos los productos que componen la promocion
							ArrayList<PromocionProducto> listadoPromocionProducto = null;
							try
							{
								listadoPromocionProducto = new PromocionProductoBLL().ObtenerPromocionesProductoPorPromocionIdPrecioListaId(
																	localSincronizacionVentaNoConfirmadas.get_promocionId(),
																	clienteVenta.get_precioListaId());
							}
							catch(Exception localException)
							{
								if(localException.getMessage() == null || localException.getMessage().isEmpty())
								{
							        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la promocion por promocionId y precioListaId: vacio");
								}
								else
								{
									myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la promocion por promocionId y precioListaId: " + localException.getMessage());
								} 
							}
							
							if(listadoPromocionProducto==null)
							{
								theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los productos de la promocion por promocionId", 1);
								return;
							}
							else
							{
								//Recorremos los productos que componen la promocion para ingresarlos al almacen devolucionDistribuidor
								for(PromocionProducto itemPromocion : listadoPromocionProducto)
								{
									//Verificamos que el producto existe en el almacen devolucionDistribuidor
									DevolucionDistribuidorProducto localDevolucionDistribuidorProducto = null;
									try
									{
										localDevolucionDistribuidorProducto = new DevolucionDistribuidorProductoBLL().
																					ObtenerDevolucionDistribuidorProductoPorProductoId(
																					(int)devolucionDistribuidorId, itemPromocion.get_productoId());
									}
									catch(Exception localException)
									{
										if(localException.getMessage() == null || localException.getMessage().isEmpty())
										{
									        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidor por productoId: vacio");
										}
										else
										{
											myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidor por productoId: " + localException.getMessage());
										} 
									}
									
									if(localDevolucionDistribuidorProducto == null)
									{
										//Como el producto no existe lo insertamos directamente en unidades y paquetes 
										//multiplicados por la cantidad solicitada
										long devDisProId=0;
										try
										{
											devDisProId = new DevolucionDistribuidorProductoBLL().
													InsertarDevolucionDistribuidorProducto((int)devolucionDistribuidorId, 
													itemPromocion.get_productoId(),
													itemPromocion.get_cantidad()*localSincronizacionVentaNoConfirmadas.get_cantidad(),
													itemPromocion.get_cantidadPaquete()*localSincronizacionVentaNoConfirmadas.get_cantidad(),false);
										}
										catch(Exception localException)
										{
											if(localException.getMessage() == null || localException.getMessage().isEmpty())
											{
										        myLog.InsertarLog("App",this.toString(),1,"Error al insertar el producto en el almacen devolucionDistribuidor: vacio");
											}
											else
											{
												myLog.InsertarLog("App",this.toString(),1,"Error al insertar el producto en el almacen devolucionDistribuidor: " + localException.getMessage());
											} 
										}
										
										if(devDisProId == 0)
										{
											theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el producto en el almacen devolucion distribuidor.", 1);
											return;
										}
									}
									else
									{
										//Como el producto existe debemos consolidarlo y modificar las cantidades resultantes
										Producto localProducto = null;
										try
										{
											localProducto = new ProductoBLL().ObtenerProductoPor(itemPromocion.get_productoId());
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
										
										if(localProducto == null)
										{
											theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del producto.", 1);
											return;
										}
										
										cantidadExistente = localDevolucionDistribuidorProducto.get_cantidad() + 
														itemPromocion.get_cantidad()*localSincronizacionVentaNoConfirmadas.get_cantidad();
										cantidadPaqueteExistente = localDevolucionDistribuidorProducto.get_cantidadPaquete() + 
														itemPromocion.get_cantidadPaquete()*localSincronizacionVentaNoConfirmadas.get_cantidad();
										
										cantidadTotalEnUnidades = cantidadPaqueteExistente * localProducto.get_conversion() + cantidadExistente;
										
										cantidadPaqueteConsolidada =  cantidadTotalEnUnidades / localProducto.get_conversion();
										cantidadConsolidada = cantidadTotalEnUnidades % localProducto.get_conversion();
										
										long update =0;
										try
										{
											update = new DevolucionDistribuidorProductoBLL().ModificarCantidadesDevolucionDistribuidorProducto(
																(int)devolucionDistribuidorId, itemPromocion.get_productoId(),
																cantidadConsolidada,cantidadPaqueteConsolidada);
										}
										catch(Exception localException)
										{
											if(localException.getMessage() == null || localException.getMessage().isEmpty())
											{
										        myLog.InsertarLog("App",this.toString(),1,"Error al modificar las cantidades del almacen devoluciondistribuidor: vacio");
											}
											else
											{
												myLog.InsertarLog("App",this.toString(),1,"Error al modificar las cantidades del almacen devoluciondistribuidor: " + localException.getMessage());
											} 
										}
										
										if(update == 0)
										{
											theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo modificar las cantidades del almacen devolucion distribuidor.", 1);
											return;
										}
									}
								}//fin for promociones
							}
							
							ModificarSincronizacionVentaVentaIdPorPromocion(clienteId,preventaDist.get_preventaId(), localSincronizacionVentaNoConfirmadas.get_promocionId(),(int)ventaIdServer);
							
						}//fin promocionId
						
						//Actualizamos la ventaId de la sincronizacionVenta
						long l = 0;
						try
						{
							l = new SincronizacionVentaBLL().ModificarSincronizacionVentaVentaIdPorPreventaId(preventaDist.get_preventaId(),(int)newVentaId);
						}
						catch(Exception localException)
						{
							if(localException.getMessage() == null || localException.getMessage().isEmpty())
							{
						        myLog.InsertarLog("App",this.toString(),1,"Error al actualizar la ventaId por preventaId: vacio");
							}
							else
							{
								myLog.InsertarLog("App",this.toString(),1,"Error al actualizar la ventaId por preventaId: " + localException.getMessage());
							}
						}
						
						if(l==0)
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la ventaId por preventaId.", 1);
							return;
						}
					}
					//fin tipoSincronizacion==3
					
					//Verificamos si el producto fue Modificado
					else if(localSincronizacionVentaNoConfirmadas.get_tipoSincronizacion()==4)
					{
						int cantidadResultante;
						int cantidadPaqueteResultante;
						
						//Verificamos si existe un almacen devolucionDistribuidorProducto
						DevolucionDistribuidor localDevolucionDistribuidor =null;
						try
						{
							localDevolucionDistribuidor = new DevolucionDistribuidorBLL().ObtenerDevolucionDistribuidorPorDistribuidor(
									loginEmpleado.get_empleadoId());
						}
						catch(Exception localException)
						{
							if(localException.getMessage() == null || localException.getMessage().isEmpty())
							{
						        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidor por distribuidorId: vacio");
							}
							else
							{
								myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidor por distribuidorId: " + localException.getMessage());
							} 
						}
						
						if(localDevolucionDistribuidor==null)
						{
							devolucionDistribuidorId = 0;
							try
							{
								devolucionDistribuidorId = new DevolucionDistribuidorBLL().InsertarDevolucionDistribuidor(1,
													loginEmpleado.get_empleadoId(),loginEmpleado.get_anio(),loginEmpleado.get_mes(),
													loginEmpleado.get_dia(),0,false);
							}
							catch(Exception localException)
							{
								if(localException.getMessage() == null || localException.getMessage().isEmpty())
								{
							        myLog.InsertarLog("App",this.toString(),1,"Error al insertar almacenDistribuidor: vacio");
								}
								else
								{
									myLog.InsertarLog("App",this.toString(),1,"Error al insertar almacenDistribuidor: " + localException.getMessage());
								} 
							}
							
							if(devolucionDistribuidorId == 0)
							{
								theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el almacen distribuidor.", 1);
								return;
							}
						}
						else
						{
							devolucionDistribuidorId = localDevolucionDistribuidor.get_id(); 
						}
						
						//Verificamos si se modifico un producto
						if(localSincronizacionVentaNoConfirmadas.get_productoId()>0)
						{						
							//Verificamos si el producto existe en el almacenDistribuidor
							DevolucionDistribuidorProducto localDevolucionDistribuidorProducto = null;
							try
							{
								localDevolucionDistribuidorProducto = new DevolucionDistribuidorProductoBLL().
																			ObtenerDevolucionDistribuidorProductoPorProductoId(
																			(int)devolucionDistribuidorId, 
																			localSincronizacionVentaNoConfirmadas.get_productoId());
							}
							catch(Exception localException)
							{
								if(localException.getMessage() == null || localException.getMessage().isEmpty())
								{
							        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidor por productoId: vacio");
								}
								else
								{
									myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidor por productoId: " + localException.getMessage());
								} 
							}
							
							if(localDevolucionDistribuidorProducto == null)
							{
								//Como el producto no existe insertamos la diferencia consolidada en unidades y paquetes								
								cantidadResultante = localSincronizacionVentaNoConfirmadas.get_cantidad() - 
													localSincronizacionVentaNoConfirmadas.get_cantidadNueva();
								
								cantidadPaqueteResultante = localSincronizacionVentaNoConfirmadas.get_cantidadPaquete() - 
															localSincronizacionVentaNoConfirmadas.get_cantidadPaqueteNueva();
								
								//Consolidamos las cantidades, para evitar negativos al convertir de pacas a unidades
								if(cantidadResultante < 0)
								{
									Producto localProducto = null;
									try
									{
										localProducto = new ProductoBLL().ObtenerProductoPor(
																	localSincronizacionVentaNoConfirmadas.get_productoId());
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
									
									if(localProducto == null)
									{
										theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del producto.", 1);
										return;
									}
									
									cantidadTotalEnUnidades = cantidadPaqueteResultante * localProducto.get_conversion() + cantidadResultante;
									
									cantidadPaqueteResultante = cantidadTotalEnUnidades / localProducto.get_conversion();
									cantidadResultante = cantidadTotalEnUnidades % localProducto.get_conversion();
								}
								
								long devDisProId=0;
								try
								{
									devDisProId = new DevolucionDistribuidorProductoBLL().
											InsertarDevolucionDistribuidorProducto((int)devolucionDistribuidorId, 
											localSincronizacionVentaNoConfirmadas.get_productoId(),
											cantidadResultante,cantidadPaqueteResultante,false);
								}
								catch(Exception localException)
								{
									if(localException.getMessage() == null || localException.getMessage().isEmpty())
									{
								        myLog.InsertarLog("App",this.toString(),1,"Error al insertar el producto en el almacen devolucionDistribuidor: vacio");
									}
									else
									{
										myLog.InsertarLog("App",this.toString(),1,"Error al insertar el producto en el almacen devolucionDistribuidor: " + localException.getMessage());
									} 
								}
								
								if(devDisProId == 0)
								{
									theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el producto en el almacen devolucion distribuidor.", 1);
									return;
								}
							}
							else
							{
								//Como el producto existe debemos consolidarlo y modificar las cantidades resultantes
								Producto localProducto = null;
								try
								{
									localProducto = new ProductoBLL().ObtenerProductoPor(
																localSincronizacionVentaNoConfirmadas.get_productoId());
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
								
								if(localProducto == null)
								{
									theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del producto.", 1);
									return;
								}
								cantidadResultante = localSincronizacionVentaNoConfirmadas.get_cantidad() - 
													localSincronizacionVentaNoConfirmadas.get_cantidadNueva();
								
								cantidadPaqueteResultante = localSincronizacionVentaNoConfirmadas.get_cantidadPaquete() - 
													localSincronizacionVentaNoConfirmadas.get_cantidadPaqueteNueva();
								
								cantidadExistente = localDevolucionDistribuidorProducto.get_cantidad() + cantidadResultante;
								cantidadPaqueteExistente = localDevolucionDistribuidorProducto.get_cantidadPaquete() + 
																					cantidadPaqueteResultante;
								
								cantidadTotalEnUnidades = cantidadPaqueteExistente * localProducto.get_conversion() + cantidadExistente;
								
								cantidadPaqueteConsolidada =  cantidadTotalEnUnidades / localProducto.get_conversion();
								cantidadConsolidada = cantidadTotalEnUnidades % localProducto.get_conversion();
								
								long update =0;
								try
								{
									update = new DevolucionDistribuidorProductoBLL().ModificarCantidadesDevolucionDistribuidorProducto(
														(int)devolucionDistribuidorId, 
														localSincronizacionVentaNoConfirmadas.get_productoId(), 
														cantidadConsolidada,cantidadPaqueteConsolidada);
								}
								catch(Exception localException)
								{
									if(localException.getMessage() == null || localException.getMessage().isEmpty())
									{
								        myLog.InsertarLog("App",this.toString(),1,"Error al modificar las cantidades del almacen devoluciondistribuidor: vacio");
									}
									else
									{
										myLog.InsertarLog("App",this.toString(),1,"Error al modificar las cantidades del almacen devoluciondistribuidor: " + localException.getMessage());
									} 
								}
								
								if(update == 0)
								{
									theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo modificar las cantidades del almacen devolucion distribuidor.", 1);
									return;
								}
							}
							
							ModificarSincronizacionVentaVentaIdPorProducto(clienteId, preventaDist.get_preventaId(), localSincronizacionVentaNoConfirmadas.get_productoId(), (int)ventaIdServer);
							
						}//fin productoId
						
						//Verificamos si es una promocion modificada
						if(localSincronizacionVentaNoConfirmadas.get_promocionId()>0)
						{
							//Obtenemos los productos que componen la promocion
							ArrayList<PromocionProducto> listadoPromocionProducto = null;
							try
							{
								listadoPromocionProducto = new PromocionProductoBLL().ObtenerPromocionesProductoPorPromocionIdPrecioListaId(
															localSincronizacionVentaNoConfirmadas.get_promocionId(),clienteVenta.get_precioListaId());
							}
							catch(Exception localException)
							{
								if(localException.getMessage() == null || localException.getMessage().isEmpty())
								{
							        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la promocion por promocionId y precioListaId: vacio");
								}
								else
								{
									myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la promocion por promocionId y precioListaId: " + localException.getMessage());
								} 
							}
							
							if(listadoPromocionProducto==null)
							{
								theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los productos de la promocion por promocionId", 1);
								return;
							}
							else
							{
								//Recorremos los productos que componen la promocion para ingresarlos al almacen devolucionDistribuidor
								for(PromocionProducto itemPromocion : listadoPromocionProducto)
								{
									//Verificamos que el producto existe en el almacen devolucionDistribuidor
									DevolucionDistribuidorProducto localDevolucionDistribuidorProducto = null;
									try
									{
										localDevolucionDistribuidorProducto = new DevolucionDistribuidorProductoBLL().
																					ObtenerDevolucionDistribuidorProductoPorProductoId(
																					(int)devolucionDistribuidorId, itemPromocion.get_productoId());
									}
									catch(Exception localException)
									{
										if(localException.getMessage() == null || localException.getMessage().isEmpty())
										{
									        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidor por productoId: vacio");
										}
										else
										{
											myLog.InsertarLog("App",this.toString(),1,"Error al obtener la devolucionDistribuidor por productoId: " + localException.getMessage());
										} 
									}
									
									if(localDevolucionDistribuidorProducto == null)
									{
										//Como el producto no existe lo insertamos directamente en unidades 
										//multiplicados por la cantidad resultante
										cantidadResultante = localSincronizacionVentaNoConfirmadas.get_cantidad() - 
														localSincronizacionVentaNoConfirmadas.get_cantidadNueva();
										
										cantidadPaqueteResultante = localSincronizacionVentaNoConfirmadas.get_cantidadPaquete() - 
														localSincronizacionVentaNoConfirmadas.get_cantidadPaqueteNueva();
										
										long devDisProId=0;
										try
										{
											devDisProId = new DevolucionDistribuidorProductoBLL().
													InsertarDevolucionDistribuidorProducto((int)devolucionDistribuidorId, itemPromocion.get_productoId(),
													itemPromocion.get_cantidad()*cantidadResultante,
													itemPromocion.get_cantidadPaquete()*cantidadResultante,false);
										}
										catch(Exception localException)
										{
											if(localException.getMessage() == null || localException.getMessage().isEmpty())
											{
										        myLog.InsertarLog("App",this.toString(),1,"Error al insertar el producto en el almacen devolucionDistribuidor: vacio");
											}
											else
											{
												myLog.InsertarLog("App",this.toString(),1,"Error al insertar el producto en el almacen devolucionDistribuidor: " + localException.getMessage());
											} 
										}
										
										if(devDisProId == 0)
										{
											theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el producto en el almacen devolucion distribuidor.", 1);
											return;
										}
									}
									else
									{
										//Como el producto existe debemos consolidarlo y modificar las cantidades resultantes
										Producto localProducto = null;
										try
										{
											localProducto = new ProductoBLL().ObtenerProductoPor(
																			itemPromocion.get_productoId());
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
										
										if(localProducto == null)
										{
											theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del producto.", 1);
											return;
										}
										
										cantidadResultante = localSincronizacionVentaNoConfirmadas.get_cantidad() - 
															localSincronizacionVentaNoConfirmadas.get_cantidadNueva();
										
										cantidadPaqueteResultante = localSincronizacionVentaNoConfirmadas.get_cantidadPaquete() - 
															localSincronizacionVentaNoConfirmadas.get_cantidadPaqueteNueva();
										
										cantidadExistente = localDevolucionDistribuidorProducto.get_cantidad() + 
																	itemPromocion.get_cantidad()*cantidadResultante;
										cantidadPaqueteExistente = localDevolucionDistribuidorProducto.get_cantidadPaquete() + 
																	itemPromocion.get_cantidadPaquete()*cantidadResultante;
										
										cantidadTotalEnUnidades = cantidadPaqueteExistente * localProducto.get_conversion() + cantidadExistente;
										
										cantidadPaqueteConsolidada =  cantidadTotalEnUnidades / localProducto.get_conversion();
										cantidadConsolidada = cantidadTotalEnUnidades % localProducto.get_conversion();
										
										long update =0;
										try
										{
											update = new DevolucionDistribuidorProductoBLL().ModificarCantidadesDevolucionDistribuidorProducto(
																(int)devolucionDistribuidorId, 
																itemPromocion.get_productoId(), 
																cantidadConsolidada,cantidadPaqueteConsolidada);
										}
										catch(Exception localException)
										{
											if(localException.getMessage() == null || localException.getMessage().isEmpty())
											{
										        myLog.InsertarLog("App",this.toString(),1,"Error al modificar las cantidades del almacen devoluciondistribuidor: vacio");
											}
											else
											{
												myLog.InsertarLog("App",this.toString(),1,"Error al modificar las cantidades del almacen devoluciondistribuidor: " + localException.getMessage());
											} 
										}
										
										if(update == 0)
										{
											theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo modificar las cantidades del almacen devolucion distribuidor.", 1);
											return;
										}
									}
								
								}
								//fin for promocion
							}
							
							ModificarSincronizacionVentaVentaIdPorPromocion(clienteId,preventaDist.get_preventaId(), localSincronizacionVentaNoConfirmadas.get_promocionId(),(int)ventaIdServer);
							
						}//fin promocionId
						
						//Actualizamos la ventaId de la sincronizacionVenta
						int i=0;
						try
						{
							i = new SincronizacionVentaBLL().ModificarSincronizacionVentaVentaIdPorPreventaId(preventaDist.get_preventaId(),(int)newVentaId);
						}
						catch(Exception localException)
						{
							if(localException.getMessage() == null || localException.getMessage().isEmpty())
							{
						        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaId de la sincronizacionVenta: vacio");
							}
							else
							{
								myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaId de la sincronizacionVenta: " + localException.getMessage());
							}
						}
						
						if(i==0)
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la ventaId de la sincronizacionVenta.", 1);
							return;
						}

						//Calculamos las nuevas cantidades para insertar la venta producto
						//int cantidad = itemTemporal.get_cantidad() - localSincronizacionVentaNoConfirmadas.get_cantidadNueva();
						//int cantidadPaquete = itemTemporal.get_cantidadPaquete() - localSincronizacionVentaNoConfirmadas.get_cantidadPaqueteNueva();
						
						//Consolidamos la cantidades cuando la cantidad es negativa, producto de modificar de pacas a unidades
						//if(cantidad < 0)
						//{
						//	Producto localProducto = null;
						//	try
						//	{
						//		localProducto = new ProductoBLL().ObtenerProductoPor(
						//									localSincronizacionVentaNoConfirmadas.get_productoId());
						//	}
						//	catch(Exception localException)
						//	{
						//		if(localException.getMessage() == null || localException.getMessage().isEmpty())
						//		{
						//	        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del producto por productoId: vacio");
						//		}
						//		else
						//		{
						//			myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del producto por productoId: " + localException.getMessage());
						//		} 
						//	}
							
						//	if(localProducto == null)
						//	{
						//		theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del producto.", 1);
						//		return;
						//	}
							
						//	cantidadTotalEnUnidades = cantidadPaquete * localProducto.get_conversion() + cantidad;
							
						//	cantidadPaquete = cantidadTotalEnUnidades / localProducto.get_conversion();
						//	cantidad = cantidadTotalEnUnidades % localProducto.get_conversion();
						//}
						
						long l = 0;
						try
						{
							l = new VentaProductoBLL().InsertarVentaProducto((int)newVentaId,itemTemporal.get_productoId(),
									itemTemporal.get_promocionId(),
									localSincronizacionVentaNoConfirmadas.get_cantidadNueva(),
									localSincronizacionVentaNoConfirmadas.get_cantidadPaqueteNueva(),
									localSincronizacionVentaNoConfirmadas.get_montoNuevo(),
									localSincronizacionVentaNoConfirmadas.get_descuentoNuevo(),
									localSincronizacionVentaNoConfirmadas.get_montoFinalNuevo(),
									localSincronizacionVentaNoConfirmadas.get_motivoId(),sincronizacion,
									localSincronizacionVentaNoConfirmadas.get_costo(),
									localSincronizacionVentaNoConfirmadas.get_costoId(),
									localSincronizacionVentaNoConfirmadas.get_precioId(),
									localSincronizacionVentaNoConfirmadas.get_descuentoCanal(),
									localSincronizacionVentaNoConfirmadas.get_descuentoAjuste(),
									localSincronizacionVentaNoConfirmadas.get_canalPrecioRutaId(),
									localSincronizacionVentaNoConfirmadas.get_descuentoProntoPago());
						}
						catch(Exception localException)
						{
							if(localException.getMessage() == null || localException.getMessage().isEmpty())
							{
						        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ventaProducto: vacio");
							}
							else
							{
								myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ventaProductor: " + localException.getMessage());
							} 
						}
						
						if(l == 0)
						{
							theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el producto de la venta.", 1);
							return;
						}
					}//Fin tipoSincronizacion = 4
					
				}//fin for preventaProductoDist
			}
			
			//Calculamos los valores finales de la venta
			//Obtenemos las ventasProducto
			ArrayList<VentaProducto> listadoVentaProducto = null;
			try
			{
				listadoVentaProducto = new VentaProductoBLL().ObtenerVentasProductoPor((int)newVentaId);
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProducto por ventaId: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProducto por ventaId: " + localException.getMessage());
				}
			}
			
			if(listadoVentaProducto == null)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo obtener los productos de la venta.", 1);
				return;
			}
			
			//Calculamos las montos de la venta
			float monto=0;
			float descuento=0;
			float montoFinal=0;
			
			for(VentaProducto venta : listadoVentaProducto)
			{
				monto += venta.get_monto();
				descuento += venta.get_descuento();
				montoFinal += venta.get_montoFinal();
			}
			
			//Actualizamos la venta
			long update =0;
			try
			{
				update = new VentaBLL().ModificarVentaMontosYDescuentos((int)newVentaId, monto, descuento, montoFinal);
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al modificar los montos finales de la venta: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al modificar los montos finales de la venta: " + localException.getMessage());
				}
			}
			
			if(update == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se modificar los montos finales de la venta.", 1);
				return;
			}			
			
			theUtilidades.MostrarMensaje(getApplicationContext(), "Venta registrada en el dispositivo.", 1);
			return;
		}//fin else venta completa, es un registro eliminado/modificado
	}
	
	private void ModificarSincronizacionVentaVentaIdPorProducto(int clienteId,int preventaId,int productoId,int ventaIdServer)
	{
		long updateSyncVenta=0;
		try
		{
			updateSyncVenta = new SincronizacionVentaBLL().ModificarSincronizacionVentaVentaIdServerPorProducto(clienteId, preventaId, productoId, ventaIdServer);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaId de la sincronizacionVenta por clienteId, preventaId y productoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaId de la sincronizacionVenta por clienteId, preventaId y productoId: " + localException.getMessage());
			}
		}
		
		if(updateSyncVenta == 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo modificar la ventaId de la sincronizacion de la venta.", 1);
		}
	}
	
	private void ModificarSincronizacionVentaVentaIdPorPromocion(int clienteId,int preventaId,int promocionId,int ventaIdServer)
	{
		long updateSyncVenta=0;
		try
		{
			updateSyncVenta = new SincronizacionVentaBLL().ModificarSincronizacionVentaVentaIdServerPorPromocion(clienteId, preventaId, promocionId, ventaIdServer);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaId de la sincronizacionVenta por clienteId, preventaId y promocionId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaId de la sincronizacionVenta por clienteId, preventaId y promocionId: " + localException.getMessage());
			}
		}
		
		if(updateSyncVenta == 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo modificar la ventaId de la sincronizacion de la venta.", 1);
		}
	}
	
	private void ModificarEstadoEntregaClienteVenta()
	{
		long l=0;
	    try
	    {
	    	l= new ClienteVentaBLL().ModificarClienteVentaEstadoAtendido(clienteId, true);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estadoAtendido del clienteVenta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estadoAtendido del clienteVenta: " + localException.getMessage());
	    	} 
	    }
	    
	    if(l==0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo modificar el estado atendido del cliente venta.", 1);
	    	return;
	    }
	}
	
	private void ModificarEstadoEntregaPreventaDist()
	{
		long l=0;
	    try
	    {
	    	l= new PreventaDistBLL().ModificarPreventaDistEstadoEntrega(preventaDist.get_preventaId(), 1);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estadoEntrega de la preventaDist: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estadoEntrega de la preventaDist: " + localException.getMessage());
	    	} 
	    }
	    
	    if(l==0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo modificar el estado de entrega de la preventa del distribuidor.", 1);
	    	return;
	    }
	}
		
	private void MostrarPantallaVentaProducto(int productoId,int promocionId,int ventaProductoTempRowId)
	{
		Intent localIntent = new Intent(this,Distribuidorventaproducto.class);
		localIntent.putExtra("PreventaId", preventaDist.get_preventaId());
		localIntent.putExtra("ProductoId", productoId);
		localIntent.putExtra("PromocionId", promocionId);
		localIntent.putExtra("VentaProductoTempRowId",ventaProductoTempRowId);
		startActivity(localIntent);
	}
	
	private void RegistrarConfirmacionVenta()
	{
		ArrayList <SincronizacionVenta> listadoSincronizacionVenta = null;
		try
		{
			listadoSincronizacionVenta = new SincronizacionVentaBLL().ObtenerSincronizacionVentaPor(preventaDist.get_preventaId());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVentas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVentas: " + localException.getMessage());
			} 
		}
		
		if(listadoSincronizacionVenta == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la sincronizacion de la venta.", 1);
			return;
		}
		else
		{
			for(SincronizacionVenta item : listadoSincronizacionVenta)
			{
				int registrado = 0;
				try
				{
					registrado = new SincronizacionVentaBLL().ModificarSincronizacionVentaConfirmacionYSincronizacion(
										preventaDist.get_preventaId(),item.get_tipoSincronizacion(),true,
										item.is_estadoSincronizacion());
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
				        myLog.InsertarLog("App",this.toString(),1,"Error al registrar la confirmacion de la venta: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al registrar la confirmacion de la venta): " + localException.getMessage());
					} 
				}
				
				if(registrado==0)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo registrar la confirmacion de la venta.",1);
					return;
				}
			}
		}
	}
	
	private void InsertarObservacionPOP(int preventaPOPId,int productoPOPId,String observacion)
	{
		pdObservacionPOP = new ProgressDialog(this);
		pdObservacionPOP.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdObservacionPOP.setMessage("Enviando Observacion ...");
		pdObservacionPOP.setCancelable(false);
		pdObservacionPOP.setCanceledOnTouchOutside(false);

	    WSInsertProductoPOPObservacion localWSInsertProductoPOPObservacion = new WSInsertProductoPOPObservacion(preventaPOPId,productoPOPId,observacion);
	    try
	    {
			localWSInsertProductoPOPObservacion.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPorductoPOPObservacion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertProductoPOPObservacion: " + localException.getMessage());
	    	} 
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSInsertProductoPOPObservacion no se ejecuto correctamente.", 1);
	    }
	}

	private class WSInsertProductoPOPObservacion extends AsyncTask<Void, Integer, Boolean>
	 {
	    String OBSERVACIONPOP_METHOD_NAME = "InsertProductoPopObservacion";
	    String OBSERVACIONPOP_SOAP_ACTION = NAMESPACE + OBSERVACIONPOP_METHOD_NAME;
	    
	    private int _preventaPOPId;
	    private int _productoPOPId;
	    private String _observacion;
	    
	    public WSInsertProductoPOPObservacion(int preventaPOPId,int productoPOPId,String observacion)
	    {
	    	this._preventaPOPId = preventaPOPId;
	    	this._productoPOPId = productoPOPId;
	    	this._observacion = observacion;
	    }
	    
	    boolean WSInsertProductoPOP;
	    int resultadoInt;
	    String resultadoString;
	    SoapObject soapResultado;
	    
	    protected void onPreExecute()
	    {
	    	pdObservacionPOP.show();
	    }
	    
	    protected Boolean doInBackground(Void... paramVarArgs)
	    {
	    	WSInsertProductoPOP = false;
	    	
	    	SoapObject localSoapObject = new SoapObject(NAMESPACE,OBSERVACIONPOP_METHOD_NAME);
	    	localSoapObject.addProperty("preVentaId", Integer.valueOf(_preventaPOPId));
	    	localSoapObject.addProperty("productoId", Integer.valueOf(_productoPOPId));
	    	localSoapObject.addProperty("observacion", String.valueOf(_observacion));
	    	
	    	SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
	    	localSoapSerializationEnvelope.dotNet = true;
	    	localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
	    	HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
	    		
	    	try
	    	{
	    		localHttpTransportSE.call(OBSERVACIONPOP_SOAP_ACTION, localSoapSerializationEnvelope);
	    		
	    		soapResultado = ((SoapObject)localSoapSerializationEnvelope.getResponse());
	    		
	    		if(soapResultado!=null)
	    		{
	    			resultadoInt = Integer.parseInt(soapResultado.getPropertyAsString("Id"));
		    	    resultadoString = String.valueOf(soapResultado.getPropertyAsString("Resultado"));	    			
	    		}
	    	        
	    	    if (resultadoString.equals("OK") && resultadoInt > 0) 
	    	    {
	    	      	WSInsertProductoPOP = true;
	    	    }
	    	    return true;	        
			}
	    	catch (Exception localException)
	    	{
   			WSInsertProductoPOP = false;
   			
   			if(localException.getMessage() == null || localException.getMessage().isEmpty())
   			{
   		        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreventaProductoPop: vacio");
   			}
   			else
   			{
   				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertPreVentaProductoPOP: " + localException.getMessage());
   			} 
   			return true;
   		}
   	}
	    
	    protected void onPostExecute(Boolean ejecutado)
	    {
	    	if(pdObservacionPOP.isShowing())
	    	{
				pdObservacionPOP.dismiss();
	    	}
		    	
	    	if(ejecutado)
	    	{
	    		if(WSInsertProductoPOP) 
	    		{	
	    			theUtilidades.MostrarMensaje(getApplicationContext(), "Observacion Insertada.", 1);
	    		}
	    		else
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(), resultadoString, 1);
	    		}
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSDeletePreventaProductoPOP.", 1);
	    	}
	    }
	}

	private void EliminarrObservacionPOP(int preventaPOPId,int productoPOPId)
	{
		pdEliminarObservacionPOP = new ProgressDialog(this);
		pdEliminarObservacionPOP.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEliminarObservacionPOP.setMessage("Eliminando observacion ...");
		pdEliminarObservacionPOP.setCancelable(false);
		pdEliminarObservacionPOP.setCanceledOnTouchOutside(false);

	    WSDeleteProductoPOPObservacion localWSDeleteProductoPOPObservacion = new WSDeleteProductoPOPObservacion(preventaPOPId,productoPOPId);
	    try
	    {
			localWSDeleteProductoPOPObservacion.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSDeletePorductoPOPObservacion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSDeleteProductoPOPObservacion: " + localException.getMessage());
	    	} 
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSDeleteProductoPOPObservacion no se ejecuto correctamente.", 1);
	    }
	}

	private class WSDeleteProductoPOPObservacion extends AsyncTask<Void, Integer, Boolean>
	 {
	    String DELETEOBSERVACIONPOP_METHOD_NAME = "DeleteProductoPopObservacion";
	    String DELETEOBSERVACIONPOP_SOAP_ACTION = NAMESPACE + DELETEOBSERVACIONPOP_METHOD_NAME;
	    
	    private int _preventaPOPId;
	    private int _productoPOPId;
	    
	    public WSDeleteProductoPOPObservacion(int preventaPOPId,int productoPOPId)
	    {
	    	this._preventaPOPId = preventaPOPId;
	    	this._productoPOPId = productoPOPId;
	    }
	    
	    boolean WSDeleteProductoPOP;
	    int resultadoInt;
	    String resultadoString;
	    SoapObject soapResultado;
	    
	    protected void onPreExecute()
	    {
	    	pdEliminarObservacionPOP.show();
	    }
	    
	    protected Boolean doInBackground(Void... paramVarArgs)
	    {
	    	WSDeleteProductoPOP = false;
	    	
	    	SoapObject localSoapObject = new SoapObject(NAMESPACE,DELETEOBSERVACIONPOP_METHOD_NAME);
	    	localSoapObject.addProperty("preVentaId", Integer.valueOf(_preventaPOPId));
	    	localSoapObject.addProperty("productoId", String.valueOf(_productoPOPId));
	    	
	    	SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
	    	localSoapSerializationEnvelope.dotNet = true;
	    	localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
	    	HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
	    		
	    	try
	    	{
	    		localHttpTransportSE.call(DELETEOBSERVACIONPOP_SOAP_ACTION, localSoapSerializationEnvelope);
	    		
	    		soapResultado = ((SoapObject)localSoapSerializationEnvelope.getResponse());
	    		
	    		if(soapResultado!=null)
	    		{
	    			resultadoInt = Integer.parseInt(soapResultado.getPropertyAsString("Id"));
		    	    resultadoString = String.valueOf(soapResultado.getPropertyAsString("Resultado"));	    			
	    		}
	    	        
	    	    if (resultadoString.equals("OK") && resultadoInt > 0) 
	    	    {
	    	      	WSDeleteProductoPOP = true;
	    	    }
	    	    return true;	        
			}
	    	catch (Exception localException)
	    	{
	    		WSDeleteProductoPOP = false;
   			
   			if(localException.getMessage() == null || localException.getMessage().isEmpty())
   			{
   		        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSDeletePreventaProductoPop: vacio");
   			}
   			else
   			{
   				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSDeleteProductoPOPObservacion: " + localException.getMessage());
   			} 
   			return true;
   		}
   	}
	    
    protected void onPostExecute(Boolean ejecutado)
    {
    	if(pdEliminarObservacionPOP.isShowing())
    	{
			pdEliminarObservacionPOP.dismiss();
    	}
	    	
	    	if(ejecutado)
	    	{
	    		if(WSDeleteProductoPOP) 
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(), "Observacion eliminada.", 1);
	    		}
	    		else
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(), resultadoString, 1);
	    		}
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSDeleteProductoPOPObservacion.", 1);
	    	}
    	}
	}
	
	private void MostrarPantallaTipoImpresion()
	{
		Intent localIntent = new Intent(this,Distribuidortipoimpresion.class);
		if(ventaId!=0)
		{
			localIntent.putExtra("ventaId", ventaId);
		}
		if(newVentaId!=0)
		{
			localIntent.putExtra("ventaId", newVentaId);
		}
		
		startActivity(localIntent);
	}
	
	private void MostrarPantallaMapa()
	{
		Intent localIntent = new Intent(this, Distribuidormapaentregas.class);
	    localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(localIntent);
	}
	
	private void MostrarPantallaAutoventa()
	{
		Intent localIntent = null;
	    
	    localIntent = new Intent(this, Vendedorpreventanits.class);
	    localIntent.putExtra("clienteId", clienteId);
	    localIntent.putExtra("origenSolicitud", "Venta");
	    
	    startActivity(localIntent);
	}
	
	@Override	
	public void onBackPressed() 
	{
		super.onBackPressed();
		MostrarPantallaMapa();
	}
}
