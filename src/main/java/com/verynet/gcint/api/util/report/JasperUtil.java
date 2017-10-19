package com.verynet.gcint.api.util.report;

import com.verynet.gcint.api.exceptions.InvalidDirectoryException;
import com.verynet.gcint.api.util.GeneralUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.export.SimpleReportExportConfiguration;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;

/**
 * Created by day on 08/10/2016.
 */
public class JasperUtil {

    @Value("${report.directory}")
    private String path;

    public void compileReport(String jrxmlPath, String jasperPath) throws JRException {
        JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);
    }

    public JasperPrint generateReport(String jasperPath, HashMap params, Connection dbConnection) throws JRException {
        return JasperFillManager.fillReport(jasperPath, params, dbConnection);
    }

    public void exportReport(JasperPrint jasperPrint, Integer pOffset, Integer pLimit, String name) throws JRException {

        JRAbstractExporter exporter;
        SimpleReportExportConfiguration configuration;

        exporter = new JRPdfExporter();
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(getDirectory().getPath() + "/" + name));
        configuration = new SimplePdfReportConfiguration();
        reportExportPagination(configuration, pOffset, pLimit, jasperPrint.getPages().size());
        exporter.setConfiguration(configuration);
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.exportReport();

    }

    private void reportExportPagination(SimpleReportExportConfiguration exportConfiguration, Integer pOffset, Integer pLimit, Integer totalPages) {
        pOffset = pOffset == null ? 0 : (pOffset <= totalPages ? pOffset : totalPages) - 1;
        exportConfiguration.setStartPageIndex(pOffset);

        pLimit = (pLimit == null || (exportConfiguration.getStartPageIndex() + pLimit) > totalPages) ? null : exportConfiguration.getStartPageIndex() + pLimit - 1;
        exportConfiguration.setEndPageIndex(pLimit);
    }

    public void handleMultipartResponse(String fileName, HttpServletResponse response) throws IOException {

        File file = getDirectory();
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        ServletOutputStream out = response.getOutputStream();
        File fileTarget = new File(file.getAbsolutePath() + File.separator + fileName);
        BufferedInputStream fif = new BufferedInputStream(new FileInputStream(fileTarget));
        int data;
        while ((data = fif.read()) != -1) {
            out.write(data);
        }
        fif.close();
        out.flush();
        out.close();
        GeneralUtil.deleteFile(fileTarget.getPath());
    }

    private File getDirectory() {
        File file;
        if (path.equals("default")) {
            file = new File("reports");
        } else {
            file = new File(path);
        }
        boolean exist = false;
        if (!file.exists()) {
            exist = file.mkdir();
        } else {
            exist = true;
        }
        if (!exist) {
            throw new InvalidDirectoryException("The directory to save reports does not exist");
        }
        return file;
    }
}
