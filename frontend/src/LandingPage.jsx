import { useNavigate } from 'react-router-dom'
import './LandingPage.css'

function LandingPage() {
  const navigate = useNavigate()

  const handleBookTrain = () => {
    navigate('/booking')
  }

  return (
    <div className="landing-page">
      {/* Global Noise Texture */}
      <div className="noise-overlay"></div>

      {/* Header */}
      <header className="header">
        <div className="header-container">
          <div className="logo">
            <span className="logo-text">RAILWAY<span className="logo-dot">.</span></span>
          </div>
          <nav className="nav-links">
            <a href="#features">Experience</a>
            <a href="#services">The Fleet</a>
            <a href="#contact">Contact</a>
          </nav>
          <button className="header-cta" onClick={handleBookTrain}>
            Book Journey
          </button>
        </div>
      </header>

      {/* Video Hero Section */}
      <section className="video-hero">
        <video
          className="hero-video"
          autoPlay
          loop
          muted
          playsInline
        >
          <source src="/assests/Just_make_this_1080p_202601122235.mp4" type="video/mp4" />
        </video>
        <div className="hero-overlay"></div>

        <div className="hero-content-wrapper">
          <div className="hero-content">
            <div className="hero-label">EST. 2026 — PREMIUM TRAVEL</div>
            <h1 className="hero-title">
              Redefining <br />
              <span className="italic-text">Rail Travel</span>
            </h1>
            <p className="hero-description">
              Journey across India in unparalleled comfort.
              Real-time booking, instant confirmation, zero hassle.
            </p>
            <div className="hero-actions">
              <button className="hero-cta-primary" onClick={handleBookTrain}>
                Start Your Journey
              </button>
              <button className="hero-cta-minimal">
                View Destinations
              </button>
            </div>
          </div>
        </div>

        <div className="scroll-indicator">
          <div className="scroll-line"></div>
          <span>SCROLL</span>
        </div>
      </section>

      {/* Features Section */}
      <section className="features-section" id="features">
        <div className="section-container">
          <span className="section-badge">Why Choose Us</span>
          <h2 className="section-title">Premium Travel Experience</h2>
          <p className="section-subtitle">
            Everything you need for a seamless journey
          </p>

          <div className="features-grid">
            <div className="feature-card">
              <div className="feature-icon">⚡</div>
              <h3>Lightning Fast</h3>
              <p>Book tickets in under 2 minutes with our streamlined process</p>
            </div>
            <div className="feature-card">
              <div className="feature-icon">🔒</div>
              <h3>Secure Payments</h3>
              <p>Bank-grade encryption keeps your information protected</p>
            </div>
            <div className="feature-card">
              <div className="feature-icon">📱</div>
              <h3>Real-time Updates</h3>
              <p>Instant notifications about train status and availability</p>
            </div>
            <div className="feature-card">
              <div className="feature-icon">🎧</div>
              <h3>24/7 Support</h3>
              <p>Our team is always ready to assist you anytime</p>
            </div>
          </div>
        </div>
      </section>

      {/* Services Section */}
      <section className="services-section" id="services">
        <div className="section-container">
          <span className="section-badge">Our Fleet</span>
          <h2 className="section-title">Premium Train Services</h2>
          <p className="section-subtitle">
            Choose from India's finest train categories
          </p>

          <div className="services-grid">
            <div className="service-card rajdhani">
              <div className="service-icon">🚄</div>
              <h3>Rajdhani Express</h3>
              <p>Premium long-distance express connecting major cities</p>
              <span className="service-tag">Premium</span>
            </div>
            <div className="service-card shatabdi">
              <div className="service-icon">🚅</div>
              <h3>Shatabdi Express</h3>
              <p>High-speed day-travel with superior comfort</p>
              <span className="service-tag">High-Speed</span>
            </div>
            <div className="service-card vande">
              <div className="service-icon">�</div>
              <h3>Vande Bharat</h3>
              <p>India's fastest semi-high-speed train</p>
              <span className="service-tag">Fastest</span>
            </div>
          </div>

          <div className="services-cta">
            <h3>Ready to travel?</h3>
            <p>Book your next journey with just a few clicks</p>
            <button className="primary-btn" onClick={handleBookTrain}>
              🚀 Start Booking
            </button>
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="footer" id="contact">
        <div className="footer-container">
          <div className="footer-brand">
            <h3>🚂 RailWay Express</h3>
            <p>Your trusted partner for train bookings across India</p>
          </div>
          <div className="footer-links">
            <div className="footer-column">
              <h4>Quick Links</h4>
              <a href="#features">Features</a>
              <a href="#services">Services</a>
              <a href="#" onClick={handleBookTrain}>Book Tickets</a>
            </div>
            <div className="footer-column">
              <h4>Support</h4>
              <a href="#">Help Center</a>
              <a href="#">Contact Us</a>
              <a href="#">FAQs</a>
            </div>
            <div className="footer-column">
              <h4>Legal</h4>
              <a href="#">Privacy Policy</a>
              <a href="#">Terms of Service</a>
            </div>
          </div>
        </div>
        <div className="footer-bottom">
          <p>© 2026 RailWay Express. Built with React + Spring Boot</p>
        </div>
      </footer>
    </div>
  )
}

export default LandingPage
