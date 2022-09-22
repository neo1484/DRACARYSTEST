package Clases;

public class Producto 
{
	private int _productoId;
	private String _codigo;
	private String _descripcion25;
	private int _proveedorId;
	private int _conversion;
	private int _categoriaId;
	private String _descripcionUnidad25;
	private boolean _productoDePromocion;
	private int _productoReferenciaId;
	
	public Producto(){}

	public Producto(int _productoId, String _codigo, String _descripcion25,
			int _proveedorId, int _conversion, int _categoriaId,String _descripcionUnidad25,
			boolean _productoDePromocion,int _productoReferenciaId) 
	{
		super();
		this._productoId = _productoId;
		this._codigo = _codigo;
		this._descripcion25 = _descripcion25;
		this._proveedorId = _proveedorId;
		this._conversion = _conversion;
		this._categoriaId = _categoriaId;
		this._descripcionUnidad25 = _descripcionUnidad25;
		this._productoDePromocion = _productoDePromocion;
		this._productoReferenciaId = _productoReferenciaId;
	}

	public int get_productoId() {
		return _productoId;
	}

	public void set_productoId(int _productoId) {
		this._productoId = _productoId;
	}

	public String get_codigo() {
		return _codigo;
	}

	public void set_codigo(String _codigo) {
		this._codigo = _codigo;
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

	public int get_conversion() {
		return _conversion;
	}

	public void set_conversion(int _conversion) {
		this._conversion = _conversion;
	}
	
	public String toString()
	{
	    return this._descripcion25;
	}

	public int get_categoriaId() {
		return _categoriaId;
	}

	public void set_categoriaId(int _categoriaId) {
		this._categoriaId = _categoriaId;
	}

	public String get_descripcionUnidad25() {
		return _descripcionUnidad25;
	}

	public void set_descripcionUnidad25(String _descripcionUnidad25) {
		this._descripcionUnidad25 = _descripcionUnidad25;
	}

	public boolean is_productoDePromocion() {
		return _productoDePromocion;
	}

	public void set_productoDePromocion(boolean _productoDePromocion) {
		this._productoDePromocion = _productoDePromocion;
	}

	public int get_productoReferenciaId() {
		return _productoReferenciaId;
	}

	public void set_productoReferenciaId(int _productoReferenciaId) {
		this._productoReferenciaId = _productoReferenciaId;
	}
}
