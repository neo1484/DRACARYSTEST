package Clases;

public class AlmacenPOPProducto 
{
	private int _id;
	private int _almacenId;
	private int productoid;
	private int _saldo;
	
	public AlmacenPOPProducto(){}

	public AlmacenPOPProducto(int _id, int _almacenId, int productoid, int _saldo) 
	{
		super();
		this._id = _id;
		this._almacenId = _almacenId;
		this.productoid = productoid;
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
		return productoid;
	}

	public void setProductoid(int productoid) {
		this.productoid = productoid;
	}

	public int get_saldo() {
		return _saldo;
	}

	public void set_saldo(int _saldo) {
		this._saldo = _saldo;
	}
}
