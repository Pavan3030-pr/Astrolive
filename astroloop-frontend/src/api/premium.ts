import api from './client';

export interface PremiumProduct {
  id: number;
  name: string;
  description: string;
  price: number;
  category: string;
  tier: string;
  features: string;
}

export interface PurchaseResponse {
  id: number;
  productName: string;
  amount: number;
  status: string;
  simulated: boolean;
  createdAt: string;
}

export const premiumApi = {
  getProducts: () => api.get<PremiumProduct[]>('/premium/products'),
  purchase: (productId: number) => api.post<PurchaseResponse>('/premium/purchase', { productId }),
  getMyPurchases: () => api.get<PurchaseResponse[]>('/premium/purchases'),
};
