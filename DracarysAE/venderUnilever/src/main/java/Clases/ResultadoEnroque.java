package Clases;

public class ResultadoEnroque 
{
	private int Id;
	private String Mensaje;
	
	public ResultadoEnroque(){}

	public ResultadoEnroque(int id, String mensaje) {
		super();
		Id = id;
		Mensaje = mensaje;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getMensaje() {
		return Mensaje;
	}

	public void setMensaje(String mensaje) {
		Mensaje = mensaje;
	}
}
