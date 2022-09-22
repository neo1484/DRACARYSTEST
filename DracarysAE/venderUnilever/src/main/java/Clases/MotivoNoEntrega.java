package Clases;

public class MotivoNoEntrega 
{
	private int _id;
	private int _motivoId;
	private String _descripcion;
	
	public MotivoNoEntrega(int _id, int _motivoId, String _descripcion) 
	{
		super();
		this._id = _id;
		this._motivoId = _motivoId;
		this._descripcion = _descripcion;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_motivoId() {
		return _motivoId;
	}

	public void set_motivoId(int _motivoId) {
		this._motivoId = _motivoId;
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
