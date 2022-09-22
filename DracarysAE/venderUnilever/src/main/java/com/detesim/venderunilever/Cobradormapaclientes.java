package com.detesim.venderunilever;

import java.util.ArrayList;

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

import BLL.ClienteCobranzaBLL;
import BLL.MyLogBLL;
import Clases.ClienteCobranza;
import Clases.LoginEmpleado;
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

public class Cobradormapaclientes extends FragmentActivity implements OnMapReadyCallback {
	Utilidades theUtilidades = new Utilidades();
	MyLogBLL myLog = new MyLogBLL();

	LoginEmpleado loginEmpleado;
	GoogleMap mapa;
	int clienteCobranzaId;

	ArrayList<ClienteCobranza> listadoClienteCobranza;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cobradormapaclientes);

		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapaCobrador);
		mapFragment.getMapAsync(this);

		loginEmpleado = null;

		try {
			loginEmpleado = this.theUtilidades.ObtenerLoginEmpleado();
		} catch (Exception localException) {
			if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener el LoginEmpleado del cobrador: vacio");
			} else {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener el LoginEmpleado del cobrador: " + localException.getMessage());
			}
		}

		if (loginEmpleado == null) {
			theUtilidades.MostrarMensaje(getApplicationContext(), "El cobrador no se logeo correctamente.", 2);
		}
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mapa = googleMap;
		if (mapa != null) {
			if (ObtenerClientesCobranza()) {
				if (InicializarMapa()) {
					DesplegarClientesCobranza();

					theUtilidades.MostrarMensaje(getApplicationContext(), "Clientes con cobranza: " + String.valueOf(listadoClienteCobranza.size()), 1);
				} else {
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo inicializar el mapa.", 1);
				}
			} else {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron clientes con cobranza.", 1);
			}
		} else {
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo inicializar el mapa.", 1);
		}
	}

	private boolean ObtenerClientesCobranza() {
		listadoClienteCobranza = null;
		try {
			listadoClienteCobranza = new ClienteCobranzaBLL().ObtenerClientesCobranza();
		} catch (Exception localException) {
			if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener los clientes cobranza: vacio");
			} else {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener los clientes cobranza: " + localException.getMessage());
			}
		}

		if (listadoClienteCobranza == null) {
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

		if(listadoClienteCobranza != null && listadoClienteCobranza.size()>0)
		{
			ubicacionInicial = new LatLng(listadoClienteCobranza.get(0).get_latitud(),listadoClienteCobranza.get(0).get_longitud());
		}
		else
		{
			ubicacionInicial = new LatLng(0,0);
		}

		CameraPosition localCameraPosition = new CameraPosition.Builder().target(ubicacionInicial).zoom(15).build();
		mapa.animateCamera(CameraUpdateFactory.newCameraPosition(localCameraPosition));

		mapa.setInfoWindowAdapter(new InfoWindowAdapter()
		{
			@Override
			public View getInfoWindow(Marker marker)
			{
				View view = getLayoutInflater().inflate(R.layout.disenio_nombreclientecobranza, null);

				TextView tvNombre = (TextView)view.findViewById(R.id.tvMapaCobradorClienteNombre);
				TextView tvCodigoCliente = (TextView)view.findViewById(R.id.tvMapaCobradorCodigoCliente);
				TextView tvNroCobranzas = (TextView)view.findViewById(R.id.tvMapaCobradorNroCobranzas);

				tvNombre.setText(marker.getTitle());
				String codigoCliente = "Codigo cliente: " + marker.getSnippet();
				tvCodigoCliente.setText(codigoCliente);
				String noCobrnazas = "No. Cobranzas: " + ObtenerNroCobranzasPorCliente(Integer.parseInt(marker.getSnippet()));
				tvNroCobranzas.setText(noCobrnazas);

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
					MostrarPantallaCobranzas(Integer.parseInt(marker.getSnippet()));
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"Operacion no definida.",1);
				}
			}
		});

		return true;
	}

	private void DesplegarClientesCobranza()
	{		
		for(ClienteCobranza item : listadoClienteCobranza)
		{
			MarkerOptions localMarkerOptions = new MarkerOptions();
			
		    localMarkerOptions.title(String.valueOf(item.get_nombre()));
		    localMarkerOptions.snippet(String.valueOf(item.get_clienteId()));
		    localMarkerOptions.position(new LatLng(item.get_latitud(),item.get_longitud()));
		    
		    
		    if(item.get_serverId() <= 0)
		    {
		    	localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.cliente_no_atendido));
		    }
		    else
		    {
		    	localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.cliente_atendido));
		    }
		    
		    mapa.addMarker(localMarkerOptions);
		}
	}
	
	private String ObtenerNroCobranzasPorCliente(int clienteId)
	{
		ArrayList<ClienteCobranza> listadoCobranzas = null;
		try
		{
			listadoCobranzas = new ClienteCobranzaBLL().ObtenerClienteCobranzaPor(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las cobranzas por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las cobranzas por clienteId: " + localException.getMessage());
	    	} 
		}
		
		if(listadoCobranzas != null)
		{
			return String.valueOf(listadoCobranzas.size());
		}
		else
		{
			return "0";
		}
	}
	
	private void MostrarPantallaCobranzas(int clienteId)
	{
	    Intent localIntent = null;
	    localIntent = new Intent(this, Cobradorcobranza.class);
	    localIntent.putExtra("ClienteId", clienteId);
	    startActivity(localIntent);
	}

}
