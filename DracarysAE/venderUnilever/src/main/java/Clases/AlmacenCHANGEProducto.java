package Clases;

public class AlmacenCHANGEProducto 
{
	private int _id;
	private int _almacenId;
	private int _productoid;
	private int _saldo;

	public AlmacenCHANGEProducto(){}

	public AlmacenCHANGEProducto(int _id, int _almacenId, int _productoid, int _saldo) 
	{
		super();
		this._id = _id;
		this._almacenId = _almacenId;
		this._productoid = _productoid;
		this._saldo = _saldo;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_almacenId() {
		return _almacenId;
	}

	public void set_almacenId(int _almacenId) {
		this._almacenId = _almacenId;
	}

	public int getProductoid() {
		return _productoid;
	}

	public void setProductoid(int productoid) {
		this._productoid = productoid;
	}

	public int get_saldo() {
		return _saldo;
	}

	public void set_saldo(int _saldo) {
		this._saldo = _saldo;
	}
	
	
}
