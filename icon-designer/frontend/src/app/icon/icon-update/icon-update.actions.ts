import { VectorGraphicDTO } from 'src/app/vectorgraphic/vector-graphic-dto';
import { IconDTO } from '../dto/icon-dto';
import { IconLayerDTO } from '../dto/icon-layer-dto';
import { IconStackPosition } from '../icon-stack-position';

export class IconUpdateSetIconAction {
  static readonly type = '[IconUpdate] set icon';
  constructor(public entity: IconDTO | undefined) {}
}
export class IconUpdateSaveAction {
  static readonly type = '[IconUpdate] save';
  constructor(public onSaved?: () => void) {}
}
export class IconUpdateSavedAction {
  static readonly type = '[IconUpdate] saved';
  constructor(public entity: IconDTO) {}
}
export class IconUpdateRenderAction {
  static readonly type = '[IconUpdate] render';
  constructor(public onRendered?: () => void) {}
}
export class IconUpdateRenderedAction {
  static readonly type = '[IconUpdate] rendered';
  constructor(public entity: IconDTO) {}
}
export class IconUpdateSetNameAction {
  static readonly type = '[IconUpdate] set name';
  constructor(public name: string) {}
}
export class IconUpdateSetDimensionsAction {
  static readonly type = '[IconUpdate] set dimensions';
  constructor(public dimensions: number) {}
}
export class IconUpdateSelectIconStackAction {
  static readonly type = '[IconUpdate] select icon stack';
  constructor(public iconStackPosition?: IconStackPosition) {}
}
export class IconUpdateAddIconLayerAction {
  static readonly type = '[IconUpdate] add icon layer';
}
export class IconUpdateRemoveIconLayerAction {
  static readonly type = '[IconUpdate] remove icon layer';
  constructor(public iconLayer: IconLayerDTO) {}
}
export class IconUpdateSelectIconLayerAction {
  static readonly type = '[IconUpdate] select icon layer';
  constructor(public iconLayer?: number) {}
}
export class IconUpdateSelectVectorGrahpicAction {
  static readonly type = '[IconUpdate] select vector graphic';
  constructor(public vectorGraphic: VectorGraphicDTO) {}
}
