package weka;

import bayes.WBYSContainer;
import idtree.WID3Container;
import evaluation.EvalUtil;
import evaluation.Result;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import kNN.HKNNClassifier;
import kNN.HKNNContainer;
import kNN.WKNNContainer;
import preprocess.PrepUtil;
import util.FileUtil;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.Id3;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;
public class WekaEngine {
    String wekaFile;
    String modelFile;
    String dataTestFile;
    public Instances dataWeka;
    int classIndex = 0;
    public boolean inputForm = false;
    public int[] inputDataForm=new int[]{};
    public double wekaCorrect=0, ourCorrect=0;
    public void applyPreprocess(String id){
        int preprocess = Integer.parseInt(id);
        String fileId ="";
            switch(preprocess){
                case 1:
                    dataWeka = PrepUtil.unsupervisedDiscretize(dataWeka);
                    fileId = "-discretize";
                    break;
                case 2:
                    dataWeka = PrepUtil.unsupervisedNormalize(dataWeka);
                    fileId = "-normalize";
                    break;
                case 3:
                    dataWeka = PrepUtil.unsupervisedReplaceMissingValue(dataWeka);
                    fileId = "-replacemissingvalue";
                    break;
                case 4:
                    dataWeka = PrepUtil.supervisedAttributeSelection(dataWeka);
                    fileId = "-attributeselection";
                    break;
                default:
                    break;
            }
        try {
            // save ARFF
            ArffSaver saver = new ArffSaver();
            saver.setInstances(dataWeka);
            wekaFile = wekaFile.substring(0, wekaFile.length()-5);
            wekaFile += fileId+".arff";
            System.out.println(wekaFile);
            saver.setFile(new File(wekaFile));
            //saver.setDestination(new File(fileArff));
            saver.writeBatch();
        } catch (IOException ex) {
            System.out.println("file could not be saved");
        }
    }
    public String getFile(){
        return wekaFile;   
    }
    public void fileLoader(String fileSource){
        String result="";
        try {
            fileSource = fileSource.replaceAll("\\\\", "/");
            //fileSource = "D:/Kampus/Kuliah IF/Semester 6/IB/Tubes 2/SVN/gondank-letter/GondangLetter/build/web/file/letter-recognition.arff";
            System.out.println("ini file= "+fileSource);
            dataWeka = DataSource.read(fileSource);
            dataWeka.setClass(dataWeka.attribute(11));
            wekaFile = fileSource;
        } catch (Exception ex) {
            System.out.println("Error exception");
        }
    }
    public void modelLoader(String fileSource){
        String result="";
        try {
//            fileSource = fileSource.replaceAll("\\\\", "/");
//            System.out.println("ini file= "+fileSource);
//            dataWeka = DataSource.read(fileSource);
//            dataWeka.setClass(dataWeka.attribute(11));
            wekaFile = fileSource;
            // deserialize model
            Classifier cls = (Classifier) weka.core.SerializationHelper.read(fileSource);
            //System.out.println("Class = " + cls.classifyInstance(dataWeka));
            System.out.println(cls);
            
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
            wekaFile = fileArff;
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
    public String evaluateModel(int algoritma, int knn, int[] remAttr){
        String result="";
        
        try {
            switch (algoritma){
                case 1:
                    result+="<td>"+wekaKnn(knn, remAttr)+"</td>";
                    result+="<td>"+myKnn(knn, remAttr)+"</td>";
                    result+="<td colspan='2'>";
                    System.out.println("weka : "+wekaCorrect);
                    System.out.println("our : "+ourCorrect);
                    if(wekaCorrect>ourCorrect){
                        result+="Weka's better";
                    }else{
                        result+="Our's better";                        
                    }
                    result+="</td>";
                    break;
                case 2 :
                    result+="<td>"+wekaBayes(remAttr)+"</td>";
                    result+="<td>"+myBayes(remAttr)+"</td>";
                    result+="<td colspan='2'>";
                    System.out.println("weka : "+wekaCorrect);
                    System.out.println("our : "+ourCorrect);
                    if(wekaCorrect>ourCorrect){
                        result+="Weka's better";
                    }else{
                        result+="Our's better";                        
                    }
                    result+="</td>";
                    break;
                case 3:
                    result+="<td>"+wekaId3(remAttr)+"</td>";
                    result+="<td>"+myId3(remAttr)+"</td>";
                    result+="<td colspan='2'>";
                    System.out.println("weka : "+wekaCorrect);
                    System.out.println("our : "+ourCorrect);
                    if(wekaCorrect>ourCorrect){
                        result+="Weka's better";
                    }else{
                        result+="Our's better";                        
                    }
                    result+="</td>";
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            Logger.getLogger(WekaEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    public String processingInputForm(boolean addData){
        String result="";
        int[] data = inputDataForm;
        BufferedReader reader = null;
//        try {
            System.out.println("Processing Input");
            //cerating new instance
            double[] values = new double[data.length];
            for(int i=0;i<data.length;i++){
                values[i] = data[i];
            }
//            reader = new BufferedReader(
//            new FileReader("/empty-letter.arff"));
//            Instance instance = new Instance(1.0, values);
//            Instances ada;
//            try {
//                ada = new Instances(reader);
//                ada.add(instance);
                String chooser = wekaFile.substring(wekaFile.length()-6-4, wekaFile.length()-6);
                System.out.println("choose = "+chooser);
                result+=chooseClassify(chooser, addData);
//            } catch (IOException ex) {
//                Logger.getLogger(WekaEngine.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(WekaEngine.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                reader.close();
//            } catch (IOException ex) {
//                Logger.getLogger(WekaEngine.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        return result;
    }
    public String myId3(int[] mRemAttr){
        return "Id3 milik sendiri";
    }
    public String wekaId3(int[] mRemAttr){
        String result="";
        int[] removedAttributes = mRemAttr;    
        result+=("<br>=====Cross Validation======<br>");
        WID3Container id3 = new WID3Container(dataWeka, removedAttributes, classIndex);
        //System.out.println("a");
        Result cvResult = new Result();
        result+=EvalUtil.crossValidation(id3.getClassifier(), id3.getTrainingSet(), 10, cvResult);
        result+=("<br>akurasi (benar - salah) : " + cvResult.getPctCorrect() + " - " + cvResult.getPctIncorrect()+"<br>");
        wekaCorrect = cvResult.getPctCorrect();

        result+=("<br>====OUTPUT MODEL=======<br>");
        id3.trainModel();
        result+=id3.outputModel();
        
        // save model
        String fileModel = wekaFile.substring(0, wekaFile.length()-5);
        fileModel+="-wid3.model";
        try {
            weka.core.SerializationHelper.write(fileModel, id3.getClassifier());
        } catch (Exception ex) {
            Logger.getLogger(WekaEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    public String myBayes(int[] mRemAttr){
        return "bayes milik sendiri";
    }
    public String wekaBayes(int[] mRemAttr){
        String result="";
        int[] removedAttributes = mRemAttr;    

        result+=("<br>=====Cross Validation======<br>");
        System.out.println(dataWeka==null?"null":"not");
        System.out.println(removedAttributes==null?"null":"not");
        System.out.println(classIndex==0?"null":"not");
        WBYSContainer by = new WBYSContainer(dataWeka, removedAttributes, classIndex);
        Result cvResult = new Result();
        result+=EvalUtil.crossValidation(by.getClassifier(), by.getTrainingSet(), 10, cvResult);
        result+=("<br>akurasi (benar - salah) : " + cvResult.getPctCorrect() + " - " + cvResult.getPctIncorrect()+"<br>");
        wekaCorrect = cvResult.getPctCorrect();
        result+=("<br>====OUTPUT MODEL=======<br>");
        by.trainModel();
        result+=by.outputModel();
        
        // save model
        String fileModel = wekaFile.substring(0, wekaFile.length()-5);
        fileModel+="-wbys.model";
        try {
            weka.core.SerializationHelper.write(fileModel, by.getClassifier());
        } catch (Exception ex) {
            Logger.getLogger(WekaEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    public String wekaKnn(int k, int[] mRemAttr){
        String result="";
        int[] removedAttributes = mRemAttr;    

        result+=("<br>=====Cross Validation======<br>");
        WKNNContainer hc = new WKNNContainer(k, dataWeka, removedAttributes, classIndex);
        Result cvResult = new Result();
        result+=EvalUtil.crossValidation(hc.getClassifier(), hc.getTrainingSet(), 10, cvResult);
        result+=("<br>akurasi (benar - salah) : " + cvResult.getPctCorrect() + " - " + cvResult.getPctIncorrect()+"<br>");
        wekaCorrect = cvResult.getPctCorrect();

        result+=("<br>====OUTPUT MODEL=======<br>");
        hc.trainModel();
        result+=hc.outputModel();
        
        // save model
        String fileModel = wekaFile.substring(0, wekaFile.length()-5);
        fileModel+="-wknn.model";
        try {
            weka.core.SerializationHelper.write(fileModel, hc.getClassifier());
        } catch (Exception ex) {
            Logger.getLogger(WekaEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }    
    public String myKnn(int k, int[] mRemAttr){
        String result="";
        int[] removedAttributes = mRemAttr;    

        result+=("<br>=====Cross Validation======<br>");
        HKNNContainer hc = new HKNNContainer(k, dataWeka, removedAttributes, classIndex);
        Result cvResult = new Result();
        result+=EvalUtil.crossValidation(hc.getClassifier(), hc.getTrainingSet(), 10, cvResult);
        result+=("<br>akurasi (benar - salah) : " + cvResult.getPctCorrect() + " - " + cvResult.getPctIncorrect()+"<br>");
        ourCorrect = cvResult.getPctCorrect();

        result+=("<br>====OUTPUT MODEL=======<br>");
        hc.trainModel();
        result+=hc.outputModel();
        
        // save model
        String fileModel = wekaFile.substring(0, wekaFile.length()-5);
        fileModel+="-hknn.model";
        try {
            weka.core.SerializationHelper.write(fileModel, hc.getClassifier());
        } catch (Exception ex) {
            Logger.getLogger(WekaEngine.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
    public String chooseClassify(String chooser, boolean addData){
        String result="";
        try {
        if(chooser.equals("hknn")){
                result+=("<br>==== Classify HKNN =======");
                // deserialize model
                HKNNClassifier hccl = (HKNNClassifier) weka.core.SerializationHelper.read(wekaFile);
                HKNNContainer hcc = new HKNNContainer(hccl);

                Instances testdata=null;
                try {
                    testdata = FileUtil.loadInstances(dataTestFile);
                    testdata.setClassIndex(classIndex);
                } catch (Exception ex) {
                    System.out.println("err : " + ex.getMessage());
                }                                                 

                Instances resultInstance = null;          
                try {
                    resultInstance=hcc.classifyData(testdata, addData);
                    //resultInstance = cls.classifyInstance(testdata);
                } catch (Exception ex) {
                    Logger.getLogger(HKNNContainer.class.getName()).log(Level.SEVERE, null, ex);
                }
                result+=("<br>result : <br>");
                result+=("<br>"+resultInstance.toString()+"<br>");
                System.out.println("result : ");
                System.out.println(resultInstance.toString());
                
                if(addData){
                    // save model
                    try {
                        weka.core.SerializationHelper.write(wekaFile, hcc.getClassifier());
                    } catch (Exception ex) {
                        Logger.getLogger(WekaEngine.class.getName()).log(Level.SEVERE, null, ex);
                    }   
                }
            }else if(chooser.equals("wknn")){
                result+=("<br>==== Classify Weka KNN =======");
                // deserialize model
                IBk ibk = (IBk) weka.core.SerializationHelper.read(wekaFile);
                WKNNContainer hcc = new WKNNContainer(ibk);
                Instances testdata=null;
                try {
                    testdata = FileUtil.loadInstances(dataTestFile);
                    testdata.setClassIndex(classIndex);
                } catch (Exception ex) {
                    System.out.println("err : " + ex.getMessage());
                }                                                 

                Instances resultInstance = null;          
                try {
                    resultInstance=hcc.classifyData(testdata, addData);
                    //resultInstance = cls.classifyInstance(testdata);
                } catch (Exception ex) {
                    Logger.getLogger(HKNNContainer.class.getName()).log(Level.SEVERE, null, ex);
                }
                result+=("<br>result : <br>");
                result+=("<br>"+resultInstance.toString()+"<br>");
                System.out.println("result : ");
                System.out.println(resultInstance.toString());                
                if(addData){
                    // save model
                    try {
                        weka.core.SerializationHelper.write(wekaFile, hcc.getClassifier());
                    } catch (Exception ex) {
                        Logger.getLogger(WekaEngine.class.getName()).log(Level.SEVERE, null, ex);
                    }   
                }        
            }else if(chooser.equals("abys")){ // naive bayes aul

            }else if(chooser.equals("wbys")){ // weka naive bayes
                result+=("<br>==== Classify Weka Bayes =======");
                // deserialize model
                NaiveBayes bys = (NaiveBayes) weka.core.SerializationHelper.read(wekaFile);
                WBYSContainer hcc = new WBYSContainer(bys);
                Instances testdata=null;
                try {
                    testdata = FileUtil.loadInstances(dataTestFile);
                    testdata.setClassIndex(classIndex);
                } catch (Exception ex) {
                    System.out.println("err : " + ex.getMessage());
                }                                                 

                Instances resultInstance = null;          
                try {
                    resultInstance=hcc.classifyData(testdata, addData);
                    //resultInstance = cls.classifyInstance(testdata);
                } catch (Exception ex) {
                    Logger.getLogger(HKNNContainer.class.getName()).log(Level.SEVERE, null, ex);
                }
                result+=("<br>result : <br>");
                result+=("<br>"+resultInstance.toString()+"<br>");
                System.out.println("result : ");
                System.out.println(resultInstance.toString());                
                if(addData){
                    // save model
                    try {
                        weka.core.SerializationHelper.write(wekaFile, hcc.getClassifier());
                    } catch (Exception ex) {
                        Logger.getLogger(WekaEngine.class.getName()).log(Level.SEVERE, null, ex);
                    }   
                }
            }else if(chooser.equals("did3")){ // id3 dihya
                
            }else if(chooser.equals("wid3")){
                result+=("<br>==== Classify Weka ID3 =======");
                // deserialize model
                Id3 ibk = (Id3) weka.core.SerializationHelper.read(wekaFile);
                WID3Container hcc = new WID3Container(ibk);
                Instances testdata=null;
                try {
                    testdata = FileUtil.loadInstances(dataTestFile);
                    testdata.setClassIndex(classIndex);
                } catch (Exception ex) {
                    System.out.println("err : " + ex.getMessage());
                }                                                 

                Instances resultInstance = null;          
                try {
                    resultInstance=hcc.classifyData(testdata, addData);
                    //resultInstance = cls.classifyInstance(testdata);
                } catch (Exception ex) {
                    Logger.getLogger(HKNNContainer.class.getName()).log(Level.SEVERE, null, ex);
                }
                result+=("<br>result : <br>");
                result+=("<br>"+resultInstance.toString()+"<br>");
                System.out.println("result : ");
                System.out.println(resultInstance.toString());                
                if(addData){
                    // save model
                    try {
                        weka.core.SerializationHelper.write(wekaFile, hcc.getClassifier());
                    } catch (Exception ex) {
                        Logger.getLogger(WekaEngine.class.getName()).log(Level.SEVERE, null, ex);
                    }   
                }
            }        
        } catch (Exception ex) {
            Logger.getLogger(WekaEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    public String classify(boolean addData){
        String result="";
        String chooser = wekaFile.substring(wekaFile.length()-6-4, wekaFile.length()-6);
        System.out.println("choose = "+chooser);            
        result+=chooseClassify(chooser, addData);
        return result;        
    }
    
    public void dataTestLoader(String fileSource){
        dataTestFile = fileSource.replaceAll("\\\\", "/");
        System.out.println("data test file : "+dataTestFile);
    }
    
    public boolean isExist(ArrayList<Integer> dataset, int input){
        boolean found = false;
        int counter=0;
        while(!found && counter<dataset.size()){
            if(dataset.get(counter)==input){
                found = true;
            }
        }
        return found;
    }
    public boolean isExist(ArrayList<String> dataset, String input){
        boolean found = false;
        int counter=0;
        while(!found && counter<dataset.size()){
            if(dataset.get(counter).equals(input)){
                found = true;
            }
        }
        return found;
    }
    public static void main(String[] args){
        try {
            WekaEngine WE = new WekaEngine();
            //WE.convertCsvToArff("D:/Kampus/Kuliah IF/Semester 6/IB/Tubes 2/SVN/gondank-letter/GondangLetter/build/web/file/letter-recognition.csv");
            WE.fileLoader("D:/Kampus/Kuliah IF/Semester 6/IB/Gondank-Letter/build/web/file/letter-recognitiontest.arff");
//            System.out.println(WE.evaluateModel(1, 1, new int[]{}));
//            System.out.println(WE.dataWeka.attributeStats(0));
//            System.out.println("nominal="+WE.dataWeka.attributeStats(0).nominalCounts[4]);
//            System.out.println(WE.dataWeka.attributeStats(1));
//            System.out.println("ada");
//            
            WE.evaluateModel(3, 1, new int[]{}); 
            //ArrayList<String> kelas = new ArrayList<String>();
            //Map<Double, Integer> hashMap = new HashMap<Double, Integer>();
//            for(int i=0;i<WE.dataWeka.numAttributes();i++){
//                System.out.println("Atribut : "+(i+1));
//                for(int j=0;j<WE.dataWeka.attributeToDoubleArray(i).length;j++){
//                    System.out.print(j+":"+WE.dataWeka.attributeToDoubleArray(i).length+" ");
//                    if(hashMap.get(WE.dataWeka.attributeToDoubleArray(i)[j])==null){
//                        hashMap.put(WE.dataWeka.attributeToDoubleArray(i)[j], 1);
//                        System.out.println("a"+hashMap.get(WE.dataWeka.attributeToDoubleArray(i)[j]));
//                    }
//                    else{
//                        hashMap.put(WE.dataWeka.attributeToDoubleArray(i)[j], hashMap.get(WE.dataWeka.attributeToDoubleArray(i)[j])+1);
//                        System.out.println((int)WE.dataWeka.attributeToDoubleArray(i)[j] +"="+hashMap.get(WE.dataWeka.attributeToDoubleArray(i)[j]));
//                    }
//                    //System.out.println("W"+i+" "+WE.dataWeka.numAttributes());
//                }
//
//                System.out.println(hashMap.get(WE.dataWeka.attributeToDoubleArray(i)[0]));                
//            }
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
