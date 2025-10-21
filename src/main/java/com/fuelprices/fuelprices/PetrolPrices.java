package com.fuelprices.fuelprices;

public class PetrolPrices {
    private String Country;
    private String Esso;
    private String Shell;
    private String SPC;
    private String Caltex;

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getEsso() {
        return Esso;
    }

    public void setEsso(String esso) {
        Esso = esso;
    }

    public String getShell() {
        return Shell;
    }

    public void setShell(String shell) {
        Shell = shell;
    }

    public String getSPC() {
        return SPC;
    }

    public void setSPC(String SPC) {
        this.SPC = SPC;
    }

    public String getCaltex() {
        return Caltex;
    }

    public void setCaltex(String caltex) {
        Caltex = caltex;
    }

    public PetrolPrices(String Country, String Shell, String Esso,
                        String SPC, String Caltex) {
        this.Country = Country;
        this.Esso = Esso;
        this.Shell = Shell;
        this.SPC = SPC;
        this.Caltex = Caltex;
    }
}
