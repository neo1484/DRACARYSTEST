package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.AvanceVentaVendedorWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class AvanceVentaVendedorDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarAvancesVentaVendedor() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarAvancesVentaVendedor();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVentaVendedor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVentaVendedor: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	  public long InsertarAvanceVentaVendedor(ArrayList<AvanceVentaVendedorWSResult> avancesVentaVendedor, int dia) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  long l = db.insertarAvanceVentaVendedor(avancesVentaVendedor, dia);
			  return l;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los avances venta vendedor: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar los avances venta vendedor: " + localException.getMessage());
				} 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerAvancesVentaVendedor() throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerAvancesVentaVendedor();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
           			myLog.InsertarLog("App",this.toString(),1,"Error al obtener los avancesVentaVendedor por vendedorId: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los avancesVentaVendedor por vendedorId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
