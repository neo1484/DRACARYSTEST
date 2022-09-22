package Clases;

public class DiaSemana 
{
	private int _id;
	private int _diaSemanaId;
	private String _descripcion;

	public DiaSemana(){}

	public DiaSemana(int _id, int _diaSemanaId, String _descripcion) 
	{
		super();
		this._id = _id;
		this._diaSemanaId = _diaSemanaId;
		this._descripcion = _descripcion;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_diaSemanaId() {
		return _diaSemanaId;
	}

	public void set_diaSemanaId(int _diaSemanaId) {
		this._diaSemanaId = _diaSemanaId;
	}

	public String get_descripcion() {
		return _descripcion;
	}

	public void set_descripcion(String _descripcion) {
		this._descripcion = _descripcion;
	}

	public String toString()
	{
	    return this._descripcion;
	}
}
