/** generated code */


export interface SvgToPathConverterResultDTO {
  image: string;
  paths: string;
}

export class SvgToPathConverterResult {
  image: string;
  paths: string;

  constructor(data: SvgToPathConverterResultDTO) {
    this.image = data.image;
    this.paths = data.paths;
  }
}