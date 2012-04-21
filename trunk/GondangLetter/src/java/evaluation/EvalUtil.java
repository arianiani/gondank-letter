package evaluation;

import java.io.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import kNN.HKNNContainer;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;

public class EvalUtil {

    //kalo make crossValidation, mHKNNClassifier gak boleh di-train sebelumnya
    public static void crossValidation(Classifier classifier, Instances trainingSet,int fold) {
        try {            
            Evaluation crosseval = new Evaluation(trainingSet);            
            crosseval.crossValidateModel(classifier, trainingSet, fold, new Random(1));            
            System.out.println(crosseval.toSummaryString("\nHasil dari Cross-Validation dengan "+ fold + " fold\n=========\n",false));
        } catch (Exception ex) {
            System.err.println("err : " + ex.getMessage());
        }
    }    
    
    public static void percentageSplit(Classifier classifier, Instances trainingSet, float trainingPercent) {
        try {
            int trainingSize = (int) Math.round(trainingSet.numInstances() * trainingPercent / 100);
            int testSize = trainingSet.numInstances() - trainingSize;
            Instances train = new Instances(trainingSet, 0, trainingSize);        
            Instances test = new Instances(trainingSet, trainingSize, testSize);
            classifier.buildClassifier(train);
            Evaluation eval = new Evaluation(train);
            eval.evaluateModel(classifier, test);
            System.out.println(eval.toSummaryString("\nHasil dari percentage split dengan " + trainingPercent + "% training size. Results : \n",false));                                                                                          
        } catch (Exception ex) {
            System.err.println("err : " + ex.getMessage());
        }        
    }
    
    public static void useTrainingSet(Classifier classifier, Instances trainingSet) {
        try {            
            Evaluation eval = new Evaluation(trainingSet);            
            classifier.buildClassifier(trainingSet);
            eval.evaluateModel(classifier, trainingSet);            
            System.out.println("persentase benar : " + eval.pctCorrect());            
            System.out.println("persentase salah : " + eval.pctIncorrect());
        } catch (Exception ex) {
            System.err.println("err : " + ex.getMessage());
        }
        
    }   
    
    public static void main(String[] args) {
        try {
            // load unlabeled data
            Instances unlabeled = new Instances(
                                    new BufferedReader(
                                    new FileReader("contact-lenses-testset.arff")));

            unlabeled.setClassIndex(4);
            
            Instances training = new Instances(new BufferedReader(new FileReader("contact-lenses.arff")));
            training.setClassIndex(4);
            
//            Classifier tree = new IBk(1);
//            tree.buildClassifier(training);            
            int[] removedAttributes = new int[] {};
            HKNNContainer hcc = new HKNNContainer(1, "contact-lenses.arff", removedAttributes, 4);        
            hcc.trainModel();
            
            // set class attribute
            unlabeled.setClassIndex(unlabeled.numAttributes() - 1);

            // create copy
            Instances labeled = new Instances(unlabeled);

            // label instances
            for (int i = 0; i < unlabeled.numInstances(); i++) {
                double clsLabel = hcc.getClassifier().classifyInstance(unlabeled.instance(i));
                labeled.instance(i).setClassValue(clsLabel);
            }
            
            System.out.println(labeled.toString());
            // save labeled data
//            BufferedWriter writer = new BufferedWriter(
//                                    new FileWriter("contact-lenses-output.arff"));
//            writer.write(labeled.toString());
//            writer.newLine();
//            writer.flush();
//            writer.close();
        } catch (Exception ex) {
            Logger.getLogger(EvalUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
