# 🚂 Simple IRCTC Train Booking System

A minimal train ticket booking app with Java backend and React frontend.

## Project Structure

```
├── backend/          # Spring Boot API (Java)
│   └── src/main/resources/data/   # JSON data files
└── frontend/         # React + Vite
```

## Quick Start

### 1. Start Backend (Terminal 1)
```bash
cd backend
./gradlew bootRun
```
Backend runs on: http://localhost:8080

### 2. Start Frontend (Terminal 2)
```bash
cd frontend
npm run dev
```
Frontend runs on: http://localhost:5173

## API Endpoints

| Endpoint | Description |
|----------|-------------|
| GET /api/trains | Get all trains |
| GET /api/stations | Get all stations |
| GET /api/trains/search?from=NDLS&to=BCT | Search trains |

## Tech Stack

- **Backend**: Java 21, Spring Boot 3.4.1, Gradle
- **Frontend**: React, Vite 7
- **Data**: JSON files (no database)
