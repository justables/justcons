import { StateContext } from '@ngxs/store';
import { patch } from '@ngxs/store/operators';
import { RemoteAsset } from './remote-asset';

export function handleError({ context, error }: { context: StateContext<RemoteAsset>; error: any[] }) {
  context.setState(
    patch<RemoteAsset>({
      loadingState: 'error',
      error,
    })
  );
  console.error(error);
}
