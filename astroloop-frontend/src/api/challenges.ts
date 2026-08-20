import api from './client';

export interface Challenge {
  id: number;
  title: string;
  description: string;
  category: string;
  rewardPoints: number;
  startDate: string;
  endDate: string;
  joined: boolean;
  completed: boolean;
  score: number | null;
}

export const challengeApi = {
  getChallenges: () => api.get<Challenge[]>('/challenges'),
  join: (id: number) => api.post<Challenge>(`/challenges/${id}/join`),
  complete: (id: number) => api.post<Challenge>(`/challenges/${id}/complete`),
};
