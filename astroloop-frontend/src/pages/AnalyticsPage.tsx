import { useState, useEffect } from 'react';
import { analyticsApi, type AnalyticsData } from '../api/analytics';
import { LineChart, Line, BarChart, Bar, PieChart, Pie, Cell, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';

const COLORS = ['#d4a853', '#3b82f6', '#10b981', '#ec4899', '#8b5cf6'];

export default function AnalyticsPage() {
  const [data, setData] = useState<AnalyticsData | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    analyticsApi.get()
      .then((res) => setData(res.data))
      .catch(console.error)
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <div className="flex items-center justify-center py-20 text-gold-400">Loading analytics...</div>;
  if (!data) return null;

  const Stat = ({ label, value, sub }: { label: string; value: string | number; sub?: string }) => (
    <div className="bg-cosmic-800/40 border border-cosmic-600/20 rounded-xl p-5">
      <p className="text-cream-200/40 text-xs uppercase tracking-wider">{label}</p>
      <p className="text-2xl font-bold text-cream-50 mt-1">{value}</p>
      {sub && <p className="text-cream-200/30 text-xs mt-1">{sub}</p>}
    </div>
  );

  const userChartData = data.userGrowthData?.slice(-14).map((d) => ({
    date: d.date.slice(5),
    users: d.users,
  })) || [];

  const engagementChartData = data.engagementData?.map((d) => ({
    date: d.date.slice(5),
    activeUsers: d.activeUsers,
  })) || [];

  return (
    <div className="space-y-8 animate-fade-in">
      <div>
        <h1 className="text-3xl font-bold text-cream-50">Analytics Dashboard</h1>
        <p className="text-cream-200/50 mt-1">Real-time business metrics</p>
      </div>

      {/* KPI Grid */}
      <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
        <Stat label="Total Users" value={data.totalUsers} />
        <Stat label="New Users (7d)" value={data.newUsersLast7Days} />
        <Stat label="Daily Active" value={data.dailyActiveUsers} />
        <Stat label="Card Generations" value={data.totalCardGenerations} />
        <Stat label="Card Views" value={data.totalCardViews} />
        <Stat label="Total Shares" value={data.totalShares} />
        <Stat label="Viral Coefficient" value={`${data.viralCoefficient}x`} />
        <Stat label="Referral Conversion" value={`${data.referralConversionRate}%`} />
      </div>

      {/* Retention & Revenue */}
      <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
        <Stat label="D1 Retention" value={`${data.d1Retention}%`} />
        <Stat label="D7 Retention" value={`${data.d7Retention}%`} />
        <Stat label="D30 Retention" value={`${data.d30Retention}%`} />
        <Stat label="Premium Conversion" value={`${data.premiumConversionRate}%`} />
        <Stat label="Total Revenue" value={`₹${data.totalRevenue}`} />
        <Stat label="ARPU" value={`₹${data.arpu}`} />
        <Stat label="Premium Users" value={data.premiumUsers} />
        <Stat label="Consultations" value={data.totalConsultations} />
      </div>

      {/* Charts */}
      <div className="grid md:grid-cols-2 gap-6">
        {/* User Growth */}
        <div className="bg-cosmic-800/40 border border-cosmic-600/20 rounded-xl p-6">
          <h3 className="font-semibold text-cream-50 mb-4">User Growth (14 days)</h3>
          <ResponsiveContainer width="100%" height={200}>
            <LineChart data={userChartData}>
              <CartesianGrid strokeDasharray="3 3" stroke="rgba(255,255,255,0.05)" />
              <XAxis dataKey="date" tick={{ fill: 'rgba(255,255,255,0.3)', fontSize: 10 }} />
              <YAxis tick={{ fill: 'rgba(255,255,255,0.3)', fontSize: 10 }} />
              <Tooltip contentStyle={{ background: '#1a2235', border: '1px solid rgba(212,168,83,0.2)', borderRadius: '8px', color: '#faf8f5' }} />
              <Line type="monotone" dataKey="users" stroke="#d4a853" strokeWidth={2} dot={false} />
            </LineChart>
          </ResponsiveContainer>
        </div>

        {/* Engagement */}
        <div className="bg-cosmic-800/40 border border-cosmic-600/20 rounded-xl p-6">
          <h3 className="font-semibold text-cream-50 mb-4">Daily Active Users (7 days)</h3>
          <ResponsiveContainer width="100%" height={200}>
            <BarChart data={engagementChartData}>
              <CartesianGrid strokeDasharray="3 3" stroke="rgba(255,255,255,0.05)" />
              <XAxis dataKey="date" tick={{ fill: 'rgba(255,255,255,0.3)', fontSize: 10 }} />
              <YAxis tick={{ fill: 'rgba(255,255,255,0.3)', fontSize: 10 }} />
              <Tooltip contentStyle={{ background: '#1a2235', border: '1px solid rgba(212,168,83,0.2)', borderRadius: '8px', color: '#faf8f5' }} />
              <Bar dataKey="activeUsers" fill="#d4a853" radius={[4, 4, 0, 0]} />
            </BarChart>
          </ResponsiveContainer>
        </div>

        {/* Revenue Breakdown */}
        <div className="bg-cosmic-800/40 border border-cosmic-600/20 rounded-xl p-6">
          <h3 className="font-semibold text-cream-50 mb-4">Revenue Breakdown</h3>
          <ResponsiveContainer width="100%" height={200}>
            <PieChart>
              <Pie data={data.revenueData} dataKey="amount" nameKey="category" cx="50%" cy="50%" outerRadius={70}>
                {data.revenueData.map((_, idx) => <Cell key={idx} fill={COLORS[idx % COLORS.length]} />)}
              </Pie>
              <Tooltip contentStyle={{ background: '#1a2235', border: '1px solid rgba(212,168,83,0.2)', borderRadius: '8px', color: '#faf8f5' }} />
            </PieChart>
          </ResponsiveContainer>
        </div>

        {/* Referral Funnel */}
        <div className="bg-cosmic-800/40 border border-cosmic-600/20 rounded-xl p-6">
          <h3 className="font-semibold text-cream-50 mb-4">Viral Funnel</h3>
          <div className="space-y-3">
            {[
              { label: 'Cards Generated', value: data.totalCardGenerations, pct: 100 },
              { label: 'Cards Shared', value: data.totalShares, pct: data.totalCardGenerations > 0 ? Math.round(data.totalShares / data.totalCardGenerations * 100) : 0 },
              { label: 'Card Views', value: data.totalCardViews, pct: data.totalCardGenerations > 0 ? Math.round(data.totalCardViews / data.totalCardGenerations * 100) : 0 },
              { label: 'Referral Registrations', value: data.totalReferralRegistrations, pct: data.totalReferrals > 0 ? Math.round(data.totalReferralRegistrations / data.totalReferrals * 100) : 0 },
            ].map((item) => (
              <div key={item.label}>
                <div className="flex justify-between text-sm mb-1">
                  <span className="text-cream-200/60">{item.label}</span>
                  <span className="text-cream-50 font-medium">{item.value}</span>
                </div>
                <div className="w-full bg-cosmic-700/50 rounded-full h-2">
                  <div className="bg-gold-400 h-2 rounded-full transition-all duration-500" style={{ width: `${Math.min(item.pct, 100)}%` }}></div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
