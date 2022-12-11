import { Component } from '@angular/core';
import { Select, Store } from '@ngxs/store';
import { Observable } from 'rxjs';
import { VectorGraphicDTO } from './vector-graphic-dto';
import { VectorGraphicsLoadAction } from './vector-graphic.actions';
import { VectorGraphicsState } from './vector-graphic.state';

@Component({
  selector: 'app-vector-graphics',
  templateUrl: './vector-graphic.component.html',
  styleUrls: ['./vector-graphic.component.scss'],
})
export class VectorGraphicsComponent {
  @Select(VectorGraphicsState.vectorGraphics)
  vectorGraphics$!: Observable<VectorGraphicDTO[] | undefined>;

  constructor(private store: Store) {}

  ngOnInit() {
    this.store.dispatch(new VectorGraphicsLoadAction());
  }
}
