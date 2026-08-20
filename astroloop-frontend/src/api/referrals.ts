import api from './client';

export interface ReferralStats {
  totalReferrals: number;
  registeredReferrals: number;
  conversionRate: number;
}

export const referralApi = {
  getStats: () => api.get<ReferralStats>('/referrals/stats'),
};
