/** generated code */

import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


import { BASE_URL } from '../config';
import { SvgToPathConverterRequestDTO } from './/svg-to-path-converter-request-dto';
import { SvgToPathConverterResultDTO } from './/svg-to-path-converter-result-dto';

@Injectable({
    providedIn: 'root',
})
export class SvgToPathConverterService {
  constructor(protected http: HttpClient) {}

  svgToPath(svgConverterDTO: SvgToPathConverterRequestDTO): Observable<SvgToPathConverterResultDTO> {
    return this.http.post<SvgToPathConverterResultDTO>(`${BASE_URL}/svg-to-path`, svgConverterDTO)
  }
}
