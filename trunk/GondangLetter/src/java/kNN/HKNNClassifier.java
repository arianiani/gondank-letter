package kNN;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.Discretize;
import weka.filters.unsupervised.attribute.Normalize;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;

public class HKNNClassifier extends IBk {                    
    
    protected LinearKNearestNeighbourSearch linearSearch;
    
    public HKNNClassifier(int k) {
        super(k);
        linearSearch = new LinearKNearestNeighbourSearch();
    }

    @Override
    public void buildClassifier(Instances instances) throws Exception {     
        
        // can classifier handle the data?
        getCapabilities().testWithFail(instances);

        // remove instances with missing class
        instances = new Instances(instances);
        instances.deleteWithMissingClass();

        m_NumClasses = instances.numClasses();
        m_ClassType = instances.classAttribute().type();
        m_Train = new Instances(instances, 0, instances.numInstances());

        for(int i=0;i<m_Train.numAttributes(); i++) {
            if((i!=m_Train.classIndex()) && 
                    (m_Train.attribute(i).isNominal() || 
                    m_Train.attribute(i).isNumeric())) {
                m_NumAttributesUsed ++;
            }
        }
        
        linearSearch.setInstaces(m_Train);
        
        // Invalidate any currently cross-validation selected k
        m_kNNValid = false;
    }

    @Override
    public double[] distributionForInstance(Instance instance) throws Exception {
        if (m_Train.numInstances() == 0) {
            //throw new Exception("No training instances!");
            return m_defaultModel.distributionForInstance(instance);
        }
        
        Instances neighbours = linearSearch.kNearestNeighbours(instance, m_kNN);
        double[] distances = linearSearch.getDistances();
        double[] distribution = makeDistribution(neighbours, distances);
                
        return distribution;                
    }    
    
    @Override
    public void updateClassifier(Instance instance) throws Exception {
        super.updateClassifier(instance);
        
        linearSearch.addInstance(instance);
    }        
    
    public static void main(String[] args) {
        try {
            HKNNClassifier hknn  = new HKNNClassifier(5);
            
            //parse file input
            BufferedReader reader = new BufferedReader(new FileReader("contact-lenses.arff"));
            Instances trainingSet = new Instances(reader);            
            reader.close();                        
            
            //set class attribute           
            trainingSet.setClass(trainingSet.attribute("contact-lenses"));                                                            
            
            // ALFIAN testing
//            double[] values = new double[data.numAttributes()];
//            values[0] = data.attribute(0).indexOfValue("?");
//            values[1] = data.attribute(0).indexOfValue("3");
//            values[2] = data.attribute(0).indexOfValue("9");
//            values[3] = data.attribute(0).indexOfValue("5");
//            values[4] = data.attribute(0).indexOfValue("7");
//            values[5] = data.attribute(0).indexOfValue("4");
//            values[6] = data.attribute(0).indexOfValue("8");
//            values[7] = data.attribute(0).indexOfValue("7");
//            values[8] = data.attribute(0).indexOfValue("3");
//            values[9] = data.attribute(0).indexOfValue("8");
//            values[10] = data.attribute(0).indexOfValue("5");
//            values[11] = data.attribute(0).indexOfValue("6");
//            values[12] = data.attribute(0).indexOfValue("8");
//            values[13] = data.attribute(0).indexOfValue("2");
//            values[14] = data.attribute(0).indexOfValue("8");
//            values[15] = data.attribute(0).indexOfValue("6");
//            values[16] = data.attribute(0).indexOfValue("7");
//
//            Instance instance = new Instance(1, values);
//            instance.setDataset(data);
//            Discretize D = new Discretize();
//            D.setInputFormat(data);
//            D.input(instance);
//            //D.setAttributeIndicesArray(new int[]{1,2,3});
//            D.batchFinished();
//            instance = D.output();
////            ReplaceMissingValues RMV = new ReplaceMissingValues();
////            RMV.setInputFormat(data);
////            RMV.input(instance);
////            RMV.batchFinished();
////            instance = RMV.output();
////            AttributeSelection AS = new AttributeSelection();
////            AS.setInputFormat(data);
////            AS.input(instance);
////            AS.batchFinished();
////            AS.output();
//            Normalize N = new Normalize();
//            N.setInputFormat(data);
//            N.input(instance);
//            N.output();
//            //ibk.setKNN(3);
//            //ibk.setCrossValidate(true);
//            
//            
//            System.out.println("Class = " + ibk.classifyInstance(instance));
//            System.out.println(ibk);
//            
//             // serialize model
//            weka.core.SerializationHelper.write("D:/ada.model", ibk);
//
//            // deserialize model
//            Classifier cls = (Classifier) weka.core.SerializationHelper.read("D:/ada.model");
//            System.out.println("Class = " + cls.classifyInstance(instance));
//            System.out.println(cls);
//            
            
            /// ALFIAN
            
            //cross validation (gak boleh ditrain classifier sebelumnya!) 
            Evaluation crosseval = new Evaluation(trainingSet);
            crosseval.crossValidateModel(hknn, trainingSet, 10, new Random(1));            
            System.out.println(crosseval.toSummaryString("\nResults\n=========\n",false));                                                                                            
            
            //train model                                    
            hknn.buildClassifier(trainingSet);                                    
            
            //buat instances dari dataset
            Instances dataset = new Instances(new BufferedReader(new FileReader("contact-lenses-testset.arff")));
            dataset.setClass(dataset.attribute("contact-lenses"));
            
            //buat copy dari dataset yang menyimpan hasil klasifikasi
            Instances labeled = new Instances(dataset);                 
            
            //iterasi
            System.out.println(dataset.numInstances());                                        
            for(int i=0;i<dataset.numInstances();i++) {                         
                double classLabel = hknn.classifyInstance(dataset.instance(i));
                labeled.instance(i).setClassValue(classLabel);
                System.out.println("klasifikasi instance " + i + " : " + labeled.classAttribute().value((int) classLabel));
            }                        
            
            Evaluation eval = new Evaluation(trainingSet);
            eval.evaluateModel(hknn, dataset);
            System.out.println(eval.toSummaryString("\nResults\n=========\n",false));                                                                                            
            
        } catch (Exception ex) {
            Logger.getLogger(HKNNClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
