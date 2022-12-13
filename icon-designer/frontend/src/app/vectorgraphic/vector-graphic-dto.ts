/** generated code */


export interface VectorGraphicDTO {
  id: string | undefined;
  image: string | undefined;
  mask: boolean;
  name: string;
  paths: string;
  rotation: number;
  scale: number;
  translationX: number;
  translationY: number;
}

export class VectorGraphic {
  id: string | undefined;
  image: string | undefined;
  mask: boolean;
  name: string;
  paths: string;
  rotation: number;
  scale: number;
  translationX: number;
  translationY: number;

  constructor(data: VectorGraphicDTO) {
    this.id = data.id;
    this.image = data.image;
    this.mask = data.mask;
    this.name = data.name;
    this.paths = data.paths;
    this.rotation = data.rotation;
    this.scale = data.scale;
    this.translationX = data.translationX;
    this.translationY = data.translationY;
  }
}