import { VectorGraphicsLoadAction, VectorGraphicsSelectAction } from './../vector-graphic.actions';
import { VectorGraphicDTO } from './../vector-graphic-dto';
import { VectorGraphicsState } from './../vector-graphic.state';
import { Component } from '@angular/core';
import { Select, Store } from '@ngxs/store';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-vector-graphics-list',
  templateUrl: './vector-graphic-list.component.html',
  styleUrls: ['./vector-graphic-list.component.scss'],
})
export class VectorGraphicListComponent {
  @Select(VectorGraphicsState.vectorGraphics)
  vectorGraphics$!: Observable<VectorGraphicDTO[] | undefined>;

  constructor(private store: Store) {}

  ngOnInit() {
    this.store.dispatch(new VectorGraphicsLoadAction());
  }

  onSelect(selected: VectorGraphicDTO) {
    if (selected === this.store.selectSnapshot(VectorGraphicsState.selected)) {
      this.store.dispatch(new VectorGraphicsSelectAction(undefined));
    } else {
      this.store.dispatch(new VectorGraphicsSelectAction(selected));
    }
  }
}
