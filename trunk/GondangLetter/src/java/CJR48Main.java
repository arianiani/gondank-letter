/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/**
 *
 * @author rendy
 */
public class CJR48Main {
    public static void main(String[] args){
        try {
            //load training dataset
            Instances data = DataSource.read("test.arff");
            data.setClass(data.attribute(11));
            
            //create classifier
            CJR48 tree = new CJR48();
            
            //train classifier using dataset
            tree.buildClassifier(data);
            
            //cerating new instance
            double[] values = new double[data.numAttributes()];
            values[0] = data.attribute(0).indexOfValue("T");
            values[1] = data.attribute(1).indexOfValue("F");
            values[2] = data.attribute(2).indexOfValue("F");
            values[3] = data.attribute(3).indexOfValue("T");
            values[4] = data.attribute(4).indexOfValue("Some");
            values[5] = data.attribute(5).indexOfValue("$$$");
            values[6] = data.attribute(6).indexOfValue("F");
            values[7] = data.attribute(7).indexOfValue("T");
            values[8] = data.attribute(8).indexOfValue("French");
            values[9] = data.attribute(9).indexOfValue("0-10");
            values[10] = data.attribute(10).indexOfValue("T");
            values[11] = data.attribute(11).indexOfValue("");
            //test classifier using an instance           
            Instance instance = new Instance(1.0, values);
            instance.setDataset(data);
            System.out.println("Class = "+tree.classifyInstance(instance));
        } catch (Exception ex) {
            Logger.getLogger(CJR48Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
