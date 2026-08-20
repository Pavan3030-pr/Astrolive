# AstroLoop for AstroLive

**Your astrology. Your daily ritual. Your circle.**

AstroLoop is a full-stack personalized astrology platform that transforms astrology from an occasional consultation into a daily habit with built-in viral growth. It is built as a submission for the **AstroLive Challenge**.

---

## 🎯 Challenge Objective

Build a real product solution that solves genuine business problems around:
- **Structural virality** / organic user acquisition
- **User retention** and habit formation
- **New revenue opportunities**
- **Strong product differentiation** / USP
- **Scalability and feasibility**

---

## 💡 Problem Statement

Astrology apps suffer from:
1. Low daily engagement — users check once and leave
2. No viral mechanics — no organic growth loop
3. Limited monetization — reliance on consultations alone
4. Generic experiences — no personalization or social sharing

---

## 🚀 Solution: AstroLoop

AstroLoop turns astrology into a **personalized, social, daily habit** that naturally brings users back and brings new users in.

### The Viral Growth Loop

```
Personalized Value → Cosmic Card → Share → New Visitor → Signup → Personalized Value → New Cosmic Card → Share Again
```

Every user becomes an acquisition channel.

---

## ✨ Key Features

| Feature | Description |
|---------|-------------|
| **Personalized Dashboard** | Daily cosmic brief, career/love/money insights, energy score |
| **Cosmic Card** | Beautiful, shareable personalized cosmic energy card |
| **Public Share Page** | Viral share page with signup CTA |
| **Referral System** | Full referral tracking with conversion analytics |
| **Cosmic Match** | Compatibility analysis with detailed breakdowns |
| **Astrologer Marketplace** | Browse, filter, and book verified astrologers |
| **Premium Products** | Reports, memberships, credits, premium challenges |
| **Daily Streaks** | Habit formation with streaks and achievements |
| **Challenges** | Weekly engagement challenges |
| **Analytics Dashboard** | Real-time business metrics and charts |
| **JWT Authentication** | Secure register/login with persistent sessions |
| **Mobile Responsive** | Full mobile UX with bottom navigation |

---

## 🏗️ Architecture

```
┌─────────────────┐     ┌─────────────────┐     ┌──────────────┐
│   React + Vite  │────▶│  Spring Boot    │────▶│  PostgreSQL  │
│   TypeScript    │ API │  Java 21        │ JPA │              │
│   Tailwind CSS  │◀────│  REST APIs      │◀────│              │
└─────────────────┘     └─────────────────┘     └──────────────┘
```

### Backend Architecture

```
Controller → Service → Repository → PostgreSQL
```

- DTOs for API contracts (no entity exposure)
- Global exception handling
- CORS configuration
- JWT authentication filter
- Deterministic cosmic engine for consistent daily results

### Database Schema

14 entities with proper relationships:
- User, AstrologyProfile, CosmicCard, CardView
- Referral, DailyActivity, Streak
- Challenge, UserChallenge, Achievement
- Astrologer, Consultation
- PremiumProduct, Purchase

---

## 🛠️ Technology Stack

### Backend
- **Java 21** with **Spring Boot 3.2**
- **Spring Security** + **JWT** authentication
- **Spring Data JPA** + **Hibernate**
- **H2 Database** (development) / **PostgreSQL** (production)
- **Maven** build system
- **Lombok** for boilerplate reduction

### Frontend
- **React 19** with **TypeScript**
- **Vite** build tool
- **Tailwind CSS 4** for styling
- **React Router** for navigation
- **Recharts** for analytics charts
- **Axios** for API communication

---

## 📁 Project Structure

```
astrolive/
├── astroloop-backend/
│   ├── src/main/java/com/astroloop/
│   │   ├── config/          # Security, JWT, CORS, DataInitializer
│   │   ├── controller/      # REST controllers (13 controllers)
│   │   ├── dto/             # Data Transfer Objects (18 DTOs)
│   │   ├── entity/          # JPA entities (14 entities)
│   │   ├── enums/           # Business enums (6 enums)
│   │   ├── exception/       # Global exception handler
│   │   ├── repository/      # Spring Data repositories (14 repos)
│   │   ├── service/         # Business logic (10 services)
│   │   └── util/            # CosmicEngine utility
│   └── pom.xml
├── astroloop-frontend/
│   ├── src/
│   │   ├── api/             # API client layer (11 modules)
│   │   ├── components/      # Shared components (Layout)
│   │   ├── context/         # Auth context
│   │   ├── pages/           # Page components (11 pages)
│   │   ├── App.tsx          # Router configuration
│   │   └── main.tsx         # Entry point
│   └── package.json
├── .env.example
├── .gitignore
├── README.md
├── DEMO_GUIDE.md
├── SUBMISSION_CHECKLIST.md
└── PROJECT_REPORT.md
```

---

## 🚀 Getting Started

### Prerequisites
- Java 21+ (JDK)
- Node.js 18+
- Maven 3.8+
- PostgreSQL (for production)

### Backend Setup

```bash
cd astroloop-backend

# Development mode (uses H2 in-memory database)
mvn spring-boot:run

# The backend will start at http://localhost:8080
```

### Frontend Setup

```bash
cd astroloop-frontend

# Install dependencies
npm install

# Development mode
npm run dev

# The frontend will start at http://localhost:5173
```

### Production Setup

1. Configure environment variables (see `.env.example`)
2. Set up PostgreSQL database
3. Update `application-prod.yml` with your database credentials
4. Build and run:
   ```bash
   cd astroloop-backend
   mvn clean package -DskipTests
   java -jar target/astroloop-backend-1.0.0.jar --spring.profiles.active=prod
   ```

---

## 🔑 Demo Credentials

| Email | Password | Notes |
|-------|----------|-------|
| demo@astroloop.com | demo123 | Pre-seeded with profile, streak, purchases |
| aria@example.com | demo123 | Referred by demo user |
| dev@example.com | demo123 | Referred by demo user |

---

## 📡 API Endpoints

### Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login and get JWT |

### Profile
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/profile` | Create astrology profile |
| GET | `/api/profile` | Get profile |
| PUT | `/api/profile` | Update profile |

### Dashboard
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/dashboard` | Get personalized dashboard |

### Cosmic Card
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/cosmic-card/generate` | Generate cosmic card |
| GET | `/api/cosmic-card/my-cards` | Get user's cards |
| GET | `/api/cosmic-card/share/{shareId}` | View shared card (tracked) |
| POST | `/api/cosmic-card/share/{shareId}` | Increment share count |
| GET | `/api/public/cosmic-card/{shareId}` | Public card view |

### Cosmic Match
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/cosmic-match` | Calculate compatibility |

### Astrologers
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/astrologers` | Search astrologers |
| GET | `/api/astrologers/recommended` | Get recommended |
| GET | `/api/astrologers/{id}` | Get astrologer details |

### Consultations
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/consultations` | Book consultation |
| GET | `/api/consultations` | Get user consultations |

### Premium
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/premium/products` | List products |
| POST | `/api/premium/purchase` | Purchase product |
| GET | `/api/premium/purchases` | Purchase history |

### Streak & Challenges
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/streak/check-in` | Daily check-in |
| GET | `/api/streak` | Get streak status |
| GET | `/api/challenges` | List challenges |
| POST | `/api/challenges/{id}/join` | Join challenge |
| POST | `/api/challenges/{id}/complete` | Complete challenge |

### Analytics
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/analytics` | Get analytics data |
| GET | `/api/referrals/stats` | Get referral stats |

---

## 🔒 Security

- Passwords are hashed with **BCrypt**
- JWT tokens with 24-hour expiration
- CORS configured for specific origins
- Environment variables for all secrets
- No secrets committed to repository
- `.env.example` provided for reference

---

## 📊 Business Model

| Revenue Stream | Description | ARPU Impact |
|---------------|-------------|-------------|
| Premium Cosmic Report | ₹199 per report | +₹199/LTV |
| Compatibility Report | ₹299 per report | +₹299/LTV |
| Monthly Membership | ₹299/month | +₹3,588/yr |
| Consultation Credits | ₹999 for 5 sessions | +₹999/LTV |
| Premium Challenges | ₹149 per pack | +₹149/LTV |

---

## 📈 Success Metrics

| Metric | Target |
|--------|--------|
| Viral Coefficient (K) | > 1.0 |
| D1 Retention | > 40% |
| D7 Retention | > 20% |
| Premium Conversion | > 5% |
| Share Rate | > 60% |
| Consultation Conversion | > 10% |

---

## 🗺️ Future Roadmap

1. Real payment gateway integration (Razorpay/Stripe)
2. Push notifications for daily reminders
3. AI-powered personalized insights
4. Social features (comments, reactions on cards)
5. Astrologer video calls
6. Multi-language support
7. Mobile app (React Native)
8. Real-time chat during consultations

---

## ⚠️ Known Prototype Limitations

- Astrology logic is deterministic demo/prototype — not scientifically validated
- Payments are simulated (clearly labeled as demo)
- Public cosmic card URLs use localhost (production would use real domain)
- No real-time WebSocket for consultations
- Charts show limited historical data

---

## 🤖 AI Tools Used

- **Codebuff (Buffy)** — Full autonomous code generation, architecture design, and project scaffolding
- AI-assisted with Gemini for research and documentation

---

## 📄 License

This project was built for the AstroLive Challenge. All rights reserved.

---

**Built with ✦ for the AstroLive Challenge**
