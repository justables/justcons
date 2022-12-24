import { Injectable } from '@angular/core';
import { Action, Selector, State, StateContext } from '@ngxs/store';
import { patch } from '@ngxs/store/operators';
import { handleError } from 'src/core/handle-error';
import { defaultRemoteAsset } from 'src/core/remote-asset';
import { IconDTO } from './dto/icon-dto';
import { IconDeleteAction, IconDeletedAction, IconLoadAction, IconLoadedAction } from './icon.actions';
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
  @Action(IconDeleteAction)
  delete(context: StateContext<IconEntity>, { request, onDeleted }: IconDeleteAction) {
    context.setState(patch<IconEntity>({ loadingState: 'loading' }));
    this.iconService.delete(request).subscribe({
      next: (response) => {
        context.dispatch(new IconDeletedAction(response));
        if (onDeleted) {
          onDeleted();
        }
      },
      error: (error) => handleError({ context, error }),
    });
  }
  @Action(IconDeletedAction)
  deleted(context: StateContext<IconEntity>, { response }: IconDeletedAction) {
    const state = context.getState();
    context.setState(
      patch<IconEntity>({
        loadingState: 'loaded',
        response: state.response?.filter(
          (vectorGrahic) => response.filter((res) => res.id === vectorGrahic.id).length === 0
        ),
      })
    );
  }
}
