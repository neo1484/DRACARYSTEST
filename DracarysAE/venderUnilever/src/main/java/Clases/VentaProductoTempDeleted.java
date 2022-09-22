package Clases;

public class VentaProductoTempDeleted 
{
	private int _id;
	private int _rowId;
	private int _tempId;
	private int _empleadoId;
	private int _clienteId;
	private int _motivoId;
	private boolean _estadoSincronizacion;
	
	public VentaProductoTempDeleted(){}

	public VentaProductoTempDeleted(int _id, int _rowId,int _tempId, int _empleadoId,
			int _clienteId, int _motivoId, boolean _estadoSincronizacion) 
	{
		super();
		this._id = _id;
		this._rowId = _rowId;
		this._tempId = _tempId;
		this._empleadoId = _empleadoId;
		this._clienteId = _clienteId;
		this._motivoId = _motivoId;
		this._estadoSincronizacion = _estadoSincronizacion;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_rowId() {
		return _rowId;
	}

	public void set_rowId(int _rowId) {
		this._rowId = _rowId;
	}

	public int get_tempId() {
		return _tempId;
	}

	public void set_tempId(int _tempId) {
		this._tempId = _tempId;
	}

	public int get_empleadoId() {
		return _empleadoId;
	}

	public void set_empleadoId(int _empleadoId) {
		this._empleadoId = _empleadoId;
	}

	public int get_clienteId() {
		return _clienteId;
	}

	public void set_clienteId(int _clienteId) {
		this._clienteId = _clienteId;
	}

	public int get_motivoId() {
		return _motivoId;
	}

	public void set_motivoId(int _motivoId) {
		this._motivoId = _motivoId;
	}

	public boolean is_estadoSincronizacion() {
		return _estadoSincronizacion;
	}

	public void set_estadoSincronizacion(boolean _estadoSincronizacion) {
		this._estadoSincronizacion = _estadoSincronizacion;
	}
}
