/** generated code */

import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { VectorGraphicDTO } from './/vector-graphic-dto';
import { BASE_URL } from '../config';

@Injectable({
  providedIn: 'root',
})
export class VectorGraphicService {
  constructor(protected http: HttpClient) {}

  getAll(): Observable<VectorGraphicDTO[]> {
    return this.http.get<VectorGraphicDTO[]>(`${BASE_URL}/vector-graphics`);
  }
}
