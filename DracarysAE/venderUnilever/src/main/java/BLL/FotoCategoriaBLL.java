package BLL;

import java.util.ArrayList;

import Clases.FotoCategoria;
import Clases.FotoCategoriaWSResult;
import DAL.FotoCategoriaDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class FotoCategoriaBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarFotosCategoria() throws Exception
	{
	    try
	    {
	    	boolean bool = new FotoCategoriaDAL().BorrarFotosCategoria();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las fotosCategoria: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las fotosCategoria: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  	  
	public long InsertarFotoCategoria(ArrayList<FotoCategoriaWSResult> fotosCategoria) throws Exception
	{
		try
		{
			long l = new FotoCategoriaDAL().InsertarFotoCategoria(fotosCategoria);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las categorias de las fotos: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las categorias de las fotos: " + localException.getMessage());
			} 
			throw localException;
		}
	}
	   
	public FotoCategoria ObtenerFotoCategoriaPor(int categoriaId) throws Exception
	{
	    Cursor localCursor = null;
	    FotoCategoria fotoCategoria = null;
	    
	    try
	    {
	    	localCursor = new FotoCategoriaDAL().ObtenerFotoCategoriaPor(categoriaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener la fotoCategoria: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la fotoCategoria: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null && localCursor.getCount() > 0)
	    {
	        fotoCategoria = new FotoCategoria(localCursor.getInt(1),localCursor.getString(2));
	    }
	    
	    return fotoCategoria;
	}
	
	public ArrayList<FotoCategoria> ObtenerFotosCategoria() throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<FotoCategoria> listadoFotoCategoria = null;
	    
	    try
	    {
	    	localCursor = new FotoCategoriaDAL().ObtenerFotosCategoria();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las fotosCategoria: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las fotosCategoria: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null && localCursor.getCount() > 0)
	    {
        	listadoFotoCategoria = new ArrayList<FotoCategoria>();
        	listadoFotoCategoria.add(new FotoCategoria(0,"[Seleccione un item ...]"));
        	do
        	{
        		FotoCategoria localFotoCategoria = new FotoCategoria(localCursor.getInt(1),localCursor.getString(2));
        		
        		listadoFotoCategoria.add(localFotoCategoria);
        	} 
        	while (localCursor.moveToNext());
	        
	    }
	    
	    return listadoFotoCategoria;
	}
}
