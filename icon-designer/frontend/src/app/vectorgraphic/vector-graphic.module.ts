import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { VectorGraphicComponent } from './vector-graphic.component';
import { VectorGraphicUpdateComponent } from './vector-graphic-update/vector-graphic-update.component';
import { VectorGraphicListComponent } from './vector-graphic-list/vector-graphic-list.component';
import { VectorGraphicDetailsComponent } from './vector-graphic-details/vector-graphic-details.component';

@NgModule({
  declarations: [VectorGraphicComponent, VectorGraphicUpdateComponent, VectorGraphicListComponent, VectorGraphicDetailsComponent],
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  exports: [VectorGraphicComponent],
})
export class VectorGraphicsModule {}
