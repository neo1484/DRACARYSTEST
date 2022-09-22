package Clases;

public class SingleId
{
    private int _id;
    private String _username;
    private String _token;

    public SingleId(int _id, String _username, String _token) {
        this._id = _id;
        this._username = _username;
        this._token = _token;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public String get_token() {
        return _token;
    }

    public void set_token(String _token) {
        this._token = _token;
    }
}
