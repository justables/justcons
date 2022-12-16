import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IconComponent } from './icon.component';
import { RouterModule } from '@angular/router';
import { IconUpdateComponent } from './icon-update/icon-update.component';
import { IconListComponent } from './icon-list/icon-list.component';
import { UtilModule } from '../util/util.module';

@NgModule({
  declarations: [IconComponent, IconUpdateComponent, IconListComponent],
  imports: [CommonModule, RouterModule, UtilModule],
  exports: [IconComponent],
})
export class IconModule {}
