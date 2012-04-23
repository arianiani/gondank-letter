package idtree;

import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.trees.Id3;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class WID3Container {
    
    private Id3 mWID3Classifier;
    
    private int[] removedAttributes;
    
    private int classIndex;
    
    private Instances trainingSet;
    
    public WID3Container(Instances trainingSet, int[] removedAttributes, int classIndex) {
         try {
            mWID3Classifier = new Id3();

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
            Logger.getLogger(WID3Container.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public WID3Container(Id3 id3){
        mWID3Classifier = id3;
    }
    
    //latih WKNNClassifier dengan trainingSet
    public void trainModel() {
        try {
            mWID3Classifier.buildClassifier(trainingSet);
        } catch (Exception ex) {
            System.err.println("err : " + ex.getMessage());                
        }
    }    
    
    public String outputModel() {
        return mWID3Classifier.toString(); 
    } 
    
    //sebelumnya classifier harus di-train terlebih dahulu
    //jika learn true, maka test set tersebut akan dimasukkan ke training set
    public Instances classifyData(Instances data, boolean learn) throws Exception {
        // create copy
        Instances labeled = new Instances(data);

        // label instances
        for (int i = 0; i < data.numInstances(); i++) {            
            double clsLabel = mWID3Classifier.classifyInstance(data.instance(i));
            labeled.instance(i).setClassValue(clsLabel);            
            if(learn) {
                //FIXME : id3 gak mendukung updateClassifier                
            }
        }               
        
        return labeled;
    }
    
    public Id3 getClassifier() {        
        return mWID3Classifier;
    }        
    
    public Instances getTrainingSet() {
        return trainingSet;
    }       
}
