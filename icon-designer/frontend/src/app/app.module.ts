import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule } from '@angular/common/http';
import { NgxsModule } from '@ngxs/store';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { VectorGraphicsState } from './vectorgraphic/vector-graphic.state';
import { LayoutModule } from './layout/layout.module';
import { IconState } from './icon/icon.state';
import { IconUpdateState } from './icon/icon-update/icon-update.state';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    LayoutModule,
    NgxsModule.forRoot([VectorGraphicsState, IconState, IconUpdateState]),
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
