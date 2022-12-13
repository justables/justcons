import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VectorGraphicComponent } from './vectorgraphic/vector-graphic.component';
import { VectorGraphicsModule } from './vectorgraphic/vector-graphic.module';
import { VectorGraphicUpdateComponent } from './vectorgraphic/vector-graphic-update/vector-graphic-update.component';
import { VectorGraphicBulkUploadComponent } from './vectorgraphic/vector-graphic-bulk-upload/vector-graphic-bulk-upload.component';

const routes: Routes = [
  { path: '', redirectTo: 'icons', pathMatch: 'full' },
  { path: 'icons', component: VectorGraphicComponent },
  { path: 'update-icon', component: VectorGraphicUpdateComponent },
  { path: 'bulk-upload', component: VectorGraphicBulkUploadComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes), VectorGraphicsModule],
  exports: [RouterModule],
})
export class AppRoutingModule {}
