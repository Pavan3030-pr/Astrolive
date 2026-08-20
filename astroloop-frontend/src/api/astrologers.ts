import api from './client';

export interface Astrologer {
  id: number;
  name: string;
  avatarUrl: string;
  bio: string;
  verified: boolean;
  expertise: string;
  languages: string[];
  experienceYears: number;
  rating: number;
  pricePerSession: number;
  availability: string;
  totalSessions: number;
  recommendedReasons: string[];
  recommendationScore: number;
}

export interface ConsultationData {
  astrologerId: number;
  concern: string;
  consultationType: 'CHAT' | 'VOICE' | 'VIDEO';
  scheduledTime: string;
}

export interface ConsultationResponse {
  id: number;
  astrologerName: string;
  concern: string;
  consultationType: string;
  amount: number;
  status: string;
  scheduledTime: string;
  paymentSimulated: boolean;
  createdAt: string;
}

export const astrologerApi = {
  search: (params?: { expertise?: string; language?: string }) =>
    api.get<Astrologer[]>('/astrologers', { params }),
  getRecommended: () => api.get<Astrologer[]>('/astrologers/recommended'),
  getOne: (id: number) => api.get<Astrologer>(`/astrologers/${id}`),
  book: (data: ConsultationData) => api.post<ConsultationResponse>('/consultations', data),
  getMyConsultations: () => api.get<ConsultationResponse[]>('/consultations'),
};
