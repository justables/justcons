import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-base64-image',
  template: `<img [class]="classes" id="base64image" [src]="'data:image/jpeg;base64,' + img" />`,
  styles: [
    `
      img {
        filter: invert(1);
      }
    `,
  ],
})
export class Base64ImageComponent {
  @Input()
  img = '';

  @Input()
  classes = 'w-16 h-16';
}
