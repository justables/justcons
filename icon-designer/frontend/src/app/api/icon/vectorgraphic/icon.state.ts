import { Injectable } from '@angular/core';
import { Action, Selector, State, StateContext } from '@ngxs/store';
import { patch } from '@ngxs/store/operators';
import { VectorGraphicService } from 'src/app/api/icon/vectorgraphic/vector-graphic.service';
import { IconLoadAction, IconLoadedAction } from './icon.actions';
import { IconEntity } from './icon.entity';

@State<IconEntity>({
  name: 'IconState',
})
@Injectable({ providedIn: 'root' })
export class IconState {
  constructor(private vectorGraphicService: VectorGraphicService) {}

  @Selector()
  static loadingState(context: IconEntity) {
    return context.loadingState;
  }

  @Selector()
  static icons(context: IconEntity) {
    return context.response;
  }

  @Action(IconLoadAction)
  load(context: StateContext<IconEntity>) {
    context.setState(
      patch<IconEntity>({
        loadingState: 'Loading',
      }),
    );
    this.vectorGraphicService.getAll().subscribe({
      next: (response) => context.dispatch(new IconLoadedAction(response)),
    });
  }

  @Action(IconLoadedAction)
  loaded(context: StateContext<IconEntity>, { response }: IconLoadedAction) {
    context.setState(
      patch<IconEntity>({
        loadingState: 'Loaded',
        response,
      }),
    );
  }
}
