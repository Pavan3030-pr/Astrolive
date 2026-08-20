import api from './client';

export interface DashboardData {
  greeting: string;
  cosmicBrief: string;
  careerInsight: string;
  loveInsight: string;
  moneyInsight: string;
  dailyQuestion: string;
  energyScore: number;
  luckyElement: string;
  currentStreak: number;
  longestStreak: number;
  totalCheckIns: number;
  completedChallenges: number;
  totalAchievements: number;
  hasProfile: boolean;
}

export const dashboardApi = {
  get: () => api.get<DashboardData>('/dashboard'),
};
