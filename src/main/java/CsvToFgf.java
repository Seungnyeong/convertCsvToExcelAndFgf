import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvToFgf {
    private final static String SELECTED_SHEET_NAME = "test";
    private final static String BASE_FILE_PATH = "/Users/sn/IdeaProjects/promise/src/main/resources/file/";

    private static List<List<String>> readCsvFile() throws IOException {
        List<List<String>> ret = new ArrayList<>();
        Files.lines(Paths.get(BASE_FILE_PATH.concat("dataset_").concat(SELECTED_SHEET_NAME).concat(".csv")), StandardCharsets.UTF_8)
                .parallel()
                .map(line -> Arrays.asList(line.split("\\|")))
                .filter(ret::add)
                .collect(Collectors.toList());
        return ret;
    }

    private static List<FgfVO> convertToFgf(List<List<String>> list) {
        List<FgfVO> listFgf = new ArrayList<>();
        list.stream().map( s-> listFgf.add(new FgfVO.Builder()
                .setPk(s.get(0))
                .setSentence(s.get(1))
                .setCategory_l(s.get(2))
                .setCategory_m(s.get(3))
                .setCategory_m_code(s.get(4))
                .setCategory_s(s.get(5))
                .setCategory_s_code(s.get(6))
        .build())).collect(Collectors.toList()); //TODO 여기서  parallel 하면 데이터 유실 발생 왜그럼?
       return listFgf;
    }

    public static void main(String[] args) throws IOException {
        List<List<String>> result = readCsvFile();
        List<FgfVO> fgfList = convertToFgf(result);
        String filepath = BASE_FILE_PATH.concat("stt_cls_").concat(SELECTED_SHEET_NAME).concat(".fgf");
        FileWriter fw = null;
        File file = new File(filepath);

        try{
            //TODO Files.write and Stream API 이용하여 file write 방법 문의
            boolean removeFileFlag = file.exists() ? file.delete() : false;
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

        } catch ( IOException e) {
            throw e;
        } finally {
            fw.flush();
            fw.close();
            System.out.println(file.exists() == true ? SELECTED_SHEET_NAME + "fgf 파일 생성 성공 : " + filepath : "파일 생성 실패[x]");
        }
    }
}
