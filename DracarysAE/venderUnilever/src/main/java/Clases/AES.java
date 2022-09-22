package Clases;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import BLL.MyLogBLL;
import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;
import Utilidades.Utilidades;

import android.util.Log;

public class AES 
{
    Utilidades theUtilidades = new Utilidades();
    MyLogBLL myLog = new MyLogBLL();

    public String Encrypt(String text, String key) throws Exception
    {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if (len > keyBytes.length)
            len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);

        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        byte[] results = cipher.doFinal(text.getBytes("UTF-8"));
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(results); // it returns the result as a String
    }

    public String Decrypt(String text, String key) throws Exception
    {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //this parameters should not be changed

        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if (len > keyBytes.length)
            len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);

        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        byte[] results = new byte[text.length()];
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            results = cipher.doFinal(decoder.decodeBuffer(text));
        }
        catch (Exception e)
        {
            Log.i("Erron in Decryption", e.toString());
        }
        Log.i("Data", new String(results, "UTF-8"));
        return new String(results, "UTF-8"); // it returns the result as a String
    }

    public String Encriptar(String texto, String key)
    {
        try
        {
            return Encrypt(texto, key);
        }catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App", this.toString(),1,"Error al obtener la imagen del producto por porductoId: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al obtener la imagen dle producto por productoId: " + localException.getMessage());
            }
            return "";
        }
    }

    public String Desencriptar(String texto, String key)
    {
        try
        {
            return Decrypt(texto, key);
        }catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al obtener la imagen del producto por porductoId: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al obtener la imagen dle producto por productoId: " + localException.getMessage());
            }
            return "";
        }
    }
}
