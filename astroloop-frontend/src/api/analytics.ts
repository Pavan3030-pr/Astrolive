import api from './client';

export interface AnalyticsData {
  totalUsers: number;
  newUsersLast7Days: number;
  dailyActiveUsers: number;
  totalCardGenerations: number;
  totalCardViews: number;
  totalShares: number;
  totalReferralRegistrations: number;
  totalReferrals: number;
  referralConversionRate: number;
  viralCoefficient: number;
  d1Retention: number;
  d7Retention: number;
  d30Retention: number;
  premiumUsers: number;
  premiumConversionRate: number;
  totalConsultations: number;
  consultationConversionRate: number;
  totalRevenue: number;
  arpu: number;
  userGrowthData: { date: string; users: number }[];
  engagementData: { date: string; activeUsers: number }[];
  revenueData: { category: string; amount: number }[];
}

export const analyticsApi = {
  get: () => api.get<AnalyticsData>('/analytics'),
};
