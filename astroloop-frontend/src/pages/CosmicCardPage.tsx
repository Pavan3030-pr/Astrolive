import { useState, useEffect } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { cosmicCardApi, type CosmicCard, getShareUrl } from '../api/cosmicCard';
import { CardSkeleton } from '../components/LoadingSkeleton';

const elementEmoji: Record<string, string> = { Fire: '🔥', Earth: '🌍', Air: '💨', Water: '💧' };

export default function CosmicCardPage() {
  const [card, setCard] = useState<CosmicCard | null>(null);
  const [cards, setCards] = useState<CosmicCard[]>([]);
  const [initialLoading, setInitialLoading] = useState(true);
  const [copied, setCopied] = useState(false);
  const [generating, setGenerating] = useState(false);

  useEffect(() => { loadCards(); }, []);

  const loadCards = async () => {
    try {
      const res = await cosmicCardApi.getMyCards();
      setCards(res.data);
      if (res.data.length > 0) setCard(res.data[0]);
    } catch (err) { console.error(err); }
    finally { setInitialLoading(false); }
  };

  const generate = async () => {
    setGenerating(true);
    try {
      const res = await cosmicCardApi.generate();
      setCard(res.data);
      loadCards();
    } catch (err) { console.error(err); }
    finally { setGenerating(false); }
  };

  const share = async () => {
    if (!card) return;
    try { await cosmicCardApi.trackShare(card.shareId); } catch {}
    const shareUrl = getShareUrl(card.shareId);
    if (navigator.share) {
      navigator.share({ title: 'My Cosmic Energy', text: `${card.userName}'s Cosmic Energy: ${card.energyScore}`, url: shareUrl });
    } else { copyLink(); }
    loadCards();
  };

  const copyLink = () => {
    if (!card) return;
    navigator.clipboard.writeText(getShareUrl(card.shareId));
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
  };

  if (initialLoading) return <CardSkeleton />;

  return (
    <motion.div className="max-w-2xl mx-auto space-y-8" initial={{ opacity: 0 }} animate={{ opacity: 1 }}>
      <div className="text-center">
        <h1 className="text-3xl font-bold text-cream-50">Cosmic Card</h1>
        <p className="text-cream-200/50 mt-1">Your personalized, shareable cosmic energy card</p>
      </div>

      <motion.button onClick={generate} disabled={generating}
        whileHover={{ scale: 1.02 }} whileTap={{ scale: 0.98 }}
        className="w-full bg-gold-500 text-cosmic-900 py-4 rounded-xl font-semibold hover:bg-gold-400 transition-all disabled:opacity-50 text-lg">
        {generating ? (
          <span className="flex items-center justify-center gap-2">
            <svg className="animate-spin h-5 w-5" viewBox="0 0 24 24"><circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" fill="none" /><path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4z" /></svg>
            Generating your cosmic energy...
          </span>
        ) : card ? 'Generate New Card' : 'Generate My Cosmic Card'}
      </motion.button>

      <AnimatePresence mode="wait">
        {card && (
          <motion.div key={card.shareId}
            initial={{ opacity: 0, scale: 0.95, y: 20 }}
            animate={{ opacity: 1, scale: 1, y: 0 }}
            exit={{ opacity: 0, scale: 0.95, y: -20 }}
            transition={{ duration: 0.5, ease: 'easeOut' }}
            className="bg-gradient-to-br from-cosmic-700 via-cosmic-800 to-cosmic-900 border border-gold-400/15 rounded-2xl p-8 relative overflow-hidden"
            id="cosmic-card"
          >
            <div className="absolute top-0 right-0 w-40 h-40 bg-gold-400/5 rounded-full blur-3xl"></div>
            <div className="absolute bottom-0 left-0 w-32 h-32 bg-gold-400/5 rounded-full blur-3xl"></div>

            <div className="relative z-10 text-center">
              <motion.div initial={{ opacity: 0, y: -10 }} animate={{ opacity: 1, y: 0 }} transition={{ delay: 0.1 }}
                className="flex items-center justify-center gap-2 mb-6">
                <span className="text-gold-400 text-xl">✦</span>
                <span className="text-gold-400/60 text-sm tracking-[0.2em] uppercase">AstroLoop</span>
              </motion.div>

              <motion.h2 initial={{ opacity: 0 }} animate={{ opacity: 1 }} transition={{ delay: 0.2 }}
                className="text-2xl font-bold text-cream-50 mb-1">{card.userName}'s Cosmic Energy</motion.h2>
              <p className="text-cream-200/40 text-sm mb-6">{card.date}</p>

              <motion.div initial={{ opacity: 0, scale: 0.5 }} animate={{ opacity: 1, scale: 1 }}
                transition={{ delay: 0.3, type: 'spring', stiffness: 200 }} className="mb-6">
                <div className="text-5xl font-bold text-gold-400 mb-1">{card.energyScore}</div>
                <div className="text-cream-200/40 text-sm">Energy Score</div>
              </motion.div>

              <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} transition={{ delay: 0.4 }}
                className="bg-cosmic-900/40 rounded-xl p-4 mb-6 border border-cosmic-600/20">
                <p className="text-cream-200/80 italic text-sm leading-relaxed">"{card.cosmicMessage}"</p>
              </motion.div>

              <motion.div initial={{ opacity: 0, y: 10 }} animate={{ opacity: 1, y: 0 }} transition={{ delay: 0.5 }}
                className="grid grid-cols-3 gap-3 mb-6">
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
              </motion.div>

              <div className="flex items-center justify-center gap-4 text-sm">
                <span className="text-cream-200/40">{elementEmoji[card.luckyElement] || '✨'} {card.luckyElement}</span>
                <span className="text-cosmic-600">•</span>
                <span className="text-cream-200/40">⭐ {card.zodiacSign}</span>
              </div>

              <div className="flex items-center justify-center gap-6 mt-4 text-xs text-cream-200/30">
                <span>👁 {card.viewCount} views</span>
                <span>🔗 {card.shareCount} shares</span>
              </div>
            </div>
          </motion.div>
        )}
      </AnimatePresence>

      {card && (
        <motion.div className="flex gap-3" initial={{ opacity: 0, y: 10 }} animate={{ opacity: 1, y: 0 }} transition={{ delay: 0.6 }}>
          <motion.button whileHover={{ scale: 1.02 }} whileTap={{ scale: 0.98 }} onClick={share}
            className="flex-1 bg-gold-500 text-cosmic-900 py-3 rounded-xl font-semibold hover:bg-gold-400 transition-all">
            Share Card
          </motion.button>
          <motion.button whileHover={{ scale: 1.02 }} whileTap={{ scale: 0.98 }} onClick={copyLink}
            className="flex-1 border border-gold-400/30 text-gold-400 py-3 rounded-xl font-semibold hover:bg-gold-400/10 transition-all">
            {copied ? '✓ Copied!' : 'Copy Link'}
          </motion.button>
        </motion.div>
      )}

      {cards.length > 1 && (
        <div>
          <h2 className="text-lg font-semibold text-cream-50 mb-3">Previous Cards</h2>
          <div className="grid grid-cols-2 gap-3">
            {cards.slice(1, 5).map((c, i) => (
              <motion.button key={c.id} onClick={() => setCard(c)}
                initial={{ opacity: 0, y: 10 }} animate={{ opacity: 1, y: 0 }}
                transition={{ delay: i * 0.1 }}
                whileHover={{ scale: 1.03 }}
                className="bg-cosmic-800/40 border border-cosmic-600/20 rounded-xl p-4 text-left hover:border-gold-400/20 transition-all">
                <div className="text-2xl font-bold text-gold-400">{c.energyScore}</div>
                <p className="text-cream-200/40 text-xs mt-1">{c.date}</p>
                <p className="text-cream-200/60 text-xs mt-2 line-clamp-2">{c.cosmicMessage}</p>
              </motion.button>
            ))}
          </div>
        </div>
      )}
    </motion.div>
  );
}
