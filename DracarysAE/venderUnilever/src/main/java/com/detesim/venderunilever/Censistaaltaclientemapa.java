package com.detesim.venderunilever;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import BLL.ClienteBLL;
import BLL.MyLogBLL;
import BLL.TipoNegocioBLL;
import Clases.Cliente;
import Clases.LoginEmpleado;
import Clases.TipoNegocio;
import Utilidades.Utilidades;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;


public class Censistaaltaclientemapa extends FragmentActivity implements OnMapReadyCallback {
	Utilidades theUtilidades = new Utilidades();
	MyLogBLL myLog = new MyLogBLL();

	ArrayList<Cliente> listadoCliente;
	LoginEmpleado loginEmpleado;
	GoogleMap mapa;
	String Origen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cencistaaltaclientemapa);

		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapaCensista);
		mapFragment.getMapAsync(this);

		Origen = "";
		Origen = getIntent().getExtras().getString("Origen");

		if (Origen.equals("")) {
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el origen de llamada del menu censista.", 1);
			return;
		}

		loginEmpleado = null;

		try {
			loginEmpleado = this.theUtilidades.ObtenerLoginEmpleado();
		} catch (Exception localException) {
			if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener el LoginEmpleado del supervisor: vacio");
			} else {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener el LoginEmpleado del supervisor: " + localException.getMessage());
			}
		}

		if (loginEmpleado == null) {
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los datos de loginEmpleado del supervisor.", 2);
		}
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mapa = googleMap;
		if (mapa != null) {
			if (ObtenerClientes()) {
				if (InicializarMapa()) {
					MostrarClientes();
				} else {
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo cargar el mapa.", 1);
				}
			} else {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener clientes que desplegar.", 1);

				if (!InicializarMapa()) {
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo cargar el mapa.", 1);
					return;
				}
			}
		} else {
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo inicializar el mapa", 1);
		}
	}

	private boolean InicializarMapa() {
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
				&& ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			return false;
		}
		mapa.setMyLocationEnabled(true);
	    mapa.getUiSettings().setZoomControlsEnabled(true);
	    
	    LatLng ubicacionInicial = null;
	    if(listadoCliente !=null && listadoCliente.size()>0)
	    {
	    	ubicacionInicial = new LatLng((listadoCliente.get(0)).get_latitud(),(listadoCliente.get(0)).get_longitud());
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
	          MarkerOptions localMarkerOptions = new MarkerOptions();
	          localMarkerOptions.position(paramLatLng);
	          localMarkerOptions.draggable(true);
	          localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.localizacion));
	          localMarkerOptions.title("Nuevo Cliente");
	          mapa.addMarker(localMarkerOptions);
	        }
	    });
	 
	    mapa.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
	    {

			@Override
			public boolean onMarkerClick(Marker marker) 
			{
				if ((marker != null) && (marker.getTitle().equals("Nuevo Cliente"))) 
		        {
		            MostrarPantallaTipoClienteInfo(marker.getPosition().latitude, marker.getPosition().longitude);
		        }
		        return true;
			}
		    });
	    
	    return true;
	}

	private boolean ObtenerClientes()
	{
		boolean obtenido = false;
		listadoCliente = null;
	    try
	    {
	    	listadoCliente = new ClienteBLL().ObtenerClientes();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes: " + localException.getMessage());
	    	} 
	    	return false;
	    }
	    
	    if(listadoCliente != null) 
	    {
	      obtenido = true;;
	    }
	    
	    return obtenido;
	}
	
	private void MostrarClientes()
	{
		String nombreNegocio;
		
	    for(Cliente item : listadoCliente)
	    {
	    	MarkerOptions localMarkerOptions = new MarkerOptions();
	        localMarkerOptions.position(new LatLng(item.get_latitud(), item.get_longitud()));
	        localMarkerOptions.title(item.get_nombreCompleto());
	        
	        nombreNegocio = ObtenerNombreNegocio(item.get_tipoNegocioId());
	        
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
	        
	        this.mapa.addMarker(localMarkerOptions);
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
	
	@Override
	public void onBackPressed()
	{
		Intent localIntent = new Intent(this, Menucensista.class);
	    localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    localIntent.putExtra("Origen", Origen);
	    startActivity(localIntent);
	}

	private void MostrarPantallaTipoClienteInfo(double latiud, double longitud)
	{
	    Intent localIntent = new Intent(this, Censistatipoinfocliente.class);
	    localIntent.putExtra("Latitud", String.valueOf(latiud));
	    localIntent.putExtra("Longitud", String.valueOf(longitud));
	    localIntent.putExtra("Origen", Origen);
	    startActivity(localIntent);
	}
}
