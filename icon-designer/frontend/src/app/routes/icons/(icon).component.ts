import { Component, OnInit } from '@angular/core';
import { NgxsModule, Select, Store } from '@ngxs/store';
import { Observable } from 'rxjs';
import { IconState } from 'src/app/api/icon/vectorgraphic/icon.state';
import { VectorGraphicDTO } from 'src/app/api/icon/vectorgraphic/vector-graphic-dto';
import { IconLoadAction } from '../../api/icon/vectorgraphic/icon.actions';

@Component({
  selector: 'app-about',
  standalone: true,
  templateUrl: '(icon).component.html',
  styleUrls: ['(icon).component.css'],
  imports: [NgxsModule.forRoot([IconState])],
})
export default class IconPageComponent implements OnInit {
  // @Select(IconState.icons)
  // icons!: Observable<VectorGraphicDTO[] | undefined>;
  constructor(private store: Store) {}

  ngOnInit(): void {
    this.store.dispatch(new IconLoadAction());
  }
}
