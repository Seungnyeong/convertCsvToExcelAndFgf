import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvToFgf {


    public static List<List<String>> readCsvFile() throws IOException {
        List<List<String>> ret = new ArrayList<>();
        BufferedReader br;

        try {
            br = Files.newBufferedReader(Paths.get("/Users/sn/IdeaProjects/promise/src/main/resources/file/dataset2_poi2.csv"));
            String line;
            List<String> tmpList;
            while((line = br.readLine()) != null) {
                String arr[] = line.split(",");
                tmpList = Arrays.asList(arr);
                ret.add(tmpList);
            }
            return ret;
        } catch (IOException e) {
            throw e;
        }
    }

    public static List<FgfVO> convertToFgf(List<List<String>> list) {

        List<FgfVO> listFgf = new ArrayList<>();
        list.stream().map(s->listFgf.add(new FgfVO.Builder()
                .setPk(s.get(0))
                .setSentense(s.get(1))
                .setCategory_l(s.get(2))
                .setCategory_m(s.get(3))
                .setCategory_s(s.get(4))
        .build())).collect(Collectors.toList());
       return listFgf;

    }

    public static void main(String[] args) throws IOException {
        List<List<String>> result = readCsvFile();
        List<FgfVO> fgfList = convertToFgf(result);
        String filename = "/Users/sn/IdeaProjects/promise/src/main/resources/file/cls_stt_mid.fgf";
        FileWriter fw = null;
        try{
            File file = new File(filename);
            fw = new FileWriter(file, true);
            for (int i =0 ; i < fgfList.size(); i++) {
                fw.write(fgfList.get(i).getPk() + "\n");
                fw.write(fgfList.get(i).getSentense() + "\n");
                fw.write(fgfList.get(i).getCategory_l()+ "\n");
                fw.write(fgfList.get(i).getCategory_m()+ "\n");
                fw.write(fgfList.get(i).getCategory_s()+ "\n");
            }

        } catch ( IOException e) {
            throw e;
        } finally {
            fw.flush();
            fw.close();
        }
    }
}
