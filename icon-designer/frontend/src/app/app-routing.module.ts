import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VectorGraphicComponent } from './vectorgraphic/vector-graphic.component';
import { VectorGraphicsModule } from './vectorgraphic/vector-graphic.module';
import { VectorGraphicUpdateComponent } from './vectorgraphic/vector-graphic-update/vector-graphic-update.component';
import { VectorGraphicBulkUploadComponent } from './vectorgraphic/vector-graphic-bulk-upload/vector-graphic-bulk-upload.component';
import { IconComponent } from './icon/icon.component';
import { IconModule } from './icon/icon.module';
import { IconUpdateComponent } from './icon/icon-update/icon-update.component';

const routes: Routes = [
  { path: '', redirectTo: 'icons', pathMatch: 'full' },
  { path: 'vector-graphics', component: VectorGraphicComponent },
  { path: 'vector-graphics/update', component: VectorGraphicUpdateComponent },
  { path: 'vector-graphics/bulk-upload', component: VectorGraphicBulkUploadComponent },
  { path: 'icons', component: IconComponent },
  { path: 'icons/update', component: IconUpdateComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes), VectorGraphicsModule, IconModule],
  exports: [RouterModule],
})
export class AppRoutingModule {}
