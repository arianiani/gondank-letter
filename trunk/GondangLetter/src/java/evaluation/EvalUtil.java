package evaluation;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
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
            System.out.println(eval.toSummaryString("\nHasil dari percentage split dengan " + trainingPercent + "% training size. Results\n=========\n",false));                                                                                          
        } catch (Exception ex) {
            System.err.println("err : " + ex.getMessage());
        }        
    }
    
    public static void useTrainingSet(Classifier classifier, Instances trainingSet) {
        try {
            Evaluation eval = new Evaluation(trainingSet);
            eval.evaluateModel(classifier, trainingSet);
            System.out.println("persentase benar : " + eval.correct());
            System.out.println("persentase salah : " + eval.incorrect());
        } catch (Exception ex) {
            System.err.println("err : " + ex.getMessage());
        }
        
    }
}
