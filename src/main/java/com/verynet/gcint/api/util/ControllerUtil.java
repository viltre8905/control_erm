package com.verynet.gcint.api.util;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.exceptions.InvalidDirectoryException;
import com.verynet.gcint.api.model.DocumentMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.UUID;

/**
 * Created by day on 18/09/2016.
 */
public class ControllerUtil {
    @Value("${document.directory}")
    private String documentDirectory;
    private static final Logger logger = LoggerFactory.getLogger(ControllerUtil.class);

    public DocumentMetadata handleMultipartRequest(MultipartHttpServletRequest multipartHttpServletRequest) {
        try {
            Iterator iterator = multipartHttpServletRequest.getFileNames();
            if (iterator.hasNext()) {
                File file = getDirectory();
                String id = UUID.randomUUID().toString();
                String filename = (String) iterator.next();
                MultipartFile multipartFile = multipartHttpServletRequest.getFile(filename);
                DocumentMetadata documentMetadata = new DocumentMetadata();
                documentMetadata.setId(id);
                documentMetadata.setExtension(multipartFile.getOriginalFilename().split("\\.")[1]);
                documentMetadata.setType(multipartFile.getContentType());
                documentMetadata.setFileName(filename);
                documentMetadata = Context.getDocumentService().saveDocumentMetadata(documentMetadata);
                File fileTarget = new File(file.getAbsolutePath() + "/" + id);
                multipartFile.transferTo(fileTarget);
                return documentMetadata;
            }
        } catch (Exception e) {
            logger.error(String.format("Error handle multipart request: %s",e.getMessage()));
        }
        return null;
    }

    public void handleMultipartResponse(DocumentMetadata documentMetadata, HttpServletResponse response) throws IOException {

        File file = getDirectory();
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + String.format("%s.%s", documentMetadata.getFileName(), documentMetadata.getExtension()) + "\"");
        ServletOutputStream out = response.getOutputStream();
        File fileTarget = new File(file.getAbsolutePath() + "/" + documentMetadata.getId());
        BufferedInputStream fif = new BufferedInputStream(new FileInputStream(fileTarget));
        int data;
        while ((data = fif.read()) != -1) {
            out.write(data);
        }
        fif.close();
        out.flush();
        out.close();
    }

    private File getDirectory() {
        File file;
        if (documentDirectory.equals("default")) {
            file = new File("documents");
        } else {
            file = new File(documentDirectory);
        }
        boolean exist = false;
        if (!file.exists()) {
            exist = file.mkdir();
        } else {
            exist = true;
        }
        if (!exist) {
            throw new InvalidDirectoryException("The directory to save documents does not exist");
        }
        return file;
    }

    public String activate(String key) {
        File file = new File(GeneralUtil.getApplicationDataDirectory() + File.separator + "erm_data_file");
        File ivFile = new File(GeneralUtil.getApplicationDataDirectory() + File.separator + "iv");
        if (!file.exists() || !ivFile.exists()) {
            return "No se ha generado ninguna clave";
        }
        byte[] values;
        String iv;
        try {
            FileInputStream fis = new FileInputStream(file);
            values = new byte[0];
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
            BufferedReader br = new BufferedReader(new FileReader(ivFile));
            iv = br.readLine();
            br.close();
        } catch (IOException e) {
            logger.error(String.format("Licence Error: %s",e.getMessage()));
            return "Error en los archivos claves";
        }
        String originalData = "";
        try {
            originalData = EncryptionUtil.decrypt(values, key, iv);
        } catch (Exception e) {
            logger.error("Licence Error: " + e.getMessage()+" key:"+key+" IV:"+iv);
            return "La clave introducida no es válida";
        }
        String systemData = originalData.substring(0, originalData.indexOf("---other---"));
        if (!GeneralUtil.compareSystemProperties(systemData)) {
            return "La clave introducida no es válida para esta pc";
        }
        try {
            File fileKey = new File(GeneralUtil.getApplicationDataDirectory() + File.separator + "key");
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileKey));
            bw.write(key);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            logger.error(String.format("Licence Error: %s",e.getMessage()));
            return "Error al almacenar la clave";
        }
        return null;
    }

    public String activate(MultipartHttpServletRequest request) {
        try {
            Iterator iterator = request.getFileNames();
            if (iterator.hasNext()) {
                File file = new File(GeneralUtil.getApplicationDataDirectory() + File.separator + "erm_data_file");
                File ivFile = new File(GeneralUtil.getApplicationDataDirectory() + File.separator + "iv");
                BufferedWriter bw = new BufferedWriter(new FileWriter(ivFile));
                bw.write("TY98ASM21KLQ47DF");
                bw.flush();
                bw.close();
                String filename = (String) iterator.next();
                MultipartFile multipartFile = request.getFile(filename);
                InputStream inputStream = multipartFile.getInputStream();
                int val = inputStream.read();
                String data = "";
                byte[] values = new byte[0];
                while (val != -1) {
                    byte[] aux = new byte[values.length + 1];
                    for (int i = 0; i < values.length; i++) {
                        aux[i] = values[i];
                    }
                    aux[aux.length - 1] = (byte) val;
                    values = aux;
                    data += (char) val;
                    val = inputStream.read();
                }
                inputStream.close();
                byte[] bytes = new byte[values.length - 20];
                for (int i = 0; i < bytes.length; i++) {
                    bytes[i] = values[i];
                }
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes);
                return activate(data.split("<..>")[1]);
            }
        } catch (Exception e) {
            logger.error(String.format("Licence Error: %s",e.getMessage()));
        }
        return "Ha ocurrido un error inesperado";
    }

}
