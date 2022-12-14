import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Select, Store } from '@ngxs/store';
import { Observable } from 'rxjs';
import { SvgToPathConverterResultDTO } from 'src/app/svg/svg-to-path-converter-result-dto';
import { SvgToPathConverterService } from 'src/app/svg/svg-to-path-converter.service';
import { LoadingState } from 'src/core/loading-state';
import { VectorGraphicsSaveAction } from '../vector-graphic.actions';
import { VectorGraphicsState } from '../vector-graphic.state';

const defaultPrimaryColor = 'ffffff';
const defaultSecondaryColor = 'aaaaaa';

@Component({
  selector: 'app-vector-graphic-bulk-upload',
  templateUrl: './vector-graphic-bulk-upload.component.html',
  styleUrls: ['./vector-graphic-bulk-upload.component.scss'],
})
export class VectorGraphicBulkUploadComponent {
  @Select(VectorGraphicsState.loadingState)
  loadingState$!: Observable<LoadingState>;

  vectorGraphics: (SvgToPathConverterResultDTO & { name: string; svg: string })[] = [];

  form!: FormGroup;
  svgFileFormControl = new FormControl();
  primaryColorFormControl = new FormControl(defaultPrimaryColor);
  secondaryColorFormControl = new FormControl(defaultSecondaryColor);

  constructor(
    private store: Store,
    private formBuilder: FormBuilder,
    private router: Router,
    private svgToPathConverterService: SvgToPathConverterService
  ) {}

  ngOnInit() {
    this.initForm();
  }

  onFileChanged(event: any) {
    Array.from(event.target.files).forEach((file: any) => {
      var reader = new FileReader();
      reader.readAsText(file, 'UTF-8');
      reader.onload = (evt: any) => {
        const svg = evt.target.result;
        this.svgToPathConverterService
          .svgToPath({
            svg: svg,
            primaryColor: this.primaryColorFormControl.value ?? defaultPrimaryColor,
            secondaryColor: this.secondaryColorFormControl.value ?? defaultSecondaryColor,
          })
          .subscribe((response) => {
            this.vectorGraphics.push({ ...response, name: file.name, svg });
          });
      };
      reader.onerror = (evt: any) => {
        throw new Error(evt.target.result);
      };
    });
  }

  onRerender() {
    const vectorGraphics = [...this.vectorGraphics];
    this.vectorGraphics = [];
    vectorGraphics.forEach((vectorGraphic) => {
      this.svgToPathConverterService
        .svgToPath({
          svg: vectorGraphic.svg,
          primaryColor: this.primaryColorFormControl.value ?? defaultPrimaryColor,
          secondaryColor: this.secondaryColorFormControl.value ?? defaultSecondaryColor,
        })
        .subscribe((response) => {
          this.vectorGraphics.push({ ...response, name: vectorGraphic.name, svg: vectorGraphic.svg });
        });
    });
  }

  removeIcon(vectorGraphic: SvgToPathConverterResultDTO) {
    this.vectorGraphics = this.vectorGraphics.filter((vc) => vc !== vectorGraphic);
  }

  onSave() {
    this.store.dispatch(
      new VectorGraphicsSaveAction(
        this.vectorGraphics.map((vectorGraphic) => ({
          id: undefined,
          image: undefined,
          name: vectorGraphic.name,
          paths: vectorGraphic.paths,
          dimensions: vectorGraphic.dimensions,
          rotation: 0,
          scale: 1,
          translationX: 0,
          translationY: 0,
          mask: false,
        })),
        () => this.navigateBack()
      )
    );
  }

  onDiscard() {
    this.navigateBack();
  }

  private initForm() {
    this.form = this.formBuilder.group({
      svgFile: this.svgFileFormControl,
      primaryColor: this.primaryColorFormControl,
      secondaryColor: this.secondaryColorFormControl,
    });
  }

  private navigateBack() {
    this.router.navigate(['/icons']);
  }
}
