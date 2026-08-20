import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { cosmicCardApi, type CosmicCard } from '../api/cosmicCard';

export default function CosmicCardPublic() {
  const { shareId } = useParams();
  const navigate = useNavigate();
  const [card, setCard] = useState<CosmicCard | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    if (!shareId) return;
    cosmicCardApi.getPublic(shareId)
      .then((res) => setCard(res.data))
      .catch(() => setError('Card not found'))
      .finally(() => setLoading(false));
  }, [shareId]);

  const elementEmoji: Record<string, string> = { Fire: '🔥', Earth: '🌍', Air: '💨', Water: '💧' };

  if (loading) return <div className="min-h-screen bg-cosmic-900 flex items-center justify-center"><div className="text-gold-400 text-lg">Loading cosmic card...</div></div>;
  if (error || !card) return <div className="min-h-screen bg-cosmic-900 flex items-center justify-center"><div className="text-center"><p className="text-cream-200/50 mb-4">Card not found</p><button onClick={() => navigate('/')} className="text-gold-400 hover:text-gold-300">Go to AstroLoop</button></div></div>;

  return (
    <div className="min-h-screen bg-cosmic-900 flex flex-col items-center justify-center px-4 py-12">
      {/* Header */}
      <div className="text-center mb-8">
        <span className="text-gold-400 text-3xl">✦</span>
        <p className="text-cream-200/40 text-sm mt-2 tracking-wider uppercase">Shared via AstroLoop</p>
      </div>

      {/* Card */}
      <div className="w-full max-w-md bg-gradient-to-br from-cosmic-700 via-cosmic-800 to-cosmic-900 border border-gold-400/15 rounded-2xl p-8 relative overflow-hidden animate-pulse-glow">
        <div className="absolute top-0 right-0 w-40 h-40 bg-gold-400/5 rounded-full blur-3xl"></div>

        <div className="relative z-10 text-center">
          <h2 className="text-2xl font-bold text-cream-50 mb-1">{card.userName}'s Cosmic Energy</h2>
          <p className="text-cream-200/40 text-sm mb-6">{card.date}</p>

          <div className="mb-6">
            <div className="text-5xl font-bold text-gold-400 mb-1">{card.energyScore}</div>
            <div className="text-cream-200/40 text-sm">Energy Score</div>
          </div>

          <div className="bg-cosmic-900/40 rounded-xl p-4 mb-6 border border-cosmic-600/20">
            <p className="text-cream-200/80 italic text-sm leading-relaxed">"{card.cosmicMessage}"</p>
          </div>

          <div className="grid grid-cols-3 gap-3 mb-6">
            {[
              { label: 'Career', value: card.careerInsight.split('.')[0], icon: '💼' },
              { label: 'Love', value: card.loveInsight.split('.')[0], icon: '💕' },
              { label: 'Money', value: card.moneyInsight.split('.')[0], icon: '💰' },
            ].map((i) => (
              <div key={i.label} className="bg-cosmic-900/30 rounded-lg p-3">
                <span className="text-lg">{i.icon}</span>
                <p className="text-cream-200/30 text-xs mt-1">{i.label}</p>
                <p className="text-cream-200/60 text-xs mt-1 leading-relaxed">{i.value}.</p>
              </div>
            ))}
          </div>

          <div className="flex items-center justify-center gap-4 text-sm">
            <span className="text-cream-200/40">{elementEmoji[card.luckyElement] || '✨'} {card.luckyElement}</span>
            <span className="text-cosmic-600">•</span>
            <span className="text-cream-200/40">⭐ {card.zodiacSign}</span>
          </div>
        </div>
      </div>

      {/* Viral CTA */}
      <div className="w-full max-w-md mt-8 text-center">
        <div className="bg-cosmic-800/40 border border-cosmic-600/20 rounded-2xl p-8">
          <h3 className="text-xl font-bold text-cream-50 mb-2">Want to know what YOUR cosmic energy says?</h3>
          <p className="text-cream-200/50 text-sm mb-6">Discover your personalized daily cosmic insights. It takes 30 seconds to get started.</p>
          <button onClick={() => navigate('/register')}
            className="w-full bg-gold-500 text-cosmic-900 py-4 rounded-xl font-semibold hover:bg-gold-400 transition-all text-lg animate-pulse-glow">
            Discover Your Cosmic Energy
          </button>
          <p className="text-cream-200/30 text-xs mt-4">Free to join • No credit card required</p>
        </div>
      </div>

      {/* Footer */}
      <div className="mt-8 text-center">
        <button onClick={() => navigate('/')} className="text-cream-200/30 hover:text-cream-200/50 text-sm transition-colors">
          ✦ Powered by AstroLoop
        </button>
      </div>
    </div>
  );
}
