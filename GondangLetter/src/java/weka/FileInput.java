/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package weka;

import java.io.File;

/**
 *
 * @author masphei
 */
public class FileInput {
    File file_upload;
    String convertCsv;
    public File getFile_upload() {
        return file_upload;
    }

    public void setFile_upload(File file_upload) {
        this.file_upload = file_upload;
    }

    public String getConvertCsv() {
        return convertCsv;
    }

    public void setConvertCsv(String convertCsv) {
        this.convertCsv = convertCsv;
    }
    
}
