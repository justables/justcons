import { Injectable } from '@angular/core';
import { Action, Selector, State, StateContext } from '@ngxs/store';
import { patch } from '@ngxs/store/operators';
import { handleError } from 'src/core/handle-error';
import { SvgToPathConverterService } from '../svg/svg-to-path-converter.service';
import { defaultRemoteAsset, RemoteAsset } from './../../core/remote-asset';
import {
  VectorGraphicConvertAction,
  VectorGraphicConvertedAction,
  VectorGraphicsDeleteAction,
  VectorGraphicsDeletedAction,
  VectorGraphicsLoadAction,
  VectorGraphicsLoadedAction,
  VectorGraphicsSaveAction,
  VectorGraphicsSavedAction,
  VectorGraphicsSelectAction,
} from './vector-graphic.actions';
import { VectorGraphicsEntity } from './vector-graphic.entity';
import { VectorGraphicService } from './vector-graphic.service';

@State<VectorGraphicsEntity>({
  name: 'VectorGraphicsState',
  defaults: defaultRemoteAsset,
})
@Injectable()
export class VectorGraphicsState {
  constructor(
    private vectorGraphicService: VectorGraphicService,
    private svgToPathConverterService: SvgToPathConverterService
  ) {}
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
  @Action(VectorGraphicsDeleteAction)
  delete(context: StateContext<VectorGraphicsEntity>, { request, onDeleted }: VectorGraphicsDeleteAction) {
    context.setState(
      patch<VectorGraphicsEntity>({
        loadingState: 'loading',
      })
    );
    this.vectorGraphicService.delete(request).subscribe({
      next: (response) => {
        context.dispatch(new VectorGraphicsDeletedAction(response));
        if (onDeleted) {
          onDeleted();
        }
      },
      error: (error) => handleError({ context, error }),
    });
  }
  @Action(VectorGraphicsDeletedAction)
  deleted(context: StateContext<VectorGraphicsEntity>, { response }: VectorGraphicsDeletedAction) {
    const state = context.getState();
    context.setState(
      patch<VectorGraphicsEntity>({
        loadingState: 'loaded',
        response: state.response?.filter(
          (vectorGrahic) => response.filter((res) => res.id === vectorGrahic.id).length === 0
        ),
      })
    );
  }
  @Action(VectorGraphicConvertAction)
  convert(context: StateContext<VectorGraphicsEntity>, { request, onConverted }: VectorGraphicConvertAction) {
    context.setState(
      patch<VectorGraphicsEntity>({
        loadingState: 'loading',
      })
    );
    this.svgToPathConverterService.svgToPath(request).subscribe({
      next: (response) => {
        context.dispatch(new VectorGraphicConvertedAction(response));
        if (onConverted) {
          onConverted();
        }
      },
      error: (error) => handleError({ context, error }),
    });
  }
  @Action(VectorGraphicConvertedAction)
  converted(context: StateContext<VectorGraphicsEntity>, { response }: VectorGraphicConvertedAction) {
    context.setState(
      patch<VectorGraphicsEntity>({
        loadingState: 'loaded',
        preview: response,
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
