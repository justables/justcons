import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VectorGraphicComponent } from './vectorgraphic/vector-graphic.component';
import { VectorGraphicsModule } from './vectorgraphic/vector-graphic.module';
import { VectorgraphicUpdateComponent } from './vectorgraphic/vectorgraphic-update/vectorgraphic-update.component';

const routes: Routes = [
  { path: '', component: VectorGraphicComponent },
  { path: 'update-icon', component: VectorgraphicUpdateComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes), VectorGraphicsModule],
  exports: [RouterModule],
})
export class AppRoutingModule {}
