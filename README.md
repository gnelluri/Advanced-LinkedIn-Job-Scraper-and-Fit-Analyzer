# Advanced-LinkedIn-Job-Scraper-and-Fit-Analyzer
This sophisticated Java-based application scrapes job listings from LinkedIn, analyzes their fit based on user-defined criteria, and provides actionable insights.

Leveraging Playwright for efficient, stealthy web scraping, it extracts job details (title, company, location, description) from LinkedIn’s dynamic job pages. Instead of basic keyword matching, it employs BERT-based NLP (via Hugging Face Transformers) to compute semantic similarity between job descriptions and a user-specified set of positive keywords (e.g., "Java," "Spring," "SQL"), while penalizing blacklist terms (e.g., "senior," "manager") for a nuanced match percentage.

Job data is stored persistently in an SQLite database for historical analysis and exported to an Excel file named with the current date (e.g., Jobs_2025-03-20.xlsx), featuring a widened, text-wrapped "Description" column and a "Match Percentage" column. A JFreeChart bar graph visualizes match scores, offering an intuitive overview of job fit. The system runs automatically via a ScheduledExecutorService, scraping daily and sending email notifications (using SendGrid) for jobs exceeding a 70% match threshold. This end-to-end solution combines cutting-edge scraping, AI-driven analysis, and user-friendly outputs to streamline job hunting.

# Key Features:

Scraping: Playwright-powered, reliable job data extraction.


Analysis: BERT NLP for semantic job fit scoring.


Storage: SQLite for persistent data management.


Output: Excel export and JFreeChart visualization.


Automation: Daily scheduled runs with email alerts.


# Technologies: 
Java, Playwright, TensorFlow/BERT (via Python interop), SQLite, JFreeChart, SendGrid, Apache POI.
