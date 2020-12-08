import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.*;
import java.util.ArrayList;

public class ExcelToCsv {
    private final static String QUOTE="\"";
    private final static String ESCAPED_QUOTE="\\\"\\\"";
    private final static String COMMA="|";
    private final static String NEW_LINE="\n";
    private Workbook wb = null;
    private ArrayList<ArrayList> data = null;
    private int maxWidth = 7;
    private DataFormatter fmt = null;
    private FormulaEvaluator evl = null;
    private int initPo = 1;
    private final static String SELECTED_SHEET_NAME = "model";
    private final static String BASE_FILE_PATH = "/Users/sn/IdeaProjects/promise/src/main/resources/file/";
    public ExcelToCsv(File src, File dest) throws IOException, InvalidFormatException {
        validateArgs(src, dest);
        File[] files = src.isDirectory() ? src.listFiles(new ExcelFilenameFilter()) : new File[]{src};
        for (File xls : files) {
            File csv = new File(dest, getCsvFileName(xls));
            openWorkbook(xls);
            createCsv();
            saveCsvFile(csv);
        }
    }

    private void validateArgs(File src, File dest) {
        if (!src.exists()) {
            throw new IllegalArgumentException(src.getName()+" does not exist.");
        }

        if (!dest.exists()) {
            throw new IllegalArgumentException(dest.getName()+" does not exist.");
        }

        if (!dest.isDirectory()) {
            throw new IllegalArgumentException(dest.getName()+" is not a directory.");
        }
    }

    private String getCsvFileName(File xls) {
        return xls.getName().substring(0, xls.getName().lastIndexOf(".")) + "_" + SELECTED_SHEET_NAME +".csv";
    }

    private void openWorkbook(File f) throws IOException, InvalidFormatException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            wb = WorkbookFactory.create(fis);
            evl = wb.getCreationHelper().createFormulaEvaluator();
            fmt = new DataFormatter(true);
        } finally {
            if (fis != null) fis.close();
        }
    }

    private void createCsv() {
        data = new ArrayList<ArrayList>();
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet s = wb.getSheetAt(i);
            String sheetName = s.getSheetName();
            if( SELECTED_SHEET_NAME.equalsIgnoreCase(sheetName)) {
                if (s.getPhysicalNumberOfRows() > 0) {
                    for (int j =0; j <= s.getLastRowNum(); j++) {
                        addRowToCsv(s.getRow(j));
                    }
                }

            }
        }

    }


    private void saveCsvFile(File f) throws IOException {
        BufferedWriter bw = null;
        try {
          bw = new BufferedWriter(new FileWriter(f));
          for (int i = initPo; i < data.size(); i++) {
              StringBuilder b = new StringBuilder();
              ArrayList<String> line = data.get(i);
              for (int j = 0; j < maxWidth; j++) {
                  if (line.size() > j) {
                      String field = line.get(j).replace("\n","").replace("상담사:","").replace("고객:","");
                      if (field != null) {
                          b.append(escape(field));
                      }
                  }
                  if ( j < (maxWidth -1 )) {
                      b.append(COMMA);
                  }
              }
              bw.write(b.toString().trim());
              if ( i < (data.size() -1))
                  bw.newLine();
          }
        } finally {
            if (bw != null) {
                bw.flush();
                bw.close();
            }
        }
    }

    private String getData(Cell c) {
        if ( c != null) {
            try {
                return (c.getCellType() != Cell.CELL_TYPE_FORMULA) ? fmt.formatCellValue(c) : fmt.formatCellValue(c, evl);
            } catch (Exception e) {
                System.out.println("Warning : " + e.getMessage());
            }
        }
        return "";
    }

    private void addRowToCsv(Row r) {
        ArrayList<String> line = new ArrayList<String>();

        if(r != null) {
            int idx = r.getLastCellNum();
            for (int i = 0; i <= idx; i++) {
                if ( i == 0) { // pk
                    line.add(getData(r.getCell(i)));
                } else if ( i == 6) { // sentence
                    line.add(getData(r.getCell(i)));
                } else if ( i == 7) { // category_l
                    line.add(getData(r.getCell(i)));
                } else if ( i== 9) { // category_m
                    line.add(getData(r.getCell(i)));
                } else if ( i == 10) { // category_m_code
                    line.add(getData(r.getCell(i)));
                } else if ( i == 12) { // category_s
                    line.add(getData(r.getCell(i)));
                } else if ( i == 13 ) { // category_s_code
                    line.add(getData(r.getCell(i)));
                }
            }
        }
        data.add(line);
    }

    private StringBuffer appendQuote(StringBuffer b) {
        return b.insert(0, QUOTE).append(QUOTE);
    }

    private String escape(String f) {
        if (f.contains(QUOTE))
            f=f.replace(QUOTE, ESCAPED_QUOTE);

        StringBuffer b = new StringBuffer(f);
        if ((b.indexOf(COMMA)) > -1 || (b.indexOf(NEW_LINE) > -1 || b.indexOf(QUOTE) > -1)) b = appendQuote(b);

        return b.toString().trim();
    }

    public static void main(String[] args) throws Exception {

        new ExcelToCsv(new File(BASE_FILE_PATH.concat("dataset.xlsx")), new File(BASE_FILE_PATH));
    }

    class ExcelFilenameFilter implements FilenameFilter {
        public boolean accept(File file, String name) {return (name.endsWith(".xls")) || name.endsWith(".xlsx");}
    }
}
