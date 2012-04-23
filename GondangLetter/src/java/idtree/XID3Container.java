package idtree;

import evaluation.EvalUtil;
import evaluation.Result;
import java.util.logging.Level;
import java.util.logging.Logger;
import preprocess.PrepUtil;
import util.FileUtil;
import weka.classifiers.trees.Id3;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class XID3Container {
    
    private XID3Classifier mXID3Classifier;
    
    private int[] removedAttributes;
    
    private int classIndex;
    
    private Instances trainingSet;
    
    public XID3Container(Instances trainingSet, int[] removedAttributes, int classIndex) {
         try {
            mXID3Classifier = new XID3Classifier();

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
            Logger.getLogger(XID3Container.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public XID3Container(XID3Classifier xId3){
        mXID3Classifier = xId3;
    }
    
    //latih WKNNClassifier dengan trainingSet
    public void trainModel() {
        try {
            mXID3Classifier.buildClassifier(trainingSet);
        } catch (Exception ex) {
            System.err.println("err : " + ex.getMessage());                
        }
    }    
    
    public String outputModel() {
        return mXID3Classifier.toString(); 
    } 
    
    //sebelumnya classifier harus di-train terlebih dahulu
    //jika learn true, maka test set tersebut akan dimasukkan ke training set
    public Instances classifyData(Instances data, boolean learn) throws Exception {        
        Instances labeled = new Instances(data);        
        
        // label instances
        for (int i = 0; i < data.numInstances(); i++) {            
            double clsLabel = mXID3Classifier.classifyInstance(data.instance(i));
            labeled.instance(i).setClassValue(clsLabel);                        
            if(learn) 
                trainingSet.add(labeled.instance(i));
        }               
                
        //build classifier ulang
        if(learn)        
            mXID3Classifier.buildClassifier(trainingSet);
        
        return labeled;
    }
    
    public Id3 getClassifier() {        
        return mXID3Classifier;
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
            XID3Container hcc = new XID3Container(trainingSet, removedAttributes, classIndex);        
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
                Logger.getLogger(XID3Container.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println("result : ");
            System.out.println(result.toString());
            
            System.out.println("=====TES PERCENTAGE SPLIT======");
            XID3Container hc = new XID3Container(trainingSet, removedAttributes, classIndex);
            Result psResult = new Result();
            System.out.println(EvalUtil.percentageSplit(hc.getClassifier(), hc.getTrainingSet(), 5, psResult));
            System.out.println("akurasi (benar - salah) : " + psResult.getPctCorrect() + " - " + psResult.getPctIncorrect());
            
            System.out.println("=====TES USE TRAINING SET=======");
            XID3Container hct = new XID3Container(trainingSet, removedAttributes, classIndex);
            Result tsResult = new Result();
            System.out.println(EvalUtil.useTrainingSet(hct.getClassifier(), hct.getTrainingSet(), tsResult));            
            System.out.println("akurasi (benar - salah) : " + tsResult.getPctCorrect() + " - " + tsResult.getPctIncorrect());
            
            System.out.println("=====TES CROSS VALIDATION=======");
            XID3Container hccv = new XID3Container(trainingSet, removedAttributes, classIndex);
            Result cvResult = new Result();
            System.out.println(EvalUtil.crossValidation(hccv.getClassifier(), hccv.getTrainingSet(), 10, cvResult));
            System.out.println("akurasi (benar - salah) : " + cvResult.getPctCorrect() + " - " + cvResult.getPctIncorrect());
            
            System.out.println("====OUTPUT MODEL=======");
            XID3Container hc2 = new XID3Container(trainingSet, removedAttributes, classIndex);
            hc2.trainModel();
            hc2.outputModel();

            System.out.println("====TES DISCRETIZE======");
            XID3Container hc3 = new XID3Container(trainingSet, removedAttributes, classIndex);
            Instances discdata = PrepUtil.unsupervisedDiscretize(hc3.getTrainingSet());        
            try {
                FileUtil.saveInstances(discdata, "test-discretize.arff");            
            } catch (Exception ex) {            
            }

            System.out.println("====TES NORMALIZE======");
            XID3Container hc4 = new XID3Container(trainingSet, removedAttributes, classIndex);
            Instances normdata = PrepUtil.unsupervisedNormalize(hc3.getTrainingSet());
            try {
                FileUtil.saveInstances(normdata, "test-normalize.arff");            
            } catch (Exception ex) {            
            }                        
            
            Instances voteTrainingSet = FileUtil.loadInstances("vote.arff"); 
            int voteClassIndex = 16;
            voteTrainingSet.setClassIndex(voteClassIndex);
            
            //replace missing value dulu
            voteTrainingSet = PrepUtil.unsupervisedReplaceMissingValue(voteTrainingSet);
            
            System.out.println("TEST DATA VOTE");
            XID3Container xid3vote = new XID3Container(voteTrainingSet, removedAttributes, voteClassIndex);
                                    
            Result voteTsResult = new Result();
            System.out.println(EvalUtil.useTrainingSet(xid3vote.getClassifier(), xid3vote.getTrainingSet(), voteTsResult));            
            System.out.println("akurasi (benar - salah) : " + voteTsResult.getPctCorrect() + " - " + voteTsResult.getPctIncorrect());
            
            
        } catch (Exception ex) {
            Logger.getLogger(XID3Container.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
