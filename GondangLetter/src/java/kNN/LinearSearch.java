package kNN;

import weka.core.Instance;
import weka.core.Instances;

public class LinearSearch {

    protected Instances data;
    protected Instance[] best;
    
    
    public LinearSearch() {        
    }
    
    public void setInstaces(Instances instances) {
        data = instances;        
    }
    
    //menambah instance ke data
    public void addInstance(Instance instance) {
        data.add(instance);
    }
    
    public Instances kNearestNeighbours(Instance target, int k) {        
        Instances ret = new Instances(data, 0);
        for(int i=0;i<data.numInstances();i++) {
            
            if(ret.numInstances()<k) {
                
            }
            
        }
        
        return ret;
    }
    
    //asumsi iunbd
//    public double calcDistance(Instance a, Instance b) {
//        double distance = 0;
//        
//        for(int i = 0, j = 0; i< a.numAttributes() || j<b.numAttributes();) {
//            if()
//        }        
//        
//        return distance;
//    }
    
    public static void main(String[] args) {
        
    }
}
