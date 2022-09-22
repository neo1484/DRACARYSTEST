package Clases;

public class ProductoCHANGE 
{
	private int _productoId;
	private String _descripcion25;
	private int _categoriaId;
	private String _codigoBarra;
	
	public ProductoCHANGE(){}

	public ProductoCHANGE(int _productoId, String _descripcion25, int _categoriaId, String _codigoBarra) 
	{
		super();
		this._productoId = _productoId;
		this._descripcion25 = _descripcion25;
		this._categoriaId = _categoriaId;
		this._codigoBarra = _codigoBarra;
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

	public int get_categoriaId() {
		return _categoriaId;
	}

	public void set_categoriaId(int _categoriaId) {
		this._categoriaId = _categoriaId;
	}

	public String get_codigoBarra() {
		return _codigoBarra;
	}

	public void set_codigoBarra(String _codigoBarra) {
		this._codigoBarra = _codigoBarra;
	}
}
