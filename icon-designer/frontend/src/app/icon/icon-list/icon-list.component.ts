import { Component } from '@angular/core';
import { Select, Store } from '@ngxs/store';
import { Observable } from 'rxjs';
import { IconDTO } from '../dto/icon-dto';
import { IconDeleteAction, IconLoadAction } from '../icon.actions';
import { IconState } from '../icon.state';

@Component({
  selector: 'app-icon-list',
  templateUrl: './icon-list.component.html',
  styleUrls: ['./icon-list.component.scss'],
})
export class IconListComponent {
  @Select(IconState.icons)
  icons$!: Observable<IconDTO[] | undefined>;

  constructor(private store: Store) {}

  ngOnInit(): void {
    this.store.dispatch(new IconLoadAction());
  }

  onDelete(icon: IconDTO) {
    this.store.dispatch(
      new IconDeleteAction([icon], () => {
        this.store.dispatch(new IconLoadAction());
      })
    );
  }
}
