import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VectorGraphicsComponent } from './vectorgraphic/vector-graphic.component';
import { VectorGraphicsModule } from './vectorgraphic/vector-graphic.module';

const routes: Routes = [{ path: '', component: VectorGraphicsComponent }];

@NgModule({
  imports: [RouterModule.forRoot(routes), VectorGraphicsModule],
  exports: [RouterModule],
})
export class AppRoutingModule {}
