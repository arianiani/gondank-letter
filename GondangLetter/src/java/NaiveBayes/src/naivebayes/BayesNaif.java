/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package naivebayes;

import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/**
 *
 * @author hp
 * kelas ini hanya bisa menerima nilai atribut numerik dari 0 sampai 20
 */
public class BayesNaif extends NaiveBayes {

    public Instances inst;
    public char clsf;
    //matriks input
    public int[] lettr = new int[26];
    public int[][] x_box = new int[21][26];
    public int[][] y_box = new int[21][26];
    public int[][] width = new int[21][26];
    public int[][] high = new int[21][26];
    public int[][] onpix = new int[21][26];
    public int[][] x_bar = new int[21][26];
    public int[][] y_bar = new int[21][26];
    public int[][] x2bar = new int[21][26];
    public int[][] y2bar = new int[21][26];
    public int[][] xybar = new int[21][26];
    public int[][] x2ybr = new int[21][26];
    public int[][] xy2br = new int[21][26];
    public int[][] x_ege = new int[21][26];
    public int[][] xegvy = new int[21][26];
    public int[][] y_ege = new int[21][26];
    public int[][] yegvx = new int[21][26];
    //matriks probabilitas
    public float[] plettr = new float[26];
    public float[][] px_box = new float[21][26];
    public float[][] py_box = new float[21][26];
    public float[][] pwidth = new float[21][26];
    public float[][] phigh = new float[21][26];
    public float[][] ponpix = new float[21][26];
    public float[][] px_bar = new float[21][26];
    public float[][] py_bar = new float[21][26];
    public float[][] px2bar = new float[21][26];
    public float[][] py2bar = new float[21][26];
    public float[][] pxybar = new float[21][26];
    public float[][] px2ybr = new float[21][26];
    public float[][] pxy2br = new float[21][26];
    public float[][] px_ege = new float[21][26];
    public float[][] pxegvy = new float[21][26];
    public float[][] py_ege = new float[21][26];
    public float[][] pyegvx = new float[21][26];

    //============coba buat matriks===========================
    public void mat(Instances ins) {
        for (int i = 0; i < ins.numInstances(); i++) {
            String xx = ins.instance(i).toString(0);
            char xxx = xx.charAt(0);
            switch (xxx) {
                case 'A':
                    lettr[0]++;
                    (x_box[Integer.parseInt(ins.instance(i).toString(1))][0])++;
                    (y_box[Integer.parseInt(ins.instance(i).toString(2))][0])++;
                    (width[Integer.parseInt(ins.instance(i).toString(3))][0])++;
                    (high[Integer.parseInt(ins.instance(i).toString(4))][0])++;
                    (onpix[Integer.parseInt(ins.instance(i).toString(5))][0])++;
                    (x_bar[Integer.parseInt(ins.instance(i).toString(6))][0])++;
                    (y_bar[Integer.parseInt(ins.instance(i).toString(7))][0])++;
                    (x2bar[Integer.parseInt(ins.instance(i).toString(8))][0])++;
                    (y2bar[Integer.parseInt(ins.instance(i).toString(9))][0])++;
                    (xybar[Integer.parseInt(ins.instance(i).toString(10))][0])++;
                    (x2ybr[Integer.parseInt(ins.instance(i).toString(11))][0])++;
                    (xy2br[Integer.parseInt(ins.instance(i).toString(12))][0])++;
                    (x_ege[Integer.parseInt(ins.instance(i).toString(13))][0])++;
                    (xegvy[Integer.parseInt(ins.instance(i).toString(14))][0])++;
                    (y_ege[Integer.parseInt(ins.instance(i).toString(15))][0])++;
                    (yegvx[Integer.parseInt(ins.instance(i).toString(16))][0])++;
                    break;
                case 'B':
                    lettr[1]++;
                    (x_box[Integer.parseInt(ins.instance(i).toString(1))][1])++;
                    (y_box[Integer.parseInt(ins.instance(i).toString(2))][1])++;
                    (width[Integer.parseInt(ins.instance(i).toString(3))][1])++;
                    (high[Integer.parseInt(ins.instance(i).toString(4))][1])++;
                    (onpix[Integer.parseInt(ins.instance(i).toString(5))][1])++;
                    (x_bar[Integer.parseInt(ins.instance(i).toString(6))][1])++;
                    (y_bar[Integer.parseInt(ins.instance(i).toString(7))][1])++;
                    (x2bar[Integer.parseInt(ins.instance(i).toString(8))][1])++;
                    (y2bar[Integer.parseInt(ins.instance(i).toString(9))][1])++;
                    (xybar[Integer.parseInt(ins.instance(i).toString(10))][1])++;
                    (x2ybr[Integer.parseInt(ins.instance(i).toString(11))][1])++;
                    (xy2br[Integer.parseInt(ins.instance(i).toString(12))][1])++;
                    (x_ege[Integer.parseInt(ins.instance(i).toString(13))][1])++;
                    (xegvy[Integer.parseInt(ins.instance(i).toString(14))][1])++;
                    (y_ege[Integer.parseInt(ins.instance(i).toString(15))][1])++;
                    (yegvx[Integer.parseInt(ins.instance(i).toString(16))][1])++;
                    break;
                case 'C':
                    lettr[2]++;
                    (x_box[Integer.parseInt(ins.instance(i).toString(1))][2])++;
                    (y_box[Integer.parseInt(ins.instance(i).toString(2))][2])++;
                    (width[Integer.parseInt(ins.instance(i).toString(3))][2])++;
                    (high[Integer.parseInt(ins.instance(i).toString(4))][2])++;
                    (onpix[Integer.parseInt(ins.instance(i).toString(5))][2])++;
                    (x_bar[Integer.parseInt(ins.instance(i).toString(6))][2])++;
                    (y_bar[Integer.parseInt(ins.instance(i).toString(7))][2])++;
                    (x2bar[Integer.parseInt(ins.instance(i).toString(8))][2])++;
                    (y2bar[Integer.parseInt(ins.instance(i).toString(9))][2])++;
                    (xybar[Integer.parseInt(ins.instance(i).toString(10))][2])++;
                    (x2ybr[Integer.parseInt(ins.instance(i).toString(11))][2])++;
                    (xy2br[Integer.parseInt(ins.instance(i).toString(12))][2])++;
                    (x_ege[Integer.parseInt(ins.instance(i).toString(13))][2])++;
                    (xegvy[Integer.parseInt(ins.instance(i).toString(14))][2])++;
                    (y_ege[Integer.parseInt(ins.instance(i).toString(15))][2])++;
                    (yegvx[Integer.parseInt(ins.instance(i).toString(16))][2])++;
                    break;
                case 'D':
                    lettr[3]++;
                    (x_box[Integer.parseInt(ins.instance(i).toString(1))][3])++;
                    (y_box[Integer.parseInt(ins.instance(i).toString(2))][3])++;
                    (width[Integer.parseInt(ins.instance(i).toString(3))][3])++;
                    (high[Integer.parseInt(ins.instance(i).toString(4))][3])++;
                    (onpix[Integer.parseInt(ins.instance(i).toString(5))][3])++;
                    (x_bar[Integer.parseInt(ins.instance(i).toString(6))][3])++;
                    (y_bar[Integer.parseInt(ins.instance(i).toString(7))][3])++;
                    (x2bar[Integer.parseInt(ins.instance(i).toString(8))][3])++;
                    (y2bar[Integer.parseInt(ins.instance(i).toString(9))][3])++;
                    (xybar[Integer.parseInt(ins.instance(i).toString(10))][3])++;
                    (x2ybr[Integer.parseInt(ins.instance(i).toString(11))][3])++;
                    (xy2br[Integer.parseInt(ins.instance(i).toString(12))][3])++;
                    (x_ege[Integer.parseInt(ins.instance(i).toString(13))][3])++;
                    (xegvy[Integer.parseInt(ins.instance(i).toString(14))][3])++;
                    (y_ege[Integer.parseInt(ins.instance(i).toString(15))][3])++;
                    (yegvx[Integer.parseInt(ins.instance(i).toString(16))][3])++;
                    break;
                case 'E':
                    lettr[4]++;
                    (x_box[Integer.parseInt(ins.instance(i).toString(1))][4])++;
                    (y_box[Integer.parseInt(ins.instance(i).toString(2))][4])++;
                    (width[Integer.parseInt(ins.instance(i).toString(3))][4])++;
                    (high[Integer.parseInt(ins.instance(i).toString(4))][4])++;
                    (onpix[Integer.parseInt(ins.instance(i).toString(5))][4])++;
                    (x_bar[Integer.parseInt(ins.instance(i).toString(6))][4])++;
                    (y_bar[Integer.parseInt(ins.instance(i).toString(7))][4])++;
                    (x2bar[Integer.parseInt(ins.instance(i).toString(8))][4])++;
                    (y2bar[Integer.parseInt(ins.instance(i).toString(9))][4])++;
                    (xybar[Integer.parseInt(ins.instance(i).toString(10))][4])++;
                    (x2ybr[Integer.parseInt(ins.instance(i).toString(11))][4])++;
                    (xy2br[Integer.parseInt(ins.instance(i).toString(12))][4])++;
                    (x_ege[Integer.parseInt(ins.instance(i).toString(13))][4])++;
                    (xegvy[Integer.parseInt(ins.instance(i).toString(14))][4])++;
                    (y_ege[Integer.parseInt(ins.instance(i).toString(15))][4])++;
                    (yegvx[Integer.parseInt(ins.instance(i).toString(16))][4])++;
                    break;
                case 'F':
                    lettr[5]++;
                    (x_box[Integer.parseInt(ins.instance(i).toString(1))][5])++;
                    (y_box[Integer.parseInt(ins.instance(i).toString(2))][5])++;
                    (width[Integer.parseInt(ins.instance(i).toString(3))][5])++;
                    (high[Integer.parseInt(ins.instance(i).toString(4))][5])++;
                    (onpix[Integer.parseInt(ins.instance(i).toString(5))][5])++;
                    (x_bar[Integer.parseInt(ins.instance(i).toString(6))][5])++;
                    (y_bar[Integer.parseInt(ins.instance(i).toString(7))][5])++;
                    (x2bar[Integer.parseInt(ins.instance(i).toString(8))][5])++;
                    (y2bar[Integer.parseInt(ins.instance(i).toString(9))][5])++;
                    (xybar[Integer.parseInt(ins.instance(i).toString(10))][5])++;
                    (x2ybr[Integer.parseInt(ins.instance(i).toString(11))][5])++;
                    (xy2br[Integer.parseInt(ins.instance(i).toString(12))][5])++;
                    (x_ege[Integer.parseInt(ins.instance(i).toString(13))][5])++;
                    (xegvy[Integer.parseInt(ins.instance(i).toString(14))][5])++;
                    (y_ege[Integer.parseInt(ins.instance(i).toString(15))][5])++;
                    (yegvx[Integer.parseInt(ins.instance(i).toString(16))][5])++;
                    break;
                case 'G':
                    lettr[6]++;
                    (x_box[Integer.parseInt(ins.instance(i).toString(1))][6])++;
                    (y_box[Integer.parseInt(ins.instance(i).toString(2))][6])++;
                    (width[Integer.parseInt(ins.instance(i).toString(3))][6])++;
                    (high[Integer.parseInt(ins.instance(i).toString(4))][6])++;
                    (onpix[Integer.parseInt(ins.instance(i).toString(5))][6])++;
                    (x_bar[Integer.parseInt(ins.instance(i).toString(6))][6])++;
                    (y_bar[Integer.parseInt(ins.instance(i).toString(7))][6])++;
                    (x2bar[Integer.parseInt(ins.instance(i).toString(8))][6])++;
                    (y2bar[Integer.parseInt(ins.instance(i).toString(9))][6])++;
                    (xybar[Integer.parseInt(ins.instance(i).toString(10))][6])++;
                    (x2ybr[Integer.parseInt(ins.instance(i).toString(11))][6])++;
                    (xy2br[Integer.parseInt(ins.instance(i).toString(12))][6])++;
                    (x_ege[Integer.parseInt(ins.instance(i).toString(13))][6])++;
                    (xegvy[Integer.parseInt(ins.instance(i).toString(14))][6])++;
                    (y_ege[Integer.parseInt(ins.instance(i).toString(15))][6])++;
                    (yegvx[Integer.parseInt(ins.instance(i).toString(16))][6])++;
                    break;
                case 'H':
                    lettr[7]++;
                    (x_box[Integer.parseInt(ins.instance(i).toString(1))][7])++;
                    (y_box[Integer.parseInt(ins.instance(i).toString(2))][7])++;
                    (width[Integer.parseInt(ins.instance(i).toString(3))][7])++;
                    (high[Integer.parseInt(ins.instance(i).toString(4))][7])++;
                    (onpix[Integer.parseInt(ins.instance(i).toString(5))][7])++;
                    (x_bar[Integer.parseInt(ins.instance(i).toString(6))][7])++;
                    (y_bar[Integer.parseInt(ins.instance(i).toString(7))][7])++;
                    (x2bar[Integer.parseInt(ins.instance(i).toString(8))][7])++;
                    (y2bar[Integer.parseInt(ins.instance(i).toString(9))][7])++;
                    (xybar[Integer.parseInt(ins.instance(i).toString(10))][7])++;
                    (x2ybr[Integer.parseInt(ins.instance(i).toString(11))][7])++;
                    (xy2br[Integer.parseInt(ins.instance(i).toString(12))][7])++;
                    (x_ege[Integer.parseInt(ins.instance(i).toString(13))][7])++;
                    (xegvy[Integer.parseInt(ins.instance(i).toString(14))][7])++;
                    (y_ege[Integer.parseInt(ins.instance(i).toString(15))][7])++;
                    (yegvx[Integer.parseInt(ins.instance(i).toString(16))][7])++;
                    break;
                case 'I':
                    lettr[8]++;
                    (x_box[Integer.parseInt(ins.instance(i).toString(1))][8])++;
                    (y_box[Integer.parseInt(ins.instance(i).toString(2))][8])++;
                    (width[Integer.parseInt(ins.instance(i).toString(3))][8])++;
                    (high[Integer.parseInt(ins.instance(i).toString(4))][8])++;
                    (onpix[Integer.parseInt(ins.instance(i).toString(5))][8])++;
                    (x_bar[Integer.parseInt(ins.instance(i).toString(6))][8])++;
                    (y_bar[Integer.parseInt(ins.instance(i).toString(7))][8])++;
                    (x2bar[Integer.parseInt(ins.instance(i).toString(8))][8])++;
                    (y2bar[Integer.parseInt(ins.instance(i).toString(9))][8])++;
                    (xybar[Integer.parseInt(ins.instance(i).toString(10))][8])++;
                    (x2ybr[Integer.parseInt(ins.instance(i).toString(11))][8])++;
                    (xy2br[Integer.parseInt(ins.instance(i).toString(12))][8])++;
                    (x_ege[Integer.parseInt(ins.instance(i).toString(13))][8])++;
                    (xegvy[Integer.parseInt(ins.instance(i).toString(14))][8])++;
                    (y_ege[Integer.parseInt(ins.instance(i).toString(15))][8])++;
                    (yegvx[Integer.parseInt(ins.instance(i).toString(16))][8])++;
                    break;
                case 'J':
                    lettr[9]++;
                    (x_box[Integer.parseInt(ins.instance(i).toString(1))][9])++;
                    (y_box[Integer.parseInt(ins.instance(i).toString(2))][9])++;
                    (width[Integer.parseInt(ins.instance(i).toString(3))][9])++;
                    (high[Integer.parseInt(ins.instance(i).toString(4))][9])++;
                    (onpix[Integer.parseInt(ins.instance(i).toString(5))][9])++;
                    (x_bar[Integer.parseInt(ins.instance(i).toString(6))][9])++;
                    (y_bar[Integer.parseInt(ins.instance(i).toString(7))][9])++;
                    (x2bar[Integer.parseInt(ins.instance(i).toString(8))][9])++;
                    (y2bar[Integer.parseInt(ins.instance(i).toString(9))][9])++;
                    (xybar[Integer.parseInt(ins.instance(i).toString(10))][9])++;
                    (x2ybr[Integer.parseInt(ins.instance(i).toString(11))][9])++;
                    (xy2br[Integer.parseInt(ins.instance(i).toString(12))][9])++;
                    (x_ege[Integer.parseInt(ins.instance(i).toString(13))][9])++;
                    (xegvy[Integer.parseInt(ins.instance(i).toString(14))][9])++;
                    (y_ege[Integer.parseInt(ins.instance(i).toString(15))][9])++;
                    (yegvx[Integer.parseInt(ins.instance(i).toString(16))][9])++;
                    break;
                case 'K':
                    lettr[10]++;
                    (x_box[Integer.parseInt(ins.instance(i).toString(1))][10])++;
                    (y_box[Integer.parseInt(ins.instance(i).toString(2))][10])++;
                    (width[Integer.parseInt(ins.instance(i).toString(3))][10])++;
                    (high[Integer.parseInt(ins.instance(i).toString(4))][10])++;
                    (onpix[Integer.parseInt(ins.instance(i).toString(5))][10])++;
                    (x_bar[Integer.parseInt(ins.instance(i).toString(6))][10])++;
                    (y_bar[Integer.parseInt(ins.instance(i).toString(7))][10])++;
                    (x2bar[Integer.parseInt(ins.instance(i).toString(8))][10])++;
                    (y2bar[Integer.parseInt(ins.instance(i).toString(9))][10])++;
                    (xybar[Integer.parseInt(ins.instance(i).toString(10))][10])++;
                    (x2ybr[Integer.parseInt(ins.instance(i).toString(11))][10])++;
                    (xy2br[Integer.parseInt(ins.instance(i).toString(12))][10])++;
                    (x_ege[Integer.parseInt(ins.instance(i).toString(13))][10])++;
                    (xegvy[Integer.parseInt(ins.instance(i).toString(14))][10])++;
                    (y_ege[Integer.parseInt(ins.instance(i).toString(15))][10])++;
                    (yegvx[Integer.parseInt(ins.instance(i).toString(16))][10])++;
                    break;
                case 'L':
                    lettr[11]++;
                    (x_box[Integer.parseInt(ins.instance(i).toString(1))][11])++;
                    (y_box[Integer.parseInt(ins.instance(i).toString(2))][11])++;
                    (width[Integer.parseInt(ins.instance(i).toString(3))][11])++;
                    (high[Integer.parseInt(ins.instance(i).toString(4))][11])++;
                    (onpix[Integer.parseInt(ins.instance(i).toString(5))][11])++;
                    (x_bar[Integer.parseInt(ins.instance(i).toString(6))][11])++;
                    (y_bar[Integer.parseInt(ins.instance(i).toString(7))][11])++;
                    (x2bar[Integer.parseInt(ins.instance(i).toString(8))][11])++;
                    (y2bar[Integer.parseInt(ins.instance(i).toString(9))][11])++;
                    (xybar[Integer.parseInt(ins.instance(i).toString(10))][11])++;
                    (x2ybr[Integer.parseInt(ins.instance(i).toString(11))][11])++;
                    (xy2br[Integer.parseInt(ins.instance(i).toString(12))][11])++;
                    (x_ege[Integer.parseInt(ins.instance(i).toString(13))][11])++;
                    (xegvy[Integer.parseInt(ins.instance(i).toString(14))][11])++;
                    (y_ege[Integer.parseInt(ins.instance(i).toString(15))][11])++;
                    (yegvx[Integer.parseInt(ins.instance(i).toString(16))][11])++;
                    break;
                case 'M':
                    lettr[12]++;
                    (x_box[Integer.parseInt(ins.instance(i).toString(1))][12])++;
                    (y_box[Integer.parseInt(ins.instance(i).toString(2))][12])++;
                    (width[Integer.parseInt(ins.instance(i).toString(3))][12])++;
                    (high[Integer.parseInt(ins.instance(i).toString(4))][12])++;
                    (onpix[Integer.parseInt(ins.instance(i).toString(5))][12])++;
                    (x_bar[Integer.parseInt(ins.instance(i).toString(6))][12])++;
                    (y_bar[Integer.parseInt(ins.instance(i).toString(7))][12])++;
                    (x2bar[Integer.parseInt(ins.instance(i).toString(8))][12])++;
                    (y2bar[Integer.parseInt(ins.instance(i).toString(9))][12])++;
                    (xybar[Integer.parseInt(ins.instance(i).toString(10))][12])++;
                    (x2ybr[Integer.parseInt(ins.instance(i).toString(11))][12])++;
                    (xy2br[Integer.parseInt(ins.instance(i).toString(12))][12])++;
                    (x_ege[Integer.parseInt(ins.instance(i).toString(13))][12])++;
                    (xegvy[Integer.parseInt(ins.instance(i).toString(14))][12])++;
                    (y_ege[Integer.parseInt(ins.instance(i).toString(15))][12])++;
                    (yegvx[Integer.parseInt(ins.instance(i).toString(16))][12])++;
                    break;
                case 'N':
                    lettr[13]++;
                    (x_box[Integer.parseInt(ins.instance(i).toString(1))][13])++;
                    (y_box[Integer.parseInt(ins.instance(i).toString(2))][13])++;
                    (width[Integer.parseInt(ins.instance(i).toString(3))][13])++;
                    (high[Integer.parseInt(ins.instance(i).toString(4))][13])++;
                    (onpix[Integer.parseInt(ins.instance(i).toString(5))][13])++;
                    (x_bar[Integer.parseInt(ins.instance(i).toString(6))][13])++;
                    (y_bar[Integer.parseInt(ins.instance(i).toString(7))][13])++;
                    (x2bar[Integer.parseInt(ins.instance(i).toString(8))][13])++;
                    (y2bar[Integer.parseInt(ins.instance(i).toString(9))][13])++;
                    (xybar[Integer.parseInt(ins.instance(i).toString(10))][13])++;
                    (x2ybr[Integer.parseInt(ins.instance(i).toString(11))][13])++;
                    (xy2br[Integer.parseInt(ins.instance(i).toString(12))][13])++;
                    (x_ege[Integer.parseInt(ins.instance(i).toString(13))][13])++;
                    (xegvy[Integer.parseInt(ins.instance(i).toString(14))][13])++;
                    (y_ege[Integer.parseInt(ins.instance(i).toString(15))][13])++;
                    (yegvx[Integer.parseInt(ins.instance(i).toString(16))][13])++;
                    break;
                case 'O':
                    lettr[14]++;
                    (x_box[Integer.parseInt(ins.instance(i).toString(1))][14])++;
                    (y_box[Integer.parseInt(ins.instance(i).toString(2))][14])++;
                    (width[Integer.parseInt(ins.instance(i).toString(3))][14])++;
                    (high[Integer.parseInt(ins.instance(i).toString(4))][14])++;
                    (onpix[Integer.parseInt(ins.instance(i).toString(5))][14])++;
                    (x_bar[Integer.parseInt(ins.instance(i).toString(6))][14])++;
                    (y_bar[Integer.parseInt(ins.instance(i).toString(7))][14])++;
                    (x2bar[Integer.parseInt(ins.instance(i).toString(8))][14])++;
                    (y2bar[Integer.parseInt(ins.instance(i).toString(9))][14])++;
                    (xybar[Integer.parseInt(ins.instance(i).toString(10))][14])++;
                    (x2ybr[Integer.parseInt(ins.instance(i).toString(11))][14])++;
                    (xy2br[Integer.parseInt(ins.instance(i).toString(12))][14])++;
                    (x_ege[Integer.parseInt(ins.instance(i).toString(13))][14])++;
                    (xegvy[Integer.parseInt(ins.instance(i).toString(14))][14])++;
                    (y_ege[Integer.parseInt(ins.instance(i).toString(15))][14])++;
                    (yegvx[Integer.parseInt(ins.instance(i).toString(16))][14])++;
                    break;
                case 'P':
                    lettr[15]++;
                    (x_box[Integer.parseInt(ins.instance(i).toString(1))][15])++;
                    (y_box[Integer.parseInt(ins.instance(i).toString(2))][15])++;
                    (width[Integer.parseInt(ins.instance(i).toString(3))][15])++;
                    (high[Integer.parseInt(ins.instance(i).toString(4))][15])++;
                    (onpix[Integer.parseInt(ins.instance(i).toString(5))][15])++;
                    (x_bar[Integer.parseInt(ins.instance(i).toString(6))][15])++;
                    (y_bar[Integer.parseInt(ins.instance(i).toString(7))][15])++;
                    (x2bar[Integer.parseInt(ins.instance(i).toString(8))][15])++;
                    (y2bar[Integer.parseInt(ins.instance(i).toString(9))][15])++;
                    (xybar[Integer.parseInt(ins.instance(i).toString(10))][15])++;
                    (x2ybr[Integer.parseInt(ins.instance(i).toString(11))][15])++;
                    (xy2br[Integer.parseInt(ins.instance(i).toString(12))][15])++;
                    (x_ege[Integer.parseInt(ins.instance(i).toString(13))][15])++;
                    (xegvy[Integer.parseInt(ins.instance(i).toString(14))][15])++;
                    (y_ege[Integer.parseInt(ins.instance(i).toString(15))][15])++;
                    (yegvx[Integer.parseInt(ins.instance(i).toString(16))][15])++;
                    break;
                case 'Q':
                    lettr[16]++;
                    (x_box[Integer.parseInt(ins.instance(i).toString(1))][16])++;
                    (y_box[Integer.parseInt(ins.instance(i).toString(2))][16])++;
                    (width[Integer.parseInt(ins.instance(i).toString(3))][16])++;
                    (high[Integer.parseInt(ins.instance(i).toString(4))][16])++;
                    (onpix[Integer.parseInt(ins.instance(i).toString(5))][16])++;
                    (x_bar[Integer.parseInt(ins.instance(i).toString(6))][16])++;
                    (y_bar[Integer.parseInt(ins.instance(i).toString(7))][16])++;
                    (x2bar[Integer.parseInt(ins.instance(i).toString(8))][16])++;
                    (y2bar[Integer.parseInt(ins.instance(i).toString(9))][16])++;
                    (xybar[Integer.parseInt(ins.instance(i).toString(10))][16])++;
                    (x2ybr[Integer.parseInt(ins.instance(i).toString(11))][16])++;
                    (xy2br[Integer.parseInt(ins.instance(i).toString(12))][16])++;
                    (x_ege[Integer.parseInt(ins.instance(i).toString(13))][16])++;
                    (xegvy[Integer.parseInt(ins.instance(i).toString(14))][16])++;
                    (y_ege[Integer.parseInt(ins.instance(i).toString(15))][16])++;
                    (yegvx[Integer.parseInt(ins.instance(i).toString(16))][16])++;
                    break;
                case 'R':
                    lettr[17]++;
                    (x_box[Integer.parseInt(ins.instance(i).toString(1))][17])++;
                    (y_box[Integer.parseInt(ins.instance(i).toString(2))][17])++;
                    (width[Integer.parseInt(ins.instance(i).toString(3))][17])++;
                    (high[Integer.parseInt(ins.instance(i).toString(4))][17])++;
                    (onpix[Integer.parseInt(ins.instance(i).toString(5))][17])++;
                    (x_bar[Integer.parseInt(ins.instance(i).toString(6))][17])++;
                    (y_bar[Integer.parseInt(ins.instance(i).toString(7))][17])++;
                    (x2bar[Integer.parseInt(ins.instance(i).toString(8))][17])++;
                    (y2bar[Integer.parseInt(ins.instance(i).toString(9))][17])++;
                    (xybar[Integer.parseInt(ins.instance(i).toString(10))][17])++;
                    (x2ybr[Integer.parseInt(ins.instance(i).toString(11))][17])++;
                    (xy2br[Integer.parseInt(ins.instance(i).toString(12))][17])++;
                    (x_ege[Integer.parseInt(ins.instance(i).toString(13))][17])++;
                    (xegvy[Integer.parseInt(ins.instance(i).toString(14))][17])++;
                    (y_ege[Integer.parseInt(ins.instance(i).toString(15))][17])++;
                    (yegvx[Integer.parseInt(ins.instance(i).toString(16))][17])++;
                    break;
                case 'S':
                    lettr[18]++;
                    (x_box[Integer.parseInt(ins.instance(i).toString(1))][18])++;
                    (y_box[Integer.parseInt(ins.instance(i).toString(2))][18])++;
                    (width[Integer.parseInt(ins.instance(i).toString(3))][18])++;
                    (high[Integer.parseInt(ins.instance(i).toString(4))][18])++;
                    (onpix[Integer.parseInt(ins.instance(i).toString(5))][18])++;
                    (x_bar[Integer.parseInt(ins.instance(i).toString(6))][18])++;
                    (y_bar[Integer.parseInt(ins.instance(i).toString(7))][18])++;
                    (x2bar[Integer.parseInt(ins.instance(i).toString(8))][18])++;
                    (y2bar[Integer.parseInt(ins.instance(i).toString(9))][18])++;
                    (xybar[Integer.parseInt(ins.instance(i).toString(10))][18])++;
                    (x2ybr[Integer.parseInt(ins.instance(i).toString(11))][18])++;
                    (xy2br[Integer.parseInt(ins.instance(i).toString(12))][18])++;
                    (x_ege[Integer.parseInt(ins.instance(i).toString(13))][18])++;
                    (xegvy[Integer.parseInt(ins.instance(i).toString(14))][18])++;
                    (y_ege[Integer.parseInt(ins.instance(i).toString(15))][18])++;
                    (yegvx[Integer.parseInt(ins.instance(i).toString(16))][18])++;
                    break;
                case 'T':
                    lettr[19]++;
                    (x_box[Integer.parseInt(ins.instance(i).toString(1))][19])++;
                    (y_box[Integer.parseInt(ins.instance(i).toString(2))][19])++;
                    (width[Integer.parseInt(ins.instance(i).toString(3))][19])++;
                    (high[Integer.parseInt(ins.instance(i).toString(4))][19])++;
                    (onpix[Integer.parseInt(ins.instance(i).toString(5))][19])++;
                    (x_bar[Integer.parseInt(ins.instance(i).toString(6))][19])++;
                    (y_bar[Integer.parseInt(ins.instance(i).toString(7))][19])++;
                    (x2bar[Integer.parseInt(ins.instance(i).toString(8))][19])++;
                    (y2bar[Integer.parseInt(ins.instance(i).toString(9))][19])++;
                    (xybar[Integer.parseInt(ins.instance(i).toString(10))][19])++;
                    (x2ybr[Integer.parseInt(ins.instance(i).toString(11))][19])++;
                    (xy2br[Integer.parseInt(ins.instance(i).toString(12))][19])++;
                    (x_ege[Integer.parseInt(ins.instance(i).toString(13))][19])++;
                    (xegvy[Integer.parseInt(ins.instance(i).toString(14))][19])++;
                    (y_ege[Integer.parseInt(ins.instance(i).toString(15))][19])++;
                    (yegvx[Integer.parseInt(ins.instance(i).toString(16))][19])++;
//                        System.out.print("nilai x_box T "+Integer.parseInt(ins.instance(i).toString(1)));
//                        System.out.print(" "+Integer.parseInt(ins.instance(i).toString(2)));
//                        System.out.print(" "+Integer.parseInt(ins.instance(i).toString(3)));
//                        System.out.print(" "+Integer.parseInt(ins.instance(i).toString(4)));
//                        System.out.print(" "+Integer.parseInt(ins.instance(i).toString(5)));
//                        System.out.print(" "+Integer.parseInt(ins.instance(i).toString(6)));
//                        System.out.print(" "+Integer.parseInt(ins.instance(i).toString(7)));
//                        System.out.print(" "+Integer.parseInt(ins.instance(i).toString(8)));
//                        System.out.print(" "+Integer.parseInt(ins.instance(i).toString(9)));
//                        System.out.print(" "+Integer.parseInt(ins.instance(i).toString(10)));
//                        System.out.print(" "+Integer.parseInt(ins.instance(i).toString(11)));
//                        System.out.print(" "+Integer.parseInt(ins.instance(i).toString(12)));
//                        System.out.print(" "+Integer.parseInt(ins.instance(i).toString(13)));
//                        System.out.print(" "+Integer.parseInt(ins.instance(i).toString(14)));
//                        System.out.print(" "+Integer.parseInt(ins.instance(i).toString(15)));
//                        System.out.println(" "+Integer.parseInt(ins.instance(i).toString(16)));
                    break;
                case 'U':
                    lettr[20]++;
                    (x_box[Integer.parseInt(ins.instance(i).toString(1))][20])++;
                    (y_box[Integer.parseInt(ins.instance(i).toString(2))][20])++;
                    (width[Integer.parseInt(ins.instance(i).toString(3))][20])++;
                    (high[Integer.parseInt(ins.instance(i).toString(4))][20])++;
                    (onpix[Integer.parseInt(ins.instance(i).toString(5))][20])++;
                    (x_bar[Integer.parseInt(ins.instance(i).toString(6))][20])++;
                    (y_bar[Integer.parseInt(ins.instance(i).toString(7))][20])++;
                    (x2bar[Integer.parseInt(ins.instance(i).toString(8))][20])++;
                    (y2bar[Integer.parseInt(ins.instance(i).toString(9))][20])++;
                    (xybar[Integer.parseInt(ins.instance(i).toString(10))][20])++;
                    (x2ybr[Integer.parseInt(ins.instance(i).toString(11))][20])++;
                    (xy2br[Integer.parseInt(ins.instance(i).toString(12))][20])++;
                    (x_ege[Integer.parseInt(ins.instance(i).toString(13))][20])++;
                    (xegvy[Integer.parseInt(ins.instance(i).toString(14))][20])++;
                    (y_ege[Integer.parseInt(ins.instance(i).toString(15))][20])++;
                    (yegvx[Integer.parseInt(ins.instance(i).toString(16))][20])++;
                    break;
                case 'V':
                    lettr[21]++;
                    (x_box[Integer.parseInt(ins.instance(i).toString(1))][21])++;
                    (y_box[Integer.parseInt(ins.instance(i).toString(2))][21])++;
                    (width[Integer.parseInt(ins.instance(i).toString(3))][21])++;
                    (high[Integer.parseInt(ins.instance(i).toString(4))][21])++;
                    (onpix[Integer.parseInt(ins.instance(i).toString(5))][21])++;
                    (x_bar[Integer.parseInt(ins.instance(i).toString(6))][21])++;
                    (y_bar[Integer.parseInt(ins.instance(i).toString(7))][21])++;
                    (x2bar[Integer.parseInt(ins.instance(i).toString(8))][21])++;
                    (y2bar[Integer.parseInt(ins.instance(i).toString(9))][21])++;
                    (xybar[Integer.parseInt(ins.instance(i).toString(10))][21])++;
                    (x2ybr[Integer.parseInt(ins.instance(i).toString(11))][21])++;
                    (xy2br[Integer.parseInt(ins.instance(i).toString(12))][21])++;
                    (x_ege[Integer.parseInt(ins.instance(i).toString(13))][21])++;
                    (xegvy[Integer.parseInt(ins.instance(i).toString(14))][21])++;
                    (y_ege[Integer.parseInt(ins.instance(i).toString(15))][21])++;
                    (yegvx[Integer.parseInt(ins.instance(i).toString(16))][21])++;
                    break;
                case 'W':
                    lettr[22]++;
                    (x_box[Integer.parseInt(ins.instance(i).toString(1))][22])++;
                    (y_box[Integer.parseInt(ins.instance(i).toString(2))][22])++;
                    (width[Integer.parseInt(ins.instance(i).toString(3))][22])++;
                    (high[Integer.parseInt(ins.instance(i).toString(4))][22])++;
                    (onpix[Integer.parseInt(ins.instance(i).toString(5))][22])++;
                    (x_bar[Integer.parseInt(ins.instance(i).toString(6))][22])++;
                    (y_bar[Integer.parseInt(ins.instance(i).toString(7))][22])++;
                    (x2bar[Integer.parseInt(ins.instance(i).toString(8))][22])++;
                    (y2bar[Integer.parseInt(ins.instance(i).toString(9))][22])++;
                    (xybar[Integer.parseInt(ins.instance(i).toString(10))][22])++;
                    (x2ybr[Integer.parseInt(ins.instance(i).toString(11))][22])++;
                    (xy2br[Integer.parseInt(ins.instance(i).toString(12))][22])++;
                    (x_ege[Integer.parseInt(ins.instance(i).toString(13))][22])++;
                    (xegvy[Integer.parseInt(ins.instance(i).toString(14))][22])++;
                    (y_ege[Integer.parseInt(ins.instance(i).toString(15))][22])++;
                    (yegvx[Integer.parseInt(ins.instance(i).toString(16))][22])++;
                    break;
                case 'X':
                    lettr[23]++;
                    (x_box[Integer.parseInt(ins.instance(i).toString(1))][23])++;
                    (y_box[Integer.parseInt(ins.instance(i).toString(2))][23])++;
                    (width[Integer.parseInt(ins.instance(i).toString(3))][23])++;
                    (high[Integer.parseInt(ins.instance(i).toString(4))][23])++;
                    (onpix[Integer.parseInt(ins.instance(i).toString(5))][23])++;
                    (x_bar[Integer.parseInt(ins.instance(i).toString(6))][23])++;
                    (y_bar[Integer.parseInt(ins.instance(i).toString(7))][23])++;
                    (x2bar[Integer.parseInt(ins.instance(i).toString(8))][23])++;
                    (y2bar[Integer.parseInt(ins.instance(i).toString(9))][23])++;
                    (xybar[Integer.parseInt(ins.instance(i).toString(10))][23])++;
                    (x2ybr[Integer.parseInt(ins.instance(i).toString(11))][23])++;
                    (xy2br[Integer.parseInt(ins.instance(i).toString(12))][23])++;
                    (x_ege[Integer.parseInt(ins.instance(i).toString(13))][23])++;
                    (xegvy[Integer.parseInt(ins.instance(i).toString(14))][23])++;
                    (y_ege[Integer.parseInt(ins.instance(i).toString(15))][23])++;
                    (yegvx[Integer.parseInt(ins.instance(i).toString(16))][23])++;
                    break;
                case 'Y':
                    lettr[24]++;
                    (x_box[Integer.parseInt(ins.instance(i).toString(1))][24])++;
                    (y_box[Integer.parseInt(ins.instance(i).toString(2))][24])++;
                    (width[Integer.parseInt(ins.instance(i).toString(3))][24])++;
                    (high[Integer.parseInt(ins.instance(i).toString(4))][24])++;
                    (onpix[Integer.parseInt(ins.instance(i).toString(5))][24])++;
                    (x_bar[Integer.parseInt(ins.instance(i).toString(6))][24])++;
                    (y_bar[Integer.parseInt(ins.instance(i).toString(7))][24])++;
                    (x2bar[Integer.parseInt(ins.instance(i).toString(8))][24])++;
                    (y2bar[Integer.parseInt(ins.instance(i).toString(9))][24])++;
                    (xybar[Integer.parseInt(ins.instance(i).toString(10))][24])++;
                    (x2ybr[Integer.parseInt(ins.instance(i).toString(11))][24])++;
                    (xy2br[Integer.parseInt(ins.instance(i).toString(12))][24])++;
                    (x_ege[Integer.parseInt(ins.instance(i).toString(13))][24])++;
                    (xegvy[Integer.parseInt(ins.instance(i).toString(14))][24])++;
                    (y_ege[Integer.parseInt(ins.instance(i).toString(15))][24])++;
                    (yegvx[Integer.parseInt(ins.instance(i).toString(16))][24])++;
                    break;
                case 'Z':
                    lettr[25]++;
                    (x_box[Integer.parseInt(ins.instance(i).toString(1))][25])++;
                    (y_box[Integer.parseInt(ins.instance(i).toString(2))][25])++;
                    (width[Integer.parseInt(ins.instance(i).toString(3))][25])++;
                    (high[Integer.parseInt(ins.instance(i).toString(4))][25])++;
                    (onpix[Integer.parseInt(ins.instance(i).toString(5))][25])++;
                    (x_bar[Integer.parseInt(ins.instance(i).toString(6))][25])++;
                    (y_bar[Integer.parseInt(ins.instance(i).toString(7))][25])++;
                    (x2bar[Integer.parseInt(ins.instance(i).toString(8))][25])++;
                    (y2bar[Integer.parseInt(ins.instance(i).toString(9))][25])++;
                    (xybar[Integer.parseInt(ins.instance(i).toString(10))][25])++;
                    (x2ybr[Integer.parseInt(ins.instance(i).toString(11))][25])++;
                    (xy2br[Integer.parseInt(ins.instance(i).toString(12))][25])++;
                    (x_ege[Integer.parseInt(ins.instance(i).toString(13))][25])++;
                    (xegvy[Integer.parseInt(ins.instance(i).toString(14))][25])++;
                    (y_ege[Integer.parseInt(ins.instance(i).toString(15))][25])++;
                    (yegvx[Integer.parseInt(ins.instance(i).toString(16))][25])++;
                    break;
            }
        }
    }

    //========matriks probabilitas================================================
    public void matprob() {
        int totalsum = 0; //jumlah seluruh instance
        for (int n=0; n<26; n++) {
            totalsum = totalsum + lettr[n];
        }
        for (int j=0; j<26; j++) {
            plettr[j] = (float)lettr[j] / totalsum;
            for (int i=0; i<21; i++) {
                px_box[i][j] = (float)x_box[i][j] / lettr[j];
                py_box[i][j] = (float)y_box[i][j] / lettr[j];
                pwidth[i][j] = (float)width[i][j] / lettr[j];
                phigh[i][j] = (float)high[i][j] / lettr[j];
                ponpix[i][j] = (float)onpix[i][j] / lettr[j];
                px_bar[i][j] = (float)x_bar[i][j] / lettr[j];
                py_bar[i][j] = (float)y_bar[i][j] / lettr[j];
                px2bar[i][j] = (float)x2bar[i][j] / lettr[j];
                py2bar[i][j] = (float)y2bar[i][j] / lettr[j];
                pxybar[i][j] = (float)xybar[i][j] / lettr[j];
                px2ybr[i][j] = (float)x2ybr[i][j] / lettr[j];
                pxy2br[i][j] = (float)xy2br[i][j] / lettr[j];
                px_ege[i][j] = (float)x_ege[i][j] / lettr[j];
                pxegvy[i][j] = (float)xegvy[i][j] / lettr[j];
                py_ege[i][j] = (float)y_ege[i][j] / lettr[j];
                pyegvx[i][j] = (float)yegvx[i][j] / lettr[j];
            }
        }
    }
    
    public float clasIns (Instance ins) {
        float max=0;
        boolean val = true;
        for (int atr=1; atr<17; atr++) {
            if (ins.isMissing(atr)) {
                System.out.println("error");
                val = false;
                break;                
            } 
        }
        if (val) {
            max = learn(ins);
        }
        return max;
    }
    
    public float learn(Instance ins) {
        float[] prb = new float[26];
        float max = 0;
        if (ins.isMissing(0)) {
            for (int i=0; i<26; i++) {
                prb[i] = plettr[i] * px_box[Integer.parseInt(ins.toString(1))][i] 
                        * py_box[Integer.parseInt(ins.toString(2))][i]
                        * pwidth[Integer.parseInt(ins.toString(3))][i]
                        * phigh[Integer.parseInt(ins.toString(4))][i]
                        * ponpix[Integer.parseInt(ins.toString(5))][i]
                        * px_bar[Integer.parseInt(ins.toString(6))][i]
                        * py_bar[Integer.parseInt(ins.toString(7))][i]
                        * px2bar[Integer.parseInt(ins.toString(8))][i]
                        * py2bar[Integer.parseInt(ins.toString(9))][i]
                        * pxybar[Integer.parseInt(ins.toString(10))][i]
                        * px2ybr[Integer.parseInt(ins.toString(11))][i]
                        * pxy2br[Integer.parseInt(ins.toString(12))][i]
                        * px_ege[Integer.parseInt(ins.toString(13))][i]
                        * pxegvy[Integer.parseInt(ins.toString(14))][i]
                        * py_ege[Integer.parseInt(ins.toString(15))][i]
                        * pyegvx[Integer.parseInt(ins.toString(16))][i];
                //iterasi nilai perkalian probabilitas
                //System.out.println("cari max "+prb[i]);
            }
            max = carimax(prb);
        } else {
            System.out.println(ins);
        }
        return max;
    }
    
    public float carimax(float[] tf) {
        float max=tf[0];
        clsf = 65;
        for (int i=1; i<tf.length; i++) {
            if (tf[i] > max) {
                max = tf[i];
                clsf = (char) (65+i);
            }
        }
        return max;
    }
    
    public void classfy(Instances inss) {
        for (int i=0; i<inss.numInstances(); i++) {
            System.out.println("untuk instance ke-"+(i+1));
            System.out.println("max value : "+clasIns(inss.instance(i)));
            System.out.println("class : "+clsf);            
        }
    }
    
    public static void main(String[] argv) {
        try {
            Instances data = DataSource.read("letter-recognition.arff");
            data.setClass(data.attribute("lettr"));

            BayesNaif nb = new BayesNaif();
            nb.mat(data);
            nb.matprob();
//            System.out.println("Z "+nb.lettr[25]);
//            for (int i=0; i<26; i++) {
//                System.out.println("prob "+nb.plettr[i]);
//                for (int k=0; k<21; k++) {
//                    System.out.print(" "+nb.py_box[k][i]);
//                }
//                System.out.println("");
//            }
            
            //buat instances dari dataset
            Instances dataset = DataSource.read("letter - dataset.arff");
            dataset.setClass(dataset.attribute("lettr"));
            
            //buat copy dari dataset yang menyimpan hasil klasifikasi
            Instances labeled = new Instances(dataset);                 
            
            nb.classfy(dataset);
//            System.out.println("nilai max : "+nb.clasIns(dataset.instance(0)));            
//            System.out.println("class : "+nb.clsf);
            //iterasi
//            System.out.println(dataset.numInstances());                                        
//            for(int i=0;i<dataset.numInstances();i++) {                         
//                double classLabel = nb.classifyInstance(dataset.instance(i));
//                labeled.instance(i).setClassValue(classLabel);
//                System.out.println("klasifikasi instance " + i + " : " + labeled.classAttribute().value((int) classLabel));
//            }                        

//            for (int i = 0; i < 21; i++) {
//                System.out.println("nilai-" + i + " ");
//                for (int j = 0; j < 26; j++) {
//                    System.out.print(" " + nb.yegvx[i][j]);
//                }
//                System.out.println("");
//            }

        } catch (Exception ex) {
            Logger.getLogger(NaiveBayes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
