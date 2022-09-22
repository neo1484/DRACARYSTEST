package Utilidades;

import java.math.BigDecimal;
import java.math.RoundingMode;

import Clases.Fecha;

public class Facturacion 
{
	public Facturacion(){}
	
	public String ObtenerMontoEnPalabras(double numMonto)
	{
		int lnEntero;
		String lcRetorno;
		int lnTerna;
		//String lcMiles;
		String lcCadena;
		int lnUnidades;
		int lnDecenas;
		int lnCentenas;
		int lnFraccion;
		
		lnEntero = (int)numMonto;
		if((numMonto - lnEntero) > 0)
		{ 
			double valor = numMonto - lnEntero;
			valor = Double.parseDouble(new BigDecimal(valor).setScale(2, RoundingMode.HALF_UP).toString());
			lnFraccion = (int)(valor*100);
		}
		else
		{
			lnFraccion = 0;
		}
		
		lcRetorno = "";
		lnTerna = 1;
		
		while(lnEntero > 0)
		{
			//Recorro la terna
			lcCadena = "";
			lnUnidades = lnEntero % 10;
			lnEntero = (int)(lnEntero/10);
			lnDecenas = lnEntero % 10;
			lnEntero = (int)(lnEntero/10);
			lnCentenas = lnEntero % 10;
			lnEntero = (int)(lnEntero/10);
			
			//Analizo las unidades
			switch(lnUnidades)
			{
			case 1:
				if(lnTerna == 1)
				{
					lcCadena += "UNO " + lcCadena;
				}
				else
				{
					lcCadena += "UN " + lcCadena;
				}
				break;
			case 2: 
				lcCadena += "DOS " + lcCadena;
				break;
			case 3:
				lcCadena += "TRES " + lcCadena;
				break;
			case 4:
				lcCadena += "CUATRO " +lcCadena;
				break;
			case 5:
				lcCadena += "CINCO " + lcCadena;
				break;
			case 6:
				lcCadena += "SEIS " + lcCadena;
				break;
			case 7:
				lcCadena += "SIETE " + lcCadena;
				break;
			case 8:
				lcCadena += "OCHO " + lcCadena;
				break;
			case 9:
				lcCadena += "NUEVE " + lcCadena;
				break;
			}
			
			//Analizo las decenas
			switch(lnDecenas)
			{
			case 1:
				switch(lnUnidades)
				{
				case 0:
					lcCadena = "DIEZ ";
					break;
				case 1:
					lcCadena = "ONCE ";
					break;
				case 2:
					lcCadena = "DOCE ";
					break;
				case 3:
					lcCadena = "TRECE ";
					break;
				case 4:
					lcCadena = "CATORCE ";
					break;
				case 5:
					lcCadena = "QUINCE ";
					break;
				default:
					lcCadena = "DIECI" + lcCadena;
					break;
				}
				break;
			case 2:
				if(lnUnidades == 0)
				{
					lcCadena = "VEINTE " + lcCadena;
				}
				else
				{
					lcCadena = "VEINTI" + lcCadena;
				}
				break;
			case 3:
				if(lnUnidades == 0)
				{
					lcCadena = "TREINTA " + lcCadena;
				}
				else
				{
					lcCadena = "TREINTA Y " + lcCadena;
				}
				break;
			case 4:
				if(lnUnidades == 0)
				{
					lcCadena = "CUARENTA " + lcCadena;
				}
				else
				{
					lcCadena = "CUARENTA Y " + lcCadena;
				}
				break;
			case 5:
				if(lnUnidades == 0)
				{
					lcCadena = "CINCUENTA " + lcCadena;
				}
				else
				{
					lcCadena = "CINCUENTA Y " + lcCadena;
				}
				break;
			case 6:
				if(lnUnidades == 0)
				{
					lcCadena = "SESENTA " + lcCadena;
				}
				else
				{
					lcCadena = "SESENTA Y " + lcCadena;
				}
				break;
			case 7:
				if(lnUnidades == 0)
				{
					lcCadena = "SETENTA " + lcCadena;
				}
				else
				{
					lcCadena = "SETENTA Y " + lcCadena;
				}
				break;
			case 8:
				if(lnUnidades == 0)
				{
					lcCadena = "OCHENTA " + lcCadena;
				}
				else
				{
					lcCadena = "OCHENTA Y " + lcCadena;
				}
				break;
			case 9:
				if(lnUnidades == 0)
				{
					lcCadena = "NOVENTA " + lcCadena;
				}
				else
				{
					lcCadena = "NOVENTA Y " + lcCadena;
				}
				break;
			default:
				break;
			}
			
			//Analizo las centenas
			
			switch(lnCentenas)
			{
			case 1:
				if(lnUnidades == 0 && lnDecenas == 0)
				{
					lcCadena = "CIEN " + lcCadena;
				}
				else
				{
					lcCadena = "CIENTO " + lcCadena;
				}
				break;
			case 2:
				lcCadena = "DOSCIENTOS " + lcCadena;
				break;
			case 3:
				lcCadena = "TRESCIENTOS " + lcCadena;
				break;
			case 4:
				lcCadena = "CUATROCIENTOS " + lcCadena;
				break;
			case 5:
				lcCadena = "QUINIENTOS " + lcCadena;
				break;
			case 6:
				lcCadena = "SEISCIENTOS " + lcCadena;
				break;
			case 7:
				lcCadena = "SETECIENTOS " + lcCadena;
				break;
			case 8:
				lcCadena = "OCHOCIENTOS " + lcCadena;
				break;
			case 9:
				lcCadena = "NOVECIENTOS " + lcCadena;
				break;
			default:
				break;
			}
			
			//Analizo la terna
			switch(lnTerna)
			{
			case 1:
				break;
			case 2:
				if(lnUnidades + lnDecenas + lnCentenas != 0)
				{
					lcCadena += " MIL ";
				}
				break;
			case 3:
				if((lnUnidades + lnDecenas + lnCentenas != 0) && lnUnidades == 1 && lnDecenas == 0 && lnCentenas == 0)
				{
					lcCadena += " MILLON ";
				}
				if((lnUnidades + lnDecenas + lnCentenas != 0) && !(lnUnidades == 1 && lnDecenas == 0 && lnCentenas == 0))
				{
					lcCadena += " MILLONES ";
				}
				break;
			case 4:
				if(lnUnidades + lnDecenas + lnCentenas !=0)
				{
					lcCadena += " MIL MILLONES ";
				}
				break;
			default:
				break;
			}
			
			//Armo el retorno terna a terna
			lcRetorno = lcCadena + lcRetorno;
			lnTerna += 1;
		}
		
		if(lnTerna ==1)
		{
			lcRetorno = "CERO";
		}
		
		if(lnFraccion>0)
		{
			lcRetorno = lcRetorno + "CON " + lnFraccion + "/100";
		}
		else
		{
			lcRetorno = lcRetorno + "CON 00/100";
		}
		lcRetorno.replaceAll("\\s+"," ");

		return lcRetorno;
	}

	public String ValidarCaracterEspecial(String cadena)
	{
		char[] cadenaFinal= new char[cadena.length()*2];
		int posicionCadena=0;
		char caracter;
		for(int i=0;i<cadena.length();i++)
		{
			caracter = cadena.charAt(i);
			switch(caracter)
			{
			case '\\':
				cadenaFinal[posicionCadena]=caracter;
				posicionCadena++;
				cadenaFinal[posicionCadena]=caracter;
				posicionCadena++;
				break;
			default:
				cadenaFinal[posicionCadena] = caracter;
				posicionCadena++;
				break;					
			}
		}
		return new String(cadenaFinal,0,posicionCadena);
	}
	
	public String GenerarcodigoControl(String varAutorizacion, String varFactura,String varNit,Fecha datFecha, 
										Double decMonto,String varKeyDosificacion)
	{
		int intFecha;
		int intMonto;
		
		String varAutorizacion_Aux;
		String varFactura_Aux;
		String varNIT_Aux;
		String varFecha_Aux;
		String varMonto_Aux;
		
		String varVerhoeff_Aux;
		long bintVerhoeff_Total;
		String varVerhoeff_Suma;
		String varVerhoeff_Digito;
		int intVerhoeff_Posicion;
		int intVerhoeff_Cantidad;
		
		String varAlleged;
		
		int intContador;
		int intContador_Aux;
		String varCadenaCon;
		
		int VNNU_CONT_ANTE;
		int VNNU_AUXI;
		int VNTO_SUMA_ASCI;
		int VNNU_SP01=0;
		int VNNU_SP02=0;
		int VNNU_SP03=0;
		int VNNU_SP04=0;
		int VNNU_SP05=0;
		//int VNNU_SP01_STOT;
		//int VNNU_SP02_STOT;
		//int VNNU_SP03_STOT;
		//int VNNU_SP04_STOT;
		//int VNNU_SP05_STOT;
		int VNNU_SP00_TOTA;
		int VNNU_SP00_STOT;
		String VSNU_SP00_STOT;
		String VSNU_BA64;
		String VSDE_MENS;
		
		//Poner la fecha en formato AAAAMMDD
		intFecha = Integer.parseInt(datFecha.get_anio() + RellenaCeroIzquierda(String.valueOf(datFecha.get_mes())) + RellenaCeroIzquierda(String.valueOf(datFecha.get_dia())));
		intMonto = Integer.parseInt(new BigDecimal(decMonto).setScale(0,RoundingMode.HALF_UP).toString());
		
		//SETeamos valores a las variables
		varAutorizacion_Aux = varAutorizacion;
		varFactura_Aux = varFactura;
		varNIT_Aux = varNit;
		varFecha_Aux = String.valueOf(intFecha);
		varMonto_Aux = String.valueOf(intMonto);
		
		//************************************ paso 1 ******************************************/
		// concatenar consecutivamente 2 digitos verhoeff
		
		intContador = 0;
		while(intContador<=1)
		{
			//Factura
			varVerhoeff_Aux = "";
			varVerhoeff_Aux = String.valueOf(ChecksumVerhoeff(varFactura_Aux));
			varFactura_Aux = varFactura_Aux + varVerhoeff_Aux;
			
			//NIT
			varVerhoeff_Aux = "";
			varVerhoeff_Aux = String.valueOf(ChecksumVerhoeff(varNIT_Aux));
			varNIT_Aux = varNIT_Aux + varVerhoeff_Aux;
			
			//FECHA
			varVerhoeff_Aux = "";
			varVerhoeff_Aux = String.valueOf(ChecksumVerhoeff(varFecha_Aux));
			varFecha_Aux = varFecha_Aux + varVerhoeff_Aux;
			
			//MONTO
			varVerhoeff_Aux = "";
			varVerhoeff_Aux = String.valueOf(ChecksumVerhoeff(varMonto_Aux));
			varMonto_Aux = varMonto_Aux + varVerhoeff_Aux;
			
			intContador = intContador + 1;
		}		
		
		//suma de los resultados
		
		bintVerhoeff_Total = Long.parseLong(varFactura_Aux) + Long.parseLong(varNIT_Aux) 
							 + Long.parseLong(varFecha_Aux) + Long.parseLong(varMonto_Aux);
		
		//5 digitos verhoeff
		
		intContador = 0;
		varVerhoeff_Suma = String.valueOf(bintVerhoeff_Total);
		
		while(intContador <= 4)
		{
			varVerhoeff_Aux = "";
			varVerhoeff_Aux = String.valueOf(ChecksumVerhoeff(varVerhoeff_Suma));
			varVerhoeff_Suma = varVerhoeff_Suma + varVerhoeff_Aux;
			intContador = intContador + 1;			
		}
		
		//obtengo los 5  ultimos digitos de verhoeff obtenido
		varVerhoeff_Digito = SubString(varVerhoeff_Suma,varVerhoeff_Suma.length()-4,5);
		
		//************************************ paso 2 ******************************************/
		
		//suma 1 a cada digito verhoeff obstenido
		intContador = 0;
		intVerhoeff_Posicion = 1;
		intVerhoeff_Cantidad = 0;

		while(intContador < varVerhoeff_Digito.length())
		{
			varVerhoeff_Aux = "";
			intVerhoeff_Posicion = intVerhoeff_Posicion + intVerhoeff_Cantidad;
			intVerhoeff_Cantidad = Integer.valueOf(SubString(varVerhoeff_Digito,intContador+1,1)) + 1;
			varVerhoeff_Aux = SubString(varKeyDosificacion,intVerhoeff_Posicion,intVerhoeff_Cantidad);
			intContador = intContador + 1;
			
			if(intContador == 1)
			{
				varAutorizacion_Aux = varAutorizacion_Aux + varVerhoeff_Aux;
			}
			if(intContador == 2)
			{
				varFactura_Aux = varFactura_Aux + varVerhoeff_Aux;
			}
			if(intContador == 3)
			{
				varNIT_Aux = varNIT_Aux + varVerhoeff_Aux;
			}
			if(intContador == 4)
			{
				varFecha_Aux = varFecha_Aux + varVerhoeff_Aux;
			}
			if(intContador == 5)
			{
				varMonto_Aux = varMonto_Aux + varVerhoeff_Aux;
			}
		}
 
		//************************************ paso 3 ******************************************/
		
		//concateno la cadena formada por la concatenacion de todas las anteriores
		varCadenaCon = varAutorizacion_Aux + varFactura_Aux + varNIT_Aux + varFecha_Aux + varMonto_Aux;
		
		//aplicar el allegedRC4
		varAlleged = CripAllegedRC4(varCadenaCon,(varKeyDosificacion + varVerhoeff_Digito));
		
		//************************************ paso 4 ******************************************/
		//obtener la sumatoria de todos los ascii de la cadena resultante anterior
		intContador = 0;
		intContador_Aux = 0;
		
		while(intContador <= varVerhoeff_Digito.length())
		{
			intContador = intContador + 1;
			intContador_Aux = intContador;
			VNNU_AUXI = 0;
			
			while(intContador_Aux <= varAlleged.length())
			{
				VNNU_AUXI = VNNU_AUXI + (int)SubString(varAlleged,intContador_Aux,1).charAt(0);
				intContador_Aux = intContador_Aux + 5;
			}
			
			if(intContador == 1)
			{
				VNNU_SP01 = VNNU_AUXI;
			}
			
			if(intContador == 2)
			{
				VNNU_SP02 = VNNU_AUXI;
			}
			 
			if(intContador == 3)
			{
				VNNU_SP03 = VNNU_AUXI;
			}
			if(intContador == 4)
			{
				VNNU_SP04 = VNNU_AUXI;
			}
			if(intContador == 5)
			{
				VNNU_SP05 = VNNU_AUXI;
			}
		}
		 
		VNTO_SUMA_ASCI = VNNU_SP01 + VNNU_SP02 + VNNU_SP03 + VNNU_SP04 + VNNU_SP05;
			
		//************************************ paso 5 ******************************************/
		intContador = 0;
		intVerhoeff_Cantidad = 0;
		VNNU_SP00_TOTA = 0;
		VSNU_SP00_STOT = "";
		
		while(intContador <= varVerhoeff_Digito.length())
		{
			VSNU_SP00_STOT = "";
			intContador = intContador + 1;
			if(SubString(varVerhoeff_Digito,intContador,1) != "")
			{
				intVerhoeff_Cantidad = Integer.parseInt(SubString(varVerhoeff_Digito,intContador,1)) + 1;	
			}
			else
			{
				intVerhoeff_Cantidad = 1;
			}
			
			if(intContador == 1)
			{
				VSNU_SP00_STOT = String.valueOf(Math.round((VNTO_SUMA_ASCI * VNNU_SP01)/intVerhoeff_Cantidad));
			}
			if(intContador == 2)
			{
				VSNU_SP00_STOT = String.valueOf(Math.round((VNTO_SUMA_ASCI * VNNU_SP02)/intVerhoeff_Cantidad));
			}
			if(intContador == 3)
			{
				VSNU_SP00_STOT = String.valueOf(Math.round((VNTO_SUMA_ASCI * VNNU_SP03)/intVerhoeff_Cantidad));
			}
			if(intContador == 4)
			{
				VSNU_SP00_STOT = String.valueOf(Math.round((VNTO_SUMA_ASCI * VNNU_SP04)/intVerhoeff_Cantidad));
			}
			if(intContador == 5)
			{
				VSNU_SP00_STOT = String.valueOf(Math.round((VNTO_SUMA_ASCI * VNNU_SP05)/intVerhoeff_Cantidad));
			}
			
			if(VSNU_SP00_STOT != "")
			{
				VNNU_SP00_STOT = Integer.parseInt(VSNU_SP00_STOT);
			}
			else
			{
				VNNU_SP00_STOT = 0;
			}
			
			VNNU_SP00_TOTA = VNNU_SP00_TOTA + VNNU_SP00_STOT;
		}
		
		//base 64
		VSNU_BA64 = AlgorithmBaseX(VNNU_SP00_TOTA,64);

		///************************************ paso 6 ******************************************/ 
		//aplicar allegedRC64
		varAlleged = CripAllegedRC4(VSNU_BA64 ,(varKeyDosificacion+varVerhoeff_Digito));
		
		//************************************ paso 7 ******************************************/ 
		// generacion de codigo control
		intContador = 0;
		VNNU_CONT_ANTE = 0;
		VSDE_MENS = "";
		
		while(intContador < varAlleged.length())
		{
			intContador = intContador + 1;
		
			if((intContador % 2) == 0)
			{
				VSDE_MENS = VSDE_MENS + SubString(varAlleged,VNNU_CONT_ANTE,2) + '-';
			}
			else
			{
				VNNU_CONT_ANTE = intContador;
			}
		}
		//return  VSDE_MENS.substring(2,VSDE_MENS.length()); //--@varAutorizacion_Aux  --
		VSDE_MENS = SubString(VSDE_MENS,1,VSDE_MENS.length()-1);
		return VSDE_MENS;
	}
	
	public String RellenaCeroIzquierda(String varNumero)
	{
		String campo="";
		int longitud;
		String resultado;
		
		resultado = "";
		longitud = varNumero.length();
		for(int i=0;i<2-longitud;i++)
		{
			campo += "0";
		}
		resultado = campo + varNumero;
		
		return resultado;		
	}
	
	public int ChecksumVerhoeff(String NUMBER)
	{
		int c;
		int len;
		int m;
		String n;
		int i;
		
		/* Declare the Arrays */
		String d;
		String p;
		String inv;
		int check;
		d="0123456789123406789523401789563401289567401239567859876043216598710432765982104387659321049876543210";
		p="01234567891576283094580379614289160435279453126870428657390127938064157046913258";
		inv="0432156789";
		
		/* Start Processing */
		c = 0;
		n = Reverse(NUMBER);
		len = n.length();

		i=0;
		while(i<len)
		{
			/* Do the CalcChecksum */
			m = Integer.parseInt(SubString(p,(((i+1)%8)*10) + Integer.parseInt(SubString(n,i+1,1)) +1 ,1));
			c = Integer.parseInt(SubString(d,c*10+m+1,1));
			i++;
		}
		
		check = Integer.parseInt(SubString(inv,c+1,1));
		
		return check;
				
	}
	
	public String SubString(String cadena,int start,int tamanio)
	{
		String resultado="";
		int cont = 1;
		for(int i=0;i<cadena.length();i++)
		{
			char letra = cadena.charAt(i);
			if(i+1 >= start)
			{
				if(cont <= tamanio )
				{
					resultado += letra;
				}
				cont++;
			}
		}
		return resultado;
	} 
	
	public String Reverse(String cadena)
	{
		StringBuffer buffer = new StringBuffer(cadena);
		return buffer.reverse().toString();
	}
	
	public String AlgorithmBaseX(int intNumero,int intBase)
	{
		String diccionario;
		int cociente;
		String palabra;
		int resto;
		
		diccionario = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz+/";
		cociente = 1;
		resto = 0;
		palabra = "";	
		
		while(cociente > 0)
		{
			cociente = intNumero / intBase;
			resto = intNumero % intBase;
			palabra = SubString(diccionario,resto+1,1) + palabra;
			intNumero = cociente;
		}
		
		return palabra;
	}
	
	public String CripAllegedRC4(String varMensaje,String varKey)
	{
		int x,y;
		int z,i,a,b,c,d,e,f;
		
		int index1,index2,nmen;
		
        String var1,var2,varMensajeCifrado;
        
        int[][] vector1 = new int[256][3];
        
        x = 0;
        y = 0;
        z = 0;
        i = 0;
        a = 0;
        b = 0;
        c = 0;
        d = 0;
        e = 0;
        f = 0;
        //g = 0;
        index1 = 0;
        index2 = 0;
        nmen = 0;
        varMensajeCifrado = "";
        var1 = "";
        var2 = "";
        
        //simula vector, inserta en variable table los numeros de 0 hasta 255
		while(i <= 255)
		{
			vector1[i][0]=i;
			vector1[i][1]=i;
			vector1[i][2]=i;
			i = i + 1;
		}
		
		//bloque 1
		i = 0;
		while(i <= 255)
		{
			if(vector1[i][1] == i)
			{
				c=vector1[i][0];
			}
			
			int ascii = (int)SubString(varKey,index1+1,1).charAt(0);
			index2 = (ascii+ c + index2) % 256;

			//inicio-cambiar posicion
			if(vector1[i][1] == i)
			{
				a = vector1[i][0];//primer antiguo
			}
			
			b = vector1[index2][0];//segundo antiguo
			
			vector1[index2][0] = a;

			vector1[i][0] = b;

			//fin-cambiar posicion
			index1 = (index1 + 1) % varKey.length();
			i = i + 1;
		}
		
		//bloque 2
		i = 0;
		a = 0;
		b = 0;

		while(i <= varMensaje.length() - 1)
		{
			z = 0;
			x = (x + 1) % 256;
  
			z = vector1[x][0];
  
			y = (z + y) % 256;

			//inicio cambiar posicion
			a = vector1[x][0];
			
			b = vector1[y][0];
			
			vector1[y][0] = a;
			
			vector1[x][0] = b;
			//fin cambiar posicion
  
			d = vector1[x][0];
			
			e = vector1[y][0];
			
			f = vector1[(d+e)%256][0];
						
			nmen = ((int)SubString(varMensaje,i+1,1).charAt(0)) ^ f;

			var1 = AlgorithmBaseX(nmen, 16);
			var2 = RellenaCeroIzquierda(var1);

			varMensajeCifrado = varMensajeCifrado + var2;
			i = i + 1;
		}
		
		return varMensajeCifrado;
	}
}
