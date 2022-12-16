import { Injectable } from '@angular/core';
import { Action, Selector, State, StateContext } from '@ngxs/store';
import { patch } from '@ngxs/store/operators';
import { handleError } from 'src/core/handle-error';
import { defaultRemoteAsset } from 'src/core/remote-asset';
import { IconLoadAction, IconLoadedAction } from './icon.actions';
import { IconEntity } from './icon.entity';
import { IconService } from './icon.service';

@State<IconEntity>({
  name: 'IconState',
  defaults: defaultRemoteAsset,
})
@Injectable()
export class IconState {
  constructor(private iconService: IconService) {}
  @Selector()
  static loadingState(entity: IconEntity) {
    return entity.loadingState;
  }
  @Selector()
  static icons(entity: IconEntity) {
    return entity.response;
  }
  @Action(IconLoadAction)
  load(context: StateContext<IconEntity>, { onLoaded }: IconLoadAction) {
    context.setState(patch<IconEntity>({ loadingState: 'loading' }));
    this.iconService.getAll().subscribe({
      next: (response) => {
        context.dispatch(new IconLoadedAction(response));
        if (onLoaded) {
          onLoaded();
        }
      },
      error: (error) => handleError({ context, error }),
    });
  }
  @Action(IconLoadedAction)
  loaded(context: StateContext<IconEntity>, { response }: IconLoadedAction) {
    context.setState(patch<IconEntity>({ loadingState: 'loaded', response }));
  }
}
