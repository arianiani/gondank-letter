package util;

import java.io.*;
import weka.classifiers.Classifier;
import weka.core.Instances;

public class FileUtil {

    public static void saveInstances(Instances data, String filename) throws Exception {
        BufferedWriter writer;
        writer = new BufferedWriter(new FileWriter(filename));
        writer.write(data.toString());
        writer.newLine();
        writer.flush();
        writer.close();
    }

    public static Instances loadInstances(String filename) throws Exception {
        Instances result;
        BufferedReader reader;

        reader = new BufferedReader(new FileReader(filename));
        result = new Instances(reader);
        result.setClassIndex(result.numAttributes() - 1);
        reader.close();

        return result;
    }
       
}
