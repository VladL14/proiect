package org.example;

/**
 * @author Lunculescu Vlad
 */

/**
 * Class Fruct
 */
public class Fruct{
    int id;
    String nume;
    int valoare;
    String imagine;

    /**
     * Constructor
     * @param id
     * @param nume
     * @param valoare
     * @param imagine
     */
    public Fruct(int id, String nume, int valoare, String imagine){
        this.id=id;
        this.nume = nume;
        this.valoare=valoare;
        this.imagine=imagine;

    }

    /**
     * getter pentru id
     * @return id
     */
    public int getId(){
        return id;
    }

    /**
     * getter pentru valoare
     * @return valoare
     */
    public int getValoare(){
        return valoare;
    }
}
