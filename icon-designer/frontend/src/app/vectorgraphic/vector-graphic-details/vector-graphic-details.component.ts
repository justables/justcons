import { VectorGraphicsDeleteAction, VectorGraphicsSelectAction } from './../vector-graphic.actions';
import { Store } from '@ngxs/store';
import { Component, Input } from '@angular/core';
import { Preconditions } from './../../../core/preconditions';
import { VectorGraphicDTO } from './../vector-graphic-dto';

@Component({
  selector: 'app-vector-graphic-details',
  templateUrl: './vector-graphic-details.component.html',
  styleUrls: ['./vector-graphic-details.component.scss'],
})
export class VectorGraphicDetailsComponent {
  @Input()
  vectorGraphic!: VectorGraphicDTO;

  constructor(private store: Store) {}

  ngOnInit() {
    Preconditions.notNull(this.vectorGraphic);
  }

  onDelete() {
    console.log('foo');
    this.store.dispatch(
      new VectorGraphicsDeleteAction([this.vectorGraphic], () => this.store.dispatch(new VectorGraphicsSelectAction()))
    );
  }
}
