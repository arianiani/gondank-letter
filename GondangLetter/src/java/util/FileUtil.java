package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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

    public static Instances loadInstances(Instances data, String filename) throws Exception {
        Instances result;
        BufferedReader reader;

        reader = new BufferedReader(new FileReader(filename));
        result = new Instances(reader);
        result.setClassIndex(result.numAttributes() - 1);
        reader.close();

        return result;
    }
}
