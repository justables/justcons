import { RemoteAsset } from 'src/core/remote-asset';
import { IconDTO } from '../dto/icon-dto';
import { IconStackPosition } from '../icon-stack-position';

export interface IconUpdateEntity extends RemoteAsset {
  icon?: IconDTO;
  selectedStack?: IconStackPosition;
  selectedLayer?: number;
}
