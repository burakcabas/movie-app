package com.mcp.movieapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcp.movieapp.entity.Movie;
import com.mcp.movieapp.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Veritabanina ulasmak icin Repository'i buraya dahil ediyoruz
    @Autowired
    private MovieRepository movieRepository;

    // Eskiden yazdigimiz standart soru sorma metodu (bu kalabilir)
    public String askQuestion(String question) {
        // ... (Bu kisimdaki eski kodlarin ayni kalacak)
        String url = baseUrl + "?key=" + apiKey;
        String requestBody = """
                {
                  "contents": [{
                    "parts":[{"text": "%s"}]
                  }]
                }
                """.formatted(question);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        try {
            String response = restTemplate.postForObject(url, request, String.class);
            JsonNode rootNode = objectMapper.readTree(response);
            if (rootNode != null && rootNode.has("candidates")) {
                return rootNode.get("candidates").get(0)
                        .get("content").get("parts").get(0)
                        .get("text").asText();
            }
            return "Gemini'den anlasilir bir cevap alinamadi.";
        } catch (Exception e) {
            return "Yapay Zeka Baglanti Hatasi: " + e.getMessage();
        }
    }

    // YENI EKLENEN METOT: Veritabani Baglamli (Contextual) Tavsiye Sistemi
    public String recommendMovieFromDb(String userRequest) {
        // 1. Veritabanindaki tum filmleri cek
        List<Movie> movies = movieRepository.findAll();

        if (movies.isEmpty()) {
            return "Veritabaninda hic film yok. Lutfen once TMDb'den film cekin.";
        }

        // 2. Filmleri yapay zekanin okuyabilecegi bir metin listesine donustur
        StringBuilder movieListText = new StringBuilder("Veritabanımdaki Filmler:\n");
        for (Movie m : movies) {
            movieListText.append("- ").append(m.getTitle())
                    .append(" (Yıl: ").append(m.getReleaseYear())
                    .append(", Puan: ").append(m.getRating())
                    .append(", Tür/Kategori: ").append(m.getGenre()).append(")\n"); // TÜR BİLGİSİNİ EKLEDİK
        }

        // 3. Yapay zekaya veritabanini ve kurallari iceren bir "Super Prompt" (Gelistirilmis Istem) hazirla
        String superPrompt = "Sen profesyonel bir sinema eleştirmeni ve film tavsiye asistanısın. " +
                "Kullanıcının isteğine SADECE aşağıdaki listede bulunan filmlerden uygun olanları seçerek cevap ver. " +
                "Eğer listede uygun film yoksa, kibarca bu listede uygun film olmadığını belirt. " +
                "Asla liste dışından bir film önerme. " +
                "Kullanıcı İsteği: " + userRequest + "\n\n" + movieListText.toString();

        // 4. Bu ozel hazirlanmis promptu mevcut metoda gonder
        // YENI EKLENEN KISIM: Metin icindeki alt satira gecme karakterlerini JSON kurallarina uygun kaciriyoruz
        String safePrompt = superPrompt.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");

        return askQuestion(safePrompt);
    }
}