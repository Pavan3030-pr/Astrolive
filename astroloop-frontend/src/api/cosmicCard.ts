import api from './client';

export interface CosmicCard {
  id: number;
  userName: string;
  date: string;
  cosmicMessage: string;
  careerInsight: string;
  loveInsight: string;
  moneyInsight: string;
  energyScore: number;
  luckyElement: string;
  zodiacSign: string;
  shareId: string;
  viewCount: number;
  shareCount: number;
  createdAt: string;
}

export function getShareUrl(shareId: string): string {
  return `${window.location.origin}/cosmic-card/${shareId}`;
}

export const cosmicCardApi = {
  generate: () => api.post<CosmicCard>('/cosmic-card/generate'),
  getMyCards: () => api.get<CosmicCard[]>('/cosmic-card/my-cards'),
  getPublic: (shareId: string) => api.get<CosmicCard>(`/public/cosmic-card/${shareId}`),
  trackShare: (shareId: string) => api.post<CosmicCard>(`/cosmic-card/share/${shareId}`),
};
