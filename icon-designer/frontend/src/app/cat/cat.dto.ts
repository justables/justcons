/** generated code */

import { NameDTO } from '../name/name.dto';

export interface CatDTO {
  age: number;
  id: number;
  imageUrl: string;
  name: NameDTO;
}

export class Cat {
  age: number;
  id: number;
  imageUrl: string;
  name: NameDTO;

  constructor(data: CatDTO) {
    this.age = data.age;
    this.id = data.id;
    this.imageUrl = data.imageUrl;
    this.name = data.name;
  }
}
