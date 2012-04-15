/*
 *    CJR48.java
 *    Copyright (C) 2012 Institute Teknologi Bandung, Bandung, Indonesia
 *
 */

/*
 * yang belum dimengerti
 * 1. serialVersionUID generate darimana sih
 * 2. distribution itu buat apa sebenernya
 * 3. datasetnya manual dibikin jadi arff?
 * 4. dari email jadi dataset digimanain?
 */



import java.util.Enumeration;
import weka.classifiers.Classifier;
import weka.classifiers.Sourcable;
import weka.core.Capabilities.Capability;
import weka.core.*;

/**
 <!-- globalinfo-start -->
 * Class for constructing an unpruned decision tree based on the CJR48 algorithm. Can only deal with nominal attributes. No missing values allowed. Empty leaves may result in unclassified instances.<br/> 
 * <p/>
 <!-- globalinfo-end -->
 *
 *
 <!-- options-start -->
 * Valid options are: <p/>
 * 
 * <pre> -D
 *  If set, classifier is run in debug mode and
 *  may output additional info to the console</pre>
 * 
 <!-- options-end -->
 *
 * @author Rendy Bambang Junior (rendy.jr@gmail.com)
 * @version $Revision: 1 $ 
 */
public class CJR48 
  extends Classifier {

  /** for serialization */
  static final long serialVersionUID = -2693678647096322562L;
  
  //!!! variable dibawah yang di awali "m_" ini bukan baku dari WEKA, tergantung implementasi algoritma buildtree dan algoritma klasifikasi kita
  
  /** The node's successors. */ 
  private CJR48[] m_Successors; //!!! tiap node kelasnya CJR48, jadi suksesor alias anak2nya CJR48 juga

  /** Attribute used for splitting. */
  private Attribute m_Attribute; //!!! jadi isi variable ini yang dipake buat splitting (bikin node anak). Misal isinya "tingkat kegantengan". jadi nanti node anaknya dipisah berdasarkan nilai tingkat kegantengan (misal: low/medium/ultimate)

  /** Class value if node is leaf. */
  private double m_ClassValue; //!!! value dari kelas kalo udah sampe daun, ini dia yang jadi hasil penelusuran tree

  /** Class distribution if node is leaf. */
  private double[] m_Distribution; //!!! katanya sih probabilitas dari masing2 kelas untuk suatu instance. buat apa yak (?)

  /** Class attribute of dataset. */
  private Attribute m_ClassAttribute; //!!! atribut dari kelas (?)

  /**
   * Returns a string describing the classifier.
   * @return a description suitable for the GUI.
   */
  public String globalInfo() { //!!! ini yang dipake sebagai info dari kelas kita

    return  "Class for constructing an unpruned decision tree based on the CJR48 "
      + "algorithm. Can only deal with nominal attributes. No missing values "
      + "allowed. Empty leaves may result in unclassified instances.";
  }
  
  /**
   * Returns default capabilities of the classifier.
   *
   * @return      the capabilities of this classifier
   */
  public Capabilities getCapabilities() {
    Capabilities result = super.getCapabilities(); //!!! kapabiliti ini adalah fitur2 dari classifier. misal kalo missing class values di enable berarti boleh ada yang valuenya nggak ada
    result.disableAll();

    // attributes
    result.enable(Capability.NOMINAL_ATTRIBUTES); //!!! atributnya nominal, kalo dikasi real atau integer nggak bisa (didisable sama WEKAnya)

    // class
    result.enable(Capability.NOMINAL_CLASS);
    result.enable(Capability.MISSING_CLASS_VALUES); //!!! boleh ada satu kelas yang nggak ada valuenya

    // instances
    result.setMinimumNumberInstances(0); ///!!! defaultnya minimal 1, kalo diginiin kita bisa mulai tanpa satu instance pun. Gausah dipikirin kalo nggak bakal isi input yang aneh2 :p
    
    //!!! nominal (= one of a predefined list of values), numeric (= a real or integer number) or a string (= an arbitrary long list of characters, enclosed in ”double quotes”).
    
    return result;
  }

  /**
   * Builds CJR48 decision tree classifier.
   *
   * @param data the training data
   * @exception Exception if classifier can't be built successfully
   */
  public void buildClassifier(Instances data) throws Exception { //!!! ini tahap preprosesor, sebelum bisa ngeklasifikasiin, perlu di build dulu

    // can classifier handle the data?
    getCapabilities().testWithFail(data); //!!! ngecek datanya, dalam hal ini nominal semua apa bukan

    // remove instances with missing class
    data = new Instances(data); //!!! di cctor biar data asli nggak rusak
    data.deleteWithMissingClass(); //!!! ilangin instance yang ada missing valuenya
    
    makeTree(data); //!!! disini proses learningnya, bikin dtree
  }

  /**
   * Method for building an CJR48 tree.
   *
   * @param data the training data
   * @exception Exception if decision tree can't be built successfully
   */
  private void makeTree(Instances data) throws Exception { //!!! prosesnya kita implementasi masing2 :3

    // Check if no instances have reached this node.
    if (data.numInstances() == 0) {
      m_Attribute = null;
      m_ClassValue = Instance.missingValue();
      m_Distribution = new double[data.numClasses()];
      return;
    }

    // Compute attribute with maximum information gain.
    double[] infoGains = new double[data.numAttributes()];
    Enumeration attEnum = data.enumerateAttributes();
    while (attEnum.hasMoreElements()) {
      Attribute att = (Attribute) attEnum.nextElement();
      infoGains[att.index()] = computeInfoGain(data, att);
    }
    m_Attribute = data.attribute(Utils.maxIndex(infoGains));
    
    // Make leaf if information gain is zero. 
    // Otherwise create successors.
    if (Utils.eq(infoGains[m_Attribute.index()], 0)) {
      m_Attribute = null;
      m_Distribution = new double[data.numClasses()];
      Enumeration instEnum = data.enumerateInstances();
      while (instEnum.hasMoreElements()) {
        Instance inst = (Instance) instEnum.nextElement();
        m_Distribution[(int) inst.classValue()]++;
      }
      Utils.normalize(m_Distribution);
      m_ClassValue = Utils.maxIndex(m_Distribution);
      m_ClassAttribute = data.classAttribute();
    } else {
      Instances[] splitData = splitData(data, m_Attribute);
      m_Successors = new CJR48[m_Attribute.numValues()];
      for (int j = 0; j < m_Attribute.numValues(); j++) {
        m_Successors[j] = new CJR48();
        m_Successors[j].makeTree(splitData[j]);
      }
    }
  }

  /**
   * Classifies a given test instance using the decision tree.
   *
   * @param instance the instance to be classified
   * @return the classification
   * @throws NoSupportForMissingValuesException if instance has missing values
   */
  public double classifyInstance(Instance instance) //!!! disini instance diklasifikasikan pake tree yang udah di build
    throws NoSupportForMissingValuesException {

    if (instance.hasMissingValue()) {
      throw new NoSupportForMissingValuesException("CJR48: no missing values, "
                                                   + "please.");
    }
    if (m_Attribute == null) {
    System.out.println((float)5/2);
      return m_ClassValue;
    } else {
        System.out.println("llll");
      return m_Successors[(int) instance.value(m_Attribute)].classifyInstance(instance);
    }
  }

  /**
   * Computes class distribution for instance using decision tree.
   *
   * @param instance the instance for which distribution is to be computed
   * @return the class distribution for the given instance
   * @throws NoSupportForMissingValuesException if instance has missing values
   */
  public double[] distributionForInstance(Instance instance) //!!! melihat probabilitas distribusi kelas untuk certain instance
    throws NoSupportForMissingValuesException {

    if (instance.hasMissingValue()) {
      throw new NoSupportForMissingValuesException("CJR48: no missing values, "
                                                   + "please.");
    }
    if (m_Attribute == null) {
      return m_Distribution;
    } else { 
      return m_Successors[(int) instance.value(m_Attribute)].
        distributionForInstance(instance);
    }
  }

  /**
   * Prints the decision tree using the private toString method from below.
   *
   * @return a textual description of the classifier
   */
  public String toString() { //!!! ini tree yang dimunculin di output yang diGUI. kenapa ada toString(0)? Itu maksudnya rekursif dari node yang merupakan level ke 0. (jadi kalo mau print dari level ke 1, taro 1. Ini tergantung implementasi tree masing-masing

    if ((m_Distribution == null) && (m_Successors == null)) {
      return "CJR48: No model built yet.";
    }
    return "CJR48\n\n" + toString(0);
  }

  //!!! fungsi-fungsi di bawah ini adalah implementasi dari tree versi ID3. kita nanti bikin masing-masing gitu. fungsi-fungsi yang diatas yang harus diimplement karena dibikin abstract dari parentnya
  
  /**
   * Computes information gain for an attribute.
   *
   * @param data the data for which info gain is to be computed
   * @param att the attribute
   * @return the information gain for the given attribute and data
   * @throws Exception if computation fails
   */
  private double computeInfoGain(Instances data, Attribute att) 
    throws Exception {

    double infoGain = computeEntropy(data);
    Instances[] splitData = splitData(data, att);
    for (int j = 0; j < att.numValues(); j++) {
      if (splitData[j].numInstances() > 0) {
        infoGain -= ((double) splitData[j].numInstances() /
                     (double) data.numInstances()) *
          computeEntropy(splitData[j]);
      }
    }
    return infoGain;
  }

  /**
   * Computes the entropy of a dataset.
   * 
   * @param data the data for which entropy is to be computed
   * @return the entropy of the data's class distribution
   * @throws Exception if computation fails
   */
  private double computeEntropy(Instances data) throws Exception {

    double [] classCounts = new double[data.numClasses()];
    Enumeration instEnum = data.enumerateInstances();
    while (instEnum.hasMoreElements()) {
      Instance inst = (Instance) instEnum.nextElement();
      classCounts[(int) inst.classValue()]++;
    }
    double entropy = 0;
    for (int j = 0; j < data.numClasses(); j++) {
      if (classCounts[j] > 0) {
        entropy -= classCounts[j] * Utils.log2(classCounts[j]);
      }
    }
    entropy /= (double) data.numInstances();
    return entropy + Utils.log2(data.numInstances());
  }

  /**
   * Splits a dataset according to the values of a nominal attribute.
   *
   * @param data the data which is to be split
   * @param att the attribute to be used for splitting
   * @return the sets of instances produced by the split
   */
  private Instances[] splitData(Instances data, Attribute att) {

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

  /**
   * Outputs a tree at a certain level.
   *
   * @param level the level at which the tree is to be printed
   * @return the tree as string at the given level
   */
  private String toString(int level) { //!!! ini dia toString custom punya om om yang bikin, yg tadi dibilang rekursif

    StringBuffer text = new StringBuffer();
    
    if (m_Attribute == null) {
      if (Instance.isMissingValue(m_ClassValue)) {
        text.append(": null");
      } else {
        text.append(": " + m_ClassAttribute.value((int) m_ClassValue));
      } 
    } else {
      for (int j = 0; j < m_Attribute.numValues(); j++) {
        text.append("\n");
        for (int i = 0; i < level; i++) {
          text.append("|  ");
        }
        text.append(m_Attribute.name() + " = " + m_Attribute.value(j));
        text.append(m_Successors[j].toString(level + 1));
      }
    }
    return text.toString();
  }
  
  /**
   * Returns the revision string.
   * 
   * @return		the revision
   */
  public String getRevision() {
    return RevisionUtils.extract("$Revision: 1$");
  }

  /**
   * Main method.
   *
   * @param args the options for the classifier
   */
  public static void main(String[] args) {
    runClassifier(new CJR48(), args);
  }
}
