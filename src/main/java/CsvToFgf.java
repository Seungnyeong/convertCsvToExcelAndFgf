import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvToFgf {


    public static List<List<String>> readCsvFile() {
        List<List<String>> ret = new ArrayList<>();
        BufferedReader br = null;

        try {
            br = Files.newBufferedReader(Paths.get("/Users/sn/IdeaProjects/promise/src/main/resources/file/dataset2_poi2.csv"));
            String line = "";

            while((line = br.readLine()) != null) {
                List<String> tmpList = new ArrayList<String>();
                String arr[] = line.split(",");
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
            vo = new FgfVO();
            vo.setPk(list.get(i).get(0));
            vo.setSentense(list.get(i).get(1));
            vo.setCategory_l(list.get(i).get(2));
            vo.setCategory_m(list.get(i).get(3));
            vo.setCategory_s(list.get(i).get(4));
            listFgf.add(vo);
        }
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

        } catch ( Exception e) {

        } finally {
            fw.flush();
            fw.close();
        }



    }
}
