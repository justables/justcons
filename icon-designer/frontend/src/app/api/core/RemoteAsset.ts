import { LoadingState } from './LoadingState';

export interface RemoteAsset {
  loadingState: LoadingState;
  backendError: any[];
}
