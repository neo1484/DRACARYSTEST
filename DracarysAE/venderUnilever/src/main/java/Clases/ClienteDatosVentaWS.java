package Clases;

public class ClienteDatosVentaWS {
	 private int Id;
    private int ClienteId;
    private float PromedioSemanal;
    private int IntensidadCompra;
    private String UltimaCompraFecha;
    private float UltimaCompraMonto;
    private String EscadoPreventaFecha;
    private String EscadoPreventa;
    private String EscadoVentaFecha;
    private String EscadoVenta;
    
    public ClienteDatosVentaWS(){}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getClienteId() {
		return ClienteId;
	}

	public void setClienteId(int clienteId) {
		ClienteId = clienteId;
	}

	public float getPromedioSemanal() {
		return PromedioSemanal;
	}

	public void setPromedioSemanal(float promedioSemanal) {
		PromedioSemanal = promedioSemanal;
	}

	public int getIntensidadCompra() {
		return IntensidadCompra;
	}

	public void setIntensidadCompra(int intensidadCompra) {
		IntensidadCompra = intensidadCompra;
	}

	public String getUltimaCompraFecha() {
		return UltimaCompraFecha;
	}

	public void setUltimaCompraFecha(String ultimaCompraFecha) {
		UltimaCompraFecha = ultimaCompraFecha;
	}

	public float getUltimaCompraMonto() {
		return UltimaCompraMonto;
	}

	public void setUltimaCompraMonto(float ultimaCompraMonto) {
		UltimaCompraMonto = ultimaCompraMonto;
	}

	public String getEscadoPreventaFecha() {
		return EscadoPreventaFecha;
	}

	public void setEscadoPreventaFecha(String escadoPreventaFecha) {
		EscadoPreventaFecha = escadoPreventaFecha;
	}

	public String getEscadoPreventa() {
		return EscadoPreventa;
	}

	public void setEscadoPreventa(String escadoPreventa) {
		EscadoPreventa = escadoPreventa;
	}

	public String getEscadoVentaFecha() {
		return EscadoVentaFecha;
	}

	public void setEscadoVentaFecha(String escadoVentaFecha) {
		EscadoVentaFecha = escadoVentaFecha;
	}

	public String getEscadoVenta() {
		return EscadoVenta;
	}

	public void setEscadoVenta(String escadoVenta) {
		EscadoVenta = escadoVenta;
	}
}
