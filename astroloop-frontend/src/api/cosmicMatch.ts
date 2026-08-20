import api from './client';

export interface CosmicMatchResult {
  user1Name: string;
  user2Name: string;
  user1Sign: string;
  user2Sign: string;
  overallScore: number;
  loveScore: number;
  communicationScore: number;
  lifeAlignmentScore: number;
  strengths: string[];
  friction: string[];
  suggestedAction: string;
  summary: string;
}

export const cosmicMatchApi = {
  calculate: (data: { partnerDateOfBirth: string; partnerName: string }) =>
    api.post<CosmicMatchResult>('/cosmic-match', data),
};
