package Clases;

public class Expedido 
{
	private int _id;
	private String _descripcion;
	
	public Expedido(){}

	public Expedido(int _id, String _descripcion) 
	{
		super();
		this._id = _id;
		this._descripcion = _descripcion;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
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
