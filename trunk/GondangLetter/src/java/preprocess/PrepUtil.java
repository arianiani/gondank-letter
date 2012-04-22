package preprocess;

import weka.core.Instances;
import weka.filters.Filter;

public class PrepUtil {

    public static Instances unsupervisedDiscretize(Instances data) {
        try {
            Filter filter = new weka.filters.unsupervised.attribute.Discretize();
            filter.setInputFormat(data);
            return Filter.useFilter(data, filter);            
        } catch (Exception ex) {
            System.err.println("err : " + ex.getMessage());
            return null;
        }
    }
    
    public static Instances unsupervisedNumericToNominal(Instances data) {
        try {
            Filter filter = new weka.filters.unsupervised.attribute.NumericToNominal();
            filter.setInputFormat(data);
            return Filter.useFilter(data, filter);            
        } catch (Exception ex) {
            System.err.println("err : " + ex.getMessage());
            return null;
        }
    }
    
    public static Instances unsupervisedNormalize(Instances data) {
        try {           
            Filter filter = new weka.filters.unsupervised.attribute.Normalize();
            filter.setInputFormat(data);
            return Filter.useFilter(data, filter);
        } catch (Exception ex) {
            System.err.println("err : " + ex.getMessage());
            return null;
        }
    }
    
    public static Instances unsupervisedReplaceMissingValue(Instances data) {
        try {
            Filter filter = new weka.filters.unsupervised.attribute.ReplaceMissingValues();
            filter.setInputFormat(data);
            return Filter.useFilter(data, filter);
        } catch (Exception ex) {
            System.err.println("err : " + ex.getMessage());
            return null;
        }
    } 
    
    public static Instances supervisedAttributeSelection(Instances data) {
        try {
            Filter filter = new weka.filters.supervised.attribute.AttributeSelection();
            filter.setInputFormat(data);
            return Filter.useFilter(data, filter);
        } catch (Exception ex) {
            System.err.println("err : " + ex.getMessage());
            return null;
        }
    }
    
}
