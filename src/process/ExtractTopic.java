package process;

import database.CommentSQLAccess;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


/**
 * Created by llei on 2016/10/31.
 */
public class ExtractTopic {
    private static Set<String> stopWords;
    //    private static String[] usefulWordType = {"a"};
    private static String[] usefulWordType = {"a", "n", "vn", "v"};

    public static void main(String[] args) throws SQLException, IOException {
//        createInputforLDA();
        createValidCommentMap("data\\LDA\\validComment.txt");
    }

    private static void createValidCommentMap(String path) throws IOException {
        Map<Integer, String> topicMap = ClassifyCommentByMaxTopic.loadTopicMap("data\\LDA\\topicMap.txt");
        List<Double[]> topicAssignList = ClassifyCommentByMaxTopic.loadTopicAssignList("data\\LDA\\model-final.theta");
        List<String> validCommentIDList = new ArrayList<>();
        FileInputStream fis = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String lineTxt = null;
        int index = 0;
        double threshold = 0.12;
        while ((lineTxt = br.readLine()) != null) {
            List<Integer> assignTypeArr = getAssignType(topicMap, topicAssignList.get(index++), threshold);
            for (Map.Entry<Integer, String> entry : topicMap.entrySet()) {
                if (assignTypeArr.contains(entry.getKey())){

                }
            }
//            validCommentIDList.add(lineTxt + " " + maxAssignType);
        }
        fis.close();
        isr.close();
        br.close();
    }

    private static List<Integer> getAssignType(Map<Integer, String> topicMap, Double[] doubles, double theshold) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < doubles.length; i++) {
            if (topicMap.containsKey(i) && doubles[i] > theshold) {
                list.add(i);
            }
        }
        return list;
    }

    private static void createInputforLDA() throws IOException, SQLException {
        stopWords = loadStopWords("data\\stopwords.txt");
        ResultSet rs = CommentSQLAccess.getValidComment(100);
        StringBuilder sb = new StringBuilder();
        StringBuilder commentIDMap = new StringBuilder();
        int validCommentNum = 0;
        while (rs.next()) {
            String cur = splitWords(rs.getString(2));
            int wordNum = Integer.parseInt(cur.substring(0, cur.indexOf(",")));
            if (wordNum > 5) {
                validCommentNum++;
                commentIDMap.append(rs.getString(1)).append("\n");
                sb.append(cur.substring(cur.indexOf(",") + 1)).append("\n");
            }
        }
        FileOutputStream fos = new FileOutputStream("data\\LDA\\LDAinput.txt", false);
        fos.write((validCommentNum + "\n" + sb.toString()).getBytes());
        fos.close();
        FileOutputStream fos2 = new FileOutputStream("data\\LDA\\validComment.txt", false);
        fos2.write(commentIDMap.toString().getBytes());
        fos2.close();
    }

    private static Set<String> loadStopWords(String path) throws IOException {
        Set<String> set = new HashSet<>();
        FileInputStream fis = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String linetxt = null;
        while ((linetxt = br.readLine()) != null) {
            set.add(linetxt);
        }
        fis.close();
        isr.close();
        br.close();
        return set;
    }

    private static String splitWords(String line) {
        List<Term> parse = ToAnalysis.parse(line.trim());
        StringBuilder sb = new StringBuilder();
        int wordNum = 0;
        for (Term term : parse) {
            String word = term.getName().trim();
            if (word.length() >= 2 && !stopWords.contains(word)) {
//                sb.append(term + " ");
                for (String w : usefulWordType) {
                    if (w.equals(term.getNatureStr())) {
                        sb.append(term.getName() + " ");
                        wordNum++;
                        break;
                    }
                }
            }
        }
        return wordNum + "," + sb.toString();
    }
}