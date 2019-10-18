/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.empresa.proyecto.ws;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import static org.apache.commons.lang3.text.WordUtils.capitalizeFully;

@WebService(serviceName = "WS200")
public class WS200 {

    /**
     * Web service operation 1
     *
     * @param RUTconDV
     * @return
     */
    @WebMethod(operationName = "ValidarDV")
    public String ValidarDV(@WebParam(name = "RUTconDV") String RUTconDV) {
        //Algoritmo del 'Modulo 11' para calcular el digito verificador del Rol Unico Tributario (RUT)
        //1. Extraer los digitos del RUT para convertir el nuevo string en... 
        //...un entero y descomponer el DV del resto del RUT
        int DV;
        int RUTsDV;
        char[] chain = RUTconDV.toCharArray();
        String n = "";
        for (int i = 0; i < chain.length; i++) {
            if (Character.isDigit(chain[i])) {
                n += chain[i];
            }
        }
        if (chain[(chain.length) - 1] == 'K' || chain[(chain.length) - 1] == 'k') {
            DV = 10;
            RUTsDV = Integer.parseInt(n);
        } else {
            DV = (Integer.parseInt(n)) % 10;
            RUTsDV = (Integer.parseInt(n)) / 10;
        }
        //FIN 1
        //2. Algoritmo del modulo 11 y verificacion del RUT
        int suma = 0;
        int factor = 2;
        int aux = 0;
        while (RUTsDV / 10 != 0) {
            suma += (RUTsDV % 10) * factor;
            factor++;
            if (factor > 7) {
                factor = 2;
            }
            RUTsDV /= 10;
            aux = RUTsDV;
        }
        aux *= factor;
        suma += aux;
        int DivEntera = (suma / 11) * 11;
        int Diferencia = suma - DivEntera;
        int Discriminador = 11;
        Discriminador -= Diferencia;
        if (Discriminador == 11) {
            Discriminador -= 11;
        }
        if (Discriminador != DV) {
            return "RUT NO VALIDO";
        } else {
            return "RUT VALIDO";
        }
    }

    /**
     * Web service operation
     * @param ApePat
     * @param ApeMat
     * @param Nombres
     * @param Genero
     * @return 
     */
    @WebMethod(operationName = "NombrePropio")
    public String NombrePropio(@WebParam(name = "ApePat") String ApePat, @WebParam(name = "ApeMat") String ApeMat, @WebParam(name = "Nombres") String Nombres, @WebParam(name = "Genero") String Genero) {
        final char[] delimiters = {' ', '_'};
        String G = Genero;
        switch (G) {
            case ("M"):
                return "Saludos, Sr. " + capitalizeFully(Nombres, delimiters) + " " + capitalizeFully(ApePat) + " " + capitalizeFully(ApeMat);
            case ("m"):
                return "Saludos, Sr. " + capitalizeFully(Nombres, delimiters) + " " + capitalizeFully(ApePat) + " " + capitalizeFully(ApeMat);
            case ("F"):
                return "Saludos, Sra. " + capitalizeFully(Nombres, delimiters) + " " + capitalizeFully(ApePat) + " " + capitalizeFully(ApeMat);                
            case ("f"):
                return "Saludos, Sra. " + capitalizeFully(Nombres, delimiters) + " " + capitalizeFully(ApePat) + " " + capitalizeFully(ApeMat);            
            default:
                return " ";
        }
    }
}

