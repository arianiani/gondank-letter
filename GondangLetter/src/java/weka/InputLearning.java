package weka;

/**
 *
 * @author Alfian Ramadhan
 */
public class InputLearning {
    int algoritma;
    int knn;
    String checkAttribute[];

    public String[] getCheckAttribute() {
        return checkAttribute;
    }

    public void setCheckAttribute(String[] checkAttribute) {
        this.checkAttribute = checkAttribute;
    }
    
    public int getAlgoritma() {
        return algoritma;
    }

    public void setAlgoritma(int algoritma) {
        this.algoritma = algoritma;
    }

    public int getKnn() {
        return knn;
    }

    public void setKnn(int knn) {
        this.knn = knn;
    }
    
}
