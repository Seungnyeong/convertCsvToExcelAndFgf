import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {


    public static List<List<String>> readToList(String path) {
        List<List<String>> list = new ArrayList<List<String>>();
        try {
            FileInputStream fi = new FileInputStream(path);
            XSSFWorkbook workbook = new XSSFWorkbook(fi);
            XSSFSheet sheet = workbook.getSheet("b");
            XSSFCell cell;
            XSSFRow row;
            FormulaEvaluator ev = workbook.getCreationHelper().createFormulaEvaluator();
            for (int i = 1; i < sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);

                List<String> cellList = new ArrayList<String>();
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    cell = row.getCell(j);
                    if (j == 10) {
                        CellValue cellValue = ev.evaluate(cell);
                        cellList.add(cellValue.getStringValue()); //셀을 읽어와서 List에 추가
                    } else if (j == 6) {
                        String result = cellReader(cell).replaceAll("\n","").replaceAll("[-=+,#/\\?:^$.@*\\\"※~&%ㆍ!』\\\\‘|\\(\\)\\[\\]\\<\\>`\\'…》]","");
                        cellList.add("\""+ result + "\"");
                    }
                }
                list.add(cellList);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static String cellReader(XSSFCell cell) {
        String value = "";
        CellType ct = cell.getCellTypeEnum();
        if(ct != null) {
            switch(cell.getCellTypeEnum()) {
                case FORMULA:
                    value = cell.getCellFormula();
                    break;
                case NUMERIC:
                    value=cell.getNumericCellValue()+"";
                    break;
                case STRING:
                    value=cell.getStringCellValue()+"";
                    break;
                case BOOLEAN:
                    value=cell.getBooleanCellValue()+"";
                    break;
                case ERROR:
                    value=cell.getErrorCellValue()+"";
                    break;
            }
        }
        return value;
    }

    public static void convertCSV(List<List<String>> list, String title) {
        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter("/Users/sn/IdeaProjects/promise/src/main/resources/file/"+ title + ".csv", false));
            for ( int i =0; i < list.size(); i ++) {
                for ( int j =0 ; j < list.get(i).size(); j++ ) {
                    fw.write(list.get(i).get(0) + ",\"" + list.get(i).get(1) + "\"");
                    fw.newLine();
                }
            }

            fw.flush();
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String filepath = "/Users/sn/IdeaProjects/promise/src/main/resources/file/dataset.xlsx";
        List<List<String>> newlist = new ArrayList<List<String>>();
        newlist = readToList(filepath);
        convertCSV(newlist, "test2");
    }
}