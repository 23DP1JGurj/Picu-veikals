package lv.rvt;

import java.util.*;

public class Pica {
    private int nr;
    private String nosaukums;
    private String izmers;
    private double cena;
    private String sastavdalas;
    
    public Pica(int nr, String nosaukums, String izmers, double cena, String sastavdalas) {
        this.nr = nr;
        this.nosaukums = nosaukums;
        this.izmers = izmers;
        this.cena = cena;
        this.sastavdalas = sastavdalas;
    }
    
    // Getter metodes
    public int getNr() { return nr; }
    public String getNosaukums() { return nosaukums; }
    public String getIzmers() { return izmers; }
    public double getCena() { return cena; }
    public String getSastavdalas() { return sastavdalas; }
}
