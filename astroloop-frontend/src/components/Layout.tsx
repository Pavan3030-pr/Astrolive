import { useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const navItems = [
  { path: '/dashboard', label: 'Dashboard', icon: '✦' },
  { path: '/cosmic-card', label: 'Cosmic Card', icon: '🌟' },
  { path: '/cosmic-match', label: 'Cosmic Match', icon: '💫' },
  { path: '/astrologers', label: 'Astrologers', icon: '🔮' },
  { path: '/premium', label: 'Premium', icon: '👑' },
  { path: '/analytics', label: 'Analytics', icon: '📊' },
];

export default function Layout({ children }: { children: React.ReactNode }) {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();

  return (
    <div className="min-h-screen bg-cosmic-900">
      {/* Top nav */}
      <nav className="bg-cosmic-800/80 backdrop-blur-sm border-b border-cosmic-600/50 sticky top-0 z-50">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between h-16">
            <div className="flex items-center gap-2 cursor-pointer" onClick={() => navigate('/dashboard')}>
              <span className="text-gold-400 text-2xl">✦</span>
              <span className="text-cream-50 font-semibold text-lg tracking-tight">AstroLoop</span>
            </div>
            <div className="hidden md:flex items-center gap-1">
              {navItems.map((item) => (
                <button
                  key={item.path}
                  onClick={() => navigate(item.path)}
                  className={`px-3 py-2 rounded-lg text-sm font-medium transition-all duration-200 ${
                    location.pathname === item.path
                      ? 'bg-gold-500/15 text-gold-400'
                      : 'text-cream-200/60 hover:text-cream-50 hover:bg-cosmic-700/50'
                  }`}
                >
                  <span className="mr-1.5">{item.icon}</span>
                  {item.label}
                </button>
              ))}
            </div>
            <div className="flex items-center gap-3">
              <span className="text-cream-200/60 text-sm hidden sm:block">Hi, {user?.firstName}</span>
              <button onClick={() => { logout(); navigate('/'); }} className="text-cream-200/40 hover:text-cream-50 text-sm transition-colors">
                Logout
              </button>
            </div>
          </div>
        </div>
      </nav>

      {/* Main content */}
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 pb-24 md:pb-8">
        {children}
      </main>

      {/* Mobile bottom nav */}
      <div className="md:hidden fixed bottom-0 left-0 right-0 bg-cosmic-800/95 backdrop-blur-sm border-t border-cosmic-600/50 z-50">
        <div className="flex items-center justify-around py-2 px-1">
          {navItems.slice(0, 5).map((item) => (
            <button
              key={item.path}
              onClick={() => navigate(item.path)}
              className={`flex flex-col items-center gap-0.5 px-2 py-1 rounded-lg text-xs transition-all ${
                location.pathname === item.path
                  ? 'text-gold-400'
                  : 'text-cream-200/40'
              }`}
            >
              <span className="text-lg">{item.icon}</span>
              <span>{item.label.split(' ')[0]}</span>
            </button>
          ))}
        </div>
      </div>
    </div>
  );
}
