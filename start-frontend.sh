#!/bin/bash
# Start frontend on port 3000
# Usage: ./start-frontend.sh

PORT=3000

echo "🚀 Starting Frontend Server on port $PORT"
echo "📍 Open http://localhost:$PORT in your browser"
echo ""
echo "Make sure backend is running on port 8080: ./gradlew bootRun"
echo ""
echo "Press Ctrl+C to stop"
echo ""

# Check if Python 3 is available
if command -v python3 &> /dev/null; then
    cd frontend
    python3 -m http.server $PORT
elif command -v python &> /dev/null; then
    cd frontend
    python -m http.server $PORT
else
    echo "❌ Python not found. Please install Python or use another HTTP server."
    exit 1
fi
