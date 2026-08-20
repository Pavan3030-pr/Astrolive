export function Skeleton({ className = '' }: { className?: string }) {
  return (
    <div className={`bg-cosmic-700/30 rounded-xl animate-pulse ${className}`} />
  );
}

export function DashboardSkeleton() {
  return (
    <div className="space-y-6 animate-pulse">
      <div className="h-10 w-64 bg-cosmic-700/30 rounded-lg" />
      <div className="h-6 w-48 bg-cosmic-700/20 rounded-lg" />
      <div className="bg-cosmic-800/40 rounded-2xl p-8">
        <div className="flex items-center gap-6">
          <div className="w-24 h-24 bg-cosmic-700/30 rounded-full" />
          <div className="flex-1 space-y-3">
            <div className="h-4 w-32 bg-cosmic-700/30 rounded" />
            <div className="h-6 w-full bg-cosmic-700/30 rounded" />
          </div>
        </div>
      </div>
      <div className="grid grid-cols-3 gap-4">
        {[1, 2, 3].map((i) => (
          <div key={i} className="h-32 bg-cosmic-700/20 rounded-xl" />
        ))}
      </div>
    </div>
  );
}

export function CardSkeleton() {
  return (
    <div className="bg-cosmic-800/40 rounded-2xl p-8 space-y-4">
      <div className="h-8 w-48 bg-cosmic-700/30 rounded mx-auto" />
      <div className="h-16 w-24 bg-cosmic-700/30 rounded mx-auto" />
      <div className="h-20 bg-cosmic-700/20 rounded-xl" />
      <div className="grid grid-cols-3 gap-3">
        {[1, 2, 3].map((i) => (
          <div key={i} className="h-24 bg-cosmic-700/20 rounded-lg" />
        ))}
      </div>
    </div>
  );
}

export function AstrologerSkeleton() {
  return (
    <div className="bg-cosmic-800/40 rounded-2xl p-6 space-y-4">
      <div className="flex items-center gap-4">
        <div className="w-14 h-14 bg-cosmic-700/30 rounded-full" />
        <div className="flex-1 space-y-2">
          <div className="h-5 w-32 bg-cosmic-700/30 rounded" />
          <div className="h-3 w-48 bg-cosmic-700/20 rounded" />
        </div>
      </div>
    </div>
  );
}
