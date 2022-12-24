/** generated code */

import { IconLayerDTO } from './/icon-layer-dto';
import { IconStackPosition } from '../icon-stack-position';
export interface IconStackDTO {
  iconLayers: IconLayerDTO[];
  id: string | undefined;
  image: string | undefined;
  position: IconStackPosition;
}

export class IconStack {
  iconLayers: IconLayerDTO[];
  id: string | undefined;
  image: string | undefined;
  position: IconStackPosition;

  constructor(data: IconStackDTO) {
    this.iconLayers = data.iconLayers;
    this.id = data.id;
    this.image = data.image;
    this.position = data.position;
  }
}