import api from './client';

export interface AuthResponse {
  token: string;
  type: string;
  userId: number;
  email: string;
  firstName: string;
  lastName: string;
  hasProfile: boolean;
}

export const authApi = {
  register: (data: { email: string; password: string; firstName: string; lastName?: string; referralCode?: string }) =>
    api.post<AuthResponse>('/auth/register', data),
  login: (data: { email: string; password: string }) =>
    api.post<AuthResponse>('/auth/login', data),
};
