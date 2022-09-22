package com.detesim.venderunilever;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import BLL.ClienteCensoBLL;
import BLL.ClientePreventaBLL;
import BLL.MyLogBLL;
import Clases.ClienteCenso;
import Clases.ClientePreventa;
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

public class Censistaunificacionclientesmapacoordenada extends FragmentActivity implements OnMapReadyCallback {
	Utilidades theUtilidades = new Utilidades();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;

	GoogleMap mapa;
	TextView tvMensaje;

	ClientePreventa clienteVender;
	ClienteCenso clienteCenso;
	int clienteCensoId;
	int clienteVenderId;
	boolean coordenadaEstablecida;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_censistaunificacionclientesmapacoordenada);

		tvMensaje = (TextView) findViewById(R.id.tvUnificacionClienteMensaje);
		tvMensaje.setVisibility(View.INVISIBLE);

		coordenadaEstablecida = false;

		clienteCensoId = 0;
		clienteCensoId = getIntent().getExtras().getInt("ClienteCensoId");

		clienteVenderId = 0;
		clienteVenderId = getIntent().getExtras().getInt("ClienteVenderId");

		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapaNuevaCoordenada);
		mapFragment.getMapAsync(this);

		loginEmpleado = null;

		try {
			loginEmpleado = this.theUtilidades.ObtenerLoginEmpleado();
		} catch (Exception localException) {
			if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener el LoginEmpleado del censista: vacio");
			} else {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener el LoginEmpleado del censista: " + localException.getMessage());
			}
		}

		if (loginEmpleado == null) {
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los datos de login del censista.", 2);
		}
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mapa = googleMap;
		if (mapa != null) {
			if (ObtenerClientes()) {
				if (InicializarMapa()) {
					MostrarClientes();

					theUtilidades.MostrarMensaje(getApplicationContext(), "Haga click en el mapa, para fijar la nueva coordenada.", 1);
				} else {
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo inicializar el mapa.", 1);
				}
			}
		} else {
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo inicializar el mapa", 1);
		}
	}

	private boolean ObtenerClientes() {
		boolean obtenido = false;

		if (ObtenerClienteVender()) {
			if (ObtenerClienteCenso()) {
				obtenido = true;
			} else {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el cliente del censo.", 1);
			}
		} else {
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el cliente del vender.", 1);
		}

		return obtenido;
	}

	private boolean ObtenerClienteVender() {
		boolean obtenido = false;
		clienteVender = null;
		try {
			clienteVender = new ClientePreventaBLL().ObtenerClientePreventaPor(clienteVenderId);
		} catch (Exception localException) {
			if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener el cliente vender: vacio");
			} else {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener el cliente vender: " + localException.getMessage());
			}
			return false;
		}

		if (clienteVender != null) {
			obtenido = true;
			;
		}

		return obtenido;
	}

	private boolean ObtenerClienteCenso() {
		boolean obtenido = false;
		clienteCenso = null;
		try {
			clienteCenso = new ClienteCensoBLL().ObtenerClienteCensoPor(clienteCensoId);
		} catch (Exception localException) {
			if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener el cliente censo: vacio");
			} else {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener el cliente censo: " + localException.getMessage());
			}
			return false;
		}

		if (clienteCenso != null) {
			obtenido = true;
			;
		}

		return obtenido;
	}

	private boolean InicializarMapa() {
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
				&& ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			return false;
		}
		mapa.setMyLocationEnabled(true);
	    mapa.getUiSettings().setZoomControlsEnabled(true);
	    
	    LatLng ubicacionInicial = null;
	    if(clienteVender !=null)
	    {
	    	ubicacionInicial = new LatLng(clienteVender.get_latitud(),clienteVender.get_longitud());
	    }
	    else
	    {
	    	ubicacionInicial = new LatLng(0,0);
    	}
	    
	    CameraPosition localCameraPosition = new CameraPosition.Builder().target(ubicacionInicial).zoom(17).build();
	    
	    mapa.animateCamera(CameraUpdateFactory.newCameraPosition(localCameraPosition));
	    
	    mapa.setOnMapClickListener(new GoogleMap.OnMapClickListener()
	    {
	        public void onMapClick(LatLng paramLatLng)
	        {
	        	if(coordenadaEstablecida == false)
	        	{
	        		MarkerOptions localMarkerOptions = new MarkerOptions();
	  	          	localMarkerOptions.position(paramLatLng);
	  	          	localMarkerOptions.draggable(true);
	  	          	localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.localizacion));
	  	          	localMarkerOptions.title("Nueva Coordenada");
	  	          	mapa.addMarker(localMarkerOptions);
	  	          	
	  	          	coordenadaEstablecida = true;
	  	          	theUtilidades.MostrarMensaje(getApplicationContext(), "Coordenada establecida.", 1);
	  	          	tvMensaje.setVisibility(View.VISIBLE);
	        	}
	        	else
	        	{
	        		theUtilidades.MostrarMensaje(getApplicationContext(), "La coordenada ya fue establecida.", 1);
	        	}
	        }
	    });
	 
	    mapa.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
	    {

			@Override
			public boolean onMarkerClick(Marker marker) 
			{
				if ((marker != null) && (marker.getTitle().equals("Nueva Coordenada"))) 
		        {
		            MostrarPantallaUnificacionCliente(marker.getPosition().latitude, marker.getPosition().longitude);
		        }
		        return true;
			}
	    });

	    return true;
	}
	
	private void MostrarClientes()
	{		
    	MarkerOptions localMarkerVenderOptions = new MarkerOptions();
    	localMarkerVenderOptions.title(clienteVender.get_nombreCompleto());
    	localMarkerVenderOptions.snippet(String.valueOf(clienteVender.get_clienteId()));
    	localMarkerVenderOptions.position(new LatLng(clienteVender.get_latitud(),clienteVender.get_longitud()));
    	localMarkerVenderOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.cliente_atendido));	        
        this.mapa.addMarker(localMarkerVenderOptions);
	    
    	MarkerOptions localMarkerCensoOptions = new MarkerOptions();
    	localMarkerCensoOptions.title(clienteCenso.get_referencia());
    	localMarkerCensoOptions.snippet(String.valueOf(clienteCenso.get_id()));
    	localMarkerCensoOptions.position(new LatLng(clienteCenso.get_latitud(),clienteCenso.get_longitud()));
    	localMarkerCensoOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.cliente_censo));
        this.mapa.addMarker(localMarkerCensoOptions);
	}
	
	private void MostrarPantallaUnificacionCliente(double latitud,double longitud)
	{
		Intent intent = new Intent(this, Censistaunificacionclientes.class);
		intent.putExtra("CoordenadaNueva",1);
		intent.putExtra("ClienteCensoId",clienteCensoId);
		intent.putExtra("ClienteVenderId",clienteVenderId);
		intent.putExtra("LatitudNueva",latitud);
		intent.putExtra("LongitudNueva",longitud);		
		startActivity(intent);
	}
}
