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

import BLL.ClienteBLL;
import BLL.ClienteNroFotoBLL;
import BLL.MyLogBLL;
import Clases.Cliente;
import Clases.ClienteNroFoto;
import Clases.LoginEmpleado;
import Clases.ParametroGeneral;
import Utilidades.Utilidades;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

public class Censistamapaeditarcliente extends FragmentActivity implements OnMapReadyCallback {
	Utilidades theUtilidades = new Utilidades();
	MyLogBLL myLog = new MyLogBLL();

	ArrayList<Cliente> listadoCliente;
	LoginEmpleado loginEmpleado;
	ParametroGeneral parametroGeneral;
	GoogleMap mapa;
	String Origen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_censistamapaeditarcliente);

		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.censistamapaeditarcliente);
		mapFragment.getMapAsync(this);

		Bundle localBundle = getIntent().getExtras();

		Origen = "";
		Origen = localBundle.getString("Origen");
		if (Origen.equals("")) {
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el origen de la peticion.", 1);
			return;
		}

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
			theUtilidades.MostrarMensaje(getApplicationContext(), "El censista no se logeo correctamente.", 1);
		}
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mapa = googleMap;
		if (mapa != null) {
			if (ObtenerClientes()) {
				if (InicializarMapa()) {
					DesplegarClientes();
					theUtilidades.MostrarMensaje(getApplicationContext(), "Clientes en ruta: " + String.valueOf(listadoCliente.size()), 1);
				} else {
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo inicializar el mapa.", 1);
				}
			} else {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron clientes para el vendedor.", 1);
			}
		} else {
			theUtilidades.MostrarMensaje(getApplication(), "No se pudo inicializar el mapa.", 1);
		}
	}

	private boolean ObtenerClientes() {
		listadoCliente = null;
		try {
			listadoCliente = new ClienteBLL().ObtenerClientes();
		} catch (Exception localException) {
			if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener los clientes: vacio");
			} else {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener los clientes: " + localException.getMessage());
			}
		}

		if (listadoCliente == null) {
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

		if(listadoCliente != null && listadoCliente.size()>0)
		{
			ubicacionInicial = new LatLng(listadoCliente.get(0).get_latitud(),listadoCliente.get(0).get_longitud());
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
				TextView tvNroPreventas = (TextView)view.findViewById(R.id.tvMapaNombreClienteNroPreventas);
				TextView tvPromedioVentaMensual = (TextView)view.findViewById(R.id.tvMapaNombreClientePromedioVentaMensual);
				TextView tvDatosComplementarios = (TextView)view.findViewById(R.id.tvMapaNombreClienteDatosComplementarios);
				TextView tvClienteEncuestado = (TextView)view.findViewById(R.id.tvMapaNombreClienteClienteEncuestado);
				TextView tvClienteNroFotos = (TextView)view.findViewById(R.id.tvMapaNombreClienteNroFotos);

				tvNombre.setText(marker.getTitle());
				tvCodigo.setText("Codigo: " + marker.getSnippet());
				tvNroPreventas.setText("");
				tvPromedioVentaMensual.setText("");
				tvDatosComplementarios.setText("");
				tvClienteEncuestado.setText("");
				tvClienteNroFotos.setText("Nro. de fotos: " + ObtenerNroFotosPorCliente(Integer.valueOf(marker.getSnippet())));

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
					MostrarPantallaEdicionCliente(Integer.parseInt(marker.getSnippet()));
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"Operacion no definida.",1);
				}
			}
		});

		return true;
	}

	private void DesplegarClientes()
	{
		//String nombreNegocio;
		
		for(Cliente item : listadoCliente)
		{
			MarkerOptions localMarkerOptions = new MarkerOptions();
			
		    localMarkerOptions.title(String.valueOf(item.get_nombreCompleto()));
		    localMarkerOptions.snippet(String.valueOf(item.get_clienteId()));
		    localMarkerOptions.position(new LatLng(item.get_latitud(),item.get_longitud()));
		    
		    //nombreNegocio = ObtenerNombreNegocio(item.get_tipoNegocioId());
	        
	        //if(nombreNegocio.equals("Almacen/Pulperias"))
		    if(item.get_tipoNegocioId() == 2)
			{
	    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.almacen_atendido));
			}
	    	//else if(nombreNegocio.equals("Tipo A"))
		    else if(item.get_tipoNegocioId() == 18)
	    	{
	    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendaa_atendida));
	    	}
	    	//else if(nombreNegocio.equals("Tipo B"))
		    else if(item.get_tipoNegocioId() == 19)
	    	{
	    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendab_atendida));
	    	}
	    	//else if(nombreNegocio.equals("Tipo C"))
		    else if(item.get_tipoNegocioId() == 20)
	    	{
	    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendac_atendida));
	    	}
	    	//else if(nombreNegocio.equals("Micromercado"))
		    else if(item.get_tipoNegocioId() == 14)
	    	{
	    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.micromercado_atendido));
	    	}
	    	else
	    	{
	    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.cliente_atendido));
	    	}
		    
		    mapa.addMarker(localMarkerOptions);
		}
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
	
	private void MostrarPantallaEdicionCliente(int clienteId)
	{
		if(clienteId > 0)
		{
			Intent localIntent = new Intent(this,Censistaeditarcliente.class);
			localIntent.putExtra("clienteId", clienteId);
			localIntent.putExtra("peticion", "Mapa");
			localIntent.putExtra("Origen", Origen);
			startActivity(localIntent);
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "Para editar los datos de un cliente, primero debe sincronizar el cliente.", 1);
		}
	}

}