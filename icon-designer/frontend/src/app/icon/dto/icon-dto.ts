/** generated code */

import { IconStackDTO } from './/icon-stack-dto';
export interface IconDTO {
  dimensions: number;
  iconStacks: IconStackDTO[];
  id: string | undefined;
  image: string | undefined;
  name: string;
}

export class Icon {
  dimensions: number;
  iconStacks: IconStackDTO[];
  id: string | undefined;
  image: string | undefined;
  name: string;

  constructor(data: IconDTO) {
    this.dimensions = data.dimensions;
    this.iconStacks = data.iconStacks;
    this.id = data.id;
    this.image = data.image;
    this.name = data.name;
  }
}