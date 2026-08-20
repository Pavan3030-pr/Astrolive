import { useState, useEffect } from 'react';
import { astrologerApi, type Astrologer } from '../api/astrologers';

export default function AstrologersPage() {
  const [astrologers, setAstrologers] = useState<Astrologer[]>([]);
  const [loading, setLoading] = useState(true);
  const [selected, setSelected] = useState<Astrologer | null>(null);
  const [booking, setBooking] = useState(false);
  const [booked, setBooked] = useState(false);
  const [filters, setFilters] = useState({ expertise: '', language: '' });

  useEffect(() => { loadAstrologers(); }, []);

  const loadAstrologers = async () => {
    setLoading(true);
    try {
      if (filters.expertise || filters.language) {
        const res = await astrologerApi.search(filters);
        setAstrologers(res.data);
      } else {
        const res = await astrologerApi.getRecommended();
        setAstrologers(res.data);
      }
    } catch (err) { console.error(err); }
    finally { setLoading(false); }
  };

  const handleBook = async (astrologer: Astrologer) => {
    setBooking(true);
    try {
      await astrologerApi.book({
        astrologerId: astrologer.id,
        concern: 'General consultation',
        consultationType: 'CHAT',
        scheduledTime: new Date(Date.now() + 86400000).toISOString(),
      });
      setBooked(true);
      setTimeout(() => { setBooked(false); setSelected(null); }, 2000);
    } catch (err) { console.error(err); }
    finally { setBooking(false); }
  };

  return (
    <div className="space-y-8 animate-fade-in">
      <div>
        <h1 className="text-3xl font-bold text-cream-50">Astrologer Marketplace</h1>
        <p className="text-cream-200/50 mt-1">Connect with verified cosmic guides</p>
      </div>

      {/* Filters */}
      <div className="flex flex-wrap gap-3">
        <select value={filters.expertise} onChange={(e) => setFilters({ ...filters, expertise: e.target.value })}
          className="bg-cosmic-700/50 border border-cosmic-600/30 rounded-xl px-4 py-2 text-cream-50 text-sm focus:outline-none focus:border-gold-400/50">
          <option value="">All Expertise</option>
          <option value="Vedic">Vedic Astrology</option>
          <option value="Western">Western Astrology</option>
          <option value="Relationship">Relationship</option>
          <option value="Financial">Financial</option>
          <option value="Career">Career</option>
        </select>
        <select value={filters.language} onChange={(e) => setFilters({ ...filters, language: e.target.value })}
          className="bg-cosmic-700/50 border border-cosmic-600/30 rounded-xl px-4 py-2 text-cream-50 text-sm focus:outline-none focus:border-gold-400/50">
          <option value="">All Languages</option>
          <option value="Hindi">Hindi</option>
          <option value="English">English</option>
          <option value="Tamil">Tamil</option>
          <option value="Telugu">Telugu</option>
          <option value="Kannada">Kannada</option>
          <option value="Malayalam">Malayalam</option>
        </select>
        <button onClick={loadAstrologers}
          className="bg-gold-500/10 text-gold-400 px-4 py-2 rounded-xl text-sm hover:bg-gold-500/20 transition-all">
          Search
        </button>
      </div>

      {loading ? (
        <div className="text-center py-12 text-gold-400">Loading astrologers...</div>
      ) : (
        <div className="grid md:grid-cols-2 gap-6">
          {astrologers.map((a) => (
            <div key={a.id}
              className="bg-cosmic-800/40 border border-cosmic-600/20 rounded-2xl p-6 hover:border-gold-400/15 transition-all cursor-pointer"
              onClick={() => setSelected(a)}>
              <div className="flex items-start gap-4">
                <div className="w-14 h-14 bg-gold-400/10 rounded-full flex items-center justify-center flex-shrink-0">
                  <span className="text-2xl">🔮</span>
                </div>
                <div className="flex-1 min-w-0">
                  <div className="flex items-center gap-2">
                    <h3 className="font-semibold text-cream-50">{a.name}</h3>
                    {a.verified && <span className="text-gold-400 text-xs">✓ Verified</span>}
                  </div>
                  <p className="text-cream-200/40 text-xs mt-0.5">{a.expertise}</p>
                  <div className="flex items-center gap-3 mt-2 text-xs text-cream-200/50">
                    <span>⭐ {a.rating}</span>
                    <span>📅 {a.experienceYears}yr</span>
                    <span>💬 {a.totalSessions} sessions</span>
                  </div>
                </div>
                <div className="text-right flex-shrink-0">
                  <div className="text-gold-400 font-semibold">₹{a.pricePerSession}</div>
                  <div className="text-cream-200/30 text-xs">/session</div>
                </div>
              </div>

              {/* Languages */}
              <div className="flex flex-wrap gap-1.5 mt-3">
                {a.languages.map((l) => (
                  <span key={l} className="px-2 py-0.5 bg-cosmic-700/50 rounded-md text-cream-200/40 text-xs">{l}</span>
                ))}
              </div>

              {/* Recommendation */}
              {a.recommendedReasons && a.recommendedReasons.length > 0 && (
                <div className="mt-3 p-3 bg-gold-400/5 rounded-lg border border-gold-400/10">
                  <p className="text-gold-400/70 text-xs font-medium">Why recommended for you:</p>
                  {a.recommendedReasons.map((r, i) => (
                    <p key={i} className="text-cream-200/50 text-xs mt-1">• {r}</p>
                  ))}
                </div>
              )}

              <div className="flex items-center justify-between mt-3 pt-3 border-t border-cosmic-600/20">
                <span className="text-cream-200/30 text-xs">{a.availability}</span>
                <button className="px-4 py-1.5 bg-gold-500/10 text-gold-400 rounded-lg text-xs hover:bg-gold-500/20 transition-all"
                  onClick={(e) => { e.stopPropagation(); setSelected(a); }}>
                  Book Session
                </button>
              </div>
            </div>
          ))}
        </div>
      )}

      {/* Booking Modal */}
      {selected && (
        <div className="fixed inset-0 bg-black/60 backdrop-blur-sm z-50 flex items-center justify-center p-4" onClick={() => setSelected(null)}>
          <div className="bg-cosmic-800 border border-cosmic-600/30 rounded-2xl p-8 w-full max-w-md animate-fade-in" onClick={(e) => e.stopPropagation()}>
            {booked ? (
              <div className="text-center py-8">
                <div className="text-4xl mb-4">✨</div>
                <h3 className="text-xl font-semibold text-cream-50 mb-2">Booking Confirmed!</h3>
                <p className="text-cream-200/50 text-sm">Your consultation with {selected.name} has been booked.</p>
                <p className="text-gold-400/50 text-xs mt-2">⚠️ Simulated transaction — demo/prototype only</p>
              </div>
            ) : (
              <>
                <h3 className="text-xl font-semibold text-cream-50 mb-4">Book {selected.name}</h3>
                <div className="space-y-3 mb-6">
                  <div className="flex justify-between text-sm">
                    <span className="text-cream-200/50">Session Type</span>
                    <span className="text-cream-50">Chat (15 min)</span>
                  </div>
                  <div className="flex justify-between text-sm">
                    <span className="text-cream-200/50">Price</span>
                    <span className="text-gold-400 font-semibold">₹{selected.pricePerSession}</span>
                  </div>
                  <div className="flex justify-between text-sm">
                    <span className="text-cream-200/50">When</span>
                    <span className="text-cream-50">Tomorrow, 10:00 AM</span>
                  </div>
                </div>
                <p className="text-gold-400/50 text-xs mb-4">⚠️ Payment simulated for prototype demo</p>
                <div className="flex gap-3">
                  <button onClick={() => setSelected(null)}
                    className="flex-1 border border-cosmic-600/30 text-cream-200/60 py-3 rounded-xl hover:border-gold-400/30 transition-all">
                    Cancel
                  </button>
                  <button onClick={() => handleBook(selected)} disabled={booking}
                    className="flex-1 bg-gold-500 text-cosmic-900 py-3 rounded-xl font-semibold hover:bg-gold-400 transition-all disabled:opacity-50">
                    {booking ? 'Booking...' : 'Confirm & Pay'}
                  </button>
                </div>
              </>
            )}
          </div>
        </div>
      )}
    </div>
  );
}
