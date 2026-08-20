import { useState, useEffect } from 'react';
import { cosmicCardApi, type CosmicCard } from '../api/cosmicCard';

export default function CosmicCardPage() {
  const [card, setCard] = useState<CosmicCard | null>(null);
  const [cards, setCards] = useState<CosmicCard[]>([]);
  const [loading, setLoading] = useState(false);
  const [copied, setCopied] = useState(false);

  useEffect(() => { loadCards(); }, []);

  const loadCards = async () => {
    try {
      const res = await cosmicCardApi.getMyCards();
      setCards(res.data);
      if (res.data.length > 0) setCard(res.data[0]);
    } catch (err) { console.error(err); }
  };

  const generate = async () => {
    setLoading(true);
    try {
      const res = await cosmicCardApi.generate();
      setCard(res.data);
      loadCards();
    } catch (err) { console.error(err); }
    finally { setLoading(false); }
  };

  const share = async () => {
    if (!card) return;
    try { await cosmicCardApi.trackShare(card.shareId); } catch {}
    if (navigator.share) {
      navigator.share({ title: 'My Cosmic Energy', text: `${card.userName}'s Cosmic Energy: ${card.energyScore}`, url: card.shareUrl });
    } else {
      copyLink();
    }
    loadCards();
  };

  const copyLink = () => {
    if (!card) return;
    navigator.clipboard.writeText(card.shareUrl);
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
  };

  const elementEmoji: Record<string, string> = { Fire: '🔥', Earth: '🌍', Air: '💨', Water: '💧' };

  return (
    <div className="max-w-2xl mx-auto space-y-8 animate-fade-in">
      <div className="text-center">
        <h1 className="text-3xl font-bold text-cream-50">Cosmic Card</h1>
        <p className="text-cream-200/50 mt-1">Your personalized, shareable cosmic energy card</p>
      </div>

      <button onClick={generate} disabled={loading}
        className="w-full bg-gold-500 text-cosmic-900 py-4 rounded-xl font-semibold hover:bg-gold-400 transition-all disabled:opacity-50 text-lg">
        {loading ? 'Generating...' : card ? 'Generate New Card' : 'Generate My Cosmic Card'}
      </button>

      {card && (
        <div className="bg-gradient-to-br from-cosmic-700 via-cosmic-800 to-cosmic-900 border border-gold-400/15 rounded-2xl p-8 relative overflow-hidden animate-pulse-glow">
          {/* Decorative elements */}
          <div className="absolute top-0 right-0 w-40 h-40 bg-gold-400/5 rounded-full blur-3xl"></div>
          <div className="absolute bottom-0 left-0 w-32 h-32 bg-gold-400/5 rounded-full blur-3xl"></div>

          <div className="relative z-10 text-center">
            {/* Branding */}
            <div className="flex items-center justify-center gap-2 mb-6">
              <span className="text-gold-400 text-xl">✦</span>
              <span className="text-gold-400/60 text-sm tracking-[0.2em] uppercase">AstroLoop</span>
            </div>

            {/* Name & Date */}
            <h2 className="text-2xl font-bold text-cream-50 mb-1">{card.userName}'s Cosmic Energy</h2>
            <p className="text-cream-200/40 text-sm mb-6">{card.date}</p>

            {/* Energy Score */}
            <div className="mb-6">
              <div className="text-5xl font-bold text-gold-400 mb-1">{card.energyScore}</div>
              <div className="text-cream-200/40 text-sm">Energy Score</div>
            </div>

            {/* Cosmic Message */}
            <div className="bg-cosmic-900/40 rounded-xl p-4 mb-6 border border-cosmic-600/20">
              <p className="text-cream-200/80 italic text-sm leading-relaxed">"{card.cosmicMessage}"</p>
            </div>

            {/* Insights */}
            <div className="grid grid-cols-3 gap-3 mb-6">
              {[
                { label: 'Career', value: card.careerInsight.split('.')[0] + '.', icon: '💼' },
                { label: 'Love', value: card.loveInsight.split('.')[0] + '.', icon: '💕' },
                { label: 'Money', value: card.moneyInsight.split('.')[0] + '.', icon: '💰' },
              ].map((i) => (
                <div key={i.label} className="bg-cosmic-900/30 rounded-lg p-3">
                  <span className="text-lg">{i.icon}</span>
                  <p className="text-cream-200/30 text-xs mt-1">{i.label}</p>
                  <p className="text-cream-200/60 text-xs mt-1 leading-relaxed">{i.value}</p>
                </div>
              ))}
            </div>

            {/* Element & Sign */}
            <div className="flex items-center justify-center gap-4 text-sm">
              <span className="text-cream-200/40">{elementEmoji[card.luckyElement] || '✨'} {card.luckyElement}</span>
              <span className="text-cosmic-600">•</span>
              <span className="text-cream-200/40">⭐ {card.zodiacSign}</span>
            </div>

            {/* Stats */}
            <div className="flex items-center justify-center gap-6 mt-4 text-xs text-cream-200/30">
              <span>👁 {card.viewCount} views</span>
              <span>🔗 {card.shareCount} shares</span>
            </div>
          </div>
        </div>
      )}

      {/* Actions */}
      {card && (
        <div className="flex gap-3">
          <button onClick={share}
            className="flex-1 bg-gold-500 text-cosmic-900 py-3 rounded-xl font-semibold hover:bg-gold-400 transition-all">
            Share Card
          </button>
          <button onClick={copyLink}
            className="flex-1 border border-gold-400/30 text-gold-400 py-3 rounded-xl font-semibold hover:bg-gold-400/10 transition-all">
            {copied ? '✓ Copied!' : 'Copy Link'}
          </button>
        </div>
      )}

      {/* Previous Cards */}
      {cards.length > 1 && (
        <div>
          <h2 className="text-lg font-semibold text-cream-50 mb-3">Previous Cards</h2>
          <div className="grid grid-cols-2 gap-3">
            {cards.slice(1, 5).map((c) => (
              <button key={c.id} onClick={() => setCard(c)}
                className="bg-cosmic-800/40 border border-cosmic-600/20 rounded-xl p-4 text-left hover:border-gold-400/20 transition-all">
                <div className="text-2xl font-bold text-gold-400">{c.energyScore}</div>
                <p className="text-cream-200/40 text-xs mt-1">{c.date}</p>
                <p className="text-cream-200/60 text-xs mt-2 line-clamp-2">{c.cosmicMessage}</p>
              </button>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}
