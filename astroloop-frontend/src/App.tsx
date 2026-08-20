import { Routes, Route, Navigate } from 'react-router-dom';
import { useAuth } from './context/AuthContext';
import ErrorBoundary from './components/ErrorBoundary';
import Landing from './pages/Landing';
import Login from './pages/Login';
import Register from './pages/Register';
import Onboarding from './pages/Onboarding';
import Dashboard from './pages/Dashboard';
import CosmicCardPage from './pages/CosmicCardPage';
import CosmicCardPublic from './pages/CosmicCardPublic';
import CosmicMatchPage from './pages/CosmicMatchPage';
import AstrologersPage from './pages/AstrologersPage';
import PremiumPage from './pages/PremiumPage';
import AnalyticsPage from './pages/AnalyticsPage';
import Layout from './components/Layout';

function AuthRoute({ children }: { children: React.ReactNode }) {
  const { user, loading } = useAuth();
  if (loading) return <div className="min-h-screen bg-cosmic-900 flex items-center justify-center"><div className="text-gold-400 text-lg">Loading...</div></div>;
  if (!user) return <Navigate to="/login" />;
  return <Layout>{children}</Layout>;
}

function ProtectedRoute({ children }: { children: React.ReactNode }) {
  const { user, loading } = useAuth();
  if (loading) return <div className="min-h-screen bg-cosmic-900 flex items-center justify-center"><div className="text-gold-400 text-lg">Loading...</div></div>;
  if (!user) return <Navigate to="/login" />;
  if (!user.hasProfile) return <Navigate to="/onboarding" />;
  return <Layout>{children}</Layout>;
}

function PublicCardRoute() {
  return <CosmicCardPublic />;
}

export default function App() {
  return (
    <ErrorBoundary>
      <Routes>
        <Route path="/" element={<Landing />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/onboarding" element={<AuthRoute><Onboarding /></AuthRoute>} />
        <Route path="/dashboard" element={<ProtectedRoute><Dashboard /></ProtectedRoute>} />
        <Route path="/cosmic-card" element={<ProtectedRoute><CosmicCardPage /></ProtectedRoute>} />
        <Route path="/cosmic-card/:shareId" element={<PublicCardRoute />} />
        <Route path="/cosmic-match" element={<ProtectedRoute><CosmicMatchPage /></ProtectedRoute>} />
        <Route path="/astrologers" element={<ProtectedRoute><AstrologersPage /></ProtectedRoute>} />
        <Route path="/premium" element={<ProtectedRoute><PremiumPage /></ProtectedRoute>} />
        <Route path="/analytics" element={<ProtectedRoute><AnalyticsPage /></ProtectedRoute>} />
      </Routes>
    </ErrorBoundary>
  );
}
