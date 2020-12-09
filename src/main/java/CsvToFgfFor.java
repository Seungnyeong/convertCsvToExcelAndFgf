import org.openjdk.jmh.runner.RunnerException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvToFgfFor {
    private final static String SELECTED_SHEET_NAME = "model";
    private final static String BASE_FILE_PATH = "/Users/sn/IdeaProjects/promise/src/main/resources/file/";

    public static List<List<String>> readCsvFile() {
        List<List<String>> ret = new ArrayList<>();
        BufferedReader br = null;
        List<String> tmpList;

        try {
            br = Files.newBufferedReader(Paths.get(BASE_FILE_PATH.concat("dataset_").concat(SELECTED_SHEET_NAME).concat(".csv")));
            String line = "";

            while((line = br.readLine()) != null) {
                String arr[] = line.split("\\|");
                tmpList = Arrays.asList(arr);
                ret.add(tmpList);
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static List<FgfVO> convertToFgf(List<List<String>> list) {
        FgfVO vo;
        List<FgfVO> listFgf = new ArrayList<>();
        int i;
        for(i = 0; i < list.size(); i ++) {
            vo = new FgfVO.Builder()
                    .setPk(list.get(i).get(0))
                    .setSentence(list.get(i).get(1))
                    .setCategory_l(list.get(i).get(2))
                    .setCategory_m(list.get(i).get(3))
                    .setCategory_m_code(list.get(i).get(4))
                    .setCategory_s(list.get(i).get(5))
                    .setCategory_s_code(list.get(i).get(6))
                    .build();
            listFgf.add(vo);
        }
        return listFgf;

    }

    public static void main(String[] args) throws IOException, RunnerException {
        long beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간
        List<List<String>> result = readCsvFile();
        List<FgfVO> fgfList = convertToFgf(result);
        String filePath = BASE_FILE_PATH.concat("stt_cls_for_").concat(SELECTED_SHEET_NAME).concat(".fgf");
        File file = new File(filePath);
        FileWriter fw = null;
        try{
            boolean removeFileFlag = file.exists() && file.delete();
            System.out.println(SELECTED_SHEET_NAME + "fgf 파일 삭제 : " + removeFileFlag);
            fw = new FileWriter(file, true);
            for (int i =0 ; i < fgfList.size(); i++) {
                fw.write(fgfList.get(i).getPk());
                fw.write(fgfList.get(i).getSentence());
                fw.write(fgfList.get(i).getCategory_l());
                fw.write(fgfList.get(i).getCategory_m());
                fw.write(fgfList.get(i).getCategory_m_code());
                fw.write(fgfList.get(i).getCategory_s());
                fw.write(fgfList.get(i).getCategory_s_code());
            }
            long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
            System.out.println("코드 실행 전: "+beforeTime);
            System.out.println("코드 실행 후: "+afterTime);
            System.out.println("실행 차이: "+(afterTime - beforeTime));
        } catch ( Exception e) {
            throw e;
        } finally {
            fw.flush();
            fw.close();
            System.out.println(file.exists() == true ? SELECTED_SHEET_NAME + "fgf 파일 생성 성공 : " + filePath : "파일 생성 실패[x]");
        }
    }
}