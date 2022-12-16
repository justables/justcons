/** generated code */

import { IconStackDTO } from './/icon-stack-dto';
export interface IconDTO {
  iconStack: IconStackDTO[];
  id: string | undefined;
  image: string | undefined;
  name: string;
}

export class Icon {
  iconStack: IconStackDTO[];
  id: string | undefined;
  image: string | undefined;
  name: string;

  constructor(data: IconDTO) {
    this.iconStack = data.iconStack;
    this.id = data.id;
    this.image = data.image;
    this.name = data.name;
  }
}