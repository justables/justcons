import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Base64ImageComponent } from './base64-image.component';

@NgModule({
  declarations: [Base64ImageComponent],
  imports: [CommonModule],
  exports: [Base64ImageComponent],
})
export class UtilModule {}
