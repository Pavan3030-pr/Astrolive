import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      await login(email, password);
      navigate('/dashboard');
    } catch (err: any) {
      setError(err.response?.data?.error || 'Invalid credentials');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-cosmic-900 flex items-center justify-center px-4">
      <div className="w-full max-w-md">
        <div className="text-center mb-8">
          <Link to="/" className="inline-flex items-center gap-2 mb-6">
            <span className="text-gold-400 text-2xl">✦</span>
            <span className="text-cream-50 font-semibold text-lg">AstroLoop</span>
          </Link>
          <h1 className="text-3xl font-bold text-cream-50 mb-2">Welcome back</h1>
          <p className="text-cream-200/50">Sign in to your cosmic journey</p>
        </div>

        <form onSubmit={handleSubmit} className="bg-cosmic-800/60 border border-cosmic-600/30 rounded-2xl p-8">
          {error && <div className="bg-red-500/10 border border-red-500/20 text-red-400 px-4 py-3 rounded-xl mb-6 text-sm">{error}</div>}

          <div className="space-y-4">
            <div>
              <label className="block text-cream-200/70 text-sm mb-1.5">Email</label>
              <input type="email" value={email} onChange={(e) => setEmail(e.target.value)}
                className="w-full bg-cosmic-700/50 border border-cosmic-600/30 rounded-xl px-4 py-3 text-cream-50 placeholder-cream-200/30 focus:outline-none focus:border-gold-400/50 transition-colors"
                placeholder="Enter your email" required />
            </div>
            <div>
              <label className="block text-cream-200/70 text-sm mb-1.5">Password</label>
              <input type="password" value={password} onChange={(e) => setPassword(e.target.value)}
                className="w-full bg-cosmic-700/50 border border-cosmic-600/30 rounded-xl px-4 py-3 text-cream-50 placeholder-cream-200/30 focus:outline-none focus:border-gold-400/50 transition-colors"
                placeholder="Enter your password" required />
            </div>
          </div>

          <button type="submit" disabled={loading}
            className="w-full mt-6 bg-gold-500 text-cosmic-900 py-3 rounded-xl font-semibold hover:bg-gold-400 transition-all disabled:opacity-50">
            {loading ? 'Signing in...' : 'Sign In'}
          </button>

          <p className="text-center text-cream-200/40 text-sm mt-6">
            New to AstroLoop? <Link to="/register" className="text-gold-400 hover:text-gold-300 transition-colors">Create account</Link>
          </p>

          <div className="mt-6 pt-6 border-t border-cosmic-600/20">
            <p className="text-cream-200/30 text-xs text-center">
              Demo: demo@astroloop.com / demo123
            </p>
          </div>
        </form>
      </div>
    </div>
  );
}
