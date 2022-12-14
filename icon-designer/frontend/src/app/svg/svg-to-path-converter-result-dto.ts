/** generated code */


export interface SvgToPathConverterResultDTO {
  dimensions: number;
  image: string;
  paths: string;
}

export class SvgToPathConverterResult {
  dimensions: number;
  image: string;
  paths: string;

  constructor(data: SvgToPathConverterResultDTO) {
    this.dimensions = data.dimensions;
    this.image = data.image;
    this.paths = data.paths;
  }
}