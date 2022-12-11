import { RemoteAsset } from 'src/core/remote-asset';
import { VectorGraphicDTO } from './vector-graphic-dto';

export interface VectorGraphicsEntity extends RemoteAsset {
  response?: VectorGraphicDTO[];
  selected?: VectorGraphicDTO;
}
