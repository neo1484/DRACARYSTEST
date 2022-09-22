package com.detesim.venderunilever;

import java.util.ArrayList;
import java.util.Locale;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import BLL.ClienteNoAtendidoBLL;
import BLL.ClienteNroFotoBLL;
import BLL.ClientePreventaBLL;
import BLL.MyLogBLL;
import BLL.ParametroGeneralBLL;
import BLL.PreventaBLL;
import BLL.TipoNegocioBLL;
import BLL.VentaDirectaBLL;
import Clases.ClienteNoAtendido;
import Clases.ClienteNroFoto;
import Clases.ClientePreventa;
import Clases.LoginEmpleado;
import Clases.ParametroGeneral;
import Clases.Preventa;
import Clases.TipoNegocio;
import Clases.VentaDirecta;
import Utilidades.Utilidades;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

public class Vendedorventadirectamapa extends FragmentActivity implements OnMapReadyCallback {
	Utilidades theUtilidades = new Utilidades();
	MyLogBLL myLog = new MyLogBLL();

	ArrayList<ClientePreventa> listadoClientePreventa;
	LoginEmpleado loginEmpleado;
	ParametroGeneral parametroGeneral;
	GoogleMap mapa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedormapaventasdirectas);

		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapaVentaDirecta);
		mapFragment.getMapAsync(this);

		loginEmpleado = null;

		try {
			loginEmpleado = this.theUtilidades.ObtenerLoginEmpleado();
		} catch (Exception localException) {
			if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener el LoginEmpleado del vendedor: vacio");
			} else {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener el LoginEmpleado del vendedor: " + localException.getMessage());
			}
		}

		if (loginEmpleado == null) {
			theUtilidades.MostrarMensaje(getApplicationContext(), "El vendedor no se logeo correctamente.", 2);
		}
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mapa = googleMap;
		if (mapa != null) {
			ObtenerParametroGeneral();
			if (ObtenerClientesVendedor()) {
				if (InicializarMapa()) {
					DesplegarClientesVendedor();
					theUtilidades.MostrarMensaje(getApplicationContext(), "Clientes en ruta: " + String.valueOf(listadoClientePreventa.size()), 1);
				} else {
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo inicializar el mapa.", 1);
				}
			} else {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron clientes para el vendedor.", 1);
			}

		} else {
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo iniciializar el mapa.", 1);
		}
	}

	private void ObtenerParametroGeneral() {
		parametroGeneral = null;
		try {
			parametroGeneral = new ParametroGeneralBLL().ObtenerParamatroGeneral();
		} catch (Exception localException) {
			if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener los parametros generales: vacio");
			} else {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener los parametros generales: " + localException.getMessage());
			}
		}

		if (parametroGeneral == null) {
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los parametros generales.", 1);
			return;
		}
	}

	private boolean ObtenerClientesVendedor() {
		listadoClientePreventa = null;
		try {
			listadoClientePreventa = new ClientePreventaBLL().ObtenerClientesPreventa();
		} catch (Exception localException) {
			if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener los clientes preventa: vacio");
			} else {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener los clientes preventa: " + localException.getMessage());
			}
		}

		if (listadoClientePreventa == null) {
			return false;
		} else {
			return true;
		}
	}

	private boolean InicializarMapa() {

		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
				&& ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			return false;
		}
		mapa.setMyLocationEnabled(true);
		mapa.getUiSettings().setZoomControlsEnabled(true);
		LatLng ubicacionInicial=null;

		if(listadoClientePreventa != null && listadoClientePreventa.size()>0)
		{
			ubicacionInicial = new LatLng(listadoClientePreventa.get(0).get_latitud(),listadoClientePreventa.get(0).get_longitud());
		}
		else
		{
			ubicacionInicial = new LatLng(0,0);
		}
		CameraPosition localCameraPosition = new CameraPosition.Builder().target(ubicacionInicial).zoom(15).build();
		mapa.animateCamera(CameraUpdateFactory.newCameraPosition(localCameraPosition));
		mapa.getUiSettings().setZoomControlsEnabled(true);

		mapa.setInfoWindowAdapter(new InfoWindowAdapter()
		{
			@Override
			public View getInfoWindow(Marker marker)
			{
				View view = getLayoutInflater().inflate(R.layout.disenio_mapanombrecliente, null);

				TextView tvNombre = (TextView)view.findViewById(R.id.tvMapaNombreClienteNombre);
				TextView tvCodigo = (TextView)view.findViewById(R.id.tvMapaNombreClienteCodigo);
				TextView tvTerritorio = (TextView)view.findViewById(R.id.tvMapaNombreClienteTerritorio);
				TextView tvMercado = (TextView)view.findViewById(R.id.tvMapaNombreClienteMercado);
				TextView tvNroPreventas = (TextView)view.findViewById(R.id.tvMapaNombreClienteNroPreventas);
				TextView tvPromedioVentaMensual = (TextView)view.findViewById(R.id.tvMapaNombreClientePromedioVentaMensual);
				TextView tvInformacionComplementaria = (TextView)view.findViewById(R.id.tvMapaNombreClienteDatosComplementarios);
				TextView tvClienteCensado = (TextView)view.findViewById(R.id.tvMapaNombreClienteClienteEncuestado);
				TextView tvClienteNroFotos = (TextView)view.findViewById(R.id.tvMapaNombreClienteNroFotos);
				TextView tvClienteObservacion = (TextView)view.findViewById(R.id.tvMapaNombreClienteObservacion);

				ClientePreventa clientePreventa = null;

				try
				{
					clientePreventa = new ClientePreventaBLL().ObtenerClientePreventaPor(Integer.parseInt(marker.getSnippet()));
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente por codigo: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente por codigo: " + localException.getMessage());
					}
				}

				if(clientePreventa != null)
				{
					tvNombre.setText(marker.getTitle());
					tvCodigo.setText("Codigo: " +marker.getSnippet());
					tvNroPreventas.setText("");
					tvPromedioVentaMensual.setText("Promedio venta Mensual: " + ObtenerPromedioVentaMensual(Integer.valueOf(marker.getSnippet())));
					tvTerritorio.setText("Territorio: " + clientePreventa.get_zona());
					tvMercado.setText("Mercado: " + clientePreventa.get_mercado());
					tvInformacionComplementaria.setText("");
					tvClienteCensado.setText("");
					tvClienteNroFotos.setText("Nro. de fotos: " + ObtenerNroFotosPorCliente(Integer.valueOf(marker.getSnippet())));
					if(clientePreventa.get_observacion().equals(""))
					{
						tvClienteObservacion.setText("");
					}
					else
					{
						tvClienteObservacion.setText("Observacion: " + clientePreventa.get_observacion());
					}
				}
				return view;
			}

			@Override
			public View getInfoContents(Marker marker)
			{
				return null;
			}
		});

		mapa.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
		{
			@Override
			public void onInfoWindowClick(Marker marker)
			{
				if (marker != null)
				{
					if(parametroGeneral.get_tipoImpresionFactura().equals("Medio Oficio"))
					{
						if (!marker.getSnippet().equals(""))
						{
							if(marker.getTitle().toLowerCase(Locale.US).contains("sin nombres") ||
								marker.getTitle().toLowerCase(Locale.US).contains("sin paterno"))
							{
								MostrarPantallaActualizarCliente(Integer.parseInt(marker.getSnippet()));
							}
							else
							{
								MostrarPantallaVentaDirectaNits(Integer.parseInt(marker.getSnippet()));
							}
						}
						else
						{
							theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontro el Id Cliente.",1);
						}
					}
					else if(parametroGeneral.get_tipoImpresionFactura().equals("Oficio"))
					{
						if (!marker.getSnippet().equals(""))
						{
							if(marker.getTitle().toLowerCase(Locale.US).contains("sin nombres") ||
								marker.getTitle().toLowerCase(Locale.US).contains("sin paterno"))
							{
								MostrarPantallaActualizarCliente(Integer.parseInt(marker.getSnippet()));
							}
							else
							{
								MostrarPantallaVentaDirectaNits(Integer.parseInt(marker.getSnippet()));
							}
						}
						else
						{
							theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontro el Id Cliente.",1);
						}
					}
					else
					{
						if (!marker.getSnippet().equals(""))
						{
							if(marker.getTitle().toLowerCase(Locale.US).contains("sin nombres") ||
								marker.getTitle().toLowerCase(Locale.US).contains("sin paterno"))
							{
								MostrarPantallaActualizarCliente(Integer.parseInt(marker.getSnippet()));
							}
							else
							{
								MostrarPantallaVentaDirectaNits(Integer.parseInt(marker.getSnippet()));
							}
						}
						else
						{
							theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontro el Id Cliente.",1);
						}
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"Operacion no definida.",1);
				}
			}
		});

		return true;

	}

	private String ObtenerNroFotosPorCliente(int clienteId)
	{
		ClienteNroFoto theClienteNroFoto=null;
		try
		{
			theClienteNroFoto = new ClienteNroFotoBLL().ObtenerClienteNroFotoPor(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el nro de fotos por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el nro de fotos por clienteId: " + localException.getMessage());
	    	}
		}
		if(theClienteNroFoto == null)
		{
			return "0";
		}
		else
		{
			return String.valueOf(theClienteNroFoto.get_numero());
		}
	}
	
	private void DesplegarClientesVendedor()
	{
		String nombreNegocio;
		
		for(ClientePreventa item : listadoClientePreventa)
		{
			MarkerOptions localMarkerOptions = new MarkerOptions();
			
		    localMarkerOptions.title(String.valueOf(item.get_nombreCompleto()));
		    localMarkerOptions.snippet(String.valueOf(item.get_clienteId()));
		    localMarkerOptions.position(new LatLng(item.get_latitud(),item.get_longitud()));
		    
		    nombreNegocio = ObtenerNombreNegocio(item.get_clienteTipoNegocioId());
		    
		    if(VerificarExistenciaVentaDirecta(item.get_clienteId()))
		    {
				if(nombreNegocio.equals("Almacen/Pulperias"))
				{
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.almacen_atendido));
				}
				else if(nombreNegocio.equals("Tipo A"))
				{
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendaa_atendida));
				}
				else if(nombreNegocio.equals("Tipo B"))
				{
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendab_atendida));
				}
				else if(nombreNegocio.equals("Tipo C"))
				{
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendac_atendida));
				}
				else if(nombreNegocio.equals("Micromercado"))
				{
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.micromercado_atendido));
				}
				else
				{
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.cliente_atendido));
				}
		    }
		    else
		    {
				if(nombreNegocio.equals("Almacen/Pulperias"))
				{
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.almacen_noatendido));
				}
				else if(nombreNegocio.equals("Tipo A"))
				{
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendaa_noatendida));
				}
				else if(nombreNegocio.equals("Tipo B"))
				{
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendab_noatendida));
				}
				else if(nombreNegocio.equals("Tipo C"))
				{
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendac_noatendida));
				}
				else if(nombreNegocio.equals("Micromercado"))
				{
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.micromercado_noatendido));
				}
				else
				{
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.cliente_no_atendido));
				}
		    }
		    
		    if(VerificarExistenciaClienteNoAtendido(item.get_clienteId()))
		    {
		    	if(nombreNegocio.equals("Almacen/Pulperias"))
    			{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.almacen_visitado));
    			}
		    	else if(nombreNegocio.equals("Tipo A"))
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendaa_visitada));
		    	}
		    	else if(nombreNegocio.equals("Tipo B"))
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendab_visitada));
		    	}
		    	else if(nombreNegocio.equals("Tipo C"))
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendac_visitada));
		    	}
		    	else if(nombreNegocio.equals("Micromercado"))
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.micromercado_visitado));
		    	}
		    	else
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.cliente_visitado));
		    	}
		    }
		    mapa.addMarker(localMarkerOptions);
		}
	}
	
	private String ObtenerNombreNegocio(int tipoNegocioId)
	{
		TipoNegocio theNegocio=null;
		try
		{
			theNegocio = new TipoNegocioBLL().ObtenerTipoNegocioPor(tipoNegocioId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el tipoNegocio por tipoNegocioId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el tipoNegocio por tipoNegocioId: " + localException.getMessage());
	    	}
		}
		if(theNegocio == null)
		{
			return "";
		}
		else
		{
			return theNegocio.get_descripcion();
		}
	}

	private boolean VerificarExistenciaVentaDirecta(int clienteId)
	{
		VentaDirecta ventaDirecta = null;
		try
		{
			ventaDirecta = new VentaDirectaBLL().ObtenerVentaDirectaPorClienteId(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta directa por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta directa por clienteId: " + localException.getMessage());
			}
		}

		return ventaDirecta != null;
	}
	
	private boolean VerificarExistenciaClienteNoAtendido(int clienteId)
	{
		boolean verificado = false;
		ClienteNoAtendido localClienteNoAtendido = null;
		try
		{
			localClienteNoAtendido = new ClienteNoAtendidoBLL().ObtenerClientesNoAtendidosPorClienteId(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientesNoAtendidos: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientesNoAtendidos: " + localException.getMessage());
	    	} 
		}
		
		if(localClienteNoAtendido != null)
		{
			verificado = true;
		}
		return verificado;
	}
	
	private String ObtenerNroPreventasPorCliente(int clienteId)
	{
		ArrayList<Preventa> listadoPreventa = null;
		try
		{
			listadoPreventa = new PreventaBLL().ObtenerNroPreventasPorClienteId(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas por clienteId: " + localException.getMessage());
	    	} 
			return "";
		}
		
		if(listadoPreventa == null)
		{
			return "0";
		}
		else
		{
			return String.valueOf(listadoPreventa.size());
		}
	}
	
	private String ObtenerPromedioVentaMensual(int clienteId)
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
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del cliente por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del cliente por clienteId: " + localException.getMessage());
	    	} 
			return "";
		}
		
		if(theClientePreventa == null)
		{
			return "0";
		}
		else
		{
			return String.valueOf(theClientePreventa.get_promedioVenta());
		}
	}
	
 	private void MostrarPantallaVentaDirectaNits(int clienteId)
	{
	    Intent localIntent = null;
    	localIntent = new Intent(this, Vendedorventadirectanits.class);
    	localIntent.putExtra("clienteId", clienteId);
	    startActivity(localIntent);
	}
	
	private void MostrarPantallaActualizarCliente(int clienteId)
	{
		Intent localIntent = new Intent(this,Vendedoractualizacioncliente.class);
		localIntent.putExtra("ClienteId", clienteId);
		startActivity(localIntent);
	}
	
	@Override
	public void onBackPressed() 
	{
		Intent intent = new Intent(this,Menuvendedor.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}


}
