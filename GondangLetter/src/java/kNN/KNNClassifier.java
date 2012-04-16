package kNN;

import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

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
            System.out.println("Class = " + ibk.classifyInstance(instance));            
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
