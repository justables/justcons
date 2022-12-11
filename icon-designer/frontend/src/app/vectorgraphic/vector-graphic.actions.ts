import { VectorGraphicDTO } from './vector-graphic-dto';

export class VectorGraphicsLoadAction {
  static readonly type = '[VectorGraphics] load';
  constructor(public onLoaded?: () => void) {}
}
export class VectorGraphicsLoadedAction {
  static readonly type = '[VectorGraphics] loaded';
  constructor(public response: VectorGraphicDTO[]) {}
}
export class VectorGraphicsSaveAction {
  static readonly type = '[VectorGraphics] save';
  constructor(public request: VectorGraphicDTO[], public onSaved?: () => void) {}
}
export class VectorGraphicsSavedAction {
  static readonly type = '[VectorGraphics] saved';
  constructor(public response: VectorGraphicDTO[]) {}
}
export class VectorGraphicsDeleteAction {
  static readonly type = '[VectorGraphics] delete';
  constructor(public request: VectorGraphicDTO[], public onDeleted?: () => void) {}
}
export class VectorGraphicsDeletedAction {
  static readonly type = '[VectorGraphics] deleted';
  constructor(public response: VectorGraphicDTO[]) {}
}
export class VectorGraphicsSelectAction {
  static readonly type = '[VectorGraphics] select';
  constructor(public selected?: VectorGraphicDTO) {}
}
