import { VectorGraphicDTO } from './vector-graphic-dto';

export class VectorGraphicsLoadAction {
  static readonly type = '[VectorGraphics] load';
}
export class VectorGraphicsLoadedAction {
  static readonly type = '[VectorGraphics] loaded';
  constructor(public response: VectorGraphicDTO[]) {}
}
