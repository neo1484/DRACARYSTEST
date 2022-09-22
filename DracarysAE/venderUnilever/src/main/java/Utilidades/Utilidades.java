package Utilidades;

import java.util.ArrayList;
import java.util.Calendar;

import com.detesim.venderunilever.R;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;
import BLL.LoginEmpleadoBLL;
import BLL.RolBLL;
import Clases.Fecha;
import Clases.LoginEmpleado;
import Clases.Rol;

public class Utilidades 
{
	public Utilidades(){}
	
	private String _NAMESPACE = "http://detesim.android.org/";
	private int _versionMayor = 3;
	private int _versionMenor = 27;//25

	private int _fondoLogin = R.drawable.fondo_login;
	private int _fondoMenu = R.drawable.fondo_menu;
	private int _fondo = R.drawable.fondo;
	private String _KEY = "~H!=uq;nbI6";

	private String _URL = "https://erodriguez.ventamasiva30.com/WebServices/AppWebService.asmx";
	private String _URLUNILEVER = "https://erodriguez.ventamasiva30.com/WebServices/AppWebService.asmx";
	private String _URLENROQUE = "https://erodriguez.ventamasiva30.com/webservices/Enroquewebservice.asmx";
	private String _URLJSON = "https://erodriguez.ventamasiva30.com/webservices/Jappwebservice.asmx";

	//private String _URL = "https://bodeguerorpg.ventamasiva30.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://bodeguerorpg.ventamasiva30.com/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "https://bodeguerorpg.ventamasiva30.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://bodeguerorpg.ventamasiva30.com/webservices/Jappwebservice.asmx";

	//UNILEVER
	//private String _URL = "https://aiquile.ventamasiva30.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://aiquile.ventamasiva30.com/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "https://aiquile.ventamasiva30.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://aiquile.ventamasiva30.com/webservices/Jappwebservice.asmx";

	//private String _URL = "https://alphabeth.vender40.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://alphabeth.vender40.com/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "https://alphabeth.vender40.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://alphabeth.vender40.com/webservices/Jappwebservice.asmx";

	//private String _URL = "https://bodeguero.ventamasiva30.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://bodeguero.ventamasiva30.com/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "https://bodeguero.ventamasiva30.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://bodeguero.ventamasiva30.com/webservices/Jappwebservice.asmx";
	
	//private String _URL = "https://bodeguero2.ventamasiva30.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://bodeguero2.ventamasiva30.com/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "https://bodeguero2.ventamasiva30.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://bodeguero2.ventamasiva30.com/webservices/Jappwebservice.asmx";
	
	//--------------private String _URL = "http://pulperia.vender30.com/WebServices/AppWebService.asmx";
	//--------------private String _URLUNILEVER = "http://pulperia.vender30.com/WebServices/unileverWebService.asmx";
	//--------------private String _URLENROQUE = "http://pulperia.vender30.com/webservices/Enroquewebservice.asmx";
	//--------------private String _URLJSON = "http://pulperia.vender30.com/webservices/Jappwebservice.asmx";

	//private String _URL = "https://denilson.ventamasiva.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://denilson.ventamasiva.com/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "https://denilson.ventamasiva.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://denilson.ventamasiva.com/webservices/Jappwebservice.asmx";

	//--------------private String _URL = "https://dfc.ventamasiva.com/WebServices/AppWebService.asmx";
	//--------------private String _URLUNILEVER = "https://dfc.ventamasiva.com/WebServices/unileverWebService.asmx";
	//--------------private String _URLENROQUE = "https://dfc.ventamasiva.com/webservices/Enroquewebservice.asmx";
	//--------------private String _URLJSON = "https://dfc.ventamasiva.com/webservices/Jappwebservice.asmx";

	//private String _URL = "https://disjalve.ventamasiva30.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://disjalve.ventamasiva30.com/WebServices/AppWebService.asmx";
	//private String _URLENROQUE = "https://disjalve.ventamasiva30.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://disjalve.ventamasiva30.com/webservices/Jappwebservice.asmx";

	//--------------private String _URL = "https://dma.ventamasiva.com/WebServices/AppWebService.asmx";
	//--------------private String _URLUNILEVER = "https://dma.ventamasiva.com/WebServices/unileverWebService.asmx";
	//--------------private String _URLENROQUE = "https://dma.ventamasiva.com/webservices/Enroquewebservice.asmx";
	//--------------private String _URLJSON = "https://dma.ventamasiva.com/webservices/Jappwebservice.asmx";
	
//	private String _URL = "https://dms.ventamasiva30.com/WebServices/AppWebService.asmx";
//	private String _URLUNILEVER = "https://dms.ventamasiva30.com/WebServices/AppWebService.asmx";
//	private String _URLENROQUE = "https://dms.ventamasiva30.com/webservices/Enroquewebservice.asmx";
//	private String _URLJSON = "https://dms.ventamasiva30.com/webservices/Jappwebservice.asmx";

	//private String _URL = "https://floyan.ventamasiva30.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://floyan.ventamasiva30.com/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "https://floyan.ventamasiva30.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://floyan.ventamasiva30.com/webservices/Jappwebservice.asmx";

	//private String _URL = "https://jcallapa.ventamasiva30.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://jcallapa.ventamasiva30.com/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "https://jcallapa.ventamasiva30.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://jcallapa.ventamasiva30.com/webservices/Jappwebservice.asmx";

	//private String _URL = "https://jhosimarramada.ventamasiva30.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://jhosimarramada.ventamasiva30.com/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "https://jhosimarramada.ventamasiva30.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://jhosimarramada.ventamasiva30.com/webservices/Jappwebservice.asmx";
	
	//private String _URL = "https://jhosimarsrl.ventamasiva30.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://jhosimarsrl.ventamasiva30.com/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "https://jhosimarsrl.ventamasiva30.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://jhosimarsrl.ventamasiva30.com/webservices/Jappwebservice.asmx";

	//private String _URL = "https://jyrsrl.ventamasiva30.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://jyrsrl.ventamasiva30.com/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "https://jyrsrl.ventamasiva30.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://jyrsrl.ventamasiva30.com/webservices/Jappwebservice.asmx";

	//private String _URL = "https://mhuanca.ventamasiva30.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://mhuanca.ventamasiva30.com/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "https://mhuanca.ventamasiva30.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://mhuanca.ventamasiva30.com/webservices/Jappwebservice.asmx";
	
	//--------------private String _URL = "https://montero.ventamasiva.com/WebServices/AppWebService.asmx";
	//--------------private String _URLUNILEVER = "https://montero.ventamasiva.com/WebServices/unileverWebService.asmx";
	//--------------private String _URLENROQUE = "https://montero.ventamasiva.com/webservices/Enroquewebservice.asmx";
	//--------------private String _URLJSON = "https://montero.ventamasiva.com/webservices/Jappwebservice.asmx";
	
	//private String _URL = "https://multicompras.ventamasiva30.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://multicompras.ventamasiva30.com/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "https://multicompras.ventamasiva30.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://multicompras.ventamasiva30.com/webservices/Jappwebservice.asmx";

	//private String _URL = "https://reacorpbeni.ventamasiva30.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://reacorpbeni.ventamasiva30.com/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "https://reacorpbeni.ventamasiva30.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://reacorpbeni.ventamasiva30.com/webservices/Jappwebservice.asmx";

	//private String _URL = "https://sedim.ventamasiva.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://sedim.ventamasiva.com/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "https://sedim.ventamasiva.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://sedim.ventamasiva.com/webservices/Jappwebservice.asmx";
	
	//private String _URL = "https://soluciones.ventamasiva30.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://soluciones.ventamasiva30.com/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "https://soluciones.ventamasiva30.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://soluciones.ventamasiva30.com/webservices/Jappwebservice.asmx";
	
	//private String _URL = "https://tellez.ventamasiva30.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://tellez.ventamasiva30.com/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "https://tellez.ventamasiva30.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://tellez.ventamasiva30.com/webservices/Jappwebservice.asmx";

	//private String _URL = "https://ubamol.ventamasiva30.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://ubamol.ventamasiva30.com/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "https://ubamol.ventamasiva30.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://ubamol.ventamasiva30.com/webservices/Jappwebservice.asmx";
	
	//private String _URL = "https://videurlapaz.ventamasiva30.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://videurlapaz.ventamasiva30.com/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "https://videurlapaz.ventamasiva30.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://videurlapaz.ventamasiva30.com/webservices/Jappwebservice.asmx";
	
	//private String _URL = "https://videuroruro.ventamasiva30.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://videuroruro.ventamasiva30.com/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "https://videuroruro.ventamasiva30.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://videuroruro.ventamasiva30.com/webservices/Jappwebservice.asmx";
	
	//private String _URL = "https://vmorales.ventamasiva30.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://vmorales.ventamasiva30.com/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "https://vmorales.ventamasiva30.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://vmorales.ventamasiva30.com/webservices/Jappwebservice.asmx";
	
	//private String _URL = "https://wflores.ventamasiva30.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://wflores.ventamasiva30.com/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "https://wflores.ventamasiva30.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://wflores.ventamasiva30.com/webservices/Jappwebservice.asmx";

	//DETESIM
	///////////////////////////////////////////////// Recordar cambiar en strings "Vender Unilever" por "Vender" y el icono "ic_launcher_unilever" por "ic_launcher_detesim"

	//private String _URL = "https://sanignacio.vender40.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://sanignacio.vender40.com/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "https://sanignacio.vender40.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://sanignacio.vender40.com/webservices/Jappwebservice.asmx";

	//private String _URL = "https://todoonline.vender40.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://todoonline.vender40.com/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "https://todoonline.vender40.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://todoonline.vender40.com/webservices/Jappwebservice.asmx";

	//TEST
	//private String _URL = "https://test.ventamasiva30.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://test.ventamasiva30.com/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "https://test.ventamasiva30.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://test.ventamasiva30.com/webservices/Jappwebservice.asmx";

	//private String _URL = "https://unilevervacio.ventamasiva.com/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "https://unilevervacio.ventamasiva.com/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "https://unilevervacio.ventamasiva.com/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "https://unilevervacio.ventamasiva.com/webservices/Jappwebservice.asmx";

	//private String _URL = "http://192.168.0.26/dracarys/WebServices/AppWebService.asmx";
	//private String _URLUNILEVER = "http://192.168.0.26/dracarys/WebServices/unileverWebService.asmx";
	//private String _URLENROQUE = "http://192.168.0.26/dracarys/webservices/Enroquewebservice.asmx";
	//private String _URLJSON = "http://192.168.0.26/dracarys/webservices/Jappwebservice.asmx";
			
	public String get_URL()
	{
	  return this._URL;
	}
	
	public void set_URL(String _URL) 
	{
		this._URL = _URL;
	}
	
	public String get_URLUNILEVER() {
		return _URLUNILEVER;
	}
	
	public void set_URLUNILEVER(String _URLUNILEVER) {
		this._URLUNILEVER = _URLUNILEVER;
	}

	public String get_URLENROQUE() {
		return _URLENROQUE;
	}

	public void set_URLENROQUE(String _URLENROQUE) {
		this._URLENROQUE = _URLENROQUE;
	}

	public String get_URLJSON() {
		return _URLJSON;
	}

	public void set_URLJSON(String _URLJSON) {
		this._URLJSON = _URLJSON;
	}

	public String get_NAMESPACE() 
	{
		return _NAMESPACE;
	}

	public void set_NAMESPACE(String _NAMESPACE) 
	{
		this._NAMESPACE = _NAMESPACE;
	}

	public int get_versionMayor() 
	{
		return _versionMayor;
	}

	public void set_versionMayor(int _versionMayor) 
	{
		this._versionMayor = _versionMayor;
	}

	public int get_versionMenor() 
	{
		return _versionMenor;
	}

	public void set_versionMenor(int _versionMenor) 
	{
		this._versionMenor = _versionMenor;
	}

	public int get_fondoLogin() {
		return _fondoLogin;
	}

	public int get_fondoMenu() {
		return _fondoMenu;
	}

	public void set_fondoMenu(int _fondoMenu) {
		this._fondoMenu = _fondoMenu;
	}

	public void set_fondoLogin(int _fondoLogin) {
		this._fondoLogin = _fondoLogin;
	}

	public int get_fondo() {
		return _fondo;
	}

	public void set_fondo(int _fondo) {
		this._fondo = _fondo;
	}
	
	public String get_KEY() {
		return _KEY;
	}

	public void set_KEY(String _KEY) {
		this._KEY = _KEY;
	}

	public Fecha ObtenerFecha()
	{
	  final Fecha localFecha = new Fecha();
	  Calendar localCalendar = Calendar.getInstance();
	  localFecha.set_dia(localCalendar.get(Calendar.DATE));
	  localFecha.set_mes(localCalendar.get(Calendar.MONTH)+1);
	  localFecha.set_anio(localCalendar.get(Calendar.YEAR));
	  localFecha.set_hora(localCalendar.get(Calendar.HOUR_OF_DAY));
	  localFecha.set_minuto(localCalendar.get(Calendar.MINUTE));
	  localFecha.set_segundo(localCalendar.get(Calendar.SECOND));
	  localFecha.set_diaSemana(localCalendar.get(Calendar.DAY_OF_WEEK));
	  return localFecha;
	}
	
	public String ObtenerFechaString()
	{
		Fecha fecha = ObtenerFecha();
		String fechaString  = FormatearTextoACerosAnterior(String.valueOf(fecha.get_dia()),2) + "/" 
							+ FormatearTextoACerosAnterior(String.valueOf(fecha.get_mes()),2) + "/"
							+ FormatearTextoACerosAnterior(String.valueOf(fecha.get_anio()),2);
		
		return fechaString;
	}
	
	public String ObtenerFechaHoraWSString()
	{
		Fecha fecha = ObtenerFecha();
		String fechaString  = String.valueOf(fecha.get_anio()) + "-" 
							+ FormatearTextoACerosAnterior(String.valueOf(fecha.get_mes()), 2) + "-"
							+ FormatearTextoACerosAnterior(String.valueOf(fecha.get_anio()), 2) + " "
							+ FormatearTextoACerosAnterior(String.valueOf(fecha.get_hora()), 2) + ":"
							+ FormatearTextoACerosAnterior(String.valueOf(fecha.get_minuto()), 2) + ":"
							+ FormatearTextoACerosAnterior(String.valueOf(fecha.get_segundo()), 2);
		
		return fechaString;
	}
	
	public String ObtenerFechaLiteralString()
	{
		Fecha fecha = ObtenerFecha();
		
		String fechaString  = String.valueOf(fecha.get_dia()) + " de " 
							+ ObtnerMesLiteral(fecha.get_mes()) + " de "
							+ String.valueOf(fecha.get_anio());
		
		return fechaString;
	}
	
	public String ObtnerMesLiteral(int mes)
	{
		String mesLiteral = "";
		switch(mes)
		{
		case 1: mesLiteral = "Enero";
			break;
		case 2: mesLiteral = "Febrero";
			break;
		case 3: mesLiteral = "Marzo";
			break;
		case 4: mesLiteral = "Abril";
			break;
		case 5: mesLiteral = "Mayo";
			break;
		case 6: mesLiteral = "Junio";
			break;
		case 7: mesLiteral = "Julio";
			break;
		case 8: mesLiteral = "Agosto";
			break;
		case 9: mesLiteral = "Septiembre";
			break;
		case 10: mesLiteral = "Octubre";
			break;
		case 11: mesLiteral = "Noviembre";
			break;
		case 12: mesLiteral = "Diciembre";
			break;				
		}
		return mesLiteral;
	}
	
	public String ObtenerFechaHoraString()
	{
		Fecha fecha = ObtenerFecha();
		String fechaString  = FormatearTextoACerosAnterior(String.valueOf(fecha.get_dia()),2) + "/" 
					+ FormatearTextoACerosAnterior(String.valueOf(fecha.get_mes()),2) + "/" 
					+ FormatearTextoACerosAnterior(String.valueOf(fecha.get_anio()),2) + " "
					+ FormatearTextoACerosAnterior(String.valueOf(fecha.get_hora()),2) + ":" 
					+ FormatearTextoACerosAnterior(String.valueOf(fecha.get_minuto()),2) + ":" 
					+ FormatearTextoACerosAnterior(String.valueOf(fecha.get_segundo()),2);
		
		return fechaString;
	}

	public String FormatearTextoACerosAnterior(String cadena,int totalTamanio)
	{
		String resultado = "";
		int tamanioActual = cadena.length();
		for(int i=0;i<(totalTamanio-tamanioActual);i++)
		{
			resultado += "0";
		}
		resultado += cadena;
		return resultado;
	}
	
	public String FormatearTextoADimensionPosterior(String cadena,int totalTamanio)
	{
		String resultado = cadena;
		int tamanioActual = cadena.length();
		for(int i=tamanioActual;i<=totalTamanio;i++)
		{
			resultado += " ";
		}
		return resultado;
	}
	
	public String FormatearTextoADimensionAnterior(String cadena,int totalTamanio)
	{
		String resultado = "";
		int tamanioActual = cadena.length();
		for(int i=0;i<(totalTamanio-tamanioActual);i++)
		{
			resultado += " ";
		}
		resultado += cadena;
		return resultado;
	}
	
	public String CentrarTexto(int tamanioTotal,String texto)
	{
		String resultado = "";
		int margen = (tamanioTotal - texto.length()) /2;
		for(int i=1;i<margen;i++)
		{
			resultado += " ";
		}
		resultado += texto;
		return resultado;		
	}
	
	public LoginEmpleado ObtenerLoginEmpleado() throws Exception
	{
		LoginEmpleadoBLL localLoginEmpleadoBLL = new LoginEmpleadoBLL();
		LoginEmpleado localLoginEmpleado = null;
		Fecha fecha = ObtenerFecha();
		int dia = fecha.get_dia();
		int mes = fecha.get_mes();
		int anio = fecha.get_anio();
		try
		{
			localLoginEmpleado = localLoginEmpleadoBLL.ObtenerLoginEmpleadoDetallePor(dia,mes,anio);
			return localLoginEmpleado;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				Log.e("App","Error al obtener loginEmpleadoDetallePorfecha: vacio");
			}
			else
			{
				Log.e("App","Error al obtener loginEmpleadoDetallePorfecha: " + localException.getMessage());
			}
			throw localException;
		}
	}
	
	public boolean ValidarFecha(int dia,int mes,int anio)
	{
		Fecha fecha = ObtenerFecha();
		if(dia == fecha.get_dia() && mes == fecha.get_mes() && anio == fecha.get_anio())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void MostrarMensaje(Context contexto, String mensaje, int duracion)
	{
	    if (duracion == 1) 
	    {
	    	Toast.makeText(contexto,mensaje,Toast.LENGTH_SHORT).show();
	    }
	    else if(duracion == 2) 
	    {
	    	Toast.makeText(contexto,mensaje,Toast.LENGTH_LONG).show();
	    }
	    else
	    {
	    	Toast.makeText(contexto,mensaje,Toast.LENGTH_SHORT).show();
	    }
	}

	public boolean VerificarConexionInternet(Context contexto)
	{
		NetworkInfo localNetworkInfo = null;
		try
		{
			localNetworkInfo = ((ConnectivityManager)contexto.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		    return (localNetworkInfo != null) && (localNetworkInfo.isConnected() && (localNetworkInfo.isAvailable()));
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				Log.e("App","Error al verificar la conexion a internet: vacio");
			}
			else
			{
				Log.e("App","Error al verificar la conexion a internet: " + localException.getMessage());
			}
			return false;
		}	    
	}
	
	public boolean VerificarConexionInternetDatos(Context contexto)
	{
		ConnectivityManager localConnectivityManager = null;
		try
		{
			localConnectivityManager = (ConnectivityManager)contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				Log.e("App","Error al verificar la conexion a internet (Datos): vacio");
			}
			else
			{
				Log.e("App","Error al verificar la conexion a internet (Datos): " + localException.getMessage());
			}
			return false;
		}
		
	    if (localConnectivityManager != null)
	    {
	      NetworkInfo localNetworkInfo = localConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	      
	      if (localNetworkInfo != null)
	      {
	    	  if(localNetworkInfo.isConnected())
	    	  {
	    		  return true;
	    	  }
	      }
	    }
	    return false;
	}
	
	public boolean VerificarConexionInternetWifi(Context contexto)
	{
		ConnectivityManager localConnectivityManager = null;
		try
		{
			localConnectivityManager = (ConnectivityManager)contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				Log.e("App","Error al verificar la conexion a internet (Wifi): vacio");
			}
			else
			{
				Log.e("App","Error al verificar la conexion a internet (Wifi): " + localException.getMessage());
			}
			return false;
		}
	    if (localConnectivityManager != null)
	    {
	      NetworkInfo localNetworkInfo = localConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	      
	      if (localNetworkInfo != null)
	      {
	    	  if(localNetworkInfo.isConnected())
	    	  {
	    		  return true;
	    	  }
	      }
	    }
	    return false;
	}
	
	public int ObtenerDiaId()
	{
		Fecha fecha = ObtenerFecha();
	    if(fecha.get_diaSemana() == 1)
	    {
	    	return 7;
	    }
	    else
	    {
	    	return fecha.get_diaSemana() - 1;
	    }
	}

	public boolean VerificarExistenciaRolEnUsuario(int empleadoId,String rolUsuario) throws Exception
	{
		boolean verificado = false;
		ArrayList<Rol> listadoRol = null;
		try
		{
			listadoRol = new RolBLL().ObtenerRolesPor(empleadoId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				Log.e("App","Error al obtener loginEmpleadoDetallePorfecha: vacio");
			}
			else
			{
				Log.e("App","Error al obtener loginEmpleadoDetallePorfecha: " + localException.getMessage());
			}
			throw localException;
		}
		
		if(listadoRol == null)
		{
			return verificado;
		}
		else
		{
			for(Rol rol : listadoRol)
			{
				if(rol.get_rol().equals(rolUsuario))
				{
					verificado = true;
				}
			}
		}
		
		return verificado;
	}
}
