/** generated code */

import { VectorGraphicDTO } from '../../vectorgraphic/vector-graphic-dto';
export interface IconLayerDTO {
  id: string | undefined;
  sortPosition: number;
  vectorGraphic: VectorGraphicDTO | undefined;
}

export class IconLayer {
  id: string | undefined;
  sortPosition: number;
  vectorGraphic: VectorGraphicDTO | undefined;

  constructor(data: IconLayerDTO) {
    this.id = data.id;
    this.sortPosition = data.sortPosition;
    this.vectorGraphic = data.vectorGraphic;
  }
}