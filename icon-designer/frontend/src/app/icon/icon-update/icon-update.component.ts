import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Select, Store } from '@ngxs/store';
import { Observable } from 'rxjs';
import { VectorGraphicDTO } from 'src/app/vectorgraphic/vector-graphic-dto';
import { VectorGraphicsLoadAction } from 'src/app/vectorgraphic/vector-graphic.actions';
import { VectorGraphicsState } from 'src/app/vectorgraphic/vector-graphic.state';
import { IconDTO } from '../dto/icon-dto';
import { IconLayerDTO } from '../dto/icon-layer-dto';
import { IconStack, IconStackDTO } from '../dto/icon-stack-dto';
import { IconStackPosition } from '../icon-stack-position';
import {
  IconUpdateAddIconLayerAction,
  IconUpdateRemoveIconLayerAction,
  IconUpdateRenderAction,
  IconUpdateSaveAction,
  IconUpdateSelectIconLayerAction,
  IconUpdateSelectIconStackAction,
  IconUpdateSelectVectorGrahpicAction,
  IconUpdateSetIconAction,
} from './icon-update.actions';
import { IconUpdateState } from './icon-update.state';

@Component({
  selector: 'app-icon-update',
  templateUrl: './icon-update.component.html',
  styleUrls: ['./icon-update.component.scss'],
})
export class IconUpdateComponent {
  @Select(VectorGraphicsState.vectorGraphics)
  vectorGraphics$!: Observable<VectorGraphicDTO[] | undefined>;

  @Select(IconUpdateState.icon)
  icon$!: Observable<IconDTO | undefined>;

  @Select(IconUpdateState.selectedStack)
  selectedStack$!: Observable<IconStackDTO | undefined>;

  @Select(IconUpdateState.iconLayers)
  iconLayers$!: Observable<IconLayerDTO[] | undefined>;

  @Select(IconUpdateState.selectedLayer)
  selectedLayer$!: Observable<IconLayerDTO | undefined>;

  @Select(IconUpdateState.showVectorGraphics)
  showVectorGraphics$!: Observable<boolean>;

  @Select(IconUpdateState.iconStack('Center'))
  centerStack$!: Observable<IconStackDTO | undefined>;
  @Select(IconUpdateState.iconStack('TopRight'))
  topRightStack$!: Observable<IconStackDTO | undefined>;
  @Select(IconUpdateState.iconStack('BottomRight'))
  bottomRightStack$!: Observable<IconStackDTO | undefined>;
  @Select(IconUpdateState.iconStack('BottomLeft'))
  bottomLeftStack$!: Observable<IconStackDTO | undefined>;
  @Select(IconUpdateState.iconStack('TopLeft'))
  topLeftStack$!: Observable<IconStackDTO | undefined>;

  @Input()
  iconDTO?: IconDTO = {
    id: undefined,
    name: 'unnamed icon',
    dimensions: 24,
    iconStacks: [
      { id: undefined, position: 'Center', iconLayers: [], image: undefined },
      { id: undefined, position: 'TopRight', iconLayers: [], image: undefined },
      { id: undefined, position: 'BottomRight', iconLayers: [], image: undefined },
      { id: undefined, position: 'BottomLeft', iconLayers: [], image: undefined },
      { id: undefined, position: 'TopLeft', iconLayers: [], image: undefined },
    ],
    image: undefined,
  };

  constructor(private store: Store, private router: Router) {}

  ngOnInit() {
    this.store.dispatch(new IconUpdateSetIconAction(this.iconDTO));
    this.store.dispatch(new VectorGraphicsLoadAction());
  }

  getStackByPosition(position: IconStackPosition): IconStackDTO {
    const iconStack = this.iconDTO?.iconStacks.find((stack) => stack.position === position);
    if (iconStack !== undefined) {
      return iconStack;
    }
    const newIconStack: IconStack = { id: undefined, position: position, iconLayers: [], image: undefined };
    this.iconDTO?.iconStacks.push(newIconStack);
    return newIconStack;
  }

  onSelectStack(stackPosition: IconStackPosition) {
    this.store.dispatch(new IconUpdateSelectIconStackAction(stackPosition));
  }

  onSelectLayer(layer: IconLayerDTO) {
    this.store.dispatch(new IconUpdateSelectIconLayerAction(layer.sortPosition));
  }

  onAddLayer() {
    this.store.dispatch(new IconUpdateAddIconLayerAction());
  }

  onRemoveLayer(layer: IconLayerDTO) {
    this.store.dispatch(new IconUpdateRemoveIconLayerAction(layer));
    this.store.dispatch(new IconUpdateRenderAction());
  }

  onSelectVectorGraphic(vectorGraphic: VectorGraphicDTO) {
    this.store.dispatch(new IconUpdateSelectVectorGrahpicAction(vectorGraphic));
    this.store.dispatch(new IconUpdateRenderAction());
  }

  onSave() {
    this.store.dispatch(
      new IconUpdateSaveAction(() => {
        this.navigateBack();
      })
    );
  }

  onDiscard() {
    this.navigateBack();
  }

  private navigateBack() {
    this.router.navigate(['/icons']);
  }
}
