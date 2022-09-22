package Clases;

public class ProductoCosto 
{
	private int _id;
	private int _costoId;
	private int _productoId;
	private float _costoUnitario;
	private float _costoPaquete;
	private float _cpp;

	public ProductoCosto(){}

	public ProductoCosto(int _id, int _costoId, int _productoId, float _costoUnitario,
			float _costoPaquete, float _cpp) 
	{
		super();
		this._id = _id;
		this._costoId = _costoId;
		this._productoId = _productoId;
		this._costoUnitario = _costoUnitario;
		this._costoPaquete = _costoPaquete;
		this._cpp = _cpp;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_costoId() {
		return _costoId;
	}

	public void set_costoId(int _costoId) {
		this._costoId = _costoId;
	}

	public int get_productoId() {
		return _productoId;
	}

	public void set_productoId(int _productoId) {
		this._productoId = _productoId;
	}

	public float get_costoUnitario() {
		return _costoUnitario;
	}

	public void set_costoUnitario(float _costoUnitario) {
		this._costoUnitario = _costoUnitario;
	}

	public float get_costoPaquete() {
		return _costoPaquete;
	}

	public void set_costoPaquete(float _costoPaquete) {
		this._costoPaquete = _costoPaquete;
	}

	public float get_cpp() {
		return _cpp;
	}

	public void set_cpp(float _cpp) {
		this._cpp = _cpp;
	}
}
