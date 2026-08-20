import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Landing() {
  const navigate = useNavigate();
  const { user } = useAuth();

  return (
    <div className="min-h-screen bg-cosmic-900 text-cream-50 overflow-hidden">
      {/* Navigation */}
      <nav className="fixed top-0 w-full z-50 bg-cosmic-900/80 backdrop-blur-md border-b border-cosmic-600/30">
        <div className="max-w-7xl mx-auto px-6 h-16 flex items-center justify-between">
          <div className="flex items-center gap-2">
            <span className="text-gold-400 text-2xl">✦</span>
            <span className="font-semibold text-lg tracking-tight">AstroLoop</span>
          </div>
          <div className="flex items-center gap-4">
            {user ? (
              <button onClick={() => navigate('/dashboard')} className="bg-gold-500 text-cosmic-900 px-5 py-2 rounded-full text-sm font-semibold hover:bg-gold-400 transition-all">
                Dashboard
              </button>
            ) : (
              <>
                <button onClick={() => navigate('/login')} className="text-cream-200/70 hover:text-cream-50 text-sm transition-colors">Sign In</button>
                <button onClick={() => navigate('/register')} className="bg-gold-500 text-cosmic-900 px-5 py-2 rounded-full text-sm font-semibold hover:bg-gold-400 transition-all">
                  Get Started
                </button>
              </>
            )}
          </div>
        </div>
      </nav>

      {/* Hero */}
      <section className="pt-32 pb-20 px-6 relative">
        <div className="absolute inset-0 overflow-hidden">
          <div className="absolute top-20 left-1/4 w-96 h-96 bg-gold-400/5 rounded-full blur-3xl"></div>
          <div className="absolute top-40 right-1/4 w-72 h-72 bg-gold-500/5 rounded-full blur-3xl"></div>
        </div>
        <div className="max-w-4xl mx-auto text-center relative z-10">
          <p className="text-gold-400 text-sm tracking-[0.3em] uppercase mb-6 font-medium">Your Personalized Cosmic Platform</p>
          <h1 className="text-5xl md:text-7xl font-bold leading-[1.1] mb-6 tracking-tight">
            Your astrology.<br />
            <span className="text-gold-400">Your daily ritual.</span><br />
            Your circle.
          </h1>
          <p className="text-cream-200/60 text-lg md:text-xl max-w-2xl mx-auto mb-10 leading-relaxed">
            Personalized cosmic insights that become more meaningful when you share them. Discover your daily energy, generate beautiful Cosmic Cards, and grow together.
          </p>
          <div className="flex flex-col sm:flex-row gap-4 justify-center">
            <button onClick={() => navigate('/register')} className="bg-gold-500 text-cosmic-900 px-8 py-4 rounded-full text-base font-semibold hover:bg-gold-400 transition-all animate-pulse-glow">
              Discover Your Cosmic Energy
            </button>
            <button onClick={() => navigate('/register')} className="border border-cream-200/20 text-cream-200/80 px-8 py-4 rounded-full text-base font-medium hover:border-gold-400/50 hover:text-gold-400 transition-all">
              Watch Demo
            </button>
          </div>
        </div>
      </section>

      {/* Cosmic Card Preview */}
      <section className="py-20 px-6">
        <div className="max-w-6xl mx-auto">
          <div className="bg-cosmic-800/60 border border-cosmic-600/30 rounded-2xl p-8 md:p-12">
            <div className="grid md:grid-cols-2 gap-12 items-center">
              <div>
                <h2 className="text-3xl md:text-4xl font-bold mb-4">Your Daily Cosmic Card</h2>
                <p className="text-cream-200/60 text-lg mb-6 leading-relaxed">
                  Generate a personalized, visually stunning Cosmic Card every day. Share it on WhatsApp, Instagram, or anywhere — and invite friends to discover their own cosmic energy.
                </p>
                <div className="flex flex-wrap gap-3">
                  <span className="px-3 py-1 rounded-full bg-cosmic-700/50 text-cream-200/60 text-sm">✦ Unique shareable cards</span>
                  <span className="px-3 py-1 rounded-full bg-cosmic-700/50 text-cream-200/60 text-sm">✦ Personalized insights</span>
                  <span className="px-3 py-1 rounded-full bg-cosmic-700/50 text-cream-200/60 text-sm">✦ Viral growth loop</span>
                </div>
              </div>
              <div className="bg-gradient-to-br from-cosmic-700 to-cosmic-800 rounded-2xl p-8 border border-gold-400/10 animate-pulse-glow">
                <div className="text-center">
                  <div className="w-16 h-16 bg-gold-400/10 rounded-full flex items-center justify-center mx-auto mb-4">
                    <span className="text-3xl">🌟</span>
                  </div>
                  <h3 className="text-xl font-semibold text-gold-400 mb-2">Priya's Cosmic Energy</h3>
                  <p className="text-cream-200/50 text-sm mb-4">August 20, 2026</p>
                  <div className="text-3xl font-bold text-gold-400 mb-2">87</div>
                  <p className="text-cream-200/40 text-xs mb-4">Energy Score</p>
                  <p className="text-cream-200/60 text-sm italic">"The stars align to bring you clarity and purpose today."</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* How it Works - Growth Loop */}
      <section className="py-20 px-6">
        <div className="max-w-6xl mx-auto">
          <p className="text-gold-400 text-sm tracking-[0.2em] uppercase mb-3 text-center">The Viral Growth Loop</p>
          <h2 className="text-3xl md:text-4xl font-bold mb-12 text-center">How AstroLoop Grows</h2>
          <div className="grid md:grid-cols-4 gap-6">
            {[
              { step: '01', title: 'Personalized Value', desc: 'Daily cosmic insights based on your unique astrology profile', icon: '🔮' },
              { step: '02', title: 'Cosmic Card', desc: 'Generate a beautiful, shareable personalized card', icon: '🌟' },
              { step: '03', title: 'Share', desc: 'Share on WhatsApp, Instagram, or any social platform', icon: '🔗' },
              { step: '04', title: 'New User', desc: 'Friend signs up, creates profile, generates their own card', icon: '🚀' },
            ].map((item) => (
              <div key={item.step} className="bg-cosmic-800/40 border border-cosmic-600/20 rounded-xl p-6 hover:border-gold-400/20 transition-all group">
                <span className="text-gold-400/40 text-sm font-mono">{item.step}</span>
                <div className="text-3xl my-4 group-hover:scale-110 transition-transform">{item.icon}</div>
                <h3 className="font-semibold text-lg mb-2">{item.title}</h3>
                <p className="text-cream-200/50 text-sm leading-relaxed">{item.desc}</p>
              </div>
            ))}
          </div>
          <div className="text-center mt-8">
            <div className="inline-flex items-center gap-2 px-4 py-2 rounded-full bg-gold-400/10 text-gold-400 text-sm">
              <span>↻</span> The loop repeats — each user becomes an acquisition channel
            </div>
          </div>
        </div>
      </section>

      {/* Features */}
      <section className="py-20 px-6 bg-cosmic-800/30">
        <div className="max-w-6xl mx-auto">
          <p className="text-gold-400 text-sm tracking-[0.2em] uppercase mb-3 text-center">Core Features</p>
          <h2 className="text-3xl md:text-4xl font-bold mb-12 text-center">Built for Retention & Revenue</h2>
          <div className="grid md:grid-cols-3 gap-8">
            {[
              { title: 'Cosmic Match', desc: 'Discover compatibility with friends and partners through cosmic energy analysis.', icon: '💫', preview: '87% Match' },
              { title: 'Astrologer Marketplace', desc: 'Connect with verified astrologers for personalized consultations.', icon: '🔮', preview: '6+ Experts' },
              { title: 'Premium Insights', desc: 'Unlock deeper cosmic reports, exclusive challenges, and premium features.', icon: '👑', preview: '4 Products' },
              { title: 'Daily Streaks', desc: 'Build your cosmic habit. Check in daily for streaks and achievements.', icon: '🔥', preview: '7-Day Streak' },
              { title: 'Challenges', desc: 'Weekly challenges keep you engaged and earn cosmic rewards.', icon: '🏆', preview: '5 Active' },
              { title: 'Analytics', desc: 'Track your cosmic journey with detailed growth and engagement metrics.', icon: '📊', preview: 'Real-time' },
            ].map((f) => (
              <div key={f.title} className="bg-cosmic-900/50 border border-cosmic-600/20 rounded-xl p-6 hover:border-gold-400/15 transition-all">
                <div className="flex items-start justify-between mb-4">
                  <span className="text-3xl">{f.icon}</span>
                  <span className="px-2 py-1 rounded-md bg-gold-400/10 text-gold-400 text-xs font-medium">{f.preview}</span>
                </div>
                <h3 className="font-semibold text-lg mb-2">{f.title}</h3>
                <p className="text-cream-200/50 text-sm leading-relaxed">{f.desc}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Social Proof */}
      <section className="py-20 px-6">
        <div className="max-w-4xl mx-auto text-center">
          <div className="grid grid-cols-3 gap-8 mb-12">
            {[
              { value: '10x', label: 'Organic Growth' },
              { value: '67%', label: 'Share Rate' },
              { value: '3.2x', label: 'Viral Coefficient' },
            ].map((stat) => (
              <div key={stat.label}>
                <div className="text-3xl md:text-4xl font-bold text-gold-400 mb-2">{stat.value}</div>
                <div className="text-cream-200/50 text-sm">{stat.label}</div>
              </div>
            ))}
          </div>
          <p className="text-cream-200/40 text-sm italic">
            "AstroLoop turns astrology from an occasional consultation into a daily ritual that naturally brings users back and brings new users in."
          </p>
        </div>
      </section>

      {/* CTA */}
      <section className="py-20 px-6">
        <div className="max-w-3xl mx-auto text-center">
          <h2 className="text-3xl md:text-4xl font-bold mb-6">Ready to discover your cosmic energy?</h2>
          <p className="text-cream-200/60 text-lg mb-8">Join thousands who have made AstroLoop their daily cosmic ritual.</p>
          <button onClick={() => navigate('/register')} className="bg-gold-500 text-cosmic-900 px-10 py-4 rounded-full text-base font-semibold hover:bg-gold-400 transition-all">
            Start Your Cosmic Journey
          </button>
        </div>
      </section>

      {/* Footer */}
      <footer className="border-t border-cosmic-600/20 py-8 px-6">
        <div className="max-w-6xl mx-auto flex flex-col md:flex-row items-center justify-between gap-4">
          <div className="flex items-center gap-2">
            <span className="text-gold-400 text-xl">✦</span>
            <span className="text-sm text-cream-200/40">AstroLoop — An AstroLive Challenge Project</span>
          </div>
          <p className="text-cream-200/30 text-xs">
            Prototype / Demo — Astrology insights are for entertainment purposes only.
          </p>
        </div>
      </footer>
    </div>
  );
}
