package fullwipe.ciriitc.whereismybus.utilities;

import android.app.Application;

public class Variables extends Application {

    private String nomeprof,ind,tmax;

    public String getNomeprof() {
        return nomeprof;
    }

    public void setNomeprof(String nomeprof) {
        this.nomeprof = nomeprof;
    }
    
    public String getInd() {
        return ind;
    }

    public void setInd(String ind) {
        this.ind = ind;
    }
    
    public String getTmax() {
        return tmax;
    }

    public void setTmax(String tmax) {
        this.tmax = tmax;
    }
    
    
    
}