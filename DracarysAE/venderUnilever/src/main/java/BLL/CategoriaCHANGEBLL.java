package BLL;

import java.util.ArrayList;

import Clases.CategoriaCHANGE;
import DAL.CategoriaCHANGEDAL;
import android.database.Cursor;

public class CategoriaCHANGEBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarCategoriasCHANGE() throws Exception
	{
		try
		{
			boolean bool = new CategoriaCHANGEDAL().BorrarCategoriasCHANGE();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las categoriasCHANGE: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los categoriasCHANGE: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
	
	public boolean BorrarCategoriaCHANGEPor(long id) throws Exception
	{
		try
		{
			boolean bool = new CategoriaCHANGEDAL().BorrarCategoriaCHANGEPor(id);
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar la categoriaCHANGE por id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar la categoriaCHANGE por id: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
		  
	public long InsertarCategoriaCHANGE(int categoriaId,String nombre,int proveedorId) throws Exception
	{
		try
	    {
			long l = new CategoriaCHANGEDAL().InsertarCategoriaCHANGE(categoriaId, nombre, proveedorId);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar la categoriaCHANGE: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la categoriaCHANGE: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	  }

	public ArrayList<CategoriaCHANGE> ObtenerCategoriasCHANGE() throws Exception
	{
		ArrayList<CategoriaCHANGE> listadoCategoriaCHANGE = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new CategoriaCHANGEDAL().ObtenerCategoriasCHANGE();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las categoriasCHANGE: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las categoriasCHANGE: " + localException.getMessage());
			} 
			return listadoCategoriaCHANGE;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoCategoriaCHANGE = new ArrayList<CategoriaCHANGE>();
			
			do
			{
				CategoriaCHANGE CategoriaCHANGE = new CategoriaCHANGE(localCursor.getInt(1),localCursor.getString(2),localCursor.getInt(3));
				
				listadoCategoriaCHANGE.add(CategoriaCHANGE);
			}
			while(localCursor.moveToNext());
		}
		return listadoCategoriaCHANGE;
	}
	
	public ArrayList<CategoriaCHANGE> ObtenerCategoriasCHANGEPorProveedorId(int proveedorId) throws Exception
	{
		ArrayList<CategoriaCHANGE> listadoCategoriaCHANGE = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new CategoriaCHANGEDAL().ObtenerCategoriasCHANGEPorProveedorId(proveedorId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las categoriasCHANGE por proveedorId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las categoriasCHANGE por proveedorId: " + localException.getMessage());
			} 
			return listadoCategoriaCHANGE;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoCategoriaCHANGE = new ArrayList<CategoriaCHANGE>();
			
			do
			{
				CategoriaCHANGE CategoriaCHANGE = new CategoriaCHANGE(localCursor.getInt(1),localCursor.getString(2),localCursor.getInt(3));
				
				listadoCategoriaCHANGE.add(CategoriaCHANGE);
			}
			while(localCursor.moveToNext());
		
		}
		return listadoCategoriaCHANGE;
	}
}