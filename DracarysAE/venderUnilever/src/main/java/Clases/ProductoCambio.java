package Clases;

public class ProductoCambio 
{
	private int _productoId;
	private String _descripcion25;
	int _proveedorId;
	int _categoriaId;
	int _conversion;
	int _precioId;
	float _precio;
	int _costoId;
	
	public ProductoCambio(){}

	public ProductoCambio(int _productoId, String _descripcion25, int _proveedorId, int _categoriaId, int _conversion,
							int _precioId,float _precio,int _costoId) 
	{
		super();
		this._productoId = _productoId;
		this._descripcion25 = _descripcion25;
		this._proveedorId = _proveedorId;
		this._categoriaId = _categoriaId;
		this._conversion = _conversion;
		this._precioId = _precioId;
		this._precio = _precio;
		this._costoId = _costoId;
	}

	public int get_productoId() {
		return _productoId;
	}

	public void set_productoId(int _productoId) {
		this._productoId = _productoId;
	}

	public String get_descripcion25() {
		return _descripcion25;
	}

	public void set_descripcion25(String _descripcion25) {
		this._descripcion25 = _descripcion25;
	}

	public int get_proveedorId() {
		return _proveedorId;
	}

	public void set_proveedorId(int _proveedorId) {
		this._proveedorId = _proveedorId;
	}

	public int get_categoriaId() {
		return _categoriaId;
	}

	public void set_categoriaId(int _categoriaId) {
		this._categoriaId = _categoriaId;
	}

	public int get_conversion() {
		return _conversion;
	}

	public void set_conversion(int _conversion) {
		this._conversion = _conversion;
	}

	public int get_precioId() {
		return _precioId;
	}

	public void set_precioId(int _precioId) {
		this._precioId = _precioId;
	}

	public float get_precio() {
		return _precio;
	}

	public void set_precio(float _precio) {
		this._precio = _precio;
	}

	public int get_costoId() {
		return _costoId;
	}

	public void set_costoId(int _costoId) {
		this._costoId = _costoId;
	}
	
	public String toString()
	{
		return this._descripcion25;
	}
}
