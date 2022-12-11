import { LoadingState } from './loading-state';

export interface RemoteAsset {
  loadingState: LoadingState;
  error: any[];
}
