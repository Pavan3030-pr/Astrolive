import { useState } from 'react';
import { cosmicMatchApi, type CosmicMatchResult } from '../api/cosmicMatch';

export default function CosmicMatchPage() {
  const [partnerName, setPartnerName] = useState('');
  const [partnerDob, setPartnerDob] = useState('');
  const [result, setResult] = useState<CosmicMatchResult | null>(null);
  const [loading, setLoading] = useState(false);

  const calculate = async () => {
    if (!partnerDob) return;
    setLoading(true);
    try {
      const res = await cosmicMatchApi.calculate({
        partnerDateOfBirth: partnerDob,
        partnerName: partnerName || 'Your Partner',
      });
      setResult(res.data);
    } catch (err) { console.error(err); }
    finally { setLoading(false); }
  };

  const ScoreRing = ({ score, label, color }: { score: number; label: string; color: string }) => (
    <div className="text-center">
      <div className="relative w-20 h-20 mx-auto mb-2">
        <svg className="w-20 h-20 transform -rotate-90" viewBox="0 0 100 100">
          <circle cx="50" cy="50" r="42" fill="none" stroke="rgba(212,168,83,0.1)" strokeWidth="5" />
          <circle cx="50" cy="50" r="42" fill="none" stroke={color} strokeWidth="5"
            strokeDasharray={`${score * 2.64} 264`} strokeLinecap="round" />
        </svg>
        <div className="absolute inset-0 flex items-center justify-center">
          <span className="text-lg font-bold text-cream-50">{score}%</span>
        </div>
      </div>
      <p className="text-cream-200/50 text-xs">{label}</p>
    </div>
  );

  return (
    <div className="max-w-2xl mx-auto space-y-8 animate-fade-in">
      <div className="text-center">
        <h1 className="text-3xl font-bold text-cream-50">Cosmic Match</h1>
        <p className="text-cream-200/50 mt-1">Discover your cosmic compatibility</p>
      </div>

      {/* Input */}
      <div className="bg-cosmic-800/60 border border-cosmic-600/30 rounded-2xl p-8">
        <div className="space-y-4">
          <div>
            <label className="block text-cream-200/70 text-sm mb-1.5">Partner's Name</label>
            <input type="text" value={partnerName} onChange={(e) => setPartnerName(e.target.value)}
              className="w-full bg-cosmic-700/50 border border-cosmic-600/30 rounded-xl px-4 py-3 text-cream-50 placeholder-cream-200/30 focus:outline-none focus:border-gold-400/50 transition-colors"
              placeholder="Their name" />
          </div>
          <div>
            <label className="block text-cream-200/70 text-sm mb-1.5">Partner's Date of Birth</label>
            <input type="date" value={partnerDob} onChange={(e) => setPartnerDob(e.target.value)}
              className="w-full bg-cosmic-700/50 border border-cosmic-600/30 rounded-xl px-4 py-3 text-cream-50 focus:outline-none focus:border-gold-400/50 transition-colors" required />
          </div>
          <button onClick={calculate} disabled={loading || !partnerDob}
            className="w-full bg-gold-500 text-cosmic-900 py-3 rounded-xl font-semibold hover:bg-gold-400 transition-all disabled:opacity-50">
            {loading ? 'Calculating...' : 'Calculate Compatibility'}
          </button>
        </div>
      </div>

      {/* Result */}
      {result && (
        <div className="space-y-6 animate-fade-in">
          {/* Main Score */}
          <div className="bg-gradient-to-br from-pink-500/10 to-cosmic-800/40 border border-cosmic-600/20 rounded-2xl p-8 text-center">
            <div className="flex items-center justify-center gap-8 mb-6">
              <div>
                <div className="w-16 h-16 bg-gold-400/10 rounded-full flex items-center justify-center mx-auto mb-2">
                  <span className="text-2xl">👤</span>
                </div>
                <p className="font-medium text-sm">{result.user1Name}</p>
                <p className="text-cream-200/40 text-xs">{result.user1Sign}</p>
              </div>
              <div className="text-3xl text-gold-400">💫</div>
              <div>
                <div className="w-16 h-16 bg-gold-400/10 rounded-full flex items-center justify-center mx-auto mb-2">
                  <span className="text-2xl">👤</span>
                </div>
                <p className="font-medium text-sm">{result.user2Name}</p>
                <p className="text-cream-200/40 text-xs">{result.user2Sign}</p>
              </div>
            </div>
            <div className="text-6xl font-bold text-gold-400 mb-2">{result.overallScore}%</div>
            <p className="text-cream-200/50">Overall Compatibility</p>
          </div>

          {/* Scores */}
          <div className="bg-cosmic-800/40 border border-cosmic-600/20 rounded-xl p-6">
            <div className="flex justify-around">
              <ScoreRing score={result.loveScore} label="Love" color="#ec4899" />
              <ScoreRing score={result.communicationScore} label="Communication" color="#3b82f6" />
              <ScoreRing score={result.lifeAlignmentScore} label="Life Alignment" color="#10b981" />
            </div>
          </div>

          {/* Summary */}
          <p className="text-cream-200/60 text-sm text-center leading-relaxed">{result.summary}</p>

          {/* Strengths & Friction */}
          <div className="grid md:grid-cols-2 gap-4">
            <div className="bg-emerald-500/5 border border-emerald-500/10 rounded-xl p-5">
              <h3 className="font-semibold text-emerald-400 mb-3">✦ Strengths</h3>
              <ul className="space-y-2">
                {result.strengths.map((s, i) => (
                  <li key={i} className="text-cream-200/60 text-sm flex items-start gap-2">
                    <span className="text-emerald-400 mt-0.5">•</span>{s}
                  </li>
                ))}
              </ul>
            </div>
            <div className="bg-orange-500/5 border border-orange-500/10 rounded-xl p-5">
              <h3 className="font-semibold text-orange-400 mb-3">⚡ Potential Friction</h3>
              <ul className="space-y-2">
                {result.friction.map((f, i) => (
                  <li key={i} className="text-cream-200/60 text-sm flex items-start gap-2">
                    <span className="text-orange-400 mt-0.5">•</span>{f}
                  </li>
                ))}
              </ul>
            </div>
          </div>

          {/* Action */}
          <div className="bg-cosmic-800/40 border border-cosmic-600/20 rounded-xl p-5">
            <p className="text-gold-400/60 text-xs uppercase tracking-wider mb-2">Suggested Action</p>
            <p className="text-cream-200/80 text-sm">{result.suggestedAction}</p>
          </div>
        </div>
      )}
    </div>
  );
}
