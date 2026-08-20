import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { motion } from 'framer-motion';
import { dashboardApi, type DashboardData } from '../api/dashboard';
import { streakApi, type StreakData } from '../api/streak';
import { challengeApi, type Challenge } from '../api/challenges';
import { DashboardSkeleton } from '../components/LoadingSkeleton';

const fadeUp = { hidden: { opacity: 0, y: 20 }, visible: { opacity: 1, y: 0 } };
const stagger = { visible: { transition: { staggerChildren: 0.1 } } };

export default function Dashboard() {
  const [dash, setDash] = useState<DashboardData | null>(null);
  const [streak, setStreak] = useState<StreakData | null>(null);
  const [challenges, setChallenges] = useState<Challenge[]>([]);
  const [loading, setLoading] = useState(true);
  const [checkingIn, setCheckingIn] = useState(false);
  const navigate = useNavigate();

  useEffect(() => { loadData(); }, []);

  const loadData = async () => {
    try {
      const [dashRes, streakRes, challRes] = await Promise.all([
        dashboardApi.get(),
        streakApi.get(),
        challengeApi.getChallenges(),
      ]);
      setDash(dashRes.data);
      setStreak(streakRes.data);
      setChallenges(challRes.data);
    } catch (err) { console.error(err); }
    finally { setLoading(false); }
  };

  const handleCheckIn = async () => {
    setCheckingIn(true);
    try {
      const res = await streakApi.checkIn();
      setStreak(res.data);
    } catch (err) { console.error(err); }
    finally { setCheckingIn(false); }
  };

  const handleJoinChallenge = async (id: number) => {
    try { await challengeApi.join(id); loadData(); } catch (err) { console.error(err); }
  };

  if (loading) return <DashboardSkeleton />;
  if (!dash) return null;

  const energyColor = dash.energyScore >= 80 ? 'text-emerald-400' : dash.energyScore >= 60 ? 'text-gold-400' : 'text-orange-400';
  const energyBg = dash.energyScore >= 80 ? 'from-emerald-400/20' : dash.energyScore >= 60 ? 'from-gold-400/20' : 'from-orange-400/20';

  return (
    <motion.div className="space-y-8" initial="hidden" animate="visible" variants={stagger}>
      {/* Greeting */}
      <motion.div variants={fadeUp}>
        <h1 className="text-3xl md:text-4xl font-bold text-cream-50">{dash.greeting}</h1>
        <p className="text-cream-200/50 mt-1">Here's your cosmic energy today</p>
      </motion.div>

      {/* Energy Score Hero */}
      <motion.div variants={fadeUp} className={`bg-gradient-to-br ${energyBg} to-cosmic-800/40 border border-cosmic-600/20 rounded-2xl p-8 relative overflow-hidden`}>
        <div className="absolute top-0 right-0 w-48 h-48 bg-gold-400/5 rounded-full blur-3xl"></div>
        <div className="relative z-10 flex flex-col md:flex-row items-start md:items-center gap-6">
          <div className="flex-shrink-0">
            <div className="relative w-24 h-24">
              <svg className="w-24 h-24 transform -rotate-90" viewBox="0 0 100 100">
                <circle cx="50" cy="50" r="45" fill="none" stroke="rgba(212,168,83,0.1)" strokeWidth="6" />
                <motion.circle cx="50" cy="50" r="45" fill="none" stroke="currentColor" strokeWidth="6"
                  className={`${energyColor}`}
                  strokeLinecap="round"
                  initial={{ strokeDasharray: '0 283' }}
                  animate={{ strokeDasharray: `${dash.energyScore * 2.83} 283` }}
                  transition={{ duration: 1.5, ease: 'easeOut' }}
                />
              </svg>
              <div className="absolute inset-0 flex items-center justify-center">
                <motion.span
                  className={`text-2xl font-bold ${energyColor}`}
                  initial={{ opacity: 0, scale: 0.5 }}
                  animate={{ opacity: 1, scale: 1 }}
                  transition={{ delay: 0.5, duration: 0.5 }}
                >
                  {dash.energyScore}
                </motion.span>
              </div>
            </div>
          </div>
          <div>
            <p className="text-cream-200/50 text-sm mb-1">Your Cosmic Energy Today</p>
            <p className="text-cream-50 text-lg leading-relaxed">{dash.cosmicBrief}</p>
            <p className="text-gold-400/60 text-sm mt-2">Lucky Element: {dash.luckyElement}</p>
          </div>
        </div>
      </motion.div>

      {/* Insights Grid */}
      <motion.div variants={fadeUp} className="grid md:grid-cols-3 gap-4">
        {[
          { title: 'Career', insight: dash.careerInsight, icon: '💼', color: 'from-blue-400/10' },
          { title: 'Love', insight: dash.loveInsight, icon: '💕', color: 'from-pink-400/10' },
          { title: 'Money', insight: dash.moneyInsight, icon: '💰', color: 'from-amber-400/10' },
        ].map((item) => (
          <motion.div key={item.title} variants={fadeUp} whileHover={{ scale: 1.02, y: -2 }}
            className={`bg-gradient-to-br ${item.color} to-cosmic-800/40 border border-cosmic-600/20 rounded-xl p-6 transition-all`}>
            <div className="flex items-center gap-2 mb-3">
              <span className="text-xl">{item.icon}</span>
              <h3 className="font-semibold">{item.title}</h3>
            </div>
            <p className="text-cream-200/60 text-sm leading-relaxed">{item.insight}</p>
          </motion.div>
        ))}
      </motion.div>

      {/* Daily Question */}
      <motion.div variants={fadeUp} className="bg-cosmic-800/40 border border-cosmic-600/20 rounded-xl p-6">
        <p className="text-gold-400/60 text-xs uppercase tracking-wider mb-2">Daily Cosmic Question</p>
        <p className="text-cream-50 text-lg italic">{dash.dailyQuestion}</p>
      </motion.div>

      {/* Stats */}
      <motion.div variants={fadeUp} className="grid grid-cols-2 md:grid-cols-4 gap-4">
        {[
          { label: 'Current Streak', value: `${streak?.currentStreak || 0}d`, icon: '🔥' },
          { label: 'Longest Streak', value: `${streak?.longestStreak || 0}d`, icon: '⚡' },
          { label: 'Challenges', value: dash.completedChallenges, icon: '🏆' },
          { label: 'Achievements', value: dash.totalAchievements, icon: '⭐' },
        ].map((s) => (
          <motion.div key={s.label} variants={fadeUp} whileHover={{ scale: 1.03 }}
            className="bg-cosmic-800/40 border border-cosmic-600/20 rounded-xl p-4 text-center">
            <span className="text-2xl">{s.icon}</span>
            <div className="text-xl font-bold text-cream-50 mt-2">{s.value}</div>
            <div className="text-cream-200/40 text-xs mt-1">{s.label}</div>
          </motion.div>
        ))}
      </motion.div>

      {/* CTAs */}
      <motion.div variants={fadeUp} className="flex gap-4">
        <motion.button whileHover={{ scale: 1.02 }} whileTap={{ scale: 0.98 }}
          onClick={handleCheckIn} disabled={checkingIn || streak?.checkedInToday}
          className="flex-1 bg-gold-500 text-cosmic-900 py-3 rounded-xl font-semibold hover:bg-gold-400 transition-all disabled:opacity-40 disabled:cursor-not-allowed">
          {streak?.checkedInToday ? '✓ Checked In Today' : checkingIn ? 'Checking In...' : 'Daily Check-In'}
        </motion.button>
        <motion.button whileHover={{ scale: 1.02 }} whileTap={{ scale: 0.98 }}
          onClick={() => navigate('/cosmic-card')}
          className="flex-1 border border-gold-400/30 text-gold-400 py-3 rounded-xl font-semibold hover:bg-gold-400/10 transition-all">
          Generate Cosmic Card →
        </motion.button>
      </motion.div>

      {/* Challenges */}
      {challenges.length > 0 && (
        <motion.div variants={fadeUp}>
          <h2 className="text-xl font-semibold mb-4">Active Challenges</h2>
          <div className="space-y-3">
            {challenges.slice(0, 3).map((c) => (
              <motion.div key={c.id} variants={fadeUp} whileHover={{ scale: 1.01 }}
                className="bg-cosmic-800/40 border border-cosmic-600/20 rounded-xl p-4 flex items-center justify-between">
                <div>
                  <h3 className="font-medium">{c.title}</h3>
                  <p className="text-cream-200/40 text-sm">{c.description}</p>
                </div>
                <div className="flex items-center gap-3">
                  <span className="text-gold-400 text-sm font-medium">+{c.rewardPoints} pts</span>
                  {!c.joined ? (
                    <motion.button whileTap={{ scale: 0.95 }} onClick={() => handleJoinChallenge(c.id)}
                      className="px-4 py-1.5 bg-gold-500/10 text-gold-400 rounded-lg text-sm hover:bg-gold-500/20 transition-all">
                      Join
                    </motion.button>
                  ) : c.completed ? (
                    <span className="px-4 py-1.5 text-emerald-400 text-sm">✓ Done</span>
                  ) : (
                    <motion.button whileTap={{ scale: 0.95 }} onClick={() => challengeApi.complete(c.id).then(loadData)}
                      className="px-4 py-1.5 bg-gold-500/10 text-gold-400 rounded-lg text-sm hover:bg-gold-500/20 transition-all">
                      Complete
                    </motion.button>
                  )}
                </div>
              </motion.div>
            ))}
          </div>
        </motion.div>
      )}
    </motion.div>
  );
}
