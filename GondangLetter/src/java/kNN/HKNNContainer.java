package kNN;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import weka.classifiers.Evaluation;
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
            reader = new BufferedReader(new FileReader("letter-recognition.arff"));
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
                crossValidation((int)testOptionVal);
                break;
            case PERCENTAGE_SPLIT : 
                percentageSplit(testOptionVal);
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
     
    //kalo make crossValidation, mHKNNClassifier gak boleh di-train sebelumnya
    public void crossValidation(int fold) {
        try {            
            Evaluation crosseval = new Evaluation(trainingSet);            
            crosseval.crossValidateModel(mHKNNClassifier, trainingSet, fold, new Random(1));            
            System.out.println(crosseval.toSummaryString("\nHasil dari Cross-Validation dengan "+ fold + " fold\n=========\n",false));
        } catch (Exception ex) {
            System.err.println("err : " + ex.getMessage());
        }
    }    
    
    public void percentageSplit(float trainingPercent) {
        try {
            int trainingSize = (int) Math.round(trainingSet.numInstances() * trainingPercent / 100);
            int testSize = trainingSet.numInstances() - trainingSize;
            Instances train = new Instances(trainingSet, 0, trainingSize);        
            Instances test = new Instances(trainingSet, trainingSize, testSize);
            mHKNNClassifier.buildClassifier(train);
            Evaluation eval = new Evaluation(train);
            eval.evaluateModel(mHKNNClassifier, test);
            System.out.println(eval.toSummaryString("\nHasil dari percentage split dengan " + trainingPercent + "% training size. Results\n=========\n",false));                                                                                          
        } catch (Exception ex) {
            System.err.println("err : " + ex.getMessage());
        }
        
    }
    
    public static void main(String[] args) {
        
        int[] removedAttributes = new int[] {};
        int classIndex = 0; // indeks pertama
        
        HKNNContainer hc = new HKNNContainer(3, "letter-recognition.arff", removedAttributes, classIndex, CROSS_VALIDATION, 10);
        hc.run();                
    }
    
}
