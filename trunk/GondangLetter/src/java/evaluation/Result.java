package evaluation;

public class Result {
    
    protected double pctCorrect;
    
    protected double pctIncorrect;
        
    public Result() {
    }
    
    public void setPctCorrect(double value) {
        pctCorrect = value;
    }
    
    public void setPctIncorrect(double value) {
        pctIncorrect = value;
    }
    
    public double getPctCorrect() {
        return pctCorrect;
    }
    
    public double getPctIncorrect() {
        return pctIncorrect;
    }
}
