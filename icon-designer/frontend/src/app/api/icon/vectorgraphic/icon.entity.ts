import { RemoteAsset } from 'src/app/api/core/RemoteAsset';
import { VectorGraphicDTO } from 'src/app/api/icon/vectorgraphic/vector-graphic-dto';

export interface IconEntity extends RemoteAsset {
  response?: VectorGraphicDTO[];
}
