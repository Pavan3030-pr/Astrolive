import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { profileApi, type ProfileData } from '../api/profile';

const interests = [
  { id: 'CAREER' as const, icon: '💼', label: 'Career', desc: 'Professional growth & success' },
  { id: 'LOVE' as const, icon: '💕', label: 'Love', desc: 'Relationships & romance' },
  { id: 'MONEY' as const, icon: '💰', label: 'Money', desc: 'Financial prosperity' },
  { id: 'GENERAL' as const, icon: '✨', label: 'General', desc: 'Overall life guidance' },
];

export default function Onboarding() {
  const [step, setStep] = useState(0);
  const [name, setName] = useState('');
  const [dob, setDob] = useState('');
  const [timeOfBirth, setTimeOfBirth] = useState('');
  const [placeOfBirth, setPlaceOfBirth] = useState('');
  const [interest, setInterest] = useState<ProfileData['primaryInterest'] | null>(null);
  const { updateProfileState } = useAuth();
  const navigate = useNavigate();

  const steps = ['Name', 'Birth Details', 'Interest', 'Creating'];

  const handleCreate = async () => {
    if (!name || !dob || !interest) return;
    setStep(3);
    try {
      await profileApi.create({
        name,
        dateOfBirth: dob,
        timeOfBirth: timeOfBirth || undefined,
        placeOfBirth: placeOfBirth || undefined,
        primaryInterest: interest,
      });
      updateProfileState(true);
      setTimeout(() => navigate('/dashboard'), 1500);
    } catch (err: any) {
      alert(err.response?.data?.error || 'Failed to create profile');
      setStep(0);

    }
  };

  return (
    <div className="min-h-screen bg-cosmic-900 flex items-center justify-center px-4">
      <div className="w-full max-w-lg">
        <div className="text-center mb-8">
          <span className="text-gold-400 text-3xl">✦</span>
          <h1 className="text-3xl font-bold text-cream-50 mt-4 mb-2">
            {step < 3 ? 'Create Your Cosmic Profile' : 'Building Your Profile'}
          </h1>
          <p className="text-cream-200/50">
            {step < 3 ? 'Tell us about yourself for personalized insights' : 'Aligning the stars for your personalized experience...'}
          </p>
        </div>

        {/* Progress */}
        <div className="flex items-center gap-2 mb-8">
          {steps.slice(0, 3).map((s, i) => (
            <div key={s} className="flex-1">
              <div className={`h-1 rounded-full transition-all duration-500 ${i <= step ? 'bg-gold-400' : 'bg-cosmic-700'}`}></div>
              <p className={`text-xs mt-1 ${i <= step ? 'text-gold-400' : 'text-cream-200/30'}`}>{s}</p>
            </div>
          ))}
        </div>

        <div className="bg-cosmic-800/60 border border-cosmic-600/30 rounded-2xl p-8 min-h-[320px]">
          {/* Step 0: Name */}
          {step === 0 && (
            <div className="animate-fade-in">
              <label className="block text-cream-200/70 text-sm mb-2">Your Name</label>
              <input type="text" value={name} onChange={(e) => setName(e.target.value)}
                className="w-full bg-cosmic-700/50 border border-cosmic-600/30 rounded-xl px-4 py-3 text-cream-50 placeholder-cream-200/30 focus:outline-none focus:border-gold-400/50 transition-colors text-lg"
                placeholder="How should we greet you?" autoFocus />
              <button onClick={() => name && setStep(1)} disabled={!name}
                className="w-full mt-6 bg-gold-500 text-cosmic-900 py-3 rounded-xl font-semibold hover:bg-gold-400 transition-all disabled:opacity-30">
                Continue
              </button>
            </div>
          )}

          {/* Step 1: Birth Details */}
          {step === 1 && (
            <div className="animate-fade-in space-y-4">
              <div>
                <label className="block text-cream-200/70 text-sm mb-1.5">Date of Birth</label>
                <input type="date" value={dob} onChange={(e) => setDob(e.target.value)}
                  className="w-full bg-cosmic-700/50 border border-cosmic-600/30 rounded-xl px-4 py-3 text-cream-50 focus:outline-none focus:border-gold-400/50 transition-colors" required />
              </div>
              <div>
                <label className="block text-cream-200/70 text-sm mb-1.5">Time of Birth (optional)</label>
                <input type="time" value={timeOfBirth} onChange={(e) => setTimeOfBirth(e.target.value)}
                  className="w-full bg-cosmic-700/50 border border-cosmic-600/30 rounded-xl px-4 py-3 text-cream-50 focus:outline-none focus:border-gold-400/50 transition-colors" />
              </div>
              <div>
                <label className="block text-cream-200/70 text-sm mb-1.5">Place of Birth (optional)</label>
                <input type="text" value={placeOfBirth} onChange={(e) => setPlaceOfBirth(e.target.value)}
                  className="w-full bg-cosmic-700/50 border border-cosmic-600/30 rounded-xl px-4 py-3 text-cream-50 placeholder-cream-200/30 focus:outline-none focus:border-gold-400/50 transition-colors"
                  placeholder="City, Country" />
              </div>
              <div className="flex gap-3 mt-4">
                <button onClick={() => setStep(0)} className="flex-1 border border-cosmic-600/30 text-cream-200/60 py-3 rounded-xl hover:border-gold-400/30 transition-all">Back</button>
                <button onClick={() => dob && setStep(2)} disabled={!dob}
                  className="flex-1 bg-gold-500 text-cosmic-900 py-3 rounded-xl font-semibold hover:bg-gold-400 transition-all disabled:opacity-30">
                  Continue
                </button>
              </div>
            </div>
          )}

          {/* Step 2: Interest */}
          {step === 2 && (
            <div className="animate-fade-in">
              <p className="text-cream-200/60 text-sm mb-4">What matters most to you right now?</p>
              <div className="grid grid-cols-2 gap-3">
                {interests.map((i) => (
                  <button key={i.id} onClick={() => setInterest(i.id)}
                    className={`p-4 rounded-xl border text-left transition-all ${
                      interest === i.id
                        ? 'bg-gold-400/10 border-gold-400/40'
                        : 'bg-cosmic-700/30 border-cosmic-600/20 hover:border-cosmic-600/40'
                    }`}>
                    <span className="text-2xl">{i.icon}</span>
                    <p className="font-medium mt-2">{i.label}</p>
                    <p className="text-cream-200/40 text-xs mt-1">{i.desc}</p>
                  </button>
                ))}
              </div>
              <div className="flex gap-3 mt-6">
                <button onClick={() => setStep(1)} className="flex-1 border border-cosmic-600/30 text-cream-200/60 py-3 rounded-xl hover:border-gold-400/30 transition-all">Back</button>
                <button onClick={handleCreate} disabled={!interest}
                  className="flex-1 bg-gold-500 text-cosmic-900 py-3 rounded-xl font-semibold hover:bg-gold-400 transition-all disabled:opacity-30">
                  Create Profile
                </button>
              </div>
            </div>
          )}

          {/* Step 3: Creating */}
          {step === 3 && (
            <div className="animate-fade-in flex flex-col items-center justify-center py-12">
              <div className="w-16 h-16 border-2 border-gold-400 border-t-transparent rounded-full animate-spin mb-6"></div>
              <p className="text-gold-400 text-lg font-medium mb-2">Creating your cosmic profile...</p>
              <p className="text-cream-200/40 text-sm">Aligning celestial data for {name}</p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
