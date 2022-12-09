import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CatComponent } from './cat.component';

@NgModule({
  declarations: [CatComponent],
  exports: [CatComponent],
  imports: [CommonModule],
})
export class CatModule {}
