/** generated code */

import { IconLayerDTO } from './/icon-layer-dto';
import { IconStackPosition } from '../icon-stack-position';
export interface IconStackDTO {
  iconLayer: IconLayerDTO[];
  id: string | undefined;
  image: string | undefined;
  position: IconStackPosition;
}

export class IconStack {
  iconLayer: IconLayerDTO[];
  id: string | undefined;
  image: string | undefined;
  position: IconStackPosition;

  constructor(data: IconStackDTO) {
    this.iconLayer = data.iconLayer;
    this.id = data.id;
    this.image = data.image;
    this.position = data.position;
  }
}