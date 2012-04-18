package kNN;

import preprocess.PrepUtil;
import evaluation.EvalUtil;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.FileUtil;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class HKNNContainer {

    private HKNNClassifier mHKNNClassifier;
    
    private int[] removedAttributes;
    
    private int classIndex;
    
    private Instances trainingSet;   
    
    private int testOption;
    
    private float testOptionVal;
    
    public static final int NONE = 0;
    
    public static final int CROSS_VALIDATION = 1;
    
    public static final int PERCENTAGE_SPLIT = 2;        
    
    //sementara pake file
    public HKNNContainer(int k, String filename, int[] removedAttributes, int classIndex, int testOption, float testOptionVal) {        
        BufferedReader reader = null;
        try {
            mHKNNClassifier = new HKNNClassifier(k);
            
            //ambil file input training set
            reader = new BufferedReader(new FileReader(filename));
            trainingSet = new Instances(reader);            
            reader.close();
            
            //set class attribute           
            trainingSet.setClass(trainingSet.attribute(classIndex));                                                                                    
            
            //remove attribute
            Remove r = new Remove();
            r.setAttributeIndicesArray(removedAttributes);
            r.setInputFormat(trainingSet);
            trainingSet = Filter.useFilter(trainingSet, r);                        
                        
            System.out.println("jumlah attribute training set : " + trainingSet.numAttributes());            
            this.testOption = testOption;
            this.testOptionVal = testOptionVal;
        } catch (Exception ex) {
            System.err.println("err : " + ex.getMessage());                
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                System.err.println("err : " + ex.getMessage());                
            }
        }
    }
    
    public void run() {
        switch(testOption) {
            case CROSS_VALIDATION : 
                EvalUtil.crossValidation(mHKNNClassifier, trainingSet, (int)testOptionVal);
                break;
            case PERCENTAGE_SPLIT : 
                EvalUtil.percentageSplit(mHKNNClassifier, trainingSet, testOptionVal);
                break;               
        }
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
    
    public HKNNClassifier getClassifier() {        
        return mHKNNClassifier;
    }
    
    public Instances getTrainingSet() {
        return trainingSet;
    }       
    
    public static void main(String[] args) {
        
        int[] removedAttributes = new int[] {};    
        
        System.out.println("=====TES PERCENTAGE SPLIT======");
        HKNNContainer hc = new HKNNContainer(1, "contact-lenses.arff", removedAttributes, 4, PERCENTAGE_SPLIT, 66);
        hc.run();  
        
        System.out.println("=====TES USE TRAINING SET=======");
        
        
        System.out.println("====OUTPUT MODEL=======");
        HKNNContainer hc2 = new HKNNContainer(3, "contact-lenses.arff", removedAttributes, 4, NONE, 0);
        hc2.trainModel();
        hc2.outputModel();
        
        System.out.println("====TES DISCRETIZE======");
        HKNNContainer hc3 = new HKNNContainer(1, "letter-recognition.arff", removedAttributes, 0, NONE, 0);
        Instances discdata = PrepUtil.unsupervisedDiscretize(hc3.getTrainingSet());        
        try {
            FileUtil.saveInstances(discdata, "test-discretize.arff");            
        } catch (Exception ex) {            
        }
        
        System.out.println("====TES NORMALIZE======");
        HKNNContainer hc4 = new HKNNContainer(1, "letter-recognition.arff", removedAttributes, 0, NONE, 0);
        Instances normdata = PrepUtil.unsupervisedNormalize(hc3.getTrainingSet());
        try {
            FileUtil.saveInstances(normdata, "test-normalize.arff");            
        } catch (Exception ex) {            
        }
    }
    
}
