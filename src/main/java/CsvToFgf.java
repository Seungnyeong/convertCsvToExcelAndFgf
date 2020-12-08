import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CsvToFgf {
    public final static String SELECTED_SHEET_NAME = "model";
    public final static String ESCAPE_ENTER = "\n";
    public final static String BASE_FILE_PATH = "/Users/sn/IdeaProjects/promise/src/main/resources/file/";

    public static List<List<String>> readCsvFile() throws IOException {
        List<List<String>> ret = new ArrayList<>();
        Files.lines(Paths.get(BASE_FILE_PATH.concat("dataset_poi2_").concat(SELECTED_SHEET_NAME).concat(".csv")), StandardCharsets.UTF_8)
                .map(line -> Arrays.asList(line.split("\\|")))
                .filter(ret::add)
                .parallel()
                .collect(Collectors.toList());
        return ret;
    }

    public static List<FgfVO> convertToFgf(List<List<String>> list) {
        List<FgfVO> listFgf = new ArrayList<>();
        list.stream().map( s-> listFgf.add(new FgfVO.Builder()
                .setPk(s.get(0).concat(UUID.randomUUID().toString()))
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
        try{
            //TODO Files.write and Stream API 이용하여 file write 방법 문의
            File file = new File(filepath);
            fw = new FileWriter(file, true);

            for (int i =0 ; i < fgfList.size(); i++) {
                fw.write(fgfList.get(i).getPk().concat(ESCAPE_ENTER));
                fw.write(fgfList.get(i).getSentence().concat(ESCAPE_ENTER));
                fw.write(fgfList.get(i).getCategory_l().concat(ESCAPE_ENTER));
                fw.write(fgfList.get(i).getCategory_m().concat(ESCAPE_ENTER));
                fw.write(fgfList.get(i).getCategory_m_code().concat(ESCAPE_ENTER));
                fw.write(fgfList.get(i).getCategory_s().concat(ESCAPE_ENTER));
                fw.write(fgfList.get(i).getCategory_s_code().concat(ESCAPE_ENTER));
            }

        } catch ( IOException e) {
            throw e;
        } finally {
            fw.flush();
            fw.close();
        }
    }
}
