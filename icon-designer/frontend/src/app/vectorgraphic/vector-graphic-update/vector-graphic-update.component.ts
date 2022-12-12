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
import { SvgToPathConverterService } from 'src/app/svg/svg-to-path-converter.service';

@Component({
  selector: 'app-vectorgraphic-update',
  templateUrl: './vector-graphic-update.component.html',
  styleUrls: ['./vector-graphic-update.component.scss'],
})
export class VectorGraphicUpdateComponent {
  @Select(VectorGraphicsState.loadingState)
  loadingState$!: Observable<LoadingState>;

  vectorGraphic!: VectorGraphicDTO;

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

  constructor(
    private store: Store,
    private formBuilder: FormBuilder,
    private router: Router,
    private svgToPathConverterService: SvgToPathConverterService
  ) {}

  ngOnInit() {
    this.initForm();
    this.initVectorGraphic();
    this.fillForm();
  }

  onFileChanged(event: any) {
    const file = event.target.files[0];
    if (file) {
      var reader = new FileReader();
      reader.readAsText(file, 'UTF-8');
      reader.onload = (evt: any) => {
        const svg = evt.target.result;
        this.svgToPathConverterService.svgToPath(svg).subscribe((response) => {
          this.vectorGraphic.image = response.image;
          this.vectorGraphic.paths = response.paths;
        });
      };
      reader.onerror = (evt: any) => {
        throw new Error(evt.target.result);
      };
    }
  }

  onSave() {
    this.store.dispatch(new VectorGraphicsSaveAction([this.parseIcon()], () => this.navigateBack()));
  }

  onDiscard() {
    this.navigateBack();
  }

  private initForm() {
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

  private initVectorGraphic() {
    const selected = this.store.selectSnapshot(VectorGraphicsState.selected);
    if (selected !== undefined) {
      this.vectorGraphic = selected;
    } else {
      this.vectorGraphic = {
        id: undefined,
        image: undefined,
        name: 'new icon',
        paths: '',
        type: 'Filled',
        xTranslation: 0,
        yTranslation: 0,
        scale: 1,
        rotation: 0,
      };
    }
  }

  private fillForm() {
    this.iconNameFormControl.setValue(this.vectorGraphic.name);
    this.iconFilledTypeFormControl.setValue(this.vectorGraphic.type);
    this.xTranslationFormControl.setValue(this.vectorGraphic.xTranslation);
    this.yTranslationFormControl.setValue(this.vectorGraphic.yTranslation);
    this.scaleFormControl.setValue(this.vectorGraphic.scale);
    this.rotationFormControl.setValue(this.vectorGraphic.rotation);
  }

  private parseIcon(): VectorGraphicDTO {
    return {
      id: this.vectorGraphic.id,
      image: undefined,
      name: Preconditions.notNull(this.iconNameFormControl.value),
      paths: this.vectorGraphic.paths,
      type: Preconditions.in<VectorGraphicType>(
        Preconditions.notNull(this.iconFilledTypeFormControl.value) as VectorGraphicType,
        allVectorGraphicType
      ),
      xTranslation: parseInt(this.xTranslationFormControl.value ?? '0'),
      yTranslation: parseInt(this.yTranslationFormControl.value ?? '0'),
      scale: parseInt(this.scaleFormControl.value ?? '1'),
      rotation: parseInt(this.rotationFormControl.value ?? '0'),
    };
  }

  private navigateBack() {
    this.router.navigate(['/icons']);
  }
}
