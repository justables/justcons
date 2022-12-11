import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  standalone: true,
  templateUrl: 'index.html',
})
export default class HomeComponent {
  count = 0;

  increment() {
    this.count++;
  }
}
