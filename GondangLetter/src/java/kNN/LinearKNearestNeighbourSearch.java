package kNN;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.FileUtil;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class LinearKNearestNeighbourSearch implements Serializable {

    protected Instances data;
    protected double[] distances;    
    
    protected static class Element {

        public Instance instance;
        public double distance;
        public int index;

        public Element(Instance instance, double distance, int index) {
            this.distance = distance;
            this.instance = instance;
            this.index = index;
        }
    }

    //sorted list bisa memuat elemen lebih banyak dibandingkan maxSize yang diberikan, 
    protected static class SortedList {

        protected ArrayList<Element> sel;
        protected int k;        

        public SortedList(int maxSize) {
            sel = new ArrayList<Element>(maxSize);
            k = maxSize;
        }

        public void insert(Instance instance, double distance, int index) {
            if (sel.isEmpty()) {
                sel.add(new Element(instance, distance, index));
                return;
            }

            int i;
            for (i = 0; i < k && i<sel.size() &&  sel.get(i).distance < distance; i++) 
            ;
            
            if ((i < k) || (sel.size()>=k && i == k - 1 && distance == sel.get(i).distance)) {
                sel.add(i, new Element(instance, distance, index));
            }
        }

        public ArrayList<Element> getInternalList() {
            return sel;
        }
        
        public void setInternalList(ArrayList<Element> arr) {
            sel = arr;
        } 
    }

    public LinearKNearestNeighbourSearch() {
    }

    public void setInstaces(Instances instances) {
        data = instances;
    }

    //menambah instance ke data
    public void addInstance(Instance instance) {
        data.add(instance);
    }

    public Instances kNearestNeighbours(Instance target, int k) {                        
        SortedList sl = new SortedList(k);
        for (int i = 0; i < data.numInstances(); i++) {            
            sl.insert(data.instance(i), calcDistance(target, data.instance(i)), i);
        }                                  
        
        int nnsize=sl.getInternalList().size();
        if(sl.getInternalList().size() > k) {
            int i = k;
            while(i<sl.getInternalList().size() && Double.compare(sl.getInternalList().get(k-1).distance, sl.getInternalList().get(i).distance)==0) {                
                i++;
            } 
            nnsize = i;
        }                        
        
        sl.setInternalList(new ArrayList(sl.getInternalList().subList(0, nnsize)));        
        Instances ret = new Instances(data, sl.getInternalList().size());        
        distances = new double[sl.getInternalList().size()];        
        for(int i=0;i<distances.length;i++) {
            distances[i] = sl.getInternalList().get(i).distance;
            ret.add(sl.getInternalList().get(i).instance);            
        }        
        
        return ret;
    }

    public double[] getDistances() {
        return distances;
    }
    
    //asumsi instance a dan b mempunyai urutan attribute yang sama dan class index yang sama
    //hanya mendukung tipe data numerik dan nominal
    //distance untuk nilai attribute yang sama menghasilkan jarak yang lebih pendek
    //gak ada normalisasi untuk missing value
    public static double calcDistance(Instance a, Instance b) {
        int classIndex = a.classIndex();
        double sqrDistance = 0;

        for (int i = 0; i < a.numAttributes(); i++) {
            double diff = 0;

            if (i == classIndex) {
                continue;
            }

            double val1 = a.valueSparse(i);
            double val2 = b.valueSparse(i);

            if (Instance.isMissingValue(val1) || Instance.isMissingValue(val2)) {
                diff = 1;
            } else if (a.attribute(i).type() == Attribute.NOMINAL) {
                if ((int) val1 == (int) val2) {
                    diff = 0;
                } else {
                    diff = 1;
                }
            } else if (a.attribute(i).type() == Attribute.NUMERIC) {
                diff = val1 - val2;
            }
            sqrDistance += diff * diff;
        }

        return Math.sqrt(sqrDistance);
    }

    public static void main(String[] args) {
        try {
            Instances ins = FileUtil.loadInstances("letter-recognition.arff");

            System.out.println("TEST DISTANCE");
            Instance test = ins.instance(0);
            System.out.println("distance 0 dengan 1 : " + calcDistance(test, ins.instance(1)));
            System.out.println("distance 0 dengan 2 : " + calcDistance(test, ins.instance(2)));
            System.out.println("distance 0 dengan 3 : " + calcDistance(test, ins.instance(3)));
            System.out.println("distance 0 dengan 4 : " + calcDistance(test, ins.instance(4)));
            System.out.println("distance 0 dengan 5 : " + calcDistance(test, ins.instance(5)));

            System.out.println("TEST SORTED LIST");
            SortedList sl = new SortedList(2);
            for(int i=1;i<ins.numInstances();i++)
                sl.insert(ins.instance(i), calcDistance(ins.instance(0), ins.instance(i)), i);

            for (int i = 0; i < sl.getInternalList().size(); i++) {
                System.out.print(sl.getInternalList().get(i).distance + " ");
            }
            System.out.println("");
            
            System.out.println("TEST NEAREST NEIGHBOUR");
            LinearKNearestNeighbourSearch lk = new LinearKNearestNeighbourSearch();
            lk.setInstaces(ins);
            Instances nnret = lk.kNearestNeighbours(ins.instance(0), 3);
            System.out.println("distance 0 dengan 0 : " + calcDistance(test, ins.instance(0)));
            System.out.println("distance 0 dengan 5019 : " + calcDistance(test, ins.instance(5019)));
            System.out.println("distance 0 dengan 18332 : " + calcDistance(test, ins.instance(18332)));
            System.out.println("distance 0 dengan 18284 : " + calcDistance(test, ins.instance(18284)));
            System.out.println("distance 0 dengan 1467 : " + calcDistance(test, ins.instance(1467)));
            
        } catch (Exception ex) {
            Logger.getLogger(LinearKNearestNeighbourSearch.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
