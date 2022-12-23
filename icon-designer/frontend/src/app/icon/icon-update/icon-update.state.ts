import { Injectable } from '@angular/core';
import { Action, Selector, State, StateContext } from '@ngxs/store';
import { patch } from '@ngxs/store/operators';
import { handleError } from 'src/core/handle-error';
import { defaultRemoteAsset } from 'src/core/remote-asset';
import { IconService } from '../icon.service';
import {
  IconUpdateRenderAction,
  IconUpdateRenderedAction,
  IconUpdateSaveAction,
  IconUpdateSavedAction,
  IconUpdateSetAction,
} from './icon-update.actions';
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
    return entity.icon;
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
    const icon = context.getState().icon;
    if (icon === undefined) {
      throw new Error('Attempt to save an undefined icon');
    }
    context.setState(patch<IconUpdateEntity>({ loadingState: 'loading' }));
    this.iconService.save(icon).subscribe({
      next: (response) => {
        context.dispatch(new IconUpdateSavedAction(response));
        if (onSaved) {
          onSaved();
        }
      },
      error: (error) => handleError({ context, error }),
    });
  }

  @Action(IconUpdateSavedAction)
  saved(context: StateContext<IconUpdateEntity>, { entity }: IconUpdateSavedAction) {
    context.setState(patch<IconUpdateEntity>({ loadingState: 'loaded', icon: entity }));
  }
  @Action(IconUpdateRenderAction)
  render(context: StateContext<IconUpdateEntity>, { onRendered }: IconUpdateRenderAction) {
    const icon = context.getState().icon;
    if (icon === undefined) {
      throw new Error('Attempt to rerender an undefined icon');
    }
    context.setState(patch<IconUpdateEntity>({ loadingState: 'loading' }));
    this.iconService.render(icon).subscribe({
      next: (response) => {
        context.dispatch(new IconUpdateRenderedAction(response));
        if (onRendered) {
          onRendered();
        }
      },
      error: (error) => handleError({ context, error }),
    });
  }
  @Action(IconUpdateRenderedAction)
  rendered(context: StateContext<IconUpdateEntity>, { entity }: IconUpdateRenderedAction) {
    context.setState(patch<IconUpdateEntity>({ loadingState: 'loaded', icon: entity }));
  }
}
