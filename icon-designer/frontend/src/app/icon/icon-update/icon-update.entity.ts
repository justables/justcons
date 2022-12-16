import { RemoteAsset } from 'src/core/remote-asset';
import { IconDTO } from '../dto/icon-dto';

export interface IconUpdateEntity extends RemoteAsset {
  icon?: IconDTO;
}
