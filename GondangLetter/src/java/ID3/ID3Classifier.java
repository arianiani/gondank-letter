/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ID3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import kNN.HKNNClassifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.Id3;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.core.Instances;
import preprocess.PrepUtil;
import weka.core.Instance;
import weka.filters.Filter;

/**
 *
 * @author Dihya
 */
public class ID3Classifier extends Id3{
    public ID3Classifier() {
        super();
    }    
    public static void main(String[] args) {
        try{
            ID3Classifier id3c = new ID3Classifier();
            
            //parse file input
            BufferedReader reader = new BufferedReader(new FileReader("letter-recognition.arff"));
            Instances trainingSet = new Instances(reader);
            reader.close();
            
            //set class attribute           
            trainingSet.setClass(trainingSet.attribute("lettr"));
            
            //preproses
            trainingSet = preprocess.PrepUtil.unsupervisedDiscretize(trainingSet);
            trainingSet = preprocess.PrepUtil.unsupervisedNormalize(trainingSet);
            
            //khusus id3, membutuhkan preproses numerictonominal
            try {
                Filter filter = new weka.filters.unsupervised.attribute.NumericToNominal();
                filter.setInputFormat(trainingSet);
                Filter.useFilter(trainingSet, filter);
            } catch (Exception ex) {
                System.err.println("err : " + ex.getMessage()); 
            }

            //cross validation (gak boleh ditrain classifier sebelumnya!) 
            Evaluation crosseval = new Evaluation(trainingSet);
            crosseval.crossValidateModel(id3c, trainingSet, 10, new Random(1));            
            System.out.println(crosseval.toSummaryString("\nResults\n=========\n",false));                                                                                            
            
            //train model                                    
            id3c.buildClassifier(trainingSet);
            
            //buat instances dari dataset
            Instances dataset = new Instances(new BufferedReader(new FileReader("letter - dataset.arff")));
            dataset.setClass(dataset.attribute("lettr"));
            
            //buat copy dari dataset yang menyimpan hasil klasifikasi
            Instances labeled = new Instances(dataset);
            
            //iterasi
            System.out.println(dataset.numInstances());                                        
            for(int i=0;i<dataset.numInstances();i++) {                         
                double classLabel = id3c.classifyInstance(dataset.instance(i));
                labeled.instance(i).setClassValue(classLabel);
                System.out.println("klasifikasi instance " + i + " : " + labeled.classAttribute().value((int) classLabel));
            }                        
            
            Evaluation eval = new Evaluation(trainingSet);
            eval.evaluateModel(id3c, dataset);
            System.out.println(eval.toSummaryString("\nResults\n=========\n",false));                                                                                            
            
            
            //creating new instance
//          Z,3,8,4,6,2,7,7,4,13,10,6,8,0,8,8,8
//            double[] values = new double[trainingSet.numAttributes()];
//            //values[0] = trainingSet.attribute(0).indexOfValue("");
//            values[1] = trainingSet.attribute(1).indexOfValue("3");
//            values[2] = trainingSet.attribute(2).indexOfValue("8");
//            values[3] = trainingSet.attribute(3).indexOfValue("4");
//            values[4] = trainingSet.attribute(4).indexOfValue("6");
//            values[5] = trainingSet.attribute(5).indexOfValue("2");
//            values[6] = trainingSet.attribute(6).indexOfValue("7");
//            values[7] = trainingSet.attribute(7).indexOfValue("7");
//            values[8] = trainingSet.attribute(8).indexOfValue("4");
//            values[9] = trainingSet.attribute(9).indexOfValue("13");
//            values[10] = trainingSet.attribute(10).indexOfValue("10");
//            values[11] = trainingSet.attribute(11).indexOfValue("6");
//            values[12] = trainingSet.attribute(12).indexOfValue("8");
//            values[13] = trainingSet.attribute(13).indexOfValue("0");
//            values[14] = trainingSet.attribute(14).indexOfValue("8");
//            values[15] = trainingSet.attribute(15).indexOfValue("8");
//            values[16] = trainingSet.attribute(16).indexOfValue("8");
//            System.out.println(trainingSet.numAttributes());
//            //test classifier using an instance           
//            Instance instance = new Instance(1.0, values);
//            instance.setDataset(trainingSet);
//            System.out.println("Class = "+id3c.distributionForInstance(instance));
//            
        }catch (Exception ex) {
            Logger.getLogger(HKNNClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
