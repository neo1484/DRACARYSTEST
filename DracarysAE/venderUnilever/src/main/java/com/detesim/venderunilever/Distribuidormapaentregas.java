package com.detesim.venderunilever;


import BLL.ClienteVentaBLL;
import BLL.MyLogBLL;
import BLL.PreventaDistBLL;
import BLL.SincronizacionVentaBLL;
import Clases.ClienteVenta;
import Clases.LoginEmpleado;
import Clases.PreventaDist;
import Clases.SincronizacionVenta;
import Utilidades.Utilidades;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Distribuidormapaentregas extends FragmentActivity implements OnMapReadyCallback {
	Utilidades theUtilidades = new Utilidades();
	MyLogBLL myLog = new MyLogBLL();
	ArrayList<ClienteVenta> listadoClienteVenta;

	private GoogleMap mapa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_distribuidormapaentregas);

		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapaDistribuidor);
		mapFragment.getMapAsync(this);

		LoginEmpleado loginEmpleado = null;
		try {
			loginEmpleado = this.theUtilidades.ObtenerLoginEmpleado();
		} catch (Exception localException) {
			if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener el LoginEmpleado del distribuidor: vacio");
			} else {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener el LoginEmpleado del distribuidor: " + localException.getMessage());
			}
		}

		if (loginEmpleado == null) {
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los datos de loginEmpleado del distribuidor.", 2);
		}
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mapa = googleMap;
		if (mapa != null) {
			if(ObtenerClientesDistribuidor())
			{
				if(InicializarMapa())
				{
					DesplegarClientesDistribuidor();
					theUtilidades.MostrarMensaje(getApplicationContext(), "Clientes en ruta: " + String.valueOf(listadoClienteVenta.size()),1);
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo inicializar el mapa.", 1);
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron clientes para el distribuidor.", 1);
			}
		} else {
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo inicilizar el mapa.", 1);
		}
	}

	private boolean ObtenerClientesDistribuidor() {
		listadoClienteVenta = null;
		try {
			listadoClienteVenta = new ClienteVentaBLL().ObtenerClientesVenta();
		} catch (Exception localException) {
			if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener los clientesEntrega: vacio");
			} else {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener los clientesEntrega: " + localException.getMessage());
			}
		}

		if (listadoClienteVenta == null) {
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los clientes del distribuidor.", 1);
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
		LatLng ubicacionInicial=null;

		if(listadoClienteVenta != null && listadoClienteVenta.size()>0)
		{
			ubicacionInicial = new LatLng(listadoClienteVenta.get(0).get_latitud(),listadoClienteVenta.get(0).get_longitud());
		}
		else
		{
			ubicacionInicial = new LatLng(0,0);
		}

		CameraPosition localCameraPosition = new CameraPosition.Builder().target(ubicacionInicial).zoom(15).build();
		mapa.animateCamera(CameraUpdateFactory.newCameraPosition(localCameraPosition));
		mapa.getUiSettings().setZoomControlsEnabled(true);

		mapa.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter()
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
				TextView tvDatosComplementarios = (TextView)view.findViewById(R.id.tvMapaNombreClienteDatosComplementarios);
				TextView tvClienteEncuestado = (TextView)view.findViewById(R.id.tvMapaNombreClienteClienteEncuestado);
				TextView tvClienteNroFotos = (TextView)view.findViewById(R.id.tvMapaNombreClienteNroFotos);
				TextView tvObservacion = (TextView)view.findViewById(R.id.tvMapaNombreClienteObservacion);

				ClienteVenta clienteVenta = null;

				try
				{
					clienteVenta = new ClienteVentaBLL().ObtenerClienteVentaPor(Integer.parseInt(marker.getSnippet()));
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

				if(clienteVenta != null)
				{
					tvNombre.setText(marker.getTitle());
					String codigo = "Codigo: " +marker.getSnippet();
					tvCodigo.setText(codigo);
					tvTerritorio.setText("");
					tvMercado.setText("");
					String nroPreventas = "No. Preventas: " + ObtenerNumeroPreventasDistPorClienteId(Integer.parseInt(marker.getSnippet()));
					tvNroPreventas.setText(nroPreventas);
					tvPromedioVentaMensual.setText("");
					tvDatosComplementarios.setText("");
					tvClienteEncuestado.setText("");
					tvClienteNroFotos.setText("");
					tvObservacion.setText(clienteVenta.get_observacion());
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
				  if (!marker.getSnippet().equals(""))
				  {
					  MostrarPantallaDistribuidorVenta(Integer.parseInt(marker.getSnippet()));
					  return;
				  }

				  theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontro el Id Cliente.",1);
			  }
			  else
			  {
				  theUtilidades.MostrarMensaje(getApplicationContext(),"Operacion no definida.",1);
			  }
			}
		});

		return true;

	}

	private String ObtenerNumeroPreventasDistPorClienteId(int clienteId)
	{
		ArrayList<PreventaDist> listadoPreventaDist = null;
		try
		{
			listadoPreventaDist = new PreventaDistBLL().ObtenerPreventaDistNoEntregadaPorClienteId(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el numero de preventas por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el numero de preventas por clienteId: " + localException.getMessage());
	    	} 
		}
		
		if(listadoPreventaDist == null)
		{
			return "0";
		}
		else
		{
			return String.valueOf(listadoPreventaDist.size());
		}
	}

	private void DesplegarClientesDistribuidor()
	{
		//String nombreNegocio;
		
		for(ClienteVenta item : listadoClienteVenta)
		{
			MarkerOptions localMarkerOptions = new MarkerOptions();
			
		    localMarkerOptions.title(String.valueOf(item.get_nombreCompleto()));
		    localMarkerOptions.snippet(String.valueOf(item.get_clienteId()));
		    localMarkerOptions.position(new LatLng(item.get_latitud(),item.get_longitud()));
		    
		    //nombreNegocio = ObtenerNombreNegocio(item.get_clienteTipoNegocioId());
		    
		    if(item.is_estadoAtendido())
		    {
		    	//if(nombreNegocio.toUpperCase().equals("ALMACEN/PULPERIAS"))
		    	if(item.get_clienteTipoNegocioId() == 2)
    			{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.almacen_atendido));
    			}
		    	//else if(nombreNegocio.toUpperCase().equals("TIPO A"))
		    	else if(item.get_clienteTipoNegocioId() == 18)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendaa_atendida));
		    	}
		    	//else if(nombreNegocio.toUpperCase().equals("TIPO B"))
		    	else if(item.get_clienteTipoNegocioId() == 19)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendab_atendida));
		    	}
		    	//else if(nombreNegocio.toUpperCase().equals("TIPO C"))
		    	else if(item.get_clienteTipoNegocioId() == 20)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendac_atendida));
		    	}
		    	//else if(nombreNegocio.toUpperCase().equals("MICROMERCADO"))
		    	else if(item.get_clienteTipoNegocioId() == 14)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.micromercado_atendido));
		    	}
		    	else
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.cliente_atendido));
		    	}
		    }
		    else if(item.is_autoventa())
		    {
		    	//if(nombreNegocio.toUpperCase().equals("ALMACEN/PULPERIAS"))
		    	if(item.get_clienteTipoNegocioId() == 2)
    			{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.almacen_autoventa));
    			}
		    	//else if(nombreNegocio.toUpperCase().equals("TIPO A"))
		    	else if(item.get_clienteTipoNegocioId() == 18)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendaa_autoventa));
		    	}
		    	//else if(nombreNegocio.toUpperCase().equals("TIPO B"))
		    	else if(item.get_clienteTipoNegocioId() == 19)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendab_autoventa));
		    	}
		    	//else if(nombreNegocio.toUpperCase().equals("TIPO C"))
		    	else if(item.get_clienteTipoNegocioId() == 20)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendac_autoventa));
		    	}
		    	//else if(nombreNegocio.toUpperCase().equals("MICROMERCADO"))
		    	else if(item.get_clienteTipoNegocioId() == 14)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.micromercado_autoventa));
		    	}
		    	else
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.cliente_autoventa));
		    	}
		    }
		    else
		    {
		    	//if(nombreNegocio.toUpperCase().equals("ALMACEN/PULPERIAS"))
		    	if(item.get_clienteTipoNegocioId() == 2)
    			{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.almacen_noatendido));
    			}
		    	//else if(nombreNegocio.toUpperCase().equals("TIPO A"))
		    	else if(item.get_clienteTipoNegocioId() == 18)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendaa_noatendida));
		    	}
		    	//else if(nombreNegocio.toUpperCase().equals("TIPO B"))
		    	else if(item.get_clienteTipoNegocioId() == 19)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendab_noatendida));
		    	}
		    	//else if(nombreNegocio.toUpperCase().equals("TIPO C"))
		    	else if(item.get_clienteTipoNegocioId() == 20)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendac_noatendida));
		    	}
		    	//else if(nombreNegocio.toUpperCase().equals("MICROMERCADO"))
		    	else if(item.get_clienteTipoNegocioId() == 14)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.micromercado_noatendido));
		    	}
		    	else
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.cliente_no_atendido));
		    	}
		    }
		    
		    //Si tiene registrada una preventa no entregada
		    if(EsVentaNoEntregada(item.get_clienteId()))
		    {
		    	//if(nombreNegocio.toUpperCase().equals("ALMACEN/PULPERIAS"))
		    	if(item.get_clienteTipoNegocioId() == 2)
    			{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.almacen_visitado));
    			}
		    	//else if(nombreNegocio.toUpperCase().equals("TIPO A"))
		    	else if(item.get_clienteTipoNegocioId() == 18)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendaa_visitada));
		    	}
		    	//else if(nombreNegocio.toUpperCase().equals("TIPO B"))
		    	else if(item.get_clienteTipoNegocioId() == 19)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendab_visitada));
		    	}
		    	//else if(nombreNegocio.toUpperCase().equals("TIPO C"))
		    	else if(item.get_clienteTipoNegocioId() == 20)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendac_visitada));
		    	}
		    	//else if(nombreNegocio.toUpperCase().equals("MICROMERCADO"))
		    	else if(item.get_clienteTipoNegocioId() == 14)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.micromercado_visitado));
		    	}
		    	else
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.cliente_visitado));
		    	}
		    }
		    
		    //Si tiene registrada mas de una preventa
		    if(TieneOtraPreventa(item.get_clienteId()))
		    {
		    	//if(nombreNegocio.toUpperCase().equals("ALMACEN/PULPERIAS"))
		    	if(item.get_clienteTipoNegocioId() == 2)
    			{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.almacen_noatendido));
    			}
		    	//else if(nombreNegocio.toUpperCase().equals("TIPO A"))
		    	else if(item.get_clienteTipoNegocioId() == 18)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendaa_noatendida));
		    	}
		    	//else if(nombreNegocio.toUpperCase().equals("TIPO B"))
		    	else if(item.get_clienteTipoNegocioId() == 19)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendab_noatendida));
		    	}
		    	//else if(nombreNegocio.toUpperCase().equals("TIPO C"))
		    	else if(item.get_clienteTipoNegocioId() == 20)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendac_noatendida));
		    	}
		    	//else if(nombreNegocio.toUpperCase().equals("MICROMERCADO"))
		    	else if(item.get_clienteTipoNegocioId() == 14)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.micromercado_noatendido));
		    	}
		    	else
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.cliente_no_atendido));
		    	}
		    }
		    
		    //Si tiene una autoventa no entregada
		    if(EsAutoVentaNoEntregada(item.get_clienteId()))
		    {
		    	//if(nombreNegocio.toUpperCase().equals("ALMACEN/PULPERIAS"))
		    	if(item.get_clienteTipoNegocioId() == 2)
    			{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.almacen_visitado));
    			}
		    	//else if(nombreNegocio.toUpperCase().equals("TIPO A"))
		    	else if(item.get_clienteTipoNegocioId() == 18)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendaa_visitada));
		    	}
		    	//else if(nombreNegocio.toUpperCase().equals("TIPO B"))
		    	else if(item.get_clienteTipoNegocioId() == 19)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendab_visitada));
		    	}
		    	//else if(nombreNegocio.toUpperCase().equals("TIPO C"))
		    	else if(item.get_clienteTipoNegocioId() == 20)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendac_visitada));
		    	}
		    	//else if(nombreNegocio.toUpperCase().equals("MICROMERCADO"))
		    	else if(item.get_clienteTipoNegocioId() == 14)
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
	
	/*private String ObtenerNombreNegocio(int tipoNegocioId)
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
	}*/
	
	private boolean EsVentaNoEntregada(int clienteId)
	{
		boolean visitado = false;
		SincronizacionVenta localSincronizacionVenta =null;
		try
		{
			localSincronizacionVenta = new SincronizacionVentaBLL().ObtenerSincronizacionesVentaVentaNoEntregadaPor(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta no entregada por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta no entregada por clienteId: " + localException.getMessage());
	    	} 
		}
		
		if(localSincronizacionVenta!=null)
		{
			visitado = true;
		}
		return visitado;
	}	
	
	private boolean TieneOtraPreventa(int clienteId)
	{
		boolean tieneOtraPreventa = false;
		ArrayList<PreventaDist> localPreventaDist = null;
		try
		{
			localPreventaDist = new PreventaDistBLL().ObtenerPreventaDistNoEntregadaPorClienteId(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa del distribuidor no entregada por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa del distribuidor no entregada por clienteId: " + localException.getMessage());
	    	} 
		}
		
		if(localPreventaDist!=null && localPreventaDist.size()>0)
		{
			tieneOtraPreventa = true;
		}
		return tieneOtraPreventa;
	}	
	
	private boolean EsAutoVentaNoEntregada(int clienteId)
	{
		boolean visitado = false;
		SincronizacionVenta localSincronizacionVenta =null;
		try
		{
			localSincronizacionVenta = new SincronizacionVentaBLL().ObtenerSincronizacionesVentaAutoVentaNoEntregadaPor(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la autoventa no entregada por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la autoventa no entregada por clienteId: " + localException.getMessage());
	    	} 
		}
		
		if(localSincronizacionVenta!=null)
		{
			visitado = true;
		}
		return visitado;
	}
 
	private void MostrarPantallaDistribuidorVenta(int clienteId)
	{
	    Intent localIntent = new Intent(this,Distribuidorventa.class);
	    localIntent.putExtra("ClienteId", clienteId);
	    localIntent.putExtra("ItemModificado",false);
	    localIntent.putExtra("origenSolicitud", "Venta");
	    startActivity(localIntent);
	}

	private void MostrarPantallaMenuDistribuidor()
	{
		Intent localIntent = new Intent(this, Menudistribuidor.class);
	    localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(localIntent);
	}
	
	@Override
	public void onBackPressed() 
	{
		super.onBackPressed();
		MostrarPantallaMenuDistribuidor();
	}
}
