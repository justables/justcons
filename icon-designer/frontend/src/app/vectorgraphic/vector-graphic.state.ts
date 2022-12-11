import { Injectable } from '@angular/core';
import { Action, Selector, State, StateContext } from '@ngxs/store';
import { VectorGraphicsLoadAction, VectorGraphicsLoadedAction } from './vector-graphic.actions';
import { VectorGraphicsEntity } from './vector-graphic.entity';
import { patch } from '@ngxs/store/operators';
import { VectorGraphicService } from './vector-graphic.service';
import { handleError } from 'src/core/handle-error';

@State<VectorGraphicsEntity>({
  name: 'VectorGraphicsState',
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
  @Action(VectorGraphicsLoadAction)
  load(context: StateContext<VectorGraphicsEntity>) {
    context.setState(
      patch<VectorGraphicsEntity>({
        loadingState: 'loading',
      })
    );
    this.vectorGraphicService.getAll().subscribe({
      next: (response) => {
        context.dispatch(new VectorGraphicsLoadedAction(response));
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
}
