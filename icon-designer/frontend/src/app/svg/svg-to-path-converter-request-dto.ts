/** generated code */


export interface SvgToPathConverterRequestDTO {
  primaryColor: string;
  secondaryColor: string;
  svg: string;
}

export class SvgToPathConverterRequest {
  primaryColor: string;
  secondaryColor: string;
  svg: string;

  constructor(data: SvgToPathConverterRequestDTO) {
    this.primaryColor = data.primaryColor;
    this.secondaryColor = data.secondaryColor;
    this.svg = data.svg;
  }
}