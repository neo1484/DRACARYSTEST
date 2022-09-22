package BLL;

import java.util.ArrayList;
import Clases.Categoria;
import Clases.CategoriaWSResult;
import DAL.CategoriaDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class CategoriaBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarCategorias() throws Exception
	{
	    try
	    {
	    	boolean bool = new CategoriaDAL().BorrarCategorias();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las categorias: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las categorias: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public boolean BorrarCategoriasPor(int categoriaId) throws Exception
	{
	    try
	    {
	    	boolean bool = new CategoriaDAL().BorrarCategoriasPor(categoriaId);
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las categorias por categoriaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las categorias por categoriaId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long InsertarCategoria(ArrayList<CategoriaWSResult> categorias) throws Exception
	{
		try
		{
			long l = new CategoriaDAL().InsertarCategoria(categorias);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las categorias: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las categorias: " + localException.getMessage());
			} 
			throw localException;
		}
	}
	   
	public ArrayList<Categoria> ObtenerCategoriasPorProveedor(int proveedorId) throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<Categoria> listadoCategoria = null;
	    
	    try
	    {
	    	localCursor = new CategoriaDAL().ObtenerCategoriaPorProveedor(proveedorId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las categorias por proveedorId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las categoria por proveedorId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoCategoria = new ArrayList<Categoria>();
	        	do
	        	{
	        		Categoria localCategoria = new Categoria(localCursor.getInt(0),localCursor.getInt(1),localCursor.getString(2),
	        				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5), localCursor.getInt(6), localCursor.getInt(7),
	        				localCursor.getInt(8), localCursor.getInt(9));
	        		
	        		listadoCategoria.add(localCategoria);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoCategoria;
	}
	
	public Categoria ObtenerCategoria(int categoriaId) throws Exception
	{
	    Cursor localCursor = null;
	    Categoria localCategoria = null;
	    
	    try
	    {
	    	localCursor = new CategoriaDAL().ObtenerCategoriaPor(categoriaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener la categoria por categoriaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la categoria por categoriaId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
        		localCategoria = new Categoria(localCursor.getInt(0),localCursor.getInt(1),localCursor.getString(2),
        				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5), localCursor.getInt(6), localCursor.getInt(7),
        				localCursor.getInt(8), localCursor.getInt(9));
	        }
	    }
	    
	    return localCategoria;
	}

}
