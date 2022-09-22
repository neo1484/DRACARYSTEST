package Clases;

public class RelevamientoDetalleWS 
{
	private int RelevamientoId;
	private String Nombre;
	private int tipoNegocioId;
	private String CategoriaId;
	private long FotoSize;
	private double Latitud;
	private double Longitud;
	
	public RelevamientoDetalleWS(){}

	public RelevamientoDetalleWS(int relevamientoId, String nombre, int tipoNegocioId, String categoriaId,
			long fotoSize, double latitud, double longitud) {
		super();
		RelevamientoId = relevamientoId;
		Nombre = nombre;
		this.tipoNegocioId = tipoNegocioId;
		CategoriaId = categoriaId;
		FotoSize = fotoSize;
		Latitud = latitud;
		Longitud = longitud;
	}

	public int getRelevamientoId() {
		return RelevamientoId;
	}

	public void setRelevamientoId(int relevamientoId) {
		RelevamientoId = relevamientoId;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public int getTipoNegocioId() {
		return tipoNegocioId;
	}

	public void setTipoNegocioId(int tipoNegocioId) {
		this.tipoNegocioId = tipoNegocioId;
	}

	public String getCategoriaId() {
		return CategoriaId;
	}

	public void setCategoriaId(String categoriaId) {
		CategoriaId = categoriaId;
	}

	public long getFotoSize() {
		return FotoSize;
	}

	public void setFotoSize(long fotoSize) {
		FotoSize = fotoSize;
	}

	public double getLatitud() {
		return Latitud;
	}

	public void setLatitud(double latitud) {
		Latitud = latitud;
	}

	public double getLongitud() {
		return Longitud;
	}

	public void setLongitud(double longitud) {
		Longitud = longitud;
	}
}

