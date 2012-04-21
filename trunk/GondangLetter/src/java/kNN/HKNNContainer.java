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
    
    public static final int TRAINING_SET = 3;
    
    //sementara pake file
    public HKNNContainer(int k, String filename, int[] removedAttributes, int classIndex) {        
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
        
        int[] removedAttributes = new int[] {};    

        System.out.println("=====TES PERCENTAGE SPLIT======");
        HKNNContainer hc = new HKNNContainer(1, "contact-lenses.arff", removedAttributes, 4);
        System.out.println(EvalUtil.percentageSplit(hc.getClassifier(), hc.getTrainingSet(), 66));

        System.out.println("=====TES USE TRAINING SET=======");
        HKNNContainer hct = new HKNNContainer(3, "contact-lenses.arff", removedAttributes, 4);
        System.out.println(EvalUtil.useTrainingSet(hct.getClassifier(), hct.getTrainingSet()));

        System.out.println("====OUTPUT MODEL=======");
        HKNNContainer hc2 = new HKNNContainer(3, "contact-lenses.arff", removedAttributes, 4);
        hc2.trainModel();
        hc2.outputModel();

        System.out.println("====TES DISCRETIZE======");
//        HKNNContainer hc3 = new HKNNContainer(1, "letter-recognition.arff", removedAttributes, 0);
//        Instances discdata = PrepUtil.unsupervisedDiscretize(hc3.getTrainingSet());        
//        try {
//            FileUtil.saveInstances(discdata, "test-discretize.arff");            
//        } catch (Exception ex) {            
//        }

        System.out.println("====TES NORMALIZE======");
//        HKNNContainer hc4 = new HKNNContainer(1, "letter-recognition.arff", removedAttributes, 0);
//        Instances normdata = PrepUtil.unsupervisedNormalize(hc3.getTrainingSet());
//        try {
//            FileUtil.saveInstances(normdata, "test-normalize.arff");            
//        } catch (Exception ex) {            
//        }

        System.out.println("====TES classify=======");
        HKNNContainer hcc = new HKNNContainer(1, "contact-lenses.arff", removedAttributes, 4);        
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
        
    }
    
}
