import { IconDTO } from '../dto/icon-dto';

export class IconUpdateSetAction {
  static readonly type = '[IconUpdate] set';
  constructor(public entity: IconDTO | undefined) {}
}
export class IconUpdateSaveAction {
  static readonly type = '[IconUpdate] save';
  constructor(public onSaved?: () => void) {}
}
export class IconUpdateSavedAction {
  static readonly type = '[IconUpdate] saved';
  constructor(public entity: IconDTO) {}
}