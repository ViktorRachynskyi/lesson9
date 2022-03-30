package qa.guru;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.codeborne.pdftest.assertj.Assertions.assertThat;
import static io.netty.util.internal.SystemPropertyUtil.contains;

public class FileTests {


    @Test
    void readFilesTest() throws Exception {
        ZipFile zipFile = new ZipFile("src/test/resources/files/file.zip");
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (entry.getName().contains("csv")) {
                assertThat(entry.getName()).isEqualTo("csvFile.csv");
                parseCSVTest(zipFile.getInputStream(entry));
            } else if (entry.getName().contains("pdf")) {
                assertThat(entry.getName()).isEqualTo("pdfFile.pdf");
                parsePdfTest(zipFile.getInputStream(entry));
            } else if (entry.getName().contains("xlsx")) {
                assertThat(entry.getName()).isEqualTo("excelFile.xlsx");
                parseXlsxTest(zipFile.getInputStream(entry));

            }
        }
    }

    void parseCSVTest(InputStream file) throws Exception {
        try (CSVReader reader = new CSVReader(new InputStreamReader(file));) {
            List<String[]> strA = reader.readAll();
            assertThat(strA.get(0)).contains("this is CSV file");
        }
    }

    void parseXlsxTest(InputStream file) throws Exception {
        XLS xls = new XLS(file);
        assertThat(xls.excel
                .getSheetAt(0)
                .getRow(0)
                .getCell(0)
                .getStringCellValue()).contains("this is XLSX file");
    }

    void parsePdfTest(InputStream file) throws Exception {
        PDF pdf = new PDF(file);
        assertThat(pdf.text).contains("This is CSV file");
    }
}