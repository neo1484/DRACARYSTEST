package Clases;

public class RelevamientoDetalle 
{
	private int RelevamientoDetalleId;
	private int RelevamientoId;
	private String Nombre;
	private int TipoNegocioId;
	private String CategoriaId;
	private int FotoSize;
	private byte[] FotoBinary;
	private double Latitud;
	private double Longitud;
	private int Sincro;
	
	public RelevamientoDetalle(int relevamientoDetalleId, int relevamientoId, String nombre, int tipoNegocioId, 
			String categoriaId, int fotoSize, byte[] fotoBinary, double latitud, double longitud, int sincro) 
	{
		super();
		RelevamientoDetalleId = relevamientoDetalleId;
		RelevamientoId = relevamientoId;
		Nombre = nombre;
		TipoNegocioId = tipoNegocioId;
		CategoriaId = categoriaId;
		FotoSize = fotoSize;
		FotoBinary = fotoBinary;
		Latitud = latitud;
		Longitud = longitud;
		Sincro = sincro;
	}

	public int getRelevamientoDetalleId() {
		return RelevamientoDetalleId;
	}

	public void setRelevamientoDetalleId(int relevamientoDetalleId) {
		RelevamientoDetalleId = relevamientoDetalleId;
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
		return TipoNegocioId;
	}

	public void setTipoNegocioId(int tipoNegocioId) {
		TipoNegocioId = tipoNegocioId;
	}

	public String getCategoriaId() {
		return CategoriaId;
	}

	public void setCategoriaId(String categoriaId) {
		CategoriaId = categoriaId;
	}

	public int getFotoSize() {
		return FotoSize;
	}

	public void setFotoSize(int fotoSize) {
		FotoSize = fotoSize;
	}

	public byte[] getFotoBinary() {
		return FotoBinary;
	}

	public void setFotoBinary(byte[] fotoBinary) {
		FotoBinary = fotoBinary;
	}

	public double getLatitud() {
		return Latitud;
	}

	public void setLatitud(double latitud) {
		this.Latitud = latitud;
	}

	public double getLongitud() {
		return Longitud;
	}

	public void setLongitud(double longitud) {
		this.Longitud = longitud;
	}

	public int getSincro() {
		return Sincro;
	}

	public void setSincro(int sincro) {
		Sincro = sincro;
	}
}
