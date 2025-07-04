import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { SpinnerOverlayComponent } from './spinner-overlay.component';
@Component({
  selector: 'app-root',
  imports: [RouterOutlet, SpinnerOverlayComponent],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected title = 'Frontend';
}
