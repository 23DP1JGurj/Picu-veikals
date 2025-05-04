package lv.rvt;

public class Pica {
    private int nr;
    private String nosaukums;
    private double cena20cm;
    private double cena30cm;
    private double cena40cm;
    private String sastavdalas;

    public Pica(int nr, String nosaukums, double cena20cm, double cena30cm, double cena40cm, String sastavdalas) {
        this.nr = nr;
        this.nosaukums = nosaukums;
        this.cena20cm = cena20cm;
        this.cena30cm = cena30cm;
        this.cena40cm = cena40cm;
        this.sastavdalas = sastavdalas;
    }

    public int getNr() {
        return nr;
    }

    public String getNosaukums() {
        return nosaukums;
    }

    public double getCena20cm() {
        return cena20cm;
    }

    public double getCena30cm() {
        return cena30cm;
    }

    public double getCena40cm() {
        return cena40cm;
    }

    public String getSastavdalas() {
        return sastavdalas;
    }

    public double getCena(String size) {
        switch (size) {
            case "20 cm":
                return cena20cm;
            case "30 cm":
                return cena30cm;
            case "40 cm":
                return cena40cm;
            default:
                return 0.0;
        }
    }
        public String[] getIzmers() {
        return new String[] {"20 cm", "30 cm", "40 cm"};
    }
}
