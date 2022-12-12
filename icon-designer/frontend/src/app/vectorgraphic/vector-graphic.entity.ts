import { RemoteAsset } from 'src/core/remote-asset';
import { SvgToPathConverterResultDTO } from '../svg/svg-to-path-converter-result-dto';
import { VectorGraphicDTO } from './vector-graphic-dto';

export interface VectorGraphicsEntity extends RemoteAsset {
  response?: VectorGraphicDTO[];
  selected?: VectorGraphicDTO;
  preview?: SvgToPathConverterResultDTO;
}
