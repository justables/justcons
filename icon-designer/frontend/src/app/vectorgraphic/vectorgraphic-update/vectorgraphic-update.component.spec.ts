import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VectorgraphicUpdateComponent } from './vectorgraphic-update.component';

describe('VectorgraphicUpdateComponent', () => {
  let component: VectorgraphicUpdateComponent;
  let fixture: ComponentFixture<VectorgraphicUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VectorgraphicUpdateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VectorgraphicUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
