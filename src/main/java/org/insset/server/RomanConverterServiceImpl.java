/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.insset.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.insset.client.service.RomanConverterService;

/**
 *
 * @author user
 */
@SuppressWarnings("serial")
public class RomanConverterServiceImpl extends RemoteServiceServlet implements
        RomanConverterService {

    static String convertToDecimal(java.lang.String romanNumber) {
        int decimal = 0;
        int lastNumber = 0;
        String romanNumeral = romanNumber.toUpperCase();
        /* operation to be performed on upper cases even if user 
         enters roman values in lower case chars */
        for (int x = romanNumeral.length() - 1; x >= 0; x--) {
            char convertToDecimal = romanNumeral.charAt(x);

            switch (convertToDecimal) {
                case 'M':
                    decimal = processDecimal(1000, lastNumber, decimal);
                    lastNumber = 1000;
                    break;

                case 'D':
                    decimal = processDecimal(500, lastNumber, decimal);
                    lastNumber = 500;
                    break;

                case 'C':
                    decimal = processDecimal(100, lastNumber, decimal);
                    lastNumber = 100;
                    break;

                case 'L':
                    decimal = processDecimal(50, lastNumber, decimal);
                    lastNumber = 50;
                    break;

                case 'X':
                    decimal = processDecimal(10, lastNumber, decimal);
                    lastNumber = 10;
                    break;

                case 'V':
                    decimal = processDecimal(5, lastNumber, decimal);
                    lastNumber = 5;
                    break;

                case 'I':
                    decimal = processDecimal(1, lastNumber, decimal);
                    lastNumber = 1;
                    break;
            }

        }
        return Integer.toString(decimal);

    }

    public static int processDecimal(int decimal, int lastNumber, int lastDecimal) {
        if (lastNumber > decimal) {
            return lastDecimal - decimal;
        } else {
            return lastDecimal + decimal;
        }
    }

    public static String convertToRoman(int input) {
        if (input < 1 || input > 2000) {
            return "Veuillez entrer un nombre entre 1 et 2000";
        }
        String s = "";
        while (input >= 1000) {
            s += "M";
            input -= 1000;
        }
        while (input >= 900) {
            s += "CM";
            input -= 900;
        }
        while (input >= 500) {
            s += "D";
            input -= 500;
        }
        while (input >= 400) {
            s += "CD";
            input -= 400;
        }
        while (input >= 100) {
            s += "C";
            input -= 100;
        }
        while (input >= 90) {
            s += "XC";
            input -= 90;
        }
        while (input >= 50) {
            s += "L";
            input -= 50;
        }
        while (input >= 40) {
            s += "XL";
            input -= 40;
        }
        while (input >= 10) {
            s += "X";
            input -= 10;
        }
        while (input >= 9) {
            s += "IX";
            input -= 9;
        }
        while (input >= 5) {
            s += "V";
            input -= 5;
        }
        while (input >= 4) {
            s += "IV";
            input -= 4;
        }
        while (input >= 1) {
            s += "I";
            input -= 1;
        }
        return s;
    }

    @Override
    public String convertDateYears(String nbr) throws IllegalArgumentException {
        Integer day = Integer.parseInt(nbr.substring(0, 2));
        Integer month = Integer.parseInt(nbr.substring(3, 5));
        Integer year = Integer.parseInt(nbr.substring(6, 10));
        String romanDay = convertToRoman(day);
        String romanMonth = convertToRoman(month);
        String romanYear = convertToRoman(year);
        String pattern = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
        if(!nbr.matches(pattern)){
            return "Valeurs incorrectes";
        }
        return new String(romanDay+'/'+romanMonth+'/'+romanYear);
    }

    @Override
    public Integer convertRomanToArabe(String nbr) throws IllegalArgumentException {
        //Implement your code
        return new Integer(convertToDecimal(nbr));
    }

    @Override
    public String convertArabeToRoman(Integer nbr) throws IllegalArgumentException {
        //Implement your code
        return new String(convertToRoman(nbr));
    }

}
