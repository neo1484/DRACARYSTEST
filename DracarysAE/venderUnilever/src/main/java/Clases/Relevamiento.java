package Clases;

public class Relevamiento 
{
	private int RelevamientoId;
	private String Nombre;
	private String Fecha;
	private int EmpleadoId;
	private String Descripcion;
	private boolean Activo;
	
	public Relevamiento(int relevamientoId, String nombre, String fecha, int empleadoId, String descripcion,
			boolean activo) 
	{
		super();
		RelevamientoId = relevamientoId;
		Nombre = nombre;
		Fecha = fecha;
		EmpleadoId = empleadoId;
		Descripcion = descripcion;
		Activo = activo;
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

	public String getFecha() {
		return Fecha;
	}

	public void setFecha(String fechaCreacion) {
		Fecha = fechaCreacion;
	}

	public int getEmpleadoId() {
		return EmpleadoId;
	}

	public void setEmpleadoId(int empleadoId) {
		EmpleadoId = empleadoId;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public boolean isActivo() {
		return Activo;
	}

	public void setActivo(boolean activo) {
		Activo = activo;
	}
}
