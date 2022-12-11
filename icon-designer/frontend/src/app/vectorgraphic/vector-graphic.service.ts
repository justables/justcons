/** generated code */

import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


import { BASE_URL } from '../config';
import { VectorGraphicDTO } from './/vector-graphic-dto';

@Injectable({
    providedIn: 'root',
})
export class VectorGraphicService {
  constructor(protected http: HttpClient) {}

  delete(vectorGraphics: VectorGraphicDTO[]): Observable<VectorGraphicDTO[]> {
    return this.http.delete<VectorGraphicDTO[]>(`${BASE_URL}/vector-graphic`, { body: vectorGraphics })
  }

  getAll(): Observable<VectorGraphicDTO[]> {
    return this.http.get<VectorGraphicDTO[]>(`${BASE_URL}/vector-graphic`)
  }

  save(vectorGraphics: VectorGraphicDTO[]): Observable<VectorGraphicDTO[]> {
    return this.http.post<VectorGraphicDTO[]>(`${BASE_URL}/vector-graphic`, vectorGraphics)
  }
}
