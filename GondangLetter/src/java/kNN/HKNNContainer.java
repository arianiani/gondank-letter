package kNN;

import evaluation.EvalUtil;
import evaluation.Result;
import java.util.logging.Level;
import java.util.logging.Logger;
import preprocess.PrepUtil;
import util.FileUtil;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class HKNNContainer {

    private HKNNClassifier mHKNNClassifier;
    
    private int[] removedAttributes;
    
    private int classIndex;
    
    private Instances trainingSet;   
            
    //sementara pake file
    public HKNNContainer(int k, Instances trainingSet, int[] removedAttributes, int classIndex) {                        
        try {
            mHKNNClassifier = new HKNNClassifier(k);

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
    public HKNNContainer(HKNNClassifier mHKNN){
        mHKNNClassifier = mHKNN;
    }
    
    //latih HKNNClassifier dengan trainingSet
    public void trainModel() {
        try {
            mHKNNClassifier.buildClassifier(trainingSet);
        } catch (Exception ex) {
            System.err.println("err : " + ex.getMessage());                
        }
    }        
    
    public String outputModel() {
        return mHKNNClassifier.toString(); 
    }        
    
    //sebelumnya classifier harus di-train terlebih dahulu
    //jika learn true, maka test set tersebut akan dimasukkan ke training set
    public Instances classifyData(Instances data, boolean learn) throws Exception {
        // create copy
        Instances labeled = new Instances(data);

        // label instances
        for (int i = 0; i < data.numInstances(); i++) {            
            double clsLabel = mHKNNClassifier.classifyInstance(data.instance(i));
            labeled.instance(i).setClassValue(clsLabel);            
            if(learn) {
                mHKNNClassifier.updateClassifier(labeled.instance(i));
            }
        }               
        
        return labeled;
    }
    
    public HKNNClassifier getClassifier() {        
        return mHKNNClassifier;
    }
    
    public Instances getTrainingSet() {
        return trainingSet;
    }       
    
    public static void main(String[] args) {
        try {
            int[] removedAttributes = new int[] {};    
            //Instances trainingSet = FileUtil.loadInstances("letter-recognition.arff"); 
            Instances trainingSet = FileUtil.loadInstances("contact-lenses.arff"); 
            int classIndex = 4;
            trainingSet.setClassIndex(classIndex);
            
            System.out.println("====TES classify=======");
            HKNNContainer hcc = new HKNNContainer(2, trainingSet, removedAttributes, classIndex);        
            hcc.trainModel();
            Instances testdata=null;
            try {
                testdata = FileUtil.loadInstances("contact-lenses-testset.arff");
                testdata.setClassIndex(4);
            } catch (Exception ex) {
                System.out.println("err : " + ex.getMessage());
            }                                                 

            Instances result = null;          
            try {
                result = hcc.classifyData(testdata, false);
            } catch (Exception ex) {
                Logger.getLogger(HKNNContainer.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println("result : ");
            System.out.println(result.toString());
            
            System.out.println("=====TES PERCENTAGE SPLIT======");
            HKNNContainer hc = new HKNNContainer(1, trainingSet, removedAttributes, classIndex);
            Result psResult = new Result();
            System.out.println(EvalUtil.percentageSplit(hc.getClassifier(), hc.getTrainingSet(), 5, psResult));
            System.out.println("akurasi (benar - salah) : " + psResult.getPctCorrect() + " - " + psResult.getPctIncorrect());
            
            System.out.println("=====TES USE TRAINING SET=======");
            HKNNContainer hct = new HKNNContainer(1, trainingSet, removedAttributes, classIndex);
            Result tsResult = new Result();
            System.out.println(EvalUtil.useTrainingSet(hct.getClassifier(), hct.getTrainingSet(), tsResult));            
            System.out.println("akurasi (benar - salah) : " + tsResult.getPctCorrect() + " - " + tsResult.getPctIncorrect());
            
            System.out.println("=====TES CROSS VALIDATION=======");
            HKNNContainer hccv = new HKNNContainer(1, trainingSet, removedAttributes, classIndex);
            Result cvResult = new Result();
            System.out.println(EvalUtil.crossValidation(hccv.getClassifier(), hccv.getTrainingSet(), 10, cvResult));
            System.out.println("akurasi (benar - salah) : " + cvResult.getPctCorrect() + " - " + cvResult.getPctIncorrect());
            
            System.out.println("====OUTPUT MODEL=======");
            HKNNContainer hc2 = new HKNNContainer(3, trainingSet, removedAttributes, classIndex);
            hc2.trainModel();
            hc2.outputModel();

            System.out.println("====TES DISCRETIZE======");
            HKNNContainer hc3 = new HKNNContainer(1, trainingSet, removedAttributes, classIndex);
            Instances discdata = PrepUtil.unsupervisedDiscretize(hc3.getTrainingSet());        
            try {
                FileUtil.saveInstances(discdata, "test-discretize.arff");            
            } catch (Exception ex) {            
            }

            System.out.println("====TES NORMALIZE======");
            HKNNContainer hc4 = new HKNNContainer(1, trainingSet, removedAttributes, classIndex);
            Instances normdata = PrepUtil.unsupervisedNormalize(hc3.getTrainingSet());
            try {
                FileUtil.saveInstances(normdata, "test-normalize.arff");            
            } catch (Exception ex) {            
            }
            
        } catch (Exception ex) {
            Logger.getLogger(HKNNContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
