package fullwipe.ciriitc.whereismybus.dbprofili;

public class Data {
	private String fermata;
    private String denominazione;
    private String ubicazione;
    private String linea;
    private String capolinea;
    private String coordX;
    private String coordY;
    
    public Data(String fermata, String denominazione, String ubicazione, String linea, String capolinea, String coordX, String coordY) {
        super();
        this.fermata = fermata;
        this.denominazione = denominazione;
        this.ubicazione = ubicazione;
        this.linea = linea;
        this.capolinea = capolinea;
        this.coordX = coordX;
        this.coordY = coordY;
    }
    
    public String getFermata() {
        return fermata;
    }
    
    public String getDenominazione() {
        return denominazione;
    }
    
    public String getUbicazione() {
        return ubicazione;
    }
    
    public String getLinea() {
        return linea;
    }
    
    public String getCapolinea() {
        return capolinea;
    }

    public String getCoordX() {
        return coordX;
    }

    public String getCoordY() {
        return coordY;
    }

}
