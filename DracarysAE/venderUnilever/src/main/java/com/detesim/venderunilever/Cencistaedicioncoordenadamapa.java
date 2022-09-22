package com.detesim.venderunilever;

import java.util.ArrayList;
import java.util.Objects;

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


public class Cencistaedicioncoordenadamapa extends FragmentActivity implements OnMapReadyCallback {
	Utilidades theUtilidades = new Utilidades();
	MyLogBLL myLog = new MyLogBLL();

	int clienteId;
	String Origen;
	String peticion;
	String nombres;
	String paterno;
	String materno;
	String ci;
	String celular;
	int zonaId;
	int ciudadId;
	int provinciaId;
	int mercadoId;
	int zonaMercadoId;
	int tipoPagoId;
	int tipoNegocioId;
	int precioListaId;
	String tipoNit;
	String nombreFactura;
	String nit;
	int a;
	int b;
	int c;
	int d;
	int e;
	int f;
	int g;
	int h;
	int i;
	int j;
	int k;
	int l;
	int m;
	int n;
	int o;
	int p;
	int q;
	int r;
	int secuenciaPreventa;
	int secuenciaVenta;
	String casada;
	String direccion;
	String referencia;
	double latitud;
	double longitud;
	boolean activo;

	ArrayList<Cliente> listadoCliente;
	LoginEmpleado loginEmpleado;
	GoogleMap mapa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cencistaedicioncoordenadamapa);

		Bundle localBundle = getIntent().getExtras();

		Origen = "";
		assert localBundle != null;
		Origen = localBundle.getString("Origen");
		assert Origen != null;
		if (Origen.equals("")) {
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el origen de la peticion.", 1);
			return;
		}

		clienteId = 0;
		clienteId = Objects.requireNonNull(getIntent().getExtras()).getInt("clienteId");

		if (clienteId == 0) {
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el clienteId.", 1);
			return;
		}

		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapaEdicionCoordenada);
		mapFragment.getMapAsync(this);

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
		} else {
			peticion = getIntent().getExtras().getString("peticion");
			nombres = getIntent().getExtras().getString("nombres");
			paterno = getIntent().getExtras().getString("paterno");
			materno = getIntent().getExtras().getString("materno");
			ci = getIntent().getExtras().getString("ci");
			celular = getIntent().getExtras().getString("celular");
			ciudadId = getIntent().getExtras().getInt("ciudadId");
			provinciaId = getIntent().getExtras().getInt("provinciaId");
			zonaId = getIntent().getExtras().getInt("zonaId");
			mercadoId = getIntent().getExtras().getInt("mercadoId");
			zonaMercadoId = getIntent().getExtras().getInt("zonaMercadoId");
			direccion = getIntent().getExtras().getString("direccion");
			tipoNegocioId = getIntent().getExtras().getInt("tipoNegocioId");
			precioListaId = getIntent().getExtras().getInt("precioListaId");
			tipoPagoId = getIntent().getExtras().getInt("tipoPagoId");
			tipoNit = getIntent().getExtras().getString("tipoNit");
			nombreFactura = getIntent().getExtras().getString("nombreFactura");
			nit = getIntent().getExtras().getString("nit");
			latitud = getIntent().getExtras().getDouble("latitud");
			longitud = getIntent().getExtras().getDouble("longitud");
			a = getIntent().getExtras().getInt("a");
			b = getIntent().getExtras().getInt("b");
			c = getIntent().getExtras().getInt("c");
			d = getIntent().getExtras().getInt("d");
			e = getIntent().getExtras().getInt("e");
			f = getIntent().getExtras().getInt("f");
			g = getIntent().getExtras().getInt("g");
			h = getIntent().getExtras().getInt("h");
			i = getIntent().getExtras().getInt("i");
			j = getIntent().getExtras().getInt("j");
			k = getIntent().getExtras().getInt("k");
			l = getIntent().getExtras().getInt("l");
			m = getIntent().getExtras().getInt("m");
			n = getIntent().getExtras().getInt("n");
			o = getIntent().getExtras().getInt("o");
			p = getIntent().getExtras().getInt("p");
			q = getIntent().getExtras().getInt("q");
			r = getIntent().getExtras().getInt("r");
			secuenciaPreventa = getIntent().getExtras().getInt("secuenciaPreventa");
			secuenciaVenta = getIntent().getExtras().getInt("secuenciaVenta");
			activo = getIntent().getExtras().getBoolean("activo");
		}
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mapa = googleMap;
		if (mapa != null) {
			if (ObtenerClientes()) {
				if (InicializarMapa()) {
					MostrarCliente();
				} else {
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo cargar el mapa.", 1);
				}
			} else {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener clientes que desplegar.", 1);
			}
		} else {
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo inicializar el mapa.", 1);
		}
	}

	private boolean ObtenerClientes() {
		boolean obtenido = false;
		listadoCliente = null;
		try {
			listadoCliente = new ClienteBLL().ObtenerClientes();
		} catch (Exception localException) {
			if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener los clientes: vacio");
			} else {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener los clientes: " + localException.getMessage());
			}
			return false;
		}

		if (listadoCliente != null) {
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
	    if(listadoCliente !=null)
	    {
	    	ubicacionInicial = new LatLng(listadoCliente.get(0).get_latitud(),
	    								listadoCliente.get(0).get_longitud());
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
	          localMarkerOptions.title("Nueva Coordenada");
	          mapa.addMarker(localMarkerOptions);
	        }
	    });
	 
	    mapa.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
	    {

			@Override
			public boolean onMarkerClick(Marker marker) 
			{
				if ((marker != null) && (marker.getTitle().equals("Nueva Coordenada"))) 
		        {
		            MostrarPantallaCencistaEdicionCliente(marker.getPosition().latitude, marker.getPosition().longitude);
		        }
		        return true;
			}
	    });
	    
	    return true;
	}

	private void MostrarCliente()
	{
		String nombreNegocio;
		
	    for(Cliente item : listadoCliente)
	    {
	    	MarkerOptions localMarkerOptions = new MarkerOptions();
	        localMarkerOptions.position(new LatLng(item.get_latitud(), item.get_longitud()));
	        localMarkerOptions.title(item.get_nombreCompleto());
	        
	        nombreNegocio = ObtenerNombreNegocio(item.get_tipoNegocioId());
	        
	        if(clienteId != item.get_clienteId())
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
	
	private void MostrarPantallaCencistaEdicionCliente(double latitud, double longitud)
	{
	    Intent localIntent = new Intent(this, Censistaeditarcliente.class);
	    localIntent.putExtra("Origen", Origen);
	    localIntent.putExtra("clienteId", clienteId);
	    localIntent.putExtra("peticion", "Coordenada");
	    localIntent.putExtra("nombres", nombres);
	    localIntent.putExtra("paterno", paterno);
	    localIntent.putExtra("materno", materno);
	    localIntent.putExtra("ci", ci);
	    localIntent.putExtra("celular", celular);
	    localIntent.putExtra("ciudadId", ciudadId);
	    localIntent.putExtra("provinciaId", provinciaId);
	    localIntent.putExtra("zonaId", zonaId);
	    localIntent.putExtra("mercadoId", mercadoId);
	    localIntent.putExtra("zonaMercadoId", zonaMercadoId);
	    localIntent.putExtra("tipoNegocioId", tipoNegocioId);
	    localIntent.putExtra("direccion", direccion);
	    localIntent.putExtra("tipoNegocioId", tipoNegocioId);
	    localIntent.putExtra("precioListaId", precioListaId);
	    localIntent.putExtra("tipoPagoId", tipoPagoId);
	    localIntent.putExtra("tipoNit", tipoNit);
	    localIntent.putExtra("nombreFactura", nombreFactura);
	    localIntent.putExtra("nit", nit);
	    localIntent.putExtra("latitud", latitud);
	    localIntent.putExtra("longitud", longitud);
	    localIntent.putExtra("a", a);
	    localIntent.putExtra("b", b);
	    localIntent.putExtra("c", c);
	    localIntent.putExtra("d", d);
	    localIntent.putExtra("e", e);
	    localIntent.putExtra("f", f);
	    localIntent.putExtra("g", g);
	    localIntent.putExtra("h", h);
	    localIntent.putExtra("i", i);
	    localIntent.putExtra("j", j);
	    localIntent.putExtra("k", k);
	    localIntent.putExtra("l", l);
	    localIntent.putExtra("m", m);
	    localIntent.putExtra("n", n);
	    localIntent.putExtra("o", o);
	    localIntent.putExtra("p", p);
	    localIntent.putExtra("q", q);
	    localIntent.putExtra("r", r);
	    localIntent.putExtra("secuenciaPreventa", secuenciaPreventa);
	    localIntent.putExtra("secuenciaVenta", secuenciaVenta); 
	    localIntent.putExtra("activo", activo);
	    startActivity(localIntent);
	}
}
