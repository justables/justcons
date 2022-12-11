import { allVectorGraphicType, VectorGraphicType } from '../vector-graphic-type';
import { Preconditions } from '../../../core/preconditions';
import { VectorGraphicDTO } from '../vector-graphic-dto';
import { VectorGraphicsSaveAction } from '../vector-graphic.actions';
import { LoadingState } from '../../../core/loading-state';
import { Observable } from 'rxjs';
import { VectorGraphicsState } from '../vector-graphic.state';
import { Select, Store } from '@ngxs/store';
import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-vectorgraphic-update',
  templateUrl: './vector-graphic-update.component.html',
  styleUrls: ['./vector-graphic-update.component.scss'],
})
export class VectorGraphicUpdateComponent {
  @Select(VectorGraphicsState.loadingState)
  loadingState$!: Observable<LoadingState>;

  form!: FormGroup;
  svgFileFormControl = new FormControl();
  iconNameFormControl = new FormControl('new icon', [Validators.required, Validators.minLength(6)]);
  foregroundFormControl = new FormControl();
  backgroundFormControl = new FormControl();
  iconFilledTypeFormControl = new FormControl('Filled', [Validators.required]);
  xTranslationFormControl = new FormControl();
  yTranslationFormControl = new FormControl();
  scaleFormControl = new FormControl();
  rotationFormControl = new FormControl();

  constructor(private store: Store, private formBuilder: FormBuilder, private router: Router) {}

  ngOnInit() {
    this.form = this.formBuilder.group({
      svgFile: this.svgFileFormControl,
      iconNameFormControl: this.iconNameFormControl,
      foregroundFormControl: this.foregroundFormControl,
      backgroundFormControl: this.backgroundFormControl,
      iconFilledTypeFormControl: this.iconFilledTypeFormControl,
      xTranslationFormControl: this.xTranslationFormControl,
      yTranslationFormControl: this.yTranslationFormControl,
      scaleFormControl: this.scaleFormControl,
      rotationFormControl: this.rotationFormControl,
    });
  }

  onSave() {
    this.store.dispatch(new VectorGraphicsSaveAction([this.parseIcon()], () => this.navigateBack()));
  }

  onDiscard() {
    this.navigateBack();
  }

  private parseIcon(): VectorGraphicDTO {
    return {
      id: undefined,
      image: undefined,
      name: Preconditions.notNull(this.iconNameFormControl.value),
      paths: '',
      type: Preconditions.in<VectorGraphicType>(
        Preconditions.notNull(this.iconFilledTypeFormControl.value) as VectorGraphicType,
        allVectorGraphicType
      ),
      xTranslation: parseInt(this.xTranslationFormControl.value ?? '0'),
      yTranslation: parseInt(this.yTranslationFormControl.value ?? '0'),
      scale: parseInt(this.scaleFormControl.value ?? '0'),
      rotation: parseInt(this.rotationFormControl.value ?? '0'),
    };
  }

  private navigateBack() {
    this.router.navigate(['/icons']);
  }
}
