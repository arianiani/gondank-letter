package kNN;

import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.Discretize;
import weka.filters.unsupervised.attribute.Normalize;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;

public class KNNClassifier extends Classifier {    
    
    public static void main(String[] args) {
        try {
            Instances data = DataSource.read("letter-recognition.arff");
            data.setClass(data.attribute("lettr"));            
            
            IBk ibk = new IBk(3);
            
            ibk.buildClassifier(data);
            
            double[] values = new double[data.numAttributes()];
            values[0] = data.attribute(0).indexOfValue("?");
            values[1] = data.attribute(0).indexOfValue("3");
            values[2] = data.attribute(0).indexOfValue("9");
            values[3] = data.attribute(0).indexOfValue("5");
            values[4] = data.attribute(0).indexOfValue("7");
            values[5] = data.attribute(0).indexOfValue("4");
            values[6] = data.attribute(0).indexOfValue("8");
            values[7] = data.attribute(0).indexOfValue("7");
            values[8] = data.attribute(0).indexOfValue("3");
            values[9] = data.attribute(0).indexOfValue("8");
            values[10] = data.attribute(0).indexOfValue("5");
            values[11] = data.attribute(0).indexOfValue("6");
            values[12] = data.attribute(0).indexOfValue("8");
            values[13] = data.attribute(0).indexOfValue("2");
            values[14] = data.attribute(0).indexOfValue("8");
            values[15] = data.attribute(0).indexOfValue("6");
            values[16] = data.attribute(0).indexOfValue("7");

            Instance instance = new Instance(1, values);
            instance.setDataset(data);
            Discretize D = new Discretize();
            D.setInputFormat(data);
            D.input(instance);
            //D.setAttributeIndicesArray(new int[]{1,2,3});
            D.batchFinished();
            instance = D.output();
//            ReplaceMissingValues RMV = new ReplaceMissingValues();
//            RMV.setInputFormat(data);
//            RMV.input(instance);
//            RMV.batchFinished();
//            instance = RMV.output();
//            AttributeSelection AS = new AttributeSelection();
//            AS.setInputFormat(data);
//            AS.input(instance);
//            AS.batchFinished();
//            AS.output();
            Normalize N = new Normalize();
            N.setInputFormat(data);
            N.input(instance);
            N.output();
            //ibk.setKNN(3);
            //ibk.setCrossValidate(true);
            
            
            System.out.println("Class = " + ibk.classifyInstance(instance));
            System.out.println(ibk);
            
             // serialize model
            weka.core.SerializationHelper.write("D:/ada.model", ibk);

            // deserialize model
            Classifier cls = (Classifier) weka.core.SerializationHelper.read("D:/ada.model");
            System.out.println("Class = " + cls.classifyInstance(instance));
            System.out.println(cls);
            
        } catch (Exception ex) {
            Logger.getLogger(KNNClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void buildClassifier(Instances instances) throws Exception {
        // can classifier handle the data?
        getCapabilities().testWithFail(instances);

        // remove instances with missing class
        instances = new Instances(instances);
        instances.deleteWithMissingClass();
        instances.numClasses();
        
    }
}
