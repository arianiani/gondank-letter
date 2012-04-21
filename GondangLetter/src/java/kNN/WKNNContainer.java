package kNN;

import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class WKNNContainer {
    
    private IBk mWKNNClassifier;
    
    private int[] removedAttributes;
    
    private int classIndex;
    
    private Instances trainingSet;
    
    public WKNNContainer(int k, Instances trainingSet, int[] removedAttributes, int classIndex) {
         try {
            mWKNNClassifier = new IBk(k);

            this.trainingSet = trainingSet;                        

            //set class attribute           
            this.trainingSet.setClass(this.trainingSet.attribute(classIndex));                                                                                    

            //remove attribute
            Remove r = new Remove();
            r.setAttributeIndicesArray(removedAttributes);
            r.setInputFormat(this.trainingSet);
            this.trainingSet = Filter.useFilter(this.trainingSet, r);                        

            System.out.println("jumlah attribute training set : " + this.trainingSet.numAttributes());
        } catch (Exception ex) {
            Logger.getLogger(HKNNContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //latih WKNNClassifier dengan trainingSet
    public void trainModel() {
        try {
            mWKNNClassifier.buildClassifier(trainingSet);
        } catch (Exception ex) {
            System.err.println("err : " + ex.getMessage());                
        }
    }    
    
    public String outputModel() {
        return mWKNNClassifier.toString(); 
    } 
    
    //sebelumnya classifier harus di-train terlebih dahulu
    //jika learn true, maka test set tersebut akan dimasukkan ke training set
    public Instances classifyData(Instances data, boolean learn) throws Exception {
        // create copy
        Instances labeled = new Instances(data);

        // label instances
        for (int i = 0; i < data.numInstances(); i++) {            
            double clsLabel = mWKNNClassifier.classifyInstance(data.instance(i));
            labeled.instance(i).setClassValue(clsLabel);            
            if(learn) {
                mWKNNClassifier.updateClassifier(labeled.instance(i));
            }
        }               
        
        return labeled;
    }
    
    public IBk getClassifier() {        
        return mWKNNClassifier;
    }        
    
    public Instances getTrainingSet() {
        return trainingSet;
    }       
}
