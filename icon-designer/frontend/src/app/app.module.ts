import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule } from '@angular/common/http';
import { NgxsModule } from '@ngxs/store';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { VectorGraphicsState } from './vectorgraphic/vector-graphic.state';
import { LayoutModule } from './layout/layout.module';

@NgModule({
  declarations: [AppComponent],
  imports: [BrowserModule, HttpClientModule, AppRoutingModule, LayoutModule, NgxsModule.forRoot([VectorGraphicsState])],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
