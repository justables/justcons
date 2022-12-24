import { IconDTO } from './dto/icon-dto';

export class IconLoadAction {
  static readonly type = '[Icon] load';
  constructor(public onLoaded?: () => void) {}
}
export class IconLoadedAction {
  static readonly type = '[Icon] loaded';
  constructor(public response: IconDTO[]) {}
}
export class IconDeleteAction {
  static readonly type = '[Icon] delete';
  constructor(public request: IconDTO[], public onDeleted?: () => void) {}
}
export class IconDeletedAction {
  static readonly type = '[Icon] deleted';
  constructor(public response: IconDTO[]) {}
}
