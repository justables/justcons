import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { VectorGraphicComponent } from './vector-graphic.component';
import { VectorgraphicUpdateComponent } from './vectorgraphic-update/vectorgraphic-update.component';

@NgModule({
  declarations: [VectorGraphicComponent, VectorgraphicUpdateComponent],
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  exports: [VectorGraphicComponent],
})
export class VectorGraphicsModule {}
