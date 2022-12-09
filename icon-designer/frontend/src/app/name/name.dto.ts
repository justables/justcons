/** generated code */

export interface NameDTO {
  fullName: string;
  shortName: string;
}

export class Name {
  fullName: string;
  shortName: string;

  constructor(data: NameDTO) {
    this.fullName = data.fullName;
    this.shortName = data.shortName;
  }
}
