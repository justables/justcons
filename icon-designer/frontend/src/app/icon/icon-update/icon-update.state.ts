import { Injectable } from '@angular/core';
import { Action, Selector, State, StateContext } from '@ngxs/store';
import { patch } from '@ngxs/store/operators';
import { handleError } from 'src/core/handle-error';
import { defaultRemoteAsset } from 'src/core/remote-asset';
import { IconService } from '../icon.service';
import { IconUpdateSaveAction, IconUpdateSavedAction, IconUpdateSetAction } from './icon-update.actions';
import { IconUpdateEntity } from './icon-update.entity';

@State<IconUpdateEntity>({
  name: 'IconUpdateState',
  defaults: defaultRemoteAsset,
})
@Injectable()
export class IconUpdateState {
  constructor(private iconService: IconService) {}
  @Selector()
  static icon(entity: IconUpdateEntity) {
    return entity;
  }
  @Action(IconUpdateSetAction)
  set(context: StateContext<IconUpdateEntity>, { entity }: IconUpdateSetAction) {
    context.setState(
      patch<IconUpdateEntity>({
        loadingState: 'loaded',
        icon: entity,
      })
    );
  }
  @Action(IconUpdateSaveAction)
  save(context: StateContext<IconUpdateEntity>, { onSaved }: IconUpdateSaveAction) {
    context.setState(patch<IconUpdateEntity>({ loadingState: 'loading' }));
    // this.iconService.save(context.getState().icon).subscribe({
    //   next: (response) => {
    //     context.dispatch(new IconUpdateSavedAction(response));
    //     if (onSaved) {
    //       onSaved();
    //     }
    //   },
    //   error: (error) => handleError({ context, error }),
    // });
  }

  @Action(IconUpdateSavedAction)
  saved(context: StateContext<IconUpdateEntity>, { entity }: IconUpdateSavedAction) {
    context.setState(patch<IconUpdateEntity>({ loadingState: 'loaded', icon: entity }));
  }
}
