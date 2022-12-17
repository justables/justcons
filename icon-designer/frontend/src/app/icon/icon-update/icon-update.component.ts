import { Component, Input } from '@angular/core';
import { Select, Store } from '@ngxs/store';
import { Observable } from 'rxjs';
import { VectorGraphicDTO } from 'src/app/vectorgraphic/vector-graphic-dto';
import { VectorGraphicsLoadAction } from 'src/app/vectorgraphic/vector-graphic.actions';
import { VectorGraphicsState } from 'src/app/vectorgraphic/vector-graphic.state';
import { IconDTO } from '../dto/icon-dto';
import { IconLayer } from '../dto/icon-layer-dto';
import { IconStack, IconStackDTO } from '../dto/icon-stack-dto';
import { IconStackPosition } from '../icon-stack-position';
import { IconService } from '../icon.service';
import { IconUpdateRenderAction, IconUpdateSetAction } from './icon-update.actions';
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

  @Input()
  iconDTO?: IconDTO = {
    id: undefined,
    name: 'unnamed icon',
    iconStack: [],
    image: undefined,
  };

  _selectedIconStack: IconStackPosition = 'Center';
  get selectedIconStack() {
    return this._selectedIconStack;
  }
  set selectedIconStack(value: IconStackPosition) {
    this._selectedIconStack = value;
    this.currentIconLayer = this.iconDTO?.iconStack.find(
      (stack) => stack.position === this.selectedIconStack
    )?.iconLayer[0];
  }

  get currentStack() {
    return this.getStackByPosition(this.selectedIconStack);
  }

  currentIconLayer: IconLayer | undefined;

  constructor(private store: Store, private iconService: IconService) {}

  ngOnInit() {
    this.store.dispatch(new IconUpdateSetAction(this.iconDTO));
    this.store.dispatch(new VectorGraphicsLoadAction());
  }

  getStackByPosition(position: IconStackPosition): IconStackDTO {
    const iconStack = this.iconDTO?.iconStack.find((stack) => stack.position === position);
    if (iconStack !== undefined) {
      return iconStack;
    }
    const newIconStack: IconStack = { id: undefined, position: position, iconLayer: [], image: undefined };
    this.iconDTO?.iconStack.push(newIconStack);
    return newIconStack;
  }

  onAddLayer() {
    this.currentStack.iconLayer.push({
      id: undefined,
      sortPosition: this.currentStack.iconLayer.length,
      vectorGraphic: undefined,
    });
  }

  onSelectVectorGraphic(vectorGraphic: VectorGraphicDTO) {
    if (this.currentIconLayer) {
      this.currentIconLayer.vectorGraphic = vectorGraphic;
    }
    // this.iconService.renderStack(this.currentStack).subscribe((response) => {
    //   this.currentStack.image = response;
    // });
    this.rerenderIcon();
  }

  private rerenderIcon() {
    this.store.dispatch(new IconUpdateSetAction(this.iconDTO));
    this.store.dispatch(
      new IconUpdateRenderAction(() => {
        this.iconDTO = this.store.selectSnapshot(IconUpdateState.icon);
        this.store.dispatch(new IconUpdateSetAction(this.iconDTO));
      })
    );
  }
}
