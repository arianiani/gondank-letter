package idtree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.trees.Id3;
import weka.core.*;

public class XID3Classifier extends Id3 {

    protected static class Node {

        protected double classValue;
        protected double[] distribution;
        protected Attribute attribute;
        protected Node[] childs;
        protected Attribute classAttribute;

        public static void createTree(Node node, Instances instances) throws Exception {
            if (instances.numInstances() == 0) {
                node.attribute = null;
                node.classValue = Instance.missingValue();
                node.distribution = new double[instances.numClasses()];
                return;
            }

            double tempClassValue = instances.instance(0).classValue();
            boolean diffMarker = false;
            for (int i = 1; i < instances.numInstances(); i++) {
                if (instances.instance(i).classValue() != tempClassValue) {
                    diffMarker = true;
                    break;
                }
            }
            // jika tiap instance mempunyai klasifikasi yang sama, ganti node menjadi node klasifikasi
            if (!diffMarker) {
                node.classValue = tempClassValue;
                node.attribute = null;
                node.classAttribute = instances.classAttribute();
                node.distribution = new double[instances.numClasses()];
                node.distribution[(int) node.classValue] = instances.numInstances();
                Utils.normalize(node.distribution);
            }

            // cari attribute dengan information gain paling maksimum         
            int bestIGAIndex = 0; // indeks atribut dengan info gain maksimum            
            double bestIGValue = 0; // nilai info gain maksimum
            for (int i = 0; i < instances.numAttributes(); i++) {
                if (i == instances.classIndex()) {
                    continue;
                }
                double val = computeInfoGain(instances, instances.attribute(i));                
                if (bestIGValue < val) {
                    bestIGValue = val;
                    bestIGAIndex = instances.attribute(i).index();
                }
            }
            node.attribute = instances.attribute(bestIGAIndex);

            //bikin leaft jika infogain zero, selain itu lanjut bikin child nodes            
            if (Utils.eq(bestIGValue, 0)) {
                node.attribute = null;
                node.distribution = new double[instances.numClasses()];
                for (int i = 0; i < instances.numInstances(); i++) {
                    node.distribution[(int) instances.instance(i).classValue()]++;
                }
                Utils.normalize(node.distribution);
                node.classValue = Utils.maxIndex(node.distribution);
                node.classAttribute = instances.classAttribute();
            } else {
                Instances[] childInstances = splitData(instances, node.attribute);
                node.childs = new Node[node.attribute.numValues()];
                for (int i = 0; i < node.attribute.numValues(); i++) {
                    Node child = new Node();
                    node.childs[i] = child;
                    createTree(child, childInstances[i]);
                }
            }
        }

        public String printTree(int level) {
            StringBuilder text = new StringBuilder();

            if (attribute == null) {
                if (Instance.isMissingValue(classValue)) {
                    text.append(": null");
                } else {
                    text.append(": ").append(classAttribute.value((int) classValue));
                }
            } else {
                for (int j = 0; j < attribute.numValues(); j++) {
                    text.append("\n");
                    for (int i = 0; i < level; i++) {
                        text.append("|  ");
                    }
                    text.append(attribute.name()).append(" = ").append(attribute.value(j));
                    text.append(childs[j].printTree(level + 1));
                }
            }
            return text.toString();
        }
    }

    /**
     * Computes information gain for an attribute.
     *
     * @param data the data for which info gain is to be computed
     * @param att the attribute
     * @return the information gain for the given attribute and data
     * @throws Exception if computation fails
     */
    //NOTE : sengaja memakai implementasi weka
    private static double computeInfoGain(Instances data, Attribute att)
            throws Exception {

        double infoGain = computeEntropy(data);
        Instances[] splitData = splitData(data, att);
        for (int j = 0; j < att.numValues(); j++) {
            if (splitData[j].numInstances() > 0) {
                infoGain -= ((double) splitData[j].numInstances()
                        / (double) data.numInstances())
                        * computeEntropy(splitData[j]);
            }
        }
        return infoGain;
    }   
    
    protected static double xComputeInfoGain(Instances data, Attribute att) {
        double[] remainders = new double[att.numValues()];
        
        for(int i=0;i<att.numValues();i++) {
            
        }
        return 0;
    }

    protected static double computeEntropy(Instances data) throws Exception {
        double entropy = 0;
        int[] classCounts = new int[data.numClasses()];
        for(int i=0;i<data.numInstances();i++) {
            classCounts[(int)data.instance(i).classValue()]++;
        }
        
        for(int i=0;i<data.numClasses();i++) {                        
            if(classCounts[i] > 0)                
                entropy -= (classCounts[i]*1.0 / data.numInstances()) * Utils.log2(classCounts[i]*1.0 / data.numInstances()); 
        }
        
        return entropy;
    }
    
    /**
     * Splits a dataset according to the values of a nominal attribute.
     *
     * @param data the data which is to be split
     * @param att the attribute to be used for splitting
     * @return the sets of instances produced by the split
     */
    // NOTE : sengaja memakai implementasi weka
    private static Instances[] splitData(Instances data, Attribute att) {

        Instances[] splitData = new Instances[att.numValues()];
        for (int j = 0; j < att.numValues(); j++) {
            splitData[j] = new Instances(data, data.numInstances());
        }
        Enumeration instEnum = data.enumerateInstances();
        while (instEnum.hasMoreElements()) {
            Instance inst = (Instance) instEnum.nextElement();
            splitData[(int) inst.value(att)].add(inst);
        }
        for (int i = 0; i < splitData.length; i++) {
            splitData[i].compactify();
        }
        return splitData;
    }
    Node root;

    public XID3Classifier() {
        super();
    }   
    
    @Override
    public void buildClassifier(Instances data) throws Exception {
        // can classifier handle the data?
        getCapabilities().testWithFail(data);

        // remove instances with missing class
        data = new Instances(data);
        data.deleteWithMissingClass();

        root = new Node();

        Node.createTree(root, data);
    }

    @Override
    public double classifyInstance(Instance instance) throws NoSupportForMissingValuesException {
        Node curn = root;
        //iterasi sampai nyampe daun
        while(curn.attribute != null) {
            curn = curn.childs[(int)instance.value(curn.attribute)];
        }
        return curn.classValue;
    }   
    
    @Override
    public double[] distributionForInstance(Instance instance) throws NoSupportForMissingValuesException {        
        Node curn = root;
        //iterasi sampai nyampe daun
        while(curn.attribute != null) {
            curn = curn.childs[(int) instance.value(curn.attribute)];
        }
        return curn.distribution;
    }
        
    @Override
    public String toString() {
        if (root.childs == null && root.distribution == null) {
            return "Id3: No model built yet.";
        }
        return "Id3\n\n" + root.printTree(0);
    }

    public static void main(String[] args) {
        BufferedReader reader = null;
        try {
            XID3Classifier xid3 = new XID3Classifier();

            reader = new BufferedReader(new FileReader("contact-lenses.arff"));
            Instances trainingSet = new Instances(reader);
            reader.close();
            //set class attribute
            trainingSet.setClass(trainingSet.attribute("contact-lenses"));

            xid3.buildClassifier(trainingSet);
            System.out.println(xid3);
            
            System.out.println("TEST COMPUTE ENTROPY");            
            System.out.println("sendiri : " + xid3.computeEntropy(trainingSet));
            
        } catch (Exception ex) {
            Logger.getLogger(XID3Classifier.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(XID3Classifier.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
