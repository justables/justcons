import { IconDTO } from './dto/icon-dto';

export class IconLoadAction {
  static readonly type = '[Icon] load';
  constructor(public onLoaded?: () => void) {}
}
export class IconLoadedAction {
  static readonly type = '[Icon] loaded';
  constructor(public response: IconDTO[]) {}
}
