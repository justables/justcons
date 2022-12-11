import { defaultRemoteAsset } from './../../core/remote-asset';
import { Injectable } from '@angular/core';
import { Action, Selector, State, StateContext } from '@ngxs/store';
import {
  VectorGraphicsLoadAction,
  VectorGraphicsLoadedAction,
  VectorGraphicsSaveAction,
  VectorGraphicsSavedAction,
  VectorGraphicsSelectAction,
} from './vector-graphic.actions';
import { VectorGraphicsEntity } from './vector-graphic.entity';
import { patch } from '@ngxs/store/operators';
import { VectorGraphicService } from './vector-graphic.service';
import { handleError } from 'src/core/handle-error';

@State<VectorGraphicsEntity>({
  name: 'VectorGraphicsState',
  defaults: defaultRemoteAsset,
})
@Injectable()
export class VectorGraphicsState {
  constructor(private vectorGraphicService: VectorGraphicService) {}
  @Selector()
  static loadingState(entity: VectorGraphicsEntity) {
    return entity.loadingState;
  }
  @Selector()
  static vectorGraphics(entity: VectorGraphicsEntity) {
    return entity.response;
  }
  @Selector()
  static selected(entity: VectorGraphicsEntity) {
    return entity.selected;
  }
  @Action(VectorGraphicsLoadAction)
  load(context: StateContext<VectorGraphicsEntity>, { onLoaded }: VectorGraphicsLoadAction) {
    context.setState(
      patch<VectorGraphicsEntity>({
        loadingState: 'loading',
      })
    );
    this.vectorGraphicService.getAll().subscribe({
      next: (response) => {
        context.dispatch(new VectorGraphicsLoadedAction(response));
        if (onLoaded) {
          onLoaded();
        }
      },
      error: (error) => handleError({ context, error }),
    });
  }
  @Action(VectorGraphicsLoadedAction)
  loaded(context: StateContext<VectorGraphicsEntity>, { response }: VectorGraphicsLoadedAction) {
    context.setState(
      patch<VectorGraphicsEntity>({
        loadingState: 'loaded',
        response,
      })
    );
  }
  @Action(VectorGraphicsSaveAction)
  save(context: StateContext<VectorGraphicsEntity>, { request, onSaved }: VectorGraphicsSaveAction) {
    context.setState(
      patch<VectorGraphicsEntity>({
        loadingState: 'loading',
      })
    );
    this.vectorGraphicService.save(request).subscribe({
      next: (response) => {
        context.dispatch(new VectorGraphicsSavedAction(response));
        if (onSaved) {
          onSaved();
        }
      },
      error: (error) => handleError({ context, error }),
    });
  }
  @Action(VectorGraphicsSavedAction)
  saved(context: StateContext<VectorGraphicsEntity>, { response }: VectorGraphicsSavedAction) {
    context.setState(
      patch<VectorGraphicsEntity>({
        loadingState: 'loaded',
        response,
      })
    );
  }
  @Action(VectorGraphicsSelectAction)
  select(context: StateContext<VectorGraphicsEntity>, { selected }: VectorGraphicsSelectAction) {
    context.setState(
      patch<VectorGraphicsEntity>({
        selected,
      })
    );
  }
}
