# 🎬 Context-Aware AI Movie Recommendation System (RAG)

![Java](https://img.shields.io/badge/Java-21-orange.svg) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-brightgreen.svg) ![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg) ![Gemini AI](https://img.shields.io/badge/Google_Gemini-Pro-yellow.svg) ![GitHub Actions](https://img.shields.io/badge/CI%2FCD-GitHub_Actions-2088FF.svg)

## 📖 The Story Behind This Project
This project started as a standard REST API but quickly evolved into a much more complex system. My main goal was not just to build another CRUD application, but to bridge the gap between traditional databases and modern Large Language Models (LLMs). 

Instead of letting the AI answer from its general internet knowledge, I implemented a **Retrieval-Augmented Generation (RAG)** architecture. The Gemini AI acts as a "smart waiter," reading strictly from my own MySQL database to provide personalized, context-aware movie recommendations.

## 🏗️ Architectural Decisions & Challenges Overcome
* **Smart Data Fetching (TMDb API):** I didn't just dump raw API data. I implemented a parsing mechanism to map numeric genre IDs to human-readable strings via a separate dictionary fetch, ensuring the AI receives clean, analytical data.
* **Database Safety (Resilience):** Instead of destructive drop/create operations during sync, I implemented a strict `check-then-insert` logic (`existsByTitle`) to protect the database in a production-like environment.
* **The "Overzealous Guard" (Exception Handling):** I built a Global `@RestControllerAdvice` to gracefully handle user errors (like missing search queries). However, this initially broke Swagger UI's internal fetching. I resolved this by isolating the exception handler specifically to my API endpoints using `basePackages` and `@Hidden` annotations, keeping the framework's internal mechanics intact.
* **Production-Ready Operations (DevOps):** To ensure the application is monitorable, I integrated **Spring Boot Actuator** to expose live health metrics. Furthermore, I established a **CI/CD Pipeline** using **GitHub Actions**. Overcoming environment-specific hurdles (like enforcing UTF-8 encoding on Linux runners and configuring Maven to skip tests without a local DB pipeline) was a key part of making this automated build robust.
* **Nostalgic UI/UX:** For the frontend, I stepped away from the overused dark modes and implemented a custom **"Frutiger Aero"** aesthetic using Thymeleaf and Bootstrap 5. It features glassmorphism, responsive asynchronous fetching (Fetch API), and user-friendly event listeners (Enter to search, Shift+Enter for AI prompts).

## 🛠️ Tech Stack
* **Backend:** Java 21, Spring Boot 3, Spring Data JPA, Hibernate
* **Database:** MySQL
* **AI Integration:** Google Gemini Pro API (REST implementation)
* **DevOps & Monitoring:** GitHub Actions (CI/CD), Spring Boot Actuator
* **External API:** The Movie Database (TMDb) API
* **Frontend:** HTML5, CSS3, Bootstrap 5, Vanilla JS (Fetch API), Thymeleaf
* **Documentation:** OpenAPI 3.0 / Swagger UI

## ⚙️ How to Run Locally
1. Clone the repository: `git clone https://github.com/YOUR_USERNAME/movie-app.git`
2. Create a MySQL database named `mcp_movie_db`.
3. Set your Environment Variables:
   * `GEMINI_API_KEY` = Your Google Gemini API Key
   * `TMDB_API_KEY` = Your TMDb API Key
4. Run the application via Maven: `mvn spring-boot:run`
5. Open your browser and navigate to: `http://localhost:8080`
6. (Optional) Check application health: `http://localhost:8080/actuator/health`

---
---

# 🎬 Bağlam Farkındalıklı Yapay Zeka Sinema Platformu (RAG)

## 📖 Bu Projenin Hikayesi
Bu proje standart bir REST API olarak başladı ancak hızla çok daha karmaşık bir sisteme evrildi. Temel amacım sadece başka bir CRUD uygulaması yapmak değil, geleneksel veritabanları ile modern Büyük Dil Modelleri (LLM) arasındaki köprüyü kurmaktı.

Yapay zekanın internetteki genel bilgileriyle cevap vermesi yerine, **RAG (Retrieval-Augmented Generation)** mimarisini entegre ettim. Gemini AI, sadece benim MySQL veritabanımdaki filmleri okuyarak kişiselleştirilmiş ve bağlama uygun (context-aware) tavsiyeler veren akıllı bir asistan olarak görev yapıyor.

## 🏗️ Mimari Kararlar ve Çözülen Zorluklar
* **Akıllı Veri Çekimi (TMDb API):** Gelen ham veriyi doğrudan kaydetmek yerine, sayısal tür (genre) ID'lerini ayrı bir sözlük uç noktasından çekip metne çeviren bir ayrıştırma (parsing) mekanizması kurdum. Böylece yapay zeka temiz verilerle analiz yapabiliyor.
* **Veritabanı Güvenliği:** Veri senkronizasyonu sırasında tehlikeli silme (drop) işlemleri yerine, canlı ortamlara uygun katı bir `check-then-insert` (`existsByTitle`) mantığı kurarak veritabanını koruma altına aldım.
* **"Fazla Gayretli Güvenlik Görevlisi" (Hata Yönetimi):** Eksik parametreleri yakalamak için global bir `@RestControllerAdvice` inşa ettim. Ancak bu durum Swagger UI'ın iç mekanizmasını çökerttiğinde, kalkanı sadece kendi API uçlarımla sınırlandırarak ve `@Hidden` etiketiyle Swagger'dan gizleyerek framework'ün bozulmasını engelledim.
* **Canlı Ortama Hazırlık (DevOps & CI/CD):** Uygulamanın anlık sağlık durumunu (kalp atışını) izleyebilmek için **Spring Boot Actuator** entegre ettim. Kod kalitesini ve derlenebilirliğini garanti altına almak adına **GitHub Actions** ile otomatik bir CI/CD boru hattı (pipeline) kurdum. Bu süreçte sanal Linux sunucularındaki karakter kodlaması (UTF-8) uyuşmazlıkları ve veritabanı bağımlı testlerin yapılandırılması gibi gerçek dünya operasyonel zorluklarını çözdüm.
* **Nostaljik UX/UI:** Frontend tarafında standart karanlık temalardan uzaklaşıp, Thymeleaf ve Bootstrap 5 kullanarak **"Frutiger Aero"** estetiğinde cam efektli (glassmorphism) bir tasarım oluşturdum. Asenkron çalışan Fetch API ve kullanıcı dostu kısayollar (Enter ile arama) ekledim.

## ⚙️ Nasıl Çalıştırılır?
1. Depoyu klonlayın: `git clone https://github.com/KULLANICI_ADIN/movie-app.git`
2. MySQL üzerinde `mcp_movie_db` adında bir veritabanı oluşturun.
3. Çevresel Değişkenlerinizi ayarlayın:
   * `GEMINI_API_KEY` = Sizin Google Gemini API anahtarınız
   * `TMDB_API_KEY` = Sizin TMDb API anahtarınız
4. Projeyi IDE üzerinden veya Maven ile çalıştırın: `mvn spring-boot:run`
5. Tarayıcınızda şu adrese gidin: `http://localhost:8080`
6. (Opsiyonel) Sistem sağlık durumunu kontrol edin: `http://localhost:8080/actuator/health`
