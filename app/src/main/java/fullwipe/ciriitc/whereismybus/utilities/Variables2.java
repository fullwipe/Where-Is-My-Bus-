package fullwipe.ciriitc.whereismybus.utilities;

public class Variables2{
	   private static Variables2 instance;
	 
	   // Global variable
	   private String nomeprof2,ind2,tmax2,ferm2,lin2;
	 
	   // Restrict the constructor from being instantiated
	   private Variables2(){}
	 
	   public void setNomeprof2(String p){
		     this.nomeprof2=p;
		   }
	   public String getNomeprof2(){
		     return this.nomeprof2;
		   }
		   
	   public void setInd2(String ind2){
		     this.ind2=ind2;
			   }
	   public String getInd2(){
		     return this.ind2;
			   }
			   
	   public void setTmax2(String tmax2){
		     this.tmax2=tmax2;
				   }
	   public String getTmax2(){
		     return this.tmax2;
				   }	
	   
	   public void setFerm2(String ferm2){
		     this.ferm2=ferm2;
				   }
	   public String getFerm2(){
		     return this.ferm2;
				   }	
	   
	   public void setLin2(String lin2){
		     this.lin2=lin2;
				   }
	   public String getLin2(){
		     return this.lin2;
				   }	
	 
	   public static synchronized Variables2 getInstance(){
	     if(instance==null){
	       instance=new Variables2();
	     }
	     return instance;
	   }
	}