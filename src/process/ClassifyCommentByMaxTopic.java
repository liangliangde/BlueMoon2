package process;


import java.io.*;
import java.util.*;


/**
 * Created by llei on 16-3-3.
 */
public class ClassifyCommentByMaxTopic {

    public static void main(String args[]) throws IOException {
        Map<Integer, String> topicMap = loadTopicMap("data\\LDA\\topicMap.txt");
        List<Double[]> topicAssignList = loadTopicAssignList("data\\LDA\\model-final.theta");

//        List<List<String>> cluster = assignToTopic(seriesIdUserMap, totaltopicNum);
    }

    public static List<Double[]> loadTopicAssignList(String path) throws IOException {
        List<Double[]> topicAssignList = new ArrayList<>();
        FileInputStream fis = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String lineTxt = null;
        while ((lineTxt = br.readLine()) != null) {
            Double[] validTopicAssignArray = stringArr2Double(lineTxt.split(" "));
            topicAssignList.add(validTopicAssignArray);
        }
        fis.close();
        isr.close();
        br.close();
        return  topicAssignList;
    }

    public static Map<Integer, String> loadTopicMap(String path) throws IOException {
        Map<Integer, String> topicMap = new TreeMap<>();
        FileInputStream fis = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String lineTxt = null;
        while ((lineTxt = br.readLine()) != null) {
            String[] arr = lineTxt.split(" ");
            topicMap.put(Integer.parseInt(arr[0]), arr[1]);
        }
        fis.close();
        isr.close();
        br.close();
        return topicMap;
    }

    private static List<List<String>> assignToTopic(Map<String, Double[]> seriesIdUserMap, int topicNum) {
        List<List<String>> cluster = new ArrayList<>();
        for (int i = 0; i < topicNum; i++) {
            cluster.add(new ArrayList<>());
        }
        for (Map.Entry<String, Double[]> entry : seriesIdUserMap.entrySet()) {
            Double[] vec = entry.getValue();
            int maxTopic = 0;
            Double maxPro = 0.0;
            for (int i = 0; i < vec.length; i++) {
                if (vec[i] > maxPro) {
                    maxPro = vec[i];
                    maxTopic = i;
                }
            }
            cluster.get(maxTopic).add(entry.getKey());
        }
        return cluster;
    }

    private static List<String> getSeriesIds(String path) throws IOException {
        List<String> seriesIds = new ArrayList<>();
        File file = new File(path);
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String lineTxt = null;
        while ((lineTxt = bufferedReader.readLine()) != null) {
            seriesIds.add(lineTxt);
        }
        inputStreamReader.close();
        bufferedReader.close();
        return seriesIds;
    }

    private static Map<String, Double[]> getUserVec(String path, List<String> seriesIds) throws IOException {
        Map<String, Double[]> seriesIdUserMap = new HashMap<>();
        File file = new File(path);
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String lineTxt = null;
        int i = 0;
        while ((lineTxt = bufferedReader.readLine()) != null) {
            seriesIdUserMap.put(seriesIds.get(i++), stringArr2Double(lineTxt.split(" ")));
        }
        inputStreamReader.close();
        bufferedReader.close();
        return seriesIdUserMap;
    }

    private static Double[] stringArr2Double(String[] strArr) {
        Double[] doubleArr = new Double[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            doubleArr[i] = Double.parseDouble(strArr[i]);
        }
        return doubleArr;
    }

//    private static Double[] getValidAssignment(String[] strArr) {
//        Double[] doubleArr = new Double[topicMap.size()];
//        int index = 0;
//        for (int i = 0; i < strArr.length; i++) {
//            if (topicMap.containsKey(i)) {
//                doubleArr[index++] = Double.parseDouble(strArr[i]);
//            }
//        }
//        return doubleArr;
//    }
}
