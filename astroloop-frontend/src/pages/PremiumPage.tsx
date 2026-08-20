import { useState, useEffect } from 'react';
import { premiumApi, type PremiumProduct } from '../api/premium';

export default function PremiumPage() {
  const [products, setProducts] = useState<PremiumProduct[]>([]);
  const [loading, setLoading] = useState(true);
  const [purchasing, setPurchasing] = useState<number | null>(null);
  const [purchased, setPurchased] = useState<number | null>(null);

  useEffect(() => { loadProducts(); }, []);

  const loadProducts = async () => {
    try {
      const res = await premiumApi.getProducts();
      setProducts(res.data);
    } catch (err) { console.error(err); }
    finally { setLoading(false); }
  };

  const handlePurchase = async (product: PremiumProduct) => {
    setPurchasing(product.id);
    try {
      await premiumApi.purchase(product.id);
      setPurchased(product.id);
      setTimeout(() => setPurchased(null), 2000);
    } catch (err) { console.error(err); }
    finally { setPurchasing(null); }
  };

  const categoryIcon: Record<string, string> = {
    report: '📋', membership: '👑', credits: '💳', challenge: '🏆',
  };

  const tierColor: Record<string, string> = {
    BASIC: 'text-cream-200/40', PLUS: 'text-gold-400', PRO: 'text-amber-400', ELITE: 'text-purple-400',
  };

  if (loading) return <div className="flex items-center justify-center py-20 text-gold-400">Loading premium products...</div>;

  return (
    <div className="space-y-8 animate-fade-in">
      <div className="text-center">
        <h1 className="text-3xl font-bold text-cream-50">Premium Experience</h1>
        <p className="text-cream-200/50 mt-1">Unlock deeper cosmic insights</p>
      </div>

      <div className="grid md:grid-cols-2 gap-6">
        {products.map((p) => (
          <div key={p.id} className="bg-cosmic-800/40 border border-cosmic-600/20 rounded-2xl p-6 hover:border-gold-400/15 transition-all relative overflow-hidden">
            {p.category === 'membership' && (
              <div className="absolute top-0 right-0 bg-gold-500 text-cosmic-900 text-xs font-semibold px-3 py-1 rounded-bl-lg">POPULAR</div>
            )}

            <div className="flex items-start gap-3 mb-4">
              <span className="text-3xl">{categoryIcon[p.category] || '⭐'}</span>
              <div>
                <h3 className="font-semibold text-cream-50">{p.name}</h3>
                <span className={`text-xs ${tierColor[p.tier] || 'text-cream-200/40'}`}>{p.tier} tier</span>
              </div>
            </div>

            <p className="text-cream-200/50 text-sm leading-relaxed mb-4">{p.description}</p>

            {p.features && (
              <div className="mb-4">
                <p className="text-cream-200/30 text-xs mb-1">Includes:</p>
                <p className="text-cream-200/60 text-xs">{p.features}</p>
              </div>
            )}

            <div className="flex items-center justify-between pt-4 border-t border-cosmic-600/20">
              <div>
                <span className="text-2xl font-bold text-gold-400">₹{p.price}</span>
                {p.category === 'membership' && <span className="text-cream-200/30 text-sm">/month</span>}
              </div>
              {purchased === p.id ? (
                <span className="px-6 py-2 text-emerald-400 text-sm font-medium">✓ Purchased!</span>
              ) : (
                <button onClick={() => handlePurchase(p)} disabled={purchasing === p.id}
                  className="px-6 py-2 bg-gold-500 text-cosmic-900 rounded-xl font-semibold hover:bg-gold-400 transition-all disabled:opacity-50 text-sm">
                  {purchasing === p.id ? 'Processing...' : 'Purchase'}
                </button>
              )}
            </div>

            <p className="text-gold-400/30 text-xs mt-2">⚠️ Simulated payment — demo/prototype only</p>
          </div>
        ))}
      </div>
    </div>
  );
}
