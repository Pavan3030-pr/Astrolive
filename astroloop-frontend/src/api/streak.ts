import api from './client';

export interface StreakData {
  currentStreak: number;
  longestStreak: number;
  totalCheckIns: number;
  lastCheckInDate: string;
  checkedInToday: boolean;
}

export const streakApi = {
  get: () => api.get<StreakData>('/streak'),
  checkIn: () => api.post<StreakData>('/streak/check-in'),
};
