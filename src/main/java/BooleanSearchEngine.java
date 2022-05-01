import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {

    private Map<String, TreeSet<PageEntry>> answers = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {

        for (File file : pdfsDir.listFiles()) {

            var doc = new PdfDocument(new PdfReader(file));
            for (int i = 1; i < doc.getNumberOfPages(); i++) {
                var text = PdfTextExtractor.getTextFromPage(doc.getPage(i));
                var words = text.split("\\P{IsAlphabetic}+");
                Map<String, Integer> freqs = new HashMap<>();
                for (var word : words) {
                    if (word.isEmpty()) {
                        continue;
                    }
                    freqs.put(word.toLowerCase(), freqs.getOrDefault(word, 0) + 1);
                }

                for (Map.Entry<String, Integer> item : freqs.entrySet()) {
                    answers.putIfAbsent(item.getKey(), new TreeSet<>());
                    answers.get(item.getKey()).add(new PageEntry(file.getName(), i, item.getValue()));
                }
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        ArrayList<PageEntry> answer = new ArrayList<>();

        TreeSet<PageEntry> answersSet = new TreeSet<>(answers.get(word.toLowerCase()));

        while (!answersSet.isEmpty()) {
            answer.add(answersSet.pollLast());
        }
        return answer;
    }
}
