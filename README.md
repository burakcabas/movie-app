# 🎬 AI-Powered Movie Recommendation System (RAG)

Bu proje, Spring Boot kullanılarak geliştirilmiş, TMDb API ile senkronize çalışan ve **Google Gemini AI** entegrasyonu ile "Retrieval-Augmented Generation" (RAG) mimarisi barındıran akıllı bir sinema platformudur.

## 🚀 Proje Vizyonu
Standart bir CRUD uygulamasının ötesine geçerek, uygulamanın kendi veritabanının farkında olan "Context-Aware" bir yapay zeka asistanı tasarlanmıştır. Kullanıcılar sadece standart arama yapmakla kalmaz, aynı zamanda veritabanındaki filmler üzerinden kompleks sorular sorarak (Örn: "En yüksek puanlı aksiyon filmlerinden hangilerini önerirsin?") yapay zekadan anında yanıt alabilirler.

## 🛠️ Kullanılan Teknolojiler
* **Backend:** Java 21, Spring Boot 3
* **Veritabanı:** MySQL, Spring Data JPA / Hibernate
* **Yapay Zeka:** Google Gemini Pro API (RAG Mimarisi)
* **Dış Servisler:** TMDb (The Movie Database) API
* **Frontend:** HTML5, Bootstrap 5, Thymeleaf, Fetch API (Frutiger Aero Design)
* **Dokümantasyon:** Swagger UI / OpenAPI 3.0

## ✨ Temel Özellikler
1. **Dinamik Veri Çekimi:** TMDb API üzerinden düzenli olarak popüler filmler, tür sözlükleri (Genre Parsing) ile birlikte çekilerek veritabanına otomatik kaydedilir.
2. **Güvenli Veri İşleme (Resilience):** `Check-then-insert` mantığı ile veritabanı tekrarlayan kayıtlara karşı korunur. Global Exception Handling (`@RestControllerAdvice`) ile hatalar zarif JSON formatlarına dönüştürülür.
3. **Akıllı RAG Asistanı:** Kullanıcı promptları, MySQL veritabanından çekilen film bağlamlarıyla (context) zenginleştirilerek Gemini AI'a iletilir. Yapay zeka halüsinasyon görmeden, **sadece** sistemdeki filmler üzerinden yanıt üretir.
4. **Modern UX/UI:** Nostaljik "Frutiger Aero" estetiği ile tasarlanmış, asenkron (Fetch API) çalışan, "Enter" tuşu algılama gibi kullanıcı dostu detaylara sahip interaktif bir arayüz.

## ⚙️ Nasıl Çalıştırılır?
Projeyi kendi ortamınızda çalıştırmak için aşağıdaki adımları izleyin:

1. Depoyu klonlayın: `git clone https://github.com/KULLANICI_ADIN/movie-app.git`
2. MySQL üzerinde `mcp_movie_db` adında bir veritabanı oluşturun.
3. Çevresel Değişkenlerinizi (Environment Variables) ayarlayın:
   * `GEMINI_API_KEY` = Sizin Google Gemini API anahtarınız
   * `TMDB_API_KEY` = Sizin TMDb API anahtarınız
4. Projeyi IDE üzerinden veya Maven ile çalıştırın: `mvn spring-boot:run`
5. Tarayıcınızda şu adrese gidin: `http://localhost:8080`

---
*Geliştirici: Burak Çabaş - Computer Engineering MSc.*
