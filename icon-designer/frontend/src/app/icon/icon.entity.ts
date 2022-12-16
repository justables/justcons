import { RemoteAsset } from 'src/core/remote-asset';
import { IconDTO } from './dto/icon-dto';

export interface IconEntity extends RemoteAsset {
  response?: IconDTO[];
}
