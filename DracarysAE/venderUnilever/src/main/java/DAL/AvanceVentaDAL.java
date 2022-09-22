package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.AvanceVendedorDiaWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class AvanceVentaDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarAvancesVenta() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarAvancesVenta();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVenta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVenta: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	  public boolean BorrarAvanceVentaPor(int vendedorId) throws Exception
	  {
	    db.OpenDB();
	    try
	    {
	    	db.borrarAvanceVentaPorVendedorId(vendedorId);
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	           	myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVenta por vendedorId.: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVenta por vendedorId.: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	  public boolean BorrarAvanceVentaPorTipoAvanceVentaYRol(String tipoAvanceVenta,String rol) throws Exception
	  {
	    db.OpenDB();
	    try
	    {
	    	db.borrarAvanceVentaPorTipoAvanceVentaYRol(tipoAvanceVenta,rol);
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	           	myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVenta por tipoAvanceVenta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVenta por tipoAvanceVenta: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	  public long InsertarAvanceVenta(ArrayList<AvanceVendedorDiaWSResult> avancesVenta, int mes) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  long l = db.insertarAvanceVenta( avancesVenta, mes);
			  return l;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los avances venta: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar los avances venta: " + localException.getMessage());
				} 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }

	public long InsertarAvanceVenta(int vendedorId, int dia,int mes,int anio,String nombreVendedor,float presupuesto,
									float avance,float tendencia,float cobertura,String tipoAvanceVenta,
									String rol,String nombreProveedor,int noPreventas,float coberturaPorcentaje) throws Exception
	{
		db.OpenDB();
		try
		{
			long l = db.insertarAvanceVenta(vendedorId, dia, mes, anio, nombreVendedor, presupuesto,
					avance, tendencia, cobertura, tipoAvanceVenta,rol,
					nombreProveedor,noPreventas,coberturaPorcentaje);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el avanceVenta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el avanceVenta: " + localException.getMessage());
			}
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  
	  public Cursor ObtenerAvancesVentaPor(int vendedorId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerAvanceVentaPor(vendedorId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
           			myLog.InsertarLog("App",this.toString(),1,"Error al obtener los avancesVenta por vendedorId: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los avancesVenta por vendedorId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerAvancesVentaPorTipoAvanceVentaYRol(String tipoAvanceVenta,String rol) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerAvanceVentaPorTipoAvanceVentaYRol(tipoAvanceVenta,rol);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
           			myLog.InsertarLog("App",this.toString(),1,"Error al obtener los avancesVenta por tipoAvanceVenta: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los avancesVenta por tipoAvanceVenta: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
