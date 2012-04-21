package weka;

import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;
public class WekaEngine {
    String wekaFile;
    public Instances dataWeka;
    
    public String getFile(){
        return wekaFile;   
    }
    public void fileLoader(String fileSource){
        String result="";
        try {
            fileSource = fileSource.replaceAll("\\\\", "/");
            //fileSource = "D:/Kampus/Kuliah IF/Semester 6/IB/Tubes 2/SVN/gondank-letter/GondangLetter/build/web/file/letter-recognition.arff";
            System.out.println("ini file= "+fileSource);
            dataWeka = DataSource.read("test.arff");
            dataWeka.setClass(dataWeka.attribute(11));
        } catch (Exception ex) {
            System.out.println("Error exception");
        }
    }
    public void convertCsvToArff(String fileCsv){
        // load CSV
        String test = fileCsv.toLowerCase();
        System.out.println("Masuk method Convert "+fileCsv);
        if(fileCsv.length()>4 &&  test.charAt(fileCsv.length()-1) =='v' && test.charAt(fileCsv.length()-2) =='s' && test.charAt(fileCsv.length()-3) =='c' && test.charAt(fileCsv.length()-4) =='.'){
            fileCsv = fileCsv.replaceAll("\\\\", "/");
            String fileArff = fileCsv.substring(0, fileCsv.length()-3);
            fileArff+="arff";
            System.out.println("Masuk method Convert "+fileArff);
            
            CSVLoader loader = new CSVLoader();
            try {
                loader.setSource(new File(fileCsv));
                System.out.println("lulus");
                Instances data = loader.getDataSet();
                // save ARFF
                ArffSaver saver = new ArffSaver();
                saver.setInstances(data);
                saver.setFile(new File(fileArff));
                //saver.setDestination(new File(fileArff));
                saver.writeBatch();
                try {
                    dataWeka = DataSource.read(fileArff);
                } catch (Exception ex) {
                    System.out.println("Exception read file");
                }
                dataWeka.setClass(dataWeka.attribute(11));
                System.out.println("Convert selesai");
            } catch (IOException ex) {
                System.out.println("Exception File convertCsvToArff");
            }
        }
    }
    public String classify(){
        String result="";
        try {
            IBk ibk = new IBk(3);
                
                ibk.buildClassifier(dataWeka);
                
                double[] values = new double[dataWeka.numAttributes()];
                values[0] = dataWeka.attribute(0).indexOfValue("?");
                values[1] = dataWeka.attribute(0).indexOfValue("3");
                values[2] = dataWeka.attribute(0).indexOfValue("9");
                values[3] = dataWeka.attribute(0).indexOfValue("5");
                values[4] = dataWeka.attribute(0).indexOfValue("7");
                values[5] = dataWeka.attribute(0).indexOfValue("4");
                values[6] = dataWeka.attribute(0).indexOfValue("8");
                values[7] = dataWeka.attribute(0).indexOfValue("7");
                values[8] = dataWeka.attribute(0).indexOfValue("3");
                values[9] = dataWeka.attribute(0).indexOfValue("8");
                values[10] = dataWeka.attribute(0).indexOfValue("5");
                values[11] = dataWeka.attribute(0).indexOfValue("6");
                values[12] = dataWeka.attribute(0).indexOfValue("8");
                values[13] = dataWeka.attribute(0).indexOfValue("2");
                values[14] = dataWeka.attribute(0).indexOfValue("8");
                values[15] = dataWeka.attribute(0).indexOfValue("6");
                values[16] = dataWeka.attribute(0).indexOfValue("7");

                Instance instance = new Instance(1, values);
                instance.setDataset(dataWeka);

                result+= ibk;
        } catch (Exception ex) {
            Logger.getLogger(WekaEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    public static void main(String[] args){
        try {
            WekaEngine WE = new WekaEngine();
            //WE.convertCsvToArff("D:/Kampus/Kuliah IF/Semester 6/IB/Tubes 2/SVN/gondank-letter/GondangLetter/build/web/file/letter-recognition.csv");
            WE.fileLoader("D:/Kampus/Kuliah IF/Semester 6/IB/Tubes 2/SVN/gondank-letter/GondangLetter/build/web/file/letter-recognition.arff");
            System.out.println(WE.classify());
            for(int i=0;i<WE.dataWeka.numAttributes();i++){
                System.out.println("Atribut : "+(i+1));
                for(int j=0;j<WE.dataWeka.attributeToDoubleArray(i).length;j++){
                    System.out.print(WE.dataWeka.attributeToDoubleArray(i)[j]+" ");
                }
                System.out.println();
                
            }
//            //load training dataset
//            Instances data = DataSource.read("D:/Kampus/Kuliah IF/Semester 6/IB/Tubes 2/SVN/gondank-letter/GondangLetter/build/web/file/letter-recognition.csv");
//            System.out.println("WOI");
//            data.setClass(data.attribute(11));
//            ArffSaver saver = new ArffSaver();
//            saver.setInstances(data);
//            saver.setFile(new File("contoh juga.arff"));
//            //saver.setDestination(new File(fileArff));
//            saver.writeBatch();
//            //create classifier
//            IBk tree = new IBk();
//            
//            //train classifier using dataset
//            tree.buildClassifier(data);
//            
//            //cerating new instance
//            double[] values = new double[data.numAttributes()];
//            values[0] = data.attribute(0).indexOfValue("T");
//            values[1] = data.attribute(1).indexOfValue("F");
//            values[2] = data.attribute(2).indexOfValue("F");
//            values[3] = data.attribute(3).indexOfValue("T");
//            values[4] = data.attribute(4).indexOfValue("Some");
//            values[5] = data.attribute(5).indexOfValue("$$$");
//            values[6] = data.attribute(6).indexOfValue("F");
//            values[7] = data.attribute(7).indexOfValue("T");
//            values[8] = data.attribute(8).indexOfValue("French");
//            values[9] = data.attribute(9).indexOfValue("0-10");
//            values[10] = data.attribute(10).indexOfValue("T");
//            values[11] = data.attribute(11).indexOfValue("");
//            //test classifier using an instance           
//            Instance instance = new Instance(1.0, values);
//            instance.setDataset(data);
//            System.out.println("Class = "+tree.classifyInstance(instance));
        } catch (Exception ex) {
        }
        
        
        
    }
}
