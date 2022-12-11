import { LoadingState } from './loading-state';

export interface RemoteAsset {
  loadingState: LoadingState;
  errors: any[];
}

export const defaultRemoteAsset: RemoteAsset = {
  loadingState: 'not loaded',
  errors: [],
};
