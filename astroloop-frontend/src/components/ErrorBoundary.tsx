import { Component, type ReactNode } from 'react';

interface Props { children: ReactNode; }
interface State { hasError: boolean; error: Error | null; }

export default class ErrorBoundary extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = { hasError: false, error: null };
  }

  static getDerivedStateFromError(error: Error) {
    return { hasError: true, error };
  }

  render() {
    if (this.state.hasError) {
      return (
        <div className="min-h-screen bg-cosmic-900 flex items-center justify-center px-4">
          <div className="text-center max-w-md">
            <span className="text-4xl">🌌</span>
            <h1 className="text-2xl font-bold text-cream-50 mt-4 mb-2">Something went wrong</h1>
            <p className="text-cream-200/50 text-sm mb-6">
              The cosmos encountered an unexpected error. Please try again.
            </p>
            <button
              onClick={() => { this.setState({ hasError: false, error: null }); window.location.reload(); }}
              className="bg-gold-500 text-cosmic-900 px-6 py-3 rounded-xl font-semibold hover:bg-gold-400 transition-all"
            >
              Try Again
            </button>
          </div>
        </div>
      );
    }
    return this.props.children;
  }
}
