package Clases;

public class PreventaPOP 
{
	private int _id;
	private int _preventaIdServer;
	
	public PreventaPOP(){}

	public PreventaPOP(int _id, int _preventaIdServer) 
	{
		super();
		this._id = _id;
		this._preventaIdServer = _preventaIdServer;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_preventaIdServer() {
		return _preventaIdServer;
	}

	public void set_preventaIdServer(int _preventaIdServer) {
		this._preventaIdServer = _preventaIdServer;
	};
}
