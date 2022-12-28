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
import { SvgToPathConverterRequestDTO } from 'src/app/svg/svg-to-path-converter-request-dto';

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
  svgFileFormControl = new FormControl<string | null>(null);
  iconNameFormControl = new FormControl<string>('new icon', [Validators.required, Validators.minLength(2)]);
  translationXFormControl = new FormControl<number>(0);
  translationYFormControl = new FormControl<number>(0);
  scaleFormControl = new FormControl<number>(1);
  rotationFormControl = new FormControl<number>(0);
  iconMaskFormControl = new FormControl<boolean>(false, [Validators.required]);

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
        const request: SvgToPathConverterRequestDTO = {
          svg,
          primaryColor: 'ffffff',
          secondaryColor: 'fafafa',
        };
        this.svgToPathConverterService.svgToPath(request).subscribe((response) => {
          this.vectorGraphic.image = response.image;
          this.vectorGraphic.paths = response.paths;
          this.vectorGraphic.dimensions = response.dimensions;
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
      iconFilledTypeFormControl: this.iconMaskFormControl,
      translationXFormControl: this.translationXFormControl,
      translationYFormControl: this.translationYFormControl,
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
        dimensions: 24,
        translationX: 0,
        translationY: 0,
        scale: 1,
        rotation: 0,
        mask: false,
      };
    }
  }

  private fillForm() {
    this.iconNameFormControl.setValue(this.vectorGraphic.name);
    this.translationXFormControl.setValue(this.vectorGraphic.translationX);
    this.translationYFormControl.setValue(this.vectorGraphic.translationY);
    this.scaleFormControl.setValue(this.vectorGraphic.scale);
    this.rotationFormControl.setValue(this.vectorGraphic.rotation);
    this.iconMaskFormControl.setValue(this.vectorGraphic.mask);
  }

  private parseIcon(): VectorGraphicDTO {
    return {
      id: this.vectorGraphic.id,
      image: undefined,
      name: Preconditions.notNull(this.iconNameFormControl.value),
      paths: this.vectorGraphic.paths,
      dimensions: this.vectorGraphic.dimensions,
      mask: this.iconMaskFormControl.value ?? false,
      translationX: this.translationXFormControl.value ?? 0,
      translationY: this.translationYFormControl.value ?? 0,
      scale: this.scaleFormControl.value ?? 1,
      rotation: this.rotationFormControl.value ?? 0,
    };
  }

  private navigateBack() {
    this.router.navigate(['/vector-graphics']);
  }
}
