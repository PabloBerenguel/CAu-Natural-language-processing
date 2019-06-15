import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { FullscreenOverlayContainer, OverlayContainer, OverlayModule } from '@angular/cdk/overlay';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';


@NgModule( {
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    OverlayModule,
    MatProgressSpinnerModule,
    HttpClientModule,
    BrowserAnimationsModule,
  ],
  providers: [
    { provide: OverlayContainer, useClass: FullscreenOverlayContainer }
  ],
  bootstrap: [ AppComponent ]
} )
export class AppModule {
}
