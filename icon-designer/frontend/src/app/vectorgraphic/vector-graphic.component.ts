import { Router } from '@angular/router';
import { VectorGraphicsSelectAction } from './vector-graphic.actions';
import { Observable } from 'rxjs';
import { VectorGraphicsState } from './vector-graphic.state';
import { Component } from '@angular/core';
import { Select, Store } from '@ngxs/store';
import { VectorGraphicDTO } from './vector-graphic-dto';

@Component({
  selector: 'app-vector-graphics',
  templateUrl: './vector-graphic.component.html',
  styleUrls: ['./vector-graphic.component.scss'],
})
export class VectorGraphicComponent {
  @Select(VectorGraphicsState.selected)
  selected$!: Observable<VectorGraphicDTO | undefined>;

  constructor(private store: Store, private router: Router) {}

  onAdd() {
    this.store.dispatch(new VectorGraphicsSelectAction());
    this.router.navigate(['/update-icon']);
  }
}
