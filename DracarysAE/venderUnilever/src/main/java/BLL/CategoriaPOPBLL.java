package BLL;

import java.util.ArrayList;

import Clases.CategoriaPOP;
import Clases.CategoriaPOPWSResult;
import DAL.CategoriaPOPDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class CategoriaPOPBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarCategoriasPOP() throws Exception
	{
		try
		{
			boolean bool = new CategoriaPOPDAL().BorrarCategoriasPOP();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las categoriasPOP: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los categoriasPOP: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
	
	public boolean BorrarCategoriaPOPPor(long id) throws Exception
	{
		try
		{
			boolean bool = new CategoriaPOPDAL().BorrarCategoriaPOPPor(id);
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar la categoriaPOP por id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar la categoriaPOP por id: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
		  
	public long InsertarCategoriaPOP(ArrayList<CategoriaPOPWSResult> categoriasPop) throws Exception
	{
		try
	    {
			long l = new CategoriaPOPDAL().InsertarCategoriaPOP(categoriasPop);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar las categorias POP: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar las categorias POP: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	  }

	public ArrayList<CategoriaPOP> ObtenerCategoriasPOP() throws Exception
	{
		ArrayList<CategoriaPOP> listadoCategoriaPOP = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new CategoriaPOPDAL().ObtenerCategoriasPOP();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las categoriasPOP: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las categoriasPOP: " + localException.getMessage());
			} 
			return listadoCategoriaPOP;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoCategoriaPOP = new ArrayList<CategoriaPOP>();
			
			do
			{
				CategoriaPOP CategoriaPOP = new CategoriaPOP(localCursor.getInt(1),localCursor.getString(2),localCursor.getInt(3));
				
				listadoCategoriaPOP.add(CategoriaPOP);
			}
			while(localCursor.moveToNext());
			
		}
		return listadoCategoriaPOP;
	}
	
	public ArrayList<CategoriaPOP> ObtenerCategoriasPOPPorProveedorId(int proveedorId) throws Exception
	{
		ArrayList<CategoriaPOP> listadoCategoriaPOP = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new CategoriaPOPDAL().ObtenerCategoriasPOPPorProveedorId(proveedorId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las categoriasPOP por proveedorId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las categoriasPOP por proveedorId: " + localException.getMessage());
			} 
			return listadoCategoriaPOP;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoCategoriaPOP = new ArrayList<CategoriaPOP>();
			
			do
			{
				CategoriaPOP CategoriaPOP = new CategoriaPOP(localCursor.getInt(1),localCursor.getString(2),localCursor.getInt(3));
				
				listadoCategoriaPOP.add(CategoriaPOP);
			}
			while(localCursor.moveToNext());
			
		}
		return listadoCategoriaPOP;
	}
}
