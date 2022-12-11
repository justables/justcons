import { VectorGraphicDTO } from 'src/app/api/icon/vectorgraphic/vector-graphic-dto';

export class IconLoadAction {
  static readonly type = '[IconState] load';
}

export class IconLoadedAction {
  static readonly type = '[IconState] loaded';
  constructor(public response: VectorGraphicDTO[]) {}
}
