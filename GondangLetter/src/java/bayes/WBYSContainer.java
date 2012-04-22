package bayes;
import kNN.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class WBYSContainer {
    
    private NaiveBayes mWBYSClassifier;
    
    private int[] removedAttributes;
    
    private int classIndex;
    
    private Instances trainingSet;
    
    public WBYSContainer(Instances trainingSet, int[] removedAttributes, int classIndex) {
         try {
            mWBYSClassifier = new NaiveBayes();

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
    public WBYSContainer(NaiveBayes Bys){
        mWBYSClassifier = Bys;
    }
    
    //latih WKNNClassifier dengan trainingSet
    public void trainModel() {
        try {
            mWBYSClassifier.buildClassifier(trainingSet);
        } catch (Exception ex) {
            System.err.println("err : " + ex.getMessage());                
        }
    }    
    
    public String outputModel() {
        return mWBYSClassifier.toString(); 
    } 
    
    //sebelumnya classifier harus di-train terlebih dahulu
    //jika learn true, maka test set tersebut akan dimasukkan ke training set
    public Instances classifyData(Instances data, boolean learn) throws Exception {
        // create copy
        Instances labeled = new Instances(data);

        // label instances
        for (int i = 0; i < data.numInstances(); i++) {            
            double clsLabel = mWBYSClassifier.classifyInstance(data.instance(i));
            labeled.instance(i).setClassValue(clsLabel);            
            if(learn) {
                mWBYSClassifier.updateClassifier(labeled.instance(i));
            }
        }               
        
        return labeled;
    }
    
    public NaiveBayes getClassifier() {        
        return mWBYSClassifier;
    }        
    
    public Instances getTrainingSet() {
        return trainingSet;
    }       
}
