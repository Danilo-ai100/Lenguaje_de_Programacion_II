import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

// Standalone components are imported via routes or directly where needed, not here
import { AppRoutingModule } from './app-routing.module';

@NgModule({
  declarations: [],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    CommonModule,
    RouterModule
  ],
  // Bootstrap should be handled by the router or main layout if using standalone components
  // Remove bootstrap: [LoginComponent] if using standalone entry
})
export class AppModule {}
