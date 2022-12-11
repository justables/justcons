import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VectorGraphicComponent } from './vector-graphic.component';
import { VectorgraphicUpdateComponent } from './vectorgraphic-update/vectorgraphic-update.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [VectorGraphicComponent, VectorgraphicUpdateComponent],
  imports: [CommonModule, RouterModule],
  exports: [VectorGraphicComponent],
})
export class VectorGraphicsModule {}
