package Clases;

public class CategoriaCHANGE 
{
	private int _categoriaId;
	private String _nombre;
	private int _proveedorId;
	
	public CategoriaCHANGE(){}

	public CategoriaCHANGE(int _categoriaId, String _nombre, int _proveedorId) 
	{
		super();
		this._categoriaId = _categoriaId;
		this._nombre = _nombre;
		this._proveedorId = _proveedorId;
	}

	public int get_categoriaId() {
		return _categoriaId;
	}

	public void set_categoriaId(int _categoriaId) {
		this._categoriaId = _categoriaId;
	}

	public String get_nombre() {
		return _nombre;
	}

	public void set_nombre(String _nombre) {
		this._nombre = _nombre;
	}

	public int get_proveedorId() {
		return _proveedorId;
	}

	public void set_proveedorId(int _proveedorId) {
		this._proveedorId = _proveedorId;
	}
}
