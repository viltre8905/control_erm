package com.verynet.gcint.api.thread;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.Activity;
import com.verynet.gcint.api.model.DocumentMetadata;
import com.verynet.gcint.api.model.Question;
import com.verynet.gcint.api.util.EncryptionUtil;
import com.verynet.gcint.api.util.GeneralUtil;
import com.verynet.gcint.api.util.enums.ActivityStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * Created by day on 08/10/2016.
 */
public class SecundaryFunctions extends Thread {
    @Value("${document.directory}")
    private String documentDirectory;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run() {
        long secondCont = 0;
        while (true) {
            //Check Licence
            File file = new File(GeneralUtil.getApplicationDataDirectory() + File.separator + "erm_data_file");
            File ivFile = new File(GeneralUtil.getApplicationDataDirectory() + File.separator + "iv");
            String keyPath = GeneralUtil.getApplicationDataDirectory() + File.separator + "key";
            if (!file.exists() || !ivFile.exists()) {
                GeneralUtil.deleteFile(keyPath);
            } else {
                File keyFile = new File(keyPath);
                if (keyFile.exists()) {
                    String key;
                    BufferedReader br;
                    FileInputStream fis;
                    try {
                        br = new BufferedReader(new FileReader(keyFile));
                        key = br.readLine();
                        br.close();

                        byte[] values = new byte[0];
                        String iv;

                        fis = new FileInputStream(file);
                        int val = fis.read();
                        while (val != -1) {
                            byte[] aux = new byte[values.length + 1];
                            for (int i = 0; i < values.length; i++) {
                                aux[i] = values[i];
                            }
                            aux[aux.length - 1] = (byte) val;
                            values = aux;
                            val = fis.read();
                        }
                        fis.close();
                        br = new BufferedReader(new FileReader(ivFile));
                        iv = br.readLine();
                        br.close();

                        String originalData;
                        originalData = EncryptionUtil.decrypt(values, key, iv);
                        String systemData = originalData.substring(0, originalData.indexOf("---other---"));
                        if (!GeneralUtil.compareSystemProperties(systemData)) {
                            logger.warn("Another pc trying to use a invalid key");
                            GeneralUtil.deleteFile(keyPath);
                        }
                        long licenceDate = Long.parseLong(originalData.substring(originalData.indexOf("---other---") + 11, originalData.indexOf("limit=")));
                        long cont = Long.parseLong(originalData.substring(originalData.indexOf("cont=") + 5, originalData.length()));
                        long limit = Long.parseLong(originalData.substring(originalData.indexOf("limit=") + 6, originalData.indexOf("cont=")));
                        long dateDifferences = (new Date()).getTime() - licenceDate;
                        long dayDifferences = dateDifferences / (long) 86400000;
                        if (dayDifferences > cont) {
                            if (dayDifferences >= limit) {
                                GeneralUtil.deleteFile(keyPath);
                            } else {
                                String newData = originalData.substring(0, originalData.indexOf("cont=") + 5);
                                newData += dayDifferences;
                                byte[] newBytes = EncryptionUtil.encrypt(newData, key, iv);
                                FileOutputStream fos = new FileOutputStream(file);
                                fos.write(newBytes);
                                fos.flush();
                                fos.close();
                            }
                        } else if (dayDifferences < cont) {
                            GeneralUtil.deleteFile(keyPath);
                        }
                    } catch (Exception e) {
                        logger.error("Error checking licence: " + e.getMessage());
                        GeneralUtil.deleteFile(keyPath);
                    }
                }
            }

            //Clean documents 86400
            if (secondCont >= 86400) {
                secondCont = 0;
                File directory;
                if (documentDirectory.equals("default")) {
                    directory = new File("documents");
                } else {
                    directory = new File(documentDirectory);
                }
                if (directory.exists()) {
                    File[] files = directory.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        String filePath = files[i].getPath();
                        String uuid = filePath.substring(filePath.lastIndexOf(File.separator) + 1, filePath.length());
                        DocumentMetadata documentMetadata = Context.getDocumentService().getDocumentMetadata(uuid);
                        if (documentMetadata == null) {
                            boolean fileDeleted = files[i].delete();
                            if (!fileDeleted) {
                                logger.warn(String.format("Error to delete file: '%s' on clean files function", uuid));
                            }
                        }
                    }
                }
            }
            try {
                sleep(1000);
                secondCont++;
            } catch (InterruptedException e) {
                logger.warn("Interrupted sleep in secundary thread");
            }
        }
    }
}
