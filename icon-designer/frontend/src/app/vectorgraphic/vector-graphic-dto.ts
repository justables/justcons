/** generated code */

import { VectorGraphicType } from './/vector-graphic-type';
export interface VectorGraphicDTO {
  id: string;
  image: string;
  name: string;
  paths: string;
  rotation: number;
  scale: number;
  type: VectorGraphicType;
  xTranslation: number;
  yTranslation: number;
}

export class VectorGraphic {
  id: string;
  image: string;
  name: string;
  paths: string;
  rotation: number;
  scale: number;
  type: VectorGraphicType;
  xTranslation: number;
  yTranslation: number;

  constructor(data: VectorGraphicDTO) {
    this.id = data.id;
    this.image = data.image;
    this.name = data.name;
    this.paths = data.paths;
    this.rotation = data.rotation;
    this.scale = data.scale;
    this.type = data.type;
    this.xTranslation = data.xTranslation;
    this.yTranslation = data.yTranslation;
  }
}