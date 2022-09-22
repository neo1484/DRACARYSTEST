package Clases;

import android.content.Context;
import android.content.SharedPreferences;

public class AdminSessions
{
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";

    public AdminSessions(Context context)
    {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void SaveSession(LoginEmpleado loginEmpleado)
    {
        editor.putInt(SESSION_KEY, loginEmpleado.get_empleadoId()).commit();
    }

    public int GetSession()
    {
        return sharedPreferences.getInt(SESSION_KEY, -1);
    }

    public void RemoveSession()
    {
        editor.putInt(SESSION_KEY, -1).commit();
    }
}
