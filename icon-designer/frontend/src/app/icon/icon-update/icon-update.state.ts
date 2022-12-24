import { Injectable } from '@angular/core';
import { Action, createSelector, Selector, State, StateContext } from '@ngxs/store';
import { patch } from '@ngxs/store/operators';
import { handleError } from 'src/core/handle-error';
import { Preconditions } from 'src/core/preconditions';
import { defaultRemoteAsset } from 'src/core/remote-asset';
import { IconDTO } from '../dto/icon-dto';
import { IconLayerDTO } from '../dto/icon-layer-dto';
import { IconStackDTO } from '../dto/icon-stack-dto';
import { IconStackPosition } from '../icon-stack-position';
import { IconService } from '../icon.service';
import {
  IconUpdateAddIconLayerAction,
  IconUpdateRemoveIconLayerAction,
  IconUpdateRenderAction,
  IconUpdateRenderedAction,
  IconUpdateSaveAction,
  IconUpdateSavedAction,
  IconUpdateSelectIconLayerAction,
  IconUpdateSelectIconStackAction,
  IconUpdateSelectVectorGrahpicAction,
  IconUpdateSetDimensionsAction,
  IconUpdateSetIconAction,
  IconUpdateSetNameAction,
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
  static iconStack(position: IconStackPosition) {
    return createSelector([IconUpdateState], (state: IconUpdateEntity) => {
      return state.icon?.iconStacks.find((stack) => stack.position === position);
    });
  }
  @Selector()
  static selectedStack(entity: IconUpdateEntity): IconStackDTO | undefined {
    return entity.icon?.iconStacks.find((stack) => stack.position === entity.selectedStack);
  }
  @Selector()
  static iconLayers(entity: IconUpdateEntity): IconLayerDTO[] | undefined {
    return entity.icon?.iconStacks
      .find((stack) => stack.position === entity.selectedStack)
      ?.iconLayers.sort((a, b) => a.sortPosition - b.sortPosition);
  }
  @Selector()
  static selectedLayer(entity: IconUpdateEntity): IconLayerDTO | undefined {
    return entity.icon?.iconStacks
      .find((stack) => stack.position === entity.selectedStack)
      ?.iconLayers.find((layer) => layer.sortPosition === entity.selectedLayer);
  }
  @Selector()
  static showVectorGraphics(entity: IconUpdateEntity) {
    return entity.selectedLayer !== undefined;
  }
  @Action(IconUpdateSetIconAction)
  set(context: StateContext<IconUpdateEntity>, { entity }: IconUpdateSetIconAction) {
    context.setState(
      patch<IconUpdateEntity>({
        loadingState: 'loaded',
        icon: entity,
      })
    );
  }
  @Action(IconUpdateSaveAction)
  save(context: StateContext<IconUpdateEntity>, { onSaved }: IconUpdateSaveAction) {
    const icon = this.requireIcon(context);
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
    const icon = this.requireIcon(context);
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
  @Action(IconUpdateSetNameAction)
  setName(context: StateContext<IconUpdateEntity>, { name }: IconUpdateSetNameAction) {
    this.requireIcon(context);
    context.setState(
      patch<IconUpdateEntity>({
        icon: patch<IconDTO>({
          name,
        }),
      })
    );
  }
  @Action(IconUpdateSetDimensionsAction)
  setDimensions(context: StateContext<IconUpdateEntity>, { dimensions }: IconUpdateSetDimensionsAction) {
    this.requireIcon(context);
    context.setState(
      patch<IconUpdateEntity>({
        icon: patch<IconDTO>({
          dimensions,
        }),
      })
    );
  }
  @Action(IconUpdateSelectIconStackAction)
  selectIconStack(
    context: StateContext<IconUpdateEntity>,
    { iconStackPosition: iconStack }: IconUpdateSelectIconStackAction
  ) {
    context.setState(
      patch<IconUpdateEntity>({
        selectedStack: iconStack,
      })
    );
  }
  @Action(IconUpdateAddIconLayerAction)
  addIconLayer(context: StateContext<IconUpdateEntity>) {
    const icon = this.requireIcon(context);
    const currentIconStack = { ...this.requireCurrentIconStack(context) };
    currentIconStack.iconLayers = [
      ...currentIconStack.iconLayers,
      {
        id: undefined,
        sortPosition: currentIconStack.iconLayers.length,
        vectorGraphic: undefined,
      },
    ];
    const iconStacks: IconStackDTO[] = [
      ...icon.iconStacks.filter((iconStack) => iconStack.position !== currentIconStack.position),
      currentIconStack,
    ];

    context.setState(
      patch<IconUpdateEntity>({
        icon: patch<IconDTO>({
          iconStacks,
        }),
      })
    );
  }
  @Action(IconUpdateRemoveIconLayerAction)
  removeIconLayer(context: StateContext<IconUpdateEntity>, { iconLayer }: IconUpdateRemoveIconLayerAction) {
    const icon = this.requireIcon(context);
    const currentIconStack = this.requireCurrentIconStack(context);
    currentIconStack.iconLayers = [
      ...currentIconStack.iconLayers.filter((iconLayer_) => iconLayer_.sortPosition !== iconLayer.sortPosition),
    ];
    const iconStacks: IconStackDTO[] = [
      ...icon.iconStacks.filter((iconStack) => iconStack.position !== currentIconStack.position),
      currentIconStack,
    ];
    context.setState(
      patch<IconUpdateEntity>({
        icon: patch<IconDTO>({
          iconStacks,
        }),
      })
    );
  }
  @Action(IconUpdateSelectIconLayerAction)
  selectIconLayer(context: StateContext<IconUpdateEntity>, { iconLayer }: IconUpdateSelectIconLayerAction) {
    context.setState(patch<IconUpdateEntity>({ selectedLayer: iconLayer }));
  }
  @Action(IconUpdateSelectVectorGrahpicAction)
  selectVectorGraphic(context: StateContext<IconUpdateEntity>, { vectorGraphic }: IconUpdateSelectVectorGrahpicAction) {
    const icon = this.requireIcon(context);
    const selectedLayer = this.requireSelectedLayer(context);
    const currentIconStack = this.requireCurrentIconStack(context);
    currentIconStack.iconLayers = [
      ...currentIconStack.iconLayers.filter((iconLayer_) => iconLayer_.sortPosition !== selectedLayer.sortPosition),
      {
        ...selectedLayer,
        vectorGraphic,
      },
    ];
    const iconStacks: IconStackDTO[] = [
      ...icon.iconStacks.filter((iconStack) => iconStack.position !== currentIconStack.position),
      currentIconStack,
    ];
    context.setState(
      patch<IconUpdateEntity>({
        icon: patch<IconDTO>({
          iconStacks,
        }),
      })
    );
  }
  private requireIcon(context: StateContext<IconUpdateEntity>): IconDTO {
    const icon = context.getState().icon;
    return Preconditions.notNull(icon);
  }
  private requireSelectedStackPosition(context: StateContext<IconUpdateEntity>): IconStackPosition {
    const iconStack = context.getState().selectedStack;
    if (iconStack === undefined) {
      throw new Error('iconStack is undefined');
    }
    return iconStack;
  }
  private requireCurrentIconStack(context: StateContext<IconUpdateEntity>): IconStackDTO {
    const icon = this.requireIcon(context);
    const selectedStackPosition = this.requireSelectedStackPosition(context);
    const currentStack = icon.iconStacks.find((stack) => stack.position === selectedStackPosition);
    return Preconditions.notNull(currentStack);
  }
  private requireSelectedLayer(context: StateContext<IconUpdateEntity>): IconLayerDTO {
    const selectedLayer = Preconditions.notNull(context.getState().selectedLayer);
    const currentIconStack = this.requireCurrentIconStack(context);
    const currentIconLayer = currentIconStack.iconLayers.find((layer) => layer.sortPosition === selectedLayer);
    return Preconditions.notNull(currentIconLayer);
  }
}
