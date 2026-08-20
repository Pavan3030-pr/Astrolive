import api from './client';

export interface ProfileData {
  name: string;
  dateOfBirth: string;
  timeOfBirth?: string;
  placeOfBirth?: string;
  primaryInterest: 'CAREER' | 'LOVE' | 'MONEY' | 'GENERAL';
}

export interface ProfileResponse {
  id: number;
  name: string;
  dateOfBirth: string;
  timeOfBirth?: string;
  placeOfBirth?: string;
  primaryInterest: string;
  zodiacSign: string;
  moonSign: string;
  risingSign: string;
}

export const profileApi = {
  create: (data: ProfileData) => api.post<ProfileResponse>('/profile', data),
  get: () => api.get<ProfileResponse>('/profile'),
  update: (data: ProfileData) => api.put<ProfileResponse>('/profile', data),
};
