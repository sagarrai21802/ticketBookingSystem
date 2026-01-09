import { useState, useEffect } from 'react'
import './App.css'

const API_URL = 'https://irctc-backend-qnll.onrender.com/api'

function App() {
  const [trains, setTrains] = useState([])
  const [stations, setStations] = useState([])
  const [from, setFrom] = useState('')
  const [to, setTo] = useState('')
  const [loading, setLoading] = useState(false)
  const [activeTab, setActiveTab] = useState('trains')

  // Booking flow state
  const [showBooking, setShowBooking] = useState(false)
  const [selectedTrain, setSelectedTrain] = useState(null)
  const [bookingStep, setBookingStep] = useState(1) // 1: Register, 2: Payment, 3: Confirmed
  const [passengerInfo, setPassengerInfo] = useState({
    name: '',
    email: '',
    phone: '',
    age: '',
    gender: 'Male'
  })
  const [paymentInfo, setPaymentInfo] = useState({
    cardNumber: '',
    expiry: '',
    cvv: ''
  })
  const [ticketPNR, setTicketPNR] = useState('')

  useEffect(() => {
    fetchTrains()
    fetchStations()
  }, [])

  const fetchTrains = async () => {
    setLoading(true)
    try {
      const res = await fetch(`${API_URL}/trains`)
      const data = await res.json()
      setTrains(data)
    } catch (err) {
      console.error('Failed to fetch trains:', err)
    }
    setLoading(false)
  }

  const fetchStations = async () => {
    try {
      const res = await fetch(`${API_URL}/stations`)
      const data = await res.json()
      setStations(data)
    } catch (err) {
      console.error('Failed to fetch stations:', err)
    }
  }

  const searchTrains = async () => {
    if (!from || !to) return
    setLoading(true)
    try {
      const res = await fetch(`${API_URL}/trains/search?from=${from}&to=${to}`)
      const data = await res.json()
      setTrains(data)
    } catch (err) {
      console.error('Search failed:', err)
    }
    setLoading(false)
  }

  const handleBookNow = (train) => {
    setSelectedTrain(train)
    setShowBooking(true)
    setBookingStep(1)
    setPassengerInfo({ name: '', email: '', phone: '', age: '', gender: 'Male' })
    setPaymentInfo({ cardNumber: '', expiry: '', cvv: '' })
  }

  const handleRegistrationSubmit = (e) => {
    e.preventDefault()
    setBookingStep(2)
  }

  const handlePaymentSubmit = (e) => {
    e.preventDefault()
    // Generate random PNR
    const pnr = 'PNR' + Math.random().toString(36).substring(2, 10).toUpperCase()
    setTicketPNR(pnr)
    setBookingStep(3)
  }

  const closeBooking = () => {
    setShowBooking(false)
    setSelectedTrain(null)
    setBookingStep(1)
  }

  const getStationName = (code) => {
    const station = stations.find(s => s.code === code)
    return station ? station.name : code
  }

  return (
    <div className="app">
      <header className="header">
        <h1>🚂 IRCTC Train Booking</h1>
        <p>Book train tickets online</p>
      </header>

      <div className="search-box">
        <select value={from} onChange={(e) => setFrom(e.target.value)}>
          <option value="">From Station</option>
          {stations.map(s => (
            <option key={s.code} value={s.code}>{s.name} ({s.code})</option>
          ))}
        </select>
        <select value={to} onChange={(e) => setTo(e.target.value)}>
          <option value="">To Station</option>
          {stations.map(s => (
            <option key={s.code} value={s.code}>{s.name} ({s.code})</option>
          ))}
        </select>
        <button onClick={searchTrains} disabled={!from || !to}>
          🔍 Search Trains
        </button>
        <button onClick={fetchTrains} className="secondary">
          Show All
        </button>
      </div>

      <div className="tabs">
        <button
          className={activeTab === 'trains' ? 'active' : ''}
          onClick={() => setActiveTab('trains')}
        >
          🚂 Trains ({trains.length})
        </button>
        <button
          className={activeTab === 'stations' ? 'active' : ''}
          onClick={() => setActiveTab('stations')}
        >
          🏛️ Stations ({stations.length})
        </button>
      </div>

      <main className="content">
        {loading && <div className="loading">Loading...</div>}

        {activeTab === 'trains' && !loading && (
          <div className="train-list">
            {trains.length === 0 ? (
              <div className="empty">No trains found</div>
            ) : (
              trains.map(train => (
                <div key={train.number} className="train-card">
                  <div className="train-header">
                    <h3>{train.name}</h3>
                    <span className="train-number">#{train.number}</span>
                    <span className={`badge ${train.type.toLowerCase()}`}>{train.type}</span>
                  </div>
                  <div className="train-route">
                    <span className="station">{train.source}</span>
                    <span className="arrow">→</span>
                    <span className="station">{train.destination}</span>
                  </div>
                  <div className="train-details">
                    <span>🕐 {train.departureTime} - {train.arrivalTime}</span>
                    <span>💺 {train.seatsAvailable} seats</span>
                    <span className="fare">₹{train.fare}</span>
                  </div>
                  <div className="running-days">
                    {train.runningDays?.join(', ')}
                  </div>
                  <button className="book-btn" onClick={() => handleBookNow(train)}>
                    Book Now
                  </button>
                </div>
              ))
            )}
          </div>
        )}

        {activeTab === 'stations' && !loading && (
          <div className="station-grid">
            {stations.map(station => (
              <div key={station.code} className="station-card">
                <h4>{station.code}</h4>
                <p>{station.name}</p>
                <small>{station.city}, {station.state}</small>
              </div>
            ))}
          </div>
        )}
      </main>

      {/* Booking Modal */}
      {showBooking && selectedTrain && (
        <div className="modal-overlay" onClick={closeBooking}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <button className="modal-close" onClick={closeBooking}>×</button>

            {/* Progress Steps */}
            <div className="booking-steps">
              <div className={`step ${bookingStep >= 1 ? 'active' : ''} ${bookingStep > 1 ? 'completed' : ''}`}>
                <span className="step-number">1</span>
                <span className="step-label">Passenger</span>
              </div>
              <div className="step-line"></div>
              <div className={`step ${bookingStep >= 2 ? 'active' : ''} ${bookingStep > 2 ? 'completed' : ''}`}>
                <span className="step-number">2</span>
                <span className="step-label">Payment</span>
              </div>
              <div className="step-line"></div>
              <div className={`step ${bookingStep >= 3 ? 'active' : ''}`}>
                <span className="step-number">3</span>
                <span className="step-label">Confirmed</span>
              </div>
            </div>

            {/* Train Info Summary */}
            <div className="booking-train-info">
              <h3>{selectedTrain.name}</h3>
              <p>{getStationName(selectedTrain.source)} → {getStationName(selectedTrain.destination)}</p>
              <p className="fare-display">Fare: ₹{selectedTrain.fare}</p>
            </div>

            {/* Step 1: Passenger Registration */}
            {bookingStep === 1 && (
              <form className="booking-form" onSubmit={handleRegistrationSubmit}>
                <h4>👤 Passenger Details</h4>
                <input
                  type="text"
                  placeholder="Full Name"
                  value={passengerInfo.name}
                  onChange={(e) => setPassengerInfo({ ...passengerInfo, name: e.target.value })}
                  required
                />
                <input
                  type="email"
                  placeholder="Email Address"
                  value={passengerInfo.email}
                  onChange={(e) => setPassengerInfo({ ...passengerInfo, email: e.target.value })}
                  required
                />
                <input
                  type="tel"
                  placeholder="Phone Number"
                  value={passengerInfo.phone}
                  onChange={(e) => setPassengerInfo({ ...passengerInfo, phone: e.target.value })}
                  required
                />
                <div className="form-row">
                  <input
                    type="number"
                    placeholder="Age"
                    min="1"
                    max="120"
                    value={passengerInfo.age}
                    onChange={(e) => setPassengerInfo({ ...passengerInfo, age: e.target.value })}
                    required
                  />
                  <select
                    value={passengerInfo.gender}
                    onChange={(e) => setPassengerInfo({ ...passengerInfo, gender: e.target.value })}
                  >
                    <option value="Male">Male</option>
                    <option value="Female">Female</option>
                    <option value="Other">Other</option>
                  </select>
                </div>
                <button type="submit" className="btn-primary">
                  Continue to Payment →
                </button>
              </form>
            )}

            {/* Step 2: Payment */}
            {bookingStep === 2 && (
              <form className="booking-form" onSubmit={handlePaymentSubmit}>
                <h4>💳 Payment Details</h4>
                <input
                  type="text"
                  placeholder="Card Number"
                  maxLength="16"
                  value={paymentInfo.cardNumber}
                  onChange={(e) => setPaymentInfo({ ...paymentInfo, cardNumber: e.target.value })}
                  required
                />
                <div className="form-row">
                  <input
                    type="text"
                    placeholder="MM/YY"
                    maxLength="5"
                    value={paymentInfo.expiry}
                    onChange={(e) => setPaymentInfo({ ...paymentInfo, expiry: e.target.value })}
                    required
                  />
                  <input
                    type="password"
                    placeholder="CVV"
                    maxLength="3"
                    value={paymentInfo.cvv}
                    onChange={(e) => setPaymentInfo({ ...paymentInfo, cvv: e.target.value })}
                    required
                  />
                </div>
                <div className="payment-summary">
                  <span>Total Amount:</span>
                  <span className="total-fare">₹{selectedTrain.fare}</span>
                </div>
                <button type="submit" className="btn-success">
                  Pay ₹{selectedTrain.fare} →
                </button>
                <button type="button" className="btn-secondary" onClick={() => setBookingStep(1)}>
                  ← Back
                </button>
              </form>
            )}

            {/* Step 3: Confirmation */}
            {bookingStep === 3 && (
              <div className="booking-confirmation">
                <div className="success-icon">✅</div>
                <h4>Booking Confirmed!</h4>
                <div className="ticket-card">
                  <div className="pnr-number">PNR: {ticketPNR}</div>
                  <div className="ticket-details">
                    <p><strong>Train:</strong> {selectedTrain.name} ({selectedTrain.number})</p>
                    <p><strong>Route:</strong> {getStationName(selectedTrain.source)} → {getStationName(selectedTrain.destination)}</p>
                    <p><strong>Passenger:</strong> {passengerInfo.name}</p>
                    <p><strong>Time:</strong> {selectedTrain.departureTime} - {selectedTrain.arrivalTime}</p>
                    <p><strong>Fare:</strong> ₹{selectedTrain.fare}</p>
                  </div>
                  <div className="ticket-status">
                    <span className="status-confirmed">CONFIRMED</span>
                  </div>
                </div>
                <button className="btn-primary" onClick={closeBooking}>
                  Done
                </button>
              </div>
            )}
          </div>
        </div>
      )}

      <footer className="footer">
        <p>Simple IRCTC Clone | Backend: Java Spring Boot | Frontend: React + Vite</p>
      </footer>
    </div>
  )
}

export default App
