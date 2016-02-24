package fullwipe.ciriitc.whereismybus.dbprofili;

public class ProfiliList {

	 //private variables
	 int id;
	 String nome;
	 String indirizzo;
	 String tempomax;
	 String fermatasc;
	 String lineasc;
     String coorX;
     String coorY;
     String terminal;

	 // Empty constructor
	 public ProfiliList(){

	 }
	 // constructor
	 public ProfiliList(int id, String nome, String indirizzo, String tempomax, String fermatasc, String lineasc, String coorX, String coorY, String terminal){
	  this.id = id;
	  this.nome = nome;
	  this.indirizzo = indirizzo;
	  this.tempomax = tempomax;
	  this.fermatasc = fermatasc;
	  this.lineasc = lineasc;
      this.coorX = coorX;
      this.coorY = coorY;
      this.terminal = terminal;
	 }

	 // getting id
	 public int getId(){
	  return this.id;
	 }

	 // setting id
	 public void setId(int id){
	  this.id = id;
	 }

	 // getting nome
	 public String getNome(){
	  return this.nome;
	 }

	 // setting nome
	 public void setNome(String nome){
	  this.nome = nome;
	 }

	 // getting indirizzo
	 public String getIndirizzo(){
	  return this.indirizzo;
	 }

	 // setting indirizzo
	 public void setIndirizzo(String indirizzo){
	  this.indirizzo = indirizzo;
	 }
	 
	 // getting tempomax
	 public String getTempomax(){
	  return this.tempomax;
	 }

	 // setting tempomax
	 public void setTempomax(String tempomax){
	  this.tempomax = tempomax;
	 }

    // getting fermata
    public String getFermatasc(){
        return this.fermatasc;
    }

    // setting fermata
    public void setFermatasc(String fermatasc){
        this.fermatasc = fermatasc;
    }

    // getting linea
    public String getLineasc(){
        return this.lineasc;
    }

    // setting linea
    public void setLineasc(String lineasc){
        this.lineasc = lineasc;
    }

    // getting X
    public String getCoorX(){
        return this.coorX;
    }

    // setting X
    public void setCoorX(String coorX){
        this.coorX = coorX;
    }

    // getting Y
    public String getCoorY(){
        return this.coorY;
    }

    // setting Y
    public void setCoorY(String coorY){
        this.coorY = coorY;
    }

    // getting terminal
    public String getTerminal(){
        return this.terminal;
    }

    // setting terminal
    public void setTerminal(String terminal){
        this.terminal = terminal;
    }

}