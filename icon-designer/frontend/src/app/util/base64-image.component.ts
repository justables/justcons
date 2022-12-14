import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-base64-image',
  template: `<img [class]="classes" id="base64image" [src]="'data:image/jpeg;base64,' + (img || emptyImage)" />`,
  styles: [
    `
      img {
        @apply invert dark:invert-0;
      }
    `,
  ],
})
export class Base64ImageComponent {
  @Input()
  img = '';

  @Input()
  classes = 'w-16 h-16';

  emptyImage = 'iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=';
}
